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
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import appdeveloper.klop.klop.Adapter.MngNewsAdapter;
import appdeveloper.klop.klop.Model.News;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.StoreSettingsPresenterImp;
import appdeveloper.klop.klop.R;
import es.dmoral.toasty.Toasty;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class ManageNews extends AppCompatActivity {

    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdStore, strIdUser, strStatus;
    Switch swtStatus;
    TextView tvStatus;
    Toolbar toolbar;

    RecyclerView recyclerView;
    TextView tvEmpty;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;

    FloatingActionButton fabAddNews;
    Button btnAddNews;
    TextView tvJudul;
    EditText edJudul, edIsi;
    TextInputLayout judulWrapper, isiWrapper;
    ImageButton btnClose;

    AlertDialog dialog;

    StoreSettingsPresenterImp storeSettingsPresenterImp;
    MngNewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_news);


        session = new SessionPreference(this);
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        Bundle b = getIntent().getExtras();
        strIdStore = b.getString("idStore");

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
        fabAddNews = (FloatingActionButton) findViewById(R.id.fabAddNews);


        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewNews);
        tvEmpty = (TextView) findViewById(R.id.tvEmptyNews);

        storeSettingsPresenterImp = new StoreSettingsPresenterImp(this);

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe2);
        mWaveSwipeRefreshLayout.setWaveColor(getResources().getColor(R.color.colorPrimary));
        mWaveSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.white));

        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tvEmpty.setVisibility(View.GONE);
                mWaveSwipeRefreshLayout.setRefreshing(false);
                loadRecyclerViewNews();
                // adapter.notifyDataSetChanged();

            }
        });


        fabAddNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ManageNews.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_add_news, null);

                tvJudul = (TextView) mView.findViewById(R.id.tvJudul);
                tvStatus = (TextView) mView.findViewById(R.id.tvStatus);
                judulWrapper = (TextInputLayout) mView.findViewById(R.id.input_layout_judul);
                isiWrapper = (TextInputLayout) mView.findViewById(R.id.input_layout_isi);

                btnClose = (ImageButton) mView.findViewById(R.id.btnClose);
                edJudul = (EditText) mView.findViewById(R.id.edJudul);
                edIsi = (EditText) mView.findViewById(R.id.edContent);
                swtStatus = (Switch) mView.findViewById(R.id.switchStatus);
                swtStatus.setChecked(false);
                strStatus = "false";
                swtStatus.setText("DISEMBUNYIKAN");
                swtStatus.setTextColor(getResources().getColor(R.color.secondaryText2));

                btnAddNews = (Button) mView.findViewById(R.id.btnAddNews);

                tvJudul.setText("Berita Baru");
                btnAddNews.setText("Tambah");

                // nmWrapper.setError("Harap mengisi nama perawatan");

                mBuilder.setView(mView);
                dialog = mBuilder.create();

                swtStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(swtStatus.isChecked()){
                            strStatus = "true";
                            swtStatus.setText("DIPAJANG");
                            swtStatus.setTextColor(getResources().getColor(R.color.open));
                        } else{
                            strStatus = "false";
                            swtStatus.setText("DISEMBUNYIKAN");
                            swtStatus.setTextColor(getResources().getColor(R.color.secondaryText2));
                        }
                    }
                });

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideSoftKeyboard();
                        dialog.dismiss();

                    }
                });

                btnAddNews.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        storeSettingsPresenterImp.addNews(strIdStore, edJudul.getText().toString(), edIsi.getText().toString(), strStatus);
                       // storeSettingsPresenterImp.addNewTreatment(strIdStore, edJ.getText().toString(), edDescTr.getText().toString(), edHrgTr.getText().toString());
                    }
                });

                dialog.show();
            }
        });

        loadRecyclerViewNews();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadRecyclerViewNews();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        loadRecyclerViewNews();
    }


    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void loadRecyclerViewNews()
    {
        mWaveSwipeRefreshLayout.setRefreshing(true);
        storeSettingsPresenterImp.showAllNews(strIdStore);
    }

    public void generateNewsResponse(ArrayList<News> newsArrayList){
        tvEmpty.setVisibility(View.GONE);
        adapter = new MngNewsAdapter(newsArrayList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        mWaveSwipeRefreshLayout.setRefreshing(false);
    }

    public void showErrorJudul(){
        judulWrapper.setError("Harap diisi");
        isiWrapper.setErrorEnabled(false);
        requestFocus(edJudul);
    }

    public void showErrorIsi(){
        judulWrapper.setErrorEnabled(false);
        isiWrapper.setError("Harap diisi");
        requestFocus(edIsi);
    }

    public void showNoError(){
        judulWrapper.setErrorEnabled(false);
        isiWrapper.setErrorEnabled(false);
    }

    public void warningNoNewsFound(){
        //  frame.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.VISIBLE);
        mWaveSwipeRefreshLayout.setRefreshing(false);

    }

    public void AddingNewsSuccess(){
        loadRecyclerViewNews();
        btnAddNews.setText("Tambah lagi");
        edJudul.setText("");
        edIsi.setText("");
        swtStatus.setChecked(false);
        swtStatus.setText("DISEMBUNYIKAN");
        swtStatus.setTextColor(getResources().getColor(R.color.secondaryText2));
       // Toasty.success(ManageNews.this, "Berita ditambah", Toast.LENGTH_SHORT, true).show();

    }


    public void ErrorAddingNewsFailed(){
        Toasty.error(ManageNews.this, "Gagal menambah. Coba lagi nanti", Toast.LENGTH_SHORT, true).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }



    public void ErrorResponseFailed(){
        Toasty.error(ManageNews.this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT, true).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }


    public void ErrorConnectionFailed() {
        Toasty.info(ManageNews.this, "Terjadi gangguan. Periksa internet Anda dan ulangi lagi", Toast.LENGTH_SHORT, true).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
