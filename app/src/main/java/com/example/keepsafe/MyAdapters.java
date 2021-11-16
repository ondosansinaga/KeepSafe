package com.example.keepsafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapters extends RecyclerView.Adapter<MyAdapters.MyViewHolder> {

    Context context;
    ArrayList<Reporter2> list;


    public MyAdapters(Context context, ArrayList<Reporter2> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.report_item,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Reporter2 reporter2 = list.get(position);
        holder.kejadian.setText(reporter2.getKejadian());
        holder.date.setText(reporter2.getDate());
        holder.loc.setText(reporter2.getLoc());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView kejadian, date,loc;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            kejadian = itemView.findViewById(R.id.getKejadian);
            date = itemView.findViewById(R.id.getDate);
            loc= itemView.findViewById(R.id.getLoc);

        }
    }
}
