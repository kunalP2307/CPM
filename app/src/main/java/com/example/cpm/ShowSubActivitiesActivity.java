package com.example.cpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.cpm.model.Activity;
import com.example.cpm.model.Project;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShowSubActivitiesActivity extends AppCompatActivity implements SelectListener{

    RecyclerView recyclerView;
    ArrayList<Activity> activities;
    ActivityAdapter activityAdapter;
    TextView textViewTaskName, textViewResult, textViewAddSubAct;
    EditText editTextStartDate, editTextEndDate;
    Button buttonSaveActDetails;
    String taskType;
    int activityNo;
    Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sub_activities);

        bindComponents();
        selectTaskTypeForUI();
        activityAdapter = new ActivityAdapter(this, this);
        recyclerView.setAdapter(activityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        bindComponents();
        getActivities();
        initRecyclerView();
        initResult();
    }

    private void initResult(){
        if(taskType.equals("main")){
            editTextStartDate.setText(activities.get(0).getStartDate());
            editTextEndDate.setText(activities.get(0).getFinishDate());
        }
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
                if(isValidData()){
                    if(taskType.equals("main")){
                        project.getListActivities().get(activityNo).
                                get(0).setStartDate(editTextStartDate.getText().toString());
                        project.getListActivities().get(activityNo).
                                get(0).setFinishDate(editTextEndDate.getText().toString());
                        storeProjectData();
                    }
                    else{

                    }
                }
            }
        });

        textViewAddSubAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private void storeProjectData(){
        Log.d("TAG", "storeProjectData: called");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Project1");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(project);
                Toast.makeText(getApplicationContext(), "Action Successful!", Toast.LENGTH_SHORT).show();
                Log.d("TAG", "onDataChange: ");
                startActivity(new Intent(getApplicationContext(), SuccessActivity.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to Store", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isValidData(){
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        dateFormat.setLenient(false); // Disable lenient parsing

        boolean b1 = false, b2 = false;

        try {
            dateFormat.parse(editTextEndDate.getText().toString());
            b1 = true;
        } catch (ParseException e) {
            editTextEndDate.setError("Select Valid Date");
        }

        try {
            dateFormat.parse(editTextStartDate.getText().toString());
            b2 = true;
        } catch (ParseException e) {
            editTextStartDate.setError("Select Valid Date");
        }

        return b1&&b2;
    }

    private void temp(){

        findViewById(R.id.textView15).setVisibility(View.GONE);
        findViewById(R.id.view11).setVisibility(View.GONE);
        findViewById(R.id.view12).setVisibility(View.GONE);
        findViewById(R.id.text_view_add_sub_act).setVisibility(View.GONE);
        findViewById(R.id.imageView7).setVisibility(View.GONE);
        findViewById(R.id.text_view_id_row2).setVisibility(View.GONE);
        findViewById(R.id.text_view_name_row5).setVisibility(View.GONE);
        findViewById(R.id.text_view_name_row4).setVisibility(View.GONE);


    }
    private void initRecyclerView(){
        if(taskType.equals("main")) {
            textViewTaskName.setText(activities.get(0).getTaskName());
            for (int i = 1; i < activities.size(); i++) {
                activityAdapter.addActivity(activities.get(i));
            }
        }
    }
    private void getActivities(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            activities = (ArrayList<Activity>) extras.getSerializable("EXTRA_ACTIVITIES");
        }
    }

    private void selectTaskTypeForUI(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            taskType = extras.getString("EXTRA_TASK_TYPE");
            activityNo = extras.getInt("EXTRA_ACTIVITY_NO");
            project = (Project) extras.getSerializable("EXTRA_PROJECT");
            if(taskType.equals("main")){

            }
            else
                temp();
        }
    }

    @Override
    public void onItemClick(View v, int position, com.example.cpm.model.Activity activity) {

    }
}
