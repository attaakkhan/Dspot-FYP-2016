

package com.example.x_root.netduino_client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Attaullah Khan on 26/3/16.
 */
public class Ipadapter extends BaseAdapter

{

    Boolean check = false;

    // Declare Variables
    Context context;
    String[] title;
    String[] price;
    String[] id;
    CheckBox checkBox;
    int[] flag;
    LayoutInflater inflater;
    LinkedList<IP> items;
    String type = "";

    public Ipadapter(Context context) {
        this.context = context;
        //          this.type=type;
        // flag=new int[] {R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,
        //      R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password };
        items = new LinkedList<IP>();
    }

    public HashMap<String, Boolean> textValues = new HashMap<String, Boolean>();


    LinkedList<IP> getitems() {

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


    public String gettype() {
        return type;
    }

    public void settype(String type) {
        this.type = type;

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
        TextView tvipp;


        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View view = convertView;
        boolean convertViewWasNull = false;
        if (view == null) {
            view = inflater.inflate(R.layout.list_ipformat, parent, false);
            convertViewWasNull = true;
        }




        //tvname.setText(items.get(position).getname());
        //tvpin.setText(Integer.toString(items.get(position).getpin()));

        if (convertViewWasNull) {

            //be aware that you shouldn't do this for each call on getView, just once by listItem when convertView is null


        }




        tvname=(TextView)view.findViewById(R.id.list_ip_name);
        tvipp=(TextView)view.findViewById(R.id.list_ip_ipport);


        //tvname.setText(items.get(position).getname());
        //tvpin.setText(Integer.toString(items.get(position).getpin()));


        tvname.setText(items.get(position).getname());
String s=items.get(position).getip()+":"+items.get(position).getport();

        tvipp.setText(s);

        return view;
    }
}









