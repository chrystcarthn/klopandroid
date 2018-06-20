package appdeveloper.klop.klop.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import appdeveloper.klop.klop.Adapter.MngFacilityAdapter;
import appdeveloper.klop.klop.Model.FacSetting;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.StoreSettingsPresenterImp;
import appdeveloper.klop.klop.R;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class ManageFacility extends AppCompatActivity {

    String strIdStore;
    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdUser;
    RecyclerView recyclerView;
    ArrayList<String> facSelected;
    ArrayList<String> facRemoved;
    ArrayList<String> facOwned;
    ArrayList<String> backUp;
    Toolbar toolbar;
    Button btnSimpan;
    String formattedIdArraylist, formattedIdArraylist2;
    StoreSettingsPresenterImp storeSettingsPresenterImp;
    MngFacilityAdapter adapter;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_facility);


        Bundle b = getIntent().getExtras();
        strIdStore = b.getString("idStore");

        session = new SessionPreference(this);
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        facSelected = new ArrayList<String>();
        facRemoved = new ArrayList<String>();
        facOwned = new ArrayList<String>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewFac);
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

        btnSimpan = (Button) findViewById(R.id.btnSimpan);

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe2);
        mWaveSwipeRefreshLayout.setWaveColor(getResources().getColor(R.color.colorPrimary));
        mWaveSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.white));
        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                mWaveSwipeRefreshLayout.setRefreshing(false);
            }
        });
        //categoryFragPresenterImp = new CategoryFragPresenterImp(this);
        storeSettingsPresenterImp = new StoreSettingsPresenterImp(this);


        loadRecyclerViewFac();
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(adapter.facilitySelected.size() > 0){
                    for(int i = 0; i < adapter.facilitySelected.size(); i++){
                        if(facOwned.contains(adapter.facilitySelected.get(i))){
                            adapter.facilitySelected.remove(adapter.facilitySelected.get(i));
                        } else{
                            facOwned.add(adapter.facilitySelected.get(i));
                        }
                    }
                    formattedIdArraylist = adapter.facilitySelected.toString()
                            .replace("[","")
                            .replace("]","")
                            .replace(" ","")
                            .trim();
                    storeSettingsPresenterImp.addFacility(strIdStore, formattedIdArraylist);
                }
                else {
                  //  Toast.makeText(ManageFacility.this, "facilitySelected "+adapter.facilitySelected.size(), Toast.LENGTH_SHORT).show();
                }

              //  Toast.makeText(ManageFacility.this, adapter.deleteFacility.toString(), Toast.LENGTH_SHORT).show();

                if(adapter.deleteFacility.size() > 0) {
                    for(int i = 0; i < adapter.deleteFacility.size(); i++){
                        if(facOwned.contains(adapter.deleteFacility.get(i))){
                            facOwned.remove(adapter.deleteFacility.get(i));
                            facRemoved.add(adapter.deleteFacility.get(i));
                        }
                    }

                    if(facOwned.size() == 0){
                        storeSettingsPresenterImp.showFacSetting(strIdStore);
                        Toast.makeText(ManageFacility.this, "Harap memilih minimal 1 fasilitas", Toast.LENGTH_SHORT).show();
                        facRemoved.clear();

                    } else {
                        formattedIdArraylist2 = facRemoved.toString()
                                .replace("[","")
                                .replace("]","")
                                .replace(" ","")
                                .trim();
                        storeSettingsPresenterImp.deleteFacSetting(strIdStore, formattedIdArraylist2);
                    }
                }
                else
                {
              //      Toast.makeText(ManageFacility.this, "deleteFacility "+adapter.deleteFacility.size(), Toast.LENGTH_SHORT).show();
                }
              //  Toast.makeText(ManageFacility.this, "owned "+facOwned.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadRecyclerViewFac()
    {
        mWaveSwipeRefreshLayout.setRefreshing(true);
        storeSettingsPresenterImp.showFacSetting(strIdStore);
//        categoryFragPresenterImp.showAllCategory2();
    }

    public void generateFacSettingResponse(ArrayList<FacSetting> facSettingArrayList){
        facOwned.clear();
        adapter = new MngFacilityAdapter(facSettingArrayList, this);

        for(int i = 0;i<facSettingArrayList.size();i++){
            if(facSettingArrayList.get(i).getIsChecked().equals("yes")){
                facOwned.add(facSettingArrayList.get(i).getIdFacilityDb());

            }
        }

       // Toast.makeText(this, "cek "+facOwned.toString(), Toast.LENGTH_SHORT).show();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mWaveSwipeRefreshLayout.setRefreshing(false);

    }

    public void warningDataNotFound(){
        facOwned.clear();
    }


    public void noFacility(){
        Toast.makeText(this, "Belum ada fasilitas dari admin", Toast.LENGTH_SHORT).show();
    }

    public void ErrorResponse(){
        Toast.makeText(this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
    }

    public void ErrorConnectionFailed(){
        Toast.makeText(this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
    }

    public void delFailed(){

        Toast.makeText(this, "Gagal menghapus. Coba lagi nanti", Toast.LENGTH_SHORT).show();
    }

    public void delSuccess(){
        storeSettingsPresenterImp.showFacSetting(strIdStore);
      //  Toast.makeText(this, "del success", Toast.LENGTH_SHORT).show();

    }


    public void addingFacilityToStoreSuccess(){
        storeSettingsPresenterImp.showFacSetting(strIdStore);
      //  Toast.makeText(this, "add success", Toast.LENGTH_SHORT).show();
    }

    public void addingFacilityToStoreFailed(){
        Toast.makeText(this, "Gagal menambah. Coba lagi nanti", Toast.LENGTH_SHORT).show();
    }
}
