package org.udacityexamples;

import android.app.Activity;

import android.location.Location;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;
import java.util.List;

public class NavActivity extends AppCompatActivity
        implements  GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,  BaseActivityInterface {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationView mNavigationView;

    final int PLACE_PICKER_REQUEST = 1;
    private GoogleApiClient mGoogleApiClient;
    private ViewPager viewPager;
    private CategoryAdapter categoryAdapter;
    private Location currentLocation;
    private List<String> categories;
    private DrawerLayout drawer;
    private Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        categories = Arrays.asList(getResources().getStringArray(R.array.categories));
        // Set up the drawer.
        mNavigationView = (NavigationView)findViewById(R.id.navigation_view);
        Menu menu = mNavigationView.getMenu();
        for (int i = 0; i < categories.size(); i++)
            menu.add(0, Menu.NONE, i, categories.get(i)).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    viewPager.setCurrentItem(menuItem.getOrder());
                    drawer.closeDrawers();
                    return true;
                }
            });
        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar) ;
        toolbar.setTitle(R.string.app_name);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        getResources().getStringArray(R.array.categories);
        //setup client
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            categoryAdapter = new CategoryAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.categories));
            viewPager = (ViewPager) findViewById(R.id.view_pager);
            viewPager.setAdapter(categoryAdapter);
            //startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (SecurityException ge) {
            ge.printStackTrace();
            onConnectionFailed(new ConnectionResult(ConnectionResult.NETWORK_ERROR));
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        if (i == CAUSE_NETWORK_LOST)
            Snackbar.make(findViewById(R.id.root), getString(R.string.network_lost), BaseTransientBottomBar.LENGTH_LONG).show();
        else if (i == CAUSE_SERVICE_DISCONNECTED)
            Snackbar.make(findViewById(R.id.root), getString(R.string.service_disconnected), BaseTransientBottomBar.LENGTH_LONG).show();
    }

    /**
     * A placeholder fragment containing a simple view.
     */


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(findViewById(R.id.root), getString(R.string.connection_failed, connectionResult.getErrorMessage()), BaseTransientBottomBar.LENGTH_LONG).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    public LatLng getCurrentLocation() {
        return new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("detail");
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }
        else
            super.onBackPressed();
    }

    @Override
    public GoogleApiClient getClient() {
        return mGoogleApiClient;
    }
}
