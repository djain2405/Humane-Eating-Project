package org.hep.afa.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.johnkil.print.PrintButton;

import org.hep.afa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by bogdan.melnychuk on 29.12.2014.
 */
public class DetailsItemView extends RelativeLayout {
    @BindView(R.id.detail_icon)
    PrintButton icon;
    @BindView(R.id.detail_value)
    TextView value;
    @BindView(R.id.detail_additional_info)
    TextView additionalInfo;


    public DetailsItemView(Context context) {
        super(context);
        init();

    }

    public DetailsItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_details_item, this, true);
        ButterKnife.bind(this, view);
    }

    public void setIcon(int iconResId) { icon.setIconTextRes(iconResId); }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setIconImage(Drawable image) {icon.setBackground(image);}


    public void setIconOnClickListener(View.OnClickListener listener) {
        icon.setOnClickListener(listener);
    }

    public PrintButton getIcon() {
        return icon;
    }

    public void setValue(String textValue) {
        this.value.setText(textValue);
    }

    public void setAdditionalInfo(String textLabel) {
        if (textLabel == null || TextUtils.isEmpty(textLabel)) {
            this.additionalInfo.setVisibility(View.GONE);
        } else {
            this.additionalInfo.setText(textLabel);
            this.additionalInfo.setVisibility(VISIBLE);
        }
    }
}
