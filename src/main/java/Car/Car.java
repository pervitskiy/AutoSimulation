package Car;

public abstract class Car {
    private String name;
    private int timeForRepairs;

    public String getName() {
        return name;
    }

    public int getTimeForRepairs() {
        return timeForRepairs;
    }

    public Car(String name, int timeForRepairs) {
        this.name = name;
        this.timeForRepairs = timeForRepairs;
    }
}
