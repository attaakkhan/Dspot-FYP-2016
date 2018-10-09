package com.example.x_root.netduino_client;

import java.util.Comparator;

/**
 * Created by Attaullah Khan on 7/15/15.
 */
public class Mycomparator implements Comparator<Switch> {

        @Override
        public int compare(Switch i1, Switch i2) {
            if(i1.getpin() < i2.getpin()){
                return 1;
            } else {
                return -1;
            }
        }
    }
