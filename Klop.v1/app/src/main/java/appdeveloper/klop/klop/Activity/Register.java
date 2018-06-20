package appdeveloper.klop.klop.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import appdeveloper.klop.klop.PresenterImp.RegisterPresenterImp;
import appdeveloper.klop.klop.R;

/**
 * Created by CMDDJ on 12/3/2017.
 */
public class Register  extends ActionBarActivity implements View.OnClickListener  {
    Button btnRegister;
    TextView tvLogin;
    EditText edFullname, edPhone, edPassword;
    private static final String TAG = "Register";
    private RegisterPresenterImp presenterImp;
    TextInputLayout nameWrapper, phoneWrapper, passwordWrapper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        edFullname = (EditText) findViewById(R.id.edName);
        edPhone = (EditText) findViewById(R.id.edPhone);
        edPassword = (EditText) findViewById(R.id.edPassword);

        nameWrapper = (TextInputLayout) findViewById(R.id.nameWrapper);
        phoneWrapper = (TextInputLayout) findViewById(R.id.phoneWrapper);
        passwordWrapper= (TextInputLayout) findViewById(R.id.passWrapper);


        btnRegister = (Button) findViewById(R.id.btnRegister);
        tvLogin = (TextView) findViewById(R.id.tvLogin);
        presenterImp = new RegisterPresenterImp(this);

        btnRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v == btnRegister){
            presenterImp.register(edFullname.getText().toString(), edPhone.getText().toString(), edPassword.getText().toString());
        }
        else  if(v == tvLogin){
            Intent log = new Intent(Register.this, Login.class);
            startActivity(log);
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    public void showInvalidName() {
        //edEmail.setError("Invalid Email");
        phoneWrapper.setErrorEnabled(false);
        passwordWrapper.setErrorEnabled(false);
        nameWrapper.setError("Harap diisi");
        requestFocus(edFullname);
    }

    public void showInvalidPhone() {
        //edEmail.setError("Invalid Email");
        nameWrapper.setErrorEnabled(false);
        passwordWrapper.setErrorEnabled(false);
        phoneWrapper.setError("Harap diisi minimal 9 digit");
        requestFocus(edPhone);
    }

    public void showInvalidPassword() {
        // edPassword.setError("Password cannot be empty");
        nameWrapper.setErrorEnabled(false);
        phoneWrapper.setErrorEnabled(false);
        passwordWrapper.setError("Harap diisi minimal 6 karakter");
        requestFocus(edPassword);
    }


    public void removeError() {
        // edPassword.setError("Password cannot be empty");
        nameWrapper.setErrorEnabled(false);
        phoneWrapper.setErrorEnabled(false);
        passwordWrapper.setErrorEnabled(false);
    }


    public void RegistrationSuccess()
    {
        Toast.makeText(Register.this, "Registrasi berhasil", Toast.LENGTH_SHORT).show();
        Bundle b = new Bundle();
        b.putString("phone", edPhone.getText().toString());
        Intent log = new Intent(Register.this, Login.class);
        log.putExtras(b);
        startActivity(log);
    }

    public void ErrorRegistrationFailed(){
        Toast.makeText(Register.this, "Maaf, nomor sudah terdaftar", Toast.LENGTH_SHORT).show();
    }

    public void ErrorResponseFailed(){
        Toast.makeText(Register.this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
    }

    public void ErrorConnectionFailed(){
        Toast.makeText(Register.this, "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
    }


}
