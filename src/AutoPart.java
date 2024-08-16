import java.util.concurrent.atomic.AtomicInteger;

public class AutoPart {
    private static final AtomicInteger idCounter = new AtomicInteger(0);
    private String partID;
    private String partName;
    private String manufacturer;
    private int partNumber;
    private Condition condition;
    private String warranty;
    private double cost;
    private String notes;


    public enum Condition {
        NEW, USED, REFURBISHED
    }

    public AutoPart( String partName, String manufacturer, int partNumber, Condition condition, String warranty, double cost, String notes) {
        this.partID = generatePartId();
        this.partName = partName;
        this.manufacturer = manufacturer;
        this.partNumber = partNumber;
        this.condition = condition;
        this.warranty = warranty;
        this.cost = cost;
        this.notes = notes;
    }

    private String generatePartId() {
        return "p-" + idCounter.incrementAndGet();
    }

    public String getPartID() {
        return partID;
    }

    public void setPartID(String partID) {
        this.partID = partID;
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
