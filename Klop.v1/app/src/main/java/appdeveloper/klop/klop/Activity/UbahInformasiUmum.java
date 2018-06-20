package appdeveloper.klop.klop.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

import appdeveloper.klop.klop.MapConfiguration.CustomInfoWindowAdapter;
import appdeveloper.klop.klop.MapConfiguration.PlaceAutoCompleteAdapter;
import appdeveloper.klop.klop.Model.PlaceInfo;
import appdeveloper.klop.klop.Model.Store;
import appdeveloper.klop.klop.PresenterImp.StoreSettingsPresenterImp;
import appdeveloper.klop.klop.R;
import dmax.dialog.SpotsDialog;

public class UbahInformasiUmum extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {

    Toolbar toolbar;
    String strIdStore, strNmStore, strPhStore, strAddress, strLat, strLong, strLat2, strLong2;
    TextInputLayout nameWrapper, phoneWrapper, addressWrapper, koordinatWrapper;
    EditText edName, edPhone, edAddress, edKoordinat;
    private SpotsDialog progressDialog;
    Button btnSimpan;
    StoreSettingsPresenterImp storeSettingsPresenterImp;

    LatLng posisiAwal;
    private static final String TAG = "Umum";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final int PLACE_PICKER_REQUEST = 1;
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private Marker mMarker;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlaceAutoCompleteAdapter mPlaceAutoCompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private PlaceInfo mPlace;
    LatLng startP , endP;
    ImageButton btnMap;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_informasi_umum);

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(UbahInformasiUmum.this, StoreSettings.class);
                i.putExtra("nmStore", strNmStore);
                i.putExtra("phStore", strPhStore);
                i.putExtra("address", strAddress);
                i.putExtra("strLat", strLat);
                i.putExtra("strLong", strLong);
              //  Toast.makeText(UbahInformasiUmum.this, "kirim : "+strNmStore+"|"+strPhStore+"|"+strAddress, Toast.LENGTH_SHORT).show();

                setResult(Activity.RESULT_OK, i);
                finish();
            }
        });

        storeSettingsPresenterImp = new StoreSettingsPresenterImp(this);

        Intent i = getIntent();
        strIdStore = i.getStringExtra("idStore");
        strNmStore = i.getStringExtra("nmStore");
        strPhStore = i.getStringExtra("phStore");
        strAddress = i.getStringExtra("address");
        strLat = i.getStringExtra("strLat");
        strLong = i.getStringExtra("strLong");

        progressDialog = new SpotsDialog(this, R.style.Custom);

        btnMap = (ImageButton) findViewById(R.id.btnOpenMap);
        edName = (EditText) findViewById(R.id.edName);
        edPhone = (EditText) findViewById(R.id.edTelepon);
        edAddress = (EditText) findViewById(R.id.edAlamat);
        edKoordinat = (EditText) findViewById(R.id.edKordinat);

        btnSimpan = (Button) findViewById(R.id.btnSimpan);

        nameWrapper = (TextInputLayout) findViewById(R.id.input_layout_nama);
        phoneWrapper = (TextInputLayout) findViewById(R.id.input_layout_telepon);
        addressWrapper = (TextInputLayout) findViewById(R.id.input_layout_alamat);
        koordinatWrapper = (TextInputLayout) findViewById(R.id.input_layout_kordinat);

        edName.setText(strNmStore);
        edPhone.setText(strPhStore);
        edAddress.setText(strAddress);
        edKoordinat.setText(strLat+", "+strLong);


        getLocationPermission();


        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               
                
          //     Toast.makeText(UbahInformasiUmum.this, "click "+strIdStore+"|"+edName.getText().toString()+"|"+edPhone.getText().toString()+"|"+ edAddress.getText().toString()+"|"+strLat+"|"+strLong, Toast.LENGTH_SHORT).show();
               storeSettingsPresenterImp.isValid(strIdStore, edName.getText().toString(), edAddress.getText().toString(), edPhone.getText().toString(), strLat, strLong);

            }
        });

    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }


    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(UbahInformasiUmum.this);
    }

    public void dismissSpotLoading(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void showSpotLoading(){
        progressDialog.show();
        progressDialog.setCancelable(true);
    }

    public void editSuccess(){
        Toast.makeText(this, "Perubahan disimpan", Toast.LENGTH_SHORT).show();
        strNmStore = edName.getText().toString();
        strPhStore = edPhone.getText().toString();
        strAddress = edAddress.getText().toString();
        strLat2 = strLat;
        strLong2 = strLong;
    //    Toast.makeText(UbahInformasiUmum.this, strIdStore+"|"+strNmStore+"|"+strPhStore+"|"+ strAddress+"|"+strLat+"|"+strLong, Toast.LENGTH_SHORT).show();

    }

    public void editFailed(){
        Toast.makeText(this, "Gagal mengubah data. Coba beberapa saat lagi", Toast.LENGTH_SHORT).show();
    }


    public void dataInvalid(){
        Toast.makeText(this, "Data tidak valid. Harap periksa data Anda", Toast.LENGTH_SHORT).show();
    }

    public void ErrorResponse() {
        Toast.makeText(this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
    }


    public void ErrorConnection() {
        Toast.makeText(this, "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    public void showInvalidId() {
//        Toast.makeText(this, "Outlet tidak ditemukan", Toast.LENGTH_SHORT).show();
    }

    public void showInvalidName() {
        //edEmail.setError("Invalid Email");
        phoneWrapper.setErrorEnabled(false);
        addressWrapper.setErrorEnabled(false);
        koordinatWrapper.setErrorEnabled(false);
        nameWrapper.setError("Harap diisi");
        requestFocus(edName);
    }

    public void showInvalidPhone() {
        nameWrapper.setErrorEnabled(false);
        addressWrapper.setErrorEnabled(false);
        koordinatWrapper.setErrorEnabled(false);
        phoneWrapper.setError("Harap diisi");
        requestFocus(edPhone);
    }

    public void showInvalidAddress() {
        nameWrapper.setErrorEnabled(false);
        phoneWrapper.setErrorEnabled(false);
        koordinatWrapper.setErrorEnabled(false);
        addressWrapper.setError("Harap diisi");
        requestFocus(edAddress);
    }

    public void showInvalidLatLong() {
        nameWrapper.setErrorEnabled(false);
        phoneWrapper.setErrorEnabled(false);
        addressWrapper.setErrorEnabled(false);
        koordinatWrapper.setError("Ketuk peta untuk mendapatkan kordinat outlet");

        requestFocus(edKoordinat);
    }


    public void removeError() {
        nameWrapper.setErrorEnabled(false);
        phoneWrapper.setErrorEnabled(false);
        addressWrapper.setErrorEnabled(false);
        koordinatWrapper.setErrorEnabled(false);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
     //   Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            generateStoreMarker(strLat, strLong);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);


            init();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);

                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, place.getId());
                placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            }
        }
    }

    private void init(){
        Log.d(TAG, "init: initializing");

        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        mPlaceAutoCompleteAdapter = new PlaceAutoCompleteAdapter(this, R.layout.item_list_address, mGoogleApiClient,
                LAT_LNG_BOUNDS, null);

        geoLocate();




/*
*
* ------------------------------------------ Place Picker Widget -----------------------------------------------
*
* */

        edKoordinat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(UbahInformasiUmum.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesRepairableException: " + e.getMessage() );
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesNotAvailableException: " + e.getMessage() );
                }
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(UbahInformasiUmum.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesRepairableException: " + e.getMessage() );
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesNotAvailableException: " + e.getMessage() );
                }
            }
        });

        hideSoftKeyboard();
    }

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if(!places.getStatus().isSuccess()){
                Log.d(TAG, "onResult: Place query did not complete successfully: " + places.getStatus().toString());
                places.release();
                return;
            }
            final Place place = places.get(0);

            try{
                mPlace = new PlaceInfo();
                mPlace.setName(place.getName().toString());
                Log.d(TAG, "onResult: name: " + place.getName());
                mPlace.setAddress(place.getAddress().toString());
                Log.d(TAG, "onResult: address: " + place.getAddress());
                //  strAlamat = mPlace.getAddress().toString();
//                mPlace.setAttributions(place.getAttributions().toString());
//                Log.d(TAG, "onResult: attributions: " + place.getAttributions());
                mPlace.setId(place.getId());
                Log.d(TAG, "onResult: id:" + place.getId());
                mPlace.setLatlng(place.getLatLng());
                Log.d(TAG, "onResult: latlng: " + place.getLatLng());
                mPlace.setRating(place.getRating());
                Log.d(TAG, "onResult: rating: " + place.getRating());
                mPlace.setPhoneNumber(place.getPhoneNumber().toString());
                Log.d(TAG, "onResult: phone number: " + place.getPhoneNumber());
                mPlace.setWebsiteUri(place.getWebsiteUri());
                Log.d(TAG, "onResult: website uri: " + place.getWebsiteUri());

                Log.d(TAG, "onResult: place: " + mPlace.toString());
            }catch (NullPointerException e){
                Log.e(TAG, "onResult: NullPointerException: " + e.getMessage() );
            }

            moveCamera(new LatLng(place.getViewport().getCenter().latitude,
                    place.getViewport().getCenter().longitude), DEFAULT_ZOOM, mPlace);



            //  edAlamat.setText(mPlace.getAddress().toString());
            //strLat = Double.toString(place.getViewport().getCenter().latitude);
           // strLat2 = Double.toString(place.getViewport().getCenter().latitude);
            //strLong = Double.toString(place.getViewport().getCenter().longitude);
          //  strLong2 = Double.toString(place.getViewport().getCenter().longitude);
           // Toast.makeText(UbahInformasiUmum.this, "", Toast.LENGTH_SHORT).show();
            edKoordinat.setText(Double.toString(place.getViewport().getCenter().latitude) +", "+Double.toString(place.getViewport().getCenter().longitude));
            strLat = Double.toString(place.getViewport().getCenter().latitude);
            strLong = Double.toString(place.getViewport().getCenter().longitude);
            places.release();
        }
    };


    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }

        hideSoftKeyboard();
    }

    private void moveCamera(LatLng latLng, float zoom, PlaceInfo placeInfo){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        mMap.clear();
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(UbahInformasiUmum.this));

        if(placeInfo != null){
            try{
                String snippet = "Address: " + placeInfo.getAddress() + "\n" +
                        "Phone Number: " + placeInfo.getPhoneNumber() + "\n" +
                        "Website: " + placeInfo.getWebsiteUri() + "\n" +
                        "Price Rating: " + placeInfo.getRating() + "\n";

                MarkerOptions options = new MarkerOptions()
                        .position(latLng)
                        .title(placeInfo.getName())
                        .snippet(snippet);
                mMarker = mMap.addMarker(options);

            }catch (NullPointerException e){
                Log.e(TAG, "moveCamera: NullPointerException: " + e.getMessage() );
            }
        }else{
            mMap.addMarker(new MarkerOptions().position(latLng));
        }

        hideSoftKeyboard();
    }


    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void generateStoreMarker(String strLat, String strLong){
        LatLng position = new LatLng(Double.valueOf(strLat),
                Double.valueOf(strLong));

            createMarker(Double.valueOf(strLat), Double.valueOf(strLong), strNmStore);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, DEFAULT_ZOOM));

        getDeviceLocation();
    }

//    private void initMap(){
//        Log.d(TAG, "initMap: initializing map");
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//
//        mapFragment.getMapAsync(AddStore.this);
//    }

    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");
        Geocoder geocoder = new Geocoder(UbahInformasiUmum.this);
        List<Address> list = new ArrayList<>();
        if(list.size() > 0){
            Address address = list.get(0);
            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }
    }


    protected Marker createMarker(double latitude, double longitude, String title){
        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(String.valueOf(latitude)+", "+String.valueOf(longitude))
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.pinmarker2)));
    }

    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try{
            if(mLocationPermissionsGranted){

                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            LatLng position = new LatLng(currentLocation.getLatitude(),
                                    currentLocation.getLongitude());
//                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
//                                    DEFAULT_ZOOM,
//                                    "My Location");
                            mMap.addMarker(new MarkerOptions().position(position)
                                    .title("Lokasi saya"));
                            strLat = Double.toString(currentLocation.getLatitude());
                            strLong = Double.toString(currentLocation.getLongitude());

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(UbahInformasiUmum.this, "Tidak bisa mendapatkan kordinat lokasi terbaru", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
