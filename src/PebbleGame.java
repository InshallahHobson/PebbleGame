import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

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

    public static void initialiseBag(String bagLocation, ArrayList<Integer> bag) throws FileNotFoundException{
        try {
            File file = new File(bagLocation);
            Scanner reader = new Scanner(file);
            while(reader.hasNextLine()) {
                bag.add(reader.nextInt());
            }
            reader.close();
        }  catch (FileNotFoundException e) {
            System.out.println("File not found.");
            e.printStackTrace();
        }
    }

    public static void main (String[] args) throws Exception {
        new PebbleGame();
    }

    public PebbleGame() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the number of players:");
        this.noOfPlayers = input.nextInt();
        generateThreads();
        System.out.println("Please enter location of bag number 0 to load:");
        String bagLocation = input.nextLine();
        try {
            initialiseBag(bagLocation, bagX);
        } catch (FileNotFoundException e){
            e.printStackTrace();;
        }
        for (int x:bagX) {
            System.out.println(x);
        }
        input.close();
    }
}
