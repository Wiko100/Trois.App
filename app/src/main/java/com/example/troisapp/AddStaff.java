package com.example.troisapp;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddStaff extends AppCompatActivity {
    EditText email, password, confirmPassword;
    Button btnAddStaff;
    Boolean valid = true;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    public static final String TAG = "AddStaff";
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword);
        confirmPassword = findViewById(R.id.editConfirmPassword);
        btnAddStaff = findViewById(R.id.button_staff);




        btnAddStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                checkField(email);
                checkField(password);
                checkField(confirmPassword);

                if (valid) {
                    //Add data staff method
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            Toast.makeText(AddStaff.this, "Add Staff Created", Toast.LENGTH_SHORT).show();
                            Log.i(TAG, "sign in success " + email.getText().toString()+" uid:"+authResult.getUser().getUid());
                            Toast.makeText(AddStaff.this, "Add Staff succes", Toast.LENGTH_SHORT).show();
                            checkUserLevel(authResult.getUser().getUid());


                            startActivity(new Intent(getApplicationContext(), Admin.class));
                            finish();
                        }

                        private void checkUserLevel(String uid) {
                            mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                            mDatabase.child(uid).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Users users = snapshot.getValue(Users.class);
                                    Log.i(TAG, "Get collection users success " + users.getEmail()+" role:"+users.getRole());
                                    if ("staff".equals(users.getRole())) {
                                        startActivity(new Intent(getApplicationContext(), Admin.class));
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddStaff.this, "Failed to Add Data Staff", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }

    public boolean checkField (EditText textField) {
        if(textField.getText().toString().isEmpty()) {
            textField.setError("Error");
            valid = false;
        } else {
            valid = true;
        }
        return valid;
    }
}