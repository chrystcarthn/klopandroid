package appdeveloper.klop.klop.PresenterImp;

import android.util.Log;

import java.util.ArrayList;

import appdeveloper.klop.klop.API.InterfaceAPI;
import appdeveloper.klop.klop.API.RestClient;
import appdeveloper.klop.klop.Activity.DetailReportRating;
import appdeveloper.klop.klop.Activity.DetailReportReservasi;
import appdeveloper.klop.klop.Activity.Report;
import appdeveloper.klop.klop.Model.Booking;
import appdeveloper.klop.klop.Model.BookingResponse;
import appdeveloper.klop.klop.Model.Review;
import appdeveloper.klop.klop.Model.ReviewResponse;
import appdeveloper.klop.klop.Presenter.ReportPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CMDDJ on 6/8/2018.
 */

public class ReportPresenterImp implements ReportPresenter {

    private static final String TAG = "Book2";
    InterfaceAPI api;
    DetailReportReservasi detailReportReservasi;
    DetailReportRating detailReportRating;

    public ReportPresenterImp(DetailReportReservasi detailReportReservasi) {
        this.detailReportReservasi = detailReportReservasi;
        api = RestClient.createService(InterfaceAPI.class);
    }

    public ReportPresenterImp(DetailReportRating detailReportRating) {
        this.detailReportRating = detailReportRating;
        api = RestClient.createService(InterfaceAPI.class);
    }


    @Override
    public void showRate1(String strIdStore) {
        getRevRate1(strIdStore);
    }

    @Override
    public void showRate2(String strIdStore) {
        getRevRate2(strIdStore);
    }

    @Override
    public void showRate3(String strIdStore) {
        getRevRate3(strIdStore);
    }

    @Override
    public void showRate4(String strIdStore) {
        getRevRate4(strIdStore);
    }

    @Override
    public void showRate5(String strIdStore) {
        getRevRate5(strIdStore);
    }

    @Override
    public void showWaiting(String strIdStore) {
        getWaitingRes(strIdStore);
    }

    @Override
    public void showApproved(String strIdStore) {
        getApprovedRes(strIdStore);
    }

    @Override
    public void showCanceled(String strIdStore) {
        getCanceledRes(strIdStore);
    }

    @Override
    public void showRejected(String strIdStore) {
        getRejectedRes(strIdStore);
    }

