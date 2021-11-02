package mk.ukim.finki.av4.arraylist;

import java.util.ArrayList;
import java.util.List;

public class ArrayListTest {

    public static void main(String[] args) {

        /**
         * ArrayList and LinkedList implement the List interface.
         * They can store lists of theoretically unlimited size.
         * ArrayList is less efficient than an array, does not support [] notation
         * and must store data of a class type (or other reference type).
         */

        /**
         * 100 in this case is the initial capacity, does not limit the size.
         * This initial capacity can be specified, but it is not necessary.
         */
        List<Integer> integerList = new ArrayList<>(100);
        List<String> stringList = new ArrayList<>();

        /**
         * Add elements to list: add() method.
         */
        integerList.add(7);
        integerList.add(8);
        integerList.add(9);
        integerList.add(2);
        integerList.add(5);
        integerList.add(9);

        stringList.add("A");

        /**
         * Print list: only works well if the elements in the ArrayList have their own toString override.
         * For example, if we store Student objects in an ArrayList, the Student class must have a
         * toString implementation.
         */
        System.out.println(integerList);
        System.out.println(stringList);

        /**
         * Get number of elements in list: size() method.
         */
        System.out.println(integerList.size());

        /**
         * Get element at position x: get(int x) method.
         */
        System.out.println(integerList.get(2));

        /**
         * See if a list contains an element: contains(Object o) method.
         */
        System.out.println(integerList.contains(9));
        System.out.println(integerList.contains(4));

        /**
         * See the first appearance of an element in list: indexOf(Object o) method.
         */
        System.out.println(integerList.indexOf(9));

        /**
         * See the last appearance of an element in list: lastIndexOf(Object o) method.
         */
        System.out.println(integerList.lastIndexOf(9));

        /**
         * Remove elements from list based on some logic, using a predicate: removeIf(Predicate filter).
         */
        System.out.println(integerList.removeIf(i -> i > 6));
        System.out.println(integerList);
    }
}
