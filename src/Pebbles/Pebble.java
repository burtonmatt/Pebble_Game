package pebbles;

/**
 * The Pebble class.
 * Every Pebble has a weight (positive int) and a parent bag, from which will be drawn.
 */
class Pebble {

    private final Integer weight;
    private Bag parentBag;

    /**
     * Simple constructor for the Pebble class
     * @param weight the value of the pebble
     */
    public Pebble (Integer weight) {

        this.weight = weight;
    }

    /**
     * Returns the weight of the pebble
     * @return The weight of the pebble
     */
    Integer getWeight() {

        return this.weight;
    }

    /**
     * Returns the parent bag of the pebble
     * @return The parent bag of the pebble
     */
    Bag getParentBag() {
        return this.parentBag;
    }

    /**
     * Sets the parent bag of the pebble
     * @param bag the new parent of the pebble
     */
    void setParentBag(Bag bag) {
        this.parentBag = bag;
    }
}