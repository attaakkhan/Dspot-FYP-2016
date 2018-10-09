package com.example.x_root.netduino_client;

/**
 * Created by Attaullah Khan on 26/3/16.
 */
public class Mac {
    String name;
    String mac;
    String status;
    boolean checkbox=false;


    Mac(String name, String mac) {
        this.name = name;
        this.mac = mac;
       // this.ip = ip;
    }

    String getname() {
        return name;
    }
    void setcheckbox(boolean a){checkbox=a;}
    boolean getcheckbox(){return  checkbox;}
    String getmac() {
        return mac;
    }

void setStatus(String status){
    this.status=status;
}
    String getstatus() {
        return status;
    }
}
