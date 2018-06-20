package appdeveloper.klop.klop.Presenter;

/**
 * Created by CMDDJ on 5/14/2018.
 */

public interface PhotoTabFragPresenter {
    void showPhotoReview(String strIdStore);

    void addPhotoReview(String strIdStore, String strBanner, String strEncodedFile, String strIdUser);

}
