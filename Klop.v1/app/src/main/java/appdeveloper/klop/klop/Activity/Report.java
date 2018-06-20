package appdeveloper.klop.klop.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;

import appdeveloper.klop.klop.Adapter.MyReservationAdapter;
import appdeveloper.klop.klop.Model.Booking;
import appdeveloper.klop.klop.Model.BookingResponse;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.BookingFragPresenterImp;
import appdeveloper.klop.klop.R;
import es.dmoral.toasty.Toasty;

public class Report extends AppCompatActivity {

    Toolbar toolbar;
    BarChart chart, chart1;
    ArrayList<BarEntry> BARENTRY;
    ArrayList<BarEntry> BARENTRY1;
    ArrayList<String> BarEntryLabels, BarEntryLabels1;
    BarDataSet barDataSet, barDataSet1;
    BarData BARDATA, BARDATA1;
    String count, count2, rate;
    int menunggu=0;
    int diterima=0;
    int dibatalkan=0;
    public int ditolak=0;
    TextView tvHint, tvHint2;
    int btg1=0, btg2=0, btg3=0, btg4=0, btg5=0;

    String strIdUser, strIdStore;
    SessionPreference session;
    HashMap<String, String> userdata;
    TextView tvJmlhRes, tvJmlhRate, tvRating;

    BookingFragPresenterImp bookingFragPresenterImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        session = new SessionPreference(this);
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        Bundle b = getIntent().getExtras();
        strIdStore = b.getString("idStore");
        count2 = b.getString("count2");
        rate = b.getString("rate");
        btg1 = b.getInt("btg1");
        btg2 = b.getInt("btg2");
        btg3 = b.getInt("btg3");
        btg4 = b.getInt("btg4");
        btg5 = b.getInt("btg5");

        count = b.getString("count");
        menunggu = b.getInt("menunggu");
        diterima = b.getInt("diterima");
        dibatalkan = b.getInt("dibatalkan");
        ditolak = b.getInt("ditolak");
      //  Toast.makeText(this, "rate "+rate+", jumlah rev "+count2+"|"+btg1+"|"+btg2+"|"+btg3+"|"+btg4+"|"+btg5, Toast.LENGTH_SHORT).show();

    //    Toast.makeText(this, "tolak "+menunggu+"|"+diterima+"|"+dibatalkan+"|"+ditolak, Toast.LENGTH_SHORT).show();

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

        bookingFragPresenterImp = new BookingFragPresenterImp(this);

        tvHint = (TextView) findViewById(R.id.tvHint);
        tvHint2 = (TextView) findViewById(R.id.tvHint2);
        chart = (BarChart) findViewById(R.id.barchart);
        chart1 = (BarChart) findViewById(R.id.barchart1);

        tvRating = (TextView) findViewById(R.id.tvRating);


        tvRating.setText(rate);
        tvJmlhRate = (TextView) findViewById(R.id.tvJmlhRate);
        tvJmlhRes = (TextView) findViewById(R.id.tvJmlhRes);


        if(count2 == null){
            count2 = "0";
            tvJmlhRate.setText("Outlet belum pernah di ulas.\nTidak ada grafik yang tersedia untuk menampilkan data.");
            chart.setVisibility(View.GONE);
            tvHint.setVisibility(View.GONE);
        } else   {
            chart.setVisibility(View.VISIBLE);
            tvHint.setVisibility(View.VISIBLE);
            tvJmlhRate.setText("Total ulasan : "+count2);
        }

        if(count == null)
        {
            count = "0";
            tvJmlhRes.setText("Outlet belum pernah reservasi.\nTidak ada grafik yang tersedia untuk menampilkan data.");
            chart1.setVisibility(View.GONE);
            tvHint2.setVisibility(View.GONE);
        }
        else   {
            chart1.setVisibility(View.VISIBLE);
            tvHint2.setVisibility(View.VISIBLE);
            tvJmlhRes.setText("Total reservasi yang masuk : "+count);
        }

        BARENTRY = new ArrayList<>();
        BARENTRY1 = new ArrayList<>();

        BarEntryLabels = new ArrayList<String>();
        BarEntryLabels1 = new ArrayList<String>();

        AddValuesToBARENTRY();
        AddValuesToBARENTRY1();

        barDataSet = new BarDataSet(BARENTRY, "Ulasan Bintang");
        barDataSet1 = new BarDataSet(BARENTRY1, "Jenis reservasi");

