package org.hep.afa.feed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.hep.afa.R;
import org.hep.afa.main.NoConnectionDialogFragment;
import org.hep.afa.model.FacebookPost;
import org.hep.afa.utils.FacebookUtils;
import org.hep.afa.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by heather on 10/12/16.
 */

public class FeedFragment extends Fragment implements FacebookUtils.FacebookFeedListener {

    private final String TAG = getClass().getSimpleName();
    private final static int ENDLESS_SCROLL_THRESHOLD = 5;

    RecyclerView recyclerView;
    FacebookPostAdapter adapter;
    LinearLayoutManager layoutManager;
    TextView emptyFeed;
    ProgressBar progressBar;

    FacebookUtils fbUtils;
    RequestQueue requestQueue;
    List<FacebookPost> facebookPosts = new ArrayList<>();
    boolean loading;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestQueue = Volley.newRequestQueue(getContext());
        fbUtils = new FacebookUtils(requestQueue, TAG);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        progressBar = (ProgressBar) rootView.findViewById(R.id.feed_progressbar);
        emptyFeed   = (TextView) rootView.findViewById(R.id.empty_feed);

        // Configure the RecyclerView
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fb_posts_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(feedListener);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        requestQueue.start();
        loadFeed();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();

        // Clear any pending requests
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
        loading = false;
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fbUtils = null;
    }

    // Get FB posts
    private void loadFeed() {

        if (NetworkUtils.isConnected()) {
            loading = true;
            progressBar.setVisibility(View.VISIBLE);

            if (facebookPosts.size() > 0) {
                // refresh if we already have posts
                fbUtils.refreshFeed(this, facebookPosts.get(0));
            } else {
                // request new feed
                fbUtils.requestFeed(this);
            }
        }
        else {
            NoConnectionDialogFragment dialogFragment = new NoConnectionDialogFragment();
            dialogFragment.show(getFragmentManager(), getString(R.string.dialog_fragment_tag));

            if (facebookPosts.size() == 0) {
                showEmptyFeed();
            }
        }
    }

    // store the last time we updated the feed
    private void saveLastReadTime() {
        FacebookUtils.setLastFeedTime(System.currentTimeMillis()/1000);
    }

    private void showEmptyFeed() {
        recyclerView.setVisibility(View.GONE);
        emptyFeed.setVisibility(View.VISIBLE);
    }

    private void showFeed() {
        emptyFeed.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    // Create OnScrollListener to support endless scrolling on the RecyclerView
    RecyclerView.OnScrollListener feedListener = new RecyclerView.OnScrollListener() {

        int firstVisibleItem;
        int visibleItemCount;
        int totalItemCount;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = layoutManager.getItemCount();
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

            if (!loading &&
                (totalItemCount - visibleItemCount) <= (firstVisibleItem + ENDLESS_SCROLL_THRESHOLD)) {

                loading = true;
                fbUtils.nextRequestFeed(FeedFragment.this);
            }
        }
    };

    @Override
    public void feedRequestComplete(List<FacebookPost> posts) {
        facebookPosts.addAll(posts);
        if (facebookPosts.size() == 0) {
            showEmptyFeed();
        }
        else {
            showFeed();
            if (adapter == null) {
                adapter = new FacebookPostAdapter(facebookPosts);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
            saveLastReadTime();
        }
        loading = false;
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void nextFeedRequestComplete(List<FacebookPost> posts) {
        int index = facebookPosts.size();
        for (int i = 0; i < posts.size(); i++) {
            facebookPosts.add(posts.get(i));
        }

        adapter.notifyItemRangeInserted(index + 1, posts.size());
        loading = false;
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void feedRefreshComplete(List<FacebookPost> posts) {
        for (int i = 0; i < posts.size(); i++) {
            facebookPosts.add(i, posts.get(i));
        }
        adapter.notifyItemRangeInserted(1, posts.size());
        saveLastReadTime();
        loading = false;
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void feedError(VolleyError error) {
        Log.e(TAG, error.toString());
        if (facebookPosts.size() == 0) {
            showEmptyFeed();
        }
        loading = false;
        progressBar.setVisibility(View.GONE);
    }
}
