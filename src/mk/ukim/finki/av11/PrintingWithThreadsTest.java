package mk.ukim.finki.av11;

import java.util.ArrayList;
import java.util.List;

class Printer extends Thread {
    int numberToPrint;

    public Printer(int numberToPrint) {
        this.numberToPrint = numberToPrint;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(numberToPrint*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(numberToPrint);
    }
}

public class PrintingWithThreadsTest {

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();

        int n = 100;
        for (int i = 1; i <= n; i++) {
            int finalI = i;
            threads.add(new Printer(i));
        }
        //starts all the threads
        threads.forEach(Thread::start);

        //waits for the threads to end
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