        barDataSet1.notifyDataSetChanged();

        barDataSet.setColors(new int[] {getResources().getColor(R.color.closed), getResources().getColor(R.color.orange), getResources().getColor(R.color.open), getResources().getColor(R.color.colorPrimaryDark), getResources().getColor(R.color.purple)});
        barDataSet1.setColors(new int[] {getResources().getColor(R.color.gold), getResources().getColor(R.color.open), getResources().getColor(R.color.secondaryText2), getResources().getColor(R.color.closed)});

        BARDATA = new BarData(BarEntryLabels, barDataSet);
        BARDATA1 = new BarData(BarEntryLabels1, barDataSet1);

     //   barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(BARDATA);
        chart.animateY(3000);
        chart.setDescription("");

        chart1.setData(BARDATA1);
        chart1.animateY(3000);
        chart1.setDescription("");


        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                Intent intent = new Intent(Report.this, DetailReportRating.class);
                Bundle b = new Bundle();
                b.putString("idStore", strIdStore);
                b.putInt("index", entry.getXIndex());
                intent.putExtras(b);
                startActivity(intent);


//                Toast.makeText(Report.this, "click :"+entry.getXIndex(), Toast.LENGTH_SHORT).show();
//                Toasty.error(Report.this, "This is an error toast.", Toast.LENGTH_SHORT, true).show();
//                Toasty.success(Report.this, "Success!", Toast.LENGTH_SHORT, true).show();
//                Toasty.warning(Report.this, "Beware of the dog.", Toast.LENGTH_SHORT, true).show();
              //  Toasty.info(Report.this, "click :"+entry.getXIndex(), Toast.LENGTH_SHORT, true).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        chart1.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                Intent intent = new Intent(Report.this, DetailReportReservasi.class);
                Bundle b = new Bundle();
                b.putString("idStore", strIdStore);
                b.putInt("index", entry.getXIndex());
                intent.putExtras(b);
                startActivity(intent);


//                Toast.makeText(Report.this, "click :"+entry.getXIndex(), Toast.LENGTH_SHORT).show();
//                Toasty.error(Report.this, "This is an error toast.", Toast.LENGTH_SHORT, true).show();
//                Toasty.success(Report.this, "Success!", Toast.LENGTH_SHORT, true).show();
//                Toasty.warning(Report.this, "Beware of the dog.", Toast.LENGTH_SHORT, true).show();
            //    Toasty.info(Report.this, "click :"+entry.getXIndex(), Toast.LENGTH_SHORT, true).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
        barDataSet1.notifyDataSetChanged();
        chart1.notifyDataSetChanged();
        chart1.invalidate();

        chart1.notifyDataSetChanged();
        chart1.invalidate();

    }

    public void AddValuesToBARENTRY(){
        BARENTRY.add(new BarEntry(btg1, 0));
        BARENTRY.add(new BarEntry(btg2, 1));
        BARENTRY.add(new BarEntry(btg3, 2));
        BARENTRY.add(new BarEntry(btg4, 3));
        BARENTRY.add(new BarEntry(btg5, 4));

        AddValuesToBarEntryLabels();
    }

    public void AddValuesToBarEntryLabels(){
        BarEntryLabels.add("★ 1");
        BarEntryLabels.add("★ 2");
        BarEntryLabels.add("★ 3");
        BarEntryLabels.add("★ 4");
        BarEntryLabels.add("★ 5");
    }


    public void AddValuesToBARENTRY1(){

        BARENTRY1.add(new BarEntry( menunggu, 0));
        BARENTRY1.add(new BarEntry( diterima, 1));
        BARENTRY1.add(new BarEntry( dibatalkan, 2));
        BARENTRY1.add(new BarEntry( ditolak, 3));

        AddValuesToBarEntryLabels1();
    }

    public void AddValuesToBarEntryLabels1(){
        BarEntryLabels1.add("Menunggu");
        BarEntryLabels1.add("Diterima");
        BarEntryLabels1.add("Dibatalkan");
        BarEntryLabels1.add("Ditolak");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        chart.setData(BARDATA);
        chart.animateY(3000);
        chart.setDescription("");

        chart1.setData(BARDATA1);
        chart1.animateY(3000);
        chart1.setDescription("");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        chart.setData(BARDATA);
        chart.animateY(3000);
        chart.setDescription("");

        chart1.setData(BARDATA1);
        chart1.animateY(3000);
        chart1.setDescription("");
    }
}
