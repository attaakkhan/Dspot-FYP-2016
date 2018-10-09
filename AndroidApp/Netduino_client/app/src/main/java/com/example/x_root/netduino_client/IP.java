package com.example.x_root.netduino_client;

/**
 * Created by Attaullah Khan on 26/3/16.
 */
public class IP {

    String name;
    int port;
    String ip;
boolean checkbox=false;

    IP(String name, String ip, int port) {
        this.name = name;
        this.port = port;
        this.ip = ip;
    }

    String getname() {
        return name;
    }

    String getip() {
        return ip;
    }
    void setcheckbox(boolean a){checkbox=a;}
    boolean getcheckbox(){return  checkbox;}

    int getport() {
        return port;
    }

}
