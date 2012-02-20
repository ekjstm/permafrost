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

SWIGEXPORT hid_t JNICALL Java_permafrost_hdf_libhdf_ObjectLibExtensionJNI_JHOopen_1by_1idx(
	JNIEnv *jenv, 
	jclass jcls, 
	jint loc_id, 
	jstring group_name,
	jint idx_type, 
	jint order, 
	jlong n, 
	jint lapl_id, 
	jintArray type, 
	jobjectArray name
	) {
		jint sz = 0;
		char* pGroupName = NULL;
		hid_t obj_id = -1;
		jint jiType;
		char* pName = NULL;
		char* tmpName = NULL;
		const int szName = 64;
		int lenName = 0;
		jstring jname;

		if (!group_name) {
			SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, "Null group name.");
			return (-1);
		} else if (!type) {
			SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, "Null type array.");
			return (-1);
		} else if (!name) {
			SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, "Null object name array.");
			return (-1);
		}
		sz = (*jenv)->GetArrayLength(jenv, type);
		if (sz < 1) {
			SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, "Zero-length type array.");
			return (-1);
		}
		sz = (*jenv)->GetArrayLength(jenv, name);
		if (sz < 1) {
			SWIG_JavaThrowException(jenv, SWIG_JavaIllegalArgumentException, "Zero-length object name array.");
			return (-1);
		}

		pGroupName = (char *)(*jenv)->GetStringUTFChars(jenv, group_name, 0);
		if (!pGroupName) return -1;
		
		obj_id = H5Oopen_by_idx(
			(hid_t)loc_id, 
			pGroupName, 
			(H5_index_t) idx_type, 
			(H5_iter_order_t) order,
			(hsize_t) n, 
			(hid_t) lapl_id
			);

		if (pGroupName) (*jenv)->ReleaseStringUTFChars(jenv, group_name, (const char *) pGroupName);
		if (obj_id <= 0) return (obj_id);

		jiType = (jint) H5Iget_type(obj_id);
		(*jenv)->SetIntArrayRegion(jenv, type, 0, 1, &jiType);

		pName = (char*) calloc(szName, sizeof(char));
		lenName = H5Iget_name(obj_id, pName, szName-1);
		if (lenName >= szName) {
			char* tmpName = (char*) realloc(pName, sizeof(char)*lenName+1);
			if (!tmpName) {
				free(pName);
				pName = NULL;
				H5Oclose(loc_id);
				return (-1);
			}
			pName = tmpName;
			lenName = H5Iget_name(obj_id, pName, lenName);
		}
		jname = (*jenv)->NewStringUTF(jenv, pName);
		(*jenv)->SetObjectArrayElement(jenv, name, 0, jname);
		
		free(pName);
		
		return (obj_id);
}

#ifdef __cplusplus
}
#endif