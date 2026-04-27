import java.io.*;
import java.util.*;

class Employee implements Serializable {
    private static final long serialVersionUID = 1L;

    private String lastName;
    private String firstName;
    private int age;
    private String position;

    public Employee(String lastName, String firstName, int age, String position) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
        this.position = position;
    }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    @Override
    public String toString() {
        return String.format("Прізвище: %-15s | Ім'я: %-10s | Вік: %-3d | Посада: %s", lastName, firstName, age, position);
    }
}

public class zavd3 {
    private static List<Employee> employees = new ArrayList<>();
    private static String dbFileName;
    private static boolean isModified = false;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== ІНФОРМАЦІЙНА СИСТЕМА 'КОРПОРАЦІЯ' ===");
        System.out.print("Введіть ім'я файлу бази даних для завантаження (напр., db.dat): ");
        dbFileName = scanner.nextLine();

        loadDatabase();

        boolean running = true;
        while (running) {
            System.out.println("\n--- ГОЛОВНЕ МЕНЮ ---");
            System.out.println("1. Додати співробітника");
            System.out.println("2. Редагувати співробітника");
            System.out.println("3. Видалити співробітника");
            System.out.println("4. Пошук за прізвищем");
            System.out.println("5. Вивід усіх співробітників");
            System.out.println("6. Вивід співробітників за віком (сортування)");
            System.out.println("7. Вивід співробітників за першою літерою прізвища");
            System.out.println("8. Зберегти базу даних (примусово)");
            System.out.println("0. Вихід");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1": addEmployee(); break;
                case "2": editEmployee(); break;
                case "3": deleteEmployee(); break;
                case "4": searchByLastName(); break;
                case "5": displayEmployees(employees, "Список усіх співробітників"); break;
                case "6": displayByAge(); break;
                case "7": displayByLetter(); break;
                case "8": saveDatabase(); break;
                case "0":
                    running = false;
                    if (isModified) {
                        System.out.println("Виявлено незбережені зміни. Автоматичне збереження...");
                        saveDatabase();
                    }
                    System.out.println("Роботу завершено. Гарного дня!");
                    break;
                default: System.out.println("Невірна команда. Спробуйте ще раз.");
            }
        }
    }

    private static void addEmployee() {
        System.out.print("Введіть прізвище: ");
        String lastName = scanner.nextLine();
        System.out.print("Введіть ім'я: ");
        String firstName = scanner.nextLine();
        System.out.print("Введіть вік: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Введіть посаду: ");
        String position = scanner.nextLine();

        employees.add(new Employee(lastName, firstName, age, position));
        isModified = true;
        System.out.println("Співробітника успішно додано!");
    }

    private static void editEmployee() {
        System.out.print("Введіть прізвище співробітника для редагування: ");
        String lastName = scanner.nextLine();

        for (Employee emp : employees) {
            if (emp.getLastName().equalsIgnoreCase(lastName)) {
                System.out.println("Знайдено: " + emp);
                System.out.print("Нове прізвище (або Enter, щоб залишити поточне): ");
                String newLn = scanner.nextLine();
                if (!newLn.isEmpty()) emp.setLastName(newLn);

                System.out.print("Нове ім'я (або Enter, щоб залишити поточне): ");
                String newFn = scanner.nextLine();
                if (!newFn.isEmpty()) emp.setFirstName(newFn);

                System.out.print("Новий вік (або 0, щоб залишити поточний): ");
                int newAge = Integer.parseInt(scanner.nextLine());
                if (newAge > 0) emp.setAge(newAge);

                System.out.print("Нова посада (або Enter, щоб залишити поточну): ");
                String newPos = scanner.nextLine();
                if (!newPos.isEmpty()) emp.setPosition(newPos);

                isModified = true;
                System.out.println("Дані оновлено!");
                return;
            }
        }
        System.out.println("Співробітника з таким прізвищем не знайдено.");
    }

    private static void deleteEmployee() {
        System.out.print("Введіть прізвище співробітника для видалення: ");
        String lastName = scanner.nextLine();

        boolean removed = employees.removeIf(emp -> emp.getLastName().equalsIgnoreCase(lastName));
        if (removed) {
            isModified = true;
            System.out.println("Співробітника видалено.");
        } else {
            System.out.println("Співробітника не знайдено.");
        }
    }

    private static void searchByLastName() {
        System.out.print("Введіть прізвище для пошуку: ");
        String lastName = scanner.nextLine();

        List<Employee> result = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getLastName().equalsIgnoreCase(lastName)) {
                result.add(emp);
            }
        }
        displayEmployees(result, "Результати пошуку за прізвищем '" + lastName + "'");
    }

    private static void displayByAge() {
        List<Employee> sortedList = new ArrayList<>(employees);
        sortedList.sort(Comparator.comparingInt(Employee::getAge));
        displayEmployees(sortedList, "Співробітники, відсортовані за віком");
    }

    private static void displayByLetter() {
        System.out.print("Введіть першу літеру прізвища: ");
        String letter = scanner.nextLine().substring(0, 1).toLowerCase();

        List<Employee> result = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getLastName().toLowerCase().startsWith(letter)) {
                result.add(emp);
            }
        }
        displayEmployees(result, "Співробітники на літеру '" + letter.toUpperCase() + "'");
    }

    private static void displayEmployees(List<Employee> list, String title) {
        System.out.println("\n--- " + title.toUpperCase() + " ---");
        if (list.isEmpty()) {
            System.out.println("Список порожній.");
            return;
        }
        for (Employee emp : list) {
            System.out.println(emp);
        }
        System.out.println("-----------------------------------");

        System.out.print("Зберегти цей список у файл-звіт? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.print("Введіть назву файлу для звіту (напр. report.txt): ");
            String reportName = scanner.nextLine();
            saveReport(list, reportName, title);
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadDatabase() {
        File file = new File(dbFileName);
        if (!file.exists()) {
            System.out.println("⚠Файл бази даних не знайдено. Створено нову порожню базу.");
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            employees = (List<Employee>) ois.readObject();
            System.out.println("Базу даних успішно завантажено! Записів: " + employees.size());
        } catch (Exception e) {
            System.out.println("Помилка завантаження бази: " + e.getMessage());
        }
    }

    private static void saveDatabase() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dbFileName))) {
            oos.writeObject(employees);
            isModified = false;
            System.out.println("Базу даних збережено у файл " + dbFileName);
        } catch (IOException e) {
            System.out.println("Помилка збереження бази: " + e.getMessage());
        }
    }

    private static void saveReport(List<Employee> list, String filename, String title) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("=== ЗВІТ: " + title.toUpperCase() + " ===");
            writer.println("Дата генерації: " + new java.util.Date());
            writer.println("---------------------------------------------------------");
            for (Employee emp : list) {
                writer.println(emp.toString());
            }
            writer.println("---------------------------------------------------------");
            writer.println("Всього записів: " + list.size());
            System.out.println("Звіт успішно збережено у файл: " + filename);
        } catch (IOException e) {
            System.out.println("Помилка при збереженні звіту: " + e.getMessage());
        }
    }
}