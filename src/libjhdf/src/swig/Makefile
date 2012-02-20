include ../../libjhdf.mk

DIR_JAVA = ../../target/swig-java/zonule/hdf/libhdf
DIR_C = ../../target/swig-c
SFLAGS = -Wall -Wallkw -java -outdir "$(DIR_JAVA)" -package "zonule.hdf.libhdf"


SHORT_NAME = $(subst _wrap,,$*)

SRCS = $(DIR_C)/H5public_wrap.c $(DIR_C)/H5Ipublic_wrap.c $(DIR_C)/H5Apublic_wrap.c $(DIR_C)/H5Spublic_wrap.c $(DIR_C)/H5Epublic_wrap.c $(DIR_C)/H5Fpublic_wrap.c $(DIR_C)/H5Gpublic_wrap.c $(DIR_C)/H5Rpublic_wrap.c $(DIR_C)/H5Tpublic_wrap.c $(DIR_C)/H5Ppublic_wrap.c $(DIR_C)/H5Dpublic_wrap.c

OBJS = $(DIR_C)/H5public_wrap.o $(DIR_C)/H5Ipublic_wrap.o $(DIR_C)/H5Apublic_wrap.o $(DIR_C)/H5Spublic_wrap.o $(DIR_C)/H5Epublic_wrap.o $(DIR_C)/H5Fpublic_wrap.o $(DIR_C)/H5Gpublic_wrap.o $(DIR_C)/H5Rpublic_wrap.o $(DIR_C)/H5Tpublic_wrap.o $(DIR_C)/H5Ppublic_wrap.o $(DIR_C)/H5Dpublic_wrap.o

STATICLIB = libjnihdf.a
SHAREDLIB = libhdfjni1.dll
IMPLIB    = libhdfjnidll.a
JAR       = dist/hdf-1.6.6.jar

$(JAR) : $(SHAREDLIB)
	echo ant

$(STATICLIB): $(OBJS) $(OBJA)
	$(AR) $(ARFLAGS) $@ $(OBJS) $(OBJA)

$(IMPLIB): $(SHAREDLIB)

$(SHAREDLIB): $(OBJS) $(OBJA)	
	echo gcc -shared -Wl,--out-implib,$(IMPLIB),--kill-at -L$(LIB_HDF) -lhdf5dll -o $@ $(OBJS) 
	echo strip $@

$(DIR_C)/H5public_wrap.o : $(DIR_C)/H5public_wrap.c
$(DIR_C)/H5Ipublic_wrap.o : $(DIR_C)/H5Ipublic_wrap.c
$(DIR_C)/H5Apublic_wrap.o : $(DIR_C)/H5Apublic_wrap.c
$(DIR_C)/H5Spublic_wrap.o : $(DIR_C)/H5Spublic_wrap.c
$(DIR_C)/H5Epublic_wrap.o : $(DIR_C)/H5Epublic_wrap.c
$(DIR_C)/H5Fpublic_wrap.o : $(DIR_C)/H5Fpublic_wrap.c
$(DIR_C)/H5Gpublic_wrap.o : $(DIR_C)/H5Gpublic_wrap.c
$(DIR_C)/H5Rpublic_wrap.o : $(DIR_C)/H5Rpublic_wrap.c
$(DIR_C)/H5Tpublic_wrap.o : $(DIR_C)/H5Tpublic_wrap.c
$(DIR_C)/H5Ppublic_wrap.o : $(DIR_C)/H5Ppublic_wrap.c
$(DIR_C)/H5Dpublic_wrap.o : $(DIR_C)/H5Dpublic_wrap.c

.PHONY: clean

clean:
	-rm -f $(STATICLIB)
	-rm -f $(SHAREDLIB)
	-rm -f $(IMPLIB)
	-rm -Rf $(DIR_JAVA)
	-rm -f *.c
	-rm *.o
clean-java:

clean-c:
	-rm -f $(OBJS)
	-rm -f $(SRCS)

$(DIR_C)/%.c: %.i
	mkdir -p $(DIR_C)
	mkdir -p $(DIR_JAVA)
	$(SWIG) $(SFLAGS) $(INCLUDE_HDF) -o $@ $<

%.o: %.c
	$(CC) $(CFLAGS) $(INCLUDE_HDF) $(INCLUDE_JAVA) -c -o $@ $<