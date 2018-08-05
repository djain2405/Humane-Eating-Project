package org.hep.afa.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by heather on 10/12/16.
 */

public class FacebookUser implements Parcelable {

    String userId;
    String userName;

    public FacebookUser(JSONObject user) {
        setUserId(user.optString("id", null));
        setUserName(user.optString("name", null));
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public FacebookUser(Parcel in) {
        setUserId(in.readString());
        setUserName(in.readString());
    }

    public static final Parcelable.Creator CREATOR
        = new Parcelable.Creator() {
        public FacebookUser createFromParcel(Parcel in) {
            return new FacebookUser(in);
        }

        public FacebookUser[] newArray(int size) {
            return new FacebookUser[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(userName);
    }
}
