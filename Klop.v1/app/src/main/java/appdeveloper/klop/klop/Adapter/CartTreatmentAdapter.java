package appdeveloper.klop.klop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import appdeveloper.klop.klop.R;

/**
 * Created by CMDDJ on 5/27/2018.
 */

public class CartTreatmentAdapter extends BaseAdapter {
    ArrayList<String> namaTrArraylist = new ArrayList<>();
    ArrayList<String> quantityTrArraylist = new ArrayList<>();
    ArrayList<String> hargaTrArraylist = new ArrayList<>();
    Context context;


    CartTreatmentAdapter(){
        namaTrArraylist.clear();
        quantityTrArraylist.clear();
        hargaTrArraylist.clear();
    }

    public CartTreatmentAdapter(ArrayList<String> al1, ArrayList<String> al2, ArrayList<String> al3){
        namaTrArraylist = al1;
        quantityTrArraylist = al2;
        hargaTrArraylist = al3;
    }

    @Override
    public int getCount() {
        return namaTrArraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row;
        row = inflater.inflate(R.layout.list_item_cart_treatment, parent, false);
        TextView tvNmTr, tvQuantity, tvHrg;
        tvNmTr = (TextView) row.findViewById(R.id.tvCartNamaTr);
        tvQuantity = (TextView) row.findViewById(R.id.tvCartQuantityTr);
        tvHrg = (TextView) row.findViewById(R.id.tvHrgTr);
        tvNmTr.setText(namaTrArraylist.get(position));
        tvQuantity.setText(quantityTrArraylist.get(position));
        tvHrg.setText(hargaTrArraylist.get(position));

        return row;
    }
}
