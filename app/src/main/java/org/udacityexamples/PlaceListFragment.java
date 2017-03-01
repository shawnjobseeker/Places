package org.udacityexamples;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.udacityexamples.model.Result;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.udacityexamples.CategoryAdapter.FRAGMENT_INDEX;
import static org.udacityexamples.CategoryAdapter.FRAGMENT_TITLE;

/**
 * Created by Shawn Li on 2/18/2017.
 */

public class PlaceListFragment extends Fragment implements ListFragmentInterface, OnMapReadyCallback {

    private RecyclerView recyclerView;
    private int position, resultIndex;
    private String title;
    private List<Result> results;
    private GoogleMap googleMap;
    private FloatingActionButton mapButton;
    private  BaseActivityInterface activity;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        activity = (BaseActivityInterface)getActivity();
        String resourceName = getResources().getResourceName(R.array.categories) + "_" + position;
        int resourceId = getResources().getIdentifier(resourceName, "array", getActivity().getPackageName());
        String[] categories = getResources().getStringArray(resourceId);
       new Presenter(this, activity.getCurrentLocation()).observeMultipleCategories(categories);
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        position = getArguments().getInt(FRAGMENT_INDEX);
        title = getArguments().getString(FRAGMENT_TITLE);
        View view = inflater.inflate(R.layout.place_list, container, false);
        view.setTag(title);
        mapButton = (FloatingActionButton)view.findViewById(R.id.map_mode);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // map fragment
        SupportMapFragment fragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
        activity = (BaseActivityInterface)getActivity();
        onMapModeChanged(activity.isMapMode());
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            ImageView image;
            TextView name;
            TextView address;
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View v = LayoutInflater.from(getContext()).inflate(R.layout.row_places, null);
                image = (ImageView)v.findViewById(R.id.photo);
                name = (TextView)v.findViewById(R.id.name_text);
                address = (TextView)v.findViewById(R.id.address_text);
                Result result = (Result)marker.getTag();
                name.setText(result.getName());
                address.setText(result.getVicinity());
                int resource = getResources().getIdentifier(result.getIcon(), "drawable", getActivity().getPackageName());
                Picasso.with(getContext()).load(resource).into(image);
                return v;
            }
        });
        this.googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Result result = (Result)marker.getTag();
                openPlaceCard(result, true);
            }
        });
        // map mode switcher
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.setMapMode(!activity.isMapMode());
                onMapModeChanged(activity.isMapMode());
            }
        });
        if (results != null)
        showResultsInMap();
    }
    @Override
    public void showResultsInMap() {
        if (googleMap == null)
            return;
        MarkerOptions options = new MarkerOptions();

        double avgLat = 0, avgLng = 0, sumLat = 0, sumLng = 0;
        for (Result result : results) {
            double lat = result.getGeometry().getLocation().getLat();
            double lng = result.getGeometry().getLocation().getLng();
            sumLat += lat;
            sumLng += lng;
            LatLng latLng = new LatLng(lat, lng);
            int resource = getResources().getIdentifier(result.getIcon(), "drawable", getActivity().getPackageName());
            options = options.position(latLng).icon(BitmapDescriptorFactory.fromBitmap(scaleBitmap(resource, 25, 25)));
            googleMap.addMarker(options).setTag(result);
        }
        avgLat = sumLat / results.size();
        avgLng = sumLng / results.size();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(avgLat, avgLng), 12.5f));
    }

    @Override
    public void passResultSet(Set<Result> results, String statusMessage) {
        final BaseActivityInterface activity = (BaseActivityInterface)getActivity();
        this.results = new LinkedList<>(results);
        if (activity.isMapMode())
            showResultsInMap();
        this.resultIndex = 0;
        if (results.size() == 0)
            Toast.makeText(getContext(), getString(R.string.error_api, title, statusMessage), Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(new PlaceListAdapter(results, this));
    }

    @Override
    public void openPlaceCard(Result result, boolean openedFromMap) {
        if (result == null)
            return;
        resultIndex = results.indexOf(result);
        FragmentManager supportFragmentManager =  getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        Bundle args = new Bundle();
        PlaceCardFragment fragment = new PlaceCardFragment();
        fragment.setListFragment(this);
        args.putString("placeId", result.getPlaceId());
        if (result.getOpeningHours() != null)
        args.putString("hours", (result.getOpeningHours().getOpenNow()) ? getString(R.string.open) : getString(R.string.closed));
        args.putInt("icon", getResources().getIdentifier(result.getIcon(), "drawable", getActivity().getPackageName()));
        args.putStringArray("types", result.getTypes().toArray(new String[1]));
        args.putString("name", result.getName());
        args.putString("phone", result.getFormattedPhoneNumber());
        args.putString("address", result.getVicinity());
        args.putString("website", result.getWebsite());
        args.putBoolean("openedFromMap", openedFromMap);
        fragment.setArguments(args);
        transaction.replace(R.id.activity_main, fragment, "detail");
        transaction.show(fragment);
        transaction.commit();
    }

    @Override
    public Result getNextResult() {
        resultIndex += 1;
        if (resultIndex == results.size()) {
            Toast.makeText(getContext(), getString(R.string.no_more_places), Toast.LENGTH_SHORT).show();
            return null;
        }
        else
            return results.get(resultIndex);
    }

    @Override
    public Result getPreviousResult() {
        if (resultIndex == 0) {
            Toast.makeText(getContext(), getString(R.string.no_more_places), Toast.LENGTH_SHORT).show();
            return null;
        }
        else {
            resultIndex -= 1;
            return results.get(resultIndex);
        }
    }

    @Override
    public Bitmap scaleBitmap(int resource, int height, int width) {
        Drawable dr = ContextCompat.getDrawable(getContext(), resource);
        Bitmap bitmap = ((BitmapDrawable) dr).getBitmap();
        return Bitmap.createScaledBitmap(bitmap, width, height, true);
    }

    @Override
    public void onMapModeChanged(boolean mapMode) {
        if (mapMode) {
            getView().findViewById(R.id.recyclerview).setVisibility(View.GONE);
            getView().findViewById(R.id.map_container).setVisibility(View.VISIBLE);
            mapButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_format_list_bulleted));
            if (results != null)
                showResultsInMap();
        } else {
            getView().findViewById(R.id.map_container).setVisibility(View.GONE);
            getView().findViewById(R.id.recyclerview).setVisibility(View.VISIBLE);
            mapButton.setImageDrawable(ContextCompat.getDrawable(getContext(), android.R.drawable.ic_dialog_map));
        }
    }

}
