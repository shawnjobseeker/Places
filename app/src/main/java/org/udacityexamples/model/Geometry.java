package org.udacityexamples.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Shawn Li on 2/1/2017.
 */
public class Geometry {

    @SerializedName("location")
    @Expose
    private Location location;
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Geometry))
            return false;
        Geometry geo = (Geometry)obj;
        return geo.location.equals(location);
    }

    @Override
    public String toString() {
        return location.toString();
    }
}
