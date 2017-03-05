package org.example.udprojects;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlacePhotoMetadataBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.PlacePhotoResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Shawn Li on 1/31/2017.
 */

public class PhotoManager implements ResultCallback<PlacePhotoMetadataResult> {

    private GoogleApiClient googleApiClient;
    private CardFragmentInterface fragment;
    public PhotoManager(GoogleApiClient googleApiClient, CardFragmentInterface fragment) {
        this.googleApiClient = googleApiClient;
        this.fragment = fragment;
    }
    @Override
    public void onResult(@NonNull PlacePhotoMetadataResult result) {
       final  PlacePhotoMetadataBuffer buffer = result.getPhotoMetadata();
        if (buffer == null) {
            fragment.passPhoto(null);
            return;
        }
        final AtomicBoolean photoPassed = new AtomicBoolean(false);
        for (int i = 0; i < buffer.getCount(); i++) {

            final PendingResult<PlacePhotoResult> photoResult = buffer.get(i).getPhoto(googleApiClient);
            photoResult.setResultCallback(new ResultCallback<PlacePhotoResult>() {
                @Override
                public void onResult(@NonNull PlacePhotoResult placePhotoResult) {
                    if (placePhotoResult.getStatus().isSuccess() && !photoPassed.get()) {

                            photoPassed.set(true);
                            if (fragment != null)
                            fragment.passPhoto(placePhotoResult.getBitmap());

                    }

                }
            });
        }
        if (!photoPassed.get() && fragment != null)
            fragment.passPhoto(null);
        buffer.release();
    }
}
