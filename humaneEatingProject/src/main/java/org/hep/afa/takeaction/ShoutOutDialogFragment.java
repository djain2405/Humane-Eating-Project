package org.hep.afa.takeaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.hep.afa.R;
import org.hep.afa.utils.GeneralUtils;
import org.hep.afa.utils.LaunchUtils;

/**
 * Created by heather on 11/3/16.
 */

public class ShoutOutDialogFragment extends DialogFragment implements View.OnClickListener {

    private ShoutOutListener listener;

    public interface ShoutOutListener {
        void onFacebookSelected();
        void onTwitterSelected();
    }

    public void setShoutOutListener(ShoutOutListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, getActivity().getApplicationInfo().theme);
        setStyle(DialogFragment.STYLE_NO_TITLE, getActivity().getApplicationInfo().theme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_takeaction_shoutout, container, false);

        initViews(view);

        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.HEP_FullScreenDialog;
    }


    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        Bitmap backgroundBitmap = LaunchUtils.getBackgroundBitmap(getActivity());

        if (backgroundBitmap != null) {
            BitmapDrawable drawable = new BitmapDrawable(getActivity().getResources(), backgroundBitmap);
            getDialog().getWindow().setBackgroundDrawable(drawable);
        }
    }

    private void initViews(View rootView) {
        rootView.findViewById(R.id.close_share_button).setOnClickListener(this);
        rootView.findViewById(R.id.share_fb_clickable).setOnClickListener(this);
        rootView.findViewById(R.id.share_twitter_clickable).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.close_share_button:
                dismiss();
                break;

            case R.id.share_fb_clickable:
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse(getString(R.string.facebook_share_url)))
                    .build();
                ShareDialog.show(this, content);
                dismiss();
                if (listener != null) {
                    listener.onFacebookSelected();
                }
                break;

            case R.id.share_twitter_clickable:
                String tweetUrl = getString(R.string.url_tweet_template) + getString(R.string.tweet_body);
                Uri uri = Uri.parse(tweetUrl);

                Intent tweetIntent = new Intent(Intent.ACTION_VIEW, uri);

                if (GeneralUtils.isIntentSafe(tweetIntent)) {
                    startActivity(tweetIntent);
                }
                else {
                    new AlertDialog.Builder(getContext())
                        .setMessage(getString(R.string.take_action_no_twitter))
                        .setNeutralButton(getString(R.string.ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    // Do nothing
                                }
                            }
                        )
                        .create().show();
                }
                dismiss();
                if (listener != null) {
                    listener.onTwitterSelected();
                }
                break;
        }
    }
}
