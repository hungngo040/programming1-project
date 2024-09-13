package Crud;

import Auto136.AutoPart;
import Auto136.SalesTransaction;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Auto136.AutoPart.partCounter;
import static Auto136.SalesTransaction.generateTransactionId;
import static Auto136.SalesTransaction.transactionCounter;
import static Crud.AutoPartCRUD.readPartsFromFile;

public class SalesTransactionCRUD {
    private static final Scanner sc = new Scanner(System.in);
    private static final String filename = "sale_transaction.txt";

    public static LocalDate stringToDate(String dateString) {
        String[] fields = dateString.split("-");
        ArrayList<Integer> dateFields = new ArrayList<>();
        for (String field : fields) {
            dateFields.add(Integer.parseInt(field));
        }
        return LocalDate.of(dateFields.get(0), dateFields.get(1), dateFields.get(2));
    }

    public static List<AutoPart> readReplaceParts(String string) {
        List<AutoPart> parts = readPartsFromFile();
        List<AutoPart> replacedParts = new ArrayList<>();
        String partIDs = string.replace("[", "").replace("]", "");
        String[] fields = partIDs.split(",");
        for (AutoPart part : parts) {
            for (String field : fields) {
                if (part.getPartID().equals(field)) {
                    replacedParts.add(part);
                }
            }
        }
        return replacedParts;
    }

