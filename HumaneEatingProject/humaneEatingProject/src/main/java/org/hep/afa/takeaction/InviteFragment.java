package org.hep.afa.takeaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import org.hep.afa.R;
import org.hep.afa.utils.GeneralUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by heather on 10/31/16.
 */

public class InviteFragment extends Fragment
    implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String[] PROJECTION = {
        ContactsContract.Contacts._ID,
        ContactsContract.Contacts.DISPLAY_NAME,
        ContactsContract.CommonDataKinds.Email.DATA
    };

    private static final String SELECTION =
        ContactsContract.CommonDataKinds.Email.ADDRESS + " IS NOT NULL";


    public static final int CONTACT_ID_INDEX = 0;
    public static final int DISPLAY_NAME_INDEX = 1;
    public static final int EMAIL_INDEX = 2;

    ListView listView;
    TakeActionContactsAdapter adapter;
    ShareButton fbButton;
    View twitterButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_takeaction_invite, container, false);

        initViews(view);

        View headerView = inflater.inflate(R.layout.list_header_takeaction_invite, null);
        initHeaderView(headerView);
        listView.addHeaderView(headerView);



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // initialize loader
        getLoaderManager().initLoader(0, null, this);
        adapter = new TakeActionContactsAdapter(getContext(), null, 0);
        listView.setAdapter(adapter);
    }

    private List<String> getSelectedFriends() {
        if (adapter == null) {
            return new ArrayList<>();
        }
        return adapter.getSelectedContacts();
    }

    public void inviteFriends() {
        List<String> emails = getSelectedFriends();

        // allow even empty list to go through, the user can add email addresses in the client
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, emails.toArray(new String[0]));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_invitation_subject));
        emailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_invitation_body));

        if (GeneralUtils.isIntentSafe(emailIntent)) {
            startActivity(emailIntent);
            getActivity().overridePendingTransition(R.anim.slide_up2, R.anim.slide_down2);
        } else {
            new AlertDialog.Builder(getContext())
                .setMessage(getString(R.string.email_no_client))
                .setNeutralButton(getString(R.string.ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // Do nothing
                        }
                    }
                )
                .create().show();
        }
    }

    private void initHeaderView(View view) {
        fbButton = (ShareButton) view.findViewById(R.id.takeaction_invite_fb);

        ShareDialog shareDialog = new ShareDialog(this);
        if (shareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(getString(R.string.facebook_share_url)))
                    .build();
            fbButton.setShareContent(content);
        }

        twitterButton = view.findViewById(R.id.takeaction_invite_twitter);
        twitterButton.setOnClickListener(this);
    }

    private void initViews(View view) {
        listView = (ListView) view.findViewById(R.id.takeaction_invite_listview);

    }


    private void shareOnTwitter() {
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
    }

    private void shareOnFacebook() {
        ShareDialog shareDialog = new ShareDialog(this);
        if (shareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent content = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(getString(R.string.facebook_share_url)))
                    .build();
            fbButton.setShareContent(content);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.takeaction_invite_twitter:
                shareOnTwitter();
                break;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, SELECTION, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
