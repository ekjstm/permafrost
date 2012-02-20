%define HVOID_HELPERS(CTYPE, JNITYPE, JTYPE, JAVATYPE)

%typemap(jni) CTYPE* VOID_OUT %{JNITYPE##Array%}
%typemap(jtype) CTYPE* VOID_OUT "JTYPE[]"
%typemap(jstype) CTYPE* VOID_OUT  "JTYPE[]"
%typemap(javain) CTYPE* VOID_OUT  "$javainput"

%typemap(in) CTYPE* VOID_OUT(CTYPE* temp) {
  if (!$input) {
    SWIG_JavaThrowException(jenv, SWIG_JavaNullPointerException, "array null");
    return $null;
  }
  if (JCALL1(GetArrayLength, jenv, $input) == 0) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIndexOutOfBoundsException, "Array must contain at least 1 element");
    return $null;
  } 	
  $1 = &temp; 
}

%typemap(argout) CTYPE* VOID_OUT {
  JNITYPE jvalue = (JNITYPE) temp$argnum;
  JCALL4(Set##JAVATYPE##ArrayRegion, jenv, $input, 0, 1, &jvalue);
}

%typemap(jni) CTYPE* VOID_IN "JNITYPE"
%typemap(jtype) CTYPE* VOID_IN "JTYPE"
%typemap(jstype) CTYPE* VOID_IN  "JTYPE"
%typemap(javain) CTYPE* VOID_IN  "$javainput"

%typemap(in) CTYPE* VOID_IN {  
  $1 = &$input; 
}

%enddef

HVOID_HELPERS(char, jbyte, byte, Byte)
HVOID_HELPERS(short, jshort, short, Short)
HVOID_HELPERS(int, jint, int, Int)
HVOID_HELPERS(long, jint, int, Int)
HVOID_HELPERS(long long, jlong, long, Long)
#undef HVOID_HELPERS
