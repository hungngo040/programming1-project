package Auto136;

import java.io.*;

public class Car implements Purchasable {
    public static int carCounter = 1;// Initialize ID counter with the last used ID
    private String carId;
    private String make;
    private String model;
    private int year;
    private int mileage;
    private String color;
    private Status status;
    private double price;
    private String notes;

    // Enum for Auto136.Car Status
    public enum Status {
        AVAILABLE,
        SOLD
    }

    // Constructor
    public Car(String carId, String make, String model, int year, int mileage, String color, Status status, double price, String notes) {
        this.carId = carId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.color = color;
        this.status = status;
        this.price = price;
        this.notes = notes;
    }

    // Generate a unique car ID (c-number)
    public static String generateCarId() {
        return "c-" + carCounter++;
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
