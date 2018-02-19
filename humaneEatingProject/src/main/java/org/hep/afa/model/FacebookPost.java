package org.hep.afa.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.SpannableString;
import android.text.format.DateUtils;
import android.text.util.Linkify;

import org.hep.afa.utils.FacebookUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static android.text.format.DateUtils.MINUTE_IN_MILLIS;

/**
 * Created by heather on 10/12/16.
 */

public class FacebookPost implements Parcelable {

    public static final Pattern pattern =  Pattern.compile("[#]+[A-Za-z0-9-_]+\\b");
    public static final Pattern httpPattern = Pattern.compile("[a-z]+:\\/\\/[^ \\n]*");

    String postId;
    Long uniquePostId;
    String postDate;
    String postType;
    String postText;
    SpannableString postedTextWithLinks;
    String postLink;
    SpannableString postLinkWithLinks;
    String postImageUrl;
    List<FacebookComment> comments;
    FacebookUser postedBy;
    CharSequence timeAgo;
    long sinceTime;

    public FacebookPost(JSONObject post) {
        setPostId(post.optString("id", null));
        setPostDate(post.optString("created_time", null));
        createTimeAgo(System.currentTimeMillis());
        setPostType(post.optString("type", null));
        setPostText(post.optString("message", null));
        setPostLink(post.optString("link", null));
        setPostImageUrl(post.optString("picture", null));

        comments = new ArrayList<>();
        JSONObject jsonCommentObject = post.optJSONObject("comments");
        if (jsonCommentObject != null) {
            JSONArray jsonComments = jsonCommentObject.optJSONArray("data");
            if (jsonComments != null) {
                for (int i = 0; i < jsonComments.length(); i++) {
                    comments.add(new FacebookComment(jsonComments.optJSONObject(i)));
                }
            }
        }

        JSONObject jsonUser = post.optJSONObject("from");
        if (jsonUser != null) {
            setPostedBy(new FacebookUser(jsonUser));
        }
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
        setUniquePostId(postId);
    }

    public void setUniquePostId(String postId) {
        uniquePostId = Long.parseLong(postId.substring(postId.indexOf('_') + 1));
    }

    public Long getUniquePostId() {
        return uniquePostId;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public void createTimeAgo(long nowInMillis) {

        try {
            long timeInMillis = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .parse(getPostDate())
                .getTime();

            timeAgo = "Posted " + DateUtils.getRelativeTimeSpanString(timeInMillis, nowInMillis, MINUTE_IN_MILLIS, 0);
            sinceTime = timeInMillis/1000;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void setTimeAgo(CharSequence timeAgo) {
        this.timeAgo = timeAgo;
    }

    public CharSequence getTimeAgo() {
        return timeAgo;
    }

    public void setSinceTime(long sinceTime) {
        this.sinceTime = sinceTime;
    }

    public long getSinceTime() {
        return sinceTime;
    }

    public FacebookUser getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(FacebookUser postedBy) {
        this.postedBy = postedBy;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
        setPostedTextWithLinks(this.postText);
    }

    private void setPostedTextWithLinks(String postText) {

        if (postText == null) {
            postedTextWithLinks = null;
        }
        else {
            postedTextWithLinks = new SpannableString(postText);

            // some posts contain hashtags
            Linkify.addLinks(postedTextWithLinks, pattern, FacebookUtils.HASHTAG_SCHEME);

            // some posts contain urls
            Linkify.addLinks(postedTextWithLinks, httpPattern, FacebookUtils.HTTP_SHEME);
        }
    }

    public SpannableString getPostedTextWithLinks() {
        return postedTextWithLinks;
    }

    public String getPostLink() {
        return postLink;
    }

    public void setPostLink(String postLink) {
        this.postLink = postLink;
        setPostedLinkWithLinks(postLink);
    }

    private void setPostedLinkWithLinks(String postLink) {
        if (postLink == null) {
            postLinkWithLinks = null;
        }
        else {
            postLinkWithLinks = new SpannableString(postLink);
            Linkify.addLinks(postLinkWithLinks, httpPattern, FacebookUtils.HTTP_SHEME);
        }
    }

    public SpannableString getPostedLinkWithLinks() {
        return postLinkWithLinks;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public List<FacebookComment> getComments() {
        return comments;
    }

    public void setComments(List<FacebookComment> comments) {
        this.comments = comments;
    }

    public FacebookPost(Parcel in) {
        setUniquePostId(in.readString());
        setPostDate(in.readString());
        setPostType(in.readString());
        setPostText(in.readString());
        setPostLink(in.readString());
        setPostImageUrl(in.readString());
        setTimeAgo(in.readString());
        setSinceTime(in.readLong());
        in.readList(comments, List.class.getClassLoader());
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        public FacebookPost createFromParcel(Parcel in) {
            return new FacebookPost(in);
        }

        public FacebookPost[] newArray(int size) {
            return new FacebookPost[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(postId);
        dest.writeString(postDate);
        dest.writeString(postType);
        dest.writeString(postText);
        dest.writeString(postLink);
        dest.writeString(postImageUrl);
        dest.writeString(timeAgo.toString());
        dest.writeLong(sinceTime);
        dest.writeList(comments);
    }
}
