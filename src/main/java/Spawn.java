import Car.*;

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
                if (rand < 0.33) {
                    cars.offer(new Bmw());
                } else if (rand < 0.66) {
                    cars.offer(new Lada());
                } else {
                    cars.offer(new Kia());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
