import java.util.*;
import java.util.stream.Collectors;

abstract class Building {
    protected String address;

    public Building(String address) {
        this.address = address;
    }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public abstract void setFieldFromString(String data);

    public abstract void printInfo();
}

class ResidentialBuilding extends Building {
    private int numberOfResidents;

    public ResidentialBuilding(String address, int numberOfResidents) {
        super(address);
        this.numberOfResidents = numberOfResidents;
    }

    @Override
    public void setFieldFromString(String data) {
        try {
            this.numberOfResidents = Integer.parseInt(data.trim());
            System.out.println("Кількість мешканців оновлено: " + this.numberOfResidents);
        } catch (NumberFormatException e) {
            System.out.println("Помилка: Очікувалось число (кількість мешканців).");
        }
    }

    @Override
    public void printInfo() {
        System.out.println("[Житловий Будинок] Адреса: " + address + " | Мешканців: " + numberOfResidents);
    }
}

class Shop extends Building {
    private String shopType;
    private List<String> departments;

    public Shop(String address, String shopType, List<String> departments) {
        super(address);
        this.shopType = shopType;
        this.departments = new ArrayList<>(departments);
    }

    public List<String> getDepartments() { return departments; }

    public String getShopType() {
        return shopType;
    }

    @Override
    public void setFieldFromString(String data) {
        if (data != null && !data.trim().isEmpty()) {
            this.departments.add(data.trim());
            System.out.println("Відділ '" + data.trim() + "' успішно додано до магазину.");
        }
    }

    @Override
    public void printInfo() {
        System.out.println("🛒 [Магазин: " + shopType + "] Адреса: " + address + " | Відділи: " + String.join(", ", departments));
    }
}

enum AccreditationLevel { COMPREHENSIVE, GYMNASIUM, LYCEUM }

class School extends Building {
    private int numberOfStudents;
    private AccreditationLevel level;

    public School(String address, AccreditationLevel level) {
        super(address);
        this.level = level;
        generateStudentsBasedOnLevel();
    }

    private void generateStudentsBasedOnLevel() {
        Random rand = new Random();
        switch (level) {
            case COMPREHENSIVE: this.numberOfStudents = 300 + rand.nextInt(200); break; // 300-500
            case GYMNASIUM: this.numberOfStudents = 500 + rand.nextInt(300); break;     // 500-800
            case LYCEUM: this.numberOfStudents = 800 + rand.nextInt(400); break;        // 800-1200
        }
    }

    @Override
    public void setFieldFromString(String data) {
        try {
            this.level = AccreditationLevel.valueOf(data.trim().toUpperCase());
            generateStudentsBasedOnLevel(); // Перераховуємо учнів для нового рівня
            System.out.println("Рівень акредитації змінено на " + this.level);
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: Невідомий рівень акредитації. Доступні: COMPREHENSIVE, GYMNASIUM, LYCEUM.");
        }
    }

    @Override
    public void printInfo() {
        System.out.println("[Школа: " + level + "] Адреса: " + address + " | Кількість учнів: " + numberOfStudents);
    }
}

class Street {
    private String name;
    private List<Building> buildings;

    public Street(String name) {
        this.name = name;
        this.buildings = new ArrayList<>();
    }

    public void addBuilding(Building b) {
        buildings.add(b);
    }

    public boolean removeBuilding(String address) {
        return buildings.removeIf(b -> b.getAddress().equalsIgnoreCase(address));
    }

    public void printStreetInfo() {
        System.out.println("\n=== ВУЛИЦЯ: " + name.toUpperCase() + " (" + buildings.size() + " будівель) ===");
        if (buildings.isEmpty()) {
            System.out.println("Вулиця поки що порожня.");
            return;
        }
        for (int i = 0; i < buildings.size(); i++) {
            System.out.print((i + 1) + ". ");
            buildings.get(i).printInfo();
        }
    }

    public Building getBuilding(String address) {
        return buildings.stream()
                .filter(b -> b.getAddress().equalsIgnoreCase(address))
                .findFirst()
                .orElse(null);
    }

