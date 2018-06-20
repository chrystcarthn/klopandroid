package appdeveloper.klop.klop.Activity;

import android.Manifest;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import appdeveloper.klop.klop.Fragment.CategoryFragment;
import appdeveloper.klop.klop.MapConfiguration.CustomInfoWindowAdapter;
import appdeveloper.klop.klop.Fragment.StoreFragment;

import appdeveloper.klop.klop.Model.PlaceInfo;
import appdeveloper.klop.klop.MapConfiguration.PlaceAutoCompleteAdapter;
import appdeveloper.klop.klop.Model.Store;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.StorePresenterImp;
import appdeveloper.klop.klop.R;
import dmax.dialog.SpotsDialog;

public class AddStore extends AppCompatActivity implements View.OnClickListener,
                                                            OnMapReadyCallback,
                                                            GoogleApiClient.OnConnectionFailedListener{

    SessionPreference session;
    private StorePresenterImp storePresenterImp;

    EditText edNamestore, edTelp, edAlamat, edLatitude, edLongitude, edKoordinat;
    public String strKatTemp;
//    , edNmpengelola, edNohppengelola, edNoktp;
    ImageView imgKtp;
    Button btnLanjutkan;
    ImageButton btnMap;
    private SpotsDialog progressDialog;
    Toolbar toolbar;
    TextInputLayout nameWrapper, phoneWrapper, addressWrapper, koordinatWrapper, latitudeWrapper, longitudeWrapper;
//    , namapengWrapper, telppengWrapper, noktpWrapper;

    FragmentManager fm;
    Fragment fragment;

    private static final String TAG = "MapActivity";
    float zoomLevel = 16.0f;

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));


    //widgets
    private AutoCompleteTextView mSearchText;
    private ImageView mGps, mInfo, mPlacePicker;


    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlaceAutoCompleteAdapter mPlaceAutoCompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private PlaceInfo mPlace;
    private Marker mMarker;
    private String strNameU, strAlamatU, strPhoneU, strLat, strLong, strLatOU, strLongOU, strIdUserU, strDistance, strFmtDistanceU;
    LatLng startP , endP;
    HashMap<String, String> userdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informasi_umum);

        session = new SessionPreference(this);
        userdata = session.getUserDetails();
        strIdUserU = userdata.get(SessionPreference.KEY_IDUSER);

        storePresenterImp = new StorePresenterImp(this);

        fm = getSupportFragmentManager();
        fragment = fm.findFragmentByTag("AddStore");
        progressDialog = new SpotsDialog(this, R.style.Custom);
        edNamestore = (EditText) findViewById(R.id.edName);
        edTelp = (EditText) findViewById(R.id.edTelepon);
        edAlamat = (EditText) findViewById(R.id.edAlamat);
        edLatitude = (EditText) findViewById(R.id.edLatitude);
        edLongitude = (EditText) findViewById(R.id.edLongitude);
        edKoordinat = (EditText) findViewById(R.id.edKordinat);
        btnMap = (ImageButton) findViewById(R.id.btnOpenMap);

        edNamestore.setText("");
        edAlamat.setText("");
        edTelp.setText("");
        edLatitude.setText("");
        edLongitude.setText("");
        edKoordinat.setText("");

        btnLanjutkan = (Button) findViewById(R.id.btnDataUmumLanjutkan);
        toolbar = (Toolbar) findViewById(R.id.toolbar1);

        final android.app.FragmentManager fm = getFragmentManager();
        final CategoryFragment cf = new CategoryFragment();
        cf.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        nameWrapper = (TextInputLayout) findViewById(R.id.input_layout_nama);
        phoneWrapper = (TextInputLayout) findViewById(R.id.input_layout_telepon);
        addressWrapper = (TextInputLayout) findViewById(R.id.input_layout_alamat);
        latitudeWrapper = (TextInputLayout) findViewById(R.id.input_layout_latitude);
        longitudeWrapper = (TextInputLayout) findViewById(R.id.input_layout_longitude);
        koordinatWrapper = (TextInputLayout) findViewById(R.id.input_layout_kordinat);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
});

        btnLanjutkan.setOnClickListener(this);
       // btnMap.setOnClickListener(this);
        mPlacePicker = (ImageView) findViewById(R.id.place_picker);
        getLocationPermission();

    }


    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), StoreFragment.class);
        startActivityForResult(myIntent, 0);
        return true;
    }

    public void dismissSpotLoading(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void showSpotLoading(){
        progressDialog.show();
        progressDialog.setCancelable(true);
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

        edKoordinat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(AddStore.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesRepairableException: " + e.getMessage() );
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesNotAvailableException: " + e.getMessage() );
                }
            }
        });


