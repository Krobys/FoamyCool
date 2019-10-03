package com.akrivonos.beerdictionaryapplication.adapters;

import android.content.Context;
import android.util.Log;
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
import com.akrivonos.beerdictionaryapplication.models.PageSettingsDownloading;
import com.akrivonos.beerdictionaryapplication.retrofit.RetrofitSearchBeer;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.akrivonos.beerdictionaryapplication.fragments.SearchBeerNameFragment.TYPE_BEER;

public class BeerNameAdapter extends RecyclerView.Adapter<BeerNameAdapter.BeerTypeViewHolder> {
    private final MoveToDetailsBeerListener moveToDetailsBeerListener;
    private Disposable disposablePageSettings;
    private List<BeerDetailedDescription> beerTypesList = new ArrayList<>();
    private PageSettingsDownloading pageSettingsDownloadingAdapter;
    private String beerTypeName;
    private final LayoutInflater layoutInflater;

    public BeerNameAdapter(Context context, MoveToDetailsBeerListener moveToDetailsBeerListener){
        this.moveToDetailsBeerListener = moveToDetailsBeerListener;
        layoutInflater = LayoutInflater.from(context);
        Observer<PageSettingsDownloading> pageSettingsDownloadingObserver = new Observer<PageSettingsDownloading>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposablePageSettings = d;
            }

            @Override
            public void onNext(PageSettingsDownloading pageSettingsDownloading) {
                pageSettingsDownloadingAdapter = pageSettingsDownloading;
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
            }
        };
        RetrofitSearchBeer.getInstance().setObserverPageSettingsAdapter(pageSettingsDownloadingObserver);
    }

    public void disposePageObserver(){
        disposablePageSettings.dispose();
    }

    public void setData(List<BeerDetailedDescription> beerList){
        beerTypesList = beerList;
    }

    public void addData(List<BeerDetailedDescription> beerList){
        String addedBeerName = beerList.get(0).getNameBeer();
        if(beerList.size() != 0 && !addedBeerName.equals(beerTypeName)){
            beerTypesList.clear();
            beerTypeName = addedBeerName;
        }
        beerTypesList.addAll(beerList);
    }

    public BeerDetailedDescription getItem(int position){
        return beerTypesList.get(position);
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
    public void onViewAttachedToWindow(@NonNull BeerTypeViewHolder holder) {
        Log.d("test", "onViewAttachedToWindow: "+holder.getAdapterPosition());
        if(holder.getAdapterPosition() + 3 == beerTypesList.size()){
            if(pageSettingsDownloadingAdapter != null){
                int currentPage = pageSettingsDownloadingAdapter.getCurrentPage();
                int pagesAmount = pageSettingsDownloadingAdapter.getPagesAmount();
                if(currentPage < pagesAmount)
                    RetrofitSearchBeer.getInstance().startDownloadBeerList(beerTypeName, TYPE_BEER, pageSettingsDownloadingAdapter.getCurrentPage()+1);
            }
        }
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public int getItemCount() {
        return beerTypesList.size();
    }

    class BeerTypeViewHolder extends RecyclerView.ViewHolder {

        final ImageView iconBeerImageView;
        final TextView nameBeerTextView;
        BeerDetailedDescription beerDetailedDescription;

        BeerTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(view -> moveToDetailsBeerListener.moveToDetails(beerDetailedDescription));
            iconBeerImageView = itemView.findViewById(R.id.icon_beer);
            nameBeerTextView = itemView.findViewById(R.id.name_beer);

        }
    }
}