    public void findShopsInNeighborhood(String houseAddress, int radius, String targetDepartment) {
        int targetIndex = -1;

        for (int i = 0; i < buildings.size(); i++) {
            if (buildings.get(i).getAddress().equalsIgnoreCase(houseAddress)) {
                if (!(buildings.get(i) instanceof ResidentialBuilding)) {
                    System.out.println("Будівля за адресою '" + houseAddress + "' не є житловим будинком!");
                    return;
                }
                targetIndex = i;
                break;
            }
        }

        if (targetIndex == -1) {
            System.out.println("Будинок з такою адресою не знайдено на вулиці.");
            return;
        }

        System.out.println("\nШукаємо відділ '" + targetDepartment + "' в радіусі " + radius + " будівель від '" + houseAddress + "'...");

        int startIndex = Math.max(0, targetIndex - radius);
        int endIndex = Math.min(buildings.size() - 1, targetIndex + radius);

        List<Shop> foundShops = new ArrayList<>();

        for (int i = startIndex; i <= endIndex; i++) {
            Building b = buildings.get(i);
            if (b instanceof Shop) {
                Shop shop = (Shop) b;
                // Шукаємо відділ ігноруючи регістр
                boolean hasDepartment = shop.getDepartments().stream()
                        .anyMatch(dep -> dep.equalsIgnoreCase(targetDepartment));
                if (hasDepartment) {
                    foundShops.add(shop);
                }
            }
        }

        if (foundShops.isEmpty()) {
            System.out.println("   Нічого не знайдено в цій околиці.");
        } else {
            System.out.println("Знайдено магазини:");
            foundShops.forEach(s -> System.out.println("   - " + s.getAddress() + " (Тип: " + s.getShopType() + ")"));
        }
    }
}

class StreetFactory {
    public static Street createTestStreet() {
        Street street = new Street("Хрещатик");

        street.addBuilding(new ResidentialBuilding("10А", 120));
        street.addBuilding(new Shop("12", "Супермаркет", Arrays.asList("Продукти", "Хімія", "Одяг", "Хлібний", "М'ясний")));
        street.addBuilding(new ResidentialBuilding("14", 450));
        street.addBuilding(new School("16", AccreditationLevel.LYCEUM));
        street.addBuilding(new Shop("18", "Кіоск", Arrays.asList("Напої", "Преса")));
        street.addBuilding(new ResidentialBuilding("20", 50));
        street.addBuilding(new Shop("22Б", "Міні-маркет", Arrays.asList("Продукти", "Хімія")));

        return street;
    }
}

public class zavd5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Street myStreet = StreetFactory.createTestStreet();

        boolean running = true;
        while (running) {
            System.out.println("\n--- КЕРУВАННЯ ВУЛИЦЕЮ ---");
            System.out.println("1. Вивести інформацію про вулицю");
            System.out.println("2. Додати новий житловий будинок");
            System.out.println("3. Видалити будівлю за адресою");
            System.out.println("4. Змінити параметр будівлі (виклик віртуального методу)");
            System.out.println("5. Знайти магазин в околиці житлового будинку");
            System.out.println("0. Вихід");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    myStreet.printStreetInfo();
                    break;
                case "2":
                    System.out.print("Введіть адресу нового будинку: ");
                    String addr = scanner.nextLine();
                    System.out.print("Введіть кількість мешканців: ");
                    try {
                        int res = Integer.parseInt(scanner.nextLine());
                        myStreet.addBuilding(new ResidentialBuilding(addr, res));
                        System.out.println("Будинок додано!");
                    } catch (NumberFormatException e) {
                        System.out.println("Помилка введення кількості.");
                    }
                    break;
                case "3":
                    System.out.print("Введіть адресу для видалення: ");
                    String delAddr = scanner.nextLine();
                    if (myStreet.removeBuilding(delAddr)) {
                        System.out.println("Будівлю видалено.");
                    } else {
                        System.out.println("Будівлю не знайдено.");
                    }
                    break;
                case "4":
                    System.out.print("Введіть адресу будівлі: ");
                    String targetAddr = scanner.nextLine();
                    Building b = myStreet.getBuilding(targetAddr);
                    if (b != null) {
                        System.out.println("Знайдено: ");
                        b.printInfo();
                        System.out.print("Введіть нове значення (Для дому-мешканці, Для магазину-новий відділ, Для школи-рівень): ");
                        String data = scanner.nextLine();
                        b.setFieldFromString(data); // Поліморфний виклик!
                    } else {
                        System.out.println("Будівлю не знайдено.");
                    }
                    break;
                case "5":
                    System.out.print("Введіть адресу житлового будинку (наприклад, 14): ");
                    String hAddr = scanner.nextLine();
                    System.out.print("Введіть радіус пошуку (кількість будівель вліво/вправо, наприклад, 2): ");
                    try {
                        int radius = Integer.parseInt(scanner.nextLine());
                        System.out.print("Введіть назву відділу (наприклад, Продукти): ");
                        String dep = scanner.nextLine();
                        myStreet.findShopsInNeighborhood(hAddr, radius, dep);
                    } catch (NumberFormatException e) {
                        System.out.println("Помилка: Радіус має бути числом.");
                    }
                    break;
                case "0":
                    running = false;
                    System.out.println("Роботу завершено.");
                    break;
                default:
                    System.out.println("Невірна команда!");
            }
        }
    }
}