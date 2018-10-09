package com.example.x_root.netduino_client;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Attaullah Khan on 28/3/16.
 */
public class ReadWrite {



    public static void show(String str,Context c){
        Toast.makeText(c, str,
                Toast.LENGTH_LONG).show();
    }

    public static String getBetweenStrings(
            String text,
            String textFrom,
            String textTo) {

        String result = "";

        // Cut the beginning of the text to not occasionally meet a
        // 'textTo' value in it:

     try{   result =
                text.substring(
                        text.indexOf(textFrom) + textFrom.length(),
                        text.length());

        // Cut the excessive ending of the text:
        result =
                result.substring(
                        0,
                        result.indexOf(textTo));}
     catch (Exception e){ result="";}

        return result;
    }
public static LinkedList<String> read(String name,Context c){



    BufferedReader br = null;
FileReader fr;
        LinkedList<String> res=new LinkedList<String>();
    try {

        String sCurrentLine;

        br = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory() + "/Netduino_client/" + name));

        while ((sCurrentLine = br.readLine()) != null) {
        res.add(sCurrentLine.toString());



        }


    } catch (IOException e) {
        show(e.toString(),c);
    } finally {
        try {
            if (br != null)br.close();
        } catch (IOException ex) {

        }


           return res;    }

}





public static void write(String name,LinkedList<String> lines,boolean append,Context c) {
    // creates the file
    // file.createNewFile();
    // creates a FileWriter Object
    FileWriter fw=null;
    BufferedWriter bw=null;
    try {

        //Specify the file name and path here
        File file = new File(Environment.getExternalStorageDirectory() + "/Netduino_client/" + name);


	 /* This logic will make sure that the file
	  * gets created if it is not present at the
	  * */
        if (!file.exists()) {
            file.createNewFile();
        }
        if (append)
        {
            fw=new FileWriter(file,true);

        }
else fw=new FileWriter(file);


        bw = new BufferedWriter(fw);
        for (int i = 0; i < lines.size(); i++) {
            bw.write(lines.get(i));
            bw.newLine();
        }


    } catch (IOException ioe) {
        show(ioe.toString(),c);
    } finally {
        try {
            if (bw != null)
                bw.close();
            if (fw!=null)
                fw.close();


        } catch (Exception ex) {

        }
    }
}}




