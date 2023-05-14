package com.example.cpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cpm.model.Activity;
import com.example.cpm.model.Project;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;
import org.eazegraph.lib.charts.PieChart;

import java.util.ArrayList;

public class ProjectDashBoardActivity extends AppCompatActivity implements SelectListener{

    PieChart pieChart;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Project project;
    private ArrayList<ArrayList<Activity>> subActivities = new ArrayList<ArrayList<Activity>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_dash_board);

        //storeProject();
        //getProject();
    }
    private void storeProject(){
        ArrayList<ArrayList<Activity>> list = new ArrayList<>();
        list.add(new ArrayList());

        list.get(0).add(new Activity("id", "task2", "duration", "startDate", "endDate"));
        Project project = new Project("prName4", "prSt", "prEnd", 10,2,4,5,list);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Project1");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(project);
                Toast.makeText(ProjectDashBoardActivity.this, "Stored Data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProjectDashBoardActivity.this, "Failed to Store", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProject(){
       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Project1");
       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               Project project = snapshot.getValue(Project.class);
               Log.d("TAG", "onDataChange: "+project.toString());
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(ProjectDashBoardActivity.this, "Failed", Toast.LENGTH_SHORT).show();
           }
       });
    }

    @Override
    public void onItemClick(View v, int position, Activity activity) {
        Toast.makeText(this, ""+subActivities.get(position).size(), Toast.LENGTH_SHORT).show();
    }
}