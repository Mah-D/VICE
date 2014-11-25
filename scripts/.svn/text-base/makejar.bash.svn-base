#!/bin/bash

if [ "$1" = "" ]; then
    RELEASE=`java vpc.Version`
    if [ ! "$?" = 0 ]; then
	echo "  -> Error: could not get vpc version."
	exit 1
    fi

else
    RELEASE=$1
fi

cd compiler/bin

JARFILE=vpc-$RELEASE.jar

jar cmf MANIFEST.MF ../jars/$JARFILE vpc cck
if [ ! "$?" = 0 ]; then
    echo "  -> Error: could not build jar file $JARFILE."
    exit 1
fi

echo $JARFILE
