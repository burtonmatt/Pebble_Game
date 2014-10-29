package pebbles;
import java.util.LinkedList;


/**
 * Created by Andrei on 10/20/2014.
 * Version v1.12
 */
class Bag {

    private final String name;
    private LinkedList<Pebble> pebbles;

    public Bag (String name) {
        this.pebbles = new LinkedList<Pebble>();
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


    /**
     * Method called on an instance of Bag to transfer containing pebbles into this bag.
     *
     * @param bag  the bag that is going to be emptied
     */
    void transferPebbles(Bag bag) {
        this.pebbles = bag.pebbles;
        bag.pebbles.clear();
    }

}