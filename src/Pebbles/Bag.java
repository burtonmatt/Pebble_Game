package pebbles;
import java.util.LinkedList;


/**
 * The Bab Class is used to store the pebbles
 * Every bag has a name and a Linked List of Pebbles
 */
class Bag {

    private final String name;
    private LinkedList<Pebble> pebbles;

    /**
     * The constructor for the Bag class
     * @param name the name of the bag
     */
    public Bag (String name) {
        this.pebbles = new LinkedList<Pebble>();
        this.name = name;
    }

    /**
     * Removes a pebble at the given index from the Linked List
     * @param index the index of the pebble that is going to be removed
     * @return the pebble that was removed from the Bag
     */
    Pebble removePebble(int index) {
        return this.pebbles.remove(index);
    }

    /**
     * Method to add a pebble to the bag
     * @param pebble the pebble that is going to be added in the Bag
     */
    void addPebble(Pebble pebble) {

        this.pebbles.add(pebble);
    }

    /**
     * Method which returns the number of pebbles inside it
     * @return the size of the bag
     */
    int sizeOfBag() {

        return this.pebbles.size();
    }

    /**
     * Method to get the bag's name
     * @return the name of the Bag
     */
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