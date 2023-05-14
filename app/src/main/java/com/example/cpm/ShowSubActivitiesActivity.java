package com.example.cpm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.cpm.model.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class ShowSubActivitiesActivity extends AppCompatActivity implements SelectListener{

    RecyclerView recyclerView;
    ArrayList<Activity> activities;
    ActivityAdapter activityAdapter;
    TextView textViewTaskName, textViewResult, textViewAddSubAct;
    EditText editTextStartDate, editTextEndDate;
    Button buttonSaveActDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sub_activities);

        bindComponents();
        activityAdapter = new ActivityAdapter(this, this);
        recyclerView.setAdapter(activityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bindComponents();
        getActivities();
        initRecyclerView();
    }

    private void bindComponents(){
        recyclerView = findViewById(R.id.recycler_sub_activity);
        textViewTaskName = findViewById(R.id.textView_activity_name_sub_act);
        editTextEndDate = findViewById(R.id.edit_text_end_date_sub_act);
        editTextStartDate = findViewById(R.id.edit_text_start_date_sub_act);
        textViewResult = findViewById(R.id.edit_text_result_sub_act);
        buttonSaveActDetails = findViewById(R.id.button_save_act_details);
        textViewAddSubAct = findViewById(R.id.text_view_add_sub_act);

        buttonSaveActDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        textViewAddSubAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initRecyclerView(){
        textViewTaskName.setText(activities.get(0).getTaskName());
        for(int i=1; i<activities.size(); i++){
            activityAdapter.addActivity(activities.get(i));
        }
    }
    private void getActivities(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            activities = (ArrayList<Activity>) extras.getSerializable("EXTRA_ACTIVITIES");
        }
    }

    @Override
    public void onItemClick(View v, int position, com.example.cpm.model.Activity activity) {

    }
}
