%module ErrorLib
%include "typemaps.i"
%import "H5types.i"
%include "H5Etypes.i"

%header %{
#include "H5Epublic.h"
#include "../../src/c/H5Ecallbacks.h"
%}


//typedef herr_t (*H5E_walk_t)(int n, H5E_error_t *err_desc, void *client_data);
//typedef herr_t (*H5E_auto_t)(void *client_data);

/* Must define function pointers in C rather than as 
 * java constants. 
 */
%javaconst(0);
%constant herr_t JHDF_walkAndLogError(void* jerr);
%constant herr_t JHDF_walkAndThrowError(void* client_data);
%javaconst(1);

/**
 * Determines type of error stack.
 *
 * <p>H5Eauto_is_v2 determines whether the error auto reporting
 * function for an error stack conforms to the H5E_auto2_t typedef or
 * the H5E_auto1_t typedef.</p>
 *
 * <p>The is_stack parameter is set to 1 if the error stack conforms
 * to H5E_auto2_t and 0 if it conforms to H5E_auto1_t. </p>
 *
 * @param estack_id The error stack identifier
 * @param is_stack (Output) A flag indicating which error stack typedef the
 * specified error stack conforms to.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
%apply unsigned* OUTPUT {unsigned *is_stack}
herr_t H5Eauto_is_v2(hid_t estack_id, unsigned *is_stack);
%clear unsigned *is_stack;

/**
 * Clears the error stack for the current thread.
 *
 * <p>H5Eclear1 clears the error stack for the current thread.</p>
 *
 * <p>The stack is also cleared whenever an API function is called,
 * with certain exceptions (for instance, H5Eprint1). </p>
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @deprecated
 */
%javamethodmodifiers H5Eclear1 "@Deprecated\n   public"
herr_t H5Eclear1(void);

/**
 * Clears the specified error stack or the error stack for the current thread.
 *
 * <p>H5Eclear2 clears the error stack specified by estack_id, or, if
 * estack_id is set to H5E_DEFAULT, the error stack for the current
 * thread.</p>
 *
 * <p>estack_id is an error stack identifier, such as that returned by
 * H5Eget_current_stack.</p>
 *
 * <p>The current error stack is also cleared whenever an API function
 * is called, with certain exceptions (for instance, H5Eprint1 or
 * H5Eprint2). </p>
 *
 * @param estack_id Error stack identifier.
 * 
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Eclear2(hid_t estack_id);

/**
 * Closes an error message identifier.
 *
 * <p>H5Eclose_msg closes an error message identifier., which can be
 * either a major or minor message.</p>
 *
 * @param err_id Error message identifier.
 *
 * @return Returns a non-negative value on success; otherwise returns
 * a negative value.
 */
herr_t H5Eclose_msg(hid_t err_id);

/**
 * Closes object handle for error stack.
 *
 * <p>H5Eclose_stack closes the object handle for an error stack and
 * releases its resources. H5E_DEFAULT cannot be closed.</p>
 *
 * @param estack_id Error stack identifier.
 *
 * @return Returns a non-negative value on success; otherwise returns
 * a negative value.
 */
herr_t H5Eclose_stack(hid_t estack_id);

/**
 * Add major error message to an error class.
 *
 * <p>H5Ecreate_msg adds an error message to an error class defined by
 * client library or application program. The error message can be
 * either major or minor which is indicated by parameter msg_type.</p>
 *
 * @param cls Error class identifier.
 * @param msg_type The type of the error message. Valid values are
 * H5E_MAJOR and H5E_MINOR.
 * @param msg Major error message.
 *
 * @return Returns a message identifier on success; otherwise returns
 * a negative value.
 */
hid_t H5Ecreate_msg(hid_t cls, H5E_type_t msg_type, const char *msg);

/**
 * Creates a new empty error stack.
 *
 * <p>H5Ecreate_stack creates a new empty error stack and returns the
 * new stack's identifier.</p>
 *
 * @return Returns an error stack identifier on success; otherwise
 * returns a negative value.
 */
hid_t  H5Ecreate_stack(void);

/*nodoc*
 * Returns the current settings for the automatic error stack
 * traversal function and its data.
 *
 * <p>H5Eget_auto1 returns the current settings for the automatic
 * error stack traversal function, func, and its data,
 * client_data. Either or both arguments may be null, in which case
 * the value is not returned.</p>
 *
 * @param func (Output) Current setting for the function to be called
 * upon an error condition.
 * @param client_data (Output) Current setting for the data passed to
 * the error function.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @deprecated
 */
//%javamethodmodifiers H5Eget_auto1 "@Deprecated\n   public"
//herr_t H5Eget_auto1(H5E_auto1_t* func, void** client_data);

