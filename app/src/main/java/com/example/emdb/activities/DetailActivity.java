package com.example.emdb.activities;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.emdb.R;
import com.example.emdb.adapters.MovieListAdapter;
import com.example.emdb.classes.Database;
import com.example.emdb.models.Movie;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private int movieId;
    private TextView detailTitle;
    private TextView detailRating;
    private TextView detailLength;
    private TextView detailYear;
    private TextView detailDirector;
    private TextView detailStars;
    private ImageView detailImage;
    private ProgressBar detailLoading;
    private NestedScrollView scrollView;
    private ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movieId = getIntent().getIntExtra("id", 0);
        initView();
        new LoadDetailTask().execute();
    }

    private void initView() {
        detailTitle = findViewById(R.id.detailTitle);
        detailLoading = findViewById(R.id.detailProgressBar);
        scrollView = findViewById(R.id.detailScrollView);
        detailImage = findViewById(R.id.detailImage);
        detailRating = findViewById(R.id.detailRating);
        detailLength = findViewById(R.id.detailLength);
        detailYear = findViewById(R.id.detailYear);
        detailDirector = findViewById(R.id.detailDirector);
        detailStars = findViewById(R.id.detailStars);
        backImage = findViewById(R.id.backImage);

        backImage.setOnClickListener(view -> finish());
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

                Glide.with(DetailActivity.this)
                        .load(movie.getImage())
                        .into(detailImage);

                detailRating.setText(Double.toString(movie.getRating()));
                detailLength.setText(Integer.toString(movie.getLength()) + " min");
                detailYear.setText(movie.getReleaseYear());
                detailDirector.setText(movie.getDirector());
                detailStars.setText(movie.getStars());
            }
        }
    }
}