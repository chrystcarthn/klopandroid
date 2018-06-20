package appdeveloper.klop.klop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CMDDJ on 4/28/2018.
 */

public class StoreHasFacility {
    @SerializedName("ID_STOREFACILITY_DETAIL")
    @Expose
    private String iDSTOREFACILITYDETAIL;
    @SerializedName("ID_STORE")
    @Expose
    private String iDSTORE;
    @SerializedName("ID_FACILITY")
    @Expose
    private String iDFACILITY;

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
}
