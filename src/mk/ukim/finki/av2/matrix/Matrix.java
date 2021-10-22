package mk.ukim.finki.av2.matrix;

public class Matrix {

    public static double sum(double[][] matrix) {
        double sum = 0;

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                sum += matrix[i][j];
        return sum;
    }

    public static double average(double[][] matrix) {
        return sum(matrix) / (matrix.length * matrix[0].length);
    }

    public static void main(String[] args) {
        double[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}};

        System.out.println("The sum of the matrix is: " + sum(matrix));
        System.out.println("The average value of the matrix is: " + average(matrix));
    }
}
