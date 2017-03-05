package org.example.udprojects;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.maps.model.LatLng;


import org.example.udprojects.connect.ApiClient;
import org.example.udprojects.connect.ApiInterface;
import org.example.udprojects.model.PlaceRequest;
import org.example.udprojects.model.Result;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.example.udprojects.connect.ApiInterface.API_KEY;


/**
 * Created by Shawn Li on 2/1/2017.
 */

public class Presenter {

    private ListFragmentInterface listFragment;
    private ApiInterface api;
    private LatLng latLng;
    private int radius;
    private String keywords;

    public Presenter(ListFragmentInterface fragment, LatLng latLng) {
        this.listFragment = fragment;
        this.latLng = latLng;
        api = ApiClient.getClient(fragment.getActivity().getApplication()).create(ApiInterface.class);
        SharedPreferences prefs = fragment.getActivity().getPreferences(Context.MODE_PRIVATE);
        radius = prefs.getInt("presenter_radius", 1500);
        keywords = prefs.getString("presenter_keywords", null);
    }

    public void observeMultipleCategories( String[] categories) {
        MultipleObserver observer = new MultipleObserver(categories);
        for (String category : categories) {
            api.getPlaces(latLng.latitude + "," + latLng.longitude, radius, category, keywords, API_KEY).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        }
    }

    private class MultipleObserver implements Observer<PlaceRequest> {
        private int multiple;
        private List<String> categories;

        public MultipleObserver(String[] categories) {
            this.categories = Arrays.asList(categories);
            this.multiple = categories.length;
        }

        private Set<Result> results = new TreeSet<Result>();

        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onNext(PlaceRequest placeRequest) {
            for (Result result : placeRequest.getResults()) {
                result.setIcon(getTypeInBothLists(categories, result.getTypes()));
                results.add(result);
            }
            multiple--;
            if (multiple == 0)
                listFragment.passResultSet(results, placeRequest.getStatus());
        }

        private String getTypeInBothLists(List<String> types1, List<String> types2) {
            for (String type : types2) {
                if (types1.contains(type))
                    return type;
            }
            return null;
        }
    }
}

