package Pebbles;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Andrei on 10/20/2014.
 */
class PebbleGame {

    class Player {

        private int name;
        private LinkedList pebbles;
        private int totalValue;
        private int totalPebbles;

        Player (int name) {
            this.name = name;
            this.pebbles = new LinkedList<Pebble>();
            this.totalValue = 0;
            this.totalPebbles = 0;
        }

        int getTotalValue() {
            int total = 0;
            int x = this.pebbles.size();

            for (int j=0; j<x; j++) {
                Pebble current = (Pebble) this.pebbles.get(j);
                total += current.getWeight();
            }
            return total;
        }

        int getName() {
            return this.name;
        }

        int getTotalPebbles() {
            return this.totalPebbles;
        }

        Pebble discardPebble() {

            this.totalPebbles--;
            Random random1 = new Random();
            int handIndex = random1.nextInt(9);
            Pebble discardedPebble = (Pebble)this.pebbles.remove(handIndex);
            return discardedPebble;

        }

        void takePebble(Bag bag) {

            this.totalPebbles++;
            Random random1 = new Random();
            int bagIndex = random1.nextInt(bag.sizeOfBag()-1);
            this.pebbles.add(bag.removePebble(bagIndex));

        }
    }
}
