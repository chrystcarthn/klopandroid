package appdeveloper.klop.klop.PresenterImp;

import android.util.Log;

import java.util.ArrayList;

import appdeveloper.klop.klop.API.InterfaceAPI;
import appdeveloper.klop.klop.API.RestClient;
import appdeveloper.klop.klop.Activity.FilteredSearching;
import appdeveloper.klop.klop.Fragment.HomeFragment;
import appdeveloper.klop.klop.Model.Store;
import appdeveloper.klop.klop.Model.StoreResponse;
import appdeveloper.klop.klop.Presenter.FilteredSearchingPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CMDDJ on 5/5/2018.
 */

public class FilteredSearchingPresenterImp implements FilteredSearchingPresenter {

    private static final String TAG = "Filter";
    InterfaceAPI api;
    FilteredSearching filteredSearching;
    HomeFragment homeFragment;

    public FilteredSearchingPresenterImp(FilteredSearching filteredSearching) {
        this.filteredSearching = filteredSearching;
        api = RestClient.createService(InterfaceAPI.class);
    }

    public FilteredSearchingPresenterImp(HomeFragment homeFragment) {
        this.homeFragment = homeFragment;
        api = RestClient.createService(InterfaceAPI.class);

    }

    @Override
    public void searchByFilter(String strIdUser, String strTreatment, String strCategory, String strFacility, String strDays, String strPrice, String strRateMin, String strRateMax) {

    }


}
