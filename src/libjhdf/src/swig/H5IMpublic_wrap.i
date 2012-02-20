%module ImageLib
%include "typemaps.i"
%include "various.i"
%import "H5types.i"
%include "buffers.i"

%header %{
#include <errno.h>
#include "hdf5.h"
#include "H5IMpublic.h"
#define HARR_NOARRAYS 1
%}
%include "harrays.i"

#define INTERLACE_PIXEL "INTERLACE_PIXEL"
#define INTERLACE_PLANE "INTERLACE_PLANE"

/**
 * The HDF5 Image API defines a standard storage for HDF5 datasets that are 
 * indented to be interpreted as images. The specification for this API is 
 * presented in another document: HDF5 Image and Palette Specification. This 
 * version of the API is primarily concerned with two dimensional raster data 
 * similar to HDF4 Raster Images. The HDF5 image API uses the Lite HDF5 API.
 */

/**
 * Creates and writes an 8-bit palletized image.
 *
 * <p>H5IMmake_image_8bit creates and writes a dataset named dset_name 
 * attached to the file or group specified by the identifier loc_id. 
 * Attributes conforming to the HDF5 Image and Palette specification for an 
 * indexed image are attached to the dataset, thus identifying it as an image. 
 * The image data is of the type H5T_NATIVE_UCHAR. An indexed image is an 
 * image in which each each pixel information storage is an index to a table 
 * palette.</p>
 *
 * @param loc_id Identifier of the file or group to create the dataset within.
 * @param dset_name The name of the dataset to create.
 * @param width The width of the image.
 * @param height The height of the image.
 * @param buffer Buffer with data to be written to the dataset.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply long long {hsize_t width}
%apply long long {hsize_t height}
%apply unsigned char INPUT[] {const unsigned char* buffer}
herr_t H5IMmake_image_8bit(hid_t loc_id,
			   const char *dset_name,
			   hsize_t width,
			   hsize_t height,
			   const unsigned char *buffer);
%clear hsize_t width;
%clear hsize_t height;
%clear const unsigned char* buffer;

/**
 * Creates and writes an 8-bit palletized image.
 *
 * @see H5IMmake_image_8bit
 */
%rename(H5IMmake_image_8bit_direct) H5IMmake_image_8bit;
%apply long long {hsize_t width}
%apply long long {hsize_t height}
%apply unsigned char* INBUFF {const unsigned char* buffer}
herr_t H5IMmake_image_8bit( hid_t loc_id,
                            const char *dset_name,
                            hsize_t width,
                            hsize_t height,
                            const unsigned char *buffer);
%clear hsize_t width;
%clear hsize_t height;
%clear const unsigned char* buffer;
/**
 * Creates and writes a 24-bit true color image.
 *
 * <p>H5Lmake_image_24bit creates and writes a dataset named dset_name 
 * attached to the file or group specified by the identifier loc_id. This 
 * function defines a true color image conforming to the HDF5 Image and 
 * Palette specification. The function assumes that the image data is of 
 * the type H5T_NATIVE_UCHAR.</p>
 *
 * <p>A true color image is an image where the pixel storage contains several 
 * color planes. In a 24 bit RGB color model, these planes are red, green and 
 * blue. In a true color image the stream of bytes can be stored in several 
 * different ways, thus defining the interlace (or interleaving) mode. The two 
 * most used types of interlace mode are interlace by pixel and interlace by 
 * plane. In the 24 bit RGB color model example, interlace by plane means all 
 * the red components for the entire dataset are stored first, followed by all 
 * the green components, and then by all the blue components. Interlace by 
 * pixel in this example means that for each pixel the sequence red, green, 
 * blue is defined. In this function, the interlace mode is defined in the 
 * parameter interlace, a string that can have the values INTERLACE_PIXEL or 
 * INTERLACE_PLANE.</p>
 */
%apply long long {hsize_t width}
%apply long long {hsize_t height}
%apply unsigned char INPUT[] {const unsigned char* buffer}
herr_t H5IMmake_image_24bit( hid_t loc_id,
                             const char *dset_name,
                             hsize_t width,
                             hsize_t height,
                             const char *interlace,
                             const unsigned char *buffer);
