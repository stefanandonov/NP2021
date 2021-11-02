package mk.ukim.finki.av4.functional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.*;

public class FunctionalInterfacesTest {

    public static void main(String[] args) {
        /**
         * Predicate: takes one argument and returns a boolean value.
         * In Java 8: use them with filter(), anyMatch(), allMatch(), findFirst() etc.
         * Anonymous class then same implementation using lambda expression.
         */
        Predicate<Integer> LessThan100 = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer < 100;
            }
        };

        Predicate<Integer> lessThan100 = number -> number < 100;

        /**
         * Supplier: does not take arguments, returns a result.
         * Anonymous class then same implementation using lambda expression.
         */
        Supplier<Integer> IntegerSupplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return new Random().nextInt(1000);
            }
        };

        Supplier<Integer> integerSupplier = () -> new Random().nextInt(1000);

        /**
         * Consumer: takes one argument and does not return a result.
         * In Java 8: use them with foreach() etc.
         * Anonymous class, then same implementation using lambda expression, then same lambda expression using method reference.
         */
        Consumer<String> StringConsumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };

        Consumer<String> stringConsumer = s -> System.out.println(s);

        Consumer<String> stringConsumer2 = System.out::println;

        /**
         * Function: takes one argument and returns another.
         * In Java 8: use them with map() etc.
         * In our tasks used very often to parse input using BufferedReader,
         * and then map each line (String) to an object of some type.
         * Anonymous class, then same implementation using lambda expression.
         */
        Function<Integer, String> AddFiveToNumberAndFormat = new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return String.format("%d is the number", integer + 5);
            }
        };

        Function<Integer, String> getAddFiveToNumberAndFormat = integer -> String.format("%d is the number", integer + 5);

        /**
         * BiFunction: takes two arguments and returns one result.
         * In Java 8: use them with reduce() etc.
         * Anonymous class, then same implementation using lambda expression.
         */
        BiFunction<Integer, Integer, String> SumNumbersAndFormat = new BiFunction<Integer, Integer, String>() {
            @Override
            public String apply(Integer integer, Integer integer2) {
                return String.format("%d + %d = %d", integer, integer2, integer + integer2);
            }
        };

        BiFunction<Integer, Integer, String> sumNumbersAndFormat =
                (integer, integer2) -> String.format("%d + %d = %d", integer, integer2, integer + integer2);

        /**
         * Test them all !
         * In this case, stringConsumer is tested.
         * This can be done using the forEach() method on the stream of elements,
         * or directly on the list.
         */

        List<String> list = new ArrayList<>();
        list.add("S");
        list.add("A");
        list.add("P");
        list.add("T");

        list.stream().forEach(stringConsumer);
        list.forEach(stringConsumer);
    }
}
