package com.example.jeffr.popularmovieapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jeffr.popularmovieapp.DataObjects.Movie;
import com.example.jeffr.popularmovieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.RecyclerViewHolder> {
    RecyclerViewOnClick recyclerViewOnClick;
    List<Movie> movieList;
    ViewGroup parent;

    public RecyclerviewAdapter(List<Movie> movieList, RecyclerViewOnClick recyclerViewOnClick){
        this.movieList = movieList;
        this.recyclerViewOnClick = recyclerViewOnClick;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent = parent;
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
            holder.movieDate.setText(movieList.get(position).getRelease_date());
            holder.movieOverview.setText(movieList.get(position).getOverview());
            holder.movieTitle.setText(movieList.get(position).getOriginal_title());
            Picasso.with(parent.getContext())
                .load("https://image.tmdb.org/t/p/w500/"+movieList.get(position).getPoster_path())
                .into(holder.moviePoster);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerViewOnClick.rowSelected(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_date_textview)
        TextView movieDate;

        @BindView(R.id.movie_overview_textviewview)
        TextView movieOverview;

        @BindView(R.id.movie_poster_imageview)
        ImageView moviePoster;

        @BindView(R.id.movie_title_textview)
        TextView movieTitle;

        public RecyclerViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
