package appdeveloper.klop.klop.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by CMDDJ on 4/22/2018.
 */

public class ReviewResponse {

    @SerializedName("Review")
    @Expose
    private List<Review> review = null;
    @SerializedName("Count")
    @Expose
    private String count;
    @SerializedName("Bintang1")
    @Expose
    private Integer bintang1;
    @SerializedName("Bintang2")
    @Expose
    private Integer bintang2;
    @SerializedName("Bintang3")
    @Expose
    private Integer bintang3;
    @SerializedName("Bintang4")
    @Expose
    private Integer bintang4;
    @SerializedName("Bintang5")
    @Expose
    private Integer bintang5;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Integer getBintang1() {
        return bintang1;
    }

    public void setBintang1(Integer bintang1) {
        this.bintang1 = bintang1;
    }

    public Integer getBintang2() {
        return bintang2;
    }

    public void setBintang2(Integer bintang2) {
        this.bintang2 = bintang2;
    }

    public Integer getBintang3() {
        return bintang3;
    }

    public void setBintang3(Integer bintang3) {
        this.bintang3 = bintang3;
    }

    public Integer getBintang4() {
        return bintang4;
    }

    public void setBintang4(Integer bintang4) {
        this.bintang4 = bintang4;
    }

    public Integer getBintang5() {
        return bintang5;
    }

    public void setBintang5(Integer bintang5) {
        this.bintang5 = bintang5;
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
