package Auto136;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SalesTransaction {
    private static final AtomicInteger idCounter = new AtomicInteger(loadLastId()); // Initialize ID counter with the last used ID
    private String transactionID;
    private Date transactionDate;
    private String clientID;
    private String salespersonID;
    private List<Purchasable> purchasedItems; // Can contain Auto136.AutoPart or Auto136.Car
    private double discount;
    private double totalAmount;
    private String additionalNotes;

    // Constructor
    public SalesTransaction(Date transactionDate, String clientID,
                            String salespersonID, List<Purchasable> purchasedItems,
                            double discount, double totalAmount, String additionalNotes) {
        this.transactionID = generateTransactionId();
        this.transactionDate = transactionDate;
        this.clientID = clientID;
        this.salespersonID = salespersonID;
        this.purchasedItems = purchasedItems;
        this.discount = discount;
        this.totalAmount = totalAmount;
        this.additionalNotes = additionalNotes;
        writeToFile();
    }

    // Generate a unique transaction ID (t-number)
    private String generateTransactionId() {
        return "t-" + idCounter.incrementAndGet(); // Increment the ID counter for each new transaction
    }

    // Write transaction info to sales_transaction.txt
    private void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("sales_transaction.txt", true))) {
            writer.write(toFileString());
            writer.newLine();
            saveLastId(idCounter.get()); // Save the latest ID to the file after writing the transaction details
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Convert transaction info to string format for file writing
    public String toFileString() {
        return transactionID + "," + transactionDate + "," + clientID + "," + salespersonID + "," + purchasedItems + "," + discount + "," + totalAmount + "," + additionalNotes;
    }

    // Load the last used ID from file
    private static int loadLastId() {
        try (BufferedReader reader = new BufferedReader(new FileReader("transaction_id_counter.txt"))) {
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transaction_id_counter.txt"))) {
            writer.write(String.valueOf(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters and Setters
    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getSalespersonID() {
        return salespersonID;
    }

    public void setSalespersonID(String salespersonID) {
        this.salespersonID = salespersonID;
    }

    public List<Purchasable> getPurchasedItems() {
        return purchasedItems;
    }

    public void setPurchasedItems(List<Purchasable> purchasedItems) {
        this.purchasedItems = purchasedItems;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    @Override
    public String toString() {
        return "Auto136.SalesTransaction{" +
                "transactionID='" + transactionID + '\'' +
                ", transactionDate=" + transactionDate +
                ", clientID='" + clientID + '\'' +
                ", salespersonID='" + salespersonID + '\'' +
                ", purchasedItems=" + purchasedItems +
                ", discount=" + discount +
                ", totalAmount=" + totalAmount +
                ", additionalNotes='" + additionalNotes + '\'' +
                '}';
    }
}
