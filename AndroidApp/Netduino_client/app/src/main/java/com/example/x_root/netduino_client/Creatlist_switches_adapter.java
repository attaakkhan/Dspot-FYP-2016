package com.example.x_root.netduino_client;

/**
 * Created by Attaullah Khan on 7/11/15.
 */
import android.content.Context;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PolicySpi;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

class Creatlist_switches_adapter extends BaseAdapter {

    // Declare Variables
    Context context;
    String type = "switch";
    String[] title;
    String[] price;
    String[] id;
    int[] flag;
    LayoutInflater inflater;
    LinkedList<Switch> items;

    public Creatlist_switches_adapter(Context context) {
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
            a = getetname(i);
            b = getetpin(i);
           // ReadWrite.show(a + "     " + b+"    "+items.get(i).getname()+"   "+items.get(i).getpin(), context);


            if (a.compareTo("") == 0 && b.compareTo("") == 0) {
               // ReadWrite.show("a",context);
               }
             // else if (a.compareTo("")==0&&a.compareTo("0")==0){
            // if (items.get(i).getname().compareTo("") == 0 && items.get(i).getpin() == 0) {
                //ReadWrite.show("b",context);
               // }
             else if (a.compareTo("NO") == 0 && b.compareTo("NO") == 0) {
                aa.add(items.get(i));
               // ReadWrite.show("c", context);

            } else

            {
               //if(items.get(i).getname().compareTo("")!=0&&items.get(i).getpin()!=0){
                if (a.compareTo("")!=0&&a.compareTo("0")!=0){
                items.get(i).setname(getetname(i));

                items.get(i).setpin(Integer.parseInt(getetpin(i)));

                aa.add(items.get(i));}
            }}

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
        EditText etname;
        EditText etpin;


        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        View view = convertView;
        boolean convertViewWasNull = false;
        if (view == null) {
            view = inflater.inflate(R.layout.list_addswitches_format, parent, false);
            convertViewWasNull = true;
        }

        etname = (EditText) view.findViewById(R.id.list_sw_name_tv);

        etpin = (EditText) view.findViewById(R.id.list_sw_pin_tv);

        //tvname.setText(items.get(position).getname());
        //tvpin.setText(Integer.toString(items.get(position).getpin()));

        if (convertViewWasNull) {

            //be aware that you shouldn't do this for each call on getView, just once by listItem when convertView is null
            etname.addTextChangedListener(new GenericTextWatcher(etname));
            etpin.addTextChangedListener(new GenericTextWatcher(etpin));
        }

        //whereas, this should be called on each getView call, to update view tags.
        etname.setTag("etname:" + position);
        etpin.setTag("etpin:" + position);
        etname.setText(items.get(position).getname());
        etpin.setText(items.get(position).getpin()+"");





        return view;
    }


    private class GenericTextWatcher implements TextWatcher {

        private View view;

        private GenericTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {

            String text = editable.toString();
            //save the value for the given tag :
           // Toast.makeText(context, text.toString()+"eee"+view.getTag().toString(), Toast.LENGTH_LONG).show();
            Creatlist_switches_adapter.this.textValues.put(view.getTag().toString(), editable.toString());
        }


        //you can implement a method like this one for each EditText with the list position as parameter :


    }


    public String getetname(int position) {
        //here you need to recreate the id for the first editText
        String result = textValues.get("etname:" + position);
        if (result == null)
            result = "NO";

        return result;
    }

    public String getetpin(int position) {
        //here you need to recreate the id for the second editText
        String result = textValues.get("etpin:" + position);
        if (result == null)
            result = "NO";

        return result;
    }
}
//
  //   }

