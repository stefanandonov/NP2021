package mk.ukim.finki.av4.wordcount;

public class LineCounter {

    private int lines;
    private int words;
    private int chars;

    public LineCounter(int lines, int words, int chars) {
        this.lines = lines;
        this.words = words;
        this.chars = chars;
    }

    public LineCounter(String line) {
        this.lines = 1;
        this.words = line.split("\\s+").length;
        this.chars = line.length();
    }

    public LineCounter sum(LineCounter other) {
        return new LineCounter(this.lines + other.lines,
                this.words + other.words,
                this.chars + other.chars);
    }

    @Override
    public String toString() {
        return String.format("Lines: %d, Words: %d, Chars: %d\n", lines, words, chars);
    }
}
