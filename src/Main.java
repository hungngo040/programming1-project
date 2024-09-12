import Auto136.User;
import Crud.AutoPartCRUD;
import Crud.CarCRUD;
import Crud.ServiceCRUD;
import Crud.UserCRUD;
import Crud.TransactionCRUD;

import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("""
                COSC2081 GROUP ASSIGNMENT
                AUTO168 CAR DEALERSHIP MANAGEMENT SYSTEM
                Instructor: Mr. Minh Vu & Mr. Dung Nguyen
                Group: Group Name
                s4019303, Ngo Viet Hung
                sXXXXXXX, Nguyen Viet Phap
                s3989101, Nguyen Nhat Lam

                """);
        LoginManager.loadUserData();

        if (LoginManager.performLogin(sc)) {
            boolean isActive = true;
            do {
                System.out.println("1: Car operation");
                System.out.println("2: AutoPart operation");
                System.out.println("3: User operation");
                System.out.println("4: Service operation");
                System.out.println("5: Transaction operation");
                System.out.println("0: exit");
                String input = sc.nextLine();

                switch (input) {
                    case "1":
                        if (isManager()) {
                            CarCRUD.carControl();
                            UserLogger.logActivity("Car operation performed by " + LoginManager.getLoggedInUser().getUserID());
                        } else {
                            System.out.println("Access denied. Only managers can perform this operation.");
                            UserLogger.logActivity("Attempted car operation by non-manager " + LoginManager.getLoggedInUser().getUserID());
                        }
                        break;
                    case "2":
                        if (isManager()) {
                            AutoPartCRUD.partControl();
                            UserLogger.logActivity("AutoPart operation performed by " + LoginManager.getLoggedInUser().getUserID());
                        } else {
                            System.out.println("Access denied. Only managers can perform this operation.");
                            UserLogger.logActivity("Attempted AutoPart operation by non-manager " + LoginManager.getLoggedInUser().getUserID());
                        }
                        break;
                    case "3":
                        if (isManager()) {
                            UserCRUD.userControl();
                            UserLogger.logActivity("User operation performed by " + LoginManager.getLoggedInUser().getUserID());
                        } else {
                            System.out.println("Access denied. Only managers can perform this operation.");
                            UserLogger.logActivity("Attempted User operation by non-manager " + LoginManager.getLoggedInUser().getUserID());
                        }
                        break;
                    case "4":
                        if (isManager()) {
                            ServiceCRUD.serviceControl();
                            UserLogger.logActivity("Service operation performed by " + LoginManager.getLoggedInUser().getUserID());
                        } else {
                            System.out.println("Access denied. Only managers can perform this operation.");
                            UserLogger.logActivity("Attempted Service operation by non-manager " + LoginManager.getLoggedInUser().getUserID());
                        }
                        break;

                    case "5":
                        if (isManager()) {
                            System.out.println("Transaction operation selected.");
                            System.out.println("1: Add Transaction");
                            System.out.println("2: Get Transaction by ID");
                            System.out.println("3: Update Transaction");
                            System.out.println("4: Delete Transaction");
                            String transactionInput = sc.nextLine();
                            
                            switch (transactionInput) {
                                case "1":
                                    TransactionCRUD.addTransaction();
                                    break;
                                case "2":
                                    TransactionCRUD.getTransactionById();
                                    break;
                                case "3":
                                    TransactionCRUD.updateTransaction();
                                    break;
                                case "4":
                                    TransactionCRUD.deleteTransaction();
                                    break;
                                default:
                                    System.out.println("Invalid input.");
                            }
                            UserLogger.logActivity("Transaction operation performed by " + LoginManager.getLoggedInUser().getUserID());
                        } else {
                            System.out.println("Access denied. Only managers can perform this operation.");
                            UserLogger.logActivity("Attempted Transaction operation by non-manager " + LoginManager.getLoggedInUser().getUserID());
                        }
                        break;

                    
                    case "0":
                        isActive = false;
                        UserLogger.logActivity("User " + LoginManager.getLoggedInUser().getUserID() + " exited the application.");
                        break;
                    default:
                        System.out.println("Invalid input. Please try again.");
                        UserLogger.logActivity("Invalid input attempt by " + LoginManager.getLoggedInUser().getUserID());
                        break;
                }
            } while (isActive);
        } else {
            System.out.println("Login failed. Exiting application.");
        }
    }

    private static boolean isManager() {
        return LoginManager.getLoggedInUser() != null && LoginManager.getLoggedInUser().getUserType() == User.UserType.MANAGER;
    }
}
