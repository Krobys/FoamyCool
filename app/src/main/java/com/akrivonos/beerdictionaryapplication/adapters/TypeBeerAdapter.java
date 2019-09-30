package com.akrivonos.beerdictionaryapplication.adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TypeBeerAdapter extends RecyclerView.Adapter<TypeBeerAdapter.BeerTypeViewHolder> {

    @NonNull
    @Override
    public BeerTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BeerTypeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class BeerTypeViewHolder extends RecyclerView.ViewHolder {

        public BeerTypeViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
