package appdeveloper.klop.klop.PresenterImp;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import appdeveloper.klop.klop.API.InterfaceAPI;
import appdeveloper.klop.klop.API.RestClient;
import appdeveloper.klop.klop.Activity.FullScreenMngPhoto;
import appdeveloper.klop.klop.Activity.ManageCategory;
import appdeveloper.klop.klop.Activity.ManageFacility;
import appdeveloper.klop.klop.Activity.ManageNews;
import appdeveloper.klop.klop.Activity.ManagePhoto;
import appdeveloper.klop.klop.Activity.ManageSchedule;
import appdeveloper.klop.klop.Activity.ManageTreatment;
import appdeveloper.klop.klop.Activity.StoreSettings;
import appdeveloper.klop.klop.Activity.UbahInformasiUmum;
import appdeveloper.klop.klop.Adapter.MngNewsAdapter;
import appdeveloper.klop.klop.Adapter.MngScheduleAdapter;
import appdeveloper.klop.klop.Adapter.MngTreatmentAdapter;
import appdeveloper.klop.klop.Model.BookingResponse;
import appdeveloper.klop.klop.Model.CatSetting;
import appdeveloper.klop.klop.Model.CatSettingResponse;
import appdeveloper.klop.klop.Model.CategoryResponse;
import appdeveloper.klop.klop.Model.FacSetting;
import appdeveloper.klop.klop.Model.FacSettingResponse;
import appdeveloper.klop.klop.Model.News;
import appdeveloper.klop.klop.Model.NewsResponse;
import appdeveloper.klop.klop.Model.Photo;
import appdeveloper.klop.klop.Model.PhotoResponse;
import appdeveloper.klop.klop.Model.ReviewResponse;
import appdeveloper.klop.klop.Model.Schedule;
import appdeveloper.klop.klop.Model.ScheduleBody;
import appdeveloper.klop.klop.Model.ScheduleResponse;
import appdeveloper.klop.klop.Model.Store;
import appdeveloper.klop.klop.Model.StoreHasCategoryResponse;
import appdeveloper.klop.klop.Model.StoreHasFacilityResponse;
import appdeveloper.klop.klop.Model.StoreResponse;
import appdeveloper.klop.klop.Model.Treatment;
import appdeveloper.klop.klop.Model.TreatmentResponse;
import appdeveloper.klop.klop.Presenter.StoreSettingsPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CMDDJ on 6/1/2018.
 */

public class StoreSettingsPresenterImp implements StoreSettingsPresenter {

    InterfaceAPI api;
    private static final String TAG = "StoreSettings";
    public static final String ID_USER = "1";
    UbahInformasiUmum ubahInformasiUmum;
    Store item;
    StoreSettings storeSettings;
    ManageCategory manageCategory;
    ManageFacility manageFacility;
    ManageTreatment manageTreatment;
    MngTreatmentAdapter mngTreatmentAdapter;
    ManagePhoto managePhoto;
    FullScreenMngPhoto fullScreenMngPhoto;
    ManageNews manageNews;
    MngNewsAdapter mngNewsAdapter;
    ManageSchedule manageSchedule;
    MngScheduleAdapter mngScheduleAdapter;

    String strFormattedDays;
    String strFormattedTimeOpen;
    String strFormattedTimeClosed;


    public StoreSettingsPresenterImp(UbahInformasiUmum ubahInformasiUmum) {
        this.ubahInformasiUmum = ubahInformasiUmum;
        api = RestClient.createService(InterfaceAPI.class);
    }

    public StoreSettingsPresenterImp(StoreSettings storeSettings) {
        this.storeSettings = storeSettings;
        api = RestClient.createService(InterfaceAPI.class);
    }

    public StoreSettingsPresenterImp(ManageCategory manageCategory) {
        this.manageCategory = manageCategory;
        api = RestClient.createService(InterfaceAPI.class);
    }

    public StoreSettingsPresenterImp(ManageFacility manageFacility) {
        this.manageFacility = manageFacility;
        api = RestClient.createService(InterfaceAPI.class);
    }

    public StoreSettingsPresenterImp(ManageTreatment manageTreatment) {
        this.manageTreatment = manageTreatment;
        api = RestClient.createService(InterfaceAPI.class);
    }

    public StoreSettingsPresenterImp(MngTreatmentAdapter mngTreatmentAdapter) {
        this.mngTreatmentAdapter = mngTreatmentAdapter;
        api = RestClient.createService(InterfaceAPI.class);
    }

    public StoreSettingsPresenterImp(ManagePhoto managePhoto) {
        this.managePhoto = managePhoto;
        api = RestClient.createService(InterfaceAPI.class);
    }

    public StoreSettingsPresenterImp(FullScreenMngPhoto fullScreenMngPhoto) {
        this.fullScreenMngPhoto = fullScreenMngPhoto;
        api = RestClient.createService(InterfaceAPI.class);
    }

    public StoreSettingsPresenterImp(ManageNews manageNews) {
        this.manageNews = manageNews;
        api = RestClient.createService(InterfaceAPI.class);
    }

    public StoreSettingsPresenterImp(MngNewsAdapter mngNewsAdapter) {
        this.mngNewsAdapter = mngNewsAdapter;
        api = RestClient.createService(InterfaceAPI.class);
    }


    public StoreSettingsPresenterImp(ManageSchedule manageSchedule) {
        this.manageSchedule = manageSchedule;
        api = RestClient.createService(InterfaceAPI.class);
    }

