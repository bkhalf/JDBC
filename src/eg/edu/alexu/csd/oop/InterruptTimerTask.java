package eg.edu.alexu.csd.oop;

import java.util.TimerTask;

class InterruptTimerTask extends TimerTask {
    private Thread theTread;
 
    public InterruptTimerTask(Thread theTread) {
        this.theTread = theTread;
    }
 
    @Override
    public void run() {
        theTread.interrupt();
    }
}