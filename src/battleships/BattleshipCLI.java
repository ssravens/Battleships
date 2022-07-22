package battleships;

/**
 *
 * @author Alex
 */
import java.io.FileNotFoundException;
import java.util.Scanner;

// Command line interface for battleship
public class BattleshipCLI {

    // represents a model
    private static BattleshipModel model;

    // open scanner to read from user
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {

            // initialize model
            initializeModel();

            // display board
            displayBoard();


            // while game not over
            while(!model.isGameOver()) {
                // get next hit positon
                System.out.print("Enter next position to hit (e.g. B7): ");
                String position = scanner.nextLine();
                hit(position); // hit

                System.out.println();
                displayBoard();
            }


            // display tries after game is over
            System.out.println("Game over. Total tries = " + model.getTries());


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error: " + e.getMessage());
        }

    }



    // intialize model
    public static void initializeModel() throws FileNotFoundException {
        // ask if user wants to use the file to create a board
        System.out.print("Enter file name to create board (0 for automatic board): ");
        String file = scanner.nextLine();

        if(file.equals("0")) // default
            model = new BattleshipModel();
        else // using file
            model = new BattleshipModel(file);
    }



    // display board
    public static void displayBoard() {

        // display column headers
        System.out.print("  ");
        for (int i = 0; i < model.getSize(); i++) {
            char c = (char) ('A' + i);
            System.out.print(" " + c);
        }
        System.out.println();


        // display board
        for (int i = 0; i < model.getSize(); i++) {
            System.out.printf("%2d",i + 1);
            for (int j = 0; j < model.getSize(); j++) {
                ElementStatus status = model.getStatus(i, j);
                if(status == ElementStatus.HIDDEN) // hidden
                    System.out.print(" _");
                else if(status == ElementStatus.HIT) // hit
                    System.out.print(" H");
                else // miss
                    System.out.print(" M");

            }

            System.out.println();
        }



    }



    // hit a specific position
    public static void hit(String position) {
        int col = position.charAt(0) - 'A';
        int row = Integer.parseInt(position.substring(1)) - 1;

        if(!model.canHit(row, col)) // if invalid
            System.out.println("Cannot hit this position");
        else {
            // hit and check if ship is sunk
            boolean sunk = model.hit(row, col);
            if(sunk)
                System.out.println("*** A ship has sunk ***");

        }
    }




}

