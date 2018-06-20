package appdeveloper.klop.klop.Fragment;

import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnAnimationEndListener;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.HashMap;

import appdeveloper.klop.klop.Adapter.ReviewAdapter;
import appdeveloper.klop.klop.Model.Like;
import appdeveloper.klop.klop.Model.Review;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.ReviewTabFragPresenterImp;
import appdeveloper.klop.klop.R;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

/**
 * Created by CMDDJ on 4/23/2018.
 */

public class ReviewTabFragment extends Fragment {

    private CoordinatorLayout mRootView;

    ReviewTabFragPresenterImp reviewTabFragPresenterImp;
    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdStore;
    ArrayList<Review> dataTreatments;
    private ReviewAdapter adapter;
    private RecyclerView recyclerView;
    private CardView cardViewTreatment;
    TextView tvEmpty, tvJumlahRev;

    public static final String TAG = "LIKE BUTTON";

    LikeButton thumbButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = (CoordinatorLayout) inflater.inflate(R.layout.fragment_tab_review, container, false);
        setHasOptionsMenu(true);

//        cordTabRev = (CoordinatorLayout) mRootView.findViewById(R.id.cordTabRev);

        session = new SessionPreference(getContext());
        userdata = session.getUserDetails();

        Bundle b = getActivity().getIntent().getExtras();
        strIdStore = b.getString("idStore");

        reviewTabFragPresenterImp = new ReviewTabFragPresenterImp(this);
        //	cardViewTreatment = (CardView) mRootView.findViewById(R.id.cardviewTreatment);
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerViewReview);
        tvEmpty = (TextView) mRootView.findViewById(R.id.tvEmptyRev);
        tvJumlahRev = (TextView) mRootView.findViewById(R.id.tvJumlahRev);
        loadRecyclerViewReviewByOutlet();

        return mRootView;
    }

    private void loadRecyclerViewReviewByOutlet()
    {
        SpotsDialog alertDialog = new SpotsDialog(getContext());
        alertDialog.show();

        reviewTabFragPresenterImp.showReview(strIdStore);

        alertDialog.hide();
    }


    public void showSumReview(String strSumReview){
        tvJumlahRev.setVisibility(View.VISIBLE);
        tvJumlahRev.setText("Sebanyak "+strSumReview+" ulasan mengatakan...");

    }

    public void generateReviewResponse(ArrayList<Review> reviewArrayList){
            tvEmpty.setVisibility(View.GONE);
        //  recyclerView = (RecyclerView) findViewById(R.id.recyclerViewOutletbyId);
            adapter = new ReviewAdapter(reviewArrayList, getContext());
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
    }

    public static Fragment newInstance() {
        return new ReviewTabFragment();
    }

    public void warningNoReviewFound(){
        tvEmpty.setVisibility(View.VISIBLE);
        tvJumlahRev.setVisibility(View.GONE);
    }

    public void ErrorResponseFailed(){
        Toast.makeText(getContext(), "Gagal parsing JSON", Toast.LENGTH_SHORT).show();
    }

    public void ErrorConnectionFailed() {
        Toast.makeText(getContext(), "Koneksi ke server gagal", Toast.LENGTH_SHORT).show();
    }


}
