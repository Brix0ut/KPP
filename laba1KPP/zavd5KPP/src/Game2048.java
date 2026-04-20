import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game2048 {
    static final int SIZE = 4;
    static int[][] board;
    static int score;
    static int bestScore = 0;
    static Random random = new Random();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Гра 2048 ===");
        System.out.println("Правила: об'єднуйте однакові плитки, щоб отримати 2048.");
        System.out.println("Управління: W (вгору), A (вліво), S (вниз), D (вправо). Після літери натискайте Enter.");
        System.out.println("Щоб перервати гру, введіть Q.");

        while (true) {
            playGame();
            System.out.print("\nБажаєте розпочати нову гру? (1 - так, 0 - вийти): ");
            String choice = scanner.next();
            if (!choice.equals("1")) {
                break;
            }
        }
        System.out.println("\nДякую за гру! Ваш найкращий рекорд: " + bestScore);
    }

    static void playGame() {
        board = new int[SIZE][SIZE];
        score = 0;

        addNewTile(true);
        addNewTile(true);

        while (true) {
            printBoard();

            if (isGameOver()) {
                System.out.println("\nГРА ЗАКІНЧЕНА! Немає доступних ходів для об'єднання.");
                if (score > bestScore) bestScore = score;
                break;
            }

            System.out.print("Хід (W/A/S/D або Q для виходу): ");
            char move = scanner.next().toLowerCase().charAt(0);

            if (move == 'q') {
                System.out.println("Гру перервано за вашим бажанням.");
                if (score > bestScore) bestScore = score;
                break;
            }

            boolean moved = false;
            switch (move) {
                case 'w': moved = moveUp(); break;
                case 's': moved = moveDown(); break;
                case 'a': moved = moveLeft(); break;
                case 'd': moved = moveRight(); break;
                default:
                    System.out.println("Невідома команда! Використовуйте W, A, S, D.");
                    continue;
            }

            if (moved) {
                addNewTile(false);
            } else {
                System.out.println("\nЦей хід нічого не змінює. Спробуйте інший напрямок.");
            }
        }
    }

    static void addNewTile(boolean isStart) {
        ArrayList<int[]> emptyCells = new ArrayList<>();
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (board[r][c] == 0) {
                    emptyCells.add(new int[]{r, c});
                }
            }
        }

        if (emptyCells.isEmpty()) return;

        int[] cell = emptyCells.get(random.nextInt(emptyCells.size()));
        if (isStart) {
            board[cell[0]][cell[1]] = 2;
        } else {
            board[cell[0]][cell[1]] = (random.nextInt(10) < 9) ? 2 : 4;
        }
    }

    static boolean isGameOver() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (board[r][c] == 0) return false;
                if (r < SIZE - 1 && board[r][c] == board[r + 1][c]) return false;
                if (c < SIZE - 1 && board[r][c] == board[r][c + 1]) return false;
            }
        }
        return true;
    }

    static boolean processLine(int[] line) {
        boolean changed = false;

        int insertPos = 0;
        for (int i = 0; i < SIZE; i++) {
            if (line[i] != 0) {
                if (insertPos != i) {
                    line[insertPos] = line[i];
                    line[i] = 0;
                    changed = true;
                }
                insertPos++;
            }
        }

        for (int i = 0; i < SIZE - 1; i++) {
            if (line[i] != 0 && line[i] == line[i + 1]) {
                line[i] *= 2;
                score += line[i];
                line[i + 1] = 0;
                changed = true;
            }
        }

        insertPos = 0;
        for (int i = 0; i < SIZE; i++) {
            if (line[i] != 0) {
                if (insertPos != i) {
                    line[insertPos] = line[i];
                    line[i] = 0;
                    changed = true;
                }
                insertPos++;
            }
        }
        return changed;
    }

    static boolean moveLeft() {
        boolean moved = false;
        for (int r = 0; r < SIZE; r++) {
            if (processLine(board[r])) moved = true;
        }
        return moved;
    }

    static boolean moveRight() {
        boolean moved = false;
        for (int r = 0; r < SIZE; r++) {
            int[] line = new int[SIZE];
            for (int c = 0; c < SIZE; c++) line[c] = board[r][SIZE - 1 - c];
            if (processLine(line)) moved = true;
            for (int c = 0; c < SIZE; c++) board[r][SIZE - 1 - c] = line[c];
        }
        return moved;
    }

    static boolean moveUp() {
        boolean moved = false;
        for (int c = 0; c < SIZE; c++) {
            int[] line = new int[SIZE];
            for (int r = 0; r < SIZE; r++) line[r] = board[r][c];
            if (processLine(line)) moved = true;
            for (int r = 0; r < SIZE; r++) board[r][c] = line[r];
        }
        return moved;
    }

    static boolean moveDown() {
        boolean moved = false;
        for (int c = 0; c < SIZE; c++) {
            int[] line = new int[SIZE];
            for (int r = 0; r < SIZE; r++) line[r] = board[SIZE - 1 - r][c];
            if (processLine(line)) moved = true;
            for (int r = 0; r < SIZE; r++) board[SIZE - 1 - r][c] = line[r];
        }
        return moved;
    }

    static void printBoard() {
        System.out.println("\n-----------------------------");
        System.out.println("Рахунок: " + score + "   |   Рекорд: " + bestScore);
        System.out.println("-----------------------------");
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (board[r][c] == 0) {
                    System.out.printf("%6s", ".");
                } else {
                    System.out.printf("%6d", board[r][c]);
                }
            }
            System.out.println("\n");
        }
    }
}