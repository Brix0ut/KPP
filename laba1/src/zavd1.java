import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class zavd1 {
    public static void main(String[] args) {
        // Використовуємо try-with-resources для безпечного читання файлу
        try (Scanner scanner = new Scanner(new FileReader("data.txt"))) {

            // Зчитування 4 параметрів з файлу
            int capacity = scanner.nextInt(); // Ємність бака
            int distAB = scanner.nextInt();   // Відстань А -> В
            int distBC = scanner.nextInt();   // Відстань В -> С
            int weight = scanner.nextInt();   // Вага вантажу

            int consumption = 0; // Споживання палива (літрів на 1 км)

            // Визначення споживання палива залежно від ваги
            if (weight <= 500) {
                consumption = 1;
            } else if (weight <= 1000) {
                consumption = 4;
            } else if (weight <= 1500) {
                consumption = 7;
            } else if (weight <= 2000) {
                consumption = 9;
            } else {
                System.out.println("Політ неможливий: літак не піднімає вантаж більше 2000 кг.");
                return; // Завершення програми
            }

            // Розрахунок необхідного палива для кожного відрізка
            int fuelAB = distAB * consumption;
            int fuelBC = distBC * consumption;

            // Перевірка можливості перельоту А -> В
            if (fuelAB > capacity) {
                System.out.println("Політ неможливий: не вистачає ємності бака для перельоту з пункту А в пункт В.");
                return;
            }

            // Перевірка можливості перельоту В -> С (навіть з повним баком)
            if (fuelBC > capacity) {
                System.out.println("Політ неможливий: не вистачає ємності бака для перельоту з пункту В в пункт С.");
                return;
            }

            // Розрахунок залишку палива по прибуттю в пункт В
            int fuelRemainingAtB = capacity - fuelAB;

            // Розрахунок кількості палива для дозаправки
            int fuelToRefuel = fuelBC - fuelRemainingAtB;

            // Якщо палива вистачає і без дозаправки, то заправляти не потрібно
            if (fuelToRefuel < 0) {
                fuelToRefuel = 0;
            }

            System.out.println("Мінімальна кількість палива для дозаправки в пункті В: " + fuelToRefuel + " літрів.");

        } catch (IOException ex) {
            System.out.println("Помилка читання файлу: " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("Помилка даних. Переконайтеся, що файл data.txt містить 4 цілих числа.");
        }
    }
}