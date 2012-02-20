%typemap(jni) char **HSTRARR_OUTPUT "jobjectArray"
%typemap(jtype) char **HSTRARR_OUTPUT "byte[][]"
%typemap(jstype) char **HSTRARR_OUTPUT "byte[][]"
%typemap(in) char **HSTRARR_OUTPUT (jint size) {
    int i = 0;
    size = JCALL1(GetArrayLength, jenv, $input);
#ifdef __cplusplus 
    $1 = new char*[size];
#else
    $1 = (char **)calloc(size, sizeof(char *));
#endif 
    for (i=0; i<size; i++) {
        jbyteArray bytes = (jbyteArray) JCALL2(GetObjectArrayElement, jenv, $input, i);
#ifdef __cplusplus
        $1[i] = new char [JCALL1(GetArrayLength, jenv, bytes)+1];
#else
        $1[i] = (char *) calloc(JCALL1(GetArrayLength, jenv, bytes)+1, sizeof(char *));
#endif       
    }
}

%typemap(argout) char** HSTRARR_OUTPUT {
  int i = 0;
  for (i=0; i<size$argnum; i++) {
    jbyteArray bytes = (jbyteArray) JCALL2(GetObjectArrayElement, jenv, $input, i);
    JCALL4(SetByteArrayRegion, jenv, bytes, 0, strlen($1[i])+1, $1[i]);    
  }
}

%typemap(freearg) char **HSTRARR_OUTPUT {   
  int i;
  for (i=0; i<size$argnum; i++) {
#ifdef __cplusplus
     delete[] $1[i];
#else
     free($1[i]);
#endif
  }
#ifdef __cplusplus
  delete[] $1;
#else
  free($1);
#endif
}

%typemap(out) char **HSTRARR_OUTPUT ""

%typemap(javain) char **HSTRARR_OUTPUT "$javainput"
%typemap(javaout) char **HSTRARR_OUTPUT  {
    return $jnicall;
  }


%typemap(jni) void* HSTRARR_INPUT "jobjectArray"
%typemap(jtype) void* HSTRARR_INPUT "String[]"
%typemap(javain) void* HSTRARR_INPUT "$javainput"
%typemap(javaout) void* HSTRARR_INPUT  {
    return $jnicall;
  }
%typemap(jstype) void* HSTRARR_INPUT "String[]"
%typemap(in) void* HSTRARR_INPUT (jint size, char** cbuf) {
    int i = 0;
    size = JCALL1(GetArrayLength, jenv, $input);
#ifdef __cplusplus
   cbuf = new char*[size];
#else
    cbuf = (char **)calloc(size, sizeof(char *));
#endif
    $1 = cbuf;
    for (i = 0; i<size; i++) {
        jstring j_string = (jstring)JCALL2(GetObjectArrayElement, jenv, $input, i);
        const char *c_string = JCALL2(GetStringUTFChars, jenv, j_string, 0);
#ifdef __cplusplus
        cbuf[i] = new char [strlen(c_string)+1];
#else
        cbuf[i] = (char *)calloc(strlen(c_string)+1, sizeof(const char *));
#endif
        strcpy(cbuf[i], c_string);
        JCALL2(ReleaseStringUTFChars, jenv, j_string, c_string);
        JCALL1(DeleteLocalRef, jenv, j_string);
    }
}

%typemap(freearg) void* HSTRARR_INPUT {
    int i;
    for (i=0; i<size$argnum; i++)
#ifdef __cplusplus
      delete[] cbuf$argnum[i];
    delete[] cbuf$argnum;
#else
      free(cbuf$argnum[i]);
    free(cbuf$argnum);
#endif
}



%typemap(jni)    (char* CSTRING, int SIZE) "jbyteArray"
%typemap(jtype)  (char* CSTRING, int SIZE) "byte[]"
%typemap(javain) (char* CSTRING, int SIZE) "$javainput"
%typemap(jstype) (char* CSTRING, int SIZE) "byte[]"

%typemap(in) (char* CSTRING, int SIZE) {
    $1 = ($1_ltype) JCALL2(GetByteArrayElements, jenv, $input, NULL);
    $2 = ($2_ltype) JCALL1(GetArrayLength, jenv, $input);
}

%typemap(argout) (char* CSTRING, int SIZE) ""

%typemap(freearg) (char* CSTRING, int SIZE) {
    JCALL3(ReleaseByteArrayElements, jenv, $input, (jbyte *) $1, 0); 
}

%typemap(jni) PSTRING* INPUT "jstring"
%typemap(jtype) PSTRING* INPUT "String"
%typemap(jstype) PSTRING* INPUT "String"
%typemap(javain) PSTRING* INPUT "$javainput"
%typemap(in, noblock=1) PSTRING* INPUT (char* tmp) {
  $1 = 0;
  if ($input) {
    tmp = (char*)JCALL2(GetStringUTFChars, jenv, $input, 0);
    $1 = &tmp;
    if (!$1) return -1;
  }
 }

%typemap(freearg, noblock=1) PSTRING* INPUT {
  if ($1) JCALL2(ReleaseStringUTFChars, jenv, $input, (const char *)tmp$argnum); 
 }

%typemap(jni) PSTRING* OUTPUT "jbyteArray"
%typemap(jtype) PSTRING* OUTPUT "byte[]"
%typemap(jstype) PSTRING* OUTPUT "byte[]"
%typemap(javain) PSTRING* OUTPUT "$javainput"
%typemap(in) PSTRING* OUTPUT (jint size, char* tmp) {
    size = JCALL1(GetArrayLength, jenv, $input);
#ifdef __cplusplus 
    tmp = new char[size];
#else
    tmp = (char *)calloc(size, sizeof(char));
#endif     
    if(!tmp) return (-1);
    $1 = ($1_ltype)&tmp;
 }

%typemap(freearg) PSTRING* OUTPUT {
  JCALL4(SetByteArrayRegion, jenv, bytes, 0, strlen($*1), $*1]); 
 }
