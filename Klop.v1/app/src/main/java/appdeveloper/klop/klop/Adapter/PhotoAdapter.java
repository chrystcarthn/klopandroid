package appdeveloper.klop.klop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import appdeveloper.klop.klop.Model.Photo;
import appdeveloper.klop.klop.R;


public class PhotoAdapter extends BaseAdapter {

    Context context;
    private ArrayList<Photo> dataPhoto;

    public PhotoAdapter(ArrayList<Photo> dataPhoto, Context context) {
        this.context = context;
        this.dataPhoto = dataPhoto;
    }

    @Override
    public int getCount() {
        return dataPhoto.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_photo, parent, false);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
       // mHolder.txtViewName.setText(dataPhoto.get(position).getCREATED());
        Glide.with(context)
                .load(dataPhoto.get(position).getFile())
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .crossFade()
                .into(mHolder.imgViewPhoto);

        return convertView;
    }

    static class ViewHolder {
        TextView txtViewName;
        ImageView imgViewPhoto;
        ViewHolder(View view) {

           // txtViewName  = (TextView) view.findViewById(R.id.tv_name);
            imgViewPhoto = (ImageView) view.findViewById(R.id.img_cover);

        }
    }
}
