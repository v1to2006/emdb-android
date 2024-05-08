package com.example.emdb.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emdb.R;
import com.example.emdb.classes.Client;
import com.example.emdb.fragments.AddMovieFragment;
import com.example.emdb.fragments.HomeFragment;
import com.example.emdb.fragments.ProfileFragment;
import com.example.emdb.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    AddMovieFragment addMovieFragment = new AddMovieFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    private Client client = Client.getInstance();

    private long backPressedTime = 0;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            hideKeyboard();

            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                    return true;

                case R.id.search:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, searchFragment).commit();
                    return true;

                case R.id.add:
                    if (client.userLoggedIn()) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, addMovieFragment).commit();
                    } else {
                        startActivity(new Intent(MainActivity.this, LogInActivity.class));
                    }

                    return true;

                case R.id.profile:
                    if (client.userLoggedIn()) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                    } else {
                        startActivity(new Intent(MainActivity.this, LogInActivity.class));
                    }

                    return true;
            }

            return false;
        });
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            backPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "Tap again to exit", Toast.LENGTH_SHORT).show();
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
