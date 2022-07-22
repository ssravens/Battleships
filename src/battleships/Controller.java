package battleships;

/**
 *
 * @author Alex
 */
// Controller class
public class Controller {

    // model and view reference
    private BattleshipModel model;
    private BattleshipGraphicalView view;

    public Controller() {

        // set model and view
        model = new BattleshipModel();
        view = BattleshipGraphicalView.view;

        // inform view about controller and model
        view.setControllerAndModel(this, model);
        model.addObserver(view);

    }


    // hit row col position
    public void hit(int row, int col) {
        if (model.canHit(row, col)) { // if valid position
            // forward to model
            boolean shipSunk = model.hit(row, col);
            view.disable(row, col);

            if (shipSunk) // if ship was sunk
                view.showMessage("A ship has sunk");

            // if game is over
            if (model.isGameOver()) {
                view.showMessage("Game over. Total tries = " + model.getTries());

            }

        } else { // invalid position
            view.showMessage("Cannot hit this location");
        }

    }

}
