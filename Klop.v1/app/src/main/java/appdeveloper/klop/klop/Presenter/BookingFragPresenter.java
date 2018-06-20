package appdeveloper.klop.klop.Presenter;

import java.util.ArrayList;

/**
 * Created by CMDDJ on 5/26/2018.
 */

public interface BookingFragPresenter {
    void doesUserHaveOutlet(String strIdUser);

    void showListTreatment(String strIdStore);

    void showMyBooked(String strIdUser);
    void addNewBooked(String strIdStore, String strIdUser, String strDate, String strTime, String strNmGuest, String strTelpGuest, String strPeople, String strTotal);
    void addDetailBooked(String strIdBooking, ArrayList<String> strIdTr, ArrayList<String>  strQty, ArrayList<String>  strPrice,  ArrayList<String>  strSub);
    void getLatestBookingCreatedByUser(String strIdUser);
    void showMyBookedDetail(String strIdBooking);

    void showReservationReq(String strIdOwner);
    void showMyReservationDetail(String strIdBooking);

    void approveRes(String strIdBooking);
    void cancelRes(String strIdBooking);
    void dropRes(String strIdBooking);
    void declineRes(String strIdBooking);

}
