package pebbles;

/**
 *The InvalidPlayersNumberException Class will throw an exception
 * if the number of players entered into the program is less than 2
 */
class InvalidPlayersNumberException extends Throwable  {

    InvalidPlayersNumberException() {
        System.out.println("Invalid number of Players entered!");
    }
}
