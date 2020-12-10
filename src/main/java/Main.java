import auto.TowTrack;
import service.Garage;
import utils.Spawn;
import utils.workDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        Scanner sc = new Scanner(System.in);
        int i = -1;
        int numberOfParkingPace=0;
        int numberOfMaster=0;
        int numberOfTowTrack=0;

        do {
            System.out.println("Введите количество эвакуаторов:");
            while (!sc.hasNextInt()) {
                System.out.println("Некорректные данные!");
                sc.next(); // this is important!
            }
            numberOfTowTrack = sc.nextInt();
            if (numberOfTowTrack < 0 || numberOfTowTrack > 50)
                System.out.println("от 1 до 50 пжл");
        } while (numberOfTowTrack <= 0 || numberOfTowTrack > 50);

        do {
            System.out.println("Введите вместимость парковки:");
            while (!sc.hasNextInt()) {
                System.out.println("Некорректные данные!");
                sc.next(); // this is important!
            }
            numberOfParkingPace = sc.nextInt();
            if (numberOfParkingPace < 0 || numberOfParkingPace > 50)
                System.out.println("от 1 до 50 пжл");
        } while (numberOfParkingPace <= 0 || numberOfParkingPace > 50);

        do {
            System.out.println("Введите количество мастеров:");
            while (!sc.hasNextInt()) {
                System.out.println("Некорректные данные!");
                sc.next(); // this is important!
            }
            numberOfMaster = sc.nextInt();
            if (numberOfMaster < 0 || numberOfMaster > 50)
                System.out.println("от 1 до 50 пжл");
        } while (numberOfMaster <= 0 || numberOfMaster > 50);

        Garage garage = new Garage(numberOfParkingPace, numberOfMaster);

        List<TowTrack> towTrackList = new ArrayList<>();
        for (int j=1; j<=numberOfTowTrack; j++){
            towTrackList.add(new TowTrack(j));
            towTrackList.get(j-1).setGarage(garage);
            towTrackList.get(j-1).start();
        }
        //поток, который спавнит машины
        Spawn spawn = new Spawn();
        spawn.start();

        while (true){
            //проходимся по списку эвакуаторов
            towTrackList.forEach(towTrack -> {
                //если эвакуатор свободен и есть машины, которые надо забрать, то забираем
                if(!towTrack.isCarIsLoaded() && !spawn.getCars().isEmpty()){
                    System.out.println("Сломанные машины " +  spawn.getCars());
                    towTrack.setCar(spawn.getCars().poll());
                }
            });
        }
    }
}



