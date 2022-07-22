package battleships;

/**
 *
 * @author Alex
 */
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

// Battleship model
public class BattleshipModel extends Observable {

    // size of board and total ships
    public static final int SIZE = 10, SHIPS = 5;

    // board
    private Element[][] elements;
    private Ship[] ships; // ships
    private int tries; // total tries
    private int shipsSunk; // total ships already sunk

    // create a hardcoded baord
    public BattleshipModel() {

        initialize();

        // place ships
        initializeShip(ShipOrientation.VERTICAL, 5, 0, 1, 1);
        initializeShip(ShipOrientation.VERTICAL, 4, 6, 5, 2);
        initializeShip(ShipOrientation.HORIZONTAL, 3, 2, 3, 3);
        initializeShip(ShipOrientation.HORIZONTAL, 2, 9, 0, 4);
        initializeShip(ShipOrientation.HORIZONTAL, 2, 0, 8, 5);

    }

    // create model from file
    public BattleshipModel(String file) throws FileNotFoundException {
        initialize();
        loadFromFile(file);
    }

    // helper method to initialize the board to empty status
    private void initialize() {
        elements = new Element[SIZE][SIZE];
        for (int i = 0; i < elements.length; i++) {
            for (int j = 0; j < elements.length; j++) {
                elements[i][j] = new Element(false);
            }
        }

        ships = new Ship[5];
    }

    // helper method to place ship on baord
    // Precondition: the ship limit is not reached and ship doesnt intersect other
    // ships
    // Postcondition: the ship is placed ont he board
    private void initializeShip(ShipOrientation orientation, int length, int row, int col, int count) {
        assert count <= SHIPS;

        if (orientation == null) // invalid orientation
            throw new IllegalArgumentException();

        // create ship
        Ship ship = new Ship(row, col, length, orientation);
        ships[count - 1] = ship; // add ship to array

        if (orientation == ShipOrientation.VERTICAL) {
            for (int i = 0; i < length; i++) {
                // if already has ship
                if (elements[row + i][col].hasShip())
                    throw new IllegalArgumentException();

                elements[row + i][col].setShip(true);
            }
        } else { // horizontal
            for (int i = 0; i < length; i++) {
                // if already has ship
                if (elements[row][col + i].hasShip())
                    throw new IllegalArgumentException();

                elements[row][col + i].setShip(true);
            }
        }
    }

    // helper method to load ships from file
    private void loadFromFile(String file) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(file));
        try {

            // ships are in file in following sizes order
            int[] sizes = { 5, 4, 3, 2, 2 };

            // total ships so far
            int total = 0;

            // while there are ships to read
            while (scanner.hasNext()) {
                // read orientation, start row and col
                String orientation = scanner.next();
                int row = scanner.nextInt();
                int col = scanner.nextInt();

                // create ship
                total++;
                initializeShip(ShipOrientation.valueOf(orientation), sizes[total - 1], row, col, total);
            }

            // if more ships have been added
            if (total > SHIPS)
                throw new IllegalArgumentException();

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Invalid file format");
        } finally {
            scanner.close();
        }
    }

    // hit a given row col
    // Precondition: row, col are valid and position is not already hit
    // Postcondition: The position is hit, the status is updated
    // Retruns true if a new ship has sunk, else false
    public boolean hit(int row, int col) {
        assert canHit(row, col);

        tries++;
        // fire
        elements[row][col].fire();

        // notfy observers
        setChanged();
        notifyObservers();

        // check if ship has sunk
        for (int i = 0; i < ships.length; i++) {
            if (ships[i].belongs(row, col) && ships[i].hasSunk(elements)) {
                shipsSunk++;
                return true;
            }
        }

        // no new ship sunk
        return false;
    }

    // check if row, col is valid position and element is not guessed
    public boolean canHit(int row, int col) {
        return isValid(row, col) && !elements[row][col].isGuessed();
    }

    // check if the positon is valid
    private boolean isValid(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }

    // get size of board
    public int getSize() {
        return SIZE;
    }

    // get status of given row
    // Precondition: row, col is valid positon
    // Postcondiition: board is not changed
    public ElementStatus getStatus(int row, int col) {
        assert isValid(row, col);
        return elements[row][col].getStatus();
    }

    // check if given position has ship
    public boolean hasShip(int row, int col) {
        return elements[row][col].hasShip();
    }

    // get total tries
    public int getTries() {
        return tries;
    }

    // check if game is over
    public boolean isGameOver() {
        return shipsSunk == SHIPS;
    }

}

