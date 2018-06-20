package appdeveloper.klop.klop.Model;

/**
 * Created by CMDDJ on 4/10/2018.
 */

public class CartTreatment {
    private String strIdTr, strNmTr, strQty, strHrg, strSub;

    public CartTreatment(){}


    public CartTreatment(String strIdTr, String strNmTr, String strQty, String strHrg, String strSub) {
        this.strIdTr = strIdTr;

        this.strNmTr = strNmTr;
        this.strQty = strQty;
        this.strHrg = strHrg;
        this.strSub = strSub;
    }

    public String getStrIdTr() {
        return strIdTr;
    }

    public void setStrIdTr(String strIdTr) {
        this.strIdTr = strIdTr;
    }

    public String getStrNmTr() {
        return strNmTr;
    }

    public void setStrNmTr(String strNmTr) {
        this.strNmTr = strNmTr;
    }

    public String getStrQty() {
        return strQty;
    }

    public void setStrQty(String strQty) {
        this.strQty = strQty;
    }

    public String getStrHrg() {
        return strHrg;
    }

    public void setStrHrg(String strHrg) {
        this.strHrg = strHrg;
    }

    public String getStrSub() {
        return strSub;
    }

    public void setStrSub(String strSub) {
        this.strSub = strSub;
    }
}
