package appdeveloper.klop.klop.PresenterImp;

import android.util.Log;

import java.util.ArrayList;

import appdeveloper.klop.klop.API.InterfaceAPI;
import appdeveloper.klop.klop.API.RestClient;
import appdeveloper.klop.klop.Activity.ManageCategory;
import appdeveloper.klop.klop.Adapter.MngCategoryAdapter;
import appdeveloper.klop.klop.Fragment.AboutTabFragment;
import appdeveloper.klop.klop.Model.BookingResponse;
import appdeveloper.klop.klop.Model.Category;
import appdeveloper.klop.klop.Model.CategoryResponse;
import appdeveloper.klop.klop.Model.Facility;
import appdeveloper.klop.klop.Model.FacilityResponse;
import appdeveloper.klop.klop.Model.Schedule;
import appdeveloper.klop.klop.Model.ScheduleResponse;
import appdeveloper.klop.klop.Model.Store;
import appdeveloper.klop.klop.Model.StoreResponse;
import appdeveloper.klop.klop.Presenter.AboutTabFragPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CMDDJ on 4/20/2018.
 */

public class AboutTabFragPresenterImp implements AboutTabFragPresenter {

    private static final String TAG = "AboutTag";
    InterfaceAPI api;
    AboutTabFragment aboutTabFragment;
    ManageCategory manageCategory;
    String strIdOutlet;
    Store item;
    Category item2;
    Facility item3;
    MngCategoryAdapter mngCategoryAdapter;

    public AboutTabFragPresenterImp(AboutTabFragment aboutTabFragment) {
        this.aboutTabFragment = aboutTabFragment;
        api = RestClient.createService(InterfaceAPI.class);

    }

    public AboutTabFragPresenterImp(MngCategoryAdapter mngCategoryAdapter) {
        this.mngCategoryAdapter = mngCategoryAdapter;
        api = RestClient.createService(InterfaceAPI.class);

    }


    @Override
    public void showDetailOutlet(String strIdOutlet, double lat, double lng) {

        getFetchingInfoOutlet(strIdOutlet, lat, lng);
    }

    @Override
    public void showCategory(String strIdOutlet) {
        getFetchingCategoryByStore(strIdOutlet);
    }

    @Override
    public void showSchedule(String strIdOutlet) {
        getFetchingScheduleByStore(strIdOutlet);
    }



    @Override
    public void showFacility(String strIdOutlet) {
        getFetchingFacilityByStore(strIdOutlet);
    }

    private void getFetchingInfoOutlet(String strIdOutlet, double lat, double lng){
        Call<StoreResponse> requestCall;
        Log.d(TAG, "fetching info outlet "+strIdOutlet);
        requestCall = api.showInfoOutlet(strIdOutlet, lat, lng);
        requestCall.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                item = response.body().getStore().get(0);
                aboutTabFragment.generateStoreResponse(item);

            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                aboutTabFragment.ErrorConnectionFailed();
            }
        });

    }

    private void getFetchingCategoryByStore(String strIdOutlet){
        Call<CategoryResponse> requestCall;
        Log.d(TAG, "fetching info outlet "+strIdOutlet);
        requestCall = api.showCategoryByStore(strIdOutlet);
        requestCall.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                final CategoryResponse categoryResponse = response.body();
                if (categoryResponse.getStatus().toString().equalsIgnoreCase("1")) {
                    aboutTabFragment.generateCategoryResponse((ArrayList<Category>) response.body().getCategory());
                } else aboutTabFragment.warningDataNotFound();
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                aboutTabFragment.ErrorConnectionFailed();
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
                aboutTabFragment.generateScheduleResponse((ArrayList<Schedule>) response.body().getSchedule());
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                aboutTabFragment.ErrorConnectionFailed();
            }
        });


    }

    private void getFetchingFacilityByStore(String strIdOutlet){
        Call<FacilityResponse> requestCall;
        Log.d(TAG, "fetching info outlet "+strIdOutlet);
        requestCall = api.showFacilityByStore(strIdOutlet);
        requestCall.enqueue(new Callback<FacilityResponse>() {
            @Override
            public void onResponse(Call<FacilityResponse> call, Response<FacilityResponse> response) {
                aboutTabFragment.generateFacilityResponse((ArrayList<Facility>) response.body().getFacility());
                // item2 = response.body().getCategory().get(0);

                // aboutTabFragment.generateCategoryResponse(item2);
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<FacilityResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                aboutTabFragment.ErrorConnectionFailed();
            }
        });


    }



}
