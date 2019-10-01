package com.akrivonos.beerdictionaryapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.akrivonos.beerdictionaryapplication.interfaces.MoveBackListener;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveToDetailsBeerListener;
import com.akrivonos.beerdictionaryapplication.interfaces.MoveToDetailsBreweryListener;
import com.akrivonos.beerdictionaryapplication.models.BeerDetailedDescription;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MoveToDetailsBeerListener, MoveToDetailsBreweryListener, MoveBackListener {
    public static final String DETAILED_INFO_BEER = "detailed_info_beer";
    private NavController navController;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_search_beer:
                        navController.navigate(R.id.navigation_search_beer);
                    return true;
                case R.id.navigation_search_brewery:
                        navController.navigate(R.id.navigation_search_brewery);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void moveToDetails(BeerDetailedDescription beerDetailedDescription) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(DETAILED_INFO_BEER ,beerDetailedDescription);
        navController.navigate(R.id.detailedInfoBeerFragment, bundle);
    }

    @Override
    public void moveBack() {
        navController.popBackStack();
    }
}
