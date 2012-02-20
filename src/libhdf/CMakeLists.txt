#Change PROJECT_NAME to the name of your project
PROJECT(LIBHDF)
CMAKE_MINIMUM_REQUIRED(VERSION 2.6)
INCLUDE(${CMAKE_ROOT}/Modules/CheckIncludeFile.cmake)

SET(LIBHDF_LINK_TYPE SHARED)
SET(LIBHDF_BUILD_TYPE DEBUG)
SET(ZLIB_USE_INTERNAL ON)
SET(CMAKE_INSTALL_PREFIX C:/opt/libhdf)

# Check for size_t
INCLUDE(${CMAKE_ROOT}/Modules/CheckTypeSize.cmake)
CHECK_TYPE_SIZE(__int64 H5_SIZEOF___INT64)
CHECK_TYPE_SIZE(char H5_SIZEOF_CHAR)
CHECK_TYPE_SIZE(double H5_SIZEOF_DOUBLE)
CHECK_TYPE_SIZE(float H5_SIZEOF_FLOAT)
CHECK_TYPE_SIZE(int H5_SIZEOF_INT)
CHECK_TYPE_SIZE(long H5_SIZEOF_LONG)
CHECK_TYPE_SIZE("long double" H5_SIZEOF_LONG_DOUBLE)
CHECK_TYPE_SIZE(off_t H5_SIZEOF_OFF_T)
CHECK_TYPE_SIZE(short H5_SIZEOF_SHORT)
CHECK_TYPE_SIZE(size_t H5_SIZEOF_SIZE_T)


