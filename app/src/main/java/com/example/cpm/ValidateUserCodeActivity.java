package com.example.cpm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cpm.model.UserEngg;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class ValidateUserCodeActivity extends AppCompatActivity {

    EditText editTextUserCode;
    Button buttonSignUp;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_user_code);

        bindComponents();
    }

    private void bindComponents(){
        editTextUserCode = findViewById(R.id.edit_text_user_code);
        buttonSignUp = findViewById(R.id.button_sign_up);
        progressBar = findViewById(R.id.progress_bar_user_code);

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle extras = getIntent().getExtras();
                UserEngg userEngg = null;
                if (extras != null) {
                    userEngg = (UserEngg) extras.getSerializable("EXTRA_USER_DETAILS");
                    String password = extras.getString("EXTRA_PASS");
                    registerUser(userEngg, password);
                }
                else{
                    Toast.makeText(ValidateUserCodeActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser(UserEngg userEngg,String password){

        progressBar.setVisibility(View.VISIBLE);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String email = userEngg.getEmail();
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(userEngg.getEmail().replace(".", ""))
                                    .setValue(userEngg)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(ValidateUserCodeActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                            else{
                                                Toast.makeText(ValidateUserCodeActivity.this, "Failed to Register Try Again!", Toast.LENGTH_SHORT).show();
                                                Log.d("", "onComplete:inside ");
                                            }
                                        }
                                    });
                        }
                        else{
                            Toast.makeText(ValidateUserCodeActivity.this, "Failed to Register User try Again", Toast.LENGTH_SHORT).show();
                            Log.d("", "onComplete:outside ");
                        }
                    }
                });
    }
}