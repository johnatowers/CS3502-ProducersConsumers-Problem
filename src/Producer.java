//Producer class
import java.util.*;

public class Producer extends Thread {
    private Semaphore mutex;    //Producers mutex semaphore
    private Semaphore full;     //Producers full semaphore
    private Semaphore empty;    //Producers empty semaphore

    //Constructor
    public Producer(Semaphore m, Semaphore f, Semaphore e) {
        mutex = m;
        full = f;
        empty = e;
    }

    //Start the producer
    public void run() {
        while (true) {      //while loop doesnt terminate, runs indefinitely

            //Make the producer wait a certain amount of time before consuming
            Producer_Wait_Utility.nap();

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

    //This function simply produces a random char
    public char randomChar() {
        char c;
        Random rnd = new Random();  //Generate a new random char to be entered into buffer
        c = (char) (rnd.nextInt(26) + 'a');
        return c;
    }

    //Deposit function; takes random char and adds it to the buffer
    public void deposit() {
        char c = randomChar();

        //If no empty slots in buffer found...
        if (ProdConSync.inBuffer == ProdConSync.N) {
            System.out.println("Buffer is full.");
            return;
        }

        for (int i = 0; i < ProdConSync.N; i++) {       //Iterate through length of buffer...
            if (ProdConSync.sharedBuffer[i] == '0') {   //When empty slot in buffer is detected....
                System.out.println("Producer produced char '" + c + "'");
                ProdConSync.sharedBuffer[i] = c;        //Insert char
                ProdConSync.added++;                    //Increment number added by one
                ProdConSync.inBuffer++;                 //Increment number in buffer by one
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