    public StoreSettingsPresenterImp(MngScheduleAdapter mngScheduleAdapter) {
        this.mngScheduleAdapter = mngScheduleAdapter;
        api = RestClient.createService(InterfaceAPI.class);
    }


    // validating outlet id
    private boolean isValidId(String id) {
        if (!id.equals("") & !id.equals(null)) {
            return true;
        }
        return false;
    }

    // validating outlet name
    private boolean isValidName(String name) {
        if (!name.equals("") & name.length() >= 1) {
            return true;
        }
        return false;
    }

    // validating outlet phone
    private boolean isValidPhone(String phone) {
        if (!phone.equals("") & phone.length() >= 8) {
            return true;
        }
        return false;
    }

    // validating outlet address
    private boolean isValidAddress(String address) {
        if (!address.equals("")) {
            return true;
        }
        return false;
    }


    // validating outlet kordinat
    private boolean isValidKoordinat(String strLat, String strLong) {
        if (strLat != null & strLong != null) {
            return true;
        }
        return false;
    }


    public void isValid(String strIdStore, String strNmStore, String strAddress, String strPhStore, String strLat, String strLong){
        if (!isValidId(strIdStore)) {
            ubahInformasiUmum.showInvalidId();
        }
        else if (!isValidName(strNmStore)) {
            ubahInformasiUmum.showInvalidName();
        }
        else if (!isValidPhone(strPhStore)) {
            ubahInformasiUmum.showInvalidPhone();
        }
        else if (!isValidAddress(strAddress)) {
            ubahInformasiUmum.showInvalidAddress();
        }
        else if(!isValidKoordinat(strLat, strLong)){
            ubahInformasiUmum.showInvalidLatLong();
        }
        else if(!isValidName(strNmStore) & !isValidPhone(strPhStore) & !isValidAddress(strAddress) & !isValidKoordinat(strLat, strLong))
        {
            ubahInformasiUmum.showInvalidName();
        }
        else if(isValidName(strNmStore) & isValidPhone(strPhStore) & isValidAddress(strAddress) & isValidKoordinat(strLat, strLong))
        {
            ubahInformasiUmum.removeError();
            ubahInfoUmum(strIdStore, strNmStore, strAddress, strPhStore, strLat, strLong);
        }
    }



    @Override
    public void ubahInfoUmum(String strIdStore, String strNmStore, String strAddress, String strPhStore, String strLat, String strLong) {
        sendPostEditUmum(strIdStore, strNmStore, strAddress, strPhStore, strLat, strLong);
    }



    private void sendPostEditUmum(String strIdStore, String strNmStore, String strAddress, String strPhStore, String strLat, String strLong) {
        Call<StoreResponse> requestCall;
        Log.d(TAG, "sendPostEditProfile "+strIdStore+strNmStore+strAddress+strPhStore+strLat+strLong);
        requestCall = api.editStore(strIdStore, strNmStore, strAddress, strPhStore, strLat, strLong);
        requestCall.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                Log.d(TAG, "RESPON CODE" + response.code());

                //checking if get the JSON
                if(response.isSuccessful()) {
                    final StoreResponse userResponse = response.body();
                    if(userResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        ubahInformasiUmum.dismissSpotLoading();
                        ubahInformasiUmum.editSuccess();
                    }
                    else if(userResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        Log.d(TAG, "Terjadi kesalahan pada server");
                        ubahInformasiUmum.dismissSpotLoading();
                        ubahInformasiUmum.editFailed();
                    }
                    else if(userResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        Log.d(TAG, "Tidak dapat mengubah, periksa lagi data Anda");
                        ubahInformasiUmum.dismissSpotLoading();
                        ubahInformasiUmum.dataInvalid();
                    }
                }
                else{
                    Log.d(TAG, "Terjadi kesalahan pada server");
                    Log.e(TAG, response.errorBody().toString());
                    ubahInformasiUmum.dismissSpotLoading();
                    ubahInformasiUmum.ErrorResponse();
                }
            }
            //fail to connect to API
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {

                ubahInformasiUmum.dismissSpotLoading();
                ubahInformasiUmum.ErrorConnection();
            }
        });
    }


//    ============================================== show info outlet ================================================

    @Override
    public void showInfoOutlet(String strIdStore, double lat, double lng) {
        getFetchingInfoOutlet(strIdStore, lat, lng);
    }

    private void getFetchingInfoOutlet(String strIdOutlet, double lat, double lng){
        Call<StoreResponse> requestCall;
        Log.d(TAG, "fetching info outlet "+strIdOutlet);
        requestCall = api.showInfoOutlet(strIdOutlet, lat, lng);
        requestCall.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                if (response.isSuccessful()) {
                    final StoreResponse storeResponse = response.body();
                    if (storeResponse.getStatus().toString().equalsIgnoreCase("1")) {}

                    item = response.body().getStore().get(0);
                    storeSettings.generateStoreResponse(item);
                }else  {
                    storeSettings.ErrorResponse();
                }


            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                storeSettings.ErrorConnectionFailed();
            }
        });

    }

//    ================================================= add photo ===================================================

    @Override
    public void addLogo(String strIdStore, String strLogo, String strEncodedFile) {
        sendPostAddLogo(strIdStore, strLogo, strEncodedFile);
    }



    public void sendPostAddLogo(final String strIdStore, String strLogo, String strEncodedFile) {
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
                        storeSettings.addingLogoSuccess();
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                     //   storeSettings.dismissSpotLoading();
                        storeSettings.addingLogoFailed();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
                //    storeSettings.dismissSpotLoading();
                    storeSettings.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
              //  storeSettings.dismissSpotLoading();
                storeSettings.ErrorConnectionFailed();
            }
        });
    }

