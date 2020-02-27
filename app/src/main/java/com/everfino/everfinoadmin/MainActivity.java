package com.everfino.everfinoadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.everfino.everfinoadmin.Fragment.ProfileFragment;
import com.everfino.everfinoadmin.Fragment.RestFragment;
import com.everfino.everfinoadmin.Fragment.StatisticFragment;
import com.everfino.everfinoadmin.Fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView tab_menu;
    AppSharedPreferences appSharedPreferences;
    HashMap<String,String> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appSharedPreferences=new AppSharedPreferences(this);

        map=appSharedPreferences.getPref();

        if(Integer.parseInt(map.get("adminid"))==0 && map.get("username")=="")
        {
            Intent i=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
        }
        else
        {
            Log.e("###############",map.get("adminid")+map.get("username"));
        }

        tab_menu = findViewById(R.id.tab_menu);

        tab_menu.setSelectedItemId(R.id.nav_restaurant);
        Fragment fragment=new RestFragment();
        loadFragment(fragment);

        tab_menu.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.nav_profile:

                        //LiveOrder

                        fragment = new ProfileFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.nav_restaurant:
                        //Table

                        fragment = new RestFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.nav_user:
                        //Menu
                        fragment = new UserFragment();
                        loadFragment(fragment);
                        break;
                    case R.id.nav_statistic:
                        //Statistic

                        fragment = new StatisticFragment();
                        loadFragment(fragment);
                        break;

                }
                return true;
            }
        });
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
