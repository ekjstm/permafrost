%include "cpointer.i"

/*
 * Reference types allowed.
 */
%rename H5R_type_t ReferenceType;
typedef enum {
    H5R_BADTYPE     =   (-1),   /*invalid Reference Type                     */
    H5R_OBJECT,                 /*Object reference                           */
    H5R_DATASET_REGION,         /*Dataset Region Reference                   */
    H5R_MAXTYPE                 /*highest type (Invalid as true type)	     */
} H5R_type_t;
HENUM_OUTPUT_TYPEMAP(H5R_TYPE_ENUM, ReferenceType);

#define JHR_INVALID_SPACE  -1

%rename JHR_ref_type_t Reference;
typedef struct {
  %ignore addr;
  haddr_t addr;
  %ignore offset;
  int offset;
} JHR_ref_type_t;
