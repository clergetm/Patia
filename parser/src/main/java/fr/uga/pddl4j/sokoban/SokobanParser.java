package fr.uga.pddl4j.sokoban;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.parser.JSONParser;

import fr.uga.pddl4j.sokoban.exceptions.FileFormatException;
import fr.uga.pddl4j.sokoban.exceptions.MissingSokobanObjectException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.simple.JSONObject;

/**
 * Sokoban parser methods to change context of Sokoban problem.
 * <ol>
 * <li>Create a PDDL problem file from a Json configuration.</li>
 * <li>Translate the output of the outcome of a PDDL problem into Sokoban
 * syntax.</li>
 * </ol>
 * <br>
 * 
 * @author Hugo Alepoig
 * @author Yazid Cheriti
 * @author Mathys Clerget
 * @version 1.0
 * 
 */
public class SokobanParser {

    /* String for initialisation */
    private String jsonFilePathString;
    private String pddlPathString;

    private String mapName;
    private String mapModel;

    private int boxNumber = 1;

    /* PDDL Domain related */
    private List<String> floorPlan;
    private List<String> goal;
    private List<String> boxes;
    private String guardPosition;

    /* Read & Write */
    private FileWriter writer;

    /* Sokoban Character */
    static final char FLOOR = ' ';
    static final char GUARD = '@';
    static final char GUARD_ON_STORAGE = '+';
    static final char BOX = '$';
    static final char BOX_ON_STORAGE = '*';
    static final char DESTINATION = '.';
    static final char WALL = '#';

    static final char PREFIX_FLOOR = 'f';
    static final char PREFIX_DESTINATION = 'f';
    static final char PREFIX_BOX = 'b';
    static final char PREFIX_GUARD = 'g';

    /**
     * Constructor of JsonToPDDL.
     * Initalize the PathString for both JSON and PDDL files.
     * 
     * @param jsonFilePath The path to the JSON File.
     * @param pddlPath     The folder where to save the PDDL File.
     * @author MathysC
     */
    public SokobanParser(String jsonFilePath, String pddlPath) {
        this.jsonFilePathString = jsonFilePath;
        this.pddlPathString = pddlPath + "/out.pddl";
    }

    /**
     * Constructor of JsonToPDDL.
     * Put the PDDL file in the current directory.
     * 
     * @param jsonFilePath The path of the JSON File.
     * @author MathysC
     */
    public SokobanParser(String jsonFilePath) {
        this(jsonFilePath, ".");
    }

