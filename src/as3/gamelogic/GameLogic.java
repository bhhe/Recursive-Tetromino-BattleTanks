package as3.gamelogic;

import java.util.ArrayList;
/*
 * GameLogic.java
 * Class Description: The logic and reasonings behind the
 *                    tank game
 * Last modified on: February 21, 2018
 * Author: Bowen He
 * Student ID : 301174759
 * Email : bhhe@sfu.ca
 */

public class GameLogic {
    private int fortressHealth;
    private boolean cheatsOn = false;
    private ArrayList<Tank> tanks = new ArrayList<Tank>();
    private int totalDamage = 0;
    private int amountOfTanks = 0;
    private Map gameMap;

    /*****************
        Constructors
     *****************/

    public GameLogic(int amountOfTanks){
        this.amountOfTanks = amountOfTanks;
        this.fortressHealth = 1500;
        initializeGame();
    }

    public GameLogic(int amountOfTanks,boolean cheatsOn){
        this.amountOfTanks = amountOfTanks;
        this.fortressHealth = 1500;
        this.cheatsOn = cheatsOn;
        initializeGame();
    }

    /*****************
        Methods
     *****************/

    //Generate all tanks needed for game and place on map
    public void initializeGame(){
        this.gameMap = new Map();
        ArrayList<TankBlock> tankBlocks = new ArrayList<TankBlock>();
        for(int tankNum = 0; tankNum < amountOfTanks; tankNum++){
            tankBlocks = gameMap.generateTankPositions(tankNum);
            Tank newTank =  new Tank(tankNum,tankBlocks);
            tanks.add(newTank);
        }
    }

    //inflict damage to fortress
    public void takeDamage(){
        fortressHealth -= calculateTotalDamage();
        if(fortressHealth < 0){
            fortressHealth = 0;
        }
    }

    public boolean continueCondition(){
        if(fortressHealth > 0 && tanksAlive() > 0){
            return true;
        }
        return false;
    }

    public boolean winCondition(){
        if(fortressHealth > 0 && tanksAlive() == 0){
            return true;
        }
        return false;
    }

    //check coordinate on gameMap based on parameter and alter
    //hit/miss markers based on the selected block
    public boolean attackSpot(int[] attackCoordinates){
        TankBlock tankBlock = gameMap.getTankBlock(attackCoordinates);
        int tankNum = tankBlock.getTankNum();
        int blockNum = tankBlock.getBlockNum();
        boolean isHit= gameMap.hitAbleCheck(attackCoordinates);
        if(isHit){
            tanks.get(tankNum).destroyBlock(blockNum);
            gameMap.setHitPlayMap(attackCoordinates,true);
            return true;
        }
        gameMap.setHitPlayMap(attackCoordinates,false);
        return false;
    }

    public int tanksAlive(){
        int tanksAlive = 0;
        for(Tank tank: tanks){
            if(tank.isAlive()){
                tanksAlive++;
            }
        }
        return tanksAlive;
    }

    //add all alive tank damage to return
    public int calculateTotalDamage(){
        totalDamage = 0;
        for(Tank tank: tanks){
            if(tank.isAlive()) {
                totalDamage += tank.getDamage();
            }
        }
        return totalDamage;
    }

    public boolean isCheatsOn(){
        return cheatsOn;
    }

    /*****************
        Getter Methods
     *****************/

    public int getFortressHealth(){
        return fortressHealth;
    }

    public TankBlock[][] getCheatMap(){
        return gameMap.getCheatMap();
    };

    public char[][] getPlayMap(){
        return gameMap.getPlayMap();
    }

    public int getBoardSize(){
        return gameMap.getBoardsize();
    }

    public int getAmountOfTanks(){
        return amountOfTanks;
    }

    public ArrayList<Tank> getTanks(){
        return tanks;
    }

}
