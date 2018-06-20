package appdeveloper.klop.klop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by CMDDJ on 5/29/2018.
 */

public class BookingResponse {

    @SerializedName("Booking")
    @Expose
    private List<Booking> booking = null;
    @SerializedName("count")
    @Expose
    private String count;
    @SerializedName("Menunggu")
    @Expose
    private Integer menunggu;
    @SerializedName("Diterima")
    @Expose
    private Integer diterima;
    @SerializedName("Dibatalkan")
    @Expose
    private Integer dibatalkan;
    @SerializedName("Ditolak")
    @Expose
    private Integer ditolak;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Booking> getBooking() {
        return booking;
    }

    public void setBooking(List<Booking> booking) {
        this.booking = booking;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Integer getMenunggu() {
        return menunggu;
    }

    public void setMenunggu(Integer menunggu) {
        this.menunggu = menunggu;
    }

    public Integer getDiterima() {
        return diterima;
    }

    public void setDiterima(Integer diterima) {
        this.diterima = diterima;
    }

    public Integer getDibatalkan() {
        return dibatalkan;
    }

    public void setDibatalkan(Integer dibatalkan) {
        this.dibatalkan = dibatalkan;
    }

    public Integer getDitolak() {
        return ditolak;
    }

    public void setDitolak(Integer ditolak) {
        this.ditolak = ditolak;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
