package com.example.x_root.netduino_client;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Attaullah Khan on 8/19/15.
 */
public class Griditem {
   private Uri path;
  private  Bitmap bitmap;
   // int price;
//Context a;
    String realpath;
    public Griditem(Uri path,String realpath) {
        this.path = path;
        this.realpath=realpath;
        storeimage();
        //a=cc;
        //  this.price=price;
    }

    private void storeimage() {
        File imageFile =new File(realpath);
        //Toast.makeText(this.a, imageFile.getAbsolutePath(), Toast.LENGTH_LONG).show();

        //Bitmap bitmap2 = BitmapFactory.decodeFile(imageFile2.getAbsolutePath());
        // a.setImageBitmap(bitmap2);
        //txttitle.setText(paths.get(position).getLastPathSegment());
        if (imageFile.exists()) { //Drawable myDrawable = getResources().getDrawable(R.mipmap.ic_launcher);
            //a.setImageDrawable(myDrawable);
          //  Toast.makeText(this.a, imageFile.getAbsolutePath()+"afafa", Toast.LENGTH_LONG).show();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            //Toast.makeText(getApplicationContext(), imageFile2.getPath()+"sfasfasfasf", Toast.LENGTH_LONG).show();
            bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

        }
    }

    Uri getpath() {
        return path;
    }
    String getrealpath() {
        return realpath;
    }

    Bitmap getbitmap() {
        return bitmap;
    }


}
