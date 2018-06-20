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
package appdeveloper.klop.klop.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import appdeveloper.klop.klop.Activity.ShowDetailStore;
import appdeveloper.klop.klop.Activity.ShowDirection;
import appdeveloper.klop.klop.Model.Category;
import appdeveloper.klop.klop.Model.Facility;
import appdeveloper.klop.klop.Model.Schedule;
import appdeveloper.klop.klop.Model.Store;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.AboutTabFragPresenterImp;
import appdeveloper.klop.klop.R;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class AboutTabFragment extends Fragment {
	private CoordinatorLayout mRootView;
	TextView tvTelepon, tvAlamat, tvKategori, tvJadwal, tvHari, tvFasilitas;
	ImageButton btnDialUp, btnMap;
	String strIdStore, strNmStore, strLat, strLong;
	AboutTabFragPresenterImp aboutTabFragPresenterImp;
	SessionPreference session;
	HashMap<String, String> userdata;
	private static final int REQUEST_CALL = 1;
	ShowDetailStore showDetailStore;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = (CoordinatorLayout) inflater.inflate(R.layout.fragment_tab_about, container, false);

		session = new SessionPreference(getContext());
		userdata = session.getUserDetails();
		tvTelepon = (TextView) mRootView.findViewById(R.id.tvNoTelp);
		tvAlamat = (TextView) mRootView.findViewById(R.id.tvAlamat);
		tvKategori = (TextView) mRootView.findViewById(R.id.tvCategory);
		tvJadwal = (TextView) mRootView.findViewById(R.id.tvSchedule);
		tvHari = (TextView) mRootView.findViewById(R.id.tvHari);
		btnDialUp = (ImageButton) mRootView.findViewById(R.id.btnDialUp);
		btnMap = (ImageButton) mRootView.findViewById(R.id.btnMap);
		tvFasilitas = (TextView) mRootView.findViewById(R.id.tvFacility);


		aboutTabFragPresenterImp = new AboutTabFragPresenterImp(this);

		Bundle b = getActivity().getIntent().getExtras();
		strIdStore = b.getString("idStore");
		strNmStore = b.getString("nmStore");
		strLat = b.getString("strLat");
		strLong = b.getString("strLong");

		aboutTabFragPresenterImp.showDetailOutlet(strIdStore, -7.7746817, 110.4159096);
		aboutTabFragPresenterImp.showCategory(strIdStore);
		aboutTabFragPresenterImp.showSchedule(strIdStore);
		aboutTabFragPresenterImp.showFacility(strIdStore);


		btnDialUp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
						.setTitleText("Panggil sekarang?")
						.setContentText("Anda akan melakukan panggilan ke\n" + strNmStore)
						.setConfirmText("Ya")
						.setCancelText("Nanti saja")
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
			}
		});

		tvTelepon.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
						.setTitleText("Telpon sekarang?")
						.setContentText("Anda akan melakukan panggilan ke\n" + strNmStore)
						.setConfirmText("Ya")
						.setCancelText("Nanti saja")
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
			}
		});

		btnMap.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent googleMapIntent = new Intent(Intent.ACTION_VIEW);
				googleMapIntent.setData(Uri.parse("http://maps.google.com/maps?daddr="+strLat+","+strLong));
				startActivity(googleMapIntent);

			}
		});

		return mRootView;
	}

	private void makePhoneCall() {
		String number = tvTelepon.getText().toString();
		if (number.trim().length() > 0) {

			if (ContextCompat.checkSelfPermission(getContext(),
					Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(getActivity(),
						new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
			} else {
				String dial = "tel:" + number;
				startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
			}

		} else {
			Toast.makeText(getContext(), "Nomor tidak tersedia", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == REQUEST_CALL) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				makePhoneCall();
			} else {
				Toast.makeText(getContext(), "Permission DENIED", Toast.LENGTH_SHORT).show();
			}
		}
	}


	public void ErrorConnectionFailed(){
		Toast.makeText(getContext(), "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
	}

	public void generateStoreResponse(Store store){
		tvTelepon.setText(store.getPhoneStore().toString());
		tvAlamat.setText(store.getAddressStore().toString());
	}

	public void generateCategoryResponse(ArrayList<Category> categoryArrayList){
		if(categoryArrayList != null) {
			String strTemp = "";

			for (int i = 0; i < categoryArrayList.size(); i++) {
				strTemp = strTemp + categoryArrayList.get(i).getNAMECATEGORY().toString();
				if(i < categoryArrayList.size()-1){
					strTemp = strTemp + ", ";
				}
			}
			tvKategori.setText(strTemp);
		}else{
			tvKategori.setText("-");
		}
	}

	public void warningDataNotFound(){
		tvKategori.setText("-");
	}

	public void generateScheduleResponse(ArrayList<Schedule> scheduleArrayList){
		if(scheduleArrayList != null){
			String strTemp="";
			String strTemp2="";

			for(int i=0; i<scheduleArrayList.size(); i++){
				strTemp2 = strTemp2 + scheduleArrayList.get(i).getDAYS().toString();
				if(i < scheduleArrayList.size()-1){
					strTemp2 = strTemp2+ "\n";
				}
				strTemp = strTemp + scheduleArrayList.get(i).getTIMEOPEN().toString() +" - "+ scheduleArrayList.get(i).getTIMECLOSED().toString();
				if(i < scheduleArrayList.size()-1){
					strTemp = strTemp+ "\n";
				}
			}
			tvHari.setText(strTemp2);
			tvJadwal.setText(strTemp);
		}else {
			tvHari.setText("-");
			tvJadwal.setText("");
		}

	}

	public void generateFacilityResponse(ArrayList<Facility> facilityArrayList){
		if(facilityArrayList != null) {
			String strTemp = "";

			for (int i = 0; i < facilityArrayList.size(); i++) {
				strTemp = strTemp + facilityArrayList.get(i).getNAMEFACILITY();
				if(i < facilityArrayList.size()-1){
					strTemp = strTemp + ", ";
				}
			}
			tvFasilitas.setText(strTemp);
		} else{
			tvFasilitas.setText("-");
		}
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		//initRecyclerView();
	}

//	private void initRecyclerView() {
//		mRootView.setAdapter(new FakePageAdapter(20));
//	}

	public static Fragment newInstance() {
		return new AboutTabFragment();
	}


	private static class FakePageAdapter extends RecyclerView.Adapter<FakePageVH> {
		private final int numItems;

		FakePageAdapter(int numItems) {
			this.numItems = numItems;
		}

		@Override
		public FakePageVH onCreateViewHolder(ViewGroup viewGroup, int i) {
			View itemView = LayoutInflater.from(viewGroup.getContext())
				.inflate(R.layout.list_item_treatment, viewGroup, false);

			return new FakePageVH(itemView);
		}

		@Override
		public void onBindViewHolder(FakePageVH fakePageVH, int i) {
			// do nothing
		}

		@Override
		public int getItemCount() {
			return numItems;
		}
	}

	private static class FakePageVH extends RecyclerView.ViewHolder {
		FakePageVH(View itemView) {
			super(itemView);
		}
	}
}
