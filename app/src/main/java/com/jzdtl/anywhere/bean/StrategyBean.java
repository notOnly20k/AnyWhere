package com.jzdtl.anywhere.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by gcy on 2017/1/6.
 */

public class StrategyBean implements Parcelable{
    private String type;
    private List<Page> pages;

    public static class Page implements Parcelable{
        private String title;
        private List<Children> childrens;

        public static class Children implements Parcelable{
            private String title;
            private List<Section> sections;

            public static class Section implements Parcelable{
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
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.title);
                    dest.writeString(this.description);
                    dest.writeStringList(this.photos);
                }

                public Section() {
                }

                protected Section(Parcel in) {
                    this.title = in.readString();
                    this.description = in.readString();
                    this.photos = in.createStringArrayList();
                }

                public static final Creator<Section> CREATOR = new Creator<Section>() {
                    @Override
                    public Section createFromParcel(Parcel source) {
                        return new Section(source);
                    }

                    @Override
                    public Section[] newArray(int size) {
                        return new Section[size];
                    }
                };
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public List<Section> getSections() {
                return sections;
            }

            public void setSections(List<Section> sections) {
                this.sections = sections;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.title);
                dest.writeTypedList(this.sections);
            }

            public Children() {
            }

            protected Children(Parcel in) {
                this.title = in.readString();
                this.sections = in.createTypedArrayList(Section.CREATOR);
            }

            public static final Creator<Children> CREATOR = new Creator<Children>() {
                @Override
                public Children createFromParcel(Parcel source) {
                    return new Children(source);
                }

                @Override
                public Children[] newArray(int size) {
                    return new Children[size];
                }
            };
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<Children> getChildrens() {
            return childrens;
        }

        public void setChildrens(List<Children> childrens) {
            this.childrens = childrens;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeTypedList(this.childrens);
        }

        public Page() {
        }

        protected Page(Parcel in) {
            this.title = in.readString();
            this.childrens = in.createTypedArrayList(Children.CREATOR);
        }

        public static final Creator<Page> CREATOR = new Creator<Page>() {
            @Override
            public Page createFromParcel(Parcel source) {
                return new Page(source);
            }

            @Override
            public Page[] newArray(int size) {
                return new Page[size];
            }
        };
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeTypedList(this.pages);
    }

    public StrategyBean() {
    }

    protected StrategyBean(Parcel in) {
        this.type = in.readString();
        this.pages = in.createTypedArrayList(Page.CREATOR);
    }

    public static final Creator<StrategyBean> CREATOR = new Creator<StrategyBean>() {
        @Override
        public StrategyBean createFromParcel(Parcel source) {
            return new StrategyBean(source);
        }

        @Override
        public StrategyBean[] newArray(int size) {
            return new StrategyBean[size];
        }
    };
}
