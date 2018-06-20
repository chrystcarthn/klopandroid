package appdeveloper.klop.klop.Activity;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import appdeveloper.klop.klop.Adapter.CategoryAdapter;
import appdeveloper.klop.klop.Fragment.CategoryFragment;
import appdeveloper.klop.klop.Fragment.FacilityFragment;
import appdeveloper.klop.klop.Model.CategoryDb;
import appdeveloper.klop.klop.Model.FacilityDb;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.CategoryFragPresenterImp;
import appdeveloper.klop.klop.PresenterImp.FacilityFragPresenterImp;
import appdeveloper.klop.klop.R;

/**
 * Created by CMDDJ on 5/2/2018.
 */

public class FilteredSearching extends AppCompatActivity implements CategoryFragment.OnHeadlineSelectedListener,
        FacilityFragment.OnHeadlineSelectedListener{

    SessionPreference session;
    HashMap<String, String> userdata;

    String strIdUser, min, max;
    String[] listHari;
    boolean[] checkedHari;
    ArrayList<Integer> mHariChoosen = new ArrayList<>();


    TextInputLayout nameWrapper, categoryWrapper, facilityWrapper, hargaWrapper, harga0Wrapper;
    Button btnPilihKategori, btnPilihFasilitas, btnHari, btnFilter;
    ImageButton btnHargaMax;
    EditText txtAutoTreatment;
    EditText edNmKatTerpilih, edNmFasTerpilih, edHariTerpilih, edHargaMin, edHargaMax;
    TextView tvKat, tvFas, tvHari, tvMin, tvMax, tvHargaMin;
    LinearLayout hargals;
    Toolbar toolbar;

    String[] listHarga, listHarga1;

    CategoryFragPresenterImp categoryFragPresenterImp;
    FacilityFragPresenterImp facilityFragPresenterImp;

    ArrayList<String> strIdCategory = new ArrayList<String>();
    ArrayList<String> strIdFacility = new ArrayList<String>();

    public String allCategory, allFacility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_filtered_searching);

        session = new SessionPreference(this);
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        strIdCategory = new ArrayList<String>();
        strIdFacility = new ArrayList<String>();

        categoryFragPresenterImp = new CategoryFragPresenterImp(this);
        facilityFragPresenterImp = new FacilityFragPresenterImp(this);

        categoryFragPresenterImp.showAllCategory2();
        facilityFragPresenterImp.showAllFacility2();

        toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        listHari = getResources().getStringArray(R.array.hari_arrays);
        listHarga = getResources().getStringArray(R.array.hrgmin_arrays);
        listHarga1 = getResources().getStringArray(R.array.hrgmax_arrays);
        checkedHari = new boolean[listHari.length];

      //  hargals = (LinearLayout) findViewById(R.id.lsEdHarga);
        txtAutoTreatment = (EditText) findViewById(R.id.autoTextTreatment);

        nameWrapper = (TextInputLayout) findViewById(R.id.input_layout_perawatan);
        categoryWrapper = (TextInputLayout) findViewById(R.id.input_layout_kategori);
        facilityWrapper = (TextInputLayout) findViewById(R.id.input_layout_fasilitas);
        harga0Wrapper = (TextInputLayout) findViewById(R.id.input_layout_hargamin);
        hargaWrapper = (TextInputLayout) findViewById(R.id.input_layout_hargamax);

        edNmKatTerpilih = (EditText) findViewById(R.id.edKategoriFiltered);
        edNmFasTerpilih = (EditText) findViewById(R.id.edFasilitasFiltered);
        edHariTerpilih = (EditText) findViewById(R.id.edHariTerpilih);
        edHargaMin = (EditText) findViewById(R.id.edHargaMin);
        edHargaMax = (EditText) findViewById(R.id.edHargaMax);

        tvKat  = (TextView) findViewById(R.id.tvKatFiltered);
        tvFas = (TextView) findViewById(R.id.tvFasFiltered);
        tvMin = (TextView) findViewById(R.id.minValue);
        tvMax = (TextView) findViewById(R.id.maxValue);
        tvMin.setText("Rating 0");
        tvMax.setText(" sampai 5");
       // tvHargaMin = (TextView) findViewById(R.id.tvTes);

