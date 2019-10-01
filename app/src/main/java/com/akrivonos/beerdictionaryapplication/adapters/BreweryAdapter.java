package com.akrivonos.beerdictionaryapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.beerdictionaryapplication.R;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveToDetailsBreweryListener;

public class BreweryAdapter extends RecyclerView.Adapter<BreweryAdapter.BreweryViewHolder> {

    private LayoutInflater layoutInflater;
    private MoveToDetailsBreweryListener moveToDetailsBreweryListener;

    public BreweryAdapter(Context context, MoveToDetailsBreweryListener moveToDetailsBreweryListener){
        layoutInflater = LayoutInflater.from(context);
        this.moveToDetailsBreweryListener = moveToDetailsBreweryListener;
    }

    @NonNull
    @Override
    public BreweryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.brewery_item, parent, false);
        return new BreweryViewHolder(view);
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
