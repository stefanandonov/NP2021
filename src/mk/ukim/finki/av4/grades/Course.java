package mk.ukim.finki.av4.grades;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Course {

    private List<Student> students;

    public Course() {
        this.students = new ArrayList<>();
    }

    public void readData(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        this.students = bufferedReader.lines()
                .map(Student::createStudent)
                .collect(Collectors.toList());
    }

    public void printSortedData(OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);

        this.students.stream().sorted().forEach(printWriter::println);
        printWriter.flush();
    }

    public void printDetailedData(OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);

        this.students.forEach(i -> printWriter.println(i.printFullFormat()));
        printWriter.flush();
    }

    public void printGradeDistribution(OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);

        int[] gradeDistribution = new int[6];
        for (Student s : students) {
            gradeDistribution[s.getGrade() - 'A']++;
        }

        for (int i = 0; i < 6; i++) {
            printWriter.printf("%c -> %d\n", i + 'A', gradeDistribution[i]);
        }
        printWriter.flush();
    }
}
