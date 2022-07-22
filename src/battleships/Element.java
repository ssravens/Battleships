package battleships;

/**
 *
 * @author Alex
 */
// A battleship model is composed of 2D array of Element
public class Element {

    // current status of element
    private ElementStatus status;
    private boolean ship; // whether element has ship or not


    // create empty element
    public Element() {
        status = ElementStatus.HIDDEN;
    }

    // create element with ship
    public Element(boolean ship) {
        this.ship = ship;
        status = ElementStatus.HIDDEN;
    }


    // set element ship status
    public void setShip(boolean ship) {
        this.ship = ship;
    }


    // check if element has ship
    public boolean hasShip() {
        return ship;
    }


    // get element status
    public ElementStatus getStatus() {
        return status;
    }

    // heck if element has already been hit
    public boolean isGuessed() {
        return status != ElementStatus.HIDDEN;
    }


    // fire on this element
    public void fire() {
        if(ship)
            status = ElementStatus.HIT;
        else
            status = ElementStatus.MISS;
    }



}

