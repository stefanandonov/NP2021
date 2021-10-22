package mk.ukim.finki.av2.prefix;

public class StringPrefix {

    /**
     * @param first  - the first string
     * @param second - the second string
     * @return true if the first string is a prefix of the second one
     *
     * Java has a method for this functionality: startsWith()
     */
    public static boolean isPrefix(String first, String second) {
        if (first.length() > second.length()) return false;

        for (int i = 0; i < first.length(); i++)
            if (first.charAt(i) != second.charAt(i))
                return false;
        return true;
    }

    public static void main(String[] args) {
        System.out.println(StringPrefix.isPrefix("test", "testing"));
        System.out.println(isPrefix("test", "apple"));
        System.out.println(isPrefix("test", "tesla"));
    }
}
