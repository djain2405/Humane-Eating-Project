package org.hep.afa.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import org.hep.afa.R;

/**
 * Created by Bogdan Melnychuk on 8/26/15.
 */
public class InfOther extends LinearLayout {
    public InfOther(Context context) {
        super(context);
        init();

    }

    public InfOther(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_inf_other, this, true);
    }
}