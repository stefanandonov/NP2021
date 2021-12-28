package mk.ukim.finki.exams.midterm1.term1.task0;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class LineProcessor {
    List<String> lines;

    LineProcessor() {

    }

    private long countOccurrences(String string, char c) {
        return string.toLowerCase().chars().filter(ch -> ch == Character.toLowerCase(c)).count();
    }

    public void readLines(InputStream is, OutputStream os, char c) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        lines = br.lines()
                .collect(Collectors.toList());

        br.close();

        PrintWriter pw = new PrintWriter(os);
        Comparator<String> comparator = (o1, o2) -> (int) (countOccurrences(o1, c) - countOccurrences(o2, c));
        String result = lines.stream().max(comparator.thenComparing(Comparator.naturalOrder())).orElse("");
        pw.println(result);
        pw.flush();
    }
}

public class LineProcessorTest {
    public static void main(String[] args) {
        LineProcessor lineProcessor = new LineProcessor();

        try {
            lineProcessor.readLines(System.in, System.out, 'a');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

