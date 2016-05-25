package turing.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Scanner;

import turing.model.TuringMachine;

/**
 * The interface between user and Turing Machine, based on text commands.
 */
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
     * Initializes a new Turing Machine if the command and the file path were 
     * given correctly and the file format was correct. Returns {@code true} 
     * if successful and {@code false} if it failed.
     * 
     * @param scanner Contains the instructions of the user.
     * @return Returns whether the Initialization has worked or not.
     * @throws IOException
     */
    private static boolean readDTMFromFile(Scanner scanner) throws IOException {
        if (scanner.hasNext()) {
            String filePath = scanner.next();
            if (!scanner.hasNext()) {
                File file = new File(filePath);
                try {
                    Shell.turingMachine = TuringMachineFactory
                            .loadFromFile(file);
                } catch (FileNotFoundException e) {
                    error("Ungültiger Dateiname oder -pfad!");
                    return true;
                } catch (ParseException e) {
                    error("Ungültiges Dateiformat!");
                    return true;
                }
                return false;
            }
        }
        error("Falsches Parameterformat! INPUT Dateipfad erwartet!");
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
            error("Falsches Parameterformat! CHECK Wort erwartet!");
            return;
        }
        if (turingMachine.check(word)) {
            System.out.println("accept");
        } else {
            System.out.println("reject");
        }
    }

    /**
     * Writes the content of the working tape after checking the given word to 
     * the console.
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
     * Writes a textual representation of the Turing Machine to the console.
     * 
     * @param noTuringMachine Tells whether a DTM is initialized or not.
     */
    private static void printTuringMachine(boolean noTuringMachine) {
        if (noTuringMachine) {
            error("Es wurde noch keine Turingmaschine initialisiert!");
        } else {
            System.out.print(Shell.turingMachine);
        }
    }

    /**
     * Writes a text containing information about the syntax and function of the
     * supported instructions to the console.
     */
    private static void help() {
        System.out.println("Dieses Programm lädt die Konfiguration einer "
                + "deterministischen Turingmaschine aus einer Datei und "
                + "simuliert diese anschließend auf übergebenen Wörtern. Dabei "
                + "kann wahlweise die Zugehörigkeit des Wortes zur Sprache der "
                + "Turingmaschine oder der Inhalt des Arbeitsbandes nach "
                + "Ausführung des letzten Schritts ausgegeben werden. Außerdem "
                + "kann eine textuelle Repräsentation der Turingmaschine "
                + "ausgegeben werden. Unterstützte Befehle:");
        System.out.println("INPUT Dateipfad | Versucht, die angegebene "
                + "Datei zu öffnen und die Konfiguration der Turingmaschine "
                + "daraus zu lesen. Lädt die beschriebene Turingmaschine in "
                + "das Programm, falls erfolgreich.");
        System.out.println("CHECK Wort | Gibt aus, ob das Wort Element der "
                + "Sprache der beschriebenen Turingmaschine ist (accept) oder "
                + "nicht (reject).");
        System.out.println("RUN Wort | Gibt den Inhalt des Arbeitsbands der "
                + "Turingmaschine nach dem letzten ausgeführten Schritt aus.");
        System.out.println("PRINT | Gibt eine textuelle Repräsentation der "
                + "Turingmaschine aus.");
        System.out.println("HELP | Gibt diesen Hilfetext aus.");
        System.out.println("QUIT | Beendet das Programm.");
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
                    hasNoTuringMachine = Shell
                            .readDTMFromFile(instructionScanner);
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
     * @throws IOException if a problem with a file or a Reader occurs.
     */
    public static void main(String[] args) throws IOException {
        BufferedReader inputReader = new BufferedReader(
                new InputStreamReader(System.in));
        Shell.execute(inputReader);
        inputReader.close();
    }
}
