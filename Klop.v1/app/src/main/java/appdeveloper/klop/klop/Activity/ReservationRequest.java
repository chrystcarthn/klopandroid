package appdeveloper.klop.klop.Activity;

import android.content.Intent;
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
import appdeveloper.klop.klop.PresenterImp.BookingFragPresenterImp;
import appdeveloper.klop.klop.R;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class ReservationRequest extends AppCompatActivity {

    Toolbar toolbar;
    private RecyclerView recyclerView;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;

    TextView emptyTextView;

    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdUser;
    BookingFragPresenterImp bookingFragPresenterImp;
    MyReservationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_request);

        session = new SessionPreference(this);
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe2);
        mWaveSwipeRefreshLayout.setWaveColor(getResources().getColor(R.color.colorPrimary));
        mWaveSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.white));

        bookingFragPresenterImp = new BookingFragPresenterImp(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewReservation);
        emptyTextView = (TextView) findViewById(R.id.tvEmptyReservation);

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

        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                emptyTextView.setVisibility(View.GONE);
                loadRecyclerViewDataReservation();
            }
        });

        loadRecyclerViewDataReservation();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadRecyclerViewDataReservation();
    }

    private void loadRecyclerViewDataReservation()
    {
        mWaveSwipeRefreshLayout.setRefreshing(true);
        bookingFragPresenterImp.showReservationReq(strIdUser);
    }

    public void generateReservationResponse(ArrayList<Booking> reservationArrayList){
        emptyTextView.setVisibility(View.GONE);
        adapter = new MyReservationAdapter(reservationArrayList,this);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }


    public void ErrorResponse() {
        Toast.makeText(this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
        emptyTextView.setText("Coba lagi beberapa saat");
        emptyTextView.setVisibility(View.VISIBLE);
    }


    public void ErrorConnection() {
        Toast.makeText(this, "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
        emptyTextView.setText("Coba lagi beberapa saat");
        emptyTextView.setVisibility(View.VISIBLE);
    }

    public void warningDataNotFound(){
       // Toast.makeText(this, "Oops, data tidak ditemukan!", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
        emptyTextView.setVisibility(View.VISIBLE);
    }

    public void loginTimeOut(){
        Toast.makeText(this, "Login time out!", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }
}
