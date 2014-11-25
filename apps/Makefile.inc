#-------------------------------------------------------------
# Makefile for applications
#-------------------------------------------------------------

LIB=../../lib

%.elf:%.v
	java -cp ../../compiler/bin vpc.Interpreter -seconds=5 -stats -config=$(CONFIG) $< $(LIBRARIES)
	mv output.elf $@
