package com.example.cpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
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

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;

public class ProjectDashBoardActivity extends AppCompatActivity implements SelectListener{

    PieChart pieChart;

    RecyclerView recyclerView;
    private ActivityAdapter activityAdapter;
    TextView textViewProjectTitle, textViewStartDate, textViewEndDate;
    private ArrayList<ArrayList<Activity>> subActivities = new ArrayList<ArrayList<Activity>>();
    private Project project;
    TextView textViewAddMainActivity,textViewCompletedOnTime, textViewCompletedWithDelay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_dash_board);

        getProjectData();
        bindComponents();
        activityAdapter = new ActivityAdapter(this, this);
        recyclerView.setAdapter(activityAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        initRecyclerView();
        setProjectDetails();
    }

    private void setProjectDetails(){
        textViewProjectTitle.setText(project.getProjectName());
        textViewStartDate.setText(project.getStartDate());
        textViewEndDate.setText(project.getExpectedEndDate());

        setPieChart();
    }

    private void setPieChart(){
        int outOff = project.getListActivities().size();
        int completedWithDelay = getCountOfCompletedWithDelay();
        int completedWithoutDelay = getCountOfCompletedWitOuthDelay();
        pieChart.addPieSlice(
                new PieModel(
                        "Completed Without Delay",
                         completedWithoutDelay * outOff,
                        Color.parseColor("#FF00FF0A")));
        textViewCompletedOnTime.setText(textViewCompletedOnTime.getText() + " (" + completedWithoutDelay + "/" + outOff + ")");

        pieChart.addPieSlice(
                new PieModel(
                        "Completed With Delay",
                        completedWithDelay * outOff,
                        Color.parseColor("#FFFF0000")));
        textViewCompletedWithDelay.setText(textViewCompletedWithDelay.getText() + " (" + completedWithDelay + "/" + outOff + ")");

        pieChart.startAnimation();
    }

    private int getCountOfCompletedWithDelay(){
        int count = 0;
        for(int i=0; i<project.getListActivities().size(); i++){
            if(project.getListActivities().get(i).get(0).getDelay() < 0)
                count++;
        }
        return count;
    }

    private int getCountOfCompletedWitOuthDelay(){
        int count = 0;
        for(int i=0; i<project.getListActivities().size(); i++){
            if(project.getListActivities().get(i).get(0).getDelay() > 0)
                count++;
        }
        return count;
    }

    private void bindComponents(){
        textViewProjectTitle = findViewById(R.id.textView_project_dash_titile);
        textViewStartDate = findViewById(R.id.text_view_dash_start_date);
        textViewEndDate = findViewById(R.id.text_view_dash_end_date);
        textViewCompletedOnTime = findViewById(R.id.textViewCompletedOnTime);
        textViewCompletedWithDelay = findViewById(R.id.textViewCompletedWithDelay);
        pieChart = findViewById(R.id.pieChartDashBoard);

        recyclerView = findViewById(R.id.recycler_main_activity);
        textViewAddMainActivity = findViewById(R.id.text_view_add_main_act);
        textViewAddMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddNewTaskActivity.class);
                intent.putExtra("EXTRA_TASK_TYPE", "main");
                intent.putExtra("EXTRA_PROJECT", project);
                startActivity(intent);
            }
        });
    }
    private void getProjectData(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            project = (Project) extras.getSerializable("PROJECT_DATA");
        }
    }

    private void initRecyclerView(){
        for(int i=0; i<project.getListActivities().size(); i++){
            activityAdapter.addActivity(project.getListActivities().get(i).get(0));
        }
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
                Toast.makeText(ProjectDashBoardActivity.this, "Stored Data", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProjectDashBoardActivity.this, "Failed to Store", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getProject(){
       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Project1");
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
        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), ShowSubActivitiesActivity.class);
        intent.putExtra("EXTRA_ACTIVITIES", project.getListActivities().get(position));
        intent.putExtra("EXTRA_TASK_TYPE", "main");
        intent.putExtra("EXTRA_ACTIVITY_NO", position);
        intent.putExtra("EXTRA_PROJECT", project);
        startActivity(intent);

    }
}