package com.example.x_root.netduino_client;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by Attaullah Khan on 7/17/15.
 */
public class ListCartAdapter extends BaseAdapter {
    Context context;

    LayoutInflater inflater;
    LinkedList<CartItem> items;

    public ListCartAdapter(Context context){
        this.context=context;
        // flag=new int[] {R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,
        //      R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password };
        items=new LinkedList<CartItem>();
    }



    LinkedList<CartItem>  getitems(){

        return items;


    }




    @Override
    public int getCount() {
        return items.size();
    }


    public void sort()
    {
        Collections.sort(items, new Mycartcomparator());
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // Declare Variables
        TextView txttitle;
        TextView txtprice;
        TextView txtid;
        EditText txtqun;
        ImageView imgflag;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.list_cart_format, parent, false);

        // Locate the TextViews in listview_item.xml
        txttitle = (TextView) itemView.findViewById(R.id.listswitchname);
        txtprice = (TextView) itemView.findViewById(R.id.lilistswitchpin);
        txtid = (TextView) itemView.findViewById(R.id.listcartid);
        txtqun=(EditText) itemView.findViewById(R.id.listcarteditText);
        // Locate the ImageView in listview_item.xml
        imgflag = (ImageView) itemView.findViewById(R.id.listcartflag);

        // Capture position and set to the TextViews
        //txttitle.setText(items.get(position).getitem().gettitle());
        //txtprice.setText(Integer.toString(items.get(position).getitem().getprice()));
        //txtid.setText(Integer.toString(items.get(position).getitem().getid()));
        //txtqun.setText(Integer.toString(items.get(position).getquantity()));

        // Capture position and set to the ImageView
        imgflag.setImageResource(R.mipmap.ic_password);

        return itemView;
    }
}


