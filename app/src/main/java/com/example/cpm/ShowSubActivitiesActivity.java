package com.example.cpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.cpm.model.Activity;
import com.example.cpm.model.Project;
import com.example.cpm.utils.ActivityUtils;
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

import org.checkerframework.checker.units.qual.A;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ShowSubActivitiesActivity extends AppCompatActivity implements SelectListener{

    RecyclerView recyclerView;
    ArrayList<Activity> activities;
    ActivityAdapter activityAdapter;
    TextView textViewTaskName, textViewResult, textViewAddSubAct, textViewDuration, textViewTaskId, textViewUploadDoc, textViewViewDoc;
    EditText editTextStartDate, editTextEndDate;
    Button buttonSaveActDetails;
    String taskType;
    int activityNo, subActivityNo;
    String docsMainUrl = "images/project1/actMain";
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
        getActivities();
        initRecyclerView();
        initResult();
    }

    private void initResult(){
        if(taskType.equals("main")){
            editTextStartDate.setText(activities.get(0).getStartDate());
            editTextEndDate.setText(activities.get(0).getFinishDate());
            textViewDuration.setText(activities.get(0).getDuration()+" Days");
            if(ActivityUtils.getDelay(activities.get(0)) == 0){
                textViewResult.setText("Completed On Time");
                textViewResult.setTextColor(Color.BLUE);
            }
            else if(ActivityUtils.getDelay(activities.get(0)) < 0){
                textViewResult.setText("Delay of "+ActivityUtils.getDelay(activities.get(0)) * -1+" Days");
                textViewResult.setTextColor(Color.RED);
            }
            else {
                textViewResult.setText("Completed "+ActivityUtils.getDelay(activities.get(0))+" Earlier");
                textViewResult.setTextColor(Color.GREEN);
            }
        }
        else{
            editTextStartDate.setText(activities.get(subActivityNo + 1).getStartDate());
            editTextEndDate.setText(activities.get(subActivityNo + 1).getFinishDate());
            textViewDuration.setText(activities.get(subActivityNo + 1).getDuration()+" Days");
            if(ActivityUtils.getDelay(activities.get(subActivityNo + 1)) == 0){
                textViewResult.setText("Completed On Time");
                textViewResult.setTextColor(Color.BLUE);
            }
            else if(ActivityUtils.getDelay(activities.get(subActivityNo + 1)) <= 0){
                textViewResult.setText("Delay of "+ActivityUtils.getDelay(activities.get(subActivityNo + 1)) * -1+" Days");
                textViewResult.setTextColor(Color.RED);
            }
            else {
                textViewResult.setText("Completed "+ActivityUtils.getDelay(activities.get(subActivityNo + 1))+" Earlier");
                textViewResult.setTextColor(Color.GREEN);
            }
        }
    }

    private void bindComponents(){
        recyclerView = findViewById(R.id.recycler_sub_activity);
        textViewTaskName = findViewById(R.id.textView_activity_name_sub_act);
        editTextEndDate = findViewById(R.id.edit_text_end_date_sub_act);
        editTextStartDate = findViewById(R.id.edit_text_start_date_sub_act);
        textViewResult = findViewById(R.id.edit_text_result_sub_act);
        textViewDuration = findViewById(R.id.edit_text_duration_sub_act);
        buttonSaveActDetails = findViewById(R.id.button_save_act_details);
        textViewAddSubAct = findViewById(R.id.text_view_add_sub_act);
        textViewTaskId  = findViewById(R.id.textView_activity_id_sub_act);
        textViewViewDoc = findViewById(R.id.textView_view_document);
        textViewUploadDoc = findViewById(R.id.textView_upload_document);

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
                        project.getListActivities().get(activityNo).
                                get(subActivityNo + 1).setStartDate(editTextStartDate.getText().toString());
                        project.getListActivities().get(activityNo).
                                get(subActivityNo + 1).setFinishDate(editTextEndDate.getText().toString());
                        storeProjectData();
                    }
                }
            }
        });

        textViewAddSubAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddNewTaskActivity.class);
                intent.putExtra("EXTRA_TASK_TYPE", "sub");
                intent.putExtra("EXTRA_ACT_NO",activityNo);
                intent.putExtra("EXTRA_PROJECT", project);
                startActivity(intent);
            }
        });

        textViewUploadDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddTaskDocumentActivity.class);
                intent.putExtra("EXTRA_DOC_URL", docsMainUrl+activities.get(0).getId());
                startActivity(intent);
            }
        });

        textViewViewDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShowTaskDocumentActivity.class);
                intent.putExtra("EXTRA_DOC_URL", docsMainUrl+activities.get(0).getId());
                startActivity(intent);
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
        findViewById(R.id.textView_upload_document).setVisibility(View.GONE);
        findViewById(R.id.imageView11).setVisibility(View.GONE);
        findViewById(R.id.textView_view_document).setVisibility(View.GONE);
        findViewById(R.id.imageView10).setVisibility(View.GONE);
        findViewById(R.id.view19).setVisibility(View.GONE);
        findViewById(R.id.view18).setVisibility(View.GONE);

    }
    private void initRecyclerView(){
        if(taskType.equals("main")) {
            textViewTaskName.setText(activities.get(0).getTaskName());
            textViewTaskId.setText(activities.get(0).getId());
            for (int i = 1; i < activities.size(); i++) {
                activityAdapter.addActivity(activities.get(i));
            }
        }
        else{
            textViewTaskName.setText(activities.get(subActivityNo + 1).getTaskName());
            textViewTaskId.setText(activities.get(subActivityNo + 1).getId());
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
            if (taskType.equals("main")) {

            } else {
                temp();
                subActivityNo = extras.getInt("EXTRA_ACTIVITY_NO_SUB");
            }
        }
    }

    @Override
    public void onItemClick(View v, int position, com.example.cpm.model.Activity activity) {
        Intent intent = new Intent(getApplicationContext(), ShowSubActivitiesActivity.class);
        intent.putExtra("EXTRA_TASK_TYPE","sub");
        intent.putExtra("EXTRA_ACTIVITY_NO", activityNo);
        intent.putExtra("EXTRA_ACTIVITY_NO_SUB", position);
        intent.putExtra("EXTRA_PROJECT", project);
        intent.putExtra("EXTRA_ACTIVITIES", project.getListActivities().get(activityNo));
        startActivity(intent);
    }
}
