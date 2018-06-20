package appdeveloper.klop.klop.Presenter;

/**
 * Created by CMDDJ on 5/16/2018.
 */

public interface ReviewPresenter {
    void addReview(String strIdStore, String strIdUser, String strTextReview, String strRate);
    void updateRateStore(String strIdStore);


}
