package appdeveloper.klop.klop.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import appdeveloper.klop.klop.Fragment.BookFragment;
import appdeveloper.klop.klop.Fragment.HomeFragment;
import appdeveloper.klop.klop.Fragment.ProfileFragment;
import appdeveloper.klop.klop.Fragment.StoreFragment;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.R;

public class TabbedMenu extends AppCompatActivity {

    private TextView mTextMessage;
    FragmentManager fm;
    Fragment fragment;
    private FrameLayout frame;
    private BottomNavigationView main_nav;

    private HomeFragment homeFragment;
    private BookFragment bookFragment;
//    private StoreFragment storeFragment;
    private ProfileFragment profileFragment;

    private Toolbar toolbar;
    MaterialSearchView materialSearchView;

    String[] list;
    String strIdStore;

    private SessionPreference sessions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_menu);

        sessions = new SessionPreference(this);

        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbar);

        frame = (FrameLayout) findViewById(R.id.frame);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavigation);

        homeFragment = new HomeFragment();
        bookFragment = new BookFragment();
//        storeFragment = new StoreFragment();
        profileFragment = new ProfileFragment();

        if(sessions.isUserLoggedIn() == true)
        {
            //       Toast.makeText(SplashScreen.this, "User Login Status: "+sessions.isUserLoggedIn()+",", Toast.LENGTH_SHORT).show();

                    /* Create an Intent that will start the Menu-Activity. */
            setFragment(homeFragment);

        }
        else
        {
            //    Toast.makeText(SplashScreen.this, "User Login Status: "+sessions.isUserLoggedIn()+",", Toast.LENGTH_SHORT).show();

                     /* Create an Intent that will start the Menu-Activity. */
            Intent loginFirst = new Intent(TabbedMenu.this,Login.class);
            loginFirst.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(loginFirst);
            finish();
        }


        setFragment(homeFragment);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.navigation_home :
                        setFragment(homeFragment);
                        toolbar.setTitle("Utama");
                        return true;

                    case R.id.navigation_booking :
                        setFragment(bookFragment);
                        toolbar.setTitle("Reservasi Saya");
                        return true;

//                    case R.id.navigation_store :
//                        setFragment(storeFragment);
//                        toolbar.setTitle("Kelola Outlet");
//                        return true;

                    case R.id.navigation_account :
                        setFragment(profileFragment);
                        toolbar.setTitle("Kelola Akun");
                        return true;

                }

                return false;
            }
        });

    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commit();

    }
}
