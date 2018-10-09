package com.example.x_root.netduino_client;

/**
 * Created by root on 7/8/15.
 */
public class Switch {
    String name;
   int pin;
    Boolean state;


    Switch(String name,int pin,Boolean state){
        this.name=name;
        this.pin=pin;
        this.state=state;
        }

    String getname()
    {
        return name;
    }

    int getpin()
    {
        return pin;
    }
    Boolean getstate()
    {
        return state;
    }


    void setpin(int pin){
    this.pin=pin;

}
    void setname(String name){
        this.name=name;

    }

}
