package com.example.campusclubs.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.campusclubs.R;
import com.example.campusclubs.models.Club;

import java.util.List;

public class ClubAdapter extends BaseAdapter {

    private Context context;
    private List<Club> clubs;

    public ClubAdapter(Context context, List<Club> clubs) {
        this.context = context;
        this.clubs = clubs;
    }

    @Override
    public int getCount() {
        return clubs.size();
    }

    @Override
    public Object getItem(int position) {
        return clubs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return clubs.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Club club = clubs.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_club, parent, false);
        }
        TextView tvName = convertView.findViewById(R.id.tvItemClubName);
        TextView tvCategory = convertView.findViewById(R.id.tvItemClubCategory);

        tvName.setText(club.getName());
        tvCategory.setText(club.getCategory());

        return convertView;
    }
}
