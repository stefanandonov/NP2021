package mk.ukim.finki.av5.math;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class GenericMathOperationsTest {

    public static String statistics(List<? extends Number> numbers) {

        /**
         * DoubleSummaryStatistics is a Java class that holds statistics such as
         * count, min, max, sum, average on a list of double values.
         *
         * DoubleSummaryStatistics can be used as a reduction target for a stream (marked as second way bellow), which means for
         * a list of double values we can call .summaryStatistics() and obtain a DoubleSummaryStatistics object.
         *
         * The code in comment below is another way to create a DoubleSummaryStatistics object.
         */

        /*
            First way

            DoubleSummaryStatistics doubleSummaryStatistics = new DoubleSummaryStatistics();
            numbers.stream().forEach(i -> doubleSummaryStatistics.accept(i.doubleValue()));
         */

        /*
            Second, better way
         */
        DoubleSummaryStatistics doubleSummaryStatistics =
                numbers.stream()
                        .mapToDouble(i -> i.doubleValue())
                        .summaryStatistics();

        double standardDeviation = 0;

        for (Number n : numbers)
            standardDeviation += (n.doubleValue() - doubleSummaryStatistics.getAverage())
                    * (n.doubleValue() - doubleSummaryStatistics.getAverage());

        double finalStandardDeviation = Math.sqrt(standardDeviation / numbers.size());

        return String.format("Min: %.2f\nMax: %.2f\nAverage: %.2f\nStandard deviation: %.2f\n" +
                        "Count: %d\nSum: %.2f",
                doubleSummaryStatistics.getMin(),
                doubleSummaryStatistics.getMax(),
                doubleSummaryStatistics.getAverage(),
                finalStandardDeviation,
                doubleSummaryStatistics.getCount(),
                doubleSummaryStatistics.getSum());
    }

    public static void main(String[] args) {
        Random random = new Random();

        List<Integer> integers = new ArrayList<>();
        IntStream.range(0, 100000)
                .forEach(i -> integers.add(random.nextInt(100) + 1));
        System.out.println(statistics(integers));

        List<Double> doubles = new ArrayList<>();
        IntStream.range(0, 100000)
                .forEach(i -> doubles.add(random.nextDouble() * 100.0));
        System.out.println(statistics(doubles));
    }
}
