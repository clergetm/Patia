<div id="top"></div>

<!-- TITLE -->
<div align="center">
<h1 align="center">Planner</h1>

  <p align="center">
    This is our SAT planner
  </p>
</div>


<!-- ABOUT THE PROJECT -->

## About The Project
This project is created by :
 1. Hugo Apeloig:    [profile](https://github.com/hugoapeloig)
 2. Yazid Cheriti:   [profile](https://github.com/maleusa)
 3. Mathys Clerget:  [profile](https://github.com/mathysc)
 
 

### How to run
To launch the test plan of our satplanner simply launch the script bash `testplanSAT` that you can find in this folder.
However you must have the repository of [**PDDL4J**][pddl4j] located in ../../pddl4J regarding this folder.
### Implementation
The idea of the planner is the following, using the 'instantiate' method provided by PDDL4J, we get a grounded problem which allows us using methods like .getActions() to retrieve our different proposals. We then map each of the propositions to a unique integer. Then we give each of the clauses the form {1, 2, -3} which means for example that either the prepositions 1 or 2 are true or 3 is false. We then add our clauses to the SAT4J solver which then solves all the clauses and returns a list of propositions that must be true (a stable model). 
Our propositions are encoded in the form of actions for a time T. Indeed the action : move(a,b) at time 0 is different from move(a,b) at time 2. In this way the integer 1 and the integer 2 can represent the same action but at a different time T. Constraints will therefore be added to ensure that only one and only one action can be performed for each T=i. In this way, the result given by SAT4J will be a list of actions linked each for a different time T and we can transform it into a plan: Action a1 at T=0, Action a2 at T=1, etc.
Once reconstructed, the plan is transformed back into a textual plan (from a1, a3, to : moove(a,b), place(c,d)), then displayed to the user of our planner.
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
[pddl4j]:https://github.com/pellierd/pddl4j
