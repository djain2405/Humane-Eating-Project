package org.hep.afa.transform;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.Element;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;
import android.view.View;

/**
 * Created by heather on 11/10/16.
 * From http://stackoverflow.com/questions/6795483/create-blurry-transparent-background-effect
 * with a few modifications
 */

public class BlurBuilder {

    private static final float BLUR_RADIUS = 25.0f;

    public static Bitmap blur(View v) {
        return blur(v.getContext(), getScreenshot(v));
    }

    private static Bitmap getScreenshot(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.draw(c);
        return b;
    }

    public static Bitmap blur(Context ctx, Bitmap image) {

        Bitmap outBitmap = Bitmap.createBitmap(image.getWidth(), image.getHeight(), Bitmap.Config.ARGB_8888);

        RenderScript rs = RenderScript.create(ctx);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, image);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outBitmap);

        // cleanup
        image.recycle();
        rs.destroy();

        return outBitmap;
    }
}
