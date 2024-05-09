package com.example.emdb.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.emdb.R;
import com.example.emdb.classes.Client;
import com.example.emdb.classes.Database;
import com.example.emdb.fragments.AddMovieFragment;
import com.example.emdb.fragments.DetailFragment;
import com.example.emdb.fragments.HomeFragment;
import com.example.emdb.fragments.LogInFragment;
import com.example.emdb.fragments.ProfileFragment;
import com.example.emdb.fragments.RecoverPasswordFragment;
import com.example.emdb.fragments.SearchFragment;
import com.example.emdb.fragments.SignUpFragment;
import com.example.emdb.interfaces.OnButtonClickListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements OnButtonClickListener {

    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    SearchFragment searchFragment = new SearchFragment();
    AddMovieFragment addMovieFragment = new AddMovieFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    LogInFragment logInFragment = new LogInFragment();
    SignUpFragment signUpFragment = new SignUpFragment();
    DetailFragment detailFragment = new DetailFragment();
    RecoverPasswordFragment recoverPasswordFragment = new RecoverPasswordFragment();

    private Client client = Client.getInstance();
    private Database database = Database.getInstance();

    private long backPressedTime = 0;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!database.checkConnection()) {
            setContentView(R.layout.activity_no_connection);
            return;
        }

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
                        Toast.makeText(MainActivity.this, "You must be logged in to add movies", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, logInFragment).commit();
                    }
                    return true;

                case R.id.profile:
                    if (client.userLoggedIn()) {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                    } else {
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, logInFragment).commit();
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
            getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
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

    @Override
    public void onLoginClicked() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, logInFragment).commit();
    }

    @Override
    public void onSignUpClicked() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, signUpFragment).commit();
    }

    @Override
    public void onRecoverPasswordClicked() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, recoverPasswordFragment).commit();
    }
}
