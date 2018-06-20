package appdeveloper.klop.klop.Model;

/**
 * Created by CMDDJ on 4/22/2018.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Review {


    @SerializedName("id_review")
    @Expose
    private String idReview;
    @SerializedName("id_store")
    @Expose
    private String idStore;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("text_review")
    @Expose
    private String textReview;
    @SerializedName("rate")
    @Expose
    private String rate;
    @SerializedName("total_report")
    @Expose
    private String totalReport;
    @SerializedName("status_review")
    @Expose
    private String statusReview;
    @SerializedName("total_likes")
    @Expose
    private String totalLikes;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("avatar")
    @Expose
    private String avatar;

    public String getIdReview() {
        return idReview;
    }

    public void setIdReview(String idReview) {
        this.idReview = idReview;
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

    public String getTextReview() {
        return textReview;
    }

    public void setTextReview(String textReview) {
        this.textReview = textReview;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getTotalReport() {
        return totalReport;
    }

    public void setTotalReport(String totalReport) {
        this.totalReport = totalReport;
    }

    public String getStatusReview() {
        return statusReview;
    }

    public void setStatusReview(String statusReview) {
        this.statusReview = statusReview;
    }

    public String getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(String totalLikes) {
        this.totalLikes = totalLikes;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

}
