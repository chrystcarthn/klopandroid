package appdeveloper.klop.klop.PresenterImp;

import android.util.Log;

import java.util.ArrayList;

import appdeveloper.klop.klop.API.InterfaceAPI;
import appdeveloper.klop.klop.API.RestClient;
import appdeveloper.klop.klop.Activity.DetailBookedItem;
import appdeveloper.klop.klop.Activity.DetailReservationItem;
import appdeveloper.klop.klop.Activity.Report;
import appdeveloper.klop.klop.Activity.ReservationRequest;
import appdeveloper.klop.klop.Fragment.Book2Fragment;
import appdeveloper.klop.klop.Fragment.Book3Fragment;
import appdeveloper.klop.klop.Fragment.BookFragment;
import appdeveloper.klop.klop.Model.Booking;
import appdeveloper.klop.klop.Model.BookingDetail;
import appdeveloper.klop.klop.Model.BookingDetailResponse;
import appdeveloper.klop.klop.Model.BookingResponse;
import appdeveloper.klop.klop.Model.StoreResponse;
import appdeveloper.klop.klop.Model.Treatment;
import appdeveloper.klop.klop.Model.TreatmentResponse;
import appdeveloper.klop.klop.Presenter.BookingFragPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CMDDJ on 5/26/2018.
 */

public class BookingFragPresenterImp implements BookingFragPresenter {

    private static final String TAG = "Book2";
    InterfaceAPI api;
    Booking booking;
    String strIdBooking;
    Book2Fragment book2Fragment;
    BookFragment bookFragment;
    Book3Fragment book3Fragment;
    DetailBookedItem detailBookedItem;
    DetailReservationItem detailReservationItem;
    ReservationRequest reservationRequest;
    Report report;

    String strFormattedTr;
    String strFormattedQty;
    String strFormattedPrice;
    String strFormattedSub;

    public BookingFragPresenterImp(Book2Fragment book2Fragment) {
        this.book2Fragment = book2Fragment;
        api = RestClient.createService(InterfaceAPI.class);
    }


    public BookingFragPresenterImp(BookFragment bookFragment) {
        this.bookFragment = bookFragment;
        api = RestClient.createService(InterfaceAPI.class);
    }


    public BookingFragPresenterImp(Book3Fragment book3Fragment) {
        this.book3Fragment = book3Fragment;
        api = RestClient.createService(InterfaceAPI.class);
    }

    public BookingFragPresenterImp(DetailBookedItem detailBookedItem) {
        this.detailBookedItem = detailBookedItem;
        api = RestClient.createService(InterfaceAPI.class);
    }

    public BookingFragPresenterImp(DetailReservationItem detailReservationItem) {
        this.detailReservationItem = detailReservationItem;
        api = RestClient.createService(InterfaceAPI.class);
    }

    public BookingFragPresenterImp(ReservationRequest reservationRequest) {
        this.reservationRequest = reservationRequest;
        api = RestClient.createService(InterfaceAPI.class);
    }

    public BookingFragPresenterImp(Report report) {
        this.report = report;
        api = RestClient.createService(InterfaceAPI.class);
    }



    @Override
    public void showListTreatment(String strIdStore) {
        showListTr(strIdStore);
    }


    private void showListTr(String strIdStore){
        Call<TreatmentResponse> requestCall;
        Log.d(TAG, "fetching info outlet "+strIdStore);
        requestCall = api.showTreatmentByStore(strIdStore);
        requestCall.enqueue(new Callback<TreatmentResponse>() {
            @Override
            public void onResponse(Call<TreatmentResponse> call, Response<TreatmentResponse> response) {
                if(response.isSuccessful()) {
                    final TreatmentResponse treatmentResponse = response.body();
                    if (treatmentResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        book2Fragment.generateBookTreatmentResponse((ArrayList<Treatment>) response.body().getTreatment());
                    } else {
                        book2Fragment.warningNoTreatmentFound();
                    }
                }else{
                    book2Fragment.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<TreatmentResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                book2Fragment.ErrorConnectionFailed();
            }
        });
    }

//    =============================================== tampil reservasi saya ======================================================


    @Override
    public void showMyBooked(String strIdUser) {
        getFetchingListMyBooked(strIdUser);
    }


    private void getFetchingListMyBooked(String strIdUser){
        Call<BookingResponse> requestCall;
        Log.d(TAG, "fetching list booking ");
        requestCall = api.showListAllBooking(strIdUser);
        requestCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                if (response.isSuccessful()) {
                    final BookingResponse bookingResponse = response.body();
                    if (bookingResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        bookFragment.generateBookingResponse((ArrayList<Booking>) response.body().getBooking());
                    }
                    else if (bookingResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        bookFragment.warningDataNotFound();
                    }
                    else if (bookingResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        bookFragment.loginTimeOut();
                    }
                }
                else {
                    bookFragment.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                bookFragment.ErrorConnection();
            }
        });
    }

