package org.udacityexamples;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;

import org.xml.sax.helpers.AttributeListImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shawn Li on 1/31/2017.
 */

public class PhotoManager implements ResultCallback<PlacePhotoMetadataResult> {

    private GoogleApiClient googleApiClient;
    private PlaceCardFragment fragment;
    private List<Bitmap> bitmaps;
    public PhotoManager(GoogleApiClient googleApiClient, PlaceCardFragment fragment) {
        this.googleApiClient = googleApiClient;
        this.fragment = fragment;
        this.bitmaps = new ArrayList<Bitmap>();
    }
    @Override
    public void onResult(@NonNull PlacePhotoMetadataResult result) {
        PlacePhotoMetadataBuffer buffer = result.getPhotoMetadata();
        for (int i = 0; i < buffer.getCount(); i++) {
            PendingResult<PlacePhotoResult> photoResult = buffer.get(i).getPhoto(googleApiClient);
            photoResult.setResultCallback(new ResultCallback<PlacePhotoResult>() {
                @Override
                public void onResult(@NonNull PlacePhotoResult placePhotoResult) {
                    if (placePhotoResult.getStatus().isSuccess())
                        bitmaps.add(placePhotoResult.getBitmap());
                }
            });
        }
        fragment.passPhotos(bitmaps);
        buffer.release();

    }
}
