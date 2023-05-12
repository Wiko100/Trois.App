package com.example.troisapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class AddStock extends AppCompatActivity {
    EditText nama, item, tipe, jumlah;
    ImageView imageViewProduct;
    Uri imageUri;
    Button btnAddStock;
    Boolean valid = true;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;public static final String TAG = "AddStock";
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        imageViewProduct = findViewById(R.id.imageview_product);
        nama = findViewById(R.id.editNameItem);
        item = findViewById(R.id.editItemId);
        tipe = findViewById(R.id.editTypeItem);
        jumlah = findViewById(R.id.editQuantity);
        btnAddStock = findViewById(R.id.button_stock);


        btnAddStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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
                            Log.i(TAG, "sign in success " + nama.getText().toString() + " uid:" + authResult.getUser().getUid());
                            Toast.makeText(AddStock.this, "Add Stock succes", Toast.LENGTH_SHORT).show();
                            checkUserLevel(authResult.getUser().getUid());

                            startActivity(new Intent(getApplicationContext(), ProductList.class));
                            finish();
                        }

                        private void checkUserLevel(String uid) {
                            mDatabase = FirebaseDatabase.getInstance().getReference("Product");
                            mDatabase.child(uid).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    GetSetProduct product = snapshot.getValue(GetSetProduct.class);
                                    Log.i(TAG, "Get collection product success " + product.getProduct() + " role:" + product.getRole());
                                    if ("stock".equals(product.getRole())) {
                                        startActivity(new Intent(getApplicationContext(), ProductList.class));
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
                            Toast.makeText(AddStock.this, "Failed to Add Data Stock", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void addPicture(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200 && resultCode == RESULT_OK){
            imageUri = data.getData();
            Glide.with(AddStock.this)
                    .load(imageUri)
                    .placeholder(R.drawable.add_stok)
                    .error(R.drawable.add_stok)
                    .into(imageViewProduct);

            addToStorage(imageUri);
        }
    }

    private void addToStorage(Uri imageUri) {
//        Stora
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