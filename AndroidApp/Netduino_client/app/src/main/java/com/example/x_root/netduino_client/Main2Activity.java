package com.example.x_root.netduino_client;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

public class Main2Activity extends Activity {


    Button maincreate1;
    Button maindevices1;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        maincreate1 = (Button) findViewById(R.id.maincreate111);
        maincreate1.setOnClickListener(maincreatel);
        File folder = new File(Environment.getExternalStorageDirectory() + "/Netduino_client");
        boolean success = true;
        if (!folder.exists()) {
            Toast.makeText(getApplicationContext(), "Folder Does not exist Creating....", Toast.LENGTH_LONG).show();
            success = folder.mkdir();
            if (success) {

                Toast.makeText(getApplicationContext(), "Folder Created", Toast.LENGTH_LONG).show();
                // Do something Toast.makeText(getApplicationContext(),  "Already Started", Toast.LENGTH_LONG).show();on success
            } else {
                Toast.makeText(getApplicationContext(), "Cuould not Create Appliction Folder", Toast.LENGTH_LONG).show();
                // Do something else on failure
            }}


        maindevices1 = (Button) findViewById(R.id.maindevices111);
        maindevices1.setOnClickListener(maindevicesl);
        Devices.readmyips(this);

        Devices.readmymacs(this);

        // Devices.initiate_reset();




    }


View.OnClickListener maincreatel //snapshot button listner
        = new View.OnClickListener() {

    @Override
    public void onClick(View arg0) {
        Intent intent = new Intent(arg0.getContext(),Create_activity.class);
        //          intent.putExtra("key", "picture");
                startActivity(intent);


    }
};
View.OnClickListener maindevicesl //snapshot button listner
        = new View.OnClickListener() {

    @Override
    public void onClick(View arg0) {

        Intent intent = new Intent(arg0.getContext(),Netduino.class);
        //          intent.putExtra("key", "picture");
        startActivity(intent);
    }
};}