package turing.utility;

public final class Shell {
    /**
     * The automaton the Shell currently works with.
     */
    private static LambdaNFA automaton = null;

    private static void error(String errorInformation) {
        System.out.println("Error! " + errorInformation);
    }

    private Shell() { }
    
    /**
     * Initializes a new automaton if the command was given correctly. Returns
     * {@code true} if successful and {@code false} if it failed.
     * 
     * @param scanner Contains the instructions by the user.
     * @return Returns whether the Initialization has worked or not.
     * @throws IOException
     */
    private static boolean initAutomaton(Scanner scanner) throws IOException {
        if (scanner.hasNextInt()) {
            int sizeOfAutomaton = scanner.nextInt();
            if (!scanner.hasNext() && sizeOfAutomaton > 0) {
                Shell.automaton = new LambdaNFA(sizeOfAutomaton,
                        new int[] {sizeOfAutomaton});
                return false;
            }
        }
        error("Falsches Parameterformat! Nach INIT die Anzahl der Zustände "
                + "des Automaten eingeben.");
        return true;
    }
    
    /**
     * Checks if a transition the user wants to add to the automaton is valid 
     * and adds it if possible.
     * 
     * @param scanner Contains the instructions of the user.
     * @param noAutomaton Tells whether an automaton is initialized or not.
     * @throws IOException
     */
    private static void addTransition(Scanner scanner, boolean noAutomaton)
            throws IOException {
        int firstState = 0;
        int secondState = 0;
        char symbol = '.';
        String wrongParameters = "Falsches Parameterformat! ADD Zustand Zustand"
                + " Zeichen des Formats [" + Automaton.FIRST_SYMBOL + "-"
                + Automaton.LAST_SYMBOL + "] oder " + LambdaNFA.LAMBDA
                + " benötigt.";
        if (noAutomaton) {
            error("Es wurde noch kein Automat initialisiert!");
            return;
        }
        if (scanner.hasNextInt()) {
            firstState = scanner.nextInt();
        } else {
            error(wrongParameters);
            return;
        }
        if (scanner.hasNextInt()) {
            secondState = scanner.nextInt();
        } else {
            error(wrongParameters);
            return;
        }
        if (scanner.hasNext()) {
            String inputString = scanner.next();
            if ((inputString.length() == 1) && !(scanner.hasNext())) {
                symbol = inputString.charAt(0);
                if (automaton.isValidTransition(firstState, secondState,
                        symbol)) {
                    automaton.addTransition(firstState, secondState, symbol);
                    return;
                }
            }
        }
        error(wrongParameters);
    }
    
    /**
     * Writes to the console whether the command is incorrect, the checked 
     * word is within the language or not.
     * 
     * @param scanner Contains the instructions given by the user
     * @param noAutomaton Tells whether an automaton is initialized or not.
     * @throws IOException
     */
    private static void isElement(Scanner scanner, boolean noAutomaton)
            throws IOException {
        if (noAutomaton) {
            error("Es wurde noch kein Automat initialisiert!");
            return;
        }
        if (scanner.hasNext()) {
            if (scanner.hasNext()) {
                String word = scanner.next();
                if (word.equals("\"\"")) {
                    word = "";
                } else if ((word.charAt(0) == '"')
                        && (word.charAt(word.length() - 1) == '"')) {
                    word = word.substring(1, word.length() - 1);
                } else {
                    error("Falsches Format: CHECK \"Zu überprüfendes Wort\" "
                            + "erwartet!");
                    return;
                }
                if (!scanner.hasNext()) {
                    if (automaton.isElement(word)) {
                        System.out.println("In language.");
                        return;
                    } else {
                        System.out.println("Not in language.");
                        return;
                    }
                }
            }
        }
        error("Falsches Format: CHECK \"Zu überprüfendes Wort\" erwartet!");
    }

    /**
     * Writes the longest prefix of the word the automaton would still accept
     * to the console.
     * 
     * @param scanner Contains the instructions given by the user
     * @param noAutomaton Tells whether an automaton is initialized or not.
     * @throws IOException
     */
    private static void longestPrefix(Scanner scanner, boolean noAutomaton)
            throws IOException {
        if (noAutomaton) {
            error("Es wurde noch kein Automat initialisiert!");
            return;
        }
        if (scanner.hasNext()) {
            if (scanner.hasNext()) {
                String word = scanner.next();
                if (word.equals("\"\"")) {
                    word = "";
                } else if ((word.charAt(0) == '"')
                        && (word.charAt(word.length() - 1) == '"')) {
                    word = word.substring(1, word.length() - 1);
                } else {
                    error("Falsches Format: PREFIX \"Zu überprüfendes Wort\" "
                            + "erwartet!");
                    return;
                }
                if (!scanner.hasNext()) {
                    String prefix = automaton.longestPrefix(word);
                    if (prefix == null) {
                        System.out.println("No prefix in language.");
                    } else {
                        System.out.println(
                                "\"" + automaton.longestPrefix(word) + "\"");
                    }
                    return;
                }
            }
        }
        error("Falsches Format: PREFIX \"Zu überprüfendes Wort\" erwartet!");
    }

