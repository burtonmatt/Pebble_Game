package pebbles;

/**
 *
 * Created by Andrei on 10/20/2014.
 */
class Pebble {

    private Integer weight;
    private Bag parentBag;

    Pebble (Integer weight) {

        this.weight = weight;
    }

    Integer getWeight() {

        return this.weight;
    }

    void setWeight(Integer weight ) {

        this.weight = weight;
    }

    Bag getParentBag() {
        return this.parentBag;
    }

    void setParentBag(Bag bag) {
        this.parentBag = bag;
    }
}