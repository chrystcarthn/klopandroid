package appdeveloper.klop.klop.Other;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

import appdeveloper.klop.klop.Adapter.BookTreatmentsAdapter;
import appdeveloper.klop.klop.R;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.FilterResultListener;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;


/**
 * Created by MADNESS on 5/14/2017.
 */

public class BookTreatmentSearchCompat<T extends Searchable> extends BaseSearchDialogCompat<T> {
    private String mTitle;
    private String mSearchHint;
    private SearchResultListener<T> mSearchResultListener;

    public BookTreatmentSearchCompat(Context context, String title, String searchHint,
                                     @Nullable Filter filter, ArrayList<T> items,
                                     SearchResultListener<T> searchResultListener) {
        super(context, items, filter, null,null);
        init(title, searchHint, searchResultListener);
    }

    private void init(String title, String searchHint,
                      SearchResultListener<T> searchResultListener){
        mTitle = title;
        mSearchHint = searchHint;
        mSearchResultListener = searchResultListener;
    }

    @Override
    protected void getView(View view) {
        setContentView(view);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCancelable(true);
        TextView txtTitle = (TextView) view.findViewById(ir.mirrajabi.searchdialog.R.id.txt_title);
        final EditText searchBox = (EditText) view.findViewById(getSearchBoxId());
        txtTitle.setText(mTitle);
        searchBox.setHint(mSearchHint);
        view.findViewById(ir.mirrajabi.searchdialog.R.id.dummy_background)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        final BookTreatmentsAdapter adapter = new BookTreatmentsAdapter<>(getContext(),
                R.layout.list_item_booktreatments,getItems());
        adapter.setSearchResultListener(mSearchResultListener);
        adapter.setSearchDialog(this);
        setFilterResultListener(new FilterResultListener<T>() {
            @Override
            public void onFilter(ArrayList<T> items) {
                ((BookTreatmentsAdapter)getAdapter())
                        .setSearchTag(searchBox.getText().toString())
                        .setItems(items);
            }
        });
        setAdapter(adapter);
    }

    public BookTreatmentSearchCompat setTitle(String title) {
        mTitle = title;
        return this;
    }

    public BookTreatmentSearchCompat setSearchHint(String searchHint) {
        mSearchHint = searchHint;
        return this;
    }

    public BookTreatmentSearchCompat setSearchResultListener(
            SearchResultListener<T> searchResultListener) {
        mSearchResultListener = searchResultListener;
        return this;
    }

    @LayoutRes
    @Override
    protected int getLayoutResId() {
        return ir.mirrajabi.searchdialog.R.layout.search_dialog_compat;
    }

    @IdRes
    @Override
    protected int getSearchBoxId() {
        return ir.mirrajabi.searchdialog.R.id.txt_search;
    }

    @IdRes
    @Override
    protected int getRecyclerViewId() {
        return ir.mirrajabi.searchdialog.R.id.rv_items;
    }
}
