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

        while (true){
            towTrackList.forEach(towTrack -> {
                if(!towTrack.isCarIsLoaded() && !spawn.getCars().isEmpty()){
                    towTrack.setCar(spawn.getCars().poll());
                }
            });
        }
    }
}

