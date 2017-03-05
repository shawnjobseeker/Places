package org.example.udprojects;

import android.app.Application;
import android.support.v4.app.Fragment;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Shawn Li on 1/31/2017.
 */

public interface BaseActivityInterface {
     Application getApplication();
     LatLng getCurrentLocation();
     GoogleApiClient getClient();
     void setMapMode(boolean mapMode);
     boolean isMapMode();
     void removePlaceCard(Fragment fragment);
     void refreshViewPager();
}
