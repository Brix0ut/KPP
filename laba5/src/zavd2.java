import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class zavd2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String forbiddenWordsFileName = "forbidden_words.txt";

        List<String> forbiddenWords = new ArrayList<>();
        try {
            forbiddenWords = Files.readAllLines(Paths.get(forbiddenWordsFileName));
            if (forbiddenWords.isEmpty()) {
                System.out.println("Файл із забороненими словами порожній. Перевіряти нічого.");
                return;
            }
        } catch (IOException e) {
            System.out.println("Помилка: Не вдалося знайти файл '" + forbiddenWordsFileName + "'.");
            System.out.println("Будь ласка, створіть цей файл у корені вашого проєкту та додайте туди слова.");
            return;
        }

        System.out.print("Введіть шлях до каталогу з текстовими файлами (наприклад, C:\\my_texts): ");
        String dirPathStr = scanner.nextLine();
        Path dirPath = Paths.get(dirPathStr);

        if (!Files.isDirectory(dirPath)) {
            System.out.println("Вказаний шлях не існує або не є каталогом.");
            return;
        }

        System.out.println("\nПочинаємо сканування каталогу: " + dirPath.toAbsolutePath());

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, "*.txt")) {
            boolean txtFilesFound = false;

            for (Path file : stream) {
                txtFilesFound = true;
                processFile(file, forbiddenWords, scanner);
            }

            if (!txtFilesFound) {
                System.out.println("У вказаному каталозі не знайдено жодного .txt файлу.");
            } else {
                System.out.println("\nПеревірку всіх файлів завершено.");
            }

        } catch (IOException e) {
            System.out.println("Помилка при читанні каталогу: " + e.getMessage());
        }
    }

    private static void processFile(Path file, List<String> forbiddenWords, Scanner scanner) {
        try {
            String content = Files.readString(file);
            String modifiedContent = content;

            Map<String, Integer> foundWordsStats = new HashMap<>();
            boolean hasForbidden = false;

            for (String word : forbiddenWords) {
                if (word == null || word.trim().isEmpty()) continue;
                word = word.trim();

                String regex = "(?iU)\\b" + Pattern.quote(word) + "\\b";
                Matcher matcher = Pattern.compile(regex).matcher(content);

                int count = 0;
                while (matcher.find()) {
                    count++;
                }

                if (count > 0) {
                    foundWordsStats.put(word, count);
                    hasForbidden = true;

                    String stars = "*".repeat(word.length());
                    modifiedContent = modifiedContent.replaceAll(regex, stars);
                }
            }

            if (hasForbidden) {
                System.out.println("\n=================================================");
                System.out.println("УВАГА! Знайдено заборонені слова у файлі: " + file.getFileName());
                System.out.println("Детальна статистика:");

                for (Map.Entry<String, Integer> entry : foundWordsStats.entrySet()) {
                    System.out.println("  - '" + entry.getKey() + "' : " + entry.getValue() + " раз(ів)");
                }

                System.out.print("Бажаєте виправити цей файл (замінити ці слова на '*')? (y/n): ");
                String answer = scanner.nextLine();

                if (answer.trim().equalsIgnoreCase("y") || answer.trim().equalsIgnoreCase("н")) {

                    Files.writeString(file, modifiedContent);
                    System.out.println("Файл " + file.getFileName() + " успішно виправлено.");
                } else {
                    System.out.println("Файл " + file.getFileName() + " залишено без змін.");
                }
            }

        } catch (IOException e) {
            System.out.println("Помилка при обробці файлу " + file.getFileName() + ": " + e.getMessage());
        }
    }
}