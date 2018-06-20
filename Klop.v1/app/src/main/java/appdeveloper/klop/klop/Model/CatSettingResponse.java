package appdeveloper.klop.klop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by CMDDJ on 6/3/2018.
 */

public class CatSettingResponse {
    @SerializedName("CatSetting")
    @Expose
    private List<CatSetting> catSetting = null;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<CatSetting> getCatSetting() {
        return catSetting;
    }

    public void setCatSetting(List<CatSetting> catSetting) {
        this.catSetting = catSetting;
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
