# URL Link

```bash
mvn install:install-file \
   -Dfile=<path-to-file-pddl4j-4.0.0.jar> \
   -DgroupId=fr.uga \
   -DartifactId=pddl4j \
   -Dversion=4.0.0 \
   -Dpackaging=jar \
   -DgeneratePom=true
```  

```bash
mvn install:install-file \
   -Dfile=<path-to-file-sokobanParser.jar> \
   -DgroupId=fr.uga.pddl4j.sokoban \
   -DartifactId=parser \
   -Dversion=1.0 \
   -Dpackaging=jar \
   -DgeneratePom=true
 ```  


 ```bash
  mvn compile
 ```


```java
java --add-opens java.base/java.lang=ALL-UNNAMED \
      -server -Xms2048m -Xmx2048m \
      -cp "$(mvn dependency:build-classpath -Dmdep.outputFile=/dev/stdout -q):target/test-classes/:target/classes" \
      sokoban.SokobanMain
```

http://localhost:8888/test.html


```sh
java -cp pddl4j-4.0.0.jar fr.uga.pddl4j.planners.statespace.FF  pddl4S/domainSokoban.pddl pddl4S/p01.pddl
```