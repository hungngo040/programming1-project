package Auto136;

import java.io.*;
import java.time.LocalDate;
import java.util.List;


public class Service {
    public static int serviceCounter = 1;// Initialize ID counter with the last used ID
    private String serviceID;
    private LocalDate serviceDate;
    private String clientID;
    private String mechanicID;
    private String serviceType;
    private List<AutoPart> replacedParts;
    private double serviceCost;
    private String additionalNotes;

    // Constructor
    public Service(String serviceId,LocalDate serviceDate, String clientID, String mechanicID, String serviceType,
                   List<AutoPart> replacedParts, double serviceCost, String additionalNotes) {
        this.serviceID = serviceId;
        this.serviceDate = serviceDate;
        this.clientID = clientID;
        this.mechanicID = mechanicID;
        this.serviceType = serviceType;
        this.replacedParts = replacedParts;
        this.serviceCost = serviceCost;
        this.additionalNotes = additionalNotes;

    }

    // Generate a unique service ID (s-number)
    public static String generateServiceId() {
        return "s-" + serviceCounter++; // Increment the ID counter for each new service
    }

    // Getters and Setters
    public String getServiceID() {
        return serviceID;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
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

