package sokoban;

import com.codingame.gameengine.runner.SoloGameRunner;

public class SokobanMain {
    public static void main(String[] args) {
        if (args.length == 1) {
            SoloGameRunner gameRunner = new SoloGameRunner();
            gameRunner.setAgent(Agent.class);
            gameRunner.setTestCase(args[0]);

            gameRunner.start();
        }
    }
}
