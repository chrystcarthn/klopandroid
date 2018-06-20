package appdeveloper.klop.klop.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import appdeveloper.klop.klop.Adapter.StoreAdapter;
import appdeveloper.klop.klop.Adapter.StoreManageAdapter;
import appdeveloper.klop.klop.Model.Store;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.StorePresenterImp;
import appdeveloper.klop.klop.R;
import dmax.dialog.SpotsDialog;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class MyStore extends AppCompatActivity implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{

    private StorePresenterImp showListOutletPresenterImp;
    //    ArrayList<Store> listOutlet;
    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdUser;
//    private List<Store> storeList;
//    private ListView listKontenView;

    private StoreManageAdapter adapter;
    private RecyclerView recyclerView;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    TextView emptyTextView;
    private FloatingActionButton fab;

    Toolbar toolbar;
    String owner="0";

    double lat, lng;
    static final int REQUEST_LOCATION =1;
    LocationManager locationManager;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store);

        session = new SessionPreference(this);
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        getLocation();

//        Bundle b = getIntent().getExtras();
//        owner = b.getString("owner");

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


        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe2);
        mWaveSwipeRefreshLayout.setWaveColor(getResources().getColor(R.color.colorPrimary));
        mWaveSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.white));
        emptyTextView = (TextView) findViewById(R.id.tvEmptyStr);
        showListOutletPresenterImp = new StorePresenterImp(this);
        fab = (FloatingActionButton)findViewById(R.id.fabAddStore);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewOutletbyId);
        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                emptyTextView.setVisibility(View.GONE);
                loadRecyclerViewDataOutlet();
            }
        });

       // Toast.makeText(this, "coordinates "+lat+", "+lng, Toast.LENGTH_SHORT).show();
        loadRecyclerViewDataOutlet();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyStore.this, AddStore.class);
                startActivity(intent);

            }
        });

    }

    void getLocation(){
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this,
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this,
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if(location != null){
                    double latti = location.getLatitude();
                    double longi = location.getLongitude();

                   // Toast.makeText(this, "coordinates "+latti+", "+longi, Toast.LENGTH_SHORT).show();

                    lat = latti;
                    lng = longi;
                } else
                    Toast.makeText(this, "Gagal mendapatkan kordinat lokasi terbaru", Toast.LENGTH_SHORT).show();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        loadRecyclerViewDataOutlet();
    }

    public void noOutletRegistered(){
      //  Toast.makeText(this, "Oops, data tidak ditemukan!", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
        recyclerView.setVisibility(View.GONE);
        emptyTextView.setVisibility(View.VISIBLE);
    }

    public void loginTimeOut(){
        Toast.makeText(this, "Login time out!", Toast.LENGTH_SHORT).show();
    }

    public void generateStoreResponse(ArrayList<Store> storeArrayList){

        if(storeArrayList != null){
            emptyTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new StoreManageAdapter(storeArrayList, this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            mWaveSwipeRefreshLayout.setRefreshing(false);
        }else{
            mWaveSwipeRefreshLayout.setRefreshing(false);
            emptyTextView.setVisibility(View.VISIBLE);
        }



    }




    private void loadRecyclerViewDataOutlet()
    {
        mWaveSwipeRefreshLayout.setRefreshing(true);
        showListOutletPresenterImp.showListOutletByIdUser(strIdUser, lat, lng);
    }

//    public void showListOutletSuccess(){
//        Toast.makeText(this, "List Outlet", Toast.LENGTH_SHORT).show();
//
//
//    }

    public void getDetailStore(List<Store> listStore){
        for(int i=0 ; i<=listStore.size() ; i++){
            listStore.get(i).getNameStore().toString();
            listStore.get(i).getAddressStore().toString();
        }


    }

//    public void showEmptyListOutlet() {
//        Toast.makeText(this, "Tidak ada outlet yang terdaftar", Toast.LENGTH_SHORT).show();
//    }


    public void ErrorResponseFailed() {
        Toast.makeText(this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
    }

    public void ErrorResponse() {
        Toast.makeText(this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
    }


    public void ErrorConnection() {
        Toast.makeText(this, "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
    }


    public void ErrorConnectionFailed() {
        Toast.makeText(this, "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
        emptyTextView.setVisibility(View.VISIBLE);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(MyStore.this);
        searchView.setQueryHint("Ketik nama outlet...");

        super.onCreateOptionsMenu(menu);
        return  true;

    }
//    =============================================================== search view ===========================================================


    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
//            mRefreshLayout.finishRefreshing();
            mWaveSwipeRefreshLayout.setRefreshing(false);
            // emptyTextView.setVisibility(View.VISIBLE);
            return false;
        }
        emptyTextView.setVisibility(View.GONE);
        doSearchUserStoreByKeyword(newText);

        return false;
    }

    public void resetSearch() {

        emptyTextView.setVisibility(View.GONE);
        loadRecyclerViewDataOutlet();
    }

    public void doSearchUserStoreByKeyword(String strKeyword){
        SpotsDialog alertDialog = new SpotsDialog(this);
        alertDialog.show();

        mWaveSwipeRefreshLayout.setRefreshing(true);
        showListOutletPresenterImp.searchListUserOutletByKeyword(strIdUser, strKeyword, lat, lng);
        mWaveSwipeRefreshLayout.setRefreshing(false);
        alertDialog.hide();
    }

}
