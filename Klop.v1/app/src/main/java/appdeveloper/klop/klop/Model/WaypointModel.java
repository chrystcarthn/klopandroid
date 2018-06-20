package appdeveloper.klop.klop.Model;

/**
 * Created by CMDDJ on 1/12/2018.
 */

public class WaypointModel {
    private String textAlamat;
    private String textLongitude;
    private String textLatitude;

    public WaypointModel(String textAlamat,String textLongitude,String textLatitude){
        this.textAlamat = textAlamat;
        this.textLongitude = textLongitude;
        this.textLatitude = textLatitude;
    }

    public String getTextAlamat() {
        return textAlamat;
    }

    public void setTextAlamat(String textAlamat) {
        this.textAlamat = textAlamat;
    }

    public String getTextLongitude() {
        return textLongitude;
    }

    public void setTextLongitude(String textLongitude) {
        this.textLongitude = textLongitude;
    }

    public String getTextLatitude() {
        return textLatitude;
    }

    public void setTextLatitude(String textLatitude) {
        this.textLatitude = textLatitude;
    }
}
