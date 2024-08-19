import java.util.Date;
import java.util.List;

public class SalesTransaction {
    private String transactionID;
    private Date transactionDate;
    private String clientID;
    private String salespersonID;
    private List<Purchasable> purchasedItems; // Can contain AutoPart or Car
    private double discount;
    private double totalAmount;
    private String additionalNotes;

    // Constructors, getters, and setters

    public SalesTransaction(String transactionID, Date transactionDate, String clientID,
                            String salespersonID, List<Purchasable> purchasedItems,
                            double discount, double totalAmount, String additionalNotes) {
        this.transactionID = transactionID;
        this.transactionDate = transactionDate;
        this.clientID = clientID;
        this.salespersonID = salespersonID;
        this.purchasedItems = purchasedItems;
        this.discount = discount;
        this.totalAmount = totalAmount;
        this.additionalNotes = additionalNotes;
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
        return "SalesTransaction{" +
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