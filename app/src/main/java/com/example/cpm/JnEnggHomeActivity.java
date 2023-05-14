package com.example.cpm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class JnEnggHomeActivity extends AppCompatActivity {

    TextView textViewProject1, textViewProject2, textViewProject3, textViewAddProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jn_engg_home);

        bindComponents();

    }

    private void bindComponents() {
        textViewProject1 = findViewById(R.id.textViewProject1);
        textViewProject2 = findViewById(R.id.textViewProject2);
        textViewProject3 = findViewById(R.id.textViewProject3);
        textViewAddProject = findViewById(R.id.textViewAddProject);




    }
}