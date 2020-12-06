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


        Garage garage = new Garage(numberOfParkingPace, numberOfMaster);

        List<TowTrack> towTrackList = new ArrayList<>();
        for (int i=1; i<=numberOfTowTrack; i++){
            towTrackList.add(new TowTrack(i));
            towTrackList.get(i-1).setGarage(garage);
            towTrackList.get(i-1).start();
        }

        Spawn spawn = new Spawn();
        spawn.start();

        int count = 0;
        while (true){
            towTrackList.forEach(towTrack -> {
                if(!towTrack.isCarIsLoaded() && !spawn.getCars().isEmpty()){
                    towTrack.setCar(spawn.getCars().poll());
                }
            });
        }

        /*
        List<Car> carList = new ArrayList<>();
        carList.add(new Bmw()); //0 -bmw
        carList.add(new Lada()); //1- lada
        carList.add(new Kia()); //2-kia


        garage.putCar(carList.get(0));
        garage.putCar(carList.get(1));
        garage.putCar(carList.get(2));
        garage.putCar(carList.get(2));
        garage.putCar(carList.get(1));
        garage.putCar(carList.get(2));
        garage.putCar(carList.get(0));
        garage.putCar(carList.get(0));
        garage.putCar(carList.get(1));

         */

    }
}

