package Crud;

import Auto136.*;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import static Crud.SalesTransactionCRUD.*;
import static Crud.ServiceCRUD.*;

public class StatisticCalculation {
    private static final Scanner sc = new Scanner(System.in);
    private static final String filename = "sale_transaction.txt";

    // Method to calculate the number of cars sold in a specific month
    public static void calculateCarsSoldInMonth() {
        List<SalesTransaction> transactions = readTransactionsFromFile();
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Enter the year (e.g., 2024): ");
                int year = Integer.parseInt(sc.nextLine());

                System.out.println("Enter the month (1-12): ");
                int month = Integer.parseInt(sc.nextLine());

                if (month < 1 || month > 12) {
                    System.out.println("Invalid month entered. Please enter a value between 1 and 12.");
                    continue;
                }

                int carsSold = 0;

                for (SalesTransaction transaction : transactions) {
                    LocalDate transactionDate = transaction.getTransactionDate();
                    YearMonth transactionMonth = YearMonth.from(transactionDate);


                    if (transactionMonth.getYear() == year && transactionMonth.getMonthValue() == month) {
                        carsSold += transaction.getNewCars().size();
                    }
                }

                System.out.println("Total cars sold in " + YearMonth.of(year, month) + ": " + carsSold);

                return;

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric values for year and month.");
            }
        }
    }

    public static void calculateRevenue(String period) {
        List<SalesTransaction> transactions = readTransactionsFromFile();
        List<Service> services = readServicesFromFile();
        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                double totalRevenue = 0;

                switch (period.toLowerCase()) {
                    case "day":
                        System.out.println("Enter day (YYYY-MM-DD): ");
                        LocalDate targetDate = LocalDate.parse(sc.nextLine());

                        // Calculate revenue from transactions
                        for (SalesTransaction transaction : transactions) {
                            if (transaction.getTransactionDate().equals(targetDate)) {
                                totalRevenue += transaction.getTotalAmount();
                            }
                        }


                        for (Service service : services) {
                            if (service.getServiceDate().equals(targetDate)) {
                                totalRevenue += service.getServiceCost();
                            }
                        }

                        System.out.println("Total revenue on " + targetDate + ": " + totalRevenue);
                        return;

                    case "week":
                        System.out.println("Enter year and week number (YYYY-WW): ");
                        String[] parts = sc.nextLine().split("-");
                        int targetYear = Integer.parseInt(parts[0]);
                        int targetWeek = Integer.parseInt(parts[1]);

                        WeekFields weekFields = WeekFields.of(Locale.getDefault());
                        LocalDate startOfWeek = LocalDate.of(targetYear, 1, 1)
                                .with(weekFields.weekOfWeekBasedYear(), targetWeek)
                                .with(DayOfWeek.MONDAY);
                        LocalDate endOfWeek = startOfWeek.plusDays(6);


                        for (SalesTransaction transaction : transactions) {
                            LocalDate transactionDate = transaction.getTransactionDate();
                            if ((transactionDate.isEqual(startOfWeek) || transactionDate.isAfter(startOfWeek)) &&
                                    (transactionDate.isEqual(endOfWeek) || transactionDate.isBefore(endOfWeek))) {
                                totalRevenue += transaction.getTotalAmount();
                            }
                        }


                        for (Service service : services) {
                            LocalDate serviceDate = service.getServiceDate();
                            if ((serviceDate.isEqual(startOfWeek) || serviceDate.isAfter(startOfWeek)) &&
                                    (serviceDate.isEqual(endOfWeek) || serviceDate.isBefore(endOfWeek))) {
                                totalRevenue += service.getServiceCost();
                            }
                        }

                        System.out.println("Total revenue between " + startOfWeek + " and " + endOfWeek + ": " + totalRevenue);
                        return;

                    case "month":
                        System.out.println("Enter year and month (YYYY-MM): ");
                        YearMonth targetMonth = YearMonth.parse(sc.nextLine());


                        for (SalesTransaction transaction : transactions) {
                            LocalDate transactionDate = transaction.getTransactionDate();
                            YearMonth transactionMonth = YearMonth.from(transactionDate);
                            if (transactionMonth.equals(targetMonth)) {
                                totalRevenue += transaction.getTotalAmount();
                            }
                        }


                        for (Service service : services) {
                            LocalDate serviceDate = service.getServiceDate();
                            YearMonth serviceMonth = YearMonth.from(serviceDate);
                            if (serviceMonth.equals(targetMonth)) {
                                totalRevenue += service.getServiceCost();
                            }
                        }

                        System.out.println("Total revenue in " + targetMonth + ": " + totalRevenue);
                        return;

                    default:
                        System.out.println("Invalid period. Choose 'day', 'week', or 'month'.");
                        ;
                }
            } catch (Exception e) {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }

    public static void calculateTotalServiceRevenueByMechanic(String mechanicId) {
        List<Service> services = readServicesFromFile();
        double totalRevenue = 0;

        for (Service service : services) {
            if (service.getMechanicID().equals(mechanicId)) {
                totalRevenue += service.getServiceCost();
            }
        }

        System.out.println("Total revenue from services done by mechanic " + mechanicId + ": " + totalRevenue);
    }

    public static void calculateTotalCarSalesRevenueBySalesperson(String salespersonId) {
        List<SalesTransaction> transactions = readTransactionsFromFile();
        double totalRevenue = 0;

        for (SalesTransaction transaction : transactions) {
            if (transaction.getSalespersonID().equals(salespersonId)) {
                totalRevenue += transaction.getTotalAmount();
            }
        }

        System.out.println("Total revenue from cars sold by salesperson " + salespersonId + ": " + totalRevenue);
    }

    // Method to list all cars sold in a specific day, week, or month
    public static void listCarsSoldInPeriod(String period) {
        List<SalesTransaction> transactions = readTransactionsFromFile();
        Scanner sc = new Scanner(System.in);

        while (true) {
            switch (period.toLowerCase()) {
                case "day":
                    System.out.println("Enter day (YYYY-MM-DD): ");
                    String dayInput = sc.nextLine();

                    try {
                        LocalDate targetDate = LocalDate.parse(dayInput);
                        System.out.println("Cars sold on " + targetDate + ":");
                        for (SalesTransaction transaction : transactions) {
                            if (transaction.getTransactionDate().equals(targetDate)) {
                                printCars(transaction.getNewCars());
                            }
                        }
                        return;
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                    }
                    break;

                case "week":
                    System.out.println("Enter year and week number (YYYY-WW): ");
                    String weekInput = sc.nextLine();
                    String[] weekParts = weekInput.split("-");

                    if (weekParts.length != 2) {
                        System.out.println("Invalid input format. Please use YYYY-WW.");
                        break;
                    }

                    try {
                        int targetYear = Integer.parseInt(weekParts[0]);
                        int targetWeek = Integer.parseInt(weekParts[1]);

                        if (targetWeek < 1 || targetWeek > 53) {
                            System.out.println("Week number must be between 1 and 53.");
                            break;
                        }

                        // Define the start and end dates for the target week
                        WeekFields weekFields = WeekFields.of(Locale.getDefault());
                        LocalDate startOfWeek = LocalDate.of(targetYear, 1, 1)
                                .with(weekFields.weekOfWeekBasedYear(), targetWeek)
                                .with(DayOfWeek.MONDAY);
                        LocalDate endOfWeek = startOfWeek.plusDays(6);

                        System.out.println("Cars sold between " + startOfWeek + " and " + endOfWeek + ":");

                        for (SalesTransaction transaction : transactions) {
                            LocalDate transactionDate = transaction.getTransactionDate();

                            if ((transactionDate.isEqual(startOfWeek) || transactionDate.isAfter(startOfWeek)) &&
                                    (transactionDate.isEqual(endOfWeek) || transactionDate.isBefore(endOfWeek))) {
                                printCars(transaction.getNewCars());
                            }
                        }
                        return;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid year or week number.");
                    }
                    break;

                case "month":
                    System.out.println("Enter year and month (YYYY-MM): ");
                    String monthInput = sc.nextLine();

                    try {
                        YearMonth targetMonth = YearMonth.parse(monthInput);
                        System.out.println("Cars sold in " + targetMonth + ":");
                        for (SalesTransaction transaction : transactions) {
                            LocalDate transactionDate = transaction.getTransactionDate();
                            YearMonth transactionMonth = YearMonth.from(transactionDate);

                            if (transactionMonth.equals(targetMonth)) {
                                printCars(transaction.getNewCars());
                            }
                        }
                        return;
                    } catch (Exception e) {
                        System.out.println("Invalid year and month format. Please enter in YYYY-MM format.");
                    }
                    break;

                default:
                    System.out.println("Invalid period. Choose 'day', 'week', or 'month'.");
                    return;
            }
        }
    }

    private static void printCars(List<Car> cars) {
        for (Car car : cars) {
            System.out.println("Car ID: " + car.getCarId() + ", Model: " + car.getModel());
        }
    }

    public static void listTransactionsInPeriod(String period) {
        List<SalesTransaction> transactions = readTransactionsFromFile();
        Scanner sc = new Scanner(System.in);

        while (true) {
            switch (period.toLowerCase()) {
                case "day":
                    System.out.println("Enter day (YYYY-MM-DD): ");
                    String dayInput = sc.nextLine();

                    try {
                        LocalDate targetDate = LocalDate.parse(dayInput);
                        System.out.println("Transactions on " + targetDate + ":");
                        for (SalesTransaction transaction : transactions) {
                            if (transaction.getTransactionDate().equals(targetDate)) {
                                printTransaction(transaction);
                            }
                        }
                        return;
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                    }
                    break;

                case "week":
                    System.out.println("Enter year and week number (YYYY-WW): ");
                    String weekInput = sc.nextLine();
                    String[] weekParts = weekInput.split("-");

                    if (weekParts.length != 2) {
                        System.out.println("Invalid input format. Please use YYYY-WW.");
                        break;
                    }

                    try {
                        int targetYear = Integer.parseInt(weekParts[0]);
                        int targetWeek = Integer.parseInt(weekParts[1]);

                        if (targetWeek < 1 || targetWeek > 53) {
                            System.out.println("Week number must be between 1 and 53.");
                            break;
                        }

                        // Define the start and end dates for the target week
                        WeekFields weekFields = WeekFields.of(Locale.getDefault());
                        LocalDate startOfWeek = LocalDate.of(targetYear, 1, 1)
                                .with(weekFields.weekOfWeekBasedYear(), targetWeek)
                                .with(DayOfWeek.MONDAY);
                        LocalDate endOfWeek = startOfWeek.plusDays(6);

                        System.out.println("Transactions between " + startOfWeek + " and " + endOfWeek + ":");

                        for (SalesTransaction transaction : transactions) {
                            LocalDate transactionDate = transaction.getTransactionDate();

                            if ((transactionDate.isEqual(startOfWeek) || transactionDate.isAfter(startOfWeek)) &&
                                    (transactionDate.isEqual(endOfWeek) || transactionDate.isBefore(endOfWeek))) {
                                printTransaction(transaction);
                            }
                        }
                        return;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid year or week number.");
                    }
                    break;

                case "month":
                    System.out.println("Enter year and month (YYYY-MM): ");
                    String monthInput = sc.nextLine();

                    try {
                        YearMonth targetMonth = YearMonth.parse(monthInput);
                        System.out.println("Transactions in " + targetMonth + ":");
                        for (SalesTransaction transaction : transactions) {
                            LocalDate transactionDate = transaction.getTransactionDate();
                            YearMonth transactionMonth = YearMonth.from(transactionDate);

                            if (transactionMonth.equals(targetMonth)) {
                                printTransaction(transaction);
                            }
                        }
                        return;
                    } catch (Exception e) {
                        System.out.println("Invalid year and month format. Please enter in YYYY-MM format.");
                    }
                    break;

                default:
                    System.out.println("Invalid period. Choose 'day', 'week', or 'month'.");
                    return;
            }
        }
    }

    private static void printTransaction(SalesTransaction transaction) {
        System.out.println("Transaction ID: " + transaction.getTransactionID());
        System.out.println("Client ID: " + transaction.getClientID());
        System.out.println("Total Amount: " + transaction.getTotalAmount());
    }

    public static void listPartsSoldInPeriod(String period) {
        List<SalesTransaction> transactions = readTransactionsFromFile();
        Scanner sc = new Scanner(System.in);

        while (true) {
            switch (period.toLowerCase()) {
                case "day":
                    System.out.println("Enter day (YYYY-MM-DD): ");
                    String dayInput = sc.nextLine();

                    try {
                        LocalDate targetDate = LocalDate.parse(dayInput);
                        System.out.println("Parts sold on " + targetDate + ":");
                        for (SalesTransaction transaction : transactions) {
                            if (transaction.getTransactionDate().equals(targetDate)) {
                                printParts(transaction.getReplacedParts());
                            }
                        }
                        return;
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                    }
                    break;

                case "week":
                    System.out.println("Enter year and week number (YYYY-WW): ");
                    String weekInput = sc.nextLine();
                    String[] weekParts = weekInput.split("-");

                    if (weekParts.length != 2) {
                        System.out.println("Invalid input format. Please use YYYY-WW.");
                        break;
                    }

                    try {
                        int targetYear = Integer.parseInt(weekParts[0]);
                        int targetWeek = Integer.parseInt(weekParts[1]);

                        if (targetWeek < 1 || targetWeek > 53) {
                            System.out.println("Week number must be between 1 and 53.");
                            break;
                        }

                        // Define the start and end dates for the target week
                        WeekFields weekFields = WeekFields.of(Locale.getDefault());
                        LocalDate startOfWeek = LocalDate.of(targetYear, 1, 1)
                                .with(weekFields.weekOfWeekBasedYear(), targetWeek)
                                .with(DayOfWeek.MONDAY);
                        LocalDate endOfWeek = startOfWeek.plusDays(6);

                        System.out.println("Parts sold between " + startOfWeek + " and " + endOfWeek + ":");

                        for (SalesTransaction transaction : transactions) {
                            LocalDate transactionDate = transaction.getTransactionDate();

                            if ((transactionDate.isEqual(startOfWeek) || transactionDate.isAfter(startOfWeek)) &&
                                    (transactionDate.isEqual(endOfWeek) || transactionDate.isBefore(endOfWeek))) {
                                printParts(transaction.getReplacedParts());
                            }
                        }
                        return;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid year or week number.");
                    }
                    break;

                case "month":
                    System.out.println("Enter year and month (YYYY-MM): ");
                    String monthInput = sc.nextLine();

                    try {
                        YearMonth targetMonth = YearMonth.parse(monthInput);
                        System.out.println("Parts sold in " + targetMonth + ":");
                        for (SalesTransaction transaction : transactions) {
                            LocalDate transactionDate = transaction.getTransactionDate();
                            YearMonth transactionMonth = YearMonth.from(transactionDate);

                            if (transactionMonth.equals(targetMonth)) {
                                printParts(transaction.getReplacedParts());
                            }
                        }
                        return;
                    } catch (Exception e) {
                        System.out.println("Invalid year and month format. Please enter in YYYY-MM format.");
                    }
                    break;

                default:
                    System.out.println("Invalid period. Choose 'day', 'week', or 'month'.");
                    return;
            }
        }
    }

    private static void printParts(List<AutoPart> parts) {
        for (AutoPart part : parts) {
            System.out.println("Part ID: " + part.getPartID() + ", Name: " + part.getPartName());
        }
    }

    public static void listServicesInPeriod(String period) {
        List<Service> services = readServicesFromFile();
        Scanner sc = new Scanner(System.in);

        while (true) {
            switch (period.toLowerCase()) {
                case "day":
                    System.out.println("Enter day (YYYY-MM-DD): ");
                    String dayInput = sc.nextLine();

                    try {
                        LocalDate targetDate = LocalDate.parse(dayInput);
                        System.out.println("Services on " + targetDate + ":");
                        for (Service service : services) {
                            if (service.getServiceDate().equals(targetDate)) {
                                printService(service);
                            }
                        }
                        return;
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please enter the date in YYYY-MM-DD format.");
                    }
                    break;

                case "week":
                    System.out.println("Enter year and week number (YYYY-WW): ");
                    String weekInput = sc.nextLine();
                    String[] weekParts = weekInput.split("-");

                    if (weekParts.length != 2) {
                        System.out.println("Invalid input format. Please use YYYY-WW.");
                        break;
                    }

                    try {
                        int targetYear = Integer.parseInt(weekParts[0]);
                        int targetWeek = Integer.parseInt(weekParts[1]);

                        if (targetWeek < 1 || targetWeek > 53) {
                            System.out.println("Week number must be between 1 and 53.");
                            break;
                        }

                        // Define the start and end dates for the target week
                        WeekFields weekFields = WeekFields.of(Locale.getDefault());
                        LocalDate startOfWeek = LocalDate.of(targetYear, 1, 1)
                                .with(weekFields.weekOfWeekBasedYear(), targetWeek)
                                .with(DayOfWeek.MONDAY);
                        LocalDate endOfWeek = startOfWeek.plusDays(6);

                        System.out.println("Services between " + startOfWeek + " and " + endOfWeek + ":");

                        for (Service service : services) {
                            LocalDate serviceDate = service.getServiceDate();

                            if ((serviceDate.isEqual(startOfWeek) || serviceDate.isAfter(startOfWeek)) &&
                                    (serviceDate.isEqual(endOfWeek) || serviceDate.isBefore(endOfWeek))) {
                                printService(service);
                            }
                        }
                        return;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid year or week number.");
                    }
                    break;

                case "month":
                    System.out.println("Enter year and month (YYYY-MM): ");
                    String monthInput = sc.nextLine();

                    try {
                        YearMonth targetMonth = YearMonth.parse(monthInput);
                        System.out.println("Services in " + targetMonth + ":");
                        for (Service service : services) {
                            LocalDate serviceDate = service.getServiceDate();
                            YearMonth serviceMonth = YearMonth.from(serviceDate);

                            if (serviceMonth.equals(targetMonth)) {
                                printService(service);
                            }
                        }
                        return;
                    } catch (Exception e) {
                        System.out.println("Invalid year and month format. Please enter in YYYY-MM format.");
                    }
                    break;

                default:
                    System.out.println("Invalid period. Choose 'day', 'week', or 'month'.");
                    return;
            }
        }
    }


    private static void printService(Service service) {
        System.out.println("Service ID: " + service.getServiceID());
        System.out.println("Client ID: " + service.getClientID());
        System.out.println("Total Cost: " + service.getServiceCost());
    }

    // Control for statistical operation
    public static void ManagerStatisticOpControl() {
        File file = new File(filename);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        boolean isActive = true;
        do {
            System.out.println("1: Calculate car sold in month");
            System.out.println("2: Calculate transaction revernue in a period");
            System.out.println("3: Calculate the revenue of the services done of a mechanic");
            System.out.println("4: Calculate the revenue of the cars sold of a salesperson");
            System.out.println("5: List of car sold in a period");
            System.out.println("6: List of transactions in a period ");
            System.out.println("7: List of service in a period");
            System.out.println("8: List of part sold in a period ");
            System.out.println("0: exit");
            String input = sc.nextLine();

            switch (input) {
                case "1":
                    calculateCarsSoldInMonth();
                break;
                case "2":
                    System.out.println("Enter the period you want(day/week/month)");
                    String period1 = sc.nextLine();
                    calculateRevenue(period1);
                break;
                case "3":
                    System.out.println("Enter the mechanic id: ");
                    String mechanicID = sc.nextLine();
                    calculateTotalServiceRevenueByMechanic(mechanicID);
                break;
                case "4":
                    System.out.println("Enter the sale person id: ");
                    String salesPersonID = sc.nextLine();
                    calculateTotalCarSalesRevenueBySalesperson(salesPersonID);
                break;
                case "5":
                    System.out.println("Enter the period you want(day/week/month)");
                    String period = sc.nextLine();
                    listCarsSoldInPeriod(period);
                break;
                case "6":
                    System.out.println("Enter the period you want(day/week/month)");
                    String period4 = sc.nextLine();
                    listTransactionsInPeriod(period4);
                break;
                case "7":
                    System.out.println("Enter the period you want(day/week/month)");
                    String period5 = sc.nextLine();
                    listServicesInPeriod(period5);
                break;
                case "8":
                    System.out.println("Enter the period you want(day/week/month)");
                    String period6 = sc.nextLine();
                    listPartsSoldInPeriod(period6);
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

    public static void employeeStatisticOpControl() {
        File file = new File(filename);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        boolean isActive = true;
        do {
            System.out.println("1: Calculate transaction revernue in a period");
            System.out.println("2: List of car sold in a period");
            System.out.println("3: List of service in a period");
            System.out.println("0: exit");
            String input = sc.nextLine();

            switch (input) {
                case "1":
                    System.out.println("Enter the period you want(day/week/month)");
                    String period1 = sc.nextLine();
                    calculateRevenue(period1);
                    break;
                case "2":
                    System.out.println("Enter the period you want(day/week/month)");
                    String period = sc.nextLine();
                    listCarsSoldInPeriod(period);
                    break;
                case "3":
                    System.out.println("Enter the period you want(day/week/month)");
                    String period5 = sc.nextLine();
                    listServicesInPeriod(period5);
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
