package com.exclusive.original.impression_tracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ImpressionAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(parent.getContext()).inflate( R.layout.unit_impression, parent, false ));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            viewHolder vH = (viewHolder) holder;
            vH.position.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return 50;
    }


    public class viewHolder extends RecyclerView.ViewHolder{
        TextView position;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            position = itemView.findViewById(R.id.position);
        }
    }

}
