package org.hep.afa.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.github.johnkil.print.PrintDrawable;

import org.hep.afa.R;

/**
 * Created by Bogdan Melnychuk on 8/24/15.
 */
public class IconUtils {
    /**
     * Color white; font - default
     *
     * @return
     */
    public static Drawable getIcon(Context context, int textRes, int colorRes) {
        return new PrintDrawable.Builder(context)
                .iconTextRes(textRes)
                .iconColorRes(colorRes)
                .iconSizeRes(R.dimen.fab_icon_size)
                .build();
    }

    public static Drawable getIcon(Context context, int textRes) {
        return getIcon(context, textRes, android.R.color.white);
    }
}
