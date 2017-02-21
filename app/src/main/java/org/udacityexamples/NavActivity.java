package org.udacityexamples;

import android.app.Activity;

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
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;
import java.util.List;

public class NavActivity extends AppCompatActivity
        implements  GoogleApiClient.OnConnectionFailedListener,  BaseActivityInterface {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationView mNavigationView;

    final int PLACE_PICKER_REQUEST = 1;
    private GoogleApiClient mGoogleApiClient;
    private ViewPager viewPager;
    private CategoryAdapter categoryAdapter;
    private LatLng currentLocation;
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
                .enableAutoManage(this, this)
                .build();
        mGoogleApiClient.connect();

        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (Exception ge) {
            ge.printStackTrace();
            onConnectionFailed(new ConnectionResult(ConnectionResult.NETWORK_ERROR));
        }
    }
    /**
     * A placeholder fragment containing a simple view.
     */
        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                // set adapter AFTER location is determined
                currentLocation = PlacePicker.getPlace(this, data).getLatLng();
                //setup ViewPager
                categoryAdapter = new CategoryAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.categories));
                viewPager = (ViewPager) findViewById(R.id.view_pager);
                viewPager.setAdapter(categoryAdapter);

            }
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(findViewById(R.id.root), "Connection failed", BaseTransientBottomBar.LENGTH_LONG).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }

    public LatLng getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("detail");
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }
    }

    @Override
    public GoogleApiClient getClient() {
        return mGoogleApiClient;
    }
}
