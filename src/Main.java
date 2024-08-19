import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        System.out.println("HELLO");
        Car car1 = new Car("yes", "Audi", 2008, 820, "Black", Car.Status.AVAILABLE, 4000, "Good as new");
        Car car2 = new Car("yes", "Audi", 2008, 820, "Black", Car.Status.AVAILABLE, 4000, "Good as new");
        Car car3 = new Car("yes", "Audi", 2008, 820, "Black", Car.Status.AVAILABLE, 4000, "Good as new");
        System.out.printf("%s%n%s%n%s%n", car1, car2,car3);
        User user1 = new User("s001","Jonny",new Date(30,12,3992),"China","02300323023","Jommy@gmail.com", User.UserType.SALESPERSON,true);
        System.out.println(user1);
        String stat = String.valueOf(car2.getStatus());
        System.out.println(stat);
        AutoPart part1 = new AutoPart("wheel", "docker", 3, AutoPart.Condition.NEW, "No", 100,"backup wheel");
        AutoPart part2 = new AutoPart("door","Toyota",2, AutoPart.Condition.NEW,"2 years",1000,"backup door");
        System.out.println(part1);
        // Create a list of replaced parts
        List<AutoPart> replacedParts = new ArrayList<>();
        replacedParts.add(part1);
        replacedParts.add(part2);
        Service service1 = new Service("sv001",new Date(2,4,2003),"cl0023","mc0089","repair",replacedParts,3000,"Discount");
        System.out.println(service1);
        ArrayList<Purchasable> purchasedItems = new ArrayList<>();
        purchasedItems.add(car2);
        purchasedItems.add(part1);
        SalesTransaction sale1 = new SalesTransaction("393939",new Date(3,9,2018),"cl1001","s0020",purchasedItems,500,9000,"No");
        System.out.println(sale1);

    }
}
