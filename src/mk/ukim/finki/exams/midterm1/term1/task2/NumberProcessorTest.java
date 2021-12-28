package mk.ukim.finki.exams.midterm1.term1.task2;

import java.util.*;
import java.util.stream.Collectors;

interface NumberProcessor<R, T extends Number> {
    R compute(ArrayList<T> numbers);
}

class Numbers<T extends Number> {
    ArrayList<T> numbers;

    public Numbers(ArrayList<T> numbers) {
        this.numbers = numbers;
    }

    <R> void process(NumberProcessor<R, T> processor) {
        System.out.println(processor.compute(numbers));
    }
}

public class NumberProcessorTest<T extends Number> {

    public static void main(String[] args) {

        ArrayList<Integer> integerArrayList = new ArrayList<>();
        ArrayList<Double> doubleArrayList = new ArrayList<>();

        int countOfIntegers;
        Scanner sc = new Scanner(System.in);
        countOfIntegers = sc.nextInt();
        while (countOfIntegers > 0) {
            integerArrayList.add(sc.nextInt());
            --countOfIntegers;
        }

        int countOfDoubles;
        countOfDoubles = sc.nextInt();
        while (countOfDoubles > 0) {
            doubleArrayList.add(sc.nextDouble());
            --countOfDoubles;
        }

        Numbers<Integer> integerNumbers = new Numbers<>(integerArrayList);

        //TODO first processor
        NumberProcessor<Long, Integer> firstProcessor = numbers1 -> numbers1.stream()
                .filter(n -> n.doubleValue() < 0)
                .count();
        System.out.println("RESULTS FROM THE FIRST NUMBER PROCESSOR");
        integerNumbers.process(firstProcessor);

        //TODO second processor
        NumberProcessor<String, Integer> secondProcessor = numbers1 -> {
            DoubleSummaryStatistics dss = numbers1.stream()
                    .mapToDouble(Number::doubleValue)
                    .summaryStatistics();
            return String.format("Count: %d Min: %.2f Average: %.2f Max: %.2f",
                    dss.getCount(),
                    dss.getMin(),
                    dss.getAverage(),
                    dss.getMax());
        };
        System.out.println("RESULTS FROM THE SECOND NUMBER PROCESSOR");
        integerNumbers.process(secondProcessor);

        Numbers<Double> doubleNumbers = new Numbers<>(doubleArrayList);

        //TODO third processor
        NumberProcessor<ArrayList<Double>, Double> thirdProcessor = numbers1 -> numbers1.stream()
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));

        System.out.println("RESULTS FROM THE THIRD NUMBER PROCESSOR");
        doubleNumbers.process(thirdProcessor);

        //TODO fourth processor
        NumberProcessor<Double, Double> fourthProcessor = numbers1 -> {
            ArrayList<Double> tmp = numbers1.stream().sorted().collect(Collectors.toCollection(ArrayList::new));
            if (tmp.size() % 2 == 0) {
                return (tmp.get(tmp.size() / 2) + tmp.get(tmp.size() / 2 - 1)) / 2.0;
            } else {
                return tmp.get(tmp.size() / 2);
            }
        };
        System.out.println("RESULTS FROM THE FOURTH NUMBER PROCESSOR");
        doubleNumbers.process(fourthProcessor);

    }

}
