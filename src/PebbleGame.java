import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class PebbleGame {
    private int noOfPlayers;
    private ArrayList<Player> players;
    private ArrayList<Pebble> bagA = new ArrayList<Pebble>();
    private ArrayList<Pebble> bagB = new ArrayList<Pebble>();
    private ArrayList<Pebble> bagC = new ArrayList<Pebble>();
    private ArrayList<Pebble> bagX = new ArrayList<Pebble>();
    private ArrayList<Pebble> bagY = new ArrayList<Pebble>();
    private ArrayList<Pebble> bagZ = new ArrayList<Pebble>();
    static Random randomNumGen = new Random();


    public class Player {
        private Pebble[] pebblesInHand = new Pebble[10];
        private Boolean won = false;

        public Player() {
            players.add(this);
            chooseStartingPebbles();
            checkWon();
        }

        public Pebble[] getPebblesInHand(){
            return pebblesInHand;
        }

        public Boolean getWon() {
            return won;
        }

        public void chooseStartingPebbles() {
            double randNum = randomNumGen.nextDouble();
            if (randNum < (1.0/3.0)) {
                for (int i = 0; i <= 9; i++) {
                    Pebble pebble = drawFrom(bagX);
                    pebblesInHand[i] = pebble;
                    pebble.setDiscardTo(bagA);
                }
            }
            if (randNum < (2.0/3.0)) {
                for (int i = 0; i <= 9; i++) {
                    Pebble pebble = drawFrom(bagY);
                    pebblesInHand[i] = pebble;
                    pebble.setDiscardTo(bagB);
                }
            } else {
                for (int i = 0; i <= 9; i++) {
                    Pebble pebble = drawFrom(bagZ);
                    pebblesInHand[i] = pebble;
                    pebble.setDiscardTo(bagC);
                }
            }
        }

        public void checkWon() {
            int totalWeight = 0;
            for (Pebble pebble : pebblesInHand) {
                    totalWeight += pebble.getWeight();
            }
            if (totalWeight == 100) {
                won = true;
            } else {
                won = false;
            }
        }

        public void discardPebble() {

        }

        public void drawPebble() {
        }
    }

    public static Pebble drawFrom(ArrayList<Pebble> bag) {
        int index = randomNumGen.nextInt(bag.size());
        Pebble pebble = bag.get(index);
        bag.remove(index);
        return pebble;
    }

    public void generateThreads() {
        for (int i = 0; i <= this.players.size(); i++) {
            Player currentPlayer = players.get(i);
            new Thread(Integer.toString(i)) {
                public void run() {
                    while (!currentPlayer.getWon()) {
                        currentPlayer.discardPebble();
                        currentPlayer.drawPebble();

                    }
                }
            }.start();
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

    public PebbleGame() {
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter the number of players:");
        this.noOfPlayers = input.nextInt();
        input.nextLine();

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

    public void initialisePlayers(){
        for (int i = 1; i <= this.noOfPlayers; i++) {
            Player p = new Player();
            Pebble[] ps = p.getPebblesInHand();




        }
    }

    public static void main (String[] args)  {
        PebbleGame mainGame = new PebbleGame();
        mainGame.initialisePlayers();
        mainGame.generateThreads();

    }
}
