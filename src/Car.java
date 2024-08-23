import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Car implements Serializable, Purchasable {
    private static final AtomicInteger idCounter = new AtomicInteger(loadLastId()); // Initialize ID counter with the last used ID
    private String carId;
    private String make;
    private String model;
    private int year;
    private int mileage;
    private String color;
    private Status status;
    private double price;
    private String notes;

    // Enum for Car Status
    public enum Status {
        AVAILABLE,
        SOLD
    }

    // Constructor
    public Car(String make, String model, int year, int mileage, String color, Status status, double price, String notes) {
        this.carId = generateCarId();
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.color = color;
        this.status = status;
        this.price = price;
        this.notes = notes;
        writeToFile();
    }

    // Generate a unique car ID (c-number)
    private String generateCarId() {
        return "c-" + idCounter.incrementAndGet(); // Increment the ID counter for each new car
    }

    // Write car info to car.txt
    private void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("car.txt", true))) {
            writer.write(toFileString());
            writer.newLine();
            saveLastId(idCounter.get()); // Save the latest ID to the file after writing the car details
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Convert car info to string format for file writing
    private String toFileString() {
        return carId + "," + make + "," + model + "," + year + "," + mileage + "," + color + "," + status + "," + price + "," + notes;
    }

    // Load the last used ID from file
    private static int loadLastId() {
        try (BufferedReader reader = new BufferedReader(new FileReader("car_id_counter.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                return Integer.parseInt(line);
            }
        } catch (IOException | NumberFormatException e) {
            // Handle file not found or format issues
        }
        return 0; // Default value if file not found or empty
    }

    // Save the last used ID to file
    private static void saveLastId(int id) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("car_id_counter.txt"))) {
            writer.write(String.valueOf(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters and Setters
    public String getCarId() {
        return carId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId='" + carId + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", mileage=" + mileage +
                ", color='" + color + '\'' +
                ", status=" + status +
                ", price=" + price +
                ", notes='" + notes + '\'' +
                '}';
    }
}
