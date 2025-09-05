import java.util.Scanner;
import java.io.*;

public class TheDoctor {
    private static UnweightedGraph graph = new UnweightedGraph();
    private static Scanner scanner = new Scanner(System.in);

    // Simple method to clear screen
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For Unix/Linux/Mac
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // If clearing fails, just print some newlines
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }
    }

    public static void menu() {
        System.out.println("Welcome to The Doctor!\n");
        System.out.println("\nMenu: Enter '1' to '3' to continue.");
        System.out.println("-".repeat(36));
        System.out.println("      1. Create Graph");
        System.out.println("      2. Search for a Disease");
        System.out.println("      3. View Disease Network");
        System.out.println("      0. Exit");
        System.out.print("  Selection: ");
    }

    public static void createGraph() {
        System.out.println("Create Graph: Enter '1' to '5' for updating the graph.");
        System.out.println("-".repeat(56));
        System.out.println("      1. Add a disease");
        System.out.println("      2. Remove a disease");
        System.out.println("      3. Add an edge");
        System.out.println("      4. Remove an edge");
        System.out.println("      0. Return to the main menu");
        System.out.print("  Selection: ");
    }

    // Display the main menu
    public static void showMenu() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║            THE DOCTOR                  ║");
        System.out.println("║        Disease Network System          ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║  1. Add Disease                        ║");
        System.out.println("║  2. Remove Disease                     ║");
        System.out.println("║  3. Connect Diseases                   ║");
        System.out.println("║  4. Remove Connection                  ║");
        System.out.println("║  5. Search Disease                     ║");
        System.out.println("║  6. Traverse Network                   ║");
        System.out.println("║  7. Display Network                    ║");
        System.out.println("║  8. Save to File                       ║");
        System.out.println("║  9. Load from File                     ║");
        System.out.println("║  0. Exit                               ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.print("Enter your choice (0-9): ");
    }

    // Get valid integer input from menu
    public static int getIntInput(int min, int max) {
        while (true) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.print("Please enter a number between " + min + " and " + max + ": ");
                }
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.nextLine(); // clear invalid input
            }
        }
    }

    // Get valid integer input from create graph
    public static int getIntInputAgain(int min, int max) {
        while (true) {
            try {
                int choice2 = scanner.nextInt();
                scanner.nextLine(); // consume newline

                if (choice2 >= min && choice2 <= max) {
                    return choice2;
                } else {
                    System.out.print("Please enter a number between " + min + " and " + max + ": ");
                }
            } catch (Exception e) {
                System.out.print("Invalid input. Please enter a number: ");
                scanner.nextLine(); // clear invalid input
            }
        }
    }

    // Get non-empty string input
    public static String getStringInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    // Add a disease to the network
    public static void addDisease() {
        System.out.println("\n--- Add Disease ---");
        while (true) {
            String disease = getStringInput("Enter disease name: ");

            if (graph.addVertex(disease)) {
                System.out.println("Successfully added: " + disease);
            } else {
                System.out.println("Disease '" + disease + "' already exists!");
            }

            // Prompt to continue
            String continueChoice;
            do {
                System.out.print("Continue? (Y/N): ");
                continueChoice = scanner.nextLine().trim().toUpperCase();
                if (!continueChoice.equals("Y") && !continueChoice.equals("N")) {
                    System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                }
            } while (!continueChoice.equals("Y") && !continueChoice.equals("N"));

            if (continueChoice.equals("N")) {
                break; // Exit the loop
            }
            // If 'Y', loop continues to prompt for another disease
        }

        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    // Remove a disease from the network
    public static void removeDisease() {
        System.out.println("\n--- Remove Disease ---");
        if (graph.getSize() == 0) {
            System.out.println("No diseases in the network.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        System.out.println("Current diseases: ");
        String[] diseases = graph.getAllDiseases();
        for (int i = 0; i < diseases.length; i++) {
            System.out.println((i + 1) + ". " + diseases[i]);
        }

        String disease = getStringInput("Enter disease name to remove: ");

        if (graph.removeVertex(disease)) {
            System.out.println("Successfully removed: " + disease);
        } else {
            System.out.println("Disease '" + disease + "' not found!");
        }

        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    // Connect two diseases
    public static void connectDiseases() {
        System.out.println("\n--- Connect Diseases ---");
        if (graph.getSize() < 2) {
            System.out.println("Need at least 2 diseases to create a connection.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        System.out.println("Available diseases: ");
        String[] diseases = graph.getAllDiseases();
        for (int i = 0; i < diseases.length; i++) {
            System.out.println((i + 1) + ". " + diseases[i]);
        }

        String disease1 = getStringInput("Enter first disease: ");
        String disease2 = getStringInput("Enter second disease: ");

        if (disease1.equals(disease2)) {
            System.out.println("Cannot connect a disease to itself!");
        } else if (graph.addEdge(disease1, disease2)) {
            System.out.println("Successfully connected: " + disease1 + " ↔ " + disease2);
        } else {
            System.out.println(" Connection failed. Check if both diseases exist and aren't already connected.");
        }

        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    // Remove connection between diseases
    public static void removeConnection() {
        System.out.println("\n--- Remove Connection ---");
        String disease1 = getStringInput("Enter first disease: ");
        String disease2 = getStringInput("Enter second disease: ");

        if (graph.removeEdge(disease1, disease2)) {
            System.out.println("Successfully removed connection between: " + disease1 + " and " + disease2);
        } else {
            System.out.println(" Connection not found or diseases don't exist.");
        }

        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    // Search for a disease and show its connections
    public static void searchDisease() {
        System.out.println("\n--- Search Disease ---");
        String disease = getStringInput("Enter disease name to search: ");

        if (!graph.hasVertex(disease)) {
            System.out.println(" Disease '" + disease + "' not found in the network.");
        } else {
            System.out.println("Found: " + disease);
            String[] neighbors = graph.getNeighbors(disease);

            if (neighbors.length == 0) {
                System.out.println("  No connected diseases.");
            } else {
                System.out.println("  Connected to:");
                for (String neighbor : neighbors) {
                    System.out.println("    → " + neighbor);
                }
            }
        }

        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    // Traverse the network starting from a disease
    public static void traverseNetwork() {
        System.out.println("\n--- Traverse Network ---");
        if (graph.getSize() == 0) {
            System.out.println("No diseases in the network.");
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
            return;
        }

        String disease = getStringInput("Enter starting disease for traversal: ");
        graph.traverseFrom(disease);

        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    // Display the entire network
    public static void displayNetwork() {
        System.out.println("\n--- Display Network ---");
        graph.displayGraph();
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    // Save network to file
    public static void saveToFile() {
        System.out.println("\n--- Save to File ---");
        try {
            // Save diseases
            FileWriter diseaseWriter = new FileWriter("diseases.txt");
            String[] diseases = graph.getAllDiseases();
            for (String disease : diseases) {
                diseaseWriter.write(disease + "\n");
            }
            diseaseWriter.close();

            // Save connections
            FileWriter connectionWriter = new FileWriter("connections.txt");
            for (String disease : diseases) {
                String[] neighbors = graph.getNeighbors(disease);
                for (String neighbor : neighbors) {
                    // Only write each connection once (avoid duplicates)
                    if (disease.compareTo(neighbor) < 0) {
                        connectionWriter.write(disease + "," + neighbor + "\n");
                    }
                }
            }
            connectionWriter.close();

            System.out.println("Network saved successfully!");
        } catch (IOException e) {
            System.out.println(" Error saving to file: " + e.getMessage());
        }

        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    // Load network from file
    public static void loadFromFile() {
        System.out.println("\n--- Load from File ---");
        try {
            // Load diseases
            BufferedReader diseaseReader = new BufferedReader(new FileReader("diseases.txt"));
            String disease;
            while ((disease = diseaseReader.readLine()) != null) {
                disease = disease.trim();
                if (!disease.isEmpty()) {
                    graph.addVertex(disease);
                }
            }
            diseaseReader.close();

            // Load connections
            BufferedReader connectionReader = new BufferedReader(new FileReader("connections.txt"));
            String connection;
            while ((connection = connectionReader.readLine()) != null) {
                String[] parts = connection.split(",");
                if (parts.length == 2) {
                    graph.addEdge(parts[0].trim(), parts[1].trim());
                }
            }
            connectionReader.close();

            System.out.println("Network loaded successfully!");
        } catch (FileNotFoundException e) {
            System.out.println(" No saved files found.");
        } catch (IOException e) {
            System.out.println(" Error loading from file: " + e.getMessage());
        }

        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    public static void viewNetwork() {
        try {
            NetworkViewer.setGraph(graph);
            NetworkViewer.show(graph); // Call show instead of launching a new thread
        } catch (Exception e) {
            System.out.println("Error launching network viewer: " + e.getMessage());
            System.out.print("Press Enter to continue...");
            scanner.nextLine();
        }
    }

    // Main program
    public static void main(String[] args) {
        System.out.println("Starting Disease Network System...");

        while (true) {
            clearScreen();
            Art.Logo();
            menu();

            int choice = getIntInput(0, 3);

            switch (choice) {
                case 0:
                    System.out.println("\nThank you for using The Doctor!");
                    System.out.println("Goodbye!");
                    scanner.close();
                    return;

                case 1:
                    clearScreen();
                    Art.Logo();
                    createGraph();
                    

                    int choice2 = getIntInputAgain(0, 4);

                    switch (choice2) {

                        case 0:
                            menu();
                            break;

                        case 1:
                            addDisease();
                            break;
                        case 2:
                            removeDisease();
                            break;
                        case 3:
                            connectDiseases();
                            break;
                        case 4:
                            removeConnection();
                            break;

                    }
                    break;

                case 2:
                    searchDisease();
                    
                    break;
                case 3:
                    viewNetwork();
                    break;
                    
                
                
            }
        }
    }
}