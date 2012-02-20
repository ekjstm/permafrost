/* Array support functions declarations macro */
%define HARR_ARRAYS2D_DECL(CTYPE, JAVATYPE, JFUNCNAME)
%{
int HARR_JavaNewArray2D##JFUNCNAME (JNIEnv *jenv, CTYPE ***carr, jobjectArray input);
int HARR_JavaArrayIn2D##JFUNCNAME (JNIEnv *jenv,  CTYPE ***carr, jobjectArray input);
void HARR_JavaArrayArgout2D##JFUNCNAME (JNIEnv *jenv, CTYPE ***carr, jobjectArray input);
jobjectArray HARR_JavaArrayOut2D##JFUNCNAME (JNIEnv *jenv, CTYPE *result, jsize sx, jsize sy);
%}
%enddef

/* Array support functions macro */
%define HARR_ARRAYS2D_IMPL(CTYPE, JNITYPE, JAVATYPE, JFUNCNAME)
%{
/* CTYPE[][] support */
int HARR_JavaNewArray2D##JFUNCNAME (JNIEnv *jenv, CTYPE ***carr, jobjectArray input) {
  int n;
  jsize sx, sy;
  CTYPE* chunk;
  JNITYPE##Array slice;
  if (!input) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, "Array must be non-null.");
    return 0;
  }
  sx = JCALL1(GetArrayLength, jenv, input);
  if (sx < 1) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, "Array must have non-zero width.");
  }
  slice = JCALL2(GetObjectArrayElement, jenv, input, 0);
  sy = JCALL1(GetArrayLength, jenv, slice);
  JCALL1(DeleteLocalRef, jenv, slice);
%}
#ifdef __cplusplus
%{  chunk = new CTYPE[sx*sy]; %}
#else
%{  chunk = (CTYPE*) calloc(sx*sy, sizeof(CTYPE)); %}
#endif
%{  if (!chunk) {
    SWIG_JavaThrowException(jenv, SWIG_JavaOutOfMemoryError, "Array memory allocation failed");
    return 0;
  }
%}
#ifdef __cplusplus
%{  *carr = new *CTYPE[sx]; %}
#else
%{  *carr = (CTYPE**) calloc(sx, sizeof(CTYPE*)); %}
#endif
%{  if (!*carr) {
    free(chunk);
    SWIG_JavaThrowException(jenv, SWIG_JavaOutOfMemoryError, "Array memory allocation failed");
    return 0;
  }
  for (n=0; n<sx; n++) { 
    (*carr)[n] = &chunk[n*sy];
  }
  return 1; 
}

int HARR_JavaArrayIn2D##JFUNCNAME (JNIEnv *jenv, CTYPE ***carr, jobjectArray input) {
  int n, m;
  JNITYPE##Array slice;
  CTYPE* chunk;
  JNITYPE *jarr;
  jsize sx, sy;
  if (!input) {
    SWIG_JavaThrowException(jenv, SWIG_JavaNullPointerException, "Array must be non-null.");
    return 0;
  }
  sx = JCALL1(GetArrayLength, jenv, input);
  if (sx < 1) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, "Array must have non-zero width.");
  }
  slice = JCALL2(GetObjectArrayElement, jenv, input, 0);
  sy = JCALL1(GetArrayLength, jenv, slice);
  JCALL1(DeleteLocalRef, jenv, slice);
