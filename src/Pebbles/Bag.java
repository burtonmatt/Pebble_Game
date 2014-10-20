package Pebbles;
import java.util.LinkedList;


/**
 * Created by Andrei on 10/20/2014.
 */
class Bag {

    private String name;
    private LinkedList pebbles;
    private String colour;

    Bag (String name, String colour) {
        this.pebbles = new LinkedList<Pebble>();
        this.colour = colour;
        this.name = name;
    }

    Pebble removePebble(int index) {
        return (Pebble)this.pebbles.remove(index);
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

}