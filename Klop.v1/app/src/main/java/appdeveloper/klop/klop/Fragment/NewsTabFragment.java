package appdeveloper.klop.klop.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import appdeveloper.klop.klop.Adapter.NewsAdapter;
import appdeveloper.klop.klop.Model.News;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.NewsTabFragPresenterImp;
import appdeveloper.klop.klop.R;
import es.dmoral.toasty.Toasty;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsTabFragment extends Fragment {

    private CoordinatorLayout mRootView;
    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdStore, strIdUser;
    FrameLayout frame;
    private RecyclerView recyclerView;
    TextView tvEmpty;
    NewsTabFragPresenterImp newsTabFragPresenterImp;
    NewsAdapter adapter;



    public NewsTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = (CoordinatorLayout) inflater.inflate(R.layout.fragment_tab_news, container, false);
        setHasOptionsMenu(true);
        session = new SessionPreference(getContext());
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        Bundle b = getActivity().getIntent().getExtras();
        strIdStore = b.getString("idStore");

        newsTabFragPresenterImp = new NewsTabFragPresenterImp(this);

        tvEmpty = (TextView) mRootView.findViewById(R.id.tvEmptyNews);

        frame = (FrameLayout) mRootView.findViewById(R.id.frameTr);
        recyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerViewNews);


        loadRecyclerViewNews();
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        return mRootView;

    }

    public static Fragment newInstance() {
        return new NewsTabFragment();
    }


    public void loadRecyclerViewNews()
    {
       newsTabFragPresenterImp.showPublishedNews(strIdStore);
    }


    public void generateNewsResponse(ArrayList<News> newsArrayList){
        tvEmpty.setVisibility(View.GONE);
        adapter = new NewsAdapter(newsArrayList, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    public void warningNoNewsFound(){
        //  frame.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.VISIBLE);
    }

    public void ErrorResponseFailed(){
        Toasty.error(getContext(), "Data tidak valid", Toast.LENGTH_SHORT, true).show();
    }


    public void ErrorConnectionFailed() {
        Toast.makeText(getContext(), "Koneksi ke server gagal", Toast.LENGTH_SHORT).show();
    }

}
