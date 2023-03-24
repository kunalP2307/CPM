package com.example.cpm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    Spinner spinnerAuthority;
    EditText editTextEmail, editTextPassword;
    TextView textViewForgotPassword;
    Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        bindComponents();
        initSpinner();
    }

    public void bindComponents() {

        spinnerAuthority = findViewById(R.id.spinner_authority_login);
        spinnerAuthority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        editTextEmail = findViewById(R.id.edit_text_email_login);
        editTextPassword = findViewById(R.id.edit_text_pass_login);
        buttonLogin = findViewById(R.id.button_login);
        textViewForgotPassword = findViewById(R.id.text_view_forgot_password);
    }

    public void initSpinner(){
        ArrayAdapter arrayAdapter = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                Authorities.ENGINEERS
        );

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAuthority.setAdapter(arrayAdapter);
    }
}