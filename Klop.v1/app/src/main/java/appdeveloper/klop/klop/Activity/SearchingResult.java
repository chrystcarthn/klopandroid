package appdeveloper.klop.klop.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import appdeveloper.klop.klop.Adapter.FilterAdapter;
import appdeveloper.klop.klop.Adapter.StoreAdapter;
import appdeveloper.klop.klop.Model.Store;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.HomePresenterImp;
import appdeveloper.klop.klop.R;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by CMDDJ on 5/10/2018.
 */

public class SearchingResult  extends AppCompatActivity {

    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdUser, strTreatment, strCategory, strFacility, strDays, strPriceMin, strPriceMax, strRateMin, strRateMax, strStatus;

    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    TextView emptyTextView;
    private StoreAdapter adapter;
    private RecyclerView recyclerView;
    HomePresenterImp homePresenterImp;
    ArrayList<Store> clone = new ArrayList<Store>();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_result);

        session = new SessionPreference(this);
        userdata = session.getUserDetails();
       // strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        homePresenterImp = new HomePresenterImp(this);

        Bundle b = getIntent().getExtras();
        strStatus = b.getString("dariFilter");
        strIdUser = b.getString("idUser");
        strTreatment = b.getString("strTreatment");
        strCategory = b.getString("strCategory");
        strFacility = b.getString("strFacility");
        strDays = b.getString("strDays");
        strPriceMin = b.getString("strPriceMin");
        strPriceMax = b.getString("strPriceMax");
        strRateMin = b.getString("strRateMin");
        strRateMax = b.getString("strRateMax");

//            Toast.makeText(Main.this, "MAIN : "+strIdUser+" | "+strTreatment+ " | "+ strCategory+ " | "+strFacility+ " | "+ strDays+ " | "+strPrice+ " | "+strRateMin+ " | "+strRateMax, Toast.LENGTH_SHORT).show();
//            Toast.makeText(Main.this, "MAIN : "+strIdUser+" | "+strTreatment+ " | "+ strCategory+ " | "+strFacility+ " | "+ strDays+ " | "+strPrice+ " | "+strRateMin+ " | "+strRateMax, Toast.LENGTH_SHORT).show();

        emptyTextView = (TextView) findViewById(R.id.tvEmptyFilter);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewOutletbyId);

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setWaveColor(getResources().getColor(R.color.yellow));
        mWaveSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.white));
        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                emptyTextView.setVisibility(View.GONE);
                loadFilteringResult(strTreatment, strCategory, strFacility, strDays, strPriceMin, strPriceMax, strRateMin, strRateMax);
            }
        });

        loadFilteringResult(strTreatment, strCategory, strFacility, strDays, strPriceMin, strPriceMax, strRateMin, strRateMax);
//        Toast.makeText(this, strIdUser+"|"+strTreatment+"|"+strCategory+"|"+strFacility+"|"+strDays+"|"+strPriceMin+"|"+strPriceMax+"|"+strRateMin+"|"+strRateMax, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, strIdUser+"|"+strTreatment+"|"+strCategory+"|"+strFacility+"|"+strDays+"|"+strPriceMin+"|"+strPriceMax+"|"+strRateMin+"|"+strRateMax, Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, strIdUser+"|"+strTreatment+"|"+strCategory+"|"+strFacility+"|"+strDays+"|"+strPriceMin+"|"+strPriceMax+"|"+strRateMin+"|"+strRateMax, Toast.LENGTH_SHORT).show();

    }

    private void loadFilteringResult(String strTreatments, String strCategorys, String strFacilitys, String strDayss, String strPricesMin, String strPricesMax, String strRateMins, String strRateMaxs) {
        mWaveSwipeRefreshLayout.setRefreshing(true);
        homePresenterImp.searchByFilter(strTreatments, strCategorys, strFacilitys, strDayss, strPricesMin, strPricesMax, strRateMins, strRateMaxs, -7.7746817, 110.4159096);

    }

    public void loginTimeOut() {
        Toast.makeText(SearchingResult.this, "Login time out!", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }

    public void warningDataNotFound() {
       // Toast.makeText(SearchingResult.this, "Oops, data tidak ditemukan!", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
        emptyTextView.setVisibility(View.VISIBLE);
    }

    public void ErrorResponse() {
        Toast.makeText(SearchingResult.this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
        emptyTextView.setText("Coba beberapa saat lagi");
        emptyTextView.setVisibility(View.VISIBLE);
    }

    public void ErrorConnectionFailed(){
        Toast.makeText(SearchingResult.this, "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
    }

    public void generateStoreResponses(ArrayList<Store> storeArrayList){
        if(storeArrayList != null){

            emptyTextView.setVisibility(View.GONE);
            adapter = new StoreAdapter(storeArrayList, SearchingResult.this);
            final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(SearchingResult.this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

            mWaveSwipeRefreshLayout.setRefreshing(false);
        }else{
            mWaveSwipeRefreshLayout.setRefreshing(false);
            emptyTextView.setVisibility(View.VISIBLE);
        }
    }

}