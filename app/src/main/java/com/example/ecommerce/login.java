package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Snapshot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerce.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login extends AppCompatActivity {
    EditText semail , spaswd;
    Button log;
    ProgressDialog loading;
    String Parentuser="User";
    TextView Users ,admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        log = (Button) findViewById(R.id.log);
        semail = (EditText) findViewById(R.id.email);
        spaswd=(EditText) findViewById(R.id.paswd);
        loading = new ProgressDialog(this);
        admin = (TextView) findViewById(R.id.admin);
        Users= (TextView) findViewById(R.id.Users);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 userlogin();
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log.setText("LogIn Admin");
                admin.setVisibility(View.INVISIBLE);
                Users.setVisibility(View.VISIBLE);
                Parentuser = "Admins";
            }
        });
        Users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log.setText("LogIn ");
                admin.setVisibility(View.VISIBLE);
                Users.setVisibility(View.INVISIBLE);
                Parentuser = "User";

            }
        });
    }

    private void userlogin() {
        String Email = semail.getText().toString();
        String Passwd = spaswd.getText().toString();
         if (TextUtils.isEmpty(Email))
        {
            Toast.makeText(this,"Enter The Email",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Passwd))
        {
            Toast.makeText(this,"Enter The Password",Toast.LENGTH_SHORT).show();
        }
        else {
             loading.setTitle("LogIn");
             loading.setMessage("Please! Wait While We are checking details");
             loading.setCanceledOnTouchOutside(false);
             loading.show();
             AllowAcces(Email,Passwd);
         }
    }

    private void AllowAcces(final String email, final String passwd) {
        final DatabaseReference Root;
        Root= FirebaseDatabase.getInstance().getReference();
        Root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(Parentuser).child(email).exists()) {
                    User Data = snapshot.child(Parentuser).child(email).getValue(User.class);
                    if (Data.getEmail().equals(email)) {
                        if (Data.getPassword().equals(passwd)) {
                            if(Parentuser.equals("Admins"))
                            {
                                Toast.makeText(login.this, "Login Succesful", Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                                Intent i = new Intent(login.this, AdminProduct.class);
                                startActivity(i);
                            }
                            else if (Parentuser.equals("User"))
                            {
                                Toast.makeText(login.this, "Login Succesful In AdminPanel", Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                                Intent i = new Intent(login.this, login.class);
                                startActivity(i);
                            }
                            else{

                            }

                        } else {
                            loading.dismiss();
                            Toast.makeText(login.this, "Email And Password Incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(login.this, "Email Does Not exists", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
