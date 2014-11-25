#!/bin/bash

# $1 - application name
# $2 - configuration name
filenames() {
     export ELF_FILE=output/$1-$2.elf
     export C_FILE=output/$1-$2.c
     export OUTFILE=output/$1-$2.avrora
     export VPC_FILE=output/$1-$2.vpc
     export GCC_FILE=output/$1-$2.gcc
}

# $1 - file
# $2 - segment name
getSegment() {
      HEX=0x0`avr-objdump -h $1 | grep "$2" | awk '{ print $3 }'`
      echo `printf "%d" $HEX`
}

# $1 - Application ELF file
# $2 - Avrora output file
getTime() {
      echo `grep Active "$2" | awk '{ print $2 }'`
}

# $1 - Application ELF file
# $2 - Avrora output file
getData() {
      DATA=`getSegment "$1" .data`
      BSS=`getSegment "$1" .bss`

      let TOTAL=DATA+BSS
      echo $TOTAL
}

# $1 - Application ELF file
# $2 - Avrora output file
getCode() {
      echo `getSegment "$1" .text`
}

# $1 - Application ELF file
# $2 - Avrora output file
getStack() {
      STACK_SIZE=`grep Maximum\ stack\ size $2 | awk '{ print $4 }'`
      if [ "$STACK_SIZE" = "" ]; then
	  echo 0
      else
	  echo $STACK_SIZE
      fi
}

# $1 - Virgil files
# $2 - configuration name
compile() {
   ##### run the VPC compiler
   $VPC $VPC_OPTIONS -statistics -config=config.$2 $1 &> $VPC_FILE

   ##### molest sleep routine
   # sed -e 's/0x0055) = 0/0x0055) = 0; asm("sleep")/' output.c > $C_FILE
   
   ##### run avr-gcc
   (time $AVRGCC -o $ELF_FILE $C_FILE &> $GCC_FILE)  >> $GCC_FILE 2>&1
   
   ##### ugly: get the time from the time command
   echo gcc : `grep real $GCC_FILE | cut -dm -f2 | cut -ds -f1` 0 "%" >> $VPC_FILE

   rm output.c
}

# $1 - application name
# $2 - configuration name
display() {
    DATA=`getData "$ELF_FILE" "$OUTFILE"`
    STACK=`getStack "$ELF_FILE" "$OUTFILE"`
    CODE=`getCode "$ELF_FILE" "$OUTFILE"`
    TIME=`getTime "$ELF_FILE" "$OUTFILE"`

    printf "%-15s \t%6d \t%5d  \t%6d  \t%10d \n" "$2" "$DATA" "$STACK" "$CODE" "$TIME"
}

# $1 - elf file
# $2 - out file
runAvrora() {
    $AVRORA $AVRORA_OPTIONS -seconds=$SIM_SECONDS $1 > $2
}

# $1 - application name
# $2 - configuration name
benchmark() {

    bench=$1
    config=$2

    filenames $bench $config
    
    files="`echo $BASEDIR/apps/$1/*.v` $LIBRARIES"
    
    printf "$bench $config: "
    
    printf "compiling..."
    compile "$files" $config
    
    printf "running..."
    runAvrora $ELF_FILE $OUTFILE
    
    printf "done.\n"
}

if [ "$VPC_CLASSPATH" = "" ]; then
	echo Please set the VPC_CLASSPATH env variable.
	exit 1
fi

if [ "$AVRORA_CLASSPATH" = "" ]; then
	echo Please set the AVRORA_CLASSPATH env variable.
	exit 1
fi

METRICS="Data Stack Code Time"
BENCHMARKS="BinaryTree PolyTree BubbleSort Decoder Fannkuch MsgKernel TestRadio TestSPI TestUSART TestADC LinkedList Blink Empty"
CONFIGS="opt0 opt1 opt2 opt3 hlrc hlrcopt1 hlrcopt3 vl vlrc vlrcopt1 vlrcopt3"

CBENCHMARKS="BubbleSort TestADC LinkedList Blink Empty"
CCONFIGS="basegv basenngv"

BASEDIR=/project/titzer/virgil
SIM_SECONDS=20

MICA2=$BASEDIR/lib/mica2
AVR=$BASEDIR/lib/avr
UTIL=$BASEDIR/lib/util

