package com.example.x_root.netduino_client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Attaullah Khan on 26/3/16.
 */


/**
 * Created by Attaullah Khan on 7/11/15.
 */
import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.LinkedList;
public class List_mac_adapter
        extends BaseAdapter {
    String type="mac";
    CheckBox checkBox;
    // Declare Variables
    Context context;
    String[] title;
    String[] price;
    String[] id;
    int[] flag;
    LayoutInflater inflater;
    LinkedList<Mac> items;

    public List_mac_adapter(Context context){
        this.context=context;
        // flag=new int[] {R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,
        //      R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password };
        items=new LinkedList<Mac>();
    }
    public HashMap<String, Boolean> textValues = new HashMap<String, Boolean>();


    LinkedList<Mac>  getitems(){

        return items;


    }
    public boolean getcheckbox(int position) {
        //here you need to recreate the id for the second editText
        Boolean result = textValues.get("box:" + position);
        if (result == null)
            result = false;

        return result;}
    void renew() {
        int size = items.size();

        Toast.makeText(context, textValues.size() + "tvsize" + textValues.size(), Toast.LENGTH_LONG).show();
        for (int i = 0; i < size; i++) {

            items.get(i).setcheckbox(getcheckbox(i));


        }
        Toast.makeText(context, items.size() + "isize", Toast.LENGTH_LONG).show();

    }


    public String gettype(){
        return type;
    }


    @Override
    public int getCount() {
        return items.size();
    }




    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        ;TextView tvname;
        TextView tvmac;



        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View view = convertView;
        boolean convertViewWasNull = false;
        if (view == null) {
            view = inflater.inflate(R.layout.list_mac_format, parent, false);
            convertViewWasNull = true;
        }

        tvname=(TextView)view.findViewById(R.id.list_mac_name);
        tvmac=(TextView)view.findViewById(R.id.list_mac_mac);


        //tvname.setText(items.get(position).getname());
        //tvpin.setText(Integer.toString(items.get(position).getpin()));


        tvmac.setText(items.get(position).getname());

        tvmac.setText(items.get(position).getmac());



        return view;
    }
}
