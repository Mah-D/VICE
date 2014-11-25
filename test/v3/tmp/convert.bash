#!/bin/bash

CONVERSIONS=(
	"int\\[\\]"		"Array<int>"
	"bool\\[\\]"		"Array<bool>"
	"char\\[\\]"		"Array<char>"
	"\\/\\/ @Result: "	""
	"NullCheck"		"!NullCheck"
	"Divide"		"!Divide"
	"Bounds"		"!Bounds"
	"): "			") -> "
	"):"			") -> "
	"function()"		"void"
	"function"		""
	"constructor"		"new"
	"\\["			"\\("
	"\\]"			"\\)"
	"\\(=[0-9]*\\),"        "\\1;"
       " @Harness: v2-exec"   "@execute"
        "eption,"               "eption;"
)

conv=0

while [ "$conv" -lt "${#CONVERSIONS[@]}" ]; do
   	from=${CONVERSIONS[$conv]}
   	argn=`expr $conv + 1`
    	to=${CONVERSIONS[$argn]}

	echo Converting "$from    $to"

	for f in *.v3; do
	    sed -e "s/$from/$to/g" $f > new/$f
	    mv new/*.v3 .
	done
	
	conv=`expr $conv + 2`
done
