package org.hep.afa.addlisting;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.flurry.android.FlurryAgent;

import org.hep.afa.activity.LegendActivity;
import org.hep.afa.R;

/**
 * This activity displays restaurant list available to be added to the backend Parse Storage.
 * It auto populates the restaurant information upon the addition.
 */
public class AutoAddListingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_add_listing);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setCustomView(R.layout.actionbar_auto_add_listing);
        actionBar.setDisplayShowCustomEnabled(true);

        // Section: Add Manually button
        Button addManually = (Button) findViewById(R.id.add_manually);
        addManually.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), AddListingActivity.class));
                        overridePendingTransition(R.anim.slide_up2, R.anim.slide_down2);
                    }

                }
        );

        // Section: Qualifications button
        Button qualifications = (Button) findViewById(R.id.qualifications);
        qualifications.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), LegendActivity.class));
                        overridePendingTransition(R.anim.slide_up2, R.anim.slide_down2);
                    }
                }
        );

        // Start the fragment part here.
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PlainRestaurantListViewFragment plainRestaurantFragment = new PlainRestaurantListViewFragment();
        fragmentTransaction.replace(R.id.restaurant_list, plainRestaurantFragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_auto_add_listing, menu);
        for(int i=0; i < menu.size(); i++)
        {
            menu.getItem(i).setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId())
        {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
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
