package Crud;

import Auto136.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static Auto136.AutoPart.*;


public class AutoPartCRUD {
    private static final Scanner sc = new Scanner(System.in);
    private static final String filename = "part.txt";

    public static AutoPart.Condition checkCondition(String condition) {
        AutoPart.Condition newCondition = null;
        if (condition.equals("new")) {
            newCondition = Condition.NEW;
        } else if (condition.equals("used")) {
            newCondition = Condition.USED;
        } else if (condition.equals("refurbished")) {
            newCondition = Condition.REFURBISHED;
        }
        return newCondition;
    }

    // Method to read from file
    public static List<AutoPart> readPartsFromFile() {
        List<AutoPart> parts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                try {
                    AutoPart part = new AutoPart(fields[0], fields[1], fields[2], Integer.parseInt(fields[3]),
                            AutoPart.Condition.valueOf(fields[4]), fields[5],
                            Double.parseDouble(fields[6]), fields[7]);
                    parts.add(part);

                    int id = Integer.parseInt(fields[0].substring(2));
                    if (id >= partCounter) {
                        partCounter = id + 1;
                    }
                } catch (Exception e) {
                    System.out.println("An unexpected error occurred while creating the part: " + e.getMessage());
                    System.exit(0);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return parts;
    }

    // Method to write to file
    public static void writePartsToFile(List<AutoPart> parts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (AutoPart part : parts) {
                writer.write(part.getPartID() + "," +
                        part.getPartName() + "," +
                        part.getManufacturer() + "," +
                        part.getPartNumber() + "," +
                        part.getCondition() + "," +
                        part.getWarranty() + "," +
                        part.getCost() + "," +
                        part.getNotes());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to create a part
    public static void createPart() {
        List<AutoPart> parts = readPartsFromFile();
        String partName;
        String manufacturer;
        int partNumber = 0;
        String condition = "";
        String warranty;
        double cost = 0.0;
        String notes;

        do {
            System.out.println("Enter part name: ");
            partName = sc.nextLine().trim();
            if (partName.isEmpty()) {
                System.out.println("Part name cannot be empty. Please try again.");
            }
        } while (partName.isEmpty());


        do {
            System.out.println("Enter part manufacturer: ");
            manufacturer = sc.nextLine().trim();
            if (manufacturer.isEmpty()) {
                System.out.println("Part manufacturer cannot be empty. Please try again.");
            }
        } while (manufacturer.isEmpty());

        boolean validPartNumber = false;
        while (!validPartNumber) {
            System.out.println("Enter part number: ");
            if (sc.hasNextInt()) {
                partNumber = sc.nextInt();
                sc.nextLine();
                if (partNumber >= 0) {
                    validPartNumber = true;
                } else {
                    System.out.println("Part number must be higher than 0. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for part.");
                sc.next();
            }
        }

        boolean validCondition = false;
        while (!validCondition) {
            System.out.println("Enter part condition (new/used/refurbished): ");
            condition = sc.nextLine().toLowerCase();
            if (condition.equals("new") || condition.equals("used") || condition.equals("refurbished")) {
                validCondition = true;
            } else {
                System.out.println("Condition must be either 'new' or 'used' or 'refurbished'. Please try again.");
            }
        }
        do {
            System.out.println("Enter warranty: ");
            warranty = sc.nextLine();
            if (warranty.isEmpty()) {
                System.out.println("Part warranty cannot be empty. Please try again.");
            }
        } while (warranty.isEmpty());

        boolean validCost = false;
        while (!validCost) {
            System.out.println("Enter part cost: ");
            if (sc.hasNextDouble()) {
                cost = sc.nextDouble();
                sc.nextLine();
                if (cost > 0) {
                    validCost = true;
                } else {
                    System.out.println("Cost must be a positive number. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for the cost.");
                sc.next();
            }
        }
        System.out.println("Enter part notes: ");
        notes = sc.nextLine();
        if (notes.isEmpty()) {
            notes = "none";
        }

        AutoPart.Condition newCondition = checkCondition(condition);
        String newPartId = generatePartId();
        AutoPart newPart = new AutoPart(newPartId,partName,manufacturer,partNumber,newCondition,warranty,cost,notes);
        parts.add(newPart);
        writePartsToFile(parts);
        System.out.printf("AutoPart Added ID: %s%n", newPart.getPartID());
    }


    // Method to display part read from file
    public static void readPart() {
        List<AutoPart> parts = readPartsFromFile();
        System.out.println("Enter specific ID or a for all:");
        String input = sc.nextLine();
        boolean exist = false;
        if (input.equals("a")) {
            for (AutoPart part : parts) {
                System.out.println(part);
            }
        } else {
            for (AutoPart part : parts) {
                if (input.equals(part.getPartID())) {
                    exist = true;
                    System.out.println(part);
                }
            }
            if (!exist) System.out.println("Part does not exist");
        }
    }

    // Method to update a single data from part
    public static void updatePart() {
        List<AutoPart> parts = readPartsFromFile();

        System.out.println("Enter part id: ");
        String id = sc.nextLine();
        boolean exist = false;
        for (AutoPart part : parts) {
            if (id.equals(part.getPartID())) {
                exist = true;
                System.out.println("Enter data to update");
                String data = sc.nextLine();
                boolean invalid = false;
                switch (data) {
                    case "name":
                        System.out.println("Enter part name: ");
                        part.setPartName(sc.nextLine());
                        break;
                    case "manufacturer":
                        System.out.println("Enter manufacturer: ");
                        part.setManufacturer(sc.nextLine());
                        break;
                    case "number":
                        System.out.println("Enter part number: ");
                        part.setPartNumber(sc.nextInt());
                        sc.nextLine();
                        break;
                    case "condition":
                        System.out.println("Enter part condition: ");
                        part.setCondition(checkCondition(sc.nextLine()));
                        break;
                    case "warranty":
                        System.out.println("Enter part warranty: ");
                        part.setWarranty(sc.nextLine());
                        break;
                    case "cost":
                        System.out.println("Enter part cost: ");
                        part.setCost(sc.nextDouble());
                        sc.nextLine();
                        break;
                    case "notes":
                        System.out.println("Enter part notes: ");
                        part.setNotes(sc.nextLine());
                        break;
                    default:
                        invalid = true;
                        System.out.println("No matching field found. Please enter a valid field.");
                        break;
                }
                if (!invalid) {
                    writePartsToFile(parts);
                    System.out.println("Updated: " + id);
                    break;
                }
            }
        }
        if (!exist) {
            System.out.println("No such ID");
        }
    }

    // Method to delete a part
    public static void deletePart() {
        List<AutoPart> parts = readPartsFromFile();
        System.out.println("Enter a specific ID to delete:");
        String input = sc.nextLine();
        boolean exist = false;
        for (AutoPart part : parts) {
            if (input.equals(part.getPartID())) {
                exist = true;
                parts.remove(part);
                break;
            }
        }
        if (exist) {
            writePartsToFile(parts);
            System.out.println("Part deleted");
        } else {
            System.out.println("Part does not exist");
        }
    }

    // Main control for crud operation
    public static void partControl() {
        File file = new File(filename);
        // If file does not exist, create it
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        boolean isActive = true;
        do {
            System.out.println("1: Add a part");
            System.out.println("2: View part");
            System.out.println("3: Update part");
            System.out.println("4: Delete part");
            System.out.println("0: exit");
            String input = sc.nextLine();

            switch (input) {
                case "1":
                    createPart();
                    break;
                case "2":
                    readPart();
                    break;
                case "3":
                    updatePart();
                    break;
                case "4":
                    deletePart();
                    break;
                case "0":
                    isActive = false;
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
        } while (isActive);
    }
}
