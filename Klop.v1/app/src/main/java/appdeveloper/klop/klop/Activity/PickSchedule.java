package appdeveloper.klop.klop.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.R;

public class PickSchedule extends AppCompatActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener {

    BroadcastReceiver receiver;
    String flagAturJadwal;
    SessionPreference session;
    HashMap<String, String> userdata;
    String strIdUser, getHour, getMinutes;
    int flagSenin1=0, flagSenin2=0,
            flagSelasa1=0, flagSelasa2=0,
            flagRabu1=0, flagRabu2=0,
            flagKamis1=0, flagKamis2=0,
            flagJumat1=0, flagJumat2=0,
            flagSabtu1=0, flagSabtu2=0,
            flagMinggu1=0, flagMinggu2=0;

    Toolbar toolbar;
    Button btnSelesai;
    CheckBox cbSenin, cbSelasa, cbRabu, cbKamis, cbJumat, cbSabtu, cbMinggu;
    EditText edSenin1, edSenin2, edSelasa1, edSelasa2, edRabu1, edRabu2, edKamis1, edKamis2, edJumat1, edJumat2, edSabtu1, edSabtu2, edMinggu1, edMinggu2;
    TextInputLayout wrapperSenin1, wrapperSenin2, wrapperSelasa1, wrapperSelasa2, wrapperRabu1, wrapperRabu2, wrapperKamis1, wrapperKamis2, wrapperJumat1, wrapperJumat2, wrapperSabtu1, wrapperSabtu2, wrapperMinggu1, wrapperMinggu2;
    TextView tvError, expandableButton1, expandableButton2, expandableButton3, expandableButton4, expandableButton5, expandableButton6, expandableButton7;
    ExpandableRelativeLayout  expandableLayout1, expandableLayout2, expandableLayout3, expandableLayout4, expandableLayout5, expandableLayout6, expandableLayout7;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    int initAddStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_schedule);

        session = new SessionPreference(this);
        userdata = session.getUserDetails();
        strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };

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

        btnSelesai = (Button) findViewById(R.id.btnDataDetilLanjutkan);

        tvError = (TextView) findViewById(R.id.tvErrorPickSchedule);



        edSenin1 = (EditText) findViewById(R.id.edSenin1);
        edSenin2 = (EditText) findViewById(R.id.edSenin2);
        edSelasa1 = (EditText) findViewById(R.id.edSelasa1);
        edSelasa2 = (EditText) findViewById(R.id.edSelasa2);
        edRabu1 = (EditText) findViewById(R.id.edRabu1);
        edRabu2 = (EditText) findViewById(R.id.edRabu2);
        edKamis1 = (EditText) findViewById(R.id.edKamis1);
        edKamis2 = (EditText) findViewById(R.id.edKamis2);
        edJumat1 = (EditText) findViewById(R.id.edJumat1);
        edJumat2 = (EditText) findViewById(R.id.edJumat2);
        edSabtu1 = (EditText) findViewById(R.id.edSabtu1);
        edSabtu2 = (EditText) findViewById(R.id.edSabtu2);
        edMinggu1 = (EditText) findViewById(R.id.edMinggu1);
        edMinggu2 = (EditText) findViewById(R.id.edMinggu2);

        wrapperSenin1 = (TextInputLayout) findViewById(R.id.wrapperSenin1);

        cbSenin = (CheckBox) findViewById(R.id.cbSenin);
        cbSelasa = (CheckBox) findViewById(R.id.cbSelasa);
        cbRabu = (CheckBox) findViewById(R.id.cbRabu);
        cbKamis = (CheckBox) findViewById(R.id.cbKamis);
        cbJumat = (CheckBox) findViewById(R.id.cbJumat);
        cbSabtu = (CheckBox) findViewById(R.id.cbSabtu);
        cbMinggu = (CheckBox) findViewById(R.id.cbMinggu);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        Intent i = getIntent();
        try {
            flagAturJadwal = i.getStringExtra("flagAturJadwal");
          //  Toast.makeText(this, "Terima flagAturJadwal dari AddStore :" + flagAturJadwal, Toast.LENGTH_SHORT).show();
        }catch (Exception e){e.printStackTrace();}


        if(flagAturJadwal.equals("0")){
            editor.clear();
            editor.commit();
            initRefreshLayout();
          //  Toast.makeText(this, "condition false | "+flagAturJadwal, Toast.LENGTH_SHORT).show();
        }else if(flagAturJadwal.equals("1")){

          //  Toast.makeText(this, "condition true", Toast.LENGTH_SHORT).show();
            getSavedLayoutSchedule();
        }// else   Toast.makeText(this, "condition else |"+flagAturJadwal, Toast.LENGTH_SHORT).show();

        cbSenin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edSenin1.setEnabled(true);
                    edSenin2.setEnabled(true);
                    edSenin1.setTextColor(getResources().getColor(R.color.primaryText));
                    edSenin2.setTextColor(getResources().getColor(R.color.primaryText));
                }else{
                    edSenin1.setText("00:00");
                    edSenin2.setText("00:00");
                    edSenin1.setEnabled(false);
                    edSenin2.setEnabled(false);
                    edSenin1.setTextColor(getResources().getColor(R.color.secondaryText2));
                    edSenin2.setTextColor(getResources().getColor(R.color.secondaryText2));
                }
            }
        });

        cbSelasa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edSelasa1.setEnabled(true);
                    edSelasa2.setEnabled(true);
                    edSelasa1.setTextColor(getResources().getColor(R.color.primaryText));
                    edSelasa2.setTextColor(getResources().getColor(R.color.primaryText));
                }else{
                    edSelasa1.setText("00:00");
                    edSelasa2.setText("00:00");
                    edSelasa1.setEnabled(false);
                    edSelasa2.setEnabled(false);
                    edSelasa1.setTextColor(getResources().getColor(R.color.secondaryText2));
                    edSelasa2.setTextColor(getResources().getColor(R.color.secondaryText2));
                }
            }
        });

        cbRabu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edRabu1.setEnabled(true);
                    edRabu2.setEnabled(true);
                    edRabu1.setTextColor(getResources().getColor(R.color.primaryText));
                    edRabu2.setTextColor(getResources().getColor(R.color.primaryText));
                }else{
                    edRabu1.setText("00:00");
                    edRabu2.setText("00:00");
                    edRabu1.setEnabled(false);
                    edRabu2.setEnabled(false);
                    edRabu1.setTextColor(getResources().getColor(R.color.secondaryText2));
                    edRabu2.setTextColor(getResources().getColor(R.color.secondaryText2));
                }
            }
        });

        cbKamis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edKamis1.setEnabled(true);
                    edKamis2.setEnabled(true);
                    edKamis1.setTextColor(getResources().getColor(R.color.primaryText));
                    edKamis2.setTextColor(getResources().getColor(R.color.primaryText));
                }else{
                    edKamis1.setText("00:00");
                    edKamis2.setText("00:00");
                    edKamis1.setEnabled(false);
                    edKamis2.setEnabled(false);
                    edKamis1.setTextColor(getResources().getColor(R.color.secondaryText2));
                    edKamis2.setTextColor(getResources().getColor(R.color.secondaryText2));
                }
            }
        });

        cbJumat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edJumat1.setEnabled(true);
                    edJumat2.setEnabled(true);
                    edJumat1.setTextColor(getResources().getColor(R.color.primaryText));
                    edJumat2.setTextColor(getResources().getColor(R.color.primaryText));
                }else{
                    edJumat1.setText("00:00");
                    edJumat2.setText("00:00");
                    edJumat1.setEnabled(false);
                    edJumat2.setEnabled(false);
                    edJumat1.setTextColor(getResources().getColor(R.color.secondaryText2));
                    edJumat2.setTextColor(getResources().getColor(R.color.secondaryText2));
                }
            }
        });

        cbSabtu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edSabtu1.setEnabled(true);
                    edSabtu2.setEnabled(true);
                    edSabtu1.setTextColor(getResources().getColor(R.color.primaryText));
                    edSabtu2.setTextColor(getResources().getColor(R.color.primaryText));
                }else{
                    edSabtu1.setText("00:00");
                    edSabtu2.setText("00:00");
                    edSabtu1.setEnabled(false);
                    edSabtu2.setEnabled(false);
                    edSabtu1.setTextColor(getResources().getColor(R.color.secondaryText2));
                    edSabtu2.setTextColor(getResources().getColor(R.color.secondaryText2));
                }
            }
        });

        cbMinggu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edMinggu1.setEnabled(true);
                    edMinggu2.setEnabled(true);
                    edMinggu1.setTextColor(getResources().getColor(R.color.primaryText));
                    edMinggu2.setTextColor(getResources().getColor(R.color.primaryText));
                }else{
                    edMinggu1.setText("00:00");
                    edMinggu2.setText("00:00");
                    edMinggu1.setEnabled(false);
                    edMinggu2.setEnabled(false);
                    edMinggu1.setTextColor(getResources().getColor(R.color.secondaryText2));
                    edMinggu2.setTextColor(getResources().getColor(R.color.secondaryText2));
                }
            }
        });

        expandableButton1 = (TextView)findViewById(R.id.expandableButton1);
        expandableButton2 = (TextView)findViewById(R.id.expandableButton2);
        expandableButton3 = (TextView)findViewById(R.id.expandableButton3);
        expandableButton4 = (TextView)findViewById(R.id.expandableButton4);
        expandableButton5 = (TextView)findViewById(R.id.expandableButton5);
        expandableButton6 = (TextView)findViewById(R.id.expandableButton6);
        expandableButton7 = (TextView)findViewById(R.id.expandableButton7);

        expandableLayout1 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1);
        expandableLayout2 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout2);
        expandableLayout3 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout3);
        expandableLayout4 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout4);
        expandableLayout5 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout5);
        expandableLayout6 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout6);
        expandableLayout7 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout7);

        expandableButton1.setOnClickListener(this);
        expandableButton2.setOnClickListener(this);
        expandableButton3.setOnClickListener(this);
        expandableButton4.setOnClickListener(this);
        expandableButton5.setOnClickListener(this);
        expandableButton6.setOnClickListener(this);
        expandableButton7.setOnClickListener(this);

        edSenin1.setOnClickListener(this);
        edSenin2.setOnClickListener(this);

        edSelasa1.setOnClickListener(this);
        edSelasa2.setOnClickListener(this);

        edRabu1.setOnClickListener(this);
        edRabu2.setOnClickListener(this);

        edKamis1.setOnClickListener(this);
        edKamis2.setOnClickListener(this);

        edJumat1.setOnClickListener(this);
        edJumat2.setOnClickListener(this);

        edSabtu1.setOnClickListener(this);
        edSabtu2.setOnClickListener(this);

        edMinggu1.setOnClickListener(this);
        edMinggu2.setOnClickListener(this);

        btnSelesai.setOnClickListener(this);

    }

    public void getSavedLayoutSchedule(){
        if (pref.getBoolean("cbSenin", false) == true){ //false is default value
            cbSenin.setChecked(true); //it was checked
            edSenin1.setEnabled(true);
            edSenin2.setEnabled(true);
            edSenin1.setText(pref.getString("edSenin1", edSenin1.getText().toString()));
            edSenin2.setText(pref.getString("edSenin2", edSenin2.getText().toString()));
            edSenin1.setTextColor(getResources().getColor(R.color.primaryText));
            edSenin2.setTextColor(getResources().getColor(R.color.primaryText));
        } else{
            cbSenin.setChecked(false); //it was NOT checked
        }


        if (pref.getBoolean("cbSelasa", false) == true){ //false is default value
            cbSelasa.setChecked(true); //it was checked
            edSelasa1.setEnabled(true);
            edSelasa2.setEnabled(true);
            edSelasa1.setText(pref.getString("edSelasa1", edSelasa1.getText().toString()));
            edSelasa2.setText(pref.getString("edSelasa2", edSelasa2.getText().toString()));
            edSelasa1.setTextColor(getResources().getColor(R.color.primaryText));
            edSelasa2.setTextColor(getResources().getColor(R.color.primaryText));
        } else{
            cbSelasa.setChecked(false); //it was NOT checked
        }

        if (pref.getBoolean("cbRabu", false) == true){ //false is default value
            cbRabu.setChecked(true); //it was checked
            edRabu1.setEnabled(true);
            edRabu2.setEnabled(true);
            edRabu1.setText(pref.getString("edRabu1", edRabu1.getText().toString()));
            edRabu2.setText(pref.getString("edRabu2", edRabu2.getText().toString()));
            edRabu1.setTextColor(getResources().getColor(R.color.primaryText));
            edRabu2.setTextColor(getResources().getColor(R.color.primaryText));
        } else{
            cbRabu.setChecked(false); //it was NOT checked
        }

        if (pref.getBoolean("cbKamis", false) == true){ //false is default value
            cbKamis.setChecked(true); //it was checked
            edKamis1.setEnabled(true);
            edKamis2.setEnabled(true);
            edKamis1.setText(pref.getString("edKamis1", edKamis1.getText().toString()));
            edKamis2.setText(pref.getString("edKamis2", edKamis2.getText().toString()));
            edKamis1.setTextColor(getResources().getColor(R.color.primaryText));
            edKamis2.setTextColor(getResources().getColor(R.color.primaryText));
        } else{
            cbKamis.setChecked(false); //it was NOT checked
        }

        if (pref.getBoolean("cbJumat", false) == true){ //false is default value
            cbJumat.setChecked(true); //it was checked
            edJumat1.setEnabled(true);
            edJumat2.setEnabled(true);
            edJumat1.setText(pref.getString("edJumat1", edJumat1.getText().toString()));
            edJumat2.setText(pref.getString("edJumat2", edJumat2.getText().toString()));
            edJumat1.setTextColor(getResources().getColor(R.color.primaryText));
            edJumat2.setTextColor(getResources().getColor(R.color.primaryText));
        } else{
            cbJumat.setChecked(false); //it was NOT checked
        }

        if (pref.getBoolean("cbSabtu", false) == true){ //false is default value
            cbSabtu.setChecked(true); //it was checked
            edSabtu1.setEnabled(true);
            edSabtu2.setEnabled(true);
            edSabtu1.setText(pref.getString("edSabtu1", edSabtu1.getText().toString()));
            edSabtu2.setText(pref.getString("edSabtu2", edSabtu2.getText().toString()));
            edSabtu1.setTextColor(getResources().getColor(R.color.primaryText));
            edSabtu2.setTextColor(getResources().getColor(R.color.primaryText));
        } else{
            cbSabtu.setChecked(false); //it was NOT checked
        }

        if (pref.getBoolean("cbMinggu", false) == true){ //false is default value
            cbMinggu.setChecked(true); //it was checked
            edMinggu1.setEnabled(true);
            edMinggu2.setEnabled(true);
            edMinggu1.setText(pref.getString("edMinggu1", edMinggu1.getText().toString()));
            edMinggu2.setText(pref.getString("edMinggu2", edMinggu2.getText().toString()));
            edMinggu1.setTextColor(getResources().getColor(R.color.primaryText));
            edMinggu2.setTextColor(getResources().getColor(R.color.primaryText));
        } else{
            cbMinggu.setChecked(false); //it was NOT checked
        }

    }

    public void initRefreshLayout(){
        cbSenin.setChecked(false);
        edSenin1.setText("00:00");
        edSenin2.setText("00:00");
        edSenin1.setEnabled(false);
        edSenin2.setEnabled(false);
        edSenin1.setTextColor(getResources().getColor(R.color.secondaryText2));
        edSenin2.setTextColor(getResources().getColor(R.color.secondaryText2));

        cbSelasa.setChecked(false);
        edSelasa1.setText("00:00");
        edSelasa2.setText("00:00");
        edSelasa1.setEnabled(false);
        edSelasa2.setEnabled(false);
        edSelasa1.setTextColor(getResources().getColor(R.color.secondaryText2));
        edSelasa2.setTextColor(getResources().getColor(R.color.secondaryText2));

        cbRabu.setChecked(false);
        edRabu1.setText("00:00");
        edRabu2.setText("00:00");
        edRabu1.setEnabled(false);
        edRabu2.setEnabled(false);
        edRabu1.setTextColor(getResources().getColor(R.color.secondaryText2));
        edRabu2.setTextColor(getResources().getColor(R.color.secondaryText2));

        cbKamis.setChecked(false);
        edKamis1.setText("00:00");
        edKamis2.setText("00:00");
        edKamis1.setEnabled(false);
        edKamis2.setEnabled(false);
        edKamis1.setTextColor(getResources().getColor(R.color.secondaryText2));
        edKamis2.setTextColor(getResources().getColor(R.color.secondaryText2));

        cbJumat.setChecked(false);
        edJumat1.setText("00:00");
        edJumat2.setText("00:00");
        edJumat1.setEnabled(false);
        edJumat2.setEnabled(false);
        edJumat1.setTextColor(getResources().getColor(R.color.secondaryText2));
        edJumat2.setTextColor(getResources().getColor(R.color.secondaryText2));

        cbSabtu.setChecked(false);
        edSabtu1.setText("00:00");
        edSabtu2.setText("00:00");
        edSabtu1.setEnabled(false);
        edSabtu2.setEnabled(false);
        edSabtu1.setTextColor(getResources().getColor(R.color.secondaryText2));
        edSabtu2.setTextColor(getResources().getColor(R.color.secondaryText2));

        cbMinggu.setChecked(false);
        edMinggu1.setText("00:00");
        edMinggu2.setText("00:00");
        edMinggu1.setEnabled(false);
        edMinggu2.setEnabled(false);
        edMinggu1.setTextColor(getResources().getColor(R.color.secondaryText2));
        edMinggu2.setTextColor(getResources().getColor(R.color.secondaryText2));
    }

    public void setEditorValue(){
        editor.putBoolean("cbSenin", cbSenin.isChecked());
        editor.putBoolean("cbSelasa", cbSelasa.isChecked());
        editor.putBoolean("cbRabu", cbRabu.isChecked());
        editor.putBoolean("cbKamis", cbKamis.isChecked());
        editor.putBoolean("cbJumat", cbJumat.isChecked());
        editor.putBoolean("cbSabtu", cbSabtu.isChecked());
        editor.putBoolean("cbMinggu", cbMinggu.isChecked());

        editor.putString("edSenin1", edSenin1.getText().toString());
        editor.putString("edSenin2", edSenin2.getText().toString());

        editor.putString("edSelasa1", edSelasa1.getText().toString());
        editor.putString("edSelasa2", edSelasa2.getText().toString());

        editor.putString("edRabu1", edRabu1.getText().toString());
        editor.putString("edRabu2", edRabu2.getText().toString());

        editor.putString("edKamis1", edKamis1.getText().toString());
        editor.putString("edKamis2", edKamis2.getText().toString());

        editor.putString("edJumat1", edJumat1.getText().toString());
        editor.putString("edJumat2", edJumat2.getText().toString());

        editor.putString("edSabtu1", edSabtu1.getText().toString());
        editor.putString("edSabtu2", edSabtu2.getText().toString());

        editor.putString("edMinggu1", edMinggu1.getText().toString());
        editor.putString("edMinggu2", edMinggu2.getText().toString());

        editor.commit(); // commit changes
    }

    public int checkCountJadwal(){
        int countJadwal = 0;
        if(cbSenin.isChecked()){
            countJadwal++;
        }
        if(cbSelasa.isChecked()){
            countJadwal++;
        }
        if(cbRabu.isChecked()){
            countJadwal++;
        }
        if(cbKamis.isChecked()){
            countJadwal++;
        }
        if(cbJumat.isChecked()){
            countJadwal++;
        }
        if(cbSabtu.isChecked()){
            countJadwal++;
        }
        if(cbMinggu.isChecked()){
            countJadwal++;
        }
        return countJadwal;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void kirimJadwal(){
        setEditorValue();

        Intent i = new Intent(PickSchedule.this, AddStoreDetailInfo.class);

        i.putExtra("h1", "Senin");
        i.putExtra("h2", "Selasa");
        i.putExtra("h3", "Rabu");
        i.putExtra("h4", "Kamis");
        i.putExtra("h5", "Jumat");
        i.putExtra("h6", "Sabtu");
        i.putExtra("h7", "Minggu");

        i.putExtra("b1", edSenin1.getText().toString());
        i.putExtra("b2", edSelasa1.getText().toString());
        i.putExtra("b3", edRabu1.getText().toString());
        i.putExtra("b4", edKamis1.getText().toString());
        i.putExtra("b5", edJumat1.getText().toString());
        i.putExtra("b6", edSabtu1.getText().toString());
        i.putExtra("b7", edMinggu1.getText().toString());

        i.putExtra("t1", edSenin2.getText().toString());
        i.putExtra("t2", edSelasa2.getText().toString());
        i.putExtra("t3", edRabu2.getText().toString());
        i.putExtra("t4", edKamis2.getText().toString());
        i.putExtra("t5", edJumat2.getText().toString());
        i.putExtra("t6", edSabtu2.getText().toString());
        i.putExtra("t7", edMinggu2.getText().toString());

        //    Toast.makeText(this,  pref.getString("edSenin1", edSenin1.getText().toString())+ "|" + pref.getString("edSenin2", edSenin2.getText().toString()), Toast.LENGTH_SHORT).show();
        setResult(Activity.RESULT_OK, i);
       // startActivity(i);
        finish();
      // startActivityForResult(i, 1);
    }

    @Override
    public void onClick(View v) {
        if(v == btnSelesai){
            if(isSeninValid() == true && isSelasaValid() && isRabuValid() && isKamisValid() && isJumatValid() && isSabtuValid() && isMingguValid()){
                if(checkCountJadwal() == 0){
                    tvError.setVisibility(View.VISIBLE);
                    tvError.setText("Anda belum memilih jadwal");
                }else{
                    tvError.setVisibility(View.GONE);
                    kirimJadwal();
                }

            }
            else {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("Harap mengisi jam pada hari yang buka");
            }

        }
        else if(v == expandableButton1){
            expandableLayout1 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout1);
            expandableLayout1.toggle(); // toggle expand and collapse
            if(expandableLayout1.isExpanded()){
                expandableButton1.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_arrow_down, 0);
            }
            else  expandableButton1.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_arrow_up, 0);
        }
        else  if(v == expandableButton2){
            expandableLayout2 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout2);
            expandableLayout2.toggle(); // toggle expand and collapse

            if(expandableLayout2.isExpanded()){
                expandableButton2.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_arrow_down, 0);
            }
            else  expandableButton2.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_arrow_up, 0);

        }
        else  if(v == expandableButton3){
            expandableLayout3 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout3);
            expandableLayout3.toggle(); // toggle expand and collapse

            if(expandableLayout3.isExpanded()){
                expandableButton3.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_arrow_down, 0);
            }
            else  expandableButton3.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_arrow_up, 0);

        }
        else  if(v == expandableButton4){
            expandableLayout4 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout4);
            expandableLayout4.toggle(); // toggle expand and collapse

            if(expandableLayout4.isExpanded()){
                expandableButton4.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_arrow_down, 0);
            }
            else  expandableButton4.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_arrow_up, 0);
        }
        else  if(v == expandableButton5){
            expandableLayout5 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout5);
            expandableLayout5.toggle(); // toggle expand and collapse

            if(expandableLayout5.isExpanded()){
                expandableButton5.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_arrow_down, 0);
            }
            else  expandableButton5.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_arrow_up, 0);
        }
        else  if(v == expandableButton6){
            expandableLayout6 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout6);
            expandableLayout6.toggle(); // toggle expand and collapse

            if(expandableLayout6.isExpanded()){
                expandableButton6.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_arrow_down, 0);
            }
            else  expandableButton6.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_arrow_up, 0);
        }
        else  if(v == expandableButton7){
            expandableLayout7 = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout7);
            expandableLayout7.toggle(); // toggle expand and collapse

            if(expandableLayout7.isExpanded()){
                expandableButton7.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_arrow_down, 0);
            }
            else  expandableButton7.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.ic_arrow_up, 0);
        }
        else if(v == edSenin1){
            flagSenin1=1;
            flagSenin2=0;
            flagSelasa1=0;
            flagSelasa2=0;
            flagRabu1=0;
            flagRabu2=0;
            flagKamis1=0;
            flagKamis2=0;
            flagJumat1=0;
            flagJumat2=0;
            flagSabtu1=0;
            flagSabtu2=0;
            flagMinggu1=0;
            flagMinggu2=0;

            String hour = edSenin1.getText().toString().substring(0,2);
            String minute = edSenin1.getText().toString().substring(3,5);
            showTime(hour, minute);

        }
        else if(v == edSenin2){
            flagSenin1=0;
            flagSenin2=1;
            flagSelasa1=0;
            flagSelasa2=0;
            flagRabu1=0;
            flagRabu2=0;
            flagKamis1=0;
            flagKamis2=0;
            flagJumat1=0;
            flagJumat2=0;
            flagSabtu1=0;
            flagSabtu2=0;
            flagMinggu1=0;
            flagMinggu2=0;

            String hour = edSenin2.getText().toString().substring(0,2);
            String minute = edSenin2.getText().toString().substring(3,5);
            showTime(hour, minute);

        }

        else if(v == edSelasa1){
            flagSenin1=0;
            flagSenin2=0;
            flagSelasa1=1;
            flagSelasa2=0;
            flagRabu1=0;
            flagRabu2=0;
            flagKamis1=0;
            flagKamis2=0;
            flagJumat1=0;
            flagJumat2=0;
            flagSabtu1=0;
            flagSabtu2=0;
            flagMinggu1=0;
            flagMinggu2=0;

            String hour = edSelasa1.getText().toString().substring(0,2);
            String minute = edSelasa1.getText().toString().substring(3,5);
            showTime(hour, minute);

        }
        else if(v == edSelasa2){
            flagSenin1=0;
            flagSenin2=0;
            flagSelasa1=0;
            flagSelasa2=1;
            flagRabu1=0;
            flagRabu2=0;
            flagKamis1=0;
            flagKamis2=0;
            flagJumat1=0;
            flagJumat2=0;
            flagSabtu1=0;
            flagSabtu2=0;
            flagMinggu1=0;
            flagMinggu2=0;

            String hour = edSelasa2.getText().toString().substring(0,2);
            String minute = edSelasa2.getText().toString().substring(3,5);
            showTime(hour, minute);

        }

        else if(v == edRabu1){
            flagSenin1=0;
            flagSenin2=0;
            flagSelasa1=0;
            flagSelasa2=0;
            flagRabu1=1;
            flagRabu2=0;
            flagKamis1=0;
            flagKamis2=0;
            flagJumat1=0;
            flagJumat2=0;
            flagSabtu1=0;
            flagSabtu2=0;
            flagMinggu1=0;
            flagMinggu2=0;

            String hour = edRabu1.getText().toString().substring(0,2);
            String minute = edRabu1.getText().toString().substring(3,5);
            showTime(hour, minute);

        }
        else if(v == edRabu2){
            flagSenin1=0;
            flagSenin2=0;
            flagSelasa1=0;
            flagSelasa2=0;
            flagRabu1=0;
            flagRabu2=1;
            flagKamis1=0;
            flagKamis2=0;
            flagJumat1=0;
            flagJumat2=0;
            flagSabtu1=0;
            flagSabtu2=0;
            flagMinggu1=0;
            flagMinggu2=0;

            String hour = edRabu2.getText().toString().substring(0,2);
            String minute = edRabu2.getText().toString().substring(3,5);
            showTime(hour, minute);

        }

        else if(v == edKamis1){
            flagSenin1=0;
            flagSenin2=0;
            flagSelasa1=0;
            flagSelasa2=0;
            flagRabu1=0;
            flagRabu2=0;
            flagKamis1=1;
            flagKamis2=0;
            flagJumat1=0;
            flagJumat2=0;
            flagSabtu1=0;
            flagSabtu2=0;
            flagMinggu1=0;
            flagMinggu2=0;

            String hour = edKamis1.getText().toString().substring(0,2);
            String minute = edKamis1.getText().toString().substring(3,5);
            showTime(hour, minute);

        }
        else if(v == edKamis2){
            flagSenin1=0;
            flagSenin2=0;
            flagSelasa1=0;
            flagSelasa2=0;
            flagRabu1=0;
            flagRabu2=0;
            flagKamis1=0;
            flagKamis2=1;
            flagJumat1=0;
            flagJumat2=0;
            flagSabtu1=0;
            flagSabtu2=0;
            flagMinggu1=0;
            flagMinggu2=0;

            String hour = edKamis2.getText().toString().substring(0,2);
            String minute = edKamis2.getText().toString().substring(3,5);
            showTime(hour, minute);

        }

        else if(v == edJumat1){
            flagSenin1=0;
            flagSenin2=0;
            flagSelasa1=0;
            flagSelasa2=0;
            flagRabu1=0;
            flagRabu2=0;
            flagKamis1=0;
            flagKamis2=0;
            flagJumat1=1;
            flagJumat2=0;
            flagSabtu1=0;
            flagSabtu2=0;
            flagMinggu1=0;
            flagMinggu2=0;

            String hour = edJumat1.getText().toString().substring(0,2);
            String minute = edJumat1.getText().toString().substring(3,5);
            showTime(hour, minute);

        }
        else if(v == edJumat2){
            flagSenin1=0;
            flagSenin2=0;
            flagSelasa1=0;
            flagSelasa2=0;
            flagRabu1=0;
            flagRabu2=0;
            flagKamis1=0;
            flagKamis2=0;
            flagJumat1=0;
            flagJumat2=1;
            flagSabtu1=0;
            flagSabtu2=0;
            flagMinggu1=0;
            flagMinggu2=0;

            String hour = edJumat2.getText().toString().substring(0,2);
            String minute = edJumat2.getText().toString().substring(3,5);
            showTime(hour, minute);

        }

        else if(v == edSabtu1){
            flagSenin1=0;
            flagSenin2=0;
            flagSelasa1=0;
            flagSelasa2=0;
            flagRabu1=0;
            flagRabu2=0;
            flagKamis1=0;
            flagKamis2=0;
            flagJumat1=0;
            flagJumat2=0;
            flagSabtu1=1;
            flagSabtu2=0;
            flagMinggu1=0;
            flagMinggu2=0;

            String hour = edSabtu1.getText().toString().substring(0,2);
            String minute = edSabtu1.getText().toString().substring(3,5);
            showTime(hour, minute);

        }
        else if(v == edSabtu2){
            flagSenin1=1;
            flagSenin2=0;
            flagSelasa1=0;
            flagSelasa2=0;
            flagRabu1=0;
            flagRabu2=0;
            flagKamis1=0;
            flagKamis2=0;
            flagJumat1=0;
            flagJumat2=0;
            flagSabtu1=0;
            flagSabtu2=1;
            flagMinggu1=0;
            flagMinggu2=0;

            String hour = edSabtu2.getText().toString().substring(0,2);
            String minute = edSabtu2.getText().toString().substring(3,5);
            showTime(hour, minute);

        }

        else if(v == edMinggu1){
            flagSenin1=0;
            flagSenin2=0;
            flagSelasa1=0;
            flagSelasa2=0;
            flagRabu1=0;
            flagRabu2=0;
            flagKamis1=0;
            flagKamis2=0;
            flagJumat1=0;
            flagJumat2=0;
            flagSabtu1=0;
            flagSabtu2=0;
            flagMinggu1=1;
            flagMinggu2=0;

            String hour = edMinggu1.getText().toString().substring(0,2);
            String minute = edMinggu1.getText().toString().substring(3,5);
            showTime(hour, minute);

        }
        else if(v == edMinggu2){
            flagSenin1=1;
            flagSenin2=0;
            flagSelasa1=0;
            flagSelasa2=0;
            flagRabu1=0;
            flagRabu2=0;
            flagKamis1=0;
            flagKamis2=0;
            flagJumat1=0;
            flagJumat2=0;
            flagSabtu1=0;
            flagSabtu2=0;
            flagMinggu1=0;
            flagMinggu2=1;

            String hour = edMinggu2.getText().toString().substring(0,2);
            String minute = edMinggu2.getText().toString().substring(3,5);
            showTime(hour, minute);

        }

    }

    public void showTime(String hour, String minute) {
        Date date = null;

        DateFormat formatter = new SimpleDateFormat("HH:mm");
        try {
            date = formatter.parse(hour+":"+minute);
        } catch (ParseException e) {
            e.printStackTrace();
        }

     //   Toast.makeText(this, hour+":"+minute, Toast.LENGTH_SHORT).show();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        TimePickerDialog tpd = TimePickerDialog.newInstance(
                PickSchedule.this,
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

        if(flagSenin1 == 1) {
            if(!isSenin1Valid(getHour)){
                showTime(getHour, getMinutes);
                Toast.makeText(this, "Harap memasukan jam buka di bawah jam tutup", Toast.LENGTH_SHORT).show();
            } else
            edSenin1.setText(getHour + ":" + getMinutes);
        }
        else if(flagSenin2 == 1){
            if(!isSenin2Valid(getHour)){
                showTime(getHour, getMinutes);
                Toast.makeText(this, "Harap memasukan jam tutup di atas jam buka", Toast.LENGTH_SHORT).show();
            }
            else edSenin2.setText(getHour + ":" + getMinutes);
        }


        if(flagSelasa1 == 1) {
            if(!isSelasa1Valid(getHour)){
                showTime(getHour, getMinutes);
                Toast.makeText(this, "Harap memasukan jam buka di bawah jam tutup", Toast.LENGTH_SHORT).show();
            } else
            edSelasa1.setText(getHour + ":" + getMinutes);
        }
        else if(flagSelasa2 == 1){
            if(!isSelasa2Valid(getHour)){
                showTime(getHour, getMinutes);
                Toast.makeText(this, "Harap memasukan jam tutup di atas jam buka", Toast.LENGTH_SHORT).show();
            }
            else
            edSelasa2.setText(getHour + ":" + getMinutes);
        }


        if(flagRabu1 == 1) {
            if(!isRabu1Valid(getHour)){
                showTime(getHour, getMinutes);
                Toast.makeText(this, "Harap memasukan jam buka di bawah jam tutup", Toast.LENGTH_SHORT).show();
            } else
            edRabu1.setText(getHour + ":" + getMinutes);
        }
        else if(flagRabu2 == 1){
            if(!isRabu2Valid(getHour) ){
                showTime(getHour, getMinutes);
                Toast.makeText(this, "Harap memasukan jam tutup di atas jam buka", Toast.LENGTH_SHORT).show();
            }
            else
            edRabu2.setText(getHour + ":" + getMinutes);
        }



        if(flagKamis1 == 1) {
            if(!isKamis1Valid(getHour)){
                showTime(getHour, getMinutes);
                Toast.makeText(this, "Harap memasukan jam buka di bawah jam tutup", Toast.LENGTH_SHORT).show();
            } else
            edKamis1.setText(getHour + ":" + getMinutes);
        }
        else if(flagKamis2 == 1){
            if(!isKamis2Valid(getHour)){
                showTime(getHour, getMinutes);
                Toast.makeText(this, "Harap memasukan jam tutup di atas jam buka", Toast.LENGTH_SHORT).show();
            }
            else
            edKamis2.setText(getHour + ":" + getMinutes);
        }



        if(flagJumat1 == 1) {
            if(!isJumat1Valid(getHour)){
                showTime(getHour, getMinutes);
                Toast.makeText(this, "Harap memasukan jam buka di bawah jam tutup", Toast.LENGTH_SHORT).show();
            } else
            edJumat1.setText(getHour + ":" + getMinutes);
        }
        else if(flagJumat2 == 1){
            if(!isJumat2Valid(getHour)){
                showTime(getHour, getMinutes);
                Toast.makeText(this, "Harap memasukan jam tutup di atas jam buka", Toast.LENGTH_SHORT).show();
            }
            else
            edJumat2.setText(getHour + ":" + getMinutes);
        }


        if(flagSabtu1 == 1) {
            if(!isSabtu1Valid(getHour)){
                showTime(getHour, getMinutes);
                Toast.makeText(this, "Harap memasukan jam buka di bawah jam tutup", Toast.LENGTH_SHORT).show();
            } else
            edSabtu1.setText(getHour + ":" + getMinutes);
        }
        else if(flagSabtu2 == 1){
            if(!isSabtu2Valid(getHour)){
                showTime(getHour, getMinutes);
                Toast.makeText(this, "Harap memasukan jam tutup di atas jam buka", Toast.LENGTH_SHORT).show();
            }
            else
            edSabtu2.setText(getHour + ":" + getMinutes);
        }


        if(flagMinggu1 == 1) {
            if(!isMinggu1Valid(getHour)){
                showTime(getHour, getMinutes);
                Toast.makeText(this, "Harap memasukan jam buka di bawah jam tutup", Toast.LENGTH_SHORT).show();
            } else
            edMinggu1.setText(getHour + ":" + getMinutes);
        }
        else if(flagMinggu2 == 1){
            if(!isMinggu2Valid(getHour)){
                showTime(getHour, getMinutes);
                Toast.makeText(this, "Harap memasukan jam tutup di atas jam buka", Toast.LENGTH_SHORT).show();
            }
            else
            edMinggu2.setText(getHour + ":" + getMinutes);
        }

    }


