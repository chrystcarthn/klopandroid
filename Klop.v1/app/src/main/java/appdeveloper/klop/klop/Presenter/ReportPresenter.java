package appdeveloper.klop.klop.Presenter;

/**
 * Created by CMDDJ on 6/8/2018.
 */

public interface ReportPresenter {
    void showRate1(String strIdStore);
    void showRate2(String strIdStore);
    void showRate3(String strIdStore);
    void showRate4(String strIdStore);
    void showRate5(String strIdStore);

    void showWaiting(String strIdStore);
    void showApproved(String strIdStore);
    void showCanceled(String strIdStore);
    void showRejected(String strIdStore);

}
