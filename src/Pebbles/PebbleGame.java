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

        Pebble discardPebble(int index) {
            this.totalPebbles--;
        }

        void takePebble(Bag bag) {
            this.totalPebbles++;
            
        }
    }
}