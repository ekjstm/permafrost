#define SWIGJAVA

/* -----------------------------------------------------------------------------
 *  This section contains generic SWIG labels for method/variable
 *  declarations/attributes, and other compiler dependent labels.
 * ----------------------------------------------------------------------------- */

/* template workaround for compilers that cannot correctly implement the C++ standard */
#ifndef SWIGTEMPLATEDISAMBIGUATOR
# if defined(__SUNPRO_CC) && (__SUNPRO_CC <= 0x560)
#  define SWIGTEMPLATEDISAMBIGUATOR template
# elif defined(__HP_aCC)
/* Needed even with `aCC -AA' when `aCC -V' reports HP ANSI C++ B3910B A.03.55 */
/* If we find a maximum version that requires this, the test would be __HP_aCC <= 35500 for A.03.55 */
#  define SWIGTEMPLATEDISAMBIGUATOR template
# else
#  define SWIGTEMPLATEDISAMBIGUATOR
# endif
#endif

/* inline attribute */
#ifndef SWIGINLINE
# if defined(__cplusplus) || (defined(__GNUC__) && !defined(__STRICT_ANSI__))
#   define SWIGINLINE inline
# else
#   define SWIGINLINE
# endif
#endif

/* attribute recognised by some compilers to avoid 'unused' warnings */
#ifndef SWIGUNUSED
# if defined(__GNUC__)
#   if !(defined(__cplusplus)) || (__GNUC__ > 3 || (__GNUC__ == 3 && __GNUC_MINOR__ >= 4))
#     define SWIGUNUSED __attribute__ ((__unused__)) 
#   else
#     define SWIGUNUSED
#   endif
# elif defined(__ICC)
#   define SWIGUNUSED __attribute__ ((__unused__)) 
# else
#   define SWIGUNUSED 
# endif
#endif

#ifndef SWIG_MSC_UNSUPPRESS_4505
# if defined(_MSC_VER)
#   pragma warning(disable : 4505) /* unreferenced local function has been removed */
# endif 
#endif

#ifndef SWIGUNUSEDPARM
# ifdef __cplusplus
#   define SWIGUNUSEDPARM(p)
# else
#   define SWIGUNUSEDPARM(p) p SWIGUNUSED 
# endif
#endif

/* internal SWIG method */
#ifndef SWIGINTERN
# define SWIGINTERN static SWIGUNUSED
#endif

/* internal inline SWIG method */
#ifndef SWIGINTERNINLINE
# define SWIGINTERNINLINE SWIGINTERN SWIGINLINE
#endif

/* exporting methods */
#if (__GNUC__ >= 4) || (__GNUC__ == 3 && __GNUC_MINOR__ >= 4)
#  ifndef GCC_HASCLASSVISIBILITY
#    define GCC_HASCLASSVISIBILITY
#  endif
#endif

#ifndef SWIGEXPORT
# if defined(_WIN32) || defined(__WIN32__) || defined(__CYGWIN__)
#   if defined(STATIC_LINKED)
#     define SWIGEXPORT
#   else
#     define SWIGEXPORT __declspec(dllexport)
#   endif
# else
#   if defined(__GNUC__) && defined(GCC_HASCLASSVISIBILITY)
#     define SWIGEXPORT __attribute__ ((visibility("default")))
#   else
#     define SWIGEXPORT
#   endif
# endif
#endif

/* calling conventions for Windows */
#ifndef SWIGSTDCALL
# if defined(_WIN32) || defined(__WIN32__) || defined(__CYGWIN__)
#   define SWIGSTDCALL __stdcall
# else
#   define SWIGSTDCALL
# endif 
#endif

/* Deal with Microsoft's attempt at deprecating C standard runtime functions */
#if !defined(SWIG_NO_CRT_SECURE_NO_DEPRECATE) && defined(_MSC_VER) && !defined(_CRT_SECURE_NO_DEPRECATE)
# define _CRT_SECURE_NO_DEPRECATE
#endif

/* Deal with Microsoft's attempt at deprecating methods in the standard C++ library */
#if !defined(SWIG_NO_SCL_SECURE_NO_DEPRECATE) && defined(_MSC_VER) && !defined(_SCL_SECURE_NO_DEPRECATE)
# define _SCL_SECURE_NO_DEPRECATE
#endif



