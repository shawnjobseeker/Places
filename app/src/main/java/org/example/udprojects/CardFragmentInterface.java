package org.example.udprojects;

import android.app.Activity;
import android.graphics.Bitmap;

import java.util.Locale;

/**
 * Created by Jonathan on 01/03/2017.
 */

public interface CardFragmentInterface {

    void passPhoto(Bitmap image);
    void setListFragment(ListFragmentInterface fragment);
    void setWalkingDistance(String walkingDistance);
    void setCyclingDistance(String cyclingDistance);
    void setDrivingDistance(String drivingDistance);
    void setTransitDistance(String transitDistance);
    Activity getActivity();
    Locale getLocale();
}
