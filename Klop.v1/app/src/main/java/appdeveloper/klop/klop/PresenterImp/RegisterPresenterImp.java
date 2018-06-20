package appdeveloper.klop.klop.PresenterImp;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import appdeveloper.klop.klop.API.InterfaceAPI;
import appdeveloper.klop.klop.API.RestClient;
import appdeveloper.klop.klop.Activity.Register;
import appdeveloper.klop.klop.Model.UserResponse;
import appdeveloper.klop.klop.Presenter.RegisterPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CMDDJ on 12/14/2017.
 */

public class RegisterPresenterImp implements RegisterPresenter {
    Register register;
    InterfaceAPI api;
    private static final String TAG = "RegisterPresenterImp";

    public RegisterPresenterImp(Register register) {
        this.register = register;
        api = RestClient.createService(InterfaceAPI.class);

    }

    @Override
    public void register(String strName, String strPhone, String strPassword) {
        if (!isValidName(strName)) {

            register.showInvalidName();
        }
        else if (!isValidPhone(strPhone)) {

            register.showInvalidPhone();
        }
        else if (!isValidPassword(strPassword)) {

            register.showInvalidPassword();
        }
        else if(!isValidName(strName) && !isValidPhone(strPhone) && !isValidPassword(strPassword))
        {
            //register.showError();
            register.showInvalidName();
        }
        else if(isValidName(strName) && isValidPhone(strPhone) && isValidPassword(strPassword))
        {
            register.removeError();
            sendPostRegister(strName, strPhone, strPassword);
        }
    }


    private void sendPostRegister(String strName, String strPhone, String strPass) {
        Call<UserResponse> requestCall = null;
        requestCall = api.register(strName, strPhone, strPass);
        requestCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final UserResponse userResponse = response.body();
                    if(userResponse.getStatus().toString().equalsIgnoreCase("1")) {
//                        Log.i(TAG, "post register submitted to API"
//                                + ", respon code = " + response.code()
//                                + ", respon body = " + response.body().toString());
//
                        register.RegistrationSuccess();
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                       register.ErrorRegistrationFailed();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
                  register.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
               register.ErrorConnectionFailed();
            }
        });
    }



    // validating fullname
    private boolean isValidName(String name) {
        if (name != null && name.length() >= 1) {
            return true;
        }
        return false;
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 6) {
            return true;
        }
        return false;
    }

    // validating phone
    private boolean isValidPhone(String phone) {
        if (phone != null && phone.length() >= 9) {
            return true;
        }
        return false;
    }
}
