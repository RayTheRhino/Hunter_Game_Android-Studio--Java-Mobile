package com.example.myapplicationh.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplicationh.Bonus;
import com.example.myapplicationh.DataManager;
import com.example.myapplicationh.Player;
import com.example.myapplicationh.R;
import com.example.myapplicationh.utils.MSP;
import com.google.android.material.button.MaterialButton;

public class Activity_Butt_Game extends AppCompatActivity {
    public static final String MODE = "";
    private String whatMode;
    private final int UP = 2;
    private final int DOWN = 3;
    private final int LEFT = 0;
    private final int RIGHT = 1;
    private ImageView[][] imageMat;
    private ImageView[] imageLives;
    private MaterialButton[] buttonsArr;
    private TextView score_LBL;

    private int rowsNum = 6;
    private int colsNum = 5;
    private int cupleCol = 1;
    private int cupleRow = 2;
    private int cupidCol = 1;
    private int cupidRow = 0;
    private int currentLives;
    private Handler handler;
    private int timer = 1000;
    private int direction = -1;
    private final int CUPID_ROW_START = 0;
    private final int CUPID_COL_START = 1;
    private final int CUPLE_ROW_START = 2;
    private final int CUPLE_COL_START = 1;
    private final int CUPID_D = 0;
    private Bonus bonus;
    private DataManager dm;
    private String name;
    private Bundle bundle;
    MediaPlayer hitSong;
    MediaPlayer grabSong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_butt_game);
        hitSong = MediaPlayer.create(Activity_Butt_Game.this,R.raw.cupid_hit);
        grabSong = MediaPlayer.create(Activity_Butt_Game.this,R.raw.cupid_swip);

        bundle = getIntent().getExtras();
        name = bundle.getString("playerName");
        whatMode = getIntent().getStringExtra(MODE);
        if(whatMode.equals("BUTTONS")) {
            setContentView(R.layout.activity_butt_game);
            //handleButtons();
        } else{
            setContentView(R.layout.activity_butt_game);
            //handleSensors();
        }

        dm = new DataManager();
        bonus = new Bonus();
        findViews();
        initGame();
        runLogic();
    }

    private void saveScore(DataManager dm) {
        SharedPreferences.Editor editor = getSharedPreferences(MSP.getMe().getSP_FILE(), MODE_PRIVATE).edit();
        editor.putString(dm.getCurrentPlayer().getName(),String.valueOf(dm.getScore()));
        editor.apply();
    }

    private void startPos() {   //characters positions when game start
        cupleRow = CUPLE_ROW_START;
        cupleCol = CUPLE_COL_START;
        cupidRow = CUPID_ROW_START;
        cupidCol = CUPID_COL_START;
        imageMat[cupidRow][cupidCol].setVisibility(View.VISIBLE);
        imageMat[cupleRow][cupleCol].setImageResource(R.drawable.ic_cuple_icon);
        imageMat[cupleRow][cupleCol].setVisibility(View.VISIBLE);
        bonusStartPos();
    }

    private void bonusStartPos() {  //bonus placement
        bonus.bnousLoc(rowsNum,colsNum);
        imageMat[bonus.getRowBounLoc()][bonus.getColBounLoc()].setImageResource(R.drawable.ic_arrows_icon);
        imageMat[bonus.getRowBounLoc()][bonus.getColBounLoc()].setVisibility(View.VISIBLE);
    }

    private void initGame() {   //init the game
        currentLives = dm.getLives();
        score_LBL.setText(String.valueOf(dm.getScore()));
        startPos();
    }

    private boolean checkBound(int direction, int character) { //check if cupid is in matrix
        switch (direction) {
            case UP:
                return ((character == CUPID_D) ? cupidRow : cupleRow) > 0;
            case DOWN:
                return ((character == CUPID_D) ? cupidRow : cupleRow) < (rowsNum - 1);
            case LEFT:
                return ((character == CUPID_D) ? cupidCol : cupleCol) > 0;
            case RIGHT:
                return ((character == CUPID_D) ? cupidCol : cupleCol) < (colsNum - 1);
        }
        return false;
    }

    private void runLogic() {   //start game logic
        buttonsListener();
        Tick();
        randomCouplePos();
    }

    private void buttonsListener() {    //buttons listner arr
        for (int i = 0; i < buttonsArr.length; ++i) {
            int finalI = i;
            buttonsArr[i].setOnClickListener(v -> move(finalI));
        }
    }

    private void Tick() {
        handler = new Handler();
        handler.postDelayed(() -> {
            if (direction != -1) { //change direction
                move(direction);
            }
            dm.addTenToScore();
            score_LBL.setText(String.valueOf(dm.getScore()));
            runLogic();
        }, timer);
    }

    private void StopTick() {
        handler.removeCallbacksAndMessages(null);
    }

    private void move(int i) {  //cupid movement by but pressing
        switch (i) {
            case LEFT:
                if (checkBound(LEFT, CUPID_D)) {
                    imageMat[cupidRow][cupidCol].setVisibility(View.INVISIBLE);
                    cupidCol--;
                    direction = LEFT;
                    imageMat[cupidRow][cupidCol].setVisibility(View.VISIBLE);
                    checkCrash();
                    isCupidGrabArrow();
                }
                break;
            case RIGHT:
                if (checkBound(RIGHT, CUPID_D)) {
                    imageMat[cupidRow][cupidCol].setVisibility(View.INVISIBLE);
                    cupidCol++;
                    direction = RIGHT;
                    imageMat[cupidRow][cupidCol].setVisibility(View.VISIBLE);
                    checkCrash();
                    isCupidGrabArrow();
                }
                break;
            case UP:
                if (checkBound(UP, CUPID_D)) {
                    imageMat[cupidRow][cupidCol].setVisibility(View.INVISIBLE);
                    cupidRow--;
                    direction = UP;
                    imageMat[cupidRow][cupidCol].setVisibility(View.VISIBLE);
                    checkCrash();
                    isCupidGrabArrow();
                }
                break;
            case DOWN:
                if (checkBound(DOWN, CUPID_D)) {
                    imageMat[cupidRow][cupidCol].setVisibility(View.INVISIBLE);
                    cupidRow++;
                    direction = DOWN;
                    imageMat[cupidRow][cupidCol].setVisibility(View.VISIBLE);
                    checkCrash();
                    isCupidGrabArrow();
                }
                break;
        }
    }

    private void updateLives() {    //lives update
        playHitMusic();
        imageLives[--currentLives].setVisibility(View.INVISIBLE);
        vibrate();
        if (currentLives == 0) {
            exitGame();
        }
    }

    private void checkCrash() {     //check if cupid was hitted
        if ((cupleCol == cupidCol) && (cupleRow == cupidRow)) {
            direction = -1;
            updateLives();
            imageMat[cupleRow][cupleCol].setImageResource(R.drawable.ic_cupid_icon);
            imageMat[cupleRow][cupleCol].setVisibility(View.INVISIBLE);
            imageMat[bonus.getRowBounLoc()][bonus.getColBounLoc()].setImageResource(R.drawable.ic_cupid_icon);
            imageMat[bonus.getRowBounLoc()][bonus.getColBounLoc()].setVisibility(View.INVISIBLE);
            startPos();
            if(dm.getScore() > 5){
                dm.scoreCrashReduce();
                score_LBL.setText(String.valueOf(dm.getScore()));
            }
        }
    }

    private void vibrate(){
        Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        //vibrate for 500 miliseconds
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            vib.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            //depercated at API 26
            vib.vibrate(500);
        }
    }
    private void playHitMusic(){
        hitSong.start();
    }
    private void playGrabMusic(){
        grabSong.start();
    }

    private void isCoupleOnBonus(){ //when couple hit bonus score reduces
        if(bonus.isBounCrashCuple(cupleRow,cupleCol)){
            imageMat[bonus.getRowBounLoc()][bonus.getColBounLoc()].setImageResource(R.drawable.ic_cuple_icon);
            imageMat[bonus.getRowBounLoc()][bonus.getColBounLoc()].setVisibility(View.VISIBLE);
            dm.scoreCrashReduce();
            score_LBL.setText(String.valueOf(dm.getScore()));
            bonusStartPos();
        }
    }

    private void isCupidGrabArrow(){
        if(bonus.isBounCrashCuple(cupidRow,cupidCol)){
            //add game sound and vibrate
            dm.addBonusToScore();
            imageMat[bonus.getRowBounLoc()][bonus.getColBounLoc()].setImageResource(R.drawable.ic_cupid_icon);
            vibrate();
            playGrabMusic();
            bonusStartPos();
        }
    }

    private void randomCouplePos() {    //random couple position
        // define the range
        int range = (3-0) + 1;
        int rand = (int) (Math.random() * range) + 1;
        switch (rand) {
            case UP:
                if (checkBound(UP, 1)) {
                    imageMat[cupleRow][cupleCol].setVisibility(View.INVISIBLE);
                    imageMat[cupleRow][cupleCol].setImageResource(R.drawable.ic_cupid_icon);
                    cupleRow--;
                    imageMat[cupleRow][cupleCol].setImageResource(R.drawable.ic_cuple_icon);
                    imageMat[cupleRow][cupleCol].setVisibility(View.VISIBLE);
                    checkCrash();
                    isCoupleOnBonus();
                }
                break;
            case LEFT:
                if (checkBound(LEFT, 1)) {
                    imageMat[cupleRow][cupleCol].setVisibility(View.INVISIBLE);
                    imageMat[cupleRow][cupleCol].setImageResource(R.drawable.ic_cupid_icon);
                    cupleCol--;
                    imageMat[cupleRow][cupleCol].setImageResource(R.drawable.ic_cuple_icon);
                    imageMat[cupleRow][cupleCol].setVisibility(View.VISIBLE);
                    checkCrash();
                    isCoupleOnBonus();
                }
                break;
            case RIGHT:
                if (checkBound(RIGHT, 1)) {
                    imageMat[cupleRow][cupleCol].setVisibility(View.INVISIBLE);
                    imageMat[cupleRow][cupleCol].setImageResource(R.drawable.ic_cupid_icon);
                    cupleCol++;
                    imageMat[cupleRow][cupleCol].setImageResource(R.drawable.ic_cuple_icon);
                    imageMat[cupleRow][cupleCol].setVisibility(View.VISIBLE);
                    checkCrash();
                    isCoupleOnBonus();
                }
                break;
            case DOWN:
                if (checkBound(DOWN, 1)) {
                    imageMat[cupleRow][cupleCol].setVisibility(View.INVISIBLE);
                    imageMat[cupleRow][cupleCol].setImageResource(R.drawable.ic_cupid_icon);
                    cupleRow++;
                    imageMat[cupleRow][cupleCol].setImageResource(R.drawable.ic_cuple_icon);
                    imageMat[cupleRow][cupleCol].setVisibility(View.VISIBLE);
                    checkCrash();
                    isCoupleOnBonus();
                }
                break;
        }
    }

    private void exitGame() {   //finish game
        StopTick();
        Toast.makeText(getApplicationContext(), "Game is over", Toast.LENGTH_LONG).show();
        saveScore(dm);
        finish();
    }


    private void findViews() {
        imageMat = new ImageView[][]{
                {findViewById(R.id.image00), findViewById(R.id.image01), findViewById(R.id.image02), findViewById(R.id.image03), findViewById(R.id.image04)},
                {findViewById(R.id.image10), findViewById(R.id.image11), findViewById(R.id.image12), findViewById(R.id.image13), findViewById(R.id.image14)},
                {findViewById(R.id.image20), findViewById(R.id.image21), findViewById(R.id.image22), findViewById(R.id.image23), findViewById(R.id.image24)},
                {findViewById(R.id.image30), findViewById(R.id.image31), findViewById(R.id.image32), findViewById(R.id.image33), findViewById(R.id.image34)},
                {findViewById(R.id.image40), findViewById(R.id.image41), findViewById(R.id.image42), findViewById(R.id.image43), findViewById(R.id.image44)},
                {findViewById(R.id.image50), findViewById(R.id.image51), findViewById(R.id.image52), findViewById(R.id.image53), findViewById(R.id.image54)}};
        buttonsArr = new MaterialButton[]{
                findViewById(R.id.left_BTN),
                findViewById(R.id.right_BTN),
                findViewById(R.id.up_BTN),
                findViewById(R.id.down_BTN)
        };
        imageLives = new ImageView[]{findViewById(R.id.game_IMG_heart1),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart3)
        };
        score_LBL = findViewById(R.id.score_LBL);
    }
}
