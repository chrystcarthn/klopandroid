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

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import appdeveloper.klop.klop.Adapter.TreatmentAdapter;
import appdeveloper.klop.klop.Model.Treatment;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.TreatmentFragPresenterImp;
import appdeveloper.klop.klop.R;
import dmax.dialog.SpotsDialog;

public class TreatmentTabFragment extends Fragment {
	//private RecyclerView mRootView;
	private CoordinatorLayout mRootView;
	TreatmentFragPresenterImp treatmentFragPresenterImp;
	SessionPreference session;
	HashMap<String, String> userdata;
	String strIdStore, strIdUser, strOwner;
	ArrayList<Treatment> dataTreatments;
	private TreatmentAdapter adapter;
	private RecyclerView recyclerView;
	//private CardView cardViewTreatment;
	TextView tvTreatment, tvHargaTreatment, tvEmpty, tvDesc;
	TextView btnDialogAddTr;
	FrameLayout frame;
	EditText edNmTr, edDescTr, edHrgTr;
	TextInputLayout nmWrapper, descWrapper, hrgWrapper;
	ImageButton btnClose;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mRootView = (CoordinatorLayout) inflater.inflate(R.layout.fragment_tab_treatment, container, false);
		setHasOptionsMenu(true);
		session = new SessionPreference(getContext());
		userdata = session.getUserDetails();
		strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

		Bundle b = getActivity().getIntent().getExtras();
		strIdStore = b.getString("idStore");
		strOwner = b.getString("idUser");

		treatmentFragPresenterImp = new TreatmentFragPresenterImp(this);
		//cardViewTreatment = (CardView) mRootView.findViewById(R.id.cvTr);
		tvEmpty = (TextView) mRootView.findViewById(R.id.tvEmptyTr);

		frame = (FrameLayout) mRootView.findViewById(R.id.frameTr);
		recyclerView = (RecyclerView) mRootView.findViewById(R.id.recyclerViewTreatment);
		btnDialogAddTr = (TextView) mRootView.findViewById(R.id.tvAddTr);
		if(strIdUser.equals(strOwner)){
			btnDialogAddTr.setVisibility(View.GONE);

		}else{
			btnDialogAddTr.setVisibility(View.GONE);
		}
		btnDialogAddTr.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
				View mView = getLayoutInflater().inflate(R.layout.dialog_add_treatment, null);

				nmWrapper = (TextInputLayout) mView.findViewById(R.id.input_layout_namatr);
				descWrapper = (TextInputLayout) mView.findViewById(R.id.input_layout_desc);
				hrgWrapper = (TextInputLayout) mView.findViewById(R.id.input_layout_harga);
				btnClose = (ImageButton) mView.findViewById(R.id.btnClose);
				edNmTr = (EditText) mView.findViewById(R.id.edNameTr);
				edDescTr = (EditText) mView.findViewById(R.id.edDescTreatment);
				edHrgTr = (EditText) mView.findViewById(R.id.edPriceTreatment);
				Button btnAddNewTr = (Button) mView.findViewById(R.id.btnAddNewTr);

				mBuilder.setView(mView);
				final AlertDialog dialog = mBuilder.create();

				btnClose.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});

				btnAddNewTr.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						treatmentFragPresenterImp.addNewTreatment(strIdStore, edNmTr.getText().toString(), edDescTr.getText().toString(), edHrgTr.getText().toString());
					}
				});

				dialog.show();

			}
		});

		loadRecyclerViewTreatmentByOutlet();
		recyclerView.setItemAnimator(new DefaultItemAnimator());
		recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

		return mRootView;
	}

	private void loadRecyclerViewTreatmentByOutlet()
	{
		SpotsDialog alertDialog = new SpotsDialog(getContext());
		alertDialog.show();

		treatmentFragPresenterImp.showTreatment(strIdStore);

		alertDialog.hide();
	}

	public void AddingTrSuccess(){
		loadRecyclerViewTreatmentByOutlet();

//		edNmTr.setText("");
//		edDescTr.setText("");
//		edHrgTr.setText("");
	}

	public void ErrorAddingTrFailed(){
		Toast.makeText(getContext(), "Data tidak valid!", Toast.LENGTH_SHORT).show();
	}

	public void ErrorResponseFailed(){
		Toast.makeText(getContext(), "Terjadi gangguan, coba lagi beberapa saat", Toast.LENGTH_SHORT).show();
	}

	public void warningNoTreatmentFound(){
		frame.setVisibility(View.GONE);
		tvEmpty.setVisibility(View.VISIBLE);
//		tvTreatment.setText("");
//		tvHargaTreatment.setText("");
	}


	public void generateTreatmentResponse(ArrayList<Treatment> treatmentArrayList){
		tvEmpty.setVisibility(View.GONE);
		adapter = new TreatmentAdapter(treatmentArrayList, getContext());
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setAdapter(adapter);
	}


	public static Fragment newInstance() {
		return new TreatmentTabFragment();
	}

	private void requestFocus(View view) {
		if (view.requestFocus()) {
			getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
		}
	}

	public void showInvalidNameTr(){
		nmWrapper.setError("Nama perawatan harus diisi");
		hrgWrapper.setErrorEnabled(false);
		requestFocus(edNmTr);
	}

	public void showInvalidHargaTr(){
		hrgWrapper.setError("Harga perawatan harus diisi");
		nmWrapper.setErrorEnabled(false);
		requestFocus(edHrgTr);
	}



	public void ErrorConnectionFailed() {
		Toast.makeText(getContext(), "Koneksi ke server gagal", Toast.LENGTH_SHORT).show();
	}


}
