package org.hep.afa.feed;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import org.hep.afa.R;
import org.hep.afa.utils.FacebookUtils;

import java.util.List;

/**
 * Created by heather on 10/9/16.
 */

public class FeedDialogFragment extends DialogFragment {

    public static final String LINK = "link";
    public static final String USER_MSG = "user_msg";


    /**
     * Creates a DialogFragment for the Feed dialogs.  Uses default user message for confirmation
     *
     * @param link   The #hashtag or http:// url the dialog should link to on user confirmation
     */
    public static FeedDialogFragment newInstance(String link) {

        Bundle args = new Bundle();
        args.putString(LINK, link);

        FeedDialogFragment fragment = new FeedDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Creates a DialogFragment for the Feed dialogs
     *
     * @param link   The #hashtag or http:// url the dialog should link to on user confirmation
     * @param userMessage - Optionally used for urls.  If non-null, the provided user message
     *                    will be used instead of the default user message.
     * @return
     */
    public static FeedDialogFragment newInstance(String link, String userMessage) {

        Bundle args = new Bundle();
        args.putString(LINK, link);
        args.putString(USER_MSG, userMessage);

        FeedDialogFragment fragment = new FeedDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final String link = getArguments().getString(LINK);
        final String message = getArguments().getString(USER_MSG, null);

        if (isHashtag(link)) {
            return createHashtagDialog(link);
        }
        return createUrlDialog(link, message);

    }

    private Dialog createHashtagDialog(final String hashtag) {
        return new AlertDialog.Builder(getActivity())
            .setMessage(getString(R.string.hashtag_user_conf, hashtag))
            .setPositiveButton(getString(R.string.open),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Intent browserIntent =
                            new Intent(Intent.ACTION_VIEW,
                                Uri.parse(String.format(FacebookUtils.FB_HASHTAG_URL, hashtag.substring(1))));

                        if (isIntentSafe(browserIntent)) {
                            startActivity(browserIntent);
                            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
                        }
                    }
                }
            )
            .setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing
                    }
                }
            )
            .create();
    }

    private Dialog createUrlDialog(final String url, final String userMessage) {
        String message;
        if (userMessage != null && !userMessage.isEmpty()) {
            message = userMessage;
        }
        else {
            message = getString(R.string.url_user_conf);
        }
        return new AlertDialog.Builder(getActivity())
            .setMessage(message)
            .setPositiveButton(getString(R.string.open),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        Intent browserIntent =
                            new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                        if (isIntentSafe(browserIntent)) {
                            startActivity(browserIntent);
                            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
                        }
                    }
                }
            )
            .setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing
                    }
                }
            )
            .create();
    }

   private boolean isHashtag(String link) {
       if (link != null && link.contains("#")) {
           return true;
       }
       return false;
   }

    private boolean isIntentSafe(Intent intent) {
        PackageManager packageManager = getActivity().getPackageManager();
        List activities = packageManager.queryIntentActivities(intent,
            PackageManager.MATCH_DEFAULT_ONLY);
        return activities.size() > 0;
    }
}
