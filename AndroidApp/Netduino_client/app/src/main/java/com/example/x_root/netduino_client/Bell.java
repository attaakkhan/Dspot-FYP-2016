package com.example.x_root.netduino_client;

import java.util.LinkedList;

/**
 * Created by Attaullah Khan on 27/3/16.
 */
public class Bell {

    LinkedList<IP> ips;
    LinkedList<Mac> macs;

public Bell(){

    ips=new LinkedList<IP>();
    macs=new LinkedList<Mac>();

}

LinkedList<IP> getips(){return ips;}

LinkedList<Mac> getmacs(){return macs;}



}