//    ================================================= add banner =========================================================

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
                        storeSettings.addingBannerSuccess();
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        storeSettings.addingBannerFailed();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());

                    storeSettings.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());

                storeSettings.ErrorConnectionFailed();
            }
        });
    }

//    =============================================== report ==============================================

    @Override
    public void showStatisticReservation(String strIdStore) {
        getComingReservation(strIdStore);
    }


    @Override
    public void showStatisticRate(String strIdStore) {
        getComingRate(strIdStore);
    }

    private void getComingReservation(String strIdStore){
        Call<BookingResponse> requestCall;
        Log.d(TAG, "fetching list reservation ");
        requestCall = api.showStatistic(strIdStore);
        requestCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                if (response.isSuccessful()) {
                    final BookingResponse bookingResponse = response.body();
                    if (bookingResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        storeSettings.generateReservationResponse(bookingResponse);
                    }
                    else if (bookingResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        storeSettings.warningDataNotFound();
                    }
                    else if (bookingResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        storeSettings.loginTimeOut();
                    }
                }
                else {
                    storeSettings.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                storeSettings.ErrorConnection();
            }
        });
    }

    private void getComingRate(String strIdStore){
        Call<ReviewResponse> requestCall;
        Log.d(TAG, "fetching list reservation ");
        requestCall = api.showStatistic2(strIdStore);
        requestCall.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    final ReviewResponse reviewResponse = response.body();
                    if (reviewResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        storeSettings.generateReviewResponse(reviewResponse);
                    }
                    else if (reviewResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        storeSettings.warningDataNotFound();
                    }
                    else if (reviewResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        storeSettings.loginTimeOut();
                    }
                }
                else {
                    storeSettings.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                storeSettings.ErrorConnection();
            }
        });
    }


