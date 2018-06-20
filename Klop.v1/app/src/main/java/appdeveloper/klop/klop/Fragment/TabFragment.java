package appdeveloper.klop.klop.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import appdeveloper.klop.klop.Adapter.TabPagerAdapter;
import appdeveloper.klop.klop.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {

    public  static TabLayout tabLayout;
    public  static ViewPager viewPager;
    public  static int int_items=2;
    TabPagerAdapter tabPagerAdapter;
   // public TabLayout tabLayout;

    public TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_tab,null);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        //tabhost = (TabHost) v.findViewById(R.id.tabHost);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        //set an adpater

        tabPagerAdapter = new TabPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(tabPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
       // viewPager.setAdapter(new TabPagerAdapter( getChildFragmentManager()));

//        tabLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                tabLayout.setupWithViewPager(viewPager);
//                tabLayout.getTabAt(0).setText("List");
//                tabLayout.getTabAt(1).setText("Map");
//            }
//        });
        return v;
    }



}
