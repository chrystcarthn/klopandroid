package appdeveloper.klop.klop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CMDDJ on 5/22/2018.
 */

public class Like {
    @SerializedName("ID_LIKES")
    @Expose
    private String iDLIKES;
    @SerializedName("ID_REVIEW")
    @Expose
    private String iDREVIEW;
    @SerializedName("ID_USER")
    @Expose
    private String iDUSER;
    @SerializedName("CREATED")
    @Expose
    private String cREATED;

    public String getIDLIKES() {
        return iDLIKES;
    }

    public void setIDLIKES(String iDLIKES) {
        this.iDLIKES = iDLIKES;
    }

    public String getIDREVIEW() {
        return iDREVIEW;
    }

    public void setIDREVIEW(String iDREVIEW) {
        this.iDREVIEW = iDREVIEW;
    }

    public String getIDUSER() {
        return iDUSER;
    }

    public void setIDUSER(String iDUSER) {
        this.iDUSER = iDUSER;
    }

    public String getCREATED() {
        return cREATED;
    }

    public void setCREATED(String cREATED) {
        this.cREATED = cREATED;
    }

}
