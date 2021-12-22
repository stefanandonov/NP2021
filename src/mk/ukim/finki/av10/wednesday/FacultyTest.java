package mk.ukim.finki.av10.wednesday;

//package mk.ukim.finki.vtor_kolokvium;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class OperationNotAllowedException extends Exception {
    OperationNotAllowedException(String message) {
        super(message);
    }
}

abstract class Student {
    String id;
    TreeMap<Integer, List<Integer>> gradesByTerm;
    int coursesPassed;
    TreeSet<String> coursesAttended;

    Student(String id) {
        this.id = id;
        gradesByTerm = new TreeMap<>();
        coursesAttended = new TreeSet<>();
        coursesPassed = 0;
    }

    void addGrade(int term, String courseName, int grade) throws OperationNotAllowedException {
        if (!gradesByTerm.containsKey(term))
            throw new OperationNotAllowedException(String.format("Term %d is not possible for student with ID %s", term, id));

        List<Integer> gradesForTerm = gradesByTerm.get(term);
        if (gradesForTerm.size() == 3) {
            throw new OperationNotAllowedException(String.format("Student %s already has 3 grades in term %d", id, term));
        }

        gradesForTerm.add(grade);
        ++coursesPassed;
        coursesAttended.add(courseName);
    }

    boolean hasGraduated() {
        int terms = gradesByTerm.size();
        return terms * 3 == coursesPassed;
    }

    double calculateAverage() {
        return gradesByTerm.values().stream()
                .flatMap(Collection::stream)
                .mapToInt(i -> i)
                .average()
                .orElse(5.0);
    }

    double calculateAverageForTerm(int term) {
        return gradesByTerm.get(term)
                .stream()
                .mapToInt(i -> i)
                .average()
                .orElse(5.0);
    }

    abstract String getLog();

    private String getDetailedReportForTerm(int term) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Term %d\n", term));
        sb.append(String.format("Courses: %d\n", gradesByTerm.get(term).size()));
        sb.append(String.format("Average grade for term: %.2f", calculateAverageForTerm(term)));
        return sb.toString();
    }

    String getDetailedReport() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Student: %s\n", id));
//        gradesByTerm.keySet().forEach(term -> sb.append(getDetailedReportForTerm(term)).append("\n"));
        sb.append(gradesByTerm.keySet()
                .stream()
                .map(this::getDetailedReportForTerm)
                .collect(Collectors.joining("\n"))
        );
        sb.append(String.format("\nAverage grade: %.2f\n", calculateAverage()));
        sb.append(String.format("Courses attended: %s", String.join(",", coursesAttended)));
        return sb.toString();
    }

    String getShortReport() {
        return String.format("Student: %s Courses passed: %d Average grade: %.2f", id, coursesPassed, calculateAverage());
    }

    public int getCoursesPassed() {
        return coursesPassed;
    }

    public String getId() {
        return id;
    }
}

class Student3 extends Student {

    Student3(String id) {
        super(id);
        for (int i = 1; i <= 6; i++) {
            gradesByTerm.put(i, new ArrayList<>());
        }
    }

    @Override
    String getLog() {
        return String.format("Student with ID %s graduated with average grade %.2f in 3 years.", id, calculateAverage());
    }


}

class Student4 extends Student {

    Student4(String id) {
        super(id);
        for (int i = 1; i <= 8; i++) {
            gradesByTerm.put(i, new ArrayList<>());
        }
    }

    @Override
    String getLog() {
        return String.format("Student with ID %s graduated with average grade %.2f in 4 years.", id, calculateAverage());
    }
}

class Course {
    String name;
    List<Integer> grades;

    public Course(String name) {
        this.name = name;
    }

    public void addGrade(int grade) {
        grades.add(grade);
    }

    @Override
    public String toString() {
        return String.format("%s %d %.2f",
                name,
                studentsCount(),
                getAverage());
    }

    public int studentsCount (){
        return grades.size();
    }

    public double getAverage() {
        return grades.stream().mapToInt(i -> i).average().orElse(5.0);
    }

    public String getName() {
        return name;
    }
}


class Faculty {

    Map<String, Student> studentById;
    Map<String, Course> coursesByCourseName;
    List<String> logs;

    public Faculty() {
        studentById = new HashMap<>();
        logs = new ArrayList<>();
        coursesByCourseName = new HashMap<>();
    }

    void addStudent(String id, int yearsOfStudies) {
        if (yearsOfStudies == 3) {
            studentById.put(id, new Student3(id));
        } else {
            studentById.put(id, new Student4(id));
        }
    }

    void addGradeToStudent(String studentId, int term, String courseName, int grade) throws OperationNotAllowedException {
        Student s = studentById.get(studentId);
        s.addGrade(term, courseName, grade);

        coursesByCourseName.putIfAbsent(courseName, new Course(courseName));
        coursesByCourseName.get(courseName).addGrade(grade);

        if (s.hasGraduated()) {
            logs.add(s.getLog());
            studentById.remove(studentId);
        }
    }

