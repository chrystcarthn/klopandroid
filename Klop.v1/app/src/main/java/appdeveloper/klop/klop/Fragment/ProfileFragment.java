package appdeveloper.klop.klop.Fragment;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import appdeveloper.klop.klop.Activity.MyStore;
import appdeveloper.klop.klop.BuildConfig;
import appdeveloper.klop.klop.Model.User;
import appdeveloper.klop.klop.Other.CircleTransform;
import appdeveloper.klop.klop.Other.Utils;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.ProfilePresenterImp;
import appdeveloper.klop.klop.R;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.http.Url;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    String imageFileName;
    private User user;
    private SpotsDialog progressDialog;
    SessionPreference session;
    EditText edName, edEmail, edPassword, edPhone;
    String strIdUser, linkAvatar, strEmail, trim;
    TextView tvName;
    Button btnLogOut, btnOutlet;
    ImageView imgPhotoprof;
    HashMap<String, String> userdata;
    TextInputLayout nameWrapper, emailWrapper, passwordWrapper, phoneWrapper;
    private ProfilePresenterImp profilePresenterImp;
    private static final String TAG = "edit";
    private static final int RESULT_LOAD_IMAGE = 1;
    private Uri fileUri, file_uri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "Klop Photo";
    Toolbar toolbar;
    public static int COUNT_CAMERA = 100;

    private String strAvatar, strEncodedfile;
    private Bitmap bitmap;
    private File file;
    RelativeLayout rlImageProf;
    GalleryPhoto galleryPhoto;
    final int GALLERY_REQUEST = 22131;
    private final int requestCode = 20;
    Url url;
    User userTemp = new User();
    private static final int REQUEST_READ_STORAGE = 1;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Bitmap mImageBitmap;
    private String mCurrentPhotoPath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);

        session = new SessionPreference(getContext());
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        profilePresenterImp = new ProfilePresenterImp(this);

        galleryPhoto = new GalleryPhoto(getContext());

        edName = (EditText) v.findViewById(R.id.edName);
        edEmail = (EditText) v.findViewById(R.id.edEmail);
        edPassword = (EditText) v.findViewById(R.id.edPassword);
        edPhone = (EditText) v.findViewById(R.id.edPhone);
        nameWrapper = (TextInputLayout) v.findViewById(R.id.nameWrapper);
        emailWrapper = (TextInputLayout) v.findViewById(R.id.emailWrapper);
        passwordWrapper = (TextInputLayout) v.findViewById(R.id.passWrapper);
        phoneWrapper = (TextInputLayout) v.findViewById(R.id.phoneWrapper);
        tvName = (TextView) v.findViewById(R.id.tvUser);
        progressDialog = new SpotsDialog(getContext(), R.style.Custom);

        btnLogOut = (Button) v.findViewById(R.id.btnKeluar);
        btnOutlet = (Button) v.findViewById(R.id.btnOutlet);
        rlImageProf = (RelativeLayout) v.findViewById(R.id.rlImageProf);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setElevation(rlImageProf, Utils.dpToPx(5, getContext()));
            ViewCompat.setTranslationZ(rlImageProf, Utils.dpToPx(5, getContext()));

        } else {
            rlImageProf.bringToFront(); // works on both pre-lollipop and Lollipop
        }

        imgPhotoprof = (ImageView)v.findViewById(R.id.imgUser);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setElevation(imgPhotoprof, Utils.dpToPx(5, getContext()));
            ViewCompat.setTranslationZ(imgPhotoprof, Utils.dpToPx(5, getContext()));

        } else {
            imgPhotoprof.bringToFront(); // works on both pre-lollipop and Lollipop
        }

        tvName.setText("Ganti foto");

        //if(strIdUser != null){
            edName.setText(userdata.get(SessionPreference.KEY_FULLNAME));
            edPassword.setText(userdata.get(SessionPreference.KEY_PASSWORD));
            strEmail = userdata.get(SessionPreference.KEY_EMAIL);
            if(strEmail != null){
                edEmail.setText(strEmail);
            }else {
                edEmail.setText("");
            }
            edPhone.setText(userdata.get(SessionPreference.KEY_PHONE));

            try{
                linkAvatar = userdata.get(SessionPreference.KEY_AVATAR);
            }catch(Exception e){
                e.printStackTrace();
            }
            if(linkAvatar != null) {
                Picasso.with(getContext()).load(linkAvatar).into(imgPhotoprof);
            }

        btnLogOut.setOnClickListener(this);
        btnOutlet.setOnClickListener(this);
        imgPhotoprof.setOnClickListener(this);
        tvName.setOnClickListener(this);
        return v;
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_saveprofile:
           //     Toast.makeText(getActivity(), strIdUser.toString()+edName.getText().toString()+edEmail.getText().toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, strIdUser.toString()+edName.getText().toString()+edEmail.getText().toString()+" frag");
                showSpotLoading();

                profilePresenterImp.editProfile(strIdUser.toString(),
                                                    edName.getText().toString(),
                                                    edEmail.getText().toString(),
                                                    edPassword.getText().toString(),
                                                    edPhone.getText().toString(),
                                                    strAvatar,
                                                    strEncodedfile);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void showInvalidName() {
        //edEmail.setError("Invalid Email");
        emailWrapper.setErrorEnabled(false);
        passwordWrapper.setErrorEnabled(false);
        phoneWrapper.setErrorEnabled(false);
        nameWrapper.setError("Harap diisi");
        requestFocus(edName);
    }

    public void showInvalidEmail() {
        //edEmail.setError("Invalid Email");
        nameWrapper.setErrorEnabled(false);
        passwordWrapper.setErrorEnabled(false);
        phoneWrapper.setErrorEnabled(false);
        emailWrapper.setError("Format email tidak benar");
        requestFocus(edEmail);
    }

    public void showInvalidPassword() {
        // edPassword.setError("Password cannot be empty");
        nameWrapper.setErrorEnabled(false);
        emailWrapper.setErrorEnabled(false);
        phoneWrapper.setErrorEnabled(false);
        passwordWrapper.setError("Harap diisi minimal 6 karakter");
        requestFocus(edPassword);
    }

    public void showInvalidPhone(){
        nameWrapper.setErrorEnabled(false);
        emailWrapper.setErrorEnabled(false);
        passwordWrapper.setErrorEnabled(false);
        phoneWrapper.setError("Harap diisi minimal 9 digit");
        requestFocus(edPhone);
    }

    public void removeError() {
        // edPassword.setError("Password cannot be empty");
        nameWrapper.setErrorEnabled(false);
        emailWrapper.setErrorEnabled(false);
        passwordWrapper.setErrorEnabled(false);
        phoneWrapper.setErrorEnabled(false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }

    private void storeCameraPhotoInSDCard(Bitmap bitmap, String currentDate){
        File outputFile = new File(Environment.getExternalStorageDirectory(), "photo_" + currentDate + ".jpg");
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getImageFileFromSDCard(String filename){
        Bitmap bitmap = null;
        File imageFile = new File(Environment.getExternalStorageDirectory() + filename);
        try {
            FileInputStream fis = new FileInputStream(imageFile);
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            if(EasyPermissions.hasPermissions(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE,  Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

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

            strAvatar = "user" + n + ".jpg";
            strEncodedfile = Base64.encodeToString(b, Base64.NO_WRAP);
                Toast.makeText(getContext(), "path: "+selectedImagePath, Toast.LENGTH_SHORT).show();
            imgPhotoprof.setImageURI(selectedImageUri);
              //  Toast.makeText(this.getContext(), strAvatar +" 2 "+ strEncodedfile, Toast.LENGTH_SHORT).show();
                Log.i(TAG, "encoded image :"+strEncodedfile);
            } else{
                EasyPermissions.requestPermissions(this, "Access for storage",
                        101, galleryPermissions);

            }
          }
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    @Override
    public void onClick(View v) {
        if(v == btnLogOut){
                    new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Keluar Akun")
                            .setContentText("Apakah Anda yakin ingin keluar?")
                            .setConfirmText("Ya")
                            .setCancelText("Tidak")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            session.logoutUser();
                            sweetAlertDialog.dismiss();
                        }
                    }).show();

        }
        else if(v == btnOutlet){
            Bundle b = new Bundle();
            b.putString("owner", "1");
            Intent i = new Intent(getContext(), MyStore.class);
            i.putExtras(b);
            startActivity(i);
        }
        else if(v == tvName){
           selectImage();
        }
        else if(v == imgPhotoprof){
            selectImage();
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
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_user" + strIdUser +"_photo" + ".jpg");
        } else {
            return null;
        }
        MediaScannerConnection.scanFile(getActivity(), new String[]{Environment.getExternalStorageDirectory().toString()},
                null, new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                        Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });
        return mediaFile;
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath =  image.getAbsolutePath();

        return image;
    }



    private void selectImage() {
        final CharSequence[] items = { "Ambil foto dari pustaka", "Batal" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
            if (items[item].equals("Ambil foto dari pustaka")) {
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_STORAGE);
                } else {
                    openGallery();
                }
//            } else if (items[item].equals("Hapus foto")) {
//                    dialog.dismiss();
//                    imgPhotoprof.setImageResource(R.mipmap.userdefault);
            }else if (items[item].equals("Batal")) {
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
                Toast.makeText(getContext(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void EditProfileSuccess(){
        userTemp.setIDUSER(strIdUser);
        userTemp.setIDROLE("2");
        userTemp.setFULLNAME(edName.getText().toString());
        userTemp.setEMAIL(edEmail.getText().toString());
        userTemp.setPASSWORD(edPassword.getText().toString());
        userTemp.setPHONE(edPhone.getText().toString());

        if(strAvatar == null){
          //  Toast.makeText(getContext(), "strAvatar: "+strAvatar, Toast.LENGTH_SHORT).show();
            userTemp.setAVATAR(userdata.get(SessionPreference.KEY_AVATAR));
        }else {
          //  Toast.makeText(getContext(), "strAvatar2: "+strAvatar, Toast.LENGTH_SHORT).show();
            userTemp.setAVATAR("http://devpanel.xyz/klop_api/image_content/" + strAvatar);
        }

        session.createLoginSession(userTemp);
        userdata = session.getUserDetails();

        edName.setText(userTemp.getFULLNAME());
        strEmail = userdata.get(SessionPreference.KEY_EMAIL);

        edPassword.setText(userdata.get(SessionPreference.KEY_PASSWORD));
        if(strEmail != null){
            edEmail.setText(strEmail);
        }else {
            edEmail.setText("");
        }
        edPhone.setText(userdata.get(SessionPreference.KEY_PHONE));
        linkAvatar = userdata.get(SessionPreference.KEY_AVATAR);

      //  Toast.makeText(getContext(), linkAvatar, Toast.LENGTH_SHORT).show();
        Picasso.with(getContext()).load(linkAvatar).into(imgPhotoprof);
    }

    public void EditProfileFailed() {
        Toast.makeText(getContext(), "Terjadi kesalahan, coba ulangi lagi nanti", Toast.LENGTH_SHORT).show();
    }

    public void dataInvalid(){
        Toast.makeText(getContext(), "Data tidak valid, periksa kembali data Anda", Toast.LENGTH_SHORT).show();
    }

    public void userNotFound(User user){
        Toast.makeText(getContext(), "Login dahulu1 " +user.getIDUSER(), Toast.LENGTH_SHORT).show();
        session.logoutUser();
    }

    public void loginTimeOut(){
        Toast.makeText(getContext(), "Login time out", Toast.LENGTH_SHORT).show();
        session.logoutUser();
    }

    public void ErrorResponse() {
        Toast.makeText(getContext(), "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
    }


    public void ErrorConnection() {
        Toast.makeText(getContext(), "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
    }
}
