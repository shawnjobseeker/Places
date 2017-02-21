package org.udacityexamples;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;

import org.udacityexamples.model.Result;

import java.util.List;

/**
 * Created by Shawn Li on 2/18/2017.
 */

public class PlaceCardFragment extends Fragment  {

    private TextView businessName, address, phone, website, hours;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.place_card, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        BaseActivityInterface activity = (BaseActivityInterface)getActivity();
        businessName = (TextView)view.findViewById(R.id.business_name);
        address = (TextView)view.findViewById(R.id.address);
        phone = (TextView)view.findViewById(R.id.phone);
        website = (TextView)view.findViewById(R.id.website);
        hours = (TextView)view.findViewById(R.id.hours);
        Places.GeoDataApi.getPlaceById(activity.getClient(), getArguments().getString("placeId")).setResultCallback(new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(@NonNull PlaceBuffer places) {
                if (places.getStatus().isSuccess() && places.getCount() == 1) {
                    Place place = places.get(0);
                    businessName.setText(place.getName());
                    address.setText(place.getAddress());
                    phone.setText(place.getPhoneNumber());
                    website.setText(place.getWebsiteUri().toString());
                    if (getArguments().getBoolean("hours")){
                        hours.setText(getString(R.string.open));
                        hours.setTextColor(Color.YELLOW);
                    }
                    else {
                        hours.setText(getString(R.string.closed));
                        hours.setTextColor(Color.RED);
                    }
                }
                places.release();
            }
        });
        Places.GeoDataApi.getPlacePhotos(activity.getClient(), getArguments().getString("placeId") ).setResultCallback(new PhotoManager(activity.getClient(), this));
    }

    public void passPhotos(List<Bitmap> images) {
        ImageView photo = (ImageView) getView().findViewById(R.id.business_pic);
        if (images != null && images.size() > 0)
            photo.setImageBitmap(images.get(0));
        else
            photo.setImageDrawable(ContextCompat.getDrawable(getContext(), getArguments().getInt("icon")));
    }
}
