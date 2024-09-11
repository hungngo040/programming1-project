package Crud;

import Auto136.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static Auto136.Car.*;

public class CarCRUD {
    private static final Scanner sc = new Scanner(System.in);
    private static final String filename = "car.txt";


    // Method to read from file
    public static List<Car> readCarsFromFile() {
        List<Car> cars = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                try {
                    Car car = new Car(fields[0], fields[1], fields[2], Integer.parseInt(fields[3]),
                            Integer.parseInt(fields[4]), fields[5],
                            Car.Status.valueOf(fields[6]),
                            Double.parseDouble(fields[7]), fields[8]);
                    cars.add(car);

                    // Update carCounter to ensure unique IDs
                    int id = Integer.parseInt(fields[0].substring(2)); // Get the number part of c-number
                    if (id >= carCounter) {
                        carCounter = id + 1; // Ensures the next ID is unique
                    }
                } catch (Exception e) {
                    System.out.println("An unexpected error occurred while creating the car: " + e.getMessage());
                    System.exit(0);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cars;
    }

    // Method to write to file
    public static void writeCarsToFile(List<Car> cars) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Car car : cars) {
                writer.write(car.getCarId() + "," +
                        car.getMake() + "," +
                        car.getModel() + "," +
                        car.getYear() + "," +
                        car.getMileage() + "," +
                        car.getColor() + "," +
                        car.getStatus() + "," +
                        car.getPrice() + "," +
                        car.getNotes());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to create a car
    public static void createCar() {
        List<Car> cars = readCarsFromFile();
        String make;
        String model;
        int year = 0;
        int mileage = 0;
        String color;
        Car.Status status = null;
        double price = 0.0;
        String notes;

        do {
            System.out.println("Enter car make: ");
            make = sc.nextLine().trim();
            if (make.isEmpty()) {
                System.out.println("Car make cannot be empty. Please try again.");
            }
        } while (make.isEmpty());


        do {
            System.out.println("Enter car model: ");
            model = sc.nextLine().trim();
            if (model.isEmpty()) {
                System.out.println("Car model cannot be empty. Please try again.");
            }
        } while (model.isEmpty());

        boolean validYear = false;
        while (!validYear) {
            System.out.println("Enter car year: ");
            if (sc.hasNextInt()) {
                year = sc.nextInt();
                sc.nextLine(); // Clear the newline character
                if (year >= 1886 && year <= 2024) {
                    validYear = true;
                } else {
                    System.out.println("Year must be between 1800 and 2024. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for the year.");
                sc.next();
            }
        }

        boolean validMileage = false;
        while (!validMileage) {
            System.out.println("Enter car mileage: ");
            if (sc.hasNextInt()) {
                mileage = sc.nextInt();
                sc.nextLine();
                if (mileage >= 0) {
                    validMileage = true;
                } else {
                    System.out.println("Mileage cannot be negative. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for the mileage.");
                sc.next();
            }
        }

        do {
            System.out.println("Enter car color: ");
            color = sc.nextLine();
            if (color.isEmpty()) {
                System.out.println("Car color cannot be empty. Please try again.");
            }
        } while (color.isEmpty());

        boolean validStatus = false;
        while (!validStatus) {
            System.out.println("Enter car status (AVAILABLE/SOLD): ");
            String stat = sc.nextLine().toUpperCase();
            try {
                status = Car.Status.valueOf(stat);
                validStatus = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Status must be either (AVAILABLE/SOLD). Please try again.");
            }
        }

        boolean validPrice = false;
        while (!validPrice) {
            System.out.println("Enter car price: ");
            if (sc.hasNextDouble()) {
                price = sc.nextDouble();
                sc.nextLine();
                if (price > 0) {
                    validPrice = true;
                } else {
                    System.out.println("Price must be a positive number. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for the price.");
                sc.next();
            }
        }

        System.out.println("Enter car notes: ");
        notes = sc.nextLine();
        if (notes.isEmpty()) {
            notes = "none";
        }
        String newCarId = generateCarId();
        Car newCar = new Car(newCarId, make, model, year,mileage,color,status,price,notes);
        cars.add(newCar);

        writeCarsToFile(cars);
        System.out.printf("Car Added ID: %s%n", newCar.getCarId());
    }

    // Method to display car read from file
    public static void readCar() {
        List<Car> cars = readCarsFromFile();
        System.out.println("Enter specific ID or a for all:");
        String input = sc.nextLine();
        boolean exist = false;
        if (input.equals("a")) {
            for (Car car : cars) {
                System.out.println(car);
            }
        } else {
            for (Car car : cars) {
                if (input.equals(car.getCarId())) {
                    exist = true;
                    System.out.println(car);
                }
            }
            if (!exist) System.out.println("Car does not exist");
        }
    }

    // Method to update a single data from car
    public static void updateCar() {
        List<Car> cars = readCarsFromFile();

        System.out.println("Enter car id: ");
        String id = sc.nextLine();
        boolean exist = false;
        for (Car car : cars) {
            if (id.equals(car.getCarId())) {
                exist = true;
                System.out.println("Enter data to update (make/model/year/mileage/color/status/price/notes): ");
                String data = sc.nextLine();
                boolean invalid = false;
                switch (data) {
                    case "make":
                        System.out.println("Enter car make: ");
                        car.setMake(sc.nextLine());
                        break;
                    case "model":
                        System.out.println("Enter car model: ");
                        car.setModel(sc.nextLine());
                        break;
                    case "year":
                        System.out.println("Enter car year: ");
                        car.setYear(sc.nextInt());
                        sc.nextLine();
                        break;
                    case "mileage":
                        System.out.println("Enter car mileage: ");
                        car.setMileage(sc.nextInt());
                        sc.nextLine();
                        break;
                    case "color":
                        System.out.println("Enter car color: ");
                        car.setColor(sc.nextLine());
                        break;
                    case "status":
                        System.out.println("Enter car status (AVAILABLE/SOLD): ");
                        try {
                            car.setStatus(Car.Status.valueOf(sc.nextLine().toUpperCase()));
                        } catch (IllegalArgumentException e) {
                            invalid = true;
                            System.out.println("Invalid car status. Please try again.");
                        }
                        break;
                    case "price":
                        System.out.println("Enter car price: ");
                        car.setPrice(sc.nextDouble());
                        sc.nextLine();
                        break;
                    case "notes":
                        System.out.println("Enter car notes: ");
                        car.setNotes(sc.nextLine());
                        break;
                    default:
                        invalid = true;
                        System.out.println("No matching field found. Please enter a valid field.");
                        break;
                }
                if (!invalid) {
                    writeCarsToFile(cars);
                    System.out.println("Updated: " + id);
                    break;
                }
            }
        }
        if (!exist) {
            System.out.println("No such ID");
        }
    }

    // Method to delete a car
    public static void deleteCar() {
        List<Car> cars = readCarsFromFile();
        System.out.println("Enter a specific ID to delete:");
        String input = sc.nextLine();
        boolean exist = false;
        for (Car car : cars) {
            if (input.equals(car.getCarId())) {
                exist = true;
                cars.remove(car);
                break;
            }
        }
        if (exist) {
            writeCarsToFile(cars);
            System.out.println("Car deleted");
        } else {
            System.out.println("Car does not exist");
        }
    }

    // Main control for crud operation
    public static void carControl() {
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
