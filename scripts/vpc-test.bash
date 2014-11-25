#!/bin/bash

CYAN='[1;36m'
GREEN='[1;32m'
NORM='[0;00m'

VERBOSE=2
THREADS=2

TESTS=(
   parser "" 
   seman  "" 
   types  ""
   init   ""
   sched  ""
   fail   ""
   execute "-executor=interpreter"
   execute "-executor=avrora"
   execute "-executor=tir2c"
   execute "-executor=tir2c -vertical-model"
   execute "-executor=avrora -compress=true"
   execute "-executor=avrora -compress=true -avr-rom=true"
   execute "-executor=avrora -vertical-model"
   execute "-executor=avrora -vertical-model -compress=true"
)

RESULT=test/tmp/result

addTests() {
    T=`echo $1`
    if [ -f `echo $T | cut -d' ' -f1` ]; then
	export FILES="$FILES $T"
    fi
}

TOTAL=0
PASSED=0

test=0

if [ "$AVRORA_CLASSPATH" = "" ]; then
    echo "Error: you must set the AVRORA_CLASSPATH environment variable."
    exit 2
fi

while [ "$test" -lt "${#TESTS[@]}" ]; do
    dir=${TESTS[$test]}
    argn=`expr $test + 1`
    arg=${TESTS[$argn]}

    echo "${CYAN}Running${NORM}: $dir $arg"
    export FILES=''
    addTests "test/$dir/*.v"
    addTests "test/$dir/*.tst"

    java -ea -cp $CLASSPATH:$AVRORA_CLASSPATH vpc.test.Main -tmp=test/tmp/ -threads=$THREADS -verbose=$VERBOSE $arg $FILES | tee $RESULT

    OUT=`grep Passed $RESULT`

    if [ "$OUT" != "" ]; then
	NUM=`echo $OUT | cut -d' ' -f4`
	PASS=`echo $OUT | cut -d' ' -f2`

	TOTAL=`expr $TOTAL + $NUM`
	PASSED=`expr $PASSED + $PASS`
    fi

    test=`expr $test + 2`
done

echo "${GREEN}Total${NORM}: $PASSED of $TOTAL"
