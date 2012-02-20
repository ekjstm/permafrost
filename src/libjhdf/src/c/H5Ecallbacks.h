#ifndef H5ECALLBACKS_H
#define H5ECALLBACKS_H

#include "H5Epublic.h"

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <jni.h>
#include <assert.h>


typedef struct {
  JavaVM* jvm;
  JNIEnv* jenv;
  jobject logger;
  jmethodID logFcn;
} JHDF_error_log_t;

typedef struct {
  JavaVM* jvm;
  JNIEnv* jenv;
  jclass steClass;
  jmethodID steConstructor;
  jstring baseMessage;
  jint base_err_maj;
  jint base_err_min;
  int stacksize;
  jobject* stacktrace;
} JHDF_exception_t;

herr_t JHDF_walkAndLogError(hid_t estack, void* jerr);
herr_t JHDF_walkAndThrowError(hid_t estack, void* client_data);
herr_t JHDF_log_walk_cb(int n, const H5E_error2_t *err_desc, void *client_data);
herr_t JHDF_exception_walk_cb(int n, const H5E_error2_t *err_desc, void *client_data);
jclass JHDF_map_err_num(JNIEnv* jenv, int maj, int min);



#endif
