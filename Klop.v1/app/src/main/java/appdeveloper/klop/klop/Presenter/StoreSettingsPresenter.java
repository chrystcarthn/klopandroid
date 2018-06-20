package appdeveloper.klop.klop.Presenter;

import java.util.ArrayList;

/**
 * Created by CMDDJ on 6/1/2018.
 */

public interface StoreSettingsPresenter {
    void showInfoOutlet(String strIdStore, double lat, double lng);
    void ubahInfoUmum(String strIdStore, String strNmStore, String strPhStore, String strAddress, String strLat, String strLong);

    void addLogo(String strIdStore, String strLogo, String strEncodedFile);
    void addBanner(String strIdStore, String strLogo, String strEncodedFile);

    void showStatisticRate(String strIdStore);
    void showStatisticReservation(String strIdStore);

    void showCatSetting(String strIdStore);
    void deleteCatSetting(String strIdStore, String strIdCat);
    void addCategory(String strIdStore, String strIdCat);

    void showFacSetting(String strIdStore);
    void deleteFacSetting(String strIdStore, String strIdCat);
    void addFacility(String strIdStore, String strIdCat);

    void showTreatment(String strIdStore);
    void addNewTreatment(String strIdStore, String strNmTr, String strDesc, String strHargaTr);
    void updateTreatment(String strIdStore, String strIdTr, String strNmTr, String strDesc, String strHargaTr);
    void deleteTreatement(String strIdStore, String strIdTr);
    void searchTreatment(String strIdStore, String strKeyword);

    void showPhotoReview(String strIdStore);
    void useAsBanner(String strIdStore, String strUrl);
    void deletePhoto(String strIdPhoto);
    void addPhotoReview(String strIdStore, String strBanner, String strEncodedFile, String strIdUser);

    void addNews(String strIdStore, String strTitle, String strContent, String strStatus);
    void showAllNews(String strIdStore);
    void deleteNews(String strIdNews);
    void updateNews(String strIdNews, String strTitle, String strContent, String strStatus);

    void deleteOutlet(String strIdStore);

    void showSchedule(String strIdOutlet);
    void addSchedule(ArrayList<String> strDays, ArrayList<String>  strTimeOpen, ArrayList<String>  strTimeClosed, String strIdStore);
    void addHarianSchedule(String strDays, String strTimeOpen, String strTimeClosed, String strIdStore);
    void deleteSchedule(String strIdSch);
    void deleteAllSchedules(String strIdOutlet);
    void updateSchedule(String strSchedule, String strDays, String strTimeOpen, String strTimeClosed);

    void updateManualSch(String strIdStore, String strStatus);

}
