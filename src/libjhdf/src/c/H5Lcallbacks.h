#ifndef H5GCALLBACKS_H
#define H5GCALLBACKS_H

#include "H5callbacks.h"
#include "H5Lpublic.h"
#include "H5Gpublic.h"

#include <stdlib.h>
#include <jni.h>

typedef struct {
	JNIEnv* jenv;			/**<! Java environment. */
	jstring* pStrings;		/**<! malloc block of strings. */
	size_t szStrings;		/**<! size of pStrings. */
	size_t nStrings;		/**<! number of pString entries in use. */
} JHDFL_string_arr_t;

/** Callback data for JHDFL_visitAndOpen. */
typedef struct {
	JNIEnv* jenv;			/**<! Java environment. */
	hid_t lapl_id;			/**<! Link access property list id. */
	hid_t idChild;			/**<! Id of child object. */
	H5I_type_t typeChild;	/**<! Type of child object. */
	jstring jstrChild;		/**<! Name of child object. */
} JHDFL_iter_t;


herr_t JHDFL_visitAndAppendName(hid_t loc_id, const char* name, const H5L_info_t* info, void* op_data);
herr_t JHDFL_visitAndOpen(hid_t loc_id, const char* name, const H5L_info_t* info, void* op_data);
herr_t JHDFL_resizeStringArr(JHDFL_string_arr_t* hdl, size_t n);


#endif