package appdeveloper.klop.klop.PresenterImp;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import appdeveloper.klop.klop.API.InterfaceAPI;
import appdeveloper.klop.klop.API.RestClient;
import appdeveloper.klop.klop.Fragment.ProfileFragment;
import appdeveloper.klop.klop.Model.User;
import appdeveloper.klop.klop.Model.UserResponse;
import appdeveloper.klop.klop.Presenter.ProfilePresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CMDDJ on 1/21/2018.
 */

public class ProfilePresenterImp implements ProfilePresenter {


    ProfileFragment profileFragment;
    InterfaceAPI api;
    User item;
    private static final String TAG = "EditProfileImp";

    public ProfilePresenterImp(ProfileFragment profileFragment) {
        this.profileFragment = profileFragment;
        api = RestClient.createService(InterfaceAPI.class);

    }

//    ======================================================= REFREESH PROFILE =====================================================

    @Override
    public void refreshProfile(String strIdUser) {
        getNewstDataProfile(strIdUser);
    }

    private void getNewstDataProfile(String strIdUser){
        Call<UserResponse> requestCall;
        Log.d(TAG, "fetching info user "+strIdUser);
        requestCall = api.getInfoUser(strIdUser);
        requestCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()) {
                    final UserResponse userResponse = response.body();
                    if (userResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        Log.d(TAG, "tes size user "+String.valueOf(response.body().getUsers().size()));
                      //  item = response.body().getData().get(0);
                       // profileFragment.fetchInfoUser(item);
                    } else if (userResponse.getStatus().toString().equalsIgnoreCase("0")) {
                      //  item = response.body().getData().get(0);
                        profileFragment.userNotFound(item);
                    } else if (userResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        profileFragment.loginTimeOut();
                    }
                }
                else{
                    profileFragment.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                profileFragment.ErrorConnection();
            }
        });
    }


//    ======================================================== EDIT PROFILE =======================================================

    @Override
    public void editProfile(String strIdUser, String strName, String strEmail, String strPassword, String strPhone, String strAvatar, String strEncodedfile) {

        if (!isFilledName(strName)) {
            profileFragment.showInvalidName();
        }
//        else if(!strEmail.equals("") || strEmail != null) {
//            if(!isValidEmail(strEmail)) {
//
//                profileFragment.showInvalidEmail();
//            }
//        }
        else if (!isValidPassword(strPassword)) {
            profileFragment.showInvalidPassword();
        }
        else if (!isValidPhone(strPhone)) {
            profileFragment.showInvalidPhone();
        }
        else if(!isFilledName(strName) && !isValidEmail(strEmail) && !isValidPassword(strPassword) && !isValidPhone(strPhone))
        {
            //register.showError();
            profileFragment.showInvalidName();

        }
        else if(isFilledName(strName) && isValidPassword(strPassword) && isValidPhone(strPhone))
        {
            profileFragment.removeError();
            Log.d(TAG, strName.toString()+strEmail.toString()+strPassword.toString()+strPhone.toString()+" Imp");
            sendPostEditProfile(strIdUser, strName, strPassword, strPhone, strEmail, strAvatar, strEncodedfile);

        }
    }



    private void sendPostEditProfile(String strIdUser, String strName, String strPass,  String strPhone, String strEmail, String strAvatar, String strEncodedfile) {

        Call<UserResponse> requestCall;
        Log.d(TAG, "sendPostEditProfile "+strIdUser+strName+strPass+strPhone+strEmail);
        requestCall = api.edituser(strIdUser, strName, strPass, strPhone, strEmail, strAvatar, strEncodedfile);
        requestCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                Log.d(TAG, "RESPON CODE" + response.code());

                //checking if get the JSON
                if(response.isSuccessful()) {
                    final UserResponse userResponse = response.body();
                    if(userResponse.getStatus().toString().equalsIgnoreCase("1")) {

                      //  item = response.body().getData().get(0);
                        profileFragment.dismissSpotLoading();
                        profileFragment.EditProfileSuccess();
                    }
                    else if(userResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        Log.d(TAG, "Terjadi kesalahan pada server");
                        profileFragment.dismissSpotLoading();
                        profileFragment.EditProfileFailed();
                    }
                    else if(userResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        Log.d(TAG, "Tidak dapat mengubah, periksa lagi data Anda");
                        profileFragment.dismissSpotLoading();
                        profileFragment.dataInvalid();
                    }
                }
                else{
                    Log.d(TAG, "Terjadi kesalahan pada server");
                    Log.e(TAG, response.errorBody().toString());
                    profileFragment.dismissSpotLoading();
                    profileFragment.ErrorResponse();
                }
            }
            //fail to connect to API
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.e(TAG, "Tidak dapat mengubah, periksa internet Anda.");
                Log.e(TAG, "status msg editProfile : "+t.getMessage());
                Log.e(TAG, "status msg editProfile2 : "+t.toString());

                t.printStackTrace();
                profileFragment.dismissSpotLoading();
                profileFragment.ErrorConnection();
            }
        });
    }

    // validating name edittext
    private boolean isFilledName(String name) {
        if (name != null && name.length() >=1) {
            return true;
        }
        return false;
    }

    // validating email format
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    // validating password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 4) {
            return true;
        }
        return false;
    }

    //validating phone
    private boolean isValidPhone(String phone) {
        if (phone == null  || phone.length() > 6 ) {
            return true;
        }
        return false;
    }

}
