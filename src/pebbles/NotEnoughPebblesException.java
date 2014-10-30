package pebbles;

/**
 * Created by Andrei on 10/24/2014.
 * Version v1.12
 */
class NotEnoughPebblesException extends Throwable {

    NotEnoughPebblesException() {
        System.out.println("There are not enough pebbles to start the game!");
    }

}
