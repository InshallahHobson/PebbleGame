import java.util.ArrayList;
import java.util.Scanner;
public class PebbleGame {
    private int noOfPlayers;
    private ArrayList<Integer> aContents = new ArrayList<Integer>();
    private ArrayList<Integer> bContents = new ArrayList<Integer>();
    private ArrayList<Integer> cContents = new ArrayList<Integer>();
    private ArrayList<Integer> xContents = new ArrayList<Integer>();
    private ArrayList<Integer> yContents = new ArrayList<Integer>();
    private ArrayList<Integer> zContents = new ArrayList<Integer>();

    public void generateThreads() {
        for (int i = 0; i <= this.noOfPlayers; i++) {
            new Thread(Integer.toString(i)) {
                public void run() {
                    //drawPebble
                }
            }.start();
        }
    }

    public static void main(String[] args) {
        new PebbleGame();
    }

    public PebbleGame() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the number of players:");
        this.noOfPlayers = input.nextInt();
        generateThreads();
        System.out.println("Please enter location of bag number 0 to load:");

        input.close();
    }
}
