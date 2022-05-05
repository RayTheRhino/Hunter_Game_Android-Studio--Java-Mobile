package com.example.myapplicationh;

public class Bonus {
    private int bonRow;
    private int bonCol;

    public Bonus(){
        this.bonCol = 0;
        this.bonRow = 0;
    }

    public void bnousLoc(int gBounRow, int gBounCol){
        int rowRange = gBounRow-1;
        int colRange = gBounCol-1;
        int randRow = (int)(Math.random()*rowRange);
        int randCol = (int)(Math.random()*rowRange);

        setRowBounLoc(randRow);
        setColBounLoc(randCol);
    }

    public boolean isBounCrashCupid(int cupidRowLoc, int cupidColLoc ){
        if ((bonCol == cupidColLoc)&&(bonRow == cupidRowLoc)){
            return true;
        }else
            return false;
    }

    public boolean isBounCrashCuple(int cupleRowLoc, int cupleColLoc ){
        if ((bonCol == cupleColLoc)&&(bonRow == cupleRowLoc)){
            return true;
        }else
            return false;
    }

    public void setColBounLoc(int randCol) {
        this.bonCol = randCol;
    }

    public void setRowBounLoc(int randRow) {
        this.bonRow = randRow;
    }
    public int getColBounLoc(){
        return bonCol;
    }
    public int getRowBounLoc(){
        return bonRow;
    }

}
