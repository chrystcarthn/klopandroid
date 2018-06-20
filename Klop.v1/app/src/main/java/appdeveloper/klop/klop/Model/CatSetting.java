package appdeveloper.klop.klop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CMDDJ on 6/3/2018.
 */

public class CatSetting {
    @SerializedName("id_category_db")
    @Expose
    private String idCategoryDb;
    @SerializedName("name_category")
    @Expose
    private String nameCategory;
    @SerializedName("isChecked")
    @Expose
    private String isChecked;

    public String getIdCategoryDb() {
        return idCategoryDb;
    }

    public void setIdCategoryDb(String idCategoryDb) {
        this.idCategoryDb = idCategoryDb;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }
}
