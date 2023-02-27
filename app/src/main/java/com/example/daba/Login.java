package com.example.daba;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApiNotAvailableException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.DatabaseMetaData;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Student");

    // Checking password pattern
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^[+]{1}(?:[0-9\\-\\(\\)\\/\\.]\\s?){6,15}[0-9]{1}$");

    final EditText phone  = findViewById(R.id.phone);
    final EditText password  = findViewById(R.id.password);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Button login = findViewById(R.id.loginn);
        final TextView signin = findViewById(R.id.signupp);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Storing variables in string format
                String loginPhone = phone.getText().toString();
                String loginPassword = password.getText().toString();

                validatePassword();
                validatePhone();

                if(loginPhone.isEmpty() || loginPassword.isEmpty()){
                    Toast.makeText(Login.this, "Please Enter Your Phone and Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    reference.child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // Check if phone is already present in the firebase
                            if(snapshot.hasChild(loginPhone)){

                                // if true Phone is registered
                                // now take the password of user from firebase and match it with user entered password
                                final String passwordInFirebase = snapshot.child(loginPhone).child("password").getValue(String.class);

                                if(passwordInFirebase.matches(loginPassword)){
                                    Toast.makeText(Login.this, "User Login Sucessfull", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this, MainPage.class));
                                }
                                else{
                                    Toast.makeText(Login.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                                Toast.makeText(Login.this, "Wrogn Password", Toast.LENGTH_SHORT).show();
                            } // close of if
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Login.this, Register.class));
            }
        });

    }

    public boolean validatePassword(){
        String  pswInput = password.getText().toString();
        if(pswInput.isEmpty()){
            password.setError("Password can't be empty");
            return  false;
        }
        else if(!PASSWORD_PATTERN.matcher(pswInput).matches()){
            password.setError("Password too weak");
            return false;
        }
        else{
            password.setError(null);
            return true;
        }
    }

    public boolean validatePhone(){
        String phoneInput = phone.getText().toString();
        if(phoneInput.isEmpty()){
            phone.setError("Phone is Empty");
            return false;
        }
        else if(!PHONE_PATTERN.matcher(phoneInput).matches()){
            phone.setError("Invalid Phone Number");
            return false;
        }
        else{
            phone.setError(null);
            return true;
        }
    }
}