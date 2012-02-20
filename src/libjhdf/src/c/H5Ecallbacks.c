#include "H5Ecallbacks.h"
#include <CRTDBG.H>

herr_t JHDF_walkAndLogError(hid_t estack, void* client_data) {	   
	JHDF_error_log_t* jerr = (JHDF_error_log_t*) client_data;
	JavaVM* jvm = NULL;
	herr_t ret_value = 0;
   
	jvm = jerr->jvm;
	(*jvm)->GetEnv(jvm, (void **)&(jerr->jenv), JNI_VERSION_1_6); 
    
	ret_value = H5Ewalk2(estack, H5E_WALK_UPWARD, (H5E_walk2_t)&JHDF_log_walk_cb, jerr);

	jerr->jenv = NULL;

    return (ret_value);    
}


herr_t JHDF_log_walk_cb(int n, const H5E_error2_t* err_desc, void* client_data) {   
    JHDF_error_log_t* jerr = (JHDF_error_log_t*) client_data;  
    jstring jmsg = NULL;
	
	JNIEnv* jenv = jerr->jenv;

    (void)n; /* no-op to stop whining about unused parameter. */	
	   
   /* Get descriptions for the major and minor error numbers */
    {
        int mlen = 0;
        char* msg = NULL;
        const char* maj_str = H5Eget_major(err_desc->maj_num);
        const char* min_str = H5Eget_minor(err_desc->min_num);
        
        mlen = 7 + strlen(maj_str) + 
				   strlen(min_str) +
				   strlen(err_desc->func_name) +
				   strlen(err_desc->file_name) +
				   8 + //strlen(err_desc->line) + 
				   1;
        msg = calloc(mlen, sizeof(char));

        /* Print error message */ 
		_snprintf(msg, mlen, "%s; %s. %s(%s:%u)", 
			maj_str, 
			min_str,
			err_desc->func_name,
			err_desc->file_name, 
			err_desc->line
		);

        jmsg = (*jenv)->NewStringUTF(jenv, msg);
        
        (*jenv)->CallVoidMethod(jenv, jerr->logger, jerr->logFcn, jmsg);

        free(msg);
    }

    return (0);
}

herr_t JHDF_walkAndThrowError(hid_t estack, JHDF_exception_t* jerr) {
    JavaVM* jvm = NULL;
	JNIEnv* jenv = NULL;
	jint res = 0;    
    int n = 0;
    /* int stackSize = H5Eget_num(H5Eget_current_stack()); */
    const size_t stackSize = 32;

	jclass innerClass = NULL;
    jmethodID innerCtor = NULL;
    jthrowable innerEx = NULL;
    jclass exClass = NULL;
    jmethodID exCtor = NULL;
    jthrowable newEx = NULL;
    jmethodID btSetter = NULL;
    jobjectArray stack = NULL;
    herr_t ret_value = -1;

	jvm = jerr->jvm;
	res = (*jvm)->GetEnv(jvm, (void **)&(jerr->jenv), JNI_VERSION_1_6);
	jenv = jerr->jenv;

    /* Set up the error struct */
    jerr->steClass = (*jenv)->FindClass(jenv, "java/lang/StackTraceElement");
    jerr->steConstructor = (*jenv)->GetMethodID(jenv, jerr->steClass, "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V"); 
    jerr->stacktrace = (jobject*) calloc(stackSize, sizeof(jobject));
    jerr->stacksize = stackSize;

    /* Walk the stack into the array */
    ret_value = H5Ewalk2(estack, H5E_WALK_UPWARD, (H5E_walk2_t)&JHDF_exception_walk_cb, jerr);

    /* Unpack the C array into a java object array. 
     * The 1.6 API doesn't support H5Eget_num
     * so we can't make a sensible allocation until after the stack
     * walked.
     */
    for(n=0; n<jerr->stacksize; n++) {
        if (jerr->stacktrace[n] != NULL) { 
            continue; 
        } else {
            jerr->stacksize = n;
        }
    }
    stack = (*jenv)->NewObjectArray(jenv, jerr->stacksize, jerr->steClass, NULL);
    for (n=0; n<jerr->stacksize; n++) {
        (*jenv)->SetObjectArrayElement(jenv, stack, n, jerr->stacktrace[n]);
    }  

    /* Create the exception */
	innerClass = (*jenv)->FindClass(jenv, "permafrost/hdf/libhdf/NativeRuntimeException");
	innerCtor = (*jenv)->GetMethodID(jenv, innerClass, "<init>", "(Ljava/lang/String;)V");
	innerEx = (*jenv)->NewObject(jenv, innerClass, innerCtor, jerr->baseMessage);
	exClass = JHDF_map_err_num(jenv, jerr->base_err_maj, jerr->base_err_min);
    exCtor = (*jenv)->GetMethodID(jenv, exClass, "<init>", "(Ljava/lang/String;Ljava/lang/Throwable;)V"); 
    newEx = (*jenv)->NewObject(jenv, exClass, exCtor, jerr->baseMessage, innerEx);

    /* Set exception backtrace */
    btSetter = (*jenv)->GetMethodID(jenv, innerClass, "setStackTrace", "([Ljava/lang/StackTraceElement;)V");        
    (*jenv)->CallVoidMethod(jenv, innerEx, btSetter, stack);

    /* Cleanup */
    assert((*jenv)->ExceptionCheck(jenv) != 1);
    free(jerr->stacktrace);
	jerr->stacktrace = NULL;
	jerr->jenv = NULL;

    /* Throw on return. */
    (*jenv)->Throw(jenv, newEx);

    return (ret_value);    
}


