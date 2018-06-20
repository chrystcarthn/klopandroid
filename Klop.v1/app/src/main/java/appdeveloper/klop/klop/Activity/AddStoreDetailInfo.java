package appdeveloper.klop.klop.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

import appdeveloper.klop.klop.Fragment.CategoryFragment;
import appdeveloper.klop.klop.Fragment.FacilityFragment;
import appdeveloper.klop.klop.Fragment.TimePickerFragment;
import appdeveloper.klop.klop.Model.Store;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.StoreDetailPresenterImp;
import appdeveloper.klop.klop.PresenterImp.StorePresenterImp;
import appdeveloper.klop.klop.R;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import pub.devrel.easypermissions.EasyPermissions;

import static cn.pedant.SweetAlert.SweetAlertDialog.*;

/**
 * Created by CMDDJ on 4/23/2018.
 */

public class AddStoreDetailInfo extends AppCompatActivity implements CategoryFragment.OnHeadlineSelectedListener,
                                                                    FacilityFragment.OnHeadlineSelectedListener,
                                                                    AdapterView.OnItemSelectedListener,
                                                                    View.OnClickListener, TimePickerDialog.OnTimeSetListener {
    int initAddStore=0;
    SessionPreference session;
    HashMap<String, String> userdata;
    String flagAturJadwal = "0";
    Button btnLanjutkan;
    int flagAll1=0, flagAll2=0;
    TextView tvKat, tvFas, tvErrorSchedule, expandableButton0, tvHari, tvJam, tvErrorLogo, tvErrorBanner;
    EditText edNmKatTerpilih, edNmFasTerpilih, edAll1, edAll2, edTes;
    CheckBox cbAll;
    String h1=null, h2=null, h3=null, h4=null, h5=null, h6=null, h7=null;
    String b1=null, b2=null, b3=null, b4=null, b5=null, b6=null, b7=null;
    String t1=null, t2=null, t3=null, t4=null, t5=null, t6=null, t7=null;

    ImageView imgLogo, imgPhotoRev;
    TextInputLayout categoryWrapper, facilityWrapper;
    String strIdStore, strIdUser, strTempTimeOpen, strTempTimeClosed, getHour, getMinutes;
    String imgTemp = "0";
    String imgTemp2 = "0";

    ExpandableRelativeLayout expandableLayout, expHariJam;
    StoreDetailPresenterImp storeDetailPresenterImp;
    StorePresenterImp storePresenterImp;

    ArrayList<String> strDaysArraylist = new ArrayList<String>();
    ArrayList<String> strTimeOpenArraylist = new ArrayList<String>();
    ArrayList<String> strTimeClosedArraylist = new ArrayList<String>();

    private String strPhotoUrl, strEncodedFile, strPhotoUrl2, strEncodedFile2;
    GalleryPhoto galleryPhoto;
    private Uri fileUri;
    private static String DOING_LOGO = "0";
    private static String DOING_REV = "0";
    private static final int PICK_SCHEDULE = 2;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_READ_STORAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "Klop Logo";
    public static int COUNT_CAMERA = 100;
    String strName, strAlamat, strPhone, strLatO, strLongO, strFmtDistance, choosenHour, choosenMinutes;
    private SpotsDialog progressDialog;
    private static final String TAG = "addDetailStore";

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi_detil);

        session = new SessionPreference(this);
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        //  strIdUser, strName, strAlamat, strPhone, strLatO, strLongO, strFmtDistance
        Bundle b = getIntent().getExtras();
        strName = b.getString("strName");
        strAlamat = b.getString("strAlamat");
        strPhone = b.getString("strPhone");
        strLatO = b.getString("strLatO");
        strLongO = b.getString("strLongO");
        strFmtDistance = b.getString("strFmtDistance");

        toolbar = (Toolbar) findViewById(R.id.toolbar1);

     //   Toast.makeText(this, "Init flagAturJadwal di onCreate :"+ flagAturJadwal, Toast.LENGTH_SHORT).show();

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
        storeDetailPresenterImp = new StoreDetailPresenterImp(this);
        storePresenterImp = new StorePresenterImp(this);

        expandableButton0 = (TextView)findViewById(R.id.expandableButton0);

        expandableLayout = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout);
        expHariJam = (ExpandableRelativeLayout) findViewById(R.id.expHariJam);

        galleryPhoto = new GalleryPhoto(this);
        imgLogo = (ImageView)findViewById(R.id.imgLogo);
        imgPhotoRev = (ImageView)findViewById(R.id.imgPhotoRev);

        categoryWrapper = (TextInputLayout) findViewById(R.id.input_layout_kategori);
        facilityWrapper = (TextInputLayout) findViewById(R.id.input_layout_fasilitas);


        btnLanjutkan = (Button) findViewById(R.id.btnDataDetilLanjutkan);

        tvKat  = (TextView) findViewById(R.id.tvKatTerpilih);
        tvFas = (TextView) findViewById(R.id.tvFasTerpilih);
        tvErrorSchedule = (TextView) findViewById(R.id.tvErrorSchedule);
        tvHari = (TextView) findViewById(R.id.tvHari);
        tvJam = (TextView) findViewById(R.id.tvJam);
        tvErrorLogo = (TextView) findViewById(R.id.tvErrorLogo);
        tvErrorBanner = (TextView) findViewById(R.id.tvErrorBanner);

        edNmKatTerpilih = (EditText) findViewById(R.id.edKategoriTerpilih);
        edNmFasTerpilih = (EditText) findViewById(R.id.edFasilitasTerpilih);
        edAll1 = (EditText) findViewById(R.id.edAll1);
        edAll2 = (EditText) findViewById(R.id.edAll2);

        cbAll = (CheckBox) findViewById(R.id.cbAll);

        edAll1.setEnabled(false);
        edAll2.setEnabled(false);
        expandableLayout.setExpanded(true);
        edAll1.setTextColor(getResources().getColor(R.color.secondaryText2));
        edAll2.setTextColor(getResources().getColor(R.color.secondaryText2));

        final FragmentManager fm = getFragmentManager();
        final CategoryFragment cf = new CategoryFragment();
        final FacilityFragment ff = new FacilityFragment();
        cf.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        ff.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);


        cbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    edAll1.setEnabled(true);
                    edAll1.setTextColor(getResources().getColor(R.color.primaryText));
                    edAll2.setEnabled(true);
                    edAll2.setTextColor(getResources().getColor(R.color.primaryText));
                    expandableLayout.toggle(); // toggle expand and collapse
                }else{
                    edAll1.setText("00:00");
                    edAll1.setEnabled(false);
                    edAll1.setTextColor(getResources().getColor(R.color.secondaryText2));

                    edAll2.setText("00:00");
                    edAll2.setEnabled(false);
                    edAll2.setTextColor(getResources().getColor(R.color.secondaryText2));
                    expandableLayout.toggle(); // toggle expand and collapse
                }
            }
        });


        expandableButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddStoreDetailInfo.this, PickSchedule.class);
              //  Bundle bundle = new Bundle();
                i.putExtra("flagAturJadwal", flagAturJadwal);
               // Toast.makeText(AddStoreDetailInfo.this, "Kirim flagAturJadwal ke PickSchedule :"+ flagAturJadwal, Toast.LENGTH_SHORT).show();
                startActivityForResult(i, PICK_SCHEDULE);
            }
        });

        edNmKatTerpilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cf.show(fm, "CF_TAG");
            }
        });
        edNmFasTerpilih.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ff.show(fm, "FF_TAG");
            }
        });

        edAll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagAll1=1;
                flagAll2=0;
                String hour = edAll1.getText().toString().substring(0,2);
                String minute = edAll1.getText().toString().substring(3,5);
                showTime(hour, minute);

            }
        });
        edAll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flagAll1=0;
                flagAll2=1;
                String hour = edAll2.getText().toString().substring(0,2);
                String minute = edAll2.getText().toString().substring(3,5);
                showTime(hour, minute);
            }
        });

        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTemp = "logo";
                selectImage();
            }
        });

        imgPhotoRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgTemp = "rev";
                selectImage();
            }
        });
        btnLanjutkan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showSpotLoading();
                validatingScheduleSelected();
            }
        });
    }


    @Override
    protected void onRestart() {
     //   Toast.makeText(this, tvFas.getText().toString()+tvKat.getText().toString(), Toast.LENGTH_SHORT).show();

        cbAll.setChecked(false);
        expandableLayout.setExpanded(true);
        edAll1.setEnabled(false);
        edAll2.setEnabled(false);
        edAll1.setText("00:00");
        edAll2.setText("00:00");

        try{
        }catch (Exception e){
            e.printStackTrace();
        }
      //  Toast.makeText(this, "reStart, facility: "+tvFas.getText().toString()+"& category: "+tvKat.getText().toString(), Toast.LENGTH_SHORT).show();
        super.onRestart();
    }

    public void showTime(String hour, String minute) {
        Date date = null;

        DateFormat formatter = new SimpleDateFormat("HH:mm");
        try {
            date = formatter.parse(hour+":"+minute);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    //    Toast.makeText(this, hour+":"+minute, Toast.LENGTH_SHORT).show();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        TimePickerDialog tpd = TimePickerDialog.newInstance(
                AddStoreDetailInfo.this,
                Integer.parseInt(hour),
                Integer.parseInt(minute),
                true);

        tpd.setThemeDark(false);
        tpd.vibrate(false);
        tpd.setTitle("Atur Jam");
        tpd.dismissOnPause(false);
        tpd.enableSeconds(false);
        tpd.enableMinutes(true);
        if (true) {
            tpd.setAccentColor(getResources().getColor(R.color.colorPrimaryDarker));
        }

        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        getHour = String.valueOf(hourOfDay);
        getMinutes = String.valueOf(minute);

        if(getHour.length() == 1){
            getHour = "0" + getHour;
        }

        if(getMinutes.length() == 1){
            getMinutes = "0" + getMinutes;
        }


        if(flagAll1 == 1) {
            if(!isAll1Valid(getHour)){
                showTime(getHour, getMinutes);
                Toast.makeText(this, "Harap memasukan jam buka di bawah jam tutup", Toast.LENGTH_SHORT).show();
            } else
                edAll1.setText(getHour + ":" + getMinutes);
        }
        else if(flagAll2 == 1){
            if(!isAll2Valid(getHour)){
                showTime(getHour, getMinutes);
                Toast.makeText(this, "Harap memasukan jam tutup di atas jam buka", Toast.LENGTH_SHORT).show();
            }
            else edAll2.setText(getHour + ":" + getMinutes);
        }
    }


    public boolean isAll1Valid(String getHours){
        String trimClose = edAll2.getText().toString().substring(0,2);
        int jamTutup = Integer.parseInt(trimClose);
        int jamBuka = Integer.parseInt(getHours);

        if(jamTutup == 0){
            return true;
        }
        else{
            if( jamBuka > jamTutup ){
                return false;
            }
            else return true;
        }
    }


    public boolean isAll2Valid(String getHours){
        String trimOpen = edAll1.getText().toString().substring(0,2);
        int jamBuka = Integer.parseInt(trimOpen);
        int jamTutup = Integer.parseInt(getHours);

        if( jamTutup < jamBuka ){
            return false;
        }
        else return true;
    }


    public void initArray(){
        strDaysArraylist.clear();
        strTimeOpenArraylist.clear();
        strTimeClosedArraylist.clear();
    }

    public void dismissSpotLoading(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void showSpotLoading(){
        progressDialog.show();
        progressDialog.setCancelable(true);
    }




    @Override
    public void onClick(View v) {

    }

    public void openEveryDay(){
        String open = edAll1.getText().toString();
        String close = edAll2.getText().toString();

        if(open.equals("00:00") || close.equals("00:00")){
            tvErrorSchedule.setVisibility(View.VISIBLE);
            dismissSpotLoading();
        }
        else{
            String[] everyDay = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};
            String[] openArray = {open, open, open, open, open, open, open};
            String[] closeArray = {close, close, close, close, close, close, close};
            strDaysArraylist.addAll(Arrays.asList(everyDay));
            strTimeOpenArraylist.addAll(Arrays.asList(openArray));
            strTimeClosedArraylist.addAll(Arrays.asList(closeArray));
        }
    }


    public void getAturJadwalSendiri(){
        if(!b1.equals("00:00") && !t1.equals("00:00")){
            strDaysArraylist.add(h1);
            strTimeOpenArraylist.add(b1);
            strTimeClosedArraylist.add(t1);
        } else  dismissSpotLoading();
        if(!b2.equals("00:00") && !t2.equals("00:00")){
            strDaysArraylist.add(h2);
            strTimeOpenArraylist.add(b2);
            strTimeClosedArraylist.add(t2);
        } else  dismissSpotLoading();
        if(!b3.equals("00:00") && !t3.equals("00:00")){
            strDaysArraylist.add(h3);
            strTimeOpenArraylist.add(b3);
            strTimeClosedArraylist.add(t3);
        } else  dismissSpotLoading();
        if(!b4.equals("00:00") && !t4.equals("00:00")){
            strDaysArraylist.add(h4);
            strTimeOpenArraylist.add(b4);
            strTimeClosedArraylist.add(t4);
        } else  dismissSpotLoading();
        if(!b5.equals("00:00") && !t5.equals("00:00")){
            strDaysArraylist.add(h5);
            strTimeOpenArraylist.add(b5);
            strTimeClosedArraylist.add(t5);
        } else  dismissSpotLoading();
        if(!b6.equals("00:00") && !t6.equals("00:00")){
            strDaysArraylist.add(h6);
            strTimeOpenArraylist.add(b6);
            strTimeClosedArraylist.add(t6);
        } else  dismissSpotLoading();
        if(!b7.equals("00:00") && !t7.equals("00:00")){
            strDaysArraylist.add(h7);
            strTimeOpenArraylist.add(b7);
            strTimeClosedArraylist.add(t7);
        } else  dismissSpotLoading();
    }

      public void validatingScheduleSelected(){
        if(cbAll.isChecked()){

            initArray();
            openEveryDay();
//            Toast.makeText(AddStoreDetailInfo.this,"arrayList : " +String.valueOf(strDaysArraylist) + " | "  +String.valueOf(strTimeOpenArraylist) + " & " +  String.valueOf(strTimeClosedArraylist), Toast.LENGTH_SHORT).show();
//            Toast.makeText(AddStoreDetailInfo.this,"arrayList : " +String.valueOf(strDaysArraylist) + " | "  +String.valueOf(strTimeOpenArraylist) + " & " +  String.valueOf(strTimeClosedArraylist), Toast.LENGTH_SHORT).show();

            doAddStore();
        }
        else {
            if(flagAturJadwal.equals("1")){
                tvErrorSchedule.setVisibility(View.GONE);

//               Toast.makeText(AddStoreDetailInfo.this,"arrayList : " +String.valueOf(strDaysArraylist) + " | "  +String.valueOf(strTimeOpenArraylist) + " & " +  String.valueOf(strTimeClosedArraylist), Toast.LENGTH_SHORT).show();
//               Toast.makeText(AddStoreDetailInfo.this,"arrayList : " +String.valueOf(strDaysArraylist) + " | "  +String.valueOf(strTimeOpenArraylist) + " & " +  String.valueOf(strTimeClosedArraylist), Toast.LENGTH_SHORT).show();

                doAddStore();
            }
            else {
                tvErrorSchedule.setVisibility(View.VISIBLE);
                dismissSpotLoading();
            }
        }
    }

    public void doAddStore(){
//        Toast.makeText(AddStoreDetailInfo.this, "kate : "+tvKat.getText().toString() + ", fas : "+ tvFas.getText().toString(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(AddStoreDetailInfo.this, "size : "+strDaysArraylist.size() + " | " + strTimeOpenArraylist.size() + " & " +strTimeClosedArraylist.size(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(AddStoreDetailInfo.this,"arrayList : " +String.valueOf(strDaysArraylist) + " | "  +String.valueOf(strTimeOpenArraylist) + " & " +  String.valueOf(strTimeClosedArraylist), Toast.LENGTH_SHORT).show();
//        Toast.makeText(AddStoreDetailInfo.this,"arrayList : " +String.valueOf(strDaysArraylist) + " | "  +String.valueOf(strTimeOpenArraylist) + " & " +  String.valueOf(strTimeClosedArraylist), Toast.LENGTH_SHORT).show();

        if(storeDetailPresenterImp.valCategory(strIdStore, tvKat.getText().toString()) == true){
            if(storeDetailPresenterImp.valFacility(strIdStore, tvFas.getText().toString()) == true){
                if(storeDetailPresenterImp.valSchedule(strDaysArraylist, strTimeOpenArraylist, strTimeClosedArraylist, strIdStore) == false){
                    if(storeDetailPresenterImp.valLogo(strIdStore, strPhotoUrl,strEncodedFile) == true){
                        if(storeDetailPresenterImp.valPhotoReview(strIdStore, strPhotoUrl2, strEncodedFile2, strIdUser) == true){

                            storePresenterImp.addInformasiUmum(strIdUser, strName, strAlamat, strPhone, strLatO, strLongO, strFmtDistance);


                        }else{
                            initArray();

                             // Toast.makeText(AddStoreDetailInfo.this, "rev yg false", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        initArray();

                       //   Toast.makeText(AddStoreDetailInfo.this, "logo yg false", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    initArray();
                 //    Toast.makeText(AddStoreDetailInfo.this, "schedule yg true", Toast.LENGTH_SHORT).show();
                }
            }else{
                initArray();
               //  Toast.makeText(AddStoreDetailInfo.this, "fas yg false", Toast.LENGTH_SHORT).show();
            }
        }else{
            initArray();
           //  Toast.makeText(AddStoreDetailInfo.this, "kategori yg false", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onItemSelected
            (AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " +
                        parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void selectImage() {
        final CharSequence[] items = { "Ambil foto dari pustaka", "Batal" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Ambil foto dari pustaka")) {

                    if (ContextCompat.checkSelfPermission(AddStoreDetailInfo.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddStoreDetailInfo.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
                    } else {
                        openGallery();
                    }

                } else if (items[item].equals("Batal")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void openGallery(){
        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
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

    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case(RESULT_LOAD_IMAGE):{
             //   Toast.makeText(this, "onActivityResult1", Toast.LENGTH_SHORT).show();
                if(resultCode == Activity.RESULT_OK){
                    {
                        if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE,  Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            try{
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


                                // strEncodedFile = Base64.encodeToString(b, Base64.NO_WRAP);

                                if(String.valueOf(imgTemp).equalsIgnoreCase("logo")){
                                    strPhotoUrl = "logo" + n + ".jpg";
                                    strEncodedFile = Base64.encodeToString(b, Base64.NO_WRAP);
                                    imgLogo.setImageURI(selectedImageUri);
                                    // imgTemp="0";
                                }else{}

                                if(String.valueOf(imgTemp).equalsIgnoreCase("rev")){
                                    strPhotoUrl2 = "review" + n + ".jpg";
                                    strEncodedFile2 = Base64.encodeToString(b, Base64.NO_WRAP);
                                    imgPhotoRev.setImageURI(selectedImageUri);
                                    // imgTemp="0";
                                }else{}


                                //  Toast.makeText(this.getContext(), strAvatar +" 2 "+ strEncodedfile, Toast.LENGTH_SHORT).show();
                                Log.i(TAG, "encoded image :"+strEncodedFile);
                            }catch(Exception e){e.printStackTrace();}

                        } else{
                            EasyPermissions.requestPermissions(this, "Access for storage",
                                    101, galleryPermissions);

                        }
                    }
                }

            }
            case(PICK_SCHEDULE):{
                if(resultCode == Activity.RESULT_OK){
                 //   Toast.makeText(this, "onActivityResult2", Toast.LENGTH_SHORT).show();
                    h1 = data.getStringExtra("h1");
                    h2 = data.getStringExtra("h2");
                    h3 = data.getStringExtra("h3");
                    h4 = data.getStringExtra("h4");
                    h5 = data.getStringExtra("h5");
                    h6 = data.getStringExtra("h6");
                    h7 = data.getStringExtra("h7");

                    b1 = data.getStringExtra("b1");
                    b2 = data.getStringExtra("b2");
                    b3 = data.getStringExtra("b3");
                    b4 = data.getStringExtra("b4");
                    b5 = data.getStringExtra("b5");
                    b6 = data.getStringExtra("b6");
                    b7 = data.getStringExtra("b7");

                    t1 = data.getStringExtra("t1");
                    t2 = data.getStringExtra("t2");
                    t3 = data.getStringExtra("t3");
                    t4 = data.getStringExtra("t4");
                    t5 = data.getStringExtra("t5");
                    t6 = data.getStringExtra("t6");
                    t7 = data.getStringExtra("t7");

                    if(h1 != null){
                     //   Toast.makeText(this, "onRestart2"+h1+"|"+b1+"|"+t1, Toast.LENGTH_SHORT).show();
                        flagAturJadwal = "1";
                        tvErrorSchedule.setVisibility(View.GONE);
                     //   Toast.makeText(this, "OnResult"+h1+"|"+b1+"|"+t1, Toast.LENGTH_SHORT).show();
                        getAturJadwalSendiri();
//                        initArray();
//                        getAturJadwalSendiri();
//                        Toast.makeText(AddStoreDetailInfo.this,"arrayList : " +String.valueOf(strDaysArraylist) + " | "  +String.valueOf(strTimeOpenArraylist) + " & " +  String.valueOf(strTimeClosedArraylist), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(AddStoreDetailInfo.this,"arrayList : " +String.valueOf(strDaysArraylist) + " | "  +String.valueOf(strTimeOpenArraylist) + " & " +  String.valueOf(strTimeClosedArraylist), Toast.LENGTH_SHORT).show();

                    }
                    //Toast.makeText(this, "lolos", Toast.LENGTH_SHORT).show();
                  //  Toast.makeText(this, "Setting flagAturJadwal di onResult :"+ flagAturJadwal, Toast.LENGTH_SHORT).show();
                }

            }
        }
    }


    @Override
    public void categorySelected(String strIdCatTemp, String strNmCatTemp) {
        tvKat.setText(strIdCatTemp);
        edNmKatTerpilih.setText(strNmCatTemp);
//        edTes.setText(strNmCatTemp);

    }

    @Override
    public void facilitySelected(String strIdFasTemp, String strNmFasTemp) {
        tvFas.setText(strIdFasTemp);
        edNmFasTerpilih.setText(strNmFasTemp);
    }

//    =========================================== response ================================================

    public void ErrorConnectionFailed() {
        Toast.makeText(this, "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
    }

    public void addingCategoryToStoreSuccess(String strIdOutlet){
      //  Toast.makeText(this, "Category added to store", Toast.LENGTH_SHORT).show();
        storeDetailPresenterImp.addFacility(strIdOutlet, tvFas.getText().toString());
    }

    public void addingCategoryToStoreFailed(){
        Toast.makeText(this, "Kategori tidak valid. Harap periksa data Anda", Toast.LENGTH_SHORT).show();
    }


    public void addingFacilityToStoreSuccess(String strIdOutlet){
       // Toast.makeText(this, "Facility added to store", Toast.LENGTH_SHORT).show();
        storeDetailPresenterImp.addSchedule(strDaysArraylist, strTimeOpenArraylist, strTimeClosedArraylist, strIdOutlet);

    }

    public void addingFacilityToStoreFailed(){
        Toast.makeText(this, "Fasilitas tidak valid. Harap periksa data Anda", Toast.LENGTH_SHORT).show();
    }


    public void addingScheduleToStoreSuccess(String strIdOutlet){
       // Toast.makeText(this, "Schedule added to store", Toast.LENGTH_SHORT).show();
        storeDetailPresenterImp.addLogo(strIdOutlet, strPhotoUrl, strEncodedFile);
    }

    public void addingScheduleToStoreFailed(){
        Toast.makeText(this, "Jadwal tidak valid. Harap periksa data Anda", Toast.LENGTH_SHORT).show();
    }


    public void addingLogoSuccess(String strIdOutlet){
        tvErrorLogo.setVisibility(View.GONE);
      //  Toast.makeText(this, "Logo added to store", Toast.LENGTH_SHORT).show();
        storeDetailPresenterImp.addBanner(strIdOutlet, strPhotoUrl2, strEncodedFile2);
    }

    public void addingLogoFailed(){
        Toast.makeText(this, "Logo tidak valid. Harap periksa data Anda", Toast.LENGTH_SHORT).show();
    }

    public void addingBannerFailed(){
        Toast.makeText(this, "Foto sampul tidak valid. Harap periksa data Anda", Toast.LENGTH_SHORT).show();
    }


    public void addingPhotoRevSuccess(){
        tvErrorBanner.setVisibility(View.GONE);
      //  Toast.makeText(this, "Adding photo review 1 success", Toast.LENGTH_SHORT).show();
        flagAturJadwal = "0";
     //   Toast.makeText(this, "Setting flagAturJadwal di addingSuccess :"+ flagAturJadwal, Toast.LENGTH_SHORT).show();

        imgTemp = "0"; imgTemp2 = "0";
        initArray();

        dismissSpotLoading();

        new SweetAlertDialog(AddStoreDetailInfo.this, SUCCESS_TYPE)
                .setTitleText("Hore!")
                .setContentText("Data outlet berhasil dibuat! \nAdmin akan mengecek kelengkapan data terlebih dahulu sebelum dikonfirmasi")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        new SweetAlertDialog(AddStoreDetailInfo.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Yuk, aktifkan reservasi!")
                                .setContentText("Buat menu perawatan sekarang untuk mengaktivasi layanan reservasi.\nTambah perawatan sekarang?")
                                .setConfirmText("Ya")
                                .setCancelText("Nanti saja")
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();

                                    Intent i = new Intent(AddStoreDetailInfo.this, MyStore.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(i);
                                    }
                                }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Intent i = new Intent(AddStoreDetailInfo.this, ManageTreatment.class);
                                    Bundle b = new Bundle();
                                    b.putString("idStore", strIdStore);
                                    i.putExtras(b);
                                    startActivity(i);
                                sweetAlertDialog.dismiss();
                            }
                        })
                                .show();



                    }
                })
                .show();

       // Toast.makeText(this, "Photo added to store", Toast.LENGTH_SHORT).show();
    }

    public void addingPhotoRevFailed(){
        Toast.makeText(this, "Foto tidak valid. Harap periksa data Anda", Toast.LENGTH_SHORT).show();
    }

    public void addingBannerSuccess(String strIdOutlet){
     //   Toast.makeText(this, "Adding banner success", Toast.LENGTH_SHORT).show();
        tvErrorBanner.setVisibility(View.GONE);
        storeDetailPresenterImp.addPhotoReview(strIdOutlet, strPhotoUrl2, strEncodedFile2, strIdUser);
    }


    public void ErrorResponseFailed() {
        Toast.makeText(this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
    }
//    ==================================== checking validation ============================



    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void showInvalidCategory() {
        //edEmail.setError("Invalid Email");
        facilityWrapper.setErrorEnabled(false);
        tvErrorSchedule.setVisibility(View.GONE);
        categoryWrapper.setError("Harap memilih kategori");
        requestFocus(edNmKatTerpilih);
    }

    public void showInvalidFacility() {
        //edEmail.setError("Invalid Email");
        categoryWrapper.setErrorEnabled(false);
        tvErrorSchedule.setVisibility(View.GONE);
        facilityWrapper.setError("Harap memilih fasilitas");
        requestFocus(edNmKatTerpilih);
    }

    public void showInvalidPhotoR(){
        tvErrorBanner.setVisibility(View.VISIBLE);
        //Toast.makeText(this, "Harap memilih foto sampul", Toast.LENGTH_SHORT).show();
    }

    public void showInvalidLogo(){
        tvErrorLogo.setVisibility(View.VISIBLE);
       // Toast.makeText(this, "Harap memilih logo", Toast.LENGTH_SHORT).show();
    }

    public void askToLogin(){
        Toast.makeText(this, "Login dahulu", Toast.LENGTH_SHORT).show();
        session.logoutUser();
    }

    public void askToAddStore(){
        Toast.makeText(this, "Tambahkan dahulu data umum outlet", Toast.LENGTH_SHORT).show();

    }

    public void showInvalidSchedule() {
        facilityWrapper.setErrorEnabled(false);
        categoryWrapper.setErrorEnabled(false);
        tvErrorSchedule.setVisibility(View.VISIBLE);
    }

    public void AddingOutletSuccess()
    {
      //  Toast.makeText(this, "store berhasil dibuat", Toast.LENGTH_SHORT).show();
        storePresenterImp.getLatestStoreCreatedByUser(strIdUser);
    }


    public void getLatestIdStore(Store store){
      //  Toast.makeText(this, store.getIdStore(), Toast.LENGTH_SHORT).show();
        strIdStore = store.getIdStore();
        storeDetailPresenterImp.addCategory(strIdStore, tvKat.getText().toString());

    }

    public void ErrorAddingOutletFailed(){
        Toast.makeText(AddStoreDetailInfo.this, "Terjadi kesalahan, coba ulangi lagi nanti", Toast.LENGTH_SHORT).show();
    }


}
