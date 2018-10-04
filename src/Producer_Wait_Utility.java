
//Class used to implement wait times for producer
public class Producer_Wait_Utility {
    private static final int WAIT_TIME = 4; //Max time to wait is 7 seconds

    //Nap function; make producer wait for random time between 0 and 7 seconds at runtime
    public static void nap(){
        nap(WAIT_TIME);
    }

    //Make producer wait between 0 and 7 seconds after a char is produced
    public static void nap(int howLong) {
        int sleeptime = (int) (WAIT_TIME * Math.random() );
        System.out.println("Producer waiting for " + sleeptime + " seconds...");

        //Causes currently executing thread to wait
        try { Thread.sleep(sleeptime*10); }

        //Throw InterruptedException if a thread interrupts the current thread.
        catch (InterruptedException e) {
            System.out.println("ERROR in nap(): " + e);
        }
    }
}
