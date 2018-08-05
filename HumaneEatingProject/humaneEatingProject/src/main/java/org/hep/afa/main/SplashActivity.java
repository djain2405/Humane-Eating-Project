package org.hep.afa.main;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;

import com.flurry.android.FlurryAgent;

import org.hep.afa.main.HumaneEatingMainActivity;
import org.hep.afa.R;
import org.hep.afa.welcome.WelcomePage;
import org.hep.afa.welcome.WelcomePageActivity;

public class SplashActivity extends Activity {

	private final static int WAIT_TIME_IN_MILLISECONDS = 1500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if(!FlurryAgent.isSessionActive())
			FlurryAgent.init(this.getApplicationContext(),"Y8B9D2MQCFMCP2KJ5KVT");
		setContentView(R.layout.activity_screen_loader);
		if (getResources().getBoolean(R.bool.portrait_only)) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

		Handler handler = new Handler();

		/* thread for displaying the SplashScreen */
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				finish();
				showWelcomePageOrMainActivity();
			}

		}, WAIT_TIME_IN_MILLISECONDS);
	}

	private void showWelcomePageOrMainActivity() {
		if (WelcomePage.shouldDisplay(this)) {
			displayWelcomePage();
		} else {
			displayMainActivity();
		}
	}

	private void displayWelcomePage() {
		startActivity(new Intent(getApplicationContext(),
				WelcomePageActivity.class));
		WelcomePage.incrementDisplayedCount(this);
	}

	private void displayMainActivity() {
		startActivity(new Intent(getApplicationContext(),
				HumaneEatingMainActivity.class));
	}

}
