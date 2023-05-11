package com.example.troisapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Staff extends AppCompatActivity {
    EditText emailStaff, passwordStaff;
    Button btnStaff;
    Boolean valid = true;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        emailStaff = findViewById(R.id.et_Email);
        passwordStaff = findViewById(R.id.et_Password);
        btnStaff = findViewById(R.id.button_staff);

        btnStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(emailStaff);
                checkField(passwordStaff);

                if (valid) {
                    firebaseAuth.signInWithEmailAndPassword(emailStaff.getText().toString(), passwordStaff.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(Staff.this, "Login as Staff succes", Toast.LENGTH_SHORT).show();
                            checkUserLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Staff.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });


    }

    private void checkUserLevel(String uid) {
        DocumentReference df = firebaseFirestore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSucces" + documentSnapshot.getData());

                if (documentSnapshot.getString("isStaff") != null) {
                    startActivity(new Intent(getApplicationContext(), InterfaceStaff.class));
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