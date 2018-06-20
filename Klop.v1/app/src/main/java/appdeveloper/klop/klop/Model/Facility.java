package appdeveloper.klop.klop.Model;

/**
 * Created by CMDDJ on 4/10/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Facility {
    @SerializedName("ID_STOREFACILITY_DETAIL")
    @Expose
    private String iDSTOREFACILITYDETAIL;
    @SerializedName("ID_STORE")
    @Expose
    private String iDSTORE;
    @SerializedName("ID_FACILITY")
    @Expose
    private String iDFACILITY;
    @SerializedName("CREATED")
    @Expose
    private String cREATED;
    @SerializedName("ID_FACILITY_DB")
    @Expose
    private String iDFACILITYDB;
    @SerializedName("NAME_FACILITY")
    @Expose
    private String nAMEFACILITY;
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

    public String getIDSTOREFACILITYDETAIL() {
        return iDSTOREFACILITYDETAIL;
    }

    public void setIDSTOREFACILITYDETAIL(String iDSTOREFACILITYDETAIL) {
        this.iDSTOREFACILITYDETAIL = iDSTOREFACILITYDETAIL;
    }

    public String getIDSTORE() {
        return iDSTORE;
    }

    public void setIDSTORE(String iDSTORE) {
        this.iDSTORE = iDSTORE;
    }

    public String getIDFACILITY() {
        return iDFACILITY;
    }

    public void setIDFACILITY(String iDFACILITY) {
        this.iDFACILITY = iDFACILITY;
    }

    public String getCREATED() {
        return cREATED;
    }

    public void setCREATED(String cREATED) {
        this.cREATED = cREATED;
    }

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

