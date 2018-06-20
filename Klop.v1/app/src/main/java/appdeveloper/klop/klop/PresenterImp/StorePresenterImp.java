package appdeveloper.klop.klop.PresenterImp;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import appdeveloper.klop.klop.API.InterfaceAPI;
import appdeveloper.klop.klop.API.RestClient;
import appdeveloper.klop.klop.Activity.AddStore;
import appdeveloper.klop.klop.Activity.AddStoreDetailInfo;
import appdeveloper.klop.klop.Activity.MyStore;
import appdeveloper.klop.klop.Activity.ShowDetailStore;
import appdeveloper.klop.klop.Adapter.StoreAdapter;
import appdeveloper.klop.klop.Fragment.AboutTabFragment;
import appdeveloper.klop.klop.Fragment.StoreFragment;
import appdeveloper.klop.klop.Model.BookingResponse;
import appdeveloper.klop.klop.Model.Store;
import appdeveloper.klop.klop.Model.StoreResponse;
import appdeveloper.klop.klop.Model.UserResponse;
import appdeveloper.klop.klop.Presenter.StorePresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CMDDJ on 4/16/2018.
 */

public class StorePresenterImp implements StorePresenter {

    private StoreAdapter storeAdapter;
    StoreFragment storeFragment;
    MyStore myStore;
    AddStore addStore;
    ShowDetailStore showDetailStore;
    AddStoreDetailInfo addStoreDetailInfo;
    Store item, item2;
    AboutTabFragment aboutTabFragment;
    private List<Store> storeList;
   // private List<Store> storeItems = new ArrayList<Store>();

  //  ArrayList<Store> storeItems;
    InterfaceAPI api;
    private static final String TAG = "ShowListOutlet";
    public static final String ID_USER = "1";



    public StorePresenterImp(AddStore addStore) {
        this.addStore = addStore;
        api = RestClient.createService(InterfaceAPI.class);
    }

    public StorePresenterImp(AddStoreDetailInfo addStoreDetailInfo) {
        this.addStoreDetailInfo = addStoreDetailInfo;
        api = RestClient.createService(InterfaceAPI.class);
    }


    public StorePresenterImp(StoreFragment storeFragment) {
        this.storeFragment = storeFragment;
        api = RestClient.createService(InterfaceAPI.class);

    }

    public StorePresenterImp(MyStore myStore) {
        this.myStore = myStore;
        api = RestClient.createService(InterfaceAPI.class);

    }

    public StorePresenterImp(ShowDetailStore showDetailStore) {
        this.showDetailStore = showDetailStore;
        api = RestClient.createService(InterfaceAPI.class);

    }


    //=========================================== DAFTARKAN OUTLET =================================================
    @Override
    public void valInformasiUmum(String strIdUser, String strNamaOutlet, String strAddress, String strPhone, String strLat, String strLong, String strDistance) {
        if (!isValidName(strNamaOutlet)) {
            addStore.showInvalidName();
        }
        else if (!isValidPhone(strPhone)) {
            addStore.showInvalidPhone();
        }
        else if (!isValidAddress(strAddress)) {
            addStore.showInvalidAddress();
        }
        else if(!isValidKoordinat(strLat, strLong, strDistance)){
            addStore.showInvalidLatLong();
        }
        else if(!isValidName(strNamaOutlet) & !isValidPhone(strPhone) & !isValidAddress(strAddress) & !isValidKoordinat(strLat, strLong, strDistance))
        {
            addStore.showInvalidName();
        }
        else if(isValidName(strNamaOutlet) & isValidPhone(strPhone) & isValidAddress(strAddress) & isValidKoordinat(strLat, strLong, strDistance))
        {
            addStore.removeError();
            addStore.sukses();
          //  sendPostInformasiUmum(strIdUser, strNamaOutlet, strAddress, strPhone, strLat, strLong, strDistance);

        }
    }

    @Override
    public void addInformasiUmum(String strIdUser, String strNamaOutlet, String strAlamat, String strPhone, String strLat, String strLong, String strDistance) {
        sendPostInformasiUmum(strIdUser, strNamaOutlet, strAlamat, strPhone, strLat, strLong, strDistance);
    }

