package org.hep.afa.utils;

import android.graphics.Color;

/**
 * Color specific utility class.
 * @ToDo: Add all reusable methods here related to Colors here.
 */
public class ColorUtils
{
    /**
     * Returns alternating colors for odd/even positions. This is useful for listview items.
     * @param position
     * @return even: White, odd: Light Brown.
     */
    public static int alternateColor(int position)
    {
        return (position % 2 == 0) ? Color.parseColor("#E6DFCE") : Color.WHITE;
    }

}
