<div id="top"></div>

<!-- TITLE -->
<div align="center">
<h1 align="center">Parser</h1>

  <p align="center">
    This folder is the repository of our work on the parser part of the project
  </p>
</div>


<!-- ABOUT THE PARSER -->

## PARSER documentation
This parser is created by :
 1. Hugo Apeloig:    [profile](https://github.com/hugoapeloig)
 2. Yazid Cheriti:   [profile](https://github.com/maleusa)
 3. Mathys Clerget:  [profile](https://github.com/mathysc)
### Synopsis
1. `public SokobanParser()`
2. `private boolean initialize()`
3. `public void createObjects()`
4. `public void createInit()`
5. `public void createGoal()`
6. `public void createPDDL()`
7. `public static String translate()`

<p align="right">(<a href="#top">back to top</a>)</p>


### Description
  This classe is used to transform a sokoban level described in a *JSON* configuration file into a *PDDL* problem to be used with [**PDDL4J**][pddl-md], *public SokobanParser(String jsonFilePath,String pddlPath)* or *private boolean initialize(String jsonFilePath, String pddlFilePath)* can be used to indicate the *JSON* filepath and the output filepath. 
  The methods *public void createObjects()*, *public void createInit()*, *public void createGoal()*, *public void createPDDL()* are where the majority of parsing and writing work are done and they are each responsible for a part of the completion of the *PDDL* file.
  The method *public static String translate(String filename)* will take the output of [**PDDL4J**][pddl-md] on the current sokoban problem and transforms it a string of characters usable by the [**SOKOBAN GAME**][sokoban-md] (format : "RLUD..." with R : right, L : left, U: up, D : down).
<p align="right">(<a href="#top">back to top</a>)</p>


### USAGE
There is a *script* named `launch.sh` in this reposity that will automatically
 1. **Create** a *PDDL* file from a *JSON* configuration file
 2. **Plan** this *PDDL* problem using *PDDL4J* planner and write the output in **out.txt**.
 3. **Run** the server with this configuration file and the solution found in **out.txt**.
 
    When using this script please make sure to run it first with `launch.sh -c` to configure your environnement, you can also use `launch.sh -h` to display the multiple options of the bash script.
<p align="right">(<a href="#top">back to top</a>)</p>

## QUICK NAVIGATION

 1. [**PDDL4J**][pddl-md] : The *PDDL4J* exercices, domain and problem.
 2. [**parser**][parser-md] : The Java *Parser* that create a *PDDL* problem file from a *JSON* configuration file.
 3. [**planner**][planner-md]: The *SAT planner* using [SAT4J][sat-url].
 4. [**sokoban**][sokoban-md]: The *sokoban* [fork][sokoban-fork] modified to be used with our **parser**.
<p align="right">(<a href="#top">back to top</a>)</p>


<!-- ACKNOWLEDGMENTS -->

## Acknowledgments

- [Markdown Cheatsheet][md-url]
- [README.MD Template][readme-url]
<p align="right">(<a href="#top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[md-url]: https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet
[readme-url]: https://github.com/othneildrew/Best-README-Template
[sat-url]: https://www.sat4j.org/doc.php
[sokoban-fork]: https://github.com/fiorinoh/sokoban
[pddl-md]: https://github.com/MathysC/Patia/blob/main/PDDL4J/README.md
[parser-md]: https://github.com/MathysC/Patia/blob/main/parser/README.md
[planner-md]: https://github.com/MathysC/Patia/blob/main/planner/README.md
[sokoban-md]: https://github.com/MathysC/Patia/blob/main/sokoban/README.md
