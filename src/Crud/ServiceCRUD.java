package Crud;

import Auto136.AutoPart;
import Auto136.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static Auto136.Service.*;
import static Crud.AutoPartCRUD.readPartsFromFile;

public class ServiceCRUD {
    private static final Scanner sc = new Scanner(System.in);
    private static final String filename = "service.txt";

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
        String[] fields = partIDs.split(",\\s*");
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
    public static List<Service> readServicesFromFile() {
        List<Service> services = new ArrayList<>();

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
                    List<AutoPart> replacedParts = readReplaceParts(fields.get(5));
                    Service service = new Service(fields.get(0), date, fields.get(2), fields.get(3),
                            fields.get(4), replacedParts,
                            Double.parseDouble(fields.get(6)), fields.get(7));
                    services.add(service);

                    int id = Integer.parseInt(fields.get(0).substring(2));
                    if (id >= serviceCounter) {
                        serviceCounter = id + 1;
                    }
                } catch (Exception e) {
                    System.out.println("An unexpected error occurred while creating service: " + e.getMessage());
                    System.exit(0);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return services;
    }

    // Method to write to file
    public static void writeServicesToFile(List<Service> services) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Service service : services) {
                List<AutoPart> parts = service.getReplacedParts();
                List<String> replacedParts = new ArrayList<>();
                for (AutoPart part : parts) {
                    replacedParts.add(part.getPartID());
                }
                writer.write(service.getServiceID() + "," +
                        service.getServiceDate() + "," +
                        service.getClientID() + "," +
                        service.getMechanicID() + "," +
                        service.getServiceType() + "," +
                        replacedParts + "," +
                        service.getServiceCost() + "," +
                        service.getAdditionalNotes());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createService() {
        List<Service> services = readServicesFromFile();
        List<AutoPart> parts = readPartsFromFile();
        LocalDate serviceDate;
        int year = 0;
        int month = 0;
        int day = 0;

        String clientID;
        String mechanicID;
        String serviceType;
        List<AutoPart> replacedParts = new ArrayList<>();
        double serviceCost = 0.0;
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
            System.out.println("Enter clientID: ");
            clientID = sc.nextLine().trim();
            if (clientID.isEmpty()) {
                System.out.println("ClientID cannot be empty. Please try again.");
            }
        } while (clientID.isEmpty());

        do {
            System.out.println("Enter mechanicID: ");
            mechanicID = sc.nextLine().trim();
            if (mechanicID.isEmpty()) {
                System.out.println("mechanicID cannot be empty. Please try again.");
            }
        } while (mechanicID.isEmpty());

        do {
            System.out.println("Enter serviceType: ");
            serviceType = sc.nextLine().trim();
            if (serviceType.isEmpty()) {
                System.out.println("serviceType cannot be empty. Please try again.");
            }
        } while (serviceType.isEmpty());

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

        boolean validCost = false;
        while (!validCost) {
            System.out.println("Enter service cost: ");
            if (sc.hasNextDouble()) {
                serviceCost = sc.nextDouble();
                sc.nextLine();
                if (serviceCost > 0) {
                    validCost = true;
                } else {
                    System.out.println("service cost must be a positive number. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid number for the cost.");
                sc.next();
            }
        }
        System.out.println("Enter service notes: ");
        notes = sc.nextLine();
        if (notes.isEmpty()) {
            notes = "none";
        }

        serviceDate = LocalDate.of(year,month,day);
        String newServiceId = generateServiceId();
        Service newService = new Service(newServiceId,serviceDate,clientID,mechanicID,serviceType,replacedParts,serviceCost,notes);
        services.add(newService);
        writeServicesToFile(services);

    }

    // Method to display part read from file
    public static void readService() {
        List<Service> services = readServicesFromFile();
        System.out.println("Enter specific ID or a for all:");
        String input = sc.nextLine();
        boolean exist = false;
        if (input.equals("a")) {
            for (Service service : services) {
                System.out.println(service);
            }
        } else {
            for (Service service : services) {
                if (input.equals(service.getServiceID())) {
                    exist = true;
                    System.out.println(service);
                }
            }
            if (!exist) System.out.println("Service does not exist");
        }
    }

    public static void updateService() {
        List<Service> services = readServicesFromFile();
        List<AutoPart> parts = readPartsFromFile();
        System.out.println("Enter service id: ");
        String id = sc.nextLine();
        boolean exist = false;
        for (Service service : services) {
            List<AutoPart> replacedParts = service.getReplacedParts();
            if (id.equals(service.getServiceID())) {
                exist = true;
                System.out.println("Enter data to update (date/client/mechanic/type/replaced parts/cost/notes)");
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
                        LocalDate serviceDate = LocalDate.of(year,month,day);
                        service.setServiceDate(serviceDate);
                        break;
                    case "client":
                        System.out.println("Enter service clientID: ");
                        service.setClientID(sc.nextLine());
                        break;
                    case "mechanic":
                        System.out.println("Enter service mechanicID: ");
                        service.setMechanicID(sc.nextLine());
                        break;
                    case "type":
                        System.out.println("Enter service type: ");
                        service.setServiceType(sc.nextLine());
                        break;
                    case "replaced parts":
                        System.out.println("List of parts: " + replacedParts);
                        //Delete choice
                        if(!replacedParts.isEmpty()) {
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
                        service.setReplacedParts(replacedParts);
                        break;
                    case "cost":
                        System.out.println("Enter service cost: ");
                        service.setServiceCost(sc.nextDouble());
                        sc.nextLine();
                        break;
                    case "notes":
                        System.out.println("Enter service notes: ");
                        service.setAdditionalNotes(sc.nextLine());
                        break;
                    default:
                        invalid = true;
                        System.out.println("No matching field found. Please enter a valid field.");
                        break;
                }
                if (!invalid) {
                    writeServicesToFile(services);
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
    public static void deleteService() {
        List<Service> services = readServicesFromFile();
        System.out.println("Enter a specific ID to delete:");
        String input = sc.nextLine();
        boolean exist = false;
        for (Service service : services) {
            if (input.equals(service.getServiceID())) {
                exist = true;
                services.remove(service);
                break;
            }
        }
        if (exist) {
            writeServicesToFile(services);
            System.out.println("Service deleted");
        } else {
            System.out.println("Service does not exist");
        }
    }

    // Main control for crud operation
    public static void serviceControl() {
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
            System.out.println("1: Add a service");
            System.out.println("2: View service");
            System.out.println("3: Update service");
            System.out.println("4: Delete service");
            System.out.println("0: exit");
            String input = sc.nextLine();

            switch (input) {
                case "1":
                    createService();
                    break;
                case "2":
                    readService();
                    break;
                case "3":
                    updateService();
                    break;
                case "4":
                    deleteService();
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
