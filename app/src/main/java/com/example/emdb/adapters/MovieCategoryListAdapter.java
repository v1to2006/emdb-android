package com.example.emdb.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emdb.R;
import com.example.emdb.fragments.MoviesByGenreFragment;

import java.util.ArrayList;

public class MovieCategoryListAdapter extends RecyclerView.Adapter<MovieCategoryListAdapter.ViewHolder> {
    private ArrayList<String> genres;
    private Context context;

    public MovieCategoryListAdapter(ArrayList<String> genres) {
        this.genres = genres;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.view_holder_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCategoryListAdapter.ViewHolder holder, int position) {
        String genre = genres.get(position);
        holder.categoryText.setText(genre);

        holder.itemView.setOnClickListener(v -> {
            openMoviesByGenreFragment(genre);
        });
    }

    @Override
    public int getItemCount() {
        return genres.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryText = itemView.findViewById(R.id.categoryText);
        }
    }

    private void openMoviesByGenreFragment(String genre) {
        Fragment fragment = new MoviesByGenreFragment();
        Bundle bundle = new Bundle();
        bundle.putString("genre", genre);
        fragment.setArguments(bundle);

        ((FragmentActivity) context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
