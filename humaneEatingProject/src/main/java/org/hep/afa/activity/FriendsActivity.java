package org.hep.afa.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.flurry.android.FlurryAgent;

import org.hep.afa.R;
import org.hep.afa.takeaction.InviteFragment;

/**
 * Created by heather on 12/5/16.
 */

public class FriendsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        configureActionBar();

        getSupportFragmentManager().beginTransaction()
            .add(R.id.friends_activity_fragment_holder, new InviteFragment())
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
        actionBar.setCustomView(R.layout.action_bar_friends);
        actionBar.setDisplayShowCustomEnabled(true);
        findViewById(R.id.invite_button).setOnClickListener(this);
        Toolbar toolbar = (Toolbar) actionBar.getCustomView().getParent();
        toolbar.setContentInsetsAbsolute(0,0);
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.friends_activity_fragment_holder);
        if (fragment instanceof InviteFragment) {
            ((InviteFragment) fragment).inviteFriends();
        }
    }
}
