package mk.ukim.finki.av5.box;

import java.util.stream.IntStream;

public class BoxTest {

    public static void main(String[] args) {
        Box<Circle> box = new Box<>();

        IntStream.range(0, 100)
                .forEach(i -> new Circle());

        IntStream.range(0, 103)
                .forEach(element -> System.out.println(box.draw()));
    }
}
