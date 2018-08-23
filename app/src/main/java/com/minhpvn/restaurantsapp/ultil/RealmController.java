package com.minhpvn.restaurantsapp.ultil;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.minhpvn.restaurantsapp.model.realmObject.SaveMap;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class RealmController {
    private static RealmController instance;
    private final Realm realm;

    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }

    public static RealmController with(Fragment fragment) {

        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }

    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }

    public static RealmController getInstance() {

        return instance;
    }

    public Realm getRealm() {

        return realm;
    }

    //Refresh the realm istance
    public void refresh() {

        realm.refresh();
    }

    //clear all objects from Book.class
    public void clearAll() {

        realm.beginTransaction();
        realm.delete(SaveMap.class);
        realm.commitTransaction();
    }

    //find all objects in the Book.class
    public RealmResults<SaveMap> getMapItemSortByDate() {

        return realm.where(SaveMap.class).sort("date", Sort.DESCENDING).findAll();
    }

}