//    ============================================== kelola category ===============================================

    @Override
    public void showCatSetting(String strIdStore) {
        showCategory(strIdStore);
    }

    @Override
    public void deleteCatSetting(String strIdStore, String strIdCat) {
        delCatSetting(strIdStore, strIdCat);
    }

    @Override
    public void addCategory(String strIdStore, String strIdCat) {
        sendPostCategory(strIdStore, strIdCat);
    }



    public void sendPostCategory(final String strIdOutlet, String strArrayIdCat){
        Call<StoreHasCategoryResponse> requestCall = null;
        requestCall = api.addCategoryToStore(strIdOutlet, strArrayIdCat);
        requestCall.enqueue(new Callback<StoreHasCategoryResponse>() {
            @Override
            public void onResponse(Call<StoreHasCategoryResponse> call, Response<StoreHasCategoryResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final StoreHasCategoryResponse storeHasCategoryResponse = response.body();
                    if(storeHasCategoryResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        manageCategory.addingCategoryToStoreSuccess();

                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
//                        manageCategory.dismissSpotLoading();
                        manageCategory.addingCategoryToStoreFailed();

                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
//                    manageCategory.dismissSpotLoading();
                    manageCategory.ErrorConnectionFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreHasCategoryResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
//                manageCategory.dismissSpotLoading();
                manageCategory.ErrorConnectionFailed();
            }
        });
    }


    private void delCatSetting(String strIdStore, String strIdCat){
        Call<StoreHasCategoryResponse> requestCall;
        Log.d(TAG, "delete category klop "+strIdStore+"|"+strIdCat);
        requestCall = api.deleteCat(strIdStore, strIdCat);
        requestCall.enqueue(new Callback<StoreHasCategoryResponse>() {
            @Override
            public void onResponse(Call<StoreHasCategoryResponse> call, Response<StoreHasCategoryResponse> response) {
                if(response.isSuccessful()) {
                    final StoreHasCategoryResponse storeHasCategoryResponse = response.body();
                    if (storeHasCategoryResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        manageCategory.delSuccess();
                        Log.d(TAG, "status like 1");
                    } else if (storeHasCategoryResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        manageCategory.delFailed();
                        //     reviewAdapter.status2(false);
                        Log.d(TAG, "KETERANGAN "+storeHasCategoryResponse.getMessage());
                    }else if (storeHasCategoryResponse.getStatus().toString().equalsIgnoreCase("4")) {
                        manageCategory.delFailed();
                        //     reviewAdapter.status2(false);
                        Log.d(TAG, "KETERANGAN "+storeHasCategoryResponse.getMessage());
                    }
                }
                else{
                    manageCategory.ErrorResponse();
                    Log.d(TAG, "ANEH BAT "+response.errorBody());
                    Log.d(TAG, "ANEH BAT2 "+response.body());
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreHasCategoryResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                manageCategory.ErrorConnectionFailed();
            }
        });
    }




    private void showCategory(String strIdStore){
        Call<CatSettingResponse> requestCall;
        Log.d(TAG, "fetching info outlet ");
        requestCall = api.showCatSettings(strIdStore);
        requestCall.enqueue(new Callback<CatSettingResponse>() {
            @Override
            public void onResponse(Call<CatSettingResponse> call, Response<CatSettingResponse> response) {
                if (response.isSuccessful()) {
                    final CatSettingResponse photoResponse = response.body();
                    if (photoResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        manageCategory.generateCatSettingResponse((ArrayList<CatSetting>) response.body().getCatSetting());
                    } else{
                        manageCategory.noCategory();
                    }
                } else   manageCategory.ErrorResponse();
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<CatSettingResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                manageCategory.ErrorConnectionFailed();
            }
        });
    }


//    ======================================================= kelola fasilitas ===============================================

    @Override
    public void showFacSetting(String strIdStore) {
        showFacility(strIdStore);
    }

    @Override
    public void deleteFacSetting(String strIdStore, String strIdFac) {
        delFacSetting(strIdStore, strIdFac);
    }

    @Override
    public void addFacility(String strIdStore, String strIdFac) {
        sendPostFacility(strIdStore, strIdFac);
    }

    private void showFacility(String strIdStore){
        Call<FacSettingResponse> requestCall;
        Log.d(TAG, "fetching info outlet ");
        requestCall = api.showFacSettings(strIdStore);
        requestCall.enqueue(new Callback<FacSettingResponse>() {
            @Override
            public void onResponse(Call<FacSettingResponse> call, Response<FacSettingResponse> response) {
                if (response.isSuccessful()) {
                    final FacSettingResponse facSettingResponse = response.body();
                    if (facSettingResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        manageFacility.generateFacSettingResponse((ArrayList<FacSetting>) response.body().getFacSetting());
                    } else{
                        manageFacility.noFacility();
                    }
                } else   manageFacility.ErrorResponse();
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<FacSettingResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                manageFacility.ErrorConnectionFailed();
            }
        });
    }

    public void sendPostFacility(final String strIdOutlet, String strIdFac){
        Call<StoreHasFacilityResponse> requestCall = null;
        requestCall = api.addFacilityToStore(strIdOutlet, strIdFac);
        requestCall.enqueue(new Callback<StoreHasFacilityResponse>() {
            @Override
            public void onResponse(Call<StoreHasFacilityResponse> call, Response<StoreHasFacilityResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final StoreHasFacilityResponse storeHasFacilityResponse = response.body();
                    if(storeHasFacilityResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        manageFacility.addingFacilityToStoreSuccess();

                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
//                        manageCategory.dismissSpotLoading();
                        manageFacility.addingFacilityToStoreFailed();

                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
//                    manageCategory.dismissSpotLoading();
                    manageFacility.ErrorConnectionFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreHasFacilityResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
//                manageCategory.dismissSpotLoading();
                manageFacility.ErrorConnectionFailed();
            }
        });
    }


    private void delFacSetting(String strIdStore, String strIdFac){
        Call<StoreHasFacilityResponse> requestCall;
        Log.d(TAG, "delete facility klop "+strIdStore+"|"+strIdFac);
        requestCall = api.deleteFac(strIdStore, strIdFac);
        requestCall.enqueue(new Callback<StoreHasFacilityResponse>() {
            @Override
            public void onResponse(Call<StoreHasFacilityResponse> call, Response<StoreHasFacilityResponse> response) {
                if(response.isSuccessful()) {
                    final StoreHasFacilityResponse storeHasFacilityResponse = response.body();
                    if (storeHasFacilityResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        manageFacility.delSuccess();
                        Log.d(TAG, "status like 1");
                    } else if (storeHasFacilityResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        manageFacility.delFailed();
                        //     reviewAdapter.status2(false);
                        Log.d(TAG, "KETERANGAN "+storeHasFacilityResponse.getMessage());
                    }else if (storeHasFacilityResponse.getStatus().toString().equalsIgnoreCase("4")) {
                        manageFacility.delFailed();
                        //     reviewAdapter.status2(false);
                        Log.d(TAG, "KETERANGAN "+storeHasFacilityResponse.getMessage());
                    }
                }
                else{
                    manageFacility.ErrorResponse();
                    Log.d(TAG, "ANEH BAT "+response.errorBody());
                    Log.d(TAG, "ANEH BAT2 "+response.body());
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreHasFacilityResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                manageFacility.ErrorConnectionFailed();
            }
        });
    }


//    ========================================== kelola treatment ========================================

    private boolean isValidNmTr(String strNmTr){
        if(strNmTr != null || !strNmTr.equals("")){
            return true;
        }
        return false;
    }

    private boolean isValidHrgTr(String strHrgTr){
        if(strHrgTr != null || !strHrgTr.equals("")){
            return true;
        }
        return false;
    }

    @Override
    public void showTreatment(String strIdStore) {
        getFetchingTreatmentByStore(strIdStore);
    }


    @Override
    public void addNewTreatment(String strIdStore, String strNmTr, String strDesc, String strHargaTr) {
        if(!isValidNmTr(strNmTr)){
            manageTreatment.showErrorNm();
        } else
        if(!isValidHrgTr(strHargaTr)){
            manageTreatment.showErrorHrg();
        } else
        if(!isValidNmTr(strNmTr) & !isValidHrgTr(strHargaTr)){
            manageTreatment.showErrorNm();
        } else
        if(isValidNmTr(strNmTr) & isValidHrgTr(strHargaTr)){
            manageTreatment.showNoError();
            sendPostAddTreatment(strIdStore, strNmTr, strDesc, strHargaTr);
        }
    }

    @Override
    public void updateTreatment(String strIdStore, String strIdTr, String strNmTr, String strDesc, String strHargaTr) {
//        if(!isValidNmTr(strNmTr)){
//            mngTreatmentAdapter.showErrorNm();
//        } else
//        if(!isValidHrgTr(strHargaTr)){
//            mngTreatmentAdapter.showErrorHrg();
//        } else
//        if(!isValidNmTr(strNmTr) & !isValidHrgTr(strHargaTr)){
//            mngTreatmentAdapter.showErrorNm();
//        } else
//        if(isValidNmTr(strNmTr) & isValidHrgTr(strHargaTr)){
         //   mngTreatmentAdapter.showNoError();
            sendPostUpdateTreatment(strIdStore, strIdTr, strNmTr, strDesc, strHargaTr);
       // }
    }

    @Override
    public void deleteTreatement(String strIdStore, String strIdTr) {
        sendPostDeleteTreatment(strIdStore, strIdTr);
    }

    @Override
    public void searchTreatment(String strIdStore, String strKeyword) {
        searchTr(strIdStore, strKeyword);
    }


    private void getFetchingTreatmentByStore(String strIdOutlet){
        Call<TreatmentResponse> requestCall;
        Log.d(TAG, "fetching info outlet "+strIdOutlet);
        requestCall = api.showTreatmentByStore(strIdOutlet);
        requestCall.enqueue(new Callback<TreatmentResponse>() {
            @Override
            public void onResponse(Call<TreatmentResponse> call, Response<TreatmentResponse> response) {
                if(response.isSuccessful()) {
                    final TreatmentResponse treatmentResponse = response.body();
                    if (treatmentResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        manageTreatment.generateTreatmentResponse((ArrayList<Treatment>) response.body().getTreatment());
                    } else {
                        manageTreatment.warningNoTreatmentFound();
                    }
                }else{
                    manageTreatment.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<TreatmentResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                manageTreatment.ErrorConnectionFailed();
            }
        });
    }

    private void searchTr(String strIdOutlet, String strKeyword){
        Call<TreatmentResponse> requestCall;
        Log.d(TAG, "fetching info outlet "+strIdOutlet);
        requestCall = api.searchTreatment(strIdOutlet, strKeyword);
        requestCall.enqueue(new Callback<TreatmentResponse>() {
            @Override
            public void onResponse(Call<TreatmentResponse> call, Response<TreatmentResponse> response) {
                if(response.isSuccessful()) {
                    final TreatmentResponse treatmentResponse = response.body();
                    if (treatmentResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        manageTreatment.generateTreatmentResponse((ArrayList<Treatment>) response.body().getTreatment());
                    } else {
                        manageTreatment.warningNoTreatmentFound();
                    }
                }else{
                    manageTreatment.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<TreatmentResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                manageTreatment.ErrorConnectionFailed();
            }
        });
    }

    private void sendPostAddTreatment(String strIdStore, String strNmTr, String strDesc, String strHargaTr){
        // treatmentTabFragment.showSpotLoading();
        Call<TreatmentResponse> requestCall = null;
        requestCall = api.addTreatment(strIdStore, strNmTr, strDesc, strHargaTr);
        requestCall.enqueue(new Callback<TreatmentResponse>() {
            @Override
            public void onResponse(Call<TreatmentResponse> call, Response<TreatmentResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final TreatmentResponse treatmentResponse = response.body();
                    if(treatmentResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        manageTreatment.AddingTrSuccess();
                    }
                    else if(treatmentResponse.getStatus().toString().equalsIgnoreCase("3")){
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        manageTreatment.showErrorNm();
                    }else if(treatmentResponse.getStatus().toString().equalsIgnoreCase("4")){
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        manageTreatment.showErrorHrg();
                    }else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        manageTreatment.ErrorAddingTrFailed();
                    }
                }
                else{
                    manageTreatment.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<TreatmentResponse> call, Throwable t) {
                manageTreatment.ErrorConnectionFailed();
            }
        });
    }



    private void sendPostUpdateTreatment(final String strIdStore, String strIdTr, String strNmTr, String strDesc, String strHargaTr){
        // treatmentTabFragment.showSpotLoading();
        Call<TreatmentResponse> requestCall = null;
        requestCall = api.updateTreatment(strIdTr, strNmTr, strDesc, strHargaTr);
        requestCall.enqueue(new Callback<TreatmentResponse>() {
            @Override
            public void onResponse(Call<TreatmentResponse> call, Response<TreatmentResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final TreatmentResponse treatmentResponse = response.body();
                    if(treatmentResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        mngTreatmentAdapter.updateTrSuccess();
                       // getFetchingTreatmentByStore(strIdStore);
                     //   manageTreatment.mWaveSwipeRefreshLayout.setRefreshing(false);
                    } else if(treatmentResponse.getStatus().toString().equalsIgnoreCase("3")){
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        mngTreatmentAdapter.showErrorNm();
                    }else if(treatmentResponse.getStatus().toString().equalsIgnoreCase("4")) {
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        mngTreatmentAdapter.showErrorHrg();
                    }else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        mngTreatmentAdapter.ErrorUpdateTr();
                    }
                }
                else{
                    mngTreatmentAdapter.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<TreatmentResponse> call, Throwable t) {
                mngTreatmentAdapter.ErrorConnectionFailed();
            }
        });
    }

    private void sendPostDeleteTreatment(final String strIdStore, String strIdTr){
        // treatmentTabFragment.showSpotLoading();
        Call<TreatmentResponse> requestCall = null;
        requestCall = api.deleteTreatment(strIdTr);
        requestCall.enqueue(new Callback<TreatmentResponse>() {
            @Override
            public void onResponse(Call<TreatmentResponse> call, Response<TreatmentResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final TreatmentResponse treatmentResponse = response.body();
                    if(treatmentResponse.getStatus().toString().equalsIgnoreCase("1")) {
                     //   getFetchingTreatmentByStore(strIdStore);
                        mngTreatmentAdapter.deleteTrSuccess();
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        mngTreatmentAdapter.ErrorDeleteTr();
                    }
                }
                else{
                    mngTreatmentAdapter.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<TreatmentResponse> call, Throwable t) {
                mngTreatmentAdapter.ErrorConnectionFailed();
            }
        });
    }


//    ================================================ kelola foto ===================================================


    @Override
    public void showPhotoReview(String strIdStore) {
        getFetchingPhotoReview(strIdStore);
    }

    @Override
    public void useAsBanner(String strIdStore, String strUrl) {
        sendPostAsBaner(strIdStore, strUrl);
    }

    @Override
    public void deletePhoto(String strIdPhoto) {
        delPhoto(strIdPhoto);
    }

    @Override
    public void addPhotoReview(String strIdStore, String strBanner, String strEncodedFile, String strIdUser) {
        sendPostAddPhotoReview(strIdStore, strBanner, strEncodedFile, strIdUser);
    }

    private void getFetchingPhotoReview(String strIdStore){
        Call<PhotoResponse> requestCall;
        Log.d(TAG, "fetching header outlet "+strIdStore);
        requestCall = api.getOutletPhotoHeader(strIdStore);
        requestCall.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
                if (response.isSuccessful()) {
                    final PhotoResponse photoResponse = response.body();
                    if (photoResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        managePhoto.generatePhotoResponse((ArrayList<Photo>) response.body().getPhoto());
                    }
                    else if(photoResponse.getStatus().toString().equalsIgnoreCase("0")){
                        managePhoto.warningDataNotFound();
                    }
                }
                else{
                    managePhoto.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<PhotoResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                managePhoto.ErrorConnectionFailed();
            }
        });

    }

    private void sendPostAsBaner(String strIdStore, String strUrl){

        Call<StoreResponse> requestCall = null;
        requestCall = api.usePhotoAsBanner(strIdStore, strUrl);
        requestCall.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final StoreResponse storeResponse = response.body();
                    if(storeResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        fullScreenMngPhoto.updateBannerSuccess();

                    }else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        fullScreenMngPhoto.errorUpdateBanner();
                    }
                }
                else{
                    fullScreenMngPhoto.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                fullScreenMngPhoto.ErrorConnectionFailed();
            }
        });
    }

    private void delPhoto(String strIdPhoto){
        Call<PhotoResponse> requestCall = null;
        Log.d(TAG, "adding outlet "+strIdPhoto);
        requestCall = api.delPhoto(strIdPhoto);
        requestCall.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final PhotoResponse photoResponse = response.body();
                    if(photoResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        fullScreenMngPhoto.deleteSuccess();
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        fullScreenMngPhoto.deleteFailed();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
                    fullScreenMngPhoto.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<PhotoResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
                fullScreenMngPhoto.ErrorConnectionFailed();
            }
        });
    }

    private void sendPostAddPhotoReview(String strIdStore, String strBanner, String strEncodedFile, String strIdUser) {
        Call<PhotoResponse> requestCall;
        Log.d(TAG, "sendPostAddPhotoRev " + strIdStore + " | "+ strEncodedFile);
        requestCall = api.addPhotoRevStore(strIdStore, strBanner, strEncodedFile, strIdUser);
        requestCall.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final PhotoResponse photoResponse = response.body();
                    if(photoResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        managePhoto.addingPhotoRevSuccess();
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
//                        managePhoto.dismissSpotLoading();
                        managePhoto.addingPhotoRevFailed();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
//                    managePhoto.dismissSpotLoading();
                    managePhoto.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<PhotoResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
//                managePhoto.dismissSpotLoading();
                managePhoto.ErrorConnectionFailed();
            }
        });
    }


