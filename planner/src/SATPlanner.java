/* Copy-Past from the AStarP example */
//Comments inf form // are mines 
import fr.uga.pddl4j.heuristics.state.FastForward;
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
import fr.uga.pddl4j.problem.Fluent;
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
    //Then map them to an int (each propositon gonna have a unique int that is the index)
    //numAction is the time step
    private ArrayList<SATVariable> propToVar(ArrayList<Fluent> fluents, ArrayList<Action> actions, int numAction, ArrayList<SATVariable> previousVar){
        //We transform each proposition with its time associated to a unique integer
        //For example : Move(A,B)_0 = 1, Move(A,B)_1 = 12, etc
        //The unique integer start at 1 because SAT4J does'nt accept 0 as a variable

        int previousStep = 0;
        int varName = 1;

        //if we already have some variable we are implemented the same plans + 1 time step
        if(previousVar.size()>0){
            previousStep = numAction - 1;
            varName = previousVar.size(); 
        } 

        //i the step number (temporal step)
        for(int i = previousStep; i<numAction; i++){

            //Gonna be use to calculate precond and effects
            //Relative index of fluents to our time step i
            //If we add : fluents.size()+actions.size() we obtains relative index for i+1
            int[] fVarName = new int[fluents.size()];


            //We transform our propositions to SATVariables
            for (int fI=0; fI<fluents.size();fI++) {
                SATVariable p = new SATVariable(i, varName, true);
                fVarName[fI] = varName;
                varName++;
                previousVar.add(p);
            }


            //We transform our actions to SATVariables
            for (Action a : actions) {
                SATVariable aVar = new SATVariable(i, varName, false);

                //We add our precond to our actions (We only have positive preconditions)
                BitVector pre = a.getPrecondition().getPositiveFluents();
                for(int bit = 0; bit<pre.cardinality(); bit++){
                    if(pre.get(bit)) aVar.addPrecond(fVarName[bit]);
                }

                //We add our posEffect :
                BitVector posEff = a.getUnconditionalEffect().getPositiveFluents();
                for(int bit = 0; bit<posEff.cardinality(); bit++){
                    if(posEff.get(bit)) aVar.addPrecond(fVarName[bit]+ (fluents.size()+actions.size()) ); //We increase of one time step
                }

                //We add our negEffect :
                BitVector negEff = a.getUnconditionalEffect().getNegativeFluents();
                for(int bit = 0; bit<negEff.cardinality(); bit++){
                    if(negEff.get(bit)) aVar.addPrecond(fVarName[bit]+ (fluents.size()+actions.size()) ); //We increase of one time step
                }

                varName++;
                previousVar.add(aVar);
            }
        }

        return previousVar;
    }

    //We create each clauses (we don't recreate the older clauses)
    private ArrayList<int[]> createClauses(ArrayList<SATVariable> satVariables, ArrayList<int[]> previousClauses){

        TODO:
        //ALL THE WORK OF CREATING CLAUSES IS GONNA BE HERE

        //WARNING : never gonna be empty
        if(previousClauses.isEmpty()){
            //We delete our previous goal and add the new clauses 
            
            
        }

        //We make clauses for each actions from undone yet too step goal -1
        //We recreat the goal (Time ++)

        //For Clauses undone we make them
        
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
        ArrayList<Fluent> predicats; //Predicats (untimed : they are all predicats of the domain)
        ArrayList<Action> actions; //Actions (untimed : they are all actions possible of the domain)
        ArrayList<SATVariable> SATVar; //Map the number (index) of the SAT variables with the proposition (action or predicat at time I)
        int numberVarPerTimeStep;

        //We save our predicats and actions 
        predicats = new ArrayList<>(problem.getFluents());
        actions = new ArrayList<>(problem.getActions());
        //For each time step we have exactly the same number of fluents and actions

        //We create the plan 
        Plan solvedPlan = new SequentialPlan();

        //We create the solver
        ISolver solver = SolverFactory.newDefault();
        
        //With the graph plan we find the minimum number of actions 
        FastForward ff = new FastForward(problem);
        State init = new State(problem.getInitialState());
        int numAction = ff.estimate(init, problem.getGoal());

        boolean solved = false;

        SATVar = new ArrayList<>();
        SATVar = propToVar(predicats, actions, numAction, SATVar);
        
        ArrayList<int[]> clauses = new ArrayList<>();

        //We build the initial state
        BitVector iState = problem.getInitialState().getPositiveFluents();
        for (SATVariable var : SATVar) {
            if(var.getTimeStep()==0 && var.isFluent()){
                //Fluents of the initial states
                int[] tClause = new int[1];
                if(iState.get(var.getName()-1)) tClause[0] = var.getName(); //We test with -1 because clauses go from 1 to n and bitVector : 0 to n-1
                else tClause[0] = -var.getName();
                clauses.add(tClause);
            }
        }

        //For the time lmit
        boolean timeExceed = false;

        try{ 
            while(!solved){
                //If time exceed and we still don't have a plan we stop and say plan not found
                if(timeExceed)
                    throw new TimeoutException();

                //Else we try to solve the problem :
                
                SATVar = propToVar(predicats, actions, numAction, SATVar);
                clauses = createClauses(SATVar, clauses);
                
                int numVar = SATVar.size();
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

                        TODO:
                        //We transform our variables to temporal propositions
                        //List<Actions(with time related step)> tempActions
                        //Translation with modulo : number of actions + fluents 
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