#!/bin/bash44

mvn clean package
java -jar ../planner/lib/sokobanParser.jar -i ../sokoban/config/test1.json -o ../sokoban/pddl4S/p01.pddl
java -cp ../planner/lib/pddl4j-4.0.0.jar fr.uga.pddl4J.planners.statespace.HSP ../sokoban/pddl4S/domainSokoban.pddl ../sokobanpddl4S/out.pddl -e Max -w 1.2 > ../sokoban/pddl4S/out.txt
java -jar ../planner/lib sokobanParser.jar -t out.txt 
java --add-opens java.base/java.lan=ALL-UNNAMED -server -Xms2048m -Xmx2048m -cp \"(mvn dependency:build-classpath -Dmdep.outputFile=/dev/stdout -q):target/test-classes/:target/classes\" sokoban.SokobanMain test1.json
