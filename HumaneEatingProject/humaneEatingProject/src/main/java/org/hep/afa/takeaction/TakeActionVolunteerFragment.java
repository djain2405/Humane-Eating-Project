package org.hep.afa.takeaction;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.hep.afa.R;

import java.util.List;

/**
 * Created by heather on 10/31/16.
 */

public class TakeActionVolunteerFragment extends Fragment implements View.OnClickListener {

    Button volunteerButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_takeaction_volunteer, container, false);

        volunteerButton = (Button) view.findViewById(R.id.takeaction_volunteer_button);
        volunteerButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.takeaction_volunteer_button:
                // Have noticed some issues with Chrome displaying this correctly, have found
                // that clearing browser cache fixes issue
                Intent browserIntent =
                    new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.volunteermatch.org/search/org496547.jsp"));

                PackageManager packageManager = getActivity().getPackageManager();
                List activities = packageManager.queryIntentActivities(browserIntent,
                    PackageManager.MATCH_DEFAULT_ONLY);

                if (activities.size() > 0) {
                    startActivity(browserIntent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
                }
                break;
        }
    }
}
