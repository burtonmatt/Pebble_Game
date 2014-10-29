package pebbles;

/**
 *
 * Created by Andrei on 10/20/2014.
 */
class Pebble {

    private final Integer weight;
    private Bag parentBag;

    public Pebble (Integer weight) {

        this.weight = weight;
    }

    Integer getWeight() {

        return this.weight;
    }


    Bag getParentBag() {
        return this.parentBag;
    }

    void setParentBag(Bag bag) {
        this.parentBag = bag;
    }
}