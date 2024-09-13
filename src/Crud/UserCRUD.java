package Crud;

import Auto136.User;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import static Auto136.User.userCounter;

public class UserCRUD {
    private static final Scanner sc = new Scanner(System.in);
    private static final String filename = "user.txt";
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final Pattern phonePattern = Pattern.compile("\\d{10}"); // Simple validation for 10-digit phone number

    // Method to read from file
    public static List<User> readUsersFromFile() {
        List<User> users = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                try {
                    Date dateOfBirth = dateFormat.parse(fields[2]);
                    User user = new User(fields[0], fields[1], dateOfBirth, fields[3],
                            fields[4], fields[5],
                            User.UserType.valueOf(fields[6]),
                            Boolean.parseBoolean(fields[7]));
                    users.add(user);
                } catch (ParseException e) {
                    System.out.println("Error parsing date: " + e.getMessage());
                }
                int id = Integer.parseInt(fields[0].substring(2)); // Get the number part of c-number
                if (id >= userCounter) {
                    userCounter = id + 1; // Ensures the next ID is unique
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Method to write to file
    public static void writeUsersToFile(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (User user : users) {
                writer.write(user.getUserID() + "," +
                        user.getFullName() + "," +
                        dateFormat.format(user.getDateOfBirth()) + "," +
                        user.getAddress() + "," +
                        user.getPhoneNumber() + "," +
                        user.getEmail() + "," +
                        user.getUserType() + "," +
                        user.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to create a user
    public static void createUser() {
        List<User> users = readUsersFromFile();
        String fullName;
        Date dateOfBirth = null;
        String address;
        String phoneNumber;
        String email;
        String userType;
        boolean status;

        System.out.println("Enter user full name: ");
        fullName = sc.nextLine().trim();

        while (dateOfBirth == null) {
            System.out.println("Enter user date of birth (yyyy-MM-dd): ");
            try {
                dateOfBirth = dateFormat.parse(sc.nextLine());
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }

        System.out.println("Enter user address: ");
        address = sc.nextLine();

        while (true) {
            System.out.println("Enter user phone number (10 digits): ");
            phoneNumber = sc.nextLine();
            if (phonePattern.matcher(phoneNumber).matches()) {
                break;
            } else {
                System.out.println("Invalid phone number. Please enter exactly 10 digits.");
            }
        }

        System.out.println("Enter user email: ");
        email = sc.nextLine();

        boolean validType = false;
        User.UserType type = null;
        while (!validType) {
            System.out.println("Enter user type (MANAGER, SALESPERSON, MECHANIC, CLIENT): ");
            userType = sc.nextLine().toUpperCase();
            try {
                type = User.UserType.valueOf(userType);
                validType = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid user type. Please try again.");
            }
        }

        System.out.println("Enter user status (true/false): ");
        status = sc.nextBoolean();
        sc.nextLine(); // Clear the newline character

        String newUserID = User.generateUserId();
        User newUser = new User(newUserID, fullName, dateOfBirth, address, phoneNumber, email, type, status);
        users.add(newUser);

        writeUsersToFile(users);
        System.out.printf("User Added ID: %s%n", newUser.getUserID());
    }

    // Method to display user read from file
    // Method to display user read from file
    public static void readUser() {
        List<User> users = readUsersFromFile();
        System.out.println("Enter specific ID or 'a' for all:");
        String input = sc.nextLine();
        boolean found = false;

        if (input.equals("a")) {
            for (User user : users) {
                System.out.println(formatUser(user));
            }
        } else {
            for (User user : users) {
                if (input.equals(user.getUserID())) {
                    found = true;
                    System.out.println(formatUser(user));
                }
            }
            if (!found) System.out.println("User does not exist");
        }
    }

    // Helper method to format User details
    private static String formatUser(User user) {
        return String.format("UserID: %s, Full Name: %s, Date of Birth: %s, Address: %s, Phone Number: %s, Email: %s, User Type: %s, Status: %s",
                user.getUserID(),
                user.getFullName(),
                dateFormat.format(user.getDateOfBirth()),  // Format the date to yyyy-MM-dd
                user.getAddress(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getUserType(),
                user.getStatus());
    }


    // Method to update a single data from user
    public static void updateUser() {
        List<User> users = readUsersFromFile();
        System.out.println("Enter user ID: ");
        String id = sc.nextLine();
        boolean found = false;

        for (User user : users) {
            if (id.equals(user.getUserID())) {
                found = true;
                System.out.println("Enter data to update: ");
                String data = sc.nextLine();
                switch (data) {
                    case "fullName":
                        System.out.println("Enter user full name: ");
                        user.setFullName(sc.nextLine());
                        break;
                    case "dateOfBirth":
                        System.out.println("Enter user date of birth (yyyy-MM-dd): ");
                        try {
                            user.setDateOfBirth(dateFormat.parse(sc.nextLine()));
                        } catch (ParseException e) {
                            System.out.println("Invalid date format. Please try again.");
                        }
                        break;
                    case "address":
                        System.out.println("Enter user address: ");
                        user.setAddress(sc.nextLine());
                        break;
                    case "phoneNumber":
                        while (true) {
                            System.out.println("Enter user phone number (10 digits): ");
                            String phoneNumber = sc.nextLine();
                            if (phonePattern.matcher(phoneNumber).matches()) {
                                user.setPhoneNumber(phoneNumber);
                                break;
                            } else {
                                System.out.println("Invalid phone number. Please enter exactly 10 digits.");
                            }
                        }
                        break;
                    case "email":
                        System.out.println("Enter user email: ");
                        user.setEmail(sc.nextLine());
                        break;
                    case "userType":
                        System.out.println("Enter user type (MANAGER, SALESPERSON, MECHANIC, CLIENT): ");
                        try {
                            user.setUserType(User.UserType.valueOf(sc.nextLine().toUpperCase()));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid user type. Please try again.");
                        }
                        break;
                    case "status":
                        System.out.println("Enter user status (true/false): ");
                        user.setStatus(sc.nextBoolean());
                        sc.nextLine(); // Clear the newline character
                        break;
                    default:
                        System.out.println("No matching field found. Please enter a valid field.");
                        break;
                }
                writeUsersToFile(users);
                System.out.println("Updated: " + id);
                break;
            }
        }
        if (!found) {
            System.out.println("No such ID");
        }
    }

    // Method to delete a user
    public static void deleteUser() {
        List<User> users = readUsersFromFile();
        System.out.println("Enter a specific ID to delete:");
        String input = sc.nextLine();
        boolean found = false;

        for (User user : users) {
            if (input.equals(user.getUserID())) {
                found = true;
                users.remove(user);
                break;
            }
        }

        if (found) {
            writeUsersToFile(users);
            System.out.println("User deleted");
        } else {
            System.out.println("User does not exist");
        }
    }

    // Main control for CRUD operations
    public static void userControl() {
        File file = new File(filename);
        // If file does not exist, create it
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        boolean isActive = true;
        do {
            System.out.println("1: Add a user");
            System.out.println("2: View user");
            System.out.println("3: Update user");
            System.out.println("4: Delete user");
            System.out.println("0: Exit");
            String input = sc.nextLine();

            switch (input) {
                case "1":
                    createUser();
                    break;
                case "2":
                    readUser();
                    break;
                case "3":
                    updateUser();
                    break;
                case "4":
                    deleteUser();
                    break;
                case "0":
                    isActive = false;
                    break;
                default:
                    System.out.println("Invalid input. Please try again.");
                    break;
            }
        } while (isActive);
    }
}


