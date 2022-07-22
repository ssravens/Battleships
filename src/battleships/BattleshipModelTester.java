package battleships;

/**
 *
 * @author Alex
 */
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


// Junit Tests for the Model
public class BattleshipModelTester {


    // Test if hit methods returns correctly when ship is sunk and when ship is not sunk
    @Test
    public void testBattleshipModel1() {
        BattleshipModel model = new BattleshipModel();

//		     A B C D E F G H I J
//		   1 _ * _ _ _ _ _ _ * *
//		   2 _ * _ _ _ _ _ _ _ _
//		   3 _ * _ * * * _ _ _ _
//		   4 _ * _ _ _ _ _ _ _ _
//		   5 _ * _ _ _ _ _ _ _ _
//		   6 _ _ _ _ _ _ _ _ _ _
//		   7 _ _ _ _ _ * _ _ _ _
//		   8 _ _ _ _ _ * _ _ _ _
//		   9 _ _ _ _ _ * _ _ _ _
//		  10 * * _ _ _ * _ _ _ _


        // sink ship
        assertFalse(hit(model, "B1"));
        assertFalse(hit(model, "B2"));
        assertFalse(hit(model, "B3"));
        assertFalse(hit(model, "B4"));
        assertTrue(hit(model, "B5")); // ship sunk

        assertFalse(hit(model, "I1"));
        assertTrue(hit(model, "J1")); // ship sunk

    }


    // Test if the model check for gane over method works
    @Test
    public void testBattleshipModel2() {
        BattleshipModel model = new BattleshipModel();

//		     A B C D E F G H I J
//		   1 _ * _ _ _ _ _ _ * *
//		   2 _ * _ _ _ _ _ _ _ _
//		   3 _ * _ * * * _ _ _ _
//		   4 _ * _ _ _ _ _ _ _ _
//		   5 _ * _ _ _ _ _ _ _ _
//		   6 _ _ _ _ _ _ _ _ _ _
//		   7 _ _ _ _ _ * _ _ _ _
//		   8 _ _ _ _ _ * _ _ _ _
//		   9 _ _ _ _ _ * _ _ _ _
//		  10 * * _ _ _ * _ _ _ _


        // sink a ship
        for (int i = 1; i <= 5; i++) {
            hit(model, "B" + i);
        }

        // game not aver
        assertFalse(model.isGameOver());


        // sink another ship
        hit(model, "I1");
        hit(model, "J1");

        assertFalse(model.isGameOver());

        // sink another ship
        hit(model, "D3");
        hit(model, "E3");
        hit(model, "F3");

        assertFalse(model.isGameOver());

        // sink another ship
        hit(model, "A10");
        hit(model, "B10");

        assertFalse(model.isGameOver());

        // sink last ship
        for (int i = 7; i <= 10; i++) {
            hit(model, "F" + i);
        }


        // now ship has sunk
        assertTrue(model.isGameOver());

    }


    // method to test if the battle ship model correctly tracks number of tries
    @Test
    public void testBattleshipModel3() {
        BattleshipModel model = new BattleshipModel();

//		     A B C D E F G H I J
//		   1 _ * _ _ _ _ _ _ * *
//		   2 _ * _ _ _ _ _ _ _ _
//		   3 _ * _ * * * _ _ _ _
//		   4 _ * _ _ _ _ _ _ _ _
//		   5 _ * _ _ _ _ _ _ _ _
//		   6 _ _ _ _ _ _ _ _ _ _
//		   7 _ _ _ _ _ * _ _ _ _
//		   8 _ _ _ _ _ * _ _ _ _
//		   9 _ _ _ _ _ * _ _ _ _
//		  10 * * _ _ _ * _ _ _ _


        // expected tries
        int tries = 0;

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                // hit a position
                model.hit(i, j);
                // update tries
                tries++;

                // assert total tries are correct
                assertEquals(tries, model.getTries());
            }
        }

    }


    // utility method to hit a position specified as column name and row number in a string (e.g A10)
    public static boolean hit(BattleshipModel model, String position) {
        int col = position.charAt(0) - 'A';
        int row = Integer.parseInt(position.substring(1)) - 1;
        return model.hit(row, col);
    }


    // utility method display the board state
    // if displayHidden is true, the model displays the ships that have not even discovered marked by *
    public static void displayBoard(BattleshipModel model, boolean displayHidden) {

        // display columns headers
        System.out.print("  ");
        for (int i = 0; i < model.getSize(); i++) {
            char c = (char) ('A' + i);
            System.out.print(" " + c);
        }
        System.out.println();


        // display model
        for (int i = 0; i < model.getSize(); i++) {
            System.out.printf("%2d", i + 1); // row number

            //display ships
            for (int j = 0; j < model.getSize(); j++) {
                ElementStatus status = model.getStatus(i, j);
                if (status == ElementStatus.HIT) // hit
                    System.out.print(" H");
                else if (status == ElementStatus.MISS) // miss
                    System.out.print(" M");
                else if (model.hasShip(i, j)) // has ship
                    System.out.print(" *");
                else // empty
                    System.out.print(" _");

            }

            System.out.println();
        }

    }

}

