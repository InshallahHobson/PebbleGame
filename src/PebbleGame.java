import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PebbleGame {
    private int noOfPlayers;
    private ArrayList<Integer> bagA = new ArrayList<Integer>();
    private ArrayList<Integer> bagB = new ArrayList<Integer>();
    private ArrayList<Integer> bagC = new ArrayList<Integer>();
    private ArrayList<Integer> bagX = new ArrayList<Integer>();
    private ArrayList<Integer> bagY = new ArrayList<Integer>();
    private ArrayList<Integer> bagZ = new ArrayList<Integer>();

    public void generateThreads() {
        for (int i = 0; i <= this.noOfPlayers; i++) {
            new Thread(Integer.toString(i)) {
                public void run() {
                    //drawPebble
                }
            }.start();
        }
    }

    public static void initialiseBag(String bagLocation, ArrayList<Integer> bag){
        try(BufferedReader br = new BufferedReader(new FileReader(bagLocation))){
            String line;
            while((line = br.readLine()) != null){
                String[] values = line.split(",");
                for(String x: values){
                    bag.add(Integer.parseInt(x));
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

        System.out.println("Please enter location of bag number 0 to load:");
        String bagLocation1 = input.nextLine();
        initialiseBag(bagLocation1, bagY);

        System.out.println("Please enter location of bag number 0 to load:");
        String bagLocation2 = input.nextLine();
        initialiseBag(bagLocation2, bagZ);


    }
}
