package auto;

public enum Car {

    BMW(30),
    LADA(10),
    KIA(20),
    MERCEDES(5),
    TOYOTA(15),
    VOLKSWAGEN(25),
    ;

    private final int timeForRepairs;

    Car(int timeForRepairs) {
        this.timeForRepairs = timeForRepairs;
    }

    public int getTimeForRepairs() {
        return this.timeForRepairs;
    }
}


