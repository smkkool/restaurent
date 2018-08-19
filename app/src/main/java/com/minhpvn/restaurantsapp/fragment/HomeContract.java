package com.minhpvn.restaurantsapp.fragment;

import com.minhpvn.restaurantsapp.model.MultipleResources;
import com.minhpvn.restaurantsapp.model.UserList;
import com.minhpvn.restaurantsapp.model.googleMapModel.Example;
import com.minhpvn.restaurantsapp.model.googleMapNearby.Example2;
import com.minhpvn.restaurantsapp.model.googleMapNearby.Result;
import com.minhpvn.restaurantsapp.ultil.IPresenter;
import com.minhpvn.restaurantsapp.ultil.IView;

import java.util.List;

import retrofit2.Response;

interface HomeContract {
    interface View extends IView<Presenter> {

        void getNearbyPlacesSuccess(List<Result> response);


        void getPhotoSuccess(Response<String> response);
    }

    interface Presenter extends IPresenter {

        void getNearbyPlaces(String type, String location, int radius);

        void getPhoto(String photoreference, int maxheight, int maxwidth);
    }
}