    String getFacultyLogs() {
        return String.join("\n", logs);
    }

    String getDetailedReportForStudent(String id) {
        return studentById.get(id).getDetailedReport();
    }

    void printFirstNStudents(int n) {
        Comparator<Student> comparator = Comparator.comparing(Student::getCoursesPassed)
                .thenComparing(Student::calculateAverage)
                .thenComparing(Student::getId)
                .reversed();

        studentById.values().stream()
                .sorted(comparator)
                .limit(n)
                .forEach(student -> System.out.println(student.getShortReport()));
    }

    void printCourses() {
        Comparator<Course> comparator = Comparator.comparing(Course::studentsCount)
                .thenComparing(Course::getAverage)
                .thenComparing(Course::getName);

        coursesByCourseName.values().stream()
                .sorted(comparator)
                .forEach(System.out::println);
    }
}

public class FacultyTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();

        if (testCase == 1) {
            System.out.println("TESTING addStudent AND printFirstNStudents");
            Faculty faculty = new Faculty();
            for (int i = 0; i < 10; i++) {
                faculty.addStudent("student" + i, (i % 2 == 0) ? 3 : 4);
            }
            faculty.printFirstNStudents(10);

        } else if (testCase == 2) {
            System.out.println("TESTING addGrade and exception");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            try {
                faculty.addGradeToStudent("123", 7, "NP", 10);
            } catch (OperationNotAllowedException e) {
                System.out.println(e.getMessage());
            }
            try {
                faculty.addGradeToStudent("1234", 9, "NP", 8);
            } catch (OperationNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        } else if (testCase == 3) {
            System.out.println("TESTING addGrade and exception");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            for (int i = 0; i < 4; i++) {
                try {
                    faculty.addGradeToStudent("123", 1, "course" + i, 10);
                } catch (OperationNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }
            for (int i = 0; i < 4; i++) {
                try {
                    faculty.addGradeToStudent("1234", 1, "course" + i, 10);
                } catch (OperationNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else if (testCase == 4) {
            System.out.println("Testing addGrade for graduation");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            int counter = 1;
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 3; j++) {
                    try {
                        faculty.addGradeToStudent("123", i, "course" + counter, (i % 2 == 0) ? 7 : 8);
                    } catch (OperationNotAllowedException e) {
                        System.out.println(e.getMessage());
                    }
                    ++counter;
                }
            }
            counter = 1;
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 3; j++) {
                    try {
                        faculty.addGradeToStudent("1234", i, "course" + counter, (j % 2 == 0) ? 7 : 10);
                    } catch (OperationNotAllowedException e) {
                        System.out.println(e.getMessage());
                    }
                    ++counter;
                }
            }
            System.out.println("LOGS");
            System.out.println(faculty.getFacultyLogs());
            System.out.println("PRINT STUDENTS (there shouldn't be anything after this line!");
            faculty.printFirstNStudents(2);
        } else if (testCase == 5 || testCase == 6 || testCase == 7) {
            System.out.println("Testing addGrade and printFirstNStudents (not graduated student)");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), i % 5 + 6);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            if (testCase == 5)
                faculty.printFirstNStudents(10);
            else if (testCase == 6)
                faculty.printFirstNStudents(3);
            else
                faculty.printFirstNStudents(20);
        } else if (testCase == 8 || testCase == 9) {
            System.out.println("TESTING DETAILED REPORT");
            Faculty faculty = new Faculty();
            faculty.addStudent("student1", ((testCase == 8) ? 3 : 4));
            int grade = 6;
            int counterCounter = 1;
            for (int i = 1; i < ((testCase == 8) ? 6 : 8); i++) {
                for (int j = 1; j < 3; j++) {
                    try {
                        faculty.addGradeToStudent("student1", i, "course" + counterCounter, grade);
                    } catch (OperationNotAllowedException e) {
                        e.printStackTrace();
                    }
                    grade++;
                    if (grade == 10)
                        grade = 5;
                    ++counterCounter;
                }
            }
            System.out.println(faculty.getDetailedReportForStudent("student1"));
        } else if (testCase == 10) {
            System.out.println("TESTING PRINT COURSES");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            faculty.printCourses();
        } else if (testCase == 11) {
            System.out.println("INTEGRATION TEST");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 2 : 3); k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }

            }

            for (int i = 11; i < 15; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= 3; k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            System.out.println("LOGS");
            System.out.println(faculty.getFacultyLogs());
            System.out.println("DETAILED REPORT FOR STUDENT");
            System.out.println(faculty.getDetailedReportForStudent("student2"));
            try {
                System.out.println(faculty.getDetailedReportForStudent("student11"));
                System.out.println("The graduated students should be deleted!!!");
            } catch (NullPointerException e) {
                System.out.println("The graduated students are really deleted");
            }
            System.out.println("FIRST N STUDENTS");
            faculty.printFirstNStudents(10);
            System.out.println("COURSES");
            faculty.printCourses();
        }
    }
}
