package com.example.troisapp;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddStaff extends AppCompatActivity {
    EditText nama, email, password, confirmPassword;
    Button btnAddStaff;
    Boolean valid = true;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_staff);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        nama = findViewById(R.id.editNama);
        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPassword);
        confirmPassword = findViewById(R.id.editConfirmPassword);
        btnAddStaff = findViewById(R.id.button_staff);




        btnAddStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(AddStaff.this, "Add Data Staff Succes", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getApplicationContext(), Admin.class));
//                finish();

                checkField(nama);
                checkField(email);
                checkField(password);
                checkField(confirmPassword);

                if (valid) {
                    //Add data staff method
                    firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            Toast.makeText(AddStaff.this, "Account Created", Toast.LENGTH_SHORT).show();
                            DocumentReference df = firebaseFirestore.collection("Users").document(firebaseUser.getUid());
                            Map<String, Object> staffInfo = new HashMap<>();
                            staffInfo.put("Nama Staff", nama.getText().toString());
                            staffInfo.put("Email Staff", email.getText().toString());

                            staffInfo.put("isStaff", "2");
                            df.set(staffInfo);

                            startActivity(new Intent(getApplicationContext(), Admin.class));
                            finish();
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