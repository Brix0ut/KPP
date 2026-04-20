import java.util.Scanner;
import java.util.regex.Pattern;

public class zavd3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Перевірка надійності пароля ===");
        System.out.println("Вимоги:");
        System.out.println("- Мінімум 8 символів");
        System.out.println("- Тільки англійські літери (a-z, A-Z), цифри (0-9) та символи ! * _");
        System.out.println("- Мінімум по одному символу з кожної групи.");
        System.out.print("\nВведіть пароль для перевірки: ");

        String password = scanner.nextLine();

        if (isValidPassword(password)) {
            System.out.println("Пароль надійний!");
        } else {
            System.out.println("Пароль ненадійний. Він не відповідає одній або кільком вимогам.");
        }
    }

    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!*_])[a-zA-Z0-9!*_]{8,}$";

        return Pattern.matches(regex, password);
    }
}