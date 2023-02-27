package com.example.daba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Next extends AppCompatActivity {

//     Referencing to Firebase
    FirebaseDatabase rootNode;
    DatabaseReference reference;

    //Import users object
    Users usersObj;

    // variables


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        // Storing to firebase
        EditText location = findViewById(R.id.location);
        EditText postal = findViewById(R.id.postalcode);
        EditText landMark = findViewById(R.id.landmark);
        EditText district = findViewById(R.id.district);


        // 1. Send back to Register page after clicking back button
        TextView id  = findViewById(R.id.back);
        id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Next.this, Register.class));
            }
        });


        // 2. Storing additional details on Signup
        Button signup = findViewById(R.id.Signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("Student");

                String loc = location.getText().toString();
                String pos = postal.getText().toString();
                String lan = landMark.getText().toString();
                String dist = district.getText().toString();

//                String phn = usersObj.phone.toString();
//                System.out.println("The value of phn is : ; " + phn);

                // Get Intent
                String phonee = getIntent().getStringExtra("phone");
                String email = getIntent().getStringExtra("email");
                String password = getIntent().getStringExtra("password");

                if(loc.isEmpty() || pos.isEmpty() || lan.isEmpty() || dist.isEmpty()){
                    Toast.makeText(Next.this, "Fill all the Fields", Toast.LENGTH_SHORT).show();
                }
                else{

                    Users obj = new Users(email, phonee, password, loc, pos, lan, dist);
                    reference.child(phonee).setValue(obj);
                    Toast.makeText(Next.this, "User Details Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Next.this, Login.class));
                }
            }
        });

    }

//    void sendDataToFirebase(View v){
//        rootNode = FirebaseDatabase.getInstance();
//        reference = rootNode.getReference("Student");
//
//        String loc = location.getText().toString();
//        String pos = postal.getText().toString();
//        String lan = landMark.getText().toString();
//        String phn = phone.getText().toString();
//
//        if(loc.isEmpty() || pos.isEmpty() || lan.isEmpty() || phn.isEmpty()){
//            Toast.makeText(Next.this, "Fill all the Fields", Toast.LENGTH_SHORT).show();
//        }
//        else{
//
//            Details obj = new Details(loc, pos, lan, phn);
//
//            reference.child(usersObj.phone).setValue(obj);
//            Toast.makeText(Next.this, "User Details Updated", Toast.LENGTH_SHORT).show();
//        }
//    }
}