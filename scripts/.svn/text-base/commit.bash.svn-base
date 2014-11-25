#!/bin/bash

RED='[0;31m'
GREEN='[0;32m'
NORM='[0;00m'

##########################################################################
#               N O T   F O R   P U B L I C   U S E
##########################################################################
#
#  This bash script is used by the Virgil developers to commit changes
#  to CVS and is not intended to be used by users. It checks multiple
#  correctness criteria, runs the tests, and increments the build number.
#
##########################################################################

if [ "$1" = "" ]; then
    echo "${RED}Usage${NORM}: ${GREEN}commit.bash <log message>${NORM}"
    exit
fi

if [ "$2" = "release" ]; then
    echo "  -> ${GREEN}Release${NORM} commit selected."
    RELEASE=1
else
    RELEASE=0
fi

TEST_FILES="`find test -name '*.v'` `find test -name '*.v3'` `find test -name '*.tst'` "
APP_FILES="`find apps lib -name '*.v'`"
JAVA_FILES=`find compiler/src -name '*.java'`
JJ_FILES=`find compiler/src -name '*.jj'`
AEN_FILES=`find compiler/src/aeneas -name '*.v3'`

MODULES='cck vpc'
ROOTPATH=`pwd`
VPC_ARCHIVE='/services/www/html/virgil/archives/vpc'
APP_ARCHIVE='/services/www/html/virgil/archives/apps'
AEN_ARCHIVE='/services/www/html/virgil/archives/aeneas'
JAR_NEEDED=0

report() {
    if [ "$3" = "" ]; then
	echo "  -> $1$2${NORM}"
    else
	echo "  -> $1$2${NORM}: $3"
    fi
}

reportError() {
    report "$RED" "$1" "$2"
}

reportSuccess() {
    report "$GREEN" "$1" "$2"
}

removeOldVersions() {
	for m in $MODULES; do
	    rm -f /tmp/${m}Version.java
	done
	rm -f /tmp/version
	rm -f /tmp/Version.v3
}

restoreOldVersions() {
	for m in $MODULES; do
	    if `test -e /tmp/${m}Version.java`; then
		cp /tmp/${m}Version.java $ROOTPATH/compiler/src/$m/Version.java
	    fi
	done
	if `test -e /tmp/version`; then
	    cp /tmp/version $ROOTPATH/apps/version
	fi
	if `test -e /tmp/Version.v3`; then
	    cp /tmp/Version.v3 $ROOTPATH/compiler/src/aeneas
	fi
}

# routine to check for successful CVS commit conditions
checkSuccess() {

    if [ "$?" = 0 ]; then
	if [ ! "$1" = "" ]; then
	    reportSuccess "$1"
	fi
    else
	reportError "Commit error" "$2"
	$3
	cat /tmp/commit.reason
	# replace all old version files
	restoreOldVersions
	removeOldVersions
	exit 1
    fi
}

assembleCommitErrors() {
    cp /tmp/commit.log /tmp/commit.reason
}

assembleMissing() {
    echo '*** The following files are not in CVS: ***' > /tmp/commit.reason
    echo `grep cvs\ log:\ nothing\ known\ about /tmp/commit.new | awk '{ print $6 }'` >> /tmp/commit.reason
}

assembleCompileErrors() {
    cp /tmp/commit.log /tmp/commit.reason
}

assembleTestErrors() {
    cp /tmp/commit.log /tmp/commit.reason
}

assembleCheckinList() {
    echo '*** The following files are in CVS but missing here: ***'
    grep cvs\ diff:\ cannot\ find /tmp/commit.log | awk '{ print $5}' >> /tmp/commit.reason
}

echo > /tmp/commit.reason

# ====================================================================
#  Perform consistency checks for added and deleted files
# ====================================================================

echo 'Checking for bash mode "virgil"'
test "$BASH_MODE" = "virgil"
checkSuccess 'OK' 'Please convert shell to virgil mode first.' 'echo'

echo 'Checking that all Java files are added to CVS...'
cvs log $JAVA_FILES &> /tmp/commit.new
checkSuccess 'All Java files here are in CVS.' 'There are Java files missing from CVS.' 'assembleMissing'

echo 'Checking that all test files are added to CVS...'
cvs log $TEST_FILES &> /tmp/commit.new
checkSuccess 'All test files here are in CVS.' 'There are test files missing from CVS.' 'assembleMissing'

echo 'Checking that all application files are added to CVS...'
cvs log $APP_FILES &> /tmp/commit.new
checkSuccess 'All application files here are in CVS.' 'There are application files missing from CVS.' 'assembleMissing'