//    ========================================= validating jam harus diisi jika centang buka ==============================================

    public boolean isSeninValid(){

        if(cbSenin.isChecked()){
            if(edSenin1.getText().toString().equals("00:00") || edSenin2.getText().toString().equals("00:00")){
               // Toast.makeText(this, "senin1 "+edSenin1.getText().toString()+"|"+edSenin2.getText().toString(), Toast.LENGTH_SHORT).show();
                return false;
            }
            else {
               // Toast.makeText(this, "senin2 "+edSenin1.getText().toString()+"|"+edSenin2.getText().toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        else {
         //   Toast.makeText(this, "senin3", Toast.LENGTH_SHORT).show();
            return true;
        }

    }

    public boolean isSelasaValid(){
        if(cbSelasa.isChecked()){
            if(edSelasa1.getText().toString().equals("00:00") || edSelasa2.getText().toString().equals("00:00")){
              //  Toast.makeText(this, "senin1 "+edSenin1.getText().toString()+"|"+edSenin2.getText().toString(), Toast.LENGTH_SHORT).show();
                return false;
            }
            else {
              //  Toast.makeText(this, "senin2 "+edSenin1.getText().toString()+"|"+edSenin2.getText().toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        else {
          //  Toast.makeText(this, "senin3", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public boolean isRabuValid(){
        if(cbRabu.isChecked()){
            if(edRabu1.getText().toString().equals("00:00") || edRabu2.getText().toString().equals("00:00")){
               // Toast.makeText(this, "senin1 "+edSenin1.getText().toString()+"|"+edSenin2.getText().toString(), Toast.LENGTH_SHORT).show();
                return false;
            }
            else {
               // Toast.makeText(this, "senin2 "+edSenin1.getText().toString()+"|"+edSenin2.getText().toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        else {
          //  Toast.makeText(this, "senin3", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public boolean isKamisValid(){
        if(cbKamis.isChecked()){
            if(edKamis1.getText().toString().equals("00:00") || edKamis2.getText().toString().equals("00:00")){
               // Toast.makeText(this, "senin1 "+edSenin1.getText().toString()+"|"+edSenin2.getText().toString(), Toast.LENGTH_SHORT).show();
                return false;
            }
            else {
              //  Toast.makeText(this, "senin2 "+edSenin1.getText().toString()+"|"+edSenin2.getText().toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        else {
           // Toast.makeText(this, "senin3", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public boolean isJumatValid(){
        if(cbJumat.isChecked()){
            if(edJumat1.getText().toString().equals("00:00") || edJumat2.getText().toString().equals("00:00")){
               // Toast.makeText(this, "senin1 "+edSenin1.getText().toString()+"|"+edSenin2.getText().toString(), Toast.LENGTH_SHORT).show();
                return false;
            }
            else {
              //  Toast.makeText(this, "senin2 "+edSenin1.getText().toString()+"|"+edSenin2.getText().toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        else {
          //  Toast.makeText(this, "senin3", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public boolean isSabtuValid(){
        if(cbSabtu.isChecked()){
            if(edSabtu1.getText().toString().equals("00:00") || edSabtu2.getText().toString().equals("00:00")){
              //  Toast.makeText(this, "senin1 "+edSenin1.getText().toString()+"|"+edSenin2.getText().toString(), Toast.LENGTH_SHORT).show();
                return false;
            }
            else {
               // Toast.makeText(this, "senin2 "+edSenin1.getText().toString()+"|"+edSenin2.getText().toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        else {
           // Toast.makeText(this, "senin3", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public boolean isMingguValid(){
        if(cbMinggu.isChecked()){
            if(edMinggu1.getText().toString().equals("00:00") || edMinggu2.getText().toString().equals("00:00")){
              //  Toast.makeText(this, "senin1 "+edSenin1.getText().toString()+"|"+edSenin2.getText().toString(), Toast.LENGTH_SHORT).show();
                return false;
            }
            else {
             //   Toast.makeText(this, "senin2 "+edSenin1.getText().toString()+"|"+edSenin2.getText().toString(), Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        else {
          //  Toast.makeText(this, "senin3", Toast.LENGTH_SHORT).show();
            return true;
        }
    }



//    ========================================= validating jam tutup harus > jam buka ===========================================


    public boolean isSenin2Valid(String getHours){
        String trimOpen = edSenin1.getText().toString().substring(0,2);
        int jamBuka = Integer.parseInt(trimOpen);
        int jamTutup = Integer.parseInt(getHours);

        if( jamTutup < jamBuka ){
            return false;
        }
        else return true;
    }


    public boolean isSelasa2Valid(String getHours){
        String trimOpen = edSelasa1.getText().toString().substring(0,2);
        int jamBuka = Integer.parseInt(trimOpen);
        int jamTutup = Integer.parseInt(getHours);

        if( jamTutup < jamBuka ){
            return false;
        }
        else return true;
    }

    public boolean isRabu2Valid(String getHours){
        String trimOpen = edRabu1.getText().toString().substring(0,2);
        int jamBuka = Integer.parseInt(trimOpen);
        int jamTutup = Integer.parseInt(getHours);

        if( jamTutup < jamBuka ){
            return false;
        }
        else return true;
    }

    public boolean isKamis2Valid(String getHours){
        String trimOpen = edKamis1.getText().toString().substring(0,2);
        int jamBuka = Integer.parseInt(trimOpen);
        int jamTutup = Integer.parseInt(getHours);

        if( jamTutup < jamBuka ){
            return false;
        }
        else return true;
    }

    public boolean isJumat2Valid(String getHours){
        String trimOpen = edJumat1.getText().toString().substring(0,2);
        int jamBuka = Integer.parseInt(trimOpen);
        int jamTutup = Integer.parseInt(getHours);

        if( jamTutup < jamBuka ){
            return false;
        }
        else return true;
    }

    public boolean isSabtu2Valid(String getHours){
        String trimOpen = edSabtu1.getText().toString().substring(0,2);
        int jamBuka = Integer.parseInt(trimOpen);
        int jamTutup = Integer.parseInt(getHours);

        if( jamTutup < jamBuka ){
            return false;
        }
        else return true;
    }

    public boolean isMinggu2Valid(String getHours){
        String trimOpen = edMinggu1.getText().toString().substring(0,2);
        int jamBuka = Integer.parseInt(trimOpen);
        int jamTutup = Integer.parseInt(getHours);

        if( jamTutup < jamBuka ){
            return false;
        }
        else return true;
    }



//    ========================================= validating jam tutup harus > jam buka ===========================================


    public boolean isSenin1Valid(String getHours){
        String trimClose = edSenin2.getText().toString().substring(0,2);
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

    public boolean isSelasa1Valid(String getHours){
        String trimClose = edSelasa2.getText().toString().substring(0,2);
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

    public boolean isRabu1Valid(String getHours){
        String trimClose = edRabu2.getText().toString().substring(0,2);
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

    public boolean isKamis1Valid(String getHours){
        String trimClose = edKamis2.getText().toString().substring(0,2);
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

    public boolean isJumat1Valid(String getHours){
        String trimClose = edJumat2.getText().toString().substring(0,2);
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

    public boolean isSabtu1Valid(String getHours){
        String trimClose = edSabtu2.getText().toString().substring(0,2);
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

    public boolean isMinggu1Valid(String getHours){
        String trimClose = edMinggu2.getText().toString().substring(0,2);
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

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
