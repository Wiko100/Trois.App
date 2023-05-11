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

public class AddStock extends AppCompatActivity {
    EditText nama, item, tipe, jumlah;
    Button btnAddStock;
    Boolean valid = true;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        nama = findViewById(R.id.editNameItem);
        item = findViewById(R.id.editItemId);
        tipe = findViewById(R.id.editTypeItem);
        jumlah = findViewById(R.id.editQuantity);
        btnAddStock = findViewById(R.id.button_stock);




        btnAddStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(AddStaff.this, "Add Data Stock Succes", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getApplicationContext(), Admin.class));
//                finish();

                checkField(nama);
                checkField(item);
                checkField(tipe);
                checkField(jumlah);

                if (valid) {
                    //Add data staff method
                    firebaseAuth.createUserWithEmailAndPassword(nama.getText().toString(), item.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            Toast.makeText(AddStock.this, "Account Created", Toast.LENGTH_SHORT).show();
                            DocumentReference df = firebaseFirestore.collection("Users").document(firebaseUser.getUid());
                            Map<String, Object> staffInfo = new HashMap<>();
                            staffInfo.put("Nama Stock", nama.getText().toString());
                            staffInfo.put("Item Stock", item.getText().toString());

                            staffInfo.put("isStock", "2");
                            df.set(staffInfo);

                            startActivity(new Intent(getApplicationContext(), Admin.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddStock.this, "Failed to Add Data Stock", Toast.LENGTH_SHORT).show();
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