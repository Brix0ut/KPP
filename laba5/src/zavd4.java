import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

public class zavd4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть шлях до вихідного каталогу (звідки копіювати): ");
        String sourcePathStr = scanner.nextLine();
        Path sourceDir = Paths.get(sourcePathStr);

        System.out.print("Введіть шлях до цільового каталогу (куди копіювати): ");
        String targetPathStr = scanner.nextLine();
        Path targetDir = Paths.get(targetPathStr);

        if (!Files.exists(sourceDir) || !Files.isDirectory(sourceDir)) {
            System.out.println("Помилка: Вихідний каталог не існує або не є папкою.");
            return;
        }

        try {
            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
                System.out.println("Створено новий каталог: " + targetDir.toAbsolutePath());
            }

            System.out.println("\nПочинаємо копіювання файлів...");

            int copiedCount = 0;
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourceDir)) {
                for (Path entry : stream) {
                    if (Files.isRegularFile(entry)) {
                        Path targetFile = targetDir.resolve(entry.getFileName());

                        Files.copy(entry, targetFile, StandardCopyOption.REPLACE_EXISTING);

                        System.out.println("  -> Скопійовано: " + entry.getFileName());
                        copiedCount++;
                    }
                }
            }

            System.out.println("\nКопіювання завершено! Успішно скопійовано файлів: " + copiedCount);

        } catch (IOException e) {
            System.out.println("Сталася помилка при роботі з файловою системою: " + e.getMessage());
        }
    }
}