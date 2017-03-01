package org.udacityexamples;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.PlacePhotoMetadataResult;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import org.udacityexamples.model.Result;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Shawn Li on 2/18/2017.
 */

public class PlaceCardFragment extends Fragment implements CardFragmentInterface {

    private TextView businessName, address, phone, website, hours, category;
    private Button cycling, walking, driving, transit;
    private ListFragmentInterface fragment;
    private DistanceFinder finder;
    private LatLng placeLatLng;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.place_card, container, false);
        if (getArguments().getBoolean("openedFromMap")) {
            v.findViewById(R.id.button_back).setVisibility(View.GONE);
            v.findViewById(R.id.button_forward).setVisibility(View.GONE);
        }
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //back and fwd buttons
        view.findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
                fragment.openPlaceCard(fragment.getPreviousResult(), false);
            }
        });
        view.findViewById(R.id.button_forward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
                fragment.openPlaceCard(fragment.getNextResult(), false);
            }
        });
        // gather place data
        final BaseActivityInterface activity = (BaseActivityInterface)getActivity();
        businessName = (TextView)view.findViewById(R.id.business_name);
        address = (TextView)view.findViewById(R.id.address);
        phone = (TextView)view.findViewById(R.id.phone);
        website = (TextView)view.findViewById(R.id.website);
        hours = (TextView)view.findViewById(R.id.hours);
        category = (TextView)view.findViewById(R.id.category);
        businessName.setText(getArguments().getString("name"));
        address.setText(getArguments().getString("address"));
        String[] types = getArguments().getStringArray("types");
        if (types != null && types.length >= 1) {
            String typesToString = Arrays.toString(types);
            category.setText(getString(R.string.categories, typesToString.substring(1, typesToString.length()-1)));
        }
        String openClosed = getArguments().getString("hours");
        if (openClosed != null)
            hours.setText(openClosed);
        if (hours.getText().toString().equals(getString(R.string.open)))
            hours.setTextColor(Color.YELLOW);
        else
            hours.setTextColor(Color.RED);
        Places.GeoDataApi.getPlaceById(activity.getClient(), getArguments().getString("placeId")).setResultCallback(new ResultCallback<PlaceBuffer>() {
            @Override
            public void onResult(@NonNull PlaceBuffer places) {
               final Place place = places.get(0);
                address.setText(place.getAddress());
                phone.setText(place.getPhoneNumber());
                placeLatLng = place.getLatLng();
                Uri web = place.getWebsiteUri();
                if (web != null)
                    website.setText(web.toString());
                else
                    website.setText("N/A");
                finder = new DistanceFinder(PlaceCardFragment.this, activity.getCurrentLocation(), place.getLatLng());
                finder.getDistances();
            }
        });


        Places.GeoDataApi.getPlacePhotos(activity.getClient(), getArguments().getString("placeId") ).setResultCallback(new PhotoManager(activity.getClient(), this));
    }

    public void passPhoto(Bitmap image) {
        if (getView() == null)
            return;
        ImageView photo = (ImageView) getView().findViewById(R.id.business_pic);
        if (image != null) {
            photo.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            photo.setImageBitmap(image);
            photo.setPadding(0,0,0,0);
        }
        else {
            photo.setImageDrawable(ContextCompat.getDrawable(getContext(), getArguments().getInt("icon")));
            photo.setPadding(4,4,4,4);
        }
    }
    public void setListFragment(ListFragmentInterface fragment) {
        this.fragment = fragment;
    }

    @Override
    public void setCyclingDistance(String cyclingDistance) {
        cycling = (Button) getView().findViewById(R.id.cycle);
        cycling.setText(cyclingDistance);
        cycling.setOnClickListener(new NavOnClickListener(cycling.getId()));
    }

    @Override
    public void setWalkingDistance(String walkingDistance) {
        walking = (Button)getView().findViewById(R.id.walk);
        walking.setText(walkingDistance);
        walking.setOnClickListener(new NavOnClickListener(walking.getId()));
    }

    @Override
    public void setDrivingDistance(String drivingDistance) {
        driving = (Button)getView().findViewById(R.id.car);
        driving.setText(drivingDistance);
        driving.setOnClickListener(new NavOnClickListener(driving.getId()));
    }

    @Override
    public void setTransitDistance(String transitDistance) {
        transit = (Button)getView().findViewById(R.id.transit);
        transit.setText(transitDistance);
        driving.setOnClickListener(new NavOnClickListener(transit.getId()));
    }
    private class NavOnClickListener implements View.OnClickListener {
        int textViewId;
        NavOnClickListener(int textViewId) {
            this.textViewId = textViewId;
        }
        @Override
        public void onClick(View view) {
            String uriString = "";
            if (textViewId == R.id.transit) {
                LatLng current = ((BaseActivityInterface)getActivity()).getCurrentLocation();
                uriString ="http://maps.google.com/maps?saddr="+ current.latitude+","+current.longitude+"&daddr="+placeLatLng.latitude+","+placeLatLng.longitude+"&mode=transit";

            }
            else {
              uriString = "google.navigation:q=" + placeLatLng.latitude + "," + placeLatLng.longitude;
                switch (textViewId) {
                    case R.id.cycle: uriString += "&mode=b";
                        break;
                    case R.id.walk: uriString += "&mode=w";
                        break;
                    default: break;
                }
            }
            Uri uri = Uri.parse(uriString);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);
        }
    }
}