SET(LIBHDF_SRCS
  hl/src/H5DSprivate.h
  hl/src/H5DSpublic.h
  hl/src/H5HLprivate2.h
  hl/src/H5IMprivate.h
  hl/src/H5IMpublic.h
  hl/src/H5LTparse.h
  hl/src/H5LTprivate.h
  hl/src/H5LTpublic.h
  hl/src/H5PTprivate.h
  hl/src/H5PTpublic.h
  hl/src/H5TBprivate.h
  hl/src/H5TBpublic.h
  hl/src/hdf5_hl.h
  src/H5ACpkg.h
  src/H5ACprivate.h
  src/H5ACpublic.h
  src/H5api_adpt.h
  src/H5Apkg.h
  src/H5Aprivate.h
  src/H5Apublic.h
  src/H5B2pkg.h
  src/H5B2private.h
  src/H5B2public.h
  src/H5Bpkg.h
  src/H5Bprivate.h
  src/H5Bpublic.h
  src/H5Cpkg.h
  src/H5Cprivate.h
  src/H5Cpublic.h
  src/H5CSprivate.h
  src/H5Dpkg.h
  src/H5Dprivate.h
  src/H5Dpublic.h
  src/H5Edefin.h
  src/H5Einit.h
  src/H5Epkg.h
  src/H5Eprivate.h
  src/H5Epubgen.h
  src/H5Epublic.h
  src/H5Eterm.h
  src/H5FDcore.h
  src/H5FDdirect.h
  src/H5FDfamily.h
  src/H5FDlog.h
  src/H5FDmpi.h
  src/H5FDmpio.h
  src/H5FDmpiposix.h
  src/H5FDmulti.h
  src/H5FDpkg.h
  src/H5FDprivate.h
  src/H5FDpublic.h
  src/H5FDsec2.h
  src/H5FDstdio.h
  src/H5FDwindows.h
  src/H5FLprivate.h
  src/H5FOprivate.h
  src/H5Fpkg.h
  src/H5Fprivate.h
  src/H5Fpublic.h
  src/H5FSpkg.h
  src/H5FSprivate.h
  src/H5FSpublic.h
  src/H5Gpkg.h
  src/H5Gprivate.h
  src/H5Gpublic.h
  src/H5HFpkg.h
  src/H5HFprivate.h
  src/H5HFpublic.h
  src/H5HGpkg.h
  src/H5HGprivate.h
  src/H5HGpublic.h
  src/H5HLpkg.h
  src/H5HLprivate.h
  src/H5HLpublic.h
  src/H5HPprivate.h
  src/H5Ipkg.h
  src/H5Iprivate.h
  src/H5Ipublic.h
  src/H5Lpkg.h
  src/H5Lprivate.h
  src/H5Lpublic.h
  src/H5MFprivate.h
  src/H5MMprivate.h
  src/H5MMpublic.h
  src/H5MPpkg.h
  src/H5MPprivate.h
  src/H5Opkg.h
  src/H5Oprivate.h
  src/H5Opublic.h
  src/H5Oshared.h
  src/H5Ppkg.h
  src/H5Pprivate.h
  src/H5Ppublic.h
  src/H5private.h
  src/H5pubconf.h
  src/H5public.h
  src/H5RCprivate.h
  src/H5Rpkg.h
  src/H5Rprivate.h
  src/H5Rpublic.h
  src/H5RSprivate.h
  src/H5SLprivate.h
  src/H5SMpkg.h
  src/H5SMprivate.h
  src/H5Spkg.h
  src/H5Sprivate.h
  src/H5Spublic.h
  src/H5STprivate.h
  src/H5Tpkg.h
  src/H5Tprivate.h
  src/H5Tpublic.h
  src/H5TSprivate.h
  src/H5version.h
  src/H5Vprivate.h
  src/H5WBprivate.h
  src/H5Zpkg.h
  src/H5Zprivate.h
  src/H5Zpublic.h
  src/hdf5.h
  hl/src/H5DS.c
  hl/src/H5IM.c
  hl/src/H5LT.c
  hl/src/H5LTanalyze.c
  hl/src/H5LTparse.c
  hl/src/H5PT.c
  hl/src/H5TB.c
  src/H5.c
  src/H5A.c
  src/H5Abtree2.c
  src/H5AC.c
  src/H5Adense.c
  src/H5Adeprec.c
  src/H5Aint.c
  src/H5Atest.c
  src/H5B.c
  src/H5B2.c
  src/H5B2cache.c
  src/H5B2dbg.c
  src/H5B2int.c
  src/H5B2stat.c
  src/H5B2test.c
  src/H5Bcache.c
  src/H5C.c
  src/H5checksum.c
  src/H5CS.c
  src/H5D.c
  src/H5dbg.c
  src/H5Dcompact.c
  src/H5Dcontig.c
  src/H5Ddbg.c
  src/H5Ddeprec.c
  src/H5Defl.c
  src/H5detect.c
  src/H5Dfill.c
  src/H5Dint.c
  src/H5Dio.c
  src/H5Distore.c
  src/H5Dmpio.c
  src/H5Doh.c
  src/H5Dselect.c
  src/H5Dtest.c
  src/H5E.c
  src/H5Edeprec.c
  src/H5Eint.c
  src/H5F.c
  src/H5FD.c
  src/H5Fdbg.c
  src/H5FDcore.c
  src/H5FDdirect.c
  src/H5FDfamily.c
  src/H5FDlog.c
  src/H5FDmpi.c
  src/H5FDmpio.c
  src/H5FDmpiposix.c
  src/H5FDmulti.c
  src/H5FDsec2.c
  src/H5FDspace.c
  src/H5FDstdio.c
  src/H5FDwindows.c
  src/H5Ffake.c
  src/H5FL.c
  src/H5Fmount.c
  src/H5FO.c
  src/H5FS.c
  src/H5FScache.c
  src/H5FSdbg.c
  src/H5Fsfile.c
  src/H5FSsection.c
  src/H5Fsuper.c
  src/H5Ftest.c
  src/H5G.c
  src/H5Gbtree2.c
  src/H5Gcompact.c
  src/H5Gdense.c
  src/H5Gdeprec.c
  src/H5Gent.c
  src/H5Gint.c
  src/H5Glink.c
  src/H5Gloc.c
  src/H5Gname.c
  src/H5Gnode.c
  src/H5Gobj.c
  src/H5Goh.c
  src/H5Gstab.c
  src/H5Gtest.c
  src/H5Gtraverse.c
  src/H5HF.c
  src/H5HFbtree2.c
  src/H5HFcache.c
  src/H5HFdbg.c
  src/H5HFdblock.c
  src/H5HFdtable.c
  src/H5HFhdr.c
  src/H5HFhuge.c
  src/H5HFiblock.c
  src/H5HFiter.c
  src/H5HFman.c
  src/H5HFsection.c
  src/H5HFspace.c
  src/H5HFstat.c
  src/H5HFtest.c
  src/H5HFtiny.c
  src/H5HG.c
  src/H5HGdbg.c
  src/H5HL.c
  src/H5HLdbg.c
  src/H5HP.c
  src/H5I.c
  src/H5L.c
  src/H5Lexternal.c
  src/H5MF.c
  src/H5MM.c
  src/H5MP.c
  src/H5MPtest.c
  src/H5O.c
  src/H5Oainfo.c
  src/H5Oalloc.c
  src/H5Oattr.c
  src/H5Oattribute.c
  src/H5Obogus.c
  src/H5Obtreek.c
  src/H5Ocache.c
  src/H5Ocont.c
  src/H5Ocopy.c
  src/H5Odbg.c
  src/H5Odrvinfo.c
  src/H5Odtype.c
  src/H5Oefl.c
  src/H5Ofill.c
  src/H5Oginfo.c
  src/H5Olayout.c
  src/H5Olinfo.c
  src/H5Olink.c
  src/H5Omessage.c
  src/H5Omtime.c
  src/H5Oname.c
  src/H5Onull.c
  src/H5Opline.c
  src/H5Orefcount.c
  src/H5Osdspace.c
  src/H5Oshared.c
  src/H5Oshmesg.c
  src/H5Ostab.c
  src/H5Otest.c
  src/H5Ounknown.c
  src/H5P.c
  src/H5Pacpl.c
  src/H5Pdcpl.c
  src/H5Pdeprec.c
  src/H5Pdxpl.c
  src/H5Pfapl.c
  src/H5Pfcpl.c
  src/H5Pfmpl.c
  src/H5Pgcpl.c
  src/H5Pint.c
  src/H5Plapl.c
  src/H5Plcpl.c
  src/H5Pocpl.c
  src/H5Pocpypl.c
  src/H5Pstrcpl.c
  src/H5Ptest.c
  src/H5R.c
  src/H5RC.c
  src/H5Rdeprec.c
  src/H5RS.c
  src/H5S.c
  src/H5Sall.c
  src/H5Sdbg.c
  src/H5Shyper.c
  src/H5SL.c
  src/H5SM.c
  src/H5SMbtree2.c
  src/H5SMcache.c
  src/H5Smpio.c
  src/H5SMtest.c
  src/H5Snone.c
  src/H5Spoint.c
  src/H5Sselect.c
  src/H5ST.c
  src/H5Stest.c
  src/H5system.c
  src/H5T.c
  src/H5Tarray.c
  src/H5Tbit.c
  src/H5Tcommit.c
  src/H5Tcompound.c
  src/H5Tconv.c
  src/H5Tcset.c
  src/H5Tdbg.c
  src/H5Tdeprec.c
  src/H5Tenum.c
  src/H5Tfields.c
  src/H5Tfixed.c
  src/H5Tfloat.c
  src/H5timer.c
  src/H5Tinit.c
  src/H5Tnative.c
  src/H5Toffset.c
  src/H5Toh.c
  src/H5Topaque.c
  src/H5Torder.c
  src/H5Tpad.c
  src/H5Tprecis.c
  src/H5trace.c
  src/H5TS.c
  src/H5Tstrpad.c
  src/H5Tvisit.c
  src/H5Tvlen.c
  src/H5V.c
  src/H5WB.c
  src/H5Z.c
  src/H5Zdeflate.c
  src/H5Zfletcher32.c
  src/H5Znbit.c
  src/H5Zscaleoffset.c
  src/H5Zshuffle.c
  src/H5Zszip.c
  src/H5Ztrans.c
  )

