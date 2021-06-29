package com.traveller.safe_travel_guide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.traveller.safe_travel_guide.UI_Controls.About_the_app;
import com.traveller.safe_travel_guide.UI_Controls.Home_page;
import com.traveller.safe_travel_guide.UI_Controls.main_ui_control;

public class Nav_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_activity);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Home_page()).commit();

        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.about:
                            selectedFragment = new About_the_app();
                            break;
                        case R.id.Home:
                            selectedFragment = new Home_page();
                            break;
                        case R.id._travel:

                                    selectedFragment = new main_ui_control();
                            //Intent intent = new Intent(getApplicationContext(),main_ui_control.class);
                            //startActivity(intent);
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

}