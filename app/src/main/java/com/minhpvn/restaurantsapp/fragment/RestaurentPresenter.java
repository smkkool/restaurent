package com.minhpvn.restaurantsapp.fragment;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.minhpvn.restaurantsapp.model.googleMapModel.AddressDetailResponse;
import com.minhpvn.restaurantsapp.model.googleMapModel.Example;
import com.minhpvn.restaurantsapp.model.googleMapModel.QueryAddressResponse;
import com.minhpvn.restaurantsapp.ultil.APIInterface;
import com.minhpvn.restaurantsapp.ultil.ApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestaurentPresenter implements RestaurentContract.Presenter {
    private RestaurentContract.View mView;
    APIInterface apiInterface;

    public RestaurentPresenter(RestaurentContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }


    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void directByCar(String unit, LatLng origin, LatLng destination, String mode) {
        apiInterface = ApiClient.getClientGoogleMap().create(APIInterface.class);
        Call<Example> call = apiInterface.getDistanceDuration("metric", origin.latitude + "," + origin.longitude, destination.latitude + "," + destination.longitude, "driving");

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                mView.directByCarSuccess(response);
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Log.d("onFailure", t.toString());

            }

        });
    }

    @Override
    public void queryAddress(String input, String key, String country) {
        apiInterface = ApiClient.getClientGoogleMap().create(APIInterface.class);
        Call<QueryAddressResponse> call = apiInterface.queryAddress(input,key,"country:vn");
        call.enqueue(new Callback<QueryAddressResponse>() {
            @Override
            public void onResponse(Call<QueryAddressResponse> call, Response<QueryAddressResponse> response) {
                mView.queryAddressSuccess(response);
            }

            @Override
            public void onFailure(Call<QueryAddressResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void queryAddressDetail(String place_id, String key) {
        apiInterface = ApiClient.getClientGoogleMap().create(APIInterface.class);
        Call<AddressDetailResponse> call = apiInterface.queryAddressDetail(place_id,key);
        call.enqueue(new Callback<AddressDetailResponse>() {
            @Override
            public void onResponse(Call<AddressDetailResponse> call, Response<AddressDetailResponse> response) {
                mView.queryAddressDetailSuccess(response);
            }

            @Override
            public void onFailure(Call<AddressDetailResponse> call, Throwable t) {

            }
        });
    }


}
