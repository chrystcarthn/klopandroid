package appdeveloper.klop.klop.Presenter;

/**
 * Created by CMDDJ on 4/20/2018.
 */

public interface AboutTabFragPresenter {
    void showDetailOutlet(String strIdOutlet,double lat, double lng);
    void showCategory(String strIdOutlet);
    void showSchedule(String strIdOutlet);
    void showFacility(String strIdOutlet);
}
