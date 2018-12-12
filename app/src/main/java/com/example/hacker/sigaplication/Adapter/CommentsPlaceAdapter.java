package com.example.hacker.sigaplication.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hacker.sigaplication.Model.CommentsModel;
import com.example.hacker.sigaplication.Model.PlaceModel;
import com.example.hacker.sigaplication.R;

import java.util.List;

public class CommentsPlaceAdapter extends RecyclerView.Adapter<CommentsPlaceAdapter.ViewHolder> {
    private List<PlaceModel> placeModels;

    public CommentsPlaceAdapter(List<PlaceModel>placeModels)
    {
        this.placeModels=placeModels;
    }

    @NonNull
    @Override
    public CommentsPlaceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemplace,parent,false);
        return new CommentsPlaceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsPlaceAdapter.ViewHolder holder, int position) {
        PlaceModel placeModel = placeModels.get(position);
        holder.Userr.setText(placeModel.getUser());
        holder.danger.setText(placeModel.getDangerNivel());
        holder.date.setText(placeModel.getDate());
        holder.Place.setText(placeModel.getPlace_Visit());
    }

    @Override
    public int getItemCount() {
        return placeModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView Userr;
        TextView danger;
        TextView Place;
        TextView date;
        public ViewHolder(View intemView)
        {
            super(intemView);
            Userr = intemView.findViewById(R.id.UserPhoneplace);
            danger = intemView.findViewById(R.id.Danger);
            Place = intemView.findViewById(R.id.PlaceVisitt);
            date = intemView.findViewById(R.id.Dateplace);
        }
    }
}
