package appdeveloper.klop.klop.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import appdeveloper.klop.klop.Other.TouchImageView;
import appdeveloper.klop.klop.PresenterImp.StoreSettingsPresenterImp;
import appdeveloper.klop.klop.R;
import cn.pedant.SweetAlert.SweetAlertDialog;
import es.dmoral.toasty.Toasty;

public class FullScreenMngPhoto extends AppCompatActivity {

    Toolbar toolbar;
    String strIdStore, id, name, url, created;
    TouchImageView touchImageView;
    TextView tvAdder, tvWaktu;
    StoreSettingsPresenterImp storeSettingsPresenterImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_mng_photo);
        initView();
    }

    public void initView() {
        Bundle bundle = getIntent().getExtras();
        strIdStore = bundle.getString("idstore");
        id = bundle.getString("id");
        url = bundle.getString("url");
        name = bundle.getString("name");
        created = bundle.getString("created");

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

        storeSettingsPresenterImp = new StoreSettingsPresenterImp(this);

        tvAdder = (TextView) findViewById(R.id.tvAdder);
        tvWaktu = (TextView) findViewById(R.id.tvWaktu);

        tvAdder.setText(name);
        tvWaktu.setText(parseDateFormatted(created));

        touchImageView = (TouchImageView) findViewById(R.id.img_full);

        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.placeholder)
                .crossFade()
                .into(touchImageView);
    }

    public String parseDateFormatted(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        super.onCreateOptionsMenu(menu);
        return  true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_delete_photo:
             //   Toast.makeText(this, "hapus id "+id, Toast.LENGTH_SHORT).show();
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Hapus foto")
                        .setContentText("Foto ini akan dihapus")
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

                        storeSettingsPresenterImp.deletePhoto(id);
                        sweetAlertDialog.dismiss();
                    }
                }).show();

                return true;

            case R.id.action_banner:
              //  Toast.makeText(this, "ganti id "+id, Toast.LENGTH_SHORT).show();
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Konfirmasi")
                        .setContentText("Jadikan foto ini sebagai foto sampul outlet?")
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

                        storeSettingsPresenterImp.useAsBanner(strIdStore, url);
                        sweetAlertDialog.dismiss();
                    }
                }).show();

                return true;

        default:
        return super.onOptionsItemSelected(item);
    }
    }

    public void updateBannerSuccess(){
        Toast.makeText(FullScreenMngPhoto.this, "Foto sampul diubah", Toast.LENGTH_SHORT).show();
//        Toasty.success(FullScreenMngPhoto.this, "Foto sampul diubah", Toast.LENGTH_SHORT, true).show();

    }

    public void errorUpdateBanner(){
        Toast.makeText(FullScreenMngPhoto.this, "Foto sampul gagal diubah", Toast.LENGTH_SHORT).show();
      //  Toasty.error(FullScreenMngPhoto.this, "Foto sampul gagal diubah", Toast.LENGTH_SHORT, true).show();

    }

    public void ErrorResponseFailed(){
        Toast.makeText(FullScreenMngPhoto.this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
      //  Toasty.error(FullScreenMngPhoto.this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT, true).show();

    }
    public void ErrorConnectionFailed(){
        Toast.makeText(FullScreenMngPhoto.this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
    //    Toasty.error(FullScreenMngPhoto.this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT, true).show();

    }

    public void deleteSuccess(){
        Toast.makeText(FullScreenMngPhoto.this, "Foto dihapus", Toast.LENGTH_SHORT).show();
       // Toasty.success(FullScreenMngPhoto.this, "Foto dihapus", Toast.LENGTH_SHORT, true).show();
        onBackPressed();

    }


    public void deleteFailed(){
        Toasty.error(FullScreenMngPhoto.this, "Foto gagal dihapus", Toast.LENGTH_SHORT, true).show();

    }

}