/* Fix for jlong on some versions of gcc on Windows */
#if defined(__GNUC__) && !defined(__INTEL_COMPILER)
  typedef long long __int64;
#endif

/* Fix for jlong on 64-bit x86 Solaris */
#if defined(__x86_64)
# ifdef _LP64
#   undef _LP64
# endif
#endif

#include <jni.h>
#include <stdlib.h>
#include <string.h>


/* Support for throwing Java exceptions */
typedef enum {
  SWIG_JavaOutOfMemoryError = 1, 
  SWIG_JavaIOException, 
  SWIG_JavaRuntimeException, 
  SWIG_JavaIndexOutOfBoundsException,
  SWIG_JavaArithmeticException,
  SWIG_JavaIllegalArgumentException,
  SWIG_JavaNullPointerException,
  SWIG_JavaDirectorPureVirtual,
  SWIG_JavaUnknownError
} SWIG_JavaExceptionCodes;

typedef struct {
  SWIG_JavaExceptionCodes code;
  const char *java_exception;
} SWIG_JavaExceptions_t;


static void SWIGUNUSED SWIG_JavaThrowException(JNIEnv *jenv, SWIG_JavaExceptionCodes code, const char *msg) {
  jclass excep;
  static const SWIG_JavaExceptions_t java_exceptions[] = {
    { SWIG_JavaOutOfMemoryError, "java/lang/OutOfMemoryError" },
    { SWIG_JavaIOException, "java/io/IOException" },
    { SWIG_JavaRuntimeException, "java/lang/RuntimeException" },
    { SWIG_JavaIndexOutOfBoundsException, "java/lang/IndexOutOfBoundsException" },
    { SWIG_JavaArithmeticException, "java/lang/ArithmeticException" },
    { SWIG_JavaIllegalArgumentException, "java/lang/IllegalArgumentException" },
    { SWIG_JavaNullPointerException, "java/lang/NullPointerException" },
    { SWIG_JavaDirectorPureVirtual, "java/lang/RuntimeException" },
    { SWIG_JavaUnknownError,  "java/lang/UnknownError" },
    { (SWIG_JavaExceptionCodes)0,  "java/lang/UnknownError" } };
  const SWIG_JavaExceptions_t *except_ptr = java_exceptions;

  while (except_ptr->code != code && except_ptr->code)
    except_ptr++;

  (*jenv)->ExceptionClear(jenv);
  excep = (*jenv)->FindClass(jenv, except_ptr->java_exception);
  if (excep)
    (*jenv)->ThrowNew(jenv, excep, msg);
}


/* Contract support */

#define SWIG_contract_assert(nullreturn, expr, msg) if (!(expr)) {SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, msg); return nullreturn; } else

#include "hdf5.h"
#include <stdint.h>		// Use the C99 official header



int HARR_JavaNewArraySchar (JNIEnv *jenv, signed char **carr, jbyteArray input);
int HARR_JavaArrayInSchar (JNIEnv *jenv,  signed char **carr, jbyteArray input);
void HARR_JavaArrayArgoutSchar (JNIEnv *jenv, signed char *carr, jbyteArray input);
jbyteArray HARR_JavaArrayOutSchar (JNIEnv *jenv, signed char *result, jsize sz);


int HARR_JavaNewArrayUchar (JNIEnv *jenv, unsigned char **carr, jshortArray input);
int HARR_JavaArrayInUchar (JNIEnv *jenv,  unsigned char **carr, jshortArray input);
void HARR_JavaArrayArgoutUchar (JNIEnv *jenv, unsigned char *carr, jshortArray input);
jshortArray HARR_JavaArrayOutUchar (JNIEnv *jenv, unsigned char *result, jsize sz);


int HARR_JavaNewArrayShort (JNIEnv *jenv, short **carr, jshortArray input);
int HARR_JavaArrayInShort (JNIEnv *jenv,  short **carr, jshortArray input);
void HARR_JavaArrayArgoutShort (JNIEnv *jenv, short *carr, jshortArray input);
jshortArray HARR_JavaArrayOutShort (JNIEnv *jenv, short *result, jsize sz);


