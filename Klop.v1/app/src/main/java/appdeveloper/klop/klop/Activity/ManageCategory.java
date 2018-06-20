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

import appdeveloper.klop.klop.Adapter.MngCategoryAdapter;
import appdeveloper.klop.klop.Model.CatSetting;
import appdeveloper.klop.klop.Model.Category;
import appdeveloper.klop.klop.Model.CategoryDb;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.CategoryFragPresenterImp;
import appdeveloper.klop.klop.PresenterImp.StoreSettingsPresenterImp;
import appdeveloper.klop.klop.R;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class ManageCategory extends AppCompatActivity {

    String strIdStore;
    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdUser;
    Toolbar toolbar;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    RecyclerView recyclerView;
    CategoryFragPresenterImp categoryFragPresenterImp;
    StoreSettingsPresenterImp storeSettingsPresenterImp;
    ArrayList<String> catSelected;
    ArrayList<String> catRemoved;
    ArrayList<String> catOwned;
    MngCategoryAdapter adapter;
    Button btnSimpan;
    String formattedIdArraylist, formattedIdArraylist2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_category);

        Bundle b = getIntent().getExtras();
        strIdStore = b.getString("idStore");

        session = new SessionPreference(this);
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        catSelected = new ArrayList<String>();
        catRemoved = new ArrayList<String>();
        catOwned = new ArrayList<String>();

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

        //categoryFragPresenterImp = new CategoryFragPresenterImp(this);
        storeSettingsPresenterImp = new StoreSettingsPresenterImp(this);

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
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCat);


        loadRecyclerViewCat();
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(adapter.categorySelected.size() > 0){
                    for(int i = 0; i < adapter.categorySelected.size(); i++){
                        if(catOwned.contains(adapter.categorySelected.get(i))){
                            adapter.categorySelected.remove(adapter.categorySelected.get(i));
                        } else{
                            catOwned.add(adapter.categorySelected.get(i));
                        }
                    }
                    formattedIdArraylist = adapter.categorySelected.toString()
                            .replace("[","")
                            .replace("]","")
                            .replace(" ","")
                            .trim();
                    storeSettingsPresenterImp.addCategory(strIdStore, formattedIdArraylist);
                }
                else {
                   // Toast.makeText(ManageCategory.this, "categorySelected "+adapter.categorySelected.size(), Toast.LENGTH_SHORT).show();
                }

              //  Toast.makeText(ManageCategory.this, adapter.deleteCategory.toString(), Toast.LENGTH_SHORT).show();

                if(adapter.deleteCategory.size() > 0) {
                    for(int i = 0; i < adapter.deleteCategory.size(); i++){
                        if(catOwned.contains(adapter.deleteCategory.get(i))){
                            catOwned.remove(adapter.deleteCategory.get(i));
                            catRemoved.add(adapter.deleteCategory.get(i));
                        }
                    }

                    if(catOwned.size() == 0){
                        storeSettingsPresenterImp.showCatSetting(strIdStore);
                        Toast.makeText(ManageCategory.this, "Harap memilih minimal 1 kategori", Toast.LENGTH_SHORT).show();
                        catRemoved.clear();

                    } else {
                        formattedIdArraylist2 = catRemoved.toString()
                                .replace("[","")
                                .replace("]","")
                                .replace(" ","")
                                .trim();
                        storeSettingsPresenterImp.deleteCatSetting(strIdStore, formattedIdArraylist2);
                    }
                }
                else
                {
                  //  Toast.makeText(ManageCategory.this, "deleteFacility "+adapter.deleteCategory.size(), Toast.LENGTH_SHORT).show();
                }
              //  Toast.makeText(ManageCategory.this, "owned "+catOwned.toString(), Toast.LENGTH_SHORT).show();
                }

        });
    }

    private void loadRecyclerViewCat()
    {
        mWaveSwipeRefreshLayout.setRefreshing(true);
        storeSettingsPresenterImp.showCatSetting(strIdStore);
//        categoryFragPresenterImp.showAllCategory2();

    }

    public void generateCatSettingResponse(ArrayList<CatSetting> catSettingArrayList){
        catOwned.clear();
        adapter = new MngCategoryAdapter(catSettingArrayList, this);

        for(int i = 0;i<catSettingArrayList.size();i++){
            if(catSettingArrayList.get(i).getIsChecked().equals("yes")){
                catOwned.add(catSettingArrayList.get(i).getIdCategoryDb());
            }
        }

      //  Toast.makeText(this, "cek "+catOwned.toString(), Toast.LENGTH_SHORT).show();

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }



    public void warningDataNotFound(){
        catOwned.clear();
    }


    public void noCategory(){
        Toast.makeText(this, "Belum ada kategori dari admin", Toast.LENGTH_SHORT).show();
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
        storeSettingsPresenterImp.showCatSetting(strIdStore);
     //   Toast.makeText(this, "del success", Toast.LENGTH_SHORT).show();

    }


    public void addingCategoryToStoreSuccess(){
        storeSettingsPresenterImp.showCatSetting(strIdStore);
       // Toast.makeText(this, "add success", Toast.LENGTH_SHORT).show();
    }

    public void addingCategoryToStoreFailed(){

        Toast.makeText(this, "Gagal menambah. Coba lagi nanti", Toast.LENGTH_SHORT).show();
    }

}
