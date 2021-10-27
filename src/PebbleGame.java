import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class PebbleGame {
    private int noOfPlayers;
    private ArrayList<Pebble> bagA = new ArrayList<Pebble>();
    private ArrayList<Pebble> bagB = new ArrayList<Pebble>();
    private ArrayList<Pebble> bagC = new ArrayList<Pebble>();
    private ArrayList<Pebble> bagX = new ArrayList<Pebble>();
    private ArrayList<Pebble> bagY = new ArrayList<Pebble>();
    private ArrayList<Pebble> bagZ = new ArrayList<Pebble>();
    static Random randomNumGen = new Random();

    public static Pebble drawFrom(ArrayList<Pebble> bag) {
        int index = randomNumGen.nextInt(bag.size());
        Pebble pebble = bag.get(index);
        bag.remove(index);
        return pebble;
    }

    public void generateThreads() {
        for (int i = 0; i <= this.noOfPlayers; i++) {
            new Player();
            new Thread(Integer.toString(i)) {
                public void run() {
                    //chooseStartingPebbles();
                    //drawPebble();
                }
            }.start();
        }
    }

    public void chooseStartingPebbles() {
        double randNum = randomNumGen.nextDouble();
        if (randNum < (1.0/3.0)) {
            for (int i = 0; i <= 10; i++) {
                Pebble pebble = drawFrom(bagX);
            }
        }
        if (randNum < (2.0/3.0)) {
            for (int i = 0; i <= 10; i++) {
                drawFrom(bagY);
            }
        } else {
            for (int i = 0; i <= 10; i++) {
                drawFrom(bagZ);
            }
        }
    }

    public static void initialiseBag(String bagLocation, ArrayList<Pebble> bag){
        try(BufferedReader br = new BufferedReader(new FileReader(bagLocation))){
            String line;
            while((line = br.readLine()) != null){
                String[] values = line.split(",");
                for(String x: values){
                    bag.add(new Pebble(Integer.parseInt(x)));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main (String[] args)  {
        new PebbleGame();
    }

    public PebbleGame() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the number of players:");
        this.noOfPlayers = input.nextInt();
        input.nextLine();
        generateThreads();

        System.out.println("Please enter location of bag number 0 to load:");
        String bagLocation0 = input.nextLine();
        initialiseBag(bagLocation0, bagX);

        System.out.println("Please enter location of bag number 1 to load:");
        String bagLocation1 = input.nextLine();
        initialiseBag(bagLocation1, bagY);

        System.out.println("Please enter location of bag number 2 to load:");
        String bagLocation2 = input.nextLine();
        initialiseBag(bagLocation2, bagZ);

    }
}
