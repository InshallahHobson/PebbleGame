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
                System.out.println(values);
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
        generateThreads();
        System.out.println("Please enter location of bag number 0 to load:");
        String bagLocation = input.nextLine();
        initialiseBag(bagLocation, bagX);
        for (int x:bagX) {
            System.out.println(x);
        }
    }
}
