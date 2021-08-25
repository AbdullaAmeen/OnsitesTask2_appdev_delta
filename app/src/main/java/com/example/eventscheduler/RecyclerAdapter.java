package com.example.eventscheduler;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

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
        Events events = eventsArrayList.get(position);
     /*   if(!events.getBreeds().isEmpty())
            holder.tv_name.setText(events.getBreeds().get(0).getName());
        Picasso.get().load(events.getUrl()).into(holder.im_dogs);*/


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
        TextView tv_name;
        ImageView im_dogs;
        public ViewHolder(View itemView, OnCardListener onCardListener) {
            super(itemView);
          /*  tv_name = itemView.findViewById(R.id.tv_name);
            lt_parent = itemView.findViewById(R.id.lt_parent);
            im_dogs = itemView.findViewById(R.id.im_dogs);*/
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
