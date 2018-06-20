package appdeveloper.klop.klop.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import appdeveloper.klop.klop.Activity.ManageSchedule;
import appdeveloper.klop.klop.Model.Schedule;
import appdeveloper.klop.klop.PresenterImp.StoreSettingsPresenterImp;
import appdeveloper.klop.klop.R;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CMDDJ on 6/7/2018.
 */

public class MngScheduleAdapter extends RecyclerView.Adapter<MngScheduleAdapter.ViewHolder> implements TimePickerDialog.OnTimeSetListener {

    TextView tvJudul;
    EditText edHari, edBuka, edTutup;
    TextInputLayout hariWrapper, bukaWrapper, tutupWrapper;
    ImageButton btnClose;
    String strIdSch, getHour, getMinutes;
    String strIdStore;
    Button btnAddNewSch;
    StoreSettingsPresenterImp storeSettingsPresenterImp;
    private ArrayList<Schedule> dataSchedule;
    private Context context;
    int flagAll1=0, flagAll2=0;
    public String[] listHari;
    public AlertDialog dialog;
    FragmentActivity fragmentActivity;


    public MngScheduleAdapter(ArrayList<Schedule> dataSchedule, Context context) {
        this.dataSchedule = dataSchedule;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        storeSettingsPresenterImp = new StoreSettingsPresenterImp(this);

        View v = layoutInflater.inflate(R.layout.list_item_schedule, parent, false);
        return new ViewHolder(v);   }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvHari.setText(dataSchedule.get(position).getDAYS());
        holder.tvBuka.setText(dataSchedule.get(position).getTIMEOPEN());
        holder.tvTutup.setText(dataSchedule.get(position).getTIMECLOSED());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(dataSchedule.size() < 2) {
                    Toast.makeText(context, "Jadwal tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {

                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Hapus jadwal")
                            .setContentText("Jadwal ini akan dihapus")
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
                            strIdSch = dataSchedule.get(position).getIDSCHEDULE();
                            strIdStore = dataSchedule.get(position).getIDSTORE();
                            // Toast.makeText(context, "delete "+strIdStore+"|"+strIdTr, Toast.LENGTH_SHORT).show();
                            storeSettingsPresenterImp.deleteSchedule(strIdSch);
                            ((ManageSchedule) context).loadRecyclerViewSchedule();

                            sweetAlertDialog.dismiss();
                        }
                    }).show();
                }
            }
        });


        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(context, "edit "+dataTreatments.get(position).getNAMETREATMENT(), Toast.LENGTH_SHORT).show();

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

                View mView = layoutInflater.inflate(R.layout.dialog_add_schedule, null);

                tvJudul = (TextView) mView.findViewById(R.id.tvJudul);
                hariWrapper = (TextInputLayout) mView.findViewById(R.id.input_layout_hari);
                bukaWrapper = (TextInputLayout) mView.findViewById(R.id.input_layout_buka);
                tutupWrapper = (TextInputLayout) mView.findViewById(R.id.input_layout_tutup);
                btnClose = (ImageButton) mView.findViewById(R.id.btnClose);
                edHari = (EditText) mView.findViewById(R.id.edHari);
                edBuka = (EditText) mView.findViewById(R.id.edBuka);
                edTutup = (EditText) mView.findViewById(R.id.edTutup);
                btnAddNewSch = (Button) mView.findViewById(R.id.btnAddNewSch);


                tvJudul.setText("Ubah Jadwal");
                btnAddNewSch.setText("Simpan");

                edHari.setText(dataSchedule.get(position).getDAYS());
                edBuka.setText(dataSchedule.get(position).getTIMEOPEN());
                edTutup.setText(dataSchedule.get(position).getTIMECLOSED());
                strIdSch = dataSchedule.get(position).getIDSCHEDULE();
                strIdStore = dataSchedule.get(position).getIDSTORE();

                listHari = context.getResources().getStringArray(R.array.hari_arrays);


                mBuilder.setView(mView);
                dialog = mBuilder.create();

                edHari.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
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
                        dialog.dismiss();
                    }
                });

                btnAddNewSch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edHari.getText().toString().equals("")){
                            //  Toast.makeText(context, "error nama", Toast.LENGTH_SHORT).show();
                            showErrorDays();
                        } else
                        if(edBuka.getText().toString().equals("00:00")){
                            //   Toast.makeText(context, "error harga", Toast.LENGTH_SHORT).show();
                            showErrorBuka();
                        } else
                        if(edTutup.getText().toString().equals("00:00")){
                            //   Toast.makeText(context, "error harga", Toast.LENGTH_SHORT).show();
                            showErrorTutup();
                        }
                        else if(edBuka.getText().toString().equals("") & edBuka.getText().toString().equals("00:00") & edTutup.getText().toString().equals("00:00")){
                            //  Toast.makeText(context, "error nama", Toast.LENGTH_SHORT).show();
                            showErrorDays();
                        } else {
                            showNoError();
                            storeSettingsPresenterImp.updateSchedule(strIdSch, edHari.getText().toString(), edBuka.getText().toString(), edTutup.getText().toString());
                        }
                    }
                });

                dialog.show();
            }
        });
    }

    public void showTime(String hour, String minute) {
        Date date = null;

        DateFormat formatter = new SimpleDateFormat("HH:mm");
        try {
            date = formatter.parse(hour+":"+minute);
        } catch (ParseException e) {
            e.printStackTrace();
        }

//        Toast.makeText(context, hour+":"+minute, Toast.LENGTH_SHORT).show();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        TimePickerDialog tpd = TimePickerDialog.newInstance(
                MngScheduleAdapter.this,
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
            tpd.setAccentColor(context.getResources().getColor(R.color.colorPrimaryDarker));
        }

        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                Log.d("TimePicker", "Dialog was cancelled");
            }
        });



        tpd.show(((AppCompatActivity)context).getFragmentManager(), "Timepickerdialog");
    }


    public void showErrorDays(){
        bukaWrapper.setErrorEnabled(false);
        tutupWrapper.setErrorEnabled(false);
        hariWrapper.setError("Harap mengisi hari");

    }

    public void showErrorBuka(){
        hariWrapper.setErrorEnabled(false);
        tutupWrapper.setErrorEnabled(false);
        bukaWrapper.setError("Harap mengisi jam buka");

    }

    public void showErrorTutup(){
        bukaWrapper.setErrorEnabled(false);
        hariWrapper.setErrorEnabled(false);
        tutupWrapper.setError("Harap mengisi jam tutup");

    }

    public void showNoError(){
        bukaWrapper.setErrorEnabled(false);
        hariWrapper.setErrorEnabled(false);
        tutupWrapper.setErrorEnabled(false);

    }

    public void showErrorStore(){
        Toast.makeText(context, "Tidak ada outlet terpilih", Toast.LENGTH_SHORT).show();
    }


    public void updateSuccess(){
        ((ManageSchedule)context).loadRecyclerViewSchedule();
        dialog.dismiss();
    }

    public void ErrorUpdate(){
        Toast.makeText(context, "Data tidak valid. Harap periksa kembali data Anda", Toast.LENGTH_SHORT).show();
        // mWaveSwipeRefreshLayout.setRefreshing(false);
    }

    public void deleteSuccess(){
        Toast.makeText(context, "Jadwal dihapus. Sapu layar untuk memperbaharui data di halaman", Toast.LENGTH_SHORT).show();
    }

    public void deleteFailed(){
        Toast.makeText(context, "Gagal menghapus jadwal. Coba ulangi lagi nanti", Toast.LENGTH_SHORT).show();
    }


    public void ErrorResponseFailed(){
        Toast.makeText(context, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();

    }


    public void ErrorConnectionFailed() {
        Toast.makeText(context,  "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();

    }


    @Override
    public int getItemCount() {
        if(dataSchedule!=null){
            return dataSchedule.size();
        } else{
            return 0;
        }
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
                Toast.makeText(context, "Harap memasukan jam buka di bawah jam tutup", Toast.LENGTH_SHORT).show();
            } else
                edBuka.setText(getHour + ":" + getMinutes);
        }
        else if(flagAll2 == 1){
            if(!isAll2Valid(getHour)){
                showTime(getHour, getMinutes);
                Toast.makeText(context, "Harap memasukan jam tutup di atas jam buka", Toast.LENGTH_SHORT).show();
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvHari, tvBuka, tvTutup;
        ImageButton btnDelete, btnEdit;
        String strIdStore;
        LinearLayout linearLayout;

        ViewHolder(View itemView) {
            super(itemView);

            tvHari = (TextView) itemView.findViewById(R.id.tvHari);
            tvBuka = (TextView) itemView.findViewById(R.id.tvBuka);
            tvTutup = (TextView) itemView.findViewById(R.id.tvTutup);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
            btnEdit = (ImageButton) itemView.findViewById(R.id.btnEdit);
        }
    }
}

