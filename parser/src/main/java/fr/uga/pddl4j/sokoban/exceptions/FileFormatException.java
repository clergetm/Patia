package fr.uga.pddl4j.sokoban.exceptions;

/**
 * Exception for the File Format
 * @author MathysC
 */
public class FileFormatException extends Exception {
    
    /** Default constructor.
     * 
     */
    public FileFormatException(){

    }

    /** Constructor with message.
     * use Exception super constructor.
     * @param message The exception message.
     */
    public FileFormatException(String message){
        super(message);
    }
}
