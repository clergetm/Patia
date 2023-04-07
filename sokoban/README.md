<div id="top"></div>

<!-- TITLE -->
<div align="center">
<h1 align="center">Sokoban</h1>

  <p align="center">
    Repo of exercices, planner and parser in PATIA courses
  </p>
</div>


<!-- ABOUT THE PROJECT -->

## About The Project
This is a *modified copy* of this Sokoban [Repository][sokoban-fork].
To resolve a sokoban game, this project need two thing:
 1. The *configuration file* in [SokobanMain][main-java] to deploy the map, the guard, boxes etc...
 2. The *solution* in [Agent][agent-java] to move the guard and the boxes on the map.

In the original project, this information was directly written in the classes and had to be **modified each time** we wanted to try another configuration.
This information are now more *"dynamic"*:
 1. The *configuration file* is passed as the one and only argument when starting the server in the *CLI*.
 2. The *solution* is automatically given by the `translate(String fileName)` [method][translate-method] from the **Parser**.

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- ACKNOWLEDGMENTS -->
## Prerequisites
### Maven
- First, Maven is **needed**. You’ll use it to compile and install the jar used in this project.
   Then, you’ll need to install two jar:
   1. You can install the jar using the *CLI*.
       1. PDDL4J [jar][pddl-jar]

          Install pddl4j (https://github.com/pellierd/pddl4j) in your local maven repo:
          ```sh
          mvn install:install-file \
             -Dfile=../planner/lib/pddl4j-4.0.0.jar \
             -DgroupId=fr.uga \
             -DartifactId=pddl4j \
             -Dversion=4.0.0 \
             -Dpackaging=jar \
             -DgeneratePom=true
           ```  
           Work with maven: mvn clean, mvn compile, mvn test, mvn package

       2. The SokobanParser [jar][sokoban-jar]

          Install sokobanParser in your local maven repo:
          ```sh
          mvn install:install-file \
              -Dfile=../planner/lib/sokobanParser.jar \
              -DgroupId=fr.uga.pddl4j.sokoban \
              -DartifactId=parser \
              -Dversion=1.0 \
              -Dpackaging=jar \
              -DgeneratePom=true
          ```
          Work with maven: mvn clean, mvn package
   2. You can install the jar using *Visual Studio Code task*: `Maven configuration`
      - `Ctrl+Shift+B` and type `Maven Configuration` then press `Enter`.
      - `Ctrl+Shift+P`, type `Tasks, Run Task` and type `Maven Configuration` then press `Enter`.
      
### Sokoban Parser
Using the Sokoban Parser, you will create two files:
 - **out.pddl:** the *JSON* file parsed as a *PDDL* problem.
 - **out.txt :** the plan of the *PDDL* problem written. 
 
 The file **out.txt** is needed before running the server, or no solution will be used by the *Agent*.
<p align="right">(<a href="#top">back to top</a>)</p>

## Run
### Run using command line

Run with: 
````
java --add-opens java.base/java.lang=ALL-UNNAMED \
      -server -Xms2048m -Xmx2048m \
      -cp "$(mvn dependency:build-classpath -Dmdep.outputFile=/dev/stdout -q):target/test-classes/:target/classes" \
      sokoban.SokobanMain <name_of_the_configuration_file>.json
````
or (after mvn package)
```
java --add-opens java.base/java.lang=ALL-UNNAMED \
      -server -Xms2048m -Xmx2048m \
      -cp target/sokoban-1.0-SNAPSHOT-jar-with-dependencies.jar \
      sokoban.SokobanMain <name_of_the_configuration_file>.json
```

See planning solutions at http://localhost:8888/test.html

### Run using Visual Studio Code
We implemented a lot of `tasks` using *Visual Studio Code tasks configuration file* and you can find all these tasks [here][VSCode-tasks].
You can: 
 1. Configure the workspace using `Maven configuration`.
 2. Run the Sokoban server using `Run Server`.
    *VSCode* will ask you to input a json file name before running the server.
 3. Run PDDL4J Planner using `FF`, `HSP` or `GSP` planner.
    There are two version of `tasks` for each planner
    1. Default tasks ( i.e: `Default FF Planner`): with default parameters.
       
    2. Custom tasks ( i.e: `Custom FF Planner`): *VSCode* will ask you to input a lot of options.
       These options can be find in the PDDL4J [documentation][pddl-doc].


<p align="right">(<a href="#top">back to top</a>)</p>

## Acknowledgments

- [Markdown Cheatsheet][md-url]
- [README.MD Template][readme-url]
<p align="right">(<a href="#top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[md-url]: https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet
[readme-url]: https://github.com/othneildrew/Best-README-Template
[sokoban-fork]: https://github.com/fiorinoh/sokoban
[main-java]: https://github.com/MathysC/Patia/blob/main/sokoban/src/main/java/sokoban/SokobanMain.java
[agent-java]: https://github.com/MathysC/Patia/blob/main/sokoban/src/main/java/sokoban/Agent.java
[translate-method]: https://github.com/MathysC/Patia/blob/main/parser/src/main/java/fr/uga/pddl4j/sokoban/SokobanParser.java#L394
[pddl-jar]: http://pddl4j.imag.fr/download.html?highlight=download
[sokoban-jar]: https://github.com/MathysC/Patia/tree/main/planner/lib
[VSCode-tasks]: https://github.com/MathysC/Patia/blob/main/sokoban/.vscode/tasks.json
[pddl-doc]: http://pddl4j.imag.fr/running_planners_from_command_line.html
