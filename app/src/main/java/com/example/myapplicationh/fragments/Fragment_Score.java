package com.example.myapplicationh.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplicationh.Player;
import com.example.myapplicationh.callBacks.CallBack_Location;
import com.example.myapplicationh.DataManager;
import com.example.myapplicationh.R;
import com.example.myapplicationh.utils.MSP;

import java.util.ArrayList;

public class Fragment_Score extends Fragment {
    private TextView[][]scoreList;
    private AppCompatActivity activity;
    private DataManager dataManager;
    private CallBack_Location callBack_location;
    private String name;
    private ArrayList<Player> topTenList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstancesState) {
        View view = inflater.inflate(R.layout.fragment_score, container, false);
        findViews(view);
//        topTenList = MSP.getMe().getUdersData();
//        getDetails();
//        getSelected();

        return view;
    }

    public void setDataManager(DataManager dataManager){this.dataManager = dataManager;}

    public void setActivity(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void getDetails() {
        Player[] players = dataManager.getPlayers();
        for (int i = 0; i <dataManager.MAX_LEN;i++){
            if(players[i] == null)
                return;
            scoreList[i][0].setText(players[i].getName());
            scoreList[i][1].setText(players[i].getScore());
        }
    }

    private void findViews(View view) {
        scoreList = new TextView[][]{
                {view.findViewById(R.id.TXT_NAME_0),view.findViewById(R.id.TXT_SCORE_0)},
                {view.findViewById(R.id.TXT_NAME_1),view.findViewById(R.id.TXT_SCORE_1)},
                {view.findViewById(R.id.TXT_NAME_2),view.findViewById(R.id.TXT_SCORE_2)},
                {view.findViewById(R.id.TXT_NAME_3),view.findViewById(R.id.TXT_SCORE_3)},
                {view.findViewById(R.id.TXT_NAME_4),view.findViewById(R.id.TXT_SCORE_4)},
                {view.findViewById(R.id.TXT_NAME_5),view.findViewById(R.id.TXT_SCORE_5)},
                {view.findViewById(R.id.TXT_NAME_6),view.findViewById(R.id.TXT_SCORE_6)},
                {view.findViewById(R.id.TXT_NAME_7),view.findViewById(R.id.TXT_SCORE_7)},
                {view.findViewById(R.id.TXT_NAME_8),view.findViewById(R.id.TXT_SCORE_8)},
                {view.findViewById(R.id.TXT_NAME_9),view.findViewById(R.id.TXT_SCORE_9)}
        };
    }

    public void getSelected() {
        for(int i = 0; i<scoreList.length; i++){
            Player currentPlayer = dataManager . getPlayers()[i];
            int finalI = i;
            scoreList[i][0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isValidInfo(finalI)) {
                        callBack_location.setLocation(currentPlayer.getLat(), currentPlayer.getLon());
                    }
                }
            });
            scoreList[i][1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(isValidInfo(finalI)){
                        callBack_location.setLocation(currentPlayer.getLat(), currentPlayer.getLon());
                    }
                }
            });
        }
    }


    private boolean isValidInfo(int i){
        return !scoreList[i][0].getText().equals("NAME") ||
                !scoreList[i][1].getText().equals("SCORE");
    }
    public void setCallBackLocation(CallBack_Location callBackLocation){ callBack_location = callBackLocation;}

}

