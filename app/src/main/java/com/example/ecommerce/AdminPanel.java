package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminPanel extends AppCompatActivity {
    String ProductName, Product_name, Product_Details, Product_Price, currentDate, currentTime, ProductKey, DownloadImgLink;
    Button new_product;
    EditText Product_price, Product_Name, Product_details;
    ImageButton select_image1;
    StorageReference ImageRef;
    DatabaseReference Productref;
    ProgressDialog loading;
    Uri link;
     private static final int pic = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        new_product = (Button) findViewById(R.id.new_product);
        Product_Name = (EditText) findViewById(R.id.product_name);
        Product_price = (EditText) findViewById(R.id.product_detail);
        Product_details = (EditText) findViewById(R.id.product_price);
        loading = new ProgressDialog(this);
        select_image1 = (ImageButton) findViewById(R.id.select_image1);

        ProductName = getIntent().getExtras().get("Products").toString();
        ImageRef = FirebaseStorage.getInstance().getReference().child("Product Image");
        Productref = FirebaseDatabase.getInstance().getReference().child("Products");


            select_image1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Openfile();
                    Toast.makeText(AdminPanel.this, "Ohooooo!!!!!!", Toast.LENGTH_SHORT).show();
                }
            });

    new_product.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CheckDetails();
        }
    });

    }

    private void Openfile() {
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i, pic);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pic && resultCode == RESULT_OK && data != null) {
            link = data.getData();
            select_image1.setImageURI(link);
        }

    }

    private void CheckDetails() {
        Product_name = Product_Name.getText().toString();
        Product_Price = Product_price.getText().toString();
        Product_Details = Product_details.getText().toString();
        if (link == null) {
            Toast.makeText(this, "Please Select Image!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Product_Details)) {
            Toast.makeText(this, "Please Describe The Product!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Product_Price)) {
            Toast.makeText(this, "Please Select The Price!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Product_name)) {
            Toast.makeText(this, "Please Select The Name!", Toast.LENGTH_SHORT).show();
        } else {
            StoreProduct();
        }


    }

    private void StoreProduct() {
        loading.setTitle("Adding Product");
        loading.setMessage("Admin Please! Wait While We are Adding");
        loading.setCanceledOnTouchOutside(false);
        loading.show();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat CurrentDate = new SimpleDateFormat("DD MM, YYYY");
        currentDate = CurrentDate.format(cal.getTime());

        SimpleDateFormat CurrentTime = new SimpleDateFormat("HH: MM: SS, a");
        currentTime = CurrentTime.format(cal.getTime());
        ProductKey = currentTime + currentDate;


        final StorageReference filepath = ImageRef.child(link.getLastPathSegment() + ProductKey + ".jpg");
        final UploadTask uploadTask = filepath.putFile(link);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String msg = e.toString();
                Toast.makeText(AdminPanel.this, "Errot" + msg, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminPanel.this, "Succesful Uploaded", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        DownloadImgLink = filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            DownloadImgLink = task.getResult().toString();
                            Toast.makeText(AdminPanel.this, "Saved Complete", Toast.LENGTH_SHORT).show();
                            SaveProduct();
                        }


                    }
                });
            }

        });
    }


    private void SaveProduct() {
        HashMap <String , Object> Productmap = new HashMap<>();
        Productmap.put("pid",ProductKey);
        Productmap.put("time",currentTime);
        Productmap.put("date",currentDate);
        Productmap.put("details",Product_Details);
        Productmap.put("image",DownloadImgLink);
        Productmap.put("catagery",ProductName);
        Productmap.put("price",Product_Price);
        Productmap.put("pname",Product_name);
        Productref.child(Product_name).updateChildren(Productmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
              if(task.isSuccessful())
              {
                  Intent i = new Intent(AdminPanel.this, AdminProduct.class);
                  startActivity(i);
                  loading.dismiss();
                  Toast.makeText(AdminPanel.this, "Added Sucesful", Toast.LENGTH_SHORT).show();
              }
              else {
                  loading.dismiss();
                  String msg = task.getException().toString();
                  Toast.makeText(AdminPanel.this, "Error"+msg, Toast.LENGTH_SHORT).show();
              }
            }
        });


    }
}