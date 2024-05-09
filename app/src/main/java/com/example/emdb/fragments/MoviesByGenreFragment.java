package com.example.emdb.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.emdb.R;
import com.example.emdb.adapters.MovieListAdapter;
import com.example.emdb.adapters.SearchMovieListAdapter;
import com.example.emdb.classes.Database;
import com.example.emdb.models.Movie;

import java.util.ArrayList;

public class MoviesByGenreFragment extends Fragment {
    private String genre;

    private RecyclerView.Adapter genreMoviesAdapter;
    private RecyclerView genreMoviesRecycler;
    private ProgressBar genreMoviesLoading;
    private ImageView backImage;
    private TextView genreTextView;
    private ArrayList<Movie> movies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies_by_genre, container, false);

        genre = getArguments().getString("genre", null);

        initView(view);

        new LoadMoviesByGenre().execute();

        return view;
    }

    private void initView(View view) {
        genreMoviesRecycler = view.findViewById(R.id.genreMoviesRecycler);
        genreMoviesLoading = view.findViewById(R.id.genreMoviesProgressBar);
        backImage = view.findViewById(R.id.backImageGenreMovies);
        genreTextView = view.findViewById(R.id.genreTextView);

        genreMoviesRecycler.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));

        backImage.setOnClickListener(v -> {
            assert getFragmentManager() != null;
            getFragmentManager().popBackStack();
        });

        genreTextView.setText("\"" + genre + "\" movies");
    }

    private class LoadMoviesByGenre extends AsyncTask<Void, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            genreMoviesLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return Database.getInstance().getMoviesByGenre(genre);
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            genreMoviesLoading.setVisibility(View.GONE);
            if (movies != null) {
                genreMoviesAdapter = new SearchMovieListAdapter(movies);
                genreMoviesRecycler.setAdapter(genreMoviesAdapter);
            }
        }
    }
}
