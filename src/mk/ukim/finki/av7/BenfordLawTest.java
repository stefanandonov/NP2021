package mk.ukim.finki.av7;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Counter {
    int[] countingArray;
    int digitsCount;

    Counter(){
        countingArray = new int [10];
        digitsCount = 0;
    }

    void countDigit (int digit){
        countingArray[digit]++;
        digitsCount++;
    }

    @Override
    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        for (int i=1;i<10;i++){
//            sb.append(String.format("%d --> %.2f %%\n", i, countingArray[i]*100.0/digitsCount));
//        }
//        return sb.toString();

        return IntStream.range(1,10)
                .mapToObj(i -> String.format("%d --> %.2f %%", i, countingArray[i]*100.0/digitsCount))
                .collect(Collectors.joining("\n"));
    }
}

class BenfordLaw {
    List<Integer> numbers;
    Counter counter;

    BenfordLaw(){
        numbers = new ArrayList<>();
        counter = new Counter();
    }

    public void readData(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        numbers = br.lines()
                .filter(line -> line.length()>0)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public void conduct() {
        numbers.stream() //[123,456] -> [1,4]
                .map(this::getFirstDigit)
                .forEach(firstDigit -> counter.countDigit(firstDigit));
    }

    private int getFirstDigit(int number) {
        while (number>=10){
            number/=10;
        }
        return number;
    }

    @Override
    public String toString() {
        return counter.toString();
    }
}

public class BenfordLawTest {
    public static void main(String[] args) {
        BenfordLaw benfordLaw = new BenfordLaw();

        try {
            benfordLaw.readData(new FileInputStream("src/mk/ukim/finki/av7/data/librarybooks.txt"));

            benfordLaw.conduct();

            System.out.println(benfordLaw);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
