/**
 *
 */
%define HENUM_OUTPUT_TYPEMAP(LABEL, JTYPE)
%typemap(in) LABEL* OUTPUT {
  if (!$input) {
    SWIG_JavaThrowException(jenv, SWIG_JavaNullPointerException, "array null");
    return $null;
  }
  if (JCALL1(GetArrayLength, jenv, $input) == 0) {
    SWIG_JavaThrowException(jenv, SWIG_JavaIndexOutOfBoundsException, "Array must contain at least 1 element");
    return $null;
  }
   $1 = malloc(sizeof($*1_type));
}
%typemap(jni) LABEL* OUTPUT "jintArray"
%typemap(jtype) LABEL* OUTPUT "int[]"
%typemap(jstype) LABEL* OUTPUT "JTYPE[]"
%typemap(javain, 
	 pre="    int[] tmp$javainput = {-1};",
	 post="      $javainput[0] = JTYPE.swigToEnum(tmp$javainput[0]);") LABEL* OUTPUT "tmp$javainput"
%typemap(freearg) LABEL* OUTPUT "free($1);"
%typemap(argout) LABEL* OUTPUT { 
  JCALL4(SetIntArrayRegion, jenv, $input, 0, 1, (jint*) $1);
}
%enddef


/**
 *
 */
%define HENUM_INPUT_TYPEMAP(LABEL, JTYPE)
%typemap(jni) LABEL* INPUT "jint"
%typemap(jtype) LABEL* INPUT "int"
%typemap(jstype) LABEL* INPUT "JTYPE"
%typemap(javain) LABEL* INPUT "$javainput.swigValue()"
%typemap(in) LABEL* INPUT "$1 = ($1_ltype) &$input;"
%typemap(freearg) LABEL* INPUT ""
%enddef

%define HENUM_ARRIN_TYPEMAP(LABEL, JTYPE)
%typemap(jni) LABEL INPUT[] "jintArray"
%typemap(jtype) LABEL INPUT[] "int[]"
%typemap(jstype) LABEL INPUT[] "JTYPE[]"
%typemap(javain, 
	 pre="    int[] tmp$javainput = new int[$javainput.length];
    for (int n=0; n<$javainput.length; n++) tmp$javainput[n] = $javainput[n].swigValue(); "
	 ) LABEL INPUT[] "tmp$javainput"
%typemap(in) LABEL INPUT[]
   %{  if (!HARR_JavaArrayInInt(jenv, ($&1_ltype) &$1, $input)) return -1; %}
%typemap(freearg) LABEL INPUT[] 
#ifdef __cplusplus
%{ delete [] $1; %}
#else
%{ free($1); %}
#endif
%enddef

%define HENUM_ARROUT_TYPEMAP(LABEL, JTYPE)
%typemap(jni) LABEL OUTPUT[] "jintArray"
%typemap(jtype) LABEL OUTPUT[] "int[]"
%typemap(jstype) LABEL OUTPUT[] "JTYPE[]"
%typemap(javain, 
	 pre="    int[] tmp$javainput = new int[$javainput.length];",
	 post="   for(int n=0; n<$javainput.length; n++) $javainput[n] = JTYPE.swigToEnum(tmp$javainput[n]);"
	 ) LABEL OUTPUT[] "tmp$javainput"
%typemap(in) LABEL OUTPUT[]
%{if (!HARR_JavaNewArrayInt(jenv, ($&1_ltype) &$1, $input)) return -1;%}
%typemap(argout) LABEL OUTPUT[]
%{ HARR_JavaArrayArgoutInt(jenv, ($1_ltype) $1, $input); %}
%typemap(freearg) LABEL OUTPUT[] 
#ifdef __cplusplus
%{ delete [] $1; %}
#else
%{ free($1); %}
#endif
%enddef
