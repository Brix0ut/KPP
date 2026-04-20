import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CargoPlane {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new FileReader("data.txt"))) {

            int capacity = scanner.nextInt();
            int distAB = scanner.nextInt();
            int distBC = scanner.nextInt();
            int weight = scanner.nextInt();

            int consumption = 0;


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
                return;
            }

            int fuelAB = distAB * consumption;
            int fuelBC = distBC * consumption;

            if (fuelAB > capacity) {
                System.out.println("Політ неможливий: не вистачає ємності бака для перельоту з пункту А в пункт В.");
                return;
            }

            if (fuelBC > capacity) {
                System.out.println("Політ неможливий: не вистачає ємності бака для перельоту з пункту В в пункт С.");
                return;
            }

            int fuelRemainingAtB = capacity - fuelAB;

            int fuelToRefuel = fuelBC - fuelRemainingAtB;

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