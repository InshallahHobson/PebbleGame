import java.util.ArrayList;

public class Pebble {
    private int weight;
    private ArrayList<Pebble> discardTo;

    public int getWeight() {
        return weight;
    }

    public void setDiscardTo(ArrayList<Pebble> discardTo) {
        this.discardTo = discardTo;
    }

    public Pebble(int weight) {
        this.weight = weight;
    }
}
