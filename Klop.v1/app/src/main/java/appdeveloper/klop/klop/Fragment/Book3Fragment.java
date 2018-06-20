package appdeveloper.klop.klop.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


import appdeveloper.klop.klop.Activity.TabbedMenu;
import appdeveloper.klop.klop.Adapter.CartAdapter;
import appdeveloper.klop.klop.Model.Booking;
import appdeveloper.klop.klop.Model.CartTreatment;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.BookingFragPresenterImp;
import appdeveloper.klop.klop.R;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dmax.dialog.SpotsDialog;

import static cn.pedant.SweetAlert.SweetAlertDialog.SUCCESS_TYPE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Book3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Book3Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Book3Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Book3Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Book3Fragment newInstance(String param1, String param2) {
        Book3Fragment fragment = new Book3Fragment();
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

    private SpotsDialog progressDialog;

    private View view;
    SessionPreference session;
    HashMap<String, String> userdata;
    String strNamaPemesan;
    String strBunIdStore, strBunPeople, strBunNmStore, strBunAddress, strBunIdUser, strBunNama, strBunTelp, strBunFinDate, strBunDateNow, strBunMonthNow, strBunYearNow, strBunDate, strBunTime, strBunIdArr, strBunNmArr, strBunQtyArr, strBunHrgArr, strBunSubArr;
    TextView tvNamaGuest, tvTelpGuest, tvJumlah, tvSalon, tvAlamat, tvTanggal, tvJam, tvTotal, tvSapaan;
    Button btnKonfirmasi;
    String[] strIdArr, strNmArr, strQtyArr, strHrgArr, strSubArr;
    ArrayList<String> idArrayList, namaArrayList, qtyArrayList, hrgArrayList, subArrayList;
    CartTreatment cartItem;
    List<CartTreatment> listCart = new ArrayList<CartTreatment>();
    int total, days, months, years;
    RecyclerView recyclerView;
    CartAdapter adapter;

    BookingFragPresenterImp bookingFragPresenterImp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_book3, container, false);

        session = new SessionPreference(getContext());
        userdata = session.getUserDetails();
        strNamaPemesan = userdata.get(SessionPreference.KEY_FULLNAME);

        Bundle b = getArguments();
        strBunIdStore = b.getString("idStore");
        strBunNmStore = b.getString("nmStore");
        strBunAddress = b.getString("address");
        strBunPeople = b.getString("people");
        strBunIdUser = b.getString("idUser");
        strBunNama = b.getString("nama");
        strBunTelp = b.getString("telp");
