package pebbles;

/**
 * The NotEnoughPebblesException Class will throw an exception if
 * there are not enough pebbles to start the game
 */
class NotEnoughPebblesException extends Throwable {

    NotEnoughPebblesException() {
        System.out.println("There are not enough pebbles to start the game!");
    }

}
