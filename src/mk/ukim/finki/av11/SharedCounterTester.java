package mk.ukim.finki.av11;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

class Counter extends Thread {
    static int COUNTER = 0;
    static Semaphore semaphore = new Semaphore(1);

    public Counter() {
    }

    @Override
    public void run() {
        try {
            increment();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void increment() throws InterruptedException {
//        semaphore.acquire(1);
        for (int i = 1; i <= 10000; i++) {
//            COUNTER++;
            incrementSingle();
        }
//        semaphore.release(1);
    }

    synchronized static void incrementSingle () {
        COUNTER++;
    }
}

public class SharedCounterTester {
    public static void main(String[] args) {
        List<Counter> counters = new ArrayList<>();
        for (int i=1;i<=1000;i++){
            counters.add(new Counter());
        }

        counters.forEach(Counter::start);

        for (Counter counter : counters) {
            try {
                counter.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(Counter.COUNTER);
    }
}