//    ============================================== kelola news ===========================================================

    @Override
    public void addNews(String strIdStore, String strTitle, String strContent, String strStatus) {
        if(!isValidNmTr(strTitle)){
            manageNews.showErrorJudul();
        } else
        if(!isValidHrgTr(strContent)){
            manageNews.showErrorIsi();
        } else
        if(!isValidNmTr(strTitle) & !isValidHrgTr(strContent)){
            manageNews.showErrorJudul();
        } else
        if(isValidNmTr(strTitle) & isValidHrgTr(strContent)){
            manageNews.showNoError();
            sendPostAddNews(strIdStore, strTitle, strContent, strStatus);
        }
    }



    @Override
    public void showAllNews(String strIdStore) {
        getFetchingAllNews(strIdStore);
    }

    @Override
    public void deleteNews(String strIdNews) {
        delNews(strIdNews);
    }

    @Override
    public void updateNews(String strIdNews, String strTitle, String strContent, String strStatus) {
        sendPostUpdateNews(strIdNews, strTitle, strContent, strStatus);
    }

    private void sendPostAddNews(String strIdStore, String strTitle, String strContent, String strStatus){

        Call<NewsResponse> requestCall = null;
        requestCall = api.addNews(strIdStore, strTitle, strContent, strStatus);
        requestCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final NewsResponse newsResponse = response.body();
                    if(newsResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        manageNews.AddingNewsSuccess();
                    }
                    else if(newsResponse.getStatus().toString().equalsIgnoreCase("3")){
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        manageNews.showErrorJudul();
                    }else if(newsResponse.getStatus().toString().equalsIgnoreCase("4")){
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        manageNews.showErrorIsi();
                    }else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        manageNews.ErrorAddingNewsFailed();
                    }
                }
                else{
                    manageNews.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                manageNews.ErrorConnectionFailed();
            }
        });
    }


    private void getFetchingAllNews(String strIdOutlet){
        Call<NewsResponse> requestCall;
        Log.d(TAG, "fetching info outlet "+strIdOutlet);
        requestCall = api.showAllNews(strIdOutlet);
        requestCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if(response.isSuccessful()) {
                    final NewsResponse newsResponse = response.body();
                    if (newsResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        manageNews.generateNewsResponse((ArrayList<News>) response.body().getNews());
                    } else {
                        manageNews.warningNoNewsFound();
                    }
                }else{
                    manageNews.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                manageNews.ErrorConnectionFailed();
            }
        });
    }



    private void delNews(String strIdNews){
        Call<NewsResponse> requestCall = null;
        Log.d(TAG, "delete news "+strIdNews);
        requestCall = api.delNews(strIdNews);
        requestCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final NewsResponse newsResponse = response.body();
                    if(newsResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        mngNewsAdapter.deleteSuccess();
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        mngNewsAdapter.deleteFailed();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
                    mngNewsAdapter.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
                mngNewsAdapter.ErrorConnectionFailed();
            }
        });
    }

    private void sendPostUpdateNews(String strIdNews, String strTitle, String strContent, String strStatus){

        Call<NewsResponse> requestCall = null;
        requestCall = api.updateNews(strIdNews, strTitle, strContent, strStatus);
        requestCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final NewsResponse newsResponse = response.body();
                    if(newsResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        mngNewsAdapter.updateNewsSuccess();
                        // getFetchingTreatmentByStore(strIdStore);
                        //   manageTreatment.mWaveSwipeRefreshLayout.setRefreshing(false);
                    } else if(newsResponse.getStatus().toString().equalsIgnoreCase("3")){
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        mngNewsAdapter.showErrorJudul();
                    }else if(newsResponse.getStatus().toString().equalsIgnoreCase("4")) {
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        mngNewsAdapter.showErrorIsi();
                    }else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        mngNewsAdapter.ErrorUpdateNews();
                    }
                }
                else{
                    mngNewsAdapter.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                mngNewsAdapter.ErrorConnectionFailed();
            }
        });
    }