int HARR_JavaNewArrayUshort (JNIEnv *jenv, unsigned short **carr, jintArray input);
int HARR_JavaArrayInUshort (JNIEnv *jenv,  unsigned short **carr, jintArray input);
void HARR_JavaArrayArgoutUshort (JNIEnv *jenv, unsigned short *carr, jintArray input);
jintArray HARR_JavaArrayOutUshort (JNIEnv *jenv, unsigned short *result, jsize sz);


int HARR_JavaNewArrayInt (JNIEnv *jenv, int **carr, jintArray input);
int HARR_JavaArrayInInt (JNIEnv *jenv,  int **carr, jintArray input);
void HARR_JavaArrayArgoutInt (JNIEnv *jenv, int *carr, jintArray input);
jintArray HARR_JavaArrayOutInt (JNIEnv *jenv, int *result, jsize sz);


int HARR_JavaNewArrayUint (JNIEnv *jenv, unsigned int **carr, jlongArray input);
int HARR_JavaArrayInUint (JNIEnv *jenv,  unsigned int **carr, jlongArray input);
void HARR_JavaArrayArgoutUint (JNIEnv *jenv, unsigned int *carr, jlongArray input);
jlongArray HARR_JavaArrayOutUint (JNIEnv *jenv, unsigned int *result, jsize sz);


int HARR_JavaNewArrayLong (JNIEnv *jenv, long **carr, jintArray input);
int HARR_JavaArrayInLong (JNIEnv *jenv,  long **carr, jintArray input);
void HARR_JavaArrayArgoutLong (JNIEnv *jenv, long *carr, jintArray input);
jintArray HARR_JavaArrayOutLong (JNIEnv *jenv, long *result, jsize sz);


int HARR_JavaNewArrayUlong (JNIEnv *jenv, unsigned long **carr, jlongArray input);
int HARR_JavaArrayInUlong (JNIEnv *jenv,  unsigned long **carr, jlongArray input);
void HARR_JavaArrayArgoutUlong (JNIEnv *jenv, unsigned long *carr, jlongArray input);
jlongArray HARR_JavaArrayOutUlong (JNIEnv *jenv, unsigned long *result, jsize sz);


int HARR_JavaNewArrayLonglong (JNIEnv *jenv, jlong **carr, jlongArray input);
int HARR_JavaArrayInLonglong (JNIEnv *jenv,  jlong **carr, jlongArray input);
void HARR_JavaArrayArgoutLonglong (JNIEnv *jenv, jlong *carr, jlongArray input);
jlongArray HARR_JavaArrayOutLonglong (JNIEnv *jenv, jlong *result, jsize sz);


int HARR_JavaNewArrayFloat (JNIEnv *jenv, float **carr, jfloatArray input);
int HARR_JavaArrayInFloat (JNIEnv *jenv,  float **carr, jfloatArray input);
void HARR_JavaArrayArgoutFloat (JNIEnv *jenv, float *carr, jfloatArray input);
jfloatArray HARR_JavaArrayOutFloat (JNIEnv *jenv, float *result, jsize sz);


int HARR_JavaNewArrayDouble (JNIEnv *jenv, double **carr, jdoubleArray input);
int HARR_JavaArrayInDouble (JNIEnv *jenv,  double **carr, jdoubleArray input);
void HARR_JavaArrayArgoutDouble (JNIEnv *jenv, double *carr, jdoubleArray input);
jdoubleArray HARR_JavaArrayOutDouble (JNIEnv *jenv, double *result, jsize sz);


int HARR_JavaNewArray2DSchar (JNIEnv *jenv, signed char ***carr, jobjectArray input);
int HARR_JavaArrayIn2DSchar (JNIEnv *jenv,  signed char ***carr, jobjectArray input);
void HARR_JavaArrayArgout2DSchar (JNIEnv *jenv, signed char ***carr, jobjectArray input);
jobjectArray HARR_JavaArrayOut2DSchar (JNIEnv *jenv, signed char *result, jsize sx, jsize sy);


