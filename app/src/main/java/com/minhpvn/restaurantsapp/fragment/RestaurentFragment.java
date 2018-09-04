package com.minhpvn.restaurantsapp.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.minhpvn.restaurantsapp.BaseFragment;
import com.minhpvn.restaurantsapp.R;
import com.minhpvn.restaurantsapp.fragment.adapter.GoogleMapPlaceAdapter;
import com.minhpvn.restaurantsapp.model.MapPoint;
import com.minhpvn.restaurantsapp.model.googleMapModel.AddressDetailResponse;
import com.minhpvn.restaurantsapp.model.googleMapModel.Example;
import com.minhpvn.restaurantsapp.model.googleMapModel.QueryAddressResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.davidea.flexibleadapter.common.SmoothScrollLinearLayoutManager;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class RestaurentFragment extends BaseFragment implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMapLongClickListener,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener,
        LocationListener, RestaurentContract.View {
    private static final String TAG = "LOG_BUG_R";
    GoogleApiClient mGoogleApiClient;
    @BindView(R.id.pin_location) ImageView pinLocation;
    @BindView(R.id.info_title) TextView infoTitle;
    @BindView(R.id.info_address) TextView infoAddress;
    @BindView(R.id.info_phone) TextView infoPhone;
    @BindView(R.id.info_description) TextView infoDescription;
    @BindView(R.id.btn_call_support) LinearLayout btnCallSupport;
    @BindView(R.id.btn_show_direction) LinearLayout btnShowDirection;
    @BindView(R.id.info_layout) LinearLayout infoLayout;
    @BindView(R.id.showDirect) TextView showDirect;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public static final String GOOGLE_API_KEY = "AIzaSyAocTT3gezvFMp_RIbgm1sb3bTce2g9fa0";
    //    @BindView(R.id.btn_clear) ImageView btnClear;
//    @BindView(R.id.btn_select_location) ImageView btnSelectLocation;
    //    @BindView(R.id.edt_search) AppCompatEditText edtSearch;
    @BindView(R.id.list_result) RecyclerView listResult;
    @BindView(R.id.fake_view) View fakeView;
    @BindView(R.id.empty_warning) TextView emptyWarning;
    //    @BindView(R.id.search_box) CardView searchBox;
    @BindView(R.id.result_layout) CardView resultLayout;
    @BindView(R.id.pin_location2) ImageView pinLocation2;
    @BindView(R.id.ivClose) ImageView ivClose;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private Marker mMarker;
    private LocationRequest mLocationRequest;
    private ViewAnimator viewAnimator;
    private MapPoint[] mapPointList;
    private RestaurentContract.Presenter mPresenter;
    private Polyline line;
    private LatLng begin;
    private LatLng des;
    private Handler handler = new Handler();
    private List<QueryAddressResponse.Predictions> listData = new ArrayList<>();
    private GoogleMapPlaceAdapter googleMapPlaceAdapter;
    LatLng currentPoint;
    LatLng center;
    private
    ContainerFragment containerFragment;
    Animation animationbbb, fadeIn, fadeOut;
    boolean isShow = true;
    Drawable drawable;
    UiSettings uiSettings;

    public LatLng getBegin() {
        return begin;
    }

    private LatLng destination;
    private String name, address;

    public void setDestination(LatLng destination) {
        this.destination = destination;
    }

    public void setData(String name, String address) {
        this.name = name;
        this.address = address;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new RestaurentPresenter(this);
        mapPointList = new MapPoint[]{
                new MapPoint("Bar Termini", "Club bar 24/7", "01928192822", 51.51367, -0.12981),
                new MapPoint("Char ca ha noi", "Best house ever", "015938812", 21.047422, 105.798484),
//                21.047422, 105.798484


        };
        drawable = pinLocation.getDrawable();

        Log.d("vvmvmvm", mapPointList[1].toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
//            @Override
//            public void onCameraIdle() {
//
//            }
//        });

        btnShowDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = "";
                query = infoTitle.getText().toString();
//                mPresenter.directByCar("", begin, des, "");
                Uri uri = Uri.parse("https://www.google.com/search?q=" + query);
                Intent gSearchIntent = new Intent(Intent.ACTION_VIEW, uri);
                Objects.requireNonNull(getActivity()).startActivity(gSearchIntent);
            }
        });

        initViews();
        initControls();
        return view;
    }

    private void initViews() {
        animationbbb = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in_animation);
        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        googleMapPlaceAdapter = new GoogleMapPlaceAdapter(getContext(), listData, new GoogleMapPlaceAdapter.OnClickEvent() {
            @Override
            public void onClick(QueryAddressResponse.Predictions predictions) {
                resultLayout.setVisibility(View.GONE);

                mPresenter.queryAddressDetail(predictions.getPlaceId(), GOOGLE_API_KEY);
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            listResult.setLayoutManager(new SmoothScrollLinearLayoutManager(Objects.requireNonNull(getActivity()),
                    RecyclerView.VERTICAL, false));
        }
        listResult.setAdapter(googleMapPlaceAdapter);
    }

    private void initControls() {
//        edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                hideInfo();
//                if (!hasFocus) {
//                    hideKeyboard(v);
//                } else {
//                    resultLayout.startAnimation(fadeOut);
//
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            resultLayout.setVisibility(View.VISIBLE);
//                        }
//                    }, 500);
//                }
//            }
//        });
//        edtSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                String query = s.toString();
//                listResult.smoothScrollToPosition(0);
//                if (!query.isEmpty()) {
//                    hideInfo();
//                    btnClear.setVisibility(View.VISIBLE);
//                    mPresenter.queryAddress(query, GOOGLE_API_KEY, "");
//                    resultLayout.setVisibility(View.VISIBLE);
//                } else {
//                    btnClear.setVisibility(View.INVISIBLE);
//                }
//            }
//        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getApplicationContext(), R.raw.map_type));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }


        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

//        for (int i = 0; i < mapPointList.length; i++) {
//            MarkerOptions markerOptions = new MarkerOptions();
//            markerOptions.title(mapPointList[i].getPointName());
//            markerOptions.snippet(mapPointList[i].getPointDescription());
//            markerOptions.position(new LatLng(mapPointList[i].getPointLat(), mapPointList[i].getPointLong()));
//            mMap.addMarker(markerOptions);
//        }
        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
//        mMap.setMapType(MAP_TYPE_TERRAIN);
        mMap.setTrafficEnabled(true);
        pinLocation.setVisibility(View.VISIBLE);
        mMap.setOnMapClickListener(this);
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);

        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveStartedListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraMoveCanceledListener(this);

        View mapView = mapFragment.getView();
        if (mapView != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
// position on right bottom
            rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            rlp.setMargins(0, 0, 20, 20);
        }

        // change compass position
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the view
            View locationCompass = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("5"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationCompass.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
            layoutParams.setMargins(0, 0, 20, 20);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);

        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        begin = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.snippet("");
        markerOptions.title("Vị trí hiện tại");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (LocationListener) this);
        }
        if (destination != null)
            mPresenter.directByCar("", begin, destination, "");
    }


    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void showInfo() {
        viewAnimator = ViewAnimator.animate(infoLayout)
                .slideBottom()
                .duration(300)
                .decelerate()
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        infoLayout.setVisibility(View.VISIBLE);
                    }
                })
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                    }
                })
                .start();
    }

    private void showResult() {
        viewAnimator = ViewAnimator.animate(resultLayout)
                .slideBottom()
                .duration(300)
                .decelerate()
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                        resultLayout.setVisibility(View.VISIBLE);
                    }
                })
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                    }
                })
                .start();
    }

    private void hideInfo() {
        viewAnimator = ViewAnimator.animate(infoLayout)
                .translationY(0, infoLayout.getHeight())
                .duration(300)
                .decelerate()
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                    }
                })
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        infoLayout.setVisibility(View.GONE);
                    }
                })
                .start();
    }

    private void hideResult() {
        viewAnimator = ViewAnimator.animate(resultLayout)
                .translationY(0, resultLayout.getHeight())
                .duration(300)
                .decelerate()
                .onStart(new AnimationListener.Start() {
                    @Override
                    public void onStart() {
                    }
                })
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        resultLayout.setVisibility(View.GONE);
                    }
                })
                .start();
    }

    @Override
    public void onMapClick(LatLng latLng) {
//        edtSearch.clearFocus();
        if (getActivity() != null) {
            hideKeyboard2(getActivity());
        }
        if (resultLayout.getVisibility() == View.VISIBLE) {
            resultLayout.startAnimation(fadeIn);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    resultLayout.setVisibility(View.GONE);

                }
            }, 500);
        }

        hideInfo();
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
//        latlng=map.getProjection().getVisibleRegion().latLngBounds.getCenter();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (getActivity() != null) {
            hideKeyboard2(getActivity());
        }
        startDropMarkerAnimation(marker);
        resultLayout.setVisibility(View.GONE);
        String title = "";
        String phone = "";
        String type = "";
        des = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
        try {
            String arr[] = marker.getTitle().split("\\|");
            type = arr[0];
            title = arr[1];
            phone = arr[2];
        } catch (Exception ignored) {
        }


        infoTitle.setText(type);
        infoAddress.setText(marker.getSnippet());
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(marker.getPosition()).zoom(30).tilt(30).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 400, null);

        showInfo();

        return true;
    }


    @Override
    public void setPresenter(RestaurentContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void directByCarSuccess(Response<Example> response) {
        try {
            if (line != null) {
                line.remove();
            }
            showDirect.setVisibility(View.VISIBLE);
            for (int i = 0; i < response.body().getRoutes().size(); i++) {
                String distance = response.body().getRoutes().get(i).getLegs().get(i).getDistance().getText();
                String time = response.body().getRoutes().get(i).getLegs().get(i).getDuration().getText();
                showDirect.setText("Distance:" + distance + ", Duration:" + time);

                String encodedString = response.body().getRoutes().get(0).getOverviewPolyline().getPoints();
                List<LatLng> list = decodePoly(encodedString);
                line = mMap.addPolyline(new PolylineOptions()
                        .addAll(list)
                        .width(20)
                        .color(Color.RED)
                        .geodesic(true)
                );
            }
        } catch (Exception e) {
            Log.d("onResponse", "There is an error");
            e.printStackTrace();
        }

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(name);
        markerOptions.snippet(address);
        markerOptions.position(new LatLng(destination.latitude, destination.longitude));

        Marker marker;
        marker = mMap.addMarker(markerOptions);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(destination).zoom(20).tilt(30).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 400, null);

        String title = "";
        String phone = "";
        String type = "";
        try {
            String arr[] = marker.getTitle().split("\\|");
            type = arr[0];
            title = arr[1];
            phone = arr[2];
        } catch (Exception ignored) {
        }


        infoTitle.setText(type);
        infoAddress.setText(marker.getSnippet());

        showInfo();
        startDropMarkerAnimation(marker);

    }

    @Override
    public void directByCarError() {

    }

    @Override
    public void queryAddressSuccess(Response<QueryAddressResponse> queryAddressResponse) {
        if (queryAddressResponse.body() == null || !"OK".equals(queryAddressResponse.body().getStatus())) {
            listData.clear();
            googleMapPlaceAdapter.notifyDataSetChanged();
            return;
        }
        listData.clear();
        QueryAddressResponse addressResponse = queryAddressResponse.body();
        if (addressResponse != null) {
            if (addressResponse.getPredictions() != null) {
                for (int i = 0; i < addressResponse.getPredictions().size(); i++) {
                    listData.add(addressResponse.getPredictions().get(i));
                }

            }
        }
        googleMapPlaceAdapter.notifyDataSetChanged();

    }

    @Override
    public void queryAddressDetailSuccess(Response<AddressDetailResponse> response) {
        if (getActivity() != null) {
            hideKeyboard2(getActivity());
        }
        if (response.body() == null || !"OK".equals(response.body().getStatus())) {
            return;
        }

        if (response.body().getResults() != null
                && response.body().getResults().size() > 0) {
            double la = response.body().getResults().get(0).getGeometry().getLocation().getLat();
            double lo = response.body().getResults().get(0).getGeometry().getLocation().getLng();
            currentPoint = new LatLng(la, lo);


            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title(response.body().getResults().get(0).getFormattedAddress());
            markerOptions.position(currentPoint);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker());
            mMap.addMarker(markerOptions);
//            mMarker = mMap.addMarker(markerOptions);
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(currentPoint).zoom(15).tilt(30).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 400, null);
//            startDropMarkerAnimation(mMarker);
        }
    }


    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    public void hideKeyboard(View view) {
        if (getActivity() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    public static void hideKeyboard2(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    private void startDropMarkerAnimation(final Marker marker) {
        final Handler handler = new Handler();

        final long startTime = SystemClock.uptimeMillis();
        final long duration = 2000;

        Projection proj = mMap.getProjection();
        if (marker.getPosition() != null) {
            final LatLng markerLatLng = marker.getPosition();
            Point startPoint = proj.toScreenLocation(markerLatLng);
            startPoint.offset(0, -100);
            final LatLng startLatLng = proj.fromScreenLocation(startPoint);

            final Interpolator interpolator = new BounceInterpolator();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    long elapsed = SystemClock.uptimeMillis() - startTime;
                    float t = interpolator.getInterpolation((float) elapsed / duration);
                    double lng = t * markerLatLng.longitude + (1 - t) * startLatLng.longitude;
                    double lat = t * markerLatLng.latitude + (1 - t) * startLatLng.latitude;
                    marker.setPosition(new LatLng(lat, lng));

                    if (t < 1.0) {
                        // Post again 16ms later.
                        handler.postDelayed(this, 16);
                    }
                }
            });
        }


        //return false; //have not consumed the event
//        return true; //have consumed the event
    }

    @Override
    public boolean onBackPressed() {
//        edtSearch.clearFocus();
        if (infoLayout.getVisibility() == View.VISIBLE || resultLayout.getVisibility() == View.VISIBLE) {
            resultLayout.startAnimation(fadeIn);

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    resultLayout.setVisibility(View.GONE);

                }
            }, 500);

            hideInfo();
            return true;
        } else {
            return false;

        }
    }


    @Override
    public void onCameraMoveStarted(int reason) {

        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
        }
    }

    @Override
    public void onCameraMove() {
        if (isShow) {
//            pinLocation2.setVisibility(View.VISIBLE);
            pinLocation.startAnimation(animationbbb);
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            }
            isShow = false;
        }
        center = mMap.getCameraPosition().target;
        Log.d("centerrr", center.latitude + ',' + center.longitude + "");
    }

    @Override
    public void onCameraMoveCanceled() {
        isShow = true;
    }


    @Override
    public void onCameraIdle() {
//        pinLocation2.setVisibility(View.GONE);
        isShow = true;
        if (drawable instanceof Animatable) {
            if (((Animatable) drawable).isRunning()) {
                ((Animatable) drawable).stop();

            }
        }

    }


    @OnClick(R.id.ivClose)
    public void onViewClicked() {
        Objects.requireNonNull(getActivity()).onBackPressed();
    }
}
