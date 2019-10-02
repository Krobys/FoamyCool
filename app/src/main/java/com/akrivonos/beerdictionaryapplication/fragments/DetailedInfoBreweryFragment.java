package com.akrivonos.beerdictionaryapplication.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.beerdictionaryapplication.R;
import com.akrivonos.beerdictionaryapplication.adapters.BeerNameAdapter;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveBackListener;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveToDetailsBeerListener;
import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;
import com.akrivonos.beerdictionaryapplication.models.BreweryDetailedDescription;
import com.akrivonos.beerdictionaryapplication.retrofit.RetrofitSearchBeer;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.akrivonos.beerdictionaryapplication.MainActivity.DETAILED_INFO_BREWERY;

public class DetailedInfoBreweryFragment extends Fragment {
    private Disposable observerBeerDisposable;
    private MoveBackListener moveBackListener;
    private BeerNameAdapter beerNameAdapter;
    private ImageView imageBrewery;
    private TextView descriptionBrewery;
    private ProgressBar progressBar;
    private ConstraintLayout noBeerMessage;

    private final Observer<ArrayList<BeerDetailedDescription>> observerBeer = new Observer<ArrayList<BeerDetailedDescription>>() {
        @Override
        public void onSubscribe(Disposable d) {
            observerBeerDisposable = d;
        }

        @Override
        public void onNext(ArrayList<BeerDetailedDescription> beerModels) {
            if(beerModels.size() != 0) {
                beerNameAdapter.setData(beerModels);
                beerNameAdapter.notifyDataSetChanged();
            }else{
                noBeerMessage.setVisibility(View.VISIBLE);
            }
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
        }
    };

    public DetailedInfoBreweryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!= null){
            BreweryDetailedDescription breweryDetailedDescription = bundle.getParcelable(DETAILED_INFO_BREWERY);
            if(breweryDetailedDescription != null){
                String idBrewery = breweryDetailedDescription.getIdBrewery();
                startLoadBeerData(idBrewery);
            }
        }
        Activity activity = getActivity();
        if(activity != null){
            MoveToDetailsBeerListener moveToDetailsBeerListener = (MoveToDetailsBeerListener) activity;
            beerNameAdapter = new BeerNameAdapter(activity, moveToDetailsBeerListener);
            moveBackListener = (MoveBackListener) activity;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_info_brewery, container, false);
        setHasOptionsMenu(true);
        RecyclerView recyclerViewBeers = view.findViewById(R.id.recycler_view_beers_in_brewery);
        recyclerViewBeers.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewBeers.setAdapter(beerNameAdapter);

        noBeerMessage = view.findViewById(R.id.no_beer_message);
        imageBrewery = view.findViewById(R.id.image_brewery);
        descriptionBrewery = view.findViewById(R.id.description_brewery);
        progressBar = view.findViewById(R.id.progressBarBeer);

        makeObservers();
        setUpScreen();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposeAll();
    }

    private void makeObservers(){
        RetrofitSearchBeer.getInstance().setObserverBeerNames(observerBeer);
    }

    private void disposeAll(){
        observerBeerDisposable.dispose();
    }

    private void startLoadBeerData(String idBrewery){
        RetrofitSearchBeer.getInstance().startDownloadBeersListInBrewery(idBrewery);
    }

    private void setUpScreen(){
        Bundle bundle = getArguments();
        if(bundle != null){
            BreweryDetailedDescription breweryDetailedDescription = bundle.getParcelable(DETAILED_INFO_BREWERY);
            if(breweryDetailedDescription != null){
                if(breweryDetailedDescription.getIconBigUrl() != null){
                    Glide.with(imageBrewery)
                            .load(breweryDetailedDescription.getIconBigUrl())
                            .into(imageBrewery);
                }
                String description = breweryDetailedDescription.getDescriptionBrewery();
                if(description != null)
                descriptionBrewery.setText((!description.equals("")) ? description: breweryDetailedDescription.getNameBrewery());
                AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
                if(appCompatActivity != null){
                    ActionBar actionBar = appCompatActivity.getSupportActionBar();
                    if(actionBar != null){
                        actionBar.setTitle(breweryDetailedDescription.getNameBrewery());
                    }
                }
            }
        }
        if(beerNameAdapter.isSetted()){
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            moveBackListener.moveBack();
            return true;
        }
        return false;
    }

}
