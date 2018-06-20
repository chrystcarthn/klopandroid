package appdeveloper.klop.klop.PresenterImp;

import android.util.Log;

import appdeveloper.klop.klop.API.InterfaceAPI;
import appdeveloper.klop.klop.API.RestClient;
import appdeveloper.klop.klop.Activity.AddReviewRate;
import appdeveloper.klop.klop.Model.ReviewResponse;
import appdeveloper.klop.klop.Presenter.ReviewPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CMDDJ on 5/16/2018.
 */

public class ReviewPresenterImp implements ReviewPresenter {

    InterfaceAPI api;
    private static final String TAG = "Review";
    AddReviewRate addReviewRate;

    public ReviewPresenterImp(AddReviewRate addReviewRate) {
        this.addReviewRate = addReviewRate;
        api = RestClient.createService(InterfaceAPI.class);

    }

    @Override
    public void addReview(String strIdStore, String strIdUser, String strTextReview, String strRate) {
        if (!isValidStore(strIdStore)) {
            addReviewRate.showInvalidStore();
        }
        else if (!isValidUser(strIdUser)) {
            addReviewRate.showInvalidUser();
        }
        else if (!isValidTextReview(strTextReview)) {
            addReviewRate.showInvalidTextReview();
        }
        else if (!isValidRate(strRate)) {
            addReviewRate.showInvalidRate();
        }
        else if(!isValidStore(strIdStore) & !isValidUser(strIdUser) & !isValidTextReview(strTextReview) & !isValidRate(strRate))
        {
            addReviewRate.showInvalidStore();
        }
        else if(isValidStore(strIdStore) & isValidUser(strIdUser) & isValidTextReview(strTextReview) & isValidRate(strRate))
        {
            addReviewRate.removeError();
            //   addStore.sukses();
            sendPostReview(strIdStore, strIdUser, strTextReview, strRate);

        }
    }

    private boolean isValidStore(String strIdStore){
        if(strIdStore != null || !strIdStore.equals(null) || !strIdStore.equals(""))
            return true;
        else return false;
    }

    private boolean isValidUser(String strIdUser){
        if(strIdUser != null || !strIdUser.equals(null) || !strIdUser.equals(""))
            return true;
        else return false;
    }

    private boolean isValidTextReview(String strTextReview){
        if(strTextReview.length() > 0)
            return true;
        else return false;
    }

    private boolean isValidRate(String strRate){
        if(strRate != null)
            return true;
        else return false;
    }

    private void sendPostReview(String strIdStore, String strIdUser, String strTextReview, String strRate){
        addReviewRate.showSpotLoading();
        Call<ReviewResponse> requestCall = null;
        Log.d(TAG, "adding outlet "+strIdStore+strIdUser+strTextReview+strRate);
        requestCall = api.addReview(strIdStore, strIdUser, strTextReview, strRate);
        requestCall.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final ReviewResponse storeResponse = response.body();
                    if(storeResponse.getStatus().toString().equalsIgnoreCase("1")) {

                       // addReviewRate.dismissSpotLoading();
                        addReviewRate.AddingReviewSuccess();
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        addReviewRate.dismissSpotLoading();
                        addReviewRate.ErrorAddingReview();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
                    addReviewRate.dismissSpotLoading();
                    addReviewRate.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
                addReviewRate.dismissSpotLoading();
                addReviewRate.ErrorConnectionFailed();
            }
        });
    }

//    =================================================== UPDATE RATE OUTLET ====================================================

    @Override
    public void updateRateStore(String strIdStore) {
        updateRating(strIdStore);
    }

    private void updateRating(String strIdStore){
        Call<ReviewResponse> requestCall;
        Log.d(TAG, "sendPostEditProfile "+strIdStore);
        requestCall = api.updateRating(strIdStore);
        requestCall.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                Log.d(TAG, "RESPON CODE" + response.code());

                //checking if get the JSON
                if(response.isSuccessful()) {
                    final ReviewResponse userResponse = response.body();
                    if(userResponse.getStatus().toString().equalsIgnoreCase("1")) {

                        //  item = response.body().getData().get(0);
                        addReviewRate.dismissSpotLoading();
                        addReviewRate.submitReviewRateSuccess();
                    }
                    else if(userResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        Log.d(TAG, "Terjadi kesalahan pada server");
                        addReviewRate.dismissSpotLoading();
                        addReviewRate.submitReviewRateFailed();
                    }
                    else if(userResponse.getStatus().toString().equalsIgnoreCase("2")) {
                        Log.d(TAG, "Tidak dapat mengubah, periksa lagi data Anda");
                        addReviewRate.dismissSpotLoading();
                        addReviewRate.noRateFound();
                    }
                    else if(userResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        Log.d(TAG, "Tidak dapat mengubah, periksa lagi data Anda");
                        addReviewRate.dismissSpotLoading();
                        addReviewRate.dataInvalid();
                    }
                }
                else{
                    Log.d(TAG, "Terjadi kesalahan pada server");
                    Log.e(TAG, response.errorBody().toString());
                    addReviewRate.dismissSpotLoading();
                    addReviewRate.ErrorResponseFailed();
                }
            }
            //fail to connect to API
            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e(TAG, "Tidak dapat mengubah, periksa internet Anda.");
                Log.e(TAG, "status msg editProfile : "+t.getMessage());
                Log.e(TAG, "status msg editProfile2 : "+t.toString());

                t.printStackTrace();
                addReviewRate.dismissSpotLoading();
                addReviewRate.ErrorConnectionFailed();
            }
        });
    }

}
