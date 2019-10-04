package com.akrivonos.beerdictionaryapplication.fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.akrivonos.beerdictionaryapplication.R;
import com.akrivonos.beerdictionaryapplication.interfaces.BottomNavigationHideListener;
import com.akrivonos.beerdictionaryapplication.interfaces.MapCoordinatesBreweryListener;
import com.akrivonos.beerdictionaryapplication.interfaces.TopBarHideListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.jakewharton.rxbinding3.view.RxView;

import io.reactivex.disposables.Disposable;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class MapSearchFragment extends Fragment implements OnMapReadyCallback {
    private final static int MY_MAP_PERMISSION_CODE = 12;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private GoogleMap map;
    private MapCoordinatesBreweryListener mapCoordinatesBreweryListener;
    private BottomNavigationHideListener bottomNavigationHideListener;
    private TopBarHideListener topBarHideListener;
    private FusedLocationProviderClient fusedLocationClient;
    private Button chooseButton;
    private Disposable searchOnMapDis;

    public MapSearchFragment() {
        // Required empty public constructor
    }

    private boolean checkPermissionsMap() {
        Activity activity = getActivity();
        if (activity != null)
            if (checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, MY_MAP_PERMISSION_CODE);
            } else {
                return true;
            }
        return false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpListeners();
    }

    private void setUpListeners() {
        Activity activity = getActivity();
        if (activity != null) {
            bottomNavigationHideListener = (BottomNavigationHideListener) activity;
            mapCoordinatesBreweryListener = (MapCoordinatesBreweryListener) activity;
            topBarHideListener = (TopBarHideListener) activity;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_search, container, false);
        setHasOptionsMenu(true);
        setUpScreenAndValues(view);
        setUpMapFragment();
        setUpFusedLocationProvider();
        return view;
    }

    private void setUpScreenAndValues(View view) {
        chooseButton = view.findViewById(R.id.choose_button);
        topBarHideListener.hideToolbar();
        bottomNavigationHideListener.showBottomNavMenu();
    }

    private void setUpMapFragment() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapContainer);
        if (supportMapFragment != null) {
            supportMapFragment.getMapAsync(this);
        }
    }

    private void setUpFusedLocationProvider() {
        Context context = getContext();
        if (context != null) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        topBarHideListener.showToolbar();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        map = googleMap;
        searchOnMapDis = RxView.clicks(chooseButton)
                .subscribe(unit -> {
                    LatLng latLng = googleMap.getCameraPosition().target;
                    mapCoordinatesBreweryListener.setResultCoordinatesSearchBreweries(latLng);
                });
        if (checkPermissionsMap()) {
            enableOwnLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_MAP_PERMISSION_CODE) {
            for (int perm : grantResults) {
                if (perm != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
            }
            enableOwnLocation();
        }
    }

    @SuppressLint("MissingPermission") // потому что вызывается после проверки на пермишены
    private void enableOwnLocation() {
        map.setMyLocationEnabled(true);
        getCurrentLocation();
    }

    private boolean checkGpsModuleEnabled() {
        Activity activity = getActivity();
        if (activity != null) {
            LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            }
        }
        return false;
    }

    @SuppressLint("MissingPermission")
    private void moveToOwnPositionMap() {
        Activity activity = getActivity();
        if (activity != null)
            fusedLocationClient.getLastLocation().addOnSuccessListener(activity, location -> {
                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                    map.moveCamera(cameraUpdate);
                }
            });
    }

    @SuppressLint("MissingPermission") // потому что вызывается после проверки на пермишены
    private void getCurrentLocation() {
        if (checkGpsModuleEnabled()) {
            moveToOwnPositionMap();
        } else {
            Toast.makeText(getContext(), "Please enable gps module", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        searchOnMapDis.dispose();
        super.onDestroy();
    }
}