int HARR_JavaNewArray2DUchar (JNIEnv *jenv, unsigned char ***carr, jobjectArray input);
int HARR_JavaArrayIn2DUchar (JNIEnv *jenv,  unsigned char ***carr, jobjectArray input);
void HARR_JavaArrayArgout2DUchar (JNIEnv *jenv, unsigned char ***carr, jobjectArray input);
jobjectArray HARR_JavaArrayOut2DUchar (JNIEnv *jenv, unsigned char *result, jsize sx, jsize sy);


int HARR_JavaNewArray2DShort (JNIEnv *jenv, short ***carr, jobjectArray input);
int HARR_JavaArrayIn2DShort (JNIEnv *jenv,  short ***carr, jobjectArray input);
void HARR_JavaArrayArgout2DShort (JNIEnv *jenv, short ***carr, jobjectArray input);
jobjectArray HARR_JavaArrayOut2DShort (JNIEnv *jenv, short *result, jsize sx, jsize sy);


int HARR_JavaNewArray2DUshort (JNIEnv *jenv, unsigned short ***carr, jobjectArray input);
int HARR_JavaArrayIn2DUshort (JNIEnv *jenv,  unsigned short ***carr, jobjectArray input);
void HARR_JavaArrayArgout2DUshort (JNIEnv *jenv, unsigned short ***carr, jobjectArray input);
jobjectArray HARR_JavaArrayOut2DUshort (JNIEnv *jenv, unsigned short *result, jsize sx, jsize sy);


int HARR_JavaNewArray2DInt (JNIEnv *jenv, int ***carr, jobjectArray input);
int HARR_JavaArrayIn2DInt (JNIEnv *jenv,  int ***carr, jobjectArray input);
void HARR_JavaArrayArgout2DInt (JNIEnv *jenv, int ***carr, jobjectArray input);
jobjectArray HARR_JavaArrayOut2DInt (JNIEnv *jenv, int *result, jsize sx, jsize sy);


int HARR_JavaNewArray2DUint (JNIEnv *jenv, unsigned int ***carr, jobjectArray input);
int HARR_JavaArrayIn2DUint (JNIEnv *jenv,  unsigned int ***carr, jobjectArray input);
void HARR_JavaArrayArgout2DUint (JNIEnv *jenv, unsigned int ***carr, jobjectArray input);
jobjectArray HARR_JavaArrayOut2DUint (JNIEnv *jenv, unsigned int *result, jsize sx, jsize sy);


int HARR_JavaNewArray2DLong (JNIEnv *jenv, long ***carr, jobjectArray input);
int HARR_JavaArrayIn2DLong (JNIEnv *jenv,  long ***carr, jobjectArray input);
void HARR_JavaArrayArgout2DLong (JNIEnv *jenv, long ***carr, jobjectArray input);
jobjectArray HARR_JavaArrayOut2DLong (JNIEnv *jenv, long *result, jsize sx, jsize sy);


int HARR_JavaNewArray2DUlong (JNIEnv *jenv, unsigned long ***carr, jobjectArray input);
int HARR_JavaArrayIn2DUlong (JNIEnv *jenv,  unsigned long ***carr, jobjectArray input);
void HARR_JavaArrayArgout2DUlong (JNIEnv *jenv, unsigned long ***carr, jobjectArray input);
jobjectArray HARR_JavaArrayOut2DUlong (JNIEnv *jenv, unsigned long *result, jsize sx, jsize sy);


int HARR_JavaNewArray2DLonglong (JNIEnv *jenv, jlong ***carr, jobjectArray input);
int HARR_JavaArrayIn2DLonglong (JNIEnv *jenv,  jlong ***carr, jobjectArray input);
void HARR_JavaArrayArgout2DLonglong (JNIEnv *jenv, jlong ***carr, jobjectArray input);
jobjectArray HARR_JavaArrayOut2DLonglong (JNIEnv *jenv, jlong *result, jsize sx, jsize sy);


int HARR_JavaNewArray2DFloat (JNIEnv *jenv, float ***carr, jobjectArray input);
int HARR_JavaArrayIn2DFloat (JNIEnv *jenv,  float ***carr, jobjectArray input);
void HARR_JavaArrayArgout2DFloat (JNIEnv *jenv, float ***carr, jobjectArray input);
jobjectArray HARR_JavaArrayOut2DFloat (JNIEnv *jenv, float *result, jsize sx, jsize sy);


