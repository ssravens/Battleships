package battleships;

/**
 *
 * @author Alex
 */
import java.util.Observable;
import java.util.Observer;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

// Graphical view for the battleship
public class BattleshipGraphicalView extends Application implements Observer {

    // reference to view as needed by controller
    public static BattleshipGraphicalView view;

    // controller and model reference
    private Controller controller;
    private BattleshipModel model;
    private Cell[][] cells;

    private Scene scene;
    private GridPane pane;


    // create batleship
    public BattleshipGraphicalView() {
        view = this;
        controller = new Controller();

    }

    // set controller and model reference for battleship
    public void setControllerAndModel(Controller controller, BattleshipModel model) {
        this.controller = controller;
        this.model = model;

        // create grid pane for cells
        pane = new GridPane();
        pane.setHgap(0);
        pane.setVgap(0);

        // create cells and add
        cells = new Cell[model.getSize()][model.getSize()];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                cells[i][j] = new Cell(i, j);

            }

            pane.addRow(i, cells[i]);
        }


        // create scene
        scene = new Scene(pane);
        scene.setRoot(pane);


    }


    // simply show the battleship frame
    @Override
    public void start(Stage primaryStage) throws Exception {


        primaryStage.setScene(scene);
        primaryStage.sizeToScene();
        primaryStage.setTitle("Battleship");
        primaryStage.show();

    }


    // update received from model
    @Override
    public void update(Observable o, Object update) {

        // update each cell state
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                cells[i][j].update();
            }
        }
    }


    // show a message to user
    public void showMessage(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // display a cell
    public void disable(int row, int col) {
        cells[row][col].setDisable(true);
    }

    // disable all cells
    public void disableAll() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                cells[i][j].setDisable(true);
            }
        }
    }



    private class Cell extends Button {
        private int row, col;

        public Cell(int row, int col) {
            this.row = row;
            this.col = col;
            setPrefSize(40, 40);
            setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    controller.hit(row, col);
                }
            });
        }


        public void update() {
            ElementStatus status = model.getStatus(row, col);
            if(status == ElementStatus.HIDDEN)
                setText("");
            else if(status == ElementStatus.MISS)
                setText("M");
            else
                setText("H");
        }




    }

    public static void main(String[] args) {
        launch(args);
    }


}
