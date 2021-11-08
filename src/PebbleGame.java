import javax.lang.model.type.NullType;
import java.io.*;
import java.util.*;

public class PebbleGame {
    final private int noOfPlayers;
    private ArrayList<Player> players = new ArrayList<>();
    private List<Pebble> bagA = Collections.synchronizedList(new ArrayList<Pebble>());
    private List<Pebble> bagB = Collections.synchronizedList(new ArrayList<Pebble>());
    private List<Pebble> bagC = Collections.synchronizedList(new ArrayList<Pebble>());
    private List<Pebble> bagX = Collections.synchronizedList(new ArrayList<Pebble>());
    private List<Pebble> bagY = Collections.synchronizedList(new ArrayList<Pebble>());
    private List<Pebble> bagZ = Collections.synchronizedList(new ArrayList<Pebble>());
    private Boolean continuePlaying = true;
    static Random randomNumGen = new Random();


    public class Player implements Runnable {
        private ArrayList<Pebble> pebblesInHand = new ArrayList<>();
        private List<Pebble> nextDiscardTo;
        private int playerValue;

        public Player() {
            players.add(this);
            playerValue = players.indexOf(this) + 1;
        }

        public int getPlayerValue() {
            return playerValue;
        }

        public ArrayList<Pebble> getPebblesInHand(){
            return pebblesInHand;
        }

        public String getPebblesInHandFormatted(){
            String formattedPebblesInHand = String.valueOf(pebblesInHand.get(0).getWeight());
            for(int i=1; i<pebblesInHand.size(); i++){
                formattedPebblesInHand += ", " + String.valueOf(pebblesInHand.get(i).getWeight());
            }
            return formattedPebblesInHand;
        }

        public List<Pebble> getNextDiscardTo() {
            return nextDiscardTo;
        }

        public void setNextDiscardTo(List<Pebble> nextDiscardTo) {
            this.nextDiscardTo = nextDiscardTo;
        }

        public synchronized void chooseStartingPebbles() {
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
                if (Objects.isNull(pebble)) {
                    //System.out.println("pebble is null");
                } else {
                    totalWeight += pebble.getWeight();
                }
            }
            if (totalWeight == 100) {
                continuePlaying = false;
            }
        }

        public synchronized Pebble discardPebble() {
            Pebble pebbleToDiscard = drawFrom(pebblesInHand);
            nextDiscardTo.add(pebbleToDiscard);
            return pebbleToDiscard;
        }

        public synchronized Pebble drawPebble() {
            //Issues with black bags becoming empty
            double randNum = randomNumGen.nextDouble();
            if (randNum < (1.0/3.0)) {
                if (bagX.size() == 0) {
                    //System.out.println("BEFORE - white bag: " + bagA.size() + ", black bag: " + bagX.size());
                    synchronized (this) {emptyWhiteBag(bagA);}
                    //System.out.println("AFTER - white bag: " + bagA.size() + ", black bag: " + bagX.size());
                }
                Pebble pebble = drawFrom(bagX);
                pebblesInHand.add(pebble);
                this.setNextDiscardTo(bagA);
                return pebble;
            }
            if (randNum < (2.0/3.0)) {
                if (bagY.size() == 0) {
                    //System.out.println("BEFORE - white bag: " + bagB.size() + ", black bag: " + bagY.size());
                    synchronized (this) {emptyWhiteBag(bagB);}
                    //System.out.println("AFTER - white bag: " + bagB.size() + ", black bag: " + bagY.size());
                }
                Pebble pebble = drawFrom(bagY);
                pebblesInHand.add(pebble);
                this.setNextDiscardTo(bagB);
                return pebble;
            } else {
                if (bagZ.size() == 0) {
                    //System.out.println("BEFORE - white bag: " + bagC.size() + ", black bag: " + bagZ.size());
                    synchronized (this) {emptyWhiteBag(bagC);}
                    //System.out.println("AFTER - white bag: " + bagC.size() + ", black bag: " + bagZ.size());
                }
                Pebble pebble = drawFrom(bagZ);
                pebblesInHand.add(pebble);
                this.setNextDiscardTo(bagC);
                return pebble;
            }

        }

