package com.appsaga.entrymanagement.ActivityClasses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.appsaga.entrymanagement.Adapters.HostsAdapter;
import com.appsaga.entrymanagement.Models.Hosts;
import com.appsaga.entrymanagement.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HostsList extends AppCompatActivity {

    DatabaseReference databaseReference;
    ArrayList<Hosts> hosts;
    ListView hostsLists;
    ProgressDialog progressDialog;
    HostsAdapter hostsAdapter;
    FloatingActionButton addHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hosts_list);

        addHost = findViewById(R.id.add_host_fb);

        hostsLists = findViewById(R.id.hosts_list);
        progressDialog=new ProgressDialog(HostsList.this);
        progressDialog.setMessage("Loading");

        databaseReference = FirebaseDatabase.getInstance().getReference("Hosts");

        hostsLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Hosts host = hosts.get(position);
                Intent intent = new Intent(HostsList.this, Form.class);
                intent.putExtra("host",host);
                startActivity(intent);
                finish();
            }
        });

        addHost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HostsList.this,AddHost.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressDialog.show();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                hosts=new ArrayList<>();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    hosts.add(ds.getValue(Hosts.class));
                }

                hostsAdapter = new HostsAdapter(HostsList.this,hosts);
                hostsAdapter.notifyDataSetChanged();

                hostsLists.setAdapter(null);
                hostsLists.setAdapter(hostsAdapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }
}
