package com.jzdtl.anywhere.constants;

/**
 * 常量
 */
public class Constant {
    /**
     * 上传图片最大高度
     */
    public static final int UPLOAD_IMG_MAX_HEIGHT=1024;
    /**
     * 上传图片最大宽度
     */
    public static final int UPLOAD_IMG_MAX_WIDTH=1024;

    /**
     * 请求超时时间(单位:秒)
     */
    public static final int CONNECT_TIMEOUT=10;

    /**
     * 新注册用户默认性别
     */
    public static final String DEFAULT_GENDER="男";

    /**
     * 云游基地址
     */
    public static final String YUNYOU_BASE_URL = "http://q.chanyouji.com";

    /**
     * 首页接口
     * 参数 area
     * 例子：http://q.chanyouji.com/api/v2/destinations.json
     */
    public static final String YUNYOU_INDEX = "api/v2/destinations.json";

    /**
     * 国家接口
     * 参数 area
     * 例子：http://q.chanyouji.com/api/v2/destinations/list.json?area=china
     */
    public static final String YUNYOU_REGION = "api/v2/destinations/list.json";

    /**
     * 城市接口
     * http://q.chanyouji.com/api/v3/destinations/106.json
     */
    public static final String YUNYOU_CITY = "api/v3/destinations/{id}.json";

    /**
     * 搜索城市接口
     * http://q.chanyouji.com/api/v2/search.json?q=%E6%88%90%E9%83%BD
     */
    public static final String YUNYOU_SEARCH = "api/v2/search.json?";

    /**
     * 广告条接口
     * 参数market = general，first_launch=true(是否第一次启动)
     * 例子：http://q.chanyouji.com/api/v1/adverts.json?market=general&first_launch=false
     */
    public static final String YUNYOU_BANNER = "api/v1/adverts.json";

    /**
     *  游记接口
     *  参数page=1
     *  例子http://q.chanyouji.com/api/v1/timelines.json?page=1
     */
    public static final String YUNYOU_TIMELINES = "api/v1/timelines.json";

    /**
     *  用户信息接口
     *  例子：http://q.chanyouji.com/api/v1/users/19772/profiles.json
     */
    public static final String YUNYOU_PROFILES = "api/v1/users/{id}/profiles.json";

    /**
     *  用户游记接口
     *  参数page = 1
     *  例子：http://q.chanyouji.com/api/v1/users/19772/user_activities.json?page=1
     */
    public static final String YUNYOU_USER_ACTIVITIES = "api/v1/users/{id}/user_activities.json";

    /**
     *  用户游记按组分类有文章
     *  例子：http://q.chanyouji.com/api/v1/users/19772/user_activities.json?grouped=0
     */
    public static final String YUNYOU_USER_ACTIVITIES_GROUP_WITH_ARTICLE = "api/v1/users/{id}/user_activities.json?grouped=0";
    /**
     *  用户游记按组分类无文章
     *  例子：http://q.chanyouji.com/api/v1/users/19772/user_activities.json?grouped=1
     */
    public static final String YUNYOU_USER_ACTIVITIES_GROUP_NO_ARTICLE = "api/v1/users/{id}/user_activities.json?grouped=1";

    /**
     *  攻略接口
     */
    public static final String YUNYOU_STRATEGY = "http://chanyouji.com/api/wiki/destinations/{id}.json";
    /**
     *  用户评论
     *  参数type=UserActivity,id=64157,page=1
     *  例子：http://q.chanyouji.com/api/v1/comments.json?type=UserActivity&id=64157&page=1
     */
    public static final String YUNYOU_USER_COMMENT = "api/v1/comments.json";
    /**
     * 更多游记及分类排序
     * 参数：district_id=100007，月份filter=month_1，默认排序sort=made_desc，主题category_id=10，page=1，
     * http://q.chanyouji.com/api/v1/user_activities.json?district_id=100007&filter=month_1&sort=made_desc&category_id=10&page=1
     */
    public static final String YUNYOU_CITY_ACYIVITY="api/v1/user_activities.json";
    
    public static final String YUNYOU_EMAIL_FORMAT = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";

    public static final String YUNYOU_PHONE_FORMAT = "^1[3|4|5|7|8][0-9]\\d{8}$";

    public static final String YUNYOU_INVALID_FORMAT = "[\\u4e00-\\u9fa5_a-zA-Z0-9_]{0,1000}";

    public static final String YUNYOU_LENGTH_FORMAT = "[\\u4e00-\\u9fa5_a-zA-Z0-9_]{3,16}";
}