/*nodoc*
 * Returns the settings for the automatic error stack traversal
 * function and its data.
 *
 * <p>H5Eget_auto2 returns the settings for the automatic error stack
 * traversal function, func, and its data, client_data, that are
 * associated with the error stack specified by estack_id.</p>
 *
 * <p>Either or both of the func and client_data arguments may be
 * null, in which case the value is not returned. </p>
 *
 * @param estack_id Error stack identifier. H5E_DEFAULT indicates the
 * current stack.
 * @param func (Output) The function currently set to be called upon
 * an error condition.
 * @param client_data (Output) Data currently set to be passed to the
 * error function.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
//herr_t H5Eget_auto2(hid_t estack_id, H5E_auto2_t *func, void **client_data);

/**
 * Retrieves error class name.
 *
 * <p>H5Eget_class_name retrieves the name of the error class
 * specified by the class identifier. If non-NULL pointer is passed in
 * for name and size is greater than zero, the class name of size long
 * is returned. The length of the error class name is also
 * returned. If NULL is passed in as name, only the length of class
 * name is returned. If zero is returned, it means no name. User is
 * responsible for allocated enough buffer for the name.</p>
 *
 * @param class_id Error class identifier.
 * @param name (Output) The name of the class to be queried.
 * @param size The length of class name to be returned by this function.
 *
 * @return Returns non-negative value as on success; otherwise returns
 * negative value.
 */
ssize_t H5Eget_class_name(hid_t class_id, char *name, size_t size);

/**
 * Returns copy of current error stack.
 *
 * <p>H5Eget_current_stack copies the current error stack and returns
 * an error stack identifier for the new copy.</p>
 *
 * @return Returns an error stack identifier on success; otherwise
 * returns a negative value.
 */
hid_t  H5Eget_current_stack(void);

/**
 * Returns a character string describing an error specified by a major
 * error number.
 *
 * <p>Given a major error number, H5Eget_major returns a constant
 * character string that describes the error.</p>
 *
 * @param major_number Major error number.
 * 
 * @return Returns a character string describing the error if
 * successful. Otherwise returns "Invalid major error number."
 *
 * @deprecated
 */
%javamethodmodifiers H5Eget_major "@Deprecated\n   public"
const char* H5Eget_major (H5E_major_t major_number);

/**
 * Returns a character string describing an error specified by a minor
 * error number.
 *
 * <p>Given a minor error number, H5Eget_minor returns a constant
 * character string that describes the error.</p>
 *
 * @param minor_number Minor error number.
 *
 * @return Returns a character string describing the error if
 * successful. Otherwise returns "Invalid minor error number."
 *
 * @deprecated
 */
%javamethodmodifiers H5Eget_minor "@Deprecated\n   public"
const char* H5Eget_minor (H5E_minor_t minor_number);

/**
 * Retrieves an error message.
 *
 * <p>H5Eget_msg retrieves the error message including its length and
 * type. The error message is specified by mesg_id. User is
 * responsible for passing in enough buffer for the message. If mesg
 * is not NULL and size is greater than zero, the error message of
 * size long is returned. The length of the message is also
 * returned. If NULL is passed in as mesg, only the length and type of
 * the message is returned. If the return value is zero, it means no
 * message.</p>
 *
 * @param msg_id Idenfier for error message to be queried.
 * @param type (Output) The type of the error message. Valid
 * values are H5E_MAJOR and H5E_MINOR.
 * @param msg (Output) Error message buffer.
 * @param size The length of error message to be returned by this function.
 *
 * @return Returns the size of the error message in bytes on success;
 * otherwise returns a negative value.
 */
ssize_t H5Eget_msg(hid_t msg_id, H5E_type_t *type, char *msg, size_t size);

/**
 * Retrieves the number of error messages in an error stack.
 *
 * <p>H5Eget_num retrieves the number of error records in the error
 * stack specified by estack_id (including major, minor messages and
 * description).</p>
 *
 * @param estack_id Error stack identifier.
 *
 * @return Returns a non-negative value on success; otherwise returns
 * a negative value.
 */
ssize_t H5Eget_num(hid_t estack_id);

/**
 * Deletes specified number of error messages from the error stack.
 *
 * <p>H5Epop deletes the number of error records specified in count
 * from the top of the error stack specified by estack_id (including
 * major, minor messages and description). The number of error
 * messages to be deleted is specified by count.</p>
 *
 * @param estack_id Error stack identifier.
 * @param count The number of error messages to be deleted from the
 * top of error stack.
 *
 * @return Returns a non-negative value on success; otherwise returns
 * a negative value.
 */
herr_t H5Epop(hid_t estack_id, size_t count);

