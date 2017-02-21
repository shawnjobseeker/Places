package org.udacityexamples;

import android.app.Activity;
import android.view.View;

import org.udacityexamples.BaseActivityInterface;
import org.udacityexamples.Presenter;
import org.udacityexamples.model.Result;

import java.util.Set;

/**
 * Created by Shawn Li on 2/18/2017.
 */

public interface ListFragmentInterface {
    void passResultSet(Set<Result> results, String status);
    View getView();
    Activity getActivity();
    void openPlaceCard(Result result);
}
