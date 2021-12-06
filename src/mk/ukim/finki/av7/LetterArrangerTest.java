package mk.ukim.finki.av7;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

class LetterArranger{

    public static String arrangeSentence(String sentence) {
        return Arrays.stream(sentence.split("\\s+"))
                .map(word -> arrangeWord(word))
                .sorted()
                .collect(Collectors.joining(" "));
    }

    private static String arrangeWord(String word) {
        return word.chars()
                .sorted()
                .mapToObj(ch -> String.valueOf((char)ch))
                .collect(Collectors.joining());
    }
}

public class LetterArrangerTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String sentence = sc.nextLine();

        System.out.println(LetterArranger.arrangeSentence(sentence));
    }
}
