package appdeveloper.klop.klop.Presenter;

/**
 * Created by CMDDJ on 4/16/2018.
 */

public interface StorePresenter {
    void valInformasiUmum(String strIdUser, String strNamaOutlet, String strAlamat, String strPhone, String strLat, String strLong, String strDistance);
    void addInformasiUmum(String strIdUser, String strNamaOutlet, String strAlamat, String strPhone, String strLat, String strLong, String strDistance);
    void showListOutletByIdUser(String strIdUser, double strLat, double strLong);
    void showInfoOutlet(String strIdOutlet, double lat, double lng);
    void getLatestStoreCreatedByUser(String strIdUser);
    void searchListUserOutletByKeyword(String strIdUser, String strKeyword, double lat, double lng);

    void getCountBook(String strIdUser);
}
