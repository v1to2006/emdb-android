package com.example.emdb.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.emdb.R;
import com.example.emdb.adapters.MovieCategoryListAdapter;
import com.example.emdb.classes.Database;
import com.example.emdb.models.Movie;

import java.util.ArrayList;

public class MovieDetailFragment extends Fragment {

    private int movieId;
    private TextView detailTitle;

    private RecyclerView.Adapter categoriesAdapter;

    private RecyclerView categoriesRecycler;

    private ProgressBar categoriesLoading;

    private TextView detailRating;
    private TextView detailLength;
    private TextView detailYear;
    private TextView detailDirector;
    private TextView detailStars;
    private ImageView detailImage;
    private ProgressBar detailLoading;
    private ImageView backImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        FragmentActivity activity = getActivity();
        if (activity != null) {
            View currentFocus = activity.getCurrentFocus();
            if (currentFocus != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
        }

        movieId = getArguments().getInt("id", 0);
        initView(view);
        new LoadDetailTask().execute();
        new LoadCategoriesTask().execute();

        return view;
    }


    private void initView(View view) {
        detailTitle = view.findViewById(R.id.detailTitle);
        detailLoading = view.findViewById(R.id.detailProgressBar);
        detailImage = view.findViewById(R.id.detailImage);

        categoriesRecycler = view.findViewById(R.id.detailCategoriesRecycler);

        categoriesLoading = view.findViewById(R.id.detailCategoriesProgressBar);

        categoriesRecycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));

        detailRating = view.findViewById(R.id.detailRating);
        detailLength = view.findViewById(R.id.detailLength);
        detailYear = view.findViewById(R.id.detailYear);
        detailDirector = view.findViewById(R.id.detailDirector);
        detailStars = view.findViewById(R.id.detailStars);
        backImage = view.findViewById(R.id.backImage);

        backImage.setOnClickListener(v -> {
            assert getFragmentManager() != null;
            getFragmentManager().popBackStack();
        });
    }

    private class LoadDetailTask extends AsyncTask<Void, Void, Movie> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            detailLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie doInBackground(Void... voids) {
            return Database.getInstance().getMovieById(movieId);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Movie movie) {
            super.onPostExecute(movie);
            detailLoading.setVisibility(View.GONE);

            if (movie != null) {
                detailTitle.setText(movie.getTitle());

                Glide.with(requireContext())
                        .load(movie.getImage())
                        .into(detailImage);

                detailRating.setText(String.format("%.1f", movie.getRating()));
                detailLength.setText(movie.getLength() + " min");
                detailYear.setText(movie.getReleaseYear());
                detailDirector.setText(movie.getDirector());
                detailStars.setText(movie.getStars());
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
            return Database.getInstance().getMovieGenres(movieId);
        }

        @Override
        protected void onPostExecute(ArrayList<String> categories) {
            super.onPostExecute(categories);
            categoriesLoading.setVisibility(View.GONE);
            if (categories != null) {
                categoriesAdapter = new MovieCategoryListAdapter(categories);
                categoriesRecycler.setAdapter(categoriesAdapter);
            }
        }
    }
}
