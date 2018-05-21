package com.example.jeffr.popularmovieapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jeffr.popularmovieapp.R;
import com.example.jeffr.popularmovieapp.dataobjects.Review;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewsRecyclerViewAdapter extends RecyclerView.Adapter<ReviewsRecyclerViewAdapter.ReviewRecyclerViewHolder> {


    List<Review> reviews;
    ViewGroup parent;

     public ReviewsRecyclerViewAdapter(List<Review> reviews){
            this.reviews = reviews;

     }

    @NonNull
    @Override
    public ReviewRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new ReviewsRecyclerViewAdapter.ReviewRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewRecyclerViewHolder holder, int position) {
            holder.comment.setText(reviews.get(position).getContent());
            holder.reviewerName.setText(reviews.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
         if(reviews != null){
             return reviews.size();
         }
         else{
            return 0;

         }

    }

    public class ReviewRecyclerViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.reviewer_comment_textview)
        TextView comment;

        @BindView(R.id.reviewer_name_textview)
        TextView reviewerName;

        public ReviewRecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
