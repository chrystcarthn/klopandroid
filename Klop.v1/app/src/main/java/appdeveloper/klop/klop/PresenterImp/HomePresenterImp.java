package appdeveloper.klop.klop.PresenterImp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import appdeveloper.klop.klop.API.InterfaceAPI;
import appdeveloper.klop.klop.API.RestClient;
import appdeveloper.klop.klop.Activity.SearchingResult;
import appdeveloper.klop.klop.Activity.ShowDetailStore;
import appdeveloper.klop.klop.Activity.StoreInMap;
import appdeveloper.klop.klop.Fragment.HomeFragment;
import appdeveloper.klop.klop.Model.Photo;
import appdeveloper.klop.klop.Model.PhotoResponse;
import appdeveloper.klop.klop.Model.Store;
import appdeveloper.klop.klop.Model.StoreResponse;
import appdeveloper.klop.klop.Presenter.HomePresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CMDDJ on 4/17/2018.
 */

public class HomePresenterImp implements HomePresenter{

    HomeFragment homeFragment;
    ShowDetailStore showDetailStore;
    SearchingResult searchingResult;
    StoreInMap storeInMap;
    Photo item;
    InterfaceAPI api;
    Context context;
    private static final String TAG = "HomePresenterImp";
  //  public static final String ID_USER = "1";


    public HomePresenterImp(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
        api = RestClient.createService(InterfaceAPI.class);

    }

    public HomePresenterImp(ShowDetailStore showDetailStore) {
        this.showDetailStore = showDetailStore;
        api = RestClient.createService(InterfaceAPI.class);

    }

    public HomePresenterImp(StoreInMap storeInMap) {
        this.storeInMap = storeInMap;
        api = RestClient.createService(InterfaceAPI.class);

    }

    public HomePresenterImp(SearchingResult searchingResult) {
        this.searchingResult = searchingResult;
        api = RestClient.createService(InterfaceAPI.class);

    }

    @Override
    public void showListOutletVerified(String strIdUser, double lat, double lng) {
        getFetchingListOutletVerified(strIdUser, lat, lng);
    }




    private void getFetchingListOutletVerified(String strIdUser, double lat, double lng){
        Call<StoreResponse> requestCall;
        Log.d(TAG, "fetching list outlet ");
        requestCall = api.showListAllOutletVerified(strIdUser, lat, lng);
        requestCall.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                if (response.isSuccessful()) {
                    final StoreResponse storeResponse = response.body();
                    if (storeResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        homeFragment.generateStoreResponse((ArrayList<Store>) response.body().getStore());
                    }
                    else if (storeResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        homeFragment.warningDataNotFound();
                    }
                    else if (storeResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        homeFragment.loginTimeOut();
                    }
                }
                else {
                    homeFragment.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                homeFragment.ErrorConnection();
            }
        });


    }

