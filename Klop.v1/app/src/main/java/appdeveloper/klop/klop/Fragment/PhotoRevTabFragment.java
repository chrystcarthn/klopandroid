package appdeveloper.klop.klop.Fragment;


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
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import appdeveloper.klop.klop.Activity.FullScreenPhoto;
import appdeveloper.klop.klop.Activity.ManagePhoto;
import appdeveloper.klop.klop.Adapter.PhotoAdapter;
import appdeveloper.klop.klop.Model.Photo;
import appdeveloper.klop.klop.Model.PhotoResponse;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.PhotoTabFragPresenterImp;
import appdeveloper.klop.klop.R;
import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by CMDDJ on 5/13/2018.
 */

public class PhotoRevTabFragment extends Fragment{

    private CoordinatorLayout mRootView;
    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdStore;

    PhotoResponse photoResponse;
    GridView mGridView;
    private PhotoAdapter adapter;
    ProgressBar mProgressBar;
    Button btnAddPhoto;
    TextView tvEmpty;
    TextView tvJudul, tvError;
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

    //private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;

    PhotoTabFragPresenterImp photoTabFragPresenterImp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = (CoordinatorLayout) inflater.inflate(R.layout.fragment_tab_photo, container, false);

        session = new SessionPreference(getContext());
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        Bundle b = getActivity().getIntent().getExtras();
        strIdStore = b.getString("idStore");
//
//        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) mRootView.findViewById(R.id.main_swipe);
//        mWaveSwipeRefreshLayout.setWaveColor(getResources().getColor(R.color.yellow));
//        mWaveSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.white));

        progressDialog = new SpotsDialog(getContext(), R.style.Custom);

        btnAddPhoto = (Button) mRootView.findViewById(R.id.btnAddPhoto);
        tvEmpty = (TextView) mRootView.findViewById(R.id.tvEmptyPhoto);
        mGridView = (GridView) mRootView.findViewById(R.id.gridView);
        mProgressBar = (ProgressBar) mRootView.findViewById(R.id.progress_load_gallery);
        photoTabFragPresenterImp = new PhotoTabFragPresenterImp(this);
        photoTabFragPresenterImp.showPhotoReview(strIdStore);

        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
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
                       // Toast.makeText(getContext(), "berhasil "+strPhotoUrl+"|"+strEncodedFile, Toast.LENGTH_SHORT).show();
                        showSpotLoading();

                        if(strPhotoUrl == null){
                            tvError.setVisibility(View.VISIBLE);
                            dismissSpotLoading();
                        } else{

                            tvError.setVisibility(View.GONE);
                           // Toast.makeText(getContext(), "berhasil "+strPhotoUrl+"|"+strEncodedFile, Toast.LENGTH_SHORT).show();

                            photoTabFragPresenterImp.addPhotoReview(strIdStore, strPhotoUrl, strEncodedFile, strIdUser);

                        }
                    }
                });

                dialog.show();
            }
        });
        return mRootView;
    }

    public void dismissSpotLoading(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void showSpotLoading(){
        progressDialog.show();
        progressDialog.setCancelable(true);
    }


    public static Fragment newInstance() {
        return new PhotoRevTabFragment();
    }

    public void generatePhotoResponse(final ArrayList<Photo> photoArrayList){

            tvEmpty.setVisibility(View.GONE);
            adapter = new PhotoAdapter(photoArrayList, getContext());

            mProgressBar.setVisibility(View.VISIBLE);
            mGridView.setAdapter(adapter);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String url  = photoArrayList.get(position).getFile();
                    String name = photoArrayList.get(position).getCreated();
                    Intent intent = new Intent(getContext(), FullScreenPhoto.class);
                    intent.putExtra("url",url);
                    intent.putExtra("name",name);
                    startActivity(intent);
                }
            });
            mProgressBar.setVisibility(View.GONE);


    }

    public void warningDataNotFound(){
        tvEmpty.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
       // Toast.makeText(getContext(), "Oops, data tidak ditemukan!", Toast.LENGTH_SHORT).show();
    }


    public void ErrorResponse(){
        dismissSpotLoading();
        Toast.makeText(getContext(), "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
       // Toasty.error(getContext(), "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT, true).show();
    }

    public void ErrorConnectionFailed(){
        dismissSpotLoading();
        Toast.makeText(getContext(), "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
       // Toasty.error(getContext(), "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT, true).show();
    }

    public void addingPhotoRevSuccess(){
        photoTabFragPresenterImp.showPhotoReview(strIdStore);
        btnAddPhoto.setText("Tambah Lagi");
        imgPhoto.setImageResource(R.mipmap.add_image2);
        strPhotoUrl = null;
        strEncodedFile = null;
        dismissSpotLoading();
       // Toast.makeText(getContext(), "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
       // Toasty.success(getContext(), "Foto ditambah", Toast.LENGTH_SHORT, true).show();

    }

    public void addingPhotoRevFailed(){
        dismissSpotLoading();
        Toasty.error(getContext(), "Foto gagal ditambah", Toast.LENGTH_SHORT, true).show();

    }

    private void selectImage() {
        final CharSequence[] items = { "Ambil foto dari pustaka", "Batal" };

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
             if (items[item].equals("Ambil foto dari pustaka")) {

                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) getContext(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
                } else {
                    openGallery();
                }

                // startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
            } else if (items[item].equals("Batal")) {
                dialog.dismiss();
//                imgPhoto.setImageResource(R.mipmap.add_image2);
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
        MediaScannerConnection.scanFile(getContext(), new String[]{Environment.getExternalStorageDirectory().toString()},
                null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
        return mediaFile;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (RESULT_LOAD_IMAGE): {
                //   Toast.makeText(this, "onActivityResult1", Toast.LENGTH_SHORT).show();
                if (resultCode == Activity.RESULT_OK) {
                    {
                        if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            try {
                                Uri selectedImageUri = data.getData();
                                Random rand = new Random();
                                int n = rand.nextInt(1000);

                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                //   ((BitmapDrawable) imgPhotoprof.getDrawable()).getBitmap().compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                                String[] projection = {MediaStore.MediaColumns.DATA};

                                CursorLoader cursorLoader = new CursorLoader(getContext(), selectedImageUri, projection, null, null,
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






}
