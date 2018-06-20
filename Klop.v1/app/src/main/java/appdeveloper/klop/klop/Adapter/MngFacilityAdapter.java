package appdeveloper.klop.klop.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import appdeveloper.klop.klop.Model.FacSetting;
import appdeveloper.klop.klop.R;

/**
 * Created by CMDDJ on 6/4/2018.
 */

public class MngFacilityAdapter extends RecyclerView.Adapter<MngFacilityAdapter.ViewHolder> {

    public ArrayList<String> facilitySelected;
    public ArrayList<String> nmFacilitySelected;
    public ArrayList<String> deleteFacility;

    private ArrayList<FacSetting> dataFacility;
    private Context context;

    public MngFacilityAdapter(ArrayList<FacSetting> dataFacility, Context context) {
        this.dataFacility = dataFacility;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        facilitySelected = new ArrayList<String>();
        nmFacilitySelected = new ArrayList<String>();
        deleteFacility = new ArrayList<String>();

        View v = layoutInflater.inflate(R.layout.list_item_facility, parent, false);
        return new ViewHolder(v);
    }




    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvFacility.setText(dataFacility.get(position).getNameFacility());
        holder.strIdFac = dataFacility.get(position).getIdFacilityDb();
        if(dataFacility.get(position).getIsChecked().equals("yes")){
            holder.cbSelect.setChecked(true);
            holder.facOwned.add(dataFacility.get(position).getIdFacilityDb());
        } else holder.cbSelect.setChecked(false);

        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!facilitySelected.contains(holder.strIdFac)){
                        facilitySelected.add(holder.strIdFac);
                    }

                    if(deleteFacility.contains(holder.strIdFac)){
                        deleteFacility.remove(holder.strIdFac);
                    }

                }else{
                    if(facilitySelected.contains(holder.strIdFac)){
                        facilitySelected.remove(holder.strIdFac);
                    }

                    if(!deleteFacility.contains(holder.strIdFac)){
                        deleteFacility.add(holder.strIdFac);
                    }

                   // Toast.makeText(context, "list "+deleteFacility.toString(), Toast.LENGTH_SHORT).show();
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

        TextView tvFacility;
        String strIdFac;
        CheckBox cbSelect;
        ArrayList<String> facOwned;

        ViewHolder(View itemView) {
            super(itemView);
            tvFacility = (TextView) itemView.findViewById(R.id.tvFacility);
            cbSelect = (CheckBox) itemView.findViewById(R.id.cbSelect);

            facOwned = new ArrayList<String>();
        }
    }
}