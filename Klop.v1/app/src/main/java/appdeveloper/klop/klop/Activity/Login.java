package appdeveloper.klop.klop.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.HashMap;

import appdeveloper.klop.klop.Model.User;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.LoginPresenterImp;
import appdeveloper.klop.klop.R;
import dmax.dialog.SpotsDialog;

public class Login extends ActionBarActivity implements View.OnClickListener {

    Button btnLogin;
    TextView tvRegister, txtloading;
    EditText edPhone, edPassword;
    CheckBox cbTogglePw;
    SessionPreference sessions;
    SharedPreferences sp;
    public static final String IDUSER = "Id User";
    public static final String KEYEMAIL  = "Key Email";
    public static final String KEYPASSWORD  = "Key Password";

    TextInputLayout phoneWrapper, passWrapper;
    User userData;
    AVLoadingIndicatorView loading;
    AVLoadingIndicatorView loadingphotoheader;
    ScrollView whole;
    private SpotsDialog progressDialog;
    String strPhone=null;
    private LoginPresenterImp presenterImp;
    String strEmail, strPassword;
    private static final String TAG = "Login";

    SharedPreferences sharedPreferences;
    private static final String PREFER_NAME = "KlopLoginPref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        sessions = new SessionPreference(getApplicationContext());

        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        edPhone = (EditText) findViewById(R.id.edPhone);
        edPassword = (EditText) findViewById(R.id.edPassword);
        phoneWrapper = (TextInputLayout)findViewById(R.id.phoneWrapper);
        passWrapper = (TextInputLayout)findViewById(R.id.passWrapper);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        cbTogglePw = (CheckBox) findViewById(R.id.cbTogglePassword);

        loading = (AVLoadingIndicatorView) findViewById(R.id.avloadingIndicatorView);

        txtloading = (TextView)findViewById(R.id.txtloading);
        progressDialog = new SpotsDialog(this, R.style.Custom);
        presenterImp = new LoginPresenterImp(this);

        sharedPreferences = getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);

       try{
           Bundle b = getIntent().getExtras();
           strPhone = b.getString("phone");

       }catch (Exception e){e.printStackTrace();}

       if(strPhone != null){
           edPhone.setText(strPhone);
       }

        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);

        cbTogglePw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        // If it is checkec then show password else hide
                        // password
                        if (isChecked) {

                            cbTogglePw.setText(R.string.hide_pwd);// change
                            // checkbox
                            // text
                            edPassword.setInputType(InputType.TYPE_CLASS_TEXT);
                            edPassword.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            cbTogglePw.setText(R.string.show_pwd);// change
                            // checkbox
                            // text

                            edPassword.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            edPassword.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        if(v == btnLogin){

//            btnLogin.setBackgroundResource(R.drawable.star5);
//            Toast.makeText(this, "yay", Toast.LENGTH_SHORT).show();

            if(sessions.isUserLoggedIn()==true)
            {
                loginSuccess();
                finish();
            }
            else{

            //startAnim();
                presenterImp.login(edPhone.getText().toString(), edPassword.getText().toString());
             //   Toast.makeText(this, strEmail+", "+uEmail, Toast.LENGTH_SHORT).show();
               Log.d(TAG, "onClick "+edPhone.getText().toString()+edPassword.getText().toString());
            }
        }
        else if(v == tvRegister) {
            Intent reg = new Intent(Login.this, Register.class);
            startActivity(reg);
        }
       // stopAnim();

    }

    void startAnim(){
        loading.setVisibility(View.VISIBLE);
        txtloading.setVisibility(View.VISIBLE);
    }

    void stopAnim(){
        loading.setVisibility(View.GONE);
        txtloading.setVisibility(View.GONE);
    }



    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    public void dismissSpotLoading(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void showSpotLoading(){
        progressDialog.show();
        progressDialog.setCancelable(true);
//        loading = (ProgressBar) findViewById(R.id.loading);
//        loading.setVisibility(View.GONE);

//        SpotsDialog alertDialog = new SpotsDialog(Login.this);
//        alertDialog.hide();
//
//
//        if(a == true)
//        {
//           // loading.setVisibility(View.VISIBLE);
//            alertDialog.show();
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        }
//        else{
////            loading.setVisibility(View.GONE);
//
//            alertDialog.hide();
//            alertDialog.dismiss();
//            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
//        }
    }

    public void showLoading() {
        SpotsDialog alertDialog = new SpotsDialog(Login.this);
        alertDialog.show();

    }

    public void stopLoading() {
        SpotsDialog alertDialog = new SpotsDialog(Login.this);
        alertDialog.hide();

    }

    public void showInvalidPhone() {
        //edEmail.setError("Invalid Email");
        passWrapper.setErrorEnabled(false);
        phoneWrapper.setError("Harap diisi minimal 9 digit");
        requestFocus(edPhone);
    }

    public void showInvalidPassword() {
        // edPassword.setError("Password cannot be empty");
        phoneWrapper.setErrorEnabled(false);
        passWrapper.setError("Harap diisi minimal 6 karakter");
        requestFocus(edPassword);
    }

    public void removeError() {
        // edPassword.setError("Password cannot be empty");
        phoneWrapper.setErrorEnabled(false);
        passWrapper.setErrorEnabled(false);
    }

    public void loginSuccess(){
        Intent log = new Intent(Login.this, TabbedMenu.class);
        log.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(log);
    }

    public void getStart(User user){
       // Toast.makeText(Login.this, user.getIDUSER()+" \nSelamat datang " + user.getFULLNAME(), Toast.LENGTH_SHORT).show();
        sessions.createLoginSession(user);
    }

    public void loginErrorUserNotFound() {
        Toast.makeText(Login.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
    }


    public void loginErrorResponseFailed() {
        Toast.makeText(Login.this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();

    }


    public void loginErrorConnectionFailed() {
        Toast.makeText(Login.this, "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
    }
}
