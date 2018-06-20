package appdeveloper.klop.klop.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.android.photoutil.GalleryPhoto;

import java.io.File;
import java.util.HashMap;

import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.ReviewPresenterImp;
import appdeveloper.klop.klop.PresenterImp.StorePresenterImp;
import appdeveloper.klop.klop.R;
import dmax.dialog.SpotsDialog;

public class AddReviewRate extends AppCompatActivity implements View.OnClickListener {

    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdUser=null, strIdStore=null, strNmStore=null, strRev=null, strRate=null;
    Toolbar toolbar;
    ImageButton btnRate1, btnRate2, btnRate3, btnRate4, btnRate5, btnDelPhoto1;
    Button btnSendReview;
    EditText edUlasan;
    TextView tvNmStore, tvDesc;
    private SpotsDialog progressDialog;
    RelativeLayout rl1, rl2, rl3, rl4;
    ImageView img1, img2, img3, img4;
    int count=1;

    GalleryPhoto galleryPhoto;
    private Uri fileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_READ_STORAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "Klop Logo";
    public static int COUNT_CAMERA = 100;

    TextInputLayout textInputLayoutRev;

    ReviewPresenterImp reviewPresenterImp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_rate);

        session = new SessionPreference(this);
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        Bundle b = getIntent().getExtras();
        strIdStore = b.getString("idStore");
        strNmStore = b.getString("nmStore");

        reviewPresenterImp = new ReviewPresenterImp(this);

        progressDialog = new SpotsDialog(this, R.style.Custom);

        btnRate1 = (ImageButton) findViewById(R.id.btnRate1);
        btnRate2 = (ImageButton) findViewById(R.id.btnRate2);
        btnRate3 = (ImageButton) findViewById(R.id.btnRate3);
        btnRate4 = (ImageButton) findViewById(R.id.btnRate4);
        btnRate5 = (ImageButton) findViewById(R.id.btnRate5);
        btnDelPhoto1 = (ImageButton) findViewById(R.id.btnDeltPhoto1);
        btnSendReview = (Button) findViewById(R.id.btnSendReview);
        edUlasan = (EditText) findViewById(R.id.edUlasan);
        edUlasan.setText("");
        tvNmStore = (TextView) findViewById(R.id.tvNmStore);
        tvDesc = (TextView) findViewById(R.id.tvDesc);
        tvDesc.setVisibility(View.VISIBLE);
        rl1 = (RelativeLayout) findViewById(R.id.layoutfoto1);
        rl2 = (RelativeLayout) findViewById(R.id.layoutfoto2);
        rl3 = (RelativeLayout) findViewById(R.id.layoutfoto3);
        rl4 = (RelativeLayout) findViewById(R.id.layoutfoto4);
        img1 = (ImageView) findViewById(R.id.imgPhotoRev1);
        img2 = (ImageView) findViewById(R.id.imgPhotoRev2);
        img3 = (ImageView) findViewById(R.id.imgPhotoRev3);
        img4 = (ImageView) findViewById(R.id.imgPhotoRev4);
        textInputLayoutRev = (TextInputLayout) findViewById(R.id.text_input_review);

        tvNmStore.setText(strNmStore);

        btnRate1.setBackgroundResource(R.drawable.star0);
        btnRate2.setBackgroundResource(R.drawable.star0);
        btnRate3.setBackgroundResource(R.drawable.star0);
        btnRate4.setBackgroundResource(R.drawable.star0);
        btnRate5.setBackgroundResource(R.drawable.star0);
        tvDesc.setTextColor(getResources().getColor(R.color.secondaryText2));
        tvDesc.setText("Anda belum memilih bintang");

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

        btnRate1.setOnClickListener(this);
        btnRate2.setOnClickListener(this);
        btnRate3.setOnClickListener(this);
        btnRate4.setOnClickListener(this);
        btnRate5.setOnClickListener(this);
        btnSendReview.setOnClickListener(this);
        rl1.setOnClickListener(this);
    }

    public void dismissSpotLoading(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void showSpotLoading(){
        progressDialog.show();
        progressDialog.setCancelable(true);
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        if(v==btnRate1){
            btnRate1.setBackgroundResource(R.drawable.star1);

            btnRate2.setBackgroundResource(R.drawable.star0);
            btnRate3.setBackgroundResource(R.drawable.star0);
            btnRate4.setBackgroundResource(R.drawable.star0);
            btnRate5.setBackgroundResource(R.drawable.star0);

            tvDesc.setVisibility(View.VISIBLE);
            tvDesc.setText("Sangat buruk");
            strRate="1.0";
            tvDesc.setTextColor(getResources().getColor(R.color.secondaryText));

        }

        else if(v==btnRate2){
            btnRate1.setBackgroundResource(R.drawable.star2);
            btnRate2.setBackgroundResource(R.drawable.star2);

            btnRate3.setBackgroundResource(R.drawable.star0);
            btnRate4.setBackgroundResource(R.drawable.star0);
            btnRate5.setBackgroundResource(R.drawable.star0);

            tvDesc.setVisibility(View.VISIBLE);
            tvDesc.setText("Buruk");
            strRate="2.0";
            tvDesc.setTextColor(getResources().getColor(R.color.secondaryText));
        }

        else if(v==btnRate3){
            btnRate1.setBackgroundResource(R.drawable.star3);
            btnRate2.setBackgroundResource(R.drawable.star3);
            btnRate3.setBackgroundResource(R.drawable.star3);

            btnRate4.setBackgroundResource(R.drawable.star0);
            btnRate5.setBackgroundResource(R.drawable.star0);

            tvDesc.setVisibility(View.VISIBLE);
            tvDesc.setText("Sedang");
            strRate="3.0";
            tvDesc.setTextColor(getResources().getColor(R.color.secondaryText));
        }

        else if(v==btnRate4){
            btnRate1.setBackgroundResource(R.drawable.star4);
            btnRate2.setBackgroundResource(R.drawable.star4);
            btnRate3.setBackgroundResource(R.drawable.star4);
            btnRate4.setBackgroundResource(R.drawable.star4);

            btnRate5.setBackgroundResource(R.drawable.star0);

            tvDesc.setVisibility(View.VISIBLE);
            tvDesc.setText("Memuaskan");
            strRate="4.0";
            tvDesc.setTextColor(getResources().getColor(R.color.secondaryText));
        }

        else if(v==btnRate5){
            btnRate1.setBackgroundResource(R.drawable.star5);
            btnRate2.setBackgroundResource(R.drawable.star5);
            btnRate3.setBackgroundResource(R.drawable.star5);
            btnRate4.setBackgroundResource(R.drawable.star5);

            btnRate5.setBackgroundResource(R.drawable.star5);

            tvDesc.setVisibility(View.VISIBLE);
            tvDesc.setText("Luar biasa");
            strRate="5.0";
            tvDesc.setTextColor(getResources().getColor(R.color.secondaryText));
        }

        else if(v==rl1){
            selectImage();
        }

        else if(v==btnDelPhoto1){
            img1.setImageResource(R.drawable.ic_add_circle_24dp);
            btnDelPhoto1.setVisibility(View.GONE);
        }
//        else if(v==rl2){
//            selectImage();
//        }
//        else if(v==rl3){
//            selectImage();
//        }
//        else if(v==rl4){
//            selectImage();
//        }


        else if(v==btnSendReview){
            if(edUlasan.getText().toString() == null || edUlasan.getText().toString().equals("") || edUlasan.getText().toString().equals(null) ){
                strRev = "  ";
            } else
            strRev = edUlasan.getText().toString();

         //   Toast.makeText(this, strIdStore+"|"+strIdUser+"|"+strRev+"|"+edUlasan.length()+"|"+strRate, Toast.LENGTH_SHORT).show();

            reviewPresenterImp.addReview(strIdStore, strIdUser, strRev, strRate);
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Ambil Foto", "Ambil Foto dari Pustaka", "Batal" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Ambil Foto")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                    startActivityForResult(intent, COUNT_CAMERA);
                } else if (items[item].equals("Ambil Foto dari Pustaka")) {

                    if (ContextCompat.checkSelfPermission(AddReviewRate.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(AddReviewRate.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
                    } else {
                        openGallery();
                    }

                    // startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
                } else if (items[item].equals("Batal")) {
                    dialog.dismiss();
                    img1.setImageResource(R.drawable.ic_add_circle_24dp);

                }
            }
        });
        builder.show();
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


    private void openGallery(){
        Intent galleryIntent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);

        btnDelPhoto1.setVisibility(View.VISIBLE);
    }

    public void AddingReviewSuccess()
    {
        reviewPresenterImp.updateRateStore(strIdStore);
       // Toast.makeText(this, "Ulasan berhasil dikirim!", Toast.LENGTH_SHORT).show();
//        onBackPressed();
    }

    public void submitReviewRateSuccess(){
         Toast.makeText(this, "Ulasan berhasil dikirim!", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    public void submitReviewRateFailed(){
        Toast.makeText(this, "Ulasan gagal dikirim, coba lagi nanti", Toast.LENGTH_SHORT).show();
    }

    public void noRateFound(){
        Toast.makeText(this, "Harap memberi bintang penilaian", Toast.LENGTH_SHORT).show();
    }

    public void dataInvalid(){
        Toast.makeText(this, "Data tidak valid", Toast.LENGTH_SHORT).show();
    }

    public void ErrorAddingReview(){
        Toast.makeText(AddReviewRate.this, "Terjadi kesalah, coba lagi nanti", Toast.LENGTH_SHORT).show();
    }

    public void ErrorResponseFailed(){
        Toast.makeText(AddReviewRate.this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
    }

    public void ErrorConnectionFailed(){
        Toast.makeText(AddReviewRate.this, "Koneksi API gagal", Toast.LENGTH_SHORT).show();
    }

    public void showInvalidStore(){
        Toast.makeText(this, "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(AddReviewRate.this, TabbedMenu.class);
        startActivity(i);
    }

    public void showInvalidUser(){
        Toast.makeText(this, "Login dahulu", Toast.LENGTH_SHORT).show();
        session.logoutUser();
    }

    public void showInvalidTextReview(){
        Toast.makeText(this, "Harap mengisi ulasan", Toast.LENGTH_SHORT).show();
        textInputLayoutRev.setError("Harap diisi");
        requestFocus(edUlasan);
    }

    public void showInvalidRate(){
        textInputLayoutRev.setErrorEnabled(false);
        tvDesc.setVisibility(View.VISIBLE);
        tvDesc.setText("Harap memberikan bintang penilaian");
        tvDesc.setTextColor(getResources().getColor(R.color.red));
    }

    public void removeError(){}
}
