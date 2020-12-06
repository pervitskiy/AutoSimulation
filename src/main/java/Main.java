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

        List<Car> carList = new ArrayList<>();
        carList.add(new Bmw());
        carList.add(new Lada());
        carList.add(new Kia());
        carList.add(new Bmw());
        carList.add(new Bmw());
        carList.add(new Bmw());

        Garage garage = new Garage(numberOfParkingPace, numberOfMaster);
        garage.putCar(carList.get(0));
        garage.putCar(carList.get(1));
        garage.putCar(carList.get(2));
        garage.putCar(carList.get(3));
        garage.putCar(carList.get(0));
        garage.putCar(carList.get(1));
        garage.putCar(carList.get(2));
        garage.putCar(carList.get(3));
        garage.putCar(carList.get(0));
        garage.putCar(carList.get(1));
        garage.putCar(carList.get(2));
        garage.putCar(carList.get(3));

    }
}