LIBRARIES="$MICA2/Mica2.v $MICA2/LED.v $MICA2/Terminal.v $MICA2/CC1000.v $AVR/Timer0.v $AVR/Port.v $AVR/ADC.v $AVR/SPI.v $AVR/USART.v $AVR/MCU.v $UTIL/Queue.v"

AVRGCC="avr-gcc -O2 -mmcu=atmega128"
AVRORA="java -cp $AVRORA_CLASSPATH avrora.Main"
AVRORA_OPTIONS="-platform=mica2 -monitors=sleep,stack -colors=false"
VPC="java -cp $VPC_CLASSPATH vpc.Compiler"
VPC_OPTIONS="-colors=false -device=$BASEDIR/arch/atmega128.hil -linkage=c:avr"


doRun() {
    if [ "$1" = "" ]; then
	confs=$CONFIGS
    else
	confs="$1"
    fi

    for bench in $BENCHMARKS; do
	for config in $confs; do
	    benchmark $bench $config
	done
    done
}

doDisplay() {
    for bench in $BENCHMARKS; do
	printf " => %-11s \t%6s \t%5s  \t%6s  \t%10s\n" $bench Data Stack Code Time
	for config in $CONFIGS; do
	    filenames $bench $config
	    display $bench $config
	done
    done
}

doMetric() {
    if [ "$1" = "" ]; then
	configs=$CONFIGS
    else
	configs=$*
    fi

    for m in $METRICS; do
	printf "========================================================================\n"
	printf "%s" $m
	for config in $configs; do
	    printf "\t%s" $config
	done
	printf "\n"

	for b in $BENCHMARKS; do
	    printf "%s" $b
	    for config in $configs; do
		filenames $b $config
		printf "\t%d" `get$m $ELF_FILE $OUTFILE`
	    done
	    printf "\n"
	done
    done
}

doCtime() {
    if [ "$1" = "" ]; then
	echo "Usage: benchmark ctime <config>"
	exit 1
    fi

    printf "Compile Time       "
    FIRST=1

    for b in $BENCHMARKS; do
	filenames $b $1

	if [ "$FIRST" = 1 ]; then
	    FIRST=0
	    STAGES=`grep "\%" $VPC_FILE | awk '{ print $1 }'`
	    for s in $STAGES; do
		printf "\t%s" $s
	    done
	    printf "\n"
	fi

	printf "%s" $b
	TIMES=`grep "\%" $VPC_FILE | awk '{ print $3 }'`
	for t in $TIMES; do
	    printf "\t%s" $t
	done
	printf "\n"
    done
}

runCComp() {
    for b in $CBENCHMARKS; do
	
	cbench=c-$b
	vbench=v-$b

	filenames $cbench base
	printf "$cbench..."
	cp $cbench/$cbench.c $C_FILE

	printf "compiling..."
	$AVRGCC -o $ELF_FILE $C_FILE &> $GCC_FILE

	printf "running..."
	runAvrora $ELF_FILE $OUTFILE
	printf "\n"

	for c in $CCONFIGS; do
	    printf "$vbench-$c..."
	    filenames $vbench $c
	    printf "compiling..."
	    compile $cbench/$vbench.v $c
	    printf "running..."
	    runAvrora $ELF_FILE $OUTFILE
	    printf "\n"
	done
    done
}

displayCComp() {
    for m in $METRICS; do
	printf "%s" $m
	printf "\tC"
	for c in $CCONFIGS; do
	    printf "\t%s" $c
	done
	printf "\n"

	for b in $CBENCHMARKS; do
	    
	    cbench=c-$b
	    vbench=v-$b

	    printf "$b"

	    filenames $cbench base
	    printf "\t%d" `get$m $ELF_FILE $OUTFILE`
	    
	    for c in $CCONFIGS; do
		filenames $vbench $c
		printf "\t%d" `get$m $ELF_FILE $OUTFILE`
	    done
	    printf "\n"
	done
	
    done
}

doCComp() {
    if [ "$1" = "run" ]; then
	runCComp
    else
	displayCComp
    fi
}

ARG=$1
shift

if [ "$ARG" = "run" ]; then
    doRun "$*"

elif [ "$ARG" = "display" ]; then
    doDisplay $*

elif [ "$ARG" = "metric" ]; then
    doMetric $*

elif [ "$ARG" = "ctime" ]; then
    doCtime $*

elif [ "$ARG" = "ccomp" ]; then
    doCComp $*

else
    echo "Usage: benchmark <run | display | metric | ctime>"
    exit 1
fi

