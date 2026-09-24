package ca.hccis.files;

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

    /**
     * File where the order information is stored.
     */
    public static final String PATH_NAME = "c:\\cis2232\\data_abacousnac_thomal.json";

    public static void main(String[] args) {

        initialize();

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
     * Processing for menu option A.
     * <p>
     * Adds a new food order and saves it to the file.
     */
    public static void add() {
        FoodOrder newOrder = new FoodOrder();
        System.out.println("--Add Food Order--");
        newOrder.getInformation();
        orderList.add(newOrder);
        writeAll();
        System.out.println(MESSAGE_SUCCESS + ": Food order was added.");
    }

    /**
     * Processing for menu option V.
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

        } catch (IOException e) {
            System.out.println(MESSAGE_ERROR + ": Unable to initialize the application.");
            e.printStackTrace();
        }
    }
}