        //public void writeToFileDraw(Player player, Pebble p){
            // add bag
            //try{
                //FileWriter myWriter = new FileWriter("player" +
                        //player.getPlayerValue() + "_output.txt");
                //myWriter.write(String.format("player%d has drawn a %d from bag\n",
                        //playerValue,p.getWeight()));
                //myWriter.write(String.format("player%d hand is %s",player.getPlayerValue(),
                        //player.getPebblesInHandFormatted()));
                //myWriter.close();
            //}catch(IOException e){
                //System.out.println("An error has occurred");
                //e.printStackTrace();
            //}

        //}
        //public void writeToFileDiscard(Player player, Pebble p){
            // add bag
            //try{
                //FileWriter myWriter = new FileWriter("player" +
                        //player.getPlayerValue() + "_output.txt");
                //myWriter.write(String.format("player%d has discarded a %d from bag\n",
                        //playerValue,p.getWeight()));
                //myWriter.write(String.format("player%d hand is %s",player.getPlayerValue(),
                        //player.getPebblesInHandFormatted()));
                //myWriter.close();
            //}catch(IOException e){
               // System.out.println("An error has occurred");
                //e.printStackTrace();
            //}
        //}

        //public void write2FileDiscard () {}

        public synchronized static Pebble drawFrom(List<Pebble> bag) {
            synchronized (bag){
                int index;
                if (bag.size() == 1) {
                    index = 0;
                } else {
                    index = randomNumGen.nextInt(bag.size());
                }
                Pebble pebble = bag.get(index);
                //System.out.println("index: " + index + ", bag contains " + bag.size() + "pebbles.");
                bag.remove(index);
                return pebble;
            }

        }

        @Override
        public void run(){
            chooseStartingPebbles();
            checkWon();
            while (continuePlaying) {
                Pebble pebbleDiscarded = this.discardPebble();
                //this.writeToFileDiscard(this, pebbleDiscarded);
                Pebble pebbleDrawn = this.drawPebble();
                //this.writeToFileDraw(this, pebbleDrawn);
                //System.out.println(getPebblesInHand());
                checkWon();
            }
            System.out.println(this + "has finished!");
            //notify();
        }
    }

    public void generateThreads() {
        for (Player player : this.players) {
            new Thread(player).start();
        }
    }

    public static void initialiseBag(String bagLocation, List<Pebble> bag){
        try(BufferedReader br = new BufferedReader(new FileReader(bagLocation))){
            String line;
            while((line = br.readLine()) != null){
                String[] values = line.split(",");
                for(String x: values){
                    x = x.replaceAll("\\s+","");
                    x = x.replace("\n", "");
                    bag.add(new Pebble(Integer.parseInt(x)));
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public synchronized void emptyWhiteBag(List<Pebble> whiteBag) {
        if (whiteBag == bagA) {
            for (int i = 0; i < bagA.size(); i++) {
                Pebble pebble = bagA.get(i);
                bagX.add(pebble);
            }
            bagA.clear();
        } else if (whiteBag == bagB) {
            for (int i = 0; i < bagB.size(); i++) {
                Pebble pebble = bagB.get(i);
                bagY.add(pebble);
            }
            bagB.clear();
        } else if (whiteBag == bagC) {
            for (int i = 0; i < bagC.size(); i++) {
                Pebble pebble = bagC.get(i);
                bagZ.add(pebble);
            }
            bagC.clear();
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
            new Player();
        }
    }

    public static void main (String[] args)  {
        PebbleGame mainGame = new PebbleGame();
        //for(Pebble p:mainGame.bagA) {
            //System.out.println(p.getWeight());
        //};
        mainGame.initialisePlayers();
        mainGame.generateThreads();
    }
}
