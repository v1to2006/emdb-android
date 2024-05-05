package com.example.emdb.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emdb.R;
import com.example.emdb.adapters.CategoryListAdapter;
import com.example.emdb.adapters.MovieListAdapter;
import com.example.emdb.classes.Database;
import com.example.emdb.models.Movie;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter bestMoviesAdapter;
    private RecyclerView.Adapter newMoviesAdapter;
    private RecyclerView.Adapter categoriesAdapter;

    private RecyclerView bestMoviesRecycler;
    private RecyclerView newMoviesRecycler;
    private RecyclerView categoriesRecycler;

    private ProgressBar bestMoviesLoading;
    private ProgressBar newMoviesLoading;
    private ProgressBar categoriesLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        new LoadBestMoviesTask().execute();
        new LoadNewMoviesTask().execute();
        new LoadCategoriesTask().execute();
    }

    private void initView() {
        bestMoviesRecycler = findViewById(R.id.bestMoviesView);
        newMoviesRecycler = findViewById(R.id.newMoviesView);
        categoriesRecycler = findViewById(R.id.categoriesView);

        bestMoviesLoading = findViewById(R.id.bestMoviesProgressBar);
        newMoviesLoading = findViewById(R.id.newMoviesProgressBar);
        categoriesLoading = findViewById(R.id.categoriesProgressBar);

        bestMoviesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        newMoviesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private class LoadBestMoviesTask extends AsyncTask<Void, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bestMoviesLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return Database.getInstance().getBestMovies();
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

    private class LoadNewMoviesTask extends AsyncTask<Void, Void, ArrayList<Movie>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            newMoviesLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return Database.getInstance().getNewMovies();
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            newMoviesLoading.setVisibility(View.GONE);
            if (movies != null) {
                newMoviesAdapter = new MovieListAdapter(movies);
                newMoviesRecycler.setAdapter(newMoviesAdapter);
            }
        }
    }

    private class LoadCategoriesTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            categoriesLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<String> doInBackground(Void... voids) {
            return Database.getInstance().getCategories();
        }

        @Override
        protected void onPostExecute(ArrayList<String> categories) {
            super.onPostExecute(categories);
            categoriesLoading.setVisibility(View.GONE);
            if (categories != null) {
                categoriesAdapter = new CategoryListAdapter(categories);
                categoriesRecycler.setAdapter(categoriesAdapter);
            }
        }
    }

}
