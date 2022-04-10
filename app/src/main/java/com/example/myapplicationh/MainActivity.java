package com.example.myapplicationh;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private final int UP = 2;
    private final int DOWN = 3;
    private final int LEFT = 0;
    private final int RIGHT = 1;
    private ImageView[][] imageMat;
    private ImageView[] imageLives;
    private MaterialButton[] buttonsArr;
    private TextView score_LBL;

    private final int MAX_LIVES = 3;
    private int rowsNum = 5;
    private int colsNum = 3;
    private int cupleCol = 1;
    private int cupleRow = 2;
    private int cupidCol = 1;
    private int cupidRow = 0;
    private int currentLives;
    private int score =0;
    private Handler handler;
    private int timer = 1000;
    private int direction = -1;
    private final int CUPID_ROW_START = 0;
    private final int CUPID_COL_START = 1;
    private final int CUPLE_ROW_START = 2;
    private final int CUPLE_COL_START = 1;
    private final int CUPID_D = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initGame();
        runLogic();
    }

    private void startPos() {
        cupleRow = CUPLE_ROW_START;
        cupleCol = CUPLE_COL_START;
        cupidRow = CUPID_ROW_START;
        cupidCol = CUPID_COL_START;
        imageMat[cupidRow][cupidCol].setVisibility(View.VISIBLE);
        imageMat[cupleRow][cupleCol].setImageResource(R.drawable.ic_cuple_icon);
        imageMat[cupleRow][cupleCol].setVisibility(View.VISIBLE);
    }

    private void initGame() {
        currentLives = MAX_LIVES;
        score_LBL.setText(String.valueOf(score));
        startPos();
    }

    private boolean checkBound(int direction, int character) {
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

    private void runLogic() {
        buttonsListener();
        Tick();
        randomCouplePos();
    }

    private void buttonsListener() {
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
            score+= 10;
            score_LBL.setText(String.valueOf(score));
            runLogic();
        }, timer);
    }

    private void StopTick() {
        handler.removeCallbacksAndMessages(null);
    }

    private void move(int i) {
        switch (i) {
            case LEFT:
                if (checkBound(LEFT, CUPID_D)) {
                    imageMat[cupidRow][cupidCol].setVisibility(View.INVISIBLE);
                    cupidCol--;
                    direction = LEFT;
                    imageMat[cupidRow][cupidCol].setVisibility(View.VISIBLE);
                    checkCrash();
                }
                break;
            case RIGHT:
                if (checkBound(RIGHT, CUPID_D)) {
                    imageMat[cupidRow][cupidCol].setVisibility(View.INVISIBLE);
                    cupidCol++;
                    direction = RIGHT;
                    imageMat[cupidRow][cupidCol].setVisibility(View.VISIBLE);
                    checkCrash();
                }
                break;
            case UP:
                if (checkBound(UP, CUPID_D)) {
                    imageMat[cupidRow][cupidCol].setVisibility(View.INVISIBLE);
                    cupidRow--;
                    direction = UP;
                    imageMat[cupidRow][cupidCol].setVisibility(View.VISIBLE);
                    checkCrash();
                }
                break;
            case DOWN:
                if (checkBound(DOWN, CUPID_D)) {
                    imageMat[cupidRow][cupidCol].setVisibility(View.INVISIBLE);
                    cupidRow++;
                    direction = DOWN;
                    imageMat[cupidRow][cupidCol].setVisibility(View.VISIBLE);
                    checkCrash();
                }
                break;
        }
    }

    private void updateLives() {
        imageLives[--currentLives].setVisibility(View.INVISIBLE);
        if (currentLives == 0) {
            exitGame();
        }
    }

    private void checkCrash() {
        if ((cupleCol == cupidCol) && (cupleRow == cupidRow)) {
            direction = -1;
            updateLives();
            imageMat[cupleRow][cupleCol].setImageResource(R.drawable.ic_cupid_icon);
            imageMat[cupleRow][cupleCol].setVisibility(View.INVISIBLE);
            startPos();
            if(score > 5){
                score-=5;
                score_LBL.setText(String.valueOf(score));
            }
        }
    }

    private void randomCouplePos() {
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
                }
                break;
        }
    }

    private void exitGame() {
        StopTick();
        Toast.makeText(getApplicationContext(), "Game is over", Toast.LENGTH_LONG).show();
        finish();
    }

    private void findViews() {
        imageMat = new ImageView[][]{{
                findViewById(R.id.image00), findViewById(R.id.image01), findViewById(R.id.image02)},
                {findViewById(R.id.image10), findViewById(R.id.image11), findViewById(R.id.image12)},
                {findViewById(R.id.image20), findViewById(R.id.image21), findViewById(R.id.image22)},
                {findViewById(R.id.image30), findViewById(R.id.image31), findViewById(R.id.image32)},
                {findViewById(R.id.image40), findViewById(R.id.image41), findViewById(R.id.image42)}};
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