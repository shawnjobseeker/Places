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
    // Google Places Web API key
    String API_KEY = "AIzaSyCuZqSTcaVrHTBlheIAr4FXpBu5hTyRiQg";

    @GET("place/nearbysearch/json")
    Observable<PlaceRequest> getPlaces(@Query("location") String location, @Query("radius") int radius, @Query("type") String type, @Query("keyword") String keyword, @Query("key") String apiKey);

}
