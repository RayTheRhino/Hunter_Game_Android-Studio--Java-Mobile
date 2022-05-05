package com.example.myapplicationh.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myapplicationh.R;
import com.example.myapplicationh.fragments.MapsFragment;

public class Activity_Map extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_maps);

        Fragment fragment = new MapsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.map_LAY_details,fragment)
                .commit();

    }

}
