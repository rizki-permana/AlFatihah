package com.example.user.alfatihah.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.alfatihah.R;
import com.example.user.alfatihah.model.DataModel;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    private final Context context;
    private final List<DataModel> ayahs;

    public ListAdapter(Context context, List<DataModel> ayahs) {
        this.context = context;
        this.ayahs = ayahs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewHolder holder = new ViewHolder();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, null);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView1 = convertView.findViewById(R.id.number);
        holder.textView1.setText(ayahs.get(position).getNumber());
        holder.textView2 = convertView.findViewById(R.id.text);
        holder.textView2.setText(ayahs.get(position).getText());

        return convertView;
    }

    static class ViewHolder {
        TextView textView1, textView2;
    }

    public Object getItem(int position) {
        return ayahs.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public int getCount() {
        return ayahs.size();
    }

}
