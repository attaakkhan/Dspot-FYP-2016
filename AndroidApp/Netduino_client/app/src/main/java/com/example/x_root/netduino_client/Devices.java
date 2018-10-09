package com.example.x_root.netduino_client;

import android.content.Context;

import java.util.LinkedList;

/**
 * Created by Attaullah Khan on 27/3/16.
 */
public class Devices {

    public static  LinkedList<Switch> switches=new LinkedList<Switch>();;
    public static  Bell bell=new Bell();;
    public static LinkedList<Mac> mymacs=new LinkedList<Mac> ();
    public static LinkedList<IP> myips=new LinkedList<IP>();;











 public  static String writeswitches(LinkedList<Switch> ll,IP ip,Context c){

    String req="";
     for (Switch i:ll)
         req=req+i.getpin()+"?"+i.getstate().toString()+"%";

req=req.substring(0,req.length()-1);


     String res =HttpHandler.sendPost("http://"+ip.getip()+":"+ip.getport()+"/writeswitches/",req,c);
String []sp;
     sp=res.split("%");
     String[] sp2;
     int ii=0;
     boolean kk=true;

     if(sp.length==ll.size()){

         for(String i:sp){
             sp2=i.split("\\?");
             if (Boolean.parseBoolean(sp2[1])!=ll.get(ii).getstate())
                 kk=false;
             ii++;



         }if(kk)res="OK";

         else
         res="error";}
         else


         res="error";




     return res;






     }



    // String res=HttpHandler.sendPost("http://" + ip.getip() + ":" + ip.getport() + "/getalldevices/", c);






public  static  String getalldevices(IP ip,Context c){


    String res=HttpHandler.sendGet("http://" + ip.getip() + ":" + ip.getport() + "/getalldevices/", c);

    res=ReadWrite.getBetweenStrings(res, "get_all_devices_start", "get_all_devices_end");


    if(res.compareTo("")!=0) {
        String sws = ReadWrite.getBetweenStrings(res, "Switches_start", "Switches_end");
        String macs = ReadWrite.getBetweenStrings(res, "macs_start", "macs_end");
        String ips = ReadWrite.getBetweenStrings(res, "network_addrs_start", "network_addrs_end");
        String[] sp;
        // char s='?'
        String[] spmacs = macs.split("%");
        String[] spips = ips.split("%");
        String[] spsw = sws.split("%");

        bell = new Bell();
        for (int i = 0; i < spmacs.length; i++) {
            sp = spmacs[i].split("\\?");


            bell.getmacs().addLast(new Mac(sp[1], sp[0]));
            bell.getmacs().getLast().setcheckbox(true);
        }


        for (int i = 0; i < spips.length; i++) {
            sp = spips[i].split("\\?");


            bell.getips().addLast(new IP(sp[2], sp[0], Integer.parseInt(sp[1])));

            bell.getips().getLast().setcheckbox(true);
        }

        switches.clear();

        for (int i=0;i<spsw.length;i++){
            sp=spsw[i].split("\\?");


       switches.add(new Switch(sp[0], Integer.parseInt(sp[1]), Boolean.parseBoolean(sp[2])));


        }

       return "OK";
    }

return "error";

}

    public static void initiate_reset(){

        switches=new LinkedList<Switch>();
        bell=new Bell();
        mymacs=new LinkedList<Mac> ();
        myips=new LinkedList<IP>();


    }


    public static void readmyips(Context c){
        LinkedList <String> res=ReadWrite.read("myips.txt", c);
          String [] sp;
        myips.clear();
        for (int i=0;i<res.size();i++){

           sp= res.get(i).split("#");
            if(sp.length>2){

                myips.add(new IP(sp[2],sp[0],Integer.parseInt(sp[1])));
            }

        }}



    public static void readmymacs(Context c){


        LinkedList <String> res=ReadWrite.read("mymacs.txt", c);
        String [] sp;
        mymacs.clear();
        for (int i=0;i<res.size();i++){

            sp= res.get(i).split("#");
            if(sp.length>2){

                mymacs.add(new Mac(sp[1],sp[0]));
            }



    }}

public static void writemyips(Context c){
    LinkedList<String> req=new LinkedList<String>();

    for(int i=0;i<myips.size();i++){
        req.add(myips.get(i).getip()+"#"+myips.get(i).getport()+"#"+myips.get(i).getname());


    }
    ReadWrite.write("myips.txt", req, false, c);
    ReadWrite.show("hahah",c);




}
    public static void writemymacs(Context c){
        LinkedList<String> req=new LinkedList<String>();

        for(int i=0;i<mymacs.size();i++){
            req.add(mymacs.get(i).getmac()+"#"+mymacs.get(i).getname()+"#"+"Mac");


        }
        ReadWrite.write("mymacs.txt",req,false,c);




    }



}












