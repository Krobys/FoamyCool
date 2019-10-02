package com.akrivonos.beerdictionaryapplication.fragments;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.akrivonos.beerdictionaryapplication.R;
import com.akrivonos.beerdictionaryapplication.adapters.BeerNameAdapter;
import com.akrivonos.beerdictionaryapplication.interfaces.BottomNavigationHideListener;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveToDetailsBeerListener;
import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;
import com.akrivonos.beerdictionaryapplication.retrofit.RetrofitSearchBeer;
import com.jakewharton.rxbinding3.widget.RxSearchView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.MaybeObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class SearchBeerNameFragment extends Fragment {

    final static String TYPE_BEER = "beer";

    private RecyclerView beerNameRecyclerView;
    private SearchView searchView;
    private BeerNameAdapter beerNameAdapter;
    private Disposable observerBeerDisposable;
    private ProgressBar progressBar;
    private Disposable searchViewDisposable;
    private LinearLayout emptyMessage;
    private BottomNavigationHideListener bottomNavigationHideListener;

    private Observer<ArrayList<BeerDetailedDescription>> observerBeer = new Observer<ArrayList<BeerDetailedDescription>>() {
        @Override
        public void onSubscribe(Disposable d) {
            observerBeerDisposable = d;
        }

        @Override
        public void onNext(ArrayList<BeerDetailedDescription> beerModels) {
            if(beerModels.size() != 0) {
                emptyMessage.setVisibility(View.GONE);
                beerNameAdapter.setData(beerModels);
                progressBar.setVisibility(View.GONE);
                beerNameAdapter.notifyDataSetChanged();
            }else{
                Toast.makeText(getContext(), "No Data", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onComplete() {
        }
    };

    public SearchBeerNameFragment() {
        // Required empty public constructor
    }

    void disposeAll(){
        observerBeerDisposable.dispose();
        searchViewDisposable.dispose();
    }

    void makeObservers(){
        RetrofitSearchBeer.getInstance().setObserverBeerNames(observerBeer);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Activity activity = getActivity();
        if(activity != null){
            MoveToDetailsBeerListener moveToDetailsBeerListener = (MoveToDetailsBeerListener) activity;
            beerNameAdapter = new BeerNameAdapter(activity, moveToDetailsBeerListener);
            bottomNavigationHideListener = (BottomNavigationHideListener) activity;
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_beer_name, container, false);
        setHasOptionsMenu(true);
        progressBar = view.findViewById(R.id.progressBarBeer);

        emptyMessage = view.findViewById(R.id.empty_data_message);
        beerNameRecyclerView = view.findViewById(R.id.beer_name_recycle_view);
        beerNameRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        beerNameRecyclerView.setAdapter(beerNameAdapter);

        makeObservers();
        setUpScreen();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposeAll();
    }

    private void setUpScreen(){
        bottomNavigationHideListener.showBottomNavMenu();
        if(beerNameAdapter.getItemCount() == 0){
            emptyMessage.setVisibility(View.VISIBLE);
        }else {
            emptyMessage.setVisibility(View.GONE);
        }
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_bar_menu_name_beer, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menuItem.getActionView();
        searchViewDisposable = RxSearchView.queryTextChangeEvents(searchView)
                .debounce(400, TimeUnit.MILLISECONDS)
                .map(o -> o.getQueryText().toString())
                .filter(searchText -> searchText.length() > 3 && !TextUtils.isEmpty(searchText))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchText -> {
                    progressBar.setVisibility(View.VISIBLE);
                    RetrofitSearchBeer.getInstance()
                            .startDownloadBeerList(searchText, TYPE_BEER, 1);
                });
        searchView.setIconified(beerNameAdapter.isSetted());
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if(appCompatActivity != null){
            ActionBar actionBar = appCompatActivity.getSupportActionBar();
            if(actionBar != null){
                actionBar.setDisplayHomeAsUpEnabled(false);
                actionBar.setDisplayShowHomeEnabled(false);
                actionBar.setDisplayShowTitleEnabled(false);
            }
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

}
