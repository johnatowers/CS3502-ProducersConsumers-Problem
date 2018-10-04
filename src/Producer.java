//Producer class
import java.util.*;

public class Producer extends Thread {
    private Semaphore mutex;
    private Semaphore full;
    private Semaphore empty;

    //Constructor
    public Producer(Semaphore m, Semaphore f, Semaphore e) {
        mutex = m;
        full = f;
        empty = e;
    }

    //Start the producer
    public void run() {
        while (true) {      //while loop doesnt terminate, runs indefinitely
            Producer_Wait_Utility.nap();    //Make the producer wait a certain amount of time before producing

            //Begin entry section
            empty.swait();
            mutex.swait();

            //Execute critical section
            deposit();  // Deposit a char into an empty slot of the buffer

            //Begin exit section
            mutex.signal();
            full.signal();
        }
    }

    //This function simply produces a random char to add to the buffer
    public char randomChar() {
        char c;
        Random rnd = new Random();  //Generate a new random char to be entered into buffer
        c = (char) (rnd.nextInt(26) + 'a');
        return c;
    }

    //Deposit function; takes random char and adds it to the buffer
    public void deposit() {
        char c = randomChar();

        //If no empty slots in buffer found:
        if (ProdConSync.inBuffer == ProdConSync.N) {
            System.out.println("Buffer is full.");
            return;
        }

        for (int i = 0; i < ProdConSync.N; i++) {

            //When empty slot in buffer is detected:
            if (ProdConSync.sharedBuffer[i] == '0') {
                System.out.println("Producer produced char '" + c + "'");
                ProdConSync.sharedBuffer[i] = c;
                ProdConSync.added++;
                ProdConSync.inBuffer++;
                System.out.println("Producer deposited the char '" + c + "' into the buffer.");
                System.out.println("                   #in buffer: " + ProdConSync.inBuffer);
                System.out.println("                      # added: " + ProdConSync.added);
                System.out.println("                    # removed: " + ProdConSync.removed);
                System.out.println();
                return;
            }
        }
    }
}