package com.example.cpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cpm.model.Activity;
import com.example.cpm.model.Project;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class AddNewTaskActivity extends AppCompatActivity {

    String taskType;
    Project project;
    EditText editTextTaskId, editTextTitle, editTextStartDate, editTextEndDate, editTextDuration;
    Button buttonAddTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);

        getTaskType();
        bindComponents();
    }

    private void bindComponents() {
        editTextTaskId = findViewById(R.id.edit_text_add_act_id);
        editTextTitle = findViewById(R.id.edit_text_add_act_title);
        editTextDuration = findViewById(R.id.edit_text_add_act_duration);
        editTextStartDate = findViewById(R.id.edit_text_add_act_start_date);
        editTextEndDate = findViewById(R.id.edit_text_add_act_end_date);
        buttonAddTask = findViewById(R.id.button_add_task);

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(taskType.equals("main")){
                    ArrayList<Activity> list = new ArrayList<>();
                    list.add(getActivityModel());
                    project.getListActivities().add(list);
                    storeProject();
                }
                else{

                }
            }
        });
    }

    private void storeProject(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Project1");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(project);
                Toast.makeText(getApplicationContext(), "Stored Data", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SuccessActivity.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to Store", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Activity getActivityModel(){
        String id, title, duration, startDate, endDate;
        id = editTextTaskId.getText().toString();
        title = editTextTitle.getText().toString();
        duration = editTextDuration.getText().toString();
        startDate = editTextStartDate.getText().toString();
        endDate = editTextEndDate.getText().toString();

        return new Activity(id, title, duration, startDate, endDate);
    }

    private void getTaskType(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            taskType = extras.getString("EXTRA_TASK_TYPE");
            project = (Project) extras.getSerializable("EXTRA_PROJECT");
        }

    }
}