# Shared or static lbrary
OPTION(LIBHDF_BUILD_SHARED "Build libhdf as a shared library" ON)
IF(LIBHDF_BUILD_SHARED)
  SET(LIBHDF_LINK_TYPE SHARED)
ELSE(LIBHDF_BUILD_SHARED)
  SET(LIBHDF_LINK_TYPE STATIC)
ENDIF(LIBHDF_BUILD_SHARED)

SET(LIBHDF_LIBRARIES libhdf5 PARENT_SCOPE)
ADD_LIBRARY(libhdf5 ${LIBHDF_LINK_TYPE} ${LIBHDF_SRCS})

IF(LIBHDF_BUILD_SHARED)
  #SET_TARGET_PROPERTIES doesn't work for multiple declarations.
  SET_TARGET_PROPERTIES(libhdf5 PROPERTIES DEFINE_SYMBOL _HDF5DLL_)
  SET_PROPERTY(TARGET libhdf5 APPEND PROPERTY COMPILE_DEFINITIONS _HDF5_HLDLL_EXPORTS_)
   IF(WIN32)
     SET(LIBHDF_VERSION 1.8)
   ELSE(WIN32)
     SET(LIBHDF_VERSION 1.8.0)
   ENDIF(WIN32)
   SET_TARGET_PROPERTIES(libhdf5 PROPERTIES VERSION ${LIBHDF_VERSION})
   SET_TARGET_PROPERTIES(libhdf5 PROPERTIES SOVERSION ${LIBHDF_VERSION})
