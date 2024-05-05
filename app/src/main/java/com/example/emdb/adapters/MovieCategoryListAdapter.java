package com.example.emdb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emdb.R;

import java.util.ArrayList;

public class MovieCategoryListAdapter extends RecyclerView.Adapter<MovieCategoryListAdapter.ViewHolder> {
    private ArrayList<String> genres;
    private ArrayList<String> movieGenres;
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
            //Intent intent = new Intent(context, DetailActivity.class);
            //intent.putExtra("genre", genre);
            //context.startActivity(intent);
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
}
