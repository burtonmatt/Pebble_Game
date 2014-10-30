package pebbles;
import java.util.LinkedList;
import java.util.Random;
import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Class containing main method that simulates the pebble game
 * outlined in the specification for this CA.
 *
 */
class PebbleGame {
    /**
    * Nested player class that implements the Runnable interface
    * so that it can be created as a thread.
    *
    */
    static class Player implements Runnable {
	// attributes for the Player class
        private final int name;
	// we are using LinkedList as a means of modelling the Player's 'hand'
        private LinkedList<Pebble> pebbles;  
        final Random next = new Random();
		
	/**
	* Single argument constructor for the Player class that sets the Player's name.
	* Player's name is simply an integer value.
	*
	* @param name	this Player's name
	*/
        public Player (int name) {
            this.name = name;
            this.pebbles = new LinkedList<Pebble>();

        }
	/**
	* Method to calculate the total weight value of the pebbles in the Player's 'hand'.
	*
	* @return	total value of pebbles in Player's hand
	*/
        int getTotalValue() {
            int total = 0;
			
            // loop through pebbles in Player's hand, adding each pebble's weight to counter
            for (Object pebble : this.pebbles) {
                Pebble current = (Pebble) pebble;
                total += current.getWeight();
            }
            return total;
        }
		
	/**
	* Method that returns the name of the Player.
	* In this implementation, the Player's name is represented as an integer value.
	*
	* @return	this Player's name (int)
	*/
        int getPlayerName() {
            return this.name;
        }
		
	/**
	* Method that will discard a random pebble from this Player's 'hand'
	* into the white bag corresponding the the black bag that this player
	* last drew from.
	* <p>
	* This method is synchronized so that only one Player can be discarding
	* a pebble at any given time.
	*/
        synchronized void discardPebble() {

            Random random1 = new Random();
            // we generate a random integer value between 0-9 (as there are 10 pebbles in the 'hand')
            int handIndex = random1.nextInt(9);
            // we then set the pebble at the randomly generated index as the pebble to be discarded
            Pebble discarding = this.pebbles.remove(handIndex);
            // we query the last pebble that was drawn by this player for the parent bag of that pebble
            Bag parent = this.pebbles.getLast().getParentBag();
			
            // we then discard the selected pebble to the corresponding bag to the parent bag of the 
            // last pebble drawn by this player. we also write to this player's file the details
            // of the pebble that was discarded and the updated state of the player's hand
            // this information is also displayed to the terminal window
            if (parent == bagX) {
                bagA.addPebble(discarding);
                String message = "Player"+this.getPlayerName()+" has discarded a"+discarding.getWeight()+" to bagA";
                WriteToFile.write(this.getPlayerName(), message);
                System.out.println(message);
            }
            if (parent == bagY) {
                bagB.addPebble(discarding);
                String message = "Player"+this.getPlayerName()+" has discarded a"+discarding.getWeight()+" to bagB";
                WriteToFile.write(this.getPlayerName(), message);
                System.out.println(message);
            }
            if (parent == bagZ) {
                bagC.addPebble(discarding);
                String message = "Player"+this.getPlayerName()+" has discarded a"+discarding.getWeight()+" to bagC";
                WriteToFile.write(this.getPlayerName(), message);
                System.out.println(message);
            }
        }
		
	/**
	* Method to draw a pebble uniformly at random from the specified bag
	* to this Player's 'hand' during the initial pebble-drawing stage.
	* <p>
	* This method is synchronized so that only one Player can be drawing an initial pebble
	* at any given time.
	*
	* @param bag	the bag from which the pebble is to be drawn
	*/
        synchronized void takeInitialPebble(Bag bag) {

            Random random1 = new Random();
            // we generate a random integer value between 0 and the number of pebbles in the bag
            int bagIndex = random1.nextInt(bag.sizeOfBag()-1);
            // adds to the player's hand the pebble at the generated index in the bag
            // while also removing that pebble from the bag's contents
            this.pebbles.add(bag.removePebble(bagIndex));

        }
		
