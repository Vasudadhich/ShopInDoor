package com.example.ecommerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Signup extends AppCompatActivity {
    Button Sign;
    EditText semail, sPaswd, sname, sno;
    ProgressDialog Load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Sign = (Button) findViewById(R.id.sign);
        semail = (EditText) findViewById(R.id.semail);
        sPaswd = (EditText) findViewById(R.id.sPaswd);
        sname = (EditText) findViewById(R.id.sname);
        sno = (EditText) findViewById(R.id.sno);
        Load = new ProgressDialog(this);
        
        Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Account();
            }
        });
    }

    private void Account() {
        String Email = semail.getText().toString();
        String Name = sname.getText().toString();
        String Passwd = sPaswd.getText().toString();
        String Number = sno.getText().toString();
        if (TextUtils.isEmpty(Name))
        {
            Toast.makeText(this,"Enter The Name",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Email))
        {
            Toast.makeText(this,"Enter The Email",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Passwd))
        {
            Toast.makeText(this,"Enter The Password",Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Number))
        {
            Toast.makeText(this,"Enter The Number",Toast.LENGTH_SHORT).show();
        }
        else {
            Load.setTitle("SignIn");
            Load.setMessage("Please! Wait While We are checking details");
            Load.setCanceledOnTouchOutside(false);
            Load.show();
            Validation(Name,Number,Email,Passwd);

        }
    }

    private void Validation(final String name, final String number, final String email, final String passwd) {
        final DatabaseReference Root;
        Root= FirebaseDatabase.getInstance().getReference();
        Root.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child("User").child(email).exists())) {
                    HashMap<String, Object> UserData = new HashMap<>();
                    UserData.put("Phone", number);
                    UserData.put("Email", email);
                    UserData.put("Password", passwd);
                    UserData.put("Name", name);
                    Root.child("User").child(email).updateChildren(UserData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Signup.this, "Account Create Succesful", Toast.LENGTH_SHORT).show();
                                Load.dismiss();
                                Intent i = new Intent(Signup.this, login.class);
                                startActivity(i);
                            } else {
                                Toast.makeText(Signup.this, "Unknown Error!Please Try Again", Toast.LENGTH_SHORT).show();
                                Load.dismiss();
                            }
                        }
                    });
                } else {
                    Toast.makeText(Signup.this, "This" + email + "Already Exists.Please Try Another...", Toast.LENGTH_SHORT).show();
                    Load.dismiss();
                    Intent i = new Intent(Signup.this, MainActivity.class);
                    startActivity(i);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
