package mk.ukim.finki.av10.monday;

import java.util.*;
import java.util.stream.Collectors;

class Student {
    String index;
    List<Integer> labPoints;

    final static int COUNT_OF_LAB_EX = 10;

    public Student(String index, List<Integer> labPoints) {
        this.index = index;
        this.labPoints = labPoints;
    }

    public String getIndex() {
        return index;
    }

    public double getSummaryPoints() {
        return labPoints.stream().mapToInt(i -> i).sum() / (double) COUNT_OF_LAB_EX;
    }

    public boolean hasSignature() {
        return labPoints.size() >= 8;
    }

    public int getYearOfStudies() {
        return 20 - Integer.parseInt(index.substring(0,2));
    }

    @Override
    public String toString() {
        //135042 NO 0.00
        return String.format("%s %s %.2f",
                index,
                hasSignature() ? "YES" : "NO",
                getSummaryPoints()
        );
    }
}

class LabExercises {
    List<Student> students;

    public LabExercises() {
        students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void printByAveragePoints(boolean ascending, int n) {
        Comparator<Student> comparator = Comparator.comparing(Student::getSummaryPoints)
                .thenComparing(Student::getIndex);

        if (!ascending) {
            comparator = comparator.reversed();
        }

        students.stream()
                .sorted(comparator)
                .limit(n)
                .forEach(System.out::println);
    }

    public List<Student> failedStudents() {
        return students.stream()
                .filter(s -> !s.hasSignature())
                .sorted(Comparator.comparing(Student::getIndex)
                        .thenComparing(Student::getSummaryPoints)
                )
                .collect(Collectors.toList());
    }

    public Map<Integer,Double> getStatisticsByYear() {
//        Map<Integer,Double> sumOfPointsByYear = new HashMap<>();
//        Map<Integer,Integer> countByYear = new HashMap<>();
//
//        students.stream()
//                .filter(Student::hasSignature)
//                .forEach(s -> {
//                    sumOfPointsByYear.putIfAbsent(s.getYearOfStudies(), 0.0);
//                    countByYear.putIfAbsent(s.getYearOfStudies(), 0);
//
//                    sumOfPointsByYear.computeIfPresent(
//                            s.getYearOfStudies(),
//                            (k,v) -> v+s.getSummaryPoints()
//                    );
//
//                    countByYear.computeIfPresent(
//                            s.getYearOfStudies(),
//                            (k,v) -> ++v
//                    );
//                });
//
//        sumOfPointsByYear.keySet()
//                .forEach(year -> sumOfPointsByYear.computeIfPresent(
//                        year,
//                        (k,v) -> v/countByYear.get(year)
//                ));
//
//        return sumOfPointsByYear;

        return students.stream()
                .filter(Student::hasSignature)
                .collect(Collectors.groupingBy(
                        Student::getYearOfStudies,
                        Collectors.averagingDouble(Student::getSummaryPoints)
                ));
    }
}

public class LabExercisesTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LabExercises labExercises = new LabExercises();
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] parts = input.split("\\s+");
            String index = parts[0];
            List<Integer> points = Arrays.stream(parts).skip(1)
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());

            labExercises.addStudent(new Student(index, points));
        }

        System.out.println("===printByAveragePoints (ascending)===");
        labExercises.printByAveragePoints(true, 100);
        System.out.println("===printByAveragePoints (descending)===");
        labExercises.printByAveragePoints(false, 100);
        System.out.println("===failed students===");
        labExercises.failedStudents().forEach(System.out::println);
        System.out.println("===statistics by year");
        labExercises.getStatisticsByYear().entrySet().stream()
                .map(entry -> String.format("%d : %.2f", entry.getKey(), entry.getValue()))
                .forEach(System.out::println);

    }
}