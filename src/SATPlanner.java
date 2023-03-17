/* Copy-Past from the ASP example */
//Comments inf form // are mines 
import fr.uga.pddl4j.heuristics.state.StateHeuristic;
import fr.uga.pddl4j.parser.DefaultParsedProblem;
import fr.uga.pddl4j.parser.RequireKey;
import fr.uga.pddl4j.plan.Plan;
import fr.uga.pddl4j.plan.SequentialPlan;
import fr.uga.pddl4j.planners.AbstractPlanner;
import fr.uga.pddl4j.planners.Planner;
import fr.uga.pddl4j.planners.PlannerConfiguration;
import fr.uga.pddl4j.planners.ProblemNotSupportedException;
import fr.uga.pddl4j.planners.SearchStrategy;
import fr.uga.pddl4j.planners.statespace.search.StateSpaceSearch;
import fr.uga.pddl4j.problem.DefaultProblem;
import fr.uga.pddl4j.problem.Problem;
import fr.uga.pddl4j.problem.State;
import fr.uga.pddl4j.problem.operator.Action;
import fr.uga.pddl4j.problem.operator.ConditionalEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.lang.reflect.Array;
//For the SATEncoding 
import java.util.ArrayList;

import org.sat4j.maxsat.SolverFactory;
import org.sat4j.specs.ISolver;


/**
 * The class is a SAT planner. Solves a pddl problem using a SAT solver
 *
 * @author A. Apeloig, M. Clerget, Y. Cheriti
 */
@CommandLine.Command(name = "SAT",
    version = "SAT 1.0",
    description = "Solves a specified planning problem using a SAT encoding.")//,
    // sortOptions = false,
    // mixinStandardHelpOptions = true,
    // headerHeading = "Usage:%n",
    // synopsisHeading = "%n",
    // descriptionHeading = "%nDescription:%n%n",
    // parameterListHeading = "%nParameters:%n",
    // optionListHeading = "%nOptions:%n")

public class SATPlanner extends AbstractPlanner {

    //MAXVAR && NBCLAUSES for our SAT solver
    final int MAXVAR = 1000000;
    final int NBCLAUSES = 500000;


    /**
     * The class logger.
     */
    private static final Logger LOGGER = LogManager.getLogger(SATPlanner.class.getName());

    /**
     * Instantiates the planning problem from a parsed problem.
     *
     * @param problem the problem to instantiate.
     * @return the instantiated planning problem or null if the problem cannot be instantiated.
     */
    @Override
    public Problem instantiate(DefaultParsedProblem problem) {
        final Problem pb = new DefaultProblem(problem);
        pb.instantiate(); //here we instantiate our problem (we ground it).
        return pb;
    }

    //Take an instantiate problem
    //Gonna be use to get in a list: Initial states,  goals and  actions 
    //Then map them to an int (each propositon gonna have a unique int)
    private ArrayList<Integer> propToVar(final Problem problem){
        //En simple on prend tous les problem.getActions, etc et on les map chacun à un int donné
        return null;
    }

    //Plein de pseudo code je reprends ça vendredi
    // /* As in tutorial "https://sat4j.gitbooks.io/case-studies/content/using-sat4j-as-a-java-library.html"
    //  * Writing a SAT problem and solve it with clauses
    //  */

    /**
     * Take an instantiate problem, translate each proposition in variable
     *  and create each clauses with the different variables
     *
     * @param problem the problem to solve.
     * @return the plan found or null if no plan was found.
     */
    @Override
    public Plan solve(final Problem problem) {
        ISolver solver = SolverFactory.newDefault();

        // prepare the solver to accept MAXVAR variables. MANDATORY for MAXSAT solving
        solver.newVar(MAXVAR);
        solver.setExpectedNumberOfClauses(NBCLAUSES);
        // Feed the solver using Dimacs format, using arrays of int
        // (best option to avoid dependencies on SAT4J IVecInt)
        for (int i=0;<NBCLAUSES;i++) {
        int [] clause = // get the clause from somewhere
        // the clause should not contain a 0, only integer (positive or negative)
        // with absolute values less or equal to MAXVAR
        // e.g. int [] clause = {1, -3, 7}; is fine
        // while int [] clause = {1, -3, 7, 0}; is not fine 
        solver.addClause(new VecInt(clause)); // adapt Array to IVecInt
        }

        // we are done. Working now on the IProblem interface
        IProblem problem = solver;
        if (problem.isSatisfiable()) {
        ....
        } else {
        ...
        }
        }
    }

    /**
     * The main method of the <code>ASP</code> planner.
     *
     * @param args the arguments of the command line.
     */
    public static void main(String[] args) {
        try {
            final ASP planner = new ASP();
            CommandLine cmd = new CommandLine(planner);
            cmd.execute(args);
        } catch (IllegalArgumentException e) {
            LOGGER.fatal(e.getMessage());
        }
    }

    @Override
    public boolean isSupported(Problem arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isSupported'");
    }
}