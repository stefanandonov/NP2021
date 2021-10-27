package mk.ukim.finki.av3.calculator;

public class UnknownOperatorException extends Exception {

    public UnknownOperatorException(char operator) {
        super(String.format("This operator %c is not valid.", operator));
    }
}
