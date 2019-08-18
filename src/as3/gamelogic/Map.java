package as3.gamelogic;
import java.util.*;
/*
 * Map.java
 * Class Description: Map containing a board
 *                    of the tank' tetraminos
 *                    and a board for verifying
 *                    player inputs
 * Last modified on: February 21, 2018
 * Author: Bowen He
 * Student ID : 301174759
 * Email : bhhe@sfu.ca
 */

public class Map {
    //Final Comparative Values
    private final int BOARD_SIZE = 10;
    private final int TANK_SIZE = 4;
    private final int EMPTY_VAL = -1;
    private final char FOG_MARKER = '~';
    private final char HIT_MARKER = 'X';
    private final char MISS_MARKER = ' ';

    //Map and direction Storage
    private TankBlock[][] cheatMap;
    private char[][] playMap;
    private int blockNum = 0;

    /*****************
     Constructor Methods
     *****************/

    public Map(){
        cheatMap = new TankBlock[BOARD_SIZE][BOARD_SIZE];
        playMap = new char[BOARD_SIZE][BOARD_SIZE];
        initializeEmptyMaps();
    }

    /*****************
     Helper Methods
     *****************/

    //Fill cheat map with empty blocks
    //and play map with fog markers
    private void initializeEmptyMaps(){
        for(int x = 0; x < BOARD_SIZE; x++){
            for(int y = 0; y < BOARD_SIZE; y++){
                int[] currentCoordinates = {x,y};
                TankBlock emptyBlock = new TankBlock(currentCoordinates, EMPTY_VAL);
                cheatMap[x][y] = emptyBlock;
                playMap[x][y] = FOG_MARKER;
            }
        }
    }

    public ArrayList<TankBlock> generateTankPositions(int tankNum){
        blockNum = 0;
        ArrayList<TankBlock> tankBlocks = new ArrayList<TankBlock>();
        TankBlock startingBlock = generateInitialBlock(tankNum,blockNum);
        tankBlocks.add(startingBlock);
        int[] currentPosition = startingBlock.getCoordinates();
        DirectionCheck addTankBlocks = new DirectionCheck();

        //check available spots for a tetramino and add to tankBlocks til size appropriate
        //or System.Exit if failed to generate tank tetramino
        addTankBlocks.directionalSearch(currentPosition,tankBlocks,TANK_SIZE,cheatMap);

        return tankBlocks;
    }

    private TankBlock generateInitialBlock(int tankNum, int blockNum){
        int randomX = generateRandomCoordinate();
        int randomY = generateRandomCoordinate();

        //keep searching for valid initial position
        while(!checkValidSpot(randomX,randomY)){
            randomX = generateRandomCoordinate();
            randomY = generateRandomCoordinate();
        }

        cheatMap[randomX][randomY].setTankNum(tankNum);
        cheatMap[randomX][randomY].setBlockNum(blockNum);
        TankBlock startingBlock = cheatMap[randomX][randomY];

        return startingBlock;
    }

    private boolean checkValidSpot(int xCoord, int yCoord){
        if(cheatMap[xCoord][yCoord].isEmptyBlock()){
            return true;
        }
        return false;
    }

    public boolean hitAbleCheck(int[] coordinates){
        int checkX = coordinates[0];
        int checkY = coordinates[1];
        if(!checkValidSpot(checkX,checkY)){
            return true;
        }
        return false;
    }

    private int generateRandomCoordinate(){
        return  (int) (Math.random() * BOARD_SIZE);
    }

    /*****************
     Setter Methods
     *****************/

    public void setHitPlayMap(int[] coordinates, boolean hit){
        if(hit) {
            playMap[coordinates[0]][coordinates[1]] = HIT_MARKER;
        }else{
            playMap[coordinates[0]][coordinates[1]] = MISS_MARKER;
        }
        cheatMap[coordinates[0]][coordinates[1]].hit();
    }

    /*****************
     Getter Methods
     *****************/

    public TankBlock getTankBlock(int[] coordinates){
        int xCoord = coordinates[0];
        int yCoord = coordinates[1];
        return cheatMap[xCoord][yCoord];
    }

    public TankBlock[][] getCheatMap(){
        return cheatMap;
    }

    public int getBoardsize(){
        return BOARD_SIZE;
    }

    public char[][] getPlayMap(){
        return playMap;
    }
}
