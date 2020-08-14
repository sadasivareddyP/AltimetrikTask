package com.altimetrik.itunes_search.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.altimetrik.itunes_search.R;
import com.altimetrik.itunes_search.base.BaseActivity;
import com.altimetrik.itunes_search.databinding.ItemListBinding;
import com.altimetrik.itunes_search.model.Track;
import com.altimetrik.itunes_search.model.TrackEvent;
import com.altimetrik.itunes_search.view.ui.TrackDetailActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

public class CartRecyclerViewAdapter  extends RecyclerView.Adapter<CartRecyclerViewAdapter.TrackViewHolder> {

    private final Activity mActivity;
    private final ArrayList<Track> mValues;

    public CartRecyclerViewAdapter(Activity activity, ArrayList<Track> items) {
        mActivity = activity;
        mValues = items;
    }

    @NonNull
    @Override
    public CartRecyclerViewAdapter.TrackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListBinding itemListBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_list, parent, false);
        return new CartRecyclerViewAdapter.TrackViewHolder(itemListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartRecyclerViewAdapter.TrackViewHolder holder, final int position) {
        Track track = mValues.get(position);

        // Bind value of track to this adapter UI
        holder.itemListBinding.setTrack(track);

        holder.itemListBinding.layoutItem.setOnClickListener(view -> {

            // Post event in EventBus so the subscriber can observe
            EventBus.getDefault().postSticky(new TrackEvent(track));

//            Intent in = new Intent(mActivity, TrackDetailActivity.class);
//            mActivity.startActivity(in);
        });


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    class TrackViewHolder extends RecyclerView.ViewHolder {

        private ItemListBinding itemListBinding;
        private CheckBox checkBox;

        public TrackViewHolder(@NonNull ItemListBinding mItemListBinding) {
            super(mItemListBinding.getRoot());

            this.itemListBinding = mItemListBinding;
            itemListBinding.checkbox.setVisibility(View.GONE);
        }
    }


}
