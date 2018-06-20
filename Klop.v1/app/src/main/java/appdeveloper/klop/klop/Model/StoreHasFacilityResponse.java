package appdeveloper.klop.klop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by CMDDJ on 4/28/2018.
 */

public class StoreHasFacilityResponse {

    @SerializedName("StoreHasFacility")
    @Expose
    private List<StoreHasFacility> storeHasFacility = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<StoreHasFacility> getStoreHasFacility() {
        return storeHasFacility;
    }

    public void setStoreHasFacility(List<StoreHasFacility> storeHasFacility) {
        this.storeHasFacility = storeHasFacility;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
