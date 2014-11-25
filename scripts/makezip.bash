#!/bin/bash

if [ "$1" = "" ]; then
    RELEASE=`awk '{ printf "%s-%03d", $2, $3 }' apps/version`
    if [ ! "$?" = 0 ]; then
	echo "  -> Error: could not get apps version."
	exit 1
    fi

else
    RELEASE=$1
fi

ZIPFILE=apps-$RELEASE.zip

zip -r apps/zips/$ZIPFILE apps lib arch &> /dev/null
if [ ! "$?" = 0 ]; then
    echo "  -> Error: could not build zip file $ZIPFILE."
    exit 1
fi

echo $ZIPFILE
