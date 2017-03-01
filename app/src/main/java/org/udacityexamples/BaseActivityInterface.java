package org.udacityexamples;

import android.app.Application;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.graphics.Bitmap;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.model.LatLng;

import org.udacityexamples.model.Result;

import java.util.List;
import java.util.Set;

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
}
