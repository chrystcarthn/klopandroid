package appdeveloper.klop.klop.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CMDDJ on 1/5/2018.
 */

public class StoreBody implements Serializable {

    private int intId_store;
    private int intId_user;
    private String strNameStore;
    private String strDescription;
    private String strAddress;
    private String strPhone;
    private String strLatitude;
    private String strLongitude;
    private String strManager;
    private String strManagerPhone;
    private String strStatus;
    private String strSumRate;
    private String strTotalSubs;
    private List<Treatment> treatmentList;
    private List<Schedule> scheduleList;
    private List<Photo> photoList;
    private List<Facility> facilityList;
    private List<Category> categoryList;
    private String strCreated;

    public int getIntId_store() {
        return intId_store;
    }

    public void setIntId_store(int intId_store) {
        this.intId_store = intId_store;
    }

    public int getIntId_user() {
        return intId_user;
    }

    public void setIntId_user(int intId_user) {
        this.intId_user = intId_user;
    }

    public String getStrNameStore() {
        return strNameStore;
    }

    public void setStrNameStore(String strNameStore) {
        this.strNameStore = strNameStore;
    }

    public String getStrDescription() {
        return strDescription;
    }

    public void setStrDescription(String strDescription) {
        this.strDescription = strDescription;
    }

    public String getStrAddress() {
        return strAddress;
    }

    public void setStrAddress(String strAddress) {
        this.strAddress = strAddress;
    }

    public String getStrPhone() {
        return strPhone;
    }

    public void setStrPhone(String strPhone) {
        this.strPhone = strPhone;
    }

    public String getStrLatitude() {
        return strLatitude;
    }

    public void setStrLatitude(String strLatitude) {
        this.strLatitude = strLatitude;
    }

    public String getStrLongitude() {
        return strLongitude;
    }

    public void setStrLongitude(String strLongitude) {
        this.strLongitude = strLongitude;
    }

    public String getStrManager() {
        return strManager;
    }

    public void setStrManager(String strManager) {
        this.strManager = strManager;
    }

    public String getStrManagerPhone() {
        return strManagerPhone;
    }

    public void setStrManagerPhone(String strManagerPhone) {
        this.strManagerPhone = strManagerPhone;
    }

    public String getStrStatus() {
        return strStatus;
    }

    public void setStrStatus(String strStatus) {
        this.strStatus = strStatus;
    }

    public String getStrSumRate() {
        return strSumRate;
    }

    public void setStrSumRate(String strSumRate) {
        this.strSumRate = strSumRate;
    }

    public String getStrTotalSubs() {
        return strTotalSubs;
    }

    public void setStrTotalSubs(String strTotalSubs) {
        this.strTotalSubs = strTotalSubs;
    }

    public List<Treatment> getTreatmentList() {
        return treatmentList;
    }

    public void setTreatmentList(List<Treatment> treatmentList) {
        this.treatmentList = treatmentList;
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public void setScheduleList(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }

    public List<Facility> getFacilityList() {
        return facilityList;
    }

    public void setFacilityList(List<Facility> facilityList) {
        this.facilityList = facilityList;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    public String getStrCreated() {
        return strCreated;
    }

    public void setStrCreated(String strCreated) {
        this.strCreated = strCreated;
    }
}
