package org.hep.afa.feed;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.hep.afa.R;
import org.hep.afa.feed.CommentsFragment;
import org.hep.afa.feed.FeedBottomSheetDialogFragment;
import org.hep.afa.feed.FeedDialogFragment;
import org.hep.afa.feed.FeedFragment;
import org.hep.afa.model.FacebookPost;
import org.hep.afa.utils.FacebookUtils;

/**
 * Created by heather on 10/12/16.
 */

public class FeedActivity extends AppCompatActivity
    implements View.OnClickListener, FeedBottomSheetDialogFragment.FeedActionListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_feed);

        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        configureActionBar();

        getSupportFragmentManager().beginTransaction()
            .add(R.id.feed_activity_fragment_holder, new FeedFragment())
            .commit();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Uri data = intent.getData();
        if (data != null) {

            String url = data.toString();

            if (intent.getScheme().equals(getString(R.string.hashtag_scheme))) {

                String hashtag = url.substring(url.indexOf("#"));
                FeedDialogFragment dialogFragment = FeedDialogFragment.newInstance(hashtag);
                dialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_fragment_tag));
            }
            else if (intent.getScheme().contains("url")) {

                try {
                    String urlString = url.substring(FacebookUtils.HTTP_SHEME.length());
                    FeedDialogFragment dialogFragment = FeedDialogFragment.newInstance(urlString);
                    dialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_fragment_tag));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        updateActionBar(getString(R.string.feed_header_actionbar));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void configureActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setCustomView(R.layout.action_bar_feed_layout);
        actionBar.setDisplayShowCustomEnabled(true);
        findViewById(R.id.feed_share_button).setOnClickListener(this);
        Toolbar toolbar = (Toolbar) actionBar.getCustomView().getParent();
        toolbar.setContentInsetsAbsolute(0,0);
    }

    private void updateActionBar(String title) {
        View v = getSupportActionBar().getCustomView();
        TextView titleTxtView = (TextView) v.findViewById(R.id.action_bar_title);
        titleTxtView.setText(title);

        if (title.equals(getString(R.string.comments_actionbar))) {
            v.findViewById(R.id.feed_share_button).setVisibility(View.INVISIBLE);
        }
        else {
            v.findViewById(R.id.feed_share_button).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.feed_share_button:
                FeedDialogFragment dialogFragment =
                    FeedDialogFragment.newInstance(FacebookUtils.HEP_FB_URL, getString(R.string.feed_userpage_conf));
                dialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_fragment_tag));
                break;
        }
    }

    private void showNoCommentsDialog() {
        new AlertDialog.Builder(this)
            .setMessage(getString(R.string.feed_no_comments))
            .setNeutralButton(getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing
                    }
                }
            )
            .create().show();
    }

    @Override
    public void onShowComments(FacebookPost post) {

        if (post.getComments().size() > 0) {
            getSupportFragmentManager().beginTransaction()
                .add(R.id.feed_activity_fragment_holder, CommentsFragment.newInstance(post.getComments()))
                .addToBackStack(CommentsFragment.class.getSimpleName())
                .commit();
            updateActionBar(getResources().getString(R.string.comments_actionbar));
        }
        else {
            showNoCommentsDialog();
        }
    }

    @Override
    public void onShareWithFacebook(FacebookPost post) {
        ShareLinkContent shareContent = new ShareLinkContent.Builder()
            .setContentUrl(Uri.parse(post.getPostLink()))
            .setImageUrl(Uri.parse(post.getPostImageUrl()))
            .build();
        ShareDialog shareDialog = new ShareDialog(this);
        shareDialog.show(shareContent);
    }
}
