/*
 * This typemap defines several support functions for transfering array data:
 * HARR_JavaNewArray, HARR_JavaArrayIn, HARR_JavaArrayArgout and 
 * HARR_JavaArrayOut. To prevent multiple definition of these functions 
 * when using harrays.i in multiple modules, define the preprocessor variable
 * HARR_NOARRAYS.
 *
 * HARR_JavaNewArray examines a Java array and allocates a C array of the same 
 * size.
 * HARR_JavaArrayIn allocates and copies the given Java array to a C array.
 * HARR_JavaArrayArgout copies a C array to the given Java array. The length 
 * of the Java array determines the number of elements copied.
 * HARR_JavaArrayOut creates a new Java array and copies the elements of the 
 * given C array to it.
 *
 * Typemaps are defined for all numeric types. For example:
 * %apply long* INPUT  {long* ibuffer}
 * %apply long* OUTPUT {long* obuffer}
 * %apply long* INOUT  {long* iobuffer}
 *
 * The complete list:
 * bool* INPUT            bool* OUTPUT            bool* INOUT
 * signed char* INPUT     signed char* OUTPUT     signed char* INOUT
 * unsigned char* INPUT   unsigned char* OUTPUT   unsigned char* INOUT
 * short* INPUT           short* OUTPUT           short* INOUT
 * unsigned short* INPUT  unsigned short* OUTPUT  unsigned short* INOUT
 * int* INPUT             int* OUTPUT             int* INOUT
 * unsigned int* INPUT    unsigned int* OUTPUT    unsigned int* INOUT
 * long* INPUT            long* OUTPUT            long* INOUT
 * unsigned long* INPUT   unsigned long* OUTPUT   unsigned long* INOUT
 * long long* INPUT       long long* OUTPUT       long long* INOUT
 * float* INPUT           float* OUTPUT           float* INOUT
 * double* INPUT          double* OUTPUT          double* INOUT
 */

/* Array support functions declarations macro */
%define HARR_ARRAYS_DECL(CTYPE, JNITYPE, JAVATYPE, JFUNCNAME)
%{
int HARR_JavaNewArray##JFUNCNAME (JNIEnv *jenv, CTYPE **carr, JNITYPE##Array input);
int HARR_JavaArrayIn##JFUNCNAME (JNIEnv *jenv,  CTYPE **carr, JNITYPE##Array input);
void HARR_JavaArrayArgout##JFUNCNAME (JNIEnv *jenv, CTYPE *carr, JNITYPE##Array input);
JNITYPE##Array HARR_JavaArrayOut##JFUNCNAME (JNIEnv *jenv, CTYPE *result, jsize sz);
%}
%enddef

/* Array support functions macro */
%define HARR_ARRAYS_IMPL(CTYPE, JNITYPE, JAVATYPE, JFUNCNAME)
%{
/* CTYPE[] support */
int HARR_JavaNewArray##JFUNCNAME (JNIEnv *jenv, CTYPE **carr, JNITYPE##Array input) {
  jsize sz;
  if (!input) {
    SWIG_JavaThrowException(jenv, SWIG_JavaNullPointerException, "null array");
    return 0;
  }
  sz = JCALL1(GetArrayLength, jenv, input);
%}
#ifdef __cplusplus
%{  *carr = new CTYPE[sz]; %}
#else
%{  *carr = (CTYPE*) calloc(sz, sizeof(CTYPE)); %}
#endif
%{  if (!*carr) {
    SWIG_JavaThrowException(jenv, SWIG_JavaOutOfMemoryError, "array memory allocation failed");
    return 0;
  }
  return 1; 
}

int HARR_JavaArrayIn##JFUNCNAME (JNIEnv *jenv, CTYPE **carr, JNITYPE##Array input) {
  int i;
  JNITYPE *jarr;
  jsize sz;
  if (!input) {
    SWIG_JavaThrowException(jenv, SWIG_JavaNullPointerException, "null array");
    return 0;
  }
  sz = JCALL1(GetArrayLength, jenv, input);
  jarr = JCALL2(Get##JAVATYPE##ArrayElements, jenv, input, 0);
  if (!jarr)
    return 0; %}
