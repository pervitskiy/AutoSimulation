package service;

import auto.Car;
import utils.workDB;

import java.sql.Timestamp;

public class Worker extends Thread {
    private int id;
    private Garage garage;
    private boolean isActive;

    private void disable() {
        isActive = false;
    }

    public Worker(int id, Garage garage) {
        this.id = id;
        this.isActive = true;
        this.garage = garage;
    }

    @Override
    public void run() {
        Car car = null;
        while (isActive) {
            synchronized (garage) {
                car = garage.getCar().poll();
            }
            if (car != null) {
                //System.out.println(id + "" + canWork);
                workDB.add("Worker " + id + " started working with the car " + car.name(),
                        new Timestamp(System.currentTimeMillis()));
                System.out.println("Работник " + id + " начал работу с машиной " + car.name());
                try {
                    Thread.sleep(car.getTimeForRepairs() * 1000);
                } catch (InterruptedException e) {
                    workDB.add("Worker " + id + " did not cope",
                            new Timestamp(System.currentTimeMillis()));
                    System.out.println("Работник " + id + " не справился");
                    e.printStackTrace();
                }
                workDB.add("Worker " + id + " finished work on the car " + car.name(),
                        new Timestamp(System.currentTimeMillis()));
                System.out.println("Работник " + id + " закончил работу над машиной " + car.name());
                synchronized (garage) {
                    garage.getCarQueue().poll();
                    garage.notify();
                }
            }
        }
    }
}