//    ======================================== SEARCH BY KEY ======================================

    @Override
    public void searchListOutletVerifiedByKeyword(String strIdUser, String strKeyword, double lat, double lng) {
        searchStoreByKey(strIdUser, strKeyword, lat, lng);
    }

    private void searchStoreByKey(String strIdUser, String strKeyword, double lat, double lng){
        Call<StoreResponse> requestCall;
        Log.d(TAG, "fetching list outlet "+strKeyword);
        requestCall = api.searchAllStoreByKeyword(strIdUser, strKeyword, lat, lng);
        requestCall.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                if (response.isSuccessful()) {
                    final StoreResponse storeResponse = response.body();
                    if (storeResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        homeFragment.generateStoreResponse((ArrayList<Store>) response.body().getStore());
                    }
                    else if (storeResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        homeFragment.warningDataNotFound();
                    }
                    else if (storeResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        homeFragment.loginTimeOut();
                    }
                }
                else {
                    homeFragment.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                homeFragment.ErrorConnection();
            }
        });

    }

    //    ================================================ FILTER ============================================================

    @Override
    public void searchByFilter(String strTreatment, String strCategory, String strFacility, String strDays, String strPriceMin, String strPriceMax, String strRateMin, String strRateMax, double lat, double lng) {
        searchDataByFilter(strTreatment, strCategory, strFacility, strDays, strPriceMin, strPriceMax, strRateMin, strRateMax, lat, lng);
    }


    private void searchDataByFilter(String strTreatment, String strCategory, String strFacility, String strDays, String strPriceMin, String strPriceMax, String strRateMin, String strRateMax, double lat, double lng){
        Call<StoreResponse> requestCall;
        Log.d(TAG, "show filter result "+strTreatment+ " | "+ strCategory+ " | "+strFacility+ " | "+ strDays+ " | "+strPriceMin+ " | "+strPriceMax+ " | "+strRateMin+ " | "+strRateMax);
        requestCall = api.searchStoreFiltered(strTreatment, strCategory, strFacility, strDays, strPriceMin, strPriceMax, strRateMin, strRateMax, lat, lng);
        requestCall.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                if (response.isSuccessful()) {
                    final StoreResponse storeResponse = response.body();
                    if (storeResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        searchingResult.generateStoreResponses((ArrayList<Store>) response.body().getStore());
                    }else if (storeResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        searchingResult.warningDataNotFound();
                    }else if (storeResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        searchingResult.loginTimeOut();
                    }
                }
                else {
  //                  final StoreResponse storeResponse = response.body();
//                    Toast.makeText(context, storeResponse.getStatus().toString(), Toast.LENGTH_SHORT).show();
                   // Log.d(TAG, "show filter result "+strIdUser+strTreatment+ strCategory+ strFacility+ strDays+ strPrice+ strRateMin+ strRateMax);
                    Log.d(TAG, "RESPON CODE " + response.code());
                    Log.d(TAG, "RESPON CODE " + response.message().toString());
                  //  Toast.makeText(TAG, "respon "+ response.code(), Toast.LENGTH_SHORT).show();
                    searchingResult.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                searchingResult.ErrorConnectionFailed();
            }
        });
    }

//    ================================================= SHOW HEADER ====================================================
    @Override
    public void showOutletHeader(String strIdStore) {
        getOutletHeader(strIdStore);
    }

    private void getOutletHeader(String strIdStore){
        Call<PhotoResponse> requestCall;
        Log.d(TAG, "fetching header outlet "+strIdStore);
        requestCall = api.getOutletPhotoHeader(strIdStore);
        requestCall.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
                if (response.isSuccessful()) {
                    final PhotoResponse photoResponse = response.body();
                    if (photoResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        item = response.body().getPhoto().get(0);
                        showDetailStore.fetchPhotoHeader(item);
                    }
                    else if(photoResponse.getStatus().toString().equalsIgnoreCase("0")){
                        showDetailStore.warningDataNotFound();
                    }
                }
                else{
                    showDetailStore.ErrorResponseFailed();
                }
                // showDetailStore.stopAnim();

            }

            //fail to connect to API
            @Override
            public void onFailure(Call<PhotoResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                showDetailStore.ErrorConnectionFailed();
            }
        });

    }

//    =================================================== map ==========================================================


    @Override
    public void showListOutletInMap(String strIdUser, double lat, double lng) {
        fetchStoreInMap(strIdUser, lat, lng);
    }

    private void fetchStoreInMap(String strIdUser, double lat, double lng){
        Call<StoreResponse> requestCall;
        Log.d(TAG, "fetching list outlet ");
        requestCall = api.showListAllOutletVerified(strIdUser, lat, lng);
        requestCall.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                if (response.isSuccessful()) {
                    final StoreResponse storeResponse = response.body();
                    if (storeResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        storeInMap.generateStoreResponse((ArrayList<Store>) response.body().getStore());
                    }
                    else if (storeResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        storeInMap.warningDataNotFound();
                    }
                    else if (storeResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        storeInMap.loginTimeOut();
                    }
                }
                else {
                    storeInMap.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                storeInMap.ErrorConnection();
            }
        });

    }
}