herr_t JHDF_exception_walk_cb(int n, const H5E_error2_t* err_desc, void* client_data) {
    JHDF_exception_t* jerr = (JHDF_exception_t*) client_data;
   
    jstring         jclassName = NULL;
    const char*     fileName = NULL;
    jstring         jfileName = NULL;
    const char*     fcnName = NULL;
    jstring         jfcnName = NULL;
	JNIEnv*         jenv = jerr->jenv;


    /* jclass          steClass = NULL; */
    /* jmethodID       steCtor = NULL; */
    jobject         newSTE = NULL;
    
    /* Construct the error message. */   
    if (n==0) {
        char* msg = NULL;
        size_t mlen = 0;
       
        const char* maj_str = H5Eget_major(err_desc->maj_num);
        const char* min_str = H5Eget_minor(err_desc->min_num);
        mlen = 3 + strlen(maj_str) + strlen(min_str) + 1;
        msg = (char*) calloc(mlen, sizeof(char));

        _snprintf(msg, mlen, 
			"%s; %s.", 
		    maj_str, min_str);

        jerr->baseMessage = (*jenv)->NewStringUTF(jenv, msg); 
        jerr->base_err_maj = err_desc->maj_num;
        jerr->base_err_min = err_desc->min_num;
       free(msg);
    }    

    /* get the class name */   
    jclassName = (*jenv)->NewStringUTF(jenv, "(Native Method)"); 

    /* get the file name */
    fileName = err_desc->file_name;
    jfileName = (*jenv)->NewStringUTF(jenv, fileName); 
    
    /* get the function name */
    fcnName = err_desc->func_name;
    jfcnName = (*jenv)->NewStringUTF(jenv, fcnName);

    /* create the exception */
    /* steClass = (*jenv)->FindClass(jenv, "Ljava/lang/StackTraceElement;"); */
    /* jmethodID steCtor = (*jenv)->GetMethodID(jenv, steClass, "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V");   */
    newSTE = (*jenv)->NewObject(jenv, jerr->steClass, jerr->steConstructor, jclassName, jfcnName, jfileName, err_desc->line);

    /* update the error structure */
    /*(*jenv)->SetObjectArrayElement(jenv, jerr->stackTrace, n, newSTE);*/
    if (n >= jerr->stacksize) {
        jerr->stacktrace = realloc(jerr->stacktrace, n+1);
        jerr->stacksize++;
    }
    jerr->stacktrace[n] = newSTE;

    return (0);
}


