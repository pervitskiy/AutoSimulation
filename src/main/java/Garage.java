import Car.Car;

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

    //очередь из машин, которые уже обсдуживаются
    private Queue<Car> carQueueInWork = new LinkedList<Car>();

    private List<Worker> workerList = new ArrayList<>();

    public Garage(int numberOfParkingPace, int numberOfMaster) {
        this.numberOfParkingPace = numberOfParkingPace;
        for (int i=1; i<=numberOfMaster; i++){
           workerList.add(new Worker(i, this));
           workerList.get(i-1).start();
        }
    }

    public synchronized void putCar(Car car){
        while (getCarQueue().size() >= numberOfParkingPace){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Машина " + car + " доставлена в гараж");
        getCarQueue().offer(car);
        carQueueInWork.offer(car);
        notify();
    }

    public synchronized Queue<Car> getCar(){
        while (carQueue.size() < 1){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notify();
        return getCarQueueInWork();
    }
}
