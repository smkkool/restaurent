package com.minhpvn.restaurantsapp.fragment;

import android.util.Log;

import com.minhpvn.restaurantsapp.model.MultipleResources;
import com.minhpvn.restaurantsapp.model.UserList;
import com.minhpvn.restaurantsapp.model.googleMapNearby.Example2;
import com.minhpvn.restaurantsapp.ultil.APIInterface;
import com.minhpvn.restaurantsapp.ultil.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;
    APIInterface apiInterface;

    public HomePresenter(HomeContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }




    @Override
    public void getNearbyPlaces(String type, String location, int radius) {
        apiInterface = ApiClient.getClientGoogleMap().create(APIInterface.class);
        Call<Example2> call = apiInterface.getNearbyPlaces(type, location, radius);
        call.enqueue(new Callback<Example2>() {
            @Override
            public void onResponse(Call<Example2> call, Response<Example2> response) {

//                try {
//                    // This loop will go through all the results and add marker on each location.
//                    for (int i = 0; i < response.body().getResults().size(); i++) {
//                        Double lat = response.body().getResults().get(i).getGeometry().getLocation().getLat();
//                        Double lng = response.body().getResults().get(i).getGeometry().getLocation().getLng();
//                        String placeName = response.body().getResults().get(i).getName();
//                        String vicinity = response.body().getResults().get(i).getVicinity();
//                    }
//                } catch (Exception e) {
//                    Log.d("onResponse", "There is an error");
//                    e.printStackTrace();
//                }

                mView.getNearbyPlacesSuccess(response.body().getResults());
            }

            @Override
            public void onFailure(Call<Example2> call, Throwable t) {

            }
        });
    }

    @Override
    public void getPhoto(String photoreference, int maxheight, int maxwidth) {
        apiInterface = ApiClient.getClientGoogleMap().create(APIInterface.class);
        Call<String> call = apiInterface.getPhoto(photoreference, maxheight, maxwidth);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                mView.getPhotoSuccess(response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