%}
#ifdef __cplusplus
%{  chunk = new CTYPE[sx*sy]; %}
#else
%{  chunk = (CTYPE*) calloc(sx*sy, sizeof(CTYPE)); %}
#endif
%{  if (!chunk) {
    SWIG_JavaThrowException(jenv, SWIG_JavaOutOfMemoryError, "Array memory allocation failed");
    return 0;
  }
%}
#ifdef __cplusplus
%{  *carr = new *CTYPE[sx]; %}
#else
%{  *carr = (CTYPE**) calloc(sx, sizeof(CTYPE*)); %}
#endif
%{  if (!*carr) {
    free(chunk);
    SWIG_JavaThrowException(jenv, SWIG_JavaOutOfMemoryError, "Array memory allocation failed");
    return 0;
  }
  for (n=0; n<sx; n++) { 
    (*carr)[n] = &chunk[n*sy];
    slice = JCALL2(GetObjectArrayElement, jenv, input, n);
    if (!slice) {
      SWIG_JavaThrowException(jenv, SWIG_JavaNullPointerException, "Null array slice reference.");
      return 0;
    }
    sy = JCALL1(GetArrayLength, jenv, slice);
    jarr = JCALL2(Get##JAVATYPE##ArrayElements, jenv, slice, 0);
    if (!jarr) {
      SWIG_JavaThrowException(jenv, SWIG_JavaNullPointerException, "Null array slice pointer.");
      return 0; 
    }
    for (m=0; m<sy; m++) {
      HARR_TYPEMAP_ARRAY2D_ELEMENT_ASSIGN(CTYPE)
    }
    JCALL3(Release##JAVATYPE##ArrayElements, jenv, slice, jarr, 0);
    JCALL1(DeleteLocalRef, jenv, slice);
  }
  return 1;
}

int HARR_JavaArrayArgout2D##JFUNCNAME (JNIEnv *jenv, CTYPE ***carr, jobjectArray input) {
  int n, m;
  JNITYPE##Array slice;
  jsize sx, sy;
  JNITYPE *jarr;
  sx = JCALL1(GetArrayLength, jenv, input);
  for (n=0; n<sx; n++) {
    slice = JCALL2(GetObjectArrayElement, jenv, input, n);
    if (!slice) {
      SWIG_JavaThrowException(jenv, SWIG_JavaNullPointerException, "Null array slice");
      return 0;
    }
    jarr = JCALL2(Get##JAVATYPE##ArrayElements, jenv, slice, 0);
    if (!jarr) {
      SWIG_JavaThrowException(jenv, SWIG_JavaNullPointerException, "Null array slice");
      return 0;
    }
    sy = JCALL1(GetArrayLength, jenv, input);
    for (m=0; m<sy; m++) {
      HARR_TYPEMAP_ARRAY2D_JELEMENT_ASSIGN(JNITYPE)
	}
    JCALL3(Release##JAVATYPE##ArrayElements, jenv, slice, jarr, 0);
    JCALL1(DeleteLocalRef, jenv, slice);
  }
  return 1;
}

jobjectArray HARR_JavaArrayOut2D##JFUNCNAME (JNIEnv *jenv, CTYPE ***carr, jsize sx, jsize sy) {
  jobjectArray output;
  JNITYPE##Array slice;
  JNITYPE *jarr;
  int n, m;
  if (sx < 1 || sy < 1) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, "Array dimensions too small.");
    return (0);
      }
  slice = JCALL1(New##JAVATYPE##Array, jenv, sy);
  if (!slice) {
    return 0;
  }
  output = JCALL3(NewObjectArray, jenv, sx, JCALL1(GetObjectClass, jenv, slice), NULL);
  for (n=0; n<sx; n++) {
    jarr = JCALL2(Get##JAVATYPE##ArrayElements, jenv, slice, 0);
    if (!jarr) {
      SWIG_JavaThrowException(jenv, SWIG_JavaNullPointerException, "Null array slice");
      return 0;
    }
    for (m=0; m<sy; m++) {
      HARR_TYPEMAP_ARRAY2D_JELEMENT_ASSIGN(JNITYPE)
	}
    JCALL3(Release##JAVATYPE##ArrayElements, jenv, slice, jarr, 0);
    JCALL3(SetObjectArrayElement, jenv, output, 0, slice);
    JCALL1(DeleteLocalRef, jenv, slice);
  }
  return output;
}
%}
%enddef

%{
   #if defined(HARR_NOINCLUDE2D) || defined(HARR_NOARRAYS2D)    
%}

#ifdef __cplusplus
HARR_ARRAYS2D_DECL(bool, jboolean, Bool)       /* bool[] */
#endif

HARR_ARRAYS2D_DECL(signed char, jbyte, Schar)     /* signed char[] */
HARR_ARRAYS2D_DECL(unsigned char, jshort, Uchar) /* unsigned char[] */
HARR_ARRAYS2D_DECL(short, jshort, Short)         /* short[] */
HARR_ARRAYS2D_DECL(unsigned short, jint, Ushort)   /* unsigned short[] */
HARR_ARRAYS2D_DECL(int, jint, Int)                 /* int[] */
HARR_ARRAYS2D_DECL(unsigned int, jlong, Uint)     /* unsigned int[] */
HARR_ARRAYS2D_DECL(long, jint, Long)               /* long[] */
HARR_ARRAYS2D_DECL(unsigned long, jlong, Ulong)   /* unsigned long[] */
HARR_ARRAYS2D_DECL(jlong, jlong, Longlong)        /* long long[] */
HARR_ARRAYS2D_DECL(float, jfloat, Float)         /* float[] */
HARR_ARRAYS2D_DECL(double, jdouble, Double)     /* double[] */

%{
#else
%}

#ifdef __cplusplus
/* Bool array element assignment different to other types to keep Visual C++ quiet */
#define HARR_TYPEMAP_ARRAY2D_ELEMENT_ASSIGN(CTYPE) (*carr)[n][m] = (jarr[m] != 0);
#define HARR_TYPEMAP_ARRAY2D_JELEMENT_ASSIGN(CTYPE) jarr[m] = ((*carr)[n][m] != 0);
HARR_ARRAYS2D_IMPL(bool, jboolean, Boolean, Bool)       /* bool[] */
#undef HARR_TYPEMAP_ARRAY_ELEMENT_ASSIGN
#undef HARR_TYPEMAP_ARRAY_JELEMENT_ASSIGN
#endif

#define HARR_TYPEMAP_ARRAY2D_ELEMENT_ASSIGN(CTYPE) (*carr)[n][m] = (CTYPE)jarr[m];
#define HARR_TYPEMAP_ARRAY2D_JELEMENT_ASSIGN(JNITYPE) jarr[m] = (JNITYPE)((*carr)[n][m]);
HARR_ARRAYS2D_IMPL(signed char, jbyte, Byte, Schar)     /* signed char[] */
HARR_ARRAYS2D_IMPL(unsigned char, jshort, Short, Uchar) /* unsigned char[] */
HARR_ARRAYS2D_IMPL(short, jshort, Short, Short)         /* short[] */
HARR_ARRAYS2D_IMPL(unsigned short, jint, Int, Ushort)   /* unsigned short[] */
HARR_ARRAYS2D_IMPL(int, jint, Int, Int)                 /* int[] */
HARR_ARRAYS2D_IMPL(unsigned int, jlong, Long, Uint)     /* unsigned int[] */
HARR_ARRAYS2D_IMPL(long, jint, Int, Long)               /* long[] */
HARR_ARRAYS2D_IMPL(unsigned long, jlong, Long, Ulong)   /* unsigned long[] */
HARR_ARRAYS2D_IMPL(jlong, jlong, Long, Longlong)        /* long long[] */
HARR_ARRAYS2D_IMPL(float, jfloat, Float, Float)         /* float[] */
HARR_ARRAYS2D_IMPL(double, jdouble, Double, Double)     /* double[] */

%{
#endif
%}

/* Arrays of primitive types use the following macro. The array typemaps use support functions. */

%define HARR_ARRAYS2D_TYPEMAPS(CTYPE, JTYPE, JNITYPE, JFUNCNAME, JNIDESC)

%typemap(jni) CTYPE INPUT[ANY][ANY], CTYPE INPUT[][]           %{jobjectArray%}
%typemap(jtype) CTYPE INPUT[ANY][ANY], CTYPE INPUT[][]         %{JTYPE[][]%}
%typemap(jstype) CTYPE INPUT[ANY][ANY], CTYPE INPUT[][]        %{JTYPE[][]%}

%typemap(in) CTYPE INPUT[][] 
%{  if (!HARR_JavaArrayIn2D##JFUNCNAME(jenv, &$1, $input)) return -1/*$null*/; %}
%typemap(in) CTYPE INPUT[ANY][ANY] 
%{  if ($input && JCALL1(GetArrayLength, jenv, $input) != $1_size) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIndexOutOfBoundsException, "incorrect array size");
    return -1/*$null*/;
  }
 if (!HARR_JavaArrayIn2D##JFUNCNAME(jenv, &$1, $input)) return -1/*$null*/; %}

%typemap(argout) CTYPE INPUT[ANY][ANY], CTYPE INPUT[][] ""
%typemap(out) CTYPE INPUT[ANY][ANY] ""
%typemap(out) CTYPE INPUT[][] ""
%typemap(freearg) CTYPE INPUT[ANY][ANY], CTYPE INPUT[][] {
#ifdef __cplusplus
     delete[] $1[0]; 
     delete[] $1; 
#else
     free($1[0]);
     free($1);
#endif
}

%typemap(javain)CTYPE INPUT[][] "$javainput"
%typemap(javaout) CTYPE INPUT[][] {
    return $jnicall;
}

%typemap(jni) CTYPE CONTIG_IN[][]           %{jobjectArray%}
%typemap(jtype) CTYPE CONTIG_IN[][]         %{JTYPE[][]%}
%typemap(jstype) CTYPE CONTIG_IN[][]        %{JTYPE[][]%}

%typemap(in) CTYPE CONTIG_IN[][] (CTYPE** arr) {
if (!HARR_JavaArrayIn2D##JFUNCNAME(jenv, &arr, $input)) return -1/*$null*/;
 $1 = *arr;
}

%typemap(argout) CTYPE CONTIG_IN[][] ""
%typemap(out) CTYPE CONTIG_IN[][] ""
%typemap(freearg) CTYPE CONTIG_IN[][] {
#ifdef __cplusplus
     delete[] arr$argnum[0];
     delete[] arr$argnum;
#else
     free(arr$argnum[0]);
     free(arr$argnum);
#endif
}

%typemap(javain) CTYPE CONTIG_IN[][] "$javainput"
%typemap(javaout) CTYPE CONTIG_IN[][] {
    return $jnicall;
  }
%enddef

HARR_ARRAYS2D_TYPEMAPS(long long, long, jlong, Longlong, "[J")    /* long long[ANY] */
