import java.util.ArrayList;
import java.util.List;

interface IPart {
    String getName();
}

interface IWorker {
    void work(House house);
}

class Basement implements IPart {
    public String getName() { return "Фундамент"; }
}
class Wall implements IPart {
    public String getName() { return "Стіна"; }
}
class Window implements IPart {
    public String getName() { return "Вікно"; }
}
class Door implements IPart {
    public String getName() { return "Двері"; }
}
class Roof implements IPart {
    public String getName() { return "Дах"; }
}

class House {
    private List<IPart> parts = new ArrayList<>();

    public void addPart(IPart part) {
        parts.add(part);
    }

    public List<IPart> getParts() {
        return parts;
    }

    public int countParts(Class<?> partClass) {
        int count = 0;
        for (IPart p : parts) {
            if (partClass.isInstance(p)) count++;
        }
        return count;
    }

    public boolean isFinished() {
        return countParts(Basement.class) == 1 &&
                countParts(Wall.class) == 4 &&
                countParts(Door.class) == 1 &&
                countParts(Window.class) == 4 &&
                countParts(Roof.class) == 1;
    }
}

class Worker implements IWorker {
    private String name;

    public Worker(String name) {
        this.name = name;
    }

    @Override
    public void work(House house) {
        if (house.isFinished()) {
            return;
        }

        if (house.countParts(Basement.class) == 0) {
            house.addPart(new Basement());
            System.out.println("Будівельник " + name + " залив Фундамент.");
        } else if (house.countParts(Wall.class) < 4) {
            house.addPart(new Wall());
            System.out.println("Будівельник " + name + " звів Стіну.");
        } else if (house.countParts(Door.class) == 0) {
            house.addPart(new Door());
            System.out.println("Будівельник " + name + " встановив Двері.");
        } else if (house.countParts(Window.class) < 4) {
            house.addPart(new Window());
            System.out.println("Будівельник " + name + " встановив Вікно.");
        } else if (house.countParts(Roof.class) == 0) {
            house.addPart(new Roof());
            System.out.println("Будівельник " + name + " побудував Дах.");
        }
    }
}

class TeamLeader implements IWorker {
    private String name;

    public TeamLeader(String name) {
        this.name = name;
    }

    @Override
    public void work(House house) {
        System.out.println("\n--- ЗВІТ БРИГАДИРА (" + name + ") ---");
        List<IPart> built = house.getParts();
        if (built.isEmpty()) {
            System.out.println("Будівництво ще не розпочато.");
        } else {
            System.out.println("Виконано етапів: " + built.size() + " з 11.");
            System.out.println("- Фундамент: " + house.countParts(Basement.class) + "/1");
            System.out.println("- Стіни: " + house.countParts(Wall.class) + "/4");
            System.out.println("- Двері: " + house.countParts(Door.class) + "/1");
            System.out.println("- Вікна: " + house.countParts(Window.class) + "/4");
            System.out.println("- Дах: " + house.countParts(Roof.class) + "/1");
        }
        System.out.println("----------------------------\n");
    }
}

class Team {
    private List<IWorker> workers = new ArrayList<>();

    public void addWorker(IWorker worker) {
        workers.add(worker);
    }

    public void startBuilding(House house) {
        System.out.println("Бригада починає роботу...");

        int workerIndex = 0;
        while (!house.isFinished()) {
            IWorker currentWorker = workers.get(workerIndex % workers.size());
            currentWorker.work(house);
            workerIndex++;
        }

        System.out.println("\nУРА! БУДІВНИЦТВО БУДИНКУ ЗАВЕРШЕНО!");
    }
}

public class zavd2 {
    public static void main(String[] args) {
        House myHouse = new House();
        Team myTeam = new Team();

        myTeam.addWorker(new Worker("Петро"));
        myTeam.addWorker(new Worker("Іван"));

        myTeam.addWorker(new TeamLeader("Степанич"));

        myTeam.addWorker(new Worker("Микола"));

        myTeam.startBuilding(myHouse);
    }
}