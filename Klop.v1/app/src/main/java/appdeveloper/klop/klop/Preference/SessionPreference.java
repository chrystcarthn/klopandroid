package appdeveloper.klop.klop.Preference;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import appdeveloper.klop.klop.Activity.Login;
import appdeveloper.klop.klop.Model.User;

/**
 * Created by CMDDJ on 12/9/2017.
 */

public class SessionPreference {
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "KlopLoginPref";



    // All Shared Preferences Keys

    //START EMP-DATA
    private static final String IS_LOGIN = "IsUserLoggedIn";
    public static final String KEY_IDUSER = "keyIduser";
    public static final String KEY_FULLNAME = "keyFullname";
    public static final String KEY_PASSWORD = "keyPassword";
    public static final String KEY_PHONE = "keyPhone";
    public static final String KEY_EMAIL = "keyEmail";
    public static final String KEY_IDROLE = "keyIdrole";
    public static final String KEY_GENDER = "keyGender";
    public static final String KEY_AVATAR = "keyAvatar";
    //END USER-DATA

    // Constructor
    public SessionPreference(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     */
    public void createLoginSession(User user) {
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing  in pref
        editor.putString(KEY_IDUSER, user.getIDUSER());
        editor.putString(KEY_FULLNAME, user.getFULLNAME());
        editor.putString(KEY_PASSWORD, user.getPASSWORD());
        editor.putString(KEY_PHONE, user.getPHONE());
        editor.putString(KEY_EMAIL, user.getEMAIL());
        editor.putString(KEY_IDROLE, user.getIDROLE());

        editor.putString(KEY_AVATAR, user.getAVATAR());

        // commit changes
        editor.commit();
    }



    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();

        // user data
        user.put(KEY_IDUSER, pref.getString(KEY_IDUSER, null));
        user.put(KEY_FULLNAME, pref.getString(KEY_FULLNAME, null));
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_PHONE, pref.getString(KEY_PHONE, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_IDROLE, pref.getString(KEY_IDROLE, null));

        user.put(KEY_AVATAR, pref.getString(KEY_AVATAR, null));

        return user;
    }


    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }


    public void logoutUser() {
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        _context.startActivity(i);
    }
}
