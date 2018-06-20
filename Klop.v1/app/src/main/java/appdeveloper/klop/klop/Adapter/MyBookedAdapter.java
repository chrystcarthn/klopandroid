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

import java.util.ArrayList;

import appdeveloper.klop.klop.Activity.DetailBookedItem;
import appdeveloper.klop.klop.Model.Booking;
import appdeveloper.klop.klop.R;

/**
 * Created by CMDDJ on 5/29/2018.
 */

public class MyBookedAdapter extends RecyclerView.Adapter<MyBookedAdapter.ViewHolder>  {

    private ArrayList<Booking> dataBooking;
    private Context context;


    public MyBookedAdapter(ArrayList<Booking> dataBooking, Context context) {
        this.dataBooking = dataBooking;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View v = layoutInflater.inflate(R.layout.list_item_booking, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.strIdStore = dataBooking.get(position).getIdStore();
        holder.tvOutlet.setText("  "+dataBooking.get(position).getStoreDetail().get(0).getNAMESTORE());
        holder.tvGuest.setText("    "+dataBooking.get(position).getGuestName());
        holder.tvDates.setText("   "+dataBooking.get(position).getDates());
        holder.tvTimes.setText("・"+dataBooking.get(position).getTimes());

        if(dataBooking.get(position).getStatusBooking().toString().equals("waiting")){
            holder.tvStatus.setText("MENUNGGU");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.gold));
            holder.tvStatus.setBackgroundResource(R.drawable.background_waiting);
        } else
        if(dataBooking.get(position).getStatusBooking().toString().equals("approved")){
            holder.tvStatus.setText("DITERIMA");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.open));
            holder.tvStatus.setBackgroundResource(R.drawable.background_open2);
        } else
        if(dataBooking.get(position).getStatusBooking().toString().equals("declined")){
            holder.tvStatus.setText("DITOLAK");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.closed));
            holder.tvStatus.setBackgroundResource(R.drawable.background_declined);
        } else
        if(dataBooking.get(position).getStatusBooking().toString().equals("canceled")){
            holder.tvStatus.setText("DIBATALKAN");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.secondaryText2));
            holder.tvStatus.setBackgroundResource(R.drawable.background_canceled);
        } else
        if(dataBooking.get(position).getStatusBooking().toString().equals("dropped")){
            holder.tvStatus.setText("DITOLAK");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.closed));
            holder.tvStatus.setBackgroundResource(R.drawable.background_declined);
        }



        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context, holder.tvOutlet.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DetailBookedItem.class);
                Bundle b = new Bundle();
                b.putString("idBooking", dataBooking.get(position).getIdBooking());
                b.putString("status", dataBooking.get(position).getStatusBooking());
                b.putString("idStore", holder.strIdStore);
                b.putString("nmStore", dataBooking.get(position).getStoreDetail().get(0).getNAMESTORE());
                b.putString("phStore", dataBooking.get(position).getStoreDetail().get(0).getPHONESTORE());
                b.putString("address", dataBooking.get(position).getStoreDetail().get(0).getADDRESSSTORE());
                b.putString("idUser", dataBooking.get(position).getIdUser());
                b.putString("nmGuest", dataBooking.get(position).getGuestName());
                b.putString("phGuest", dataBooking.get(position).getGuestPhone());
                b.putString("dates", dataBooking.get(position).getDates());
                b.putString("times", dataBooking.get(position).getTimes());
                b.putString("people", dataBooking.get(position).getSumOfPeople());
                b.putString("total", dataBooking.get(position).getTotalPayment());
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(dataBooking!=null){
            return dataBooking.size();
        } else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOutlet, tvGuest, tvDates, tvTimes, tvStatus;
        String strIdStore;
        LinearLayout linearLayout;

        ViewHolder(View itemView) {
            super(itemView);
            tvOutlet = (TextView) itemView.findViewById(R.id.tvOutlet);
            tvGuest = (TextView) itemView.findViewById(R.id.tvGuest);
            tvDates = (TextView) itemView.findViewById(R.id.tvDate);
            tvTimes = (TextView) itemView.findViewById(R.id.tvTime);
            tvStatus = (TextView) itemView.findViewById(R.id.tvStatusOrder);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearlayoutItemBooking);
        }
    }
}
