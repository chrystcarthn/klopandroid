package appdeveloper.klop.klop.PresenterImp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import appdeveloper.klop.klop.API.InterfaceAPI;
import appdeveloper.klop.klop.API.RestClient;
import appdeveloper.klop.klop.Activity.ShowDetailStore;
import appdeveloper.klop.klop.Fragment.TreatmentTabFragment;
import appdeveloper.klop.klop.Model.Treatment;
import appdeveloper.klop.klop.Model.TreatmentResponse;
import appdeveloper.klop.klop.Presenter.TreatmentTabFragPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CMDDJ on 4/20/2018.
 */

public class TreatmentFragPresenterImp implements TreatmentTabFragPresenter {

    TreatmentTabFragment treatmentTabFragment;
    Treatment item;
    private static final String TAG = "TreatmentTag";
    InterfaceAPI api;
    Context context;
    ShowDetailStore showDetailStore;

    public TreatmentFragPresenterImp(TreatmentTabFragment treatmentTabFragment) {
        this.treatmentTabFragment = treatmentTabFragment;
        api = RestClient.createService(InterfaceAPI.class);

    }



    @Override
    public void showTreatment(String strIdOutlet) {
        getFetchingTreatmentByStore(strIdOutlet);
    }



    private void getFetchingTreatmentByStore(String strIdOutlet){
        Call<TreatmentResponse> requestCall;
        Log.d(TAG, "fetching info outlet "+strIdOutlet);
        requestCall = api.showTreatmentByStore(strIdOutlet);
        requestCall.enqueue(new Callback<TreatmentResponse>() {
            @Override
            public void onResponse(Call<TreatmentResponse> call, Response<TreatmentResponse> response) {
                if(response.isSuccessful()) {
                    final TreatmentResponse treatmentResponse = response.body();
                    if (treatmentResponse.getStatus().toString().equalsIgnoreCase("1")) {
                        treatmentTabFragment.generateTreatmentResponse((ArrayList<Treatment>) response.body().getTreatment());
                    } else {
                        treatmentTabFragment.warningNoTreatmentFound();
                    }
                }else{
                    treatmentTabFragment.ErrorResponseFailed();
                }

                // item2 = response.body().getCategory().get(0);

                // aboutTabFragment.generateCategoryResponse(item2);
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<TreatmentResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                treatmentTabFragment.ErrorConnectionFailed();
            }
        });


    }



    // =================================================== ADD NEW TREATMENT ========================================================

    private boolean isValidNmTr(String strNmTr){
        if(strNmTr != null || !strNmTr.equals("")){
            return true;
        }
        return false;
    }

    private boolean isValidHrgTr(String strHrgTr){
        if(strHrgTr != null || !strHrgTr.equals("")){
            return true;
        }
        return false;
    }

    @Override
    public void addNewTreatment(String strIdStore, String strNmTr, String strDesc, String strHargaTr) {
        if (!isValidNmTr(strNmTr)) {
            treatmentTabFragment.showInvalidNameTr();
        }
        else if (!isValidHrgTr(strHargaTr)) {
            treatmentTabFragment.showInvalidHargaTr();
        }
        else
            sendPostAddTreatment(strIdStore, strNmTr, strDesc, strHargaTr);
    }

    private void sendPostAddTreatment(String strIdStore, String strNmTr, String strDesc, String strHargaTr){
       // treatmentTabFragment.showSpotLoading();
        Call<TreatmentResponse> requestCall = null;
        requestCall = api.addTreatment(strIdStore, strNmTr, strDesc, strHargaTr);
        requestCall.enqueue(new Callback<TreatmentResponse>() {
            @Override
            public void onResponse(Call<TreatmentResponse> call, Response<TreatmentResponse> response) {
                //checking if get the JSON
                if(response.isSuccessful()) {
                    final TreatmentResponse treatmentResponse = response.body();
                    if(treatmentResponse.getStatus().toString().equalsIgnoreCase("1")) {
//                        Log.i(TAG, "post register submitted to API"
//                                + ", respon code = " + response.code()
//                                + ", respon body = " + response.body().toString());
//
                        //addStore.dismissSpotLoading();
                        treatmentTabFragment.AddingTrSuccess();
                    }
                    else{
                        Log.d(TAG, "Tidak bisa menambah JSON");

                       // Toast.makeText(context, "Gagal menambah perawatan", Toast.LENGTH_SHORT).show();
                      //  addStore.dismissSpotLoading();
                        treatmentTabFragment.ErrorAddingTrFailed();
                    }
                }
                else{
                    Log.d(TAG, "Gagal parsing JSON");
                    Log.e(TAG, response.errorBody().toString());
                    Toast.makeText(context, "Terjadi gangguan, coba lagi beberapa saat", Toast.LENGTH_SHORT).show();
                  //  addStore.dismissSpotLoading();
                    treatmentTabFragment.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<TreatmentResponse> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API");
                Log.e(TAG, t.getMessage());
                Toast.makeText(context, "Terjadi gangguan, periksa internet Anda", Toast.LENGTH_SHORT).show();
              //  addStore.dismissSpotLoading();
               treatmentTabFragment.ErrorConnectionFailed();
            }
        });
    }

}