    // Method to read from file
    public static List<SalesTransaction> readTransactionsFromFile() {
        List<SalesTransaction> transactions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {

                Pattern pattern = Pattern.compile("\\[.*?\\]|[^,\\[\\]]+");
                Matcher matcher = pattern.matcher(line);
                List<String> fields = new ArrayList<>();
                while (matcher.find()) {
                    fields.add(matcher.group());
                }
                try {
                    LocalDate date = stringToDate(fields.get(1));
                    List<AutoPart> replacedParts = readReplaceParts(fields.get(4));
                    SalesTransaction transaction = new SalesTransaction(fields.get(0), date, fields.get(2), fields.get(3),
                            replacedParts, Double.parseDouble(fields.get(5)),
                            Double.parseDouble(fields.get(6)), fields.get(7));
                    transactions.add(transaction);

                    int id = Integer.parseInt(fields.get(0).substring(2));
                    if (id >= partCounter) {
                        partCounter = id + 1;
                    }
                } catch (Exception e) {
                    System.out.println("An unexpected error occurred while creating transaction: " + e.getMessage());
                    System.exit(0);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    // Method to write to file
    public static void writeTransactionsToFile(List<SalesTransaction> transactions) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (SalesTransaction transaction : transactions) {
                List<AutoPart> parts = transaction.getReplacedParts();
                List<String> replacedParts = new ArrayList<>();
                for (AutoPart part : parts) {
                    replacedParts.add(part.getPartID());
                }
                writer.write(transaction.getTransactionID() + "," +
                        transaction.getTransactionDate() + "," +
                        transaction.getClientID() + "," +
                        transaction.getSalespersonID()+ "," +
                        replacedParts + "," +
                        transaction.getDiscount() + "," +
                        transaction.getTotalAmount() + "," +
                        transaction.getAdditionalNotes());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createTransaction() {
        List<SalesTransaction> transactions = readTransactionsFromFile();
        List<AutoPart> parts = readPartsFromFile();
        LocalDate transactionDate;
        int year = 0;
        int month = 0;
        int day = 0;

        String clientID;
        String salePersonID;
        String serviceType;
        List<AutoPart> replacedParts = new ArrayList<>();
        double discount = 0.0;
        double totalAmount = 0.0;
        String notes;

        boolean validYear = false;
        while (!validYear) {
            System.out.println("Enter date year: ");
            if (sc.hasNextInt()) {
                year = sc.nextInt();
                sc.nextLine();
                if (year >= 1) {
                    validYear = true;
                } else {
                    System.out.println("Year must be higher than 0. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for year.");
                sc.next();
            }
        }

        boolean validMonth = false;
        while (!validMonth) {
            System.out.println("Enter date month: ");
            if (sc.hasNextInt()) {
                month = sc.nextInt();
                sc.nextLine();
                if (month >= 1 && month <= 12) {
                    validMonth = true;
                } else {
                    System.out.println("Month must be in range 1-12. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for month.");
                sc.next();
            }
        }

        boolean validDay = false;
        while (!validDay) {
            System.out.println("Enter date day: ");
            if (sc.hasNextInt()) {
                day = sc.nextInt();
                sc.nextLine();
                if (day >= 1 && day <= 31) {
                    validDay = true;
                } else {
                    System.out.println("Day must be in range 1-31. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for month.");
                sc.next();
            }
        }

        do {
            System.out.println("Enter client ID: ");
            clientID = sc.nextLine().trim();
            if (clientID.isEmpty()) {
                System.out.println("Client ID cannot be empty. Please try again.");
            }
        } while (clientID.isEmpty());

        do {
            System.out.println("Enter Sale Person ID: ");
            salePersonID = sc.nextLine().trim();
            if (salePersonID.isEmpty()) {
                System.out.println(" Sale Person ID cannot be empty. Please try again.");
            }
        } while (salePersonID.isEmpty());

        //list
        boolean validParts = false;
        while (!validParts) {
            System.out.println("Enter replaced parts id(optional) or Enter 'skip': ");
            String input = sc.nextLine().trim();
            if (input.equals("skip")) {
                validParts = true;
            } else if (input.isEmpty()) {
                System.out.println("Input cannot be empty");
            } else {
                for (AutoPart part : parts) {
                    if (input.equals(part.getPartID())) {
                        validParts = true;
                        replacedParts.add(part);
                        break;
                    }
                }
                if (!validParts) {
                    System.out.println("Input invalid");
                }
            }
        }

        boolean validDiscount = false;
        while (!validDiscount) {
            System.out.println("Enter Discount: ");
            if (sc.hasNextDouble()) {
                discount = sc.nextDouble();
                sc.nextLine();
                if (discount > 0) {
                    validDiscount = true;
                } else {
                    System.out.println("Discount must be a positive number. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for the discount.");
                sc.next();
            }
        }

        boolean validTotalAmount = false;
        while (!validTotalAmount) {
            System.out.println("Enter Total Amount: ");
            if (sc.hasNextDouble()) {
                totalAmount = sc.nextDouble();
                sc.nextLine();
                if (totalAmount > 0) {
                    validTotalAmount = true;
                } else {
                    System.out.println("Total Amount must be a positive number. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for the total amount.");
                sc.next();
            }
        }

        System.out.println("Enter transaction notes: ");
        notes = sc.nextLine();
        if (notes.isEmpty()) {
            notes = "none";
        }

        transactionDate = LocalDate.of(year, month, day);
        String newTransactionId = generateTransactionId();
        SalesTransaction newTransaction = new SalesTransaction(newTransactionId, transactionDate, clientID,
                salePersonID, replacedParts, discount, totalAmount, notes);
        transactions.add(newTransaction);
        writeTransactionsToFile(transactions);

    }

    // Method to display part read from file
    public static void readTransaction() {
        List<SalesTransaction> transactions = readTransactionsFromFile();
        System.out.println("Enter specific ID or a for all:");
        String input = sc.nextLine();
        boolean exist = false;
        if (input.equals("a")) {
            for (SalesTransaction transaction : transactions) {
                System.out.println(transaction);
            }
        } else {
            for (SalesTransaction transaction : transactions) {
                if (input.equals(transaction.getTransactionID())) {
                    exist = true;
                    System.out.println(transaction);
                }
            }
            if (!exist) System.out.println("Transaction does not exist");
        }
    }

    public static void updateTransaction() {
        List<SalesTransaction> transactions = readTransactionsFromFile();
        List<AutoPart> parts = readPartsFromFile();
        System.out.println("Enter transaction id: ");
        String id = sc.nextLine();
        boolean exist = false;
        for (SalesTransaction transaction : transactions) {
            List<AutoPart> replacedParts = transaction.getReplacedParts();
            if (id.equals(transaction.getTransactionID())) {
                exist = true;
                System.out.println("Enter data to update (date/client/sale person/replaced parts/discount/total amount/notes)");
                String data = sc.nextLine();
                boolean invalid = false;
                switch (data) {
                    case "date":
                        int year = 0;
                        int month = 0;
                        int day = 0;

                        boolean validYear = false;
                        while (!validYear) {
                            System.out.println("Enter date year: ");
                            if (sc.hasNextInt()) {
                                year = sc.nextInt();
                                sc.nextLine();
                                if (year >= 1) {
                                    validYear = true;
                                } else {
                                    System.out.println("Year must be higher than 0. Please try again.");
                                }
                            } else {
                                System.out.println("Invalid input. Please enter a valid number for year.");
                                sc.next();
                            }
                        }

                        boolean validMonth = false;
                        while (!validMonth) {
                            System.out.println("Enter date month: ");
                            if (sc.hasNextInt()) {
                                month = sc.nextInt();
                                sc.nextLine();
                                if (month >= 1 && month <= 12) {
                                    validMonth = true;
                                } else {
                                    System.out.println("Month must be in range 1-12. Please try again.");
                                }
                            } else {
                                System.out.println("Invalid input. Please enter a valid number for month.");
                                sc.next();
                            }
                        }

                        boolean validDay = false;
                        while (!validDay) {
                            System.out.println("Enter date day: ");
                            if (sc.hasNextInt()) {
                                day = sc.nextInt();
                                sc.nextLine();
                                if (day >= 1 && day <= 31) {
                                    validDay = true;
                                } else {
                                    System.out.println("Day must be in range 1-31. Please try again.");
                                }
                            } else {
                                System.out.println("Invalid input. Please enter a valid number for month.");
                                sc.next();
                            }
                        }
                        LocalDate transactionDate = LocalDate.of(year, month, day);
                        transaction.setTransactionDate(transactionDate);
                        break;
                    case "client":
                        System.out.println("Enter transaction clientID: ");
                        transaction.setClientID(sc.nextLine());
                        break;
                    case "salePerson":
                        System.out.println("Enter sale person ID: ");
                        transaction.setSalespersonID(sc.nextLine());
                        break;
                    case "replaced parts":
                        System.out.println("List of parts: " + replacedParts);
                        //Delete choice
                        if (!replacedParts.isEmpty()) {
                            boolean validDelete = false;
                            while (!validDelete) {
                                System.out.println("Enter part Id to delete or enter 'skip': ");
                                String input = sc.nextLine().trim();
                                if (input.equals("skip")) {
                                    validDelete = true;
                                } else if (input.isEmpty()) {
                                    System.out.println("Input cannot be empty");
                                } else {
                                    for (AutoPart replacedPart : replacedParts) {
                                        if (input.equals(replacedPart.getPartID())) {
                                            validDelete = true;
                                            replacedParts.remove(replacedPart);
                                            break;
                                        }
                                    }
                                    if (!validDelete) {
                                        System.out.println("Input invalid");
                                    }
                                }
                            }
                        }

                        //Add choice
                        boolean validAdd = false;
                        while (!validAdd) {
                            System.out.println("Enter part Id to add or enter 'skip': ");
                            String input = sc.nextLine().trim();
                            if (input.equals("skip")) {
                                validAdd = true;
                            } else if (input.isEmpty()) {
                                System.out.println("Input cannot be empty");
                            } else {
                                for (AutoPart part : parts) {
                                    if (input.equals(part.getPartID())) {
                                        validAdd = true;
                                        replacedParts.add(part);
                                        break;
                                    }
                                }
                                if (!validAdd) {
                                    System.out.println("Input invalid");
                                }
                            }
                        }
                        transaction.setReplacedParts(replacedParts);
                        break;
                    case "discount":
                        System.out.println("Enter discount: ");
                        transaction.setDiscount(sc.nextDouble());
                        sc.nextLine();
                        break;
                    case "total amount":
                        System.out.println("Enter total amount: ");
                        transaction.setTotalAmount(sc.nextDouble());
                        sc.nextLine();
                        break;



                    case "notes":
                        System.out.println("Enter service notes: ");
                        transaction.setAdditionalNotes(sc.nextLine());
                        break;
                    default:
                        invalid = true;
                        System.out.println("No matching field found. Please enter a valid field.");
                        break;
                }
                if (!invalid) {
                    writeTransactionsToFile(transactions);
                    System.out.println("Updated: " + id);
                    break;
                }
            }
        }
        if (!exist) {
            System.out.println("No such ID");
        }
    }

    // Method to delete a part
    public static void deleteTransaction() {
        List<SalesTransaction> transactions = readTransactionsFromFile();
        System.out.println("Enter a specific ID to delete:");
        String input = sc.nextLine();
        boolean exist = false;
        for (SalesTransaction transaction : transactions) {
            if (input.equals(transaction.getTransactionID())) {
                exist = true;
                transactions.remove(transaction);
                break;
            }
        }
        if (exist) {
            writeTransactionsToFile(transactions);
            System.out.println("Transaction deleted");
        } else {
            System.out.println("Transaction does not exist");
        }
    }

    // Main control for crud operation
    public static void transactionControl() {
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
            System.out.println("1: Add a transaction");
            System.out.println("2: View transaction");
            System.out.println("3: Update transaction");
            System.out.println("4: Delete transaction");
            System.out.println("0: exit");
            String input = sc.nextLine();

            switch (input) {
                case "1":
                    createTransaction();
                    break;
                case "2":
                    readTransaction();
                    break;
                case "3":
                    updateTransaction();
                    break;
                case "4":
                    deleteTransaction();
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