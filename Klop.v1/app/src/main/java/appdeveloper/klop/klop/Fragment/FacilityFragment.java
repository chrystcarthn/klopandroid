package appdeveloper.klop.klop.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import appdeveloper.klop.klop.Adapter.FacilityAdapter;
import appdeveloper.klop.klop.Model.FacilityDb;
import appdeveloper.klop.klop.PresenterImp.FacilityFragPresenterImp;
import appdeveloper.klop.klop.R;

/**
 * Created by CMDDJ on 4/27/2018.
 */

public class FacilityFragment extends DialogFragment {

    RecyclerView rv;
    FacilityAdapter adapter;
    String strIdFasTemp, strNmFasTemp;
    FacilityFragPresenterImp facilityFragPresenterImp;
    public TextView tvTempId;
    private String idfas = "";

    OnHeadlineSelectedListener mCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
      //  getDialog().setTitle("Pilih kategori");
        View v = inflater.inflate(R.layout.fragment_multichoice, container);
        tvTempId = (TextView) v.findViewById(R.id.tvTempIdCat);
      //  tvTempId.setText(idfas);

        return v;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater.from(getContext()).inflate(R.layout.fragment_multichoice, null);
        rv = new RecyclerView(getContext());
        facilityFragPresenterImp = new FacilityFragPresenterImp(FacilityFragment.this);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        loadRecyclerViewFacility();

        // you can use LayoutInflater.from(getContext()).inflate(...) if you have xml layout
        //  rv.setAdapter(/* your adapter */);

        return new AlertDialog.Builder(getActivity())
                .setTitle("Pilih Fasilitas")
                .setView(rv)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                tvTempId.setText(adapter.formattedIdArraylist);
                                strIdFasTemp = adapter.formattedIdArraylist;
                                strNmFasTemp = adapter.formattedNmArraylist;
//                                Toast.makeText(getContext(), "OK pressed : "+adapter.formattedIdArraylist +", "+tvTempId.getText().toString(), Toast.LENGTH_SHORT).show();
                                mCallback.facilitySelected(strIdFasTemp, strNmFasTemp);
                            }
                        }
                )
                .setNegativeButton("Batal",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            //    Toast.makeText(getContext(), "Batal", Toast.LENGTH_SHORT).show();
                            }
                        }
                ).create();
    }

    public interface OnHeadlineSelectedListener{
        public void facilitySelected(String strIdFasTemp, String strNmFasTemp);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            mCallback = (OnHeadlineSelectedListener) getActivity();
        } catch (ClassCastException e){
            throw new ClassCastException(getActivity().toString() + " must implement OnHeadlineSelectedListener");
        }
    }

    public void generateFacilityDbResponse(ArrayList<FacilityDb> facilityDbArrayList){

        adapter = new FacilityAdapter(facilityDbArrayList, this.getActivity());

        rv.setAdapter(adapter);
    }


    public void ErrorResponse() {
        Toast.makeText(getContext(), "Terjadi kesalahan pada server", Toast.LENGTH_SHORT).show();

    }

    public void errorStatus() {
        Toast.makeText(getContext(), "status != 1", Toast.LENGTH_SHORT).show();

    }

    private void loadRecyclerViewFacility()
    {
//        SpotsDialog alertDialog = new SpotsDialog(getContext());
//        alertDialog.show();

        facilityFragPresenterImp.showAllFacility();

        // alertDialog.hide();
    }

    public void ErrorConnectionFailed() {
        Toast.makeText(getContext(), "Koneksi ke server gagal", Toast.LENGTH_SHORT).show();
    }



}
