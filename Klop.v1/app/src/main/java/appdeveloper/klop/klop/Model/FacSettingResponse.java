package appdeveloper.klop.klop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by CMDDJ on 6/4/2018.
 */

public class FacSettingResponse {
    @SerializedName("FacSetting")
    @Expose
    private List<FacSetting> facSetting = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<FacSetting> getFacSetting() {
        return facSetting;
    }

    public void setFacSetting(List<FacSetting> facSetting) {
        this.facSetting = facSetting;
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