    private void getWaitingRes(String strIdStore){
        Call<BookingResponse> requestCall;
        Log.d(TAG, "fetching list reservation ");
        requestCall = api.showWaiting(strIdStore);
        requestCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                if (response.isSuccessful()) {
                    final BookingResponse bookingResponse = response.body();
                    if (bookingResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        detailReportReservasi.generateReservationResponse((ArrayList<Booking>) response.body().getBooking());
                    }
                    else if (bookingResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        detailReportReservasi.warningDataNotFound();
                    }
                    else if (bookingResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        detailReportReservasi.loginTimeOut();
                    }
                }
                else {
                    detailReportReservasi.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                detailReportReservasi.ErrorConnection();
            }
        });
    }

    private void getApprovedRes(String strIdStore){
        Call<BookingResponse> requestCall;
        Log.d(TAG, "fetching list reservation ");
        requestCall = api.showApproved(strIdStore);
        requestCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                if (response.isSuccessful()) {
                    final BookingResponse bookingResponse = response.body();
                    if (bookingResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        detailReportReservasi.generateReservationResponse((ArrayList<Booking>) response.body().getBooking());
                    }
                    else if (bookingResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        detailReportReservasi.warningDataNotFound();
                    }
                    else if (bookingResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        detailReportReservasi.loginTimeOut();
                    }
                }
                else {
                    detailReportReservasi.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                detailReportReservasi.ErrorConnection();
            }
        });
    }

    private void getCanceledRes(String strIdStore){
        Call<BookingResponse> requestCall;
        Log.d(TAG, "fetching list reservation ");
        requestCall = api.showCanceled(strIdStore);
        requestCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                if (response.isSuccessful()) {
                    final BookingResponse bookingResponse = response.body();
                    if (bookingResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        detailReportReservasi.generateReservationResponse((ArrayList<Booking>) response.body().getBooking());
                    }
                    else if (bookingResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        detailReportReservasi.warningDataNotFound();
                    }
                    else if (bookingResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        detailReportReservasi.loginTimeOut();
                    }
                }
                else {
                    detailReportReservasi.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                detailReportReservasi.ErrorConnection();
            }
        });
    }

    private void getRejectedRes(String strIdStore){
        Call<BookingResponse> requestCall;
        Log.d(TAG, "fetching list reservation ");
        requestCall = api.showRejected(strIdStore);
        requestCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                if (response.isSuccessful()) {
                    final BookingResponse bookingResponse = response.body();
                    if (bookingResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        detailReportReservasi.generateReservationResponse((ArrayList<Booking>) response.body().getBooking());
                    }
                    else if (bookingResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        detailReportReservasi.warningDataNotFound();
                    }
                    else if (bookingResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        detailReportReservasi.loginTimeOut();
                    }
                }
                else {
                    detailReportReservasi.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                detailReportReservasi.ErrorConnection();
            }
        });
    }

    private void getRevRate1(String strIdOutlet){
        Call<ReviewResponse> requestCall;
        Log.d(TAG, "fetching info review "+strIdOutlet);
        requestCall = api.showRev1(strIdOutlet);
        requestCall.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if(response.isSuccessful()) {
                    final ReviewResponse ReviewResponse = response.body();
                    if (ReviewResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        detailReportRating.generateReviewResponse((ArrayList<Review>) response.body().getReview());

                    } else {
                        detailReportRating.warningNoReviewFound();
                    }
                }
                else{
                    detailReportRating.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                detailReportRating.ErrorConnectionFailed();
            }
        });
    }

    private void getRevRate2(String strIdOutlet){
        Call<ReviewResponse> requestCall;
        Log.d(TAG, "fetching info review "+strIdOutlet);
        requestCall = api.showRev2(strIdOutlet);
        requestCall.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if(response.isSuccessful()) {
                    final ReviewResponse ReviewResponse = response.body();
                    if (ReviewResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        detailReportRating.generateReviewResponse((ArrayList<Review>) response.body().getReview());

                    } else {
                        detailReportRating.warningNoReviewFound();
                    }
                }
                else{
                    detailReportRating.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                detailReportRating.ErrorConnectionFailed();
            }
        });
    }

    private void getRevRate3(String strIdOutlet){
        Call<ReviewResponse> requestCall;
        Log.d(TAG, "fetching info review "+strIdOutlet);
        requestCall = api.showRev3(strIdOutlet);
        requestCall.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if(response.isSuccessful()) {
                    final ReviewResponse ReviewResponse = response.body();
                    if (ReviewResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        detailReportRating.generateReviewResponse((ArrayList<Review>) response.body().getReview());

                    } else {
                        detailReportRating.warningNoReviewFound();
                    }
                }
                else{
                    detailReportRating.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                detailReportRating.ErrorConnectionFailed();
            }
        });
    }

    private void getRevRate4(String strIdOutlet){
        Call<ReviewResponse> requestCall;
        Log.d(TAG, "fetching info review "+strIdOutlet);
        requestCall = api.showRev4(strIdOutlet);
        requestCall.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if(response.isSuccessful()) {
                    final ReviewResponse ReviewResponse = response.body();
                    if (ReviewResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        detailReportRating.generateReviewResponse((ArrayList<Review>) response.body().getReview());

                    } else {
                        detailReportRating.warningNoReviewFound();
                    }
                }
                else{
                    detailReportRating.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                detailReportRating.ErrorConnectionFailed();
            }
        });
    }

    private void getRevRate5(String strIdOutlet){
        Call<ReviewResponse> requestCall;
        Log.d(TAG, "fetching info review "+strIdOutlet);
        requestCall = api.showRev5(strIdOutlet);
        requestCall.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if(response.isSuccessful()) {
                    final ReviewResponse ReviewResponse = response.body();
                    if (ReviewResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        detailReportRating.generateReviewResponse((ArrayList<Review>) response.body().getReview());

                    } else {
                        detailReportRating.warningNoReviewFound();
                    }
                }
                else{
                    detailReportRating.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                detailReportRating.ErrorConnectionFailed();
            }
        });
    }

}
