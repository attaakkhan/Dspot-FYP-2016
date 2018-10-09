package com.example.x_root.netduino_client;

/**
 * Created by Attaullah Khan on 7/11/15.
 */
import android.content.Context;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

class ListqrcodeAdapter extends BaseAdapter {

    // Declare Variables
    Context context;
    //String[] title;
    //String[] price;
   // String[] id;
    //int[] flag;
    LayoutInflater inflater;
    LinkedList<String> items;

    public ListqrcodeAdapter(Context context){
        this.context=context;
        // flag=new int[] {R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password,
        //      R.mipmap.ic_password,R.mipmap.ic_password,R.mipmap.ic_password };
        items=new LinkedList<String>();
    }



    LinkedList<String>  getitems(){

        return items;


    }




    @Override
    public int getCount() {
        return items.size();
    }


  //  public void sort()
    //{
      //  Collections.sort(items,new Mycomparator());
    //}

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
        TextView txt;
        //TextView txtid;
        //ImageView imgflag;

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(R.layout.qrcode_format, parent, false);
        // Locate the TextViews in listview_item.xml
        txttitle = (TextView) itemView.findViewById(R.id.qrcodetv2);
        txt = (TextView) itemView.findViewById(R.id.qrcodetv1);
      //  txtid = (TextView) itemView.findViewById(R.id.id);
        // Locate the ImageView in listview_item.xml
        //imgflag = (ImageView) itemView.findViewById(R.id.flag);

        // Capture position and set to the TextViews
        txttitle.setText(items.get(position));
        txt.setText(position+") :" );
        //txtid.setText(Integer.toString(items.get(position).getid()));

        // Capture position and set to the ImageView
//        imgflag.setImageResource(R.mipmap.ic_password);

        return itemView;
    }
}
