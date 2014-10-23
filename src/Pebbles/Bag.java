package pebbles;
import java.util.LinkedList;


/**
 * Created by Andrei on 10/20/2014.
 * Version v1.12
 */
class Bag {

    private final String name;
    private LinkedList<Pebble> pebbles;
    private final String colour;

    public Bag (String name, String colour) {
        this.pebbles = new LinkedList<Pebble>();
        this.colour = colour;
        this.name = name;
    }

    Pebble removePebble(int index) {
        return this.pebbles.remove(index);
    }

    void addPebble(Pebble pebble) {

        this.pebbles.add(pebble);
    }

    int sizeOfBag() {

        return this.pebbles.size();
    }

    String getName() {

        return this.name;
    }

    String getColour() {

        return this.colour;
    }

    /**
     * Method called on an instance of Bag to transfer containing pebbles into this bag.
     *
     * @param bag
     */
    void transferPebbles(Bag bag) {
        this.pebbles = bag.pebbles;
        bag.pebbles.clear();
    }

}