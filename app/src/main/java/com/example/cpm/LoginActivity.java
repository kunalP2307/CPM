package com.example.cpm;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cpm.R;
import com.example.cpm.model.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    Spinner spinnerAuthority;
    EditText editTextEmail, editTextPassword;
    TextView textViewForgotPassword, textViewSignUp;
    Button buttonLogin;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        checkIfLoggedIn();
        bindComponents();
        initSpinner();
    }

    private void checkIfLoggedIn() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent i = new Intent(LoginActivity.this, JnEnggHomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }
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
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String authority = spinnerAuthority.getSelectedItem().toString();
                if(isValidCreds()){
                    loginUserWithEmail(
                        new Login(email, password, authority)
                    );
                }
            }
        });

        textViewForgotPassword = findViewById(R.id.text_view_forgot_password);
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        textViewSignUp = findViewById(R.id.text_view_sign_up);
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });
    }

    public void loginUserWithEmail(Login login){
        String email = login.getEmail();
        String password = login.getPassword();
        String authority = login.getAuthority();

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(), JnEnggHomeActivity.class));
                }
                else{
                    Toast.makeText(LoginActivity.this, "Invalid Credentials Please Try Again.!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public boolean isValidCreds(){
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (!(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
            editTextEmail.setError("Please Enter Valid Email");
            editTextEmail.requestFocus();
            return false;
        }
        if (password.length() < 8) {
            editTextPassword.setError("Password must contain 8 characters");
            editTextPassword.requestFocus();
            return false;
        }

        return true;
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