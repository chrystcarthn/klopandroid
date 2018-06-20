package appdeveloper.klop.klop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CMDDJ on 4/26/2018.
 */

public class StoreHasCategory {
    @SerializedName("ID_STORECATEGORY_DETAIL")
    @Expose
    private String iDSTORECATEGORYDETAIL;
    @SerializedName("ID_STORE")
    @Expose
    private String iDSTORE;
    @SerializedName("ID_CATEGORY")
    @Expose
    private String iDCATEGORY;

    public String getIDSTORECATEGORYDETAIL() {
        return iDSTORECATEGORYDETAIL;
    }

    public void setIDSTORECATEGORYDETAIL(String iDSTORECATEGORYDETAIL) {
        this.iDSTORECATEGORYDETAIL = iDSTORECATEGORYDETAIL;
    }

    public String getIDSTORE() {
        return iDSTORE;
    }

    public void setIDSTORE(String iDSTORE) {
        this.iDSTORE = iDSTORE;
    }

    public String getIDCATEGORY() {
        return iDCATEGORY;
    }

    public void setIDCATEGORY(String iDCATEGORY) {
        this.iDCATEGORY = iDCATEGORY;
    }

}
