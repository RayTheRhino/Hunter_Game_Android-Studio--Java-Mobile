package com.example.myapplicationh.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationh.DataManager;
import com.example.myapplicationh.LocationService;
import com.example.myapplicationh.Player;
import com.example.myapplicationh.R;
import com.example.myapplicationh.callBacks.CallBack_Score;
import com.example.myapplicationh.callBacks.CallBack_Update;
import com.example.myapplicationh.utils.MSP;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.Objects;

public class Activity_Menu extends AppCompatActivity {
    private MaterialButton buttonsGameB;
    private MaterialButton sensorGameB;
    private MaterialButton topTenB;
    private MaterialButton enter_BTN_name;
    private EditText txt_EDT_name;
    private String scoreFromGame = "";
    private DataManager dataManager;
    private String startScore = "00";
    private Bundle bundle = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        MSP.initHelper(this);
        if (bundle == null){
            bundle = new Bundle();
        }
        findViews();
        buttonsClicked();
    }

    private void buttonsClicked() {
        buttonsGameB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_Butt_Game.class);
                intent.putExtra(Activity_Butt_Game.MODE, "BUTTONS");
                String name = MSP.getMe().getString(MSP.getMe().getSP_FILE(), txt_EDT_name.getText().toString());
                saveName(name);
                bundle.putString("playerName",name);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        sensorGameB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_Sensor_Game.class);
                intent.putExtra(Activity_Butt_Game.MODE, "SENSORS");
                String name = MSP.getMe().getString(MSP.getMe().getSP_FILE(), txt_EDT_name.getText().toString());
                saveName(name);
                startActivity(intent);
            }
        });

        topTenB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Activity_Top_Ten.class);
                startActivity(intent);
            }
        });

        enter_BTN_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setVisibility();
            }
        });
    }

    private void setVisibility(){
        buttonsGameB.setVisibility(View.VISIBLE);
        sensorGameB.setVisibility(View.VISIBLE);
        topTenB.setVisibility(View.VISIBLE);
        txt_EDT_name.setVisibility(View.INVISIBLE);
        enter_BTN_name.setVisibility(View.INVISIBLE);
    }

    private void findViews() {
        buttonsGameB = findViewById(R.id.game_BTN_hunter_game);
        sensorGameB = findViewById(R.id.game_BTN_sensor_game);
        topTenB= findViewById(R.id.game_BTN_record_table);
        txt_EDT_name = findViewById(R.id.txt_EDT_name);
        enter_BTN_name = findViewById(R.id.enter_BTN_name);

    }

    private void saveName(String name) {
        SharedPreferences.Editor editor = getSharedPreferences(MSP.getMe().getSP_FILE(), MODE_PRIVATE).edit();
        editor.putString(name,startScore);
        editor.apply();
    }
    private void getResults(){
        dataManager = new Gson().fromJson(MSP.getMe().getString("DATA_MANAGER",""),DataManager.class);
        LocationService ls = new LocationService(Activity_Menu.this);
        double lon,lat;
        if(ls.canGetLocation()){
            lon = ls.getLongitude();
            lat = ls.getLatitude();
        }else {
            ls.showSettingsAlert();
            lon = ls.getLongitude();
            lat = ls.getLatitude();
        }
        Log.d("LocationService", "Lat : " + lat +" , Lon : " + lon);

        dataManager.addPlayer(new Player()
                .setName(Objects.requireNonNull(txt_EDT_name.getText()).toString())
                .setScore(scoreFromGame)
                .setLat(lat)
                .setLon(lon));

        MSP.getMe().putString("DATA_MANAGER", new Gson().toJson(dataManager));
    }

    CallBack_Update callBackUpdate = new CallBack_Update() {
        @Override
        public void setData(DataManager dataManager) {

        }

        @Override
        public DataManager getData() {
            dataManager = new Gson().fromJson(MSP.getMe().getString("DATA_MANAGER",""),DataManager.class);
            return dataManager;
        }

    };




}
