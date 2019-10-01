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
import com.akrivonos.beerdictionaryapplication.interfaces.MoveToDetailsBeerListener;
import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;
import com.akrivonos.beerdictionaryapplication.models.BeerModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class BeerNameAdapter extends RecyclerView.Adapter<BeerNameAdapter.BeerTypeViewHolder> {
    private MoveToDetailsBeerListener moveToDetailsBeerListener;
    private List<BeerDetailedDescription> beerTypesList = new ArrayList<>();
    private final LayoutInflater layoutInflater;

    public BeerNameAdapter(Context context, MoveToDetailsBeerListener moveToDetailsBeerListener){
        this.moveToDetailsBeerListener = moveToDetailsBeerListener;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setData(List<BeerDetailedDescription> beerList){
        beerTypesList = beerList;
    }

    public void addData(List<BeerDetailedDescription> beerList){
        beerTypesList.addAll(beerList);
    }

    public void throwOffData(){
        beerTypesList.clear();
    }

    public boolean isSetted(){
        return beerTypesList.size() > 0;
    }

    @NonNull
    @Override
    public BeerTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.beer_item, parent, false);
        return new BeerTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BeerTypeViewHolder holder, int position) {
        BeerDetailedDescription detailsBeer = beerTypesList.get(position);
        holder.beerDetailedDescription = detailsBeer;
        holder.nameBeerTextView.setText(detailsBeer.getNameBeer());
        if(detailsBeer.getIconSmallUrl() != null)
        Glide.with(holder.iconBeerImageView)
                    .load(detailsBeer.getIconSmallUrl())
                    .into(holder.iconBeerImageView);

    }

    @Override
    public int getItemCount() {
        return beerTypesList.size();
    }

    class BeerTypeViewHolder extends RecyclerView.ViewHolder {

        ImageView iconBeerImageView;
        TextView nameBeerTextView;
        BeerDetailedDescription beerDetailedDescription;

        BeerTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(view -> moveToDetailsBeerListener.moveToDetails(beerDetailedDescription));
            iconBeerImageView = itemView.findViewById(R.id.icon_beer);
            nameBeerTextView = itemView.findViewById(R.id.name_beer);

        }
    }
}
