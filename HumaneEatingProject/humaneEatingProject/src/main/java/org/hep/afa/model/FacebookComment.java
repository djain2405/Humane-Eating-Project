package org.hep.afa.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static android.text.format.DateUtils.MINUTE_IN_MILLIS;

/**
 * Created by heather on 10/12/16.
 */

public class FacebookComment implements Parcelable {

    String commentId;
    String commentDate;
    CharSequence timeAgo;
    String commentText;
    FacebookUser user;

    public FacebookComment(JSONObject comment) {
        setCommentId(comment.optString("id", null));
        setCommentDate(comment.optString("created_time", null));
        createTimeAgo(System.currentTimeMillis());
        setCommentText(comment.optString("message", null));

        JSONObject jsonUser = comment.optJSONObject("from");
        if (jsonUser != null) {
            setUser(new FacebookUser(jsonUser));
        }
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public void createTimeAgo(long nowInMillis) {

        try {
            long timeInMillis = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .parse(getCommentDate())
                .getTime();

            timeAgo = DateUtils.getRelativeTimeSpanString(timeInMillis, nowInMillis, MINUTE_IN_MILLIS, 0);
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

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public FacebookUser getUser() {
        return user;
    }

    public void setUser(FacebookUser user) {
        this.user = user;
    }

    public FacebookComment(Parcel in) {
        setCommentId(in.readString());
        setCommentDate(in.readString());
        setCommentText(in.readString());
        setUser((FacebookUser)in.readParcelable(FacebookUser.class.getClassLoader()));
    }

    public static final Parcelable.Creator CREATOR
        = new Parcelable.Creator() {
        public FacebookComment createFromParcel(Parcel in) {
            return new FacebookComment(in);
        }

        public FacebookComment[] newArray(int size) {
            return new FacebookComment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(commentId);
        dest.writeString(commentDate);
        dest.writeString(timeAgo.toString());
        dest.writeString(commentText);
        dest.writeParcelable(user, flags);
    }
}
