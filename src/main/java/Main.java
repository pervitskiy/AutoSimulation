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

