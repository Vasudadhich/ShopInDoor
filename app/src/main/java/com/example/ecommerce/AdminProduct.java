package com.example.ecommerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdminProduct extends AppCompatActivity {
    ImageView tshirt,sports,female,sweter,glasses,shoes,watch,namkeen,masala,paped,supari,special;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product);
        tshirt = (ImageView) findViewById(R.id.tshirt);
        sports = (ImageView) findViewById(R.id.sport);
        female = (ImageView) findViewById(R.id.female);
        sweter = (ImageView) findViewById(R.id.sweter);
        glasses = (ImageView) findViewById(R.id.glasses);
        shoes= (ImageView) findViewById(R.id.shoes);
        watch = (ImageView) findViewById(R.id.watch);
        namkeen = (ImageView) findViewById(R.id.namkeen);
        masala = (ImageView) findViewById(R.id.masala);
        paped = (ImageView) findViewById(R.id.paped);
        supari = (ImageView) findViewById(R.id.supari);
        special = (ImageView) findViewById(R.id.special);
        tshirt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminProduct.this,AdminPanel.class);
                i.putExtra("Products","tshirts");
                startActivity(i);
            }
        });

        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminProduct.this,AdminPanel.class);
                i.putExtra("Products","sports");
                startActivity(i);
            }
        });
        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminProduct.this,AdminPanel.class);
                i.putExtra("Products","female");
                startActivity(i);
            }
        });
        sweter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminProduct.this,AdminPanel.class);
                i.putExtra("Products","sweter");
                startActivity(i);
            }
        });
        glasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminProduct.this,AdminPanel.class);
                i.putExtra("Products","glasses");
                startActivity(i);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminProduct.this,AdminPanel.class);
                i.putExtra("Products","shoes");
                startActivity(i);
            }
        });
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminProduct.this,AdminPanel.class);
                i.putExtra("Products","watch");
                startActivity(i);
            }
        });
        namkeen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminProduct.this,AdminPanel.class);
                i.putExtra("Products","namkeen");
                startActivity(i);
            }
        });
        masala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminProduct.this,AdminPanel.class);
                i.putExtra("Products","masala");
                startActivity(i);
            }
        });
        paped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminProduct.this,AdminPanel.class);
                i.putExtra("Products","paped");
                startActivity(i);
            }
        });
        supari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminProduct.this,AdminPanel.class);
                i.putExtra("Products","supari");
                startActivity(i);
            }
        });
        special.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminProduct.this,AdminPanel.class);
                i.putExtra("Products","special");
                startActivity(i);
            }
        });
    }
}