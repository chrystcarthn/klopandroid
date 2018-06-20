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

import appdeveloper.klop.klop.Model.CategoryDb;
import appdeveloper.klop.klop.R;

/**
 * Created by CMDDJ on 4/24/2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<CategoryDb> dataCategory;
    private Context context;

    ArrayList<String> strIdCategory = new ArrayList<String>();
    ArrayList<String> strNmCategory = new ArrayList<String>();

    public String formattedIdArraylist, formattedNmArraylist;

    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View v = layoutInflater.inflate(R.layout.item_checkbox, parent, false);
        return new CategoryAdapter.ViewHolder(v);
    }

    public CategoryAdapter(ArrayList<CategoryDb> dataCategory, Context context) {
        this.dataCategory = dataCategory;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(final CategoryAdapter.ViewHolder holder, final int position) {
        holder.txtViewNmCat.setText(dataCategory.get(position).getNAMECATEGORY());
        holder.cbCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.cbCategory.isChecked()){
                    holder.strTemp = dataCategory.get(position).getIDCATEGORYDB();
                    holder.strTempNama = dataCategory.get(position).getNAMECATEGORY();
                    //Toast.makeText(context,"adding... "+holder.strTemp, Toast.LENGTH_SHORT).show();
                    strIdCategory.add(holder.strTemp);
                    strNmCategory.add(holder.strTempNama);

                   // Toast.makeText(context, "size : "+strIdCategory.size(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(context,"arrayList : " +String.valueOf(strIdCategory), Toast.LENGTH_SHORT).show();
                    formattedIdArraylist = strIdCategory.toString()
                                        .replace("[","")
                                        .replace("]","")
                                        .replace(" ","")
                                        .trim();
                    formattedNmArraylist = strNmCategory.toString()
                                        .replace("[","")
                                        .replace("]","")
                                        .trim();
                }
                else{
                    holder.strTemp = dataCategory.get(position).getIDCATEGORYDB();
                    holder.strTempNama = dataCategory.get(position).getNAMECATEGORY();

                    //   Toast.makeText(context,"removing... "+holder.strTemp, Toast.LENGTH_SHORT).show();
                    strIdCategory.remove(holder.strTemp);
                    strNmCategory.remove(holder.strTempNama);

                    formattedIdArraylist = strIdCategory.toString()
                            .replace("[","")
                            .replace("]","")
                            .replace(" ","")
                            .trim();
                    formattedNmArraylist = strNmCategory.toString()
                            .replace("[","")
                            .replace("]","")
                            .trim();
                }


            }
        });
    }



    @Override
    public int getItemCount() {
        if(dataCategory!=null){
            return dataCategory.size();
        } else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txtViewNmCat, txtTempId;
        String strTemp, strTempNama;
        CheckBox cbCategory;

        ViewHolder(View itemView) {
            super(itemView);

            txtViewNmCat = (TextView) itemView.findViewById(R.id.tvCategoryName);
            cbCategory = (CheckBox) itemView.findViewById(R.id.cbCategory);
            txtTempId = (TextView) itemView.findViewById(R.id.tvTempIdCat);

        }
    }
}
