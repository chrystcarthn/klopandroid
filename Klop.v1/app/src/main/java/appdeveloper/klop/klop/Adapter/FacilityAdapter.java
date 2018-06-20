package appdeveloper.klop.klop.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import appdeveloper.klop.klop.Model.FacilityDb;
import appdeveloper.klop.klop.R;

/**
 * Created by CMDDJ on 4/27/2018.
 */

public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.ViewHolder> {

    private ArrayList<FacilityDb> dataFacility;
    private Context context;

    ArrayList<String> strIdFacility = new ArrayList<String>();
    ArrayList<String> strNmFacility = new ArrayList<String>();

    public String formattedIdArraylist, formattedNmArraylist;

    @Override
    public FacilityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View v = layoutInflater.inflate(R.layout.item_checkbox, parent, false);
        return new FacilityAdapter.ViewHolder(v);
    }

    public FacilityAdapter(ArrayList<FacilityDb> dataCategory, Context context) {
        this.dataFacility = dataCategory;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(final FacilityAdapter.ViewHolder holder, final int position) {
        holder.txtViewNmFas.setText(dataFacility.get(position).getNAMEFACILITY());
        holder.cbCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cbCategory.isChecked()){
                    holder.strTemp = dataFacility.get(position).getIDFACILITYDB();
                    holder.strTempNama = dataFacility.get(position).getNAMEFACILITY();
//                    Toast.makeText(context,"clicked : " +dataFacility.get(position).getIDFACILITYDB(), Toast.LENGTH_SHORT).show();


                    strIdFacility.add(holder.strTemp);
                    strNmFacility.add(holder.strTempNama);

                    formattedIdArraylist = strIdFacility.toString()
                            .replace("[","")
                            .replace("]","")
                            .replace(" ","")
                            .trim();
                    formattedNmArraylist = strNmFacility.toString()
                            .replace("[","")
                            .replace("]","")
                            .trim();
//                    Toast.makeText(context,"formatted : " +formattedIdArraylist, Toast.LENGTH_SHORT).show();

                }
                else{
                    holder.strTemp = dataFacility.get(position).getIDFACILITYDB();
                    holder.strTempNama = dataFacility.get(position).getNAMEFACILITY();

                    strIdFacility.remove(holder.strTemp);
                    strNmFacility.remove(holder.strTempNama);

                    formattedIdArraylist = strIdFacility.toString()
                            .replace("[","")
                            .replace("]","")
                            .replace(" ","")
                            .trim();
                    formattedNmArraylist = strNmFacility.toString()
                            .replace("[","")
                            .replace("]","")
                            .trim();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(dataFacility!=null){
            return dataFacility.size();
        } else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtViewNmFas, txtTempId;
        String strTemp, strTempNama;
        CheckBox cbCategory;

        ViewHolder(View itemView) {
            super(itemView);

            txtViewNmFas = (TextView) itemView.findViewById(R.id.tvCategoryName);
            cbCategory = (CheckBox) itemView.findViewById(R.id.cbCategory);
            txtTempId = (TextView) itemView.findViewById(R.id.tvTempIdCat);

        }
    }
}
