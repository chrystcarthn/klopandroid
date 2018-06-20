package appdeveloper.klop.klop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CMDDJ on 6/6/2018.
 */

public class News {
    @SerializedName("ID_NEWS")
    @Expose
    private String iDNEWS;
    @SerializedName("ID_STORE")
    @Expose
    private String iDSTORE;
    @SerializedName("TITLE")
    @Expose
    private String tITLE;
    @SerializedName("CONTENT")
    @Expose
    private String cONTENT;
    @SerializedName("PUBLISHED")
    @Expose
    private String pUBLISHED;
    @SerializedName("CREATED")
    @Expose
    private String cREATED;
    @SerializedName("UPDATED")
    @Expose
    private String uPDATED;

    public String getIDNEWS() {
        return iDNEWS;
    }

    public void setIDNEWS(String iDNEWS) {
        this.iDNEWS = iDNEWS;
    }

    public String getIDSTORE() {
        return iDSTORE;
    }

    public void setIDSTORE(String iDSTORE) {
        this.iDSTORE = iDSTORE;
    }

    public String getTITLE() {
        return tITLE;
    }

    public void setTITLE(String tITLE) {
        this.tITLE = tITLE;
    }

    public String getCONTENT() {
        return cONTENT;
    }

    public void setCONTENT(String cONTENT) {
        this.cONTENT = cONTENT;
    }

    public String getPUBLISHED() {
        return pUBLISHED;
    }

    public void setPUBLISHED(String pUBLISHED) {
        this.pUBLISHED = pUBLISHED;
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

}
