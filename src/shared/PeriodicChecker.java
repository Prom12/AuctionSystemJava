package shared;

public class PeriodicChecker extends Thread {
    @Override
    public void run()
    {
        while(true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }
        }
    }
}
