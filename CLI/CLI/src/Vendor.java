

public class Vendor implements Runnable{
    private final int maxTicketCapacity;
    private final int ticketReleaseRate;
    private final TicketPool ticketPool;
    public Vendor(int maxTicketCapacity, int ticketReleaseRate, TicketPool ticketPool){
        this.maxTicketCapacity=maxTicketCapacity;
        this.ticketReleaseRate=ticketReleaseRate;
        this.ticketPool=ticketPool;
    }
    @Override
    public void run(){
        while (ticketPool.getVendorAddedTickets() < ticketPool.getTotalTickets()) {
            ticketPool.addTicket();
            try {
                Thread.sleep(1000L/ticketReleaseRate);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }
}
