package org.hep.afa.feed;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.hep.afa.R;
import org.hep.afa.model.FacebookComment;
import org.hep.afa.model.FacebookUser;
import org.hep.afa.transform.CircleTransform;
import org.hep.afa.utils.FacebookUtils;

import java.util.List;


/**
 * Created by heather on 10/21/16.
 */

public class FacebookCommentsAdapter extends RecyclerView.Adapter<FacebookCommentsAdapter.ViewHolder> {

    List<FacebookComment> comments;

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView userImage;
        TextView userName;
        TextView comment;
        TextView timeAgo;

        ViewHolder(View itemView) {
            super(itemView);
            userImage = (ImageView) itemView.findViewById(R.id.comment_user_image);
            userName = (TextView) itemView.findViewById(R.id.comment_user_name);
            comment = (TextView) itemView.findViewById(R.id.user_comment);
            timeAgo = (TextView) itemView.findViewById(R.id.comment_time_ago);
        }
    }

    public FacebookCommentsAdapter(List<FacebookComment> comments) {
        this.comments = comments;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FacebookComment comment = comments.get(position);
        FacebookUser user = comment.getUser();

        Picasso.with(holder.userName.getContext())
            .load(FacebookUtils.createUserProfileImageUrl(user.getUserId()))
            .transform(new CircleTransform())
            .fit()
            .centerCrop()
            .into(holder.userImage);

        holder.userName.setText(user.getUserName());

        String commentText = comment.getCommentText();
        if (commentText == null || commentText.isEmpty()) {
            holder.comment.setVisibility(View.GONE);
        }
        else {
            holder.comment.setVisibility(View.VISIBLE);
            holder.comment.setText(commentText);
        }
        holder.timeAgo.setText(comment.getTimeAgo());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_facebook_comments, parent, false));
    }


    @Override
    public int getItemCount() {
        return comments.size();
    }
}
