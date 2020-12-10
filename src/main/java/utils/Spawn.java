package utils;

import auto.*;

import java.util.*;

public class Spawn extends Thread {

    private Queue<Car> cars = new LinkedList<Car>();

    public synchronized Queue<Car> getCars() {
        return cars;
    }

    public synchronized void setCars(Queue<Car> cars) {
        this.cars = cars;
    }

    @Override
    public void run() {
        long i;
        while(true) { //бесконечно крутим
            try {
                //рандомно во времени создаем машину
                i = Math.round(Math.random() * 1000);
                Thread.sleep(i); // 4 секунды в милисекундах
                double rand = Math.random();
                if (rand < 0.16) {
                    cars.offer(Car.BMW);
                } else if (rand < 0.33) {
                    cars.offer(Car.KIA);
                } else if (rand < 0.49) {
                    cars.offer(Car.MERCEDES);
                } else if (rand < 0.66) {
                    cars.offer(Car.VOLKSWAGEN);
                } else if (rand < 0.83) {
                    cars.offer(Car.TOYOTA);
                } else {
                    cars.offer(Car.LADA);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /*
            if (!getCars().isEmpty()){
                System.out.println(getCars());
            }

             */
        }
    }
}
