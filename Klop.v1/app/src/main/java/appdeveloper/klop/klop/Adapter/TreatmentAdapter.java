package appdeveloper.klop.klop.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import appdeveloper.klop.klop.Model.Treatment;
import appdeveloper.klop.klop.R;

/**
 * Created by CMDDJ on 4/20/2018.
 */

public class TreatmentAdapter extends RecyclerView.Adapter<TreatmentAdapter.ViewHolder> {

    private ArrayList<Treatment> dataTreatments;
    private Context context;

    public TreatmentAdapter(ArrayList<Treatment> dataTreatments, Context context) {
        this.dataTreatments = dataTreatments;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View v = layoutInflater.inflate(R.layout.list_item_treatment, parent, false);
        return new ViewHolder(v);   }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvTreatment.setText(dataTreatments.get(position).getNAMETREATMENT());
        if(dataTreatments.get(position).getDESCRIPTIONTREATMENT().equals("")){
            holder.tvDesc.setVisibility(View.GONE);
        } else {
            holder.tvDesc.setVisibility(View.VISIBLE);
            holder.tvDesc.setText(dataTreatments.get(position).getDESCRIPTIONTREATMENT());
        }


        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        String hargaFormatted = dataTreatments.get(position).getPRICETREATMENT();

        holder.tvHargaTreatment.setText(formatRupiah.format((double)Integer.parseInt(hargaFormatted)));
    }

    @Override
    public int getItemCount() {
        if(dataTreatments!=null){
            return dataTreatments.size();
        } else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTreatment, tvDesc, tvHargaTreatment;

        String strIdStore;
        LinearLayout linearLayout;

        ViewHolder(View itemView) {
            super(itemView);

            tvTreatment = (TextView) itemView.findViewById(R.id.tvTreatment);
            tvDesc = (TextView) itemView.findViewById(R.id.tvDescTr);
            tvHargaTreatment = (TextView) itemView.findViewById(R.id.tvHargaTreatment);
        }
    }
}
