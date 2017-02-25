package org.udacityexamples;

import android.content.res.Resources;

import com.google.android.gms.maps.model.LatLng;

import org.udacityexamples.connect.ApiClient;
import org.udacityexamples.connect.ApiInterface;
import org.udacityexamples.model.PlaceDetail;
import org.udacityexamples.model.PlaceRequest;
import org.udacityexamples.model.Result;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.udacityexamples.connect.ApiInterface.API_KEY;

/**
 * Created by Shawn Li on 2/1/2017.
 */

public class Presenter {

    private ListFragmentInterface listFragment;
    private ApiInterface api;
    private LatLng latLng;

    public Presenter(ListFragmentInterface fragment, LatLng latLng) {
        this.listFragment = fragment;
        this.latLng = latLng;
        api = ApiClient.getClient(fragment.getActivity().getApplication()).create(ApiInterface.class);
    }

    public void observeMultipleCategories( String[] categories) {
        MultipleObserver observer = new MultipleObserver(categories);
        for (String category : categories) {
            api.getPlaces(latLng.latitude + "," + latLng.longitude, 1500, category, null, API_KEY).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
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

