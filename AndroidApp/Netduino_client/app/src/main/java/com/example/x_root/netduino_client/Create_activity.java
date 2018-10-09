package com.example.x_root.netduino_client;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
//import android.support.v7.widget.SearchView;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;



import java.io.File;
import java.util.LinkedList;

public class Create_activity extends Activity {

    boolean a = false;
    Button create_ips_b;
    Button create_macs_b;
    Button create_addip_b;
    Button create_addmac_b;
    Button create_createnetduino_b;


    ListView maclist;
    ListView iplist;
    RelativeLayout rl;
    EditText create_ft11;
    EditText create_ft21;
    EditText create_ft31;
    Button create_add1;
    TextView create_tv1;


    Button create_ip_b;
    Button create_sw_b;
    Button create_b_ips_b;
    Button create_b_macs_b;
    Button create_create_b;
    Button create_back_b;
    Button create_update_b;
    Creatlist_switches_adapter swadapter;
    createlist_mac_adapter cmacadapter;
    createlist_mac_adapter cmacbelladapter;
    createlist_ip_adapter cipadapter;
    createlist_ip_adapter cipbelladapter;
    createlist_ip_adapter cfromipadapter;
    Ipadapter vipadapter;
    List_mac_adapter vmacadapter;



    ListView listc;
    ListView listv;
    ViewSwitcher switcher;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // requestWindowFeature(Window.FEATURE_ACTION_BAR);


        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_create);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        // showSettingsAlert();
        //  switcher = (ViewSwitcher) findViewById(R.id.ViewSwitcheremail);


        switcher = (ViewSwitcher) findViewById(R.id.ViewSwitcheremail);





            create_ft11 = (EditText) findViewById(R.id.create_ft1);
            create_ft21 = (EditText) findViewById(R.id.create_ft2);
            create_ft31 = (EditText) findViewById(R.id.create_ft3);
            create_add1 = (Button) findViewById(R.id.create_add);
            create_tv1 = (TextView) findViewById(R.id.create_tv1);
            listv = (ListView) findViewById(R.id.list_create22);
            rl = (RelativeLayout) findViewById(R.id.create_rl);
            switcher.showNext();
            rl.setVisibility(View.GONE);
            switcher.showNext();

            listc = (ListView) findViewById(R.id.list_create);
            swadapter = new Creatlist_switches_adapter(this);
            cmacadapter = new createlist_mac_adapter(this);
            cipbelladapter = new createlist_ip_adapter(this);
            cfromipadapter = new createlist_ip_adapter(this);
            cipadapter = new createlist_ip_adapter(this);
             vipadapter=new Ipadapter(this);

             vipadapter=new Ipadapter(this);
             vmacadapter=new List_mac_adapter(this);
             vipadapter.getitems().add(new IP("", "as", 9));







            create_ips_b = (Button) findViewById(R.id.create_ips1);
            create_ips_b.setOnClickListener(create_ipsl);

            create_add1.setOnClickListener(create_add1l);
            create_macs_b = (Button) findViewById(R.id.create_macs1);
            create_macs_b.setOnClickListener(create_macsl);
            create_addip_b = (Button) findViewById(R.id.create_addip1);
            create_addip_b.setOnClickListener(create_addipl);
            create_addmac_b = (Button) findViewById(R.id.create_addmac1);
            create_addmac_b.setOnClickListener(create_addmacl);
            create_createnetduino_b = (Button) findViewById(R.id.create_createnetduino);
            create_createnetduino_b.setOnClickListener(create_createnetduinol);


            create_ip_b = (Button) findViewById(R.id.create_ips);
            create_ip_b.setOnClickListener(create_ip_bl);
            create_sw_b = (Button) findViewById(R.id.create_macs);
            create_sw_b.setOnClickListener(create_sw_bl);
            create_b_ips_b = (Button) findViewById(R.id.create_addip);
            create_b_ips_b.setOnClickListener(create_b_ips_bl);
            create_b_macs_b = (Button) findViewById(R.id.create_addmac);
            create_b_macs_b.setOnClickListener(create_b_macs_bl);
            create_create_b = (Button) findViewById(R.id.create_create_b);
            create_create_b.setOnClickListener(create_create_bl);
            create_back_b = (Button) findViewById(R.id.create_back_b);
            create_back_b.setOnClickListener(create_back_bl);
            create_update_b = (Button) findViewById(R.id.create_udateip_b);
            create_update_b.setOnClickListener(create_update_bl);



        cipadapter.getitems().addAll(Devices.myips);
        cmacadapter.getitems().addAll(Devices.mymacs);
        cipbelladapter.getitems().addAll(Devices.myips);




           // list.setAdapter(ip_adapter);
        }


        @Override
        protected void onNewIntent (Intent intent)           //handle search
        {
            handleIntent(intent);
        }

    private void handleIntent(Intent intent)

    {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(getApplicationContext(), query,


                    Toast.LENGTH_LONG).show();
        }
    }


