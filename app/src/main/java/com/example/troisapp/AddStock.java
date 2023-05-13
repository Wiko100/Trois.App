package com.example.troisapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import io.grpc.Context;

public class AddStock extends AppCompatActivity{
    private static final int STORAGE_PERMISSION_CODE = 101;
    EditText prodName, prodDescription, prodPrice;
    Spinner prodCategory;
    ImageView imageViewProduct;
    Uri imageUri;
    Button btnAddStock,btnaddimage;
    Boolean valid = true;

    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        imageViewProduct = findViewById(R.id.imageview_product);
        prodName = findViewById(R.id.editNameItem);
        prodCategory = findViewById(R.id.spinner_category);
        prodDescription = findViewById(R.id.editDescription);
        prodPrice = findViewById(R.id.editPrice);
        btnAddStock = findViewById(R.id.button_stock);
        btnaddimage = findViewById(R.id.button_add_image);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prodCategory.setAdapter(adapter);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        btnaddimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //permission isn't working, so I force this app to have access to files with or without permission
//                if(checkPermission()){
//                    addPicture();
//                }else{
//                    requestPermission();
//                }
                addPicture();
            }
        });



        btnAddStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String category;

                checkField(prodName);
                checkField(prodDescription);
                checkField(prodPrice);

                if(prodCategory.getSelectedItem().toString().equals("Clothing")){
                    category = "Produk/Clothing";
                }else if(prodCategory.getSelectedItem().toString().equals("Elektronik")){
                    category = "Produk/Elektronik";
                }else if(prodCategory.getSelectedItem().toString().equals("Book")){
                    category = "Produk/Book";
                }else if(prodCategory.getSelectedItem().toString().equals("Makeup")){
                    category = "Produk/Makeup";
                }else{
                    category = "empty";
                }

                Toast.makeText(AddStock.this, category, Toast.LENGTH_SHORT).show();

                final StorageReference reference = firebaseStorage.getReference().child(category)
                        .child(System.currentTimeMillis()+"");

                reference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                GetSetProduct model = new GetSetProduct();
                                model.setPicture(uri.toString());
                                model.setProduct(prodName.getText().toString());
                                model.setDescription(prodDescription.getText().toString());
                                model.setPrice(prodPrice.getText().toString());

                                database.getReference().child(category).push().setValue(model)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(AddStock.this, "Product Upload Success", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(AddStock.this, Category.class);
                                                startActivity(intent);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(AddStock.this, "Product Upload Failed", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
                    }
                });
            }
        });
    }

    public void addPicture(){
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, 101);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intent, 101);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();

    }

    //this is for check and reuqest for permission, but unfinished
//    private void requestPermission() {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
//            try{
//                Intent intent = new Intent();
//                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
//            }catch(Exception e){
//                Intent intent = new Intent();
//                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
//            }
//        }else{
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
//        }
//    }
//
//    public boolean checkPermission(){
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
//            return Environment.isExternalStorageManager();
//        }else{
//            int read = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
//
//            return read == PackageManager.PERMISSION_GRANTED;
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101 && resultCode == RESULT_OK){
            imageUri = data.getData();
            imageViewProduct.setImageURI(imageUri);
        }
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