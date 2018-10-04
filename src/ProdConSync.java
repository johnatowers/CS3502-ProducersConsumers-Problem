import java.sql.Array;
import java.time.*;
import java.util.*;

import static java.time.Duration.*;

public class ProdConSync {
    public static int inBuffer = 0;
    public static int added = 0;
    public static int removed = 0;
    public static final int N = 100; // Slots in buffer
    public static char sharedBuffer[] = new char[N];


    public static void main(String[] args) {
        for (int i = 0; i < N; i++) {
            sharedBuffer[i] = '0';
        }

        Semaphore mutex = new Semaphore(1);
        Semaphore full = new Semaphore(0);
        Semaphore empty = new Semaphore(N);
        Producer prod = new Producer(mutex, full, empty);
        Consumer cons = new Consumer(mutex, full, empty);
        //Duration duration = Duration.ofSeconds(30);
        //Instant start = Instant.now();
        //Instant stop = start.plusSeconds(30);
        prod.start();
        cons.start();
        /*Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                prod.stop();
                cons.stop();
            }
        }, 2*1000);*/
    }
}
