#!/bin/bash

VINT3="java -classpath ${VIRGIL_HOME}/compiler/bin vpc.Interpreter -print-value=false -language=virgil3"
AENEAS="$VINT3 ${AENEAS_HOME}/*.v3 $VERBOSE"
AENEAS_JVM="$VINT3 ${AENEAS_HOME}/*.v3 ${AENEAS_HOME}/jvm/*.v3 -target=jvm"
AENEAS_TEST="$VINT3 ${AENEAS_HOME}/*.v3 ${AENEAS_HOME}/test/*.v3 -test"

jvmTests() {
	if [ "$#" = "0" ]; then
	    cd ${VIRGIL_HOME}/test/v3/execute
	    TESTS=`echo *.v3`
	else
	    TESTS=$*
	fi
	rm -f bin/*.class

	echo Compiling....
	$AENEAS_JVM -multiple -verbose=1 $TESTS

	echo Running....
	java -classpath ${VIRGIL_HOME}/compiler/bin:bin/ aeneas.jvm.JVMTester $TESTS
}

jvmTests $*
