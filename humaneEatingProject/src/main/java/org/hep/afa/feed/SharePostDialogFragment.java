package org.hep.afa.feed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.hep.afa.R;
import org.hep.afa.model.FacebookPost;

/**
 * Created by heather on 10/18/16.
 */

public class SharePostDialogFragment extends DialogFragment {

    public static final String POST = "post";

    public static SharePostDialogFragment newInstance(FacebookPost post) {

        Bundle args = new Bundle();
        args.putParcelable(POST, post);

        SharePostDialogFragment fragment = new SharePostDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final FacebookPost post = getArguments().getParcelable(POST);

        return new AlertDialog.Builder(getActivity())
            .setMessage(getString(R.string.feed_share_conf))
            .setPositiveButton(getString(R.string.share),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ShareLinkContent shareContent = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse(post.getPostLink()))
                            .setImageUrl(Uri.parse(post.getPostImageUrl()))
                            .build();
                        ShareDialog shareDialog = new ShareDialog(getActivity());
                        shareDialog.show(shareContent);
                    }
                })
            .setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing
                    }
                }
            )
            .create();
    }
}
