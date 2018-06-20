package appdeveloper.klop.klop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by CMDDJ on 5/29/2018.
 */

public class Booking {
    @SerializedName("id_booking")
    @Expose
    private String idBooking;
    @SerializedName("id_store")
    @Expose
    private String idStore;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("dates")
    @Expose
    private String dates;
    @SerializedName("times")
    @Expose
    private String times;
    @SerializedName("guest_name")
    @Expose
    private String guestName;
    @SerializedName("guest_phone")
    @Expose
    private String guestPhone;
    @SerializedName("status_booking")
    @Expose
    private String statusBooking;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("sum_of_people")
    @Expose
    private String sumOfPeople;
    @SerializedName("total_payment")
    @Expose
    private String totalPayment;
    @SerializedName("StoreDetail")
    @Expose
    private List<StoreDetail> storeDetail = null;
    @SerializedName("UserDetail")
    @Expose
    private List<UserDetail> userDetail = null;
    @SerializedName("BookingDetail")
    @Expose
    private List<BookingDetail> bookingDetail = null;

    public String getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(String idBooking) {
        this.idBooking = idBooking;
    }

    public String getIdStore() {
        return idStore;
    }

    public void setIdStore(String idStore) {
        this.idStore = idStore;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getGuestPhone() {
        return guestPhone;
    }

    public void setGuestPhone(String guestPhone) {
        this.guestPhone = guestPhone;
    }

    public String getStatusBooking() {
        return statusBooking;
    }

    public void setStatusBooking(String statusBooking) {
        this.statusBooking = statusBooking;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getSumOfPeople() {
        return sumOfPeople;
    }

    public void setSumOfPeople(String sumOfPeople) {
        this.sumOfPeople = sumOfPeople;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

    public List<StoreDetail> getStoreDetail() {
        return storeDetail;
    }

    public void setStoreDetail(List<StoreDetail> storeDetail) {
        this.storeDetail = storeDetail;
    }

    public List<UserDetail> getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(List<UserDetail> userDetail) {
        this.userDetail = userDetail;
    }

    public List<BookingDetail> getBookingDetail() {
        return bookingDetail;
    }

    public void setBookingDetail(List<BookingDetail> bookingDetail) {
        this.bookingDetail = bookingDetail;
    }

}
