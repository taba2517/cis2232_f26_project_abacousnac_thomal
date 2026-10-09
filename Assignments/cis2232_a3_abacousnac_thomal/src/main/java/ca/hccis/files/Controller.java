package ca.hccis.files;

import ca.hccis.files.bo.FoodOrderBO;
import ca.hccis.files.entity.FoodOrder;
import ca.hccis.util.CisUtility;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Dimension;

/**
 * Controls the overall flow of the program.
 *
 * @author Thomal Abacousnac
 * @since 20260923
 */
public class Controller {
    public static final String EXIT = "X";
    public static final String MENU = "A) Add" + System.lineSeparator()
            + "V) View" + System.lineSeparator()
            + "X) eXit" + System.lineSeparator();

    public static final String MESSAGE_ERROR = "Error";
    public static final String MESSAGE_EXIT = "Goodbye";
    public static final String MESSAGE_SUCCESS = "Success";
    private static List<FoodOrder> orderList = new ArrayList<>();
    private static Gson gson = new Gson();
    private static FoodOrderBO foodOrderBO = new FoodOrderBO();

    /**
     * Shared accumulator for the total number of food orders.
     */
    private static int totalOrders = 0;

    /**
     * File where the order information is stored.
     */
    public static final String PATH_NAME = "c:\\cis2232\\data_abacousnac_thomal.json";

    public static void main(String[] args) {

        initialize();

        // Thread 1: Console interface.
        Thread consoleThread = new Thread(() -> runConsoleMenu(), "Console-Thread");

        consoleThread.start();

        // Thread 2: JOptionPane interface.
        SwingUtilities.invokeLater(() -> runJOptionPaneMenu());
    }

    /**
     * Runs the console menu in its own thread.
     */
    public static void runConsoleMenu() {
        String menuOption;

        do {
            menuOption = CisUtility.getInputString(MENU);
            switch (menuOption.toUpperCase()) {
                case "A":
                    add();
                    break;
                case "V":
                    viewAll();
                    break;
                case EXIT:
                    System.out.println(MESSAGE_EXIT);
                    break;
                default:
                    System.out.println(MESSAGE_ERROR);
                    break;
            }
        } while (!menuOption.equalsIgnoreCase(EXIT));
    }

