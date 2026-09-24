package ca.hccis.files;

import ca.hccis.files.entity.Camper;
import ca.hccis.util.CisUtility;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

/**
 * Controls the overall flow of the program.
 *
 * @author cis2232
 * @since 20260917
 */
public class Controller {

    public static final int EXIT = 0;

    public static final String MENU = "1) Add" + System.lineSeparator()
            + "2) Edit" + System.lineSeparator()
            + "3) View" + System.lineSeparator()
            + EXIT + ") Exit"
            + System.lineSeparator();

    public static final String MESSAGE_ERROR = "Error";
    public static final String MESSAGE_EXIT = "Goodbye";
    public static final String MESSAGE_SUCCESS = "Success";

    private static HashMap<Integer, Camper> camperMap = new HashMap();
    private static Gson gson = new Gson();

    //TODO if the cis2232 folder does not exist, then have your program create it.
    //TODO filename to be changed from campers based on assignment requirements.
    public static final String PATH_NAME = "E:\\cis2232\\campers.json";

    public static void main(String[] args) {

        initialize();

        //Gson
//        Camper test = camperMap.get(22334);
//        String camperJson = gson.toJson(test);
//        IO.println(camperJson);
//
//        Camper camperFromJson = gson.fromJson(camperJson, Camper.class);
//        System.out.println(camperFromJson.toString());


        int menuOption;

        do {
            menuOption = CisUtility.getInputInt(MENU);

            switch (menuOption) {
                case EXIT:
                    System.out.println(MESSAGE_EXIT);
                    break; //Break out of the loop as we're finished.
                case 1:
                    add();
                    break;
                case 2:
                    edit();
                    break;
                case 3:
                    viewAll();
                    break;
                default:
                    System.out.println(MESSAGE_ERROR);
                    break;
            }
        } while (menuOption != EXIT);
    }

    /**
     * Processing for menu option 1
     *
     * @author
     * @since
     */
    public static void add() {
        Camper newCamper = new Camper();
        IO.println("--Add Camper--");
        newCamper.getInformation();

        //TODO what if the registration id already exists.  Give the user a warning and ask if they want to overwrite the row.
        //read file nad see if the new camper is already there, and if so check with user to see if should overwrite

        int registrationId = newCamper.getRegistrationId();

        // Check if the registration ID already exists.
        if (camperMap.containsKey(registrationId)) {
            System.out.println("A camper with registration ID " + registrationId + " already exists");
            String answer = CisUtility.getInputString("Do you want to overwrite this camper? (Y/N): ");
            if (answer.equalsIgnoreCase("Y")) {
                camperMap.put(registrationId, newCamper);
                writeAll();
                System.out.println(MESSAGE_SUCCESS + ": Camper was overwritten.");
            } else {
                System.out.println("Camper was not added.");
            }
        } else {
            camperMap.put(registrationId, newCamper);
            writeAll();
            System.out.println(MESSAGE_SUCCESS + ": Camper was added.");
        }
    }

    /**
     * Processing for menu option 2.
     *
     * @author
     * @since
     */
    public static void edit() {
        System.out.println("--Edit Camper--");
        int regID = CisUtility.getInputInt("Reg ID: ");
        Camper editingCamper = camperMap.get(regID);
        if (editingCamper == null) {
            System.out.println(MESSAGE_ERROR + ": No camper was found with registration ID " + regID + ".");
            return;
        }
        editingCamper.edit();
        //TODO What if the regID not found?
        //Handle this situation.
        writeAll(); //save to file
        System.out.println(MESSAGE_SUCCESS + ": Camper was updated.");
    }

    /**
     * Processing for menu option 3.
     *
     * @author
     * @since
     */
    public static void viewAll() {
        System.out.println("--View All Campers--");
        readAll();
        if (camperMap.isEmpty()) {
            System.out.println("No campers found.");
        } else {
            for (Camper current : camperMap.values()) {
                System.out.println(current);
            }
        }
        //TODO Need to show all the campers.  Note want to show the latest from the file, not just
        //what is currently in the map.
    }


    public static void writeAll() {
        try {
            FileWriter writer = new FileWriter(PATH_NAME, false);
            for (Camper current : camperMap.values()) {
                writer.append(gson.toJson(current));
                writer.append(System.lineSeparator());
                System.out.println("Successfully written JSON string to file.");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readAll() {
        try {
            FileReader reader = new FileReader(PATH_NAME);
            List<String> lines = reader.readAllLines();
            for (int i = 0; i < lines.size(); i++) {
                Camper camperFromJson = gson.fromJson(lines.get(i), Camper.class);
                camperMap.put(camperFromJson.getRegistrationId(), camperFromJson);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void initialize() {

        Path path = Paths.get(PATH_NAME);

        // Create the cis2232 folder if it does not exist.
        Path parentDirectory = path.getParent();

        if (!Files.exists(parentDirectory)) {
            try {
                Files.createDirectories(parentDirectory);
                System.out.println("cis2232 folder created.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Check if the file exists
        if (Files.exists(path)) {
            System.out.println("Campers exist.");
            readAll();
        } else {


            Camper camper = new Camper(1, 22334, "Bob", "Stephens", "2020-01-05");
            Camper camper2 = new Camper(2, 22335, "Alice", "Johnson", "2019-07-14");
            Camper camper3 = new Camper(3, 22336, "Charlie", "Williams", "2021-03-22");
            Camper camper4 = new Camper(4, 22337, "Diana", "Brown", "2020-11-09");
            Camper camper5 = new Camper(5, 22338, "Ethan", "Miller", "2018-05-17");
            camperMap.put(camper.getRegistrationId(), camper);
            camperMap.put(camper2.getRegistrationId(), camper2);
            camperMap.put(camper3.getRegistrationId(), camper3);
            camperMap.put(camper4.getRegistrationId(), camper4);
            camperMap.put(camper5.getRegistrationId(), camper5);

            writeAll();
        }

    }
}
