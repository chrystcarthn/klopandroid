package appdeveloper.klop.klop.Activity;

import android.app.AlertDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import appdeveloper.klop.klop.Adapter.MngTreatmentAdapter;
import appdeveloper.klop.klop.Adapter.TreatmentAdapter;
import appdeveloper.klop.klop.Model.Treatment;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.StoreSettingsPresenterImp;
import appdeveloper.klop.klop.PresenterImp.TreatmentFragPresenterImp;
import appdeveloper.klop.klop.R;
import dmax.dialog.SpotsDialog;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class ManageTreatment extends AppCompatActivity implements SearchView.OnQueryTextListener {

    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdStore, strIdUser;


    MngTreatmentAdapter adapter;
    RecyclerView recyclerView;
    TextView tvEmpty, tvEmpty2, tvEmpty3, tvEmpty4;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    Toolbar toolbar;

    FloatingActionButton fabAddTr;
    Button btnAddNewTr;
    TextView tvJudul;
    EditText edNmTr, edDescTr, edHrgTr;
    TextInputLayout nmWrapper, descWrapper, hrgWrapper;
    ImageButton btnClose;

    AlertDialog dialog;

    StoreSettingsPresenterImp storeSettingsPresenterImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_treatment);

        session = new SessionPreference(this);
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        Bundle b = getIntent().getExtras();
        strIdStore = b.getString("idStore");

        storeSettingsPresenterImp = new StoreSettingsPresenterImp(this);

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
        fabAddTr = (FloatingActionButton) findViewById(R.id.fabAddTr);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewTreatment);
        tvEmpty = (TextView) findViewById(R.id.tvEmptyTr);
        tvEmpty2 = (TextView) findViewById(R.id.tvEmptyTr2);


        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe2);
        mWaveSwipeRefreshLayout.setWaveColor(getResources().getColor(R.color.colorPrimary));
        mWaveSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.white));

        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tvEmpty.setVisibility(View.GONE);
                tvEmpty2.setVisibility(View.GONE);
                mWaveSwipeRefreshLayout.setRefreshing(false);
                loadRecyclerViewTreatmentByOutlet();
               // adapter.notifyDataSetChanged();

            }
        });


        fabAddTr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ManageTreatment.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_add_treatment, null);

                tvJudul = (TextView) mView.findViewById(R.id.tvJudul);
                nmWrapper = (TextInputLayout) mView.findViewById(R.id.input_layout_namatr);
                descWrapper = (TextInputLayout) mView.findViewById(R.id.input_layout_desc);
                hrgWrapper = (TextInputLayout) mView.findViewById(R.id.input_layout_harga);
                btnClose = (ImageButton) mView.findViewById(R.id.btnClose);
                edNmTr = (EditText) mView.findViewById(R.id.edNameTr);
                edDescTr = (EditText) mView.findViewById(R.id.edDescTreatment);
                edHrgTr = (EditText) mView.findViewById(R.id.edPriceTreatment);
                btnAddNewTr = (Button) mView.findViewById(R.id.btnAddNewTr);

                tvJudul.setText("Perawatan Baru");
                btnAddNewTr.setText("Tambah");

               // nmWrapper.setError("Harap mengisi nama perawatan");

                mBuilder.setView(mView);
                 dialog = mBuilder.create();

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideSoftKeyboard();
                        dialog.dismiss();

                    }
                });

                btnAddNewTr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        storeSettingsPresenterImp.addNewTreatment(strIdStore, edNmTr.getText().toString(), edDescTr.getText().toString(), edHrgTr.getText().toString());
                    }
                });

                dialog.show();
            }
        });

        loadRecyclerViewTreatmentByOutlet();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadRecyclerViewTreatmentByOutlet();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        loadRecyclerViewTreatmentByOutlet();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(ManageTreatment.this);
        searchView.setQueryHint("Ketik nama perawatan...");

        super.onCreateOptionsMenu(menu);
        return  true;

    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    public void loadRecyclerViewTreatmentByOutlet()
    {
        recyclerView.setVisibility(View.VISIBLE);
        mWaveSwipeRefreshLayout.setRefreshing(true);
        storeSettingsPresenterImp.showTreatment(strIdStore);
    }

    public void tes(){
        storeSettingsPresenterImp.showTreatment(strIdStore);
     //   Toast.makeText(this, "berhasil", Toast.LENGTH_SHORT).show();
    }

    public void generateTreatmentResponse(ArrayList<Treatment> treatmentArrayList){

        tvEmpty.setVisibility(View.GONE);
        tvEmpty2.setVisibility(View.GONE);

        adapter = new MngTreatmentAdapter(treatmentArrayList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        mWaveSwipeRefreshLayout.setRefreshing(false);

    }


    public void AddingTrSuccess(){
        loadRecyclerViewTreatmentByOutlet();
        btnAddNewTr.setText("Tambah lagi");
        edNmTr.setText("");
        edDescTr.setText("");
        edHrgTr.setText("");
    }




    public void warningNoTreatmentFound(){

        tvEmpty.setVisibility(View.VISIBLE);
        tvEmpty2.setVisibility(View.VISIBLE);

        recyclerView.setVisibility(View.GONE);
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }

    public void ErrorAddingTrFailed(){
        Toast.makeText(this, "Data tidak valid. Harap periksa kembali data Anda", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }



    public void ErrorResponseFailed(){
        Toast.makeText(this, "Terjadi gangguan pada server. Coba ulangi lagi nanti", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }


    public void ErrorConnectionFailed() {
        Toast.makeText(this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void showErrorNm(){
        nmWrapper.setErrorEnabled(true);
        nmWrapper.setError("Harap diisi");
        hrgWrapper.setErrorEnabled(false);
        requestFocus(edNmTr);
    }

    public void showErrorHrg(){
        nmWrapper.setErrorEnabled(false);
        hrgWrapper.setError("Harap diisi");
        requestFocus(edHrgTr);
    }

    public void showNoError(){
        nmWrapper.setErrorEnabled(false);
        hrgWrapper.setErrorEnabled(false);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            mWaveSwipeRefreshLayout.setRefreshing(false);
            return false;
        }

        tvEmpty.setVisibility(View.GONE);
        tvEmpty2.setVisibility(View.GONE);

        doSearchUserStoreByKeyword(newText);

        return false;
    }

    public void resetSearch() {

        tvEmpty.setVisibility(View.GONE);
        tvEmpty2.setVisibility(View.GONE);

        loadRecyclerViewTreatmentByOutlet();
    }

    public void doSearchUserStoreByKeyword(String strKeyword){
        SpotsDialog alertDialog = new SpotsDialog(this);
        alertDialog.show();

        mWaveSwipeRefreshLayout.setRefreshing(true);
        storeSettingsPresenterImp.searchTreatment(strIdStore, strKeyword);
        mWaveSwipeRefreshLayout.setRefreshing(false);
        alertDialog.hide();
    }
}
