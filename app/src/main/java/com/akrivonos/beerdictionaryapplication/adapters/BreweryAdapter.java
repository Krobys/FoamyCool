package com.akrivonos.beerdictionaryapplication.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BreweryAdapter extends RecyclerView.Adapter<BreweryAdapter.BreweryViewHolder> {

    @NonNull
    @Override
    public BreweryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BreweryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class BreweryViewHolder extends RecyclerView.ViewHolder {

        public BreweryViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
