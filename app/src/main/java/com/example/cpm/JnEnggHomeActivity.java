package com.example.cpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cpm.model.Activity;
import com.example.cpm.model.Project;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class JnEnggHomeActivity extends AppCompatActivity {

    TextView textViewProject1, textViewProject2, textViewProject3, textViewAddProject;
    Project project1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jn_engg_home);

        getProjectData();
        bindComponents();
    }
    private void storeProject(){
        ArrayList<ArrayList<Activity>> list = new ArrayList<>();
        list.add(new ArrayList());
        list.get(0).add(new Activity("1", "General Layout", "15", "07-09-22", "27-09-22"));
        list.get(0).add(new Activity("1a", "Submission of Layout from Contractor to", "5", "07-09-22", "13-09-22"));


        list.add(new ArrayList());
        list.get(1).add(new Activity("2", "Alignment Sanction", "5", "07-09-22", "27-09-22"));
        list.get(1).add(new Activity("2a", "Submission of proposal from Sub division to division", "2", "28-09-22", "29-09-22"));

        list.add(new ArrayList());
        list.get(2).add(new Activity("3", "Detailed Design", "20", "28-09-22", "04-10-22"));
        list.get(2).add(new Activity("3a", "Submission of proposal from Sub division to division", "10", "03-10-22", "14-10-22"));

        list.add(new ArrayList());
        list.get(3).add(new Activity("4", "Control Blasting Proposal", "8", "03-10-22", "28-10-22"));
        list.get(3).add(new Activity("4a", "Submission of proposal from Sub division to division", "1", "05-10-22", "05-10-22"));

        list.add(new ArrayList());
        list.get(4).add(new Activity("5", "Estimate", "32", "05-10-22", "14-10-22"));
        list.get(4).add(new Activity("5a", "Lead Approval", "2", "05-10-22", "06-10-22"));

        list.add(new ArrayList());
        list.get(5).add(new Activity("6", "Break up Proposal", "6", "18-11-22", "25-11-22"));
        list.get(5).add(new Activity("6a", "Submission of proposal from Sub division to Division", "1", "18-11-22", "18-11-22"));


        Project project = new Project("PDN Design,Estimate,DTP and Tender", "06-09-22", "01-03-23", 127,2,4,5,list);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Project1");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(project);
                Toast.makeText(getApplicationContext(), "Stored Data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to Store", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getProjectData(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Project1");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                project1 = snapshot.getValue(Project.class);
                textViewProject1.setText("\u2022 "+project1.getProjectName());
                Log.d("TAG", "onDataChange: "+project1.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(JnEnggHomeActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void bindComponents() {
        textViewProject1 = findViewById(R.id.textViewProject1);
        textViewProject1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(), ProjectDashBoardActivity.class);

                if(project1 != null) {
                    project1.setDelay();


                    intent.putExtra("PROJECT_DATA", (Serializable) project1);

                    Log.d("", "onClick: "+project1);
                    startActivity(intent);
                }
                else
                    Toast.makeText(JnEnggHomeActivity.this, "Please Wait for a While!!", Toast.LENGTH_SHORT).show();

            }
        });
        textViewProject2 = findViewById(R.id.textViewProject2);
        textViewProject3 = findViewById(R.id.textViewProject3);
        textViewAddProject = findViewById(R.id.textViewAddProject);

    }
}