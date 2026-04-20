import java.util.Random;

public class zavd3 {
    public static void main(String[] args) {
        int rows = 4;
        int cols = 5;
        int[][] matrix = new int[rows][cols];
        Random random = new Random();

        System.out.println("Початковий масив:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextInt(100);
                System.out.printf("%4d", matrix[i][j]);
            }
            System.out.println();
        }

        int maxR = 0;
        int maxC = 0;
        int maxVal = matrix[0][0];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] > maxVal) {
                    maxVal = matrix[i][j];
                    maxR = i;
                    maxC = j;
                }
            }
        }

        System.out.println("\nНайбільший елемент: " + maxVal + " на позиції [" + maxR + "][" + maxC + "]");

        if (maxR != 0) {
            int[] tempRow = matrix[0];
            matrix[0] = matrix[maxR];
            matrix[maxR] = tempRow;
        }

        if (maxC != 0) {
            for (int i = 0; i < rows; i++) {
                int tempCol = matrix[i][0];
                matrix[i][0] = matrix[i][maxC];
                matrix[i][maxC] = tempCol;
            }
        }

        System.out.println("\nМасив після перестановки (макс. елемент у лівому верхньому куті):");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("%4d", matrix[i][j]);
            }
            System.out.println();
        }
    }
}