//    ============================================== buat reservasi baru ===========================================


    public boolean isValidBooking(String strIdStore, String strIdUser, String strDate, String strTime, String strNmGuest, String strTelpGuest, String strPeople, String strTotal){
        if(!strIdStore.equals(null) || !strIdStore.equals("") ||
                !strIdUser.equals(null) || !strIdUser.equals("") ||
                !strDate.equals(null) || !strDate.equals("") ||
                !strTime.equals(null) || !strTime.equals("") ||
                !strNmGuest.equals(null) || !strNmGuest.equals("") ||
                !strTelpGuest.equals(null) || !strTelpGuest.equals("") ||
                !strPeople.equals(null) || !strPeople.equals("") ||
                !strTotal.equals(null) || !strTotal.equals("")
                ){
            return true;
        }else{

            return false;}
    }


    @Override
    public void addNewBooked(String strIdStore, String strIdUser, String strDate, String strTime, String strNmGuest, String strTelpGuest, String strPeople, String strTotal) {
        if(isValidBooking(strIdStore, strIdUser, strDate, strTime, strNmGuest, strTelpGuest, strPeople, strTotal)){
            sendPostNewBooking(strIdStore, strIdUser, strDate, strTime, strNmGuest, strTelpGuest, strPeople, strTotal);
        } else book3Fragment.dataInValid1();
    }


    private void sendPostNewBooking(String strIdStore, String strIdUser, String strDate, String strTime, String strNmGuest, String strTelpGuest, String strPeople, String strTotal) {
        book3Fragment.showSpotLoading();
        Call<BookingResponse> requestCall = null;
        Log.d(TAG, "adding booking "+strIdStore+"|"+strIdUser+"|"+strDate+"|"+strTime+"|"+strNmGuest+"|"+strTelpGuest+"|"+strPeople+"|"+strTotal);
        requestCall = api.addBooking(strIdStore, strIdUser, strDate, strTime, strNmGuest, strTelpGuest, strPeople, strTotal);
        requestCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final BookingResponse bookingResponse = response.body();
                    if(bookingResponse.getStatus().toString().equalsIgnoreCase("1")) {

                        book3Fragment.addingBookingSuccess();
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        book3Fragment.dismissSpotLoading();
                        book3Fragment.errorAddingBooking();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
                    book3Fragment.dismissSpotLoading();
                    book3Fragment.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
                book3Fragment.dismissSpotLoading();
                book3Fragment.ErrorConnectionFailed();
            }
        });
    }

