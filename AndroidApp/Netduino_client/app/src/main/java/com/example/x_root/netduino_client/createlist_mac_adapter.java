package com.example.x_root.netduino_client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Attaullah Khan on 26/3/16.
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
public class createlist_mac_adapter
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

    public createlist_mac_adapter(Context context){
        this.context=context;
        // flag=new int[] {R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,
        //      R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password };
        items=new LinkedList<Mac>();
    }
    public HashMap<String, Boolean> textValues = new HashMap<String, Boolean>();


    LinkedList<Mac>  getitems(){

        return items;


    }
    public Boolean getcheckbox(int position) {
        //here you need to recreate the id for the second editText
        Boolean res = textValues.get("box:" + position);


        return res;}
    void renew() {
        int size = items.size();

        Toast.makeText(context, textValues.size() + "tvsize" + textValues.size(), Toast.LENGTH_LONG).show();
        for (int i = 0; i < size; i++) {
            if(getcheckbox(i)!=null)
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

        ;


        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View view = convertView;
        boolean convertViewWasNull = false;
        if (view == null) {
            view = inflater.inflate(R.layout.list_addbellip_format, parent, false);
            convertViewWasNull = true;
        }

        checkBox = (CheckBox) view.findViewById(R.id.create_b_ip_cb);


        //tvname.setText(items.get(position).getname());
        //tvpin.setText(Integer.toString(items.get(position).getpin()));
        if (convertViewWasNull) {

            //be aware that you shouldn't do this for each call on getView, just once by listItem when convertView is null
            //setOnClickListener(new View.OnClickListener() {
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override

                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (buttonView.isChecked()) {
                        //checked
                        textValues.put(buttonView.getTag().toString(), true);

                    } else {
                        //not checked
                        textValues.put(buttonView.getTag().toString(), false);
                    }

                }
            });



        }

        checkBox.setTag("box:" + position);
        checkBox.setChecked(items.get(position).getcheckbox());

        return view;
    }
}
