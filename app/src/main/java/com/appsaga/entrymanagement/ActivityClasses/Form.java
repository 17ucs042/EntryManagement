package com.appsaga.entrymanagement.ActivityClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.appsaga.entrymanagement.Models.Hosts;
import com.appsaga.entrymanagement.Models.Visitors;
import com.appsaga.entrymanagement.R;
import com.appsaga.entrymanagement.Services.SendMail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Form extends AppCompatActivity {

    EditText name, email, phone;
    String guest_name, guest_phone, guest_email,guest_check_in,guest_check_in_date;
    Button checkIn,ok;
    Hosts host;
    String message;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private Intent intent;
    Visitors visitor;
    DatabaseReference databaseReference;
    String token;
    Dialog dialog;
    TextView tokenText;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        name = findViewById(R.id.guest_name);
        email = findViewById(R.id.guest_email);
        phone = findViewById(R.id.guest_phone);

        checkIn=findViewById(R.id.check_in);

        dialog = new Dialog(Form.this);
        dialog.setContentView(R.layout.token_dialog);
        ok=dialog.findViewById(R.id.ok);
        tokenText=dialog.findViewById(R.id.token_view);

        progressDialog=new ProgressDialog(Form.this);

        host=(Hosts) getIntent().getSerializableExtra("host");
        databaseReference= FirebaseDatabase.getInstance().getReference("Visitors");

        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();

                guest_name = name.getText().toString();
                guest_email = email.getText().toString();
                guest_phone = phone.getText().toString();

                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss a");
                guest_check_in = dateFormat.format(date);

                SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MMM-yyyy");
                guest_check_in_date =dateFormat1.format(date);

                if (!guest_name.equals("") && !guest_email.equals("") && !guest_phone.equals("") && guest_phone.length()>=10 && guest_phone.length()<=13)
                {
                    String subject = guest_name + " has made an entry to meet you";
                    message = "Name: "+guest_name+"\n\n"+
                            "Email: "+guest_email+"\n\n"+
                            "Contact Number: "+guest_phone+"\n\n"+
                            "Check-in Time: "+guest_check_in;

                    SendMail sendMail=new SendMail(Form.this,host.getEmail(),subject,message);
                    sendMail.execute();

                    token = guest_phone.substring(4,9)+guest_check_in.charAt(0)+guest_check_in_date.charAt(0);

                    visitor=new Visitors(guest_name,guest_check_in,guest_check_in_date,guest_phone,guest_email,"YES",host,token);

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            String id = ""+guest_name.charAt(0)+host.getName().charAt(0)+guest_phone.substring(4,10)+
                                    host.getPhone().charAt(4)+guest_email.charAt(0)+host.getEmail().charAt(0)+
                                    guest_check_in_date.substring(0,2)+guest_check_in.charAt(1);

                            databaseReference.child(id).setValue(visitor);

                            progressDialog.dismiss();

                            tokenText.setText(token);

                            SendMail sendMail=new SendMail(Form.this,guest_email,"Entry Token","Your token for the visit is: "+token);
                            sendMail.execute();

                            dialog.show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    Toast.makeText(Form.this, "Please fill in all the details", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendSMSMessage();
                Toast.makeText(Form.this,"Checked in",Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void sendSMSMessage() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(host.getPhone(), null, "I have made an entry to meet you\n\n" +message, null, null);

            dialog.dismiss();
            finish();
            //startActivity(intent);
            Log.d("test","msg1");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendSMSMessage();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Sending SMS failed.", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                    finish();
                    return;
                }
            }
        }

    }
}
