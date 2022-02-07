package mk.ukim.finki.konsultacii;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class StudentTemp {
    String idx;
    String program;
    Integer grades;

    public StudentTemp(String idx, String program, Integer grades) {
        this.idx = idx;
        this.program = program;
        this.grades = grades;
    }

    public String getIdx() {
        return idx;
    }

    public String getProgram() {
        return program;
    }

    public Integer getGrades() {
        return grades;
    }
}
class Student1 {
    String idx;
    String program;
    List<Integer> grades;

    public Student1(String idx, String program, List<Integer> grades) {
        this.idx = idx;
        this.program = program;
        this.grades = grades;
    }

    public String getIdx() {
        return idx;
    }

    public String getProgram() {
        return program;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public List<StudentTemp> getStudentTemps() {
        return grades.stream()
                .map(grade -> new StudentTemp(idx, program, grade))
                .collect(Collectors.toList());
    }
}

public class MatejTest {
    public static void main(String[] args) {
        List<Student1> students = new ArrayList<>();
        students.add(new Student1("111", "KNI", List.of(10,9,10,8,7,8,10)));
        students.add(new Student1("112", "PET", List.of(8,9,10,6,6,6,6,6,6)));
        students.add(new Student1("113", "PET", List.of(6,7,6,6,6,6,6,6)));
        //    SMER       OCENKA   FREKV
        //Map<String,Map<Integer,Integer>>
        Map<String, Map<Integer, Integer>> map = students.stream()
                .flatMap(student -> student.getStudentTemps().stream())
                .collect(Collectors.groupingBy(
                        StudentTemp::getProgram,
                        Collectors.groupingBy(
                                StudentTemp::getGrades,
                                Collectors.summingInt(i -> 1)
                        )));


//        map.entrySet().stream().sorted((e1,e2) -> Integer.compare(e1.getValue().getOrDefault(10,0), e1.getValue().getOrDefault(10,0)))


    }
}
