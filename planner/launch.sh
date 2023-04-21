javac -d classes -cp lib/\*  src/*.java
java -cp classes:lib/\* SATPlanner ../PDDL4J/roommvt/domainRoom.pddl  ../PDDL4J/roommvt/room.pddl > out.txt && vim -R out.txt