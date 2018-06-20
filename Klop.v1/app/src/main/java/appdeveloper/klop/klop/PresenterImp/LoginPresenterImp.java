package appdeveloper.klop.klop.PresenterImp;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import appdeveloper.klop.klop.API.InterfaceAPI;
import appdeveloper.klop.klop.API.RestClient;
import appdeveloper.klop.klop.Activity.Login;
import appdeveloper.klop.klop.Model.User;
import appdeveloper.klop.klop.Model.UserResponse;
import appdeveloper.klop.klop.Presenter.LoginPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CMDDJ on 12/14/2017.
 */

public class LoginPresenterImp implements LoginPresenter{

    User user;
    Login login;
    InterfaceAPI api;
    private static final String TAG = "LoginPresenterImp";

    public LoginPresenterImp(Login login) {
        this.login = login;
        api = RestClient.createService(InterfaceAPI.class);

    }

    @Override
    public void login(String strPhone, String strPassword) {
        if (!isValidPhone(strPhone) && isValidPassword(strPassword)) {
            Log.d(TAG, "ValidationPhone "+strPhone+strPassword);
            login.showInvalidPhone();
        }

        else if (!isValidPassword(strPassword) && isValidPhone(strPhone)) {
            Log.d(TAG, "ValidationPassword "+strPhone+strPassword);
            login.showInvalidPassword();
        }
        else if (!isValidPhone(strPhone) && !isValidPassword(strPassword)){
           // login.showError();
            login.showInvalidPhone();
        }
        else if(isValidPhone(strPhone) && isValidPassword(strPassword))
        {
            Log.d(TAG, "ValidationSucced "+strPhone+strPassword);
            login.removeError();
            sendPostLogin(strPhone, strPassword);
        }
    }


    private void sendPostLogin(String strPhonePost, String strPassPost) {

//        try {

            Call<UserResponse> requestCall;
            Log.d(TAG, "sendPostLogin "+strPhonePost+strPassPost);
           // login.showLoading();
            login.showSpotLoading();
            requestCall = api.login(strPhonePost, strPassPost);
            requestCall.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                    //checking if get the JSON
                    if (response.isSuccessful()) {
                        final UserResponse userResponse = response.body();
                        //check if data found
                        if (userResponse.getStatus().toString().equalsIgnoreCase("1")) {
                            user = userResponse.getUsers().get(0);
    //                        String strFullname = userResponse.getData().get(0).getFULLNAME();
 //                        Log.i(TAG, "post login submitted to API"
 //                                + ", fullname = " + strFullname
 //                                + ", respon code = " + response.code()
 //                                + ", respon body = " + response.body().toString());
                            login.getStart(user);
                            login.loginSuccess();
                            login.dismissSpotLoading();
                        } else {
//                            login.stopLoading();
                            login.dismissSpotLoading();
                            login.loginErrorUserNotFound();
                        }
                    } else if (!response.isSuccessful()) {
                        login.dismissSpotLoading();
                        Log.d(TAG, "Unable to parse JSON");
                        Log.e(TAG, response.errorBody().toString());
                        login.loginErrorResponseFailed();
                    }
                }

                //fail to connect to API
                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {
                    Log.e(TAG, "Unable to submit post to API");
                    Log.e(TAG, t.getMessage());
                    login.loginErrorConnectionFailed();
                    //
                }
            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }


    // validating email format
//    private boolean isValidEmail(String email) {
//        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
//                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//            Pattern pattern = Pattern.compile(EMAIL_PATTERN);
//            Matcher matcher = pattern.matcher(email);
//            return matcher.matches();
//
//    }

    // validating email edittext
    private boolean isFilledEmail(String email) {
        if (email != null && email.length() >= 4) {
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

    // validating password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 6) {
            return true;
        }
        return false;
    }
}
