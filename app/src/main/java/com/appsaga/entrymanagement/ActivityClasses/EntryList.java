package com.appsaga.entrymanagement.ActivityClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.appsaga.entrymanagement.Adapters.VisitorsAdapter;
import com.appsaga.entrymanagement.Models.Hosts;
import com.appsaga.entrymanagement.Models.Visitors;
import com.appsaga.entrymanagement.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EntryList extends AppCompatActivity {

    DatabaseReference databaseReference;
    ArrayList<Visitors> visitors;
    VisitorsAdapter visitorsAdapter;
    ListView entry_list;
    ProgressDialog progressDialog;
    Button makeEntry;
    TextView noEntries;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_list);

        databaseReference = FirebaseDatabase.getInstance().getReference("Visitors");

        Log.d("Testing",databaseReference+"");

        entry_list = findViewById(R.id.entry_list);

        makeEntry = findViewById(R.id.make_entry);
        noEntries = findViewById(R.id.no_entry);

        progressDialog = new ProgressDialog(EntryList.this);
        progressDialog.show();

        makeEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(EntryList.this, HostsList.class));
            }
        });

        entry_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Hosts host = visitors.get(position).getHost();
                String name = visitors.get(position).getName();
                String email = visitors.get(position).getEmail();
                String phone = visitors.get(position).getPhone();
                String check_in = visitors.get(position).getCheckin();
                String check_in_date = visitors.get(position).getCheckin_Date();

                Intent intent = new Intent(EntryList.this, Checkout.class);
                intent.putExtra("host",host);
                intent.putExtra("guest_check_in",check_in);
                intent.putExtra("guest_name",name);
                intent.putExtra("guest_email",email);
                intent.putExtra("guest_phone",phone);
                intent.putExtra("guest_check_in_date",check_in_date);

                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                visitors = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Visitors visitor = ds.getValue(Visitors.class);

                    if(visitor.getOngoing().equals("YES"))
                    {
                        visitors.add(ds.getValue(Visitors.class));
                    }
                }

                if (visitors.size() != 0) {
                    visitorsAdapter = new VisitorsAdapter(EntryList.this, visitors);
                    entry_list.setAdapter(visitorsAdapter);
                    noEntries.setVisibility(View.GONE);
                    entry_list.setVisibility(View.VISIBLE);
                } else {
                    noEntries.setVisibility(View.VISIBLE);
                    entry_list.setVisibility(View.GONE);
                }

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS permission not granted.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }
}
