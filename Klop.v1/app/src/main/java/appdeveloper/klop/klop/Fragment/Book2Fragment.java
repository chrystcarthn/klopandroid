package appdeveloper.klop.klop.Fragment;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import appdeveloper.klop.klop.Activity.CreateMyBooking;
import appdeveloper.klop.klop.Adapter.BookTreatmentAdapter;
import appdeveloper.klop.klop.Other.BookTreatmentSearchCompat;
import appdeveloper.klop.klop.Model.Treatment;
import appdeveloper.klop.klop.PresenterImp.BookingFragPresenterImp;
import appdeveloper.klop.klop.R;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Book2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Book2Fragment extends Fragment implements View.OnClickListener, TimePickerDialog.OnTimeSetListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Book2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Book2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Book2Fragment newInstance(String param1, String param2) {
        Book2Fragment fragment = new Book2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private View view;
    private EditText edDate, edTime, edNameTr;
    TextView tvNamaTr, tvQuantity, tvHargaTr, tvTrKosong, tvNmOutlet, tvAddress;
    private EditText editTextNamaAyah;
    private Button buttonNext;
    private String namaLengkap;
    private String alamat;
    RecyclerView recyclerView;
    ListView lv;
    String choosenDate, choosenMonth, choosenYear;
    String choosenHour, choosenMinutes;
    BookTreatmentAdapter adapter;
    TextInputLayout dateWrapper, timeWrapper, trWrapper;

    LinearLayout llCart;

    Calendar c = Calendar.getInstance();
    int cyear = c.get(Calendar.YEAR);
    int cmonth = c.get(Calendar.MONTH);
    int cday = c.get(Calendar.DAY_OF_MONTH);

    static final int DIALOG_ID = 0;
    static final long ONE_MINUTE_IN_MILLIS=60000;
    ArrayList<Treatment> treatmentBookedArraylist = new ArrayList<Treatment>();

    ArrayList<String> idTrArraylist = new ArrayList<>();
    ArrayList<String> namaTrArraylist = new ArrayList<>();
    ArrayList<String> quantityTrArraylist = new ArrayList<>();
    ArrayList<String> hargaTrArraylist = new ArrayList<>();
    ArrayList<String> subTrArraylist = new ArrayList<>();

    String[] idTrArray, namaTrArray, qtyTrArray, hrgTrArray, subTrArray;

    Button btnNext, btnAddTr;

    BookingFragPresenterImp book2FragPresenterImp;
    Treatment[] arrayTr;
    List<Treatment> listTr = new ArrayList<Treatment>();
    ElegantNumberButton numberQuantity;
    String strIdTr = null, strHrgTr = null;
    int days, months, years;
    ListView lvCartTr;

    Calendar date;
    long t;
    Date limit, available;
    String timeAfter30, timeAfterLimit, jamSubLimit, menitSubLimit, jamSubAvail, menitSubAvail;
    String datenow=null;
    String monthnow=null;
    String yearnow=null;
    String hournow=null;
    String minutesnow=null;

    String strBunIdStore, strBunNmStore, strBunPeople, strBunIdUser, strBunNama, strBunTelp, strBunAddress;
    String strFinDate, strFinTime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_book2, container, false);

        Bundle b = getArguments();
        strBunIdStore = b.getString("idStore");
        strBunNmStore = b.getString("nmStore");
        strBunAddress = b.getString("address");
        strBunPeople = b.getString("people");
        strBunIdUser = b.getString("idUser");
        strBunNama = b.getString("nama");
        strBunTelp = b.getString("telp");

        loadComponent();
        return view;
    }

    private void loadComponent() {
        llCart = (LinearLayout) view.findViewById(R.id.llCart);
        tvTrKosong = (TextView) view.findViewById(R.id.tvTrKosong);
        dateWrapper = (TextInputLayout) view.findViewById(R.id.wrapperTgl);
        timeWrapper = (TextInputLayout) view.findViewById(R.id.wrapperJam);
        trWrapper = (TextInputLayout) view.findViewById(R.id.input_layout_tr);
        numberQuantity = (ElegantNumberButton) view.findViewById(R.id.number_button);
        numberQuantity.setNumber("1");
        tvNmOutlet = (TextView) view.findViewById(R.id.tvNmOutlet);
        tvAddress = (TextView) view.findViewById(R.id.tvAddressOutlet);
        btnAddTr = (Button) view.findViewById(R.id.btnBookTr);
        btnNext = (Button) view.findViewById(R.id.btnNext);

        edNameTr = (EditText) view.findViewById(R.id.edNameTr);

        edDate = (EditText) view.findViewById(R.id.edTgl);
        edTime = (EditText) view.findViewById(R.id.edJam);

        tvNmOutlet.setText(strBunNmStore);
        tvAddress.setText(strBunAddress);

        setEdDate();
        setEdTime();

        book2FragPresenterImp = new BookingFragPresenterImp(this);

        lvCartTr = (ListView) view.findViewById(R.id.lvCartTr);


        if(idTrArray != null){
         //   Toast.makeText(getContext(), "array ID: "+idTrArray.toString(), Toast.LENGTH_SHORT).show();
          //  try{
                idTrArray = new String[idTrArraylist.size()];
                idTrArray = idTrArraylist.toArray(idTrArray);

                namaTrArray = new String[namaTrArraylist.size()];
                namaTrArray = namaTrArraylist.toArray(namaTrArray);

                qtyTrArray = new String[quantityTrArraylist.size()];
                qtyTrArray = quantityTrArraylist.toArray(qtyTrArray);

                hrgTrArray = new String[hargaTrArraylist.size()];
                hrgTrArray = hargaTrArraylist.toArray(hrgTrArray);

                subTrArray = new String[subTrArraylist.size()];
                subTrArray = subTrArraylist.toArray(subTrArray);

                final CustomAdapter customAdapter = new CustomAdapter();
                lvCartTr.setAdapter(customAdapter);

                if(customAdapter.isEmpty()){
                    tvTrKosong.setVisibility(View.VISIBLE);
                } else tvTrKosong.setVisibility(View.GONE);

                lvCartTr.refreshDrawableState();


            lvCartTr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    lvCartTr.setSelection(position);
                    view.setSelected(true);

                    idTrArraylist.remove(position);
                    namaTrArraylist.remove(position);
                    quantityTrArraylist.remove(position);
                    hargaTrArraylist.remove(position);
                    subTrArraylist.remove(position);

                    idTrArray = new String[idTrArraylist.size()];
                    idTrArray = idTrArraylist.toArray(idTrArray);

                    namaTrArray = new String[namaTrArraylist.size()];
                    namaTrArray = namaTrArraylist.toArray(namaTrArray);

                    qtyTrArray = new String[quantityTrArraylist.size()];
                    qtyTrArray = quantityTrArraylist.toArray(qtyTrArray);

                    hrgTrArray = new String[hargaTrArraylist.size()];
                    hrgTrArray = hargaTrArraylist.toArray(hrgTrArray);

                    subTrArray = new String[subTrArraylist.size()];
                    subTrArray = subTrArraylist.toArray(subTrArray);

                    final CustomAdapter customAdapter = new CustomAdapter();
                    lvCartTr.setAdapter(customAdapter);

                    if(customAdapter.isEmpty()){
                        tvTrKosong.setVisibility(View.VISIBLE);
                    } else tvTrKosong.setVisibility(View.GONE);

                    lvCartTr.refreshDrawableState();
                   // Toast.makeText(getContext(), namaTrArraylist.toString(), Toast.LENGTH_SHORT).show();
                   // Toast.makeText(getContext(), quantityTrArraylist.toString(), Toast.LENGTH_SHORT).show();

                }
            });

            //  }catch (Exception e){e.printStackTrace();}

        }


        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewTreatment);

        edDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 showCalendar();
            }
        });
        edTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTime();
            }
        });

        arrayTr = listTr.toArray(new Treatment[listTr.size()]);
        book2FragPresenterImp.showListTreatment(strBunIdStore);
     //   Toast.makeText(getContext(), "idstore "+strBunIdStore, Toast.LENGTH_SHORT).show();


        edNameTr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BookTreatmentSearchCompat<>(getContext(), "Cari...",
                        "Cari perawatan apa?", null, initTreatment(),
                        new SearchResultListener<Treatment>() {
                            @Override
                            public void onSelected(BaseSearchDialogCompat dialog,
                                                   Treatment item, int position) {

                                strIdTr = item.getIDTREATMENT();
                                edNameTr.setText(item.getTitle());
                                strHrgTr = item.getPRICETREATMENT();

                                dialog.dismiss();
                            }
                        }).show();
            }
        });



        btnAddTr.setOnClickListener(this);
        btnNext.setOnClickListener(this);
    }


    public void setEdDate(){
        datenow = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        monthnow  = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
        yearnow = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());

        String dnow, mnow;
        dnow = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        mnow = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());

        if (String.valueOf(datenow).length() < 2) {
            datenow = "0" + String.valueOf(datenow);
        } else {
            datenow = String.valueOf(datenow);
        }

        if (String.valueOf(monthnow).length() < 2) {
            monthnow = "0" + String.valueOf(monthnow);
        } else {
            monthnow = String.valueOf(monthnow);
        }

      //  Toast.makeText(getContext(), "tgl "+datenow+"|"+monthnow+"|"+yearnow, Toast.LENGTH_SHORT).show();

        strFinDate = datenow+"/"+monthnow+"/"+yearnow;
      //  Toast.makeText(getContext(), "tanggal Init:"+strFinDate, Toast.LENGTH_SHORT).show();
        edDate.setText(getDayName(String.valueOf(yearnow), mnow, String.valueOf(dnow)) + ", " + datenow + " " + getMonthName(mnow)+" "+yearnow);

    }

    public String plusTheTime(){
        date = Calendar.getInstance();
        t = date.getTimeInMillis();
        limit = new Date(t + (30 * ONE_MINUTE_IN_MILLIS));
        available = new Date(t + (31 * ONE_MINUTE_IN_MILLIS));

        timeAfter30 = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(limit);
        timeAfterLimit = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(available);

        jamSubLimit = timeAfter30.substring(0,2);
        menitSubLimit = timeAfter30.substring(3,5);

        jamSubAvail = timeAfter30.substring(0,2);
        menitSubAvail = timeAfter30.substring(3,5);

        return jamSubAvail+":"+menitSubAvail;
    }

    public void setEdTime(){

        String times = plusTheTime();
        strFinTime = times;
        edTime.setText(times);
    }


    private ArrayList<Treatment> initTreatment(){
        ArrayList<Treatment> items2 = new ArrayList<>();
        // Thanks to https://randomuser.me for the images
        items2 = treatmentBookedArraylist;

        return items2;
    }


      public void showCalendar() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getActivity().getFragmentManager(), "Date Picker");
    }

    public void showTime() {
        TimePickerFragment time = new TimePickerFragment();

        date = Calendar.getInstance();
        t = date.getTimeInMillis();
        limit = new Date(t + (30 * ONE_MINUTE_IN_MILLIS));
        available = new Date(t + (31 * ONE_MINUTE_IN_MILLIS));

        timeAfter30 = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(limit);
        timeAfterLimit = new SimpleDateFormat("HH:mm", Locale.ENGLISH).format(available);

        jamSubLimit = timeAfter30.substring(0,2);
        menitSubLimit = timeAfter30.substring(3,5);

        jamSubAvail = timeAfter30.substring(0,2);
        menitSubAvail = timeAfter30.substring(3,5);



        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();

        args.putInt("hour", Integer.valueOf(jamSubAvail));
        args.putInt("minute", Integer.valueOf(menitSubAvail));
        time.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        time.setCallBack(ontime);
        time.show(getActivity().getFragmentManager(), "Timepick");
    }

    public boolean isValidDate(){
        if(edDate.length() == 0)
            return false;
        else return true;
    }

    public boolean isValidTime(){
        if(edTime.length() == 0)
            return false;
        else return true;
    }

    public boolean isValidNmTr(){
        if(edNameTr.length() == 0)
            return false;
        else return true;
    }


    public boolean isValidQuantity(){
        if(numberQuantity.getNumber().equals("0"))
            return false;
        else return true;
    }

    public void showInvalidDate() {
        dateWrapper.setErrorEnabled(true);
        timeWrapper.setErrorEnabled(false);
        trWrapper.setErrorEnabled(false);

        dateWrapper.setError("Harap menentukan tangal dahulu");
        requestFocus(edDate);
    }

    public void showInvalidTime() {
        dateWrapper.setErrorEnabled(false);
        timeWrapper.setErrorEnabled(true);
        trWrapper.setErrorEnabled(false);

        timeWrapper.setError("Harap menentukan jam dahulu");
        requestFocus(edTime);
    }

    public void showInvalidNmTr() {
        dateWrapper.setErrorEnabled(false);
        timeWrapper.setErrorEnabled(false);
        trWrapper.setErrorEnabled(true);

        trWrapper.setError("Harap memilih perawatan dahulu");
        requestFocus(edNameTr);
    }

    public void removeError(){
        dateWrapper.setErrorEnabled(false);
        timeWrapper.setErrorEnabled(false);
        trWrapper.setErrorEnabled(false);
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == btnAddTr) {
            if(!isValidNmTr()){
                showInvalidNmTr();
            } else
            if(!isValidQuantity()){
                Toast.makeText(getContext(), "Jumlah tidak boleh 0", Toast.LENGTH_SHORT).show();
            } else{
                removeError();

//                Toast.makeText(getContext(), namaTrArraylist.toString(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(getContext(), edNameTr.getText().toString(), Toast.LENGTH_SHORT).show();
//                if(namaTrArraylist.contains(edNameTr.getText().toString())){
//                    int index = namaTrArraylist.indexOf(edNameTr.getText().toString());
//                    String qty = quantityTrArraylist.get(index);
//                    String newqty = String.valueOf(Integer.valueOf(qty) + Integer.valueOf(numberQuantity.getNumber()));
//                    quantityTrArraylist.get(index).replace(quantityTrArraylist.get(index), newqty);
//
//                    final CustomAdapter customAdapter = new CustomAdapter();
//                    lvCartTr.setAdapter(customAdapter);
//
//                    if(customAdapter.isEmpty()){
//                        tvTrKosong.setVisibility(View.VISIBLE);
//                    } else tvTrKosong.setVisibility(View.GONE);
//
//                    lvCartTr.refreshDrawableState();
//
//
//                    lvCartTr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                            lvCartTr.setSelection(position);
//                            view.setSelected(true);
//
//                            idTrArraylist.remove(position);
//                            namaTrArraylist.remove(position);
//                            quantityTrArraylist.remove(position);
//                            hargaTrArraylist.remove(position);
//                            subTrArraylist.remove(position);
//
//                            idTrArray = new String[idTrArraylist.size()];
//                            idTrArray = idTrArraylist.toArray(idTrArray);
//
//                            namaTrArray = new String[namaTrArraylist.size()];
//                            namaTrArray = namaTrArraylist.toArray(namaTrArray);
//
//                            qtyTrArray = new String[quantityTrArraylist.size()];
//                            qtyTrArray = quantityTrArraylist.toArray(qtyTrArray);
//
//                            hrgTrArray = new String[hargaTrArraylist.size()];
//                            hrgTrArray = hargaTrArraylist.toArray(hrgTrArray);
//
//                            subTrArray = new String[subTrArraylist.size()];
//                            subTrArray = subTrArraylist.toArray(subTrArray);
//
//                            final CustomAdapter customAdapter = new CustomAdapter();
//                            lvCartTr.setAdapter(customAdapter);
//
//                            if(customAdapter.isEmpty()){
//                                tvTrKosong.setVisibility(View.VISIBLE);
//                            } else tvTrKosong.setVisibility(View.GONE);
//
//                            lvCartTr.refreshDrawableState();
//
//                        }
//                    });
//
//                }else {

                    int sub = Integer.valueOf(numberQuantity.getNumber()) * Integer.valueOf(strHrgTr);
                    String subtotal = String.valueOf(sub);

                    tvTrKosong.setVisibility(View.GONE);
                    idTrArraylist.add(strIdTr);
                    namaTrArraylist.add(edNameTr.getText().toString());
                    quantityTrArraylist.add(numberQuantity.getNumber());
                    hargaTrArraylist.add(strHrgTr);
                    subTrArraylist.add(subtotal);

                    idTrArray = new String[idTrArraylist.size()];
                    idTrArray = idTrArraylist.toArray(idTrArray);

                    namaTrArray = new String[namaTrArraylist.size()];
                    namaTrArray = namaTrArraylist.toArray(namaTrArray);

                    qtyTrArray = new String[quantityTrArraylist.size()];
                    qtyTrArray = quantityTrArraylist.toArray(qtyTrArray);

                    hrgTrArray = new String[hargaTrArraylist.size()];
                    hrgTrArray = hargaTrArraylist.toArray(hrgTrArray);

                    subTrArray = new String[subTrArraylist.size()];
                    subTrArray = subTrArraylist.toArray(subTrArray);

                    final CustomAdapter customAdapter = new CustomAdapter();
                    lvCartTr.setAdapter(customAdapter);
                    edNameTr.setText("");
                    numberQuantity.setNumber("1");
                    //    Toast.makeText(getContext(), namaTrArraylist.toString(), Toast.LENGTH_SHORT).show();
                    //    Toast.makeText(getContext(), quantityTrArraylist.toString(), Toast.LENGTH_SHORT).show();

                    lvCartTr.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            lvCartTr.setSelection(position);
                            view.setSelected(true);

                            idTrArraylist.remove(position);
                            namaTrArraylist.remove(position);
                            quantityTrArraylist.remove(position);
                            hargaTrArraylist.remove(position);
                            subTrArraylist.remove(position);

                            idTrArray = new String[idTrArraylist.size()];
                            idTrArray = idTrArraylist.toArray(idTrArray);

                            namaTrArray = new String[namaTrArraylist.size()];
                            namaTrArray = namaTrArraylist.toArray(namaTrArray);

                            qtyTrArray = new String[quantityTrArraylist.size()];
                            qtyTrArray = quantityTrArraylist.toArray(qtyTrArray);

                            hrgTrArray = new String[hargaTrArraylist.size()];
                            hrgTrArray = hargaTrArraylist.toArray(hrgTrArray);

                            subTrArray = new String[subTrArraylist.size()];
                            subTrArray = subTrArraylist.toArray(subTrArray);

                            final CustomAdapter customAdapter = new CustomAdapter();
                            lvCartTr.setAdapter(customAdapter);

                            if (customAdapter.isEmpty()) {
                                tvTrKosong.setVisibility(View.VISIBLE);
                            } else tvTrKosong.setVisibility(View.GONE);

                            lvCartTr.refreshDrawableState();
                            //     Toast.makeText(getContext(), namaTrArraylist.toString(), Toast.LENGTH_SHORT).show();
                            //    Toast.makeText(getContext(), quantityTrArraylist.toString(), Toast.LENGTH_SHORT).show();

                        }
                    });


                    llCart.setVisibility(View.VISIBLE);
               // }
            }
        } else
        if (view == btnNext) {
            if(!isValidDate()){
                showInvalidDate();
            } else
            if(!isValidTime()){
                showInvalidTime();
            } else
                if(idTrArraylist.size() == 0){
                Toast.makeText(getContext(), "Anda belum memesan perawatan apa pun", Toast.LENGTH_SHORT).show();
            } else{
                    removeError();

                    CreateMyBooking.goToStepUlasan();
                    Book3Fragment book3Fragment = new Book3Fragment();


                    String strIdArr = Arrays.toString(idTrArray);
                    String strNamaTrArr = Arrays.toString(namaTrArray);
                    String strQtyArr = Arrays.toString(qtyTrArray);
                    String strHrgArr = Arrays.toString(hrgTrArray);
                    String strSubTotalArr = Arrays.toString(subTrArray);

                  //  strFinDate = datenow+"/"+monthnow+"/"+yearnow;

                    Bundle bundle = new Bundle();
                    bundle.putString("idStore", strBunIdStore);
                    bundle.putString("nmStore", strBunNmStore);
                    bundle.putString("address", strBunAddress);
                    bundle.putString("people", strBunPeople);
                    bundle.putString("idUser", strBunIdUser);
                    bundle.putString("nama", strBunNama);
                    bundle.putString("telp", strBunTelp);
//                    bundle.putInt("days", days);
//                    bundle.putInt("months", months);
//                    bundle.putInt("years", years);
                    bundle.putString("tanggal", edDate.getText().toString());
                    bundle.putString("jam", edTime.getText().toString());
                    bundle.putString("idarr", strIdArr.replace(", ",","));
                    bundle.putString("trarr", strNamaTrArr.replace(", ",","));
                    bundle.putString("qtyarr", strQtyArr.replace(", ",","));
                    bundle.putString("hrgarr", strHrgArr.replace(", ",","));
                    bundle.putString("subarr", strSubTotalArr.replace(", ",","));
                  //  Toast.makeText(getContext(), "bundle sent "+days+"/"+months+"/"+years, Toast.LENGTH_SHORT).show();
                  //  Toast.makeText(getContext(), "bundle sent "+strBunNmStore, Toast.LENGTH_SHORT).show();

                    book3Fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_in_from_right, R.anim.slide_out_from_right, R.anim.slide_in_from_left, R.anim.slide_out_from_left)
                            .replace(R.id.frame_layout, book3Fragment)
                            .addToBackStack(null)
                            .commit();
                }



    }
    }

    class CustomAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return idTrArray.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.list_item_cart_treatment, null);

            TextView tvNmTr = (TextView) convertView.findViewById(R.id.tvCartNamaTr);
            TextView tvQuantity = (TextView) convertView.findViewById(R.id.tvCartQuantityTr);
            TextView tvHrg = (TextView) convertView.findViewById(R.id.tvCartHargaTr);

            tvNmTr.setText("  "+namaTrArray[position]);
            tvQuantity.setText(qtyTrArray[position]+"x");

            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
            Locale localeID = new Locale("in", "ID");
            NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
            String hargaFormatted = hrgTrArray[position];

            tvHrg.setText(formatRupiah.format((double)Integer.parseInt(hargaFormatted)));

            return convertView;
        }
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            String strDateNow = datenow+"/"+monthnow+"/"+yearnow;

            days = dayOfMonth;
            months = monthOfYear;
            years = year;

            String tampungmonth;
            String tampungdate;
            if (String.valueOf(dayOfMonth).length() < 2) {
                tampungdate = "0" + String.valueOf(dayOfMonth);
            } else {
                tampungdate = String.valueOf(dayOfMonth);
            }

            if (String.valueOf(monthOfYear + 1).length() < 2) {
                tampungmonth = "0" + String.valueOf(monthOfYear + 1);
            } else {
                tampungmonth = String.valueOf(monthOfYear + 1);
            }
            String strDateChoosen = tampungdate+"/"+tampungmonth+"/"+year;

