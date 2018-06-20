package appdeveloper.klop.klop.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import appdeveloper.klop.klop.Activity.CreateMyBooking;
import appdeveloper.klop.klop.Activity.ReservationRequest;
import appdeveloper.klop.klop.Adapter.MyBookedAdapter;
import appdeveloper.klop.klop.Model.Booking;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.BookingFragPresenterImp;
import appdeveloper.klop.klop.R;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookFragment newInstance(String param1, String param2) {
        BookFragment fragment = new BookFragment();
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

    TextView tvTest;

    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdUser;
    MyBookedAdapter adapter;
    BookingFragPresenterImp bookingFragPresenterImp;
    private RecyclerView recyclerView;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    FloatingTextButton ftbReservasi;
    TextView emptyTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_booking, container, false);

        session = new SessionPreference(getContext());
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) v.findViewById(R.id.main_swipe2);
        mWaveSwipeRefreshLayout.setWaveColor(getResources().getColor(R.color.colorPrimary));
        mWaveSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.white));

        bookingFragPresenterImp = new BookingFragPresenterImp(this);
        emptyTextView = (TextView) v.findViewById(R.id.tvEmptyBooked);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewBooking);
        ftbReservasi = (FloatingTextButton) v.findViewById(R.id.ftbReservasi);


        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                emptyTextView.setVisibility(View.GONE);
                loadRecyclerViewDataBooking();
                bookingFragPresenterImp.doesUserHaveOutlet(strIdUser);
            }
        });


        loadRecyclerViewDataBooking();
        bookingFragPresenterImp.doesUserHaveOutlet(strIdUser);
        ftbReservasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), ReservationRequest.class);
                startActivity(i);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadRecyclerViewDataBooking();
    }

    private void loadRecyclerViewDataBooking()
    {
        mWaveSwipeRefreshLayout.setRefreshing(true);
        bookingFragPresenterImp.showMyBooked(strIdUser);
    }

    public void generateBookingResponse(ArrayList<Booking> bookingArrayList){
        if(bookingArrayList != null){
            emptyTextView.setVisibility(View.GONE);
            adapter = new MyBookedAdapter(bookingArrayList, getContext());
            final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            mWaveSwipeRefreshLayout.setRefreshing(false);
        }else{
            mWaveSwipeRefreshLayout.setRefreshing(false);
            emptyTextView.setVisibility(View.VISIBLE);
        }
    }


    public void userHasNoStore() {
        ftbReservasi.setVisibility(View.GONE);
    }

    public void userHasStore() {
        ftbReservasi.setVisibility(View.VISIBLE);
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
        emptyTextView.setText("Coba beberapa saat lagi");
        emptyTextView.setVisibility(View.VISIBLE);
    }

    public void warningDataNotFound(){
        //Toast.makeText(getContext(), "Oops, data tidak ditemukan!", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
        emptyTextView.setVisibility(View.VISIBLE);
    }

    public void loginTimeOut(){
        Toast.makeText(getContext(), "Login time out!", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
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
