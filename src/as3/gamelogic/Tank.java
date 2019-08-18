package as3.gamelogic;
import java.util.*;
import java.lang.Math;
/*
 * Tank.java
 * Class Description: Tank object stores
 *                    tetramino blocks
 * Last modified on: February 21, 2018
 * Author: Bowen He
 * Student ID : 301174759
 * Email : bhhe@sfu.ca
 */

public class Tank {
    private ArrayList<TankBlock> tetraminoList = new ArrayList<TankBlock>();
    private int tankNum;
    private boolean aliveStatus = true;
    private int blocksDestroyed = 0;

    /*****************
     Constructor
     *****************/

    public Tank(int tankNum, ArrayList<TankBlock> tetraminoList) {
        this.tankNum = tankNum;
        this.tetraminoList = tetraminoList;
    }

    /*****************
     Helper Methods
     *****************/

    public boolean isAlive(){
        return aliveStatus;
    }

    public void destroyBlock(int blockNum){
        if(!tetraminoList.get(blockNum).isHit()) {
            tetraminoList.get(blockNum).hit();
            blocksDestroyed++;
        }
        if(blocksDestroyed == tetraminoList.size()){
            aliveStatus = false;
        }
    }

    /*****************
     Getter Methods
     *****************/

    public int getDamage(){
        int dmg = 20;
        if(blocksDestroyed < tetraminoList.size() && blocksDestroyed > 0){
            dmg = (int)Math.ceil(20 / (4 * blocksDestroyed));
        }else if(blocksDestroyed == tetraminoList.size()){
            dmg = 0;
        }
        return dmg;
    }

    public int getTankNum(){
        return tankNum;
    }

}
