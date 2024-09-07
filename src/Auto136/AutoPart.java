package Auto136;

import java.io.*;

public class AutoPart implements Serializable, Purchasable {
    public static int partCounter = 1; // Initialize ID counter with the last used ID
    private String partID;
    private String partName;
    private String manufacturer;
    private int partNumber;
    private Condition condition;
    private String warranty;
    private double cost;
    private String notes;

    // Enum for Auto136.AutoPart Condition
    public enum Condition {
        NEW, USED, REFURBISHED
    }

    // Constructor
    public AutoPart(String partId, String partName, String manufacturer, int partNumber, Condition condition, String warranty, double cost, String notes) {
        this.partID = partId;
        this.partName = partName;
        this.manufacturer = manufacturer;
        this.partNumber = partNumber;
        this.condition = condition;
        this.warranty = warranty;
        this.cost = cost;
        this.notes = notes;
    }

    // Generate a unique part ID (p-number)
    private String generatePartId() {
        return "c-" + partCounter++;
    }

    // Getters and Setters
    public String getPartID() {
        return partID;
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(int partNumber) {
        this.partNumber = partNumber;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "AutoPart{" +
                "partID='" + partID + '\'' +
                ", partName='" + partName + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", partNumber=" + partNumber +
                ", condition=" + condition +
                ", warranty='" + warranty + '\'' +
                ", cost=" + cost +
                ", notes='" + notes + '\'' +
                '}';
    }
}

