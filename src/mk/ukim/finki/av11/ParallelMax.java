package mk.ukim.finki.av11;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class Searcher extends Thread {
    int start;
    int end;
    int max;

    public Searcher(int start, int end) {
        this.start = start;
        this.end = end;
        max = ParallelMax.ARRAY[start];
    }

    public int getMax() {
        return max;
    }

    @Override
    public void run() {
        for (int i = start + 1; i < end; i++) {
            if (ParallelMax.ARRAY[i] > max) {
                max = ParallelMax.ARRAY[i];
            }
        }
    }
}

public class ParallelMax {
    static int SIZE = 1000000;
    static int SEARCHER = 1000;
    static Random RANDOM = new Random();
    static int ARRAY[] = new int[SIZE];

    public static void main(String[] args) {
        for (int i = 0; i < SIZE; i++) {
            ARRAY[i] = RANDOM.nextInt(1000000);
        }

        LocalDateTime startTime = LocalDateTime.now();
        int max = Arrays.stream(ARRAY).max().getAsInt();
        LocalDateTime endTime = LocalDateTime.now();
        System.out.printf("Finding maximum with linear search: %d\n", Duration.between(startTime,endTime).toMillis());

        startTime = LocalDateTime.now();
        List<Searcher> searchers = new ArrayList<>();
        int step = SIZE / SEARCHER;
        for (int start = 0; start < SIZE; start += step) {
            int end = start+step;
            searchers.add(new Searcher(start, end));
        }

        searchers.forEach(Searcher::start);
        for (Searcher searcher : searchers) {
            try {
                searcher.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(searchers.stream()
                .mapToInt(Searcher::getMax)
                .max()
                .getAsInt()
        );
        endTime = LocalDateTime.now();

        System.out.printf("Finding max with threads took %s", Duration.between(startTime, endTime).toMillis());
    }


}
