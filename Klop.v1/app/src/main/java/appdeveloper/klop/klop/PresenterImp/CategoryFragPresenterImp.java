package appdeveloper.klop.klop.PresenterImp;

import android.util.Log;

import java.util.ArrayList;

import appdeveloper.klop.klop.API.InterfaceAPI;
import appdeveloper.klop.klop.API.RestClient;
import appdeveloper.klop.klop.Activity.FilteredSearching;
import appdeveloper.klop.klop.Activity.ManageCategory;
import appdeveloper.klop.klop.Fragment.CategoryFragment;
import appdeveloper.klop.klop.Model.CategoryDb;
import appdeveloper.klop.klop.Model.CategoryDbResponse;
import appdeveloper.klop.klop.Model.StoreHasCategory;
import appdeveloper.klop.klop.Model.StoreHasCategoryResponse;
import appdeveloper.klop.klop.Presenter.CategoryFragPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CMDDJ on 4/26/2018.
 */

public class CategoryFragPresenterImp implements CategoryFragPresenter {

    private static final String TAG = "AddCat";
    InterfaceAPI api;
    CategoryFragment categoryFragment;
    CategoryFragPresenter categoryFragPresenter;
    ManageCategory manageCategory;
    String strIdOutlet;
    FilteredSearching filteredSearching;

    public CategoryFragPresenterImp(CategoryFragment categoryFragment) {
        this.categoryFragment = categoryFragment;
        api = RestClient.createService(InterfaceAPI.class);

    }

    public CategoryFragPresenterImp(FilteredSearching filteredSearching) {
        this.filteredSearching = filteredSearching;
        api = RestClient.createService(InterfaceAPI.class);

    }


    public CategoryFragPresenterImp(ManageCategory manageCategory) {
        this.manageCategory = manageCategory;
        api = RestClient.createService(InterfaceAPI.class);

    }


    //============================================ SHOW CATEGORY IN MULTI CHOICE SPINNER =============================================

    @Override
    public void showAllCategory() {
        showCategory();
    }

    @Override
    public void showAllCategory2() {
        showCategory2();
    }


    private void showCategory(){

        Call<CategoryDbResponse> requestCall;
        Log.d(TAG, "fetching info outlet ");
        requestCall = api.showCategoryInDb();
        requestCall.enqueue(new Callback<CategoryDbResponse>() {
            @Override
            public void onResponse(Call<CategoryDbResponse> call, Response<CategoryDbResponse> response) {
                categoryFragment.generateCategoryDbResponse((ArrayList<CategoryDb>) response.body().getCategoryDb());
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<CategoryDbResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                categoryFragment.ErrorConnectionFailed();
            }
        });


    }

    private void showCategory2(){
        Call<CategoryDbResponse> requestCall;
        Log.d(TAG, "fetching info outlet ");
        requestCall = api.showCategoryInDb();
        requestCall.enqueue(new Callback<CategoryDbResponse>() {
            @Override
            public void onResponse(Call<CategoryDbResponse> call, Response<CategoryDbResponse> response) {
                filteredSearching.generateCategoryDbResponse((ArrayList<CategoryDb>) response.body().getCategoryDb());
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<CategoryDbResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                filteredSearching.ErrorConnectionFailed();
            }
        });
    }


}