JNIEXPORT void JNICALL Java_permafrost_hdf_libhdf_NativeErrorHandler_disable(JNIEnv *jenv, jobject self, jobject handler) {
	jclass clazz = NULL;
	jmethodID mid = NULL;
    JHDF_error_log_t* jerr;
    jclass excep = NULL;
	hid_t currStack = 0;

	(void)self;

	clazz = (*jenv)->GetObjectClass(jenv, handler);        
    mid = (*jenv)->GetMethodID(jenv, clazz, "error", "(Ljava/lang/String;)V");       
    if (mid == NULL) {
        (*jenv)->ExceptionClear(jenv);
        excep = (*jenv)->FindClass(jenv, "java/lang/NoSuchMethodException");
        (*jenv)->ThrowNew(jenv, excep, "No method void error(String).");
		return;
    }
    
    jerr = malloc(sizeof(JHDF_error_log_t));
	(*jenv)->GetJavaVM(jenv, &(jerr->jvm));
    jerr->jenv = jenv;
    jerr->logger = (*jenv)->NewGlobalRef(jenv, handler);
    jerr->logFcn = mid;

	currStack = H5E_DEFAULT;
    H5Eset_auto2(currStack, &JHDF_walkAndLogError, jerr);
}

JNIEXPORT void JNICALL Java_permafrost_hdf_libhdf_NativeErrorHandler_enable(JNIEnv *jenv, jobject self) {  
    JHDF_exception_t* jerr;
	hid_t currStack = 0;

	(void)self;

    /* Enable runtime heap checks. */
    
    // Get current flag
    //int tmpFlag = _CrtSetDbgFlag( _CRTDBG_REPORT_FLAG );
    //// Turn on leak-checking bit
    //tmpFlag |= _CRTDBG_CHECK_ALWAYS_DF;
    //tmpFlag |= _CRTDBG_LEAK_CHECK_DF;
    //// Set flag to the new value
    //_CrtSetDbgFlag( tmpFlag );
    //
    //// check heap
    //assert(_CrtCheckMemory());   
	
    jerr = (JHDF_exception_t*) malloc(sizeof(JHDF_exception_t));
	(*jenv)->GetJavaVM(jenv, &(jerr->jvm));
    jerr->jenv = jenv;
    jerr->stacktrace = NULL;
    jerr->steClass = NULL;
    jerr->steConstructor = NULL;
    jerr->stacksize = 0;

	currStack = H5E_DEFAULT;
    H5Eset_auto2(currStack, &JHDF_walkAndThrowError, jerr);
}

