import Car.Car;

import java.util.*;

public class Garage {
    private int numberOfParkingPace;
    private Queue<Car> carQueue = new LinkedList<Car>();
    private int availableEmployees;
    private List<Worker> workerList = new ArrayList<>();

    public Garage(int numberOfParkingPace, int numberOfMaster) {
        this.numberOfParkingPace = numberOfParkingPace;
        for (int i=1; i<=numberOfMaster; i++){
           workerList.add(new Worker(i));
           workerList.get(i-1).start();
        }
    }

    public synchronized void putCar(Car car){
        while (carQueue.size() > numberOfParkingPace){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Машина " + car + " доставлена в гараж");
        carQueue.offer(car);
        notify();
    }

    public synchronized void getCar(){
        availableEmployees = 0;
        //находим количество свободных сотрудников
        workerList.forEach(worker -> {
            if (worker.isCanWork()){
                availableEmployees++;
            }
        });
        //если машин нет или все сотрудники заняты
        while (carQueue.size() < 1 || availableEmployees == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //проходимся по всем сотрудникам и находим не занятых
        for (Worker worker : workerList){
            if (worker.isCanWork()){
                worker.setCar(carQueue.poll());
                break;
            }
        }
        notify();
    }

}
