#!/bin/bash

while [ $# != 0 ]; do
        flag="$1"
        case "$flag" in
            -verbose) 
		VERBOSE="-verbose=2"
		shift
                ;;
            -vpc) VPC_ONLY=1
		shift
                ;;
            -aeneas) AENEAS_ONLY=1
		shift
                ;;
            -nofail) NO_FAIL=1
		shift
                ;;
             *) break
                ;;
        esac
done

AENEAS_FILES="${AENEAS_HOME}/*.v3 ${AENEAS_HOME}/*/*.v3"

VINT3="java -Xmx512m -classpath ${VIRGIL_HOME}/compiler/bin vpc.Interpreter -print-value=false -language=virgil3"
JVM_AENEAS="java -Xmx512m -classpath ${VIRGIL_HOME}/compiler/bin:${VIRGIL_HOME}/compiler/src/aeneas/bin aeneas.jvm.V3System Aeneas"
VPC_AENEAS="$VINT3 ${AENEAS_FILES} -a $VERBOSE"

jvmTests() {
	AENEAS="$2"
	cd ${VIRGIL_HOME}/test/v3/execute
	TESTS=`echo *.v3`
	rm -f bin/*.class

	echo "($1) Compiling to jvm..."
	$AENEAS -target=jvm -multiple -verbose=1 $TESTS
	echo

	echo "($1) Running on jvm..."
	java -classpath bin/:${HOME}/virgil/compiler/bin aeneas.jvm.JVMTester $TESTS

	echo
}

parseTests() {
	AENEAS="$2"
	echo "($1) Running parse tests..."
	cd ${VIRGIL_HOME}/test/v3/parser
	TESTS=`echo *.v3`
	$AENEAS -test $TESTS

	echo
}

semanTests() {
	AENEAS="$2"
	echo "($1) Running semantic tests..."
	cd ${VIRGIL_HOME}/test/v3/seman
	TESTS=`echo *.v3`
	$AENEAS -test $TESTS

	echo
}

interpreterTests() {
	AENEAS="$2"
	echo "($1) Running interpreter tests..."
	cd ${VIRGIL_HOME}/test/v3/execute
	TESTS=`echo *.v3`
	$AENEAS -test $TESTS

	echo
}

failingTests() {
	AENEAS="$2"
	echo "($1) Running failure tests..."
	cd ${VIRGIL_HOME}/test/v3/fail
	TESTS=`echo *.v3`
	$AENEAS -test $TESTS

	echo
}

compileAeneas() {
	AENEAS="$2"
	echo "($1) Compiling Aeneas..."
	cd /tmp
	if [ -e bin ]; then
		rm -f bin/*.class
	else
		mkdir bin
	fi
	$AENEAS -target=jvm ${AENEAS_FILES}
	cp -r bin/*.class ${AENEAS_HOME}/bin/
}

runTests() {
	( parseTests "$1" "$2")
	( semanTests "$1" "$2")
	( interpreterTests "$1" "$2")
	( jvmTests "$1" "$2")

	if [ "$NO_FAIL" != 1 ]; then
		( failingTests "$1" "$2")
	fi
}

if [ "$VIRGIL_HOME" = "" ]; then
	echo "The VIRGIL_HOME environment variable must be set."
	exit 0
fi

if [ "${AENEAS_HOME}" = "" ]; then
	echo "The AENEAS_HOME environment variable must be set."
	exit 0
fi

if [ "$#" = "0" ]; then
	if [ "$VPC_ONLY" = 1 ]; then
		# only run the tests on VPC
		( runTests "vpc" "$VPC_AENEAS")

	elif [ "$AENEAS_ONLY" = 1 ]; then
		# only run the tests by recompiling Aeneas
		( compileAeneas "aeneas-1" "$JVM_AENEAS" )
		( runTests "aeneas-2" "$JVM_AENEAS")

	else
		# run the tests in all modes
		( runTests "vpc" "$VPC_AENEAS")
		( compileAeneas "vpc" "$VPC_AENEAS" )
		( runTests "aeneas-1" "$JVM_AENEAS")
		( compileAeneas "aeneas-1" "$JVM_AENEAS" )
		( runTests "aeneas-2" "$JVM_AENEAS")
	fi
	
else
    $VPC_AENEAS -test $*
fi