echo 'Checking that all Aeneas files are added to CVS...'
cvs log $AEN_FILES &> /tmp/commit.new
checkSuccess 'All Aeneas files here are in CVS.' 'There are Aeneas files missing from CVS.' 'assembleMissing'

echo 'Detecting changes to repository...'
cvs diff &> /tmp/commit.log
if `test "$?" = 0`; then
    reportSuccess "No changes to commit."
    exit 0
else
    reportSuccess "Changes detected."

    echo 'Searching for missing files...'
    test `grep cvs\ diff:\ cannot\ find /tmp/commit.log | wc -l` = 0
    checkSuccess 'No files are missing from CVS.' 'Files are missing here that are in CVS.' 'assembleCheckinList'
fi


# ====================================================================
#  Check changes to compiler source modules
# ====================================================================
cd compiler
echo "Removing all class files with \"make clean\""
make clean &> /tmp/commit.log
checkSuccess 'make clean successful.' 'make clean failed.' 'assembleCommitErrors'

for m in $MODULES; do
    echo "Checking compiler module $m {"
    MODULE_FILES=`find src/$m -name '*.java'`

    cvs diff src/$m &> /tmp/commit.log
    if `test "$?" = 0`; then
	reportSuccess "No changes to commit."
    else

	VERSION_JAVA=src/$m/Version.java

	echo "  -> Changes detected."
	test -e $VERSION_JAVA
	checkSuccess '' 'Version.java does not exist' 'echo'

	cp $VERSION_JAVA /tmp/${m}Version.java
	if [ "$RELEASE" = 1 ]; then
	    awk '{ if ( $1 == "public" && $4 == "int" && $5 == "commit" ) printf("    public static final int commit = 0;\n"); else print }' /tmp/${m}Version.java > $VERSION_JAVA
	else
	    awk '{ if ( $1 == "public" && $4 == "int" && $5 == "commit" ) printf("    public static final int commit = %d;\n",($7+1)); else print }' /tmp/${m}Version.java > $VERSION_JAVA
	    JAR_NEEDED=1
	fi

	checkSuccess 'Version updated.' 'Version not updated correctly' 'echo'
    fi

    echo "  -> Making $m..."
    make $m &> /tmp/commit.log
    checkSuccess 'Compiled successfully.' 'There were compilation errors building the project.' 'assembleCompileErrors'
    echo "}"
done

echo "Checking Aeneas {"

cvs diff $APP_FILES &> /tmp/commit.log
if `test "$?" = 0`; then
    reportSuccess "No changes to commit."
else

    VERSION_FILE=src/aeneas/Version.v3
    
    echo "  -> Changes detected."
    test -e $VERSION_FILE
    checkSuccess '' "$VERSION_FILE exists" 'echo'
    
    cp $VERSION_FILE /tmp/Version.v3
    if [ "$RELEASE" = 1 ]; then
	awk '{ if ( $1 == "field" && $2 == "commit:" && $3 == "int" ) printf("\tfield commit: int = 0;\n"); else print }' /tmp/Version.3 > $VERSION_FILE
    else
	awk '{ if ( $1 == "field" && $2 == "commit:" && $3 == "int" ) printf("\tfield commit: int = %d;\n",($5+1)); else print }' /tmp/Version.v3 > $VERSION_FILE
	AEN_ZIP_NEEDED=1
    fi
    
    checkSuccess 'Version updated.' 'Version not updated correctly' 'echo'
fi

echo "}"
cd ..

cd apps
echo "Checking apps {"
echo "  -> Making apps..."
make &> /tmp/commit.log
checkSuccess 'Compiled successfully.' 'There were compilation errors building the applications.' 'assembleCompileErrors'
echo "}"

# go back to root directory
cd ..

# ====================================================================
#  Check changes to applications
# ====================================================================

cd apps
echo "Removing all application binaries \"make clean\""
make clean &> /tmp/commit.log
checkSuccess 'make clean successful.' 'make clean failed.' 'assembleCommitErrors'

cd ..

echo "Checking apps {"

cvs diff $APP_FILES &> /tmp/commit.log
if `test "$?" = 0`; then
    reportSuccess "No changes to commit."
else

    VERSION_FILE=apps/version
    
    echo "  -> Changes detected."
    test -e apps/version
    checkSuccess '' 'apps/version exists' 'echo'
    
    cp $VERSION_FILE /tmp/version
    if [ "$RELEASE" = 1 ]; then
	awk '{ printf "%s %s 0", $1, $2  }' /tmp/version > $VERSION_FILE
    else
	awk '{ printf "%s %s %d", $1, $2, ($3+1)  }' /tmp/version > $VERSION_FILE
	ZIP_NEEDED=1
    fi
    
    checkSuccess 'Version updated.' 'Version not updated correctly' 'echo'
