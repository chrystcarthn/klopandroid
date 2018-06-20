package appdeveloper.klop.klop.Model;

import java.io.Serializable;

/**
 * Created by CMDDJ on 4/10/2018.
 */

public class CategoryBody implements Serializable {
    private String strIdStoreCatDetail;
    private String strIdStore;
    private String strIdCat;
    private String strNmCat;
    private String strCreated;

    public String getStrIdStoreCatDetail() {
        return strIdStoreCatDetail;
    }

    public void setStrIdStoreCatDetail(String strIdStoreCatDetail) {
        this.strIdStoreCatDetail = strIdStoreCatDetail;
    }

    public String getStrIdStore() {
        return strIdStore;
    }

    public void setStrIdStore(String strIdStore) {
        this.strIdStore = strIdStore;
    }

    public String getStrIdCat() {
        return strIdCat;
    }

    public void setStrIdCat(String strIdCat) {
        this.strIdCat = strIdCat;
    }

    public String getStrNmCat() {
        return strNmCat;
    }

    public void setStrNmCat(String strNmCat) {
        this.strNmCat = strNmCat;
    }

    public String getStrCreated() {
        return strCreated;
    }

    public void setStrCreated(String strCreated) {
        this.strCreated = strCreated;
    }
}
