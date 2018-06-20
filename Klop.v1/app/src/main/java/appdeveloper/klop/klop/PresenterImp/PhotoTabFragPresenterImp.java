package appdeveloper.klop.klop.PresenterImp;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import appdeveloper.klop.klop.API.InterfaceAPI;
import appdeveloper.klop.klop.API.RestClient;
import appdeveloper.klop.klop.Fragment.PhotoRevTabFragment;
import appdeveloper.klop.klop.Model.Photo;
import appdeveloper.klop.klop.Model.PhotoResponse;
import appdeveloper.klop.klop.Model.StoreResponse;
import appdeveloper.klop.klop.Presenter.PhotoTabFragPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CMDDJ on 5/14/2018.
 */

public class PhotoTabFragPresenterImp implements PhotoTabFragPresenter {

    private static final String TAG = "TreatmentTag";
    InterfaceAPI api;
    Context context;
    PhotoRevTabFragment photoRevTabFragment;

    public PhotoTabFragPresenterImp(PhotoRevTabFragment photoRevTabFragment) {
        this.photoRevTabFragment = photoRevTabFragment;
        api = RestClient.createService(InterfaceAPI.class);

    }

    @Override
    public void showPhotoReview(String strIdStore) {
        getFetchingPhotoReview(strIdStore);
    }

    @Override
    public void addPhotoReview(String strIdStore, String strBanner, String strEncodedFile, String strIdUser) {
        sendPostAddPhotoReview(strIdStore, strBanner, strEncodedFile, strIdUser);
    }

    private void getFetchingPhotoReview(String strIdStore){
        Call<PhotoResponse> requestCall;
        Log.d(TAG, "fetching header outlet "+strIdStore);
        requestCall = api.getOutletPhotoHeader(strIdStore);
        requestCall.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
                if (response.isSuccessful()) {
                    final PhotoResponse photoResponse = response.body();
                    if (photoResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        photoRevTabFragment.generatePhotoResponse((ArrayList<Photo>) response.body().getPhoto());
                    }
                    else if(photoResponse.getStatus().toString().equalsIgnoreCase("0")){
                        photoRevTabFragment.warningDataNotFound();
                    }
                }
                else{
                    photoRevTabFragment.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<PhotoResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                photoRevTabFragment.ErrorConnectionFailed();
            }
        });

    }


    private void sendPostAddPhotoReview(String strIdStore, String strBanner, String strEncodedFile, String strIdUser) {
        Call<PhotoResponse> requestCall;
        Log.d(TAG, "sendPostAddPhotoRev " + strIdStore + " | "+ strEncodedFile);
        requestCall = api.addPhotoRevStore(strIdStore, strBanner, strEncodedFile, strIdUser);
        requestCall.enqueue(new Callback<PhotoResponse>() {
            @Override
            public void onResponse(Call<PhotoResponse> call, Response<PhotoResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final PhotoResponse photoResponse = response.body();
                    if(photoResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        photoRevTabFragment.addingPhotoRevSuccess();
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
//                        managePhoto.dismissSpotLoading();
                        photoRevTabFragment.addingPhotoRevFailed();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
//                    managePhoto.dismissSpotLoading();
                    photoRevTabFragment.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<PhotoResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
//                managePhoto.dismissSpotLoading();
                photoRevTabFragment.ErrorConnectionFailed();
            }
        });
    }

}
