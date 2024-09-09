package Auto136;

import java.io.*;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class User implements Serializable {
    public static int userCounter = 1;
    private String userID;
    private String fullName;
    private Date dateOfBirth;
    private String address;
    private String phoneNumber;
    private String email;
    private UserType userType;  // Using the enum type
    private boolean status;

    public enum UserType {
        MANAGER,
        SALESPERSON,
        MECHANIC,
        CLIENT
    }

    public User(String userID, String fullName, Date dateOfBirth, String address, String phoneNumber, String email, UserType userType, boolean status) {
        this.userID = userID;
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userType = userType;
        this.status = status;
    }

    // Generate a unique car ID (u-number)
    public static String generateUserId() {
        return "u-" + userCounter++;
    }




    // Getters and Setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID='" + userID + '\'' +
                ", fullName='" + fullName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", userType=" + userType +
                ", status=" + status +
                '}';
    }
}
