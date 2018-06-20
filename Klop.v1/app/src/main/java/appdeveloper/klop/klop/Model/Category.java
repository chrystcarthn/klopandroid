package appdeveloper.klop.klop.Model;

/**
 * Created by CMDDJ on 4/10/2018.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("ID_STORECATEGORY_DETAIL")
    @Expose
    private String iDSTORECATEGORYDETAIL;
    @SerializedName("ID_STORE")
    @Expose
    private String iDSTORE;
    @SerializedName("ID_CATEGORY")
    @Expose
    private String iDCATEGORY;
    @SerializedName("CREATED")
    @Expose
    private String cREATED;
    @SerializedName("ID_CATEGORY_DB")
    @Expose
    private String iDCATEGORYDB;
    @SerializedName("NAME_CATEGORY")
    @Expose
    private String nAMECATEGORY;
    @SerializedName("CREATED_BY")
    @Expose
    private String cREATEDBY;
    @SerializedName("PUBLISHED")
    @Expose
    private String pUBLISHED;
    @SerializedName("UPDATED")
    @Expose
    private String uPDATED;
    @SerializedName("UPDATED_BY")
    @Expose
    private String uPDATEDBY;

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

    public String getCREATED() {
        return cREATED;
    }

    public void setCREATED(String cREATED) {
        this.cREATED = cREATED;
    }

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

    public String getCREATEDBY() {
        return cREATEDBY;
    }

    public void setCREATEDBY(String cREATEDBY) {
        this.cREATEDBY = cREATEDBY;
    }

    public String getPUBLISHED() {
        return pUBLISHED;
    }

    public void setPUBLISHED(String pUBLISHED) {
        this.pUBLISHED = pUBLISHED;
    }

    public String getUPDATED() {
        return uPDATED;
    }

    public void setUPDATED(String uPDATED) {
        this.uPDATED = uPDATED;
    }

    public String getUPDATEDBY() {
        return uPDATEDBY;
    }

    public void setUPDATEDBY(String uPDATEDBY) {
        this.uPDATEDBY = uPDATEDBY;
    }

}
