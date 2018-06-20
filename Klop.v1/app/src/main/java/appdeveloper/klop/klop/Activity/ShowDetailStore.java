/*
 * Copyright (C) 2017
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package appdeveloper.klop.klop.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import appdeveloper.klop.klop.Fragment.AboutTabFragment;
import appdeveloper.klop.klop.Fragment.NewsTabFragment;
import appdeveloper.klop.klop.Fragment.PhotoRevTabFragment;
import appdeveloper.klop.klop.Fragment.ReviewTabFragment;
import appdeveloper.klop.klop.Fragment.TreatmentTabFragment;
import appdeveloper.klop.klop.Model.Photo;
import appdeveloper.klop.klop.Model.Store;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.Presenter.NewsTabFragPresenter;
import appdeveloper.klop.klop.PresenterImp.HomePresenterImp;
import appdeveloper.klop.klop.PresenterImp.StorePresenterImp;
import appdeveloper.klop.klop.PresenterImp.TreatmentFragPresenterImp;
import appdeveloper.klop.klop.R;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

import static cn.pedant.SweetAlert.SweetAlertDialog.SUCCESS_TYPE;

public class ShowDetailStore extends AppCompatActivity
	implements AppBarLayout.OnOffsetChangedListener, View.OnClickListener {

	private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
	private boolean mIsAvatarShown = true;
	private ImageView mProfileImage, imgOutletHeader;
	private int mMaxScrollSize;

	TextView tvNama, tvRating, tvTelepon, tvStoreStatus, txtloading, tvUlasan, tvReservasi;

	ScrollView whole2;

	AVLoadingIndicatorView loading;
	AppBarLayout appbarLayout;
	NestedScrollView nsv;

	CircleImageView cImg;
	AppBarLayout whole;
    int intToday;
	SessionPreference session;
	HashMap<String, String> userdata;
	String strIdUser, strIdStore, linkOutletLogo, linkOutletHeader, strOwner, strNmStore, strAddress, strPhone;
	FloatingActionButton fbEditOutlet;
	StorePresenterImp storePresenterImp;
	HomePresenterImp homePresenterImp;
	private static final int REQUEST_CALL = 1;
	int intTr;
    ArrayList<String> strDaysArraylist = new ArrayList<String>();
    ArrayList<String> strTimeOpenArraylist = new ArrayList<String>();
    ArrayList<String> strTimeClosedArraylist = new ArrayList<String>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showdetailstore2);

		session = new SessionPreference(this);
		userdata = session.getUserDetails();
		strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

		loading = (AVLoadingIndicatorView) findViewById(R.id.avloadingIndicatorView);
		txtloading = (TextView)findViewById(R.id.txtloading);
		appbarLayout = (AppBarLayout) findViewById(R.id.materialup_appbar);
		appbarLayout.setVisibility(View.GONE);
		nsv = (NestedScrollView) findViewById(R.id.nsv);
		nsv.setVisibility(View.GONE);
		mProfileImage = (ImageView) findViewById(R.id.materialup_profile_image);
		mProfileImage.setVisibility(View.GONE);
		startAnim();


		TabLayout tabLayout = (TabLayout) findViewById(R.id.materialup_tabs);
		ViewPager viewPager  = (ViewPager) findViewById(R.id.materialup_viewpager);


		imgOutletHeader = (ImageView) findViewById(R.id.materialup_profile_backdrop);
		fbEditOutlet = (FloatingActionButton) findViewById(R.id.fabEditStore);
		tvNama = (TextView) findViewById(R.id.tvNmStore);
		tvUlasan = (TextView) findViewById(R.id.tvBuatUlasan);
		tvReservasi = (TextView) findViewById(R.id.tvReservasi);
		tvRating = (TextView) findViewById(R.id.tvRating);
		tvStoreStatus = (TextView) findViewById(R.id.txtStatusStore);
		tvTelepon = (TextView) findViewById(R.id.tvNoTelp);



		Toolbar toolbar = (Toolbar) findViewById(R.id.materialup_toolbar);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override public void onClick(View v) {
				onBackPressed();
			}
		});

		appbarLayout.addOnOffsetChangedListener(this);
		mMaxScrollSize = appbarLayout.getTotalScrollRange();

		viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager()));

		tabLayout.setupWithViewPager(viewPager);

		storePresenterImp = new StorePresenterImp(this);
		homePresenterImp = new HomePresenterImp(this);


		Bundle b = getIntent().getExtras();
		strIdStore = b.getString("idStore");
		strNmStore = b.getString("nmStore");
		strAddress = b.getString("address");
		intTr = b.getInt("intTr");
		strPhone = b.getString("strPhone");

		storePresenterImp.showInfoOutlet(strIdStore, -7.7746817, 110.4159096);
		//homePresenterImp.showOutletHeader(strIdStore);

		tvUlasan.setOnClickListener(this);
		tvReservasi.setOnClickListener(this);
	}

	void startAnim(){
		loading.setVisibility(View.VISIBLE);
		appbarLayout.setVisibility(View.GONE);
		nsv.setVisibility(View.GONE);
		txtloading.setVisibility(View.VISIBLE);
		mProfileImage.setVisibility(View.GONE);
	}

	public void stopAnim(){
		loading.setVisibility(View.GONE);
		appbarLayout.setVisibility(View.VISIBLE);
		nsv.setVisibility(View.VISIBLE);
		mProfileImage.setVisibility(View.VISIBLE);
		txtloading.setVisibility(View.GONE);
	}

	public void warningDataNotFound(){
		Toast.makeText(ShowDetailStore.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
	}

	public void ErrorConnectionFailed(){
		Toast.makeText(ShowDetailStore.this, "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
	}

	public void ErrorResponseFailed(){
		Toast.makeText(ShowDetailStore.this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();

	}

	public void continueBook(){
		if(intTr == 0){
			new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
					.setTitleText("Perawatan masih kosong")
					.setContentText("Layanan ini akan tersedia jika\npemilik outlet sudah menambahkan\nmenu perawatan.\n" + "Lanjutkan reservasi melalui\npanggilan telepon saja?")
					.setConfirmText("Ya")
					.setCancelText("Batal")
					.showCancelButton(true)
					.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
						@Override
						public void onClick(SweetAlertDialog sweetAlertDialog) {
							sweetAlertDialog.dismiss();
						}
					}).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
				@Override
				public void onClick(SweetAlertDialog sweetAlertDialog) {
					makePhoneCall();
					sweetAlertDialog.dismiss();
				}
			})
					.show();
		} else{
			Intent i = new Intent(ShowDetailStore.this, CreateMyBooking.class);
			Bundle b = new Bundle();
			b.putString("idStore", strIdStore);
			b.putString("nmStore", strNmStore);
			b.putString("address", strAddress);
			i.putExtras(b);
			startActivity(i);
		}
	}

	public void bookReachLimit(){
		new SweetAlertDialog(ShowDetailStore.this, SweetAlertDialog.ERROR_TYPE)
				.setTitleText("Oops...")
				.setContentText("Anda masih memiliki 3 revervasi yang tertunda. Harap menyelesaikan reservasi lama untuk melanjutkan")
				.show();
	}

	public void fetchPhotoHeader(Photo photo){
		try{
			linkOutletHeader = photo.getFile().toString();
		}catch (Exception e){
			e.printStackTrace();
		}

		if(linkOutletHeader != null){
		//	Toast.makeText(this, "link header 2: " + linkOutletHeader, Toast.LENGTH_SHORT).show();
			Picasso.with(this).load(linkOutletHeader).into(imgOutletHeader);		}
	}

	public String getNameOfToday(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                return "Minggu";

            case Calendar.MONDAY:
                return "Senin";

            case Calendar.TUESDAY:
                return "Selasa";

            case Calendar.WEDNESDAY:
                return "Rabu";

            case Calendar.THURSDAY:
                return "Kamis";

            case Calendar.FRIDAY:
                return "Jumat";

            case Calendar.SATURDAY:
                return "Sabtu";


        }
		stopAnim();
        return "";
    }

    public String getTimeNow(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
        return mdformat.format(calendar.getTime());
    }

    private int getIndexDays(String days){
        return strDaysArraylist.indexOf(days);
    }

    private void getStatusStoreNow(Store store){
		int intIndexDay=0;
		String open=null, closed=null;

		if(store.getIsSetmanual().equals("no")){
			for(int i=0; i<store.getSchedule().size(); i++){
				strDaysArraylist.add(store.getSchedule().get(i).getDAYS().toString());
				strTimeOpenArraylist.add(store.getSchedule().get(i).getTIMEOPEN().toString());
				strTimeClosedArraylist.add(store.getSchedule().get(i).getTIMECLOSED().toString());
			}

			if(strDaysArraylist.contains(getNameOfToday())){
				intIndexDay = getIndexDays(getNameOfToday());
				open = strTimeOpenArraylist.get(intIndexDay);
				closed = strTimeClosedArraylist.get(intIndexDay);

				if(checkTimings(getTimeNow(), open, closed) == true){
					storeOpen();
				}else storeClosed();

			} else {
				storeClosed();
			}

		} else if(store.getIsSetmanual().equals("open")){
			storeOpen();
		} else if(store.getIsSetmanual().equals("closed")){
			storeClosed();
		}

      //  Toast.makeText(this, "TODAY "+getNameOfToday()+" index: "+intIndexDay+" dgn jam "+open+" - "+closed, Toast.LENGTH_SHORT).show();
    }

    private boolean checkTimings(String timeNow, String startTime, String endTime){
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try{
            Date date1 = sdf.parse(timeNow);
            Date date2 = sdf.parse(startTime);
            Date date3 = sdf.parse(endTime);

            if(date1.after(date2) & date1.before(date3)){
                return true;
            } else{
                return false;
            }
        }catch (Exception e){e.printStackTrace();}
        return false;
    }

    private void storeOpen(){
        tvStoreStatus.setText("BUKA");
        tvStoreStatus.setTextColor(getResources().getColor(R.color.open));
        tvStoreStatus.setBackgroundResource(R.drawable.background_open2);
    }

    private void storeClosed(){
        tvStoreStatus.setText("TUTUP");
        tvStoreStatus.setTextColor(getResources().getColor(R.color.closed));
        tvStoreStatus.setBackgroundResource(R.drawable.background_closed2);
    }

	public void fetchInfoStore(Store store){
		//Toast.makeText(this, store.getNameStore().toString()+strIdStore, Toast.LENGTH_SHORT).show();
		String rate;
		tvNama.setText(store.getNameStore().toString());

		rate = String.format("%.01f", Float.valueOf(store.getRateSum().toString()))
				.replace(",",".")
				.replace(",0","")
				.replace(".0","")
				.trim();

		tvRating.setText(rate);

		//Toast.makeText(this, "hari ini : "+getNameOfToday() +" jam "+getTimeNow(), Toast.LENGTH_SHORT).show();
     //   Toast.makeText(this, "size schedule "+store.getSchedule().size(), Toast.LENGTH_SHORT).show();
        if(store.getSchedule().size() != 0){
            getStatusStoreNow(store);
        }


		//tvAlamat.setText(store.getAddressStore().toString());
		strOwner = store.getIdUser().toString();

		try{
			linkOutletLogo = store.getLogoStore().toString();
		}catch(Exception e){
			e.printStackTrace();
		}

		if(linkOutletLogo != null) {
			//Toast.makeText(this, "link logo:" + linkOutletLogo, Toast.LENGTH_SHORT).show();
			Picasso.with(this).load(linkOutletLogo).into(mProfileImage);
		}

		try{
			linkOutletHeader = store.getBanner();
		}catch (Exception e){
			e.printStackTrace();
		}

		if(linkOutletHeader != null){
			//	Toast.makeText(this, "link header 2: " + linkOutletHeader, Toast.LENGTH_SHORT).show();
			Picasso.with(this).load(linkOutletHeader).into(imgOutletHeader);		}

		if(strIdUser.equals(strOwner)){
			fbEditOutlet.setVisibility(View.GONE);

		}else{
			fbEditOutlet.setVisibility(View.GONE);
		}

		fbEditOutlet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
			}
		});

		stopAnim();

	}




	public static void start(Context c) {
		c.startActivity(new Intent(c, ShowDetailStore.class));
	}

	@Override
	public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
		if (mMaxScrollSize == 0)
			mMaxScrollSize = appBarLayout.getTotalScrollRange();

		int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;

		if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
			mIsAvatarShown = false;

			mProfileImage.animate()
				.scaleY(0).scaleX(0)
				.setDuration(200)
				.start();
		}

		if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
			mIsAvatarShown = true;

			mProfileImage.animate()
				.scaleY(1).scaleX(1)
				.start();
		}
	}

	@Override
	public void onClick(View v) {
		if(v==tvUlasan){
			Intent i = new Intent(ShowDetailStore.this, AddReviewRate.class);
			Bundle b = new Bundle();
			b.putString("idStore", strIdStore);
			b.putString("nmStore", strNmStore);
			i.putExtras(b);
			startActivity(i);
		} else
			if(v == tvReservasi){
				storePresenterImp.getCountBook(strIdUser);


			}
	}

	private void makePhoneCall() {
		if (strPhone.trim().length() > 0) {

			if (ContextCompat.checkSelfPermission(this,
					Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(ShowDetailStore.this,
						new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
			} else {
				String dial = "tel:" + strPhone;
				startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
			}

		} else {
			Toast.makeText(this, "Nomor tidak tersedia", Toast.LENGTH_SHORT).show();
		}
	}

	private static class TabsAdapter extends FragmentPagerAdapter {
		private static final int TAB_COUNT = 5;

		TabsAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return TAB_COUNT;
		}

		@Override
		public Fragment getItem(int i) {
			switch(i)
			{
				case 0:
					return AboutTabFragment.newInstance();
				case 1:
					return TreatmentTabFragment.newInstance();
				case 2:
					return PhotoRevTabFragment.newInstance();
				case 3:
					return ReviewTabFragment.newInstance();
				case 4:
					return NewsTabFragment.newInstance();
			}
			return AboutTabFragment.newInstance();
		}


		private String tabTitles[] = new String[]{"Tentang", "Perawatan", "Foto", "Ulasan", "Promo"};


		@Override
		public CharSequence getPageTitle(int position) {


			return tabTitles[position];
		}


	}
}
