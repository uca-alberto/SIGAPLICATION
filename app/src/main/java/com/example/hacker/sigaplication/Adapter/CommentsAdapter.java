package com.example.hacker.sigaplication.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hacker.sigaplication.Model.CommentsModel;
import com.example.hacker.sigaplication.R;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {
    private List<CommentsModel> commentsModels;

    public CommentsAdapter(List<CommentsModel>marketModels)
    {
        this.commentsModels=marketModels;
    }
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentsAdapter.ViewHolder viewHolder, int i) {
        CommentsModel commentsModel = commentsModels.get(i);
        viewHolder.Userr.setText(commentsModel.getUser());
        viewHolder.Comment.setText(commentsModel.getComment());
        viewHolder.date.setText(commentsModel.getDate());
        viewHolder.Place.setText(commentsModel.getPlace_visit());
    }

    @Override
    public int getItemCount() {
        return commentsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView Userr;
        TextView Comment;
        TextView Place;
        TextView date;
        public ViewHolder(View intemView)
        {
            super(intemView);
            Userr = intemView.findViewById(R.id.UserPhone);
            Comment = intemView.findViewById(R.id.Comments);
            Place = intemView.findViewById(R.id.PlaceVisit);
            date = intemView.findViewById(R.id.Date);
        }
    }
}
