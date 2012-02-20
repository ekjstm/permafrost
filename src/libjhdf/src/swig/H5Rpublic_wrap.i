%module ReferenceLib
%include "typemaps.i"
%import "H5types.i"
%include "H5Rtypes.i"

%header %{
#include <errno.h>
#include "H5Rpublic.h"
#define HARR_NOARRAYS 1
%}
%include "harrays.i"
%import "H5Gtypes.i"
%import "H5Otypes.i"

%{
  typedef struct {
    haddr_t addr;
    int offset;
  } JHR_ref_type_t;
%}
//%pointer_functions(JHR_ref_type_t, Reference);


/* Functions in H5R.c */
/**
 * Creates a reference.
 *
 * <p>H5Rcreate creates the reference, ref, of the type specified in
 * ref_type, pointing to the object name located at loc_id.</p>
 *
 * <p>The HDF5 library maps the void type specified above for ref to
 * the type specified in ref_type, which will be one of those
 * appearing in the first column of the following table. The second
 * column of the table lists the HDF5 constant associated with each
 * reference type.</p>
 <table border=0>
    <tr>
       <td><em>hdset_reg_ref_t</em>&nbsp;&nbsp;</td>
       <td><code>H5R_DATASET_REGION</code>&nbsp;&nbsp;</td>
       <td>Dataset region reference</td>
    </tr>
    <tr>
       <td><em>hobj_ref_t</em></td>
       <td><code>H5R_OBJECT</code></td>
       <td>Object reference</td>
   </tr>
 </table>
 *
 * <p>The parameters loc_id and name are used to locate the object.</p>
 *
 * <p>The parameter space_id identifies the region to be pointed to for
 * a dataset region reference. This parameter is unused with object
 * references. </p>
 *
 * @param ref (Output) Reference created by the function call.
 * @parma loc_id Location identifier used to locate the object being
 * pointed to.
 * @param name Name of object at location loc_id.
 * @param ref_type Type of reference.
 * @param space_id Dataspace identifier with selection. Used for
 * dataset region references.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */

herr_t H5Rcreate(JHR_ref_type_t* ref, hid_t loc_id, const char *name,
		 H5R_type_t ref_type, hid_t space_id);

/**
 * Opens the HDF5 object referenced.
 *
 * <p>Given a reference, ref, to an object or a region in an object,
 * H5Rdereference opens that object and returns an identifier.</p>
 *
 * <p>The parameter obj_id must be a valid identifier for an object in
 * the HDF5 file containing the referenced object, including the file
 * identifier.</p>
 *
 * <p>The parameter ref_type specifies the reference type of the
 * reference ref.</p>
 *
 * @param obj_id Valid identifier for the file containing the
 * referenced object or any object in that file.
 * @param ref_type The reference type of ref.
 * @param ref Reference to open.
 *
 * @return Returns identifier of referenced object if successful;
 * otherwise returns a negative value.
 */
hid_t H5Rdereference(hid_t obj_id, H5R_type_t ref_type, JHR_ref_type_t* ref);

/**
 * Retrieves a name of a referenced object.
 *
 * <p>H5Rget_name retrieves a name for the object identified by
 * ref.</p>
 *
 * <p>loc_id is the identifier for the dataset containing the
 * reference or for the group containing that dataset.</p>
 *
 * <p>H5R_type_t is the reference type of ref. Valid values include
 * the following:</p>
 <dl>
   <dt>H5R_OBJECT</dt>
   <dd>Object reference</dd>
   <dt>H5R_DATASET_REGION</dt>
   <dd>Dataset region reference</dd>
 </dl>
 *
 * <p>ref is the reference for which the target object's name is
 * sought.</p>
 *
 * <p>If ref is an object reference, name will be returned with the
 * name of the referenced object. If ref is a dataset region
 * reference, name will contain the name of the object containing the
 * referenced region.</p>
 *
 * <p>Up to size characters of the name are returned in name;
 * additional characters, if any, are not returned to the user
 * application.</p>
 *
 * <p>If the length of the name, which determines the required value
 * of size, is unknown, a preliminary H5Rget_name call can be
 * made. The return value of this call will be the size of the object
 * name. That value can then be assigned to size for a second
 * H5Rget_name call, which will retrieve the actual name.</p>
 *
 * <p>If there is no name associated with the object identifier or if
 * the name is NULL, H5Rget_name returns 0 (zero).</p>
 *
 * <p>Note that an object in an HDF5 file may have multiple paths if
 * there are multiple links pointing to it. This function may return
 * any one of these paths. </p>
 *
 * @param loc_id Identifier for the dataset containing the reference
 * or for the group that dataset is in.
 * @param ref_type Type of reference.
 * @param ref An object or dataset region reference.
 * @param name (Output) A name associated with the referenced object
 * or dataset region.
 * @param size The size of the name buffer.
 *
 * @return Returns the length of the name if successful, returning 0
 * (zero) if no name is associated with the identifier. Otherwise
 * returns a negative value.
 */
%apply signed char OUTPUT[] {char* name}
ssize_t H5Rget_name(hid_t loc_id, H5R_type_t ref_type, 
		    const JHR_ref_type_t *ref, char *name/*out*/, size_t size);
%clear char* name;

