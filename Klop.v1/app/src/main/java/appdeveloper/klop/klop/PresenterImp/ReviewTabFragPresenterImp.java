package appdeveloper.klop.klop.PresenterImp;

import android.util.Log;

import java.util.ArrayList;

import appdeveloper.klop.klop.API.InterfaceAPI;
import appdeveloper.klop.klop.API.RestClient;
import appdeveloper.klop.klop.Adapter.ReviewAdapter;
import appdeveloper.klop.klop.Fragment.ReviewTabFragment;
import appdeveloper.klop.klop.Model.Like;
import appdeveloper.klop.klop.Model.LikeResponse;
import appdeveloper.klop.klop.Model.Review;
import appdeveloper.klop.klop.Model.ReviewResponse;
import appdeveloper.klop.klop.Presenter.ReviewTabFragPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CMDDJ on 4/23/2018.
 */

public class ReviewTabFragPresenterImp implements ReviewTabFragPresenter {

    ReviewTabFragment reviewTabFragment;
    ReviewAdapter.ViewHolder reviewAdapter;

    Review item;
    private static final String TAG = "ReviewTab";
    InterfaceAPI api;
    Like item2, temp;
    int awal=0;


    public ReviewTabFragPresenterImp(ReviewTabFragment reviewTabFragment) {
        this.reviewTabFragment = reviewTabFragment;
        api = RestClient.createService(InterfaceAPI.class);

    }

    public ReviewTabFragPresenterImp(ReviewAdapter.ViewHolder reviewAdapter) {
        this.reviewAdapter = reviewAdapter;
        api = RestClient.createService(InterfaceAPI.class);

    }

    @Override
    public void showReview(String strIdStore) {
        getFetchingReviewByStore(strIdStore);
    }

    private void getFetchingReviewByStore(String strIdOutlet){
        Call<ReviewResponse> requestCall;
        Log.d(TAG, "fetching info review "+strIdOutlet);
        requestCall = api.showReviewByStore(strIdOutlet);
        requestCall.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if(response.isSuccessful()) {
                    final ReviewResponse ReviewResponse = response.body();
                    if (ReviewResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        reviewTabFragment.generateReviewResponse((ArrayList<Review>) response.body().getReview());
                        reviewTabFragment.showSumReview(response.body().getCount().toString());
                    } else {
                        reviewTabFragment.warningNoReviewFound();
                    }
                }
                else{
                    reviewTabFragment.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                reviewTabFragment.ErrorConnectionFailed();
            }
        });
    }


//    ===================================================== LIKE ==============================================================

    @Override
    public void like(String strIdReview, String strIdUser) {
        likeReview(strIdReview, strIdUser);
    }

    @Override
    public void unlike(String strIdReview, String strIdUser) {
        unLikeReview(strIdReview, strIdUser);
    }

    @Override
    public void checkLike(String strIdReview, String strIdUser) {
        checkAwalLikeOrNot(strIdReview, strIdUser);

    }

    @Override
    public void checkLikeForToggle(String strIdReview, String strIdUser) {
        checkStatusLike(strIdReview, strIdUser);
    }

    @Override
    public void changeLike(String strIdReview, String strIdUser) {
        changeLikeWithToggle(strIdReview, strIdUser);
    }

    private void likeReview(String strIdReview, String strIdUser){
        Call<LikeResponse> requestCall = null;
        Log.d(TAG, "adding likes "+strIdReview+ strIdUser);
        requestCall = api.addLike(strIdReview, strIdUser);
        requestCall.enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final LikeResponse likeResponse = response.body();
                    if(likeResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        reviewAdapter.settingLiked();
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        reviewAdapter.errorLikingReview();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
                    reviewAdapter.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
                reviewAdapter.ErrorConnectionFailed();
            }
        });
    }

    private void unLikeReview(String strIdReview, String strIdUser){
        Call<LikeResponse> requestCall = null;
        Log.d(TAG, "adding outlet "+strIdReview+ strIdUser);
        requestCall = api.unLike(strIdReview, strIdUser);
        requestCall.enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final LikeResponse likeResponse = response.body();
                    if(likeResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        reviewAdapter.settingUnliked();
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        reviewAdapter.errorUnlikingReview();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
                    reviewAdapter.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
                reviewAdapter.ErrorConnectionFailed();
            }
        });
    }

    private void checkAwalLikeOrNot(String strIdReview, String strIdUser){
        Call<LikeResponse> requestCall;
        Log.d(TAG, "fetching info review "+strIdReview+strIdUser);
        requestCall = api.checkLikeOrNot(strIdReview, strIdUser);
        requestCall.enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                if(response.isSuccessful()) {
                    final LikeResponse likeResponse = response.body();
                    if (likeResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        item2 = response.body().getLikes().get(0);
                        reviewAdapter.status(true);
                       // reviewAdapter.isLikeds();
                       // reviewAdapter.Like();
                        Log.d(TAG, "status like 1");
//                        awal=1;
                       // reviewAdapter.isLiked(item2);
                    } else {
                        reviewAdapter.status(false);
                      //  reviewAdapter.unLike();
                        Log.d(TAG, "status like 0");
//                        awal=0;
                      //  reviewAdapter.isUnlike();
                    }
                }
                else{
                    reviewAdapter.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                reviewAdapter.ErrorConnectionFailed();
            }
        });
    }

    private void checkStatusLike(String strIdReview, String strIdUser){
        Call<LikeResponse> requestCall;
        Log.d(TAG, "fetching info review "+strIdReview+strIdUser);
        requestCall = api.checkLikeOrNot(strIdReview, strIdUser);
        requestCall.enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                if(response.isSuccessful()) {
                    final LikeResponse likeResponse = response.body();
                    if (likeResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        item2 = response.body().getLikes().get(0);
                        //reviewAdapter.status2(true);
                        reviewAdapter.unLike();
                        Log.d(TAG, "status like 1");
                    } else {
                        reviewAdapter.Like();
                   //     reviewAdapter.status2(false);
                        Log.d(TAG, "status like 0");
                    }
                }
                else{
                    reviewAdapter.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                reviewAdapter.ErrorConnectionFailed();
            }
        });
    }


    private void changeLikeWithToggle(final String strIdReview, final String strIdUser){
        Call<LikeResponse> requestCall;
        Log.d(TAG, "fetching info review "+strIdReview+strIdUser);
        requestCall = api.checkLikeOrNot(strIdReview, strIdUser);
        requestCall.enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                if(response.isSuccessful()) {
                    final LikeResponse likeResponse = response.body();
                    Log.d(TAG, "REVIEWLOG check "+likeResponse.getStatus().toString());
                    if (likeResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        item2 = response.body().getLikes().get(0);
                        //reviewAdapter.doUnLikeReview();
                        unlike(strIdReview,  strIdUser);
                        Log.d(TAG, "REVIEWLOG doUnLikeReview1 "+likeResponse.getStatus().toString()+"|"+item2.getIDLIKES()+"|"+item2.getIDREVIEW()+"|"+item2.getIDUSER());

                    } else {
                       // reviewAdapter.doLikeReview();
                        like(strIdReview,  strIdUser);
                        Log.d(TAG, "REVIEWLOG doLikeReview2 "+likeResponse.getStatus().toString());

                    }
                }
                else{
                    reviewAdapter.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                reviewAdapter.ErrorConnectionFailed();
            }
        });
    }
}
