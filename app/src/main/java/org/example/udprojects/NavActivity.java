package org.example.udprojects;

import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;
import java.util.List;

public class NavActivity extends AppCompatActivity
        implements  GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, BaseActivityInterface {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationView mNavigationView;
    private boolean mapMode;
    private GoogleApiClient mGoogleApiClient;
    private ViewPager viewPager;
    private CategoryAdapter categoryAdapter;
    private Location currentLocation;
    private List<String> categories;
    private DrawerLayout drawer;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        mapMode = getPreferences(MODE_PRIVATE).getBoolean("mapMode", false);
        categories = Arrays.asList(getResources().getStringArray(R.array.categories));
        TypedArray icons = getResources().obtainTypedArray(R.array.categories_icons);

        // Set up the drawer.
        mNavigationView = (NavigationView)findViewById(R.id.navigation_view);
        Menu menu = mNavigationView.getMenu();
        for (int i = 0; i < categories.size(); i++)
            menu.add(0, Menu.NONE, i, categories.get(i))
            .setIcon(ContextCompat.getDrawable(this, icons.getResourceId(i, -1)))
                    .setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    viewPager.setCurrentItem(menuItem.getOrder());
                    Fragment fragment = getSupportFragmentManager().findFragmentByTag("detail");
                    if (fragment != null)
                        removePlaceCard(fragment);
                    drawer.closeDrawers();
                    return true;
                }
            });
        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar) ;
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        // prevent drawer from being swiped open, whilst maintaining functionality of navigation button
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!drawer.isDrawerOpen(mNavigationView))
                drawer.openDrawer(mNavigationView);
                else
                    drawer.closeDrawers();
            }
        });
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
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        try {
            SharedPreferences prefs = getPreferences(MODE_PRIVATE);
            currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            categoryAdapter = new CategoryAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.categories));
            viewPager = (ViewPager) findViewById(R.id.view_pager);
            viewPager.setAdapter(categoryAdapter);
            viewPager.setCurrentItem(prefs.getInt("setPage", 0));
            // store location immediately!
            SharedPreferences.Editor editor = prefs.edit();
            if (currentLocation != null) {
                editor.putFloat("currentLat", (float) currentLocation.getLatitude());
                editor.putFloat("currentLng", (float) currentLocation.getLongitude());
                editor.apply();
            }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SettingsFragment fragment = new SettingsFragment();
            transaction.replace(R.id.activity_main, fragment, "settings");
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Snackbar.make(findViewById(R.id.root), getString(R.string.connection_failed, connectionResult.getErrorMessage()), BaseTransientBottomBar.LENGTH_LONG).show();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("mapMode", mapMode);
        if (viewPager != null)
        editor.putInt("setPage", viewPager.getCurrentItem());
        editor.apply();
        mGoogleApiClient.disconnect();
    }

    public LatLng getCurrentLocation() {
        // location from SharedPreferences
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        return new LatLng(prefs.getFloat("currentLat", 0.0f), prefs.getFloat("currentLng", 0.0f));
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("detail");
        if (fragment != null)
            removePlaceCard(fragment);
        else
            super.onBackPressed();
    }

    @Override
    public GoogleApiClient getClient() {
        return mGoogleApiClient;
    }


    @Override
    public void setMapMode(boolean mapMode) {
        this.mapMode = mapMode;
        // 3 fragments visible at once!
       List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof ListFragmentInterface && fragment.isVisible())
                ((ListFragmentInterface)fragment).onMapModeChanged(mapMode);
        }
    }

    @Override
    public boolean isMapMode() {
        return mapMode;
    }

    @Override
    public void removePlaceCard(final Fragment fragment) {
        if (fragment != null) {

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.remove(fragment);
                    transaction.commit();



        }
    }

    @Override
    public void refreshViewPager() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof ListFragmentInterface && fragment.isVisible())
                ((ListFragmentInterface) fragment).refresh();
        }
    }
}
