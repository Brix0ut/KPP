import java.util.*;
import java.util.stream.Collectors;

class Television {
    private String model;
    private int year;
    private double price;
    private double diagonal;
    private String manufacturer;

    public Television(String model, int year, double price, double diagonal, String manufacturer) {
        this.model = model;
        this.year = year;
        this.price = price;
        this.diagonal = diagonal;
        this.manufacturer = manufacturer;
    }

    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getPrice() { return price; }
    public double getDiagonal() { return diagonal; }
    public String getManufacturer() { return manufacturer; }

    @Override
    public String toString() {
        return String.format("ТБ: %-15s | Рік: %d | Діагональ: %.1f\" | Ціна: %7.2f грн | Виробник: %s",
                model, year, diagonal, price, manufacturer);
    }
}

public class zavd4 {
    public static void main(String[] args) {
        List<Television> tvList = Arrays.asList(
                new Television("OLED65C1", 2023, 65000, 65, "LG"),
                new Television("QE55Q80A", 2022, 42000, 55, "Samsung"),
                new Television("Bravia XR", 2023, 75000, 65, "Sony"),
                new Television("Mi TV 4A", 2021, 8000, 32, "Xiaomi"),
                new Television("Kivi 40U", 2024, 12000, 40, "Kivi"),
                new Television("Samsung Frame", 2024, 55000, 40, "Samsung"),
                new Television("TCL 40S", 2023, 9000, 40, "TCL"),
                new Television("LG NanoCell", 2024, 25000, 43, "LG"),
                new Television("Philips LED", 2021, 15000, 50, "Philips"),
                new Television("Mi TV P1", 2024, 6500, 24, "Xiaomi"),
                new Television("Sony X80J", 2022, 38000, 55, "Sony")
        );

        int currentYear = 2024;
        double targetDiagonal = 55;
        String targetManufacturer = "LG";
        double targetMinPrice = 10000;
        double expensiveThreshold = 40000;

        System.out.println("=== 1. Усі телевізори ===");
        tvList.forEach(System.out::println);

        System.out.println("\n=== 2. Телевізори з діагоналлю " + targetDiagonal + "\" ===");
        tvList.stream()
                .filter(tv -> tv.getDiagonal() == targetDiagonal)
                .forEach(System.out::println);

        System.out.println("\n=== 3. Усі телевізори виробника " + targetManufacturer + " ===");
        tvList.stream()
                .filter(tv -> tv.getManufacturer().equalsIgnoreCase(targetManufacturer))
                .forEach(System.out::println);

        System.out.println("\n=== 4. Телевізори " + currentYear + " року, діагональ <= 30\", ціна >= " + targetMinPrice + " ===");
        List<Television> filteredYearDiagPrice = tvList.stream()
                .filter(tv -> tv.getYear() == currentYear && tv.getDiagonal() <= 30 && tv.getPrice() >= targetMinPrice)
                .collect(Collectors.toList());
        if (filteredYearDiagPrice.isEmpty()) {
            System.out.println("Таких телевізорів не знайдено.");
        } else {
            filteredYearDiagPrice.forEach(System.out::println);
        }

        System.out.println("\n=== 5. Телевізори дорожчі за " + expensiveThreshold + " грн ===");
        tvList.stream()
                .filter(tv -> tv.getPrice() > expensiveThreshold)
                .forEach(System.out::println);

        System.out.println("\n=== 6. Телевізори, відсортовані за ціною (зростання) ===");
        tvList.stream()
                .sorted(Comparator.comparing(Television::getPrice))
                .forEach(System.out::println);

        System.out.println("\n=== 7. Телевізори, відсортовані по діагоналі (зменшення) ===");
        tvList.stream()
                .sorted(Comparator.comparing(Television::getDiagonal).reversed())
                .forEach(System.out::println);

        System.out.println("\n=== 8. Групи телевізорів відповідно до виробника ===");
        Map<String, List<Television>> groupedByManufacturer = tvList.stream()
                .collect(Collectors.groupingBy(Television::getManufacturer));

        groupedByManufacturer.forEach((manufacturer, list) -> {
            System.out.println("Виробник: " + manufacturer);
            list.forEach(tv -> System.out.println("   " + tv));
        });

        System.out.println("\n=== 9. 5 найдорожчих телевізорів ===");
        tvList.stream()
                .sorted(Comparator.comparing(Television::getPrice).reversed())
                .limit(5)
                .forEach(System.out::println);

        System.out.println("\n=== 10. 3 телевізори з найменшою діагоналлю ===");
        tvList.stream()
                .sorted(Comparator.comparing(Television::getDiagonal))
                .limit(3)
                .forEach(System.out::println);

        System.out.println("\n=== 11. Останній найдорожчий телевізор з діагоналлю 40 дюймів ===");
        tvList.stream()
                .filter(tv -> tv.getDiagonal() == 40)
                .max(Comparator.comparing(Television::getPrice))
                .ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Телевізорів з діагоналлю 40\" не знайдено.")
                );
    }
}