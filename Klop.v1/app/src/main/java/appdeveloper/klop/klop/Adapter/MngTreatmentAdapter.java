package appdeveloper.klop.klop.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.media.Image;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import appdeveloper.klop.klop.Activity.ManageTreatment;
import appdeveloper.klop.klop.Model.Treatment;
import appdeveloper.klop.klop.PresenterImp.StoreDetailPresenterImp;
import appdeveloper.klop.klop.PresenterImp.StoreSettingsPresenterImp;
import appdeveloper.klop.klop.R;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CMDDJ on 6/5/2018.
 */

public class MngTreatmentAdapter extends RecyclerView.Adapter<MngTreatmentAdapter.ViewHolder> {

    TextView tvJudul;
    EditText edNmTr, edDescTr, edHrgTr;
    TextInputLayout nmWrapper, descWrapper, hrgWrapper;
    ImageButton btnClose;
    String strIdTr;
    String strIdStore;
    StoreSettingsPresenterImp storeSettingsPresenterImp;
    private ArrayList<Treatment> dataTreatments;
    private Context context;

    public AlertDialog dialog;

    public MngTreatmentAdapter(ArrayList<Treatment> dataTreatments, Context context) {
        this.dataTreatments = dataTreatments;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        storeSettingsPresenterImp = new StoreSettingsPresenterImp(this);

        View v = layoutInflater.inflate(R.layout.list_item_treatment2, parent, false);
        return new ViewHolder(v);   }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
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

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Hapus perawatan")
                        .setContentText("Perawatan ini akan dihapus")
                        .setConfirmText("Ya")
                        .setCancelText("Batal")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        strIdTr = dataTreatments.get(position).getIDTREATMENT();
                        strIdStore = dataTreatments.get(position).getIDSTORE();
                       // Toast.makeText(context, "delete "+strIdStore+"|"+strIdTr, Toast.LENGTH_SHORT).show();
                        storeSettingsPresenterImp.deleteTreatement(strIdStore, strIdTr);
                        ((ManageTreatment)context).loadRecyclerViewTreatmentByOutlet();

                        sweetAlertDialog.dismiss();
                    }
                }).show();



            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context, "edit "+dataTreatments.get(position).getNAMETREATMENT(), Toast.LENGTH_SHORT).show();

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
                LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

                View mView = layoutInflater.inflate(R.layout.dialog_add_treatment, null);

                tvJudul = (TextView) mView.findViewById(R.id.tvJudul);
                nmWrapper = (TextInputLayout) mView.findViewById(R.id.input_layout_namatr);
                descWrapper = (TextInputLayout) mView.findViewById(R.id.input_layout_desc);
                hrgWrapper = (TextInputLayout) mView.findViewById(R.id.input_layout_harga);
                btnClose = (ImageButton) mView.findViewById(R.id.btnClose);
                edNmTr = (EditText) mView.findViewById(R.id.edNameTr);
                edDescTr = (EditText) mView.findViewById(R.id.edDescTreatment);
                edHrgTr = (EditText) mView.findViewById(R.id.edPriceTreatment);
                Button btnAddNewTr = (Button) mView.findViewById(R.id.btnAddNewTr);

                tvJudul.setText("Ubah Perawatan");
                btnAddNewTr.setText("Simpan");

                edNmTr.setText(dataTreatments.get(position).getNAMETREATMENT());
                edDescTr.setText(dataTreatments.get(position).getDESCRIPTIONTREATMENT());
                edHrgTr.setText(dataTreatments.get(position).getPRICETREATMENT());
                strIdTr = dataTreatments.get(position).getIDTREATMENT();
                strIdStore = dataTreatments.get(position).getIDSTORE();

                mBuilder.setView(mView);
               dialog = mBuilder.create();

                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnAddNewTr.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edNmTr.getText().toString().equals("")){
                          //  Toast.makeText(context, "error nama", Toast.LENGTH_SHORT).show();
                            showErrorNm();
                        } else
                        if(edHrgTr.getText().toString().equals("")){
                         //   Toast.makeText(context, "error harga", Toast.LENGTH_SHORT).show();
                            showErrorHrg();
                        } else if(edNmTr.getText().toString().equals("") & edHrgTr.getText().toString().equals("")){
                          //  Toast.makeText(context, "error nama", Toast.LENGTH_SHORT).show();
                            showErrorNm();
                        } else {
                            showNoError();
                            storeSettingsPresenterImp.updateTreatment(strIdStore, strIdTr, edNmTr.getText().toString(), edDescTr.getText().toString(), edHrgTr.getText().toString());
                            ((ManageTreatment)context).loadRecyclerViewTreatmentByOutlet();
                        }
                    }
                });

                dialog.show();
            }
        });
    }



    public void showErrorNm(){
        nmWrapper.setError("Harap mengisi nama perawatan");
        hrgWrapper.setErrorEnabled(false);
      //  requestFocus(edNmTr);
    }

    public void showErrorHrg(){
        nmWrapper.setErrorEnabled(false);
        hrgWrapper.setError("Harap mengisi harga perawatan");
       // requestFocus(edHrgTr);
    }

    public void showNoError(){
        nmWrapper.setErrorEnabled(false);
        hrgWrapper.setErrorEnabled(false);
    }

    public void updateTrSuccess(){
        dialog.dismiss();

    }

    public void ErrorUpdateTr(){
        Toast.makeText(context, "Data tidak valid. Harap periksa kembali data Anda", Toast.LENGTH_SHORT).show();
        // mWaveSwipeRefreshLayout.setRefreshing(false);
    }

    public void deleteTrSuccess(){
        Toast.makeText(context, "Perawatan dihapus. Sapu layar untuk memperbaharui data di halaman", Toast.LENGTH_SHORT).show();
    }

    public void ErrorDeleteTr(){
        Toast.makeText(context, "Gagal menghapus perawatan", Toast.LENGTH_SHORT).show();
    }


    public void ErrorResponseFailed(){
        Toast.makeText(context, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();

    }


    public void ErrorConnectionFailed() {
        Toast.makeText(context, "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();

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
        ImageButton btnDelete, btnEdit;
        String strIdStore;
        LinearLayout linearLayout;



        ViewHolder(View itemView) {
            super(itemView);

            tvTreatment = (TextView) itemView.findViewById(R.id.tvTreatment);
            tvDesc = (TextView) itemView.findViewById(R.id.tvDescTr);
            tvHargaTreatment = (TextView) itemView.findViewById(R.id.tvHargaTreatment);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
            btnEdit = (ImageButton) itemView.findViewById(R.id.btnEdit);


        }
    }
}
