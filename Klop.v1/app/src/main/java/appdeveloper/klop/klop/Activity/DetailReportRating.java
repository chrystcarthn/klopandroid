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

import appdeveloper.klop.klop.Adapter.ReviewAdapter;
import appdeveloper.klop.klop.Model.Review;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.ReportPresenterImp;
import appdeveloper.klop.klop.R;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class DetailReportRating extends AppCompatActivity {

    Toolbar toolbar;
    private RecyclerView recyclerView;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdUser;
    ReportPresenterImp reportPresenterImp;
    int status;
    String strIdStore;
    TextView emptyTextView;
    ReviewAdapter adapter;
    TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_report_rating);

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
        tvEmpty = (TextView) findViewById(R.id.tvEmptyRev);
        reportPresenterImp = new ReportPresenterImp(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewReview);

        //   Toast.makeText(this, "ind "+status, Toast.LENGTH_SHORT).show();

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe2);
        mWaveSwipeRefreshLayout.setWaveColor(getResources().getColor(R.color.yellow));
        mWaveSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.white));

        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                loadRecyclerViewRev();
            }
        });

        loadRecyclerViewRev();
    }

    private void loadRecyclerViewRev()
    {
        mWaveSwipeRefreshLayout.setRefreshing(true);

        if(status == 0){
            reportPresenterImp.showRate1(strIdStore);
        } else
        if(status == 1){
            reportPresenterImp.showRate2(strIdStore);
        } else
        if(status == 2){
            reportPresenterImp.showRate3(strIdStore);
        } else
        if(status == 3){
            reportPresenterImp.showRate4(strIdStore);
        } else
        if(status == 4){
            reportPresenterImp.showRate5(strIdStore);
        }
    }

    public void generateReviewResponse(ArrayList<Review> reviewArrayList){
        tvEmpty.setVisibility(View.GONE);
        //  recyclerView = (RecyclerView) findViewById(R.id.recyclerViewOutletbyId);
        adapter = new ReviewAdapter(reviewArrayList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }


    public void warningNoReviewFound(){
        tvEmpty.setVisibility(View.VISIBLE);
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }

    public void ErrorResponseFailed(){
        tvEmpty.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Gagal parsing JSON", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }

    public void ErrorConnectionFailed() {
        tvEmpty.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Koneksi ke server gagal", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }
}
