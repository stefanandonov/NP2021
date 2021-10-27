package mk.ukim.finki.av3.calculator;

/** 1st important thing from this problem: Strategy Design Pattern
 *
 * We use this interface to implement the pattern.
 *
 * Read more about this pattern on these links:
 * https://www.tutorialspoint.com/design_pattern/strategy_pattern.htm
 * https://www.journaldev.com/1754/strategy-design-pattern-in-java-example-tutorial
 * https://java-design-patterns.com/patterns/strategy/
 * ... many more (just look for Java implementations)
 */
public interface Strategy {
    double calculate(double num1, double num2);
}