//        days = b.getInt("days");
//        months = b.getInt("months");
//        years = b.getInt("years");
        strBunDate = b.getString("tanggal");
        strBunTime = b.getString("jam");
        strBunIdArr = b.getString("idarr").replace("[","").replace("]","");
        strBunNmArr = b.getString("trarr").replace("[","").replace("]","");
        strBunQtyArr = b.getString("qtyarr").replace("[","").replace("]","");
        strBunHrgArr = b.getString("hrgarr").replace("[","").replace("]","");
        strBunSubArr = b.getString("subarr").replace("[","").replace("]","");

        bookingFragPresenterImp = new BookingFragPresenterImp(this);

        loadComponent();

        recyclerView.setAdapter(adapter);
        total = sumSubTotal(subTotalArrToInt(subArrayList));

        tvSapaan.setText("Sedikit lagi, "+strNamaPemesan+"!");
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        String hargaFormatted = String.valueOf(total);


        tvTotal.setText(formatRupiah.format((double)Integer.parseInt(hargaFormatted)));

        return view;    }

    private void loadComponent() {
        tvSapaan = (TextView) view.findViewById(R.id.tvSapaan);
        tvSalon = (TextView) view.findViewById(R.id.tvSalon);
        tvAlamat = (TextView) view.findViewById(R.id.tvAlamatSalon);
        tvNamaGuest = (TextView) view.findViewById(R.id.tvGuest);
        tvTelpGuest = (TextView) view.findViewById(R.id.tvPhone);
        tvJumlah = (TextView) view.findViewById(R.id.tvPeople);
        tvTanggal = (TextView) view.findViewById(R.id.tvTanggal);
        tvJam = (TextView) view.findViewById(R.id.tvJam);
        tvTotal = (TextView) view.findViewById(R.id.tvTotal);
        progressDialog = new SpotsDialog(getContext(), R.style.Custom);
        btnKonfirmasi = (Button) view.findViewById(R.id.btnKonfirmasiReservasi);

        tvSalon.setText(strBunNmStore);
        tvAlamat.setText(strBunAddress);
        tvNamaGuest.setText(strBunNama);
        tvTelpGuest.setText(strBunTelp);
        tvJumlah.setText(strBunPeople+" orang");
    //    tvTanggal.setText(setEdDate(days, months, years));
        tvTanggal.setText(strBunDate);
        tvJam.setText(strBunTime);

        idArrayList = new ArrayList<String>(Arrays.asList(strBunIdArr.split(",")));
        namaArrayList = new ArrayList<String>(Arrays.asList(strBunNmArr.split(",")));
        qtyArrayList = new ArrayList<String>(Arrays.asList(strBunQtyArr.split(",")));
        hrgArrayList = new ArrayList<String>(Arrays.asList(strBunHrgArr.split(",")));
        subArrayList = new ArrayList<String>(Arrays.asList(strBunSubArr.split(",")));

       // Toast.makeText(getContext(), "1: "+idArrayList.get(0).toString(), Toast.LENGTH_SHORT).show();

        for(int i = 0 ; i<idArrayList.size() ; i++){
            cartItem = new CartTreatment();
            cartItem.setStrIdTr(idArrayList.get(i).toString());
            cartItem.setStrNmTr(namaArrayList.get(i).toString());
            cartItem.setStrQty(qtyArrayList.get(i).toString());
            cartItem.setStrHrg(hrgArrayList.get(i).toString());
            cartItem.setStrSub(subArrayList.get(i).toString());
            listCart.add(cartItem);
        }
      //  Toast.makeText(getContext(), "list : "+listCart.get(0).getStrIdTr(), Toast.LENGTH_SHORT).show();

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewCart);

        adapter = new CartAdapter(listCart);

        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        btnKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookingFragPresenterImp.addNewBooked(strBunIdStore, strBunIdUser, strBunDate, strBunTime, strBunNama, strBunTelp, strBunPeople, String.valueOf(total));
            //    Toast.makeText(getContext(), "tes "+strBunIdStore+"|"+strBunIdStore+"|"+strBunDate+"|"+strBunTime+"|"+strBunNama+"|"+strBunTelp+"|"+strBunPeople+"|"+total, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public ArrayList<Integer> subTotalArrToInt(ArrayList<String> strArrSubTotal){
       ArrayList<Integer> result = new ArrayList<Integer>();
       for(String stringValue : strArrSubTotal){
           try{
               result.add(Integer.parseInt(stringValue));
           } catch (NumberFormatException nfe){}
       }
       return result;
    }

    public int sumSubTotal(ArrayList<Integer> intArrSubTotal){
        int total = 0;
        for(int tot : intArrSubTotal)
            total += tot;

        return  total;
    }

    public String getDayName(String year,String month, String day){

        String namahari="";

        int intyear = Integer.parseInt(year);
        int intmonth = Integer.parseInt(month);
        int intday = Integer.parseInt(day);

     //   Toast.makeText(getContext(), year+" "+month+" "+day, Toast.LENGTH_SHORT).show();
        String dateString = String.format("%d-%d-%d", intyear, intmonth, intday);
        Date dates = null;
        try {
            dates = new SimpleDateFormat("yyyy-M-d").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(dates);
    //    Toast.makeText(getContext(), "hari: "+dayOfWeek, Toast.LENGTH_SHORT).show();

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

    public String setEdDate(int day, int month, int year){
        String tampungmonth;
        String tampungdate;
        if (String.valueOf(day).length() < 2) {
            tampungdate = "0" + String.valueOf(day);
        } else {
            tampungdate = String.valueOf(day);
        }

        if (String.valueOf(month + 1).length() < 2) {
            tampungmonth = "0" + String.valueOf(month+ 1);
        } else {
            tampungmonth = String.valueOf(month + 1);
        }


     //   Toast.makeText(getContext(), "hari: "+getDayName(String.valueOf(year), tampungmonth, String.valueOf(month)), Toast.LENGTH_SHORT).show();

        String strFinDate = tampungdate+"/"+tampungmonth+"/"+year;
      //  Toast.makeText(getContext(), "tanggal formatted:"+strFinDate, Toast.LENGTH_SHORT).show();

        return getDayName(String.valueOf(year), tampungmonth, String.valueOf(month)) + ", " + tampungdate + " " + getMonthName(tampungmonth)+" "+year;

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


    public void dismissSpotLoading(){
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public void showSpotLoading(){
        progressDialog.show();
        progressDialog.setCancelable(true);
    }

    public void addingBookingSuccess(){
        bookingFragPresenterImp.getLatestBookingCreatedByUser(strBunIdUser);
    }

    public void doAddDetail(String strIdBooking){
        //Toast.makeText(getContext(), "last "+strIdBooking, Toast.LENGTH_SHORT).show();
        bookingFragPresenterImp.addDetailBooked(strIdBooking,idArrayList, qtyArrayList, hrgArrayList, subArrayList);
    }

    public void addingDetailBookedSuccess(){
        initArray();

        dismissSpotLoading();

        new SweetAlertDialog(getContext(), SUCCESS_TYPE)
                .setTitleText("Hore!")
                .setContentText("Reservasi berhasil dibuat! \n Harap menunggu konfirmasi dari pihak outlet")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        Intent i = new Intent(getContext(), TabbedMenu.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                    }
                })
                .show();
    }

    public void initArray(){
        idArrayList.clear();
        namaArrayList.clear();
        qtyArrayList.clear();
        subArrayList.clear();
    }

    public void addingDetailBookedFailed(){

    }

    public void errorAddingBooking(){
        Toast.makeText(getContext(), "Anda mencapai batas maksimal reservasi. Selesaikan reservasi lama untuk melanjutkan", Toast.LENGTH_SHORT).show();
    }

    public void dataInValid1(){
        Toast.makeText(getContext(), "Data yang Anda masukkan tidak valid", Toast.LENGTH_SHORT).show();
    }

    public void dataInValid2(){
        Toast.makeText(getContext(), "Data yang Anda masukkan tidak valid", Toast.LENGTH_SHORT).show();
    }

    public void dataInValid3(){
        Toast.makeText(getContext(), "Data yang Anda masukkan tidak valid", Toast.LENGTH_SHORT).show();
    }


    public void ErrorResponseFailed(){
        Toast.makeText(getContext(), "Gagal parsing JSON", Toast.LENGTH_SHORT).show();
    }

    public void ErrorConnectionFailed(){
        Toast.makeText(getContext(), "Koneksi API gagal", Toast.LENGTH_SHORT).show();
    }
}
