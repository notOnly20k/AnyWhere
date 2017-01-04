package com.jzdtl.anywhere.callback;

import com.jzdtl.anywhere.bean.BannerResult;
import com.jzdtl.anywhere.bean.IndexResult;
import com.jzdtl.anywhere.bean.RegionResult;
import com.jzdtl.anywhere.bean.TimeLinesResult;
import com.jzdtl.anywhere.bean.UserActivitiesResult;
import com.jzdtl.anywhere.bean.UserGroupNoActivitiesResult;
import com.jzdtl.anywhere.bean.UserGroupWithActivitiesResult;
import com.jzdtl.anywhere.bean.UserProfilesResult;
import com.jzdtl.anywhere.constants.Constant;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by gcy on 2016/12/27.
 */

public interface ApiService {
    /**
     * 获得区域结果
     * @param area 国家名称 如果：china
     * @return
     */
    @GET(Constant.YUNYOU_REGION)
    Observable<RegionResult> getResionResult(@Query("area") String area);

    /**
     * 获得城市结果
     * @param id 城市id
     * @return
     */
    @GET(Constant.YUNYOU_CITY)
    Call<ResponseBody> getDestinationResult(@Path("id") String id);
    /**
     *  获得广告条结果
     * @param market general
     * @param first_launch 是否第一次启动 true/false
     * @return
     */
    @GET(Constant.YUNYOU_BANNER)
    Observable<BannerResult> getBannerResult(@Query("market") String market, @Query("first_launch") String first_launch);

    /**
     *  获得首页结果
     * @return
     */
    @GET(Constant.YUNYOU_INDEX)
    Observable<IndexResult> getIndexResult();

    /**
     * 获得游记
     * @param page 页数
     * @return
     */
    @GET(Constant.YUNYOU_TIMELINES)
    Observable<TimeLinesResult> getTimeLinesResult(@Query("page") String page);

    /**
     * 获得用户信息
     * @return
     */
    @GET(Constant.YUNYOU_PROFILES)
    Observable<UserProfilesResult> getUserProfileResult();

    /**
     * 获得用户所有游记
     * @param page 页数
     * @return
     */
    @GET(Constant.YUNYOU_USER_ACTIVITIES)
    Observable<UserActivitiesResult> getUserActivitiesResult(@Query("page") String page);

    /**
     * 获得按组查询的结果，有文章有图片
     * @return
     */
    @GET(Constant.YUNYOU_USER_ACTIVITIES_GROUP_WITH_ARTICLE)
    Observable<UserGroupWithActivitiesResult> getUserGroupWithActivitiesResult();

    /**
     * 获得按组查询的结 果，只有图片
     * @return
     */
    @GET(Constant.YUNYOU_USER_ACTIVITIES_GROUP_NO_ARTICLE)
    Observable<UserGroupNoActivitiesResult> getUserGroupNoActivitiesResult();


}
