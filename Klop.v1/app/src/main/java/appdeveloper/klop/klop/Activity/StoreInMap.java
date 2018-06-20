package appdeveloper.klop.klop.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import appdeveloper.klop.klop.Adapter.StoreAdapter;
import appdeveloper.klop.klop.Model.Store;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.HomePresenterImp;
import appdeveloper.klop.klop.R;

public class StoreInMap extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "StoreInMap";

    private static final float DEFAULT_ZOOM = 15f;
    private static final int PLACE_PICKER_REQUEST = 1;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    Toolbar toolbar;
    LatLng posisiAwal;
    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdUser, strLat, strLong;
    HomePresenterImp homePresenterImp;
    ArrayList<Store> markerOutlet = new ArrayList<Store>();
    ArrayList<String> outlet = new ArrayList<String>();
    RelativeLayout layoutDetails, rlDetail;
    TextView tvNmStore, tvAddress, tvRate, tvDetail, tvStoreStatus, tvDistance;
    String strId, linkOutletHeader;
    ImageView imgOutletHeader;

    double lat, lng;
    ArrayList<String> strDaysArraylist = new ArrayList<String>();
    ArrayList<String> strTimeOpenArraylist = new ArrayList<String>();
    ArrayList<String> strTimeClosedArraylist = new ArrayList<String>();

    static final int REQUEST_LOCATION =1;
    LocationManager locationManager;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_in_map);

        session = new SessionPreference(this);
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        getLocation();

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

        imgOutletHeader = (ImageView) findViewById(R.id.imgPhotoRev);
        tvStoreStatus = (TextView) findViewById(R.id.txtStatusStore);
        markerOutlet = new ArrayList<Store>();
        tvNmStore = (TextView) findViewById(R.id.tvNmStore);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        tvDistance = (TextView) findViewById(R.id.tvDistance);
        tvRate = (TextView) findViewById(R.id.tvRating);
        tvDetail = (TextView) findViewById(R.id.tvDetail);
        rlDetail = (RelativeLayout) findViewById(R.id.rlDetail);
        layoutDetails = (RelativeLayout) findViewById(R.id.layoutDetails);

        homePresenterImp = new HomePresenterImp(this);
        getLocationPermission();
    }


    void getLocation(){
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this,
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this,
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if(location != null){
                    double latti = location.getLatitude();
                    double longi = location.getLongitude();
                    // Toast.makeText(this, "coordinates "+latti+", "+longi, Toast.LENGTH_SHORT).show();

                    lat = latti;
                    lng = longi;
                } else
                    Toast.makeText(this, "Tidak bisa mendapatkan kordinat lokasi", Toast.LENGTH_SHORT).show();
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_LOCATION:
                getLocation();
                break;
        }
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

        mapFragment.getMapAsync(StoreInMap.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
       // Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;

        if (mLocationPermissionsGranted) {
            homePresenterImp.showListOutletInMap(strIdUser, lat, lng);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);

//
//            init();
        }
    }

    public void generateStoreResponse(final ArrayList<Store> storeArrayList){
        int i;
        final int indexId;
        for(i = 0 ; i  < storeArrayList.size() ; i++){
            createMarker(Double.valueOf(storeArrayList.get(i).getLatitude()), Double.valueOf(storeArrayList.get(i).getLongitude()), storeArrayList.get(i).getNameStore(), storeArrayList.get(i).getAddressStore());

            markerOutlet = storeArrayList;
           // Toast.makeText(StoreInMap.this, "id1 "+strId, Toast.LENGTH_SHORT).show();

        }


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
             //   Toast.makeText(StoreInMap.this, "title "+marker.getTitle().toString(), Toast.LENGTH_SHORT).show();

                if(!marker.getTitle().toString().equals("Lokasi saya")) {
                    final int indexi = searchNamaByIndex(marker.getTitle().toString());

                    // Toast.makeText(StoreInMap.this, "id2 "+indexi, Toast.LENGTH_SHORT).show();

                    layoutDetails.setVisibility(View.VISIBLE);
                    Animation expandIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.expand);
                    layoutDetails.startAnimation(expandIn);

                    strId = markerOutlet.get(indexi).getIdStore().toString();

                    try {
                        linkOutletHeader = markerOutlet.get(indexi).getBanner();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (linkOutletHeader != null) {
                        //	Toast.makeText(this, "link header 2: " + linkOutletHeader, Toast.LENGTH_SHORT).show();
                        Picasso.with(StoreInMap.this).load(linkOutletHeader).into(imgOutletHeader);
                    }


                    tvNmStore.setText(markerOutlet.get(indexi).getNameStore().toString());
                    tvAddress.setText(markerOutlet.get(indexi).getAddressStore().toString());
                    String rate = String.format("%.01f", Float.valueOf(markerOutlet.get(indexi).getRateSum().toString()))
                            .replace(",", ".")
                            .replace(",0", "")
                            .replace(".0", "")
                            .trim();

                    tvRate.setText(rate);

                    String jarak;
                    if(markerOutlet.get(indexi).getDistanceKilo() != null){
                        jarak = String.format("%.02f", Float.valueOf(markerOutlet.get(indexi).getDistanceKilo()))
                                .replace(",",".")
                                .replace(",00","")
                                .replace(".00","")
                                .trim();

                    } else {jarak = "invalid"; }

                    tvDistance.setText(jarak+" Km");

                    if (markerOutlet.get(indexi).getSchedule().size() != 0) {
                        getStatusStoreNow(markerOutlet.get(indexi));
                    }

                    tvDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Intent intent = new Intent(StoreInMap.this, ShowDetailStore.class);
                            Bundle b = new Bundle();
                            b.putString("idStore", strId);
                            b.putString("idUser", strIdUser);
                            b.putString("nmStore", tvNmStore.getText().toString());
                            b.putString("address", tvAddress.getText().toString());
                            b.putString("strLat", markerOutlet.get(indexi).getLatitude());
                            b.putString("strLong", markerOutlet.get(indexi).getLongitude());
                            intent.putExtras(b);
                            startActivity(intent);
                        }
                    });

                    return false;
                } else
                {
                    layoutDetails.setVisibility(View.GONE);
                    return false;
                }

            }
        });

        getDeviceLocation();
    }

    public int searchNamaByIndex(String title){
        for(int i=0;i<markerOutlet.size();i++){
            if(title.equals(markerOutlet.get(i).getNameStore().toString())){
                return i;
            }
        }
        return -1;
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

    private int getIndexDays(String days){
        return strDaysArraylist.indexOf(days);
    }

    private void getStatusStoreNow(Store store){
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
            storeOpen();
        } else if(store.getIsSetmanual().equals("closed")){
            storeClosed();
        }

        //  Toast.makeText(this, "TODAY "+getNameOfToday()+" index: "+intIndexDay+" dgn jam "+open+" - "+closed, Toast.LENGTH_SHORT).show();
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

    private void storeOpen(){
        tvStoreStatus.setText("BUKA");
        tvStoreStatus.setTextColor(getResources().getColor(R.color.open));
        tvStoreStatus.setBackgroundResource(R.drawable.background_open2);
    }

    private void storeClosed(){
        tvStoreStatus.setText("TUTUP");
        tvStoreStatus.setTextColor(getResources().getColor(R.color.closed));
        tvStoreStatus.setBackgroundResource(R.drawable.background_closed2);
    }

    protected Marker createMarker(double latitude, double longitude, String title, String snippet){
        return mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.flag2)));
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
                            posisiAwal =new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            CameraPosition cameraPosition = new CameraPosition.Builder()
                                    .target(posisiAwal)
                                    .zoom(13)
                                    .tilt(30)
                                    .build();
                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
//                                    DEFAULT_ZOOM,
//                                    "My Location");
                            mMap.addMarker(new MarkerOptions().position(position)
                                    .title("Lokasi saya")
                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.mylocation)));
                            strLat = Double.toString(currentLocation.getLatitude());
                            strLong = Double.toString(currentLocation.getLongitude());

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(StoreInMap.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }



    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


    public void warningDataNotFound(){
        Toast.makeText(this, "Tidak ada outlet ditemukan", Toast.LENGTH_SHORT).show();
    }


    public void ErrorResponse() {
        Toast.makeText(this, "Terjadi kesalahan pada server", Toast.LENGTH_SHORT).show();
    }

    public void ErrorConnection() {
        Toast.makeText(this, "Gagal menampilkan, periksa internet Anda.", Toast.LENGTH_SHORT).show();
    }

    public void loginTimeOut(){
        Toast.makeText(this, "Login time out!", Toast.LENGTH_SHORT).show();

    }
}
