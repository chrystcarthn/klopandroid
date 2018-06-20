package appdeveloper.klop.klop.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import appdeveloper.klop.klop.Adapter.MngPhotoAdapter;
import appdeveloper.klop.klop.Adapter.PhotoAdapter;
import appdeveloper.klop.klop.Model.Photo;
import appdeveloper.klop.klop.Model.PhotoResponse;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.StoreSettingsPresenterImp;
import appdeveloper.klop.klop.R;
import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import pub.devrel.easypermissions.EasyPermissions;

public class ManagePhoto extends AppCompatActivity {


    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdStore;
    FloatingActionButton fabNewPhoto;
    PhotoResponse photoResponse;
    GridView mGridView;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    ProgressBar mProgressBar;
    Toolbar toolbar;
    TextView tvEmpty;
    StoreSettingsPresenterImp storeSettingsPresenterImp;
    MngPhotoAdapter adapter;
    TextView tvJudul, tvError;
    Button btnAddPhoto;
    ImageButton btnClose;
    AlertDialog dialog;

    ImageView imgPhoto;

    private Uri fileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int REQUEST_READ_STORAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "Klop Logo";
    public static int COUNT_CAMERA = 100;

    String strPhotoUrl=null, strEncodedFile=null, strIdUser;
    private static final String TAG = "managPhoto";

    private SpotsDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_photo);

        session = new SessionPreference(this);
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        Bundle b = getIntent().getExtras();
        strIdStore = b.getString("idStore");

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

        fabNewPhoto = (FloatingActionButton) findViewById(R.id.fabNewPhoto);
        tvEmpty = (TextView) findViewById(R.id.tvEmptyPhotos);
        mGridView = (GridView) findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_load_gallery);

        storeSettingsPresenterImp = new StoreSettingsPresenterImp(this);

//        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe2);
//        mWaveSwipeRefreshLayout.setWaveColor(getResources().getColor(R.color.yellow));
//        mWaveSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.white));
//        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                // Do work to refresh the list here.
//                mWaveSwipeRefreshLayout.setRefreshing(false);
//            }
//        });

        fabNewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ManagePhoto.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_add_photo, null);

                tvJudul = (TextView) mView.findViewById(R.id.tvJudul);
                tvError = (TextView) mView.findViewById(R.id.tvError);

                imgPhoto = (ImageView) mView.findViewById(R.id.imgPhoto);
                btnAddPhoto = (Button) mView.findViewById(R.id.btnAddPhoto);
                btnClose = (ImageButton) mView.findViewById(R.id.btnClose);
                btnAddPhoto.setText("Tambah");

                // nmWrapper.setError("Harap mengisi nama perawatan");

                mBuilder.setView(mView);
                dialog = mBuilder.create();

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        strPhotoUrl=null;
                        strEncodedFile=null;
                        hideSoftKeyboard();
                        dialog.dismiss();

                    }
                });

                imgPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectImage();
                    }
                });

                btnAddPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showSpotLoading();

                        if(strPhotoUrl == null){
                            tvError.setVisibility(View.VISIBLE);
                            dismissSpotLoading();
                        } else{

                            tvError.setVisibility(View.GONE);
                            storeSettingsPresenterImp.addPhotoReview(strIdStore, strPhotoUrl, strEncodedFile, strIdUser);
                        }
                    }
                });

                dialog.show();
            }
        });

        loadRecyclerviewPhoto();
    }

    public void dismissSpotLoading(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void showSpotLoading(){
        progressDialog.show();
        progressDialog.setCancelable(true);
    }

    private void selectImage() {
        final CharSequence[] items = { "Ambil foto dari pustaka", "Batal" };

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
         if (items[item].equals("Ambil foto dari pustaka")) {

            if (ContextCompat.checkSelfPermission(ManagePhoto.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ManagePhoto.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
            } else {
                openGallery();
            }

            // startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
        } else if (items[item].equals("Batal")) {
            dialog.dismiss();
//            imgPhoto.setImageResource(R.mipmap.add_image2);
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

                                    strPhotoUrl = "review" + n + ".jpg";
                                    strEncodedFile = Base64.encodeToString(b, Base64.NO_WRAP);
                                    imgPhoto.setImageURI(selectedImageUri);
                                    tvError.setVisibility(View.GONE);

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


    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        loadRecyclerviewPhoto();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadRecyclerviewPhoto();
    }

    public void loadRecyclerviewPhoto(){
//        mWaveSwipeRefreshLayout.setRefreshing(true);
        storeSettingsPresenterImp.showPhotoReview(strIdStore);
    }

    public void generatePhotoResponse(final ArrayList<Photo> photoArrayList){

        tvEmpty.setVisibility(View.GONE);
        adapter = new MngPhotoAdapter(photoArrayList, this);

        mProgressBar.setVisibility(View.VISIBLE);
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String idphoto = photoArrayList.get(position).getIdPhoto();
                String url  = photoArrayList.get(position).getFile();
                String name = photoArrayList.get(position).getFullName();
                String created = photoArrayList.get(position).getCreated();
                Intent intent = new Intent(ManagePhoto.this, FullScreenMngPhoto.class);
                intent.putExtra("idstore", strIdStore);
                intent.putExtra("id",idphoto);
                intent.putExtra("url",url);
                intent.putExtra("name",name);
                intent.putExtra("created",created);

                startActivity(intent);
            }
        });
        mProgressBar.setVisibility(View.GONE);

        storeSettingsPresenterImp = new StoreSettingsPresenterImp(this);
    }

    public void warningDataNotFound(){
        tvEmpty.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
      //  Toast.makeText(this, "Oops, data tidak ditemukan!", Toast.LENGTH_SHORT).show();
        //mWaveSwipeRefreshLayout.setRefreshing(false);
        // emptyTextView.setVisibility(View.VISIBLE);
    }


    public void ErrorResponse(){
        Toasty.error(ManagePhoto.this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT, true).show();
    }

    public void ErrorConnectionFailed(){
        Toasty.info(ManagePhoto.this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT, true).show();
    }

    public void addingPhotoRevSuccess(){
        loadRecyclerviewPhoto();
        btnAddPhoto.setText("Tambah Lagi");
        imgPhoto.setImageResource(R.mipmap.add_image2);
        strPhotoUrl = null;
        strEncodedFile = null;
        dismissSpotLoading();
      //  Toasty.success(ManagePhoto.this, "Foto ditambah", Toast.LENGTH_SHORT, true).show();

    }

    public void addingPhotoRevFailed(){
        dismissSpotLoading();
        Toasty.error(ManagePhoto.this, "Gagal menambah, Coba lagi nanti", Toast.LENGTH_SHORT, true).show();

    }
}
