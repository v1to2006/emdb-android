package com.example.emdb.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.emdb.R;
import com.example.emdb.classes.Client;
import com.example.emdb.classes.Database;
import com.example.emdb.fragments.AddMovieFragment;
import com.example.emdb.fragments.HomeFragment;
import com.example.emdb.fragments.LogInFragment;
import com.example.emdb.fragments.MoviesByGenreFragment;
import com.example.emdb.fragments.ProfileFragment;
import com.example.emdb.fragments.RecoverPasswordFragment;
import com.example.emdb.fragments.SearchFragment;
import com.example.emdb.fragments.SignUpFragment;
import com.example.emdb.interfaces.OnButtonClickListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements OnButtonClickListener, SwipeRefreshLayout.OnRefreshListener {
    private BottomNavigationView bottomNavigationView;
    private SwipeRefreshLayout noConnectionSwipeRefresh;

    private HomeFragment homeFragment = new HomeFragment();
    private SearchFragment searchFragment = new SearchFragment();
    private AddMovieFragment addMovieFragment = new AddMovieFragment();
    private ProfileFragment profileFragment = new ProfileFragment();
    private LogInFragment logInFragment = new LogInFragment();
    private SignUpFragment signUpFragment = new SignUpFragment();
    private RecoverPasswordFragment recoverPasswordFragment = new RecoverPasswordFragment();

    private Client client = Client.getInstance();
    private Database database = Database.getInstance();

    private long backPressedTime = 0;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation_view);

        if (!database.checkConnection()) {
            setContentView(R.layout.activity_no_connection);

            noConnectionSwipeRefresh = findViewById(R.id.noConnectionSwipeRefresh);
            noConnectionSwipeRefresh.setOnRefreshListener(this);

            return;
        }

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

    @Override
    public void onHomeClicked() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
    }

    @Override
    public void onRefresh() {
        new Handler(Looper.getMainLooper()).post(() -> {
            if (database.checkConnection()) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
            noConnectionSwipeRefresh.setRefreshing(false);
        });
    }
}