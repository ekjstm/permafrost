#include "H5Lcallbacks.h"

herr_t JHDFL_visitAndOpen(hid_t loc_id, const char* name, const H5L_info_t* info, void* op_data) {     	
	JHDFL_iter_t* hdl;
	JNIEnv* jenv;

	hdl = (JHDFL_iter_t*) op_data;
	jenv = hdl->jenv;
	hdl->idChild = H5Oopen(loc_id, name, hdl->lapl_id);
	if (hdl->idChild > 0) {
		hdl->typeChild = H5Iget_type(hdl->idChild);
		hdl->jstrChild = (*jenv)->NewStringUTF(jenv, name);
	}

	return (hdl->idChild);
}

herr_t JHDFL_visitAndAppendName(hid_t loc_id, const char* name, const H5L_info_t* info, void* op_data) {
	JHDFL_string_arr_t* hdl;
	JNIEnv* jenv;

	hdl = (JHDFL_string_arr_t*) op_data;
	jenv = hdl->jenv;
	if (hdl->nStrings == hdl->szStrings) {
		if (JHDFL_resizeStringArr(hdl, hdl->szStrings + 5) < 0) {
			return (-1);
		}
	}
	hdl->pStrings[hdl->nStrings] = (*jenv)->NewStringUTF(jenv, name);
	hdl->nStrings++;
    return (0);
}

herr_t JHDFL_resizeStringArr(JHDFL_string_arr_t* hdl, size_t n) {
	void* arr = NULL;
	arr = realloc(hdl->pStrings, n*sizeof(jstring));
	if (arr == NULL) {
		return (-1);
	} else {
		hdl->pStrings = arr;
		hdl->szStrings = n;
		return (1);
	}
}

//JNIEXPORT herr_t JNICALL Java_zonule_hdf_libhdf_GroupHandler_doRunHandler(JNIEnv *jenv, jclass jcls, jobject handler, jstring methodName, jint locId, jstring groupName) {
//    jclass clazz = NULL;
//    char* method_name = NULL;    
//    jmethodID mid = NULL;
//    JHDF_handler_t* hdl;
//    jclass excep = NULL;
//    herr_t status = -1;
//    hid_t loc_id = (hid_t) locId;
//    char* group_name = NULL;
//    int idx = 0;
//
//    (void)jcls; /* no-op to stop whining about unused parameter. */
//
//    clazz = (*jenv)->GetObjectClass(jenv, handler);
//    method_name = (char*)(*jenv)->GetStringUTFChars(jenv, methodName, 0);
//    mid = (*jenv)->GetMethodID(jenv, clazz, method_name, "(ILjava/lang/String;)V");       
//    if (mid == NULL) {
//        (*jenv)->ExceptionClear(jenv);
//        excep = (*jenv)->FindClass(jenv, "java/lang/NoSuchMethodException");
//        (*jenv)->ThrowNew(jenv, excep, method_name);
//    }
//    (*jenv)->ReleaseStringUTFChars(jenv, methodName, method_name);
//    
//    hdl = (JHDF_handler_t*) malloc(sizeof(JHDF_handler_t));
//    hdl->jenv = jenv;
//    hdl->handler = (*jenv)->NewGlobalRef(jenv, handler);
//    hdl->handlerFcn = mid;
//
//    group_name = (char*)(*jenv)->GetStringUTFChars(jenv, groupName, 0);
//    status = H5Giterate(loc_id, group_name, &idx, &JHDF_groupHandler, hdl);
//    (*jenv)->ReleaseStringUTFChars(jenv, groupName, group_name);
//    (*jenv)->DeleteGlobalRef(jenv, handler);
//
//    return (status);
//}