jclass JHDF_map_err_num(JNIEnv* jenv, int maj, int min) {
	jclass exClass = NULL;	
	/* Argument errors */	
	if (maj == H5E_ARGS) {
		/* Information is uinitialized */
		if (min == H5E_UNINITIALIZED) {
			exClass = (*jenv)->FindClass(jenv, "java/lang/IllegalArgumentException");
			return (exClass);

		/* Feature is unsupported */
		} else if (min == H5E_UNSUPPORTED) {
			exClass = (*jenv)->FindClass(jenv, "java/lang/UnsupportedOperationException");
			return (exClass);

		/* Inappropriate type */
		} else if (min == H5E_BADTYPE) {
			exClass = (*jenv)->FindClass(jenv, "java/lang/IllegalArgumentException");
			return (exClass);

		/* Out of range */
		} else if (min == H5E_BADRANGE) {
			exClass = (*jenv)->FindClass(jenv, "java/lang/IllegalArgumentException");
			return (exClass);

		/* Bad value */
		} else if (min ==  H5E_BADVALUE) {
			exClass = (*jenv)->FindClass(jenv, "java/lang/IllegalArgumentException");
			return (exClass);
		}
	} else if (maj == H5E_RESOURCE) {

	} else if (maj == H5E_INTERNAL) {

	/* File accessability errors */
	} else if (maj == H5E_FILE) {

		/* File already exists */
		if (min == H5E_FILEEXISTS) {
			exClass = (*jenv)->FindClass(jenv, "java/io/IOException");
			return (exClass);

		/* File already open */
		} else if (min == H5E_FILEOPEN) {

		/* Unable to create file */
		} else if (min == H5E_CANTCREATE) {
			exClass = (*jenv)->FindClass(jenv, "java/io/IOException");
			return (exClass);

		/* Unable to open file */
		} else if (min == H5E_CANTOPENFILE) {
			exClass = (*jenv)->FindClass(jenv, "java/io/FileNotFoundException");
			return (exClass);

		/* Unable to close file */
		} else if (min == H5E_CANTCLOSEFILE) {

		/* Not an HDF5 file */
		} else if (min == H5E_NOTHDF5) {
			exClass = (*jenv)->FindClass(jenv, "permafrost/hdf/libhdf/DataFormatException");
			return (exClass); 

		/* Bad file ID accessed */
		} else if (min == H5E_BADFILE) {

		/* File has been truncated */
		} else if (min == H5E_TRUNCATED) {

		/* File mount error */
		} else if (min == H5E_MOUNT) {
			exClass = (*jenv)->FindClass(jenv, "java/io/IOException");
			return (exClass);
		}
	} else if (maj == H5E_IO) {
	} else if (maj == H5E_FUNC) {
	} else if (maj == H5E_ATOM) {
	} else if (maj == H5E_CACHE) {
	} else if (maj == H5E_LINK) {
	} else if (maj == H5E_BTREE) {
	} else if (maj == H5E_SYM) {
	} else if (maj == H5E_HEAP) {
	/* Object header */
	} else if (maj == H5E_OHDR) {
		/* Bad object header link count */
		if(min == H5E_LINKCOUNT) {
			exClass = (*jenv)->FindClass(jenv, "permafrost/hdf/libhdf/DataFormatException");
			return (exClass); 

		/* Wrong version number */
		} else if (min == H5E_VERSION) {
			exClass = (*jenv)->FindClass(jenv, "permafrost/hdf/libhdf/DataFormatException");
			return (exClass); 

		/* Alignment error */
		} else if (min == H5E_ALIGNMENT) {
			exClass = (*jenv)->FindClass(jenv, "permafrost/hdf/libhdf/DataFormatException");
			return (exClass); 

		/* Unrecognized message */
		} else if (min == H5E_BADMESG) {
			exClass = (*jenv)->FindClass(jenv, "permafrost/hdf/libhdf/DataFormatException");
			return (exClass); 
		}
	} else if (maj == H5E_DATATYPE) {
	} else if (maj == H5E_DATASPACE) {
	} else if (maj == H5E_DATASET) {
	} else if (maj == H5E_STORAGE) {
	} else if (maj == H5E_PLIST) {
	} else if (maj == H5E_ATTR) {

	} else if (maj == H5E_PLINE) {
		/* Requested filter is not available */
		if (min == H5E_NOFILTER) {
			exClass = (*jenv)->FindClass(jenv, "java/lang/UnsupportedOperationException");
			return (exClass);
		
		/* Filter present but encoding disabled */
		} else if (min == H5E_NOENCODER) {
			exClass = (*jenv)->FindClass(jenv, "java/lang/UnsupportedOperationException");
			return (exClass);
		} 

	} else if (maj == H5E_EFL) {
	} else if (maj == H5E_REFERENCE) {
	} else if (maj == H5E_VFL) {
	} else if (maj == H5E_TST) {
	} else if (maj == H5E_RS) {
	} else if (maj == H5E_ERROR) {
	} else if (maj == H5E_SLIST) {
	} else if (maj == H5E_FSPACE) {
	} else if (maj == H5E_SOHM) {

	}

	exClass = (*jenv)->FindClass(jenv, "permafrost/hdf/libhdf/NativeRuntimeException");
	return (exClass);       

}

//JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *jvm, void *reserved) {
//
//	Java_permafrost_hdf_libhdf_NativeErrorHandler_enable(JNIEnv *jenv, jclass jcls)
//	return (JNI_VERSION_1_6);
//}