	/**
	* Method to draw a pebble from a random black bag into this Player's hand
	* <p>
	* This method is synchronized so that only one Player can be drawing a pebble
	* at any given time.
	*/
        synchronized void takePebble() {
            String message;
            String message2;
            // randomly generate an integer that can take one of three values
            int randomBag = next.nextInt(2);
			
            // depending on the random integer, takes a pebble from one of the black bags
            if (randomBag == 0){
		// checks that the selected bag is not empty
                if (bagX.sizeOfBag() !=0) {
                    takeInitialPebble(bagX);
                    // writes to the player's file the pebble that has been drawn
                    message = "player"+name+" has drawn a "+this.pebbles.getLast().getWeight()+" from bag"+bagX.getName();
                    WriteToFile.write(name,message);
                    // and also displays the pebbles now in this player's hand
                    message2 = "player"+name+" hand is "+this.pebbles.get(0).getWeight()+", "+this.pebbles.get(1).getWeight()+", "+this.pebbles.get(2).getWeight()
                               +", "+this.pebbles.get(3).getWeight()+", "+this.pebbles.get(4).getWeight()+", "+this.pebbles.get(5).getWeight()+", "+
                               this.pebbles.get(6).getWeight()+", "+this.pebbles.get(7).getWeight()+", "+this.pebbles.get(8).getWeight()+", "+this.pebbles.get(9).getWeight();
                    WriteToFile.write(name,message2);
                    // this information is also displayed to the terminal window
                    System.out.println(message);
                    System.out.println(message2);
                }
                else { // if the bag that was initially selected, the player attempts to draw from one of the other black bags
                    if (bagY.sizeOfBag() !=0){
                        takeInitialPebble(bagY);
                        message = "player"+name+" has drawn a "+this.pebbles.getLast().getWeight()+" from bag"+bagY.getName();
                        WriteToFile.write(name,message);
			message2 = "player"+name+" hand is "+this.pebbles.get(0).getWeight()+", "+this.pebbles.get(1).getWeight()+", "+this.pebbles.get(2).getWeight()
                                   +", "+this.pebbles.get(3).getWeight()+", "+this.pebbles.get(4).getWeight()+", "+this.pebbles.get(5).getWeight()+", "+
                                   this.pebbles.get(6).getWeight()+", "+this.pebbles.get(7).getWeight()+", "+this.pebbles.get(8).getWeight()+", "+this.pebbles.get(9).getWeight();
			WriteToFile.write(name,message2);
                        System.out.println(message);
			System.out.println(message2);
                    }
                    else {
                        if (bagZ.sizeOfBag() != 0){
                            takeInitialPebble(bagZ);
                            message = "player"+name+" has drawn a "+this.pebbles.getLast().getWeight()+" from bag"+bagZ.getName();
                            WriteToFile.write(name,message);
                            message2 = "player"+name+" hand is "+this.pebbles.get(0).getWeight()+", "+this.pebbles.get(1).getWeight()+", "+this.pebbles.get(2).getWeight()
                                       +", "+this.pebbles.get(3).getWeight()+", "+this.pebbles.get(4).getWeight()+", "+this.pebbles.get(5).getWeight()+", "+
                                       this.pebbles.get(6).getWeight()+", "+this.pebbles.get(7).getWeight()+", "+this.pebbles.get(8).getWeight()+", "+this.pebbles.get(9).getWeight();
                            WriteToFile.write(name,message2);
                            System.out.println(message);
                            System.out.println(message2);
                        }
                        else { // if all the black bags are empty, a message is displayed to the terminal window
                            System.out.println("All bags are empty!");

                        }
                    }
                }
            }
            if (randomBag == 1){ // same process as above
                if (bagY.sizeOfBag() !=0) {
                    takeInitialPebble(bagY);
                    message = "player"+name+" has drawn a "+this.pebbles.getLast().getWeight()+" from bag"+bagY.getName();
                    WriteToFile.write(name,message);
                    message2 = "player"+name+" hand is "+this.pebbles.get(0).getWeight()+", "+this.pebbles.get(1).getWeight()+", "+this.pebbles.get(2).getWeight()
                               +", "+this.pebbles.get(3).getWeight()+", "+this.pebbles.get(4).getWeight()+", "+this.pebbles.get(5).getWeight()+", "+
                               this.pebbles.get(6).getWeight()+", "+this.pebbles.get(7).getWeight()+", "+this.pebbles.get(8).getWeight()+", "+this.pebbles.get(9).getWeight();
                    WriteToFile.write(name,message2);
                    System.out.println(message);
                    System.out.println(message2);
                }
                else {
                    if (bagZ.sizeOfBag() !=0){
                        takeInitialPebble(bagZ);
                        message = "player"+name+" has drawn a "+this.pebbles.getLast().getWeight()+" from bag"+bagZ.getName();
                        WriteToFile.write(name,message);
                        message2 = "player"+name+" hand is "+this.pebbles.get(0).getWeight()+", "+this.pebbles.get(1).getWeight()+", "+this.pebbles.get(2).getWeight()
                                   +", "+this.pebbles.get(3).getWeight()+", "+this.pebbles.get(4).getWeight()+", "+this.pebbles.get(5).getWeight()+", "+
                                   this.pebbles.get(6).getWeight()+", "+this.pebbles.get(7).getWeight()+", "+this.pebbles.get(8).getWeight()+", "+this.pebbles.get(9).getWeight();
			WriteToFile.write(name,message2);
                        System.out.println(message);
			System.out.println(message2);
                    }
                    else {
                        if (bagX.sizeOfBag() != 0){
                            takeInitialPebble(bagX);
                            message = "player"+name+" has drawn a "+this.pebbles.getLast().getWeight()+" from bag"+bagX.getName();
                            WriteToFile.write(name,message);
                            message2 = "player"+name+" hand is "+this.pebbles.get(0).getWeight()+", "+this.pebbles.get(1).getWeight()+", "+this.pebbles.get(2).getWeight()
                                       +", "+this.pebbles.get(3).getWeight()+", "+this.pebbles.get(4).getWeight()+", "+this.pebbles.get(5).getWeight()+", "+
                                       this.pebbles.get(6).getWeight()+", "+this.pebbles.get(7).getWeight()+", "+this.pebbles.get(8).getWeight()+", "+this.pebbles.get(9).getWeight();
                            WriteToFile.write(name,message2);
                            System.out.println(message);
                            System.out.println(message2);
                        }
                        else {
                            System.out.println("All bags are empty!");

                        }
                    }
                }
            }
            if (randomBag == 2){ // same process as above
                if (bagZ.sizeOfBag() !=0) {
                    takeInitialPebble(bagZ);
                    message = "player"+name+" has drawn a "+this.pebbles.getLast().getWeight()+" from bag"+bagZ.getName();
                    WriteToFile.write(name,message);
                    message2 = "player"+name+" hand is "+this.pebbles.get(0).getWeight()+", "+this.pebbles.get(1).getWeight()+", "+this.pebbles.get(2).getWeight()
                               +", "+this.pebbles.get(3).getWeight()+", "+this.pebbles.get(4).getWeight()+", "+this.pebbles.get(5).getWeight()+", "+
                               this.pebbles.get(6).getWeight()+", "+this.pebbles.get(7).getWeight()+", "+this.pebbles.get(8).getWeight()+", "+this.pebbles.get(9).getWeight();
                    WriteToFile.write(name,message2);
                    System.out.println(message);
                    System.out.println(message2);
                }
                else {
                    if (bagX.sizeOfBag() !=0){
                        takeInitialPebble(bagX);
                        message = "player"+name+" has drawn a "+this.pebbles.getLast().getWeight()+" from bag"+bagX.getName();
                        WriteToFile.write(name,message);
			message2 = "player"+name+" hand is "+this.pebbles.get(0).getWeight()+", "+this.pebbles.get(1).getWeight()+", "+this.pebbles.get(2).getWeight()
                                   +", "+this.pebbles.get(3).getWeight()+", "+this.pebbles.get(4).getWeight()+", "+this.pebbles.get(5).getWeight()+", "+
                                   this.pebbles.get(6).getWeight()+", "+this.pebbles.get(7).getWeight()+", "+this.pebbles.get(8).getWeight()+", "+this.pebbles.get(9).getWeight();
			WriteToFile.write(name,message2);
                        System.out.println(message);
			System.out.println(message2);
                    }
                    else {
                        if (bagY.sizeOfBag() != 0){
                            takeInitialPebble(bagY);
                            message = "player"+name+" has drawn a "+this.pebbles.getLast().getWeight()+" from bag"+bagY.getName();
                            WriteToFile.write(name,message);
                            message2 = "player"+name+" hand is "+this.pebbles.get(0).getWeight()+", "+this.pebbles.get(1).getWeight()+", "+this.pebbles.get(2).getWeight()
                                       +", "+this.pebbles.get(3).getWeight()+", "+this.pebbles.get(4).getWeight()+", "+this.pebbles.get(5).getWeight()+", "+
                                       this.pebbles.get(6).getWeight()+", "+this.pebbles.get(7).getWeight()+", "+this.pebbles.get(8).getWeight()+", "+this.pebbles.get(9).getWeight();
                            WriteToFile.write(name,message2);
                            System.out.println(message);
                            System.out.println(message2);
                        }
                        else {
                            System.out.println("All bags are empty!");

                        }
                    }
                }
            }

        }