%clear hsize_t width;
%clear hsize_t height;
%clear const unsigned char* buffer;

/**
 * Creates and writes a 24-bit true color image.
 * 
 * @see H5IMmake_image_24bit
 */
%rename(H5IMmake_image_24bit_direct) H5IMmake_image_24bit;
%apply long long {hsize_t width}
%apply long long {hsize_t height}
%apply unsigned char* INBUFF {const unsigned char* buffer}
herr_t H5IMmake_image_24bit( hid_t loc_id,
                             const char *dset_name,
                             hsize_t width,
                             hsize_t height,
                             const char *interlace,
                             const unsigned char *buffer);
%clear hsize_t width;
%clear hsize_t height;
%clear const unsigned char* buffer;

/**
 * Gets information about an image dataset.
 *
 * <p>H5IMget_image_info gets dimensions, interlace mode and number of associated palettes about an image named dset_name attached to the file or group specified by the identifier loc_id.</p>
 *
 * @param loc_id Identifier of the file or group in which the dataset is located.
 * @param dset_name The name of the dataset.
 * @param width (Output) The width of the image.
 * @param height (Output) The height of the image.
 * @param planes (Output) The number of color planes of the image.
 * @param interlace (Output) The interlace mode of the image.
 * @param npals (Output) The number of palettes associated to the image.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply long long* OUTPUT {hsize_t* width}
%apply long long* OUTPUT {hsize_t* height}
%apply long long* OUTPUT {hsize_t* planes}
%apply signed char OUTPUT[] {char* interlace}
%apply long long* OUTPUT {hssize_t* npals}
herr_t H5IMget_image_info(hid_t loc_id,
			  const char *dset_name,
			  hsize_t *width,
			  hsize_t *height,
			  hsize_t *planes,
			  char    *interlace,
			  hssize_t *npals );
%clear hsize_t* width;
%clear hsize_t* height;
%clear hsize_t* planes;
%clear char* interlace;
%clear hssize_t* npals;

/**
 * Reads image data from disk.
 *
 * <p>H5IMread_image reads a dataset named dset_name attached to the file or group specified by the identifier loc_id.</p>
 *
 * @param loc_id Identifier of the file or group to read the dataset from.
 * @param dset_name The name of the dataset.
 * @param buffer (Output) Buffer with data to store the image.
 *
 * @param Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply unsigned char OUTPUT[] {unsigned char* buffer}
herr_t H5IMread_image(hid_t loc_id,
                       const char *dset_name,
                       unsigned char *buffer);
%clear unsigned char* buffer;

/**
 * Reads image data from disk.
 *
 * @see H5IMread_image
 */
%rename(H5IMread_image_direct) H5IMread_image;
%apply unsigned char* OUTBUFF {unsigned char* buffer}
herr_t H5IMread_image(hid_t loc_id,
                       const char *dset_name,
                       unsigned char *buffer);
%clear unsigned char* buffer;

