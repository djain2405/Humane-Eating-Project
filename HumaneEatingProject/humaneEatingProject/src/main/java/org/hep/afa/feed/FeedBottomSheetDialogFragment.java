package org.hep.afa.feed;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.hep.afa.R;
import org.hep.afa.model.FacebookPost;


/**
 * Created by heather on 10/20/16.
 */

public class FeedBottomSheetDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private static String POST = "post";

    private FacebookPost post;

    TextView share;
    TextView showComments;
    TextView cancel;

    /**
     * The Activity hosting this dialog fragment must implement the FeedActionListener
     * interface
     */
    public interface FeedActionListener {
        void onShareWithFacebook(FacebookPost post);
        void onShowComments(FacebookPost post);
    }

    public static FeedBottomSheetDialogFragment newInstance(FacebookPost post) {

        Bundle args = new Bundle();
        args.putParcelable(POST, post);

        FeedBottomSheetDialogFragment fragment = new FeedBottomSheetDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.bottomsheet_feed, null);

        share = (TextView) view.findViewById(R.id.feeddialog_share);
        share.setOnClickListener(this);

        showComments = (TextView) view.findViewById(R.id.feeddialog_comments);
        showComments.setOnClickListener(this);

        cancel = (TextView) view.findViewById(R.id.feeddialog_cancel);
        cancel.setOnClickListener(this);

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        post = getArguments().getParcelable(POST);

        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.HEP_BottomSheetStyleDialog);
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            FeedActionListener listener = (FeedActionListener) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement FeedActionListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // If we want to change the size of the dialog to improve look in landscape or tablet, update it here....
        // the commented line below simply wraps the width of the specified layout - which isn't probably quite
        // what we want
        // getDialog().getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    public void onClick(View v) {

        FeedActionListener listener = (FeedActionListener) getActivity();

        switch (v.getId()) {
            case R.id.feeddialog_comments:
                listener.onShowComments(post);
                break;

            case R.id.feeddialog_share:
                listener.onShareWithFacebook(post);
        }
        dismiss();
    }
}
