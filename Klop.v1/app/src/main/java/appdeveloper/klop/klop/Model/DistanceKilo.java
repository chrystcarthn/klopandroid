package appdeveloper.klop.klop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CMDDJ on 6/11/2018.
 */

public class DistanceKilo {
    @SerializedName("DistanceOutlet")
    @Expose
    private String distanceOutlet;

    public String getDistanceOutlet() {
        return distanceOutlet;
    }

    public void setDistanceOutlet(String distanceOutlet) {
        this.distanceOutlet = distanceOutlet;
    }
}
