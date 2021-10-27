package mk.ukim.finki.av3.calculator;

import java.util.Scanner;

public class CalculatorTest {

    /**
     * We use a separate function for this logic, to avoid duplicating code.
     * @param line
     * @return the first char of the line, in lowercase
     */
    public static char getCharLower(String line) {
        if (line.trim().length() > 0) {
            return Character.toLowerCase(line.charAt(0));
        } else return '?';
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            Calculator calculator = new Calculator();
            System.out.println(calculator.init());

            while (true) {
                String line = scanner.nextLine();
                char choice = getCharLower(line);
                if (choice == 'r') {
                    System.out.println(String.format("final result = %f", calculator.getResult()));
                    break;
                }
                /** 2nd important thing from this problem: Reading from SI and parsing the input
                 *
                 * In this case we read line by line and split each line on one or more empty spaces.
                 * We obtain an array of strings and then parse each string to the actual type we need.
                 */
                String[] parts = line.split("\\s+");
                char operator = parts[0].charAt(0);
                double value = Double.parseDouble(parts[1]);

                try {
                    String result = calculator.execute(operator, value);
                    System.out.println(result);
                    System.out.println(calculator);
                } catch (UnknownOperatorException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println("(Y/N)");
            String line = scanner.nextLine();
            char choice = getCharLower(line);
            if (choice == 'n') break;
        }
    }
}
