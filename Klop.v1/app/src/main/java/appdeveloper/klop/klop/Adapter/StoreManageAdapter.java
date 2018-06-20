package appdeveloper.klop.klop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import appdeveloper.klop.klop.Activity.StoreSettings;
import appdeveloper.klop.klop.Model.Photo;
import appdeveloper.klop.klop.Model.Store;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.R;

/**
 * Created by CMDDJ on 6/1/2018.
 */

public class StoreManageAdapter  extends RecyclerView.Adapter<StoreManageAdapter.ViewHolder> {
    private ArrayList<Photo> dataPhoto;

    private ArrayList<Store> dataStore;
    private Context context;

    ArrayList<String> strDaysArraylist = new ArrayList<String>();
    ArrayList<String> strTimeOpenArraylist = new ArrayList<String>();
    ArrayList<String> strTimeClosedArraylist = new ArrayList<String>();

    public StoreManageAdapter(ArrayList<Store> dataStore, Context context) {
        this.dataStore = dataStore;
        this.context = context;
    }

    @Override
    public StoreManageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View v = layoutInflater.inflate(R.layout.list_item_store, parent, false);
        return new StoreManageAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.strIdStore = dataStore.get(position).getIdStore();
        holder.strIdUser = dataStore.get(position).getIdUser();
        holder.txtViewName.setText(dataStore.get(position).getNameStore());

        String jarak;
        if(dataStore.get(position).getDistanceKilo() != null){
            jarak = String.format("%.02f", Float.valueOf(dataStore.get(position).getDistanceKilo()))
                    .replace(",",".")
                    .replace(",00","")
                    .replace(".00","")
                    .trim();

        } else {jarak = "invalid"; }


        holder.txtViewAddress.setText(jarak+" Km・"+dataStore.get(position).getAddressStore());

        holder.txtViewPhone.setText(dataStore.get(position).getPhoneStore());
        holder.strLat = dataStore.get(position).getLatitude();
        holder.strLong = dataStore.get(position).getLongitude();

        String rate = String.format("%.01f", Float.valueOf(dataStore.get(position).getRateSum().toString()))
                .replace(",",".")
                .replace(",0","")
                .replace(".0","")
                .trim();

        holder.tvRating.setText(rate);

        try{
            holder.linkPhoto = dataStore.get(position).getBanner();
        }catch (Exception e){e.printStackTrace();}

        if(holder.linkPhoto != null){
            //Toast.makeText(context, "link: "+dataStore.get(position).getBanner(), Toast.LENGTH_SHORT).show();
            Picasso.with(context).load(holder.linkPhoto).into(holder.imgOutlet);
        }

        if(dataStore.get(position).getSchedule().size() != 0){
            if(getStatusStoreNow(dataStore.get(position)).equals("buka")){
                holder.tvStoreStatus.setText("BUKA");
                holder.tvStoreStatus.setTextColor(context.getResources().getColor(R.color.open));
                holder.tvStoreStatus.setBackgroundResource(R.drawable.background_open2);
            } else {
                holder.tvStoreStatus.setText("TUTUP");
                holder.tvStoreStatus.setTextColor(context.getResources().getColor(R.color.closed));
                holder.tvStoreStatus.setBackgroundResource(R.drawable.background_closed2);
            }
        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StoreSettings.class);
                Bundle b = new Bundle();
                b.putString("idStore", holder.strIdStore);
//                b.putString("idUser", holder.strIdUser);
                b.putString("nmStore", holder.txtViewName.getText().toString());
                b.putString("phStore", dataStore.get(position).getPhoneStore());
                b.putString("address", dataStore.get(position).getAddressStore());
                b.putString("strLat", holder.strLat);
                b.putString("strLong", holder.strLong);
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });
    }

    public String getNameOfToday(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {
            case Calendar.SUNDAY:
                return "Minggu";

            case Calendar.MONDAY:
                return "Senin";

            case Calendar.TUESDAY:
                return "Selasa";

            case Calendar.WEDNESDAY:
                return "Rabu";

            case Calendar.THURSDAY:
                return "Kamis";

            case Calendar.FRIDAY:
                return "Jumat";

            case Calendar.SATURDAY:
                return "Sabtu";


        }
        return "";
    }


    private String getStatusStoreNow(Store store){
        int intIndexDay=0;
        String open=null, closed=null;

        if(store.getIsSetmanual().equals("no")){
            for(int i=0; i<store.getSchedule().size(); i++){
                strDaysArraylist.add(store.getSchedule().get(i).getDAYS().toString());
                strTimeOpenArraylist.add(store.getSchedule().get(i).getTIMEOPEN().toString());
                strTimeClosedArraylist.add(store.getSchedule().get(i).getTIMECLOSED().toString());
            }

            if(strDaysArraylist.contains(getNameOfToday())){
                intIndexDay = getIndexDays(getNameOfToday());
                open = strTimeOpenArraylist.get(intIndexDay);
                closed = strTimeClosedArraylist.get(intIndexDay);

                if(checkTimings(getTimeNow(), open, closed) == true){
                    return "buka";
                }else return "tutup";

            } else {
                return "tutup";
            }

        } else if(store.getIsSetmanual().equals("open")){
            return "buka";
        } else
            return "tutup";

        //  Toast.makeText(this, "TODAY "+getNameOfToday()+" index: "+intIndexDay+" dgn jam "+open+" - "+closed, Toast.LENGTH_SHORT).show();
    }

    public String getTimeNow(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm");
    //    Toast.makeText(context, "now "+mdformat.format(calendar.getTime()).toString(), Toast.LENGTH_SHORT).show();

        return mdformat.format(calendar.getTime());
    }

    private int getIndexDays(String days){
        return strDaysArraylist.indexOf(days);
    }


    private boolean checkTimings(String timeNow, String startTime, String endTime){
        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try{
            Date date1 = sdf.parse(timeNow);
            Date date2 = sdf.parse(startTime);
            Date date3 = sdf.parse(endTime);

            if(date1.after(date2) & date1.before(date3)){
                return true;
            } else{
                return false;
            }
        }catch (Exception e){e.printStackTrace();}
        return false;
    }

    @Override
    public int getItemCount() {
        if(dataStore!=null){
            return dataStore.size();
        } else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtViewName,txtViewAddress, txtViewPhone, tvRating, tvStoreStatus;
        ImageView imgOutlet;
        String strIdStore, strIdUser, strLat, strLong, linkPhoto;
        LinearLayout linearLayout;

        ViewHolder(View itemView) {
            super(itemView);
            txtViewName = (TextView) itemView.findViewById(R.id.txt_notice_title);
            txtViewAddress = (TextView) itemView.findViewById(R.id.txt_notice_address);
            txtViewPhone = (TextView) itemView.findViewById(R.id.txt_notice_phone);
            tvRating = (TextView) itemView.findViewById(R.id.tvRating);
            tvStoreStatus = (TextView) itemView.findViewById(R.id.txtStatusStore);
            imgOutlet = (ImageView) itemView.findViewById(R.id.imgCardViewOutlet);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearlayoutItemOutlet);
        }
    }
}


