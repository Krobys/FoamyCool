package com.akrivonos.beerdictionaryapplication.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akrivonos.beerdictionaryapplication.R;
import com.akrivonos.beerdictionaryapplication.adapters.BeerNameAdapter;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveBackListener;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveToDetailsBeerListener;
import com.akrivonos.beerdictionaryapplication.room.RoomAppDatabase;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FavoriteBeerFragment extends Fragment {
    private MoveBackListener moveBackListener;
    private RecyclerView recyclerViewFavorite;
    private BeerNameAdapter beerNameAdapterFavorites;
    private RoomAppDatabase appDatabase;
    private Disposable favoritesBeerDisposable;
    private ConstraintLayout emptyMessage;
    private final ItemTouchHelper.Callback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
           switch (direction){
               case ItemTouchHelper.LEFT:
                   String beerId = beerNameAdapterFavorites.getItem(viewHolder.getAdapterPosition()).getId();
                   appDatabase.favoriteBeerDao().setBeerNotFavorite(beerId);
                   break;
           }
        }
    };

    public FavoriteBeerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = getActivity();
        if(activity != null){
            MoveToDetailsBeerListener moveToDetailsBeerListener = (MoveToDetailsBeerListener) activity;
            beerNameAdapterFavorites = new BeerNameAdapter(activity, moveToDetailsBeerListener);
            moveBackListener = (MoveBackListener) activity;
            appDatabase = RoomAppDatabase.getDatabase(activity);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_beer, container, false);
        setHasOptionsMenu(true);
        recyclerViewFavorite = view.findViewById(R.id.recycler_favorite_beer);
        recyclerViewFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFavorite.setAdapter(beerNameAdapterFavorites);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewFavorite);
        emptyMessage = view.findViewById(R.id.empty_data_message);
        favoritesBeerDisposable = appDatabase.favoriteBeerDao().getFavoritesBeer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(favoritesBeerList -> {
                    if(favoritesBeerList.size() > 0){
                        beerNameAdapterFavorites.setData(favoritesBeerList);
                        beerNameAdapterFavorites.notifyDataSetChanged();
                        emptyMessage.setVisibility(View.GONE);
                    }else {
                        emptyMessage.setVisibility(View.VISIBLE);
                    }

                });
        setUpActionBar();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposeAll();
    }

    private void disposeAll(){
        favoritesBeerDisposable.dispose();
    }

    private void setUpActionBar(){
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if(appCompatActivity != null){
            ActionBar actionBar = appCompatActivity.getSupportActionBar();
            if(actionBar != null){
                actionBar.setDisplayShowTitleEnabled(true);
                actionBar.setTitle("Favorite Beer");
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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
