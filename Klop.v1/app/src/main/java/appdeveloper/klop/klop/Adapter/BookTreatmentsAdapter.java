package appdeveloper.klop.klop.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import appdeveloper.klop.klop.Model.Treatment;
import appdeveloper.klop.klop.R;
import com.bumptech.glide.Glide;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import ir.mirrajabi.searchdialog.StringsHelper;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;


public class BookTreatmentsAdapter<T extends Searchable>
        extends RecyclerView.Adapter<BookTreatmentsAdapter.ViewHolder> {
    protected Context mContext;
    private List<T> mItems = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private int mLayout;
    private SearchResultListener mSearchResultListener;
    private AdapterViewBinder<T> mViewBinder;
    private String mSearchTag;
    private boolean mHighlightPartsInCommon = true;
    private String mHighlightColor = "#FFED2E47";
    private BaseSearchDialogCompat mSearchDialog;

    public BookTreatmentsAdapter(Context context, @LayoutRes int layout, List<T> items) {
        this(context,layout,null, items);
    }

    public BookTreatmentsAdapter(Context context, AdapterViewBinder<T> viewBinder,
                                 @LayoutRes int layout, List<T> items) {
        this(context,layout,viewBinder, items);
    }

    public BookTreatmentsAdapter(Context context, @LayoutRes int layout,
                                 @Nullable AdapterViewBinder<T> viewBinder,
                                 List<T> items) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mItems = items;
        this.mLayout = layout;
        this.mViewBinder = viewBinder;
    }

    public List<T> getItems() {
        return mItems;
    }

    public void setItems(List<T> objects) {
        this.mItems = objects;
        notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public BookTreatmentsAdapter<T> setViewBinder(AdapterViewBinder<T> viewBinder) {
        this.mViewBinder = viewBinder;
        return this;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = mLayoutInflater.inflate(mLayout, parent, false);
        convertView.setTag(new ViewHolder(convertView));
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        initializeViews(getItem(position), holder, position);
    }
    private void initializeViews(final T object, final ViewHolder holder,
                                 final int position) {
        if(mViewBinder != null)
            mViewBinder.bind(holder, object, position);
        LinearLayout root = holder.getViewById(R.id.root);
       // TextView tvId = holder.getViewById(R.id.tvIdTr);
        TextView tvNm = holder.getViewById(R.id.tvNmTr);
        TextView tvHrg = holder.getViewById(R.id.tvHrgTr);
//        CircleImageView image = holder.getViewById(R.id.image);
//        /*if(position%2 == 0)
//            root.setBackgroundColor(Color.parseColor("#f6f6f6"));
//        else root.setBackgroundColor(Color.parseColor("#fcfcfc"));*/
//        Glide.with(mContext)
//                .load(((Treatment)object).getImageUrl())
//                .asBitmap()
//                .into(image);
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.ENGLISH);
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        String hargaFormatted = ((Treatment)object).getPRICETREATMENT();

      //  holder.tvHargaTreatment.setText(formatRupiah.format((double)Integer.parseInt(hargaFormatted)));

        if(mSearchTag != null && mHighlightPartsInCommon) {
//            tvId.setText(StringsHelper.highlightLCS(((Treatment)object).getIDTREATMENT(), getSearchTag(),
//                    Color.parseColor(mHighlightColor)));
            tvNm.setText(StringsHelper.highlightLCS(object.getTitle(), getSearchTag(),
                    Color.parseColor(mHighlightColor)));
            tvHrg.setText(StringsHelper.highlightLCS(formatRupiah.format((double)Integer.parseInt(hargaFormatted)), getSearchTag(),
                    Color.parseColor(mHighlightColor)));
        }
        else {
//            tvId.setText(((Treatment)object).getIDTREATMENT());
            tvNm.setText(object.getTitle());
            tvHrg.setText(formatRupiah.format((double)Integer.parseInt(hargaFormatted)));
        }

        if (mSearchResultListener != null)
            holder.getBaseView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSearchResultListener.onSelected(mSearchDialog, object, position);
                }
            });
    }

    public SearchResultListener getSearchResultListener(){
        return mSearchResultListener;
    }
    public void setSearchResultListener(SearchResultListener searchResultListener){
        this.mSearchResultListener = searchResultListener;
    }

    public BookTreatmentsAdapter setSearchTag(String searchTag) {
        mSearchTag = searchTag;
        return this;
    }

    public String getSearchTag() {
        return mSearchTag;
    }

    public BookTreatmentsAdapter setHighlightPartsInCommon(boolean highlightPartsInCommon) {
        mHighlightPartsInCommon = highlightPartsInCommon;
        return this;
    }

    public boolean isHighlightPartsInCommon() {
        return mHighlightPartsInCommon;
    }

    public BookTreatmentsAdapter setHighlightColor(String highlightColor) {
        mHighlightColor = highlightColor;
        return this;
    }

    public BookTreatmentsAdapter setSearchDialog(BaseSearchDialogCompat searchDialog) {
        mSearchDialog = searchDialog;
        return this;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View mBaseView;

        public ViewHolder(View view) {
            super(view);
            mBaseView = view;
        }

        public View getBaseView() {
            return mBaseView;
        }
        public <T> T getViewById(@IdRes int id){
            return (T)mBaseView.findViewById(id);
        }
        public void clearAnimation(@IdRes int id)
        {
            mBaseView.findViewById(id).clearAnimation();
        }
    }
    
    public interface AdapterViewBinder<T> {
        void bind(ViewHolder holder, T item, int position);
    }
}