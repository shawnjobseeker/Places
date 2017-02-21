package org.udacityexamples.connect;

import org.udacityexamples.model.PlaceDetail;
import org.udacityexamples.model.PlaceRequest;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Shawn Li on 2/1/2017.
 */

public interface ApiInterface {
    String API_KEY = "AIzaSyAZmj1LRmj3oBlwu3-YLXu-UsCXQYK7wdI";

    @GET("place/nearbysearch/json")
    Observable<PlaceRequest> getPlaces(@Query("location") String location, @Query("radius") int radius, @Query("type") String type, @Query("keyword") String keyword, @Query("key") String apiKey);



}
