package org.hep.afa.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.github.johnkil.print.PrintView;

import org.hep.afa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bogdan Melnychuk on 9/13/15.
 */
public class RatingBar extends LinearLayout {
    @BindView(R.id.star1)
    PrintView star1;
    @BindView(R.id.star2)
    PrintView star2;
    @BindView(R.id.star3)
    PrintView star3;
    @BindView(R.id.star4)
    PrintView star4;
    @BindView(R.id.star5)
    PrintView star5;

    PrintView[] stars;

    public RatingBar(Context context) {
        super(context);
        init();
    }

    public RatingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RatingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_rating_stars, this, true);
        ButterKnife.bind(this, view);
        stars = new PrintView[]{star1, star2, star3, star4, star5};
    }

    public void setRating(double value) {
        for (int i = 0; i < stars.length; i++) {
            PrintView currentStar = stars[i];
            double diff = value - i;
            if (diff >= 1) {
                currentStar.setIconTextRes(R.string.ic_star);
            } else if (diff <= 0) {
                currentStar.setIconTextRes(R.string.ic_star_outline);
            } else {
                currentStar.setIconTextRes(R.string.ic_star_half);
            }
        }
    }

}