//v


    View.OnClickListener create_ip_bl  //snapshot button listner
            = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {

            cipadapter.renew();
            listc.setAdapter(cipadapter);


        }
    };
    View.OnClickListener create_sw_bl //snapshot button listner
            = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
          swadapter.renew();

            swadapter.getitems().add(new Switch("", 0, false));
            listc.setAdapter(swadapter);
//
        }
    };
    View.OnClickListener create_b_ips_bl  //snapshot button listner
            = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {

            cipbelladapter.renew();
            listc.setAdapter(cipbelladapter);
//
        }
    };
    View.OnClickListener create_b_macs_bl //snapshot button listner
            = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {




           cmacadapter.renew();


            listc.setAdapter(cmacadapter);

        }
    };


    View.OnClickListener create_create_bl //qrcode button listner
            = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
cipadapter.renew();
            cmacadapter.renew();
            cipbelladapter.renew();
            swadapter.renew();

            String req="create_devices_start Switches_start";
for(Switch i :swadapter.getitems()){

    req=req+i.getname()+"?"+i.getpin()+"?"+i.getstate()+"%";


}
            req=req.substring(0,req.length()-1);
            req=req+"Switches_end";


req=req+"Bell_start network_addrs_start";
            boolean b=false;
            for(IP i : cipbelladapter.getitems()){

                if (i.getcheckbox())
                    req=req+i.getip()+"?"+i.getport()+"?"+i.getname()+"%";
            b=true;
            }
            if(b){

                req=req.substring(0,req.length()-1);
            }
            b=false;
            req=req+"network_addrs_end  macs_start";
            for(Mac i:cmacadapter.getitems()){

                if (i.getcheckbox())
                    req=req+i.getname()+"?"+i.getname()+"?Mac%";
                b=true;

            }
            if(b){

                req=req.substring(0,req.length()-1);
            }
            req=req+"macs_end  Bell_end create_devices_end";
            IP ip=null;

            for (int i=0;i<cipadapter.getitems().size();i++){
                if(cipadapter.getitems().get(i).getcheckbox()==true){
                    ip=cipadapter.getitems().get(i); break;
                }}
            if(ip==null){ReadWrite.show("Plese select IP",Create_activity.this);
            }
            else{

                LinkedList<String>a=new LinkedList<String>();
                a.add(req);
ReadWrite.write("sad.txt",a,false,Create_activity.this);
       String res =HttpHandler.sendPost("http://"+ip.getip()+":"+ip.getport()+"/createdevices/",req,Create_activity.this);
ReadWrite.show(res,Create_activity.this);}


            //      Intent intent = new Intent(arg0.getContext(),ImageGridActivity.class);
            //    intent.putExtra("key", "qrcode");
            //  startActivity(intent);
        }
    };
    View.OnClickListener create_back_bl
            = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            switcher.showNext();


            // switcher.showPrevious();
        }
    };
    View.OnClickListener create_update_bl
            = new View.OnClickListener() {






        @Override
        public void onClick(View arg0) {
            //cipadapter.renew();
            String res;
             cipadapter.renew();

            IP ip=null;

            for (int i=0;i<cipadapter.getitems().size();i++){
                if(cipadapter.getitems().get(i).getcheckbox()==true){
                    ip=cipadapter.getitems().get(i); break;
                }}
                if(ip==null){ReadWrite.show("Plese select IP",Create_activity.this);
                }
            else{
                   res =Devices.getalldevices(ip,Create_activity.this);

                    if(res.compareTo("OK")==0){

                        cipbelladapter.getitems().clear();
                        cipbelladapter.getitems().addAll(Devices.myips);
                        cipbelladapter.getitems().addAll(Devices.bell.getips());
                        cmacadapter.getitems().clear();
                        cmacadapter.getitems().addAll(Devices.bell.getmacs());
                        cmacadapter.getitems().addAll(Devices.mymacs);
                        swadapter.textValues.clear();
                        swadapter.getitems().clear();
                       // swadapter.textValues.clear();
                        swadapter.getitems().addAll(Devices.switches);
                        ReadWrite.show("Updated Press Create",Create_activity.this);




                    }
                    else{

                        ReadWrite.show("Failed",Create_activity.this);

                    }


                }
                                   }



    };
    View.OnClickListener create_ipsl //snapshot button listner
            = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {



            rl.setVisibility(View.INVISIBLE);
            listv.setVisibility(View.VISIBLE);
            create_tv1.setText("YOUR IPS");
            vipadapter.getitems().clear();

               vipadapter.getitems().addAll(Devices.myips);
            listv.setAdapter(vipadapter);


           // list.setAdapter(ip_adapter);

            // swadapter.getitems().add(new Switch("Not set",0 ,false));
            //list.setAdapter(swadapter);
//            Intent intent = new Intent(arg0.getContext(),ImageGridActivity.class);
            //          intent.putExtra("key", "picture");
            //        startActivity(intent);
        }
    };
    View.OnClickListener create_macsl  //snapshot button listner
            = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            rl.setVisibility(View.INVISIBLE);
            listv.setVisibility(View.VISIBLE);
            create_tv1.setText("YOUR MACS");
            create_ft21.setText("Mac");
            vmacadapter.getitems().clear();
            vmacadapter.getitems().addAll(Devices.mymacs);

            listv.setAdapter(vmacadapter);


            //  list.setAdapter(ip_adapter);
