package org.hep.afa.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.johnkil.print.PrintView;

import org.hep.afa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bogdan Melnychuk on 8/26/15.
 */
public class CategoryLabel extends LinearLayout {

    @BindView(R.id.icon)
    PrintView icon;
    @BindView(R.id.textLabel)
    TextView textLabel;

    public CategoryLabel(Context context) {
        super(context);
        init();

    }

    public CategoryLabel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_category_label, this, true);
        ButterKnife.bind(this, view);
    }

    public void setColor(int color) {
        this.icon.setIconColor(color);
        this.textLabel.setTextColor(color);
    }

    public void setText(String text) {
        this.textLabel.setText(text);
    }
}