package com.example.emdb.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emdb.R;
import com.example.emdb.adapters.MovieListAdapter;
import com.example.emdb.classes.Database;
import com.example.emdb.models.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter bestMoviesAdapter;
    private RecyclerView bestMoviesRecycler;
    private ProgressBar bestMoviesLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        new LoadMoviesTask().execute();
    }

    private void initView() {
        bestMoviesRecycler = findViewById(R.id.bestMoviesView);
        bestMoviesLoading = findViewById(R.id.bestMoviesProgressBar);
        bestMoviesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private class LoadMoviesTask extends AsyncTask<Void, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bestMoviesLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return Database.getInstance().getMovies();
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            bestMoviesLoading.setVisibility(View.GONE);
            if (movies != null) {
                bestMoviesAdapter = new MovieListAdapter(movies);
                bestMoviesRecycler.setAdapter(bestMoviesAdapter);
            }
        }
    }
}
