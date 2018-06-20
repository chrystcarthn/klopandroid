package appdeveloper.klop.klop.PresenterImp;

import android.util.Log;

import java.util.ArrayList;

import appdeveloper.klop.klop.API.InterfaceAPI;
import appdeveloper.klop.klop.API.RestClient;
import appdeveloper.klop.klop.Activity.AddStoreDetailInfo;
import appdeveloper.klop.klop.Fragment.CategoryFragment;
import appdeveloper.klop.klop.Model.CategoryDb;
import appdeveloper.klop.klop.Model.CategoryDbResponse;
import appdeveloper.klop.klop.Model.PhotoResponse;
import appdeveloper.klop.klop.Model.ScheduleResponse;
import appdeveloper.klop.klop.Model.StoreHasCategoryResponse;
import appdeveloper.klop.klop.Model.StoreHasFacilityResponse;
import appdeveloper.klop.klop.Model.StoreResponse;
import appdeveloper.klop.klop.Presenter.StoreDetailPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CMDDJ on 4/23/2018.
 */

public class StoreDetailPresenterImp implements StoreDetailPresenter {

    InterfaceAPI api;
    private static final String TAG = "StoreDetail";
    public static final String ID_USER = "1";
    String strFormattedDays;
    String strFormattedTimeOpen;
    String strFormattedTimeClosed;


    AddStoreDetailInfo addStoreDetailInfo;
    public StoreDetailPresenterImp(AddStoreDetailInfo addStoreDetailInfo) {
        this.addStoreDetailInfo = addStoreDetailInfo;
        api = RestClient.createService(InterfaceAPI.class);
    }


//    ============================================= V A L I D A T I N G =====================================================

    @Override
    public boolean valCategory(String strIdStore, String strIdCat) {
        if(strIdCat != ""){
            return true;
        }else{
            addStoreDetailInfo.dismissSpotLoading();
            addStoreDetailInfo.showInvalidCategory();
            return false;
        }
    }

    @Override
    public boolean valFacility(String strIdStore, String strIdFas) {
        if(strIdFas != ""){
            return true;
        }else{
            addStoreDetailInfo.dismissSpotLoading();
            addStoreDetailInfo.showInvalidFacility();
            return false;}
    }

    @Override
    public boolean valSchedule(ArrayList<String> strDays, ArrayList<String> strTimeOpen, ArrayList<String> strTimeClosed, String strIdStore) {
        if((strDays.size() == 0 || strTimeOpen.size() == 0 || strTimeClosed.size() == 0) ||
                (strDays.size() != strTimeOpen.size() & strDays.size() != strTimeClosed.size()) ){
            addStoreDetailInfo.dismissSpotLoading();
            addStoreDetailInfo.showInvalidSchedule();
            return true;
        }else{

            return false;
        }
    }

    @Override
    public boolean valLogo(String strIdStore, String strLogo, String strEncodedFile) {
        if(strLogo == null || strEncodedFile == null){
            addStoreDetailInfo.dismissSpotLoading();
            addStoreDetailInfo.showInvalidLogo();
            return false;
        }else{

            return true;}
    }

    @Override
    public boolean valPhotoReview(String strIdStore, String strPhoto, String strEncodedFile, String strIdUser) {
        if(strPhoto == null || strEncodedFile == null){
            addStoreDetailInfo.dismissSpotLoading();
            addStoreDetailInfo.showInvalidPhotoR();
            return false;
        }else{
            return true;
            }
    }





    @Override
    public void validatingLogo(String strIdStore, String strLogo, String strEncodedFile) {
        if(!isValidLogo(strLogo, strEncodedFile)){
            addStoreDetailInfo.dismissSpotLoading();
            addStoreDetailInfo.showInvalidLogo();
        }else{}
    }

    @Override
    public void validatingPhotoReview(String strIdStore, String strLogo, String strEncodedFile, String strIdUser) {
        if(!isValidPhoto(strLogo, strEncodedFile)){
            addStoreDetailInfo.dismissSpotLoading();
            addStoreDetailInfo.showInvalidPhotoR();
        }else{}
    }



    @Override
    public void validatingCategory(String strIdStore, String strIdCat) {
        if (!isValidCategory(strIdCat)) {
            addStoreDetailInfo.dismissSpotLoading();
            addStoreDetailInfo.showInvalidCategory();
        }
        else {
           // sendPostCategory(strIdStore, strIdCat);
        }
    }

    @Override
    public void validatingFacility(String strIdStore, String strIdFas) {
        if (!isValidFacility(strIdFas)) {
            addStoreDetailInfo.dismissSpotLoading();
            addStoreDetailInfo.showInvalidFacility();
        }
        else {
            //sendPostFacility(strIdStore, strArrayIdFas);
        }
    }

