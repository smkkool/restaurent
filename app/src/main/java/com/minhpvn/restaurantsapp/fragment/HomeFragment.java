package com.minhpvn.restaurantsapp.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.maps.model.LatLng;
import com.minhpvn.restaurantsapp.BaseFragment;
import com.minhpvn.restaurantsapp.R;
import com.minhpvn.restaurantsapp.fragment.adapter.HomeAdapter;
import com.minhpvn.restaurantsapp.fragment.adapter.HomeHighlightAdapter;
import com.minhpvn.restaurantsapp.model.googleMapNearby.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeFragment extends BaseFragment implements HomeContract.View, LocationListener {
    @BindView(R.id.rcv1) RecyclerView rcv1;
    @BindView(R.id.imgProfile) ProfilePictureView imgProfile;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.rcv2) RecyclerView rcv2;
    @BindView(R.id.swipeRefresh) SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.nestedScroll) NestedScrollView nestedScroll;
    private HomeContract.Presenter mPresenter;
    private HomeAdapter homeAdapter;
    private HomeHighlightAdapter homeHighlightAdapter;
    LatLng begin;
    private LocationManager locationManager;
    private String provider;
    public static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 1234;
    private Criteria criteria;
    private Location location2;
    private List<Result> lstResult = new ArrayList<>();
    private List<String> linkPhoto = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        criteria = new Criteria();
        mPresenter = new HomePresenter(this);
        imgProfile.setProfileId("2076095055735083");
        initControls();
        getLocation();

        return view;
    }

    private void initControls() {
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefresh.setRefreshing(true);
                progressBar.setVisibility(View.VISIBLE);
                getLocation();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(getApplicationContext(),
                                    Manifest.permission.ACCESS_COARSE_LOCATION)
                                    == PackageManager.PERMISSION_GRANTED) {

                        getLocation();
                        return;
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void getNearbyPlacesSuccess(List<Result> response) {
        nestedScroll.fullScroll(View.FOCUS_UP);
        nestedScroll.smoothScrollTo(0,0);
        swipeRefresh.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        lstResult.clear();

        if (response != null)
            lstResult.addAll(response);
        progressBar.setVisibility(View.GONE);
        homeAdapter = new HomeAdapter(getApplicationContext(), response);
        rcv1.setAdapter(homeAdapter);
        rcv1.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        Collections.sort(lstResult, new Comparator<Result>() {
            @Override
            public int compare(Result o1, Result o2) {
                if (o1.getRating() != null && o2.getRating() != null) {
                    return o2.getRating().compareTo(o1.getRating());

                } else return 0;
            }
        });

        List<Result> x = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            x.add(lstResult.get(i));
        }
        homeHighlightAdapter = new HomeHighlightAdapter(getApplicationContext(), x);
        rcv2.setAdapter(homeHighlightAdapter);
        rcv2.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));
        homeAdapter.notifyDataSetChanged();
        homeHighlightAdapter.notifyDataSetChanged();


    }

    @Override
    public void getPhotoSuccess(Response<String> response) {
        linkPhoto.add(response.toString());
        Log.d("linkPhoto", linkPhoto.size() + "");
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onLocationChanged(Location location) {
        if (location != null)
            mPresenter.getNearbyPlaces("restaurant", location.getLatitude() + "," + location.getLongitude(), 1500);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("LocationListener", "onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("LocationListener", "onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("LocationListener", "onProviderDisabled");
    }

    private boolean isGPSEnabled, isNetworkEnabled, canGetLocation;
    private double latitude, longitude;

    public Location getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext()
                    .getSystemService(Context.LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                canGetLocation = true;
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                1,
                                15, this);
                    } else {
                        requestPermissions(
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_ACCESS_FINE_LOCATION);
                    }

                    Log.d("Network", "Network Enabled");
                    if (locationManager != null) {
                        location2 = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location2 != null) {
                            latitude = location2.getLatitude();
                            longitude = location2.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location2 == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                1,
                                15, this);
                        Log.d("GPS", "GPS Enabled");
                        if (locationManager != null) {
                            location2 = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location2 != null) {
                                latitude = location2.getLatitude();
                                longitude = location2.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location2;
    }
}
