package com.example.jeffr.popularmovieapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jeffr.popularmovieapp.MovieDBContract;
import com.example.jeffr.popularmovieapp.dataobjects.Movie;
import com.example.jeffr.popularmovieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.RecyclerViewHolder> {
    RecyclerViewOnClick recyclerViewOnClick;
    Cursor cursor;
    ViewGroup parent;

    public RecyclerviewAdapter( RecyclerViewOnClick recyclerViewOnClick){
        this.recyclerViewOnClick = recyclerViewOnClick;
    }

    public void setCursor(Cursor cursor){
        this.cursor = cursor;
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

        if (!cursor.moveToPosition(position))
            return; // bail if returned null

            holder.movieDate.setText(cursor.getString(cursor.getColumnIndex(MovieDBContract.MovieEntry.COLUMN_DATE)));
            holder.movieOverview.setText(cursor.getString(cursor.getColumnIndex(MovieDBContract.MovieEntry.COLUMN_OVERVIEW)));
            holder.movieTitle.setText(cursor.getString(cursor.getColumnIndex(MovieDBContract.MovieEntry.COLUMN_TITLE)));
            Picasso.with(parent.getContext())
                .load("https://image.tmdb.org/t/p/w500/"+cursor.getString(cursor.
                        getColumnIndex(MovieDBContract.MovieEntry.COLUMN_POSTER_PATH)))
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
        return cursor != null ? cursor.getCount() : 0;
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
