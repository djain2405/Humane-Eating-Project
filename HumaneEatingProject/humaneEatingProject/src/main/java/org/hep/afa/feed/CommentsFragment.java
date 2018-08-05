package org.hep.afa.feed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.hep.afa.R;
import org.hep.afa.model.FacebookComment;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by heather on 10/21/16.
 */

public class CommentsFragment extends Fragment {

    private static final String COMMENTS = "comments";


    RecyclerView recyclerView;
    FacebookCommentsAdapter adapter;
    List<FacebookComment> comments;

    public static CommentsFragment newInstance(List<FacebookComment> comments) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(COMMENTS, (ArrayList)comments);

        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments, container, false);

        comments = getArguments().getParcelableArrayList(COMMENTS);

        recyclerView = (RecyclerView) view.findViewById(R.id.fb_comments_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (adapter == null) {
            adapter = new FacebookCommentsAdapter(comments);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
