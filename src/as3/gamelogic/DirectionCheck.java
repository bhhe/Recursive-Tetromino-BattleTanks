package as3.gamelogic;

import java.util.ArrayList;
import java.util.Collections;
/*
 * DirectionCheck.java
 * Class Description: purpose to recursively check for available
 *                    spots to place new tank blocks in Map
 * Last modified on: February 21, 2018
 * Author: Bowen He
 * Student ID : 301174759
 * Email : bhhe@sfu.ca
 */

public class DirectionCheck {

    //Directional arrows on NumPad
    //7 8 9       8
    //4 5 6  => 4   6
    //1 2 3       2
    private final int DOWN = 2;
    private final int LEFT = 4;
    private final int RIGHT = 6;
    private final int UP = 8;
    private final int FAILURE = -1;

    private boolean spotFound;
    private ArrayList<Integer> directionList = new ArrayList<Integer>();

    /*****************
     Constructor
     *****************/
    public DirectionCheck(){
        spotFound = false;
        directionList.add(UP);
        directionList.add(DOWN);
        directionList.add(LEFT);
        directionList.add(RIGHT);
    }

    /*****************
     Helper Methods
     *****************/

    public void directionalSearch(int[] currentPosition, ArrayList<TankBlock> tankBlocks, int tankSize, TankBlock[][] cheatMap){
        spotFound = false;
        Collections.shuffle(directionList);     //shuffle the directions
        int[] checkPosition = currentPosition;

        //check until tetramino meets size requirement
        while(tankBlocks.size() != tankSize) {
            int dListIndex = 0;

            //check all directions of a position on map or until spot is found
            while(dListIndex <  directionList.size() && !spotFound){

                //update check position
                checkPosition = checkDirection(directionList.get(dListIndex),currentPosition,tankBlocks,cheatMap);
                dListIndex++;
            }

            if(spotFound) {

                //recursively check new position for new add
                directionalSearch(checkPosition, tankBlocks, tankSize,cheatMap);

            }else if(dListIndex == directionList.size() && !spotFound){
                //all directions checked and no possible positions found then exit
                //program due to failure to generate tank on map

                System.out.println("Tank " + (char)('A' +tankBlocks.get(0).getTankNum())
                                    + " Failed to be Created. Exiting Program!");
                System.exit(FAILURE);
            }
        }
    }

    //check different directions based on direction parameter and potentially
    //and return the newly added position
    private int[] checkDirection(int direction,int[] currentPosition,ArrayList<TankBlock> tankBlocks, TankBlock[][] cheatMap){
        int[] newPosition = currentPosition;
        if(direction == UP){
            newPosition = checkUp(currentPosition,tankBlocks,cheatMap);
        }else if(direction == DOWN){
            newPosition = checkDown(currentPosition,tankBlocks,cheatMap);
        }else if(direction == LEFT){
            newPosition = checkLeft(currentPosition,tankBlocks,cheatMap);
        }else if(direction == RIGHT){
            newPosition = checkRight(currentPosition,tankBlocks,cheatMap);
        }
        return newPosition;
    }

    /*
        UpDownLeftRight Methods
        All add if a slot is available
     */
    private int[] checkUp(int[] currentPosition,ArrayList<TankBlock> tankBlocks, TankBlock[][] cheatMap){
        //Create local variables to modify/use
        int checkX = currentPosition[0];
        int currentY = currentPosition[1];
        int tankNum = tankBlocks.get(0).getTankNum();
        int blockNum = tankBlocks.size();
        if(checkX > 0){
            checkX--;
            allocateBlockCheck(checkX,currentY,tankNum,blockNum,tankBlocks,cheatMap);
        }
        int[] newPosition = {checkX,currentY};
        return newPosition;
    }

    private int[] checkDown(int[] currentPosition,ArrayList<TankBlock> tankBlocks, TankBlock[][] cheatMap){
        //Create local variables to modify/use
        int checkX = currentPosition[0];
        int currentY = currentPosition[1];
        int tankNum = tankBlocks.get(0).getTankNum();
        int blockNum = tankBlocks.size();
        if(checkX < 9){
            checkX++;
            allocateBlockCheck(checkX,currentY,tankNum,blockNum,tankBlocks,cheatMap);
        }
        int[] newPosition = {checkX,currentY};
        return newPosition;
    }

    private int[] checkLeft(int[] currentPosition,ArrayList<TankBlock> tankBlocks, TankBlock[][] cheatMap){
        //Create local variables to modify/use
        int currentX = currentPosition[0];
        int checkY = currentPosition[1];
        int tankNum = tankBlocks.get(0).getTankNum();
        int blockNum = tankBlocks.size();
        if(checkY > 0){
            checkY--;
            allocateBlockCheck(currentX,checkY,tankNum,blockNum,tankBlocks,cheatMap);
        }
        int[] newPosition = {currentX,checkY};
        return newPosition;
    }

    private int[] checkRight(int[] currentPosition,ArrayList<TankBlock> tankBlocks, TankBlock[][] cheatMap){
        //Create local variables to modify/use
        int currentX = currentPosition[0];
        int checkY = currentPosition[1];
        int tankNum = tankBlocks.get(0).getTankNum();
        int blockNum = tankBlocks.size();
        if(checkY < 9){
            checkY++;
            allocateBlockCheck(currentX,checkY,tankNum,blockNum,tankBlocks,cheatMap);
        }
        int[] newPosition = {currentX,checkY};
        return newPosition;
    }

    //check that block is empty and allocate
    //desired tank num and the block num
    private void allocateBlockCheck(int xCoord, int yCoord, int tankNum, int blockNum, ArrayList<TankBlock> tankBlocks, TankBlock[][] cheatMap){
        if(cheatMap[xCoord][yCoord].isEmptyBlock()){
            spotFound = true;
            cheatMap[xCoord][yCoord].setTankNum(tankNum);
            cheatMap[xCoord][yCoord].setBlockNum(blockNum);
            tankBlocks.add(cheatMap[xCoord][yCoord]);
        }
    }
}