/**
 * Creates and writes a palette.
 *
 * <p>H5IMmake_palette creates and writes a dataset named pal_name. 
 * Attributes conforming to the HDF5 Image and Palette specification are 
 * attached to the dataset, thus identifying it as a palette. The palette 
 * data is of the type H5T_NATIVE_UCHAR.</p>
 *
 * @param loc_id Identifier of the file or group to create the dataset within.
 * @param pal_name The name of the palette.
 * @param pal_dims An array of the size of the palette dimensions.
 * @param pal_data Buffer with data to be written to the dataset.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply long long INPUT[] {hsize_t* pal_dims}
%apply unsigned char INPUT[] {const unsigned char* pal_data}
herr_t  H5IMmake_palette(hid_t loc_id,
                         const char *pal_name,
                         const hsize_t *pal_dims,
                         const unsigned char *pal_data);
%clear hsize_t* pal_dims;
%clear const unsigned char* pal_data;

/**
 * Attaches a palette to an image.
 *
 * <p>H5IMlink_palette attaches a palette named pal_name to an image specified 
 * by image_name. The image dataset may or not already have an attached 
 * palette. If it has, the array of palette references is extended to hold 
 * the reference to the new palette.</p>
 *
 * @param loc_id Identifier of the file or group.
 * @param image_name The name of the dataset to attach the palette to.
 * @param pal_name The name of the palette.
 * 
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t H5IMlink_palette(hid_t loc_id,
                        const char *image_name,
                        const char *pal_name);

/**
 * Dettaches a palette from an image.
 *
 * <p>H5IMunlink_palette dettaches a palette from an image specified by 
 * image_name.</p>
 *
 * @param loc_id Identifier of the file or group.
 * @param image_name The name of the image dataset.
 * @param pal_name The name of the palette.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
herr_t  H5IMunlink_palette(hid_t loc_id,
                           const char *image_name,
                           const char *pal_name);

/**
 * Gets the number of palettes associated to an image.
 *
 * <p>H5IMget_npalettes gets the number of palettes associated to an image 
 * specified by image_name.</p>
 *
 * @param loc_id Identifier of the file or group in which the image dataset is located.
 * @param image_name The name of the image dataset.
 * @param npals (Output) The number of palettes.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply long long* OUTPUT {hssize_t* npals}
herr_t  H5IMget_npalettes(hid_t loc_id,
                          const char *image_name,
                          hssize_t *npals);
%clear hssize_t* npals;

/**
 * Gets the dimensions of a palette dataset.
 *
 * <p>H5IMget_palette_info gets the dimensions of the palette dataset 
 * identified by pal_number (a zero based index) associated to an image 
 * specified by image_name.</p>
 *
 * @param loc_id Identifier of the file or group in which the image dataset is located. 
 * @param image_name The name of the image dataset.
 * @param pal_number The zero based index that identifies the palette.
 * @param pal_dims (Output) The dimensions of the palette dataset.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply long long OUTPUT[] {hsize_t* pal_dims}
herr_t  H5IMget_palette_info(hid_t loc_id,
			     const char *image_name,
			     int pal_number,
			     hsize_t *pal_dims);
%clear hsize_t* pal_dims;

/**
 * Gets the palette dataset.
 *
 * <p>H5IMget_palette gets the palette dataset identified by pal_number 
 * (a zero based index) associated to an image specified by image_name.</p>
 *
 * @param loc_id Identifier of the file or group in which the image dataset is located.
 * @param image_name The name of the image dataset.
 * @param pal_number The zero based index that identifies the palette.
 * @param pal_data (Output) The palette dataset.
 *
 * @return Returns a non-negative value if successful; otherwise returns a negative value.
 */
%apply unsigned char OUTPUT[] {unsigned char* pal_data}
herr_t  H5IMget_palette( hid_t loc_id,
                        const char *image_name,
                        int pal_number,
                        unsigned char *pal_data );
%clear unsigned char* pal_data;

/**
 * Inquires if a dataset is an image.
 *
 * <p>H5IMis_image inquires if a dataset  named dset_name, attached to the 
 * file or group specified by the identifier loc_id, is an image based on 
 * the HDF5 Image and Palette Specification.</p>
 *
 * @param loc_id Identifier of the file or group in which the dataset is located.
 * @param dset_name The name of the dataset.
 *
 * @return Returns true (1) or false (0) if successful; otherwise returns a negative value.
 */
herr_t H5IMis_image(hid_t loc_id,
		    const char *dset_name);

/**
 * Inquires if a dataset is a palette.
 *
 * <p>H5IMis_palette inquires if a dataset named dset_name, attached to the 
 * file or group specified by the identifier loc_id, is a palette based on 
 * the HDF5 Image and Palette Specification.</p>
 *
 * @param loc_id  Identifier of the file or group in which the dataset is located.
 * @param dset_name The name of the dataset.
 * 
 * @return Returns true (1) or false (0) if successful; otherwise returns a negative value.
 */ 
herr_t H5IMis_palette(hid_t loc_id,
                     const char *dset_name);
