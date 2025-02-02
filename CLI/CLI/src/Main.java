import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    public static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        eventLogger();

        Configuration config = Configuration.configDetails();
        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());

        List<Thread> vendorThreads = new ArrayList<>();
        List<Thread> customerThreads = new ArrayList<>();
        Scanner scanner=new Scanner(System.in);
        boolean process = true;
        System.out.print("Press 1 to start the ticket process and 0 to stop: ");
        while (process) {
            int decision=scanner.nextInt();
            switch (decision) {
                case 1 -> {
                    if(vendorThreads.isEmpty() && customerThreads.isEmpty()){
                        System.out.println("Starting the process...");
                        System.out.println("System started successfully with " + Configuration.getNoOfVendors() + " vendors and " + Configuration.getNoOfCustomers() + " customers.");
                        for (int i = 1; i <= Configuration.getNoOfVendors(); i++) {
                            Vendor vendor = new Vendor(config.getMaxTicketCapacity(), config.getTicketReleaseRate(), ticketPool);
                            Thread vendorThread = new Thread(vendor, "vendor: " + i);
                            vendorThreads.add(vendorThread);
                            vendorThread.start();
                        }
                        for (int i = 1; i <= Configuration.getNoOfCustomers(); i++) {
                            Customer customer = new Customer(ticketPool, config.getCustomerRetrievalRate());
                            Thread customerThread = new Thread(customer, "customer: " + i);
                            customerThreads.add(customerThread);
                            customerThread.start();
                        }
                    } else if (!vendorThreads.isEmpty() && !customerThreads.isEmpty()){
                        ticketPool = new TicketPool(config.getMaxTicketCapacity(), config.getTotalTickets());
                        System.out.println("Restarting the process.");
                        System.out.println("System started successfully with " + Configuration.getNoOfVendors() + " vendors and " + Configuration.getNoOfCustomers() + " customers.");
                        for (Thread thread : vendorThreads) {
                            thread.interrupt();
                        }
                        for (Thread thread : customerThreads) {
                            thread.interrupt();
                        }
                        vendorThreads.clear();
                        customerThreads.clear();
                        for (int i = 1; i <= Configuration.getNoOfVendors(); i++) {
                            Vendor vendor = new Vendor(config.getMaxTicketCapacity(), config.getTicketReleaseRate(), ticketPool);
                            Thread vendorThread = new Thread(vendor, "vendor: " + i);
                            vendorThreads.add(vendorThread);
                            vendorThread.start();
                        }
                        for (int i = 1; i <= Configuration.getNoOfCustomers(); i++) {
                            Customer customer = new Customer(ticketPool, config.getCustomerRetrievalRate());
                            Thread customerThread = new Thread(customer, "customer: " + i);
                            customerThreads.add(customerThread);
                            customerThread.start();
                        }
                    }
                }
                case 0 -> {
                    System.out.println("Ending the process");
                    for (Thread thread : vendorThreads) {
                        thread.interrupt();
                    }
                    for (Thread thread : customerThreads) {
                        thread.interrupt();
                    }
                    vendorThreads.clear();
                    customerThreads.clear();
                    System.out.println("Ticket process finished.");
                    process = false;
                }
                default -> System.out.println("Invalid input! Please input '1' to start or '0' to stop.");
            }
        }
    }

    public static void eventLogger(){
        try{
            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(false);

            FileHandler fileHandler = new FileHandler("ticketing_system.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
        } catch (IOException e){
            System.out.println("An error occurred while creating the logger." + e.getMessage());
        }
    }
}