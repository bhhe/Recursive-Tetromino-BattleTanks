package as3;
import as3.gamelogic.GameLogic;
import as3.gameui.GameUi;
/*
 * Main.java
 * Last modified on: February 21, 2018
 * Author: Bowen He
 */
public class Main {
    private static final int DEFAULT_TANK_AMOUNT = 5;

    public static void main(String[] args) {
        int numOfArgs = args.length;
        int numOfTanks = DEFAULT_TANK_AMOUNT;

        //initialize default logic
        GameLogic gameLogic = new GameLogic(DEFAULT_TANK_AMOUNT);

        //We're assuming correct values inputted in args
        if(numOfArgs == 1){
            numOfTanks = Integer.parseInt(args[0]);
            gameLogic = new GameLogic(numOfTanks);
        }else if(numOfArgs == 2){
            numOfTanks = Integer.parseInt(args[0]);
            String cheats = args[1];

            //check for cheat logic
            if(cheats.equalsIgnoreCase("--cheat")) {
                gameLogic = new GameLogic(numOfTanks, true);
            }else{
                gameLogic = new GameLogic(numOfTanks);
            }
        }

        //insert logic into user interface
        //and display it
        GameUi ui = new GameUi(gameLogic);
        ui.displayUI();
    }
}