#ifdef __cplusplus
%{  *carr = new CTYPE[sz]; %}
#else
%{  *carr = (CTYPE*) calloc(sz, sizeof(CTYPE)); %}
#endif
%{  if (!*carr) {
    SWIG_JavaThrowException(jenv, SWIG_JavaOutOfMemoryError, "array memory allocation failed");
    return 0;
  }
  for (i=0; i<sz; i++)
    HARR_TYPEMAP_ARRAY_ELEMENT_ASSIGN(CTYPE)

  JCALL3(Release##JAVATYPE##ArrayElements, jenv, input, jarr, 0);
  return 1;
}

int HARR_JavaArrayArgout##JFUNCNAME (JNIEnv *jenv, CTYPE *carr, JNITYPE##Array input) {
  int i;
  jsize sz;
  JNITYPE *jarr;
  jarr = JCALL2(Get##JAVATYPE##ArrayElements, jenv, input, 0);
  if (!jarr)
    return 0;
  sz = JCALL1(GetArrayLength, jenv, input);
  for (i=0; i<sz; i++)
    jarr[i] = (JNITYPE)carr[i];
  JCALL3(Release##JAVATYPE##ArrayElements, jenv, input, jarr, 0);
  return 1;
}

JNITYPE##Array HARR_JavaArrayOut##JFUNCNAME (JNIEnv *jenv, CTYPE *result, jsize sz) {
  JNITYPE *arr;
  int i;
  JNITYPE##Array jresult = JCALL1(New##JAVATYPE##Array, jenv, sz);
  if (!jresult)
    return NULL;
  arr = JCALL2(Get##JAVATYPE##ArrayElements, jenv, jresult, 0);
  if (!arr)
    return NULL;
  for (i=0; i<sz; i++)
    arr[i] = (JNITYPE)result[i];
  JCALL3(Release##JAVATYPE##ArrayElements, jenv, jresult, arr, 0);
  return jresult;
}
%}
%enddef

%{
   #if defined(HARR_NOINCLUDE) || defined(HARR_NOARRAYS)    
%}

#ifdef __cplusplus
HARR_ARRAYS_DECL(bool, jboolean, Boolean, Bool)       /* bool[] */
#endif

HARR_ARRAYS_DECL(signed char, jbyte, Byte, Schar)     /* signed char[] */
HARR_ARRAYS_DECL(unsigned char, jshort, Short, Uchar) /* unsigned char[] */
HARR_ARRAYS_DECL(short, jshort, Short, Short)         /* short[] */
HARR_ARRAYS_DECL(unsigned short, jint, Int, Ushort)   /* unsigned short[] */
HARR_ARRAYS_DECL(int, jint, Int, Int)                 /* int[] */
HARR_ARRAYS_DECL(unsigned int, jlong, Long, Uint)     /* unsigned int[] */
HARR_ARRAYS_DECL(long, jint, Int, Long)               /* long[] */
HARR_ARRAYS_DECL(unsigned long, jlong, Long, Ulong)   /* unsigned long[] */
HARR_ARRAYS_DECL(jlong, jlong, Long, Longlong)        /* long long[] */
HARR_ARRAYS_DECL(float, jfloat, Float, Float)         /* float[] */
HARR_ARRAYS_DECL(double, jdouble, Double, Double)     /* double[] */

%{
#else
%}

#ifdef __cplusplus
/* Bool array element assignment different to other types to keep Visual C++ quiet */
#define HARR_TYPEMAP_ARRAY_ELEMENT_ASSIGN(CTYPE) (*carr)[i] = (jarr[i] != 0);
HARR_ARRAYS_IMPL(bool, jboolean, Boolean, Bool)       /* bool[] */
#undef HARR_TYPEMAP_ARRAY_ELEMENT_ASSIGN
#endif

#define HARR_TYPEMAP_ARRAY_ELEMENT_ASSIGN(CTYPE) (*carr)[i] = (CTYPE)jarr[i];
HARR_ARRAYS_IMPL(signed char, jbyte, Byte, Schar)     /* signed char[] */
HARR_ARRAYS_IMPL(unsigned char, jshort, Short, Uchar) /* unsigned char[] */
HARR_ARRAYS_IMPL(short, jshort, Short, Short)         /* short[] */
HARR_ARRAYS_IMPL(unsigned short, jint, Int, Ushort)   /* unsigned short[] */
HARR_ARRAYS_IMPL(int, jint, Int, Int)                 /* int[] */
HARR_ARRAYS_IMPL(unsigned int, jlong, Long, Uint)     /* unsigned int[] */
HARR_ARRAYS_IMPL(long, jint, Int, Long)               /* long[] */
HARR_ARRAYS_IMPL(unsigned long, jlong, Long, Ulong)   /* unsigned long[] */
HARR_ARRAYS_IMPL(jlong, jlong, Long, Longlong)        /* long long[] */
HARR_ARRAYS_IMPL(float, jfloat, Float, Float)         /* float[] */
HARR_ARRAYS_IMPL(double, jdouble, Double, Double)     /* double[] */

%{
#endif
%}

/* Arrays of primitive types use the following macro. The array typemaps use support functions. */

%define HARR_ARRAYS_TYPEMAPS(CTYPE, JTYPE, JNITYPE, JFUNCNAME, JNIDESC)

%typemap(jni) CTYPE INPUT[ANY], CTYPE INPUT[]               %{JNITYPE##Array%}
%typemap(jtype) CTYPE INPUT[ANY], CTYPE INPUT[]             %{JTYPE[]%}
%typemap(jstype) CTYPE INPUT[ANY], CTYPE INPUT[]            %{JTYPE[]%}

%typemap(in) CTYPE INPUT[] 
%{  if (!HARR_JavaArrayIn##JFUNCNAME(jenv, &$1, $input)) return -1/*$null*/; %}
%typemap(in) CTYPE INPUT[ANY] 
%{  if ($input && JCALL1(GetArrayLength, jenv, $input) != $1_size) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIndexOutOfBoundsException, "incorrect array size");
    return -1/*$null*/;
  }
 if (!HARR_JavaArrayIn##JFUNCNAME(jenv, &$1, $input)) return -1/*$null*/; %}

%typemap(argout) CTYPE INPUT[ANY], CTYPE INPUT[] ""
%typemap(out) CTYPE INPUT[ANY] ""
%typemap(out) CTYPE INPUT[] ""
%typemap(freearg) CTYPE INPUT[ANY], CTYPE INPUT[] 
#ifdef __cplusplus
%{ delete [] $1; %}
#else
%{ free($1); %}
#endif

%typemap(javain) CTYPE INPUT[ANY], CTYPE INPUT[] "$javainput"
%typemap(javaout) CTYPE INPUT[ANY], CTYPE INPUT[] {
    return $jnicall;
  }


%typemap(jni) CTYPE OUTPUT[ANY], CTYPE OUTPUT[]               %{JNITYPE##Array%}
%typemap(jtype) CTYPE OUTPUT[ANY], CTYPE OUTPUT[]             %{JTYPE[]%}
%typemap(jstype) CTYPE OUTPUT[ANY], CTYPE OUTPUT[]            %{JTYPE[]%}

%typemap(in) CTYPE OUTPUT[] %{if (!HARR_JavaNewArray##JFUNCNAME(jenv, &$1, $input)) return -1/*$null*/;%}
%typemap(in) CTYPE OUTPUT[ANY]
%{  if ($input && JCALL1(GetArrayLength, jenv, $input) != $1_size) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIndexOutOfBoundsException, "incorrect array size");
    return -1/*$null*/;
  }
 if (!HARR_JavaNewArray##JFUNCNAME(jenv, &$1, $input)) return -1/*$null*/; %}

%typemap(argout) CTYPE OUTPUT[ANY], CTYPE OUTPUT[]
%{ HARR_JavaArrayArgout##JFUNCNAME(jenv, $1, $input); %}
%typemap(out) CTYPE OUTPUT[ANY] 
%{$result = SWIG_JavaArrayOut##JFUNCNAME(jenv, $1, $1_dim0); %}
%typemap(out) CTYPE OUTPUT[] ""
%typemap(freearg) CTYPE OUTPUT[ANY], CTYPE OUTPUT[] 
#ifdef __cplusplus
%{ delete [] $1; %}
#else
%{ free($1); %}
#endif

%typemap(javain) CTYPE OUTPUT[ANY], CTYPE OUTPUT[] "$javainput"
%typemap(javaout) CTYPE OUTPUT[ANY], CTYPE OUTPUT[] {
    return $jnicall;
  }

%typemap(jni) CTYPE INOUT[ANY], CTYPE INOUT[]               %{JNITYPE##Array%}
%typemap(jtype) CTYPE INOUT[ANY], CTYPE INOUT[]             %{JTYPE[]%}
%typemap(jstype) CTYPE INOUT[ANY], CTYPE INOUT[]            %{JTYPE[]%}

%typemap(in) CTYPE INOUT[] 
%{  if (!HARR_JavaArrayIn##JFUNCNAME(jenv, &$1, $input)) return -1/*$null*/; %}
%typemap(in) CTYPE INOUT[ANY] 
%{  if ($input && JCALL1(GetArrayLength, jenv, $input) != $1_size) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIndexOutOfBoundsException, "incorrect array size");
    return -1/*$null*/;
  }
 if (!HARR_JavaArrayIn##JFUNCNAME(jenv, &$1, $input)) return -1/*$null*/; %}

%typemap(argout) CTYPE INOUT[ANY], CTYPE INOUT[]
%{ HARR_JavaArrayArgout##JFUNCNAME(jenv, $1, $input); %}
%typemap(out) CTYPE INOUT[ANY] 
%{$result = SWIG_JavaArrayOut##JFUNCNAME(jenv, $1, $1_dim0); %}
%typemap(out) CTYPE INOUT[] ""
%typemap(freearg) CTYPE INOUT[ANY], CTYPE INOUT[] 
#ifdef __cplusplus
%{ delete [] $1; %}
#else
%{ free($1); %}
#endif

%typemap(javain) CTYPE INOUT[ANY], CTYPE INOUT[] "$javainput"
%typemap(javaout) CTYPE INOUT[ANY], CTYPE INOUT[] {
    return $jnicall;
  }

%enddef

HARR_ARRAYS_TYPEMAPS(bool, boolean, jboolean, Bool, "[Z")       /* bool[ANY] */
HARR_ARRAYS_TYPEMAPS(signed char, byte, jbyte, Schar, "[B")     /* signed char[ANY] */
HARR_ARRAYS_TYPEMAPS(unsigned char, short, jshort, Uchar, "[S") /* unsigned char[ANY] */
HARR_ARRAYS_TYPEMAPS(short, short, jshort, Short, "[S")         /* short[ANY] */
HARR_ARRAYS_TYPEMAPS(unsigned short, int, jint, Ushort, "[I")   /* unsigned short[ANY] */
HARR_ARRAYS_TYPEMAPS(int, int, jint, Int, "[I")                 /* int[ANY] */
HARR_ARRAYS_TYPEMAPS(unsigned int, long, jlong, Uint, "[J")     /* unsigned int[ANY] */
HARR_ARRAYS_TYPEMAPS(long, int, jint, Long, "[I")               /* long[ANY] */
HARR_ARRAYS_TYPEMAPS(unsigned long, long, jlong, Ulong, "[J")   /* unsigned long[ANY] */
HARR_ARRAYS_TYPEMAPS(long long, long, jlong, Longlong, "[J")    /* long long[ANY] */
HARR_ARRAYS_TYPEMAPS(float, float, jfloat, Float, "[F")         /* float[ANY] */
HARR_ARRAYS_TYPEMAPS(double, double, jdouble, Double, "[D")     /* double[ANY] */

