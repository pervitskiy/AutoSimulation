import Car.Car;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.Exchanger;

public class Worker extends Thread {
    private int id;
    private Garage garage;
    private Car car;
    private boolean isActive;


    public synchronized Car getCar() {
        return car;
    }

    private void disable() {
        isActive = false;
    }

    public Worker(int id, Garage garage) {
        this.id = id;
        this.isActive = true;
        this.garage = garage;
    }

    public synchronized void setCar(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        Car car = null;
        while (isActive) {
            car = garage.getCar().poll();
            if (car != null) {
                setCar(car);
                //System.out.println(id + "" + canWork);
                System.out.println("Работник " + id + " начал работу с машиной " + car.getName());
                try {
                    Thread.sleep(car.getTimeForRepairs() * 1000);
                } catch (InterruptedException e) {
                    System.out.println("Работник " + id + " не справился");
                    e.printStackTrace();
                }
                System.out.println("Работник " + id + " закончил работу над машиной " + car.getName());
                garage.getCarQueue().poll();
            }
        }
    }
}



