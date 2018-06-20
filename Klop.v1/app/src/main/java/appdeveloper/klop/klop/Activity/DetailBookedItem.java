package appdeveloper.klop.klop.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import appdeveloper.klop.klop.Adapter.MyDetailBookedAdapter;
import appdeveloper.klop.klop.Model.BookingDetail;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.BookingFragPresenterImp;
import appdeveloper.klop.klop.R;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

public class DetailBookedItem extends AppCompatActivity {

    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdBooking, strStatus, strIdUser, strIdStore, strNmStore, strPhStore, strAddressStore, strNmGuest, strPhGuest, strDates, strTimes, strPeople, strTotal;
    Toolbar toolbar;

    String datenow=null;
    String monthnow=null;
    String yearnow=null;

    TextView tvStatus, tvNmGuest, tvPhGuest, tvPeople, tvNmOutlet, tvAddressOutlet, tvDates, tvTimes, tvTotal, tvDetail;
    RecyclerView recyclerView;
    Button btnBatal;
    BookingFragPresenterImp bookingFragPresenterImp;
    String strDateToday;

    MyDetailBookedAdapter adapter;

    private SpotsDialog progressDialog;

    ScrollView scroll;
    TextView txtloading;
    AVLoadingIndicatorView loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_booked_item);

        session = new SessionPreference(this);
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        loading = (AVLoadingIndicatorView) findViewById(R.id.avloadingIndicatorView);
        txtloading = (TextView)findViewById(R.id.txtloading);
        scroll = (ScrollView) findViewById(R.id.scroll);
        scroll.setVisibility(View.GONE);
        startAnim();

        progressDialog = new SpotsDialog(this, R.style.Custom);

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
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCart);

        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvNmGuest = (TextView) findViewById(R.id.tvNmGuest);
        tvPhGuest = (TextView) findViewById(R.id.tvPhGuest);
        tvPeople = (TextView) findViewById(R.id.tvPeople);
        tvNmOutlet = (TextView) findViewById(R.id.tvNmOutlet);
        tvAddressOutlet = (TextView) findViewById(R.id.tvAddressOutlet);
        tvDates = (TextView) findViewById(R.id.tvDates);
        tvTimes = (TextView) findViewById(R.id.tvTimes);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvDetail = (TextView) findViewById(R.id.tvDetail);
        btnBatal = (Button) findViewById(R.id.btnBatalkanReservasi);

        Bundle b = getIntent().getExtras();
        strIdBooking = b.getString("idBooking");
        strStatus = b.getString("status");
        strIdStore = b.getString("idStore");
        strNmStore = b.getString("nmStore");
        strPhStore = b.getString("phStore");
        strAddressStore = b.getString("address");
        strIdUser = b.getString("idUser");
        strNmGuest = b.getString("nmGuest");
        strPhGuest = b.getString("phGuest");
        strDates = b.getString("dates");
        strTimes = b.getString("times");
        strPeople = b.getString("people");
        strTotal = b.getString("total");

        //getDateToday();

        if(strStatus.equals("waiting")){
            tvStatus.setText("Menunggu konfirmasi dari pihak outlet");
            tvStatus.setTextColor(getResources().getColor(R.color.gold));
            btnBatal.setVisibility(View.VISIBLE);

        } else
        if(strStatus.equals("approved")){
            tvStatus.setText("Reservasi ini diterima oleh pihak outlet");
            tvStatus.setTextColor(getResources().getColor(R.color.open));
            btnBatal.setVisibility(View.VISIBLE);
        } else
        if(strStatus.equals("declined")){
            tvStatus.setText("Reservasi ini ditolak oleh pihak outlet");
            tvStatus.setTextColor(getResources().getColor(R.color.closed));
            btnBatal.setVisibility(View.GONE);
        } else
        if(strStatus.equals("canceled")){
           tvStatus.setText("Anda telah membatalkan reservasi ini");
            tvStatus.setTextColor(getResources().getColor(R.color.secondaryText2));
            btnBatal.setVisibility(View.GONE);
        } else
        if(strStatus.equals("dropped")){
            tvStatus.setText("Pihak outlet telah membatalkan reservasi ini");
            tvStatus.setTextColor(getResources().getColor(R.color.closed));
            btnBatal.setVisibility(View.GONE);
        }

        tvNmOutlet.setText(strNmStore);
        tvNmGuest.setText(strNmGuest);
        tvPhGuest.setText(strPhGuest);
        tvPeople.setText(strPeople+" orang");
        tvAddressOutlet.setText(strAddressStore);
        tvDates.setText(strDates);
        tvTimes.setText(strTimes);

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        String hargaFormatted = String.valueOf(strTotal);

        bookingFragPresenterImp.showMyBookedDetail(strIdBooking);

        tvTotal.setText(formatRupiah.format((double)Integer.parseInt(hargaFormatted)));

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(DetailBookedItem.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Konfirmasi")
                        .setContentText("Anda yakin ingin membatalkan reservasi ini?")
                        .setConfirmText("Ya")
                        .setCancelText("Batal")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        bookingFragPresenterImp.cancelRes(strIdBooking);
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
            }
        });

        tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailBookedItem.this, ShowDetailStore.class);
                Bundle b = new Bundle();
                b.putString("idStore", strIdStore);
                b.putString("nmStore", strNmStore);
                b.putString("address", strAddressStore);
                i.putExtras(b);
                startActivity(i);
            }
        });

        stopAnim();
    }

    void startAnim(){
        loading.setVisibility(View.VISIBLE);
        txtloading.setVisibility(View.VISIBLE);
        scroll.setVisibility(View.GONE);
    }

    public void stopAnim(){
        loading.setVisibility(View.GONE);
        scroll.setVisibility(View.VISIBLE);
        txtloading.setVisibility(View.GONE);
    }

    public void getDateToday(){
        datenow = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        monthnow  = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
        yearnow = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());

        String dnow, mnow;
        dnow = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        mnow = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());

        if (String.valueOf(datenow).length() < 2) {
            datenow = "0" + String.valueOf(datenow);
        } else {
            datenow = String.valueOf(datenow);
        }

        if (String.valueOf(monthnow).length() < 2) {
            monthnow = "0" + String.valueOf(monthnow);
        } else {
            monthnow = String.valueOf(monthnow);
        }


        //  Toast.makeText(getContext(), "tanggal Init:"+strFinDate, Toast.LENGTH_SHORT).show();
        strDateToday = getDayName(String.valueOf(yearnow), mnow, String.valueOf(dnow)) + ", " + datenow + " " + getMonthName(mnow)+" "+yearnow;

    }


    public String getDayName(String year,String month, String day){

        String namahari="";

        int intyear = Integer.parseInt(year);
        int intmonth = Integer.parseInt(month);
        int intday = Integer.parseInt(day);

        // Toast.makeText(getContext(), year+" "+month+" "+day, Toast.LENGTH_SHORT).show();
        String dateString = String.format("%d-%d-%d", intyear, intmonth, intday);
        Date dates = null;
        try {
            dates = new SimpleDateFormat("yyyy-M-d").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(dates);
        //   Toast.makeText(getContext(), "hari: "+dayOfWeek, Toast.LENGTH_SHORT).show();

        if(dayOfWeek.equals("Monday")){
            namahari = "Senin";
        }else if(dayOfWeek.equals("Tuesday")){
            namahari = "Selasa";
        }else if(dayOfWeek.equals("Wednesday")){
            namahari = "Rabu";
        }else if(dayOfWeek.equals("Thursday")){
            namahari = "Kamis";
        }else if(dayOfWeek.equals("Friday")){
            namahari = "Jumat";
        }else if(dayOfWeek.equals("Saturday")){
            namahari = "Sabtu";
        }else if(dayOfWeek.equals("Sunday")){
            namahari = "Minggu";
        }
        return namahari;
    }


    public String getMonthName(String month){
        String monthName = "";

        if(month.equals("01")){
            monthName = "Januari";
        }else if(month.equals("02")){
            monthName = "Februari";
        }else if(month.equals("03")){
            monthName = "Maret";
        }else if(month.equals("04")){
            monthName = "April";
        }else if(month.equals("05")){
            monthName = "Mei";
        }else if(month.equals("06")){
            monthName = "Juni";
        }else if(month.equals("07")){
            monthName = "Juli";
        }else if(month.equals("08")){
            monthName = "Agustus";
        }else if(month.equals("09")){
            monthName = "September";
        }else if(month.equals("10")){
            monthName = "Oktober";
        }else if(month.equals("11")){
            monthName = "November";
        }else if(month.equals("12")){
            monthName = "Desember";
        }
        return monthName;
    }


    public void generateBookingDetailResponse(ArrayList<BookingDetail> bookingDetailArrayList){
            adapter = new MyDetailBookedAdapter(bookingDetailArrayList, this);
            final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

            recyclerView.setAdapter(adapter);
    }


    public void reqCanceled(){
        Toast.makeText(this, "Reservasi dibatalkan", Toast.LENGTH_SHORT).show();
        tvStatus.setText("Anda telah membatalkan reservasi ini");
        tvStatus.setTextColor(getResources().getColor(R.color.secondaryText2));
        btnBatal.setVisibility(View.GONE);
    }

    public void errorAction(){
        Toast.makeText(this, "Telah terjadi kesalaham. Coba ulangi lagi nanti", Toast.LENGTH_SHORT).show();
    }

    public void idBookingInValid(){
        Toast.makeText(this, "Tidak ada reservasi", Toast.LENGTH_SHORT).show();
    }

    public void ErrorResponse(){
        Toast.makeText(this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
    }

    public void ErrorConnection(){
        Toast.makeText(this, "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
    }

    public void  warningDataNotFound(){
        Toast.makeText(this, "Tidak ada perawatan yang dipesan", Toast.LENGTH_SHORT).show();
    }

    public void  loginTimeOut(){
       // Toast.makeText(this, "Masuk terlebih dahulu", Toast.LENGTH_SHORT).show();
        loginTimeOut();
    }

    public void dismissSpotLoading(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void showSpotLoading(){
        progressDialog.show();
        progressDialog.setCancelable(true);
    }
}
