package com.minhpvn.restaurantsapp.fragment;

import com.minhpvn.restaurantsapp.model.MultipleResources;
import com.minhpvn.restaurantsapp.model.UserList;
import com.minhpvn.restaurantsapp.ultil.IPresenter;
import com.minhpvn.restaurantsapp.ultil.IView;

import java.util.List;

interface HomeContract {
    interface View extends IView<Presenter> {
        void doGetListResourcesSuccess(MultipleResources response);

        void doGetListResourcesError();

        void doCreateUserWithFieldSuccess(UserList response);

        void doCreateUserWithFieldError();
        void doGetUserListSuccess(List<UserList.Datum> response);
        void doGetUserListError();
    }

    interface Presenter extends IPresenter {
        void doGetListResources();

        void doCreateUserWithField(String name, String job);

        void doGetUserList(int page);
    }
}
