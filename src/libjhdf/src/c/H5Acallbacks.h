#ifndef H5ACALLBACKS_H
#define H5ACALLBACKS_H

#include "H5callbacks.h"
#include "H5Apublic.h"

#include <stdlib.h>
#include <jni.h>

/** Callback data for JHDFA_visitAndOpen. */
typedef struct {
	JNIEnv* jenv;			/**<! Java environment. */
	hid_t aapl_id;			/**<! Link access property list id. */
	hid_t idChild;			/**<! Id of child attribute. */
	H5I_type_t typeChild;	/**<! Type of child object. */
	jstring jstrChild;		/**<! Name of child attribute. */
} JHDFA_iter_t;

herr_t JHDFA_visitAndOpen(hid_t loc_id, const char* name, void* client_data);

#endif