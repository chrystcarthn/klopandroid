package appdeveloper.klop.klop.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import appdeveloper.klop.klop.Model.CartTreatment;
import appdeveloper.klop.klop.R;

/**
 * Created by CMDDJ on 5/28/2018.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    private List<CartTreatment> cartList;


    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_cart_confirmation, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CartAdapter.MyViewHolder holder, int position) {
        CartTreatment cartTreatment = cartList.get(position);

        holder.tvNamatr.setText(cartTreatment.getStrNmTr());
        holder.tvQty.setText(cartTreatment.getStrQty()+"x");

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        String hargaFormatted = cartTreatment.getStrHrg();
        String subFormatted = cartTreatment.getStrSub();

        holder.tvHarga.setText(formatRupiah.format((double)Integer.parseInt(hargaFormatted)));
        holder.tvSub.setText(formatRupiah.format((double)Integer.parseInt(subFormatted)));
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNamatr, tvQty, tvHarga, tvSub;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvNamatr = (TextView) itemView.findViewById(R.id.tvCartNamaTr);
            tvQty = (TextView) itemView.findViewById(R.id.tvCartQuantityTr);
            tvHarga = (TextView) itemView.findViewById(R.id.tvCartHargaTr);
            tvSub = (TextView) itemView.findViewById(R.id.tvSubTotal);
        }
    }

    public CartAdapter(List<CartTreatment> cartList) {
        this.cartList = cartList;
    }
}
