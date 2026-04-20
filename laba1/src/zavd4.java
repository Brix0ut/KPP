public class zavd4 {
    public static void main(String[] args) {
        int[][] magicSquare = {
                {4, 14, 12},
                {18, 10, 2},
                {8, 6, 16}
        };

        int[][] notMagicSquare = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        System.out.println("Перший масив є магічним квадратом? " + isMagicSquare(magicSquare));
        System.out.println("Другий масив є магічним квадратом? " + isMagicSquare(notMagicSquare));
    }

    public static boolean isMagicSquare(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }

        int n = matrix.length;

        for (int i = 0; i < n; i++) {
            if (matrix[i].length != n) {
                return false;
            }
        }

        int targetSum = 0;
        for (int j = 0; j < n; j++) {
            targetSum += matrix[0][j];
        }

        for (int i = 1; i < n; i++) {
            int rowSum = 0;
            for (int j = 0; j < n; j++) {
                rowSum += matrix[i][j];
            }
            if (rowSum != targetSum) return false;
        }

        for (int j = 0; j < n; j++) {
            int colSum = 0;
            for (int i = 0; i < n; i++) {
                colSum += matrix[i][j];
            }
            if (colSum != targetSum) return false;
        }

        int mainDiagSum = 0;
        for (int i = 0; i < n; i++) {
            mainDiagSum += matrix[i][i];
        }
        if (mainDiagSum != targetSum) return false;

        int antiDiagSum = 0;
        for (int i = 0; i < n; i++) {
            antiDiagSum += matrix[i][n - 1 - i];
        }
        if (antiDiagSum != targetSum) return false;

        return true;
    }
}