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

import appdeveloper.klop.klop.Model.News;
import appdeveloper.klop.klop.R;

/**
 * Created by CMDDJ on 6/6/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<News> dataNews;
    private Context context;

    public NewsAdapter(ArrayList<News> dataNews, Context context) {
        this.dataNews = dataNews;
        this.context = context;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View v = layoutInflater.inflate(R.layout.list_item_news, parent, false);
        return new NewsAdapter.ViewHolder(v);   }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, int position) {
        holder.tvJudul.setText(dataNews.get(position).getTITLE());
        holder.tvIsi.setText(dataNews.get(position).getCONTENT());

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
        String strIdStore;
        LinearLayout linearLayout;

        ViewHolder(View itemView) {
            super(itemView);

            tvJudul = (TextView) itemView.findViewById(R.id.tvJudul);
            tvIsi = (TextView) itemView.findViewById(R.id.tvIsi);

        }
    }
}
