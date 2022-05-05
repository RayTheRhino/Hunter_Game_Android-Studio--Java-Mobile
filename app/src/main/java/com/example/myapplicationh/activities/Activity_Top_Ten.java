package com.example.myapplicationh.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationh.R;
import com.example.myapplicationh.callBacks.CallBack_Location;
import com.example.myapplicationh.fragments.Fragment_Score;
import com.example.myapplicationh.fragments.MapsFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.button.MaterialButton;

public class Activity_Top_Ten extends AppCompatActivity {
    private Fragment_Score fragmentScore;
    private MapsFragment fragmentMap;
    private MaterialButton back_BTN_topTen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_ten);

        findViews();
        createListner();
        fragmentScore = new Fragment_Score();
//        fragmentScore.setActivity(this);
//        fragmentScore.setDataManager(callBackUpdate.getData());
//        fragmentScore.setCallBackLocation(callBackLocation);
        //fragmentScore.setDataManager();
        getSupportFragmentManager().beginTransaction().add(R.id.game_LAY_list,fragmentScore).commit();

        fragmentMap = new MapsFragment();
//        fragmentMap.setActivity(this);
//        fragmentMap.setDataManager(callBackUpdate.getData());
        getSupportFragmentManager().beginTransaction().add(R.id.map_LAY_details, fragmentMap).commit();
    }

    private void createListner() {
        back_BTN_topTen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_Menu.class);
                startActivity(intent);
            }
        });

    }

    private void findViews() {
        back_BTN_topTen = findViewById(R.id.back_BTN_topTen);

    }

    // public static void setCallBack_Update(CallBack_Update callBack_update) { callBackUpdate = callBack_update; }

    private void zoomOnMap(double lat, double lon) {
        GoogleMap gm = fragmentMap.getGoogleMap();
        LatLng point = new LatLng(lat, lon);
        gm.addMarker(new MarkerOptions().position(point).title("* Crash Site * | Pilot Name: "));
        gm.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 13.0f));
    }

    CallBack_Location callBackLocation = new CallBack_Location() {
        @Override
        public void setLocation(double lat, double lon) {
            zoomOnMap(lat, lon);
        }
    };


}
