import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class zavd1 {
    private static Map<String, String> authData = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        authData.put("admin", "12345");
        authData.put("student", "2026");

        boolean running = true;
        while (running) {
            System.out.println("\n=== СИСТЕМА АВТОРИЗАЦІЇ ===");
            System.out.println("1. Додати нового користувача");
            System.out.println("2. Видалити існуючого користувача");
            System.out.println("3. Перевірити чи існує користувач");
            System.out.println("4. Змінити логін існуючого користувача");
            System.out.println("5. Змінити пароль користувача");
            System.out.println("6. Показати всіх користувачів");
            System.out.println("0. Вихід");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": addUser(); break;
                case "2": deleteUser(); break;
                case "3": checkUser(); break;
                case "4": changeLogin(); break;
                case "5": changePassword(); break;
                case "6": printAll(); break;
                case "0":
                    running = false;
                    System.out.println("Роботу завершено.");
                    break;
                default: System.out.println("Невірна команда!");
            }
        }
    }

    private static void addUser() {
        System.out.print("Введіть новий логін: ");
        String login = scanner.nextLine();

        boolean exists = authData.keySet().stream().anyMatch(k -> k.equals(login));
        if (exists) {
            System.out.println("Користувач з таким логіном вже існує!");
            return;
        }

        System.out.print("Введіть пароль: ");
        String password = scanner.nextLine();
        authData.put(login, password);
        System.out.println("Користувача додано.");
    }

    private static void deleteUser() {
        System.out.print("Введіть логін для видалення: ");
        String login = scanner.nextLine();

        if (authData.remove(login) != null) {
            System.out.println("Користувача видалено.");
        } else {
            System.out.println("Користувача не знайдено.");
        }
    }

    private static void checkUser() {
        System.out.print("Введіть логін для перевірки: ");
        String login = scanner.nextLine();

        boolean exists = authData.keySet().stream().anyMatch(k -> k.equalsIgnoreCase(login));
        if (exists) {
            System.out.println("Користувач '" + login + "' зареєстрований у системі.");
        } else {
            System.out.println("Користувача не знайдено.");
        }
    }

    private static void changeLogin() {
        System.out.print("Введіть поточний логін: ");
        String oldLogin = scanner.nextLine();

        if (!authData.containsKey(oldLogin)) {
            System.out.println("Користувача не знайдено.");
            return;
        }

        System.out.print("Введіть новий логін: ");
        String newLogin = scanner.nextLine();

        if (authData.containsKey(newLogin)) {
            System.out.println("Цей логін вже зайнятий!");
            return;
        }

        String password = authData.remove(oldLogin);
        authData.put(newLogin, password);
        System.out.println("Логін успішно змінено.");
    }

    private static void changePassword() {
        System.out.print("Введіть логін: ");
        String login = scanner.nextLine();

        if (authData.containsKey(login)) {
            System.out.print("Введіть новий пароль: ");
            String newPassword = scanner.nextLine();

            authData.put(login, newPassword);
            System.out.println("Пароль успішно змінено.");
        } else {
            System.out.println("Користувача не знайдено.");
        }
    }

    private static void printAll() {
        System.out.println("\n--- СПИСОК КОРИСТУВАЧІВ ---");
        if (authData.isEmpty()) {
            System.out.println("Список порожній.");
        } else {
            authData.entrySet().stream()
                    .forEach(entry -> System.out.println("Логін: " + entry.getKey() + " | Пароль: " + entry.getValue()));
        }
        System.out.println("---------------------------");
    }
}