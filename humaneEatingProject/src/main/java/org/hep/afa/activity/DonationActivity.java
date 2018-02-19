package org.hep.afa.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;

import org.hep.afa.R;

/**
 * DonationActivity for.. well .. Donation.
 */
public class DonationActivity extends AppCompatActivity
{

    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_donate);

        // up navigation button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_plain);
        actionBar.setDisplayShowCustomEnabled(true);
        TextView label = (TextView) findViewById(R.id.actionbar_title);
        label.setText(R.string.donate);

		// Section: Donation button
		Button donate = (Button) findViewById(R.id.donate_button);
		donate.setOnClickListener(
				new View.OnClickListener()
                {
					@Override
					public void onClick(View v) {
                        // Redirect to AFA donation site.
						FlurryAgent.logEvent("Donate View");
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_donate))));
					}
				}
		);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
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

}