	/**
	* Run method overidden from the Runnable interface.
	* <p>
	* This method draws and discards pebbles until the value of
        * all the pebbles in the Player's hand equals 100.
	*/
        public void run() {
            // create a file that will document this player's actions
            WriteToFile.create(name);
            Bag full;
            String message;
            String message2;
			
            // player draws 9 initial pebbles to its hand, each time drawing from the fullest of the three black bags 
            for (int i =0;i< 9;i++) {
                full = PebbleGame.fullestBag(bagX,bagY,bagZ);
                takeInitialPebble(full);
		// after each draw, the player's file is updated to display the pebble that has been drawn
                message = "player"+name+" has drawn a "+this.pebbles.getLast().getWeight()+" from bag"+full.getName();
                WriteToFile.write(name,message);
		// information also displayed to terminal window
                System.out.println(message);
            }
			
            // once the player has drawn all initial pebbles, its file is updated to display its initial hand
            message2 = "player"+name+" hand is "+this.pebbles.get(0).getWeight()+", "+this.pebbles.get(1).getWeight()+", "+this.pebbles.get(2).getWeight()
                       +", "+this.pebbles.get(3).getWeight()+", "+this.pebbles.get(4).getWeight()+", "+this.pebbles.get(5).getWeight()+", "+
                       this.pebbles.get(6).getWeight()+", "+this.pebbles.get(7).getWeight()+", "+this.pebbles.get(8).getWeight()+", "+this.pebbles.get(9).getWeight();
            WriteToFile.write(name,message2);
            // also displayed to terminal window
            System.out.println(message2);

            // the player will repeatedly draw and discard pebbles until
            // it has 10 pebbles in its hand such that the total weight
            // of all 10 is equal to 100
            while (!PebbleGame.done) {
                // before drawing a pebble, the black bags are checked
		// to make sure that they are not empty
		// if they are empty, the contents of their corresponding
		// white bags are transferred into the black bags
                if (bagX.sizeOfBag() == 0 && bagA.sizeOfBag() != 0){
                    bagX.transferPebbles(bagA);
                }
                if (bagY.sizeOfBag() == 0 && bagB.sizeOfBag() != 0){
                    bagY.transferPebbles(bagB);
                }
                if (bagZ.sizeOfBag() == 0 && bagC.sizeOfBag() != 0){
                    bagZ.transferPebbles(bagC);
                }

                takePebble();

		// if the player's pebbles total 100, the player has won
                if (this.getTotalValue() == 100) {
                    // the state of PebbleGame is updated so that all other players stop playing
                    PebbleGame.done = true;
                    // final writing is done to each player's file
                    endCredits(this.getPlayerName());
                }

		// if the player has not won, it discards a pebble
                discardPebble();
            }

        }

