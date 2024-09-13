package Auto136;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class SalesTransaction {
    public static int transactionCounter = 1; // Initialize ID counter with a default value
    private String transactionID;
    private LocalDate transactionDate;
    private String clientID;
    private String salespersonID;
    private List<AutoPart> replacedParts;
    private double discount;
    private double totalAmount;
    private String additionalNotes;

    // Constructor
    public SalesTransaction(String transactionID, LocalDate transactionDate, String clientID,
                            String salespersonID, List<AutoPart> replacedParts,
                            double discount, double totalAmount, String additionalNotes) {
        this.transactionID = transactionID;
        this.transactionDate = transactionDate;
        this.clientID = clientID;
        this.salespersonID = salespersonID;
        this.replacedParts = replacedParts;
        this.discount = discount;
        this.totalAmount = totalAmount;
        this.additionalNotes = additionalNotes;
    }

    // Generate a unique transaction ID (t-number)
    public static String generateTransactionId() {
        return "t-" + (transactionCounter++); // Increment the counter for each new transaction
    }

    // Getters and Setters
    public String getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
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

    public List<AutoPart> getReplacedParts() {
        return replacedParts;
    }

    public void setReplacedParts(List<AutoPart> replacedParts) {
        this.replacedParts = replacedParts;
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
        return "SalesTransaction{" +
                "transactionID='" + transactionID + '\'' +
                ", transactionDate=" + transactionDate +
                ", clientID='" + clientID + '\'' +
                ", salespersonID='" + salespersonID + '\'' +
                ", replacedParts=" + replacedParts +
                ", discount=" + discount +
                ", totalAmount=" + totalAmount +
                ", additionalNotes='" + additionalNotes + '\'' +
                '}';
    }
}

