package com.appsaga.entrymanagement.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.appsaga.entrymanagement.R;
import com.appsaga.entrymanagement.Models.Visitors;

import java.util.ArrayList;

public class VisitorsAdapter extends ArrayAdapter<Visitors> {

    Context context;

    public VisitorsAdapter(@NonNull Context context, ArrayList<Visitors> visitors) {
        super(context, 0,visitors);
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View ongoing_visitors_view = convertView;

        if(ongoing_visitors_view==null) {
            ongoing_visitors_view = LayoutInflater.from(context).inflate(R.layout.ongoing_visitor_view, null);
        }

        Visitors visitor = getItem(position);

        TextView name = ongoing_visitors_view.findViewById(R.id.name);
        TextView email = ongoing_visitors_view.findViewById(R.id.email);
        TextView contact = ongoing_visitors_view.findViewById(R.id.contact);
        TextView check_in = ongoing_visitors_view.findViewById(R.id.check_in);

        name.setText(visitor.getName());
        email.setText(visitor.getEmail());
        contact.setText(visitor.getPhone());
        check_in.setText(visitor.getCheckin_Date()+"\n"+visitor.getCheckin());

        return ongoing_visitors_view;
    }
}
