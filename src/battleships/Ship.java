package battleships;

/**
 *
 * @author Alex
 */
// Represents a ship
public class Ship {

    // start row and col
    private int row;
    private int col;
    // ship length
    private int length;

    // horizontal or vertical
    private ShipOrientation orientation;


    // create ship
    public Ship(int row, int col, int length, ShipOrientation orientation) {
        this.row = row;
        this.col = col;
        this.length = length;
        this.orientation = orientation;
    }


    // Getters

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getLength() {
        return length;
    }

    public ShipOrientation getOrientation() {
        return orientation;
    }


    // check if a given cell belongs to this ship
    public boolean belongs(int row, int col) {
        if(orientation == ShipOrientation.HORIZONTAL) {
            return this.row == row && col >= this.col && col < this.col + length;
        } else {
            return this.col == col && row >= this.row && row < this.row + length;
        }
    }



    // check if ship has sunk
    public boolean hasSunk(Element[][] elements) {
        if(orientation == ShipOrientation.HORIZONTAL) { // horizontal
            for (int c = 0; c < length; c++) {
                if(elements[row][col + c].getStatus() != ElementStatus.HIT) // not hit
                    return false;
            }

            // all hit
            return true;
        }  else { // vertical
            for (int r = 0; r < length; r++) {
                if(elements[row + r][col].getStatus() != ElementStatus.HIT) // not hit
                    return false;
            }

            // all hit
            return true;
        }
    }

}

