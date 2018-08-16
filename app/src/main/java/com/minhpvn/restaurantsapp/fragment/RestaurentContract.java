package com.minhpvn.restaurantsapp.fragment;

import com.google.android.gms.maps.model.LatLng;
import com.minhpvn.restaurantsapp.model.googleMapModel.AddressDetailResponse;
import com.minhpvn.restaurantsapp.model.googleMapModel.Example;
import com.minhpvn.restaurantsapp.model.googleMapModel.QueryAddressResponse;
import com.minhpvn.restaurantsapp.ultil.IPresenter;
import com.minhpvn.restaurantsapp.ultil.IView;

import retrofit2.Response;

interface RestaurentContract {
    interface View extends IView<Presenter> {


        void directByCarSuccess(Response<Example> response);

        void directByCarError();

        void queryAddressSuccess(Response<QueryAddressResponse> queryAddressResponse);

        void queryAddressDetailSuccess(Response<AddressDetailResponse> response);
    }

    interface Presenter extends IPresenter {

        void directByCar(String unit, LatLng origin, LatLng destination, String mode);

        void queryAddress(String input, String key, String country);

        void queryAddressDetail(String place_id,String key);
    }
}
