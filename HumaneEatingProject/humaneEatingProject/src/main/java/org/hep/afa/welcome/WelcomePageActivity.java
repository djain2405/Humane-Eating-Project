package org.hep.afa.welcome;

import java.util.List;
import java.util.Vector;

import com.flurry.android.FlurryAgent;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.hep.afa.R;

public class WelcomePageActivity extends AppCompatActivity {
	private WelcomePagerAdapter mPagerAdapter;
	TextView textViewWelcomePagesSwipe;
	ImageView imageViewWelcomePagesDotButton1;
	ImageView imageViewWelcomePagesDotButton2;
	ImageView imageViewWelcomePagesDotButton3;
	ImageView imageViewWelcomePagesDotButton4;
	public static boolean flag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager_layout);
		initializePaging();
		initializeViews();
		flag = true;
		if (getResources().getBoolean(R.bool.portrait_only)) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		}

		ActionBar actionBar = getSupportActionBar();
		actionBar.setIcon(android.R.color.transparent);
		actionBar.setCustomView(R.layout.welcomepages_label_centre);
		actionBar.setDisplayShowCustomEnabled(true);
	}

	private void initializePaging() {
		List<Fragment> fragments = new Vector<Fragment>();
		fragments.add(Fragment.instantiate(this,
				WelcomePageFragment1.class.getName()));
		fragments.add(Fragment.instantiate(this,
				WelcomePageFragment2.class.getName()));
		fragments.add(Fragment.instantiate(this,
				WelcomePageFragment3.class.getName()));
		fragments.add(Fragment.instantiate(this,
				WelcomePageFragment4.class.getName()));
		mPagerAdapter = new WelcomePagerAdapter(this.getSupportFragmentManager(),
				fragments);

		ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
		pager.setAdapter(mPagerAdapter);

		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				if (arg0 == 0) {
					imageViewWelcomePagesDotButton1
							.setImageResource(R.drawable.dot_green2x);
					imageViewWelcomePagesDotButton2
							.setImageResource(R.drawable.dot_beige2x);
					imageViewWelcomePagesDotButton3
							.setImageResource(R.drawable.dot_beige2x);
					imageViewWelcomePagesDotButton4
							.setImageResource(R.drawable.dot_beige2x);
				} else if (arg0 == 1) {
					imageViewWelcomePagesDotButton1
							.setImageResource(R.drawable.dot_beige2x);
					imageViewWelcomePagesDotButton2
							.setImageResource(R.drawable.dot_green2x);
					imageViewWelcomePagesDotButton3
							.setImageResource(R.drawable.dot_beige2x);
					imageViewWelcomePagesDotButton4
							.setImageResource(R.drawable.dot_beige2x);
				} else if (arg0 == 2) {
					textViewWelcomePagesSwipe.setVisibility(View.VISIBLE);
					imageViewWelcomePagesDotButton1
							.setImageResource(R.drawable.dot_beige2x);
					imageViewWelcomePagesDotButton2
							.setImageResource(R.drawable.dot_beige2x);
					imageViewWelcomePagesDotButton3
							.setImageResource(R.drawable.dot_green2x);
					imageViewWelcomePagesDotButton4
							.setImageResource(R.drawable.dot_beige2x);

				} else if (arg0 == 3) {
					textViewWelcomePagesSwipe.setVisibility(View.GONE);
					imageViewWelcomePagesDotButton1
							.setImageResource(R.drawable.dot_beige2x);
					imageViewWelcomePagesDotButton2
							.setImageResource(R.drawable.dot_beige2x);
					imageViewWelcomePagesDotButton3
							.setImageResource(R.drawable.dot_beige2x);
					imageViewWelcomePagesDotButton4
							.setImageResource(R.drawable.dot_green2x);

				} else {
					Toast.makeText(getApplicationContext(), "page Not Found",
							Toast.LENGTH_LONG).show();

				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.human_eating_main, menu);
		return true;
	}

	private void initializeViews() {
		textViewWelcomePagesSwipe = (TextView) findViewById(R.id.textViewSwipe);
		imageViewWelcomePagesDotButton1 = (ImageView) findViewById(R.id.imageViewDotButton1);
		imageViewWelcomePagesDotButton2 = (ImageView) findViewById(R.id.imageViewDotButton2);
		imageViewWelcomePagesDotButton3 = (ImageView) findViewById(R.id.imageViewDotButton3);
		imageViewWelcomePagesDotButton4 = (ImageView) findViewById(R.id.imageViewDotButton4);
	}
	
	@Override
	protected void onResume() {
		if(flag==false){
			finish();	
		}	
		super.onResume();
	}
	
	//Flurry Setup 
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
