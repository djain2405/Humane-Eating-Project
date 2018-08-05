package org.hep.afa.takeaction;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;

import org.hep.afa.R;
import org.hep.afa.activity.LearnActivity;
import org.hep.afa.transform.BlurBuilder;
import org.hep.afa.utils.GeneralUtils;

import static org.hep.afa.takeaction.TakeActionActivity.TakeActionMenuItem.TAKE_ACTION_DONATE;

/**
 * Created by heather on 10/27/16.
 */

public class TakeActionActivity extends AppCompatActivity
    implements TakeActionMenuFragment.MenuItemSelectedListener, View.OnClickListener {

    public enum TakeActionMenuItem {
        TAKE_ACTION_DONATE,
        TAKE_ACTION_SURVEY,
        TAKE_ACTION_SHOUT_OUT,
        TAKE_ACTION_INVITE,
        TAKE_ACTION_ADD_RESTAURANT,
        TAKE_ACTION_LEARN,
        TAKE_ACTION_TALK,
        TAKE_ACTION_VOLUNTEER
    }

    private static Bitmap backgroundBitmap;

    public static Bitmap getBackgroundBitmap() {
        return backgroundBitmap;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_take_action);

        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        configureActionBar();

        getSupportFragmentManager().beginTransaction()
            .add(R.id.takeaction_activity_fragment_holder, new TakeActionMenuFragment())
            .commit();

    }

    @Override
    protected void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this, "Y8B9D2MQCFMCP2KJ5KVT");
    }

    @Override
    protected void onStop() {
        super.onStop();
        FlurryAgent.onEndSession(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (backgroundBitmap != null) {
            backgroundBitmap.recycle();
            backgroundBitmap = null;
        }
    }

    private void configureActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setCustomView(R.layout.action_bar_take_action_layout);
        actionBar.setDisplayShowCustomEnabled(true);
        findViewById(R.id.invite_button).setOnClickListener(this);
        Toolbar toolbar = (Toolbar) actionBar.getCustomView().getParent();
        toolbar.setContentInsetsAbsolute(0,0);
    }

    private void updateActionBarTitle(String title) {
        View v = getSupportActionBar().getCustomView();
        TextView titleTxtView = (TextView) v.findViewById(R.id.action_bar_title);
        titleTxtView.setText(title);

        if (title.equals(getString(R.string.take_action_actionbar_invite_title))) {
            v.findViewById(R.id.invite_button).setVisibility(View.VISIBLE);
        }
        else {
            v.findViewById(R.id.invite_button).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.takeaction_activity_fragment_holder);
        if (fragment instanceof TakeActionWebFragment) {
            if (((TakeActionWebFragment) fragment).canGoBack()) {
                return;
            }
        }
        super.onBackPressed();
        updateActionBarTitle(getString(R.string.take_action));
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

    @Override
    public void onMenuItemSeleted(TakeActionMenuItem selectedItem) {
        switch (selectedItem) {
            case TAKE_ACTION_DONATE:
                Intent donateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_donate)));
                if (GeneralUtils.isIntentSafe(donateIntent)) {
                    startActivity(donateIntent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
                }
                break;

            case TAKE_ACTION_SURVEY:
                Intent surveyIntent =
                    new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.survey_url)));
                if (GeneralUtils.isIntentSafe(surveyIntent)) {
                    startActivity(surveyIntent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
                }
                break;

            case TAKE_ACTION_LEARN:
                startActivity(new Intent(this, LearnActivity.class));
                overridePendingTransition(R.anim.slide_up2, R.anim.slide_down2);
                break;

            case TAKE_ACTION_ADD_RESTAURANT:
                getSupportFragmentManager().beginTransaction()
                    .add(R.id.takeaction_activity_fragment_holder,
                        TakeActionWebFragment.newInstance("http://mobileapp.humaneeatingproject.org/listings/addListing.html"))
                    .addToBackStack(TakeActionWebFragment.class.getSimpleName())
                    .commit();
                updateActionBarTitle(getString(R.string.take_action_actionbar_add_listing));
                break;

            case TAKE_ACTION_TALK:
                getSupportFragmentManager().beginTransaction()
                    .add(R.id.takeaction_activity_fragment_holder,
                        TakeActionWebFragment.newInstance("http://mobileapp.humaneeatingproject.org/info/info.html#talkingpoints"))
                    .addToBackStack(TakeActionWebFragment.class.getSimpleName())
                    .commit();
                break;

            case TAKE_ACTION_VOLUNTEER:
                getSupportFragmentManager().beginTransaction()
                    .add(R.id.takeaction_activity_fragment_holder, new TakeActionVolunteerFragment())
                    .addToBackStack(TakeActionVolunteerFragment.class.getSimpleName())
                    .commit();
                updateActionBarTitle(" ");
                break;

            case TAKE_ACTION_INVITE:
                getSupportFragmentManager().beginTransaction()
                    .add(R.id.takeaction_activity_fragment_holder, new InviteFragment())
                    .addToBackStack(InviteFragment.class.getSimpleName())
                    .commit();
                updateActionBarTitle(getString(R.string.take_action_actionbar_invite_title));
                break;

            case TAKE_ACTION_SHOUT_OUT:
                if (backgroundBitmap == null) {
                    backgroundBitmap = BlurBuilder.blur(findViewById(android.R.id.content).getRootView());
                }
                ShoutOutDialogFragment dialogFragment = new ShoutOutDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), getString(R.string.dialog_fragment_tag));
                break;

        }
    }


    @Override
    public void onClick(View view) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.takeaction_activity_fragment_holder);
        if (fragment instanceof InviteFragment) {
            ((InviteFragment) fragment).inviteFriends();
        }
    }
}
