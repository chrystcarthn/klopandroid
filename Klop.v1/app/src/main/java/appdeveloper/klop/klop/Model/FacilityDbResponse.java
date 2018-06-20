package appdeveloper.klop.klop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by CMDDJ on 4/27/2018.
 */

public class FacilityDbResponse {

    @SerializedName("FacilityDb")
    @Expose
    private List<FacilityDb> facilityDb = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<FacilityDb> getFacilityDb() {
        return facilityDb;
    }

    public void setFacilityDb(List<FacilityDb> facilityDb) {
        this.facilityDb = facilityDb;
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
