package appdeveloper.klop.klop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CMDDJ on 6/4/2018.
 */

public class FacSetting {

    @SerializedName("id_facility_db")
    @Expose
    private String idFacilityDb;
    @SerializedName("name_facility")
    @Expose
    private String nameFacility;
    @SerializedName("isChecked")
    @Expose
    private String isChecked;

    public String getIdFacilityDb() {
        return idFacilityDb;
    }

    public void setIdFacilityDb(String idFacilityDb) {
        this.idFacilityDb = idFacilityDb;
    }

    public String getNameFacility() {
        return nameFacility;
    }

    public void setNameFacility(String nameFacility) {
        this.nameFacility = nameFacility;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }
}
