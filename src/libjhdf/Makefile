

CC = gcc

#LOC = -DDEBUG -g
CFLAGS = $(LOC)  -Wall

AS = $(CC)
ASFLAGS = $(LOC) -Wall

LD = $(CC)
LDFLAGS = $(LOC) -s

AR = ar
ARFLAGS = rcs

RC = windres
RCFLAGS = --define GCC_WINDRES

SHORT_NAME = $(subst _wrap,,$*)
SWIG = swig
DIR_JAVA = target/java/zonule/hdf/libhdf
SFLAGS = -Wall -Wallkw -java -outdir "$(DIR_JAVA)" -package "zonule.hdf.libhdf"

#LIB_HDF = ../../lib/libhdf/dll
INCLUDE_HDF = -I../../lib/libhdf/include
LIB_HDF = ../libhdf/hdf5/lib
INCLUDE_HDF = -I../libhdf/src
INCLUDE_JAVA = -I$(JAVA_HOME)/include -I$(JAVA_HOME)/include/win32

SRC = H5public_wrap.c H5Ipublic_wrap.c H5Apublic_wrap.c H5Spublic_wrap.c H5Epublic_wrap.c H5Fpublic_wrap.c H5Gpublic_wrap.c H5Rpublic_wrap.c H5Tpublic_wrap.c H5Ppublic_wrap.c H5Dpublic_wrap.c
OBJS = H5public_wrap.o H5Ipublic_wrap.o H5Apublic_wrap.o H5Spublic_wrap.o H5Epublic_wrap.o H5Fpublic_wrap.o H5Gpublic_wrap.o H5Rpublic_wrap.o H5Tpublic_wrap.o H5Ppublic_wrap.o H5Dpublic_wrap.o

STATICLIB = libjnihdf.a
SHAREDLIB = libhdfjni1.dll
IMPLIB    = libhdfjnidll.a
JAR       = dist/hdf-1.6.6.jar

$(JAR) : $(SHAREDLIB)
	ant

$(STATICLIB): $(OBJS) $(OBJA)
	$(AR) $(ARFLAGS) $@ $(OBJS) $(OBJA)

$(IMPLIB): $(SHAREDLIB)

$(SHAREDLIB): $(OBJS) $(OBJA)	
	gcc -shared -Wl,--out-implib,$(IMPLIB),--kill-at -L$(LIB_HDF) -lhdf5dll -o $@ $(OBJS) 
	strip $@

H5public_wrap.o : H5public_wrap.c
H5Ipublic_wrap.o : H5Ipublic_wrap.c
H5Apublic_wrap.o : H5Apublic_wrap.c
H5Spublic_wrap.o : H5Spublic_wrap.c
H5Epublic_wrap.o : H5Epublic_wrap.c
H5Fpublic_wrap.o : H5Fpublic_wrap.c
H5Gpublic_wrap.o : H5Gpublic_wrap.c
H5Rpublic_wrap.o : H5Rpublic_wrap.c
H5Tpublic_wrap.o : H5Tpublic_wrap.c
H5Ppublic_wrap.o : H5Ppublic_wrap.c
H5Dpublic_wrap.o : H5Dpublic_wrap.c

.PHONY: clean

clean:
	-rm -f $(STATICLIB)
	-rm -f $(SHAREDLIB)
	-rm -f $(IMPLIB)
	-rm -Rf $(DIR_JAVA)
	-rm -f *.c
	-rm *.o

%.c: %.i
	mkdir -p $(DIR_JAVA)
	$(SWIG) $(SFLAGS) $(INCLUDE_HDF) -o $@ $<

%.o: %.c
	$(CC) $(CFLAGS) $(INCLUDE_HDF) $(INCLUDE_JAVA) -c -o $@ $<