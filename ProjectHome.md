# WHAT IS PERMAFROST #
Permafrost is a suite of tools motivated by the need of Java application developers for a transparent means of storing hierarchical/binary data. Permafrost plans to provide three key components for developing an application-specific data store:

  * libjhdf; a JNI binding for libhdf5.
> > libjhdf has a simple Java-oriented API for working with the [HDF5](http://www.hdfgroup.org) native object structure library. It is NOT the HDF Group/NCSA [Java HDF Interface](http://www.hdfgroup.org/hdf-java-html/index.html) (JHI). libjhdf is a ground-up project to develop a new JNI/Java library for HDF5 support.


> libjhdf is unique from other HDF wrappers in a number of ways:
    * It is SWIG based
> > We are using SWIG to generate the basic wrapper code. This leaves us free to work on other parts of the project and allows the possibility of producing wrappers for other languages later.
    * It is a complete wrapper
> > No other HDF wrapper we are familiar with exposes the complete HDF API. We use comparatively simple wrappers to expose the HDF C API rather than the object-oriented HDF C++ API. An object-oriented layer is implemented in pure Java on top of these wrappers. This has the advantage of giving the library a more Java-like feel.
    * Error handling is done through Java exceptions
> > We provide custom JNI for commonly used library callbacks, including the error handler.   No need to check return values.

  * permafrost core; a Java persistence framework.
> > The permafrost core is planned as an annotation-driven persistence framework designed to run on top of libjhdf. Classes are annotated to indicate which members are persistent and how data should be mapped to the HDF structure. No mapping metadata is stored in the HDF structure, so Java entity classes can be designed to map existing HDF data. Annotations are similar to EJB 3.0/JSR-220, but not identical.

  * hoarfrost; a flexible HDF5 editor
> > hoarfrost is planned to application developers to create and edit HDF5 data files. This mitigates the need to write custom importers for testing or one-off configuration data. It also is useful for viewing data when debugging HDF applications.

---


# STATUS #
You may wish to check the [News](News.md) page for the most current status of the project.

### LIBJHDF ###
We are currently implementing the SWIG Java wrapper for this component. HDF5 API documentation and unit tests will be ported at the same time.

### CAVEAT EMPTOR ###
This project is somewhat understaffed. As such, it will undergo periods where development work is slow/non-existent. However, we will do our best to _document_ times when the project is idled and inform our users. In addition, we will always respond to support requests (at least with advice). This project will not become abandonware.

---


# LICENSE #
Permafrost is licensed under the New BSD license.

In addition, we kindly ask you to acknowledge Permafrost and its authors in any program or publication in which you use Permafrost. (You are not required to do so; it is up to your common sense to decide whether you want to comply with this request or not.) For general publications, we suggest referencing:

Solomon Gibbs, "The Permafrost Suite of Tools", http://code.google.com/p/permafrost, Downloaded `[DATE]`.