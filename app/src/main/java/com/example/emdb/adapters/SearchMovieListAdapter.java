package com.example.emdb.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.emdb.R;
import com.example.emdb.activities.DetailActivity;
import com.example.emdb.models.Movie;

import java.util.ArrayList;

public class SearchMovieListAdapter extends RecyclerView.Adapter<SearchMovieListAdapter.ViewHolder> {
    private ArrayList<Movie> movies;
    private Context context;

    public SearchMovieListAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.view_holder_search_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.movieTitleText.setText(movie.getTitle());
        holder.movieRatingText.setText("" + movie.getRating());
        holder.movieYearText.setText(movie.getReleaseYear());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));

        Glide.with(context)
                .load(movie.getImage())
                .apply(requestOptions)
                .into(holder.movieImage);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("id", movie.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView movieTitleText;
        TextView movieRatingText;
        TextView movieYearText;
        ImageView movieImage;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitleText = itemView.findViewById(R.id.movieTitleText);
            movieRatingText = itemView.findViewById(R.id.movieRatingText);
            movieYearText = itemView.findViewById(R.id.movieYearText);
            movieImage = itemView.findViewById(R.id.movieImageView);
        }
    }
}
