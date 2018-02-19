package org.hep.afa.feed;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.hep.afa.R;
import org.hep.afa.feed.FeedBottomSheetDialogFragment;
import org.hep.afa.feed.SharePostDialogFragment;
import org.hep.afa.model.FacebookPost;
import org.hep.afa.utils.FacebookUtils;

import java.util.List;

/**
 * Created by heather on 10/12/16.
 */

public class FacebookPostAdapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>
    implements View.OnClickListener {

    private static final int HEADER_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private List<FacebookPost> posts;

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView postText;
        TextView postLink;
        TextView postedAgo;
        ImageView linkImage;
        ImageView moreButton;

        public ItemViewHolder(View itemView) {
            super(itemView);
            postText = (TextView) itemView.findViewById(R.id.fb_post_message);
            postLink = (TextView) itemView.findViewById(R.id.fb_post_link);
            postedAgo = (TextView) itemView.findViewById(R.id.fb_posted_time_ago);
            linkImage = (ImageView)itemView.findViewById(R.id.fb_link_image);
            moreButton = (ImageView) itemView.findViewById(R.id.fb_feed_more_icon);
        }
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View headerView) {
            super(headerView);
        }
    }

    public FacebookPostAdapter(List<FacebookPost> posts) {
        this.posts = posts;
        setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == ITEM_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_facebook_feed, parent, false);
            return new ItemViewHolder(view);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_header_facebook_feed, parent, false);
            return new HeaderViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemHolder = (ItemViewHolder) holder;

            // configure onItemClick handling
            holder.itemView.setTag(position);
            holder.itemView.setOnClickListener(this);

            FacebookPost post = posts.get(position - 1);

            String postText = post.getPostText();
            if (postText == null || postText.isEmpty()) {
                itemHolder.postText.setVisibility(View.GONE);
            }
            else {
                itemHolder.postText.setVisibility(View.VISIBLE);
                itemHolder.postText.setText(post.getPostedTextWithLinks());
                itemHolder.postText.setMovementMethod(LinkMovementMethod.getInstance());

                // include post text as part of item click
                itemHolder.postText.setTag(position);
                itemHolder.postText.setOnClickListener(this);
            }

            String postLink = post.getPostLink();
            if (postLink == null || postLink.isEmpty()) {
                itemHolder.postLink.setVisibility(View.GONE);
            }
            else {
                itemHolder.postLink.setVisibility(View.VISIBLE);
                itemHolder.postLink.setText(post.getPostedLinkWithLinks());
                itemHolder.postLink.setMovementMethod(LinkMovementMethod.getInstance());
            }

            itemHolder.postedAgo.setText(post.getTimeAgo());

            if (post.getPostImageUrl() == null || post.getPostImageUrl().isEmpty()) {
                Picasso.with(itemHolder.linkImage.getContext())
                    .load(FacebookUtils.createUserProfileImageUrl(post.getPostedBy().getUserId()))
                    .fit()
                    .centerCrop()
                    .into(((ItemViewHolder) holder).linkImage);
            }

            else {
                Picasso.with(itemHolder.linkImage.getContext())
                    .load(post.getPostImageUrl())
                    .fit()
                    .centerCrop()
                    .into(((ItemViewHolder) holder).linkImage);
                itemHolder.linkImage.setTag(position);
                itemHolder.linkImage.setOnClickListener(this);
            }

            itemHolder.moreButton.setTag(position);
            itemHolder.moreButton.setOnClickListener(this);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEADER_VIEW;
        }
        return ITEM_VIEW;
    }

    @Override
    public int getItemCount() {

        if (posts == null) {
            return 0;
        }
        return posts.size() + 1;
    }

    @Override
    public long getItemId(int position) {
        if (position == 0) {
            return 0;
        }
        return posts.get(position - 1).getUniquePostId();
    }

    @Override
    public void onClick(View v) {

        final int position = (int) v.getTag();

        switch (v.getId()) {
            case R.id.fb_feed_more_icon:
                if (v.getContext() instanceof FragmentActivity) {
                    FragmentActivity activity = (FragmentActivity)v.getContext();
                    FeedBottomSheetDialogFragment bottomSheetDialogFragment =
                        FeedBottomSheetDialogFragment.newInstance(posts.get(position - 1));
                    bottomSheetDialogFragment.show(activity.getSupportFragmentManager(),
                        activity.getString(R.string.dialog_fragment_tag));

                }
                return;

            case R.id.fb_link_image:
                if (v.getContext() instanceof FragmentActivity) {
                    FragmentActivity activity = (FragmentActivity)v.getContext();
                    SharePostDialogFragment dialogFragment =
                        SharePostDialogFragment.newInstance(posts.get(position - 1));
                    dialogFragment.show(activity.getSupportFragmentManager(), activity.getString(R.string.dialog_fragment_tag));
                }

                return;
        }

        // handle all else as item click
        final Context context = v.getContext();
        new AlertDialog.Builder(context)
            .setMessage(context.getString(R.string.view_post_conf))
            .setPositiveButton(context.getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        PackageManager packageManager = context.getPackageManager();

                        Intent fbIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("fb://post/" + posts.get(position - 1).getPostId()));

                        List activities = packageManager.queryIntentActivities(fbIntent,
                            PackageManager.MATCH_DEFAULT_ONLY);

                        if(activities.size() > 0) {
                            context.startActivity(fbIntent);
                        }
                        else {
                            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://www.facebook.com/" + posts.get(position - 1).getPostId()));

                            activities = packageManager.queryIntentActivities(webIntent,
                                PackageManager.MATCH_DEFAULT_ONLY);

                            if(activities.size() > 0) {
                                context.startActivity(webIntent);
                            }
                        }
                    }
                }
            )
            .setNegativeButton(v.getContext().getString(R.string.cancel), null)
            .create().show();
    }
}
