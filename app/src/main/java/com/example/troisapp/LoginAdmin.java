package com.example.troisapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginAdmin extends AppCompatActivity {
    EditText emailAdmin, passwordAdmin;
    Button btnAdmin;
    Boolean valid = true;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    public static final String TAG = "LoginAdmin";
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        emailAdmin = findViewById(R.id.editEmail);
        passwordAdmin = findViewById(R.id.editPassword);
        btnAdmin = findViewById(R.id.button_admin);

        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(emailAdmin);
                checkField(passwordAdmin);

                if (valid) {
                    firebaseAuth.signInWithEmailAndPassword(emailAdmin.getText().toString(), passwordAdmin.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Log.i(TAG, "sign in success " + emailAdmin.getText().toString()+" uid:"+authResult.getUser().getUid());
                            Toast.makeText(LoginAdmin.this, "Login as Admin succes", Toast.LENGTH_SHORT).show();
                            checkUserLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginAdmin.this, "Login Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }

    private void checkUserLevel(String uid) {
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                Log.i(TAG, "Get collection users success " + users.getEmail()+" role:"+users.getRole());
                if ("admin".equals(users.getRole())) {
                    startActivity(new Intent(getApplicationContext(), Admin.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//
//        DocumentReference df = firebaseFirestore.collection("Users").document(uid);
//        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Log.i(TAG, "Get collection users success " + documentSnapshot.getString("email")+" role:"+documentSnapshot.getString("role"));
//                if ("admin".equals(documentSnapshot.getString("role"))) {
//                    startActivity(new Intent(getApplicationContext(), Admin.class));
//                }
//
//
//            }
//        });
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