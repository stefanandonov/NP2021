package mk.ukim.finki.av4.grades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class CalculateGradesTest {

    public static void main(String[] args) {

        Course course = new Course();
        File inputFile = new File("C:\\Users\\ana5t\\work\\teaching\\winter\\NP\\2021 - 2022\\code\\local\\local\\src\\aud4\\files\\students");
        File outputFile = new File("C:\\Users\\ana5t\\work\\teaching\\winter\\NP\\2021 - 2022\\code\\local\\local\\src\\aud4\\files\\results");
        try {
            course.readData(new FileInputStream(inputFile));
            System.out.println("===Printing sorted students to screen===");
            course.printSortedData(System.out);

            System.out.println("===Printing detailed report to file===");
            course.printDetailedData(new FileOutputStream(outputFile));

            System.out.println("===Printing grade distribution to screen===");
            course.printGradeDistribution(System.out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
