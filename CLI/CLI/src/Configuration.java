import java.sql.SQLOutput;
import java.util.Scanner;

public class Configuration {
    private static int totalTickets;
    private static int ticketReleaseRate;
    private static int customerRetrievalRate;
    private static int maxTicketCapacity;
    private static int noOfVendors;
    private static int noOfCustomers;


    public static Configuration configDetails(){
        Configuration configuration = new Configuration();
        Scanner scanner=new Scanner(System.in);
        System.out.print("Enter total tickets: ");
        totalTickets=scanner.nextInt();
        Main.logger.info("Total tickets: " + totalTickets);
        System.out.print("Enter ticket release rate for vendor in seconds: ");
        ticketReleaseRate=scanner.nextInt();
        Main.logger.info("Ticket Release Rate: " + ticketReleaseRate);
        System.out.print("Enter customer retrieval rate for customer in seconds: ");
        customerRetrievalRate=scanner.nextInt();
        Main.logger.info("Customer Retrieval Rate: " + customerRetrievalRate);
        System.out.print("Enter max ticket capacity for the ticket pool: ");
        maxTicketCapacity=scanner.nextInt();
        Main.logger.info("Max ticket capacity: " + maxTicketCapacity);
        System.out.print("Enter the number of vendors: ");
        noOfVendors = scanner.nextInt();
        Main.logger.info("Number of Vendors: " + noOfVendors);
        System.out.print("Enter the number of customers: ");
        noOfCustomers = scanner.nextInt();
        Main.logger.info("Number of Customers: " + noOfCustomers);


        return configuration;


    }
    public int getTotalTickets(){
        return totalTickets;
    }
    public int getTicketReleaseRate(){
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }
    public int getMaxTicketCapacity(){
        return maxTicketCapacity;
    }

    public static int getNoOfCustomers() {
        return noOfCustomers;
    }

    public static int getNoOfVendors() {
        return noOfVendors;
    }
}