// ======================================================= delete store ========================================================

    @Override
    public void deleteOutlet(String strIdStore) {
        sendPostDeleteStore(strIdStore);
    }


    private void sendPostDeleteStore(String strIdStore){

        Call<StoreResponse> requestCall = null;
        requestCall = api.deleteStore(strIdStore);
        requestCall.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final StoreResponse storeResponse = response.body();
                    if(storeResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        //   getFetchingTreatmentByStore(strIdStore);
                        storeSettings.deleteSuccess();
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        storeSettings.ErrorDelete();
                    }
                }
                else{
                    storeSettings.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                storeSettings.ErrorConnectionFailed();
            }
        });
    }


//    ==================================================== kelola jadwal ====================================================

    @Override
    public void showSchedule(String strIdOutlet) {
        getFetchingScheduleByStore(strIdOutlet);
    }

    @Override
    public void addSchedule(ArrayList<String> strDays, ArrayList<String> strTimeOpen, ArrayList<String> strTimeClosed, String strIdStore) {
        getFormattedDaysAndTime(strDays, strTimeOpen, strTimeClosed);
        Log.d(TAG, "TESTING " + strFormattedDays + " | " + strFormattedTimeOpen + " | " + strFormattedTimeClosed + " | " + strIdStore);
        sendPostAllSchedule(strFormattedDays, strFormattedTimeOpen, strFormattedTimeClosed, strIdStore);

    }

    private boolean isValidDays(String strDays){
        if(strDays == null)
            return false;
        else return true;
    }

    private boolean isValidBuka(String strBuka){
        if(strBuka == null || strBuka.equals("00:00"))
            return false;
        else return true;
    }

    private boolean isValidTutup(String strTutup){
        if(strTutup == null || strTutup.equals("00:00"))
            return false;
        else return true;
    }

    private boolean isValidStore(String strStore){
        if(strStore == null)
            return false;
        else return true;
    }

    @Override
    public void addHarianSchedule(String strDays, String strTimeOpen, String strTimeClosed, String strIdStore) {
        if(!isValidDays(strDays)){
            manageSchedule.showErrorDays();
        } else
        if(!isValidBuka(strTimeOpen)){
            manageSchedule.showErrorBuka();
        } else
        if(!isValidTutup(strTimeClosed)){
            manageSchedule.showErrorTutup();
        } else
        if(!isValidStore(strIdStore)){
            manageSchedule.showErrorStore();
        } else
        if(!isValidDays(strDays) & !isValidBuka(strTimeOpen) & !isValidTutup(strTimeClosed) & !isValidStore(strIdStore)){
            manageSchedule.showErrorDays();
        } else
        if(isValidDays(strDays) & isValidBuka(strTimeOpen) & isValidTutup(strTimeClosed) & isValidStore(strIdStore)){
            manageSchedule.showNoError();
            sendPostHarianSch(strDays, strTimeOpen, strTimeClosed, strIdStore);
        }


    }

    @Override
    public void deleteSchedule(String strIdSch) {
        delSchedule(strIdSch);
    }

    @Override
    public void deleteAllSchedules(String strIdOutlet) {
        delAllSchedule(strIdOutlet);
    }

    @Override
    public void updateSchedule(String strSchedule, String strDays, String strTimeOpen, String strTimeClosed) {
        sendPostUpdateSch(strSchedule, strDays, strTimeOpen, strTimeClosed);
    }

    @Override
    public void updateManualSch(String strIdStore, String strStatus) {
        sendUpdateManualSch(strIdStore, strStatus);
    }

    public void sendPostHarianSch(String strDays, String strTimeOpen, String strTimeClosed, final String strIdStore){
        Call<ScheduleResponse> requestCall = null;
        requestCall = api.addHarianSch(strDays, strTimeOpen, strTimeClosed, strIdStore);
        requestCall.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final ScheduleResponse scheduleResponse = response.body();
                    if(scheduleResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        manageSchedule.addingHarianSuccess();
                    }
                    else{
                        manageSchedule.addingHarianFailed();
                    }
                }
                else{
                    manageSchedule.ErrorConnectionFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                manageSchedule.ErrorConnectionFailed();
            }
        });
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

    private void sendPostAllSchedule(String strDays, String strTimeOpen, String strTimeClosed, final String strIdStore){
        Call<ScheduleResponse> requestCall = null;
        requestCall = api.addSchedule(strDays, strTimeOpen, strTimeClosed, strIdStore);
        requestCall.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final ScheduleResponse scheduleResponse = response.body();
                    if(scheduleResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        manageSchedule.addingAllScheduleSuccess();
                    }
                    else{
//                        Log.d(TAG, "Tidak bisa menambah JSON");
//                        addStoreDetailInfo.dismissSpotLoading();
                        manageSchedule.addingAllScheduleFailed();
                    }
                }
                else{
//                    Log.d(TAG, "Gagal parsing JSON");
//                    Log.e(TAG, response.errorBody().toString());
//                    addStoreDetailInfo.dismissSpotLoading();
                    manageSchedule.ErrorConnectionFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
//                Log.e(TAG, "Unable to submit post to API");
//                Log.e(TAG, t.getMessage());
//                addStoreDetailInfo.dismissSpotLoading();
                manageSchedule.ErrorConnectionFailed();
            }
        });
    }


    private void getFetchingScheduleByStore(String strIdOutlet){
        Call<ScheduleResponse> requestCall;
        Log.d(TAG, "fetching info outlet "+strIdOutlet);
        requestCall = api.showScheduleByStore(strIdOutlet);
        requestCall.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                if(response.isSuccessful()) {
                    final ScheduleResponse scheduleResponse = response.body();
                    if(scheduleResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        manageSchedule.generateScheduleResponse((ArrayList<Schedule>) response.body().getSchedule());
                    }
                    else{
                        manageSchedule.warningNoDataFound();
                    }
                }
                else {
                    manageSchedule.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                manageSchedule.ErrorConnectionFailed();
            }
        });

    }

    private void delAllSchedule(String strIdStore){
        Call<ScheduleResponse> requestCall = null;
        Log.d(TAG, "delete all schedule "+strIdStore);
        requestCall = api.deleteAllSchedules(strIdStore);
        requestCall.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final ScheduleResponse scheduleResponse = response.body();
                    if(scheduleResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        manageSchedule.deleteAllSuccess();
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        manageSchedule.deleteAllFailed();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
                    manageSchedule.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
                manageSchedule.ErrorConnectionFailed();
            }
        });
    }


    private void delSchedule(String strIdSch){
        Call<ScheduleResponse> requestCall = null;
        Log.d(TAG, "delete schedule "+strIdSch);
        requestCall = api.deleteSchedule(strIdSch);
        requestCall.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final ScheduleResponse scheduleResponse = response.body();
                    if(scheduleResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        mngScheduleAdapter.deleteSuccess();
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        mngScheduleAdapter.deleteFailed();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
                    mngScheduleAdapter.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
                mngScheduleAdapter.ErrorConnectionFailed();
            }
        });
    }

    private void sendPostUpdateSch(String strSchedule, String strDays, String strTimeOpen, String strTimeClosed){

        Call<ScheduleResponse> requestCall = null;
        requestCall = api.updateSchedule(strSchedule, strDays, strTimeOpen, strTimeClosed);
        requestCall.enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final ScheduleResponse scheduleResponse = response.body();
                    if(scheduleResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        mngScheduleAdapter.updateSuccess();

                    } else if(scheduleResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        mngScheduleAdapter.showErrorStore();
                    }else if(scheduleResponse.getStatus().toString().equalsIgnoreCase("4")){
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        mngScheduleAdapter.showErrorDays();
                    }else if(scheduleResponse.getStatus().toString().equalsIgnoreCase("5")) {
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        mngScheduleAdapter.showErrorBuka();
                    }else if(scheduleResponse.getStatus().toString().equalsIgnoreCase("6")) {
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        mngScheduleAdapter.showErrorTutup();
                    }else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        mngScheduleAdapter.ErrorUpdate();
                    }
                }
                else{
                    mngScheduleAdapter.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                mngScheduleAdapter.ErrorConnectionFailed();
            }
        });
    }


    private void sendUpdateManualSch(String strIdStore, String strManual){

        Call<StoreResponse> requestCall = null;
        requestCall = api.updateSetManual(strIdStore, strManual);
        requestCall.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final StoreResponse storeResponse = response.body();
                    if(storeResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        storeSettings.updateManualSuccess();

                    } else  {
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        storeSettings.showErrorInput();
                    }
                }
                else{
                    storeSettings.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                storeSettings.ErrorConnectionFailed();
            }
        });
    }



}