	/**
	* Method that does the final file writing when a player has won the game.
	* <p>
	* Method is synchronized so that the first player to win the game will be
	* the player that is declared as the winner.
	*
	* @param    winner  the player who has won the game
	*/
        private synchronized void endCredits(int winner){
            String message;
			
            // winner's file is updated with the fact that they have won
            if (this.getTotalValue() == 100) {
                PebbleGame.done = true;
                message = "player"+winner+" has won";
                WriteToFile.write(winner,message);
                System.out.println(message);

            }
            // losers' files are updated with the fact that they have lost and that the winner has won
            for (int j=1;j<=PebbleGame.playersNr.get(); j++) {
                message = "player"+j+" has lost, as player"+winner+" has won.";
                if (j != winner) {
                    WriteToFile.write(j,message);
                    System.out.println(message);					
                }

            }
        }
    }

    private static Bag fullest;
    // the bags that will be used in the game are created
    private static final Bag bagA = new Bag("A");
    private static final Bag bagB = new Bag("B");
    private static final Bag bagC = new Bag("C");

    private static final Bag bagX = new Bag("X");
    private static final Bag bagY = new Bag("Y");
    private static final Bag bagZ = new Bag("Z");

    // done set to false as no player has won the game
    private static boolean done = false;
    private static AtomicInteger playersNr;
	
