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

import com.appsaga.entrymanagement.Models.Hosts;
import com.appsaga.entrymanagement.R;

import java.util.ArrayList;

public class HostsAdapter extends ArrayAdapter<Hosts> {

    Context context;

    public HostsAdapter(@NonNull Context context, ArrayList<Hosts> hosts) {
        super(context, 0,hosts);
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View hostsview = convertView;

        if(hostsview==null) {
            hostsview = LayoutInflater.from(context).inflate(R.layout.hosts_view, null);
        }

        Hosts host = getItem(position);

        TextView name = hostsview.findViewById(R.id.name);
        TextView post = hostsview.findViewById(R.id.post);
        TextView department = hostsview.findViewById(R.id.department);
        TextView name_icon = hostsview.findViewById(R.id.name_icon);

        name.setText(host.getName());
        post.setText(host.getPost());
        department.setText(host.getDepartment());
        name_icon.setText(host.getName().toUpperCase().charAt(0)+"");

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

        return hostsview;
    }
}
