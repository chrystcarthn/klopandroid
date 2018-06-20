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

import appdeveloper.klop.klop.Activity.AddStore;
import appdeveloper.klop.klop.Adapter.CategoryAdapter;
import appdeveloper.klop.klop.Model.CategoryDb;
import appdeveloper.klop.klop.PresenterImp.CategoryFragPresenterImp;
import appdeveloper.klop.klop.R;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

/**
 * Created by CMDDJ on 4/24/2018.
 */

public class CategoryFragment extends DialogFragment {

    RecyclerView rv;
    CategoryAdapter adapter;
    String strIdCatTemp, strNmCatTemp;
    CategoryFragPresenterImp categoryFragPresenterImp;
    ArrayList<String> stringArrayList = new ArrayList<String>();
    public TextView tvTempId;
    AddStore addStore;



    private String idcat = "";

    OnHeadlineSelectedListener mCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        getDialog().setTitle("Pilih kategori");
        View v = inflater.inflate(R.layout.fragment_multichoice, container);
        tvTempId = (TextView) v.findViewById(R.id.tvTempIdCat);
     //   tvTempId.setText(idcat);

        return v;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater.from(getContext()).inflate(R.layout.fragment_multichoice, null);
        rv = new RecyclerView(getContext());

      //  pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        categoryFragPresenterImp = new CategoryFragPresenterImp(CategoryFragment.this);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        loadRecyclerViewCategory();

        // you can use LayoutInflater.from(getContext()).inflate(...) if you have xml layout
      //  rv.setAdapter(/* your adapter */);

        return new AlertDialog.Builder(getActivity())
                .setTitle("Pilih Kategori")
                .setView(rv)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                tvTempId.setText(adapter.formattedIdArraylist);
                                strIdCatTemp = adapter.formattedIdArraylist;
                                strNmCatTemp = adapter.formattedNmArraylist;
//                                Toast.makeText(getContext(), "OK pressed : "+adapter.formattedIdArraylist +", "+tvTempId.getText().toString(), Toast.LENGTH_SHORT).show();
                                mCallback.categorySelected(strIdCatTemp, strNmCatTemp);
                            }
                        }
                )
                .setNegativeButton("Batal",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                              //  Toast.makeText(getContext(), "Batal", Toast.LENGTH_SHORT).show();
                            }
                        }
                ).create();
    }

    public interface OnHeadlineSelectedListener{
        public void categorySelected(String strIdCatTemp, String strNmCatTemp);
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

    public void generateCategoryDbResponse(ArrayList<CategoryDb> categoryDbArrayList){

        adapter = new CategoryAdapter(categoryDbArrayList, this.getActivity());

        rv.setAdapter(adapter);
    }



    private void loadRecyclerViewCategory()
    {
//        SpotsDialog alertDialog = new SpotsDialog(getContext());
//        alertDialog.show();

        categoryFragPresenterImp.showAllCategory();

       // alertDialog.hide();
    }


    public void ErrorConnectionFailed() {
        Toast.makeText(getContext(), "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
    }





}
