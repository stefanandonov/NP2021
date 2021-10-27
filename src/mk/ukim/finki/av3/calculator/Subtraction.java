package mk.ukim.finki.av3.calculator;

public class Subtraction implements Strategy {
    @Override
    public double calculate(double num1, double num2) {
        return num1 - num2;
    }
}
