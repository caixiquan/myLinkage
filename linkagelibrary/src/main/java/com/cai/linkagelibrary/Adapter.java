package com.cai.linkagelibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter extends BaseAdapter{

    LayoutInflater inflater = null;
    ArrayList<MyListItem> listInfo;
    public Adapter(Context context,ArrayList<MyListItem> listInfo){
        //inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater = LayoutInflater.from(context);
        //this.inflater = inflater;
        this.listInfo = listInfo;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listInfo.size();
    }
    @Override
    public Object getItem(int index) {
        // TODO Auto-generated method stub
        return listInfo.get(index);
    }
    @Override
    public long getItemId(int index) {
        // TODO Auto-generated method stub
        return index;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if(convertView == null || convertView.getTag() == null){
            convertView = inflater.inflate(R.layout.linkage_item,null);
            holder = new ViewHolder();
           
            holder.textView = (TextView)convertView.findViewById(R.id.textview);
            convertView.setTag(holder);
        }else{
             
            holder = (ViewHolder)convertView.getTag();
        }
        MyListItem appInfo = listInfo.get(position);
        holder.textView.setText(appInfo.getName());
        return convertView;
    }
    public class ViewHolder{
        TextView textView;
    }


}