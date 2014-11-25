#
# Makefile for virgil source code and documentation
# --> Probably the dumbest makefile ever.
#

all: vpc

cck:
	javac -source 1.4 -d bin `find src/cck -name '*.java'`

vpc:
	javac -source 1.5 -d bin `find src/vpc src/aeneas src/cck -name '*.java'`

debug:
	javac -g -source 1.5 -d bin `find src/vpc src/aeneas src/cck -name '*.java'`

clean:
	rm -rf bin/cck bin/vpc bin/aeneas doc/*.html doc/cck doc/vpc
	rm -rf `find . -name '*.o'`
	rm -rf `find . -name a.out`
	rm -rf `find . -name output.c`
	rm -f jars/*.jar
	rm -f ../test/tmp/*.c ../test/tmp/*.elf
	rm -f ../test/v3/execute/bin/*.class
	rm -f src/aeneas/bin/*.class

doc: doc/index.html

doc/index.html:
	javadoc -breakiterator -sourcepath src -d doc `find src/vpc -name '*.java'`
