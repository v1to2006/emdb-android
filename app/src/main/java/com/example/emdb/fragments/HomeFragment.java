package com.example.emdb.fragments;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emdb.R;
import com.example.emdb.adapters.CategoryListAdapter;
import com.example.emdb.adapters.MovieListAdapter;
import com.example.emdb.classes.Database;
import com.example.emdb.models.Movie;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);
        new LoadBestMoviesTask().execute();
        new LoadNewMoviesTask().execute();
        new LoadCategoriesTask().execute();

        return view;
    }

    private void initView(View view) {
        bestMoviesRecycler = view.findViewById(R.id.bestMoviesView);
        newMoviesRecycler = view.findViewById(R.id.newMoviesView);
        categoriesRecycler = view.findViewById(R.id.categoriesView);

        bestMoviesLoading = view.findViewById(R.id.bestMoviesProgressBar);
        newMoviesLoading = view.findViewById(R.id.newMoviesProgressBar);
        categoriesLoading = view.findViewById(R.id.categoriesProgressBar);

        bestMoviesRecycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        newMoviesRecycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        categoriesRecycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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