//            String compHour = new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());
//            String compMinute  = new SimpleDateFormat("mm", Locale.getDefault()).format(new Date());
//            String timesCompare = compHour+":"+compMinute;
//
            if(checkTimings(strFinTime, plusTheTime()) == false){
                if(checkDates(strDateNow, strDateChoosen) == false || strDateNow.equals(strDateChoosen)){
                    errorTanggal();
                    showCalendar();
                }  else{
                    choosenDate = tampungdate;
                    choosenMonth = tampungmonth;
                    choosenYear = String.valueOf(year);

                    //        Toast.makeText(getContext(), "hari: "+getDayName(String.valueOf(year), tampungmonth, String.valueOf(dayOfMonth)), Toast.LENGTH_SHORT).show();

                    strFinDate = tampungdate+"/"+tampungmonth+"/"+year;
                 //   Toast.makeText(getContext(), "tanggal Repick:"+strFinDate, Toast.LENGTH_SHORT).show();

                    edDate.setText(getDayName(String.valueOf(year), tampungmonth, String.valueOf(dayOfMonth)) + ", " + tampungdate + " " + getMonthName(tampungmonth)+" "+year);
                    // }
                }
            } else{
                if(checkDates(strDateNow, strDateChoosen) == false){
                    errorTanggal();
                    showCalendar();
                }  else{
                    choosenDate = tampungdate;
                    choosenMonth = tampungmonth;
                    choosenYear = String.valueOf(year);

                    //        Toast.makeText(getContext(), "hari: "+getDayName(String.valueOf(year), tampungmonth, String.valueOf(dayOfMonth)), Toast.LENGTH_SHORT).show();

                    strFinDate = tampungdate+"/"+tampungmonth+"/"+year;
               //     Toast.makeText(getContext(), "tanggal Repick:"+strFinDate, Toast.LENGTH_SHORT).show();

                    edDate.setText(getDayName(String.valueOf(year), tampungmonth, String.valueOf(dayOfMonth)) + ", " + tampungdate + " " + getMonthName(tampungmonth)+" "+year);
                    // }
                }
            }


        }
    };

    private boolean checkDates(String dateNow, String dateChoosen){
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

   //     Toast.makeText(getContext(), "now: "+dateNow+" choosen: "+dateChoosen, Toast.LENGTH_SHORT).show();

        try{
            Date date1 = sdf.parse(dateNow);
            Date date2 = sdf.parse(dateChoosen);

            if(date2.before(date1)) {
             //   Toast.makeText(getContext(), "false", Toast.LENGTH_SHORT).show();
                return false;
            }
            else{
             //   Toast.makeText(getContext(), "true", Toast.LENGTH_SHORT).show();
                return true;
            }
        }catch (Exception e){e.printStackTrace();}
        return false;
    }

    private boolean checkTimings(String timeChoosen, String timeLimit){
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

    //    Toast.makeText(getContext(), "jam: "+timeChoosen+timeLimit, Toast.LENGTH_SHORT).show();
        try{
            Date time1 = sdf.parse(timeChoosen);
            Date time2 = sdf.parse(timeLimit);


            if(time1.before(time2)){
                return false;
            } else{
                return true;
            }
        }catch (Exception e){e.printStackTrace();}
        return false;
    }

    TimePickerDialog.OnTimeSetListener ontime = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String tampunghour = null, tampungminute = null;


        String strTimeNow = hourOfDay+":"+minute;


        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        Date d1 = null;
            try {
                d1 = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(strTimeNow);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String compDate = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
            String compMonth  = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
            String compYear = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
            String datesCompare = compDate+"/"+compMonth+"/"+compYear;

            if(strFinDate.equals(datesCompare)){
                if(checkTimings(strTimeNow, timeAfter30) == false){
                    Toast.makeText(getContext(), "Pemilihan waktu minimal 30 menit dari sekarang yaitu pukul "+timeAfter30, Toast.LENGTH_SHORT).show();
                    showTime();
                }
            }

             if (String.valueOf(hourOfDay).length() < 2) {
                 tampunghour = "0" + hourOfDay;
             } else {
                 tampunghour = String.valueOf(hourOfDay);
             }

             if (String.valueOf(minute).length() < 2) {
                 tampungminute = "0" + minute;
             } else {
                 tampungminute = String.valueOf(minute);
             }

             String times = tampunghour+":"+tampungminute;
             strFinTime = times;
             edTime.setText(times);
        }
    };


    public void generateBookTreatmentResponse(ArrayList<Treatment> treatmentArrayList){

        treatmentBookedArraylist = treatmentArrayList;
        listTr = treatmentArrayList;
    }

    public String getDayName(String year,String month, String day){

        String namahari="";

        int intyear = Integer.parseInt(year);
        int intmonth = Integer.parseInt(month);
        int intday = Integer.parseInt(day);

       // Toast.makeText(getContext(), year+" "+month+" "+day, Toast.LENGTH_SHORT).show();
        String dateString = String.format("%d-%d-%d", intyear, intmonth, intday);
        Date dates = null;
        try {
            dates = new SimpleDateFormat("yyyy-M-d").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(dates);
     //   Toast.makeText(getContext(), "hari: "+dayOfWeek, Toast.LENGTH_SHORT).show();

        if(dayOfWeek.equals("Monday")){
            namahari = "Senin";
        }else if(dayOfWeek.equals("Tuesday")){
            namahari = "Selasa";
        }else if(dayOfWeek.equals("Wednesday")){
            namahari = "Rabu";
        }else if(dayOfWeek.equals("Thursday")){
            namahari = "Kamis";
        }else if(dayOfWeek.equals("Friday")){
            namahari = "Jumat";
        }else if(dayOfWeek.equals("Saturday")){
            namahari = "Sabtu";
        }else if(dayOfWeek.equals("Sunday")){
            namahari = "Minggu";
        }
        return namahari;
    }


    public String getMonthName(String month){
        String monthName = "";

        if(month.equals("01")){
            monthName = "Januari";
        }else if(month.equals("02")){
            monthName = "Februari";
        }else if(month.equals("03")){
            monthName = "Maret";
        }else if(month.equals("04")){
            monthName = "April";
        }else if(month.equals("05")){
            monthName = "Mei";
        }else if(month.equals("06")){
            monthName = "Juni";
        }else if(month.equals("07")){
            monthName = "Juli";
        }else if(month.equals("08")){
            monthName = "Agustus";
        }else if(month.equals("09")){
            monthName = "September";
        }else if(month.equals("10")){
            monthName = "Oktober";
        }else if(month.equals("11")){
            monthName = "November";
        }else if(month.equals("12")){
            monthName = "Desember";
        }
        return monthName;
    }


    public void  errorTanggal(){
        Toast.makeText(getContext(), "Pemilihan tanggal hanya boleh mulai hari ini atau setelahnya", Toast.LENGTH_SHORT).show();
        
    }

    public void  errorWaktu(){
        Toast.makeText(getContext(), "Pemilihan jam tidak boleh sebelum jam sekarang", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        String tampunghour;
        String tampungminute;

        String datenow = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
        String monthnow  = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
        String yearnow = new SimpleDateFormat("yyyy", Locale.getDefault()).format(new Date());
        String hournow = new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());
        String minutenow = new SimpleDateFormat("mm", Locale.getDefault()).format(new Date());

        String getHour = String.valueOf(hourOfDay);
        String getMinutes = String.valueOf(minute);

        if(String.valueOf(hourOfDay).length() == 1){
            getHour = "0" + getHour;
        }

        if(String.valueOf(minute).length() == 1){
            getMinutes = "0" + getMinutes;
        }

        if (String.valueOf(hourOfDay).length() < 2) {
            tampunghour = "0" + hourOfDay;
        } else {
            tampunghour = String.valueOf(hourOfDay);
        }

        if (String.valueOf(minute).length() < 2) {
            tampungminute = "0" + minute;
        } else {
            tampungminute = String.valueOf(minute);
        }


        if (choosenYear.equals(yearnow) && choosenMonth.equals(monthnow) && choosenDate.equals(datenow)) {
            if (hourOfDay < Integer.parseInt(hournow)) {
                errorWaktu();
                return;
            } else if (hourOfDay == Integer.parseInt(hournow) && minute < Integer.parseInt(minutenow)) {
                errorWaktu();
                return;
            }
        }

        edTime.setText(tampunghour + ":" + tampungminute);
    }

    public void ErrorResponseFailed(){
        Toast.makeText(getContext(), "Terjadi gangguan, coba lagi beberapa saat", Toast.LENGTH_SHORT).show();
    }

    public void ErrorConnectionFailed() {
        Toast.makeText(getContext(), "Koneksi ke server gagal", Toast.LENGTH_SHORT).show();
    }

    public void warningNoTreatmentFound(){
        Toast.makeText(getContext(), "Perawatan tidak tersedia", Toast.LENGTH_SHORT).show();
    }
}
