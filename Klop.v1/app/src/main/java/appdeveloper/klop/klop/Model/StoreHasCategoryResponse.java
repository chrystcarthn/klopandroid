package appdeveloper.klop.klop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by CMDDJ on 4/26/2018.
 */

public class StoreHasCategoryResponse {
    @SerializedName("StoreHasCategory")
    @Expose
    private List<StoreHasCategory> storeHasCategory = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<StoreHasCategory> getStoreHasCategory() {
        return storeHasCategory;
    }

    public void setStoreHasCategory(List<StoreHasCategory> storeHasCategory) {
        this.storeHasCategory = storeHasCategory;
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
