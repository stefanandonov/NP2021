package mk.ukim.finki.av4.wordcount;

import java.io.*;
import java.util.Scanner;

public class WordCountTest {

    /**
     * Reads data from an InputStream using Scanner.
     *
     * @param inputStream
     */
    public static void readDataWithScanner(InputStream inputStream) {
        int lines = 0, words = 0, chars = 0;
        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            ++lines;
            words += line.split("\\s+").length;
            chars += line.length();
        }
        System.out.printf("Lines: %d, Words: %d, Chars: %d\n", lines, words, chars);
    }

    /**
     * Reads data from an InputStream using BufferedReader.
     *
     * @param inputStream
     * @throws IOException
     */
    public static void readDataWithBufferedReader(InputStream inputStream) throws IOException {
        int lines = 0, words = 0, chars = 0;
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            ++lines;
            words += line.split("\\s+").length;
            chars += line.length();
        }
        System.out.printf("Lines: %d, Words: %d, Chars: %d\n", lines, words, chars);
    }

    /**
     * Reads data from an InputStream using BufferedReader and map() and reduce() to parse data.
     * With map() we map each line from the input to an object of class LineCounter.
     * With reduce() we give an identity (initial) value - in this case new LineCounter(0, 0, 0)
     * and we show how to perform the reduction using a Function - in this case left represents
     * the aggregated value and right represents the new value added, and using a lambda expression
     * we show the function implementation.
     * Use Ctrl + Click on the reduce() part in the code to read full documentation.
     *
     * @param inputStream
     */
    public static void readDataWithBufferedReaderAndMapReduce(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        LineCounter result = bufferedReader.lines()
                .map(line -> new LineCounter(line))
                .reduce(
                        new LineCounter(0, 0, 0),
                        (left, right) -> left.sum(right)
                );
        System.out.println(result);
    }

    /**
     * The same method as above (readDataWithBufferedReaderAndMapReduce), only using method reference.
     *
     * @param inputStream
     */
    public static void readDataWithBufferedReaderAndMapReduce2(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        LineCounter result = bufferedReader.lines()
                .map(LineCounter::new)
                .reduce(
                        new LineCounter(0, 0, 0),
                        LineCounter::sum
                );
        System.out.println(result);
    }

    /**
     * Reads data from an InputStream using BufferedReader and uses an implementation
     * of the Consumer interface, LineConsumer.
     *
     * @param inputStream
     */
    public static void readDataWithBufferedReaderAndConsumer(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        LineConsumer lineConsumer = new LineConsumer();
        bufferedReader.lines().forEach(lineConsumer);
        System.out.println(lineConsumer);
    }

    public static void main(String[] args) {

        /**
         * The File class is just a wrapper for a file object so that we can work with it.
         */
        File file = new File("C:\\Users\\ana5t\\work\\teaching\\winter" +
                "\\NP\\2021 - 2022\\code\\local\\local\\src\\aud4\\files\\text");

        try {
            readDataWithScanner(new FileInputStream(file));
            readDataWithBufferedReader(new FileInputStream(file));
            readDataWithBufferedReaderAndMapReduce(new FileInputStream(file));
            readDataWithBufferedReaderAndMapReduce2(new FileInputStream(file));
            readDataWithBufferedReaderAndConsumer(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
