package as3.gameui;

import as3.gamelogic.GameLogic;
import as3.gamelogic.Tank;
import as3.gamelogic.TankBlock;

import java.util.Scanner;
/*
 * GameUi.java
 * Class Description: a visual interface for
 *                    the user to interact with
 * Last modified on: February 21, 2018
 * Author: Bowen He
 * Student ID : 301174759
 * Email : bhhe@sfu.ca
 */

public class GameUi {

    //Final variables
    private final int MIN_SIZE = 2;
    private final int MAX_SIZE = 3;
    private final int INPUT_SIZE = 2;
    private final int xValIndex = 0;
    private final int yValIndex = 1;
    private final int ROW_MIN = 0;
    private final int ROW_MAX = 9;
    private final int COL_MIN = 1;
    private final int COL_MAX = 10;

    private Scanner scanner = new Scanner(System.in);
    private int boardSize = 0;
    private GameLogic gameLogic;
    private int[] inputtedCoordinates = new int[2];

    public GameUi(GameLogic gamelogic){
        this.gameLogic = gamelogic;
        this.boardSize = gamelogic.getBoardSize();
    }

    /*****************
     Display Methods
     *****************/

    public void displayUI(){

        //output cheatMap if cheats are on
        if(gameLogic.isCheatsOn()){
            displayCheatMap();
            displayAttackResult(true);
        }

        boolean continueGame = gameLogic.continueCondition();
        displayIntro();

        //continue running the game until continue condition is false
        while(continueGame) {
            displayPlayMap();
            displayAttackResult(false);
            gameLogic.takeDamage();
            continueGame = gameLogic.continueCondition();
        }

        //Reveal maps for the final time and output results
        displayPlayMap();
        if(gameLogic.winCondition()){
            System.out.println("Congratulations! You won!\n");
        }else{
            System.out.println("I'm sorry, your fortress has been smashed\n");
        }
        displayCheatMap();
    }

    public void displayPlayMap(){
        char[][] playMap = gameLogic.getPlayMap();
        displayColNums();
        for(int x = 0; x < boardSize; x++){
            System.out.print("    " + (char)('A' + x) + " ");
            for(int y = 0; y < boardSize; y++){
                System.out.print(" " + playMap[x][y] + " ");
            }
            System.out.println();
        }
    }

    public void displayIntro(){
        String introduction = "Starting game with " + gameLogic.getAmountOfTanks() + " tanks.";
        String formatLine = "";
        for(int i = 0; i < introduction.length(); i++){
            formatLine += "-";
        }
        System.out.println(introduction);
        System.out.println(formatLine);
        System.out.println("Welcome to Fortress Defense!");
        System.out.println("by Bowen He");
        System.out.println(formatLine + "\n\n");
    }

    public void displayAttackResult(boolean isIntro){
        System.out.println("Fortress Structure Left: " + gameLogic.getFortressHealth());

        //Only output for the intro
        if(isIntro){
            System.out.println("(Lower case tank letters are where you shot.)\n\n");
        }else{

            //for the remainder of the game, ask for user input to attack a spot
            inputtedCoordinates = getUserInput();
            boolean attackSuccess = gameLogic.attackSpot(inputtedCoordinates);

            if(attackSuccess){
                System.out.println("HIT!");
            }else{
                System.out.println("Miss.");
            }
            displayTankAttacks();
        }
    }

    public void displayTankAttacks(){
        String tankNumInfo = " of " + gameLogic.getAmountOfTanks()
                            + " shot you for ";
        for(Tank tank: gameLogic.getTanks()){
            if(tank.isAlive()){
                System.out.println("Alive tank #" + (tank.getTankNum() + 1)
                                    + tankNumInfo + tank.getDamage() + "!");
            }
        }
        System.out.println();
    }

    //Prints out the contents of the map containing
    //all tankBlocks in appropriate format
    public void displayCheatMap(){
        TankBlock[][] cheatMap = gameLogic.getCheatMap();
        displayColNums();

        //output every tankblock's tanknum in Letter form
        //that's stored inside the cheatMap in x-y coord order
        for(int x = 0; x < boardSize; x++){

            //Output Column of letters
            System.out.print("    " + (char)('A' + x) + " ");

            //check all elements inside the board
            for(int y = 0; y < boardSize; y++){
                TankBlock outBlock = cheatMap[x][y];

                if(outBlock.isEmptyBlock()) {

                    //print if is empty block based on hit/miss
                    if(outBlock.isHit()) {
                        System.out.print("   ");
                    }else{
                        System.out.print(" . ");
                    }
                }else{

                    //if it's a tankBlock then print on based hit/miss
                    if(outBlock.isHit()) {
                        System.out.print(" " + (char)('a' + cheatMap[x][y].getTankNum()) + " ");
                    }else{
                        System.out.print(" " + (char)('A' + cheatMap[x][y].getTankNum()) + " ");
                    }
                }
            }
            System.out.println();
        }
    }

    public void displayColNums(){
        System.out.println("Game Board:");
        System.out.println("       1  2  3  4  5  6  7  8  9 10");
    }

    /****************
        Input Methods
    *****************/

    //Returns an array of the coordinate that the user entered after
    //validating for appropriate inputs
    public int[] getUserInput(){
        int [] input = new int[INPUT_SIZE];
        boolean validInput = false;
        String inputInStr = "";
        while(!validInput){
            System.out.print("Enter your move: ");
            inputInStr = scanner.nextLine();

            //do not move on until an appropriate input is provided
            //before inserting into array to return
            if(validateInput(inputInStr)){
                inputInStr = inputInStr.toUpperCase();
                input[xValIndex] = (inputInStr.charAt(xValIndex) - 'A');
                input[yValIndex] = Integer.parseInt(inputInStr.substring(yValIndex)) - 1;
                validInput = true;
            }else{
                System.out.println("Invalid target. Please enter a coordinate such as D10.");
            }
        }
        return input;
    }

    public boolean validateInput(String input){
        String inputUppercase = input.toUpperCase();
        int inputSize = input.length();

        //check input is appropriate size
        if(inputSize < MIN_SIZE || inputSize > MAX_SIZE){
            return false;
        }

        //after input size is appropriate then slot into
        //char array and verify the 2nd half of the input
        //are all digits
        char[] inputToChars = inputUppercase.toCharArray();
        for(int i = 1; i < inputToChars.length;i++){
            if(!Character.isDigit(inputToChars[i])){
                return false;
            }
        }

        //After passing previous input validations,
        //test if the inputted values are within desired range
        int xInput = (inputToChars[xValIndex] - 'A');
        int yInput = Integer.parseInt(inputUppercase.substring(yValIndex));
        if(xInput < ROW_MIN || xInput > ROW_MAX){
            return false;
        }
        if(yInput < COL_MIN || yInput > COL_MAX){
            return false;
        }
        return true;
    }
}

