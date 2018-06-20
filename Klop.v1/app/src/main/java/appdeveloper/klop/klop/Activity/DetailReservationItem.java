package appdeveloper.klop.klop.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import appdeveloper.klop.klop.Adapter.MyDetailReservationAdapter;
import appdeveloper.klop.klop.Model.BookingDetail;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.BookingFragPresenterImp;
import appdeveloper.klop.klop.R;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import static cn.pedant.SweetAlert.SweetAlertDialog.SUCCESS_TYPE;

public class DetailReservationItem extends AppCompatActivity {

    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdBooking, strStatus, strIdUser, strIdStore, strNmStore, strPhStore, strAddressStore, strNmGuest, strPhGuest, strDates, strTimes, strPeople, strTotal;
    Toolbar toolbar;
    private SpotsDialog progressDialog;
    TextView tvStatus, tvNmGuest, tvPhGuest, tvPeople, tvNmOutlet, tvAddressOutlet, tvDates, tvTimes, tvTotal;
    RecyclerView recyclerView;
    Button btnTolak, btnTerima, btnCancel;
    BookingFragPresenterImp bookingFragPresenterImp;
    private static final int REQUEST_CALL = 1;
    MyDetailReservationAdapter adapter;

    ScrollView scroll;
    TextView txtloading;
    AVLoadingIndicatorView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_reservation_item);

        session = new SessionPreference(this);
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        loading = (AVLoadingIndicatorView) findViewById(R.id.avloadingIndicatorView);
        txtloading = (TextView)findViewById(R.id.txtloading);
        scroll = (ScrollView) findViewById(R.id.scroll);
        scroll.setVisibility(View.GONE);
        startAnim();

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

        progressDialog = new SpotsDialog(this, R.style.Custom);
        bookingFragPresenterImp = new BookingFragPresenterImp(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewCart);

        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvNmGuest = (TextView) findViewById(R.id.tvNmGuest);
        tvPhGuest = (TextView) findViewById(R.id.tvPhGuest);
        tvPeople = (TextView) findViewById(R.id.tvPeople);
        tvNmOutlet = (TextView) findViewById(R.id.tvNmOutlet);
       // tvAddressOutlet = (TextView) findViewById(R.id.tvAddressOutlet);
        tvDates = (TextView) findViewById(R.id.tvDates);
        tvTimes = (TextView) findViewById(R.id.tvTimes);
        tvTotal = (TextView) findViewById(R.id.tvTotal);

        btnTerima = (Button) findViewById(R.id.btnTerimaReservasi);
        btnTolak = (Button) findViewById(R.id.btnTolakReservasi);
        btnCancel = (Button) findViewById(R.id.btnBatalkanReservasi);

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

     //   Toast.makeText(this, "adapter "+strIdBooking, Toast.LENGTH_SHORT).show();


        if(strStatus.equals("waiting")){
            tvStatus.setText("Menunggu konfirmasi Anda");
            tvStatus.setTextColor(getResources().getColor(R.color.gold));
            btnTerima.setVisibility(View.VISIBLE);
            btnTolak.setVisibility(View.VISIBLE);
            btnCancel.setVisibility(View.GONE);
        } else
        if(strStatus.equals("approved")){
            tvStatus.setText("Anda telah menerima reservasi ini");
            tvStatus.setTextColor(getResources().getColor(R.color.open));
            btnTerima.setVisibility(View.GONE);
            btnTolak.setVisibility(View.GONE);
            btnCancel.setVisibility(View.VISIBLE);
        } else
        if(strStatus.equals("declined")){
            tvStatus.setText("Anda menolak reservasi ini");
            tvStatus.setTextColor(getResources().getColor(R.color.closed));
            btnTerima.setVisibility(View.GONE);
            btnTolak.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
        } else
        if(strStatus.equals("canceled")){
            tvStatus.setText("Pemesan membatalkan reservasi ini");
            tvStatus.setTextColor(getResources().getColor(R.color.secondaryText2));
            btnTerima.setVisibility(View.GONE);
            btnTolak.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
        } else
        if(strStatus.equals("dropped")){
            tvStatus.setText("Anda membatalkan reservasi ini");
            tvStatus.setTextColor(getResources().getColor(R.color.closed));
            btnTerima.setVisibility(View.GONE);
            btnTolak.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
        }

        tvNmOutlet.setText(strNmStore);
        tvNmGuest.setText(strNmGuest);
        tvPhGuest.setText("  "+strPhGuest);
        tvPhGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(DetailReservationItem.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Telpon sekarang?")
                        .setContentText("Anda akan melakukan panggilan ke\n" + strNmStore)
                        .setConfirmText("Ya")
                        .setCancelText("Nanti saja")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        makePhoneCall();
                        sweetAlertDialog.dismiss();
                    }
                }).show();
            }
        });
        tvPeople.setText(strPeople);

        tvDates.setText(strDates);
        tvTimes.setText(strTimes);

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        String hargaFormatted = String.valueOf(strTotal);

        bookingFragPresenterImp.showMyReservationDetail(strIdBooking);

        tvTotal.setText(formatRupiah.format((double)Integer.parseInt(hargaFormatted)));

        btnTerima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingFragPresenterImp.approveRes(strIdBooking);
            }
        });

        btnTolak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingFragPresenterImp.declineRes(strIdBooking);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(DetailReservationItem.this, SweetAlertDialog.WARNING_TYPE)
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
                        bookingFragPresenterImp.dropRes(strIdBooking);
                        sweetAlertDialog.dismiss();
                    }
                })
                        .show();

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

    private void makePhoneCall() {
        String number = strPhGuest;
        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                String dial = "tel:" + number;
                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
            }

        } else {
            Toast.makeText(this, "Nomor tidak tersedia", Toast.LENGTH_SHORT).show();
        }
    }

    public void generateBookingDetailResponse(ArrayList<BookingDetail> reservationDetailArrayList){
        adapter = new MyDetailReservationAdapter(reservationDetailArrayList, this);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        recyclerView.setAdapter(adapter);
    }

    public void reqApproved(){
        Toast.makeText(this, "Reservasi diterima", Toast.LENGTH_SHORT).show();
        tvStatus.setText("Anda telah menerima reservasi ini");
        tvStatus.setTextColor(getResources().getColor(R.color.open));
        btnTerima.setVisibility(View.GONE);
        btnTolak.setVisibility(View.GONE);
        btnCancel.setVisibility(View.VISIBLE);
    }

    public void reqDeclined(){
        Toast.makeText(this, "Reservasi ditolak", Toast.LENGTH_SHORT).show();
        tvStatus.setText("Anda menolak reservasi ini");
        tvStatus.setTextColor(getResources().getColor(R.color.closed));
        btnTerima.setVisibility(View.GONE);
        btnTolak.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);
    }

    public void reqDropped(){
        Toast.makeText(this, "Reservasi dibatalkan", Toast.LENGTH_SHORT).show();
        tvStatus.setText("Anda membatalkan reservasi ini");
        tvStatus.setTextColor(getResources().getColor(R.color.closed));
        btnTerima.setVisibility(View.GONE);
        btnTolak.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);
    }

    public void errorAction(){
        Toast.makeText(this, "Telah terjadi kesalahan. Coba ulangi lagi nanti", Toast.LENGTH_SHORT).show();
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
      //  Toast.makeText(this, "Masuk terlebih dahulu", Toast.LENGTH_SHORT).show();
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
