package appdeveloper.klop.klop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CMDDJ on 5/29/2018.
 */

public class StoreDetail {
    @SerializedName("ID_STORE")
    @Expose
    private String iDSTORE;
    @SerializedName("ID_USER")
    @Expose
    private String iDUSER;
    @SerializedName("NAME_STORE")
    @Expose
    private String nAMESTORE;
    @SerializedName("LOGO_STORE")
    @Expose
    private String lOGOSTORE;
    @SerializedName("ADDRESS_STORE")
    @Expose
    private String aDDRESSSTORE;
    @SerializedName("PHONE_STORE")
    @Expose
    private String pHONESTORE;
    @SerializedName("LATITUDE")
    @Expose
    private String lATITUDE;
    @SerializedName("LONGITUDE")
    @Expose
    private String lONGITUDE;
    @SerializedName("DISTANCE")
    @Expose
    private String dISTANCE;
    @SerializedName("STATUS_STORE")
    @Expose
    private String sTATUSSTORE;
    @SerializedName("RATE_SUM")
    @Expose
    private String rATESUM;
    @SerializedName("TOTAL_SUBS")
    @Expose
    private String tOTALSUBS;
    @SerializedName("CREATED")
    @Expose
    private String cREATED;

    public String getIDSTORE() {
        return iDSTORE;
    }

    public void setIDSTORE(String iDSTORE) {
        this.iDSTORE = iDSTORE;
    }

    public String getIDUSER() {
        return iDUSER;
    }

    public void setIDUSER(String iDUSER) {
        this.iDUSER = iDUSER;
    }

    public String getNAMESTORE() {
        return nAMESTORE;
    }

    public void setNAMESTORE(String nAMESTORE) {
        this.nAMESTORE = nAMESTORE;
    }

    public String getLOGOSTORE() {
        return lOGOSTORE;
    }

    public void setLOGOSTORE(String lOGOSTORE) {
        this.lOGOSTORE = lOGOSTORE;
    }

    public String getADDRESSSTORE() {
        return aDDRESSSTORE;
    }

    public void setADDRESSSTORE(String aDDRESSSTORE) {
        this.aDDRESSSTORE = aDDRESSSTORE;
    }

    public String getPHONESTORE() {
        return pHONESTORE;
    }

    public void setPHONESTORE(String pHONESTORE) {
        this.pHONESTORE = pHONESTORE;
    }

    public String getLATITUDE() {
        return lATITUDE;
    }

    public void setLATITUDE(String lATITUDE) {
        this.lATITUDE = lATITUDE;
    }

    public String getLONGITUDE() {
        return lONGITUDE;
    }

    public void setLONGITUDE(String lONGITUDE) {
        this.lONGITUDE = lONGITUDE;
    }

    public String getDISTANCE() {
        return dISTANCE;
    }

    public void setDISTANCE(String dISTANCE) {
        this.dISTANCE = dISTANCE;
    }

    public String getSTATUSSTORE() {
        return sTATUSSTORE;
    }

    public void setSTATUSSTORE(String sTATUSSTORE) {
        this.sTATUSSTORE = sTATUSSTORE;
    }

    public String getRATESUM() {
        return rATESUM;
    }

    public void setRATESUM(String rATESUM) {
        this.rATESUM = rATESUM;
    }

    public String getTOTALSUBS() {
        return tOTALSUBS;
    }

    public void setTOTALSUBS(String tOTALSUBS) {
        this.tOTALSUBS = tOTALSUBS;
    }

    public String getCREATED() {
        return cREATED;
    }

    public void setCREATED(String cREATED) {
        this.cREATED = cREATED;
    }
}
