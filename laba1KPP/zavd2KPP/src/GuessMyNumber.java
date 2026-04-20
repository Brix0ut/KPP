import java.util.Random;
import java.util.Scanner;

public class GuessMyNumber {
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    static int userScore = 0;
    static int computerScore = 0;

    public static void main(String[] args) {
        System.out.println("=== Вітаємо у грі GUESS MY NUMBER! ===");

        boolean level1Success = playLevel(1, 1, 10, 5, 3, 5);

        if (level1Success) {
            System.out.println("\nВи пройшли перший рівень без програшів!");
            System.out.print("Бажаєте перейти на 2 рівень? (1 - Так, 0 - Ні): ");
            int choice = scanner.nextInt();

            if (choice == 1) {
                playLevel(2, 10, 100, 22, 2, 10);
            } else {
                System.out.println("Ви вирішили завершити гру.");
            }
        } else {
            System.out.println("\nВи програли один із раундів на першому рівні. Перехід на другий рівень неможливий. [cite: 190]");
        }

        System.out.println("\n===========================");
        System.out.println("     ФІНАЛЬНИЙ РАХУНОК     ");
        System.out.println("Гравець: " + userScore + " | Комп'ютер: " + computerScore);
        System.out.println("===========================");
    }

    static boolean playLevel(int level, int min, int max, int initialLives, int totalRounds, int multiplier) {
        boolean levelWon = true;
        System.out.println("\n>>> ПОЧАТОК РІВНЯ " + level + " <<<");

        for (int round = 1; round <= totalRounds; round++) {
            System.out.println("\n--- Раунд " + round + " з " + totalRounds + " ---");
            int targetNumber = random.nextInt(max - min + 1) + min;
            int lives = initialLives;
            boolean roundWon = false;

            while (lives > 0) {
                System.out.println("Життів: " + lives + ". Введіть число від " + min + " до " + max + ":");
                int guess = scanner.nextInt();

                if (guess == targetNumber) {
                    System.out.println("Вітаю! Ви вгадали число " + targetNumber + "!");
                    roundWon = true;
                    break;
                } else {
                    lives--;
                    System.out.println("Неправильно! Залишилось життів: " + lives);

                    if (lives > 0) {
                        System.out.print("Бажаєте підказку за 1 життя? (1 - Так, 0 - Ні): ");
                        int hintChoice = scanner.nextInt();
                        if (hintChoice == 1) {
                            lives--;
                            if (targetNumber > guess) {
                                System.out.println("Підказка: загадане число БІЛЬШЕ вашого.");
                            } else {
                                System.out.println("Підказка: загадане число МЕНШЕ вашого.");
                            }
                            System.out.println("Життів після підказки: " + lives);
                        }
                    }
                }
            }

            if (roundWon) {
                int points = lives * multiplier;
                userScore += points;
                System.out.println("Ви виграли раунд! Отримано очок: " + points);
            } else {
                System.out.println("Ви програли раунд. Загадане число було: " + targetNumber);
                int compPoints = initialLives * multiplier;
                computerScore += compPoints;
                System.out.println("Комп'ютер отримує очок: " + compPoints);
                levelWon = false;
            }

            System.out.println("Поточний рахунок -> Гравець: " + userScore + " | Комп'ютер: " + computerScore);
        }
        return levelWon;
    }
}