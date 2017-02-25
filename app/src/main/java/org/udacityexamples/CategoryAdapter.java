package org.udacityexamples;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

/**
 * Created by Shawn Li on 2/18/2017.
 */

public class CategoryAdapter extends FragmentStatePagerAdapter {

    public static final String FRAGMENT_INDEX = "index_fragment";
    public static final String FRAGMENT_TITLE = "title_fragment";
    private String[] array;
    @Override
    public Fragment getItem(int position) {

        if (position < 0 || position >= array.length)
            return null;
       PlaceListFragment fragment = new PlaceListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(FRAGMENT_INDEX, position);
        bundle.putString(FRAGMENT_TITLE, array[position]);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 14;
    }

    public CategoryAdapter(FragmentManager fm,String[] array) {
        super(fm);
        this.array = array;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return array[position];
    }

}
