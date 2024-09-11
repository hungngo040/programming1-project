package Crud;

import Auto136.SalesTransaction;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionCRUD {
    private static final Scanner sc = new Scanner(System.in);
    private static final String FILE_NAME = "transactions.txt"; // File name for storing sales transactions

    // Create a new SalesTransaction and save it to the file
    public void createSalesTransaction(SalesTransaction transaction) {
        List<SalesTransaction> transactions = readAllSalesTransactions();
        transactions.add(transaction);
        writeAllSalesTransactions(transactions);
    }

    // Read all sales transactions from the file
    public List<SalesTransaction> readAllSalesTransactions() {
        List<SalesTransaction> transactions = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            transactions = (List<SalesTransaction>) ois.readObject();
        } catch (FileNotFoundException e) {
            // No existing file, so return an empty list
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    // Read a specific SalesTransaction by transactionID
    public SalesTransaction readSalesTransaction(String transactionID) {
        List<SalesTransaction> transactions = readAllSalesTransactions();
        for (SalesTransaction transaction : transactions) {
            if (transaction.getTransactionID().equals(transactionID)) {
                return transaction;
            }
        }
        return null; // Transaction not found
    }

    // Update an existing SalesTransaction
    public void updateSalesTransaction(SalesTransaction updatedTransaction) {
        List<SalesTransaction> transactions = readAllSalesTransactions();
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getTransactionID().equals(updatedTransaction.getTransactionID())) {
                transactions.set(i, updatedTransaction);
                writeAllSalesTransactions(transactions);
                return;
            }
        }
    }

    // Delete a SalesTransaction by transactionID (Soft Delete)
    public void deleteSalesTransaction(String transactionID) {
        List<SalesTransaction> transactions = readAllSalesTransactions();
        transactions.removeIf(transaction -> transaction.getTransactionID().equals(transactionID));
        writeAllSalesTransactions(transactions);
    }

    // Write all sales transactions to the file
    private void writeAllSalesTransactions(List<SalesTransaction> transactions) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(transactions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // List all transactions within a specific date range
    public List<SalesTransaction> listTransactionsByDateRange(Date startDate, Date endDate) {
        List<SalesTransaction> transactions = readAllSalesTransactions();
        List<SalesTransaction> result = new ArrayList<>();
        for (SalesTransaction transaction : transactions) {
            if (transaction.getTransactionDate().after(startDate) && transaction.getTransactionDate().before(endDate)) {
                result.add(transaction);
            }
        }
        return result;
    }

    // List all transactions of a specific client
    public List<SalesTransaction> listTransactionsByClient(String clientID) {
        List<SalesTransaction> transactions = readAllSalesTransactions();
        List<SalesTransaction> result = new ArrayList<>();
        for (SalesTransaction transaction : transactions) {
            if (transaction.getClientID().equals(clientID)) {
                result.add(transaction);
            }
        }
        return result;
    }

    // List all transactions of a specific salesperson
    public List<SalesTransaction> listTransactionsBySalesperson(String salespersonID) {
        List<SalesTransaction> transactions = readAllSalesTransactions();
        List<SalesTransaction> result = new ArrayList<>();
        for (SalesTransaction transaction : transactions) {
            if (transaction.getSalespersonID().equals(salespersonID)) {
                result.add(transaction);
            }
        }
        return result;
    }

    // Calculate total sales within a specific date range
    public double calculateTotalSalesByDateRange(Date startDate, Date endDate) {
        List<SalesTransaction> transactions = listTransactionsByDateRange(startDate, endDate);
        double totalSales = 0;
        for (SalesTransaction transaction : transactions) {
            totalSales += transaction.getTotalAmount();
        }
        return totalSales;
    }
}
