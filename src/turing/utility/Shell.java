package turing.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Scanner;

import turing.model.TuringMachine;

public final class Shell {
    /**
     * The Turing Machine the Shell currently works with.
     */
    private static TuringMachine turingMachine = null;

    private static void error(String errorInformation) {
        System.out.println("Error! " + errorInformation);
    }

    private Shell() {
        throw new UnsupportedOperationException(
                "Illegal call of utility class constructor.");
    }
    
    /**
     * Initializes a new automaton if the command was given correctly. Returns
     * {@code true} if successful and {@code false} if it failed.
     * 
     * @param scanner Contains the instructions by the user.
     * @return Returns whether the Initialization has worked or not.
     * @throws IOException
     */
    private static boolean readDTMFromFile(Scanner scanner) throws IOException {
        if (scanner.hasNext()) {
            String fileName = scanner.next();
            if (!scanner.hasNext()) {
                File file = new File (fileName);
                try {
                    Shell.turingMachine = 
                            TuringMachineFactory.loadFromFile(file);
                } catch (FileNotFoundException e) {
                    error ("Ungültiger Dateiname oder -pfad!");
                    return true;
                } catch (ParseException e) {
                    error ("Ungültiges Dateiformat!");
                    return true;
                }
                return false;
            }    
        }
        error("Falsches Parameterformat! Nach INPUT nur den Namen der Datei, "
                + "die die Turing Maschine beschreibt, angeben!");
        return true;
    }
    
    /**
     * Writes to the console whether the command is incorrect or the checked 
     * word is within the language or not.
     * 
     * @param scanner Contains the instructions given by the user
     * @param noTuringMachine Tells whether a DTM is initialized or not.
     * @throws IOException
     */
    private static void checkWord(Scanner scanner, boolean noTuringMachine)
            throws IOException {
        if (noTuringMachine) {
            error("Es wurde noch keine Turing Maschine initialisiert!");
            return;
        }
        String word = "";
        if (scanner.hasNext()) {
            word = scanner.next();
        }
        if (scanner.hasNext()) {
            error ("Falsches Parameterformat! CHECK Wort erwartet!");
            return;
        }
        if (turingMachine.check(word)) {
            System.out.println("accept");
        } else {
            System.out.println("reject");
        }
    }

    /**
     * Writes the content of the working tape to the console.
     * 
     * @param scanner Contains the instructions given by the user
     * @param noTuringMachine Tells whether a DTM is initialized or not.
     * @throws IOException
     */
    private static void run(Scanner scanner, boolean noTuringMachine)
            throws IOException {
        if (noTuringMachine) {
            error("Es wurde noch keine Turing Maschine initialisiert!");
            return;
        }
        String word = "";
        if (scanner.hasNext()) {
            word = scanner.next();
        }
        if (scanner.hasNext()) {
                error("Falsches Format! SIMULATE Wort erwartet!");
                return;
        }
        System.out.println(turingMachine.simulate(word));
    }

    /**
     * Prints a String representation of the Turing Machine on the console.
     * 
     * @param noTuringMachine Tells whether a DTM is initialized or not.
     */
    private static void printTuringMachine(boolean noTuringMachine) {
        if (noTuringMachine) {
            error("Es wurde noch keine Turing Maschine initialisiert!");
        } else {
            System.out.print(Shell.turingMachine);
        }
    }

    /**
     * Writes a text containing information about the syntax and function of 
     * the supported instructions to the console.
     */
    private static void help() {
        System.out.println();
    }

    /**
     * Checks the instructions the user gives to the Shell and calls the right 
     * methods to execute them.
     * 
     * @param reader Reads the input from the user.
     * @throws IOException
     */
    private static void execute(BufferedReader reader) throws IOException {
        boolean continueInput = true;
        boolean hasNoTuringMachine = true;
        while (continueInput) {
            System.out.print("dtm> ");
            Scanner instructionScanner = new Scanner(reader.readLine());
            if (instructionScanner.hasNext()) {
                String command = instructionScanner.next();
                char instructor = command.toUpperCase().charAt(0);
                switch (instructor) {
                case 'I':
                    hasNoTuringMachine = 
                                    Shell.readDTMFromFile(instructionScanner);
                    break;
                case 'C':
                    Shell.checkWord(instructionScanner, hasNoTuringMachine);
                    break;
                case 'R':
                    Shell.run(instructionScanner, hasNoTuringMachine);
                    break;
                case 'P':
                    Shell.printTuringMachine(hasNoTuringMachine);
                    break;
                case 'H':
                    help();
                    break;
                case 'Q':
                    continueInput = false;
                    break;
                default:
                    error("Ungültiger Eingabebefehl!");
                    break;
                }
            }
            instructionScanner.close();
        }
    }

    /**
     * Reads the instructions of the user and executes them.
     * 
     * @param args Currently unused.
     * @throws IOException May be thrown by BufferedReader or InputStreamReader.
     */
    public static void main(String[] args) throws IOException {
        BufferedReader inputReader = new BufferedReader(
                new InputStreamReader(System.in));
        execute(inputReader);
        inputReader.close();
    }
}
