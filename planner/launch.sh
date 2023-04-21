javac -d classes -cp lib/\*  src/*.java
java -cp classes:lib/\* SATPlanner ../PDDL4J/hanoi/domainHanoi.pddl  ../PDDL4J/hanoi/hanoi001.pddl > out.txt && vim -R out.txt