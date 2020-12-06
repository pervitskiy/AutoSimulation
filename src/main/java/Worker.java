import Car.Car;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.Exchanger;

public class Worker extends Thread {
    private int id;
    private boolean canWork;
    private Car car;
    private boolean isActive;


    public Car getCar() {
        return car;
    }

    private void disable() {
        isActive = false;
    }

    public Worker(int id) {
        this.id = id;
        this.canWork = true;
        this.isActive = true;
    }

    public synchronized void setCar(Car car) {
        this.car = car;
        this.canWork = false;
    }

    public synchronized void setCanWork(boolean canWork) {
        this.canWork = canWork;
    }

    public synchronized boolean isCanWork() {
        return canWork;
    }

    @Override
    public void run() {
        while (isActive) {
            //System.out.println(id + "" + canWork);
            //будет работатать
            if (!isCanWork()) {
                System.out.println("Работник " + id + " начал работу с машиной " + car.getName());
                try {
                    Thread.sleep(car.getTimeForRepairs() * 1000);
                } catch (InterruptedException e) {
                    System.out.println("Работник " + id + " не справился");
                    e.printStackTrace();
                }
                System.out.println("Работник " + id + " закончил работу над машиной " + car.getName());
                System.out.println("");
                setCanWork(true);
            }
        }
    }
}