    @Override
    public void validatingSchedule(ArrayList<String> strDays, ArrayList<String> strTimeOpen, ArrayList<String> strTimeClosed, String strIdStore) {
        if (isInvalidSchedule(strDays, strTimeOpen, strTimeClosed)) {
            Log.d(TAG, "TESTING INVALID");
            addStoreDetailInfo.dismissSpotLoading();
            addStoreDetailInfo.showInvalidSchedule();
        } else {
//            getFormattedDaysAndTime(strDays, strTimeOpen, strTimeClosed);
//            Log.d(TAG, "TESTING " + strFormattedDays + " | " + strFormattedTimeOpen + " | " + strFormattedTimeClosed + " | " + strIdStore);
//            sendPostSchedule(strFormattedDays, strFormattedTimeOpen, strFormattedTimeClosed, strIdStore);
//        }
        }
    }



    public boolean isValidLogo(String strLogo, String strEncodedFile){
        if(strLogo != "" || strEncodedFile != ""){
            return true;
        }else{

            return false;}
    }

    public boolean isValidPhoto(String strLogo, String strEncodedFile){
        if(strLogo != "" || strEncodedFile != ""){
            return true;
        }else{

            return false;}
    }


//    ============================================= ADD STORE_HAS_CATEGORY ====================================================

    public boolean isValidCategory(String strArrayIdCat){
        if(strArrayIdCat != ""){
            return true;
        }else{

        return false;}
    }



    @Override
    public void addCategory(String strIdStore, String strArrayIdCat) {
        sendPostCategory(strIdStore, strArrayIdCat);
    }



    private void sendPostCategory(final String strIdOutlet, String strArrayIdCat){
        Call<StoreHasCategoryResponse> requestCall = null;
        requestCall = api.addCategoryToStore(strIdOutlet, strArrayIdCat);
        requestCall.enqueue(new Callback<StoreHasCategoryResponse>() {
            @Override
            public void onResponse(Call<StoreHasCategoryResponse> call, Response<StoreHasCategoryResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final StoreHasCategoryResponse storeHasCategoryResponse = response.body();
                    if(storeHasCategoryResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        addStoreDetailInfo.addingCategoryToStoreSuccess(strIdOutlet);

                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        addStoreDetailInfo.dismissSpotLoading();
                        addStoreDetailInfo.addingCategoryToStoreFailed();

                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
                    addStoreDetailInfo.dismissSpotLoading();
                    addStoreDetailInfo.ErrorConnectionFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreHasCategoryResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
                addStoreDetailInfo.dismissSpotLoading();
                addStoreDetailInfo.ErrorConnectionFailed();
            }
        });
    }


    //    ============================================= ADD STORE_HAS_FACILITY ====================================================


    public boolean isValidFacility(String strArrayIdFas){
        if(strArrayIdFas != ""){
            return true;
        }else{

            return false;}
    }

    @Override
    public void addFacility(String strIdStore, String strArrayIdFas) {
        sendPostFacility(strIdStore, strArrayIdFas);
    }



    public void sendPostFacility(final String strIdOutlet, String strArrayIdFas){
        Call<StoreHasFacilityResponse> requestCall = null;
        requestCall = api.addFacilityToStore(strIdOutlet, strArrayIdFas);
        requestCall.enqueue(new Callback<StoreHasFacilityResponse>() {
            @Override
            public void onResponse(Call<StoreHasFacilityResponse> call, Response<StoreHasFacilityResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final StoreHasFacilityResponse storeHasFacilityResponse = response.body();
                    if(storeHasFacilityResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        addStoreDetailInfo.addingFacilityToStoreSuccess(strIdOutlet);
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        addStoreDetailInfo.dismissSpotLoading();
                        addStoreDetailInfo.addingFacilityToStoreFailed();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
                    addStoreDetailInfo.dismissSpotLoading();
                    addStoreDetailInfo.ErrorConnectionFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreHasFacilityResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
                addStoreDetailInfo.dismissSpotLoading();
                addStoreDetailInfo.ErrorConnectionFailed();
            }
        });
    }


    //    ============================================= ADD SCHEDULE ====================================================

    public boolean isInvalidSchedule(ArrayList<String>  strDays, ArrayList<String>  strTimeOpen, ArrayList<String>  strTimeClosed){
        if((strDays.size() == 0 || strTimeOpen.size() == 0 || strTimeClosed.size() == 0) ||
                (strDays.size() != strTimeOpen.size() & strDays.size() != strTimeClosed.size()) ){
            return true;
        }else{
            return false;
        }
    }

    public void getFormattedDaysAndTime(ArrayList<String>  strDays, ArrayList<String>  strTimeOpen, ArrayList<String>  strTimeClosed) {
        strFormattedDays = strDays.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .trim();

        strFormattedTimeOpen = strTimeOpen.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .trim();

        strFormattedTimeClosed = strTimeClosed.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .trim();

    }

    @Override
    public void addSchedule(ArrayList<String>  strDays, ArrayList<String>  strTimeOpen, ArrayList<String>  strTimeClosed, String strIdStore) {
            getFormattedDaysAndTime(strDays, strTimeOpen, strTimeClosed);
            Log.d(TAG, "TESTING " + strFormattedDays + " | " + strFormattedTimeOpen + " | " + strFormattedTimeClosed + " | " + strIdStore);
            sendPostSchedule(strFormattedDays, strFormattedTimeOpen, strFormattedTimeClosed, strIdStore);
    }



    public void sendPostSchedule(String strDays, String strTimeOpen, String strTimeClosed, final String strIdStore){
        Call<ScheduleResponse> requestCall = null;
        requestCall = api.addSchedule(strDays, strTimeOpen, strTimeClosed, strIdStore);
        requestCall.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final ScheduleResponse scheduleResponse = response.body();
                    if(scheduleResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        addStoreDetailInfo.addingScheduleToStoreSuccess(strIdStore);
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        addStoreDetailInfo.dismissSpotLoading();
                        addStoreDetailInfo.addingScheduleToStoreFailed();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
                    addStoreDetailInfo.dismissSpotLoading();
                    addStoreDetailInfo.ErrorConnectionFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
                addStoreDetailInfo.dismissSpotLoading();
                addStoreDetailInfo.ErrorConnectionFailed();
            }
        });
    }


