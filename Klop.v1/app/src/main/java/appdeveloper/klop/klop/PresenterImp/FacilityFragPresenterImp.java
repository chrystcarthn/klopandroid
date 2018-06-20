package appdeveloper.klop.klop.PresenterImp;

import android.util.Log;

import java.util.ArrayList;

import appdeveloper.klop.klop.API.InterfaceAPI;
import appdeveloper.klop.klop.API.RestClient;
import appdeveloper.klop.klop.Activity.FilteredSearching;
import appdeveloper.klop.klop.Fragment.FacilityFragment;
import appdeveloper.klop.klop.Model.FacilityDb;
import appdeveloper.klop.klop.Model.FacilityDbResponse;
import appdeveloper.klop.klop.Presenter.FacilityFragPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CMDDJ on 4/27/2018.
 */

public class FacilityFragPresenterImp implements FacilityFragPresenter{

    private static final String TAG = "AddCat";
    InterfaceAPI api;
    FacilityFragment facilityFragment;
    FacilityFragPresenter FacilityFragPresenter;
    String strIdOutlet;
    FilteredSearching filteredSearching;

    public FacilityFragPresenterImp(FacilityFragment facilityFragment) {
        this.facilityFragment = facilityFragment;
        api = RestClient.createService(InterfaceAPI.class);
    }

    public FacilityFragPresenterImp(FilteredSearching filteredSearching) {
        this.filteredSearching = filteredSearching;
        api = RestClient.createService(InterfaceAPI.class);
    }

    @Override
    public void showAllFacility() {
        showFacility();
    }

    @Override
    public void showAllFacility2() {
        showFacility2();
    }

    public void showFacility(){
        Call<FacilityDbResponse> requestCall;
        Log.d(TAG, "fetching info outlet ");
        requestCall = api.showFacilityInDb();
        requestCall.enqueue(new Callback<FacilityDbResponse>() {
            @Override
            public void onResponse(Call<FacilityDbResponse> call, Response<FacilityDbResponse> response) {
                if (response.isSuccessful()) {
                    final FacilityDbResponse facilityDbResponse = response.body();
                    if (facilityDbResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        facilityFragment.generateFacilityDbResponse((ArrayList<FacilityDb>) response.body().getFacilityDb());
                        Log.e(TAG, "showFacility TESTING"+response.body().getFacilityDb().get(0).getIDFACILITYDB());
                    }else facilityFragment.errorStatus();
                } else facilityFragment.ErrorResponse();
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<FacilityDbResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                try {
                    facilityFragment.ErrorConnectionFailed();
                }catch (Exception e){e.printStackTrace();}
            }
        });
    }

    public void showFacility2(){
        Call<FacilityDbResponse> requestCall;
        Log.d(TAG, "fetching info outlet ");
        requestCall = api.showFacilityInDb();
        requestCall.enqueue(new Callback<FacilityDbResponse>() {
            @Override
            public void onResponse(Call<FacilityDbResponse> call, Response<FacilityDbResponse> response) {
                if (response.isSuccessful()) {
                    final FacilityDbResponse facilityDbResponse = response.body();
                    if (facilityDbResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        filteredSearching.generateFacilityDbResponse((ArrayList<FacilityDb>) response.body().getFacilityDb());
                        Log.e(TAG, "showFacility TESTING"+response.body().getFacilityDb().get(0).getIDFACILITYDB());
                    }else filteredSearching.errorStatus();
                } else filteredSearching.ErrorResponseFailed();
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<FacilityDbResponse> call, Throwable t) {

                try {
                    filteredSearching.ErrorConnectionFailed();
                }catch (Exception e){e.printStackTrace();}
            }
        });
    }
}
