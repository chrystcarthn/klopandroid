package appdeveloper.klop.klop.Model;

/**
 * Created by CMDDJ on 4/10/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Schedule {

    @SerializedName("ID_SCHEDULE")
    @Expose
    private String iDSCHEDULE;
    @SerializedName("DAYS")
    @Expose
    private String dAYS;
    @SerializedName("TIME_OPEN")
    @Expose
    private String tIMEOPEN;
    @SerializedName("TIME_CLOSED")
    @Expose
    private String tIMECLOSED;
    @SerializedName("NATIONALDAYCLOSEDEXCEPTSUNDAYC")
    @Expose
    private String nATIONALDAYCLOSEDEXCEPTSUNDAYC;
    @SerializedName("CREATED")
    @Expose
    private String cREATED;
    @SerializedName("UPDATED")
    @Expose
    private String uPDATED;
    @SerializedName("ID_STORE")
    @Expose
    private String iDSTORE;

    public String getIDSCHEDULE() {
        return iDSCHEDULE;
    }

    public void setIDSCHEDULE(String iDSCHEDULE) {
        this.iDSCHEDULE = iDSCHEDULE;
    }

    public String getDAYS() {
        return dAYS;
    }

    public void setDAYS(String dAYS) {
        this.dAYS = dAYS;
    }

    public String getTIMEOPEN() {
        return tIMEOPEN;
    }

    public void setTIMEOPEN(String tIMEOPEN) {
        this.tIMEOPEN = tIMEOPEN;
    }

    public String getTIMECLOSED() {
        return tIMECLOSED;
    }

    public void setTIMECLOSED(String tIMECLOSED) {
        this.tIMECLOSED = tIMECLOSED;
    }

    public String getNATIONALDAYCLOSEDEXCEPTSUNDAYC() {
        return nATIONALDAYCLOSEDEXCEPTSUNDAYC;
    }

    public void setNATIONALDAYCLOSEDEXCEPTSUNDAYC(String nATIONALDAYCLOSEDEXCEPTSUNDAYC) {
        this.nATIONALDAYCLOSEDEXCEPTSUNDAYC = nATIONALDAYCLOSEDEXCEPTSUNDAYC;
    }

    public String getCREATED() {
        return cREATED;
    }

    public void setCREATED(String cREATED) {
        this.cREATED = cREATED;
    }

    public String getUPDATED() {
        return uPDATED;
    }

    public void setUPDATED(String uPDATED) {
        this.uPDATED = uPDATED;
    }

    public String getIDSTORE() {
        return iDSTORE;
    }

    public void setIDSTORE(String iDSTORE) {
        this.iDSTORE = iDSTORE;
    }

}