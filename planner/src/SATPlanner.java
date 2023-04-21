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
                if(!previousVar.contains(p)) //We add the propositions only they are not already in it
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

        //Then we had our fluents for the final time step
        for (int fI=0; fI<fluents.size();fI++) {
            SATVariable p = new SATVariable(numAction, varName, true);
            varName++;
            previousVar.add(p);
        }

        return previousVar;
    }

    //We create each clauses (we don't recreate the older clauses)
    //NEED TO BURN AND REBUILD THIS METHOD
    //WE CREATE EACH TRANSITION CLAUSES ONLY FOR TIME STEP : prev TO TIMESTEPMAX
    //CHECK SATPLANNER to transform pre and post in clauses
    private ArrayList<int[]> createTransitionClauses(ArrayList<SATVariable> satVariables, ArrayList<int[]> previousClauses, int lastStep, int numberVarPerTimeStep){
        //We make clauses for each actions from undone yet too step goal -1
        //For clauses we have : and,or for one particular clause and AND,OR for each clause at least one list at the left or right of it

        //Initial and goal states have already been done

        int time = lastStep-1;
        //If its the first time, we create clauses from Time 0 to Time n (where time n is goal's time)
        if(previousClauses.isEmpty()){
            time=0;
        }


        //Action implication and constraints are only for time i ~ n-1 and state changes if for i ~ n (thanks to fNext)
        for(int timeStep=time; timeStep<lastStep; timeStep++){

            //For each time step
            //We first create the action implication
            for (SATVariable a : satVariables) {
                if(a.getTimeStep()==timeStep && !a.isFluent()){
                    //a is an action at time i
                    //a -> pre(a) and eff+(a) and eff-(a)
                    //Transformed to :
                    //AND( (not(a) OR pre(a)) (not(a) OR eff+(a)) (not(a) OR eff-(a)) )

                    for (int pre : a.getPrecond()) {
                        int[] clausePrecond = {-a.getName(),pre};
                        previousClauses.add(clausePrecond);
                    }

                    for (int posEff : a.getPosEffect()) {
                        int[] clausePosEff = {-a.getName(),posEff};
                        previousClauses.add(clausePosEff);
                    }

                    for (int negEff : a.getNegEffect()) {
                        int[] clauseNegEff = {-a.getName(),negEff};
                        previousClauses.add(clauseNegEff);
                    }

                }
            }

            //Then we handle state changes : if f at i and not(f) at i+1 means action with neg effect occurs
            //                               and not(f) at i and f at i+1 means action with pos effect occurs
            for(SATVariable f : satVariables){
                if(f.getTimeStep()==timeStep && f.isFluent())
                    for(SATVariable fNext : satVariables) 
                        if (fNext.getName()==f.getName()+numberVarPerTimeStep){
                            // -f and fNext -> OR(ai) where fNext in eff+(ai)
                            // f and -fNext -> OR(ai) where fNext in eff-(ai)
                            //Transformed to respectively :
                            // f or -fNext OR (ai) eff+
                            // -f or fNext OR (ai) eff-
                            
                            ArrayList<Integer> actionWithPosEff = new ArrayList<>();
                            ArrayList<Integer> actionWithNegEff = new ArrayList<>();

                            //For each action at time step i we check if fNext is in the eff+ or eff-
                            //TODO : Optimization
                            for (SATVariable a : satVariables) {
                                if(a.getTimeStep()==timeStep && !a.isFluent())
                                    for(int affectedF : a.getPosEffect())
                                        if(affectedF == fNext.getName()){
                                            actionWithPosEff.add(a.getName());
                                            break; //We break from the first for (posEffect)
                                        }
                                    for(int affectedF : a.getNegEffect())
                                        if(affectedF == fNext.getName()){
                                            actionWithNegEff.add(a.getName());
                                            break; //We break from the second for (negEffect)
                                        }
                            }

                            //We create and add the two clauses (for each f and fNext)
                            int[] clausePosEff = new int[actionWithPosEff.size()+2];
                            int[] clauseNegEff = new int[actionWithNegEff.size()+2];

                            clausePosEff[0] = f.getName();
                            clausePosEff[1] = -fNext.getName();
                            for(int index=0; index<actionWithPosEff.size(); index++) clausePosEff[index+2] = actionWithPosEff.get(index);

                            clauseNegEff[0] = -f.getName();
                            clauseNegEff[1] = fNext.getName();
                            for(int index=0; index<actionWithNegEff.size(); index++) clauseNegEff[index+2] = actionWithNegEff.get(index);
                            
                            previousClauses.add(clausePosEff);
                            previousClauses.add(clauseNegEff);
                        }
            }

            //We handle action restriction : only one action can occur at time i

            //The list for handle doubles (to minimize the number of clauses)
            ArrayList<Integer> alreadyHandled = new ArrayList<>();
            for (SATVariable a : satVariables) {
                //Each action at time i impli no other action at time i
                if(a.getTimeStep()==timeStep)
                    for (SATVariable aSameTime : satVariables) {
                        if(!alreadyHandled.contains(aSameTime.getName()) && a.getName()!=aSameTime.getName() && aSameTime.getTimeStep()==timeStep){
                            // aSameTime is an action that is not a, not already handled and a 
                            // not(a) or not(aSameTime)
                            int[] clause = {-a.getName(),-aSameTime.getName()};
                            previousClauses.add(clause);
                        }
                    }
            }
        }

        
        return previousClauses;
    }

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
        numberVarPerTimeStep=predicats.size()+actions.size();

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
        
        ArrayList<int[]> initClauses = new ArrayList<>();
        ArrayList<int[]> goalClauses = new ArrayList<>();
        ArrayList<int[]> transitionClauses = new ArrayList<>();

        //We build the initial state only once
        BitVector iState = problem.getInitialState().getPositiveFluents();
        for (SATVariable var : SATVar) {
            if(var.getTimeStep()==0 && var.isFluent()){
                //Fluents of the initial states
                int[] tClause = new int[1];
                if(iState.get(var.getName()-1)) tClause[0] = var.getName(); //We test with -1 because clauses go from 1 to n and bitVector : 0 to n-1
                else tClause[0] = -var.getName();
                initClauses.add(tClause);
            }
        }

        //For the goal state :
        BitVector gState = problem.getGoal().getPositiveFluents();

        //For the time lmit
        boolean timeExceed = false;

        try{ 
            while(!solved){
                //If time exceed and we still don't have a plan we stop and say plan not found
                if(timeExceed)
                    throw new TimeoutException();

                //Else we try to solve the problem :
                
                //We build the SATvariables needed
                SATVar = propToVar(predicats, actions, numAction, SATVar);

                //We build the corresponding transitionClauses
                transitionClauses = createTransitionClauses(SATVar, transitionClauses, numAction, numberVarPerTimeStep);

                //We clear and rebuild the goal 
                goalClauses.clear();
                for (SATVariable var : SATVar) {
                    if(var.getTimeStep()==numAction && var.isFluent()){
                        //Fluents of the goal states (if everythings work, there is only fluents with this timeStep)
                        int[] tClause = new int[1];
                        if(gState.get((var.getName()-1)%(numberVarPerTimeStep))) tClause[0] = var.getName(); //We test with -1 because clauses go from 1 to n and bitVector : 0 to n-1
                        //We only add positives fluents because STRIPS doesn't accept not in Goal
                        goalClauses.add(tClause);
                    }
                }
                
                int numVar = SATVar.size();
                // prepare the solver to accept numVar variables. MANDATORY for MAXSAT solving
                solver.newVar(numVar);
                
                int numClause = initClauses.size() + transitionClauses.size() + goalClauses.size();
                // prepare the solver to accept numClause clauses. MANDATORY for MAXSAT solving
                solver.setExpectedNumberOfClauses(numClause);

                // Feed the solver using Dimacs format, using arrays of int
                // (best option to avoid dependencies on SAT4J IVecInt)
                
                //We add our initialClauses
                for (int i=0;i<initClauses.size();i++) {
                    int [] clause = initClauses.get(i); // get the clause from initClauses
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

                //We add our transitionClauses
                for (int i=0;i<transitionClauses.size();i++) {
                    int [] clause = transitionClauses.get(i); // get the clause from transitionClauses
                    try {
                        solver.addClause(new VecInt(clause)); // adapt Array to IVecInt
                    } catch (ContradictionException e) {
                        e.printStackTrace();
                    } 
                }

                //We add our goalClauses
                for (int i=0;i<goalClauses.size();i++) {
                    int [] clause = goalClauses.get(i); // get the clause from goalClauses
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

                        //We create back the plan
                        //Translation with modulo : number of actions + fluents 
                        for (SATVariable a : SATVar) {
                            //We only take actions
                            if(!a.isFluent()){
                                for(int v : model){
                                    //We compare with variables of the model7
                                    if(a.getName()==v) {
                                        //The action a occurs at time getTime 
                                        int place = (a.getName()%numberVarPerTimeStep)-predicats.size(); //The modulo give the index between 0 and numberVar and
                                                                                                         // the minus give the index on the vector (+1) 
                                        place -= 1; //Because the list start at 0 and SATVar at 1

                                        Action act = actions.get(place);

                                        solvedPlan.add(a.getTimeStep(),act);
                                    }
                                }
                            }
                        }

                        

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