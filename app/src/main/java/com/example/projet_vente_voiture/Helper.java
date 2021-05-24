package com.example.projet_vente_voiture;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Environment;
import android.view.Display;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Helper {

    public static int getScreenWidth(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static String getDrawablePath(Resources r, int i) {

        Bitmap bitmap = BitmapFactory.decodeResource(r, i);
        File mFile1 = Environment.getExternalStorageDirectory();
        String fileName = "img1.jpg";
        File mFile2 = new File(mFile1, fileName);

        try {
            FileOutputStream outStream = new FileOutputStream(mFile2);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);

            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  mFile1.getAbsolutePath() + "/" + fileName;
    }

    public static void copy(File src, File dst) throws IOException {
        try (InputStream in = new FileInputStream(src)) {
            try (OutputStream out = new FileOutputStream(dst)) {
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
    }

    public static Bitmap makeMiniBitmap(Bitmap bitmap, int diviseur){
        return Bitmap.createScaledBitmap(bitmap,bitmap.getWidth()/diviseur,bitmap.getHeight()/diviseur,false);
    }
}