    private static boolean generateAutomaton() {
        automaton = new LambdaNFA(5, new int[] {5});
        automaton.addTransition(1, 2, 'a');
        automaton.addTransition(2, 3, 'a');
        automaton.addTransition(3, 4, 'a');
        automaton.addTransition(4, 5, 'a');
        automaton.addTransition(2, 2, 'a');
        automaton.addTransition(2, 3, '~');
        automaton.addTransition(1, 4, '~');
        automaton.addTransition(1, 3, 'b');
        automaton.addTransition(3, 4, 'b');
        automaton.addTransition(4, 5, 'b');
        return false;
    }

    /**
     * Prints a String representation of the automaton on the console.
     * 
     * @param noAutomaton Tells whether an automaton is initialized or not.
     */
    private static void displayAutomaton(boolean noAutomaton) {
        if (noAutomaton) {
            error("Es wurde noch kein Automat initialisiert!");
        } else {
            System.out.print(Shell.automaton);
        }
    }

    /**
     * Writes a text containing information about the syntax and function of 
     * the supported instructions to the console.
     */
    private static void help() {
        System.out.println("Mit diesem Programm kann ein "
                + "nicht-deterministischer endlicher Automat mit "
                + "Spontanübergängen erzeugt, erweitert und in "
                + "Textform ausgegeben werden und es können diesem "
                + "Wörter übergeben werden, welche auf Zugehörigkeit "
                + "zur Sprache des Automaten geprüft und deren "
                + "längster in der Sprache des Automaten enthaltener "
                + "Präfix ausgegeben werden kann. Unterstütze " + "Befehle:");
        System.out.println("INIT Zahl | Initialisiert einen neuen Automaten "
                + "mit der angegebenen Zahl an Zuständen. Der Zustand "
                + "mit der Nummer 1 ist der Startzustand, die "
                + "Zustände werden fortlaufend nummeriert und der "
                + "letzte Zustand ist der Endzustand.");
        System.out.println("ADD Zahl1 Zahl2 Zeichen | Fügt einen neuen Zustand "
                + "in den Automaten ein, der vom Zustand mit der "
                + "Nummer Zahl1 in den Zustand mit der Nummer Zahl2 "
                + "mit dem angegebenen Zeichen übergeht. Für "
                + "Spontanübergänge " + LambdaNFA.LAMBDA
                + " benutzen. Möchte man eine bereits vorhandene Kante"
                + " einfügen, so wird der Automat nicht verändert.");
        System.out.println("CHECK \"Wort\" | Gibt an, ob das in "
                + "Anführungszeichen angegebene Wort in der Sprache "
                + "des Automaten ist.");
        System.out.println("PREFIX \"Wort\" | Gibt das längste in der Sprache "
                + "enthaltene Präfix des angegeben Wortes in "
                + "Anführungszeichen zurück oder den Text 'No prefix "
                + "in language.', falls kein Präfix des Wortes in der "
                + "Sprache des Automaten ist.");
        System.out.println("GENERATE | Initialisiert einen im Programm bereits "
                + "vordefinierten Automaten. Dieser besitzt folgende"
                + " Transitionen und die darin enthaltenen Zustände:"
                + "\n(1, 2) a\n(1, 3) b\n(1, 4) ~\n"
                + "(2, 2) a\n(2, 3) ~\n(2, 3) a\n(3, 4) a\n(3, 4) b\n"
                + "(4, 5) a\n(4, 5) b");
        System.out.println("DISPLAY | Gibt alle im aktuell geladenen Automaten "
                + "vorhandenen Transitionen untereinander und "
                + "lexikographisch sortiert im Format '(Startzustand, "
                + "Endzustand) Übergangssymbol' an.");
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
        boolean hasNoAutomaton = true;
        while (continueInput) {
            System.out.print("nfa> ");
            Scanner instructionScanner = new Scanner(reader.readLine());
            if (instructionScanner.hasNext()) {
                String command = instructionScanner.next();
                char instructor = command.toUpperCase().charAt(0);
                switch (instructor) {
                case 'I':
                    hasNoAutomaton = initAutomaton(instructionScanner);
                    break;
                case 'A':
                    addTransition(instructionScanner, hasNoAutomaton);
                    break;
                case 'C':
                    isElement(instructionScanner, hasNoAutomaton);
                    break;
                case 'P':
                    longestPrefix(instructionScanner, hasNoAutomaton);
                    break;
                case 'G':
                    hasNoAutomaton = generateAutomaton();
                    break;
                case 'D':
                    displayAutomaton(hasNoAutomaton);
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
