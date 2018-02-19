package org.hep.afa.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by heatherlaurin on 1/25/18.
 *
 * Parent view that allows us to specify it should intercept all touch events from
 * children (e.g. a click away to dismiss view)
 */

public class ParentFrameLayout extends FrameLayout {

    private boolean shouldIntercept;

    public ParentFrameLayout(Context context) {
        super(context);
    }

    public ParentFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void shouldInterceptTouch(boolean shouldIntercept) {
        this.shouldIntercept = shouldIntercept;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (shouldIntercept) {
            return true;
        }
        return false;
    }
}