//            Intent intent = new Intent(arg0.getContext(),ImageGridActivity.class);
            //          intent.putExtra("key", "picture");
            //        startActivity(intent);
        }
    };
    View.OnClickListener create_addipl//snapshot button listner
            = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            create_tv1.setText("ADD IP");
            listv.setVisibility(View.INVISIBLE);
            rl.setVisibility(View.VISIBLE);
            create_ft31.setVisibility(View.VISIBLE);
            create_ft21.setText("Mac");
            // create_ft31.setText("Port");

            create_ft31.setText("Port");
            //      Intent intent = new Intent(arg0.get
//            Intent intent = new Intent(arg0.getContext(),ImageGridActivity.class);
            //          intent.putExtra("key", "picture");
            //        startActivity(intent);
        }
    };


    View.OnClickListener create_add1l //qrcode button listner
            = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            if( create_tv1.getText().toString().compareTo("ADD MAC")==0)
            {Devices.mymacs.addLast(new Mac(create_ft11.getText().toString(), create_ft21.getText().toString()));
                Devices.writemymacs(Create_activity.this);

                cmacadapter.getitems().add(Devices.mymacs.getLast());
                vmacadapter.getitems().add(Devices.mymacs.getLast());
            }
            else
            if( create_tv1.getText().toString().compareTo("ADD IP")==0)
            {   Devices.myips.addLast(new IP(create_ft11.getText().toString(), create_ft21.getText().toString(), Integer.parseInt(create_ft31.getText().toString())));
//ReadWrite.show(Integer.parseInt(create_ft31.getText().toString())+"",Create_activity.this);

                Devices.writemyips(Create_activity.this);
                cipadapter.getitems().add(Devices.myips.getLast());
                cipbelladapter.getitems().add(Devices.myips.getLast());
               



            }

            //      Intent intent = new Intent(arg0.getContext(),ImageGridActivity.class);
            //    intent.putExtra("key", "qrcode");
            //  startActivity(intent);
        }
    };
    View.OnClickListener create_addmacl //qrcode button listner
            = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {
            create_tv1.setText("ADD MAC");
            listv.setVisibility(View.INVISIBLE);
            rl.setVisibility(View.VISIBLE);
            create_ft21.setText("Mac");
            // create_ft31.setText("Port");

            create_ft31.setVisibility(View.INVISIBLE);
            //      Intent intent = new Intent(arg0.getContext(),ImageGridActivity.class);
            //    intent.putExtra("key", "qrcode");
            //  startActivity(intent);
        }
    };
    View.OnClickListener create_createnetduinol
            = new View.OnClickListener() {

        @Override
        public void onClick(View arg0) {

            ReadWrite.show("HAHAH0",Create_activity.this);
            switcher.showPrevious();
        }
    };


    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Create_activity.this);
        // Setting Dialog Title
        alertDialog.setTitle("Item Not Found");
        // Setting Dialog Message
        alertDialog.setMessage("Do You Want to Send Photo or Qrcode via An Email ");
        // On pressing Settings button
        alertDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();     // Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                // mContext.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setPositiveButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        // if (id == R.id.action_settings) {
//
        //          if(a){a=false;
        //            switcher.showPrevious();
        //      }
        //    else { a=true;switcher.showNext();
        //  }
        //  return true;
        //}

//
        return false;
//        return super.onOptionsItemSelected(item);
    }


}