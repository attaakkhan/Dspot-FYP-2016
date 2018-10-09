package com.example.x_root.netduino_client;

import java.util.Comparator;

/**
 * Created by Attaullah Khan on 7/17/15.
 */
public class Mycartcomparator implements Comparator<CartItem> {
    @Override
    public int compare(CartItem i1, CartItem i2) {
        if (i1.getquantity() < i2.getquantity()) {
            return 1;
        } else {
            return -1;
        }
    }
}
