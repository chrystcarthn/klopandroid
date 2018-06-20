package appdeveloper.klop.klop.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


import com.tuesda.walker.circlerefresh.CircleRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;

import appdeveloper.klop.klop.Activity.FilteredSearching;
import appdeveloper.klop.klop.Activity.StoreInMap;
import appdeveloper.klop.klop.Adapter.StoreAdapter;
import appdeveloper.klop.klop.Adapter.StoreManageAdapter;
import appdeveloper.klop.klop.Manifest;
import appdeveloper.klop.klop.Model.Store;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.Presenter.HomePresenter;
import appdeveloper.klop.klop.PresenterImp.HomePresenterImp;
import appdeveloper.klop.klop.R;
import dmax.dialog.SpotsDialog;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener,
                                                    MenuItem.OnActionExpandListener
                                                {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdUser, strIdUser2, strTreatment, strCategory, strFacility, strDays, strPrice, strRateMin, strRateMax, strStatus;
    private StoreAdapter adapter;
    private RecyclerView recyclerView;
    HomePresenterImp homePresenterImp;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    TextView emptyTextView;
    private SpotsDialog progressDialog;
    Toolbar toolbar;
    private FloatingTextButton fabFilter, fabMap;
    ScrollView scrollView;

    double lat, lng;

    static final int REQUEST_LOCATION =1;
    LocationManager locationManager;
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
                                                    // TabHost tabhost;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);

        locationManager = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
        getLocation();

        session = new SessionPreference(getContext());
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        // mRefreshLayout = (CircleRefreshLayout) v.findViewById(R.id.refresh_layout);
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) v.findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setWaveColor(getResources().getColor(R.color.colorPrimary));
        mWaveSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.white));
        // SwipeRefreshLayout
        progressDialog = new SpotsDialog(getContext(), R.style.Custom);

        homePresenterImp = new HomePresenterImp(this);
        emptyTextView = (TextView) v.findViewById(R.id.tvEmptyHome);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewOutletbyId);


        fabFilter = (FloatingTextButton) v.findViewById(R.id.fabFilter);
        fabMap = (FloatingTextButton) v.findViewById(R.id.fabMap);

        fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FilteredSearching.class);
                startActivity(intent);
            }
        });

        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), StoreInMap.class);
                startActivity(intent);
            }
        });


        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                emptyTextView.setVisibility(View.GONE);
                loadRecyclerViewDataOutlet();
            }
        });

            loadRecyclerViewDataOutlet();

        return v;
    }



    void getLocation(){
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(getContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(getContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
               Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

               if(location != null){
                   double latti = location.getLatitude();
                   double longi = location.getLongitude();
               //    Toast.makeText(getContext(), "coordinates "+latti+", "+longi, Toast.LENGTH_SHORT).show();

                   lat = latti;
                   lng = longi;
               } else
                   Toast.makeText(getContext(), "Cannot get the coordinates", Toast.LENGTH_SHORT).show();
            }else{
                ActivityCompat.requestPermissions(getActivity(),
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(getActivity(),
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

    public void generateStoreResponse(ArrayList<Store> storeArrayList){
        if(storeArrayList != null){
            recyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
            adapter = new StoreAdapter(storeArrayList, getContext());
            final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

            mWaveSwipeRefreshLayout.setRefreshing(false);
        }else{
            mWaveSwipeRefreshLayout.setRefreshing(false);
            emptyTextView.setVisibility(View.VISIBLE);
        }
    }


    private void loadRecyclerViewDataOutlet()
    {
        mWaveSwipeRefreshLayout.setRefreshing(true);
        homePresenterImp.showListOutletVerified(strIdUser, lat, lng);
    }


    public void dismissSpotLoading(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void showSpotLoading(){
        progressDialog.show();
        progressDialog.setCancelable(true);
    }



    public void loginTimeOut(){
        Toast.makeText(getContext(), "Login time out!", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }

    public void warningDataNotFound(){
      //  Toast.makeText(getContext(), "Oops, data tidak ditemukan!", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
        emptyTextView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    public void ErrorResponse() {
        Toast.makeText(getContext(), "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
        emptyTextView.setText("Coba lagi beberapa saat");
        emptyTextView.setVisibility(View.VISIBLE);
    }


    public void ErrorConnection() {
        Toast.makeText(getContext(), "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
        emptyTextView.setText("Coba lagi beberapa saat");
        emptyTextView.setVisibility(View.VISIBLE);
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(HomeFragment.this);
        searchView.setQueryHint("Ketik nama outlet...");

        super.onCreateOptionsMenu(menu, inflater);
        super.onCreateOptionsMenu(menu, inflater);
    }


//    =============================================================== search view ===========================================================


    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
//            mRefreshLayout.finishRefreshing();
            mWaveSwipeRefreshLayout.setRefreshing(false);
           // emptyTextView.setVisibility(View.VISIBLE);
            return false;
        }
        emptyTextView.setVisibility(View.GONE);
        doSearchStoreByKeyword(newText);

        return false;
    }

    public void resetSearch() {

        emptyTextView.setVisibility(View.GONE);
        loadRecyclerViewDataOutlet();
    }

    public void doSearchStoreByKeyword(String strKeyword){
        SpotsDialog alertDialog = new SpotsDialog(getContext());
        alertDialog.show();

      mWaveSwipeRefreshLayout.setRefreshing(true);
        homePresenterImp.searchListOutletVerifiedByKeyword(strIdUser, strKeyword, lat, lng);
       mWaveSwipeRefreshLayout.setRefreshing(false);
        alertDialog.hide();
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
}
