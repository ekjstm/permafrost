#include "H5Acallbacks.h"

herr_t JHDF_attributeHandler(hid_t loc_id, const char* name, void* op_data) {
    jstring Name;

    JHDF_handler_t* hdl = (JHDF_handler_t*) op_data;
    JNIEnv* jenv = hdl->jenv;
     Name = (*jenv)->NewStringUTF(jenv, name);
    (*jenv)->CallVoidMethod(jenv, hdl->handler, hdl->handlerFcn, loc_id, Name);
    return (0);
}

JNIEXPORT herr_t JNICALL Java_zonule_hdf_libhdf_AttributeHandler_doRunHandler(JNIEnv *jenv, jclass jcls, jobject handler, jstring methodName, jint locId) {
    jclass clazz = NULL;
    char* method_name = NULL;    
    jmethodID mid = NULL;
    JHDF_handler_t* hdl;
    jclass excep = NULL;
    herr_t status = -1;
    hid_t loc_id = (hid_t) locId;
    unsigned idx = 0;

    (void)jcls; /* no-op to stop whining about unused parameter. */

    clazz = (*jenv)->GetObjectClass(jenv, handler);
    method_name = (char*)(*jenv)->GetStringUTFChars(jenv, methodName, 0);
    mid = (*jenv)->GetMethodID(jenv, clazz, method_name, "(ILjava/lang/String;)V");       
    if (mid == NULL) {
        (*jenv)->ExceptionClear(jenv);
        excep = (*jenv)->FindClass(jenv, "java/lang/NoSuchMethodException");
        (*jenv)->ThrowNew(jenv, excep, method_name);
    }
    (*jenv)->ReleaseStringUTFChars(jenv, methodName, method_name);
    
    hdl = (JHDF_handler_t*) malloc(sizeof(JHDF_handler_t));
    hdl->jenv = jenv;
    hdl->handler = (*jenv)->NewGlobalRef(jenv, handler);
    hdl->handlerFcn = mid;

    status = H5Aiterate1(loc_id, &idx, &JHDF_attributeHandler, hdl);

    (*jenv)->DeleteGlobalRef(jenv, handler);

    return (status);
}