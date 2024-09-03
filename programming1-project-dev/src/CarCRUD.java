
import java.io.*;
import java.util.List;
import java.util.Scanner;

public class CarCRUD {

    public static Car.Status checkStatus(String status) {
        Car.Status stat = null;
        if (status.equals("available")) {
            stat = Car.Status.AVAILABLE;
        } else if (status.equals("sold")) {
            stat = Car.Status.SOLD;
        }
        return stat;
    }

    public static void Add() throws IOException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("car.dat"));

        @SuppressWarnings("unchecked")
        List<Car> cars;
        try {
            cars = (List<Car>) in.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        in.close();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter car make: ");
        String make = sc.nextLine();
        System.out.println("Enter car model: ");
        String model = sc.nextLine();
        System.out.println("Enter car year: ");
        int year = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter car mileage: ");
        int mileage = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter car color: ");
        String color = sc.nextLine();
        System.out.println("Enter car status(available/sold): ");
        String status = sc.nextLine();
        System.out.println("Enter car price: ");
        double price = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter car notes: ");
        String notes = sc.nextLine();


        Car.Status stat = checkStatus(status);

        Car newCar = new Car(make, model, year,mileage,color,stat,price,notes);

        cars.add(newCar);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("car.dat"));
        out.writeObject(cars);
        out.close();
        System.out.println("Added: " + newCar);

    }

    public static void View() throws IOException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("car.dat"));

        @SuppressWarnings("unchecked")
        List<Car> cars;
        try {
            cars = (List<Car>) in.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        in.close();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter specific ID or a for all:");
        String input = scanner.nextLine();

        if (input.equals("a")) {
            for (Car car : cars) {
                System.out.println(car);
            }
        } else {
            for (Car car : cars) {
                if (input.equals(car.getCarId())) {
                    System.out.println(car);
                }
            }

        }

    }

    public static void Update() throws IOException{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("car.dat"));

        @SuppressWarnings("unchecked")
        List<Car> cars;
        try {
            cars = (List<Car>) in.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        in.close();

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter car id: ");
        String id = sc.nextLine();

        for (Car car : cars) {
            if (id.equals(car.getCarId())) {
                System.out.println("Enter data to update: ");
                String data = sc.nextLine();
                switch (data) {
                    case "make":
                        System.out.println("Enter car make: ");
                        car.setMake(sc.nextLine());
                        break;
                    case "model":
                        System.out.println("Enter car model: ");
                        car.setModel(sc.nextLine());
                        break;
                    case "year":
                        System.out.println("Enter car year: ");
                        car.setYear(sc.nextInt());
                        sc.nextLine();
                        break;
                    case "mileage":
                        System.out.println("Enter car mileage: ");
                        car.setMileage(sc.nextInt());
                        sc.nextLine();
                        break;
                    case "color":
                        System.out.println("Enter car color: ");
                        car.setColor(sc.nextLine());
                        break;
                    case "status":
                        System.out.println("Enter car status(available/sold): ");
                        car.setStatus(checkStatus(sc.nextLine()));
                        break;
                    case "price":
                        System.out.println("Enter car price: ");
                        car.setPrice(sc.nextInt());
                        sc.nextLine();
                        break;
                    case "notes":
                        System.out.println("Enter car notes: ");
                        car.setNotes(sc.nextLine());
                        break;
                }
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("car.dat"));
                out.writeObject(cars);
                out.close();
                System.out.println("Updated: " + id);
            } else{
                System.out.println("No such ID");
                break;
            }
        }


    }

    public static void Delete() throws IOException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("car.dat"));

        @SuppressWarnings("unchecked")
        List<Car> cars;
        try {
            cars = (List<Car>) in.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        in.close();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a specific ID to delete:");
        String input = scanner.nextLine();
        for (Car car : cars) {
            if (input.equals(car.getCarId())) {
                cars.remove(car);
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("car.dat"));
                out.writeObject(cars);
                out.close();
                System.out.println("Deleted: " + input);
            } else {
                System.out.println("No such ID");
                break;
            }
        }
    }
    public static void main(String[] args) throws IOException {


        label:
        while (true){
            System.out.println("1: Add a car");
            System.out.println("2: View car");
            System.out.println("3: Update car");
            System.out.println("4: Delete car");
            System.out.println("0: exit");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    Add();
                    break;
                case "2":
                    View();
                    break;
                case "3":
                    Update();
                    break;
                case "4":
                    Delete();
                    break;
                case "0":
                    break label;
            }

        }
    }
}
