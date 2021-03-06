package com.jzdtl.anywhere.callback;

import com.jzdtl.anywhere.bean.BannerResult;
import com.jzdtl.anywhere.bean.CityActivityResult;
import com.jzdtl.anywhere.bean.CommentsResult;
import com.jzdtl.anywhere.bean.IndexResult;
import com.jzdtl.anywhere.bean.RegionResult;
import com.jzdtl.anywhere.bean.SearchResult;
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
     * 获得攻略结果
     * @param id 城市id
     * @return
     */
    @GET(Constant.YUNYOU_STRATEGY)
    Call<ResponseBody> getStrategyResult(@Path("id") String id);
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
     * 获得搜索结果
     * @param city 城市
     * @return
     */
    @GET(Constant.YUNYOU_SEARCH)
    Observable<SearchResult> getSearchResult(@Query("q") String city);

    /**
     * 获得用户信息
     * @return
     */
    @GET(Constant.YUNYOU_PROFILES)
    Observable<UserProfilesResult> getUserProfileResult(@Path("id")String id);

    /**
     * 获得用户所有游记
     * @param page 页数
     * @return
     */
    @GET(Constant.YUNYOU_USER_ACTIVITIES)
    Observable<UserActivitiesResult> getUserActivitiesResult(@Path("id")String id,@Query("page") String page);

    /**
     * 获得按组查询的结果，有文章有图片
     * @return
     */
    @GET(Constant.YUNYOU_USER_ACTIVITIES_GROUP_WITH_ARTICLE)
    Observable<UserGroupWithActivitiesResult> getUserGroupWithActivitiesResult(@Path("id")String id);

    /**
     * 获得按组查询的结 果，只有图片
     * @return
     */
    @GET(Constant.YUNYOU_USER_ACTIVITIES_GROUP_NO_ARTICLE)
    Observable<UserGroupNoActivitiesResult> getUserGroupNoActivitiesResult(@Path("id")String id);

    @GET(Constant.YUNYOU_USER_COMMENT)
    Observable<CommentsResult>getCommentResult(@Query("type")String type,@Query("id")String id,@Query("page")String page);
    /**
     * 更多游记
     */
    @GET(Constant.YUNYOU_CITY_ACYIVITY)
    Observable<CityActivityResult>getCityActivityResult(@Query("district_id")String id, @Query("filter")String month, @Query("sort")String sort,
                                                        @Query("category_id")String category_id, @Query("page")String page);

}
