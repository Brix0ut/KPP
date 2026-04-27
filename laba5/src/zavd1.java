import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class zavd1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть шлях до першого файлу (наприклад, file1.txt): ");
        String path1 = scanner.nextLine();

        System.out.print("Введіть шлях до другого файлу (наприклад, file2.txt): ");
        String path2 = scanner.nextLine();

        try (BufferedReader br1 = new BufferedReader(new FileReader(path1));
             BufferedReader br2 = new BufferedReader(new FileReader(path2))) {

            String line1, line2;
            int lineNumber = 1;
            boolean areIdentical = true;

            System.out.println("\n=== Результат порівняння ===");

            while (true) {
                line1 = br1.readLine();
                line2 = br2.readLine();

                if (line1 == null && line2 == null) {
                    break;
                }

                if (line1 == null || line2 == null || !line1.equals(line2)) {
                    areIdentical = false;
                    System.out.println("Відмінність у рядку " + lineNumber + ":");
                    System.out.println("Файл 1: " + (line1 == null ? "<Кінець файлу>" : line1));
                    System.out.println("Файл 2: " + (line2 == null ? "<Кінець файлу>" : line2));
                    System.out.println("---------------------------------");
                }
                lineNumber++;
            }

            if (areIdentical) {
                System.out.println("Файли повністю збігаються!");
            }

        } catch (IOException e) {
            System.out.println("Помилка при роботі з файлами. Перевірте, чи існують ці файли.");
            System.out.println("Деталі помилки: " + e.getMessage());
        }
    }
}