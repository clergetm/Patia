/* Copy-Past from the AStarP example */
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
import fr.uga.pddl4j.util.BitVector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.lang.reflect.Array;
import java.util.ArrayList;

//For the SATEncoding 
import org.sat4j.core.VecInt;
import org.sat4j.maxsat.SolverFactory;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;


/**
 * The class is a SAT planner. Solves a pddl problem using a SAT solver
 *
 * // @author A. Apeloig, M. Clerget, Y. Cheriti
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

    //TODO: //CHOOSE OUR ACTIONS REPRESENTATIONS
    private Map<Integer,Object[]> propToVar; //Map the number of the SAT variables with the proposition (action or predicat at time I)

    public SATPlanner(){
        super();
        propToVar = new HashMap<Integer,Object[]>();
    }


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
        
        for (Action a : pb.getActions()) {
            System.out.println("===============================================");
            System.out.println("Arité: " + a.arity());
            System.out.println("Parametre lenght: "+ a.getParameters().length);
            System.out.println("Nom: " + a.getName());
            BitVector cond = a.getPrecondition().getPositiveFluents();
            for (Action b : pb.getActions()) {
                for (ConditionalEffect ce : b.getConditionalEffects()) {
                    if (ce.getCondition().getPositiveFluents().equals(cond)) {
                        System.out.println("Super ça fonctionne enfin!");
                        System.out.println(b.getName());
                    }
                }
            }
            System.out.println("Precondition" + a.getPrecondition().getNegativeFluents());
            
        }
        return pb;
    }

    //Take an instantiate problem
    //Gonna be use to get in a list: Initial states,  goals and  actions 
    //Then map them to an int (each propositon gonna have a unique int)
    private ArrayList<Integer> propToVar(final Problem problem, int numAction, ArrayList<Integer> previousVar){
        //We transform each proposition with its time associated to a unique integer
        //For example : Move(A,B)_0 = 1, Move(A,B)_1 = 12, etc
        //The unique integer start at 1 because SAT4J does'nt accept 0 as a variable

        int previousStep = 0;
        int varName = 1;

        //if we already have some variable we are implemented the same plans + 1 time step
        if(previousVar.size()>0){
            previousStep = numAction - 1;
            varName = previousVar.get(previousVar.size()-1) + 1; 
        } 

        //i the step number (temporal step)
        for(int i = previousStep; i<numAction; i++){
            //We map our proposition to variables 
            
            TODO:
            //ALL THE WORK OF CREATING VARIABLES IS GONNA BE HERE

            propToVar.put(varName, null); // We map it

            previousVar.add(varName);
            varName++;
        }
        return previousVar;
    }

    //We create each clauses (we don't recreate the older clauses)
    private ArrayList<int[]> createClauses(ArrayList<int[]> previousClauses, String someOtherArgs){

        TODO:
        //ALL THE WORK OF CREATING CLAUSES IS GONNA BE HERE
        
        return previousClauses;
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
        //We create the plan 
        Plan solvedPlan = new SequentialPlan();

        //We create the solver
        ISolver solver = SolverFactory.newDefault();

        //With the graph plan we find the minimum number of actions 
        int numAction = 0; //problem.

        boolean solved = false;

        ArrayList<Integer> variables = new ArrayList<>();
        ArrayList<int[]> clauses = new ArrayList<>();

        //For the time lmit
        boolean timeExceed = false;

        try{ 
            while(!solved){
                //If time exceed and we still don't have a plan we stop and say plan not found
                if(timeExceed)
                    throw new TimeoutException();

                //Else we try to solve the problem :
                
                variables = propToVar(problem, numAction, variables);
                clauses = createClauses(clauses, "otherArgs");
                
                int numVar = variables.size();
                // prepare the solver to accept numVar variables. MANDATORY for MAXSAT solving
                solver.newVar(numVar);
                
                int numClause = clauses.size();
                // prepare the solver to accept numClause clauses. MANDATORY for MAXSAT solving
                solver.setExpectedNumberOfClauses(numClause);

                // Feed the solver using Dimacs format, using arrays of int
                // (best option to avoid dependencies on SAT4J IVecInt)
                for (int i=0;i<numClause;i++) {
                    int [] clause = clauses.get(i); // get the clause from clauses
                    // the clause should not contain a 0, only integer (positive or negative)
                    // with absolute values less or equal to MAXVAR
                    // e.g. int [] clause = {1, -3, 7}; is fine
                    // while int [] clause = {1, -3, 7, 0}; is not fine 
                    try {
                        solver.addClause(new VecInt(clause)); // adapt Array to IVecInt
                    } catch (ContradictionException e) {
                        e.printStackTrace();
                    } 
                }

                // we are done. Working now on the IProblem interface
                IProblem problemSAT = solver;
                try {
                    if (problemSAT.isSatisfiable()) {
                        //The problem is solvable
                        solved=true;

                        //SAT4J gives us the model : the list of variables that are true to solve the problem 
                        int[] model = problemSAT.findModel();

                        TODO:// WHICH TYPE THE ACTIONS (WITH TIME) GONNA BE ?

                        //We transform our variables to temporal propositions
                        //List<Actions(with time related step)> tempActions
                        for(int v : model){
                            //We take back in our map the actions related to v and add them to the list
                        }
                        
                        //We had each TemporalActions to our plan

                    } else {
                        //No plan can be found we increase the total number of actions of the plan
                        numAction++;
                        }
                } catch (TimeoutException e) {
                    e.printStackTrace();
                    return null; //This Timeout is the one of the SATSolver : If it reach its limit, no plan solution has been found
                }
            }
        } catch (TimeoutException e) {
            e.printStackTrace();
            return null; //No plan was found in the restricted time
        }
        
        //We find a plan without Time Exceed
        return solvedPlan;
    }

    /**
     * The main method of the <code>ASP</code> planner.
     *
     * @param args the arguments of the command line.
     */
    public static void main(String[] args) {
        try {
            final SATPlanner planner = new SATPlanner();
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