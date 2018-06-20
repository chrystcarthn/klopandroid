package appdeveloper.klop.klop.Presenter;

import java.util.ArrayList;

/**
 * Created by CMDDJ on 4/23/2018.
 */

public interface StoreDetailPresenter {

    boolean valCategory(String strIdStore, String strIdCat);
    boolean valFacility(String strIdStore, String strIdFas);
    boolean valSchedule(ArrayList<String> strDays, ArrayList<String>  strTimeOpen, ArrayList<String>  strTimeClosed, String strIdStore);
    boolean valLogo(String strIdStore, String strLogo, String strEncodedFile);
    boolean valPhotoReview(String strIdStore, String strPhoto, String strEncodedFile, String strIdUser);

    void validatingCategory(String strIdStore, String strIdCat);
    void validatingFacility(String strIdStore, String strIdFas);
    void validatingSchedule(ArrayList<String> strDays, ArrayList<String>  strTimeOpen, ArrayList<String>  strTimeClosed, String strIdStore);
    void validatingLogo(String strIdStore, String strLogo, String strEncodedFile);
    void validatingPhotoReview(String strIdStore, String strLogo, String strEncodedFile, String strIdUser);
//
    void addCategory(String strIdStore, String strIdCat);
    void addFacility(String strIdStore, String strIdFas);
    void addSchedule(ArrayList<String> strDays, ArrayList<String>  strTimeOpen, ArrayList<String>  strTimeClosed, String strIdStore);
    void addLogo(String strIdStore, String strLogo, String strEncodedFile);
    void addBanner(String strIdStore, String strLogo, String strEncodedFile);
    void addPhotoReview(String strIdStore, String strLogo2, String strEncodedFile2, String strIdUser);


}
