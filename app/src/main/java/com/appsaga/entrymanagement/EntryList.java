package com.appsaga.entrymanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class EntryList extends AppCompatActivity {

    DatabaseReference databaseReference;
    ArrayList<Visitors> visitors;
    VisitorsAdapter visitorsAdapter;
    ListView entry_list;
    ProgressDialog progressDialog;
    Button makeEntry;
    TextView noEntries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_list);

        databaseReference = FirebaseDatabase.getInstance().getReference("Visitors");

        entry_list = findViewById(R.id.entry_list);

        makeEntry = findViewById(R.id.make_entry);
        noEntries = findViewById(R.id.no_entry);

        progressDialog = new ProgressDialog(EntryList.this);
        progressDialog.show();

        makeEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(EntryList.this, MainActivity.class));
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
    }
}
