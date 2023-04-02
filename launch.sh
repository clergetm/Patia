#!/usr/bin/env bash

# Path variables
path_parser="parser"
path_planner="planner"
path_sokoban="sokoban"

# Lib path variables
path_lib="$path_planner/lib"
lib_pddl="$path_lib/pddl4j-4.0.0.jar"
lib_sokoban="$path_lib/sokobanParser.jar"

# Java Variables
Xm="1G"

# Parser Variables
json_file="test1"

# Planner Variables
planner="HSP"
logLevel="INFO"
timeout="600"
weight="1.0"
heuristic="FAST_FORWARD"
strategy="ASTAR"

# Sokoban Variables
folder="$path_sokoban/pddl4S"
domain="$path_sokoban/pddl4S/domainSokoban.pddl"
problem="$path_sokoban/pddl4S/out.pddl"
text="$path_sokoban/pddl4S/out.txt"

# Write in the console a long line
function echoLine() {
    echo "====================================================================="
}

# Write in the console the planner used
function echoPlanner() {
    echoLine
    echo "RUN $planner PLANNER"
    echoLine
}

# create and compile the jar used in this project
function configuration() {
    createJar
    prepareSokobanEnv
}

# Create the Sokoban Jar in Parser folder
function createJar() {
    pushd $path_parser # Go to Parser folder
    mvn clean package
    popd # Go back to Patia parser
}

# mvn install jar in Sokoban folder
function prepareSokobanEnv() {
    pushd $path_sokoban # Go to Sokoban folder
    mvn clean
    mvn install:install-file \
        -Dfile=../$lib_pddl \
        -DgroupId=fr.uga \
        -DartifactId=pddl4j \
        -Dversion=4.0.0 \
        -Dpackaging=jar \
        -DgeneratePom=true

    mvn install:install-file \
        -Dfile=../$lib_sokoban \
        -DgroupId=fr.uga.pddl4j.sokoban \
        -DartifactId=parser \
        -Dversion=1.0 \
        -Dpackaging=jar \
        -DgeneratePom=true

    mvn compile
    popd # Go back to Patia parser
}

# Run the codingame Sokoban server
function runServer() {
    echoLine
    echo "RUN SERVER"
    echoLine
    pushd $path_sokoban # Go to sokoban folder
    java --add-opens \
        java.base/java.lang=ALL-UNNAMED \
        -server \
        -Xms2048m \
        -Xmx2048m \
        -cp \
        "$(mvn dependency:build-classpath -Dmdep.outputFile=/dev/stdout -q):target/test-classes/:target/classes" \
        sokoban.SokobanMain \
        "$json_file.json"
    popd # Go back to Patia parser
}

# Parse the json file into PDDL File
function createPDDLFile() {
    echoLine
    echo "CREATE PDDL FILE FROM $json_file.json"
    echoLine
    java -jar $lib_sokoban \
        -i $path_sokoban/config/$json_file.json \
        -o $path_sokoban/pddl4S
}

# Run FF Planner and write the output in out.txt
function FF() {
    java \
        -Xms$Xm \
        -Xmx$Xm \
        -cp $lib_pddl \
        fr.uga.pddl4j.planners.statespace.FF \
        $domain \
        $problem \
        --log=$logLevel \
        --timeout=$timeout \
        --weight=$weight \
        >$text
}

# Run HSP Planner and write the output in out.txt
function HSP() {
    java \
        -Xms$Xm \
        -Xmx$Xm \
        -cp $lib_pddl \
        fr.uga.pddl4j.planners.statespace.HSP \
        $domain \
        $problem \
        --heuristic=$heuristic \
        --log=$logLevel \
        --timeout=$timeout \
        --weight=$weight \
        >$text
}