ENDIF(LIBHDF_BUILD_SHARED)

OPTION(ZLIB_SUPPORT "Build libhdf with zlib support" ON)
OPTION(ZLIB_USE_INTERNAL "Use internal version of zlib" ON)
IF(ZLIB_SUPPORT)
  IF(ZLIB_USE_INTERNAL)
    ADD_SUBDIRECTORY(../zlib ${CMAKE_CURRENT_BINARY_DIR})         
    ADD_DEPENDENCIES(libhdf5 zlib)

  ELSE(ZLIB_USE_INTERNAL)
    FIND_PACKAGE(ZLIB)
    IF(ZLIB_FOUND)     
      ## Update zlib target to match these vars.     
      MESSAGE("System ZLIB okay.")

    ELSE(ZLIB_FOUND)    
      MESSAGE(FATAL_ERROR "No system ZLIB. Enable internal ZLIB library or disable ZLIB support.")
    ENDIF(ZLIB_FOUND)
  ENDIF(ZLIB_USE_INTERNAL) 
  
  # Missing zlib should be trapped above. Set zlib present flags.
  SET(H5_HAVE_FILTER_DEFLATE 1)  
  SET(H5_HAVE_ZLIB_H 1)    
  INCLUDE_DIRECTORIES(${ZLIB_INCLUDE_DIR})
  LINK_LIBRARIES(${ZLIB_LIBRARIES})
ENDIF(ZLIB_SUPPORT)

OPTION(SZLIB_SUPPORT "Build libhdf with szlib support" ON)
IF(SZLIB_SUPPORT)
  ADD_SUBDIRECTORY(../szlib ${CMAKE_CURRENT_BINARY_DIR})  
  ADD_DEPENDENCIES(libhdf5 szlib)  
  INCLUDE_DIRECTORIES(${SZLIB_INCLUDE_DIR})
  LINK_LIBRARIES(${SZLIB_LIBRARIES})     
  SET(H5_HAVE_FILTER_SZIP 1)  
  SET(H5_HAVE_SZLIB_H 1)    
ENDIF(SZLIB_SUPPORT)

# Debug or release build
OPTION(LIBHDF_BUILD_DEBUG "Build libhdf with debugging info" OFF)
IF(LIBHDF_BUILD_DEBUG)
  SET(LIBHDF_BUILD_TYPE DEBUG)
ELSE(LIBHDF_BUILD_DEBUG)
  SET(LIBHDF_BUILD_TYPE RELEASE)
ENDIF(LIBHDF_BUILD_DEBUG)

SET_PROPERTY(TARGET libhdf5 APPEND PROPERTY COMPILE_DEFINITIONS_DEBUG DEBUG)
SET_PROPERTY(TARGET libhdf5 APPEND PROPERTY COMPILE_DEFINITIONS_RELEASE NDEBUG)
#SET_TARGET_PROPERTIES(zlib PROPERTIES DEBUG_POSTFIX D)

# Deal with MS attempt to deprecate everything
IF(MSVC)
  SET_PROPERTY(TARGET libhdf5 APPEND PROPERTY 
    COMPILE_DEFINITIONS _CRT_SECURE_NO_DEPRECATE)
  SET_PROPERTY(TARGET libhdf5 APPEND PROPERTY 
    COMPILE_DEFINITIONS _CRT_NONSTDC_NO_DEPRECATE)
ENDIF(MSVC)

SET(LIBHDF_INCLUDE_DIR ${PROJECT_SOURCE_DIR}/src ${PROJECT_SOURCE_DIR}/hl/src)
INCLUDE_DIRECTORIES(${LIBHDF_INCLUDE_DIR} ${ZLIB_INCLUDE_DIR} ${SZLIB_INCLUDE_DIR})
SET(LIBHDF_INCLUDE_DIR ${LIBHDF_INCLUDE_DIR} PARENT_SCOPE)

CONFIGURE_FILE(${PROJECT_SOURCE_DIR}/src/H5pubconf.h.in ${PROJECT_SOURCE_DIR}/src/H5pubconf.h)

# Visual Studio Sugar
SET_TARGET_PROPERTIES(libhdf5 PROPERTIES PROJECT_LABEL "libhdf")

# Make Sugar
#SET(CMAKE_VERBOSE_MAKEFILE ON)

TARGET_LINK_LIBRARIES(libhdf5 ${ZLIB_LIBRARIES} ${SZLIB_LIBRARIES})