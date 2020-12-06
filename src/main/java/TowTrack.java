import Car.Car;

public class TowTrack extends Thread {
    private int id;
    private boolean isActive;
    private Garage garage;
    private boolean carIsLoaded;
    private Car car;

    public TowTrack(int id) {
        this.id = id;
        this.isActive = true;
        this.carIsLoaded = false;
    }

    public void setGarage(Garage garage) {
        this.garage = garage;
    }

    public synchronized Car getCar() {
        return car;
    }

    public synchronized void setCar(Car car) {
        this.car = car;
        this.carIsLoaded = true;
    }
    private void disable() {
        isActive = false;
    }

    public synchronized boolean isCarIsLoaded() {
        return carIsLoaded;
    }

    public synchronized void setCarIsLoaded(boolean carIsLoaded) {
        this.carIsLoaded = carIsLoaded;
    }

    public void run() {
        while (isActive){
            if (isCarIsLoaded()){
                System.out.println("Эвакуатор " + id + " забрал машину " + getCar());
                System.out.println("Эвакуатор " + id + " подъехал к гаражу");
                garage.putCar(getCar());
                System.out.println("Эвакуатор " + id + " освободился");
                setCarIsLoaded(false);
            }
        }
    }
}
