package com.appsaga.entrymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Form extends AppCompatActivity {

    EditText name, email, phone;
    String guest_name, guest_phone, guest_email,guest_check_in;
    Button checkIn;
    Hosts host;
    String message;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;
    private Intent intent;
    Visitors visitor;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        name = findViewById(R.id.guest_name);
        email = findViewById(R.id.guest_email);
        phone = findViewById(R.id.guest_phone);

        checkIn=findViewById(R.id.check_in);

        host=(Hosts) getIntent().getSerializableExtra("host");
        databaseReference= FirebaseDatabase.getInstance().getReference("Visitors");

        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                guest_name = name.getText().toString();
                guest_email = email.getText().toString();
                guest_phone = phone.getText().toString();

                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
                guest_check_in = dateFormat.format(date);

                if (!guest_name.equals("") && !guest_email.equals("") && !guest_phone.equals(""))
                {
//                    intent = new Intent(Form.this,EntryList.class);
//                    intent.putExtra("host",host);
//                    intent.putExtra("guest_name",guest_name);
//                    intent.putExtra("guest_email",guest_email);
//                    intent.putExtra("guest_phone",guest_phone);
//                    intent.putExtra("guest_check_in",guest_check_in);

                    visitor=new Visitors(guest_name,guest_check_in,guest_phone,guest_email,"YES");
                    String subject = guest_name + " has made an entry to meet you";
                    message = "Name: "+guest_name+"\n\n"+
                            "Email: "+guest_email+"\n\n"+
                            "Contact Number: "+guest_phone+"\n\n"+
                            "Check-in Time: "+guest_check_in;

                    SendMail sendMail=new SendMail(Form.this,host.getEmail(),subject,message);
                    sendMail.execute();

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            long count = dataSnapshot.getChildrenCount();

                            databaseReference.child(String.valueOf(count+1)).setValue(visitor);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    sendSMSMessage();
                }
                else
                {
                    Toast.makeText(Form.this, "Please fill in all the details", Toast.LENGTH_SHORT).show();
                }
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

            //startActivity(intent);
            finish();
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
                    finish();
                    return;
                }
            }
        }

    }
}
