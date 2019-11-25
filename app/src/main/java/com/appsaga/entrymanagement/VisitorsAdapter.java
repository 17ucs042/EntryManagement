package com.appsaga.entrymanagement;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
        TextView name_icon = ongoing_visitors_view.findViewById(R.id.name_icon);

        name.setText(visitor.getName());
        email.setText(visitor.getEmail());
        contact.setText(visitor.getPhone());
        check_in.setText(visitor.getCheckin());
        name_icon.setText(visitor.getName().toUpperCase().charAt(0)+"");

        if(position%3==0)
        {
            GradientDrawable back = (GradientDrawable)name_icon.getBackground();
            back.setColor(Color.RED);
        }
        else if(position%3==1)
        {
            GradientDrawable back = (GradientDrawable)name_icon.getBackground();
            back.setColor(Color.BLUE);
        }
        else {
            GradientDrawable back = (GradientDrawable)name_icon.getBackground();
            back.setColor(Color.GREEN);
        }

        return ongoing_visitors_view;
    }
}
