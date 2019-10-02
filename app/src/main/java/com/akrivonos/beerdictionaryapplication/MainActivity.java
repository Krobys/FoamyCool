package com.akrivonos.beerdictionaryapplication;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.akrivonos.beerdictionaryapplication.interfaces.BottomNavigationHideListener;
import com.akrivonos.beerdictionaryapplication.interfaces.MapCoordinatesBreweryListener;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveBackListener;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveToDetailsBeerListener;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveToDetailsBreweryListener;
import com.akrivonos.beerdictionaryapplication.interfaces.TopBarHideListener;
import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;
import com.akrivonos.beerdictionaryapplication.models.BreweryDetailedDescription;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MoveToDetailsBeerListener,
        MoveToDetailsBreweryListener,
        MoveBackListener,
        BottomNavigationHideListener,
        MapCoordinatesBreweryListener,
        TopBarHideListener
{
    public static final String DETAILED_INFO_BEER = "detailed_info_beer";
    public static final String DETAILED_INFO_BREWERY = "detailed_info_brewery";
    public static final String COORDINATES_BREWERIES_SEARCH = "coordinates_breweries_search";
    private Toolbar toolbar;
    private NavController navController;
    private BottomNavigationView navView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search_beer:
                        navController.navigate(R.id.navigation_search_beer);
                    return true;
                case R.id.navigation_search_brewery:
                        navController.navigate(R.id.navigation_map_search);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void moveToDetails(BeerDetailedDescription beerDetailedDescription) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(DETAILED_INFO_BEER ,beerDetailedDescription);
        navController.navigate(R.id.navigation_detailed_info_beer, bundle);
    }

    @Override
    public void moveBack() {
        navController.popBackStack();
    }

    @Override
    public void hideBottomNavMenu() {
        navView.setVisibility(View.GONE);
    }

    @Override
    public void showBottomNavMenu() {
        navView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setResultCoordinatesSearchBreweries(LatLng latLng) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(COORDINATES_BREWERIES_SEARCH, latLng);
        navController.navigate(R.id.navigation_search_brewery, bundle);
    }

    @Override
    public void moveToDetails(BreweryDetailedDescription breweryDetailedDescription) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(DETAILED_INFO_BREWERY, breweryDetailedDescription);
        navController.navigate(R.id.navigation_detailed_info_brewery, bundle);
    }

    @Override
    public void hideToolbar() {
        toolbar.setVisibility(View.GONE);
    }

    @Override
    public void showToolbar() {
        toolbar.setVisibility(View.VISIBLE);
    }
}
