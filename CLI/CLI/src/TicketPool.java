import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;




public class TicketPool {
    private final int maximumCapacity;
    private final int totalTickets;
    private int vendorAddedTickets = 1;
    private final List<Integer> ticketList = Collections.synchronizedList(new ArrayList<>());
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private boolean allTicketsSold = false;
    public TicketPool(int maximumCapacity, int totalTickets){
        this.maximumCapacity=maximumCapacity;
        this.totalTickets=totalTickets;
    }

    public void addTicket (){
        lock.lock();
        try {
            if(vendorAddedTickets > totalTickets){
                System.out.println("Max ticket capacity reached.");
                return;
            }
            while (ticketList.size() >= maximumCapacity) {
                System.out.println("Ticket pool is full. Vendor is waiting");
                Main.logger.info("Ticket pool is full. Vendor is waiting");
                notFull.await();
            }

            ticketList.add(1);
            vendorAddedTickets++;
            System.out.println("1 Ticket added by " + Thread.currentThread().getName() + ", Current size: " + ticketList.size());
            Main.logger.info("1 Ticket added by " + Thread.currentThread().getName() + ", Current size: " + ticketList.size());
            notEmpty.signalAll();
        } catch (InterruptedException e){
        Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
    public void purchaseTicket(){
        lock.lock();
        try{
            while (ticketList.isEmpty()){
                if (vendorAddedTickets >= totalTickets) {
                    allTicketsSold = true;
                    System.out.println("All tickets sold. No more tickets available.");
                    System.out.print("Press 1 to start the ticket process and 0 to stop: ");
                    return;
                }
                    System.out.println("Ticket pool is empty. Customer is waiting.");
                    Main.logger.info("Ticket pool is empty. Customer is waiting.");
                    notEmpty.await();
            }
            ticketList.remove(0);
            System.out.println("1 Ticket purchased by "+Thread.currentThread().getName() + ", Current pool size: " + ticketList.size());
            Main.logger.info("1 Ticket purchased by "+Thread.currentThread().getName() + ", Current pool size: " + ticketList.size());
            notFull.signalAll();
        } catch (InterruptedException e){
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public List<Integer> getTicketList() {
        lock.lock();
        try {
            return ticketList;
        } finally {
            lock.unlock();
        }
    }

    public int getVendorAddedTickets() {
        return vendorAddedTickets;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public boolean isAllTicketsSold() {
        return allTicketsSold;
    }
}
