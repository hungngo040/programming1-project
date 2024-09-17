import Auto136.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class LoginManager {
    private static final String USER_DATA_FILE = "user_account.txt";
    private static final Map<String, String> credentials = new ConcurrentHashMap<>();
    private static final Map<String, User> users = new HashMap<>();
    private static User loggedInUser;

    public static void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 9) {
                    String username = parts[0];
                    String password = parts[1];
                    String fullName = parts[2];
                    Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(parts[3]);
                    String address = parts[4];
                    String phoneNumber = parts[5];
                    String email = parts[6];
                    User.UserType userType = User.UserType.valueOf(parts[7]);
                    boolean status = Boolean.parseBoolean(parts[8]);

                    credentials.put(username, password);
                    User user = new User(username, fullName, dateOfBirth, address, phoneNumber, email, userType, status);
                    users.put(username, user);
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error loading user data: " + e.getMessage());
        }
    }

    public static boolean performLogin(Scanner sc) {
        boolean isLoggedIn = false;
        while (!isLoggedIn) {
            System.out.println("Enter username: ");
            String username = sc.nextLine().trim();
            System.out.println("Enter password: ");
            String password = sc.nextLine().trim();

            if (validateCredentials(username, password)) {
                loggedInUser = users.get(username);
                if (loggedInUser != null) {
                    System.out.println("Login successful! Welcome, " + loggedInUser.getFullName());
                    UserLogger.logActivity("Successful login by " + loggedInUser.getUserID());
                    isLoggedIn = true;
                } else {
                    System.out.println("User not found.");
                    UserLogger.logActivity("Failed login attempt with username: " + username);
                }
            } else {
                System.out.println("Invalid username or password.");
                UserLogger.logActivity("Failed login attempt with username: " + username);
            }
        }
        return isLoggedIn;
    }

    private static boolean validateCredentials(String username, String password) {
        return credentials.containsKey(username) && credentials.get(username).equals(password);
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }
}