    /**
     * Initialize variable of the JsonToPDDL object.
     * <p>
     * Such as the files and the writer.
     * </p>
     * <p>
     * If something went wrong:
     * <ol>
     * <li>initialize method return false.</li>
     * <li>the user can’t create a PDDL file.</li>
     * </ol>
     * </p>
     * <br>
     * 
     * @param jsonFilePath The path to the JSON File.
     * @param pddlFilePath Where the PDDL File will be saved.
     * @return true if the initialization was successful else false.
     * @author MathysC
     */
    private boolean initialize(String jsonFilePath, String pddlFilePath) {
        File pddlFile;
        File jsonFile;
        final String format = ".json";
        boolean initialized = false;
        try {
            /* Check JsonFile */
            jsonFile = new File(jsonFilePath);
            if (!jsonFile.getName().endsWith(format)) {
                throw new FileFormatException("Wrong Format");
            }

            /* Create Pddl File */
            pddlFile = new File(pddlFilePath);

            /* Initialize variables */
            JSONObject json = (JSONObject) ((new JSONParser()).parse(new FileReader(jsonFile)));
            this.floorPlan = new ArrayList<>();
            this.goal = new ArrayList<>();
            this.boxes = new ArrayList<>();

            /* Get json data */
            mapName = jsonFile.getName().replace(format, "");
            mapModel = json.get("testIn").toString();

            /* Set writer of PDDL problem */
            this.writer = new FileWriter(pddlFile, false);

            initialized = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return initialized;
    }

    /**
     * Write the define of the PDDL Problem.
     * 
     * @throws IOException append exception.
     * @author YazidC
     */
    public void createHeader() throws IOException {
        this.writer.append("(define (problem " + mapName + ")");
        this.writer.append("(:domain SOKOBAN)\n");
        this.writer.append("(:objects\n");
        this.writer.flush();
    }

    /**
     * Write the object part of the PDDL Problem.
     * 
     * @throws IOException append exception.
     * @author YazidC
     * @throws MissingSokobanObjectException
     */
    public void createObjects() throws IOException, MissingSokobanObjectException {

        ArrayList<String> map = new ArrayList<>(Arrays.asList(mapModel.split("\n")));
        
        for (String s : map) {
            System.out.println(s);
        }
        int row = 1;
        int cell = 1;

        final String strFloor = " - floor\n";
        final String strDest = " - destination\n";
        final String strBox = " - box\n";
        final String cfloor = "f";
        char[] arrayMap = new char[mapModel.length()];
        mapModel.getChars(0, mapModel.length(), arrayMap, 0);
        for (int i = 0; i < map.size(); i++) {
            boolean betweenWalls = false;
            row++;
            cell = 1;
            for (int j = 0; j < map.get(i).length(); j++) {
                if (map.get(i).charAt(j) == '#')
                    betweenWalls = true;
                if (betweenWalls) {
                    switch (map.get(i).charAt(j)) {
                        case ' ':
                            addFloor(row, cell);
                            break;
                        case '.':
                            addDestination(row, cell);
                            break;
                        case '@':
                            addFloor(row, cell);
                            guardPosition = cfloor + row + cell;
                            break;
                        case '+':
                            addDestination(row, cell);
                            guardPosition = cfloor + row + cell;
                            break;
                        case '$':
                            addFloor(row, cell);
                            addBox(row, cell);
                            break;
                        case '*':
                            addDestination(row, cell);
                            addBox(row, cell);
                            break;
                        case '#':
                            break;
                        default:
                            throw new MissingSokobanObjectException("Invalid character read.");
                    }
                }
                cell++;
            }
        }
        // Check missing object
        if (guardPosition.isEmpty()) {
            throw new MissingSokobanObjectException("The guard position is missing");
        } else {
            writer.append(")\n");
        }
    }

    /**
     * Write the initialisation part of the PDDL problem
     * 
     * @throws IOException append exception.
     * @author YazidC
     */
    public void createInit() throws IOException {
        writer.append("(:init\n");
        String cell;
        String toCompare;
        for (int i = 0; i < floorPlan.size(); i++) {
            cell = floorPlan.get(i);
            if (boxes.contains(cell)) {
                writer.append("(on b" + --boxNumber + " " + cell + ")\n(withbox " + cell + ")\n");
            }
            if (!boxes.contains(cell) && !guardPosition.equals(cell))
                writer.append("(empty " + cell + ")\n");
            if (guardPosition.equals(cell))
                writer.append("(on g " + cell + ")\n");
            int right = Integer.parseInt(cell.substring(2));
            int down = Integer.parseInt(cell.substring(1, 2)) + 1;
            right++;
            toCompare = cell.substring(0,2) + right;
            if (floorPlan.contains(toCompare)) {
                this.writeToPath(cell, toCompare, 'r');
                this.writeToPath(toCompare, cell, 'l');
            }
            toCompare = "f" + down + (right - 1);
            if (floorPlan.contains(toCompare)) {
                this.writeToPath(cell, toCompare, 'd');
                this.writeToPath(toCompare, cell, 'u');
            }
        }
        writer.append(")\n");
    }

    /**
     * Write the goal of the PDDL problem.
     * 
     * @throws IOException append exception.
     * @author YazidC
     */
    public void createGoal() throws IOException {
        writer.append("  (:goal (and\n");
        for (String d : goal) {
            writer.append("(withbox " + d + ")\n");
        }
        writer.append("\n    ))\n)");
    }

    /**
     * Create the PDDL problem from the given json.
     * 
     * @author YazidC
     */
    public void createPDDL() {
        boolean creation = false;
        try {
            if (initialize(jsonFilePathString, pddlPathString)) {
                createHeader();
                createObjects();
                createInit();
                createGoal();
                writer.flush();
                creation = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (creation) {
                System.out.println("Successful PDDL creation!");
            } else {
                System.err.println("PDDL Creation stopped, please refer to exceptions.");
            }
        }
    }

    /**
     * Append in the writer the path from a cell to another in one direction.
     * 
     * @param from      the first cell
     * @param to        the second cell
     * @param direction the direction {l, r, u, d}
     * @throws IOException append exception.
     * @author MathysC
     */
    private void writeToPath(String from, String to, char direction) throws IOException {
        this.writer.append("(path " + from + " " + to + " " + direction + ")\n");
    }

    private void addFloor(int row, int cell) throws IOException {
        this.writer.append(""+PREFIX_FLOOR + row + cell + " - floor\n");
        this.floorPlan.add(""+ PREFIX_FLOOR + row + cell);
    }

    private void addDestination(int row, int cell) throws IOException {
        this.writer.append(""+PREFIX_DESTINATION + row + cell + " - destination\n");
        this.floorPlan.add(""+ PREFIX_DESTINATION + row + cell);
        this.goal.add("" + PREFIX_DESTINATION + row + cell);      
    }

    private void addBox(int row, int cell) throws IOException {
        writer.append("" + PREFIX_BOX + boxNumber + " - box\n");
        boxes.add("" + PREFIX_FLOOR + row + cell);
        boxNumber++;
    }
    
    /**
     * Translate a PDDL plan
     * <p>
     * Translate a PDDL plan in file <fileName> for a Sokoban problem and
     * translate it to Sokoban format: "URDD"
     * </p>
     * 
     * @param fileName Terminal outcome of the resolved pddl problem in a txt file.
     * @return The PDDL plan in Sokoban format or "" if no plan
     * @author HugoA
     */
    public static String translate(String fileName) {
        StringBuilder jsonPlan = new StringBuilder();
        File planFile = new File(fileName);
        String line = "";

        try (Scanner reader = new Scanner(planFile)) {
            // We skip every line until the plan
            while (reader.hasNextLine() && !line.contains("found plan as follows:"))
                if (line.contains("no plan found"))
                    return "";
                else
                    line = reader.nextLine();

            reader.nextLine(); // One empty line

            // For each action of the pddl plan
            line = reader.nextLine();
            while (reader.hasNextLine() && !line.equals("") && !line.equals('\n')) {
                // We check that the action is'nt a reverse one
                if (!line.contains("reverse")) {

                    // We find our movement direction
                    int i;
                    for (i = 0; line.charAt(i) != ')'; i++)
                        ;

                    // We append our direction to our JSON plan
                    jsonPlan.append(Character.toUpperCase(line.charAt(i - 1)));
                    line = reader.nextLine();
                }
            }
        } catch (FileNotFoundException | NoSuchElementException e) {
            e.printStackTrace();
        }

        return jsonPlan.toString();
    }

    /**
     * Parse the command line and act according to the command:
     * 4 Options are available:
     * <table>
     * <thead>
     *   <tr>
     *     <th>option</th>
     *     <th>description</th>
     *   </tr>
     * </thead>
     * <tbody>
     *   <tr>
     *     <td>-i</td>
     *     <td>JSON Configuration file as input</td>
     *   </tr>
     *   <tr>
     *     <td>-o</td>
     *     <td>Folder where to save the PDDL file</td>
     *   </tr>
     *   <tr>
     *     <td>-t</td>
     *     <td>PDDL Plan file to translate in Sokoban Format</td>
     *   </tr>
     *   <tr>
     *     <td>-h</td>
     *     <td>Show Help</td>
     *   </tr>
     * </tbody>
     * </table>
     * 
     * @param args arguments from command line
     * @author MathysC
     */
    public static void main(String[] args) {

        SokobanParser sokobanParser;

        /* CLI Options */
        String inputOpt = "i";
        String outputOpt = "o";
        String translateOpt = "t";
        String helpOpt = "h";
        Options options = new Options();
        Option cInput = Option.builder(inputOpt).argName("json").hasArg().desc("JSON configuration file").build();
        Option cOutput = Option.builder(outputOpt).argName("path").hasArg().desc("folder where to save PDDL file")
                .build();
        Option tInput = Option.builder(translateOpt).argName("toTranslate").hasArg().desc("PDDL Plan to translate")
                .build();

        options.addOption(cInput);
        options.addOption(cOutput);
        options.addOption(tInput);
        options.addOption(helpOpt, "show Help");
        CommandLineParser commandParser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();

        try {

            CommandLine cmd = commandParser.parse(options, args);
            // Create options
            if (cmd.hasOption(inputOpt)) {
                // With output given
                if (cmd.hasOption(outputOpt)) {
                    sokobanParser = new SokobanParser(cmd.getOptionValue("i"), cmd.getOptionValue("o"));
                } else {
                    sokobanParser = new SokobanParser(cmd.getOptionValue("i"));
                }
                sokobanParser.createPDDL();
            }
            // Translate a Plan
            else if (cmd.hasOption(translateOpt)) {
                System.out.println(SokobanParser.translate(cmd.getOptionValue("t")));
            }
            // Show Help
            else if (cmd.hasOption(helpOpt)) {
                formatter.printHelp("SokobanParser", options);
            }

        } catch (ParseException e) {
            System.err.println("Parsing failed.  Reason: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
