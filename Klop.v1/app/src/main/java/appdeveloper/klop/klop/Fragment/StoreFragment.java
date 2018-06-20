package appdeveloper.klop.klop.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import appdeveloper.klop.klop.Activity.AddStore;
import appdeveloper.klop.klop.Adapter.StoreAdapter;
import appdeveloper.klop.klop.Model.Store;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.StorePresenterImp;
import appdeveloper.klop.klop.R;
import dmax.dialog.SpotsDialog;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StoreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */


public class StoreFragment extends Fragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public StoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoreFragment newInstance(String param1, String param2) {
        StoreFragment fragment = new StoreFragment();
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

    private static final String URL_DATA = "";
//    private RecyclerView recyclerView;
//    private RecyclerView.Adapter adapter;
    private StorePresenterImp showListOutletPresenterImp;
//    ArrayList<Store> listOutlet;
    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdUser;
//    private List<Store> storeList;
//    private ListView listKontenView;

    private StoreAdapter adapter;
    private RecyclerView recyclerView;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    TextView emptyTextView;
    private FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_store, container, false);
        setHasOptionsMenu(true);

        session = new SessionPreference(getContext());
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) v.findViewById(R.id.main_swipe2);
        mWaveSwipeRefreshLayout.setWaveColor(getResources().getColor(R.color.colorPrimary));
        mWaveSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.white));
        emptyTextView = (TextView) v.findViewById(R.id.tvEmptyStr);
        showListOutletPresenterImp = new StorePresenterImp(this);
        fab = (FloatingActionButton) v.findViewById(R.id.fabAddStore);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewOutletbyId);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                emptyTextView.setVisibility(View.GONE);
                loadRecyclerViewDataOutlet();
            }
        });
   //     storeList = new ArrayList<>();


        loadRecyclerViewDataOutlet();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(getContext(), AddStore.class);
                startActivity(intent);
//                Intent i = new Intent(Main.this,AddStoreDetailInfo.class);
//                startActivity(i);
            }
        });


        // Inflate the layout for this fragment
        return v;
    }

    public void noOutletRegistered(){
        Toast.makeText(getContext(), "Oops, data tidak ditemukan!", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
        emptyTextView.setVisibility(View.VISIBLE);
    }

    public void loginTimeOut(){
        Toast.makeText(getContext(), "Login time out!", Toast.LENGTH_SHORT).show();
    }

    public void generateStoreResponse(ArrayList<Store> storeArrayList){
      //  recyclerView = (RecyclerView) findViewById(R.id.recyclerViewOutletbyId);
        if(storeArrayList != null){
            emptyTextView.setVisibility(View.GONE);
            adapter = new StoreAdapter(storeArrayList, getContext());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            // layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
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
        showListOutletPresenterImp.showListOutletByIdUser(strIdUser, -7.7746817, 110.4159096);
    }

    public void showListOutletSuccess(){
        Toast.makeText(getContext(), "List Outlet", Toast.LENGTH_SHORT).show();


    }

    public void getDetailStore(List<Store> listStore){
        for(int i=0 ; i<=listStore.size() ; i++){
            listStore.get(i).getNameStore().toString();
            listStore.get(i).getAddressStore().toString();
        }


    }

    public void showEmptyListOutlet() {
        Toast.makeText(getContext(), "Tidak ada outlet yang terdaftar", Toast.LENGTH_SHORT).show();
    }


    public void ErrorResponseFailed() {
        Toast.makeText(getContext(), "Kesalahan pada API", Toast.LENGTH_SHORT).show();
    }

    public void ErrorResponse() {
        Toast.makeText(getContext(), "Terjadi kesalahan pada server", Toast.LENGTH_SHORT).show();
    }


    public void ErrorConnection() {
        Toast.makeText(getContext(), "Tidak dapat mengubah, periksa internet Anda.", Toast.LENGTH_SHORT).show();
    }


    public void ErrorConnectionFailed() {
        Toast.makeText(getContext(), "Koneksi ke server gagal", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
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
        searchView.setOnQueryTextListener(StoreFragment.this);
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
        doSearchUserStoreByKeyword(newText);

        return false;
    }

    public void resetSearch() {

        emptyTextView.setVisibility(View.GONE);
        loadRecyclerViewDataOutlet();
    }

    public void doSearchUserStoreByKeyword(String strKeyword){
        SpotsDialog alertDialog = new SpotsDialog(getContext());
        alertDialog.show();

        mWaveSwipeRefreshLayout.setRefreshing(true);
        showListOutletPresenterImp.searchListUserOutletByKeyword(strIdUser, strKeyword, -7.7746817, 110.4159096);
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
