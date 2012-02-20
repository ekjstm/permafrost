#ifndef H5CALLBACKS_H
#define H5CALLBACKS_H


#include <stdlib.h>
#include <jni.h>


typedef struct {
  JNIEnv* jenv;
  jobject handler;
  jmethodID handlerFcn;
} JHDF_handler_t;


#endif