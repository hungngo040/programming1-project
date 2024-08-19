public class Main {

    public static void main(String[] args) {
        System.out.println("HELLO");
        Car car1 = new Car("yes", "Audi", 2008, 820, "Black", Car.Status.AVAILABLE, 4000, "Good as new");
        Car car2 = new Car("yes", "Audi", 2008, 820, "Black", Car.Status.AVAILABLE, 4000, "Good as new");
        Car car3 = new Car("yes", "Audi", 2008, 820, "Black", Car.Status.AVAILABLE, 4000, "Good as new");
        System.out.printf("%s%n%s%n%s%n", car1, car2,car3);
        String stat = String.valueOf(car2.getStatus());
        System.out.println(stat);
        AutoPart part1 = new AutoPart("wheel", "docker", 3, AutoPart.Condition.NEW, "No", 100,"backup wheel");
        System.out.println(part1);
    }
}