/**
 * Prints the current error stack in a default manner.
 *
 * <p>H5Eprint1 prints the error stack for the current thread on the
 * specified stream, stream. Even if the error stack is empty, a
 * one-line message will be printed:</p>
 <code>HDF5-DIAG: Error detected in thread 0.</code>
 *
 * <p>H5Eprint1 is a convenience function for H5Ewalk1 with a function
 * that prints error messages. Users are encouraged to write their own
 * more specific error handlers. </p>
 *
 * @param stream File pointer, or stderr if NULL.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @deprecated
 */
%javamethodmodifiers H5Eprint1 "@Deprecated\n   public"
herr_t H5Eprint1(FILE* stream);

/**
 * Prints the specified error stack in a default manner.
 *
 * <p>H5Eprint2 prints the error stack specified by estack_id on the
 * specified stream, stream. Even if the error stack is empty, a
 * one-line message of the following form will be printed:</p>
 <code>HDF5-DIAG: Error detected in HDF5 library version: 1.5.62
 thread 0.</code>
 *
 * <p>A similar line will appear before the error messages of each
 * error class stating the library name, library version number, and
 * thread identifier.</p>
 *
 * <p>If estack_id is H5E_DEFAULT, the current error stack will be
 * printed.</p>
 *
 * <p>H5Eprint2 is a convenience function for H5Ewalk2 with a function
 * that prints error messages. Users are encouraged to write their own
 * more specific error handlers. </p>
 *
 * @param estack_id Identifier of the error stack to be printed. If
 * the identifier is H5E_DEFAULT, the current error stack will be
 * printed.
 * @param stream File pointer, or stderr if NULL.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Eprint2(hid_t estack_id, FILE *stream);

/**
 * Pushes new error record onto error stack.
 *
 * <p>H5Epush1 pushes a new error record onto the error stack for the
 * current thread.</p>
 *
 * <p>The error has major and minor numbers maj_num and min_num, the
 * function func where the error was detected, the name of the file
 * file where the error was detected, the line line within that file,
 * and an error description string str.</p>
 *
 * <p>The function name, filename, and error description strings must
 * be statically allocated. </p>
 *
 * @param file Name of the file in which the error was detected.
 * @param func Name of the function in which the error was detected.
 * @param line Line within the file at which the error was detected.
 * @param maj Major error number.
 * @param min Minor error number.
 * @param str Error description string.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @deprecated
 */
%javamethodmodifiers H5Epush1 "@Deprecated\n   public"
herr_t H5Epush1(const char *file, const char *func, unsigned line, 
               H5E_major_t maj, H5E_minor_t min, const char *str);

/**
 * Pushes new error record onto error stack.
 *
 * <p>H5Epush2 pushes a new error record onto the error stack
 * specified by estack_id.</p>
 *
 * <p>The error record contains the error class identifier class_id,
 * the major and minor message identifiers major_id and minor_id, the
 * function name func where the error was detected, the filename file
 * and line number line within that file where the error was detected,
 * and an error description msg.</p>
 *
 * <p>The major and minor errors must be in the same error class.</p>
 *
 * <p>The function name, filename, and error description strings must
 * be statically allocated.</p>
 *
 * <p>msg can be a format control string with additional
 * arguments. This design of appending additional arguments is similar
 * to the system and C functions printf and fprintf. </p>
 *
 * @param estack_id Identifier of the error stack to which the error
 * record is to be pushed. If the identifier is H5E_DEFAULT, the error
 * record will be pushed to the current stack.
 * @param file Name of the file in which the error was detected.
 * @param func Name of the function in which the error was detected.
 * @param line Line number within the file at which the error was detected.
 * @param cls_id Error class identifier.
 * @param maj_id Major error identifier.
 * @param min_id Minor error identifier.
 * @param msg Error description string.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
herr_t H5Epush2(hid_t estack_id, const char *file, const char *func, 
		unsigned line, hid_t cls_id, hid_t maj_id, hid_t min_id, 
		const char *msg, ...);

/**
 * Registers a client library or application program to the HDF5 error API.
 *
 * <p>H5Eregister_class registers a client library or application
 * program to the HDF5 error API so that the client library or
 * application program can report errors together with HDF5
 * library. It receives an identifier for this error class for further
 * error operations. The library name and version number will be
 * printed out in the error message as preamble.</p>
 *
 * @param cls_name Name of the error class.
 * @param lib_name Name of the client library or application to which
 * the error class belongs.
 * @param version Version of the client library or application to
 * which the error class belongs. A NULL can be passed in.
 *
 * @return Returns a class identifier on success; otherwise returns a
 * negative value.
 */
hid_t H5Eregister_class(const char *cls_name, const char *lib_name,
			const char *version);

/**
 * Turns automatic error printing on or off.
 *
 * <p>H5Eset_auto1 turns on or off automatic printing of errors. When
 * turned on (non-null func pointer), any API function which returns
 * an error indication will first call func, passing it client_data as
 * an argument.</p>
 *
 * <p>When the library is first initialized the auto printing function
 * is set to H5Eprint1 (cast appropriately) and client_data is the
 * standard error stream pointer, stderr.</p>
 *
 * <p>Automatic stack traversal is always in the H5E_WALK_DOWNWARD
 * direction. </p>
 *
 * @param func Function to be called upon an error condition.
 * @param client_data Data passed to the error function.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @deprecated
 */
