package com.appsaga.entrymanagement.ActivityClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.appsaga.entrymanagement.Models.Hosts;
import com.appsaga.entrymanagement.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddHost extends AppCompatActivity {

    EditText name, email, phone;
    String host_name, host_phone, host_email;
    Button addHost;
    Hosts host;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_host);

        name = findViewById(R.id.host_name);
        email=findViewById(R.id.host_email);
        phone=findViewById(R.id.host_phone);

        addHost = findViewById(R.id.add_host);

        databaseReference= FirebaseDatabase.getInstance().getReference("Hosts");

        progressDialog=new ProgressDialog(AddHost.this);

        addHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                addHostTODatabase();
            }
        });
    }

    private void addHostTODatabase()
    {
        host_name=name.getText().toString();
        host_phone=phone.getText().toString();
        host_email=email.getText().toString();

        if (!host_name.equals("") && !host_email.equals("") && !host_phone.equals("") && host_phone.length()>=10
                && host_phone.length()<=13) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    host = new Hosts(host_name, host_phone, host_email);
                    databaseReference.child((dataSnapshot.getChildrenCount()+1) + "").setValue(host);
                    progressDialog.dismiss();
                    Toast.makeText(AddHost.this,"Host Added",Toast.LENGTH_LONG).show();
                    finish();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    progressDialog.dismiss();
                }
            });
        }
        else
        {
            Toast.makeText(AddHost.this, "Please fill in all the details", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
