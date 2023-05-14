package com.example.cpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cpm.model.Project;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

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
    private void getProjectData(){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Project1");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                project1 = snapshot.getValue(Project.class);

//                Log.d("TAG", "onDataChange: "+project1.toString());
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