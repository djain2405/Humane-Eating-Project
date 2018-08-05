package org.hep.afa.takeaction;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.hep.afa.R;
import org.hep.afa.utils.GeneralUtils;

/**
 * Created by heatherlaurin on 4/25/17.
 */

public class SurveyDialogFragment extends DialogFragment implements View.OnClickListener {

    private SurveyListener listener;

    public interface SurveyListener {
        void onTakeSurveySelected();
    }

    public void setSurveyListener(SurveyListener listener) {
        this.listener = listener;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, getActivity().getApplicationInfo().theme);
        setStyle(DialogFragment.STYLE_NO_TITLE, getActivity().getApplicationInfo().theme);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey, container, false);

        initViews(view);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.HEP_FullScreenDialog;
    }

    private void initViews(View rootView) {
        rootView.findViewById(R.id.survey_button).setOnClickListener(this);
        rootView.findViewById(R.id.close_survey_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.survey_button:
                if (listener != null) {
                    listener.onTakeSurveySelected();
                }
                Intent surveyIntent =
                        new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.survey_url)));
                if (GeneralUtils.isIntentSafe(surveyIntent)) {
                    startActivity(surveyIntent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.fade_out);
                }
                break;
        }
        dismiss();
    }
}