    //    ============================================= ADD LOGO ====================================================

    @Override
    public void addLogo(String strIdStore, String strLogo, String strEncodedFile) {
        sendPostAddLogo(strIdStore, strLogo, strEncodedFile);
    }



    private void sendPostAddLogo(final String strIdStore, String strLogo, String strEncodedFile) {
        Call<StoreResponse> requestCall;
        Log.d(TAG, "sendPostAddLogo " + strIdStore + " | "+ strEncodedFile);
        requestCall = api.addLogoStore(strIdStore, strLogo, strEncodedFile);
        requestCall.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final StoreResponse storeResponse = response.body();
                    if(storeResponse.getStatus().toString().equalsIgnoreCase("1")) {
//                        Log.i(TAG, "post register submitted to API"
//                                + ", respon code = " + response.code()
//                                + ", respon body = " + response.body().toString());
//
                        addStoreDetailInfo.addingLogoSuccess(strIdStore);
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        addStoreDetailInfo.dismissSpotLoading();
                        addStoreDetailInfo.addingLogoFailed();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
                    addStoreDetailInfo.dismissSpotLoading();
                    addStoreDetailInfo.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
                addStoreDetailInfo.dismissSpotLoading();
                addStoreDetailInfo.ErrorConnectionFailed();
            }
        });
    }

//    ==================================================== update banner =================================================

    @Override
    public void addBanner(String strIdStore, String strLogo, String strEncodedFile) {
        sendPostAddBanner(strIdStore, strLogo, strEncodedFile);
    }

    public void sendPostAddBanner(final String strIdStore, String strBanner, String strEncodedFile) {
        Call<StoreResponse> requestCall;
        Log.d(TAG, "sendPostAddBaner " + strIdStore + " | "+ strEncodedFile);
        requestCall = api.addBannerStore(strIdStore, strBanner, strEncodedFile);
        requestCall.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final StoreResponse storeResponse = response.body();
                    if(storeResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        addStoreDetailInfo.addingBannerSuccess(strIdStore);
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        addStoreDetailInfo.dismissSpotLoading();
                        addStoreDetailInfo.addingBannerFailed();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
                    addStoreDetailInfo.dismissSpotLoading();
                    addStoreDetailInfo.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
                addStoreDetailInfo.dismissSpotLoading();
                addStoreDetailInfo.ErrorConnectionFailed();
            }
        });
    }
    //    ============================================= ADD PHOTO REVIEW ====================================================


    @Override
    public void addPhotoReview(String strIdStore, String strLogo, String strEncodedFile, String strIdUser) {
        sendPostAddPhotoReview(strIdStore, strLogo, strEncodedFile, strIdUser);
    }

    private void sendPostAddPhotoReview(String strIdStore, String strLogo, String strEncodedFile, String strIdUser) {
        Call<PhotoResponse> requestCall;
        Log.d(TAG, "sendPostAddPhotoRev " + strIdStore + " | "+ strEncodedFile);
        requestCall = api.addPhotoRevStore(strIdStore, strLogo, strEncodedFile, strIdUser);
        requestCall.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final PhotoResponse photoResponse = response.body();
                    if(photoResponse.getStatus().toString().equalsIgnoreCase("1")) {
//                        Log.i(TAG, "post register submitted to API"
//                                + ", respon code = " + response.code()
//                                + ", respon body = " + response.body().toString());

                        addStoreDetailInfo.addingPhotoRevSuccess();

                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        addStoreDetailInfo.dismissSpotLoading();
                        addStoreDetailInfo.addingPhotoRevFailed();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
                    addStoreDetailInfo.dismissSpotLoading();
                    addStoreDetailInfo.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<PhotoResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
                addStoreDetailInfo.dismissSpotLoading();
                addStoreDetailInfo.ErrorConnectionFailed();
            }
        });
    }

}
