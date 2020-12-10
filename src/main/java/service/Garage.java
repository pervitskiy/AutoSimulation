package service;

import auto.Car;
import auto.TowTrack;
import utils.workDB;

import java.sql.Timestamp;
import java.util.*;

public class Garage {

    private int numberOfParkingPace;

    public synchronized Queue<Car> getCarQueue() {
        return carQueue;
    }

    public synchronized void setCarQueue(Queue<Car> carQueue) {
        this.carQueue = carQueue;
    }

    //очередь машин в гараже
    private Queue<Car> carQueue = new LinkedList<Car>();

    public synchronized Queue<Car> getCarQueueInWork() {
        return carQueueInWork;
    }

    public synchronized void setCarQueueInWork(Queue<Car> carQueueInWork) {
        this.carQueueInWork = carQueueInWork;
    }

    //очередь из машин, которые уже обслуживаются
    private Queue<Car> carQueueInWork = new LinkedList<Car>();

    private List<Worker> workerList = new ArrayList<>();

    public Garage(int numberOfParkingPace, int numberOfMaster) {
        this.numberOfParkingPace = numberOfParkingPace;
        for (int i=1; i<=numberOfMaster; i++){
           workerList.add(new Worker(i, this));
           workerList.get(i-1).start();
        }
    }

    public synchronized void putCar(Car car, TowTrack towTrack){
        while (getCarQueue().size() >= numberOfParkingPace){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        workDB.add("Car " + car + " delivered to the garage by a Tow truck " + towTrack.getIdTrack(),
                   new Timestamp(System.currentTimeMillis()));
        System.out.println("Машина " + car + " доставлена в гараж Эвакуатором " + towTrack.getIdTrack() );
        getCarQueue().offer(car);
        carQueueInWork.offer(car);
        notify();
    }

    public synchronized Queue<Car> getCar(){
        while (carQueue.size() < 1){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return getCarQueueInWork();
    }
}
