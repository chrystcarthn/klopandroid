package appdeveloper.klop.klop.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import appdeveloper.klop.klop.Adapter.MngNewsAdapter;
import appdeveloper.klop.klop.Adapter.MngScheduleAdapter;
import appdeveloper.klop.klop.Model.Schedule;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.StoreSettingsPresenterImp;
import appdeveloper.klop.klop.R;
import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

public class ManageSchedule extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdUser, strIdStore, strTempTimeOpen, strTempTimeClosed, getHour, getMinutes;
    Toolbar toolbar;
    TextView tvEmpty;
    RecyclerView recyclerView;
    private WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    public String[] listHari;
    FloatingActionButton fabAdd;
    Button btnAddSchedule;
    TextView tvJudul;
    EditText edHari, edBuka, edTutup;
    TextInputLayout hariWrapper, bukaWrapper, tutupWrapper;
    ImageButton btnClose;
    Button btnAddNewSch;
    AlertDialog dialog;

    int flagAll1=0, flagAll2=0;

    ArrayList<String> strDaysArraylist = new ArrayList<String>();
    ArrayList<String> strTimeOpenArraylist = new ArrayList<String>();
    ArrayList<String> strTimeClosedArraylist = new ArrayList<String>();

    StoreSettingsPresenterImp storeSettingsPresenterImp;
    MngScheduleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_schedule);

        Bundle b = getIntent().getExtras();
        strIdStore = b.getString("idStore");

        session = new SessionPreference(this);
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);
        tvEmpty = (TextView) findViewById(R.id.tvEmptySch);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewSchedule);
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAddSch);


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

        storeSettingsPresenterImp = new StoreSettingsPresenterImp(this);

        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) findViewById(R.id.main_swipe2);
        mWaveSwipeRefreshLayout.setWaveColor(getResources().getColor(R.color.colorPrimary));
        mWaveSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.white));

        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mWaveSwipeRefreshLayout.setRefreshing(false);
                loadRecyclerViewSchedule();

            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ManageSchedule.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_add_schedule, null);

                listHari = getResources().getStringArray(R.array.hari2_arrays);

                tvJudul = (TextView) mView.findViewById(R.id.tvJudul);
                hariWrapper = (TextInputLayout) mView.findViewById(R.id.input_layout_hari);
                bukaWrapper = (TextInputLayout) mView.findViewById(R.id.input_layout_buka);
                tutupWrapper = (TextInputLayout) mView.findViewById(R.id.input_layout_tutup);
                btnClose = (ImageButton) mView.findViewById(R.id.btnClose);
                edHari = (EditText) mView.findViewById(R.id.edHari);
                edBuka = (EditText) mView.findViewById(R.id.edBuka);
                edTutup = (EditText) mView.findViewById(R.id.edTutup);
                btnAddNewSch = (Button) mView.findViewById(R.id.btnAddNewSch);

                tvJudul.setText("Jadwal Baru");
                btnAddNewSch.setText("Tambah");

                mBuilder.setView(mView);
                dialog = mBuilder.create();

                edHari.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(ManageSchedule.this);
                        mBuilder.setTitle("Pada hari apa outlet buka?");
                        mBuilder.setSingleChoiceItems(listHari, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                edHari.setText(listHari[which]);

                                dialog.dismiss();
                            }
                        });
                        AlertDialog mDialog = mBuilder.create();
                        mDialog.show();
                    }
                });

                edBuka.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flagAll1=1;
                        flagAll2=0;
                        String hour = edBuka.getText().toString().substring(0,2);
                        String minute = edBuka.getText().toString().substring(3,5);
                        showTime(hour, minute);
                    }
                });

                edTutup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        flagAll1=0;
                        flagAll2=1;
                        String hour = edTutup.getText().toString().substring(0,2);
                        String minute = edTutup.getText().toString().substring(3,5);
                        showTime(hour, minute);
                    }
                });


                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        hideSoftKeyboard();
                        dialog.dismiss();

                    }
                });

                btnAddNewSch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      if(edHari.getText().toString().equals("Setiap hari")){
                           new SweetAlertDialog(ManageSchedule.this, SweetAlertDialog.WARNING_TYPE)
                                   .setTitleText("Hapus jadwal")
                                   .setContentText("Semua jadwal lama akan dihapus dan diganti dengan jadwal buka setiap hari")
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
                                   storeSettingsPresenterImp.deleteAllSchedules(strIdStore);
                                   initArray();
                                   openEveryDay();
                                   storeSettingsPresenterImp.addSchedule(strDaysArraylist, strTimeOpenArraylist, strTimeClosedArraylist, strIdStore);
                                   sweetAlertDialog.dismiss();
                               }
                           }).show();
                      } else if(!edHari.getText().toString().equals("Setiap hari")) {
                            storeSettingsPresenterImp.addHarianSchedule(edHari.getText().toString(), edBuka.getText().toString(), edTutup.getText().toString(), strIdStore);
                      }
                    }
                });

                dialog.show();
            }
        });

        loadRecyclerViewSchedule();
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

    }

    public void openEveryDay(){
        String open = edBuka.getText().toString();
        String close = edTutup.getText().toString();

        String[] everyDay = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};
        String[] openArray = {open, open, open, open, open, open, open};
        String[] closeArray = {close, close, close, close, close, close, close};
        strDaysArraylist.addAll(Arrays.asList(everyDay));
        strTimeOpenArraylist.addAll(Arrays.asList(openArray));
        strTimeClosedArraylist.addAll(Arrays.asList(closeArray));

    }

    public void initArray(){
        strDaysArraylist.clear();
        strTimeOpenArraylist.clear();
        strTimeClosedArraylist.clear();
    }


    public void showTime(String hour, String minute) {
        Date date = null;

        DateFormat formatter = new SimpleDateFormat("HH:mm");
        try {
            date = formatter.parse(hour+":"+minute);
        } catch (ParseException e) {
            e.printStackTrace();
        }

      //  Toast.makeText(this, hour+":"+minute, Toast.LENGTH_SHORT).show();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        TimePickerDialog tpd = TimePickerDialog.newInstance(
                ManageSchedule.this,
                Integer.parseInt(hour),
                Integer.parseInt(minute),
                true);

        tpd.setThemeDark(false);
        tpd.vibrate(false);
        tpd.setTitle("Atur Jam");
        tpd.dismissOnPause(false);
        tpd.enableSeconds(false);
        tpd.enableMinutes(true);
        if (true) {
            tpd.setAccentColor(getResources().getColor(R.color.colorPrimaryDarker));
        }

        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }



    public void loadRecyclerViewSchedule(){
        mWaveSwipeRefreshLayout.setRefreshing(true);
        storeSettingsPresenterImp.showSchedule(strIdStore);
    }

    public void generateScheduleResponse(ArrayList<Schedule> scheduleArrayList){
        recyclerView.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);
        adapter = new MngScheduleAdapter(scheduleArrayList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }

    public void ErrorResponseFailed(){
        Toast.makeText(this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }


    public void ErrorConnectionFailed() {
        Toast.makeText(this, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();
        mWaveSwipeRefreshLayout.setRefreshing(false);
    }

    public void warningNoDataFound(){
        //  frame.setVisibility(View.GONE);
        tvEmpty.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        mWaveSwipeRefreshLayout.setRefreshing(false);

    }

    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        getHour = String.valueOf(hourOfDay);
        getMinutes = String.valueOf(minute);

        if(getHour.length() == 1){
            getHour = "0" + getHour;
        }

        if(getMinutes.length() == 1){
            getMinutes = "0" + getMinutes;
        }


        if(flagAll1 == 1) {
            if(!isAll1Valid(getHour)){
                showTime(getHour, getMinutes);
                Toast.makeText(this, "Harap memasukan jam buka di bawah jam tutup", Toast.LENGTH_SHORT).show();
            } else
                edBuka.setText(getHour + ":" + getMinutes);
        }
        else if(flagAll2 == 1){
            if(!isAll2Valid(getHour)){
                showTime(getHour, getMinutes);
                Toast.makeText(this, "Harap memasukan jam tutup di atas jam buka", Toast.LENGTH_SHORT).show();
            }
            else edTutup.setText(getHour + ":" + getMinutes);
        }
    }

    public boolean isAll1Valid(String getHours){
        String trimClose = edTutup.getText().toString().substring(0,2);
        int jamTutup = Integer.parseInt(trimClose);
        int jamBuka = Integer.parseInt(getHours);

        if(jamTutup == 0){
            return true;
        }
        else{
            if( jamBuka > jamTutup ){
                return false;
            }
            else return true;
        }
    }


    public boolean isAll2Valid(String getHours){
        String trimOpen = edBuka.getText().toString().substring(0,2);
        int jamBuka = Integer.parseInt(trimOpen);
        int jamTutup = Integer.parseInt(getHours);

        if( jamTutup < jamBuka ){
            return false;
        }
        else return true;
    }

    public void addingHarianSuccess(){
        btnAddNewSch.setText("Tambah lagi");
        edHari.setText("Setiap hari");
        edBuka.setText("00:00");
        edTutup.setText("00:00");
        loadRecyclerViewSchedule();
     //   Toast.makeText(this, "jadwal berhasil ditambah", Toast.LENGTH_SHORT).show();

    }

    public void addingHarianFailed(){
        Toast.makeText(this, "Gagal menambah. Coba ulangi lagi nanti", Toast.LENGTH_SHORT).show();
    }

    public void addingAllScheduleSuccess(){
       // Toast.makeText(this, "semua jadwal berhasil ditambah", Toast.LENGTH_SHORT).show();
    }

    public void addingAllScheduleFailed(){
        Toast.makeText(this, "Gagal menambah. Coba ulangi lagi nanti", Toast.LENGTH_SHORT).show();
    }

    public void deleteAllSuccess(){
       // Toast.makeText(this, "semua jadwal berhasil dihapus", Toast.LENGTH_SHORT).show();
    }

    public void deleteAllFailed(){
        Toast.makeText(this, "Gagal menghapus. Coba ulangi lagi nanti", Toast.LENGTH_SHORT).show();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public void showErrorDays(){
        bukaWrapper.setErrorEnabled(false);
        tutupWrapper.setErrorEnabled(false);
        hariWrapper.setError("Harap diisi");
        requestFocus(edHari);
    }

    public void showErrorBuka(){
        hariWrapper.setErrorEnabled(false);
        tutupWrapper.setErrorEnabled(false);
        bukaWrapper.setError("Harap diisi");
        requestFocus(edHari);
    }

    public void showErrorTutup(){
        bukaWrapper.setErrorEnabled(false);
        hariWrapper.setErrorEnabled(false);
        tutupWrapper.setError("Harap diisi");
        requestFocus(edHari);
    }

    public void showNoError(){
        bukaWrapper.setErrorEnabled(false);
        hariWrapper.setErrorEnabled(false);
        tutupWrapper.setErrorEnabled(false);

    }

    public void showErrorStore(){
        Toast.makeText(this, "Tidak ada outlet terpilih", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        loadRecyclerViewSchedule();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadRecyclerViewSchedule();
    }
}
