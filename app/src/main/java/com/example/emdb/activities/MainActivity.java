package com.example.emdb.activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emdb.R;
import com.example.emdb.adapters.MovieListAdapter;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter bestMoviesAdapter;
    private RecyclerView.Adapter categoriesAdapter;
    private RecyclerView.Adapter comingSoonMoviesAdapter;
    private RecyclerView bestMoviesRecycler;
    private RecyclerView categoriesRecycler;
    private RecyclerView comingSoonMoviesRecycler;
    private ProgressBar bestMoviesLoading;
    private ProgressBar categoriesLoading;
    private ProgressBar comingSoonMoviesLoading;
    private Handler slideHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();
        load();
    }

    private void load() {
        bestMoviesLoading.setVisibility(View.VISIBLE);
        bestMoviesAdapter = new MovieListAdapter();
        bestMoviesRecycler.setAdapter(bestMoviesAdapter);
        bestMoviesLoading.setVisibility(View.GONE);
    }

    private void initView() {
        bestMoviesRecycler = findViewById(R.id.bestMoviesView);
        categoriesRecycler = findViewById(R.id.categoriesView);
        comingSoonMoviesRecycler = findViewById(R.id.comingSoonView);

        bestMoviesLoading = findViewById(R.id.bestMoviesProgressBar);
        categoriesLoading = findViewById(R.id.categoriesProgressBar);
        comingSoonMoviesLoading = findViewById(R.id.comingSoonProgressBar);

        bestMoviesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        comingSoonMoviesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }
}