//        btnPilihKategori = (Button) findViewById(R.id.btnPilihKategori);
//        btnPilihFasilitas = (Button) findViewById(R.id.btnPilihFasilitas);
//        btnHari = (Button) findViewById(R.id.btnHari);
//        btnHargaMax = (ImageButton) findViewById(R.id.btnPilihHargaMax);
        btnFilter = (Button) findViewById(R.id.btnFilter);

        final FragmentManager fm = getFragmentManager();
        final CategoryFragment cf = new CategoryFragment();
        final FacilityFragment ff = new FacilityFragment();
        cf.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        ff.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);


        edHargaMin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(FilteredSearching.this);
                mBuilder.setTitle("Pilih harga terendah");
                mBuilder.setSingleChoiceItems(listHarga, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edHargaMin.setText("Rp "+ listHarga[which]);
//                        tvHargaMin.setText(listHarga[which]);
                        dialog.dismiss();
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

        edHargaMax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(FilteredSearching.this);
                mBuilder.setTitle("Pilih harga tertinggi");
                mBuilder.setSingleChoiceItems(listHarga1, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edHargaMax.setText("Rp "+ listHarga1[which]);
//                        tvHargaMin.setText(listHarga[which]);
                        dialog.dismiss();
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });



        edNmKatTerpilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cf.show(fm, "CF_TAG");
            }
        });

        edNmFasTerpilih.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ff.show(fm, "FF_TAG");
            }
        });

        edHariTerpilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(FilteredSearching.this);
                mBuilder.setTitle("Pilih hari");
                mBuilder.setMultiChoiceItems(listHari, checkedHari, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                        if(isChecked){
                            if(!mHariChoosen.contains(which)){
                                mHariChoosen.add(which);
                            }else{
                                mHariChoosen.remove(which);
                            }
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for(int i = 0; i<mHariChoosen.size(); i++){
                            item = item + listHari[mHariChoosen.get(i)];
                            if(i != mHariChoosen.size()-1){
                                item = item + ", ";
                            }
                        }
                        edHariTerpilih.setText(item);
                    }
                });
                mBuilder.setNegativeButton("Batal", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
//                mBuilder.setNeutralButton("Clear", new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        for(int i = 0; i < checkedHari.length; i++){
//                            checkedHari[i] = false;
//                            mHariChoosen.clear();
//                            edHariTerpilih.setText("");
//                        }
//                    }
//                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });


        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String strTreatment=null, strCategory, strFacility, strDays, strPriceMin, strPriceMax, strRateMin, strRateMax, strStatus;

               if(txtAutoTreatment.equals(null) || txtAutoTreatment.getText().toString().length() == 0){
                   nameWrapper.setErrorEnabled(true);
                   nameWrapper.setError("Harap diisi");
                   requestFocus(txtAutoTreatment);
               } else{
                   strTreatment = txtAutoTreatment.getText().toString();

                   strCategory = tvKat.getText().toString();
                   if(strCategory.equals("Apa saja") || strCategory.equals("")){

                       strCategory = allCategory.toString();
                   }

                   strFacility = tvFas.getText().toString();
                   if(strFacility.equals("Apa saja") || strFacility.equals("")){

                       strFacility = allFacility.toString();
                   }
//                Toast.makeText(FilteredSearching.this, "tvFas "+tvFas.getText().toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(FilteredSearching.this, "tvFas "+tvFas.getText().toString(), Toast.LENGTH_SHORT).show();

                   strDays = edHariTerpilih.getText().toString();
                   if(strDays.equals("Apa saja") || strDays.equals("")){
                       strDays = "Senin,Selasa,Rabu,Kamis,Jumat,Sabtu,Minggu";
                   }

                   strPriceMin = edHargaMin.getText().toString()
                           .replace("Rp","")
                           .replace(" ","")
                           .replace(".","")
                           .trim();
                   if(strPriceMin.equals("Berapasaja") || strPriceMin.equals("")){
                       strPriceMin = "0";
                   }


                   strPriceMax = edHargaMax.getText().toString()
                           .replace("Rp","")
                           .replace(" ","")
                           .replace(".","")
                           .trim();
                   if(strPriceMax.equals("Berapasaja") || strPriceMax.equals("")){
                       strPriceMax = "2000000";
                   }

                   strRateMin = tvMin.getText().toString()
                           .replace("Rating","")
                           .replace(" ","")
                           .trim();
                   strRateMax = tvMax.getText().toString()
                           .replace("sampai","")
                           .replace(" ","")
                           .trim();
                   strStatus = "filter";

                   Intent i = new Intent(FilteredSearching.this, SearchingResult.class);
                   Bundle b = new Bundle();
                   b.putString("dariFilter", strStatus);
                   b.putString("idUser", strIdUser);
                   b.putString("strTreatment", strTreatment);
                   b.putString("strCategory", strCategory);
                   b.putString("strFacility", strFacility);
                   b.putString("strDays", strDays);
                   b.putString("strPriceMin", strPriceMin);
                   b.putString("strPriceMax", strPriceMax);
                   b.putString("strRateMin", strRateMin);
                   b.putString("strRateMax", strRateMax);

                   i.putExtras(b);
                   startActivity(i);

//                Toast.makeText(FilteredSearching.this, strIdUser+" | "+strTreatment+ " | "+ strCategory+ " | "+strFacility+ " | "+ strDays+ " | "+strPrice+ " | "+strRateMin+ " | "+strRateMax, Toast.LENGTH_SHORT).show();
//                Toast.makeText(FilteredSearching.this, strIdUser+" | "+strTreatment+ " | "+ String.valueOf(strCategory)+ " | "+strFacility+ " | "+ strDays+ " | "+strPriceMin+ " | "+strPriceMax+strRateMin+ " | "+strRateMax, Toast.LENGTH_SHORT).show();
//                Toast.makeText(FilteredSearching.this, strIdUser+" | "+strTreatment+ " | "+ strCategory+ " | "+strFacility+ " | "+ strDays+ " | "+strPriceMin+" | "+strPriceMax+ " | "+strRateMin+ " | "+strRateMax, Toast.LENGTH_SHORT).show();


               }
            }
        });

        final NumberFormat formatter = new DecimalFormat("#.#");

        RangeSeekBar rangeSeekbar = (RangeSeekBar) findViewById(R.id.rangeSeekbar);
        rangeSeekbar.setNotifyWhileDragging(true);
        rangeSeekbar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                min = minValue.toString();
                max = maxValue.toString();
                String strMin = formatter.format(minValue);
                String strMax = formatter.format(maxValue);
             //   Toast.makeText(getApplicationContext(), "Min Value- " + minValue + " & " + "Max Value- " + maxValue, Toast.LENGTH_LONG).show();
                tvMin.setText("Rating " + strMin);
                tvMax.setText(" sampai " + strMax);
            }
        });

        final RangeSeekBar<Double> seekBar = new RangeSeekBar<Double>(this);
        seekBar.setRangeValues(0.0, 5.0);

        seekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Double>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Double minValue, Double maxValue) {


            }
        });

        rangeSeekbar.setNotifyWhileDragging(true);
     //   ((LinearLayout) findViewById(R.id.seekbarLayout)).addView(rangeSeekbar);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void generateCategoryDbResponse(ArrayList<CategoryDb> categoryDbArrayList){

      //  Toast.makeText(this, categoryDbArrayList.toString(), Toast.LENGTH_SHORT).show();

        for(int i=0; i<categoryDbArrayList.size(); i++){
            strIdCategory.add(categoryDbArrayList.get(i).getIDCATEGORYDB());
        }

        allCategory = strIdCategory.toString()
                .replace("[","")
                .replace("]","")
                .replace(" ","")
                .trim();

    }

    public void generateFacilityDbResponse(ArrayList<FacilityDb> facilityDbArrayList){

     //   Toast.makeText(this, facilityDbArrayList.toString(), Toast.LENGTH_SHORT).show();

        for(int i=0; i<facilityDbArrayList.size(); i++){
            strIdFacility.add(facilityDbArrayList.get(i).getIDFACILITYDB());
        }

        allFacility = strIdFacility.toString()
                .replace("[","")
                .replace("]","")
                .replace(" ","")
                .trim();
    }

    public void errorStatus(){
       // Toast.makeText(Register.this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();

    }

    public void ErrorResponseFailed(){
       // Toast.makeText(Register.this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();

    }

    public void ErrorConnectionFailed(){
       // Toast.makeText(Register.this, "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void facilitySelected(String strIdFasTemp, String strNmFasTemp) {
        tvFas.setText(strIdFasTemp);
        edNmFasTerpilih.setText(strNmFasTemp);

    }

    @Override
    public void categorySelected(String strIdCatTemp, String strNmCatTemp) {

        tvKat.setText(strIdCatTemp);
        edNmKatTerpilih.setText(strNmCatTemp);
    }
}
