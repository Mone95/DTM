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
                    error ("Ung�ltiger Dateiname oder -pfad!");
                    return true;
                } catch (ParseException e) {
                    error ("Ung�ltiges Dateiformat!");
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
        System.out.println("Mit diesem Programm kann ein "
                + "nicht-deterministischer endlicher Automat mit "
                + "Spontan�berg�ngen erzeugt, erweitert und in "
                + "Textform ausgegeben werden und es k�nnen diesem "
                + "W�rter �bergeben werden, welche auf Zugeh�rigkeit "
                + "zur Sprache des Automaten gepr�ft und deren "
                + "l�ngster in der Sprache des Automaten enthaltener "
                + "Pr�fix ausgegeben werden kann. Unterst�tze " + "Befehle:");
        System.out.println("INIT Zahl | Initialisiert einen neuen Automaten "
                + "mit der angegebenen Zahl an Zust�nden. Der Zustand "
                + "mit der Nummer 1 ist der Startzustand, die "
                + "Zust�nde werden fortlaufend nummeriert und der "
                + "letzte Zustand ist der Endzustand.");
        System.out.println("ADD Zahl1 Zahl2 Zeichen | F�gt einen neuen Zustand "
                + "in den Automaten ein, der vom Zustand mit der "
                + "Nummer Zahl1 in den Zustand mit der Nummer Zahl2 "
                + "mit dem angegebenen Zeichen �bergeht. F�r "
                + "Spontan�berg�nge "
                + " benutzen. M�chte man eine bereits vorhandene Kante"
                + " einf�gen, so wird der Automat nicht ver�ndert.");
        System.out.println("CHECK \"Wort\" | Gibt an, ob das in "
                + "Anf�hrungszeichen angegebene Wort in der Sprache "
                + "des Automaten ist.");
        System.out.println("PREFIX \"Wort\" | Gibt das l�ngste in der Sprache "
                + "enthaltene Pr�fix des angegeben Wortes in "
                + "Anf�hrungszeichen zur�ck oder den Text 'No prefix "
                + "in language.', falls kein Pr�fix des Wortes in der "
                + "Sprache des Automaten ist.");
        System.out.println("GENERATE | Initialisiert einen im Programm bereits "
                + "vordefinierten Automaten. Dieser besitzt folgende"
                + " Transitionen und die darin enthaltenen Zust�nde:"
                + "\n(1, 2) a\n(1, 3) b\n(1, 4) ~\n"
                + "(2, 2) a\n(2, 3) ~\n(2, 3) a\n(3, 4) a\n(3, 4) b\n"
                + "(4, 5) a\n(4, 5) b");
        System.out.println("DISPLAY | Gibt alle im aktuell geladenen Automaten "
                + "vorhandenen Transitionen untereinander und "
                + "lexikographisch sortiert im Format '(Startzustand, "
                + "Endzustand) �bergangssymbol' an.");
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
                    error("Ung�ltiger Eingabebefehl!");
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
