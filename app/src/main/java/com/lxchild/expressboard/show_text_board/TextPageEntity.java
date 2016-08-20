package com.lxchild.expressboard.show_text_board;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LXChild on 2015/6/1.
 */
public class TextPageEntity implements Parcelable {
    private String text;
    public static final Parcelable.Creator<TextPageEntity> CREATOR = new Parcelable.Creator<TextPageEntity>() {

        @Override
        public TextPageEntity createFromParcel(Parcel source) {
            TextPageEntity page = new TextPageEntity();
            page.text = source.readString();
            return page;
        }

        @Override
        public TextPageEntity[] newArray(int size) {
            return new TextPageEntity[0];
        }
    };

    public String getContentText() {
        return text;
    }

    public void setContentText(String txt_resource) {
        this.text = txt_resource;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
    }
}
