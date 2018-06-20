package appdeveloper.klop.klop.Model;

/**
 * Created by CMDDJ on 4/10/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ir.mirrajabi.searchdialog.core.Searchable;

public class Treatment implements Searchable{

    @SerializedName("ID_TREATMENT")
    @Expose
    private String iDTREATMENT;
    @SerializedName("ID_STORE")
    @Expose
    private String iDSTORE;
    @SerializedName("NAME_TREATMENT")
    @Expose
    private String nAMETREATMENT;
    @SerializedName("DESCRIPTION_TREATMENT")
    @Expose
    private String dESCRIPTIONTREATMENT;
    @SerializedName("PRICE_TREATMENT")
    @Expose
    private String pRICETREATMENT;
    @SerializedName("CREATED")
    @Expose
    private String cREATED;
    @SerializedName("UPDATED")
    @Expose
    private String uPDATED;
    @SerializedName("ISDELETED")
    @Expose
    private String iSDELETED;
    @SerializedName("DELETED")
    @Expose
    private String dELETED;

    public String getIDTREATMENT() {
        return iDTREATMENT;
    }

    public void setIDTREATMENT(String iDTREATMENT) {
        this.iDTREATMENT = iDTREATMENT;
    }

    public String getIDSTORE() {
        return iDSTORE;
    }

    public void setIDSTORE(String iDSTORE) {
        this.iDSTORE = iDSTORE;
    }

    public String getNAMETREATMENT() {
        return nAMETREATMENT;
    }

    public void setNAMETREATMENT(String nAMETREATMENT) {
        this.nAMETREATMENT = nAMETREATMENT;
    }

    public String getDESCRIPTIONTREATMENT() {
        return dESCRIPTIONTREATMENT;
    }

    public void setDESCRIPTIONTREATMENT(String dESCRIPTIONTREATMENT) {
        this.dESCRIPTIONTREATMENT = dESCRIPTIONTREATMENT;
    }

    public String getPRICETREATMENT() {
        return pRICETREATMENT;
    }

    public void setPRICETREATMENT(String pRICETREATMENT) {
        this.pRICETREATMENT = pRICETREATMENT;
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

    public String getISDELETED() {
        return iSDELETED;
    }

    public void setISDELETED(String iSDELETED) {
        this.iSDELETED = iSDELETED;
    }

    public String getDELETED() {
        return dELETED;
    }

    public void setDELETED(String dELETED) {
        this.dELETED = dELETED;
    }


    @Override
    public String getTitle() {
        return nAMETREATMENT;
    }




}