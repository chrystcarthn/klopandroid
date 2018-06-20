package appdeveloper.klop.klop.Presenter;

/**
 * Created by CMDDJ on 1/21/2018.
 */

public interface ProfilePresenter {
   // void editProfile(String strIdUser, String strName,  String strPassword, String strPhone, String strEmail);
    void editProfile(String strIdUser, String strName,  String strPassword, String strPhone, String strEmail, String strAvatar, String strEncodedfile);
    void refreshProfile(String strIdUser);
}
