package com.minhpvn.restaurantsapp.ultil;

import com.minhpvn.restaurantsapp.model.MultipleResources;
import com.minhpvn.restaurantsapp.model.User;
import com.minhpvn.restaurantsapp.model.UserList;
import com.minhpvn.restaurantsapp.model.googleMapModel.AddressDetailResponse;
import com.minhpvn.restaurantsapp.model.googleMapModel.Example;
import com.minhpvn.restaurantsapp.model.googleMapModel.QueryAddressResponse;
import com.minhpvn.restaurantsapp.model.googleMapNearby.Example2;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("/api/unknown")
    Call<MultipleResources> doGetListResources();

    @POST("/api/users")
    Call<User> createUser(@Body User user);

    @GET("/api/users?")
    Call<UserList> doGetUserList(@Query("page") int page);

    @FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);

    //google map api
    @GET("api/directions/json?key=AIzaSyAocTT3gezvFMp_RIbgm1sb3bTce2g9fa0")
    Call<Example> getDistanceDuration(@Query("units") String units,
                                      @Query("origin") String origin,
                                      @Query("destination") String destination,
                                      @Query("mode") String mode);

    @GET("api/place/autocomplete/json")
    Call<QueryAddressResponse> queryAddress(@Query(value = "input", encoded = true) String input,
                                            @Query("key") String key, @Query("components") String country);

    @GET("api/geocode/json")
    Call<AddressDetailResponse> queryAddressDetail(@Query(value = "place_id") String placeId,
                                                   @Query("key") String key);

    @GET("api/place/nearbysearch/json?sensor=true&key=AIzaSyAocTT3gezvFMp_RIbgm1sb3bTce2g9fa0")
    Call<Example2> getNearbyPlaces(@Query("type") String type, @Query("location") String location, @Query("radius") int radius, @Query("pagetoken") String pagetoken);


    //    https://maps.googleapis.com/maps/api/place/photo?photoreference=PHOTO_REFERENCE&sensor=false&maxheight=MAX_HEIGHT&maxwidth=MAX_WIDTH&key=YOUR_API_KEY
    @GET("api/place/photo?sensor=false&key=AIzaSyAocTT3gezvFMp_RIbgm1sb3bTce2g9fa0")
    Call<String> getPhoto(@Query("photoreference") String photoreference, @Query("maxheight") int maxheight, @Query("maxwidth") int maxwidth);
}