    /**
     * Runs the JOptionPane menu independently.
     */
    public static void runJOptionPaneMenu() {
        boolean running = true;
        while (running) {
            String option = (String) JOptionPane.showInputDialog(
                    null,
                    "Select an option:",
                    "Food Ordering System",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{
                            "A) Add",
                            "V) View",
                            "X) Exit"
                    },
                    "A) Add"
            );
            if (option == null || option.startsWith("X")) {
                running = false;
                continue;
            }
            switch (option.substring(0, 1)) {
                case "A":
                    addJOptionPane();
                    break;
                case "V":
                    viewJOptionPane();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, MESSAGE_ERROR);
                    break;
            }
        }
    }

    /**
     * Processing for menu add.
     * <p>
     * Adds a new food order and calculates its total cost.
     */
    public static void add() {
        FoodOrder newOrder = new FoodOrder();
        System.out.println("--Add Food Order--");
        newOrder.getInformation();
        // Calculate the total cost using Assignment #2.
        double totalCost = foodOrderBO.calculate(newOrder);
        newOrder.setTotalCost(totalCost);
        orderList.add(newOrder);
        writeAll();
        int currentTotal = incrementTotalOrders();
        System.out.println("Combined total orders: " + currentTotal);
        System.out.printf("Total Cost: $%.2f%n", newOrder.getTotalCost());
        System.out.println(MESSAGE_SUCCESS + ": Food order was added.");
    }

    /**
     * Processing for menu viewAll.
     * <p>
     * Displays all food orders stored in the file.
     */
    public static void viewAll() {
        System.out.println("--View All Food Orders--");

        // Read the latest information from the file.
        readAll();
        if (orderList.isEmpty()) {
            System.out.println("No food orders found.");
        } else {
            for (FoodOrder current : orderList) {
                System.out.println(current);
            }
        }
    }

    /**
     * Writes all food orders to the JSON file.
     */
    public static void writeAll() {
        try {
            Path path = Paths.get(PATH_NAME);
            // Create the directory if it does not exist.
            Path parentDirectory = path.getParent();
            if (parentDirectory != null
                    && !Files.exists(parentDirectory)) {
                Files.createDirectories(parentDirectory);
            }
            try (FileWriter writer = new FileWriter(PATH_NAME, false)) {
                for (FoodOrder current : orderList) {
                    writer.append(gson.toJson(current));
                    writer.append(System.lineSeparator());
                }
            }
            System.out.println(MESSAGE_SUCCESS + ": Orders successfully saved.");
        } catch (IOException e) {
            System.out.println(MESSAGE_ERROR + ": Unable to write orders to file.");
            e.printStackTrace();
        }
    }

    /**
     * Reads all food orders from the JSON file.
     */
    public static void readAll() {
        Path path = Paths.get(PATH_NAME);
        if (!Files.exists(path)) {
            return;
        }

        try {
            // Clear the current list so that the file
            // represents the latest data.
            orderList.clear();
            List<String> lines = Files.readAllLines(path);
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    FoodOrder orderFromJson =
                            gson.fromJson(line, FoodOrder.class);
                    if (orderFromJson != null) {
                        orderList.add(orderFromJson);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(MESSAGE_ERROR + ": Unable to read orders from file.");
            e.printStackTrace();
        }
    }

    /**
     * Adds a food order through JOptionPane.
     */
    public static void addJOptionPane() {
        FoodOrder newOrder = new FoodOrder();
        try {
            newOrder.getInformationJOptionPane();
            // Reuse the Assignment #2 calculation.
            double totalCost = foodOrderBO.calculate(newOrder);
            newOrder.setTotalCost(totalCost);
            orderList.add(newOrder);
            writeAll();
            int currentTotal = incrementTotalOrders();
            JOptionPane.showMessageDialog(
                    null,
                    "Food order added successfully."
                            + System.lineSeparator()
                            + String.format(
                            "Total cost: $%.2f",
                            newOrder.getTotalCost()
                    )
            );
        } catch (java.util.concurrent.CancellationException e) {
            JOptionPane.showMessageDialog(null, "Order entry cancelled.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Please enter valid numbers for quantity and price.",
                    MESSAGE_ERROR,
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Displays all food orders in a scrollable dialog.
     */
    public static void viewJOptionPane() {
        if (orderList.isEmpty()) {
            JOptionPane.showMessageDialog(
                    null,
                    "No food orders found."
            );
            return;
        }
        StringBuilder output = new StringBuilder();
        for (FoodOrder order : orderList) {
            output.append(order.toString())
                    .append(System.lineSeparator())
                    .append("----------------------------------------")
                    .append(System.lineSeparator())
                    .append(System.lineSeparator());
        }
        output.append("Combined total orders: ")
                .append(getTotalOrders());
        // Create a text area to display all orders.
        JTextArea textArea = new JTextArea(output.toString());
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setCaretPosition(0);
        // Add scrolling support.
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(550, 400));
        JOptionPane.showMessageDialog(
                null,
                scrollPane,
                "All Food Orders",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Initializes the application.
     * <p>
     * Creates the cis2232 directory if necessary.
     * If the JSON file exists, existing orders are loaded.
     * Otherwise, an empty JSON file is created.
     */
    public static void initialize() {
        Path path = Paths.get(PATH_NAME);
        try {
            // Create the cis2232 directory if it does not exist.
            Path parentDirectory = path.getParent();
            if (parentDirectory != null
                    && !Files.exists(parentDirectory)) {
                Files.createDirectories(parentDirectory);
                System.out.println("cis2232 folder created.");
            }
            // Check if the JSON file exists.
            if (Files.exists(path)) {
                System.out.println("Existing orders found.");
                readAll();
            } else {
                // Create the empty file.
                Files.createFile(path);
                System.out.println("Order file created.");
            }

            // Initialize the shared accumulator.
            totalOrders = orderList.size();

            System.out.println(
                    "Current total orders: " + totalOrders
            );

        } catch (IOException e) {
            System.out.println(MESSAGE_ERROR + ": Unable to initialize the application.");
            e.printStackTrace();
        }
    }

    /**
     * Increments the shared order accumulator.
     */
    public static synchronized int incrementTotalOrders() {
        totalOrders++;
        return totalOrders;
    }

    /**
     * Returns the current shared order count.
     */
    public static synchronized int getTotalOrders() {
        return totalOrders;
    }
}