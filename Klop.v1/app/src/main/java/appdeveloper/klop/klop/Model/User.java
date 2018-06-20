package appdeveloper.klop.klop.Model;

/**
 * Created by CMDDJ on 12/3/2017.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable{

    @SerializedName("ID_USER")
    @Expose
    private String iDUSER;
    @SerializedName("FULL_NAME")
    @Expose
    private String fULLNAME;
    @SerializedName("PASSWORD")
    @Expose
    private String pASSWORD;
    @SerializedName("PHONE")
    @Expose
    private String pHONE;
    @SerializedName("EMAIL")
    @Expose
    private String eMAIL;
    @SerializedName("STATUS")
    @Expose
    private String sTATUS;
    @SerializedName("ID_ROLE")
    @Expose
    private String iDROLE;
    @SerializedName("AVATAR")
    @Expose
    private String aVATAR;
    @SerializedName("CREATED")
    @Expose
    private String cREATED;

    public String getIDUSER() {
        return iDUSER;
    }

    public void setIDUSER(String iDUSER) {
        this.iDUSER = iDUSER;
    }

    public String getFULLNAME() {
        return fULLNAME;
    }

    public void setFULLNAME(String fULLNAME) {
        this.fULLNAME = fULLNAME;
    }

    public String getPASSWORD() {
        return pASSWORD;
    }

    public void setPASSWORD(String pASSWORD) {
        this.pASSWORD = pASSWORD;
    }

    public String getPHONE() {
        return pHONE;
    }

    public void setPHONE(String pHONE) {
        this.pHONE = pHONE;
    }

    public String getEMAIL() {
        return eMAIL;
    }

    public void setEMAIL(String eMAIL) {
        this.eMAIL = eMAIL;
    }

    public String getSTATUS() {
        return sTATUS;
    }

    public void setSTATUS(String sTATUS) {
        this.sTATUS = sTATUS;
    }

    public String getIDROLE() {
        return iDROLE;
    }

    public void setIDROLE(String iDROLE) {
        this.iDROLE = iDROLE;
    }

    public String getAVATAR() {
        return aVATAR;
    }

    public void setAVATAR(String aVATAR) {
        this.aVATAR = aVATAR;
    }

    public String getCREATED() {
        return cREATED;
    }

    public void setCREATED(String cREATED) {
        this.cREATED = cREATED;
    }
}