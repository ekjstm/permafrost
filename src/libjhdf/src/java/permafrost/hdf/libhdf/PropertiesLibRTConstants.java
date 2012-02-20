/**
 * 
 */
package permafrost.hdf.libhdf;

/**
 * Provides access to runtime constants in the PropertiesLib package.
 */
public class PropertiesLibRTConstants {

	//public static final int H5P_ROOT = PropertiesLib.getH5P_ROOT_CLASS();
	/** 
	 * Properties for object creation during the object copying
	 * process. See H5Pset_copy_object.
	 */
	//public static final int H5P_OBJECT_CREATE = PropertiesLib.getH5P_OBJECT_CREATE_CLASS();
	
	/**
	 * Properties for file creation. See Files in the HDF User's
	 * Guide for details about the file creation properties.
	 */
	public static final int H5P_FILE_CREATE = PropertiesLib.getH5P_FILE_CREATE_LIST();
	
	/**
	 * Properties for file access. See Files in the HDF User's Guide
    * for details about the file creation properties. 
	 */
	public static final int H5P_FILE_ACCESS = PropertiesLib.getH5P_FILE_ACCESS_LIST();
	
	/**
	 * Properties for dataset creation. See Datasets in the HDF
	 * User's Guide for details about dataset creation properties.
	 */
	public static final int H5P_DATASET_CREATE = PropertiesLib.getH5P_DATASET_CREATE_LIST();
	
	/**
	 * Properties for dataset access.
	 */
	public static final int H5P_DATASET_ACCESS = PropertiesLib.getH5P_DATASET_ACCESS_LIST();
	
	/**
	 * Properties for raw data transfer. See Datasets in the HDF
	 * User's Guide for details about raw data transfer properties.
	 */
	public static final int H5P_DATASET_XFER = PropertiesLib.getH5P_DATASET_XFER_LIST();
	
	/**
	 * Properties for file mounting. With this parameter, H5Pcreate
	 * creates and returns a new mount property list initialized with
	 * default values.
	 */
	public static final int H5P_FILE_MOUNT = PropertiesLib.getH5P_FILE_MOUNT_LIST();
	
	/**
	 * Properties for group creation during the object copying
	 * process. See H5Pset_copy_object.
	 */
	public static final int H5P_GROUP_CREATE = PropertiesLib.getH5P_GROUP_CREATE_LIST();
	
	/**
	 * Properties for group access during the object copying
	 * process. See H5Pset_copy_object.
	 */
	public static final int H5P_GROUP_ACCESS = PropertiesLib.getH5P_GROUP_ACCESS_LIST();
	
	/**
	 * Properties for datatype creation during the object copying
	 * process. See H5Pset_copy_object.
	 */
	public static final int H5P_DATATYPE_CREATE = PropertiesLib.getH5P_DATATYPE_CREATE_LIST();
	
	/**
	 * Properties for datatype access during the object copying
	 * process. See H5Pset_copy_object.
	 */
	public static final int H5P_DATATYPE_ACCESS = PropertiesLib.getH5P_DATATYPE_ACCESS_LIST();
	
	/**
	 * Properties for string creation during the object copying
	 * process. See H5Pset_copy_object.
	 */
	//public static final int H5P_STRING_CREATE = PropertiesLib.getH5P_STRING_CREATE_CLASS();
	
	/**
	 * Properties for attribute creation during the object copying
	 * process. See H5Pset_copy_object.
	 */
	public static final int H5P_ATTRIBUTE_CREATE = PropertiesLib.getH5P_ATTRIBUTE_CREATE_LIST();
	
	/**
	 * Properties governing the object copying process. See
	 * H5Pset_copy_object.
	 */
	public static final int H5P_OBJECT_COPY = PropertiesLib.getH5P_OBJECT_COPY_LIST();
	
	/**
	 * Properties for link creation during the object copying
	 * process. See H5Pset_copy_object.
	 */
	public static final int H5P_LINK_CREATE = PropertiesLib.getH5P_LINK_CREATE_LIST();
	
	/**
	 * Properties for link access during the object copying
	 * process. See H5Pset_copy_object.
	 */
	public static final int H5P_LINK_ACCESS = PropertiesLib.getH5P_LINK_ACCESS_LIST();
	
	
	/**
	 * 
	 */
	private PropertiesLibRTConstants() {
		super();
	}

}
