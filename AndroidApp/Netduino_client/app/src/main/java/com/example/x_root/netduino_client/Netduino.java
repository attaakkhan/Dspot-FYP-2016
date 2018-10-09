package com.example.x_root.netduino_client;

import android.app.Activity;
import android.bluetooth.BluetoothClass;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

public class Netduino extends Activity {
Button netduinoswitches,netduinoallon,netduinoalloff,netduinoip,netduinomacs;
    ListView list;
    createlist_ip_adapter netipadapter;

    Listswitchadapter netswadapter;
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_netduino);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }




list=(ListView)findViewById(R.id.list_netduino);



        netipadapter=new createlist_ip_adapter(this);
netswadapter=new Listswitchadapter(this);

        netduinoip = (Button) findViewById(R.id.netduinoip);
        netduinoip.setOnClickListener(netduinoipl);
        netduinomacs = (Button) findViewById(R.id.netduinomac);
        netduinomacs.setOnClickListener(netduinomacsl);
        netduinoallon = (Button) findViewById(R.id.netduinoallon);
        netduinoallon.setOnClickListener(netduinoallonl);
        netduinoswitches = (Button) findViewById(R.id.netduinoswitches);
        netduinoswitches.setOnClickListener(netduinoswitchesl);
        netduinoalloff = (Button) findViewById(R.id.netduinoalloff);
        netduinoalloff.setOnClickListener(netduinoalloffl);

        netipadapter.getitems().addAll(Devices.myips);
        list.setAdapter(netipadapter);
        netswadapter.ls=list;

    }



    View.OnClickListener netduinoipl  //snapshot button listner
            = new View.OnClickListener() {




        @Override
        public void onClick(View arg0) {

            netipadapter.renew();
            list.setAdapter(netipadapter);










        }
    };



    View.OnClickListener netduinomacsl  //snapshot button listner
            = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {




        }
    };

View.OnClickListener netduinoswitchesl  //snapshot button listner
        = new View.OnClickListener() {

    @Override
    public void onClick(View arg0) {

        IP ip=null;

        for (int i=0;i<netipadapter.getitems().size();i++){
            if(netipadapter.getitems().get(i).getcheckbox()==true){


                ip=netipadapter.getitems().get(i); break;
            }}
        if(ip==null){ReadWrite.show("Plese select IP",Netduino.this);
        }
        else{
           String  res =Devices.getalldevices(ip,Netduino.this);

            if(res.compareTo("OK")==0){


               netswadapter.getitems().clear();
                netswadapter.getitems().addAll(Devices.switches);
                netswadapter.ip=ip;

                list.setAdapter(netswadapter);
                ReadWrite.show("GET",Netduino.this);}


            else{


                ReadWrite.show("ERROR",Netduino.this);}}
        }



};

View.OnClickListener netduinoallonl //snapshot button listner
        = new View.OnClickListener() {

@Override
public void onClick(View arg0) {

    IP ip=null;

    for (int i=0;i<netipadapter.getitems().size();i++){
        if(netipadapter.getitems().get(i).getcheckbox()==true){


            ip=netipadapter.getitems().get(i); break;
        }}
    if(ip==null){ReadWrite.show("Plese select IP",Netduino.this);
    }
    else{
        String  res =Devices.getalldevices(ip,Netduino.this);

        if(res.compareTo("OK")==0){
                LinkedList<Switch> ll=new LinkedList<Switch>();
             ll.addAll(Devices.switches);
            for (Switch i:ll)
            i.state=true;
            if(Devices.writeswitches(ll,ip,Netduino.this).compareTo("OK")==0)
            {
            netswadapter.getitems().clear();
            netswadapter.getitems().addAll(ll);

            netswadapter.ip=ip;

            list.setAdapter(netswadapter);
            ReadWrite.show("GET",Netduino.this);}


        else{


            ReadWrite.show("ERROR",Netduino.this);}}
}




        }
        };
        View.OnClickListener netduinoalloffl  //snapshot button listner
        = new View.OnClickListener() {

@Override
public void onClick(View arg0) {

    IP ip=null;

    for (int i=0;i<netipadapter.getitems().size();i++){
        if(netipadapter.getitems().get(i).getcheckbox()==true){


            ip=netipadapter.getitems().get(i); break;
        }}
    if(ip==null){ReadWrite.show("Plese select IP",Netduino.this);
    }
    else{
        String  res =Devices.getalldevices(ip,Netduino.this);

        if(res.compareTo("OK")==0){
            LinkedList<Switch> ll=new LinkedList<Switch>();
            ll.addAll(Devices.switches);
            for (Switch i:ll)
                i.state=false;
            if(Devices.writeswitches(ll,ip,Netduino.this).compareTo("OK")==0)
            {
                netswadapter.getitems().clear();
                netswadapter.getitems().addAll(ll);

                netswadapter.ip=ip;

                list.setAdapter(netswadapter);
                ReadWrite.show("GET",Netduino.this);}


            else{


                ReadWrite.show("ERROR",Netduino.this);}}
    }




}
        };



        }




