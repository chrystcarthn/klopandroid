package appdeveloper.klop.klop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import appdeveloper.klop.klop.Activity.DetailBookedItem;
import appdeveloper.klop.klop.Model.Booking;
import appdeveloper.klop.klop.Model.BookingDetail;
import appdeveloper.klop.klop.R;

/**
 * Created by CMDDJ on 5/30/2018.
 */

public class MyDetailBookedAdapter extends RecyclerView.Adapter<MyDetailBookedAdapter.ViewHolder>  {

    private ArrayList<BookingDetail> dataBookingDetail;
    private Context context;


    public MyDetailBookedAdapter(ArrayList<BookingDetail> dataBookingDetail, Context context) {
        this.dataBookingDetail = dataBookingDetail;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View v = layoutInflater.inflate(R.layout.list_item_cart_confirmation, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
      //  holder.strIdStore = dataBookingDetail.get(position).getIdStore();
        holder.tvTr.setText(dataBookingDetail.get(position).getNameTreatment());
        holder.tvQty.setText(dataBookingDetail.get(position).getQuantity()+"x");

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        String hargaFormatted = String.valueOf(dataBookingDetail.get(position).getPriceNow());
        String hargaFormatted2 = String.valueOf(dataBookingDetail.get(position).getSubTotal());

        holder.tvPrice.setText(formatRupiah.format((double)Integer.parseInt(hargaFormatted)));
        holder.tvSub.setText(formatRupiah.format((double)Integer.parseInt(hargaFormatted2)));
    }

    @Override
    public int getItemCount() {
        if(dataBookingDetail!=null){
            return dataBookingDetail.size();
        } else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTr, tvQty, tvPrice, tvSub;
        String strIdStore;

        ViewHolder(View itemView) {
            super(itemView);
            tvTr = (TextView) itemView.findViewById(R.id.tvCartNamaTr);
            tvQty = (TextView) itemView.findViewById(R.id.tvCartQuantityTr);
            tvPrice = (TextView) itemView.findViewById(R.id.tvCartHargaTr);
            tvSub = (TextView) itemView.findViewById(R.id.tvSubTotal);
        }
    }
}