// ======================================================= tambah detail ===============================================================

    public boolean isValidDetail(String strIdBooking, ArrayList<String> strIdTr, ArrayList<String> strQty, ArrayList<String> strPrice, ArrayList<String> strSub) {
        if( !strIdBooking.equals(null) || !strIdBooking.equals("") ||
                (strIdTr.size() == 0 || strQty.size() == 0 || strPrice.size() == 0 || strSub.size() == 0) ||
                (strIdTr.size() != strQty.size() & strIdTr.size() != strPrice.size() & strIdTr.size() != strSub.size())) {
            book3Fragment.dismissSpotLoading();
            return true;
        }else{
            book3Fragment.dataInValid2();
            return false;
        }
    }


    public void formatTheArraylist(ArrayList<String> strIdTr, ArrayList<String> strQty, ArrayList<String> strPrice, ArrayList<String> strSub) {
        strFormattedTr = strIdTr.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .trim();

        strFormattedQty = strQty.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .trim();

        strFormattedPrice = strPrice.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .trim();

        strFormattedSub = strSub.toString()
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .trim();

    }

    @Override
    public void addDetailBooked(String strIdBooking, ArrayList<String> strIdTr, ArrayList<String> strQty, ArrayList<String> strPrice, ArrayList<String> strSub) {
        if(isValidDetail(strIdBooking, strIdTr, strQty, strPrice, strSub)){
            formatTheArraylist(strIdTr, strQty, strPrice, strSub);
            sendPostDetailBooked(strIdBooking, strFormattedTr, strFormattedQty, strFormattedPrice, strFormattedSub);
        } else book3Fragment.dataInValid3();
    }


    private void sendPostDetailBooked(final String strIdBooking, String strIdTr, String strQty, String strPrice, String strSub) {
        Call<BookingResponse> requestCall = null;
        requestCall = api.addDetailBooking(strIdBooking, strIdTr, strQty, strPrice, strSub);
        requestCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final BookingResponse bookingResponse = response.body();
                    if(bookingResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        book3Fragment.addingDetailBookedSuccess();
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");
                        book3Fragment.dismissSpotLoading();
                        book3Fragment.addingDetailBookedFailed();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
                    book3Fragment.dismissSpotLoading();
                    book3Fragment.ErrorConnectionFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
                book3Fragment.dismissSpotLoading();
                book3Fragment.ErrorConnectionFailed();
            }
        });
    }


//    ==================================================== get 1 last booking =======================================================

    @Override
    public void getLatestBookingCreatedByUser(String strIdUser) {
        getLatestIdBookedCreated(strIdUser);
    }

    private void getLatestIdBookedCreated(String strIdUser){
        Call<BookingResponse> requestCall;
        Log.d(TAG, "fetching last outlet "+strIdUser);
        requestCall = api.getLatestBooked(strIdUser);
        requestCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                if(response.isSuccessful()) {
                    final BookingResponse bookingResponse = response.body();
                    if(bookingResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        Log.d(TAG, "ID booking"+String.valueOf(bookingResponse.getStatus().toString()));
                        Log.d(TAG, "ID booking "+String.valueOf(bookingResponse.getBooking().get(0).getIdStore()));

                        strIdBooking = response.body().getBooking().get(0).getIdBooking();
                        book3Fragment.doAddDetail(strIdBooking);
                    }
                    else{
                        Log.d(TAG, "Tidak bisa mengambil JSON");
                        Log.d(TAG, "ID booking "+String.valueOf(response.body().getBooking().get(0).getIdStore()));
                        Log.d(TAG, "ID booking "+String.valueOf(bookingResponse.getStatus().toString()));

                        book3Fragment.ErrorResponseFailed();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
                    book3Fragment.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                book3Fragment.ErrorConnectionFailed();
            }
        });

    }