/**
 * Retrieves the type of object that an object reference points to.
 *
 * <p>Given an object reference, ref, H5Rget_obj_type1 returns the
 * type of the referenced object.</p>
 *
 * <p>An object reference is a reference that points to an object. The
 * referenced object is the object the reference points to.</p>
 *
 * <p>The location identifier, loc_id, is the identifier for either
 * the dataset containing the object reference or the group containing
 * that dataset.</p>
 *
 * <p>Valid reference types, to pass in as ref_type, include the
 * following:</p>
 <dl>
   <dt>H5R_OBJECT</dt>
   <dd>Object reference</dd>
   <dt>H5R_DATASET_REGION</dt>
   <dd>Dataset region reference</dd>
 </dl>
 *
 * <p>If the application does not already know the object reference
 * type, that can be determined with three preliminary calls:</p>
 <ol>
    <li>H5Dget_type returns a datatype identifier for the dataset's
    datatype.</li>
    <li>Using that datatype identifier, H5Tget_class returns a
    datatype class.</li>
    <li>If the datatype class is H5T_REFERENCE, H5Tequal can then be
    used to determine whether the reference's datatype is
    H5T_STD_REF_OBJ or H5T_STD_REF_DSETREG:
    <ol>
       <li>If the datatype is H5T_STD_REF_OBJ, the reference object
       type is H5R_OBJECT.</li>
       <li>If the datatype is H5T_STD_REF_DSETREG, the reference
       object type is H5R_DATASET_REGION. </li>
   </ol>
 </ol>
 * <p>When the function completes successfully, it returns one of the
 * following valid object type values (defined in H5Gpublic.h):</p>
 <dl>
    <dt>H5G_LINK</dt>
    <dd>Object is a symbolic link.</dd>
    <dt>H5G_GROUP</dt>
    <dd>Object is a group.</dd>
    <dt>H5G_DATASET</dt>
    <dd>Object is a dataset.</dd>
    <dt>H5G_TYPE</dt>
    <dd>Object is a named datatype.</dd>
 </dl>
 *
 * @param loc_id The dataset containing the reference object or the
 * group containing that the dataset.
 * @param ref_type Type of reference to query.
 * @param ref Reference to query.
 *
 * @return Returns a valid object type if successful; otherwise
 * returns H5G_UNKNOWN.
 *
 * @deprecated
 */
%javamethodmodifiers H5Rget_obj_type1 "@Deprecated\n   public"
H5G_obj_t H5Rget_obj_type1(hid_t id, H5R_type_t ref_type, JHR_ref_type_t *ref);

/**
 * Retrieves the type of object that an object reference points to.
 *
 * <p>Given an object reference, ref, H5Rget_obj_type1 returns the
 * type of the referenced object.</p>
 *
 * <p>An object reference is a reference that points to an object. The
 * referenced object is the object the reference points to.</p>
 *
 * <p>The location identifier, loc_id, is the identifier for either
 * the dataset containing the object reference or the group containing
 * that dataset.</p>
 *
 * <p>Valid reference types, to pass in as ref_type, include the
 * following:</p>
 <dl>
   <dt>H5R_OBJECT</dt>
   <dd>Object reference</dd>
   <dt>H5R_DATASET_REGION</dt>
   <dd>Dataset region reference</dd>
 </dl>
 * <p>If the application does not already know the object reference
 * type, that can be determined with three preliminary calls:</p>
 <ol>
    <li>H5Dget_type returns a datatype identifier for the dataset's
    datatype.</li>
    <li>Using that datatype identifier, H5Tget_class returns a
    datatype class.</li>
    <li>If the datatype class is H5T_REFERENCE, H5Tequal can then be
    used to determine whether the reference's datatype is
    H5T_STD_REF_OBJ or H5T_STD_REF_DSETREG:
    <ol>
       <li>If the datatype is H5T_STD_REF_OBJ, the reference object
       type is H5R_OBJECT.</li>
       <li>If the datatype is H5T_STD_REF_DSETREG, the reference
       object type is H5R_DATASET_REGION. </li>
   </ol>
 </ol>
 * <p>When the function completes successfully, it returns one of the
 * following valid object type values (defined in H5Gpublic.h):</p>
 <dl>
    <dt>H5G_LINK</dt>
    <dd>Object is a symbolic link.</dd>
    <dt>H5G_GROUP</dt>
    <dd>Object is a group.</dd>
    <dt>H5G_DATASET</dt>
    <dd>Object is a dataset.</dd>
    <dt>H5G_TYPE</dt>
    <dd>Object is a named datatype.</dd>
 </dl>
 *
 * @param loc_id The dataset containing the reference object or the
 * group containing that the dataset.
 * @param ref_type Type of reference to query.
 * @param ref Reference to query.
 * @param obj_type (Output) Type of referenced object.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply H5O_TYPE_ENUM* OUTPUT {H5O_type_t *obj_type}
herr_t H5Rget_obj_type2(hid_t id, H5R_type_t ref_type, 
			const JHR_ref_type_t *ref, 
			H5O_type_t *obj_type);
%clear H5O_type_t *obj_type;

/**
 * Retrieves a dataspace with the specified region selected.
 *
 * <p>Given a reference to an object ref, H5Rget_region creates a copy
 * of the dataspace of the dataset pointed to and defines a selection
 * in the copy which is the region pointed to.</p>
 *
 * <p>The parameter ref_type specifies the reference type of
 * ref. ref_type may contain the following value:</p>
 <ul>
    <li>H5R_DATASET_REGION (1) </li>
 </ul>
 *
 * @param dataset Dataset containing reference object.
 * @param ref_type The reference type of ref.
 * @param ref Reference to open.
 *
 * @return Returns a valid identifier if successful; otherwise returns
 * a negative value.
 */
hid_t H5Rget_region(hid_t dataset, H5R_type_t ref_type, JHR_ref_type_t *ref);


