package appdeveloper.klop.klop.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import appdeveloper.klop.klop.Model.Like;
import appdeveloper.klop.klop.Model.Review;
import appdeveloper.klop.klop.Preference.SessionPreference;
import appdeveloper.klop.klop.PresenterImp.ReviewTabFragPresenterImp;
import appdeveloper.klop.klop.R;
import appdeveloper.klop.klop.Other.Utils;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by CMDDJ on 4/22/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private ArrayList<Review> dataReview;
    private Context context;
    ReviewTabFragPresenterImp reviewTabFragPresenterImp;
    boolean liked2;
    Like itemLike, itemLike2;
//    int isLiked, isUnliked;
    int awal=0;
    String strGlobalIdRev, strGlobalIdUser;

    public ReviewAdapter(ArrayList<Review> dataReview, Context context) {
        this.dataReview = dataReview;
        this.context = context;
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View v = layoutInflater.inflate(R.layout.list_item_review, parent, false);

        return new ReviewAdapter.ViewHolder (v);
    }

    @Override
    public void onBindViewHolder(final ReviewAdapter.ViewHolder holder, final int position) {
        strGlobalIdRev = dataReview.get(position).getIdReview();
        strGlobalIdUser = holder.strIdUser;

        //    Toast.makeText(context, "review "+strGlobalIdRev+" user "+strGlobalIdUser, Toast.LENGTH_SHORT).show();

        holder.txtViewNama.setText(dataReview.get(position).getFullName());
        holder.txtReview.setText(dataReview.get(position).getTextReview());

        holder.txtRate.setText(dataReview.get(position).getRate()+"・");
        holder.txtDate.setText(parseDateFormatted(dataReview.get(position).getCreated()));

        // Toast.makeText(context, "liked2: "+holder.liked, Toast.LENGTH_SHORT).show();


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setElevation(holder.imgUser, Utils.dpToPx(5, context));
            ViewCompat.setTranslationZ(holder.imgUser, Utils.dpToPx(5, context));
        } else {
            holder.imgUser.bringToFront(); // works on both pre-lollipop and Lollipop
        }
        try {
            holder.linkPhoto = dataReview.get(position).getAvatar();
        }catch(Exception e){e.printStackTrace();}

        if(holder.linkPhoto != null){
            Picasso.with(context).load(holder.linkPhoto).into(holder.imgUser);
        }else   holder.imgUser.setImageResource(R.mipmap.userdefault);

        reviewTabFragPresenterImp.checkLike(dataReview.get(position).getIdReview().toString(), holder.strIdUser);
        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewTabFragPresenterImp.checkLike(dataReview.get(position).getIdReview().toString(), holder.strIdUser);
            }
        });
    }


    public String parseDateFormatted(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    @Override
    public int getItemCount() {
        if(dataReview!=null){
            return dataReview.size();
        } else{
            return 0;
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtViewNama, txtDate, txtRate, txtReview, tvCountLike;
        ImageView imgUser;
        boolean liked = false;
        int flag;
        boolean flagCek;
        boolean indicator;
        boolean suka = false;
        SharedPreferences pref;
        SharedPreferences.Editor editor;
        int isLiked;
        String strIdUser, linkPhoto, strDate;
        LinearLayout linearLayout;
        ImageButton btnLike, btnReport;

        CoordinatorLayout cordTabRev;

        SessionPreference session;
        HashMap<String, String> userdata;

        ViewHolder(View itemView) {
            super(itemView);

            session = new SessionPreference(context);
            userdata = session.getUserDetails();
            strIdUser = userdata.get(SessionPreference.KEY_IDUSER);

            txtViewNama = (TextView) itemView.findViewById(R.id.tvUserNameReviewer);
            txtDate = (TextView) itemView.findViewById(R.id.tvDateReview);
            txtReview = (TextView) itemView.findViewById(R.id.tvReviewContent);
            txtRate = (TextView) itemView.findViewById(R.id.tvRateReview);
            imgUser = (ImageView) itemView.findViewById(R.id.imgAvatar);
            btnLike = (ImageButton) itemView.findViewById(R.id.btnLike);
            btnReport = (ImageButton) itemView.findViewById(R.id.btnReport);
            tvCountLike = (TextView) itemView.findViewById(R.id.tvCountLike);

            reviewTabFragPresenterImp = new ReviewTabFragPresenterImp(this);
            btnReport.setOnClickListener(this);
          //  btnLike.setOnClickListener(this);



        }

        public void status(Boolean status){
            if(status == false)
                unLike();
            else Like();
        }

        public void status2(Boolean status){
            if(status == true)
                unLike();
            else Like();
        }

        public void Like(){
            btnLike.setImageResource(R.drawable.thumb_blue);
        }

        public void unLike(){
            btnLike.setImageResource(R.drawable.thumb_grey);

        }










        public boolean indicators(){
        //    Toast.makeText(context, "indicator :"+indicator, Toast.LENGTH_SHORT).show();
            if(indicator == true){
                return true;
            } else return false;
        }


        public void settingLiked(){
            indicator = true;
        //    Toast.makeText(context, "berhasil suka :" + indicator, Toast.LENGTH_SHORT).show();
         //   Toast.makeText(context, "menjadi like", Toast.LENGTH_SHORT).show();

            btnLike.setImageResource(R.drawable.thumb_blue);

//            reviewTabFragPresenterImp.like(strGlobalIdRev,  strIdUser);
//            btnLike.setImageResource(R.drawable.thumb_blue);
//            liked = true;
        }

        public void settingUnliked(){
            indicator = false;
          //  Toast.makeText(context, "berhasil tidak suka :"+indicator, Toast.LENGTH_SHORT).show();

        //    Toast.makeText(context, "menjadi unlike", Toast.LENGTH_SHORT).show();
//            reviewTabFragPresenterImp.unlike(strGlobalIdRev,  strIdUser);
            btnLike.setImageResource(R.drawable.thumb_grey);
//            btnLike.setImageResource(R.drawable.thumb_blue);
//            liked = true;
        }



        public int toggleThumb(){
            if(flag == 1){
          //      Toast.makeText(context, "menyukai", Toast.LENGTH_SHORT).show();
                return 1;
            } else {
            //    Toast.makeText(context, "tidak suka", Toast.LENGTH_SHORT).show();
                return 0;
            }
        }

        public void doLikeReview(){
            flag = 1;
            //  reviewTabFragPresenterImp.like(strGlobalIdRev,  strIdUser);

        }

        public void doUnLikeReview(){
            flag = 0;
            // reviewTabFragPresenterImp.unlike(strGlobalIdRev,  strIdUser);

        }

        public int cek(){
            if(flagCek == true){
           //     Toast.makeText(context, "flagCek likes = " + flagCek, Toast.LENGTH_SHORT).show();
                return 1;
            }else {
            //    Toast.makeText(context, "flagCek likes " + flagCek, Toast.LENGTH_SHORT).show();
                return 0;
            }
        }

        public void isLikeds(){
         //   Toast.makeText(context, "isLiked yay", Toast.LENGTH_SHORT).show();
            btnLike.setImageResource(R.drawable.thumb_blue);
            //flagCek = true;
           // return true;
        }

        public void isUnlike(){
         //   Toast.makeText(context, "isUnlike umm", Toast.LENGTH_SHORT).show();
            btnLike.setImageResource(R.drawable.thumb_grey);
           // flagCek = false;
          //  return false;
        }


        public void netral(){
            Toast.makeText(context, "netral", Toast.LENGTH_SHORT).show();
        }

        public void errorLikingReview(){
            Toast.makeText(context, "Gagal like", Toast.LENGTH_SHORT).show();
        }



        public void errorUnlikingReview(){
            Toast.makeText(context, "Gagal unlike", Toast.LENGTH_SHORT).show();
        }


        public void ErrorResponseFailed(){
            Toast.makeText(context, "Terjadi kesalahan, mohon ulangi lagi nanti", Toast.LENGTH_SHORT).show();

        }

        public void ErrorConnectionFailed(){
            Toast.makeText(context, "Terjadi kesalahan, periksa internet Anda", Toast.LENGTH_SHORT).show();

        }




        @Override
        public void onClick(View v) {
//            if(v == btnLike){
//                reviewTabFragPresenterImp.changeLike(strGlobalIdRev, strIdUser);
//            }
           // else
                if (v == btnReport){
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Laporkan Ulasan")
                        .setContentText("Ulasan tidak bermanfaat?")
                        .setConfirmText("Ya")
                        .setCancelText("Tidak")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
//                        Snackbar snackbar = Snackbar.make(cordTabRev, "Terima kasih atas masukan Anda.", Snackbar.LENGTH_LONG);
//                        snackbar.show();
                        Toast.makeText(context, "Terima kasih atas masukan Anda.", Toast.LENGTH_SHORT).show();
                        sweetAlertDialog.dismiss();
                    }
                }).show();
            }
        }
    }
}
