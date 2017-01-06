package com.jzdtl.anywhere.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gcy on 2017/1/5.
 */

public class ExpandListBean implements Parcelable{
    private String groupName;
    private List<ContentBean> contentData;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<ContentBean> getContentData() {
        return contentData;
    }

    public void setContentData(List<ContentBean> contentData) {
        this.contentData = contentData;
    }

    public static class ContentBean implements Parcelable{
        private String title;
        private String description;
        private List<String> photos;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<String> getPhotos() {
            return photos;
        }

        public void setPhotos(List<String> photos) {
            this.photos = photos;
        }

        @Override
        public String toString() {
            return "ContentBean{" +
                    "title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", photos=" + photos +
                    '}';
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.description);
            dest.writeStringList(this.photos);
        }

        public ContentBean() {
        }

        protected ContentBean(Parcel in) {
            this.title = in.readString();
            this.description = in.readString();
            this.photos = in.createStringArrayList();
        }

        public static final Creator<ContentBean> CREATOR = new Creator<ContentBean>() {
            @Override
            public ContentBean createFromParcel(Parcel source) {
                return new ContentBean(source);
            }

            @Override
            public ContentBean[] newArray(int size) {
                return new ContentBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.groupName);
        dest.writeList(this.contentData);
    }

    public ExpandListBean() {
    }

    protected ExpandListBean(Parcel in) {
        this.groupName = in.readString();
        this.contentData = new ArrayList<ContentBean>();
        in.readList(this.contentData, ContentBean.class.getClassLoader());
    }

    public static final Creator<ExpandListBean> CREATOR = new Creator<ExpandListBean>() {
        @Override
        public ExpandListBean createFromParcel(Parcel source) {
            return new ExpandListBean(source);
        }

        @Override
        public ExpandListBean[] newArray(int size) {
            return new ExpandListBean[size];
        }
    };

    @Override
    public String toString() {
        return "ExpandListBean{" +
                "groupName='" + groupName + '\'' +
                ", contentData=" + contentData +
                '}';
    }
}
