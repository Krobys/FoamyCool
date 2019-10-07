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

import com.akrivonos.beerdictionaryapplication.BeerModelData;
import com.akrivonos.beerdictionaryapplication.R;
import com.akrivonos.beerdictionaryapplication.adapters.BeerNameAdapter;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveBackListener;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveToDetailsBeerListener;
import com.akrivonos.beerdictionaryapplication.interfaces.mvp_listeners.view_control_listeners.FavoriteBeerViewPresenterListener;
import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;
import com.akrivonos.beerdictionaryapplication.presenters.FavoriteBeerPresenter;

import java.util.List;

public class FavoriteBeerFragment extends Fragment implements FavoriteBeerViewPresenterListener {
    private MoveBackListener moveBackListener;
    private BeerNameAdapter beerNameAdapterFavorites;
    private ConstraintLayout emptyMessage;
    private FavoriteBeerPresenter favoriteBeerPresenter;
    private RecyclerView recyclerViewFavorite;
    private final ItemTouchHelper.Callback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if (direction == ItemTouchHelper.LEFT) {
                String beerId = beerNameAdapterFavorites.getItem(viewHolder.getAdapterPosition()).getId();
                favoriteBeerPresenter.setBeerNotFavorite(beerId);
            }
        }
    };

    public FavoriteBeerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpAdapterAndListeners();
    }

    private void setUpAdapterAndListeners() {
        Activity activity = getActivity();
        if (activity != null) {
            MoveToDetailsBeerListener moveToDetailsBeerListener = (MoveToDetailsBeerListener) activity;
            beerNameAdapterFavorites = new BeerNameAdapter(activity, moveToDetailsBeerListener);
            moveBackListener = (MoveBackListener) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_beer, container, false);
        setHasOptionsMenu(true);
        setUpPresenter();
        setUpScreenAndValues(view);
        setUpActionBar();
        return view;
    }

    private void setUpPresenter(){
        BeerModelData beerModelData = BeerModelData.getInstance(getContext());
        favoriteBeerPresenter = new FavoriteBeerPresenter(beerModelData, this);
    }

    private void setUpScreenAndValues(View view) {
        recyclerViewFavorite = view.findViewById(R.id.recycler_favorite_beer);
        emptyMessage = view.findViewById(R.id.empty_data_message);
        recyclerViewFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFavorite.setAdapter(beerNameAdapterFavorites);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewFavorite);
        favoriteBeerPresenter.loadFavoriteBeerList();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        favoriteBeerPresenter.detachView();
    }

    private void setUpActionBar() {
        AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
        if (appCompatActivity != null) {
            ActionBar actionBar = appCompatActivity.getSupportActionBar();
            if (actionBar != null) {
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
        if (item.getItemId() == android.R.id.home) {
            moveBackListener.moveBack();
            return true;
        }
        return false;
    }

    @Override
    public void showBeerList(List<BeerDetailedDescription> beerDetailedDescriptions) {
        beerNameAdapterFavorites.setData(beerDetailedDescriptions);
        beerNameAdapterFavorites.notifyDataSetChanged();
    }

    @Override
    public void setVisibilityEmptyMessage(int visibility) {
        emptyMessage.setVisibility(visibility);
    }

    @Override
    public void setVisibilityRecView(int visibility) {
        recyclerViewFavorite.setVisibility(visibility);
    }
}
