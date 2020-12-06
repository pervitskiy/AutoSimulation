import Car.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(System.in);
        System.out.println("Введите количество эвакуаторов: ");
        int numberOfTowTrack = sc.nextInt();
        System.out.println("Введите вместимость парковки: ");
        int numberOfParkingPace = sc.nextInt();
        System.out.println("Введите количество мастеров: ");
        int numberOfMaster = sc.nextInt();

        List<Worker> listWorker = new ArrayList<Worker>();
        for(int i=1 ; i<numberOfMaster; i++){
            listWorker.add(new Worker(i, "Работник" + i));
        }
        List<Car> carList = new ArrayList<Car>();
        carList.add(new Bmw());
        carList.add(new Lada());
        carList.add(new Kia());
        carList.add(new Bmw());
        carList.add(new Bmw());
        carList.add(new Bmw());

        Garage garage = new Garage(numberOfParkingPace, listWorker);
        garage.takeTheCar(carList.get(0));
        garage.takeTheCar(carList.get(1));
        garage.takeTheCar(carList.get(2));
        garage.takeTheCar(carList.get(3));
        garage.takeTheCar(carList.get(4));

    }
}

