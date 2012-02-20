PROJECT(LIBJHDF)
CMAKE_MINIMUM_REQUIRED(VERSION 2.6)
INCLUDE(${CMAKE_ROOT}/Modules/CheckIncludeFile.cmake)

SET(LIBJHDF_SRCS
  src/c/H5Acallbacks.c
  src/c/H5Acallbacks.h
  src/c/H5callbacks.h
  src/c/H5Ecallbacks.c
  src/c/H5Ecallbacks.h
  src/c/H5Lcallbacks.c
  src/c/H5Lcallbacks.h
  src/c/JHApublic.c
  src/c/JHDpublic.c
  src/c/JHOpublic.c
  target/swig-c/H5Apublic_wrap.c
  target/swig-c/H5Dpublic_wrap.c
  target/swig-c/H5Epublic_wrap.c
  target/swig-c/H5Fpublic_wrap.c
  target/swig-c/H5Gpublic_wrap.c
  target/swig-c/H5IMpublic_wrap.c
  target/swig-c/H5Ipublic_wrap.c
  target/swig-c/H5Lpublic_wrap.c
  target/swig-c/H5LTpublic_wrap.c
  target/swig-c/H5Opublic_wrap.c
  target/swig-c/H5Ppublic_wrap.c
  target/swig-c/H5PTpublic_wrap.c
  target/swig-c/H5public_wrap.c
  target/swig-c/H5Rpublic_wrap.c
  target/swig-c/H5Spublic_wrap.c
  target/swig-c/H5TBpublic_wrap.c
  target/swig-c/H5Tpublic_wrap.c
  target/swig-c/H5Zpublic_wrap.c
  )


# libjhdf must be build shared so JNI can load it
ADD_LIBRARY(libjhdf SHARED ${LIBJHDF_SRCS})


SET_PROPERTY(TARGET libjhdf APPEND PROPERTY COMPILE_DEFINITIONS _HDF5USEDLL_)
SET_PROPERTY(TARGET libjhdf APPEND PROPERTY COMPILE_DEFINITIONS _HDF5USEHLDLL_)
IF(WIN32)
  SET(LIBJHDF_VERSION 0.1)
ELSE(WIN32)
  SET(LIBJHDF_VERSION 0.1.0)
ENDIF(WIN32)
SET_TARGET_PROPERTIES(libjhdf PROPERTIES VERSION ${LIBJHDF_VERSION})
SET_TARGET_PROPERTIES(libjhdf PROPERTIES SOVERSION ${LIBJHDF_VERSION})
   
FIND_PACKAGE(JNI)
IF(NOT JAVA_INCLUDE_PATH)     
  MESSAGE(FATAL_ERROR "No system JNI library.")
ENDIF(NOT JAVA_INCLUDE_PATH)
INCLUDE_DIRECTORIES(${JAVA_INCLUDE_PATH} ${JAVA_INCLUDE_PATH2})
LINK_LIBRARIES(${JAVA_JVM_LIB_PATH})

ADD_SUBDIRECTORY(../libhdf ${CMAKE_CURRENT_BINARY_DIR})         
ADD_DEPENDENCIES(libjhdf libhdf5)


# Debug or release build
OPTION(LIBJHDF_BUILD_DEBUG "Build libhdf with debugging info" OFF)
IF(LIBJHDF_BUILD_DEBUG)
  SET(LIBJHDF_BUILD_TYPE DEBUG)
ELSE(LIBJHDF_BUILD_DEBUG)
  SET(LIBJHDF_BUILD_TYPE RELEASE)
ENDIF(LIBJHDF_BUILD_DEBUG)

SET_PROPERTY(TARGET libjhdf APPEND PROPERTY COMPILE_DEFINITIONS_DEBUG DEBUG)
SET_PROPERTY(TARGET libjhdf APPEND PROPERTY COMPILE_DEFINITIONS_RELEASE NDEBUG)
#SET_TARGET_PROPERTIES(zlib PROPERTIES DEBUG_POSTFIX D)

# Deal with MS attempt to deprecate everything
IF(MSVC)
  SET_PROPERTY(TARGET libjhdf APPEND PROPERTY 
    COMPILE_DEFINITIONS _CRT_SECURE_NO_DEPRECATE)
  SET_PROPERTY(TARGET libjhdf APPEND PROPERTY 
    COMPILE_DEFINITIONS _CRT_NONSTDC_NO_DEPRECATE)
ENDIF(MSVC)

SET(LIBJHDF_INCLUDE_DIR ${PROJECT_SOURCE_DIR}/src/c)
INCLUDE_DIRECTORIES(${LIBJHDF_INCLUDE_DIR} ${LIBHDF_INCLUDE_DIR})
# Deal with MS attempt to deprecate everything
IF(MSVC)
  SET(VS_INCLUDE_DIR ${PROJECT_SOURCE_DIR}/src/c/vs-specific)
  INCLUDE_DIRECTORIES(${VS_INCLUDE_DIR})
ENDIF(MSVC)

# Visual Studio Sugar
SET_TARGET_PROPERTIES(libjhdf PROPERTIES PROJECT_LABEL "libjhdf")

# Make Sugar
#SET(CMAKE_VERBOSE_MAKEFILE ON)

TARGET_LINK_LIBRARIES(libjhdf ${LIBJHDF_LIBRARIES})