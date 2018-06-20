package appdeveloper.klop.klop.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import appdeveloper.klop.klop.Adapter.MyReservationAdapter;
import appdeveloper.klop.klop.Model.Booking;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.ReportPresenterImp;
import appdeveloper.klop.klop.R;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class DetailReportReservasi extends AppCompatActivity {

    Toolbar toolbar;
    private RecyclerView recyclerView;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdUser;
    MyReservationAdapter adapter;
    ReportPresenterImp reportPresenterImp;
    int status;
    String strIdStore;
    TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_request);

        session = new SessionPreference(this);
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        Bundle b = getIntent().getExtras();
        strIdStore = b.getString("idStore");
        status = b.getInt("index");

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
        emptyTextView = (TextView) findViewById(R.id.tvEmptyReservation);
        reportPresenterImp = new ReportPresenterImp(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewReservation);

     //   Toast.makeText(this, "ind "+status, Toast.LENGTH_SHORT).show();

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe2);
        mWaveSwipeRefreshLayout.setWaveColor(getResources().getColor(R.color.yellow));
        mWaveSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.white));

        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                loadRecyclerViewDataReservation();
            }
        });

        loadRecyclerViewDataReservation();

    }

    private void loadRecyclerViewDataReservation()
    {
        mWaveSwipeRefreshLayout.setRefreshing(true);

        if(status == 0){
            reportPresenterImp.showWaiting(strIdStore);
        } else
        if(status == 1){
            reportPresenterImp.showApproved(strIdStore);
        } else
        if(status == 2){
            reportPresenterImp.showCanceled(strIdStore);
        } else
        if(status == 3){
            reportPresenterImp.showRejected(strIdStore);
        }
    }

    public void generateReservationResponse(ArrayList<Booking> reservationArrayList){
        emptyTextView.setVisibility(View.GONE);
        adapter = new MyReservationAdapter(reservationArrayList,this);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }

    public void ErrorConnection() {
        Toast.makeText(this, "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
        emptyTextView.setText("Coba beberapa saat lagi.");
        emptyTextView.setVisibility(View.VISIBLE);
    }

    public void warningDataNotFound(){
        // Toast.makeText(this, "Oops, data tidak ditemukan!", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
        emptyTextView.setVisibility(View.VISIBLE);
    }

    public void loginTimeOut(){
        mWaveSwipeRefreshLayout.setRefreshing(false);
        Toast.makeText(this, "Login time out!", Toast.LENGTH_SHORT).show();
        session.logoutUser();

    }

    public void ErrorResponse() {
        Toast.makeText(this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
        emptyTextView.setText("Coba beberapa saat lagi.");
        emptyTextView.setVisibility(View.VISIBLE);
    }

}