/*
*
* ------------------------------------------ Place Picker Widget -----------------------------------------------
*
* */

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(AddStore.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesRepairableException: " + e.getMessage() );
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesNotAvailableException: " + e.getMessage() );
                }
            }
        });

        hideSoftKeyboard();
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

    /*
    *
    *------------------------------------------- Google Maps Configuration --------------------------------
    *
    * */

    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");
        Geocoder geocoder = new Geocoder(AddStore.this);
        List<Address> list = new ArrayList<>();
        if(list.size() > 0){
            Address address = list.get(0);
            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
        }
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
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,
                                    "My Location");
                            mMap.addMarker(new MarkerOptions().position(position)
                                    .title("Lokasi saya"));
                            strLat = Double.toString(currentLocation.getLatitude());
                            strLong = Double.toString(currentLocation.getLongitude());

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(AddStore.this, "Gagal mendapatkan kordinat lokasi terbaru", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void moveCamera(LatLng latLng, float zoom, PlaceInfo placeInfo){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        mMap.clear();
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(AddStore.this));

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



    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(AddStore.this);
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

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // Disallow ScrollView to intercept touch events.
                toolbar.requestDisallowInterceptTouchEvent(true);
                break;

            case MotionEvent.ACTION_UP:
                // Allow ScrollView to intercept touch events.
                toolbar.requestDisallowInterceptTouchEvent(false);
                break;
        }

        // Handle MapView's touch events.
        super.onTouchEvent(ev);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    @Override
    public void onClick(View v) {
        if(v == edAlamat){
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

            try {
                startActivityForResult(builder.build(AddStore.this), PLACE_PICKER_REQUEST);
            } catch (GooglePlayServicesRepairableException e) {
                Log.e(TAG, "onClick: GooglePlayServicesRepairableException: " + e.getMessage() );
            } catch (GooglePlayServicesNotAvailableException e) {
                Log.e(TAG, "onClick: GooglePlayServicesNotAvailableException: " + e.getMessage() );
            }
        }  else if(v == btnLanjutkan){


            strNameU = edNamestore.getText().toString();
            strAlamatU = edAlamat.getText().toString();
            strPhoneU = edTelp.getText().toString();
            strLatOU = edLatitude.getText().toString();
            strLongOU = edLongitude.getText().toString();
            startP = new LatLng(Double.parseDouble(strLat), Double.parseDouble(strLong));
         //   Toast.makeText(this, String.valueOf(edLatitude.length()), Toast.LENGTH_SHORT).show();

            if(strLatOU.equals("")) {
                strLatOU = null;
                strLongOU = null;
                strDistance = null;
                strFmtDistanceU = null;
            }else {
                endP = new LatLng(Double.parseDouble(strLatOU), Double.parseDouble(strLongOU));
                strDistance = String.format("%.2f", CalculationByDistance(startP, endP));
                strFmtDistanceU = strDistance.toString()
                        .replace(",",".")
                        .trim();
               // Toast.makeText(this, "distance: "+strDistance, Toast.LENGTH_SHORT).show();
            }
            storePresenterImp.valInformasiUmum(strIdUserU, strNameU, strAlamatU, strPhoneU, strLatOU, strLongOU, strFmtDistanceU);
        }
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lon1 = StartP.longitude;
        double lat2 = EndP.latitude;
        double lon2 = EndP.longitude;

        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);

        return Radius * c;
    }


    public void sukses(){

        Intent i = new Intent(AddStore.this,AddStoreDetailInfo.class);
        Bundle b = new Bundle();

        b.putString("strIdUser", strIdUserU);
        b.putString("strName", strNameU);
        b.putString("strAlamat", strAlamatU);
        b.putString("strPhone", strPhoneU);
        b.putString("strLatO", strLatOU);
        b.putString("strLongO", strLongOU);
        b.putString("strFmtDistance", strFmtDistanceU);
        i.putExtras(b);
        startActivity(i);

       // storePresenterImp.getLatestStoreCreatedByUser(strIdUserU);
//        Toast.makeText(this, "alamat: "+edAlamat.getText().toString(), Toast.LENGTH_SHORT).show();
//        Toast.makeText(this, "latlong: "+edLatitude.getText().toString()+", "+edLongitude.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
     //   Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

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


    /*
        --------------------------- google places API autocomplete suggestions -----------------
    */

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            hideSoftKeyboard();

            final AutocompletePrediction item = mPlaceAutoCompleteAdapter.getItem(i);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);

        }
    };

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
           edLatitude.setText(Double.toString(place.getViewport().getCenter().latitude));
           //strLong = Double.toString(place.getViewport().getCenter().longitude);
           edLongitude.setText(Double.toString(place.getViewport().getCenter().longitude));
           edKoordinat.setText(Double.toString(place.getViewport().getCenter().latitude) +", "+Double.toString(place.getViewport().getCenter().longitude));
            places.release();
        }
    };

    /*
    *
    * ---------------------------------- Mendaftarkan Outlet - Informasi Umum -----------------------------------
    *
    * */

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void showInvalidName() {
        //edEmail.setError("Invalid Email");
        phoneWrapper.setErrorEnabled(false);
        addressWrapper.setErrorEnabled(false);
        koordinatWrapper.setErrorEnabled(false);
        nameWrapper.setError("Nama outlet harus diisi");
        requestFocus(edNamestore);
    }

    public void showInvalidPhone() {
        nameWrapper.setErrorEnabled(false);
        addressWrapper.setErrorEnabled(false);
        koordinatWrapper.setErrorEnabled(false);
        phoneWrapper.setError("Harap diisi minimal 9 digit");
        requestFocus(edTelp);
    }

    public void showInvalidAddress() {
        nameWrapper.setErrorEnabled(false);
        phoneWrapper.setErrorEnabled(false);
        koordinatWrapper.setErrorEnabled(false);
        addressWrapper.setError("Harap diisi");
        requestFocus(edAlamat);
    }

    public void showInvalidLatLong() {
       // Toast.makeText(this, "INVALID LATLNG", Toast.LENGTH_SHORT).show();
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



    public void ErrorResponseFailed(){
        Toast.makeText(AddStore.this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
    }

    public void ErrorConnectionFailed(){
        Toast.makeText(AddStore.this, "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
    }



}
