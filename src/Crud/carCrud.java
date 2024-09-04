package Crud;
import Auto136.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class carCrud {
    private static final Scanner sc = new Scanner(System.in);

    public static Car.Status checkStatus(String status) {
        Car.Status stat = null;
        if (status.equals("available")) {
            stat = Car.Status.AVAILABLE;
        } else if (status.equals("sold")) {
            stat = Car.Status.SOLD;
        }
        return stat;
    }

    public static void createCar() {

        System.out.println("Enter car make: ");
        String make = sc.nextLine();
        System.out.println("Enter car model: ");
        String model = sc.nextLine();
        System.out.println("Enter car year: ");
        int year = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter car mileage: ");
        int mileage = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter car color: ");
        String color = sc.nextLine();
        System.out.println("Enter car status(available/sold): ");
        String status = sc.nextLine();
        System.out.println("Enter car price: ");
        double price = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter car notes: ");
        String notes = sc.nextLine();

        Car.Status stat = checkStatus(status);

        Car newCar = new Car(make, model, year,mileage,color,stat,price,notes);
        System.out.printf("added car %s%n", newCar.getCarId());

    }

    public static void readCar() {
        System.out.println("Enter specific ID or a for all:");
        String input = sc.nextLine();
        String line;
        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader("car.txt"));
            if (input.equals("a")) {
                while((line = reader.readLine()) != null) {
                    String[] row = line.split(",");
                    for (String i: row) {
                        System.out.printf("%-10s", i);
                    }
                    System.out.println();
                }
            } else {
                while((line = reader.readLine()) != null) {
                    String[] row = line.split(",");
                    if (input.equals(row[0])) {
                        for (String i: row) {
                            System.out.printf("%-10s", i);
                        }
                        System.out.println();
                    }
                }
            }
            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void updateCar() {
        final int ID_INDEX = 0;
        final int MAKE_INDEX = 1;
        final int MODEL_INDEX = 2;
        final int YEAR_INDEX = 3;
        final int MILEAGE_INDEX = 4;
        final int COLOR_INDEX = 5;
        final int STATUS_INDEX = 6;
        final int PRICE_INDEX = 7;
        final int NOTES_INDEX = 8;
        Path filePath = Paths.get("car.txt");
        System.out.println("Enter a specific ID:");
        String input = sc.nextLine();
        System.out.println("Enter the field to edit (e.g., 'price'):");
        String fieldToEdit = sc.nextLine().trim().toLowerCase();
        System.out.println("Enter the new value for the field:");
        String newValue = sc.nextLine().trim();

        try {
            // Read all lines from the file into a List
            List<String> lines = Files.readAllLines(filePath);

            int fieldIndex;
            switch (fieldToEdit) {
                case "make":
                    fieldIndex = MAKE_INDEX;
                    break;
                case "model":
                    fieldIndex = MODEL_INDEX;
                    break;
                case "year":
                    fieldIndex = YEAR_INDEX;
                    break;
                case "mileage":
                    fieldIndex = MILEAGE_INDEX;
                    break;
                case "color":
                    fieldIndex = COLOR_INDEX;
                    break;
                case "status":
                    fieldIndex = STATUS_INDEX;
                    break;
                case "price":
                    fieldIndex = PRICE_INDEX;
                    break;
                case "notes":
                    fieldIndex = NOTES_INDEX;
                    break;
                default:
                    System.out.println("Invalid field specified.");
                    return;
            }
            // Filter out the lines that start with the user input
            List<String> updatedLines = lines.stream()
                    .map(line -> {
                        String[] parts = line.split(",");
                        if (parts.length > fieldIndex && parts[ID_INDEX].equals(input)) {
                            parts[fieldIndex] = newValue;
                            return String.join(",", parts);
                        }
                        return line;
                    })
                    .collect(Collectors.toList());

            // Write the modified lines back to the file
            Files.write(filePath, updatedLines);
            System.out.println("Updated successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void deleteCar() {
        Path filePath = Paths.get("car.txt");
        System.out.println("Enter a specific ID to delete:");
        String input = sc.nextLine();

        try {
            // Read all lines from the file into a List
            List<String> lines = Files.readAllLines(filePath);

            // Filter out the lines that start with the user input
            List<String> updatedLines = lines.stream()
                    .filter(line -> !line.startsWith(input + ","))
                    .collect(Collectors.toList());

            // Write the modified lines back to the file
            Files.write(filePath, updatedLines);
            System.out.println("Car deleted");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void carControl() {
        File file = new File("car.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        boolean isActive = true;
        do {
            System.out.println("1: Add a car");
            System.out.println("2: View car");
            System.out.println("3: Update car");
            System.out.println("4: Delete car");
            System.out.println("0: exit");
            String input = sc.nextLine();

            switch (input) {
                case "1":
                    createCar();
                    break;
                case "2":
                    readCar();
                    break;
                case "3":
                    updateCar();
                    break;
                case "4":
                    deleteCar();
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
