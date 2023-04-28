#!/bin/bash

XM=256m
VERSION=4.0.0
COMMANDTIMEOUT=300

domain_name="domain"
problem_name=""
complete_path=""

# path
#  The base path of benchmarks.
path="../../pddl4j/src/test/resources/benchmarks/pddl"

# ipc_folders
#  The array of ipc’s folders where the benchmark at the same index are.
#  The size of this array must be the same as "benchmarks" and "benchmarks_types".
ipc_folders=("ipc2000" "ipc2002" "ipc1998" "ipc1998")

# benchmarks
#  The array of benchmark’s folders we want to use.
#  The size of this array must be the same as "ipc_folders" and "benchmarks_types".
benchmarks=("blocks" "depots" "gripper" "logistics")

# benchmark_types
#  The array of types of problem (of the benchmark at the same index) we want to plan.
#  The size of this array must be the same as "ipc_folders" and "benchmarks".
benchmark_types=("strips-typed" "strips-automatic" "strips" "strips-round1")

# index_folder
#  Variable used to navigate between folders
#  MUST NOT be changed
index_folder=0

# index_file
#  SHOULD NOT be changed unless the first problem file start at 0
#  Example :p000.pddl
index_file=1

# Configurate the output folders
function configuration() {
    echo "### Remove and create output folders"
    echo
    for (($((index_folder = 0)); $index_folder < ${#benchmarks[@]}; $((index_folder++)))); do
        echo "# ${benchmarks[index_folder]} output folder"
        rm -rf "${benchmarks[index_folder]}"
        mkdir "${benchmarks[index_folder]}"
    done
    echo
    echo "### Compile SAT classes"
    javac -d classes -cp lib/\*  src/*.java
    echo "## Done"
    echo
    echo "### Configuration successful"
    echo
}

# Run all benchmarks and solve each problem using SAT planner
function run_benchmarks() {
    for (($((index_folder = 0)); $index_folder < ${#benchmarks[@]}; $((index_folder++)))); do
        # Create the right path to use for this loop
        echo "Find the number of pddl problem in the folder:"
        complete_path="$path/${ipc_folders[index_folder]}/${benchmarks[index_folder]}/${benchmark_types[index_folder]}"
        pushd $complete_path
        pddl_files=($(printf "%s " p*.pddl))
        popd
        # Execute SAT Planifications
        nbfiles=${#pddl_files[@]}
        echo "### Execute ${nbfiles} SAT planifications on ${benchmarks[index_folder]}"
        echo
        for (($((index_file = 0)); $index_file <= 20; $((index_file++)))); do
        # for (($((index_file = 0)); $index_file <= $((${nbfiles}-1)); $((index_file++)))); do
            problem_name=${pddl_files[index_file]}
            # Solve using SAT Planner
            solveSAT
        done
        echo
    done
}

# Run SAT Planner
function solveSAT() {
    echo "# Plan ${problem_name}"
    timeout $COMMANDTIMEOUT \
        java -cp classes:lib/\* \
            SATPlanner \
            ${complete_path}/${domain_name}.pddl \
            ${complete_path}/${problem_name} \
            >${benchmarks[index_folder]}/${benchmarks[index_folder]}_$((${index_file}+1)).txt

}
# MUST BE RUN FROM PDDL4J/
# bash ./src/test/resources/validators/testPlan.sh
configuration
run_benchmarks
exit 0