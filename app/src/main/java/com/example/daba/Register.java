package com.example.daba;
import static com.example.daba.R.id.loginpage;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;


public class Register extends AppCompatActivity {

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


    // Create object of DatabaseReference Class to access firebase Realtime Database
    DatabaseReference reference;
    FirebaseDatabase rootNode;

    // Variables
    EditText email, phoneNO, password,confirmPassword;
    Button register;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.email);
        phoneNO = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmpassword);
        register = findViewById(R.id.register);
        login = findViewById(loginpage);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // get the data from edit text to String Variables
                 String emailtxt = email.getText().toString();
                 String passwordtxt = password.getText().toString();
                 String confirmPasswordd = confirmPassword.getText().toString();
                 String phoneno = phoneNO.getText().toString();

                // Check if users filled all the fields
                validateEmail();
                validatePassword();
                validatePhone();

//                if(emailtxt.isEmpty() || passwordtxt.isEmpty() || confirmPasswordd.isEmpty() || phoneno.isEmpty()){
//                    Toast.makeText(Register.this, "Please Fill all the Fields", Toast.LENGTH_SHORT).show();
//
//                }
//                // Check if password is matching with confirm password{
//                else if(!passwordtxt.equals(confirmPasswordd)){
//                    Toast.makeText(Register.this, "Password are not Matching", Toast.LENGTH_SHORT).show();
//                }

                if(validatePhone()&&validateEmail()&&validatePassword()){

                // Sending data to firebase Realtime Database

                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("Student");

                    Toast.makeText(Register.this, "Sucessfull", Toast.LENGTH_SHORT).show();

                    // Set Intent to pass extras
                    Intent intent  = new Intent(Register.this,Next.class);
                    intent.putExtra("phone", phoneno);
                    intent.putExtra("email", emailtxt);
                    intent.putExtra("password", passwordtxt);

                    //Send to next page
                    startActivity(intent);

                    reference.child("Student").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // check if phone number is not registered before
                            if(snapshot.hasChild(phoneno)){
                                Toast.makeText(Register.this, "Phone number already Registered", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(Register.this, "Sucessfull", Toast.LENGTH_SHORT).show();

                                // Set Intent to pass extras
                                Intent intent  = new Intent(Register.this,Next.class);
                                intent.putExtra("phone", phoneno);
                                intent.putExtra("email", emailtxt);
                                intent.putExtra("password", passwordtxt);

                                //Send to next page
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



//                    Users one = new Users( emailtxt,  passwordtxt, phoneno);
//                    reference.child(phoneno).setValue(one);
                    //reference.setValue(one);
                    //reference.push().setValue(one);


                }

            }
        });


        // Sending to login page when clicked on login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Register.this, Login.class));

            }
        });

    }  //onClick() method closed

//
    public boolean validateEmail(){
            String emailInput = email.getText().toString();
            if(emailInput.isEmpty()){
                email.setError("Email Cannot be empty");
                return false;
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()){
                email.setError("Please enter a valid email address.");
                return false;
            }
            else{
                email.setError(null);
                return true;
            }
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
        String phoneInput = phoneNO.getText().toString();
        if(phoneInput.isEmpty()){
            phoneNO.setError("Phone is Empty");
            return false;
        }
        else if(!PHONE_PATTERN.matcher(phoneInput).matches()){
            phoneNO.setError("Invalid Phone Number");
            return false;
        }
        else{
            phoneNO.setError(null);
            return true;
        }
    }
}