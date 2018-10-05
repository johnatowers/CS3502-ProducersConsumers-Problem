//Class used to implement wait times for consumer
public class Consumer_Wait_Utility {

    private static final int WAIT_TIME = 4; //Max time to wait is 4 seconds

    //Nap function; make consumer wait for random time between 0 and 4 seconds at runtime
    public static void nap(){
        nap(WAIT_TIME);
    }

    //Make consumer wait between 0 and 4 seconds after a char is consumed
    public static void nap(int howLong) {
        int waitTime = (int) (WAIT_TIME * Math.random());
        System.out.println("Consumer waiting for " + waitTime + " seconds...");

        //Causes currently executing thread to wait
        try { Thread.sleep(waitTime*1000); }

        //Throw InterruptedException if a thread interrupts the current thread.
        catch (InterruptedException e) {
            System.out.println("ERROR in nap(): " + e);
        }
    }
}