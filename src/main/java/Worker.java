import Car.Car;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.Exchanger;

public class Worker {
    private int id;
    private boolean canWork;
    private Car car;
    private String name;
    private Exchanger<Car> exchangerCar = new Exchanger<Car>();
    private Exchanger<Boolean> booleanExchanger = new Exchanger<Boolean>();
    private Exchanger<Boolean> canWorkExchanger = new Exchanger<Boolean>();
    private Exchanger<Worker> workerExchanger = new Exchanger<>();

    public Car getCar() {
        return car;
    }

    public Worker(int id, String name) {
        this.id = id;
        this.name = name;
        this.canWork = true;
        new Work(this.id).start();
    }

    public void setCar(Car car){
        this.car = car;
    }

    public void setCanWork(boolean canWork) {
        this.canWork = canWork;
    }

    public boolean isCanWork() {
        return canWork;
    }

    public void startWork(){
        try {
            exchangerCar.exchange(getCar());
            booleanExchanger.exchange(isCanWork());
            workerExchanger.exchange(this);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

//
class Work extends Thread{
    private boolean isActive;
    private int id;
    private boolean isCanWork = false;

    private Exchanger<Worker> workerExchanger = new Exchanger<Worker>();
    private Exchanger<Car> exchangerCar = new Exchanger<Car>();
    private Exchanger<Boolean> booleanExchanger = new Exchanger<Boolean>();
    private Car car;
    private Worker worker;

    private void disable(){
        isActive = false;
    }

    public Work(int id) {
        this.isActive = true;
        this.id = id;
    }

    @Override
    public void run() {
        //бесконечный цикл
        while (isActive){
            //если получил работу
            try {
                car = exchangerCar.exchange(null);
                isCanWork = booleanExchanger.exchange(null);
                worker = workerExchanger.exchange(null);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(isCanWork){
                worker.setCanWork(false);
                System.out.println("Работник " + id + "начал ремонт машины " + car.getName());
                try {
                    Thread.sleep(car.getTimeForRepairs() * 1000);
                } catch (InterruptedException e) {
                    System.out.println("Работник " + id + "обосрался и что-то пошло не так");
                }
                System.out.println("Работник " + id + "закончил ремонт машины " + car.getName());
                worker.setCanWork(true);
            }
        }
    }
}
