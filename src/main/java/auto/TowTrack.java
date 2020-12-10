package auto;


import service.Garage;
import utils.workDB;

import java.sql.Timestamp;

public class TowTrack extends Thread {
    private int idTrack;
    private boolean isActive;
    private Garage garage;
    private boolean carIsLoaded;
    private Car car;

    public TowTrack(int idTrack) {
        this.idTrack = idTrack;
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

    public int getIdTrack() {
        return idTrack;
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

    //наверное так не правильно)
    public void run() {
        while (isActive){
            if (isCarIsLoaded()){
                workDB.add("Tow Track " + idTrack + " took the car " + getCar(),
                           new Timestamp(System.currentTimeMillis()));
                System.out.println("Эвакуатор " + idTrack + " забрал машину " + getCar());
                workDB.add("Tow Track  " + idTrack + " drove up to the garage",
                           new Timestamp(System.currentTimeMillis()));
                System.out.println("Эвакуатор " + idTrack + " подъехал к гаражу");
                garage.putCar(getCar(), this);
                workDB.add("Tow Track  " + idTrack  + " freed",
                           new Timestamp(System.currentTimeMillis()));
                System.out.println("Эвакуатор " + idTrack + " освободился");
                setCarIsLoaded(false);
            }
        }
    }
}
