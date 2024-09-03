import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Service implements Serializable {
    private static final AtomicInteger idCounter = new AtomicInteger(loadLastId()); // Initialize ID counter with the last used ID
    private String serviceID;
    private Date serviceDate;
    private String clientID;
    private String mechanicID;
    private String serviceType;
    private List<AutoPart> replacedParts;
    private double serviceCost;
    private String additionalNotes;

    // Constructor
    public Service(Date serviceDate, String clientID, String mechanicID, String serviceType,
                   List<AutoPart> replacedParts, double serviceCost, String additionalNotes) {
        this.serviceID = generateServiceId();
        this.serviceDate = serviceDate;
        this.clientID = clientID;
        this.mechanicID = mechanicID;
        this.serviceType = serviceType;
        this.replacedParts = replacedParts;
        this.serviceCost = serviceCost;
        this.additionalNotes = additionalNotes;
        writeToFile();
    }

    // Generate a unique service ID (s-number)
    private String generateServiceId() {
        return "s-" + idCounter.incrementAndGet(); // Increment the ID counter for each new service
    }

    // Write service info to service.txt
    private void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("service.txt", true))) {
            writer.write(toFileString());
            writer.newLine();
            saveLastId(idCounter.get()); // Save the latest ID to the file after writing the service details
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Convert service info to string format for file writing
    private String toFileString() {
        StringBuilder parts = new StringBuilder();
        if (replacedParts != null) {
            for (AutoPart part : replacedParts) {
                parts.append(part.getPartID()).append(";");
            }
        }
        return serviceID + "," + serviceDate + "," + clientID + "," + mechanicID + "," + serviceType + ","
                + parts + "," + serviceCost + "," + additionalNotes;
    }

    // Load the last used ID from file
    private static int loadLastId() {
        try (BufferedReader reader = new BufferedReader(new FileReader("service_id_counter.txt"))) {
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("service_id_counter.txt"))) {
            writer.write(String.valueOf(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters and Setters
    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public String getMechanicID() {
        return mechanicID;
    }

    public void setMechanicID(String mechanicID) {
        this.mechanicID = mechanicID;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public List<AutoPart> getReplacedParts() {
        return replacedParts;
    }

    public void setReplacedParts(List<AutoPart> replacedParts) {
        this.replacedParts = replacedParts;
    }

    public double getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(double serviceCost) {
        this.serviceCost = serviceCost;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    @Override
    public String toString() {
        return "Service{" +
                "serviceID='" + serviceID + '\'' +
                ", serviceDate=" + serviceDate +
                ", clientID='" + clientID + '\'' +
                ", mechanicID='" + mechanicID + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", replacedParts=" + replacedParts +
                ", serviceCost=" + serviceCost +
                ", additionalNotes='" + additionalNotes + '\'' +
                '}';
    }
}

