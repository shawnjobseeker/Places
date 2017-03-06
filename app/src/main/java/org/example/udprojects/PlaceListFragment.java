package org.example.udprojects;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;


import org.example.udprojects.model.Result;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.example.udprojects.CategoryAdapter.FRAGMENT_INDEX;
import static org.example.udprojects.CategoryAdapter.FRAGMENT_TITLE;


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
    private SupportMapFragment mapFragment;

    public Locale getLocale() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return getResources().getConfiguration().getLocales().get(0);
         else
            return getResources().getConfiguration().locale;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("locale", getLocale().getLanguage());
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
        activity = (BaseActivityInterface)getActivity();
        presentData();
        // map fragment
        mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        onMapModeChanged(activity.isMapMode());
        super.onViewCreated(view, savedInstanceState);
    }
    private void presentData() {
        activity = (BaseActivityInterface)getActivity();
        // presenter
        String resourceName = getResources().getResourceName(R.array.categories) + "_" + position;
        int resourceId = getResources().getIdentifier(resourceName, "array", getActivity().getPackageName());
        String[] categories = getResources().getStringArray(resourceId);
        new Presenter(this, activity.getCurrentLocation()).observeMultipleCategories(categories);
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
        if (mapButton != null)
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
        final LatLngBounds.Builder builder = LatLngBounds.builder();
        final AtomicBoolean hasPoints = new AtomicBoolean(false);
        for (Result result : results) {
            hasPoints.set(true);
            double lat = result.getGeometry().getLocation().getLat();
            double lng = result.getGeometry().getLocation().getLng();
            LatLng latLng = new LatLng(lat, lng);
            builder.include(latLng);
            int resource = getResources().getIdentifier(result.getIcon(), "drawable", getActivity().getPackageName());
            options = options.position(latLng).icon(BitmapDescriptorFactory.fromBitmap(scaleBitmap(resource, 25, 25)));
            googleMap.addMarker(options).setTag(result);
        }
        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                if (hasPoints.get())
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 20));
            }
        });

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
        if (isLargeScreen())
            transaction.replace(R.id.map_container, fragment, "detail");
        else
            transaction.replace(R.id.activity_main, fragment, "detail");
        transaction.show(fragment);
        transaction.commit();
    }
    // determine screen size
    private boolean isLargeScreen() {
        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        int orientation = getResources().getConfiguration().orientation;
        boolean a = (orientation == Configuration.ORIENTATION_LANDSCAPE) && (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE);
        boolean b = (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE);
        return a || b;
    }
    @Override
    public Result getNextResult() {
        if (resultIndex == results.size() - 1) {
            Toast.makeText(getContext(), getString(R.string.no_more_places), Toast.LENGTH_SHORT).show();
            return null;
        }
        else
            return results.get(resultIndex+1);
    }

    @Override
    public Result getPreviousResult() {
        if (resultIndex == 0) {
            Toast.makeText(getContext(), getString(R.string.no_more_places), Toast.LENGTH_SHORT).show();
            return null;
        }
        else
            return results.get(resultIndex-1);
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


    public void refresh() {
        if (googleMap != null)
        googleMap.clear();
        if (recyclerView != null) {
            recyclerView.removeAllViews();
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        presentData();
    }
}
