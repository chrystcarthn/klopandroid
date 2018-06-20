package appdeveloper.klop.klop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import appdeveloper.klop.klop.Model.CatSetting;
import appdeveloper.klop.klop.Model.Category;
import appdeveloper.klop.klop.Model.CategoryDb;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.AboutTabFragPresenterImp;
import appdeveloper.klop.klop.PresenterImp.CategoryFragPresenterImp;
import appdeveloper.klop.klop.R;

/**
 * Created by CMDDJ on 6/3/2018.
 */

public class MngCategoryAdapter extends RecyclerView.Adapter<MngCategoryAdapter.ViewHolder> {

    public ArrayList<String> categorySelected;
    public ArrayList<String> nmCategorySelected;
    public ArrayList<String> deleteCategory;

    private ArrayList<CatSetting> dataCategory;
    private Context context;

    public MngCategoryAdapter(ArrayList<CatSetting> dataCategory, Context context) {
        this.dataCategory = dataCategory;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        categorySelected = new ArrayList<String>();
        nmCategorySelected = new ArrayList<String>();
        deleteCategory = new ArrayList<String>();

        View v = layoutInflater.inflate(R.layout.list_item_category, parent, false);
        return new ViewHolder(v);
    }




    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvCategory.setText(dataCategory.get(position).getNameCategory());
        holder.strIdCat = dataCategory.get(position).getIdCategoryDb();
        if(dataCategory.get(position).getIsChecked().equals("yes")){
            holder.cbSelect.setChecked(true);
            holder.catOwned.add(dataCategory.get(position).getIdCategoryDb());
        } else holder.cbSelect.setChecked(false);



      //  Toast.makeText(context, "list "+ holder.catOwned.toString(), Toast.LENGTH_SHORT).show();

        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if(!categorySelected.contains(holder.strIdCat)){
                        categorySelected.add(holder.strIdCat);
                    }

                    if(deleteCategory.contains(holder.strIdCat)){
                        deleteCategory.remove(holder.strIdCat);
                    }

                }else{
                    if(categorySelected.contains(holder.strIdCat)){
                        categorySelected.remove(holder.strIdCat);
                    }

                    if(!deleteCategory.contains(holder.strIdCat)){
                        deleteCategory.add(holder.strIdCat);
                    }
                  //  nmCategorySelected.remove(dataCategory.get(position).getNameCategory());
                  //  Toast.makeText(context, "list "+deleteCategory.toString(), Toast.LENGTH_SHORT).show();
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

        TextView tvCategory;
        String strIdCat;
        CheckBox cbSelect;
        ArrayList<String> catOwned;

        ViewHolder(View itemView) {
            super(itemView);
            tvCategory = (TextView) itemView.findViewById(R.id.tvCategory);
            cbSelect = (CheckBox) itemView.findViewById(R.id.cbSelect);

            catOwned = new ArrayList<String>();
        }
    }
}


