import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AutoPart implements Serializable, Purchasable {
    private static final AtomicInteger idCounter = new AtomicInteger(loadLastId()); // Initialize ID counter with the last used ID
    private String partID;
    private String partName;
    private String manufacturer;
    private int partNumber;
    private Condition condition;
    private String warranty;
    private double cost;
    private String notes;

    // Enum for AutoPart Condition
    public enum Condition {
        NEW, USED, REFURBISHED
    }

    // Constructor
    public AutoPart(String partName, String manufacturer, int partNumber, Condition condition, String warranty, double cost, String notes) {
        this.partID = generatePartId();
        this.partName = partName;
        this.manufacturer = manufacturer;
        this.partNumber = partNumber;
        this.condition = condition;
        this.warranty = warranty;
        this.cost = cost;
        this.notes = notes;
        writeToFile();
    }

    // Generate a unique part ID (p-number)
    private String generatePartId() {
        return "p-" + idCounter.incrementAndGet(); // Increment the ID counter for each new part
    }

    // Write part info to autopart.txt
    private void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("autopart.txt", true))) {
            writer.write(toFileString());
            writer.newLine();
            saveLastId(idCounter.get()); // Save the latest ID to the file after writing the part details
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Convert part info to string format for file writing
    private String toFileString() {
        return partID + "," + partName + "," + manufacturer + "," + partNumber + "," + condition + "," + warranty + "," + cost + "," + notes;
    }

    // Load the last used ID from file
    private static int loadLastId() {
        try (BufferedReader reader = new BufferedReader(new FileReader("autopart_id_counter.txt"))) {
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("autopart_id_counter.txt"))) {
            writer.write(String.valueOf(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

