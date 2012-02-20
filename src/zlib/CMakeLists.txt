PROJECT(ZLIB)
CMAKE_MINIMUM_REQUIRED(VERSION 2.6)

SET(ZLIB_LINK_TYPE SHARED)
SET(ZLIB_BUILD_TYPE DEBUG)
SET(CMAKE_INSTALL_PREFIX C:/opt/zlib)

# Check for unistd.h
# Needed for *nix platforms
INCLUDE(${CMAKE_ROOT}/Modules/CheckIncludeFile.cmake)
CHECK_INCLUDE_FILE("unistd.h" HAVE_UNISTD_H)
IF(HAVE_UNISTD_H)
  ADD_DEFINITIONS(-DHAVE_UNISTD_H)
ENDIF(HAVE_UNISTD_H)

# Check for needed functions
INCLUDE(${CMAKE_ROOT}/Modules/CheckFunctionExists.cmake)
CHECK_FUNCTION_EXISTS(vsnprintf HAVE_VSNPRINTF)

CONFIGURE_FILE(${PROJECT_SOURCE_DIR}/zconf.in.h ${PROJECT_SOURCE_DIR}/zconf.h)

# Source files
SET(ZLIB_SRCS
crc32.h
deflate.h
inffast.h
inffixed.h
inflate.h
inftrees.h
trees.h
zconf.h
zlib.h
zutil.h
adler32.c
compress.c
contrib/minizip/crypt.h
contrib/minizip/ioapi.h
contrib/minizip/iowin32.h
contrib/minizip/mztools.h
contrib/minizip/unzip.h
contrib/minizip/zip.h
crc32.c
deflate.c
gzio.c
infback.c
inffast.c
inflate.c
inftrees.c
#minigzip.c
trees.c
uncompr.c
zutil.c
#contrib/masmx64/inffas8664.c
#contrib/masmx86/gvmat32c.c
contrib/minizip/ioapi.c
contrib/minizip/iowin32.c
#contrib/minizip/miniunz.c
#contrib/minizip/minizip.c
contrib/minizip/mztools.c
contrib/minizip/unzip.c
contrib/minizip/zip.c
)

# Build Options
# Install prefix
OPTION(CMAKE_INSTALL_PREFIX
  "Install path prefix. Prepened to install directories," 
  C:/opt/zlib
  )

# Shared or static lbrary
OPTION(ZLIB_BUILD_SHARED
  "Build zlib as a shared library" 
  ON
)
IF(ZLIB_BUILD_SHARED)    
  SET(ZLIB_LINK_TYPE SHARED)
ELSE(ZLIB_BUILD_SHARED)
  SET(ZLIB_LINK_TYPE STATIC)
ENDIF(ZLIB_BUILD_SHARED)    

SET(ZLIB_LIBRARIES zlib PARENT_SCOPE)
ADD_LIBRARY(zlib ${ZLIB_LINK_TYPE} ${ZLIB_SRCS})

IF(ZLIB_BUILD_SHARED)    
  SET(ZLIB_COMPILE_DEFINITIONS ZLIB_DLL)
  SET_PROPERTY(TARGET zlib APPEND PROPERTY COMPILE_DEFINITIONS ZLIB_DLL)
  SET_TARGET_PROPERTIES(zlib PROPERTIES DEFINE_SYMBOL ZLIB_INTERNAL)
  IF(WIN32)
    SET(ZLIB_VERSION 1.2)
  ELSE(WIN32)
    SET(ZLIB_VERSION 1.2.3)
  ENDIF(WIN32)
    SET_TARGET_PROPERTIES(zlib PROPERTIES VERSION ${ZLIB_VERSION})
    SET_TARGET_PROPERTIES(zlib PROPERTIES SOVERSION ${ZLIB_VERSION})
ENDIF(ZLIB_BUILD_SHARED)

# Debug or release build
OPTION(ZLIB_BUILD_DEBUG
  "Build zlib with debugging info"
  OFF
)
IF(ZLIB_BUILD_DEBUG)
  SET(ZLIB_BUILD_TYPE DEBUG)
ELSE(ZLIB_BUILD_DEBUG)
  SET(ZLIB_BUILD_TYPE RELEASE)
ENDIF(ZLIB_BUILD_DEBUG)

SET_PROPERTY(TARGET zlib APPEND PROPERTY COMPILE_DEFINITIONS_DEBUG DEBUG)
SET_PROPERTY(TARGET zlib APPEND PROPERTY COMPILE_DEFINITIONS_RELEASE NDEBUG)
#SET_TARGET_PROPERTIES(zlib PROPERTIES DEBUG_POSTFIX D)

# Deal with MS attempt to deprecate everything
IF(MSVC)
  SET_PROPERTY(TARGET zlib APPEND PROPERTY 
    COMPILE_DEFINITIONS _CRT_SECURE_NO_DEPRECATE)
  SET_PROPERTY(TARGET zlib APPEND PROPERTY 
    COMPILE_DEFINITIONS _CRT_NONSTDC_NO_DEPRECATE)
ENDIF(MSVC)

# Set up the include directories
SET(ZLIB_INCLUDE_DIR ${PROJECT_SOURCE_DIR} PARENT_SCOPE)
INCLUDE_DIRECTORIES(${PROJECT_SOURCE_DIR})

# Visual Studio Sugar
SET_TARGET_PROPERTIES(zlib PROPERTIES PROJECT_LABEL "zlib")

# Make Sugar
#SET(CMAKE_VERBOSE_MAKEFILE ON)

# Execute the build
TARGET_LINK_LIBRARIES(zlib)
