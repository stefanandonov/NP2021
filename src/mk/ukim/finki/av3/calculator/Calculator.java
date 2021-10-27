package mk.ukim.finki.av3.calculator;

public class Calculator {

    private double result;
    private Strategy strategy;

    public Calculator() {
        this.result = 0.0;
    }

    public String execute(char operation, double value) throws UnknownOperatorException {
        if (operation == '+') {
            strategy = new Addition();
        } else if (operation == '-') {
            strategy = new Subtraction();
        } else if (operation == '*') {
            strategy = new Multiplication();
        } else if (operation == '/') {
            strategy = new Division();
        } else throw new UnknownOperatorException(operation);

        /**
         * 3rd important thing from this problem: Exceptions and their handling
         *
         * Never throw an exception of type X that in a try-catch block that catches an exception of type X.
         * For example, in this case, we should not put a try-catch block here to throw the UnknownOperatorException within the try part
         * and then catch the exception in the catch part.
         *
         * See the code in CalculatorTest to see how to use the try-catch block.
         */

        result = strategy.calculate(result, value);
        return String.format("result %c %.2f = %.2f", operation, value, result);
    }

    public double getResult() {
        return result;
    }

    public String init() {
        return String.format(" result = %.2f", result);
    }

    @Override
    public String toString() {
        return String.format("updated result = %.2f", result);
    }
}
