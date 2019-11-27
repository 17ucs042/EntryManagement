package com.appsaga.entrymanagement.ActivityClasses;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.appsaga.entrymanagement.Models.Hosts;
import com.appsaga.entrymanagement.R;

public class HostDetails extends AppCompatActivity {

    TextView name,post,dept,about,phone,email;
    private Hosts host;
    Button cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_details);

        host = (Hosts) getIntent().getSerializableExtra("host_details");

        name=findViewById(R.id.name);
        post=findViewById(R.id.post);
        dept=findViewById(R.id.dept);
        about=findViewById(R.id.about);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);

        cont=findViewById(R.id.cont);

        fillDetails();

        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HostDetails.this, Form.class);
                intent.putExtra("host",host);
                startActivity(intent);
                finish();
            }
        });
    }

    private void fillDetails()
    {
        name.setText(host.getName());
        post.setText(host.getPost());
        dept.setText(host.getDepartment());
        about.setText(host.getAbout());
        phone.setText(host.getPhone());
        email.setText(host.getEmail());
    }
}
