package sokoban;

import fr.uga.pddl4j.sokoban.SokobanParser;

public class Agent {
    private static final String PATH = "./pddl4S";
    public static void main(String[] args) {
        String solution = SokobanParser.translate(PATH + "/out.txt");
        for (char c : solution.toCharArray()) System.out.println(c);
    }
}
