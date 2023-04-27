javac -d classes -cp lib/\*  src/*.java
java -cp classes:lib/\* SATPlanner ../PDDL4J/graph-color/domainGraphColor.pddl ../PDDL4J/graph-color/graphColor001.pddl > out.txt 