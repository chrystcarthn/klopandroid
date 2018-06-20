package appdeveloper.klop.klop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by CMDDJ on 5/29/2018.
 */

public class BookingDetail {
    @SerializedName("id_booking_detail")
    @Expose
    private String idBookingDetail;
    @SerializedName("id_booking")
    @Expose
    private String idBooking;
    @SerializedName("id_treatment")
    @Expose
    private String idTreatment;
    @SerializedName("name_treatment")
    @Expose
    private String nameTreatment;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("price_now")
    @Expose
    private String priceNow;
    @SerializedName("sub_total")
    @Expose
    private String subTotal;

    public String getIdBookingDetail() {
        return idBookingDetail;
    }

    public void setIdBookingDetail(String idBookingDetail) {
        this.idBookingDetail = idBookingDetail;
    }

    public String getIdBooking() {
        return idBooking;
    }

    public void setIdBooking(String idBooking) {
        this.idBooking = idBooking;
    }

    public String getIdTreatment() {
        return idTreatment;
    }

    public void setIdTreatment(String idTreatment) {
        this.idTreatment = idTreatment;
    }

    public String getNameTreatment() {
        return nameTreatment;
    }

    public void setNameTreatment(String nameTreatment) {
        this.nameTreatment = nameTreatment;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPriceNow() {
        return priceNow;
    }

    public void setPriceNow(String priceNow) {
        this.priceNow = priceNow;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }
}
