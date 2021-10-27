package mk.ukim.finki.av3.lambda;

public class AnonymousClassExample {

    FunctionalInterface Addition = new FunctionalInterface() {
        @Override
        public double doOperation(double a, double b) {
            return a + b;
        }
    };

    public static void main(String[] args) {
        AnonymousClassExample example = new AnonymousClassExample();
        System.out.println(example.Addition.doOperation(5, 7));
    }
}
