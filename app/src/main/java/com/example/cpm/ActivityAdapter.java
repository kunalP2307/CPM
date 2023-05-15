package com.example.cpm;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cpm.model.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder>{

    private Context context;
    private List<Activity> activityList;
    private SelectListener listener;

    public ActivityAdapter(Context context, SelectListener listener){
        this.context = context;
        activityList = new ArrayList<>();
        this.listener = listener;
    }

    public void addActivity(Activity activity){
        activityList.add(activity);
        notifyDataSetChanged();
    }

    public void clear(){
        activityList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_row, parent, false);
        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        holder.bindViews(activityList.get(position));

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view, position,activityList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }


    public class ActivityViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewId, textViewName, textViewDelay;
        private ConstraintLayout constraintLayout;
        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewId = itemView.findViewById(R.id.text_view_id_row);
            textViewDelay = itemView.findViewById(R.id.text_view_delay_row);
            textViewName = itemView.findViewById(R.id.text_view_name_row);
            constraintLayout = itemView.findViewById(R.id.activity_row);
        }

        public void bindViews(Activity activity){
            textViewId.setText(activity.getId());
            textViewName.setText(activity.getTaskName());

            if(activity.getDelay() < 0)
                textViewDelay.setTextColor(Color.RED);
            if(activity.getDelay() == 0)
                textViewDelay.setTextColor(Color.BLUE);
            if(activity.getDelay() >  0)
                textViewDelay.setTextColor(Color.GREEN);

            if(activity.getDelay() < 0)
                textViewDelay.setText(activity.getDelay() * -1 +" Days");
            else
                textViewDelay.setText(activity.getDelay()+" Days");
        }
    }
}