    /**
    * Main method for PebbleGame class.
    *
    * @throws NotEnoughPebblesException
    * @throws InvalidPlayersNumberException
    * @param args[]
    */
    public static void main(String args[]) throws NotEnoughPebblesException, InvalidPlayersNumberException {
	
	//fill the black bags with pebbles
        fillBag(bagX,args[0]);
        fillBag(bagY,args[1]);
        fillBag(bagZ,args[2]);
	// checks how many players will be playing the game
        playersNr = new AtomicInteger(Integer.parseInt(args[3]));
	// checks that a valid number of players are playing
        checkPlayersNumber(playersNr.get());
	// checks that there are enough pebbles in the game
        checkEnoughPebbles(bagX, bagY,bagZ, playersNr.get());

	// create a new thread for each player
        for (int i = 1; i <= playersNr.get(); i++) {
            new Thread(new Player(i)).start();
        }

	// players' files are closed as no more writing will occur
        WriteToFile.closing(playersNr.get());
    }

    /**
    * Method to fill a black bag.
    * <p>
    * The bag is filled with pebbles with weights corresponding
    * to the values contained within the specified file.
    *
    * @param    bag the bag to be filled
    * @param    fileName    the file from which to obtain the pebble weight values
    */
    private static void fillBag(Bag bag, String fileName) {

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader in = new BufferedReader(fileReader);
            // reads in the text contained within the specified file
            String values1 = in.readLine();
            // as the format of the file should be comma separated values
            // we can create an array of the individual values in the file
            String[] xWeights = values1.split(",");
            // the values are read in String form, so we parse the values into integers
            for (String xWeight : xWeights) {
                int weight;
                weight = Integer.parseInt(xWeight);
		// weight values must be positive, so the game breaks if there is an invalid value
                if (weight <= 0) {
                    System.out.println("Weight value is smaller than 0.");
                    break;
                }
		// a new pebble is created for each value read in from the file
                Pebble bagFiller = new Pebble(weight);
		// the bag to which the pebble is added is set
                bagFiller.setParentBag(bag);
		// pebble is added to the bag
                bag.addPebble(bagFiller);
            }

        }
        catch (FileNotFoundException e) {
            System.out.println("File not found for Bag: "+bag.getName());

        }
        catch (IOException e) {
            System.out.println("IO Exception thrown.");

        }
    }

    /**
    * Method to check that there are enough pebbles in the game.
    *
    * @param bagx	bag X
    * @param bagy	bag Y
    * @param bagz	bag Z
    * @throws NotEnoughPebblesException
    */
    private static void checkEnoughPebbles(Bag bagx, Bag bagy, Bag bagz, int players) throws NotEnoughPebblesException {
        // counts the total number of pebbles across the 3 black bags
        int counter;
        counter = bagx.sizeOfBag()+bagy.sizeOfBag()+bagz.sizeOfBag();
        // the total should be at least 1 more than the total number of players times 9
        if (counter < ((players*9)+1)) {
            // if there are not enough pebbles then we throw an exception
            throw new NotEnoughPebblesException();
        }
    }

    /**
    * Method to check that there is a valid number of players in the game.
    *
    * @param players	number of players in the game
    * @throws InvalidPlayersNumberException
    */
    private static void checkPlayersNumber(int players) throws InvalidPlayersNumberException {
	// there should be at least 2 players in the game
        if (players < 2)
            throw new InvalidPlayersNumberException();

    }

    /**
    * Method to return the bag containing the most pebbles at a given time.
    *
    * @param bagx	bag X
    * @param bagy	bag Y
    * @param bagz	bag Z
    * @return fullest	returns the fullest bag
    */
    private static Bag fullestBag(Bag bagx, Bag bagy, Bag bagz) {

        int max = 0;
	// checks each bag in turn and updates the fullest variable if the bag contains more pebbles than the last
        if (max < bagx.sizeOfBag()) {
            fullest = bagx;
            max = bagx.sizeOfBag();
        }
        if (max < bagy.sizeOfBag()) {
            fullest = bagy;
            max = bagy.sizeOfBag();
        }
        if (max < bagz.sizeOfBag()) {
            fullest = bagz;
        }
        return fullest;
    }
}