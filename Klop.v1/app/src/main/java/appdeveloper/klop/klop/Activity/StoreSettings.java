package appdeveloper.klop.klop.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import appdeveloper.klop.klop.Model.BookingResponse;
import appdeveloper.klop.klop.Model.ReviewResponse;
import appdeveloper.klop.klop.Model.Store;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.StoreSettingsPresenterImp;
import appdeveloper.klop.klop.R;
import cn.pedant.SweetAlert.SweetAlertDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class StoreSettings extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;
    String strIdStore, strNmStore, strIdUser, strLat, strLong, strLinkLogo, strLinkBanner;
    SessionPreference session;
    HashMap<String, String> userdata;
    TextView tvStatusPub, tvHint, tvNmStore, tvPhStore, tvAddress, tvUbahUmum, tvUbahLogo, tvUbahFotoSampul, tvKelKat, tvKelFas, tvKelJad, tvKelFoto, tvKelTr, tvKelPromo, tvReport, tvDeleteOutlet;
    private static final int COMMON_INFO = 0;
    Bundle b;
    StoreSettingsPresenterImp storeSettingsPresenterImp;

    ArrayList<String> strDaysArraylist = new ArrayList<String>();
    ArrayList<String> strTimeOpenArraylist = new ArrayList<String>();
    ArrayList<String> strTimeClosedArraylist = new ArrayList<String>();

    private Uri fileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_READ_STORAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "Klop Logo";
    public static int COUNT_CAMERA = 100;
    ImageView imgLogo, imgBanner;
    String imgTemp = "0", imgTemp2 = "0";
    private String strPhotoUrl, strEncodedFile, strPhotoUrl2, strEncodedFile2;
    private static final String TAG = "settings";
    String strRate;
    int menunggu, diterima, dibatalkan, ditolak;
    String count;
    CheckBox cbSetManual;
    Switch switchStatus;
    int btg1, btg2, btg3, btg4, btg5;
    String count2;
    String statusManual=null;

    ScrollView scroll;
    TextView txtloading;
    AVLoadingIndicatorView loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_settings);

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

        Bundle b = getIntent().getExtras();
        strIdStore = b.getString("idStore");

        storeSettingsPresenterImp = new StoreSettingsPresenterImp(this);
        storeSettingsPresenterImp.showInfoOutlet(strIdStore, -7.7746817, 110.4159096);
        storeSettingsPresenterImp.showStatisticReservation(strIdStore);
        storeSettingsPresenterImp.showStatisticRate(strIdStore);

        cbSetManual = (CheckBox) findViewById(R.id.cbSetManual);
        switchStatus = (Switch) findViewById(R.id.switchStatus);
        tvHint = (TextView) findViewById(R.id.tvHint);

        tvNmStore = (TextView) findViewById(R.id.tvNmOutlet);
        tvPhStore = (TextView) findViewById(R.id.tvTelpOutlet);
        tvAddress = (TextView) findViewById(R.id.tvAddressOutlet);
        tvUbahUmum = (TextView) findViewById(R.id.tvUbahUmum);
        tvStatusPub = (TextView) findViewById(R.id.tvStatusPub);

        tvUbahLogo = (TextView) findViewById(R.id.tvUnggahLogo);
        tvUbahFotoSampul = (TextView) findViewById(R.id.tvUnggahFotoSampul);

        imgLogo = (ImageView)findViewById(R.id.imgLogo);
        imgBanner = (ImageView)findViewById(R.id.imgBanner);

        tvKelKat = (TextView) findViewById(R.id.tvKelKat);
        tvKelFas = (TextView) findViewById(R.id.tvKelFas);
        tvKelJad = (TextView) findViewById(R.id.tvKelJad);

        tvKelFoto = (TextView) findViewById(R.id.tvKelFoto);
        tvKelTr = (TextView) findViewById(R.id.tvKelTr);
        tvKelPromo = (TextView) findViewById(R.id.tvKelPromo);

        tvReport = (TextView) findViewById(R.id.tvReport);
        tvDeleteOutlet = (TextView) findViewById(R.id.tvDelete);

        tvUbahUmum.setOnClickListener(this);
        tvUbahLogo.setOnClickListener(this);
        tvUbahFotoSampul.setOnClickListener(this);
        tvKelKat.setOnClickListener(this);
        tvKelFas.setOnClickListener(this);
        tvKelJad.setOnClickListener(this);
        tvKelFoto.setOnClickListener(this);
        tvKelTr.setOnClickListener(this);
        tvKelPromo.setOnClickListener(this);
        tvReport.setOnClickListener(this);
        tvDeleteOutlet.setOnClickListener(this);
        tvHint.setVisibility(View.GONE);

        cbSetManual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    if(switchStatus.getText().toString().equals("BUKA")){
                        statusManual = "open";
                        switchStatus.setEnabled(true);
                        switchStatus.setTextColor(getResources().getColor(R.color.open));
                        storeSettingsPresenterImp.updateManualSch(strIdStore, statusManual);

                        switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if(switchStatus.isChecked()){
                                    statusManual = "open";
                                    switchStatus.setText("BUKA");
                                    switchStatus.setTextColor(getResources().getColor(R.color.open));
                                    storeSettingsPresenterImp.updateManualSch(strIdStore, statusManual);
                                } else if(!switchStatus.isChecked()){
                                    statusManual = "closed";
                                    switchStatus.setText("TUTUP");
                                    switchStatus.setTextColor(getResources().getColor(R.color.closed));
                                    storeSettingsPresenterImp.updateManualSch(strIdStore, statusManual);
                                }
                            }
                        });


                    } else if(switchStatus.getText().toString().equals("TUTUP")){
                            statusManual = "closed";
                            switchStatus.setEnabled(true);
                            switchStatus.setTextColor(getResources().getColor(R.color.closed));
                            storeSettingsPresenterImp.updateManualSch(strIdStore, statusManual);

                            switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    if(switchStatus.isChecked()){
                                        statusManual = "open";
                                        switchStatus.setText("BUKA");
                                        switchStatus.setTextColor(getResources().getColor(R.color.open));
                                        storeSettingsPresenterImp.updateManualSch(strIdStore, statusManual);
                                    } else if(!switchStatus.isChecked()){
                                        statusManual = "closed";
                                        switchStatus.setText("TUTUP");
                                        switchStatus.setTextColor(getResources().getColor(R.color.closed));
                                        storeSettingsPresenterImp.updateManualSch(strIdStore, statusManual);
                                    }
                                }
                            });
                    }

                }else{
                    statusManual = "no";
                    switchStatus.setEnabled(false);
                    switchStatus.setTextColor(getResources().getColor(R.color.secondaryText2));
                    storeSettingsPresenterImp.updateManualSch(strIdStore, statusManual);
                }
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

    @Override
    protected void onPostResume() {
        super.onPostResume();
        storeSettingsPresenterImp.showInfoOutlet(strIdStore, 7.7746817,110.4159096);
    }

    @Override
    public void onClick(View v) {
        if(v == tvUbahUmum){
            Intent i = new Intent(StoreSettings.this, UbahInformasiUmum.class);
            i.putExtra("idStore", strIdStore);
            i.putExtra("nmStore", tvNmStore.getText().toString());
            i.putExtra("phStore", tvPhStore.getText().toString());
            i.putExtra("address", tvAddress.getText().toString());
            i.putExtra("strLat", strLat);
            i.putExtra("strLong", strLong);
            startActivity(i);

        } else
        if(v == tvUbahLogo){
            imgTemp = "logo";
            selectImage();

        } else
        if(v == tvUbahFotoSampul){
            imgTemp = "banner";
            selectImage();

        } else
        if(v == tvKelKat){
            Intent i = new Intent(StoreSettings.this, ManageCategory.class);
            Bundle b = new Bundle();
            b.putString("idStore", strIdStore);
            i.putExtras(b);
            startActivity(i);
        } else
        if(v == tvKelFas){
            Intent i = new Intent(StoreSettings.this, ManageFacility.class);
            Bundle b = new Bundle();
            b.putString("idStore", strIdStore);
            i.putExtras(b);
            startActivity(i);
        } else
        if(v == tvKelJad){
            Intent i = new Intent(StoreSettings.this, ManageSchedule.class);
            Bundle b = new Bundle();
            b.putString("idStore", strIdStore);
            i.putExtras(b);
            startActivity(i);
        } else
        if(v == tvKelFoto){
            Intent i = new Intent(StoreSettings.this, ManagePhoto.class);
            Bundle b = new Bundle();
            b.putString("idStore", strIdStore);
            i.putExtras(b);
            startActivity(i);
        } else
        if(v == tvKelTr){
            Intent i = new Intent(StoreSettings.this, ManageTreatment.class);
            Bundle b = new Bundle();
            b.putString("idStore", strIdStore);
            i.putExtras(b);
            startActivity(i);

        } else
        if(v == tvKelPromo){
            Intent i = new Intent(StoreSettings.this, ManageNews.class);
            Bundle b = new Bundle();
            b.putString("idStore", strIdStore);
            i.putExtras(b);
            startActivity(i);
        } else
        if(v == tvReport){
          //  Toast.makeText(this, "rate "+strRate+", jumlah rev "+count2+"|"+btg1+"|"+btg2+"|"+btg3+"|"+btg4+"|"+btg5, Toast.LENGTH_SHORT).show();

            Intent i = new Intent(StoreSettings.this, Report.class);
            Bundle b = new Bundle();
            b.putString("idStore", strIdStore);
            b.putString("rate", strRate);
            b.putString("count2", count2);
            b.putInt("btg1", btg1);
            b.putInt("btg2", btg2);
            b.putInt("btg3", btg3);
            b.putInt("btg4", btg4);
            b.putInt("btg5", btg5);

            b.putString("count", count);
            b.putInt("menunggu", menunggu);
            b.putInt("diterima", diterima);
            b.putInt("dibatalkan", dibatalkan);
            b.putInt("ditolak", ditolak);

            i.putExtras(b);
            startActivity(i);

        } else
        if(v == tvDeleteOutlet){
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Hapus "+tvNmStore.getText().toString())
                    .setContentText("Outlet yang sudah dihapus tidak akan bisa dikembalikan lagi \n Anda yakin ingin tetap menghapus?")
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

                    storeSettingsPresenterImp.deleteOutlet(strIdStore);
                    sweetAlertDialog.dismiss();
                }
            }).show();
        }
    }

    public void generateStoreResponse(Store store){
        tvNmStore.setText(store.getNameStore());
        tvPhStore.setText(store.getPhoneStore());
        tvAddress.setText(store.getAddressStore());
        strLat = store.getLatitude();
        strLong = store.getLongitude();
        strRate = String.format("%.01f", Float.valueOf(store.getRateSum().toString()))
                .replace(",",".")
                .replace(",0","")
                .replace(".0","")
                .trim();
        if(store.getStatusStore().equals("unverified")){
            tvStatusPub.setText("Menunggu verifikasi admin");
            tvStatusPub.setTextColor(getResources().getColor(R.color.orange));
        } else
        if(store.getStatusStore().equals("verified")){
            tvStatusPub.setText("Terverifikasi");
            tvStatusPub.setTextColor(getResources().getColor(R.color.open));
        } else
        if(store.getStatusStore().equals("rejected")){
            tvStatusPub.setText("Ditolak");
            tvStatusPub.setTextColor(getResources().getColor(R.color.closed));
        }



        try{
            strLinkLogo = store.getLogoStore().toString();
        }catch(Exception e){
            e.printStackTrace();
        }

        if(strLinkLogo != null) {
            //Toast.makeText(this, "link logo:" + linkOutletLogo, Toast.LENGTH_SHORT).show();
            Picasso.with(this).load(strLinkLogo).into(imgLogo);
        }

        try{
            strLinkBanner = store.getBanner().toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        if(strLinkBanner != null){
            //	Toast.makeText(this, "link header 2: " + linkOutletHeader, Toast.LENGTH_SHORT).show();
            Picasso.with(this).load(strLinkBanner).into(imgBanner);		}



        int intIndexDay=0;
        String open=null, closed=null;

        if(store.getIsSetmanual().equals("no")){
            for(int i=0; i<store.getSchedule().size(); i++){
                strDaysArraylist.add(store.getSchedule().get(i).getDAYS().toString());
                strTimeOpenArraylist.add(store.getSchedule().get(i).getTIMEOPEN().toString());
                strTimeClosedArraylist.add(store.getSchedule().get(i).getTIMECLOSED().toString());
            }

            if(strDaysArraylist.contains(getNameOfToday())){
                intIndexDay = getIndexDays(getNameOfToday());
                open = strTimeOpenArraylist.get(intIndexDay);
                closed = strTimeClosedArraylist.get(intIndexDay);

                if(checkTimings(getTimeNow(), open, closed) == true){
                    storeOpen();
                }else storeClosed();

            } else {
                storeClosed();
            }

        } else if(store.getIsSetmanual().equals("open")){
            storeOpenM();
        } else if(store.getIsSetmanual().equals("closed")){
            storeClosedM();
        }
    }

    private int getIndexDays(String days){
        return strDaysArraylist.indexOf(days);
    }

    public String getNameOfToday(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                return "Minggu";

            case Calendar.MONDAY:
                return "Senin";

            case Calendar.TUESDAY:
                return "Selasa";

            case Calendar.WEDNESDAY:
                return "Rabu";

            case Calendar.THURSDAY:
                return "Kamis";

            case Calendar.FRIDAY:
                return "Jumat";

            case Calendar.SATURDAY:
                return "Sabtu";
        }
        return "";
    }


    public String getTimeNow(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        return mdformat.format(calendar.getTime());
    }


    private boolean checkTimings(String timeNow, String startTime, String endTime){
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try{
            Date date1 = sdf.parse(timeNow);
            Date date2 = sdf.parse(startTime);
            Date date3 = sdf.parse(endTime);

            if(date1.after(date2) & date1.before(date3)){
                return true;
            } else{
                return false;
            }
        }catch (Exception e){e.printStackTrace();}
        return false;
    }


    private void storeOpenM(){
//        statusManual = "open";
        cbSetManual.setChecked(true);
        switchStatus.setEnabled(true);
        switchStatus.setChecked(true);
        switchStatus.setText("BUKA");
        switchStatus.setTextColor(getResources().getColor(R.color.open));
    }

    private void storeClosedM(){
//        statusManual = "closed";
        cbSetManual.setChecked(true);
        switchStatus.setEnabled(true);
        switchStatus.setChecked(false);
        switchStatus.setText("TUTUP");
        switchStatus.setTextColor(getResources().getColor(R.color.closed));
    }

    private void storeOpen(){
//        statusManual = "no";
        cbSetManual.setChecked(false);
        switchStatus.setChecked(true);
        switchStatus.setEnabled(false);
        switchStatus.setText("BUKA");
        switchStatus.setTextColor(getResources().getColor(R.color.secondaryText2));
    }

    private void storeClosed(){
//        statusManual = "no";
        cbSetManual.setChecked(false);
        switchStatus.setChecked(false);
        switchStatus.setEnabled(false);
        switchStatus.setText("TUTUP");
        switchStatus.setTextColor(getResources().getColor(R.color.secondaryText2));
    }

    public void ErrorResponse(){
        Toast.makeText(this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
    }

    public void ErrorConnectionFailed(){
        Toast.makeText(this, "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
    }

    public void updateManualSuccess(){

        //Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show();
    }

    public void showErrorInput(){
        Toast.makeText(this, "problem", Toast.LENGTH_SHORT).show();
    }

    private void selectImage() {
        final CharSequence[] items = { "Ambil foto dari pustaka", "Batal" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Ambil foto dari pustaka")) {

                    if (ContextCompat.checkSelfPermission(StoreSettings.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(StoreSettings.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
                    } else {
                        openGallery();
                    }

                    // startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
                } else if (items[item].equals("Batal")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private void openGallery(){
        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    private File getOutputMediaFile(int type) {
        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Create a media file name
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_store" + strIdStore +"_photo" + ".jpg");
        } else {
            return null;
        }
        MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStorageDirectory().toString()},
                null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (RESULT_LOAD_IMAGE): {
                //   Toast.makeText(this, "onActivityResult1", Toast.LENGTH_SHORT).show();
                if (resultCode == Activity.RESULT_OK) {
                    {
                        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            try {
                                Uri selectedImageUri = data.getData();
                                Random rand = new Random();
                                int n = rand.nextInt(1000);

                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                //   ((BitmapDrawable) imgPhotoprof.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                                String[] projection = {MediaStore.MediaColumns.DATA};

                                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null,
                                        null);
                                Cursor cursor = cursorLoader.loadInBackground();
                                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                                cursor.moveToFirst();

                                String selectedImagePath = cursor.getString(column_index);

                                Bitmap bm = BitmapFactory.decodeFile(selectedImagePath);
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                                byte[] b = baos.toByteArray();

                                if (String.valueOf(imgTemp).equalsIgnoreCase("logo")) {
                                    strPhotoUrl = "logo" + n + ".jpg";
                                    strEncodedFile = Base64.encodeToString(b, Base64.NO_WRAP);
                                    imgLogo.setImageURI(selectedImageUri);
                                    storeSettingsPresenterImp.addLogo(strIdStore, strPhotoUrl, strEncodedFile);
                                     imgTemp = "0";
                                } else {
                                }
                                if (String.valueOf(imgTemp).equalsIgnoreCase("banner")) {
                                    strPhotoUrl2 = "review" + n + ".jpg";
                                    strEncodedFile2 = Base64.encodeToString(b, Base64.NO_WRAP);
                                    imgBanner.setImageURI(selectedImageUri);
                                    storeSettingsPresenterImp.addBanner(strIdStore, strPhotoUrl2, strEncodedFile2);
                                     imgTemp2 ="0";
                                } else {
                                }
                                //  Toast.makeText(this.getContext(), strAvatar +" 2 "+ strEncodedfile, Toast.LENGTH_SHORT).show();
                                Log.i(TAG, "encoded image :" + strEncodedFile);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            EasyPermissions.requestPermissions(this, "Access for storage",
                                    101, galleryPermissions);

                        }
                    }
                }

            }
        }
    }

    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    public void deleteSuccess(){
        Toast.makeText(this, "Outlet berhasil dihapus", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(StoreSettings.this, MyStore.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void ErrorDelete(){
        Toast.makeText(this, "Gagal menghapus outlet", Toast.LENGTH_SHORT).show();
    }

    public void addingLogoSuccess(){
        Toast.makeText(this, "Logo berhasil diubah", Toast.LENGTH_SHORT).show();
    }

    public void addingLogoFailed(){
        Toast.makeText(this, "Logo gagal diubah", Toast.LENGTH_SHORT).show();
    }

    public void ErrorResponseFailed() {
        Toast.makeText(this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
    }

    public void addingBannerFailed(){
        Toast.makeText(this, "Foto sampul gagal diubah", Toast.LENGTH_SHORT).show();
    }

    public void addingBannerSuccess(){
        Toast.makeText(this, "Foto sampul berhasil diubah", Toast.LENGTH_SHORT).show();
    }

    public void generateReservationResponse(BookingResponse bookingResponse){
        //Toast.makeText(this, bookingResponse.getBooking().get(0).getIdStore()+bookingResponse.getMenunggu()+"|"+bookingResponse.getDiterima()+"|"+bookingResponse.getDibatalkan()+"|"+bookingResponse.getDitolak(), Toast.LENGTH_SHORT).show();

        getVal(bookingResponse.getCount(), bookingResponse.getMenunggu(), bookingResponse.getDiterima(),bookingResponse.getDibatalkan(), bookingResponse.getDitolak());

    }

    public void generateReviewResponse(ReviewResponse reviewResponse){
       // Toast.makeText(this, reviewResponse.getCount()+"|"+reviewResponse.getBintang1()+"|"+reviewResponse.getBintang2()+"|"+reviewResponse.getBintang3()+"|"+reviewResponse.getBintang4()+"|"+reviewResponse.getBintang5(), Toast.LENGTH_SHORT).show();

        getVal2(reviewResponse.getCount(), reviewResponse.getBintang1(), reviewResponse.getBintang2(), reviewResponse.getBintang3(), reviewResponse.getBintang4(), reviewResponse.getBintang5());

    }

    public void getVal(String counts, int number1, int number2, int number3, int number4){
        if(!counts.equals(null)) {
            count = counts;
            menunggu = number1;
            diterima = number2;
            dibatalkan = number3;
            ditolak = number4;
        } else {
            count = "0";
            menunggu = 0;
            diterima = 0;
            dibatalkan = 0;
            ditolak = 0;
        }
    }

    public void getVal2(String counts, int btg01, int btg02, int btg03, int btg04, int btg05){
        if(!counts.equals(null)){
            count2 = counts;
            btg1 = btg01;
            btg2 = btg02;
            btg3 = btg03;
            btg4 = btg04;
            btg5 = btg05;
        } else {
            count2 = "0";
            btg1 = 0;
            btg2 = 0;
            btg3 = 0;
            btg4 = 0;
            btg5 = 0;
        }

    }


    public void ErrorConnection() {
        Toast.makeText(this, "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
//        mWaveSwipeRefreshLayout.setRefreshing(false);

    }

    public void warningDataNotFound(){
       //  Toast.makeText(this, "Oops, data REV tidak ditemukan!", Toast.LENGTH_SHORT).show();
        //  mWaveSwipeRefreshLayout.setRefreshing(false);

    }

    public void loginTimeOut(){
    //    Toast.makeText(this, "login dahulu", Toast.LENGTH_SHORT).show();
        session.logoutUser();
    }

}
