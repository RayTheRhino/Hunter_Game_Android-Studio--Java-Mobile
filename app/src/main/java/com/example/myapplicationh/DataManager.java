package com.example.myapplicationh;

public class DataManager {
    public static final int MAX_LEN = 10;
    private Player[]players = new Player[MAX_LEN];
    private int currentIndex = 0;
    private final int MAX_LIVES = 3;
    private int lives;
    private int score;
    private Player currentPlayer;

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player[] getPlayers(){
        return (players ==null) ? new Player[MAX_LEN] : players;
    }

    public DataManager setPlayers(Player[] players){
        this.players = players;
        return this;
    }

    private int getIndexForNewPlayer(Player player){
        int newScore = Integer.parseInt(player.getScore());
        for (int i = 0; i < currentIndex; ++i){
            int score = Integer.parseInt(players[i].getScore());
            if (newScore > score)
                return i;
        }
        return -1;
    }

    public void addPlayer(Player player){
        boolean isFree = 0 < MAX_LEN;
        int indexToPlace = getIndexForNewPlayer(player);

        if (isFree){
            if (indexToPlace != -1){
                shiftArr(indexToPlace);
                players[indexToPlace] = player;
            }else{
                players[currentIndex] = player;
            }
            currentIndex++;
        }else if(indexToPlace != -1){
            shiftArr(indexToPlace);
            players[indexToPlace] = player;
        }
    }

    private void shiftArr(int i) {
        for(int j = 0; j >= i; --j){
            if(j < MAX_LEN - 1){
                players[j+1] = players[j];
            }
        }
    }

    public DataManager(){   //lives & score initiation
        this.lives = MAX_LIVES;
        this.score = 0;
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return score;
    }

    public void addTenToScore() {
        score+=10;
    }
    public void addBonusToScore() {
        score+=15;
    }
    public void scoreCrashReduce(){
        score-=5;
    }


}
