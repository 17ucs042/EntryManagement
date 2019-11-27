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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    ArrayList<Hosts> hosts;
    ListView hostsLists;
    ProgressDialog progressDialog;
    HostsAdapter hostsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hostsLists = findViewById(R.id.hosts_list);
        progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading");

        databaseReference = FirebaseDatabase.getInstance().getReference("Hosts");
        hosts=new ArrayList<>();

        progressDialog.show();

        fillListView();

        hostsLists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Hosts host = hosts.get(position);
                Intent intent = new Intent(MainActivity.this, HostDetails.class);
                intent.putExtra("host_details",host);
                startActivity(intent);
                finish();
            }
        });
    }

    private void fillListView()
    {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    hosts.add(ds.getValue(Hosts.class));
                }

                hostsAdapter = new HostsAdapter(MainActivity.this,hosts);

                hostsLists.setAdapter(hostsAdapter);

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
