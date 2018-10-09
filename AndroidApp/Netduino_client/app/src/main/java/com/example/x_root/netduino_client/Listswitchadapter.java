package com.example.x_root.netduino_client;

/**
 * Created by Attaullah Khan on 7/11/15.
 */
import android.content.Context;

import android.preference.TwoStatePreference;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PolicySpi;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

class Listswitchadapter extends BaseAdapter {

    // Declare Variables
    Context context;
    String type = "switch";
    String[] title;
    String[] price;
    String[] id;
    int[] flag;
    LayoutInflater inflater;
    LinkedList<Switch> items;
ListView ls;

    IP ip;

    public Listswitchadapter(Context context) {
        this.context = context;
        // flag=new int[] {R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,
        //      R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password };
        items = new LinkedList<Switch>();
        type = "switch";
    }

    public HashMap<String, String> textValues = new HashMap<String, String>();
    public HashMap<String, String> textValues2 = new HashMap<String, String>();

    LinkedList<Switch> getitems() {

        return items;


    }

    void renew() {
        int size = items.size();


        // boolean a = false;
        // Toast.makeText(context, textValues.size() + "tvsize" + textValues.size(), Toast.LENGTH_LONG).show();
        String a, b;
        LinkedList<Switch> aa = new LinkedList<Switch>();
        for (int i = 0; i < size; i++) {

        }
        items.clear();
        items.addAll(aa);

        // else
        //if(aa.compareTo("")==0&&b.compareTo("0")==0)
        //  items.remove(i);



        // Toast.makeText(context,  items.size()+ "isize", Toast.LENGTH_LONG).show();

    }


    public String gettype() {
        return type;
    }

    @Override
    public int getCount() {
        return items.size();
    }


    public void sort() {
        Collections.sort(items, new Mycomparator());
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

        // Declare Variables
        TextView tvname;
        TextView tvpin; ;

        Button on,off;


        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


         View view = convertView;
        final View vv=view;
        boolean convertViewWasNull = false;
        if (view == null) {
            view = inflater.inflate(R.layout.list_switch_formate, parent, false);
            convertViewWasNull = true;
        }

        tvname = (TextView) view.findViewById(R.id.listswitchname);

       tvpin = (TextView) view.findViewById(R.id.lilistswitchpin);

        on=(Button)view.findViewById(R.id.listswitchon);
        off=(Button)view.findViewById(R.id.listswitchoff);
        //tvname.setText(items.get(position).getname());
        //tvpin.setText(Integer.toString(items.get(position).getpin()));

        if (convertViewWasNull) {



            //be aware that you shouldn't do this for each call on getView, just once by listItem when convertView is null

        }
        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                ReadWrite.show(v.getTag().toString(),context);
                boolean state;
               Button on,off;


                Switch sw = items.get(Integer.parseInt(v.getTag().toString()));
                state = sw.state;
                sw.state = true;
                LinkedList<Switch> ll = new LinkedList<Switch>();
                ll.add(sw);
                if (Devices.writeswitches(ll, ip, context).compareTo("OK") == 0)
                { ls.setAdapter(Listswitchadapter.this);
                }
                else
                    items.get(Integer.parseInt(v.getTag().toString())).state = state;


            }

        });
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean state;
                Switch sw = items.get(Integer.parseInt(v.getTag().toString()));
                state = sw.state;
                sw.state = false;

                Button on,off;


                LinkedList<Switch> ll = new LinkedList<Switch>();
                ll.add(sw);
                if (Devices.writeswitches(ll, ip, context).compareTo("OK") == 0)
                {
                    ls.setAdapter(Listswitchadapter.this);

                }
                else
                    items.get(Integer.parseInt(v.getTag().toString())).state = state;


            }



        });

        //whereas, this should be called on each getView call, to update view tags.
        if (items.get(position).getstate()){
        on.setVisibility(View.INVISIBLE);
            off.setVisibility(View.VISIBLE);}
        else{
            off.setVisibility(View.INVISIBLE);
            on.setVisibility(View.VISIBLE);
        }
        on.setTag("" + position);
        off.setTag("" + position);

        tvname.setText(items.get(position).getname());
        tvpin.setText(items.get(position).getpin()+"");





        return view;
    }



}
//
//   }

