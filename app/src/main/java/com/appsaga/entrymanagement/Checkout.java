package com.appsaga.entrymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Checkout extends AppCompatActivity {

    TextView guest_name,guest_phone,guest_email,guest_check_in;
    TextView host_name,host_phone,host_email;
    Hosts host;
    String name,email,phone,check_in;
    Button checkout,yes,no;
    private Dialog dialog;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        host = (Hosts) getIntent().getSerializableExtra("host");
        name = getIntent().getStringExtra("guest_name");
        email = getIntent().getStringExtra("guest_email");
        phone = getIntent().getStringExtra("guest_phone");
        check_in=getIntent().getStringExtra("guest_check_in");

        guest_name=findViewById(R.id.guest_name);
        guest_phone=findViewById(R.id.guest_phone);
        guest_email=findViewById(R.id.guest_email);
        guest_check_in=findViewById(R.id.guest_check_in);

        host_name=findViewById(R.id.host_name);
        host_email=findViewById(R.id.host_email);
        host_phone=findViewById(R.id.host_phone);

        fillDetails();

        dialog = new Dialog(Checkout.this);
        dialog.setContentView(R.layout.checkout_dialog);

        checkout=findViewById(R.id.checkout);
        yes = dialog.findViewById(R.id.btn_yes);
        no = dialog.findViewById(R.id.btn_no);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
            }
        });

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkout();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
    }

    private void fillDetails()
    {
        guest_name.setText(name);
        guest_email.setText(email);
        guest_phone.setText(phone);
        guest_check_in.setText(check_in);

        host_name.setText(host.getName());
        host_phone.setText(host.getPhone());
        host_email.setText(host.getEmail());
    }

    @Override
    public void onBackPressed() {

        dialog.show();
    }

    private void checkout()
    {
        String subject = "Thank you for visiting";
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        String check_out = dateFormat.format(date);

        message = "Name: "+name+"\n\n"+
                "Contact Number: "+phone+"\n\n"+
                "Check-in Time: "+check_in+"\n\n"+
                "Check-out Time: "+check_out+"\n\n"+
                "Host Name: "+host.getName()+"\n\n"+
                "Address Visited: "+"Innovaccer Office";

        SendMail sendMail=new SendMail(Checkout.this,email,subject,message);
        sendMail.execute();
        finish();
    }
}
