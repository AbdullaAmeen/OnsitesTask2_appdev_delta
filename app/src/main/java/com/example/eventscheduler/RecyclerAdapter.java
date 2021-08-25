package com.example.eventscheduler;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<Events> eventsArrayList;
    private Context context;
    OnCardListener onCardListener;

    public RecyclerAdapter(Context context, ArrayList<Events> eventsArrayList, OnCardListener onCardListener) {
        this.eventsArrayList = eventsArrayList;
        this.context = context;
        this.onCardListener = onCardListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, onCardListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Events event = eventsArrayList.get(position);
        holder.tv_name.setText(event.getEventName());
        holder.tv_desc.setText(event.getEventDesc());
        holder.tv_time.setText(DateFormat.getTimeInstance(DateFormat.DEFAULT).format(event.getC().getTime()) );
        holder.sh_toggle.setText(DateFormat.getTimeInstance(DateFormat.DEFAULT).format(event.getTime()));

    }

    @Override
    public int getItemCount() {
        if(!eventsArrayList.isEmpty())
            return eventsArrayList.size();

        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnCardListener onCardListener;
        ConstraintLayout lt_parent;
        TextView tv_name, tv_desc, tv_time;
        Switch sh_toggle;
        public ViewHolder(View itemView, OnCardListener onCardListener) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_EventName);
            lt_parent = itemView.findViewById(R.id.lt_parent);
            tv_desc = itemView.findViewById(R.id.tv_eventdesc);
            tv_time = itemView.findViewById(R.id.tv_eventTIme);
            sh_toggle = itemView.findViewById(R.id.sh_eventSwitch);
            this.onCardListener = onCardListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCardListener.onCardClick(getAdapterPosition());
        }
    }

    public interface OnCardListener{
        void onCardClick(int position);

    }


}