# Run GSP Planner and write the output in out.txt
function GSP() {
    java \
        -Xms$Xm \
        -Xmx$Xm \
        -cp $lib_pddl \
        fr.uga.pddl4j.planners.statespace.GSP \
        $domain \
        $problem \
        --heuristic=$heuristic \
        --log=$logLevel \
        --timeout=$timeout \
        --weight=$weight \
        --search-strategies=$strategy \
        >$text
}

# Launch the specific planner
function launchPlanner() {
    echoPlanner
    echo "logLevel:     $logLevel"
    echo "timeout:      $timeout"
    echo "weight:       $weight"
    if [[ $planner =~ ^FF$ ]]; then
        echoLine
        FF
    else
        echo "heuristic:    $heuristic"
        if [[ $planner =~ ^HSP$ ]]; then
            echoLine
            HSP
        else
            [[ $planner =~ ^GSP$ ]]
            echo "strategy:     $strategy"
            echoLine
            GSP
        fi
    fi
}

# Launch the whole process
function launch() {
    # 0. remove out files (This step is optionnal)
    rm -rf $problem $text

    # 1. Create PDDL File
    createPDDLFile

    # 2. Plan PDDL file
    launchPlanner

    # 3, run Server with json file and txt output
    runServer

    clear
}

# Show options informations
function help() {
    echoLine
    echo "Help page for launch.sh"
    echo "Options:"
    echo "   -h       Show this help message and exit."
    echo
    echo "   -c       Configure workspace and exit"
    echo "              ! WARNING: You must configure the workspace"
    echo "                to avoid error when executing this script"
    echo
    echo "   -i       Set the name of the json configuration file"
    echo "              ! This file must be in $path_sokoban/config "
    echo "                (Preset to test1)"
    echo
    echo "   -p       Set the type of planner: FF, HSP, GSP "
    echo "                (Preset to HSP)"
    echo
    # echo "-l        Set the level of trace of the planner: ALL, DEBUG,"
    # echo "          INFO, ERROR, FATAL, OFF, TRACE (preset INFO)."
    # echo
    echo "   -t       Set the time out of the planner in seconds (preset 600s)."
    echo
    echo "   -w       Set the weight of the heuristic (preset 1.0)."
    echo
    echo "   -e       Set the heuristic : AJUSTED_SUM, AJUSTED_SUM2,"
    echo "                AJUSTED_SUM2M, COMBO, MAX, FAST_FORWARD,"
    echo "                SET_LEVEL, SUM, SUM_MUTEX (preset FAST_FORWARD)"
    echo "             ! WARNING Not used when planner is set to FF"
    echo
    echo "   -s       Set the search strategies: ASTAR,"
    echo "                ENFORCED_HILL_CLIMBING, BREADTH_FIRST,"
    echo "                GREEDY_BEST_FIRST, DEPTH_FIRST, HILL_CLIMBING"
    echo "                (preset: ASTAR) "
    echo "             ! Only used when planner is set to GSP"
    echo
    echo "Examples:"
    echo "   bash ./launch.sh -c"
    echo
    echo "   bash ./launch.sh -i test10 -p HSP -t 1000 -e MAX -w 1.2"
    echo
    echo "   bash ./launch.sh -i test15 -p GSP -t 1000 -e COMBO -s BREADTH_FIRST"
    echo
    echoLine
}

##################################
# Get the options
while getopts "hci:p:t:w:e:s:" option; do
    case $option in
    h) # display Help
        help
        exit
        ;;
    c) # Jar compilation and installation
        configuration
        exit
        ;;
    i) # JSON File name
        json_file=$OPTARG ;;
    p) # Planner
        planner=$OPTARG ;;
    t) # Timeout
        timeout=$OPTARG ;;
    w) # Weight
        weight=$OPTARG ;;
    e) # Heuristic
        heuristic=$OPTARG ;;
    s) # Strategy
        strategy=$OPTARG ;;
    \?) # Invalid option
        echo "Invalid option: -$OPTARG" >&2
        exit
        ;;
    esac
done

launch
