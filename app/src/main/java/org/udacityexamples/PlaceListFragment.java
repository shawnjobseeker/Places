package org.udacityexamples;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;

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

public class PlaceListFragment extends Fragment implements ListFragmentInterface {

    private RecyclerView recyclerView;
    private int position, resultIndex;
    private String title;
    private List<Result> results;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        BaseActivityInterface activity = (BaseActivityInterface)getActivity();
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
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        return view;
    }


    @Override
    public void passResultSet(Set<Result> results, String statusMessage) {
        this.results = new LinkedList<>(results);
        this.resultIndex = 0;
        if (results.size() == 0)
            Toast.makeText(getContext(), getString(R.string.error_api, title, statusMessage), Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(new PlaceListAdapter(results, this));
    }

    @Override
    public void openPlaceCard(Result result) {
        if (result == null)
            return;
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
        fragment.setArguments(args);
        transaction.replace(R.id.activity_main, fragment, "detail");
        transaction.show(fragment);
        transaction.commit();
    }

    @Override
    public Result getNextResult() {
        if (resultIndex == results.size()) {
            Toast.makeText(getContext(), getString(R.string.no_more_places), Toast.LENGTH_SHORT).show();
            return null;
        }
        else {
            resultIndex += 1;
            return results.get(resultIndex);
        }
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
}