fi

cd apps
echo "  -> Making apps..."
make &> /tmp/commit.log
checkSuccess 'Compiled successfully.' 'There were compilation errors building the applications.' 'assembleCompileErrors'
echo "}"

# go back to root directory
cd ..
   
# ====================================================================
#  Run all tests
# ====================================================================

#    ./scripts/runtests.bash
#    checkSuccess 'All tests passed.' 'There were test case failures.' 'assembleTestErrors'

# ====================================================================
#  Attempt to make and archive new jar, if necessary
# ====================================================================
if [ "$JAR_NEEDED" = 1 ]; then

    echo "Checking archive path..."
    if [ ! -d $VPC_ARCHIVE ]; then
	reportError "Archive path $VPC_ARCHIVE does not exist."
	exit 1
    fi

    JARFILE=`./scripts/makejar.bash`
    checkSuccess "JAR built successfully" 'JAR build failed' assembleCommitErrors

    echo "Checking for previous jar..."
    if [ -e "$VPC_ARCHIVE/$JARFILE" ]; then
	reportError "JAR file $VPC_ARCHIVE/$JARFILE already exists."
	restoreOldVersions
	removeOldVersions
	exit 1
    fi
    
fi

# ====================================================================
#  Attempt to make and archive new zip, if necessary
# ====================================================================
if [ "$ZIP_NEEDED" = 1 ]; then

    echo "Checking archive path..."
    if [ ! -d $APP_ARCHIVE ]; then
	reportError "Archive path $APP_ARCHIVE does not exist."
	exit 1
    fi

    echo "Cleaning up application binaries with "make clean"..."
    cd apps && make clean &> /tmp/commit.log
    cd ..
    checkSuccess "make clean successful" 'make clean failed' assembleCommitErrors

    ZIPFILE=`./scripts/makezip.bash`
    checkSuccess "ZIP built successfully" 'ZIP build failed' assembleCommitErrors

    echo "Checking for previous zip..."
    if [ -e "$APP_ARCHIVE/$ZIPFILE" ]; then
	reportError "ZIP file $APP_ARCHIVE/$ZIPFILE already exists."
	restoreOldVersions
	removeOldVersions
	exit 1
    fi
    
fi

# ====================================================================
#  Attempt to make and archive new zip, if necessary
# ====================================================================
if [ "$AEN_ZIP_NEEDED" = 1 ]; then

    echo "Checking archive path..."
    if [ ! -d $AEN_ARCHIVE ]; then
	reportError "Archive path $AEN_ARCHIVE does not exist."
	exit 1
    fi

    AEN_VERSION=`java vpc.Interpreter -language=virgil3 compiler/src/aeneas/*.v3 -version | head -n 1 | cut -d' ' -f2`
    checkSuccess "Aeneas is version $AEN_VERSION" 'Could not get Aeneas Version number' assembleCommitErrors
    AEN_ZIPFILE="aeneas-${AEN_VERSION}.zip"
    cd compiler/src/
    zip -r $ROOTPATH/apps/zips/$AEN_ZIPFILE aeneas > /tmp/commit.log
    checkSuccess "ZIP built successfully" 'ZIP build failed' assembleCommitErrors
    cd ../..

    echo "Checking for previous zip..."
    if [ -e "$AEN_ARCHIVE/$AEN_ZIPFILE" ]; then
	reportError "ZIP file $AEN_ARCHIVE/$AEN_ZIPFILE already exists."
	restoreOldVersions
	removeOldVersions
	exit 1
    fi
    
fi

if [ "$2" = test ]; then
    echo "Commit aborted by user."
    restoreOldVersions
    removeOldVersions
    exit 0
fi

# ====================================================================
#  Attempt to commit code
# ====================================================================
echo Attempting to commit to CVS...
cvs commit -m "$1" &> /tmp/commit.log
checkSuccess 'Commit completed successfully.' 'There were errors committing to CVS.' 'assembleCommitErrors'

if [ "$JAR_NEEDED" = 1 ]; then
    cp compiler/jars/$JARFILE $VPC_ARCHIVE
    checkSuccess "$JARFILE archived successfully" 'JAR copy failed' echo
fi

if [ "$ZIP_NEEDED" = 1 ]; then
    cp apps/zips/$ZIPFILE $APP_ARCHIVE
    checkSuccess "$ZIPFILE archived successfully" 'zip copy failed' echo
fi

if [ "$AEN_ZIP_NEEDED" = 1 ]; then
    cp apps/zips/$AEN_ZIPFILE $AEN_ARCHIVE
    checkSuccess "$AEN_ZIPFILE archived successfully" 'zip copy failed' echo
fi

cat /tmp/commit.log

removeOldVersions
