package appdeveloper.klop.klop.Presenter;

/**
 * Created by CMDDJ on 4/17/2018.
 */

public interface HomePresenter {
    void showListOutletVerified(String strIdUser, double lat, double lng);
    void showListOutletInMap(String strIdUser, double lat, double lng);
    void searchListOutletVerifiedByKeyword(String strIdUser, String strKeyword, double lat, double lng);

    void searchByFilter(String strTreatment,
                        String strCategory,
                        String strFacility,
                        String strDays,
                        String strPriceMin,
                        String strPriceMax,
                        String strRateMin,
                        String strRateMax,
                        double lat,
                        double lng);

    void showOutletHeader(String strIdStore);
}
