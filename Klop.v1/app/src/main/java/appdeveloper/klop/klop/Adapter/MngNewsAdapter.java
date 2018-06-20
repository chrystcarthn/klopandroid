package appdeveloper.klop.klop.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import appdeveloper.klop.klop.Activity.ManageNews;
import appdeveloper.klop.klop.Model.News;
import appdeveloper.klop.klop.PresenterImp.StoreSettingsPresenterImp;
import appdeveloper.klop.klop.R;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CMDDJ on 6/6/2018.
 */

public class MngNewsAdapter extends RecyclerView.Adapter<MngNewsAdapter.ViewHolder> {


    Button btnAddNews;
    TextView tvJudul, tvStatusView;
    EditText edJudul, edIsi;
    TextInputLayout judulWrapper, isiWrapper;
    ImageButton btnClose;
    Switch swtStatus;
    String strStatus;


    String strIdNews;
    String strIdStore;
    StoreSettingsPresenterImp storeSettingsPresenterImp;
    private ArrayList<News> dataNews;
    private Context context;

    public AlertDialog dialog;

    public MngNewsAdapter(ArrayList<News> dataNews, Context context) {
        this.dataNews = dataNews;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        storeSettingsPresenterImp = new StoreSettingsPresenterImp(this);

        View v = layoutInflater.inflate(R.layout.list_item_news2, parent, false);
        return new ViewHolder(v);   }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tvJudul.setText(dataNews.get(position).getTITLE());
        holder.tvIsi.setText(dataNews.get(position).getCONTENT());

        if(dataNews.get(position).getPUBLISHED().equals("false")){
            holder.tvStatus.setText("DISEMBUNYIKAN");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.secondaryText2));

        } else if(dataNews.get(position).getPUBLISHED().equals("true")){
            holder.tvStatus.setText("DIPAJANG");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.open));
        }


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Hapus berita")
                        .setContentText("Berita ini akan dihapus")
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
                        strIdNews = dataNews.get(position).getIDNEWS();
                        strIdStore = dataNews.get(position).getIDSTORE();
                        // Toast.makeText(context, "delete "+strIdStore+"|"+strIdTr, Toast.LENGTH_SHORT).show();
                        storeSettingsPresenterImp.deleteNews(strIdNews);
                        ((ManageNews)context).loadRecyclerViewNews();

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

                View mView = layoutInflater.inflate(R.layout.dialog_add_news, null);

                tvJudul = (TextView) mView.findViewById(R.id.tvJudul);
                tvStatusView = (TextView) mView.findViewById(R.id.tvStatus);
                judulWrapper = (TextInputLayout) mView.findViewById(R.id.input_layout_judul);
                isiWrapper = (TextInputLayout) mView.findViewById(R.id.input_layout_isi);
                swtStatus = (Switch) mView.findViewById(R.id.switchStatus);
                btnClose = (ImageButton) mView.findViewById(R.id.btnClose);
                edJudul = (EditText) mView.findViewById(R.id.edJudul);
                edIsi = (EditText) mView.findViewById(R.id.edContent);

                Button btnAddNews = (Button) mView.findViewById(R.id.btnAddNews);

                tvJudul.setText("Ubah Berita");
                btnAddNews.setText("Simpan");

                edJudul.setText(dataNews.get(position).getTITLE());
                edIsi.setText(dataNews.get(position).getCONTENT());

                if(dataNews.get(position).getPUBLISHED().equals("true")){
                    swtStatus.setChecked(true);
                    swtStatus.setText("DIPAJANG");
                    swtStatus.setTextColor(context.getResources().getColor(R.color.open));
                    strStatus = "true";
                } else if(dataNews.get(position).getPUBLISHED().equals("false")){
                    swtStatus.setChecked(false);
                    swtStatus.setText("DISEMBUNYIKAN");
                    swtStatus.setTextColor(context.getResources().getColor(R.color.secondaryText2));
                    strStatus = "false";
                }

                strIdNews = dataNews.get(position).getIDNEWS();
                strIdStore = dataNews.get(position).getIDSTORE();

                mBuilder.setView(mView);
                dialog = mBuilder.create();

                swtStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(swtStatus.isChecked()){
                            strStatus = "true";
                            swtStatus.setText("DIPAJANG");
                            swtStatus.setTextColor(context.getResources().getColor(R.color.open));
                        } else{
                            strStatus = "false";
                            swtStatus.setText("DISEMBUNYIKAN");
                            swtStatus.setTextColor(context.getResources().getColor(R.color.secondaryText2));
                        }
                    }
                });


                btnClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnAddNews.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(edJudul.getText().toString().equals("")){
                            //  Toast.makeText(context, "error nama", Toast.LENGTH_SHORT).show();
                            showErrorJudul();
                        } else
                        if(edIsi.getText().toString().equals("")){
                            //   Toast.makeText(context, "error harga", Toast.LENGTH_SHORT).show();
                            showErrorIsi();
                        } else if(edJudul.getText().toString().equals("") & edIsi.getText().toString().equals("")){
                            //  Toast.makeText(context, "error nama", Toast.LENGTH_SHORT).show();
                            showErrorJudul();
                        } else {
                            showNoError();
                            storeSettingsPresenterImp.updateNews(strIdNews, edJudul.getText().toString(), edIsi.getText().toString(), strStatus);
                            ((ManageNews)context).loadRecyclerViewNews();
                        }
                    }
                });

                dialog.show();
            }
        });
    }



    public void showErrorJudul(){
        judulWrapper.setError("Harap mengisi judul berita");
        isiWrapper.setErrorEnabled(false);
        //  requestFocus(edNmTr);
    }

    public void showErrorIsi(){
        judulWrapper.setErrorEnabled(false);
        isiWrapper.setError("Harap mengisi isi berita");
        // requestFocus(edHrgTr);
    }

    public void showNoError(){
        judulWrapper.setErrorEnabled(false);
        isiWrapper.setErrorEnabled(false);
    }

    public void updateNewsSuccess(){
        dialog.dismiss();

    }

    public void ErrorUpdateNews(){
        Toast.makeText(context, "Data tidak valid!", Toast.LENGTH_SHORT).show();
        // mWaveSwipeRefreshLayout.setRefreshing(false);
    }

    public void deleteSuccess(){
        Toast.makeText(context, "Berita dihapus. Sapu layar untuk memperbaharui data di halaman", Toast.LENGTH_SHORT).show();
    }

    public void deleteFailed(){
        Toast.makeText(context, "Gagal menghapus berita. Coba ulangi lagi nanti", Toast.LENGTH_SHORT).show();
    }


    public void ErrorResponseFailed(){
        Toast.makeText(context, "Terjadi gangguan pada server. Coba lagi nanti", Toast.LENGTH_SHORT).show();

    }


    public void ErrorConnectionFailed() {
        Toast.makeText(context, "Terjadi gangguan. Periksa koneksi internet Anda", Toast.LENGTH_SHORT).show();

    }


    @Override
    public int getItemCount() {
        if(dataNews!=null){
            return dataNews.size();
        } else{
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul, tvIsi, tvStatus;
        ImageButton btnDelete, btnEdit;

        String strIdStore;
        LinearLayout linearLayout;

        ViewHolder(View itemView) {
            super(itemView);

            tvJudul = (TextView) itemView.findViewById(R.id.tvJudul);
            tvIsi = (TextView) itemView.findViewById(R.id.tvIsi);
            tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btnDelete);
            btnEdit = (ImageButton) itemView.findViewById(R.id.btnEdit);


        }
    }
}
