package fr.uga.pddl4j.sokoban.exceptions;

/**
 * Exception for a Sokoban Object missing.
 * @author MathysC
 */
public class MissingSokobanObjectException extends Exception {

    /** Default constructor.
     * 
     */
    public MissingSokobanObjectException() {

    }

    /** Constructor with message.
     * use Exception super constructor.
     * @param message The exception message.
     */
    public MissingSokobanObjectException(String message) {
        super(message);
    }
}