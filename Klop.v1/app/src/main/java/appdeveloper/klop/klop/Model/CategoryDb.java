package appdeveloper.klop.klop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CMDDJ on 4/24/2018.
 */

public class CategoryDb {

    @SerializedName("ID_CATEGORY_DB")
    @Expose
    private String iDCATEGORYDB;
    @SerializedName("NAME_CATEGORY")
    @Expose
    private String nAMECATEGORY;
    @SerializedName("CREATED")
    @Expose
    private String cREATED;

    public String getIDCATEGORYDB() {
        return iDCATEGORYDB;
    }

    public void setIDCATEGORYDB(String iDCATEGORYDB) {
        this.iDCATEGORYDB = iDCATEGORYDB;
    }

    public String getNAMECATEGORY() {
        return nAMECATEGORY;
    }

    public void setNAMECATEGORY(String nAMECATEGORY) {
        this.nAMECATEGORY = nAMECATEGORY;
    }

    public String getCREATED() {
        return cREATED;
    }

    public void setCREATED(String cREATED) {
        this.cREATED = cREATED;
    }

}
