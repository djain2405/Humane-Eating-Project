package org.hep.afa.takeaction;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.hep.afa.R;

/**
 * Created by heather on 10/27/16.
 */

public class TakeActionMenuFragment extends Fragment implements View.OnClickListener {

    View donateButton;
    View surveyButton;
    View shoutOutButton;
    View inviteButton;
    View addRestaurantButton;
    View learnButton;
    View talkButton;
    View volunteerButton;

    public interface MenuItemSelectedListener {
        void onMenuItemSeleted(TakeActionActivity.TakeActionMenuItem selectedItem);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_takeaction_menu, container, false);

        initViews(rootView);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            MenuItemSelectedListener listener = (MenuItemSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                + " must implement MenuItemSelectedListener");
        }
    }

    private void initViews(View rootView) {
        donateButton = rootView.findViewById(R.id.donate_button);
        donateButton.setOnClickListener(this);

        surveyButton = rootView.findViewById(R.id.survey_button);
        surveyButton.setOnClickListener(this);

        shoutOutButton = rootView.findViewById(R.id.shoutout_button);
        shoutOutButton.setOnClickListener(this);

        inviteButton = rootView.findViewById(R.id.invite_button);
        inviteButton.setOnClickListener(this);

        addRestaurantButton = rootView.findViewById(R.id.add_restaurant_button);
        addRestaurantButton.setOnClickListener(this);

        learnButton = rootView.findViewById(R.id.learn_button);
        learnButton.setOnClickListener(this);

        talkButton = rootView.findViewById(R.id.talk_button);
        talkButton.setOnClickListener(this);

        volunteerButton = rootView.findViewById(R.id.volunteer_button);
        volunteerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MenuItemSelectedListener listener = (MenuItemSelectedListener)getActivity();

        switch (view.getId()) {
            case R.id.donate_button:
                listener.onMenuItemSeleted(TakeActionActivity.TakeActionMenuItem.TAKE_ACTION_DONATE);
                break;

            case R.id.survey_button:
                listener.onMenuItemSeleted(TakeActionActivity.TakeActionMenuItem.TAKE_ACTION_SURVEY);
                break;

            case R.id.shoutout_button:
                listener.onMenuItemSeleted(TakeActionActivity.TakeActionMenuItem.TAKE_ACTION_SHOUT_OUT);
                break;

            case R.id.invite_button:
                listener.onMenuItemSeleted(TakeActionActivity.TakeActionMenuItem.TAKE_ACTION_INVITE);
                break;

            case R.id.add_restaurant_button:
                listener.onMenuItemSeleted(TakeActionActivity.TakeActionMenuItem.TAKE_ACTION_ADD_RESTAURANT);
                break;

            case R.id.learn_button:
                listener.onMenuItemSeleted(TakeActionActivity.TakeActionMenuItem.TAKE_ACTION_LEARN);
                break;

            case R.id.talk_button:
                listener.onMenuItemSeleted(TakeActionActivity.TakeActionMenuItem.TAKE_ACTION_TALK);
                break;

            case R.id.volunteer_button:
                listener.onMenuItemSeleted(TakeActionActivity.TakeActionMenuItem.TAKE_ACTION_VOLUNTEER);
                break;
        }
    }
}
