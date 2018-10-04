public class Consumer extends Thread {
    private Semaphore mutex;
    private Semaphore full;
    private Semaphore empty;
    public char consumed = '0';

    //Constructor
    public Consumer(Semaphore m, Semaphore f, Semaphore e) {
        mutex = m;
        full = f;
        empty = e;
    }

    //Start the consumer
    public void run() {
        while (true)     //While loop doesnt terminate, runs indefinitely
        {
            //Make the producer wait a certain amount of time before consuming
            Consumer_Wait_Utility.nap();

            //Begin entry section
            full.swait();
            mutex.swait();

            //Execute critical section
            removeItem();// Remove char from full slot of buffer

            //Begin exit section
            mutex.signal();
            empty.signal();

            //'Consume' the char
            consumeItem();
            // Exit out of loop
        }
    }

    //Function used to remove a char from the buffer
    public void removeItem() {

        //If the buffer is empty
        if (ProdConSync.inBuffer == 0) {
            System.out.println("Buffer is empty.");
            return;
        }
        //Search the entire array...
        for (int i = 0; i < ProdConSync.N; i++) {
            if (ProdConSync.sharedBuffer[i] != 0) {
                consumed = ProdConSync.sharedBuffer[i];
                ProdConSync.sharedBuffer[i] = 0;
                ProdConSync.removed++;
                ProdConSync.inBuffer--;
                System.out.println("Consumer removed char '" + consumed + "' from buffer.");
                System.out.println("                   #in buffer: " + ProdConSync.inBuffer);
                System.out.println("                      # added: " + ProdConSync.added);
                System.out.println("                    # removed: " + ProdConSync.removed);
                System.out.println();
                return;
            }
        }
    }


    //'Destroy' the removed char
    public void consumeItem() {
        if (ProdConSync.inBuffer == 0) {
            return;
        } else {
            System.out.println("Consumer consumed char '" + consumed + "'");
            consumed = '0';
        }
    }
}
