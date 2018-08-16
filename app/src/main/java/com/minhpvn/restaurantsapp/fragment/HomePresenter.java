package com.minhpvn.restaurantsapp.fragment;

import android.util.Log;
import android.widget.Toast;

import com.minhpvn.restaurantsapp.model.MultipleResources;
import com.minhpvn.restaurantsapp.model.UserList;
import com.minhpvn.restaurantsapp.ultil.APIInterface;
import com.minhpvn.restaurantsapp.ultil.ApiClient;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;
    APIInterface apiInterface;

    public HomePresenter(HomeContract.View mView) {
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    @Override
    public void doGetListResources() {
        apiInterface = ApiClient.getClient().create(APIInterface.class);
        Call<MultipleResources> call = apiInterface.doGetListResources();
        call.enqueue(new Callback<MultipleResources>() {
            @Override
            public void onResponse(Call<MultipleResources> call, Response<MultipleResources> response) {
                Log.d("TAG",response.code()+"");

                mView.doGetListResourcesSuccess(response.body());

            }

            @Override
            public void onFailure(Call<MultipleResources> call, Throwable t) {
                mView.doGetListResourcesError();
                call.cancel();
            }
        });
    }

    @Override
    public void doCreateUserWithField(String name, String job) {
        apiInterface = ApiClient.getClient().create(APIInterface.class);
        Call<UserList> call3 = apiInterface.doCreateUserWithField(name,job);
        call3.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                UserList userList = response.body();
                mView.doCreateUserWithFieldSuccess(userList);


            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                call.cancel();
            }
        });

    }

    @Override
    public void doGetUserList(int page) {
        apiInterface = ApiClient.getClient().create(APIInterface.class);
        Call<UserList> call2 = apiInterface.doGetUserList(page);
        call2.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                    Log.d("responsemmm",response.body().toString());
                UserList userList = response.body();
                Integer text = userList.page;
                Integer total = userList.total;
                Integer totalPages = userList.totalPages;
                List<UserList.Datum> datumList = userList.data;
                mView.doGetUserListSuccess(datumList);



            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                mView.doGetUserListError();
                call.cancel();
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
