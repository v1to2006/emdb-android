package com.example.emdb.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.emdb.R;
import com.example.emdb.adapters.MovieListAdapter;
import com.example.emdb.adapters.SearchMovieListAdapter;
import com.example.emdb.classes.Database;
import com.example.emdb.models.Movie;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private EditText searchText;
    private RecyclerView searchMoviesRecycler;
    private ProgressBar searchMoviesLoading;
    private SearchMovieListAdapter searchMoviesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);
        loadSearchedMovies("");
        return view;
    }

    private void initView(View view) {
        searchText = view.findViewById(R.id.searchText);
        searchMoviesRecycler = view.findViewById(R.id.searchMovieView);
        searchMoviesLoading = view.findViewById(R.id.searchMovieProgressBar);

        searchMoviesRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        searchMoviesAdapter = new SearchMovieListAdapter(new ArrayList<>());
        searchMoviesRecycler.setAdapter(searchMoviesAdapter);

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                loadSearchedMovies(s.toString());
            }
        });
    }

    private void loadSearchedMovies(String query) {
        new LoadSearchedMoviesAsyncTask().execute(query);
    }

    private class LoadSearchedMoviesAsyncTask extends AsyncTask<String, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            searchMoviesLoading.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... queries) {
            String query = queries[0];
            return Database.getInstance().getMovieBySearch(query);
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            searchMoviesLoading.setVisibility(View.GONE);
            if (movies != null) {
                searchMoviesAdapter = new SearchMovieListAdapter(movies);
                searchMoviesRecycler.setAdapter(searchMoviesAdapter);
            }
        }
    }
}
