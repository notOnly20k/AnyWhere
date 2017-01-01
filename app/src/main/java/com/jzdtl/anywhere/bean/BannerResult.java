package com.jzdtl.anywhere.bean;

import java.util.List;

/**
 * Created by gcy on 2016/12/27.
 */

public class BannerResult {

    /**
     * message : ok
     * status : 200
     * data : [{"photo":{"width":1280,"height":600,"photo_url":"http://inspiration.chanyouji.cn/Advert/146/4fb28b484589bf25cf26cc99744383ce.jpg"},"id":146,"ios_url":"","android_url":"","target_id":"103","advert_type":"album","topic":"氢气球每周精选","market":"all","open_in_browser":false},{"photo":{"width":1280,"height":600,"photo_url":"http://inspiration.chanyouji.cn/Advert/140/15b26df3cfee6743346fe4958a119f8d.jpg"},"id":140,"ios_url":"","android_url":"","target_id":"98","advert_type":"album","topic":"跨越北极圈寻访圣诞村","market":"all","open_in_browser":false},{"photo":{"width":1280,"height":600,"photo_url":"http://inspiration.chanyouji.cn/Advert/142/b120581c9804c5a42917e11261198296.jpg"},"id":142,"ios_url":"http://m.ctrip.com/html5/you/operations/app.html?app=1&popup=close&autoawaken=close","android_url":"http://m.ctrip.com/html5/you/operations/app.html?app=1&popup=close&autoawaken=close","target_id":"","advert_type":"url","topic":"携程攻略","market":"all","open_in_browser":false},{"photo":{"width":1280,"height":600,"photo_url":"http://inspiration.chanyouji.cn/Advert/128/3767179302031cf063bc7d85454bf3cd.jpg"},"id":128,"ios_url":"","android_url":"","target_id":"90","advert_type":"album","topic":"在热气球里遇见世界","market":"all","open_in_browser":false}]
     */

    private String message;
    private int status;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * photo : {"width":1280,"height":600,"photo_url":"http://inspiration.chanyouji.cn/Advert/146/4fb28b484589bf25cf26cc99744383ce.jpg"}
         * id : 146
         * ios_url :
         * android_url :
         * target_id : 103
         * advert_type : album
         * topic : 氢气球每周精选
         * market : all
         * open_in_browser : false
         */

        private PhotoBean photo;
        private int id;
        private String ios_url;
        private String android_url;
        private String target_id;
        private String advert_type;
        private String topic;
        private String market;
        private boolean open_in_browser;

        public PhotoBean getPhoto() {
            return photo;
        }

        public void setPhoto(PhotoBean photo) {
            this.photo = photo;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIos_url() {
            return ios_url;
        }

        public void setIos_url(String ios_url) {
            this.ios_url = ios_url;
        }

        public String getAndroid_url() {
            return android_url;
        }

        public void setAndroid_url(String android_url) {
            this.android_url = android_url;
        }

        public String getTarget_id() {
            return target_id;
        }

        public void setTarget_id(String target_id) {
            this.target_id = target_id;
        }

        public String getAdvert_type() {
            return advert_type;
        }

        public void setAdvert_type(String advert_type) {
            this.advert_type = advert_type;
        }

        public String getTopic() {
            return topic;
        }

        public void setTopic(String topic) {
            this.topic = topic;
        }

        public String getMarket() {
            return market;
        }

        public void setMarket(String market) {
            this.market = market;
        }

        public boolean isOpen_in_browser() {
            return open_in_browser;
        }

        public void setOpen_in_browser(boolean open_in_browser) {
            this.open_in_browser = open_in_browser;
        }

        public static class PhotoBean {
            /**
             * width : 1280
             * height : 600
             * photo_url : http://inspiration.chanyouji.cn/Advert/146/4fb28b484589bf25cf26cc99744383ce.jpg
             */

            private int width;
            private int height;
            private String photo_url;

            public int getWidth() {
                return width;
            }

            public void setWidth(int width) {
                this.width = width;
            }

            public int getHeight() {
                return height;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public String getPhoto_url() {
                return photo_url;
            }

            public void setPhoto_url(String photo_url) {
                this.photo_url = photo_url;
            }
        }
    }


}
