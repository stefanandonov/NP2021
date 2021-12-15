package mk.ukim.finki.av9;

import java.util.ArrayList;
import java.util.List;

public class SubnumberTest {

    private static List<String> getSubNumbers(String number) {
        List<String> result = new ArrayList<>();
        for (int len = 3; len <= number.length(); len++) {
            for (int i = 0; i <= number.length() - len; i++) {
                result.add(number.substring(i, i+len));
            }
        }
        return result;
    }
    public static void main(String[] args) {
        System.out.println(getSubNumbers("077446818"));
    }
}
