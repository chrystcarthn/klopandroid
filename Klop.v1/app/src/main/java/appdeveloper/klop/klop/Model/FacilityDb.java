package appdeveloper.klop.klop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CMDDJ on 4/27/2018.
 */

public class FacilityDb {

    @SerializedName("ID_FACILITY_DB")
    @Expose
    private String iDFACILITYDB;
    @SerializedName("NAME_FACILITY")
    @Expose
    private String nAMEFACILITY;
    @SerializedName("CREATED")
    @Expose
    private String cREATED;

    public String getIDFACILITYDB() {
        return iDFACILITYDB;
    }

    public void setIDFACILITYDB(String iDFACILITYDB) {
        this.iDFACILITYDB = iDFACILITYDB;
    }

    public String getNAMEFACILITY() {
        return nAMEFACILITY;
    }

    public void setNAMEFACILITY(String nAMEFACILITY) {
        this.nAMEFACILITY = nAMEFACILITY;
    }

    public String getCREATED() {
        return cREATED;
    }

    public void setCREATED(String cREATED) {
        this.cREATED = cREATED;
    }

}