//    ================================================= tampil detail ===================================================

    @Override
    public void showMyBookedDetail(String strIdBooked) {
        getFetchingMyCart(strIdBooked);
    }

    private void getFetchingMyCart(String strIdBooked){
        Call<BookingDetailResponse> requestCall;
        Log.d(TAG, "fetching list cart ");
        requestCall = api.showListAllDetail(strIdBooked);
        requestCall.enqueue(new Callback<BookingDetailResponse>() {
            @Override
            public void onResponse(Call<BookingDetailResponse> call, Response<BookingDetailResponse> response) {
                if (response.isSuccessful()) {
                    final BookingDetailResponse bookingDetailResponse = response.body();
                    if (bookingDetailResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        detailBookedItem.generateBookingDetailResponse((ArrayList<BookingDetail>) response.body().getBookingDetail());
                    }
                    else if (bookingDetailResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        detailBookedItem.warningDataNotFound();
                    }
                    else if (bookingDetailResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        detailBookedItem.loginTimeOut();
                    }
                }
                else {
                    detailBookedItem.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<BookingDetailResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                detailBookedItem.ErrorConnection();
            }
        });
    }

//    ================================================ tampil reservasi =====================================================

    @Override
    public void showMyReservationDetail(String strIdBooking) {
        getFetchingMyCart2(strIdBooking);
    }


    private void getFetchingMyCart2(String strIdBooked){
        Call<BookingDetailResponse> requestCall;
        Log.d(TAG, "fetching list cart ");
        requestCall = api.showListAllDetail(strIdBooked);
        requestCall.enqueue(new Callback<BookingDetailResponse>() {
            @Override
            public void onResponse(Call<BookingDetailResponse> call, Response<BookingDetailResponse> response) {
                if (response.isSuccessful()) {
                    final BookingDetailResponse bookingDetailResponse = response.body();
                    if (bookingDetailResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        detailReservationItem.generateBookingDetailResponse((ArrayList<BookingDetail>) response.body().getBookingDetail());
                    }
                    else if (bookingDetailResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        detailReservationItem.warningDataNotFound();
                    }
                    else if (bookingDetailResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        detailReservationItem.loginTimeOut();
                    }
                }
                else {
                    detailReservationItem.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<BookingDetailResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                detailReservationItem.ErrorConnection();
            }
        });
    }

//    ========================================== does user have outlet? ===========================================


    @Override
    public void doesUserHaveOutlet(String strIdUser) {
        checkUserHasOutlet(strIdUser);
    }

    private void checkUserHasOutlet(String strIdUser){
        Call<StoreResponse> requestCall;
        Log.d(TAG, "fetching list cart ");
        requestCall = api.doesUserHaveOutlet(strIdUser);
        requestCall.enqueue(new Callback<StoreResponse>() {
            @Override
            public void onResponse(Call<StoreResponse> call, Response<StoreResponse> response) {
                if (response.isSuccessful()) {
                    final StoreResponse storeResponse = response.body();
                    if (storeResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        bookFragment.userHasStore();
                    }
                    else if (storeResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        bookFragment.userHasNoStore();
                    }
                    else if (storeResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        bookFragment.loginTimeOut();
                    }
                }
                else {
                    bookFragment.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<StoreResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                bookFragment.ErrorConnection();
            }
        });
    }


//    ================================================== show reservation request ===============================================

    @Override
    public void showReservationReq(String strIdOwner) {
        getReservationRequest(strIdOwner);
    }

    private void getReservationRequest(String strIdOwner){
        Call<BookingResponse> requestCall;
        Log.d(TAG, "fetching list reservation ");
        requestCall = api.showAllRequest(strIdOwner);
        requestCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                if (response.isSuccessful()) {
                    final BookingResponse bookingResponse = response.body();
                    if (bookingResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        reservationRequest.generateReservationResponse((ArrayList<Booking>) response.body().getBooking());
                    }
                    else if (bookingResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        reservationRequest.warningDataNotFound();
                    }
                    else if (bookingResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        reservationRequest.loginTimeOut();
                    }
                }
                else {
                    reservationRequest.ErrorResponse();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                reservationRequest.ErrorConnection();
            }
        });
    }


//    ================================================= ACTION RESERVATION ====================================================

    @Override
    public void approveRes(String strIdBooking) {
        approving(strIdBooking);
    }

    @Override
    public void cancelRes(String strIdBooking) {
        canceling(strIdBooking);
    }

    @Override
    public void dropRes(String strIdBooking) {
        dropping(strIdBooking);
    }

    @Override
    public void declineRes(String strIdBooking) {
        declining(strIdBooking);
    }



    private void approving(String strIdBooking){
        Call<BookingResponse> requestCall;
        Log.d(TAG, "update status booking "+strIdBooking);
        requestCall = api.terimaReservasi(strIdBooking);
        requestCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                Log.d(TAG, "RESPON CODE" + response.code());
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final BookingResponse bookingResponse = response.body();
                    if(bookingResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        detailReservationItem.dismissSpotLoading();
                        detailReservationItem.reqApproved();
                    }
                    else if(bookingResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        detailReservationItem.dismissSpotLoading();
                        detailReservationItem.errorAction();
                    }
                    else if(bookingResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        detailReservationItem.dismissSpotLoading();
                        detailReservationItem.idBookingInValid();
                    }
                }
                else{
                    Log.d(TAG, "Terjadi kesalahan pada server");
                    Log.e(TAG, response.errorBody().toString());
                    detailReservationItem.dismissSpotLoading();
                    detailReservationItem.ErrorResponse();
                }
            }
            //fail to connect to API
            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                t.printStackTrace();
                detailReservationItem.dismissSpotLoading();
                detailReservationItem.ErrorConnection();
            }
        });
    }

    private void declining(String strIdBooking){
        Call<BookingResponse> requestCall;
        Log.d(TAG, "update status booking "+strIdBooking);
        requestCall = api.tolakReservasi(strIdBooking);
        requestCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                Log.d(TAG, "RESPON CODE" + response.code());
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final BookingResponse bookingResponse = response.body();
                    if(bookingResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        detailReservationItem.dismissSpotLoading();
                        detailReservationItem.reqDeclined();
                    }
                    else if(bookingResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        detailReservationItem.dismissSpotLoading();
                        detailReservationItem.errorAction();
                    }
                    else if(bookingResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        detailReservationItem.dismissSpotLoading();
                        detailReservationItem.idBookingInValid();
                    }
                }
                else{
                    Log.d(TAG, "Terjadi kesalahan pada server");
                    Log.e(TAG, response.errorBody().toString());
                    detailReservationItem.dismissSpotLoading();
                    detailReservationItem.ErrorResponse();
                }
            }
            //fail to connect to API
            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                t.printStackTrace();
                detailReservationItem.dismissSpotLoading();
                detailReservationItem.ErrorConnection();
            }
        });
    }

    private void canceling(String strIdBooking){
        Call<BookingResponse> requestCall;
        Log.d(TAG, "update status booking "+strIdBooking);
        requestCall = api.batalkanReservasi(strIdBooking);
        requestCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                Log.d(TAG, "RESPON CODE" + response.code());
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final BookingResponse bookingResponse = response.body();
                    if(bookingResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        detailBookedItem.dismissSpotLoading();
                        detailBookedItem.reqCanceled();
                    }
                    else if(bookingResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        detailBookedItem.dismissSpotLoading();
                        detailBookedItem.errorAction();
                    }
                    else if(bookingResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        detailBookedItem.dismissSpotLoading();
                        detailBookedItem.idBookingInValid();
                    }
                }
                else{
                    Log.d(TAG, "Terjadi kesalahan pada server");
                    Log.e(TAG, response.errorBody().toString());
                    detailBookedItem.dismissSpotLoading();
                    detailBookedItem.ErrorResponse();
                }
            }
            //fail to connect to API
            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                t.printStackTrace();
                detailBookedItem.dismissSpotLoading();
                detailBookedItem.ErrorConnection();
            }
        });
    }

    private void dropping(String strIdBooking){
        Call<BookingResponse> requestCall;
        Log.d(TAG, "update status booking "+strIdBooking);
        requestCall = api.batalkanReservasiOlehOutlet(strIdBooking);
        requestCall.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                Log.d(TAG, "RESPON CODE" + response.code());
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final BookingResponse bookingResponse = response.body();
                    if(bookingResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        detailReservationItem.dismissSpotLoading();
                        detailReservationItem.reqDropped();
                    }
                    else if(bookingResponse.getStatus().toString().equalsIgnoreCase("0")) {
                        detailReservationItem.dismissSpotLoading();
                        detailReservationItem.errorAction();
                    }
                    else if(bookingResponse.getStatus().toString().equalsIgnoreCase("3")) {
                        detailReservationItem.dismissSpotLoading();
                        detailReservationItem.idBookingInValid();
                    }
                }
                else{
                    Log.d(TAG, "Terjadi kesalahan pada server");
                    Log.e(TAG, response.errorBody().toString());
                    detailReservationItem.dismissSpotLoading();
                    detailReservationItem.ErrorResponse();
                }
            }
            //fail to connect to API
            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                t.printStackTrace();
                detailReservationItem.dismissSpotLoading();
                detailReservationItem.ErrorConnection();
            }
        });
    }

// ============================================================== report ==================================================================




}
