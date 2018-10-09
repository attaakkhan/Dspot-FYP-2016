package com.example.x_root.netduino_client;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.widget..SearchView;
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

public class MainActivity extends Activity{
    Button captureB,qrcodeB,searchB,backB;


    ViewSwitcher switcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);
        // showSettingsAlert();
      //  switcher = (ViewSwitcher) findViewById(R.id.ViewSwitcheremail);
       //switcher.showNext();
        Devices.readmyips(this);
        Devices.readmymacs(this);

       // Devices.initiate_reset();






//((Creatlist_switches_adapter)list.getAdapter()).type.



        //searchB=(Button)findViewById(R.id.searchb);


        //captureB.setOnClickListener(captureBListener);
   //     qrcodeB=(Button)findViewById(R.id.qrcodeb);
     //   qrcodeB.setOnClickListener(qrcodeBListener);
       // backB=(Button)findViewById(R.id.backb);
       // backB.setOnClickListener(backBListener);

    }


    @Override
    protected void onNewIntent(Intent intent)           //handle search
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









    public  void  showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        // Setting Dialog Title
        alertDialog.setTitle("Item Not Found");
        // Setting Dialog Message
        alertDialog.setMessage("Do You Want to Send Photo or Qrcode via An Email ");
        // On pressing Settings button
        alertDialog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
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
        if (id == R.id.action_settings) {

            //if(a){a=false;
              //  switcher.showPrevious();
            //}
            //else { a=true;switcher.showNext();
            //}
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
