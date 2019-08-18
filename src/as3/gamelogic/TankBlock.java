package as3.gamelogic;
/*
 * TankBlock.java
 * Class Description: Tetramino blocks of a tank
 * Last modified on: February 21, 2018
 * Author: Bowen He
 * Student ID : 301174759
 * Email : bhhe@sfu.ca
 */
public class TankBlock {
    private final int EMPTY_VAL = -1;
    private boolean destroyed = false;
    private int[] coordinates;
    private int tankNum;
    private int blockNum;

    /*****************
     Constructor
     *****************/
    public TankBlock(int [] coordinates, int tankNum){
        this.coordinates = coordinates;
        this.tankNum = tankNum;
    }

    /*****************
     Helper Methods
     *****************/
    public void hit(){
        this.destroyed = true;
    }

    public boolean isHit(){
        return destroyed;
    }

    public boolean isEmptyBlock(){
        if(tankNum == EMPTY_VAL){
            return true;
        }
        return false;
    }

    /*****************
     Setter Methods
     *****************/
    public void setTankNum(int tankNum){
        this.tankNum = tankNum;
    }

    public void setBlockNum(int blockNum){
        this.blockNum = blockNum;
    }

    /*****************
     Getter Methods
     *****************/
    public int getTankNum(){
        return tankNum;
    }

    public int getBlockNum(){
        return blockNum;
    }

    public int[] getCoordinates(){
        return coordinates;
    }
}
