package com.akrivonos.beerdictionaryapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.beerdictionaryapplication.R;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveToDetailsBreweryListener;
import com.akrivonos.beerdictionaryapplication.models.BreweryDetailedDescription;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class BreweryAdapter extends RecyclerView.Adapter<BreweryAdapter.BreweryViewHolder> {
    private List<BreweryDetailedDescription> breweryList = new ArrayList<>();
    private final LayoutInflater layoutInflater;
    private final MoveToDetailsBreweryListener moveToDetailsBreweryListener;

    public BreweryAdapter(Context context, MoveToDetailsBreweryListener moveToDetailsBreweryListener) {
        layoutInflater = LayoutInflater.from(context);
        this.moveToDetailsBreweryListener = moveToDetailsBreweryListener;
    }

    public void setData(List<BreweryDetailedDescription> breweryList) {
        this.breweryList = breweryList;
    }

    public boolean isSet() {
        return breweryList.size() > 0;
    }

    @NonNull
    @Override
    public BreweryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.brewery_item, parent, false);
        return new BreweryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BreweryViewHolder holder, int position) {
        BreweryDetailedDescription breweryDetailedDescription = breweryList.get(position);
        holder.breweryDetailedDescription = breweryDetailedDescription;
        holder.nameBrewery.setText(breweryDetailedDescription.getNameBrewery());
        if (breweryDetailedDescription.getIconSmallUrl() != null) {
            Glide.with(holder.imageBrewery)
                    .load(breweryDetailedDescription.getIconSmallUrl())
                    .into(holder.imageBrewery);
        }
    }

    @Override
    public int getItemCount() {
        return breweryList.size();
    }

    class BreweryViewHolder extends RecyclerView.ViewHolder {
        final ImageView imageBrewery;
        final TextView nameBrewery;
        BreweryDetailedDescription breweryDetailedDescription;

        BreweryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v -> moveToDetailsBreweryListener.moveToDetails(breweryDetailedDescription));
            imageBrewery = itemView.findViewById(R.id.image_brewery);
            nameBrewery = itemView.findViewById(R.id.name_brewery);
        }
    }
}
