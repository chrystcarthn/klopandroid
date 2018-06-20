package appdeveloper.klop.klop.PresenterImp;

import android.util.Log;

import java.util.ArrayList;

import appdeveloper.klop.klop.API.InterfaceAPI;
import appdeveloper.klop.klop.API.RestClient;
import appdeveloper.klop.klop.Adapter.MngNewsAdapter;
import appdeveloper.klop.klop.Fragment.NewsTabFragment;
import appdeveloper.klop.klop.Model.News;
import appdeveloper.klop.klop.Model.NewsResponse;
import appdeveloper.klop.klop.Presenter.NewsTabFragPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CMDDJ on 6/6/2018.
 */

public class NewsTabFragPresenterImp implements NewsTabFragPresenter{

    InterfaceAPI api;
    private static final String TAG = "newsTab";
    NewsTabFragment newsTabFragment;

    public NewsTabFragPresenterImp(NewsTabFragment newsTabFragment) {
        this.newsTabFragment = newsTabFragment;
        api = RestClient.createService(InterfaceAPI.class);
    }


    @Override
    public void showPublishedNews(String strIdStore) {
        getFetchingPubNews(strIdStore);
    }

    private void getFetchingPubNews(String strIdOutlet){
        Call<NewsResponse> requestCall;
        Log.d(TAG, "fetching info outlet "+strIdOutlet);
        requestCall = api.showPubNews(strIdOutlet);
        requestCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if(response.isSuccessful()) {
                    final NewsResponse newsResponse = response.body();
                    if (newsResponse.getStatus().toString().equalsIgnoreCase("1")) {
                         newsTabFragment.generateNewsResponse((ArrayList<News>) response.body().getNews());
                    } else {
                        newsTabFragment.warningNoNewsFound();
                    }
                }else{
                    newsTabFragment.ErrorResponseFailed();
                }
            }

            //fail to connect to API
            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e(TAG, "Koneksi ke server gagal");
                Log.e(TAG, t.getMessage());
                newsTabFragment.ErrorConnectionFailed();
            }
        });
    }

}
