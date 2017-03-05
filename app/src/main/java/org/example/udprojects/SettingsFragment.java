package org.example.udprojects;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jonathan on 03/03/2017.
 */

public class SettingsFragment extends Fragment implements View.OnClickListener {

    private Spinner units;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.settings, container, false);
        v.findViewById(R.id.button_ok).setOnClickListener(this);
        v.findViewById(R.id.button_cancel).setOnClickListener(this);
        units = (Spinner)v.findViewById(R.id.units);
        units.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.units)));
        return v;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_ok) {
            TextView radiusText = (TextView)getView().findViewById(R.id.sweep_radius);
            double radius = Double.parseDouble(radiusText.getText().toString());
            if (units.getSelectedItem().equals(getString(R.string.mi)))
                radius = radius * 1.6039;
            if (radius > 50.0)
                Toast.makeText(getContext(), getString(R.string.too_far), Toast.LENGTH_SHORT).show();
            else
                saveSettings((int)(radius * 1000.0));
        }
        else
            closeFragment();
    }
    private void closeFragment() {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.remove(this);
        transaction.commit();
    }
    private void saveSettings(int rad) {
        TextView keywords = (TextView)getView().findViewById(R.id.keywords);
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("presenter_radius", rad);
        if (keywords.getText().length() == 0)
            editor.putString("presenter_keywords", null);
        else
            editor.putString("presenter_keywords", keywords.getText().toString());
        editor.apply();
        BaseActivityInterface activity = (BaseActivityInterface)getActivity();
        activity.refreshViewPager();
        closeFragment();
    }

}
