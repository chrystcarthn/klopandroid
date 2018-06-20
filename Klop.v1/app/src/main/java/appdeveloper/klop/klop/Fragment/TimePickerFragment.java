package appdeveloper.klop.klop.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.DialogFragment;
import android.util.Log;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.widget.TimePicker;
import android.widget.Toast;
//
//import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
//import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
//import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import appdeveloper.klop.klop.Activity.CreateMyBooking;
import appdeveloper.klop.klop.R;

/**
 * Created by CMDDJ on 5/18/2018.
 */

public class TimePickerFragment extends DialogFragment {

    TimePickerDialog.OnTimeSetListener ontimeSet;

        private int hour, minute;

    public TimePickerFragment(){}


    @SuppressLint("NewApi")
    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        hour = args.getInt("hour");
        minute = args.getInt("minute");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        Calendar c = Calendar.getInstance();
//        int hour = c.get(Calendar.HOUR_OF_DAY);
//        int minute = c.get(Calendar.MINUTE);
//
//        Date date = null;
//
//        String choosenHour = new SimpleDateFormat("HH", Locale.getDefault()).format(new Date());
//        String choosenMinutes = new SimpleDateFormat("mm", Locale.getDefault()).format(new Date());

//        String hour = choosenHour;
//        String minute = choosenMinutes;

//        DateFormat formatter = new SimpleDateFormat("HH:mm");
//        try {
//            date = formatter.parse(hour+":"+minute);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
////
//        Calendar now = Calendar.getInstance();
//        now.setTime(date);
//
//        TimePickerDialog tpd = TimePickerDialog.newInstance(
//                (TimePickerDialog.OnTimeSetListener) getActivity(),
//               hour,
//               minute,
//                true
//        );
//        tpd.setThemeDark(false);
//        tpd.vibrate(false);
//        tpd.setTitle("Atur Jam");
//        tpd.dismissOnPause(false);
//        tpd.enableSeconds(false);
//        tpd.enableMinutes(true);
//        if (true) {
//            tpd.setAccentColor(getResources().getColor(R.color.colorAccent));
//        }
//
//        tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialogInterface) {
//                Log.d("TimePicker", "Dialog was cancelled");
//            }
//        });

        return new TimePickerDialog(getActivity(), ontimeSet,   hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));

      //  return new TimePickerDialog(getContext(), ontimeSet, hour,  minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
    }


//    @Override
//    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
//    //    Toast.makeText(getActivity(), "tes:"+hourOfDay+":"+minute, Toast.LENGTH_SHORT).show();
//
//    }

//    @Override
//    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//
//    }


    public void setCallBack(TimePickerDialog.OnTimeSetListener ontime) {
        ontimeSet = ontime;
    }


//    @Override
//    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//      //  Toast.makeText(getActivity(), "tes:"+hourOfDay+":"+minute, Toast.LENGTH_SHORT).show();
//    }


}
