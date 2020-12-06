import Car.Car;

import java.util.*;
import java.util.concurrent.Exchanger;

public class Garage {
    private int numberOfParkingPace;
    private List<Worker> workerList;
    private int countCar;
    private Queue<Car> carQueue = new LinkedList<Car>();
    private Exchanger<List<Worker>> exchangerWorker = new Exchanger<List<Worker>>();
    private Exchanger<Queue<Car>> exchangerCar = new Exchanger<Queue<Car>>();

    public void setNumberOfParkingPace(int numberOfParkingPace) {
        this.numberOfParkingPace = numberOfParkingPace;
    }

    public void setWorkerList(List<Worker> workerList) {
        this.workerList = workerList;
    }

    public void setCountCar(int countCar) {
        this.countCar = countCar;
    }

    public void setCarQueue(Queue<Car> carQueue) {
        this.carQueue = carQueue;
    }

    public int getNumberOfParkingPace() {
        return numberOfParkingPace;
    }

    public List<Worker> getWorkerList() {
        return workerList;
    }

    public int getCountCar() {
        return countCar;
    }

    public Queue<Car> getCarQueue() {
        return carQueue;
    }

    public Garage(int numberOfParkingPace, List<Worker> workerList) {
        this.numberOfParkingPace = numberOfParkingPace;
        this.workerList = workerList;
        this.countCar = 0;
        new Distributor().start();
    }

    /**
     * @param car - машина, для добавления в гараж
     * @return true - если были свободные места
     */
    public boolean takeTheCar(Car car) throws InterruptedException {
        if (countCar < numberOfParkingPace){
            countCar++;
            System.out.println("Машина " +car.getName() + "заехала в гараж и ожидает мастера");
            carQueue.add(car);
            //отправляем данные в распределитель
            try {
                this.exchangerWorker.exchange(workerList);
                this.exchangerCar.exchange(carQueue);
            }
            catch(InterruptedException ex){
                System.out.println(ex.getMessage());
            }
            finally {
                return true;
            }
        }
        return false;
    }

}

class Distributor extends Thread{

    private boolean isActive = true;
    //для обмена данными между потоками
    private Exchanger<List<Worker>> exchangerWorker = new Exchanger<>();
    private Exchanger<Queue<Car>> exchangerCar = new Exchanger<>();
    //список работников
    private List<Worker> workerList;
    //список машин в гараже
    private Queue<Car> carQueue;

    private void disable(){
        isActive = false;
    }


    @Override
    public void run() {
           while (isActive){
               //получаем данные
               try {
                   workerList = exchangerWorker.exchange(null);
                   carQueue = exchangerCar.exchange(null);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
               System.out.println("Как часто происходит передача данных?");
               //если есть машины, то пройдемся по всем не занытым работникам и найдем того, кто сможет взяться за машину
               if (!carQueue.isEmpty()){
                   for(Worker worker : workerList){
                       if (worker.isCanWork()){
                           worker.setCar(carQueue.poll());
                           worker.startWork();
                       }
                   }
               }
           }
    }
}