    private void sendPostInformasiUmum(String strIdUser, String strNameOutlet, String strAddress, String strPhone, String strLatitude, String strLongitude, String strDistance) {
        addStoreDetailInfo.showSpotLoading();
        Call<StoreResponse> requestCall = null;
        Log.d(TAG, "adding outlet "+strIdUser+strNameOutlet+strAddress+strPhone+"|"+strLatitude+"|"+strLongitude+"|"+strDistance);
        requestCall = api.addstore(strIdUser, strNameOutlet, strAddress, strPhone, strLatitude, strLongitude, strDistance);
        requestCall.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final StoreResponse storeResponse = response.body();
                    if(storeResponse.getStatus().toString().equalsIgnoreCase("1")) {

                        addStoreDetailInfo.AddingOutletSuccess();
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        addStoreDetailInfo.dismissSpotLoading();
                        addStoreDetailInfo.ErrorAddingOutletFailed();
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


    // validating outlet name
    private boolean isValidName(String name) {
        if (!name.equals("") & name.length() >= 1) {
            return true;
        }
        return false;
    }

    // validating outlet phone
    private boolean isValidPhone(String phone) {
        if (!phone.equals("") & phone.length() >= 9) {
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
    private boolean isValidKoordinat(String strLat, String strLong, String strDistance) {
        if (strLat != null & strLong != null & strDistance != null) {
            return true;
        }
        return false;
    }

    //=============================================== TAMPIL OUTLET BY USER ================================================


    @Override
    public void showListOutletByIdUser(String strIdUser, double strLat, double strLong) {
        fetchingListOutletbyIdUser(strIdUser, strLat, strLong);
    }


    private void fetchingListOutletbyIdUser(String strIdUser, double strLat, double strLong){
        Call<StoreResponse> requestCall;
        Log.d(TAG, "fetching list outlet by KORDINAT "+strIdUser+" kordinat "+strLat+", "+strLong);
        requestCall = api.showListOutletbyIdUser(strIdUser, strLat, strLong);
        requestCall.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                if (response.isSuccessful()) {
                    final StoreResponse storeResponse = response.body();
                    if (storeResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        myStore.generateStoreResponse((ArrayList<Store>) response.body().getStore());
                    }
                    else if (storeResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        myStore.noOutletRegistered();
                    }
                    else if (storeResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        myStore.loginTimeOut();
                    }
                 }
                 else{ myStore.ErrorResponse();}
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                myStore.ErrorConnection();
            }
        });

    }

    // ================================================ TAMPIL DETAIL OUTLET =============================================

    @Override
    public void showInfoOutlet(String strIdOutlet, double lat, double lng) {
        getFetchingInfoOutlet(strIdOutlet, lat, lng);
    }

    private void getFetchingInfoOutlet(String strIdOutlet, double lat, double lng){

        Call<StoreResponse> requestCall;
        Log.d(TAG, "fetching info outlet "+strIdOutlet);
        requestCall = api.showInfoOutlet(strIdOutlet, lat, lng);
        requestCall.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                item = response.body().getStore().get(0);
                showDetailStore.fetchInfoStore(item);
               // showDetailStore.stopAnim();

            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                try{storeFragment.ErrorConnectionFailed();}catch (Exception e){e.printStackTrace();}
            }
        });

    }


    // ================================================ 1 OUTLET TRAKHIR CREATED BY USER =============================================

    @Override
    public void getLatestStoreCreatedByUser(String strIdUser) {
        getLatestIdStoreCreated(strIdUser);
    }


    private void getLatestIdStoreCreated(String strIdUser){
        Call<StoreResponse> requestCall;
        Log.d(TAG, "fetching last outlet "+strIdUser);
        requestCall = api.getLatestStore(strIdUser);
        requestCall.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                if(response.isSuccessful()) {
                    final StoreResponse storeResponse = response.body();
                    if(storeResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        Log.d(TAG, "ID STORE "+String.valueOf(storeResponse.getStatus().toString()));
                        item2 = response.body().getStore().get(0);
                        Log.d(TAG, "ID STORE "+String.valueOf(storeResponse.getStore().get(0).getIdStore()));

                        addStoreDetailInfo.getLatestIdStore(item2);
                    }
                    else{
                        Log.d(TAG, "Tidak bisa mengambil JSON");
                        Log.d(TAG, "ID STORE "+String.valueOf(response.body().getStore().get(0).getIdStore()));
                        Log.d(TAG, "ID STORE "+String.valueOf(storeResponse.getStatus().toString()));

                        addStoreDetailInfo.ErrorResponseFailed();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
                    addStoreDetailInfo.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                addStoreDetailInfo.ErrorConnectionFailed();
            }
        });

    }

//    ======================================== SEARCH BY KEY ======================================

    @Override
    public void searchListUserOutletByKeyword(String strIdUser, String strKeyword, double lat, double lng) {
        searchStoreByKey(strIdUser, strKeyword, lat, lng);
    }

    @Override
    public void getCountBook(String strIdUser) {
        countBook(strIdUser);
    }

    private void countBook(String strIdUser){
        Call<BookingResponse> requestCall;
        Log.d(TAG, "fetching list outlet "+strIdUser);
        requestCall = api.getCountBook(strIdUser);
        requestCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                if (response.isSuccessful()) {
                    final BookingResponse storeResponse = response.body();
                    if (storeResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        showDetailStore.continueBook();
                    }else if (storeResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        showDetailStore.bookReachLimit();
                    }
                }
                else {
                    showDetailStore.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                showDetailStore.ErrorConnectionFailed();
            }
        });

    }

    private void searchStoreByKey(String strIdUser, String strKeyword, double lat, double lng){
        Call<StoreResponse> requestCall;
        Log.d(TAG, "fetching list outlet "+strIdUser+strKeyword);
        requestCall = api.searchUserStoreByKeyword(strIdUser, strKeyword, lat, lng);
        requestCall.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                if (response.isSuccessful()) {
                    final StoreResponse storeResponse = response.body();
                    if (storeResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        myStore.generateStoreResponse((ArrayList<Store>) response.body().getStore());
                    }else if (storeResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        myStore.noOutletRegistered();
                    }else if (storeResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        myStore.loginTimeOut();
                    }
                }
                else {
                    myStore.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                myStore.ErrorConnection();
            }
        });

    }



}