//%javamethodmodifiers H5Eset_auto1 "@Deprecated\n   public"
//herr_t H5Eset_auto1(H5E_auto1_t func, void *client_data);

/*nodoc*
 * Turns automatic error printing on or off.
 *
 * <p>H5Eset_auto2 turns on or off automatic printing of errors for
 * the error stack specified with estack_id. An estack_id value of
 * H5E_DEFAULT indicates the current stack.</p>
 *
 * <p>When automatic printing is turned on, by the use of a non-null
 * func pointer, any API function which returns an error indication
 * will first call func, passing it client_data as an argument.</p>
 *
 * <p>When the library is first initialized, the auto printing
 * function is set to H5Eprint2 (cast appropriately) and client_data
 * is the standard error stream pointer, stderr.</p>
 *
 * <p>Automatic stack traversal is always in the H5E_WALK_DOWNWARD
 * direction.</p>
 *
 * <p>Automatic error printing is turned off with a H5Eset_auto2 call
 * with a NULL func pointer. </p>
 *
 * @param estack_id Error stack identifier. 
 * @param func Function to be called upon an error condition.
 * @param client_data Data passed to the error function.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
//herr_t H5Eset_auto2(hid_t estack_id, H5E_auto2_t func, void *client_data);

/**
 * Replaces the current error stack.
 *
 * <p>H5Eset_current_stack replaces the content of the current error
 * stack with a copy of the content of the error stack specified by
 * estack_id.</p>
 *
 * @param estack_id Error stack identifier.
 *
 * @return Returns a non-negative value on success; otherwise returns
 * a negative value.
 */
herr_t H5Eset_current_stack(hid_t estack_id);

/**
 * Removes an error class.
 *
 * <p>H5Eunregister_class removes the error class specified by
 * class_id. All the major and minor errors in this class will also be
 * closed.</p>
 *
 * @param class_id Error class identifier.
 *
 * @return Returns a non-negative value on success; otherwise returns
 * a negative value.
 */
herr_t H5Eunregister_class(hid_t class_id);

/*nodoc*
 * Walks the error stack for the current thread, calling a specified function.
 *
 * <p>H5Ewalk1 walks the error stack for the current thread and calls
 * the specified function for each error along the way.</p>
 *
 * <p>direction determines whether the stack is walked from the inside
 * out or the outside in. A value of H5E_WALK_UPWARD means begin with
 * the most specific error and end at the API; a value of
 * H5E_WALK_DOWNWARD means to start at the API and end at the
 * inner-most function where the error was first detected.</p>
 *
 * <p>func will be called for each error in the error stack. Its
 * arguments will include an index number (beginning at zero
 * regardless of stack traversal direction), an error stack entry, and
 * the client_data pointer passed to H5E_print. </p>
 *
 * @param direction Direction in which the error stack is to be walked.
 * @param func Function to be called for each error encountered.
 * @param client_data Data to be passed with func.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 *
 * @deprecated
 */
//%javamethodmodifiers H5Ewalk1 "@Deprecated\n   public"
//herr_t H5Ewalk1(H5E_direction_t direction, H5E_walk1_t func, 
//		void* client_data);

/*nodoc*
 * Walks the specified error stack, calling the specified function.
 *
 * <p>H5Ewalk2 walks the error stack specified by estack_id for the
 * current thread and calls the function specified in func for each
 * error along the way.</p>
 *
 * <p>If the value of estack_id is H5E_DEFAULT, then H5Ewalk2 walks
 * the current error stack.</p>
 *
 * <p>direction specifies whether the stack is walked from the inside
 * out or the outside in. A value of H5E_WALK_UPWARD means to begin
 * with the most specific error and end at the API; a value of
 * H5E_WALK_DOWNWARD means to start at the API and end at the
 * innermost function where the error was first detected.</p>
 *
 * <p>func, a function compliant with the H5E_walk2_t prototype, will
 * be called for each error in the error stack. Its arguments will
 * include an index number n (beginning at zero regardless of stack
 * traversal direction), an error stack entry err_desc, and the
 * client_data pointer passed to H5E_print.</p>
 *
 * @param estack_id Error stack identifier.
 * @param direction Direction in which the error stack is to be walked.
 * @param func Function to be called for each error encountered.
 * @param client_data Data to be passed with func.
 *
 * @return Returns a non-negative value if successful; otherwise
 * returns a negative value.
 */
//herr_t H5Ewalk2(hid_t estack_id, H5E_direction_t direction, 
//		H5E_walk2_t func, void *client_data);
