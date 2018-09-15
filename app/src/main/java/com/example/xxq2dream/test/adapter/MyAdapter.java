package com.example.xxq2dream.test.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xxq2dream.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * desc ：
 *
 * @author ：xxq2dream
 * @Date ： 2018/9/15
 */
public class MyAdapter extends RecyclerView.Adapter{

    private List<String> items = new ArrayList<>();
    private Context mContext;


    public MyAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.items = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.left_drawer_menu_layout, parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((MyViewHolder) holder).mTextView.setText(items.get(position));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.item);
        }
    }
}
