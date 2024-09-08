import Auto136.*;
import Crud.*;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
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
                    CarCRUD.carControl();
                    break;
                case "2":
                    AutoPartCRUD.partControl();
                    break;
                case "4":
                    ServiceCRUD.serviceControl();
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
