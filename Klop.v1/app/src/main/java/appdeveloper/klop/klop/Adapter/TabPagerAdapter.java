package appdeveloper.klop.klop.Adapter;

import android.accessibilityservice.AccessibilityService;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import appdeveloper.klop.klop.Fragment.HomeFragment;
import appdeveloper.klop.klop.Fragment.MappingStoreFragment;

import static appdeveloper.klop.klop.Fragment.TabFragment.int_items;
/**
 * Created by CMDDJ on 5/4/2018.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    String[] tabarray = new String[]{"List","Map"};
    Integer tabnumber = 2;

    public TabPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                MappingStoreFragment mappingStoreFragment = new MappingStoreFragment();
                return mappingStoreFragment;

        }
        return  null;

    }

    @Override
    public int getCount() {

        return tabnumber;
       // return int_items;
    }

    public CharSequence getPageTitle(int position){
        return tabarray[position];
//        switch (position){
//            case 0:
//                return "List";
//            case 1:
//                return "Map";
//        }
    //    return null;
    }
}
