import Auto136.User;
import Crud.AutoPartCRUD;
import Crud.CarCRUD;
import Crud.ServiceCRUD;
import Crud.UserCRUD;

import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
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
