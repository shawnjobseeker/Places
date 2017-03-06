package org.example.udprojects;

import com.google.android.gms.maps.model.LatLng;


import org.example.udprojects.connect.ApiClient;
import org.example.udprojects.connect.ApiInterface;
import org.example.udprojects.model.Direction;
import org.example.udprojects.model.Leg;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.example.udprojects.connect.ApiInterface.API_KEY;


/**
 * Created by Jonathan on 01/03/2017.
 */

public class DistanceFinder {

    private CardFragmentInterface fragment;
    private ApiInterface api;
    private String origin, destination;
    public static final String WALKING = "walking";
    public static final String CYCLING = "bicycling";
    public static final String DRIVING = "driving";
    public static final String TRANSIT = "transit";

    public DistanceFinder(CardFragmentInterface fragment, LatLng origin, LatLng destination) {
        api = ApiClient.getClient(fragment.getActivity().getApplication()).create(ApiInterface.class);
        this.fragment = fragment;
        this.origin = origin.latitude + "," + origin.longitude;
        this.destination = destination.latitude + "," + destination.longitude;
    }
    public void getDistances() {
        String[] modes = new String[]{WALKING, CYCLING, DRIVING, TRANSIT};
        for (String mode : modes)
        api.getDirection(origin, destination, mode, fragment.getLocale().getLanguage(), API_KEY).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DistanceObserver(mode));
    }
    private class DistanceObserver implements Observer<Direction> {
        private String mode;
        DistanceObserver(String mode) {
            this.mode = mode;
        }

        @Override
        public void onError(Throwable e) {
            e.printStackTrace();
        }

        @Override
        public void onNext(Direction direction) {
            String distance = "N/A \n(" + direction.getStatus() + ")";
            if (!direction.getRoutes().isEmpty()) {
                Leg leg = direction.getRoutes().get(0).getLegs().get(0);
                distance = leg.getDistance().getText() + "\n" + leg.getDuration().getText();
            }
            switch (mode) {
                case WALKING:
                    fragment.setWalkingDistance(distance);
                    break;
                case CYCLING:
                    fragment.setCyclingDistance(distance);
                    break;
                case TRANSIT:
                    fragment.setTransitDistance(distance);
                    break;
                default:
                    fragment.setDrivingDistance(distance);
                    break;
            }
        }

        @Override
        public void onCompleted() {


        }
    }
}
