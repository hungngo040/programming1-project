package Crud;

import Auto136.SalesTransaction;
import Auto136.Purchasable;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TransactionCRUD {
    private static final String FILE_NAME = "sales_transaction.txt";
    private static final Scanner sc = new Scanner(System.in);

    // Method to add a new transaction
    public static void addTransaction() {
        try {
            System.out.println("Enter client ID:");
            String clientID = sc.nextLine();
            System.out.println("Enter salesperson ID:");
            String salespersonID = sc.nextLine();
            System.out.println("Enter discount:");
            double discount = Double.parseDouble(sc.nextLine());
            System.out.println("Enter total amount:");
            double totalAmount = Double.parseDouble(sc.nextLine());
            System.out.println("Enter additional notes (optional):");
            String additionalNotes = sc.nextLine();

            // For simplicity, we will assume an empty list of purchased items
            List<Purchasable> purchasedItems = new ArrayList<>(); 

            SalesTransaction newTransaction = new SalesTransaction(new Date(), clientID, salespersonID, purchasedItems, discount, totalAmount, additionalNotes);
            System.out.println("Transaction added successfully: " + newTransaction.getTransactionID());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to get a transaction by its ID
    public static void getTransactionById() {
        System.out.println("Enter the transaction ID:");
        String transactionID = sc.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(transactionID)) {
                    System.out.println("Transaction found: " + line);
                    return;
                }
            }
            System.out.println("Transaction not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to update a transaction
    public static void updateTransaction() {
        System.out.println("Enter the transaction ID to update:");
        String transactionID = sc.nextLine();

        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp_" + FILE_NAME);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith(transactionID)) {
                    found = true;
                    System.out.println("Transaction found. Enter updated details.");
                    System.out.println("Enter updated discount:");
                    double discount = Double.parseDouble(sc.nextLine());
                    System.out.println("Enter updated total amount:");
                    double totalAmount = Double.parseDouble(sc.nextLine());
                    System.out.println("Enter updated additional notes (optional):");
                    String additionalNotes = sc.nextLine();

                    // Assuming purchasedItems remain unchanged for this update
                    List<Purchasable> purchasedItems = new ArrayList<>();

                    SalesTransaction updatedTransaction = new SalesTransaction(new Date(), transactionID, transactionID, purchasedItems, discount, totalAmount, additionalNotes);
                    writer.write(updatedTransaction.toFileString());
                    writer.newLine();
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }

            if (!found) {
                System.out.println("Transaction not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Replace the old file with the updated file
        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }
    }

    // Method to delete a transaction by its ID
    public static void deleteTransaction() {
        System.out.println("Enter the transaction ID to delete:");
        String transactionID = sc.nextLine();

        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp_" + FILE_NAME);

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith(transactionID)) {
                    found = true;
                    System.out.println("Transaction deleted: " + transactionID);
                } else {
                    writer.write(line);
                    writer.newLine();
                }
            }

            if (!found) {
                System.out.println("Transaction not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Replace the old file with the updated file
        if (inputFile.delete()) {
            tempFile.renameTo(inputFile);
        }
    }
}
