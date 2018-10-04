//Semaphore class
public class Semaphore {

    //used to keep track of the semaphores state
    private int sem;

    //Constructor
    public Semaphore(int intval)
    {
        sem = intval;
    }

    //Semaphore wait method, begin entry section
    public synchronized void swait() {
        while(sem <= 0) {
            try {
                wait();
            } catch (Exception e) {
                System.exit(0);
            }
        }
        sem--;
    }
    //Semaphore signal method, begin exit section
    public synchronized void signal()
    {
        sem++;
        notify();
    }
}
