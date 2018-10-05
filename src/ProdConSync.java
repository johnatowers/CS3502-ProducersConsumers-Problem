import java.sql.Array;

//Main method class
public class ProdConSync {
    public static int inBuffer = 0;     //Keeps track of how many chars in buffer
    public static int added = 0;        //Keeps track of total number of chars added to buffer since start
    public static int removed = 0;      //Keeps track of total number of chars removed from buffer since start
    public static final int N = 100;    //Number of slots in buffer
    public static char sharedBuffer[] = new char[N];    //The bounded buffer

    //Main method
    public static void main(String[] args) {

        //Initially make all slots of buffer empty - marked empty by a zero
        for (int i = 0; i < N; i++) {
            sharedBuffer[i] = '0';
        }

        Semaphore mutex = new Semaphore(1);     //Initialize binary semaphore
        Semaphore full = new Semaphore(0);      //Initialize counting semaphore 'full'
        Semaphore empty = new Semaphore(N);           //Initialize counting semaphore ' empty'
        Producer prod = new Producer(mutex, full, empty);       //Create producer
        Consumer cons = new Consumer(mutex, full, empty);       //Create consumer
        prod.start();   //Start the producer
        cons.start();   //Start the consumer
    }
}
