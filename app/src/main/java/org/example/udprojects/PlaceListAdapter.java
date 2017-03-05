package org.example.udprojects;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import org.example.udprojects.model.Result;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Shawn Li on 2/18/2017.
 */

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.ContactListViewHolder> {

    private List<Result> results;
    private PlaceListFragment fragment;
    public class ContactListViewHolder extends RecyclerView.ViewHolder {
        Result result;
        ImageView image;
        TextView name;
        TextView address;
        public ContactListViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.photo);
            name = (TextView) view.findViewById(R.id.name_text);
            address = (TextView) view.findViewById(R.id.address_text);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragment.openPlaceCard(result, false);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(ContactListViewHolder holder, int position) {
        holder.result = results.get(position);
        Drawable resource = ContextCompat.getDrawable(fragment.getContext(), fragment.getResources().getIdentifier(holder.result.getIcon(), "drawable", fragment.getContext().getPackageName()));
        holder.image.setImageDrawable(resource);
        holder.name.setText(holder.result.getName());
        holder.address.setText(holder.result.getVicinity());
    }

    @Override
    public ContactListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_places, parent, false);
        return new ContactListViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return (results == null) ? 0 : results.size();
    }
    public PlaceListAdapter(Set<Result> resultSet, PlaceListFragment fragment) {
        this.results = new ArrayList<Result>(resultSet);
        this.fragment = fragment;
    }

}
