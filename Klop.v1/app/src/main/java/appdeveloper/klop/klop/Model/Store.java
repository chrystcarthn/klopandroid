package appdeveloper.klop.klop.Model;

/**
 * Created by CMDDJ on 1/5/2018.
 */

import java.util.List;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Store {

    @SerializedName("id_store")
    @Expose
    private String idStore;
    @SerializedName("id_user")
    @Expose
    private String idUser;
    @SerializedName("name_store")
    @Expose
    private String nameStore;
    @SerializedName("logo_store")
    @Expose
    private String logoStore;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("address_store")
    @Expose
    private String addressStore;
    @SerializedName("phone_store")
    @Expose
    private String phoneStore;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("is_setmanual")
    @Expose
    private String isSetmanual;
    @SerializedName("status_store")
    @Expose
    private String statusStore;
    @SerializedName("rate_sum")
    @Expose
    private String rateSum;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("updated")
    @Expose
    private String updated;
    @SerializedName("confirmed_by")
    @Expose
    private String confirmedBy;
    @SerializedName("id_deleted")
    @Expose
    private String idDeleted;
    @SerializedName("deleted")
    @Expose
    private String deleted;
    @SerializedName("distanceKilo")
    @Expose
    private String distanceKilo;
    @SerializedName("Treatment")
    @Expose
    private List<Treatment> treatment = null;
    @SerializedName("Schedule")
    @Expose
    private List<Schedule> schedule = null;
    @SerializedName("Photo")
    @Expose
    private List<Photo> photo = null;
    @SerializedName("Review")
    @Expose
    private List<Review> review = null;
    @SerializedName("News")
    @Expose
    private List<News> news = null;
    @SerializedName("Facilities")
    @Expose
    private List<Facility> facilities = null;
    @SerializedName("Categories")
    @Expose
    private List<Category> categories = null;

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

    public String getNameStore() {
        return nameStore;
    }

    public void setNameStore(String nameStore) {
        this.nameStore = nameStore;
    }

    public String getLogoStore() {
        return logoStore;
    }

    public void setLogoStore(String logoStore) {
        this.logoStore = logoStore;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getAddressStore() {
        return addressStore;
    }

    public void setAddressStore(String addressStore) {
        this.addressStore = addressStore;
    }

    public String getPhoneStore() {
        return phoneStore;
    }

    public void setPhoneStore(String phoneStore) {
        this.phoneStore = phoneStore;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getIsSetmanual() {
        return isSetmanual;
    }

    public void setIsSetmanual(String isSetmanual) {
        this.isSetmanual = isSetmanual;
    }

    public String getStatusStore() {
        return statusStore;
    }

    public void setStatusStore(String statusStore) {
        this.statusStore = statusStore;
    }

    public String getRateSum() {
        return rateSum;
    }

    public void setRateSum(String rateSum) {
        this.rateSum = rateSum;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getConfirmedBy() {
        return confirmedBy;
    }

    public void setConfirmedBy(String confirmedBy) {
        this.confirmedBy = confirmedBy;
    }

    public String getIdDeleted() {
        return idDeleted;
    }

    public void setIdDeleted(String idDeleted) {
        this.idDeleted = idDeleted;
    }

    public String getDeleted() {
        return deleted;
    }

    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    public String getDistanceKilo() {
        return distanceKilo;
    }

    public void setDistanceKilo(String distanceKilo) {
        this.distanceKilo = distanceKilo;
    }

    public List<Treatment> getTreatment() {
        return treatment;
    }

    public void setTreatment(List<Treatment> treatment) {
        this.treatment = treatment;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }

    public List<Photo> getPhoto() {
        return photo;
    }

    public void setPhoto(List<Photo> photo) {
        this.photo = photo;
    }

    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public List<Facility> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<Facility> facilities) {
        this.facilities = facilities;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}
