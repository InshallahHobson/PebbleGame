import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class PebbleGame {
    private int noOfPlayers;
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Pebble> bagA = new ArrayList<Pebble>();
    private ArrayList<Pebble> bagB = new ArrayList<Pebble>();
    private ArrayList<Pebble> bagC = new ArrayList<Pebble>();
    private ArrayList<Pebble> bagX = new ArrayList<Pebble>();
    private ArrayList<Pebble> bagY = new ArrayList<Pebble>();
    private ArrayList<Pebble> bagZ = new ArrayList<Pebble>();
    static Random randomNumGen = new Random();


    public class Player implements Runnable {
        private ArrayList<Pebble> pebblesInHand = new ArrayList<Pebble>();
        private Boolean won = false;
        private ArrayList<Pebble> nextDiscardTo;

        public Player() {
            players.add(this);
        }

        public ArrayList<Pebble> getPebblesInHand(){
            return pebblesInHand;
        }

        public Boolean getWon() {
            return won;
        }

        public ArrayList<Pebble> getNextDiscardTo() {
            return nextDiscardTo;
        }

        public void setNextDiscardTo(ArrayList<Pebble> nextDiscardTo) {
            this.nextDiscardTo = nextDiscardTo;
        }

        public void chooseStartingPebbles() {
            double randNum = randomNumGen.nextDouble();
            if (randNum < (1.0/3.0)) {
                for (int i = 0; i <= 9; i++) {
                    Pebble pebble = drawFrom(bagX);
                    pebblesInHand.add(pebble);
                    this.setNextDiscardTo(bagA);
                }
            }
            if (randNum < (2.0/3.0)) {
                for (int i = 0; i <= 9; i++) {
                    Pebble pebble = drawFrom(bagY);
                    pebblesInHand.add(pebble);
                    this.setNextDiscardTo(bagB);
                }
            } else {
                for (int i = 0; i <= 9; i++) {
                    Pebble pebble = drawFrom(bagZ);
                    pebblesInHand.add(pebble);
                    this.setNextDiscardTo(bagC);
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
            Pebble pebbleToDiscard = drawFrom(pebblesInHand);
            nextDiscardTo.add(pebbleToDiscard);

        }

        public void drawPebble() {
            double randNum = randomNumGen.nextDouble();
            if (randNum < (1.0/3.0)) {
                Pebble pebble = drawFrom(bagX);
                pebblesInHand.add(pebble);
                this.setNextDiscardTo(bagA);
            }
            if (randNum < (2.0/3.0)) {
                Pebble pebble = drawFrom(bagY);
                pebblesInHand.add(pebble);
                this.setNextDiscardTo(bagB);
            } else {
                Pebble pebble = drawFrom(bagZ);
                pebblesInHand.add(pebble);
                this.setNextDiscardTo(bagC);
            }
        }

        @Override
        public void run(){
            chooseStartingPebbles();
            checkWon();
            while (!this.getWon()) {
                this.discardPebble();
                this.drawPebble();
                checkWon();
            }
        }
    }

    public static Pebble drawFrom(ArrayList<Pebble> bag) {
        int index = randomNumGen.nextInt(bag.size());
        Pebble pebble = bag.get(index);
        bag.remove(index);
        return pebble;
    }

    public void generateThreads() {
        for(int i=0; i<this.players.size(); i++){
            new Thread(this.players.get(i));
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
            // testing - Pebble[] ps = p.getPebblesInHand();

        }
    }

    public static void main (String[] args)  {
        PebbleGame mainGame = new PebbleGame();
        mainGame.initialisePlayers();
        mainGame.generateThreads();
    }
}
