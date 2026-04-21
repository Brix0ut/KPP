import java.util.*;

abstract class Animal {
    protected String name;
    protected String species;
    protected String foodType;
    protected double dailyFoodKg;
    protected boolean canReproduceInCaptivity;
    protected boolean isSocial;

    public Animal(String name, String species, String foodType, double dailyFoodKg,
                  boolean canReproduceInCaptivity, boolean isSocial) {
        this.name = name;
        this.species = species;
        this.foodType = foodType;
        this.dailyFoodKg = dailyFoodKg;
        this.canReproduceInCaptivity = canReproduceInCaptivity;
        this.isSocial = isSocial;
    }

    public String getSpecies() { return species; }
    public double getDailyFoodKg() { return dailyFoodKg; }
    public String getFoodType() { return foodType; }

    @Override
    public String toString() {
        return String.format("[%s] %s | Корм: %s (%.1f кг/день) | Розмноження: %s | Соціальність: %s",
                species, name, foodType, dailyFoodKg,
                (canReproduceInCaptivity ? "Можливе" : "Ускладнене"),
                (isSocial ? "Групове утримання" : "Одинак"));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Animal animal = (Animal) obj;
        return Double.compare(animal.dailyFoodKg, dailyFoodKg) == 0 &&
                canReproduceInCaptivity == animal.canReproduceInCaptivity &&
                isSocial == animal.isSocial &&
                Objects.equals(name, animal.name) &&
                Objects.equals(species, animal.species) &&
                Objects.equals(foodType, animal.foodType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, species, foodType, dailyFoodKg, canReproduceInCaptivity, isSocial);
    }
}


class Tiger extends Animal {
    private String subspecies;

    public Tiger(String name, String subspecies, double dailyFoodKg) {
        super(name, "Тигр", "Свіже м'ясо", dailyFoodKg, false, false);
        this.subspecies = subspecies;
    }

    @Override
    public String toString() {
        return super.toString() + " | Підвид: " + subspecies;
    }
}

class Crocodile extends Animal {
    private double lengthMeters;

    public Crocodile(String name, double lengthMeters) {
        super(name, "Крокодил", "М'ясо/Риба", lengthMeters * 2.5, true, true);
        this.lengthMeters = lengthMeters;
    }

    @Override
    public String toString() {
        return super.toString() + " | Довжина: " + lengthMeters + " м";
    }
}

class Kangaroo extends Animal {
    private double maxJumpHeight;

    public Kangaroo(String name, double maxJumpHeight) {
        super(name, "Кенгуру", "Свіжа рослинність", 4.5, true, true);
        this.maxJumpHeight = maxJumpHeight;
    }

    @Override
    public String toString() {
        return super.toString() + " | Макс. стрибок: " + maxJumpHeight + " м";
    }
}


public class zavd4 {
    public static void main(String[] args) {
        List<Animal> zoo = new ArrayList<>();

        zoo.add(new Tiger("Шерхан", "Бенгальський", 8.0));
        zoo.add(new Tiger("Амур", "Амурський", 10.0));
        zoo.add(new Crocodile("Гена", 3.2));
        zoo.add(new Crocodile("Зубастик", 4.5));
        zoo.add(new Crocodile("Кусюка", 2.1));
        zoo.add(new Kangaroo("Джек", 2.8));
        zoo.add(new Kangaroo("Скіппі", 3.1));

        System.out.println("=== РЕЄСТР ТВАРИН ЗООПАРКУ ===");
        for (Animal animal : zoo) {
            System.out.println(animal.toString());
        }

        System.out.println("\n=== ПОПУЛЯЦІЯ ЗА ВИДАМИ ===");
        Map<String, Integer> speciesCount = new HashMap<>();
        for (Animal animal : zoo) {
            speciesCount.put(animal.getSpecies(), speciesCount.getOrDefault(animal.getSpecies(), 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : speciesCount.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " особин");
        }

        System.out.println("\n=== МІСЯЧНА ЗАКУПІВЛЯ КОРМУ (30 ДНІВ) ===");
        Map<String, Double> monthlyFoodRequirement = new HashMap<>();

        for (Animal animal : zoo) {
            double monthlyAmount = animal.getDailyFoodKg() * 30;
            String food = animal.getFoodType();
            monthlyFoodRequirement.put(food, monthlyFoodRequirement.getOrDefault(food, 0.0) + monthlyAmount);
        }

        double totalKg = 0;
        for (Map.Entry<String, Double> entry : monthlyFoodRequirement.entrySet()) {
            System.out.printf("Категорія '%s': %.2f кг\n", entry.getKey(), entry.getValue());
            totalKg += entry.getValue();
        }
        System.out.println("-----------------------------------");
        System.out.printf("ЗАГАЛЬНА ВАГА КОРМУ НА МІСЯЦЬ: %.2f кг\n", totalKg);

        System.out.println("\n=== ПЕРЕВІРКА МЕТОДІВ ===");
        Animal croc1 = new Crocodile("Клон", 2.0);
        Animal croc2 = new Crocodile("Клон", 2.0);
        System.out.println("Чи ідентичні два однакові крокодили (equals)? " + croc1.equals(croc2));
    }
}