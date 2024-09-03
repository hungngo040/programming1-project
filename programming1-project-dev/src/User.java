import java.io.*;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class User implements Serializable {
    private static final AtomicInteger idCounter = new AtomicInteger(loadLastId()); // Initialize ID counter with the last used ID
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

    // Constructors
    public User(String fullName, Date dateOfBirth, String address,
                String phoneNumber, String email, UserType userType, boolean status) {
        this.userID = generateUserId();
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.userType = userType;
        this.status = status;
        writeToFile();
    }

    // Generate a unique user ID (u-number)
    private String generateUserId() {
        return "u-" + idCounter.incrementAndGet(); // Increment the ID counter for each new user
    }

    // Write user info to user.txt
    private void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user.txt", true))) {
            writer.write(toFileString());
            writer.newLine();
            saveLastId(idCounter.get()); // Save the latest ID to the file after writing the user details
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Convert user info to string format for file writing
    private String toFileString() {
        return userID + "," + fullName + "," + dateOfBirth + "," + address + "," + phoneNumber + "," + email + "," + userType + "," + status;
    }

    // Load the last used ID from file
    private static int loadLastId() {
        try (BufferedReader reader = new BufferedReader(new FileReader("user_id_counter.txt"))) {
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user_id_counter.txt"))) {
            writer.write(String.valueOf(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

