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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;

import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.maps.model.LatLng;
import com.minhpvn.restaurantsapp.BaseFragment;
import com.minhpvn.restaurantsapp.R;
import com.minhpvn.restaurantsapp.fragment.adapter.HomeAdapter;
import com.minhpvn.restaurantsapp.fragment.adapter.HomeHighlightAdapter;
import com.minhpvn.restaurantsapp.model.googleMapNearby.Example2;
import com.minhpvn.restaurantsapp.model.googleMapNearby.Result;
import com.minhpvn.restaurantsapp.model.realmObject.SaveMap;
import com.minhpvn.restaurantsapp.ultil.RealmController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HomeFragment extends BaseFragment implements HomeContract.View, LocationListener {
    @BindView(R.id.rcv1) RecyclerView rcv1;
    @BindView(R.id.imgProfile) ProfilePictureView imgProfile;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.rcv2) RecyclerView rcv2;
    @BindView(R.id.swipeRefresh) SwipeRefreshLayout swipeRefresh;
    //    @BindView(R.id.nestedScroll) NestedScrollView nestedScroll;
    @BindView(R.id.iv_loading) GifImageView ivLoading;
    @BindView(R.id.ln_loading) LinearLayout lnLoading;
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
    private List<Result> lstTop = new ArrayList<>();
    private List<Result> lstHighlight = new ArrayList<>();
    private List<String> linkPhoto = new ArrayList<>();
    String token = "";
    private Realm realm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        criteria = new Criteria();
        mPresenter = new HomePresenter(this);
        imgProfile.setProfileId("2076095055735083");
        initViews();
        initControls();
        getLocation();

        return view;
    }

    private void initViews() {
        this.realm = RealmController.with(this).getRealm();
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

    int positionLast, positionStart = 0;

    @Override
    public void getNearbyPlacesSuccess(final Response<Example2> response) {
//        nestedScroll.fullScroll(View.FOCUS_UP);
//        nestedScroll.smoothScrollTo(0, 0);
        rcv1.smoothScrollToPosition(0);
        swipeRefresh.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        lstResult.clear();

        lnLoading.setVisibility(View.GONE);
        if (response != null) {
            lstResult.addAll(response.body().getResults());
            lstTop.addAll(response.body().getResults());
            token = response.body().getNextPageToken();
        }


        if (begin != null) {
            Collections.sort(lstTop, new Comparator<Result>() {
                @Override
                public int compare(Result o1, Result o2) {
                    if (o1.getRating() != null && o2.getRating() != null) {
                        double delta = o1.getRating() - o2.getRating();
                        if (delta > 0) return 1;
                        if (delta < 0) return -1;
                        return 0;
                    }

                    return 0;
                }
            });
            Collections.sort(lstResult, new SortPlaces(begin));

            for (int i = 0; i < lstResult.size(); i++) {
                double a = getDistanceBetweenTwoPoints2(begin.latitude, begin.longitude, lstResult.get(i).getGeometry().getLocation().getLat(), lstResult.get(i).getGeometry().getLocation().getLng());
                Log.d("getdistanceaaa", a + " m");
                lstResult.get(i).setKm(a);

            }
        }


        homeAdapter = new HomeAdapter(getApplicationContext(), lstResult, new HomeAdapter.OnItemClick() {
            @Override
            public void onClick(Result item) {
//                ItemRestaurentDetail itemRestaurentDetail = new ItemRestaurentDetail();
//                itemRestaurentDetail.show(getFragmentManager(), "itemRestaurentDetail");
                Date date = new Date();
                SaveMap saveMap = new SaveMap();
                saveMap.setKm(item.getKm());
                saveMap.setDescription(item.getVicinity());
//                saveMap.setLatLng(new LatLng(item.getGeometry().getLocation().getLat(), item.getGeometry().getLocation().getLng()));
                saveMap.setName(item.getName());
                saveMap.setDate(date.toString());
                if (item.getOpeningHours() != null)
                    saveMap.setOpen(item.getOpeningHours().getOpenNow() != null ? item.getOpeningHours().getOpenNow() : false);
                if (item.getRating() != null)
                    saveMap.setStar(item.getRating());
                realm.beginTransaction();
                realm.copyToRealm(saveMap);
                realm.commitTransaction();
                RestaurentFragment restaurentFragment = new RestaurentFragment();
                restaurentFragment.setDestination(new LatLng(item.getGeometry().getLocation().getLat(), item.getGeometry().getLocation().getLng()));
                restaurentFragment.setData(item.getName(), item.getVicinity());
                addFragmentWithTag(restaurentFragment, "RestaurentFragment");
            }
        });
        rcv1.setAdapter(homeAdapter);

        rcv1.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rcv1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                positionLast = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                positionStart = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                Log.d("AbcpositionStart", positionStart + "");
                Log.d("AbcpositionLast", positionLast + "");
                if (positionLast == lstResult.size() - 1) {
//                    mPresenter.getNearbyPlaces("restaurant", begin.latitude + "," + begin.longitude, 500, token);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        List<Result> x = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            x.add(lstTop.get(i));
        }
        homeHighlightAdapter = new HomeHighlightAdapter(getApplicationContext(), x, new HomeHighlightAdapter.OnItemClick() {
            @Override
            public void onClick(Result item) {
                SaveMap saveMap = new SaveMap();
                saveMap.setKm(item.getKm());
                saveMap.setDescription(item.getVicinity());
//                saveMap.setLatLng(new LatLng(item.getGeometry().getLocation().getLat(), item.getGeometry().getLocation().getLng()));
                saveMap.setName(item.getName());

                saveMap.setStar(item.getRating());
                realm.beginTransaction();
                realm.copyToRealm(saveMap);
                realm.commitTransaction();
                RestaurentFragment restaurentFragment = new RestaurentFragment();
                restaurentFragment.setDestination(new LatLng(item.getGeometry().getLocation().getLat(), item.getGeometry().getLocation().getLng()));
                restaurentFragment.setData(item.getName(), item.getVicinity());
                addFragmentWithTag(restaurentFragment, "RestaurentFragment");
            }
        });
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
            begin = new LatLng(location.getLatitude(), location.getLongitude());
        swipeRefresh.setRefreshing(false);
        lnLoading.setVisibility(View.VISIBLE);
        mPresenter.getNearbyPlaces("restaurant", location.getLatitude() + "," + location.getLongitude(), 500, "");

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

    public void addFragmentWithTag(Fragment targetFragment, String tag) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.pull_in_right, R.anim.push_out_left, R.anim.pull_in_left, R.anim.push_out_right);
        if (targetFragment != null) {
            ft.addToBackStack(null);
            ft.add(R.id.container_home, targetFragment, tag).commitAllowingStateLoss();
        }
    }


    public class SortPlaces implements Comparator<Result> {
        LatLng currentLoc;

        public SortPlaces(LatLng current) {
            currentLoc = current;
        }

        @Override
        public int compare(final Result place1, final Result place2) {
            double lat1 = place1.getGeometry().getLocation().getLat();
            double lon1 = place1.getGeometry().getLocation().getLng();
            double lat2 = place2.getGeometry().getLocation().getLat();
            double lon2 = place2.getGeometry().getLocation().getLng();
            double distanceToPlace1 = getDistanceBetweenTwoPoints2(currentLoc.latitude, currentLoc.longitude, lat1, lon1);
            double distanceToPlace2 = getDistanceBetweenTwoPoints2(currentLoc.latitude, currentLoc.longitude, lat2, lon2);
            Log.d("desmcmm", (int) (distanceToPlace1 - distanceToPlace2) + "");
            return (int) (distanceToPlace1 - distanceToPlace2);
        }


    }

    public Double getDistanceBetweenTwoPoints(Double latitude1, Double longitude1, Double latitude2, Double longitude2) {
        double radius = 6378137;   // approximate Earth radius, *in meters*
        double deltaLat = latitude2 - latitude1;
        double deltaLon = longitude2 - longitude1;
        double angle = 2 * Math.asin(Math.sqrt(
                Math.pow(Math.sin(deltaLat / 2), 2) +
                        Math.cos(latitude1) * Math.cos(latitude2) *
                                Math.pow(Math.sin(deltaLon / 2), 2)));
        return radius * angle;
    }

    public Double getDistanceBetweenTwoPoints2(Double latitude1, Double longitude1, Double latitude2, Double longitude2) {
        final int RADIUS_EARTH = 6378137;

        double dLat = getRad(latitude2 - latitude1);
        double dLong = getRad(longitude2 - longitude1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(getRad(latitude1)) * Math.cos(getRad(latitude2)) * Math.sin(dLong / 2) * Math.sin(dLong / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        return (RADIUS_EARTH * c) * 1000;
        return RADIUS_EARTH * c;
    }

    private Double getRad(Double x) {
        return x * Math.PI / 180;
    }

}
