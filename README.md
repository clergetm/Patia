<div id="top"></div>

<!-- TITLE -->
<div align="center">
<h1 align="center">Patia</h1>

  <p align="center">
    Repo of exercices, planner and parser in PATIA courses
  </p>
</div>


<!-- ABOUT THE PROJECT -->

## About The Project
This project is separated in four parts:
 1. [**PDDL4J**][pddl-md] : The *PDDL4J* exercices, domain and problem.
 2. [**parser**][parser-md] : The Java *Parser* that create a *PDDL* problem file from a *JSON* configuration file.
 3. [**planner**][planner-md]: The *SAT planner* using [SAT4J][sat-url].
 4. [**sokoban**][sokoban-md]: The *sokoban* [fork][sokoban-fork] modified to be used with our **parser**.

### Sokoban script
There is a *script* named `launch.sh` in this reposity used for automatically
 1. **Create** a *PDDL* file from a *JSON* configuration file
 2. **Plan** this *PDDL* problem using *PDDL4J* planner and write the output in **out.txt**.
 3. **Run** the server with this configuration file and the solution found in **out.txt**.
 
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
