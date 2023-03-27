package com.example.cpm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cpm.R;
import com.example.cpm.model.UserEngg;

import java.io.Serializable;

public class SignUpActivity extends AppCompatActivity {

    EditText editTextMail, editTextContact, editTextPassword, editTextConfirmPassword, editTextName;
    Spinner spinnerAuthority;
    Button buttonContinue;
    RadioButton radioButtonMale, radioButtonFemale;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        bindComponents();

    }

    private void bindComponents(){
        editTextName = findViewById(R.id.edit_text_name_sign_up);
        editTextMail = findViewById(R.id.edit_text_email_sign_up);
        editTextContact = findViewById(R.id.edit_text_contact_sign_up);
        editTextPassword = findViewById(R.id.edit_text_pass_sign_up);
        editTextConfirmPassword = findViewById(R.id.edit_text_conf_pass_sign_up);
        spinnerAuthority = findViewById(R.id.spinner_authority_sign_up);
        radioButtonFemale = findViewById(R.id.radio_female_sign_up);
        radioButtonMale = findViewById(R.id.radio_male_sign_up);
        buttonContinue = findViewById(R.id.button_continue_sign_up);

        this.radioButtonFemale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(radioButtonMale.isChecked()) {
                    radioButtonMale.setChecked(false);
                    radioButtonFemale.setSelected(true);
                }
            }
        });

        this.radioButtonMale.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (radioButtonFemale.isChecked()){
                    radioButtonFemale.setChecked(false);
                    radioButtonMale.setSelected(true);
                }
            }
        });

        this.buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidInput()){
                    String gender = "Male";
                    if(radioButtonFemale.isChecked())
                        gender = "Female";

                    UserEngg userEngg = new UserEngg(
                            spinnerAuthority.getSelectedItem().toString(),
                            editTextName.getText().toString(),
                            editTextMail.getText().toString(),
                            editTextContact.getText().toString(),
                            gender
                            );

                    Intent intent = new Intent(getApplicationContext(), ValidateUserCodeActivity.class);
                    intent.putExtra("EXTRA_USER_DETAILS", (Serializable) userEngg);
                    intent.putExtra("EXTRA_PASS", editTextPassword.getText().toString());
                    startActivity(intent);
                }
            }
        });

        initSpinner();
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

    public boolean isValidInput(){
        boolean validInput = true;

        if(!radioButtonMale.isChecked() && !radioButtonFemale.isChecked()){
            Toast.makeText(this, "Select Gender", Toast.LENGTH_SHORT).show();
            validInput = false;
        }

        if(editTextName.getText().toString().isEmpty()){
            editTextName.setError("This Field Cannot be Empty!");
            editTextName.requestFocus();
            validInput = false;
        }
        if(editTextContact.getText().toString().isEmpty()){
            editTextContact.setError("This Field Cannot be Empty!");
            editTextContact.requestFocus();
            validInput = false;
        }
        if(editTextPassword.getText().toString().isEmpty()){
            editTextPassword.setError("This Field Cannot be Empty!");
            editTextPassword.requestFocus();
            validInput = false;
        }
        if(editTextConfirmPassword.getText().toString().isEmpty()){
            editTextConfirmPassword.setError("This Field Cannot be Empty!");
            editTextConfirmPassword.requestFocus();
            validInput = false;
        }
        if(editTextMail.getText().toString().isEmpty()){
            editTextMail.setError("This Field Cannot be Empty!");
            editTextMail.requestFocus();
            validInput = false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(editTextMail.getText().toString()).matches()){
            editTextMail.setError("Please Enter Valid Email ID ");
            editTextMail.requestFocus();
            validInput = false;
        }
        if(!Patterns.PHONE.matcher(editTextContact.getText().toString()).matches()){
            editTextContact.setError("Please Enter Valid Contact No ");
            editTextContact.requestFocus();
            validInput = false;
        }
        if(editTextPassword.getText().toString().length() < 6){
            editTextPassword.setError("Password length must be greater than 6");
            editTextPassword.requestFocus();
            validInput = false;
        }
        if(!editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString())){
            editTextConfirmPassword.setError("Please Re Confirm Password ");
            editTextConfirmPassword.requestFocus();
            validInput = false;
        }
        return validInput;

    }
}