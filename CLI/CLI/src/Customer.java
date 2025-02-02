public class Customer implements Runnable{
    private final TicketPool ticketPool;
    private final int customerRetrievalRate;

    public Customer(TicketPool ticketPool, int customerRetrievalRate){
        this.ticketPool=ticketPool;
        this.customerRetrievalRate=customerRetrievalRate;
    }

    @Override
    public void run(){
        while (!ticketPool.isAllTicketsSold()) {
            ticketPool.purchaseTicket();
            if(ticketPool.isAllTicketsSold()){
                return;
            }
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            try {
                Thread.sleep(1000L/customerRetrievalRate);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