int HARR_JavaNewArray2DDouble (JNIEnv *jenv, double ***carr, jobjectArray input);
int HARR_JavaArrayIn2DDouble (JNIEnv *jenv,  double ***carr, jobjectArray input);
void HARR_JavaArrayArgout2DDouble (JNIEnv *jenv, double ***carr, jobjectArray input);
jobjectArray HARR_JavaArrayOut2DDouble (JNIEnv *jenv, double *result, jsize sx, jsize sy);


SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDread_1char(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jbyteArray buf 	
	) {
		jint result = -1;
		char* cbuf = (char*) 0;
		
		if (!HARR_JavaNewArraySchar(jenv, &cbuf, buf)) return -1;
		
		result = H5Dread(
			(hid_t) loc_id, 
			H5T_NATIVE_CHAR, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) cbuf
			);	

		HARR_JavaArrayArgoutSchar(jenv, cbuf, buf); 
		free(cbuf);
		return (result);
}


SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDread_1short(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jshortArray buf 	
	) {
		jint result = -1;
		short* cbuf = (short*) 0;
		
		if (!HARR_JavaNewArrayShort(jenv, &cbuf, buf)) return -1;
		
		result = H5Dread(
			(hid_t) loc_id, 
			H5T_NATIVE_SHORT, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) cbuf
			);	

		HARR_JavaArrayArgoutShort(jenv, cbuf, buf); 
		free(cbuf);
		return (result);
}

SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDread_1int(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jintArray buf 	
	) {
		jint result = -1;
		int* cbuf = (int*) 0;
		
		if (!HARR_JavaNewArrayInt(jenv, &cbuf, buf)) return -1;
		
		result = H5Dread(
			(hid_t) loc_id, 
			H5T_NATIVE_INT, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) cbuf
			);	

		HARR_JavaArrayArgoutInt(jenv, cbuf, buf); 
		free(cbuf);
		return (result);
}

SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDread_1long_1long(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jlongArray buf 	
	) {
		jint result = -1;
		long long* cbuf = (long long*) 0;
		
		if (!HARR_JavaNewArrayLonglong(jenv, &cbuf, buf)) return -1;
		
		result = H5Dread(
			(hid_t) loc_id, 
			H5T_NATIVE_INT64, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) cbuf
			);	

		HARR_JavaArrayArgoutLonglong(jenv, cbuf, buf); 
		free(cbuf);
		return (result);
}

SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDread_1float(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jfloatArray buf 	
	) {
		jint result = -1;
		float* cbuf = (float*) 0;
		
		if (!HARR_JavaNewArrayFloat(jenv, &cbuf, buf)) return -1;
		
		result = H5Dread(
			(hid_t) loc_id, 
			H5T_NATIVE_FLOAT, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) cbuf
			);	

		HARR_JavaArrayArgoutFloat(jenv, cbuf, buf); 
		free(cbuf);
		return (result);
}

SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDread_1double(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jdoubleArray buf 	
	) {
		jint result = -1;
		double* cbuf = (double*) 0;
		
		if (!HARR_JavaNewArrayDouble(jenv, &cbuf, buf)) return -1;
		
		result = H5Dread(
			(hid_t) loc_id, 
			H5T_NATIVE_DOUBLE, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) cbuf
			);	

		HARR_JavaArrayArgoutDouble(jenv, cbuf, buf); 
		free(cbuf);
		return (result);
}

SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDwrite_1char(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jbyteArray buf 	
	) {
		jint result = -1;
		char* cbuf = (char*) 0;
		
		if (!HARR_JavaArrayInSchar(jenv, &cbuf, buf)) return -1;
		
		result = H5Dwrite(
			(hid_t) loc_id, 
			H5T_NATIVE_CHAR, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) cbuf
			);	
		
		free(cbuf);
		return (result);
}

SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDwrite_1short(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jshortArray buf 	
	) {
		jint result = -1;
		short* cbuf = (short*) 0;
		
		if (!HARR_JavaArrayInShort(jenv, &cbuf, buf)) return -1;
		
		result = H5Dwrite(
			(hid_t) loc_id, 
			H5T_NATIVE_SHORT, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) cbuf
			);	
		
		free(cbuf);
		return (result);
}

SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDwrite_1int(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jintArray buf 	
	) {
		jint result = -1;
		int* cbuf = (int*) 0;
		
		if (!HARR_JavaArrayInInt(jenv, &cbuf, buf)) return -1;
		
		result = H5Dwrite(
			(hid_t) loc_id, 
			H5T_NATIVE_INT, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) cbuf
			);	
		
		free(cbuf);
		return (result);
}

SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDwrite_1long_1long(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jlongArray buf 	
	) {
		jint result = -1;
		long long* cbuf = (long long*) 0;
		
		if (!HARR_JavaArrayInLonglong(jenv, &cbuf, buf)) return -1;
		
		result = H5Dwrite(
			(hid_t) loc_id, 
			H5T_NATIVE_INT64, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) cbuf
			);	
		
		free(cbuf);
		return (result);
}

SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDwrite_1float(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jfloatArray buf 	
	) {
		jint result = -1;
		float* cbuf = (float*) 0;
		
		if (!HARR_JavaArrayInFloat(jenv, &cbuf, buf)) return -1;
		
		result = H5Dwrite(
			(hid_t) loc_id, 
			H5T_NATIVE_FLOAT, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) cbuf
			);	
		
		free(cbuf);
		return (result);
}

SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDwrite_1double(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jdoubleArray buf 	
	) {
		jint result = -1;
		double* cbuf = (double*) 0;
		
		if (!HARR_JavaArrayInDouble(jenv, &cbuf, buf)) return -1;
		
		result = H5Dwrite(
			(hid_t) loc_id, 
			H5T_NATIVE_DOUBLE, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) cbuf
			);	
		
		free(cbuf);
		return (result);
}

SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDread_1char_12d(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jobjectArray buf 	
	) {
		jint result = -1;
		char** cbuf = (char**) 0;
		
		if (!HARR_JavaNewArray2DSchar(jenv, &cbuf, buf)) return -1;
		
		result = H5Dread(
			(hid_t) loc_id, 
			H5T_NATIVE_CHAR, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) (*cbuf)
			);	

		HARR_JavaArrayArgout2DSchar(jenv, &cbuf, buf); 
		free(*cbuf);
		free(cbuf);
		return (result);
}

SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDread_1short_12d(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jobjectArray buf 	
	) {
		jint result = -1;
		short** cbuf = (short**) 0;
		
		if (!HARR_JavaNewArray2DShort(jenv, &cbuf, buf)) return -1;
		
		result = H5Dread(
			(hid_t) loc_id, 
			H5T_NATIVE_SHORT, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) (*cbuf)
			);	

		HARR_JavaArrayArgout2DShort(jenv, &cbuf, buf); 
		free(*cbuf);
		free(cbuf);
		return (result);
}

SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDread_1int_12d(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jobjectArray buf 	
	) {
		jint result = -1;
		int** cbuf = (int**) 0;
		
		if (!HARR_JavaNewArray2DInt(jenv, &cbuf, buf)) return -1;
		
		result = H5Dread(
			(hid_t) loc_id, 
			H5T_NATIVE_INT, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) (*cbuf)
			);	

		HARR_JavaArrayArgout2DInt(jenv, &cbuf, buf); 
		free(*cbuf);
		free(cbuf);
		return (result);
}

SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDread_1long_1long_12d(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jobjectArray buf 	
	) {
		jint result = -1;
		long long** cbuf = (long long**) 0;
		
		if (!HARR_JavaNewArray2DLonglong(jenv, &cbuf, buf)) return -1;
		
		result = H5Dread(
			(hid_t) loc_id, 
			H5T_NATIVE_INT64, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) (*cbuf)
			);	

		HARR_JavaArrayArgout2DLonglong(jenv, &cbuf, buf); 
		free(*cbuf);
		free(cbuf);
		return (result);
}

SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDread_1float_12d(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jobjectArray buf 	
	) {
		jint result = -1;
		float** cbuf = (float**) 0;
		
		if (!HARR_JavaNewArray2DFloat(jenv, &cbuf, buf)) return -1;
		
		result = H5Dread(
			(hid_t) loc_id, 
			H5T_NATIVE_FLOAT, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) (*cbuf)
			);	

		HARR_JavaArrayArgout2DFloat(jenv, &cbuf, buf); 
		free(*cbuf);
		free(cbuf);
		return (result);
}

SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDread_1double_12d(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jobjectArray buf 	
	) {
		jint result = -1;
		double** cbuf = (double**) 0;
		
		if (!HARR_JavaNewArray2DDouble(jenv, &cbuf, buf)) return -1;
		
		result = H5Dread(
			(hid_t) loc_id, 
			H5T_NATIVE_DOUBLE, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) (*cbuf)
			);	

		HARR_JavaArrayArgout2DDouble(jenv, &cbuf, buf); 
		free(*cbuf);
		free(cbuf);
		return (result);
}


SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDwrite_1char_12d(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jobjectArray buf 	
	) {
		jint result = -1;
		char** cbuf = (char**) 0;
		
		if (!HARR_JavaArrayIn2DSchar(jenv, &cbuf, buf)) return -1;
		
		result = H5Dwrite(
			(hid_t) loc_id, 
			H5T_NATIVE_CHAR, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) (*cbuf)
			);	

		free(*cbuf);
		free(cbuf);
		return (result);
}

SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDwrite_1short_12d(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jobjectArray buf 	
	) {
		jint result = -1;
		short** cbuf = (short**) 0;
		
		if (!HARR_JavaArrayIn2DShort(jenv, &cbuf, buf)) return -1;
		
		result = H5Dwrite(
			(hid_t) loc_id, 
			H5T_NATIVE_SHORT, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) (*cbuf)
			);	

		free(*cbuf);
		free(cbuf);
		return (result);
}


SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDwrite_1int_12d(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jobjectArray buf 	
	) {
		jint result = -1;
		int** cbuf = (int**) 0;
		
		if (!HARR_JavaArrayIn2DInt(jenv, &cbuf, buf)) return -1;
		
		result = H5Dwrite(
			(hid_t) loc_id, 
			H5T_NATIVE_INT, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) (*cbuf)
			);	

		free(*cbuf);
		free(cbuf);
		return (result);
}

SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDwrite_1long_1long_12d(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jobjectArray buf 	
	) {
		jint result = -1;
		long long** cbuf = (long long**) 0;
		
		if (!HARR_JavaArrayIn2DLonglong(jenv, &cbuf, buf)) return -1;
		
		result = H5Dwrite(
			(hid_t) loc_id, 
			H5T_NATIVE_INT64, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) (*cbuf)
			);	

		free(*cbuf);
		free(cbuf);
		return (result);
}

SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDwrite_1float_12d(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jobjectArray buf 	
	) {
		jint result = -1;
		float** cbuf = (float**) 0;
		
		if (!HARR_JavaArrayIn2DFloat(jenv, &cbuf, buf)) return -1;
		
		result = H5Dwrite(
			(hid_t) loc_id, 
			H5T_NATIVE_FLOAT, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) (*cbuf)
			);	

		free(*cbuf);
		free(cbuf);
		return (result);
}

SWIGEXPORT jint JNICALL Java_permafrost_hdf_libhdf_DatasetLibExtensionJNI_JHDwrite_1double_12d(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id,  
	jint mem_space_id, 
	jint file_space_id, 
	jint plist_id,
	jobjectArray buf 	
	) {
		jint result = -1;
		double** cbuf = (double**) 0;
		
		if (!HARR_JavaArrayIn2DDouble(jenv, &cbuf, buf)) return -1;
		
		result = H5Dwrite(
			(hid_t) loc_id, 
			H5T_NATIVE_DOUBLE, 
			mem_space_id, 
			file_space_id,
			(hid_t) plist_id,
			(void*) (*cbuf)
			);	

		free(*cbuf);
		free(cbuf);
		return (result);
}

#ifdef __cplusplus
}
#endif
