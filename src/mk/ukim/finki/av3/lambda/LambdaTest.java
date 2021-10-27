package mk.ukim.finki.av3.lambda;

public class LambdaTest {

    public static void main(String[] args) {
        /**
         * In case we want a more complex implementation of the method from the FunctionalInterface, we use a body {} in
         * which we must put a return statement if the method is not void.
         */
        FunctionalInterface functionalInterface1 = (x, y) -> {
            System.out.println("text");
            x++;
            return x + y;
        };

        /**
         * In case we want a more simple implementation of the method (one line implementation) from the FunctionalInterface, we
         * can just use it directly like in this case.
         */
        FunctionalInterface functionalInterface2 = (x, y) -> x * y;
    }
}
