package mk.ukim.finki.av4.oldest;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OldestPersonTest {

    public static List<Person> readData(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        return bufferedReader.lines()
                .map(Person::new)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        File file = new File("C:\\Users\\ana5t\\work\\teaching\\winter\\NP" +
                "\\2021 - 2022\\code\\local\\local\\src\\aud4\\files\\people");

        try {
            List<Person> list = readData(new FileInputStream(file));

            /**
             * First way to find maximum is by standard iteration.
             * Second way is this one using max() on a stream of persons,
             * specifying that the comparison is done using Comparator of natural order
             * (smallest -> largest).
             */
            if (list.stream().max(Comparator.naturalOrder()).isPresent())
                System.out.println(list.stream().max(Comparator.naturalOrder()).get());

            /**
             * Third way is to use Collections.sort(list).
             * In this way we sort in natural order and take the last element in the list.
             *
             * You can change the compareTo() method in the Person class
             * by changing the order to be: Integer.compare(other.age, this.age),
             * and in this way you will get sorting in reversedOrder.
             */
            Collections.sort(list);
            System.out.println(list.get(list.size() - 1));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
