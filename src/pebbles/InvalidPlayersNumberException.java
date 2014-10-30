package pebbles;

/**
 * Created by Andrei on 10/24/2014.
 * Version v1.12
 */
class InvalidPlayersNumberException extends Throwable  {

    InvalidPlayersNumberException() {
        System.out.println("Invalid number of Players entered!");
    }
}
