package com.example.xch.scanzxing.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xch.scanzxing.R;
import com.example.xch.scanzxing.entity.OriginData;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    public static List<OriginData> data;
    private Context mContext;

    public RecyclerViewAdapter(List<OriginData> originData, Context context) {
        this.data = originData;
        this.mContext = context;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recyclerview, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_place.setText(data.get(position).getOriginName());
        holder.tv_data.setText(data.get(position).getOriginDesc());
        holder.tv_time.setText(data.get(position).getCreatedAt());
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_place;
        TextView tv_data;
        TextView tv_time;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_place  = itemView.findViewById(R.id.tv_place);
            tv_data  = itemView.findViewById(R.id.tv_data);
            tv_time  = itemView.findViewById(R.id.tv_time);
        }
    }
}
